package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.ElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.LaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.LaserRay;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiElectroLaser;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TileEntityElectroLaser extends TileEntityMachineTank {

	public static final int SOLUTION_SLOT = 0;

	public FluidTank inputTank;
	private int countBeam;
	private boolean firstObstacle;

	public TileEntityElectroLaser() {
		super(1, 0, 0);

		inputTank = new FluidTank(10000) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN));
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		inputTank.setTileEntity(this);

		input = new MachineStackHandler(INPUT_SLOTS, this) {
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate) {
				if (slot == SOLUTION_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN)) ) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN);
		this.markDirtyClient();
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiElectroLaser.HEIGHT;
	}

	@Override
	public boolean hasRF() {
		return true;
	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(inputTank);
	}



	// ----------------------- CUSTOM -----------------------
	public int redstoneCost(){
		return 20;
	}

	public int consumedNitrogen(){
		return 10;
	}

	public int getRange(){
		return 5;
	}

	public int deathFactor(){
		return 6;
	}
	
	public int getDamage() {
		return (getStage() + 1) * deathFactor();
	}

	public boolean isEmitting(){
		return canEmit() && isPowered();
	}


	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!worldObj.isRemote) {
			emptyContainer(SOLUTION_SLOT, inputTank);
			placeRay();
			this.markDirtyClient();
		}
	}

	private boolean canEmit() {
		return this.getRedstone() >= this.redstoneCost()
			&& input.hasEnoughFluid(inputTank.getFluid(), new FluidStack(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN), consumedNitrogen()));
	}

	private void placeRay() {
	    EnumFacing laserfacing = state().getValue(eleFacing());
	    EnumFacing rayfacing = laserfacing;
		firstObstacle = false;
		if(countBeam >= getStage() + 1){
			if(isEmitting()){
	    		for(int x = 1; x <= getStage() + 1; x++){
	    			BlockPos checkingpos = pos.offset(laserfacing, x);
					IBlockState checkingstate = worldObj.getBlockState(checkingpos); 
	    			if(firstObstacle == false){
					    if(checkingstate.getBlock() == air().getBlock()){
							IBlockState raystate = ray().getDefaultState().withProperty(rayFacing(), rayfacing); 
					    	worldObj.setBlockState(checkingpos, raystate);
				    		firstObstacle = false;
				    		TileEntityLaserRay ray = (TileEntityLaserRay)worldObj.getTileEntity(checkingpos);
				    		ray.stage = getDamage();
					    }else if(checkingstate.getBlock() == ray()){
					    	if(checkingstate.getValue(rayFacing()) == rayfacing ) {
					    		firstObstacle = false;
						    }else{
						    	firstObstacle = true;
					    	}
					    }else{
					    	firstObstacle = true;
					    }
	    			}
	    		}
	    		if(rand.nextInt(20) == 0){
	    			this.inputTank.getFluid().amount -= consumedNitrogen();
	    		}
				this.redstoneCount -= redstoneCost();
			}else{
		    	if(worldObj.getBlockState(pos.offset(laserfacing)).getBlock() == ray()){
		    		EnumFacing beamfacing = worldObj.getBlockState(pos.offset(laserfacing)).getValue(rayFacing());
				    if(beamfacing == laserfacing){
				    	worldObj.setBlockState(pos.offset(laserfacing), air());
				    }
		    	}
			}
			countBeam = 0;
		}else{
			countBeam++;
		}
	}

	public int getStage() {
		IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing elefacing = state.getValue(eleFacing());
	    BlockPos cascadepos = pos.offset(elefacing.getOpposite());
	    IBlockState cascadestate = worldObj.getBlockState(cascadepos);
	    if(cascadestate != null && cascadestate.getBlock() instanceof LaserAmplifier){
		    EnumFacing cascadefacing = cascadestate.getValue(cascadeFacing());
		    if(cascadefacing == elefacing){
		    	TileEntityLaserAmplifier amplifier = (TileEntityLaserAmplifier)worldObj.getTileEntity(cascadepos);
		    	if(amplifier.isPowered()){ 
		    		if(amplifier.getStage() <= getRange()){
		    			return amplifier.getStage();
		    		}
		    	}
		    }
	    }
	    return 0;
	}

	public boolean isPowered() {
		IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing elefacing = state.getValue(eleFacing());
	    BlockPos cascadepos = pos.offset(elefacing.getOpposite());
	    IBlockState cascadestate = worldObj.getBlockState(cascadepos);
	    if(cascadestate != null && cascadestate.getBlock() instanceof LaserAmplifier){
		    EnumFacing cascadefacing = cascadestate.getValue(cascadeFacing());
		    if(cascadefacing == elefacing){
		    	TileEntityLaserAmplifier amplifier = (TileEntityLaserAmplifier)worldObj.getTileEntity(cascadepos);
		    	if(amplifier.isPowered()){
			    	return true;
		    	}
		    }
	    }
		return false;
	}

	public PropertyDirection eleFacing(){
		return ElectroLaser.FACING;
	}

	public PropertyDirection cascadeFacing(){
		return LaserAmplifier.FACING;
	}

	public PropertyDirection rayFacing(){
		return LaserRay.FACING;
	}

	public IBlockState state(){
		return worldObj.getBlockState(pos);
	}

	public IBlockState air(){
		return Blocks.AIR.getDefaultState();
	}

	public Block ray(){
		return ModBlocks.laserRay;
	}

}