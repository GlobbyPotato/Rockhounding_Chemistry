package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.OwcBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumOwc;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.OwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.OwcController;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiOwcAssembler;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class TileEntityOwcAssembler extends TileEntityMachineInv {

	public int cookTime;

    private static final int SLOT_BULKHEAD = 0;
    private static final int SLOT_CONCRETE = 1;
    private static final int SLOT_DUCT = 2;
    private static final int SLOT_TURBINE = 3;
    private static final int SLOT_VALVE = 4;
    private static final int SLOT_GENERATOR = 5;
    private static final int SLOT_STORAGE = 6;
    private static final int SLOT_CONTROLLER = 7;
    
	ItemStack owcBulkhead = owcPart(0);
	ItemStack owcConcrete = owcPart(1);
	ItemStack owcDuct = owcPart(2);
	ItemStack owcTurbine = owcPart(3);
	ItemStack owcValve = owcPart(4);
	ItemStack owcGenerator = owcPart(5);
	ItemStack owcStorage = owcPart(6);
	ItemStack owcController = new ItemStack(ModBlocks.owcController);
	
	public TileEntityOwcAssembler() {
		super(8,0);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SLOT_BULKHEAD && ItemStack.areItemsEqual(owcBulkhead, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONCRETE && ItemStack.areItemsEqual(owcConcrete, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_DUCT && ItemStack.areItemsEqual(owcDuct, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_TURBINE && ItemStack.areItemsEqual(owcTurbine, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_VALVE && ItemStack.areItemsEqual(owcValve, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_GENERATOR && ItemStack.areItemsEqual(owcGenerator, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_STORAGE && ItemStack.areItemsEqual(owcStorage, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONTROLLER && ItemStack.areItemsEqual(owcController, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}

	private static ItemStack owcPart(int meta){
		return new ItemStack(ModBlocks.owcBlocks, 1, meta);
	}

	@Override
	public int getGUIHeight() {
		return GuiOwcAssembler.HEIGHT;
	}

	public int getMaxCookTime(){
		return ModConfig.speedAssembling;
	}

    @Override
    public void update(){
    	if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
		    EnumFacing facing = state.getValue(OwcAssembler.FACING);
	    	if(canAssembly()){handlerAssembler(state, facing);}
	    	this.markDirtyClient();
    	}
    }

	private boolean canAssembly(){
		if(	(input.getStackInSlot(SLOT_BULKHEAD) != null && input.getStackInSlot(SLOT_BULKHEAD).isItemEqual(owcBulkhead) && input.getStackInSlot(SLOT_BULKHEAD).stackSize == 24) &&			//bulkhead
			(input.getStackInSlot(SLOT_CONCRETE) != null && input.getStackInSlot(SLOT_CONCRETE).isItemEqual(owcConcrete) && input.getStackInSlot(SLOT_CONCRETE).stackSize == 16) &&			//concrete
			(input.getStackInSlot(SLOT_DUCT) != null && input.getStackInSlot(SLOT_DUCT).isItemEqual(owcDuct) && input.getStackInSlot(SLOT_DUCT).stackSize == 2) &&							//ducts
			(input.getStackInSlot(SLOT_TURBINE) != null && input.getStackInSlot(SLOT_TURBINE).isItemEqual(owcTurbine) && input.getStackInSlot(SLOT_TURBINE).stackSize == 1) &&				//turbine
			(input.getStackInSlot(SLOT_VALVE) != null && input.getStackInSlot(SLOT_VALVE).isItemEqual(owcValve) && input.getStackInSlot(SLOT_VALVE).stackSize == 1) &&						//valve
			(input.getStackInSlot(SLOT_GENERATOR) != null && input.getStackInSlot(SLOT_GENERATOR).isItemEqual(owcGenerator) && input.getStackInSlot(SLOT_GENERATOR).stackSize == 1) &&		//generator
			(input.getStackInSlot(SLOT_STORAGE) != null && input.getStackInSlot(SLOT_STORAGE).isItemEqual(owcStorage) && input.getStackInSlot(SLOT_STORAGE).stackSize == 1) &&				//storage
			(input.getStackInSlot(SLOT_CONTROLLER) != null && input.getStackInSlot(SLOT_CONTROLLER).isItemEqual(owcController) && input.getStackInSlot(SLOT_CONTROLLER).stackSize == 1)){	//controller
				return true;
			}
		return false;
    }
	
    private void handlerAssembler(IBlockState state, EnumFacing facing) {
    	boolean flag = false;
		cookTime++;
		if(cookTime >= getMaxCookTime()) {
			cookTime = 0;
			assemblyOwc(state, facing);			
			flag = true;
		}
		if(flag){this.markDirty();}
	}

    private void assemblyOwc(IBlockState state, EnumFacing facing){
    	spawnStructure(state, facing);
		for (int j = 0; j <= 7; j++){input.setStackInSlot(j, null);} 
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
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[0]));
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
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[3]));
						}else if(y==3 && x==0 && z==0){
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[2]));
						}else{
							checkPos(samplePos);
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[1]));
						}
					}
				}
			}
			//place conduit
			samplePos.setPos(newX, pos.getY()+4, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[2]));
			samplePos.setPos(newX, pos.getY()+5, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[4]));
			//place machines
			newX = pos.offset(facing, 1).getX(); newZ = pos.offset(facing, 1).getZ(); samplePos.setPos(newX, pos.getY(), newZ);
			samplePos.setPos(newX, pos.getY(), newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcController.getDefaultState().withProperty(OwcController.FACING, facing));
			samplePos.setPos(newX, pos.getY()+1, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[5]));
			samplePos.setPos(newX, pos.getY()+2, newZ);
			checkPos(samplePos);
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[6]));
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
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.cookTime = compound.getInteger("CookTime");
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CookTime", this.cookTime);
        return compound;
    }

}