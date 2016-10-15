package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.ChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;

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
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityChemicalExtractor extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyReceiver, IEnergyStorage {
    private ItemStack[] slots = new ItemStack[62];
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {1,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,51,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61};
    private static final int[] SLOTS_SIDES = new int[] {1, 2};
    Random rand = new Random();
	public int[] elementList = new int[56];

    public int powerCount;
    public int powerMax = 32000;

    public int redstoneCount;
    public int redstoneMax = 32000;

    private int consumedSyng = 1;
    private int consumedFluo = 2;
	ItemStack dustStack;
	private int scrollSlots;
	private int scrollMin = 6;
	private int scrollMax = 61;

    private int cookTime;
    private int totalCookTime;

    public static int extractingSpeed;
	public static int extractingFactor;
	private int redstoneCharge = extractingSpeed;

    private String furnaceCustomName;

	private int inputSlot = 0;
	private int fuelSlot = 1;
	private int redstoneSlot = 2;
	private int consumableSlot = 3;
	private int syngSlot = 4;
	private int fluoSlot = 5;



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
        return this.hasCustomName() ? this.furnaceCustomName : "container.chemicalExtractor";
    }

    public boolean hasCustomName(){
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_){
        this.furnaceCustomName = p_145951_1_;
    }

    public static void func_189676_a(DataFixer p_189676_0_){
        p_189676_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("ChemicalExtractor", new String[] {"Items"}));
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
    public boolean isItemValidForSlot(int index, ItemStack stack){
        if (index != 1){
            return true;
        }else{
            ItemStack itemstack = this.slots[1];
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
    }

    public int getCookTime(@Nullable ItemStack stack){
        return this.machineSpeed();
    }

    public int machineSpeed(){
		return extractingSpeed;
    	
    }

    public String getGuiID(){
        return Reference.MODID + ":chemicalExtractor";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerChemicalExtractor(playerInventory, this);
    }

    public int getFieldCount() {
        return 62;
    }

    public int getField(int id){
    	if(id == 0){		return this.powerCount;
    	}else if(id == 1){  return this.powerMax;
    	}else if(id == 2){  return this.cookTime;
    	}else if(id == 3){  return this.totalCookTime;
    	}else if(id == 4){  return this.redstoneCount;
    	}else if(id == 5){  return this.redstoneMax;
    	}else if(id == 6){  return this.elementList[0];
    	}else if(id == 7){  return this.elementList[1];
    	}else if(id == 8){  return this.elementList[2];
    	}else if(id == 9){  return this.elementList[3];
    	}else if(id == 10){ return this.elementList[4];
    	}else if(id == 11){ return this.elementList[5];
    	}else if(id == 12){ return this.elementList[6];
    	}else if(id == 13){ return this.elementList[7];
    	}else if(id == 14){ return this.elementList[8];
    	}else if(id == 15){ return this.elementList[9];
    	}else if(id == 16){ return this.elementList[10];
    	}else if(id == 17){ return this.elementList[11];
    	}else if(id == 18){ return this.elementList[12];
    	}else if(id == 19){ return this.elementList[13];
    	}else if(id == 20){ return this.elementList[14];
    	}else if(id == 21){ return this.elementList[15];
    	}else if(id == 22){ return this.elementList[16];
    	}else if(id == 23){ return this.elementList[17];
    	}else if(id == 24){ return this.elementList[18];
    	}else if(id == 25){ return this.elementList[19];
    	}else if(id == 26){ return this.elementList[20];
    	}else if(id == 27){ return this.elementList[21];
    	}else if(id == 28){ return this.elementList[22];
    	}else if(id == 29){ return this.elementList[23];
    	}else if(id == 30){ return this.elementList[24];
    	}else if(id == 31){ return this.elementList[25];
    	}else if(id == 32){ return this.elementList[26];
    	}else if(id == 33){ return this.elementList[27];
    	}else if(id == 34){ return this.elementList[28];
    	}else if(id == 35){ return this.elementList[29];
    	}else if(id == 36){ return this.elementList[30];
    	}else if(id == 37){ return this.elementList[31];
    	}else if(id == 38){ return this.elementList[32];
    	}else if(id == 39){ return this.elementList[33];
    	}else if(id == 40){ return this.elementList[34];
    	}else if(id == 41){ return this.elementList[35];
    	}else if(id == 42){ return this.elementList[36];
    	}else if(id == 43){ return this.elementList[37];
    	}else if(id == 44){ return this.elementList[38];
    	}else if(id == 45){ return this.elementList[39];
    	}else if(id == 46){ return this.elementList[40];
    	}else if(id == 47){ return this.elementList[41];
    	}else if(id == 48){ return this.elementList[42];
    	}else if(id == 49){ return this.elementList[43];
    	}else if(id == 50){ return this.elementList[44];
    	}else if(id == 51){ return this.elementList[45];
    	}else if(id == 52){ return this.elementList[46];
    	}else if(id == 53){ return this.elementList[47];
    	}else if(id == 54){ return this.elementList[48];
    	}else if(id == 55){ return this.elementList[49];
    	}else if(id == 56){ return this.elementList[50];
    	}else if(id == 57){ return this.elementList[51];
    	}else if(id == 58){ return this.elementList[52];
    	}else if(id == 59){ return this.elementList[53];
    	}else if(id == 60){ return this.elementList[54];
    	}else if(id == 61){ return this.elementList[55];
    	}else{return 0;}
    }

    public void setField(int id, int value){
    	if(id == 0){	    this.powerCount = value;
    	}else if(id == 1){  this.powerMax = value;
    	}else if(id == 2){  this.cookTime = value;
    	}else if(id == 3){  this.totalCookTime = value;
    	}else if(id == 4){  this.redstoneCount = value;
    	}else if(id == 5){  this.redstoneMax = value;
    	}else if(id == 6){  elementList[0] = value;
    	}else if(id == 7){  elementList[1] = value;
    	}else if(id == 8){  elementList[2] = value;
    	}else if(id == 9){  elementList[3] = value;
    	}else if(id == 10){ elementList[4] = value;
    	}else if(id == 11){ elementList[5] = value;
    	}else if(id == 12){ elementList[6] = value;
    	}else if(id == 13){ elementList[7] = value;
    	}else if(id == 14){ elementList[8] = value;
    	}else if(id == 15){ elementList[9] = value;
    	}else if(id == 16){ elementList[10] = value;
    	}else if(id == 17){ elementList[11] = value;
    	}else if(id == 18){ elementList[12] = value;
    	}else if(id == 19){ elementList[13] = value;
    	}else if(id == 20){ elementList[14] = value;
    	}else if(id == 21){ elementList[15] = value;
    	}else if(id == 22){ elementList[16] = value;
    	}else if(id == 23){ elementList[17] = value;
    	}else if(id == 24){ elementList[18] = value;
    	}else if(id == 25){ elementList[19] = value;
    	}else if(id == 26){ elementList[20] = value;
    	}else if(id == 27){ elementList[21] = value;
    	}else if(id == 28){ elementList[22] = value;
    	}else if(id == 29){ elementList[23] = value;
    	}else if(id == 30){ elementList[24] = value;
    	}else if(id == 31){ elementList[25] = value;
    	}else if(id == 32){ elementList[26] = value;
    	}else if(id == 33){ elementList[27] = value;
    	}else if(id == 34){ elementList[28] = value;
    	}else if(id == 35){ elementList[29] = value;
    	}else if(id == 36){ elementList[30] = value;
    	}else if(id == 37){ elementList[31] = value;
    	}else if(id == 38){ elementList[32] = value;
    	}else if(id == 39){ elementList[33] = value;
    	}else if(id == 40){ elementList[34] = value;
    	}else if(id == 41){ elementList[35] = value;
    	}else if(id == 42){ elementList[36] = value;
    	}else if(id == 43){ elementList[37] = value;
    	}else if(id == 44){ elementList[38] = value;
    	}else if(id == 45){ elementList[39] = value;
    	}else if(id == 46){ elementList[40] = value;
    	}else if(id == 47){ elementList[41] = value;
    	}else if(id == 48){ elementList[42] = value;
    	}else if(id == 49){ elementList[43] = value;
    	}else if(id == 50){ elementList[44] = value;
    	}else if(id == 51){ elementList[45] = value;
    	}else if(id == 52){ elementList[46] = value;
    	}else if(id == 53){ elementList[47] = value;
    	}else if(id == 54){ elementList[48] = value;
    	}else if(id == 55){ elementList[49] = value;
    	}else if(id == 56){ elementList[50] = value;
    	}else if(id == 57){ elementList[51] = value;
    	}else if(id == 58){ elementList[52] = value;
    	}else if(id == 59){ elementList[53] = value;
    	}else if(id == 60){ elementList[54] = value;
    	}else if(id == 61){ elementList[55] = value;
    	}
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
		for(int i = 0; i < elementList.length; i++){
			elementList[i] = compound.getInteger("element" + i);
		}
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
		for(int i = 0; i < elementList.length; i++){
			compound.setInteger("element" + i, elementList[i]);
		}
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




	//PROCESSING

    @Override
    public void update(){
    	if(slots[fuelSlot] != null){fuelHandler();}
    	if(slots[redstoneSlot] != null){redstoneHandler();}
    	if(!worldObj.isRemote){
	    	if(canExtractElements()){
	    		execute();
	    	}
	    	if(hasCylinder()){transferDust();}
    	}

    }

    private void transferDust() {
    	boolean flag = false;
		if(scrollSlots >= scrollMin && scrollSlots <= scrollMax){
			dustStack = new ItemStack(ModContents.chemicalDusts, 1, scrollSlots - 6);
			if(elementList[scrollSlots - 6] >= extractingFactor ){
				if(slots[scrollSlots] == null){
					slots[scrollSlots] = dustStack.copy();
					elementList[scrollSlots - 6] -= extractingFactor;
					if(scrollSlots < scrollMax){ scrollSlots++; }else{ scrollSlots = scrollMin; }
					flag = true;
				}else if(slots[scrollSlots] != null && slots[scrollSlots].isItemEqual(dustStack) && slots[scrollSlots].stackSize < slots[scrollSlots].getMaxStackSize()){
					slots[scrollSlots].stackSize++;
					elementList[scrollSlots - 6] -= extractingFactor;
					if(scrollSlots < scrollMax){ scrollSlots++; }else{ scrollSlots = scrollMin; }
					flag = true;
				}else{
					scrollSlots++;
				}
			}else{
				scrollSlots++;
			}
		}else{
			scrollSlots = scrollMin;
		}
		if(flag){this.markDirty();}
	}

	private boolean hasCylinder() {
		return slots[consumableSlot] != null && slots[consumableSlot].getItem() == ModContents.miscItems && slots[consumableSlot].getItemDamage() == 1;
	}

	private void execute() {
    	boolean flag = false;
		cookTime++; powerCount--; redstoneCount -= 2;
			if(cookTime >= machineSpeed()) {
				cookTime = 0; 
				handleOutput();
				flag = true;
			}
		if(flag){this.markDirty();}
	}

	private void handleOutput() {
		int uses = 0;
		int quantity = 0;
        //calculate output
		extractElements();
		//decrease input
		slots[inputSlot].stackSize--; if(slots[inputSlot].stackSize <= 0) { slots[inputSlot] = null; }
		//decrease consumable
		uses = slots[consumableSlot].getTagCompound().getInteger(ModArray.toolUses) - 1;
		slots[consumableSlot].getTagCompound().setInteger(ModArray.toolUses, uses);
		if(uses <= 0){slots[consumableSlot] = null;}
		//decrease acids
		quantity = slots[syngSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedSyng;
		slots[syngSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){slots[syngSlot].getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.acidNames[0]);}
		quantity = slots[fluoSlot].getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedFluo;
		slots[fluoSlot].getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){slots[fluoSlot].getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.acidNames[0]);}
	}

	private void extractElements() {
		if(slots[inputSlot].getItem() == ModContents.arsenateShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[17] += 36;
				elementList[41] += 22;
				elementList[19] += 6;
				elementList[1]  += 5;
				elementList[15] += 4;
				elementList[0]  += 4;
				elementList[6]  += 3;
				elementList[8]  += 3;
				elementList[32] += 2;
				elementList[3]  += 1;
				elementList[4]  += 1;
				elementList[10] += 1;
				elementList[42] += 1;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[19] += 55;
				elementList[41] += 10;
				elementList[17] += 9;
				elementList[21] += 7;
			}
		}else if(slots[inputSlot].getItem() == ModContents.borateShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[30] += 12;
				elementList[22] += 11;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[16] += 22;
				elementList[22] += 17;
				elementList[28] += 5;
				elementList[25] += 4;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[16] += 46;
				elementList[28] += 10;
				elementList[22] += 5;
				elementList[18] += 5;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[22] += 15;
				elementList[24] += 13;
				elementList[36] += 6;
				elementList[35] += 2;
				elementList[34] += 1;
				elementList[16] += 1;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[18] += 40;
				elementList[25] += 19;
				elementList[22] += 8;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[24] += 14;
				elementList[22] += 13;
				elementList[36] += 8;
				elementList[35] += 4;
			}
		}else if(slots[inputSlot].getItem() == ModContents.carbonateShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[32] += 19;
				elementList[16] += 16;
				elementList[28] += 4;
				elementList[25] += 3;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[26] += 33;
				elementList[28] += 7;
				elementList[16] += 5;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[17] += 43;
				elementList[20] += 15;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[8]  += 23;
				elementList[6]  += 21;
				elementList[0]  += 19;
				elementList[42] += 7;
			}
		}else if(slots[inputSlot].getItem() == ModContents.halideShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[19] += 49;
				elementList[17] += 14;
				elementList[23] += 9;
				elementList[35] += 1;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[35] += 14;
				elementList[28] += 9;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[35] += 28;
				elementList[16] += 14;
				elementList[30] += 6;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[34] += 27;
			}
		}else if(slots[inputSlot].getItem() == ModContents.nativeShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[16] += 55;
				elementList[26] += 29;
				elementList[27] += 10;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[17] += 100;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[17] += 60;
				elementList[24] += 26;
				elementList[20] += 7;
				elementList[16] += 7;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[16] += 82;
				elementList[26] += 13;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[26] += 64;
				elementList[16] += 20;
				elementList[42] += 11;
				elementList[33] += 4;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[23] += 100;
			}else if(slots[inputSlot].getItemDamage() == 6){
				elementList[44] += 76;
				elementList[17] += 13;
				elementList[16] += 11;
			}else if(slots[inputSlot].getItemDamage() == 7){
				elementList[44] += 62;
				elementList[18] += 38;
			}else if(slots[inputSlot].getItemDamage() == 8){
				elementList[45] += 65;
				elementList[38] += 35;
			}else if(slots[inputSlot].getItemDamage() == 9){
				elementList[45] += 51;
				elementList[17] += 49;
			}else if(slots[inputSlot].getItemDamage() == 10){
				elementList[47] += 75;
				elementList[48] += 25;
			}else if(slots[inputSlot].getItemDamage() == 11){
				elementList[48] += 52;
				elementList[47] += 31;
				elementList[44] += 11;
			}
		}else if(slots[inputSlot].getItem() == ModContents.oxideShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[21] += 47;
				elementList[16] += 25;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[21] += 35;
				elementList[27] += 14;
				elementList[16] += 8;
				elementList[26] += 6;
				elementList[24] += 5;
				elementList[28] += 1;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[39] += 42;
				elementList[40] += 24;
				elementList[16] += 8;
				elementList[25] += 7;
				elementList[28] += 5;
				elementList[29] += 2;
				elementList[24] += 1;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[39] += 33;
				elementList[15] += 16;
				elementList[0]  += 3;
				elementList[32] += 2;
				elementList[29] += 2;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[17] += 43;
				elementList[21] += 35;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[39] += 24;
				elementList[40] += 12;
				elementList[31] += 10;
				elementList[43] += 10;
				elementList[14] += 5;
				elementList[16] += 4;
				elementList[15] += 4;
				elementList[7]  += 4;
				elementList[13] += 4;
				elementList[5]  += 4;
				elementList[1]  += 4;
				elementList[2]  += 4;
				elementList[3]  += 4;
				elementList[12] += 4;
				elementList[32] += 2;
			}else if(slots[inputSlot].getItemDamage() == 6){
				elementList[18] += 79;
			}else if(slots[inputSlot].getItemDamage() == 7){
				elementList[24] += 45;
			}else if(slots[inputSlot].getItemDamage() == 8){
				elementList[20] += 36;
				elementList[24] += 30;
			}else if(slots[inputSlot].getItemDamage() == 9){
				elementList[24] += 40;
				elementList[16] += 6;
				elementList[32] += 5;
				elementList[29] += 3;
				elementList[6]  += 2;
				elementList[0]  += 2;
				elementList[28] += 1;
			}else if(slots[inputSlot].getItemDamage() == 10){
				elementList[29] += 25;
				elementList[16] += 21;
				elementList[25] += 12;
				elementList[19] += 11;
			}else if(slots[inputSlot].getItemDamage() == 11){
				elementList[29] += 25;
				elementList[31] += 24;
				elementList[43] += 24;
				elementList[32] += 2;
			}else if(slots[inputSlot].getItemDamage() == 12){
				elementList[40] += 56;
				elementList[18] += 7;
				elementList[25] += 7;
				elementList[39] += 6;
				elementList[16] += 3;
			}else if(slots[inputSlot].getItemDamage() == 13){
				elementList[40] += 66;
				elementList[25] += 6;
				elementList[16] += 6;
				elementList[39] += 6;
			}else if(slots[inputSlot].getItemDamage() == 14){
				elementList[36] += 21;
			}else if(slots[inputSlot].getItemDamage() == 15){
				elementList[36] += 36;
			}else if(slots[inputSlot].getItemDamage() == 16){
				elementList[46] += 75;
			}else if(slots[inputSlot].getItemDamage() == 17){
				elementList[46] += 61;
				elementList[16] += 9;
				elementList[25] += 9;
			}else if(slots[inputSlot].getItemDamage() == 18){
				elementList[46] += 61;
				elementList[16] += 18;
			}else if(slots[inputSlot].getItemDamage() == 19){
				elementList[49] += 88;
			}
		}else if(slots[inputSlot].getItem() == ModContents.phosphateShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[24] += 20;
				elementList[33] += 15;
				elementList[20] += 6;
				elementList[17] += 2;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[33] += 20;
				elementList[24] += 18;
				elementList[28] += 8;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[8]  += 21;
				elementList[6]  += 18;
				elementList[0]  += 16;
				elementList[4]  += 14;
				elementList[10] += 13;
				elementList[33] += 11;
				elementList[31] += 7;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[16] += 22;
				elementList[33] += 12;
				elementList[20] += 8;
				elementList[25] += 7;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[16] += 35;
				elementList[33] += 20;
				elementList[34] += 5;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[24] += 20;
				elementList[33] += 15;
			}else if(slots[inputSlot].getItemDamage() == 6){
				elementList[14] += 48;
				elementList[15] += 38;
				elementList[33] += 14;
			}else if(slots[inputSlot].getItemDamage() == 7){
				elementList[38] += 32;
				elementList[16] += 20;
				elementList[33] += 10;
				elementList[24] += 3;
			}else if(slots[inputSlot].getItemDamage() == 8){
				elementList[11] += 32;
				elementList[33] += 22;
			}else if(slots[inputSlot].getItemDamage() == 9){
				elementList[16] += 32;
				elementList[33] += 18;
				elementList[34] += 4;
			}else if(slots[inputSlot].getItemDamage() == 10){
				elementList[41] += 29;
				elementList[17] += 15;
				elementList[20] += 14;
				elementList[49] += 13;
				elementList[25] += 1;
				elementList[32] += 1;
			}else if(slots[inputSlot].getItemDamage() == 11){
				elementList[49] += 32;
				elementList[17] += 17;
				elementList[33] += 9;
				elementList[20] += 1;
			}
		}else if(slots[inputSlot].getItem() == ModContents.silicateShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[42] += 20;
				elementList[32] += 14;
				elementList[24] += 10;
				elementList[25] += 10;
				elementList[16] += 10;
				elementList[28] += 5;
				elementList[22] += 2;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[42] += 19;
				elementList[28] += 14;
				elementList[35] += 10;
				elementList[24] += 6;
				elementList[16] += 6;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[15] += 22;
				elementList[16] += 10;
				elementList[42] += 10;
				elementList[6]  += 6;
				elementList[0]  += 6;
				elementList[9]  += 6;
				elementList[10] += 6;
				elementList[36] += 4;
				elementList[3]  += 3;
				elementList[5]  += 3;
				elementList[7]  += 3;
				elementList[12] += 3;
				elementList[13] += 3;
				elementList[22] += 1;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[19] += 68;
				elementList[21] += 10;
				elementList[17] += 2;
				elementList[42] += 2;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[42] += 25;
				elementList[11] += 12;
				elementList[30] += 6;
				elementList[32] += 5;
				elementList[16] += 5;
				elementList[28] += 3;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[42] += 18;
				elementList[16] += 12;
				elementList[28] += 10;
				elementList[35] += 4;
				elementList[11] += 2;
				elementList[24] += 2;
			}else if(slots[inputSlot].getItemDamage() == 6){
				elementList[42] += 28;
				elementList[15] += 11;
				elementList[29] += 7;
				elementList[35] += 6;
				elementList[1]  += 3;
				elementList[4]  += 1;
				elementList[5]  += 1;
				elementList[1]  += 1;
				elementList[12] += 1;
				elementList[10] += 1;
			}else if(slots[inputSlot].getItemDamage() == 7){
				elementList[42] += 22;
				elementList[24] += 22;
				elementList[34] += 6;
			}else if(slots[inputSlot].getItemDamage() == 8){
				elementList[42] += 27;
				elementList[31] += 25;
				elementList[32] += 4;
				elementList[35] += 2;
				elementList[30] += 2;
				elementList[25] += 1;
			}else if(slots[inputSlot].getItemDamage() == 9){
				elementList[24] += 26;
				elementList[42] += 11;
				elementList[34] += 3;
				elementList[22] += 2;
			}else if(slots[inputSlot].getItemDamage() == 10){
				elementList[25] += 29;
				elementList[18] += 16;
				elementList[44] += 15;
				elementList[22] += 3;
			}else if(slots[inputSlot].getItemDamage() == 11){ //WTF
				elementList[42] += 14;
				elementList[0]  += 12;
				elementList[25] += 9;
				elementList[24] += 5;
				elementList[32] += 5;
				elementList[3]  += 4; 
				elementList[7]  += 4;
				elementList[13] += 4;
				elementList[12] += 4;
				elementList[28] += 2;
				elementList[29] += 1;
				elementList[21] += 1;
				elementList[16] += 1;
			}
		}else if(slots[inputSlot].getItem() == ModContents.sulfateShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[24] += 20;
				elementList[37] += 15;
				elementList[35] += 10;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[17] += 33;
				elementList[37] += 17;
				elementList[35] += 14;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[16] += 33;
				elementList[37] += 13;
				elementList[35] += 8;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[20] += 34;
				elementList[27] += 8;
				elementList[26] += 8;
				elementList[37] += 4;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[32] += 19;
				elementList[37] += 7;
				elementList[21] += 6;
				elementList[24] += 1;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[27] += 16;
				elementList[37] += 14;
				elementList[25] += 7;
				elementList[26] += 3;
			}else if(slots[inputSlot].getItemDamage() == 6){
				elementList[27] += 21;
				elementList[37] += 11;
			}else if(slots[inputSlot].getItemDamage() == 7){
				elementList[46] += 64;
				elementList[32] += 14;
			}else if(slots[inputSlot].getItemDamage() == 8){
				elementList[19] += 46;
				elementList[46] += 40;
			}
		}else if(slots[inputSlot].getItem() == ModContents.sulfideShards){
			if(slots[inputSlot].getItemDamage() == 0){
				elementList[19] += 37;
				elementList[37] += 21;
				elementList[38] += 17;
				elementList[18] += 12;
			}else if(slots[inputSlot].getItemDamage() == 1){
				elementList[38] += 36;
				elementList[19] += 36;
				elementList[37] += 17;
				elementList[17] += 11;
			}else if(slots[inputSlot].getItemDamage() == 2){
				elementList[38] += 36;
				elementList[23] += 35;
				elementList[37] += 16;
			}else if(slots[inputSlot].getItemDamage() == 3){
				elementList[19] += 88;
				elementList[37] += 12;
			}else if(slots[inputSlot].getItemDamage() == 4){
				elementList[18] += 32;
				elementList[17] += 27;
				elementList[37] += 27;
				elementList[20] += 10;
				elementList[16] += 3;
			}else if(slots[inputSlot].getItemDamage() == 5){
				elementList[26] += 37;
				elementList[16] += 33;
				elementList[37] += 33;
			}else if(slots[inputSlot].getItemDamage() == 6){
				elementList[37] += 53;
				elementList[16] += 47;
			}else if(slots[inputSlot].getItemDamage() == 7){
				elementList[37] += 30;
				elementList[17] += 30;
				elementList[18] += 28;
				elementList[16] += 10;
				elementList[20] += 2;
			}else if(slots[inputSlot].getItemDamage() == 8){
				elementList[16] += 26;
				elementList[37] += 26;
				elementList[17] += 24;
				elementList[28] += 9;
				elementList[24] += 7;
			}else if(slots[inputSlot].getItemDamage() == 9){
				elementList[20] += 64;
				elementList[37] += 33;
				elementList[16] += 3;
			}else if(slots[inputSlot].getItemDamage() == 10){
				elementList[37] += 34;
				elementList[18] += 25;
				elementList[17] += 20;
				elementList[16] += 9;
				elementList[20] += 4;
				elementList[23] += 3;
			}else if(slots[inputSlot].getItemDamage() == 11){
				elementList[17] += 44;
				elementList[37] += 29;
				elementList[18] += 14;
				elementList[16] += 13;
			}else if(slots[inputSlot].getItemDamage() == 12){
				elementList[46] += 74;
				elementList[37] += 26;
			}else if(slots[inputSlot].getItemDamage() == 13){
				elementList[47] += 75;
				elementList[37] += 25;
			}else if(slots[inputSlot].getItemDamage() == 14){
				elementList[44] += 50;
				elementList[37] += 22;
				elementList[48] += 17;
				elementList[17] += 11;
			}else if(slots[inputSlot].getItemDamage() == 15){
				elementList[49] += 78;
				elementList[37] += 22;
			}else if(slots[inputSlot].getItemDamage() == 16){
				elementList[37] += 27;
				elementList[17] += 26;
				elementList[18] += 24;
				elementList[49] += 23;
			}
		}
	}

	private boolean canExtractElements() {
		return  isValidMineral()
				&& (hasConsumable() && isConsumableUsable())
				&& powerCount >= machineSpeed()
				&& redstoneCount >= machineSpeed() * 2
				&& hasAcid(syngSlot, 5, consumedSyng) && hasAcid(fluoSlot, 4, consumedFluo);
	}

	private boolean isValidMineral() {
		return  slots[inputSlot] != null &&
			   (slots[inputSlot].getItem() == ModContents.arsenateShards ||
				slots[inputSlot].getItem() == ModContents.borateShards ||
				slots[inputSlot].getItem() == ModContents.carbonateShards ||
				slots[inputSlot].getItem() == ModContents.halideShards ||
				slots[inputSlot].getItem() == ModContents.nativeShards ||
				slots[inputSlot].getItem() == ModContents.oxideShards ||
				slots[inputSlot].getItem() == ModContents.phosphateShards ||
				slots[inputSlot].getItem() == ModContents.silicateShards ||
				slots[inputSlot].getItem() == ModContents.sulfateShards ||
				slots[inputSlot].getItem() == ModContents.sulfideShards);
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

	private void redstoneHandler() {
		if(slots[redstoneSlot] != null && slots[redstoneSlot].getItem() == Items.REDSTONE && redstoneCount <= (redstoneMax - redstoneCharge)){
			burnRedstone();
		}
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
