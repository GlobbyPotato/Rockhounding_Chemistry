package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralAnalyzer;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMineralAnalyzer extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyReceiver, IEnergyStorage {
    private ItemStack[] slots = new ItemStack[8];
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {2, 1};
    private static final int[] SLOTS_SIDES = new int[] {1, 3};
    Random rand = new Random();

    public int powerCount;

    public int powerMax = 32000;
    
    private int consumedSulf = 1;
    private int consumedChlo = 3;
    private int consumedFluo = 2;

    private int cookTime;
    private int totalCookTime;

    public static int analyzingSpeed;

    private String furnaceCustomName;

	private int inputSlot = 0;
	private int fuelSlot = 1;
	private int outputSlot = 2;
	private int consumableSlot = 3;
	private int sulfSlot = 4;
	private int chloSlot = 5;
	private int fluoSlot = 6;
	private int inductorSlot = 7;



    //----------------------- STANDARD -----------------------
    public int getSizeInventory(){
        return this.slots.length;
    }

    @Nullable
    public ItemStack getStackInSlot(int index){
        return this.slots[index];
    }

    @Nullable
    public ItemStack decrStackSize(int index, int count){
        return ItemStackHelper.getAndSplit(this.slots, index, count);
    }

    @Nullable
    public ItemStack removeStackFromSlot(int index){
        return ItemStackHelper.getAndRemove(this.slots, index);
    }

    public void setInventorySlotContents(int index, @Nullable ItemStack stack){
        boolean flag = stack != null && stack.isItemEqual(this.slots[index]) && ItemStack.areItemStackTagsEqual(stack, this.slots[index]);
        this.slots[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }
        if (index == 0 && !flag){
            this.totalCookTime = this.getCookTime(stack);
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public String getName(){
        return this.hasCustomName() ? this.furnaceCustomName : "container.mineralAnalyzer";
    }

    public boolean hasCustomName(){
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_){
        this.furnaceCustomName = p_145951_1_;
    }

    public static void func_189676_a(DataFixer p_189676_0_){
        p_189676_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("MineralAnalyzer", new String[] {"Items"}));
    }

    public void clear() {
        for (int i = 0; i < this.slots.length; ++i) {
            this.slots[i] = null;
        }
    }

    public int getInventoryStackLimit(){
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player){
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public int[] getSlotsForFace(EnumFacing side){
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    public void openInventory(EntityPlayer player){}

    public void closeInventory(EntityPlayer player){}

    public boolean isItemValidForSlot(int index, ItemStack stack){
        if (index == 2){
            return false;
        }else if (index != 1){
            return true;
        }else{
            ItemStack itemstack = this.slots[1];
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}



    //----------------------- CUSTOM -----------------------
    public int getCookTime(@Nullable ItemStack stack){
        return this.machineSpeed();
    }

    public int machineSpeed(){
		return analyzingSpeed;
    	
    }

    public String getGuiID(){
        return Reference.MODID + ":mineralAnalyzer";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerMineralAnalyzer(playerInventory, this);
    }

    public int getFieldCount() {
        return 4;
    }

    public int getField(int id){
        switch (id){
            case 0: return this.powerCount;
            case 1: return this.powerMax;
            case 2: return this.cookTime;
            case 3: return this.totalCookTime;
            default:return 0;
        }
    }

    public void setField(int id, int value){
        switch (id){
            case 0: this.powerCount = value; break;
            case 1: this.powerMax = value; break;
            case 2: this.cookTime = value; break;
            case 3: this.totalCookTime = value;
        }
    }
    
    public boolean hasPower(){
        return this.powerCount > 0;
    }

    @SideOnly(Side.CLIENT)
    public static boolean isBurning(IInventory inventory){
        return inventory.getField(2) > 0;
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        if (direction == EnumFacing.DOWN && index == 1){
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET){
                return false;
            }
        }
        return true;
    }

    
    
    
    //----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.slots.length){
                this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
        this.powerCount = compound.getInteger("PowerCount");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        if (compound.hasKey("CustomName", 8)){
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("PowerCount", this.powerCount);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("CookTimeTotal", this.totalCookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.slots.length; ++i){
            if (this.slots[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.slots[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
        if (this.hasCustomName()){
            compound.setString("CustomName", this.furnaceCustomName);
        }
        return compound;
    }




    //----------------------- PROCESS -----------------------
    public static boolean isItemFuel(ItemStack stack){
        return getItemBurnTime(stack) > 0;
    }

    public static int getItemBurnTime(ItemStack stack){
        if (stack == null){
            return 0;
        }else{
            Item item = stack.getItem();
            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR){
                Block block = Block.getBlockFromItem(item);
                if (block == Blocks.WOODEN_SLAB){return 150;}
                if (block.getDefaultState().getMaterial() == Material.WOOD){return 300;}
                if (block == Blocks.COAL_BLOCK){return 14400;}
            }
            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
        }
    }




	//PROCESSING

    @Override
    public void update(){
    	if(slots[fuelSlot] != null){fuelHandler();}
    	if(!worldObj.isRemote){
	    	if(canAnalyze()){ execute(); }
    	}
    }

    private void execute() {
    	boolean flag = false;
		cookTime++; powerCount--;
		if(cookTime >= machineSpeed()) {
			cookTime = 0; 
			handleSlots();
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private void handleSlots() {
		int uses = 0;
		int quantity = 0;
		//calculate output
		calculateOutput(slots[inputSlot].getItemDamage());
		//decrease input
		slots[inputSlot].stackSize--; if(slots[inputSlot].stackSize <= 0) { slots[inputSlot] = null; }
		//decrease consumable
		uses = slots[consumableSlot].getTagCompound().getInteger(ModArray.toolUses) - 1;
		slots[consumableSlot].getTagCompound().setInteger(ModArray.toolUses, uses);
		if(uses <= 0){slots[consumableSlot] = null;}
		//decrease acids
		quantity = slots[sulfSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedSulf;
		slots[sulfSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){slots[sulfSlot].getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.acidNames[0]);}
		quantity = slots[chloSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedChlo;
		slots[chloSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){slots[chloSlot].getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.acidNames[0]);}
		quantity = slots[fluoSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedFluo;
		slots[fluoSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){slots[fluoSlot].getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.acidNames[0]);}
	}

	private void calculateOutput(int entryMineral) {
		int	mineralChance = rand.nextInt(100);
		int outputType = 0; Item outputMineral = null;
		
		if(entryMineral == 1){  
			outputMineral = ModContents.arsenateShards;
			if(mineralChance < 80){
				outputType = 0;
			}else{
				outputType = 1;
			}
		}else if(entryMineral == 2){   
			outputMineral = ModContents.borateShards;
			if(mineralChance < 25){
				outputType = 0;
			}else if(mineralChance >= 25 && mineralChance < 40){
				outputType = 1;
			}else if(mineralChance >= 40 && mineralChance < 55){
				outputType = 2;
			}else if(mineralChance >= 55 && mineralChance < 75){
				outputType = 3;
			}else if(mineralChance >= 75 && mineralChance < 85){
				outputType = 4;
			}else if(mineralChance >= 85){
				outputType = 5;
			}
		}else if(entryMineral == 3){   
			outputMineral = ModContents.carbonateShards;
			if(mineralChance < 55){
				outputType = 0;
			}else if(mineralChance >= 55 && mineralChance < 75){
				outputType = 1;
			}else if(mineralChance >= 75 && mineralChance < 95){
				outputType = 2;
			}else{
				outputType = 3;
			}
		}else if(entryMineral == 4){   
			outputMineral = ModContents.halideShards;
			if(mineralChance < 25){
				outputType = 0;
			}else if(mineralChance >= 25 && mineralChance < 40){
				outputType = 1;
			}else if(mineralChance >= 40 && mineralChance < 55){
				outputType = 2;
			}else if(mineralChance >= 55 && mineralChance < 70){
				outputType = 3;
			}else{
				outputType = 4;
			}
		}else if(entryMineral == 5){   
			outputMineral = ModContents.nativeShards;
			if(mineralChance < 6){
				outputType = 0;
			}else if(mineralChance >= 6 && mineralChance < 12){
				outputType = 1;
			}else if(mineralChance >= 12 && mineralChance < 22){
				outputType = 2;
			}else if(mineralChance >= 22 && mineralChance < 30){
				outputType = 3;
			}else if(mineralChance >= 30 && mineralChance < 38){
				outputType = 4;
			}else if(mineralChance >= 38 && mineralChance < 48){
				outputType = 5;
			}else if(mineralChance >= 48 && mineralChance < 56){
				outputType = 6;
			}else if(mineralChance >= 56 && mineralChance < 66){
				outputType = 7;
			}else if(mineralChance >= 66 && mineralChance < 75){
				outputType = 8;
			}else if(mineralChance >= 75 && mineralChance < 83){
				outputType = 9;
			}else if(mineralChance >= 83 && mineralChance < 92){
				outputType = 10;
			}else{
				outputType = 11;
			}
		}else if(entryMineral == 6){   
			outputMineral = ModContents.oxideShards;
			if(mineralChance < 6){
				outputType = 0;
			}else if(mineralChance >= 6 && mineralChance < 11){
				outputType = 1;
			}else if(mineralChance >= 11 && mineralChance < 16){
				outputType = 2;
			}else if(mineralChance >= 16 && mineralChance < 21){
				outputType = 3;
			}else if(mineralChance >= 21 && mineralChance < 26){
				outputType = 4;
			}else if(mineralChance >= 26 && mineralChance < 31){
				outputType = 5;
			}else if(mineralChance >= 31 && mineralChance < 36){
				outputType = 6;
			}else if(mineralChance >= 36 && mineralChance < 41){
				outputType = 7;
			}else if(mineralChance >= 41 && mineralChance < 46){
				outputType = 8;
			}else if(mineralChance >= 46 && mineralChance < 51){
				outputType = 9;
			}else if(mineralChance >= 51 && mineralChance < 56){
				outputType = 10;
			}else if(mineralChance >= 56 && mineralChance < 61){
				outputType = 11;
			}else if(mineralChance >= 61 && mineralChance < 66){
				outputType = 12;
			}else if(mineralChance >= 66 && mineralChance < 71){
				outputType = 13;
			}else if(mineralChance >= 71 && mineralChance < 76){
				outputType = 14;
			}else if(mineralChance >= 76 && mineralChance < 81){
				outputType = 15;
			}else if(mineralChance >= 81 && mineralChance < 86){
				outputType = 16;
			}else if(mineralChance >= 86 && mineralChance < 91){
				outputType = 17;
			}else if(mineralChance >= 91 && mineralChance < 95){
				outputType = 18;
			}else{
				outputType = 19;
			}
		}else if(entryMineral == 7){   
			outputMineral = ModContents.phosphateShards;
			if(mineralChance < 7){
				outputType = 0;
			}else if(mineralChance >= 7 && mineralChance < 16){
				outputType = 1;
			}else if(mineralChance >= 16 && mineralChance < 25){
				outputType = 2;
			}else if(mineralChance >= 25 && mineralChance < 34){
				outputType = 3;
			}else if(mineralChance >= 34 && mineralChance < 42){
				outputType = 4;
			}else if(mineralChance >= 42 && mineralChance < 50){
				outputType = 5;
			}else if(mineralChance >= 50 && mineralChance < 58){
				outputType = 6;
			}else if(mineralChance >= 58 && mineralChance < 67){
				outputType = 7;
			}else if(mineralChance >= 67 && mineralChance < 75){
				outputType = 8;
			}else if(mineralChance >= 75 && mineralChance < 85){
				outputType = 9;
			}else if(mineralChance >= 85 && mineralChance < 93){
				outputType = 10;
			}else{
				outputType = 11;
			}
		}else if(entryMineral == 8){   
			outputMineral = ModContents.silicateShards;
			if(mineralChance < 8){
				outputType = 0;
			}else if(mineralChance >= 8 && mineralChance < 16){
				outputType = 1;
			}else if(mineralChance >= 16 && mineralChance < 23){
				outputType = 2;
			}else if(mineralChance >= 23 && mineralChance < 31){
				outputType = 3;
			}else if(mineralChance >= 31 && mineralChance < 39){
				outputType = 4;
			}else if(mineralChance >= 39 && mineralChance < 47){
				outputType = 5;
			}else if(mineralChance >= 47 && mineralChance < 55){
				outputType = 6;
			}else if(mineralChance >= 55 && mineralChance < 66){
				outputType = 7;
			}else if(mineralChance >= 66 && mineralChance < 75){
				outputType = 8;
			}else if(mineralChance >= 75 && mineralChance < 83){
				outputType = 9;
			}else if(mineralChance >= 83 && mineralChance < 91){
				outputType = 10;
			}else{
				outputType = 11;
			}
		}else if(entryMineral == 9){   
			outputMineral = ModContents.sulfateShards;
			if(mineralChance < 11){
				outputType = 0;
			}else if(mineralChance >= 11 && mineralChance < 22){
				outputType = 1;
			}else if(mineralChance >= 22 && mineralChance < 33){
				outputType = 2;
			}else if(mineralChance >= 33 && mineralChance < 44){
				outputType = 3;
			}else if(mineralChance >= 44 && mineralChance < 55){
				outputType = 4;
			}else if(mineralChance >= 55 && mineralChance < 68){
				outputType = 5;
			}else if(mineralChance >= 68 && mineralChance < 80){
				outputType = 6;
			}else if(mineralChance >= 80 && mineralChance < 91){
				outputType = 7;
			}else{
				outputType = 8;
			}
		}else if(entryMineral == 10){   
			outputMineral = ModContents.sulfideShards;
			if(mineralChance < 3){
				outputType = 0;
			}else if(mineralChance >= 3 && mineralChance < 9){
				outputType = 1;
			}else if(mineralChance >= 9 && mineralChance < 15){
				outputType = 2;
			}else if(mineralChance >= 15 && mineralChance < 21){
				outputType = 3;
			}else if(mineralChance >= 21 && mineralChance < 26){
				outputType = 4;
			}else if(mineralChance >= 26 && mineralChance < 33){
				outputType = 5;
			}else if(mineralChance >= 33 && mineralChance < 40){
				outputType = 6;
			}else if(mineralChance >= 40 && mineralChance < 46){
				outputType = 7;
			}else if(mineralChance >= 46 && mineralChance < 52){
				outputType = 8;
			}else if(mineralChance >= 52 && mineralChance < 59){
				outputType = 9;
			}else if(mineralChance >= 59 && mineralChance < 65){
				outputType = 10;
			}else if(mineralChance >= 65 && mineralChance < 71){
				outputType = 11;
			}else if(mineralChance >= 71 && mineralChance < 77){
				outputType = 12;
			}else if(mineralChance >= 77 && mineralChance < 83){
				outputType = 13;
			}else if(mineralChance >= 83 && mineralChance < 89){
				outputType = 14;
			}else if(mineralChance >= 89 && mineralChance < 94){
				outputType = 15;
			}else{
				outputType = 16;
			}
		}	
		handleOutput(outputMineral, outputType);
	}

	private void handleOutput(Item mineral, int meta) {
		ItemStack outPutStack = new ItemStack(mineral, rand.nextInt(ModContents.maxMineral) + 1, meta);
        if(slots[outputSlot] == null){
        	slots[outputSlot] = outPutStack.copy();
        }else if(slots[outputSlot].isItemEqual(outPutStack) && slots[outputSlot].stackSize < slots[outputSlot].getMaxStackSize()){
        	slots[outputSlot].stackSize += outPutStack.stackSize;
        	if(slots[outputSlot].stackSize > slots[outputSlot].getMaxStackSize()){slots[outputSlot].stackSize = slots[outputSlot].getMaxStackSize();}
        }
	}

	private boolean canAnalyze() {
		return  (slots[outputSlot] == null
				&& slots[inputSlot] != null && (slots[inputSlot].getItem() == Item.getItemFromBlock(ModContents.mineralOres) && slots[inputSlot].getItemDamage() > 0) ) 
				&& (hasConsumable() && isConsumableUsable())
				&& powerCount >= machineSpeed()
				&& hasAcid(sulfSlot, 2, consumedSulf) && hasAcid(chloSlot, 3, consumedChlo) && hasAcid(fluoSlot, 4, consumedFluo);
	}

	private boolean hasTank(int acidSlot) {
		return slots[acidSlot] != null && slots[acidSlot].getItem() == ModContents.chemicalItems && slots[acidSlot].getItemDamage() == 0;
	}
	private boolean hasAcid(int acidSlot, int acidType, int acidConsumed) {
		if(hasTank(acidSlot)){
			if(slots[acidSlot].hasTagCompound()){
				if(slots[acidSlot].getTagCompound().getString(ModArray.chemTankName).matches(TileEntityLabOven.acidNames[acidType])){
					if(slots[acidSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) >= acidConsumed){
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean hasConsumable() {
		return slots[consumableSlot] != null && slots[consumableSlot].getItem() == ModContents.miscItems && slots[consumableSlot].getItemDamage() == 5;
	}
	private boolean isConsumableUsable() {
		if(hasConsumable()){
			if(slots[consumableSlot].hasTagCompound()){
            	int uses = slots[consumableSlot].getTagCompound().getInteger(ModArray.toolUses);
            	if( uses > 0){return true;}
			}
		}
		return false;
	}


	
	//FUELING
	private void fuelHandler() {
		if(slots[fuelSlot] != null && isItemFuel(slots[fuelSlot]) ){
			if( powerCount <= (powerMax - getItemBurnTime(slots[fuelSlot])) ){
				burnFuel();
			}
		}
	}
	private void burnFuel() {
		powerCount += getItemBurnTime(slots[fuelSlot]);
		slots[fuelSlot].stackSize--;
		if(slots[fuelSlot].stackSize <= 0){slots[fuelSlot] = slots[fuelSlot].getItem().getContainerItem(slots[fuelSlot]);}
	}

	
	private boolean canInduct() {
		return slots[inductorSlot] != null && slots[inductorSlot].getItem() == ModContents.miscItems && slots[inductorSlot].getItemDamage() == 20 && slots[inductorSlot].stackSize == 1;
	}

	
	// COFH SUPPORT
	@Override
	public int getEnergyStored(EnumFacing from) {
		if(canInduct()){
			return powerCount;
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return powerMax;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (powerCount == -1) return 0;
			received = Math.min(powerMax - powerCount, maxReceive);
			if (!simulate) {
				powerCount += received;
				if(powerCount >= powerMax){powerCount = powerMax;}
			}
		}
		return received;
	}

	
	
	// FORGE ENERGY SUPPORT
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (powerCount == -1) return 0;
			received = Math.min(powerMax - powerCount, maxReceive);
			if (!simulate) {
				powerCount += received;
				if(powerCount >= powerMax){powerCount = powerMax;}
			}
		}
		return received;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		if(canInduct()){
			return powerCount;
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored() {
		return powerMax;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
	}


}
