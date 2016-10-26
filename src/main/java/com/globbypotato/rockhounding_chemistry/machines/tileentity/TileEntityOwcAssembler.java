package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.OwcBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumOwc;
import com.globbypotato.rockhounding_chemistry.machines.OwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.OwcController;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcAssembler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class TileEntityOwcAssembler extends TileEntityLockable implements ITickable, ISidedInventory {
    private ItemStack[] slots = new ItemStack[8];

    private int cookTime;
    private String inventoryName;
    
    public static int assemblingSpeed = 200;
    private static int getTier;

    private static final int SLOT_BULKHEAD = 0;
    private static final int SLOT_CONCRETE = 1;
    private static final int SLOT_DUCT = 2;
    private static final int SLOT_TURBINE = 3;
    private static final int SLOT_VALVE = 4;
    private static final int SLOT_GENERATOR = 5;
    private static final int SLOT_STORAGE = 6;
    private static final int SLOT_CONTROLLER = 7;
    
	//parts itemstacks
	ItemStack owcBulkhead = owcPart(0);
	ItemStack owcConcrete = owcPart(1);
	ItemStack owcDuct = owcPart(2);
	ItemStack owcTurbine = owcPart(3);
	ItemStack owcValve = owcPart(4);
	ItemStack owcGenerator = owcPart(5);
	ItemStack owcStorage = owcPart(6);
	ItemStack owcController = new ItemStack(ModBlocks.owcController);

	private static ItemStack owcPart(int meta){
		return new ItemStack(ModBlocks.owcBlocks, 1, meta);
	}

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
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public String getName(){
        return this.hasCustomName() ? this.inventoryName : "container.owcAssembler";
    }

    public boolean hasCustomName(){
        return this.inventoryName != null && !this.inventoryName.isEmpty();
    }

    public void setCustomInventoryName(String string){
        this.inventoryName = string;
    }

    public static void func_189676_a(DataFixer fixer){
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("OwcAssembler", new String[] {"Items"}));
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

    public void openInventory(EntityPlayer player){}

    public void closeInventory(EntityPlayer player){}

    public boolean isItemValidForSlot(int index, ItemStack stack){
        return true;
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
		return assemblingSpeed;
    	
    }

    public String getGuiID(){
        return Reference.MODID + ":owcAssembler";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerOwcAssembler(playerInventory, this);
    }

    public int getFieldCount() {
        return 2;
    }

    public int getField(int id){
        switch (id){
            case 0: return this.cookTime;
            case 1: return this.assemblingSpeed;
            default:return 0;
        }
    }

    public void setField(int id, int value){
        switch (id){
            case 0: this.cookTime = value; break;
            case 1: this.assemblingSpeed = value; 
        }
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
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
        this.cookTime = compound.getInteger("CookTime");
        if (compound.hasKey("CustomName", 8)){
            this.inventoryName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CookTime", this.cookTime);
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
            compound.setString("CustomName", this.inventoryName);
        }
        return compound;
    }


    //----------------------- PROCESS -----------------------
    @Override
    public void update(){
    	if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
		    EnumFacing facing = state.getValue(OwcAssembler.FACING);
	    	if(canAssembly()){handlerAssembler(state, facing);}
    	}
    }

    private void handlerAssembler(IBlockState state, EnumFacing facing) {
    	boolean flag = false;
		cookTime++;
		if(cookTime >= machineSpeed()) {
			cookTime = 0;
			assemblyOwc(state, facing);			
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private boolean canAssembly(){
		if(		(this.slots[SLOT_BULKHEAD] != null && this.slots[SLOT_BULKHEAD].isItemEqual(owcBulkhead) && this.slots[SLOT_BULKHEAD].stackSize == 24) &&		//bulkhead
				(this.slots[SLOT_CONCRETE] != null && this.slots[SLOT_CONCRETE].isItemEqual(owcConcrete) && this.slots[SLOT_CONCRETE].stackSize == 16) &&		//concrete
				(this.slots[SLOT_DUCT] != null && this.slots[SLOT_DUCT].isItemEqual(owcDuct) && this.slots[SLOT_DUCT].stackSize == 2) &&						//ducts
				(this.slots[SLOT_TURBINE] != null && this.slots[SLOT_TURBINE].isItemEqual(owcTurbine) && this.slots[SLOT_TURBINE].stackSize == 1) &&			//turbine
				(this.slots[SLOT_VALVE] != null && this.slots[SLOT_VALVE].isItemEqual(owcValve) && this.slots[SLOT_VALVE].stackSize == 1) &&					//valve
				(this.slots[SLOT_GENERATOR] != null && this.slots[SLOT_GENERATOR].isItemEqual(owcGenerator) && this.slots[SLOT_GENERATOR].stackSize == 1) &&	//generator
				(this.slots[SLOT_STORAGE] != null && this.slots[SLOT_STORAGE].isItemEqual(owcStorage) && this.slots[SLOT_STORAGE].stackSize == 1) &&			//storage
				(this.slots[SLOT_CONTROLLER] != null && this.slots[SLOT_CONTROLLER].isItemEqual(owcController) && this.slots[SLOT_CONTROLLER].stackSize == 1)){	//controller
			return true;
		}
		return false;
    }

    private void assemblyOwc(IBlockState state, EnumFacing facing){
    	spawnStructure(state, facing);
		for (int j = 0; j <= 7; j++){this.slots[j] = null;} 
		this.markDirty();
    }

	private void spawnStructure(IBlockState state, EnumFacing facing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); 
		int newX = 0; int newZ = 0;
		newX = pos.offset(facing, 3).getX(); newZ = pos.offset(facing, 3).getZ();
		if(!worldObj.isRemote){
			//place submerged chamber
			for (int y = -1; y <= 1; y++){
				for (int x = -1; x <= 1; x++){
					for (int z = -1; z <= 1; z++){
						samplePos.setPos(newX+x, pos.getY()+y, newZ+z);
						if(y==-1 && x==0 && z==0){
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, Blocks.WATER.getDefaultState());
						}else if(y>=0 && x==0 && z==0){
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, Blocks.AIR.getDefaultState());
						}else{
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(0)));
						}
					}
				}
			}
			//place tower and turbine
			for (int y = 2; y <= 3; y++){
				for (int x = -1; x <= 1; x++){
					for (int z = -1; z <= 1; z++){
						samplePos.setPos(newX+x, pos.getY()+y, newZ+z);
						if(y==2 && x==0 && z==0){
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(3)));
						}else if(y==3 && x==0 && z==0){
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(2)));
						}else{
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(1)));
						}
					}
				}
			}
			//place conduit
			samplePos.setPos(newX, pos.getY()+4, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(2)));
			samplePos.setPos(newX, pos.getY()+5, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(4)));
			//place machines
			newX = pos.offset(facing, 1).getX(); newZ = pos.offset(facing, 1).getZ(); samplePos.setPos(newX, pos.getY(), newZ);
			samplePos.setPos(newX, pos.getY(), newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcController.getDefaultState().withProperty(OwcController.FACING, facing));
			samplePos.setPos(newX, pos.getY()+1, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(5)));
			samplePos.setPos(newX, pos.getY()+2, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(6)));
		}
	}

	private void checkPos(MutableBlockPos samplePos) {
		if(isValidMinable(samplePos) && !isLiquid(samplePos)){
			worldObj.destroyBlock(samplePos, true);
		}
	}

	private boolean isValidMinable(MutableBlockPos samplePos) {
		return worldObj.getBlockState(samplePos) != Blocks.AIR && worldObj.getBlockState(samplePos) != Blocks.BEDROCK ;
	}

	private boolean isLiquid(MutableBlockPos samplePos) {
		return worldObj.getBlockState(samplePos).getMaterial().isLiquid();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return null;
	}
}
