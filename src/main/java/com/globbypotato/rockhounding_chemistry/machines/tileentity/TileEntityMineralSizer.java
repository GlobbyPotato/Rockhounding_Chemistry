package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralSizer;

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
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMineralSizer extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyReceiver, IEnergyStorage {
    private ItemStack[] slots = new ItemStack[7];
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {2, 1, 4, 5};
    private static final int[] SLOTS_SIDES = new int[] {1, 3};
    Random rand = new Random();

    public int powerCount;

    public int powerMax = 32000;

    private int cookTime;
    private int totalCookTime;
    ItemStack crushedStack;
    
    public static int crushingSpeed;

    private String furnaceCustomName;

	private int inputSlot = 0;
	private int fuelSlot = 1;
	private int outputSlot = 2;
	private int consumableSlot = 3;
	private int goodSlot = 4;
	private int wasteSlot = 5;
	private int inductorSlot = 6;



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
        return this.hasCustomName() ? this.furnaceCustomName : "container.mineralSizer";
    }

    public boolean hasCustomName(){
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_){
        this.furnaceCustomName = p_145951_1_;
    }

    public static void func_189676_a(DataFixer p_189676_0_){
        p_189676_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("MineralSizer", new String[] {"Items"}));
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
		return crushingSpeed;
    	
    }

    public String getGuiID(){
        return Reference.MODID + ":mineralSizer";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerMineralSizer(playerInventory, this);
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

    @Override
    public void update(){
    	if(slots[fuelSlot] != null){fuelHandler();}
    	if(!worldObj.isRemote){
	    	if(canSize(Item.getItemFromBlock(ModContents.mineralOres), 0)){				execute(1, null, 0);}
	    	if(canSize(Item.getItemFromBlock(Blocks.STONE), 1)){						execute(2, ModContents.chemicalItems, 0);}
	    	if(canCrush(ModContents.alloyItems)){										execute(3, ModContents.alloyItems, 0);}
	    	if(canCrush(ModContents.alloyBItems)){										execute(3, ModContents.alloyBItems, 0);}
	    	if(canCrush(Items.GOLD_INGOT)){												execute(4, ModContents.chemicalDusts, 45);}
	    	if(canCrush(Items.IRON_INGOT)){												execute(4, ModContents.chemicalDusts, 16);}
    	}
    }

	private boolean canCrush(Item ingots) {
		return  (slots[outputSlot] == null
				&& (slots[inputSlot] != null && slots[inputSlot].getItem() == ingots)) 
				&& (hasConsumable() && isConsumableUsable())
				&& isIngotOredict()
				&& powerCount >= machineSpeed();
	}

	private boolean isIngotOredict() {
		int[] oreIDs = OreDictionary.getOreIDs(slots[inputSlot]);
		if(oreIDs.length > 0) {
			for(int i = 0; i < oreIDs.length; i++) {
				if(oreIDs[i] > -1) {
					String oreName = OreDictionary.getOreName(oreIDs[i]);
			   		if(oreName != null && oreName.contains("ingot")){
			   			return true;
			   		}
				}
			}
		}
		return false;
	}

	private void execute(int recipeID, Item output, int damage) {
    	boolean flag = false;
		cookTime++; powerCount--;
		if(cookTime >= machineSpeed()) {
			cookTime = 0;
			if(recipeID == 1){
				returnMInerals();
			}else if(recipeID == 2){
				returnCrushes(2, output, 4);
			}else if(recipeID == 3){
				returnDusts(3, output, damage);
			}else if(recipeID == 4){
				returnDusts(4, output, damage);
			}
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private void returnDusts(int recipeID, Item dusts, int damage) {
		if(recipeID == 3){crushedStack = new ItemStack(dusts, 1, slots[inputSlot].getItemDamage() - 1);}
		if(recipeID == 4){crushedStack = new ItemStack(dusts, 1, damage);}
        handleOutput(crushedStack);
        handleSlots();
	}

	private boolean canSize(Item crushingItem, int crushingMeta) {
		return  (slots[outputSlot] == null
				&& slots[inputSlot] != null && (slots[inputSlot].getItem() == crushingItem && slots[inputSlot].getItemDamage() == crushingMeta) ) 
				&& (hasConsumable() && isConsumableUsable())
				&& powerCount >= machineSpeed();
	}

	private boolean hasConsumable() {
		return slots[consumableSlot] != null && slots[consumableSlot].getItem() == ModContents.miscItems && slots[consumableSlot].getItemDamage() == 2;
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
	
	private void returnCrushes(int recipeID, Item crushedItem, int crushedMeta) {
		if(recipeID == 2){crushedStack = new ItemStack(crushedItem, (rand.nextInt(4) + 1), crushedMeta);}
        handleOutput(crushedStack);
        handleSlots();
	}

	private void returnMInerals() {
        //calculate primary output
        ItemStack primaryStack = new ItemStack(ModContents.mineralOres, (rand.nextInt(4) + 1), extractCategory());
        handleOutput(primaryStack);
        //calculate secondary output
        if(rand.nextInt(100) < 25){
	        ItemStack secondaryStack = new ItemStack(ModContents.mineralOres, (rand.nextInt(2) + 1), extractCategory());
	        handleOutput(secondaryStack);
        }
        //calculate watse output
        if(rand.nextInt(100) < 5){
	        ItemStack wasteStack = new ItemStack(ModContents.mineralOres, 1, extractCategory());
	        handleOutput(wasteStack);
        }
        handleSlots();
	}

	private void handleOutput(ItemStack outputStack) {
        if(slots[outputSlot] == null){
        	slots[outputSlot] = outputStack.copy();
        }else if(slots[outputSlot].isItemEqual(outputStack) && slots[outputSlot].stackSize < slots[outputSlot].getMaxStackSize()){
        	slots[outputSlot].stackSize += outputStack.stackSize;
        	if(slots[outputSlot].stackSize > slots[outputSlot].getMaxStackSize()){slots[outputSlot].stackSize = slots[outputSlot].getMaxStackSize();}
        }
	}

	private void handleSlots() {
		int uses = 0;
		//decrease input
		slots[inputSlot].stackSize--; if(slots[inputSlot].stackSize <= 0) { slots[inputSlot] = null; }
		//decrease consumable
		uses = slots[consumableSlot].getTagCompound().getInteger(ModArray.toolUses) - 1;
		slots[consumableSlot].getTagCompound().setInteger(ModArray.toolUses, uses);
		if(uses <= 0){slots[consumableSlot] = null;}
	}

	private int extractCategory() {
        int getCategory = rand.nextInt(158);
        if(getCategory < 5){ return 1;										//arsenate
        }else if(getCategory >= 5   && getCategory < 17){  return 2;		//borate
        }else if(getCategory >= 17  && getCategory < 24){  return 3;		//carbonate
        }else if(getCategory >= 24  && getCategory < 33){  return 4;		//halide
        }else if(getCategory >= 33  && getCategory < 53){  return 5;		//native
        }else if(getCategory >= 53  && getCategory < 85){  return 6;		//oxide
        }else if(getCategory >= 85  && getCategory < 103){ return 7;		//phosphate
        }else if(getCategory >= 103  && getCategory < 119){return 8;		//silicate
        }else if(getCategory >= 119 && getCategory < 130){ return 9;		//sulfate
        }else if(getCategory >= 130 ){ return 10;							//sulfide
        } return 0;
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
		powerCount += getItemBurnTime(slots[fuelSlot]); slots[fuelSlot].stackSize--;
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
