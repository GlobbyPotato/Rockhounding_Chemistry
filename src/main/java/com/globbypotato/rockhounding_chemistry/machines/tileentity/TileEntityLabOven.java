package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.LabOven;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabOven;

import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyHandler;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLabOven extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyReceiver, IEnergyStorage {
    private ItemStack[] slots = new ItemStack[8];
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {2, 1};
    private static final int[] SLOTS_SIDES = new int[] {1, 4};

    public int powerCount;
    public int powerMax = 32000;

    public int redstoneCount;
    public int redstoneMax = 32000;
    
    public int recipeCount;

    private int cookTime;
    private int totalCookTime;
    
    public static int cookingSpeed;
    public static int tankMax = 100;
	private int redstoneCharge = cookingSpeed;

    private String furnaceCustomName;
	private int inputSlot = 0;
	private int fuelSlot = 1;
	private int outputSlot = 2;
	private int solventSlot = 3;
	private int redstoneSlot = 4;

    private ItemStack[] inputStack = new ItemStack[] {	null,
    													new ItemStack(ModContents.chemicalItems, 1, 2), 
    													new ItemStack(ModContents.chemicalItems, 1, 3), 
    													new ItemStack(ModContents.chemicalItems, 1, 4), 
    													new ItemStack(ModContents.chemicalItems, 1, 5)};

    public static String[] solventNames = new String[]{	"Empty", 
    													"Water", 
    													"Sulfuric Acid"};

    public static String[] acidNames = new String[] {	"Empty", 
    													"Water", 
    													"Sulfuric Acid", 
    													"Hydrochloric Acid", 
    													"Hydrofluoric Acid", 
    													"Syngas"};

    public static String[] recipeNames = new String[] {	" - ",
    													"Sulfuric acid", 
														"Hydrochloric acid", 
														"Hydrofluoric acid", 
														"Syngas"};

    public int maxAcids = inputStack.length - 1;
    public boolean recipeScan;

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
        return this.hasCustomName() ? this.furnaceCustomName : "container.labOven";
    }

    public boolean hasCustomName(){
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_){
        this.furnaceCustomName = p_145951_1_;
    }

    public static void func_189676_a(DataFixer p_189676_0_){
        p_189676_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("LabOven", new String[] {"Items"}));
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
		return cookingSpeed;
    	
    }

    public String getGuiID(){
        return Reference.MODID + ":labOven";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerLabOven(playerInventory, this);
    }

    public int getFieldCount() {
        return 6;
    }

    public int getField(int id){
        switch (id){
            case 0: return this.powerCount;
            case 1: return this.powerMax;
            case 2: return this.cookTime;
            case 3: return this.totalCookTime;
            case 4: return this.redstoneCount;
            case 5: return this.redstoneMax;
            default:return 0;
        }
    }

    public void setField(int id, int value){
        switch (id){
            case 0: this.powerCount = value; break;
            case 1: this.powerMax = value; break;
            case 2: this.cookTime = value; break;
            case 3: this.totalCookTime = value; break;
            case 4: this.redstoneCount = value; break;
            case 5: this.redstoneMax = value;
        }
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
        this.recipeCount = compound.getInteger("RecipeCount");
        this.powerCount = compound.getInteger("PowerCount");
        this.redstoneCount = compound.getInteger("RedstoneCount");
        this.cookTime = compound.getInteger("CookTime");
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        if (compound.hasKey("CustomName", 8)){
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("RecipeCount", this.recipeCount);
        compound.setInteger("PowerCount", this.powerCount);
        compound.setInteger("RedstoneCount", this.redstoneCount);
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
    	if(slots[redstoneSlot] != null && !canInduct()){redstoneHandler();}
    	
    	if(!worldObj.isRemote){
    		//show recipes
	   		if(countRecipes() > 0 && recipeScan){ showIngredients(countRecipes());}
    		//solute-solvent-solution arrays
			if(canSyntetize(1, 1, 2)){execute(2);}// sulfuric acid
			if(canSyntetize(2, 2, 3)){execute(3);}// hydrochloric acid
			if(canSyntetize(3, 2, 4)){execute(4);}// hydrofluoric acid
			if(canSyntetize(4, 1, 5)){execute(5);}// Syngas
    	}
    }

	private void showIngredients(int countRecipes) {
		slots[7] = inputStack[countRecipes()];
    	recipeScan = false;
	}

	private void execute(int outputID) {
    	boolean flag = false;
		cookTime++; powerCount--; redstoneCount --; 
		if(cookTime >= machineSpeed()) {
			cookTime = 0; 
			handleOutput(outputID);
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private boolean canSyntetize(int soluteID, int solventID, int outputID) {
		return     (slots[inputSlot] != null && (slots[inputSlot].getItem() == inputStack[soluteID].getItem() && slots[inputSlot].getItemDamage() == inputStack[soluteID].getItemDamage()))
				&& (slots[solventSlot] != null && isTankFilled(solventID))
				&& (slots[outputSlot] != null && isTankEmpty(outputID))
				&& powerCount >= machineSpeed()
				&& redstoneCount >= machineSpeed();
	}

	private boolean isTankEmpty(int outputID) {
		if(hasTank(outputSlot)){
			if(slots[outputSlot].hasTagCompound()){
				if(slots[outputSlot].getTagCompound().getString(ModArray.chemTankName).matches(acidNames[outputID]) || slots[outputSlot].getTagCompound().getString(ModArray.chemTankName).matches(acidNames[0])){
					if(slots[outputSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) < tankMax){
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isTankFilled(int solventID) {
		if(hasTank(solventSlot)){
			if(slots[solventSlot].hasTagCompound()){
				if(slots[solventSlot].getTagCompound().getString(ModArray.chemTankName).matches(solventNames[solventID])){
					if(slots[solventSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) > 0){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	private boolean hasTank(int outputSlot) {
		return 	slots[outputSlot] != null && slots[outputSlot].getItem() == ModContents.chemicalItems && slots[outputSlot].getItemDamage() == 0 && slots[outputSlot].stackSize == 1;
	}

	private void handleOutput(int outputID) {
		int quantity = 0;
		//decrease input
		slots[inputSlot].stackSize--; if(slots[inputSlot].stackSize <= 0) { slots[inputSlot] = null; }
		//decrease solvent
		quantity = slots[solventSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) - 1;
		slots[solventSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){slots[solventSlot].getTagCompound().setString(ModArray.chemTankName, acidNames[0]);}
		//add output
		quantity = slots[outputSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) + 1;
		slots[outputSlot].getTagCompound().setString(ModArray.chemTankName, acidNames[outputID]);
		slots[outputSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
	}

	public int countRecipes(){
		return recipeCount;
	}
	
    public void resetGrid(){
    	slots[7] = null;
    }

	//FUELING
	private void fuelHandler() {
		if(slots[fuelSlot] != null && isItemFuel(slots[fuelSlot]) ){
			if( powerCount <= (powerMax - getItemBurnTime(slots[fuelSlot])) ){
				burnFuel();
			}
		}
	}
	private void redstoneHandler() {
		if(slots[redstoneSlot] != null && slots[redstoneSlot].getItem() == Items.REDSTONE && redstoneCount <= (redstoneMax - redstoneCharge)){
			burnRedstone();
		}
	}
	private void burnFuel() {
		powerCount += getItemBurnTime(slots[fuelSlot]); slots[fuelSlot].stackSize--;
		if(slots[fuelSlot].stackSize <= 0){slots[fuelSlot] = slots[fuelSlot].getItem().getContainerItem(slots[fuelSlot]);}
	}
	private void burnRedstone() {
		redstoneCount += redstoneCharge; slots[redstoneSlot].stackSize--; 
		if(slots[redstoneSlot].stackSize <= 0) { slots[redstoneSlot] = null; }
	}

	
	private boolean canInduct() {
		return redstoneCount >= redstoneMax && slots[redstoneSlot] != null && slots[redstoneSlot].getItem() == ModContents.miscItems && slots[redstoneSlot].getItemDamage() == 20 && slots[redstoneSlot].stackSize == 1;
	}


	// COFH SUPPORT
	@Override
	public int getEnergyStored(EnumFacing from) {
		if(canInduct()){
			return powerCount;
		}else{
			return redstoneCount;
		}
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		if(canInduct()){
			return powerMax;
		}else{
			return redstoneMax;
		}
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
		}else{
			if (redstoneCount == -1) return 0;
			received = Math.min(redstoneMax - redstoneCount, maxReceive);
			if (!simulate) {
				redstoneCount += received;
				if(redstoneCount >= redstoneMax){redstoneCount = redstoneMax;}
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
		}else{
			if (redstoneCount == -1) return 0;
			received = Math.min(redstoneMax - redstoneCount, maxReceive);
			if (!simulate) {
				redstoneCount += received;
				if(redstoneCount >= redstoneMax){redstoneCount = redstoneMax;}
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
			return redstoneCount;
		}
	}

	@Override
	public int getMaxEnergyStored() {
		if(canInduct()){
			return powerMax;
		}else{
			return redstoneMax;
		}
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