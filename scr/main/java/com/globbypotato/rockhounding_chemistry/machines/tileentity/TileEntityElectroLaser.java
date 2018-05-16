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

	public static int totInput = 1;
	public static int totOutput = 0;

	public TileEntityElectroLaser() {
		super(totInput, totOutput, 0);

		this.inputTank = new FluidTank(10000) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN));
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.inputTank.setTileEntity(this);

		this.input = new MachineStackHandler(totInput, this) {
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate) {
				if (slot == SOLUTION_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN)) ) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
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
		return new FluidHandlerConcatenate(this.inputTank);
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
		acceptEnergy();
		if (!this.worldObj.isRemote) {
			emptyContainer(SOLUTION_SLOT, this.inputTank);
			placeRay();
			this.markDirtyClient();
		}
	}

	private boolean canEmit() {
		return this.getRedstone() >= this.redstoneCost()
			&& this.input.hasEnoughFluid(this.inputTank.getFluid(), new FluidStack(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN), consumedNitrogen()));
	}

	private void placeRay() {
	    EnumFacing laserfacing = state().getValue(eleFacing());
	    EnumFacing rayfacing = laserfacing;
		this.firstObstacle = false;
		if(this.countBeam >= getStage() + 1){
			if(isEmitting()){
	    		for(int x = 1; x <= getStage() + 1; x++){
	    			BlockPos checkingpos = this.pos.offset(laserfacing, x);
					IBlockState checkingstate = this.worldObj.getBlockState(checkingpos); 
	    			if(this.firstObstacle == false){
					    if(checkingstate.getBlock() == air().getBlock()){
							IBlockState raystate = ray().getDefaultState().withProperty(rayFacing(), rayfacing); 
					    	this.worldObj.setBlockState(checkingpos, raystate);
				    		this.firstObstacle = false;
				    		TileEntityLaserRay ray = (TileEntityLaserRay)this.worldObj.getTileEntity(checkingpos);
				    		ray.stage = getDamage();
					    }else if(checkingstate.getBlock() == ray()){
					    	if(checkingstate.getValue(rayFacing()) == rayfacing ) {
					    		this.firstObstacle = false;
						    }else{
						    	this.firstObstacle = true;
					    	}
					    }else{
					    	this.firstObstacle = true;
					    }
	    			}
	    		}
	    		if(this.rand.nextInt(20) == 0){
	    			this.inputTank.getFluid().amount -= consumedNitrogen();
	    		}
				this.redstoneCount -= redstoneCost();
			}else{
		    	if(this.worldObj.getBlockState(this.pos.offset(laserfacing)).getBlock() == ray()){
		    		EnumFacing beamfacing = this.worldObj.getBlockState(this.pos.offset(laserfacing)).getValue(rayFacing());
				    if(beamfacing == laserfacing){
				    	this.worldObj.setBlockState(this.pos.offset(laserfacing), air());
				    }
		    	}
			}
			this.countBeam = 0;
		}else{
			this.countBeam++;
		}
	}

	public int getStage() {
		IBlockState state = this.worldObj.getBlockState(this.pos);
	    EnumFacing elefacing = state.getValue(eleFacing());
	    BlockPos cascadepos = this.pos.offset(elefacing.getOpposite());
	    IBlockState cascadestate = this.worldObj.getBlockState(cascadepos);
	    if(cascadestate != null && cascadestate.getBlock() instanceof LaserAmplifier){
		    EnumFacing cascadefacing = cascadestate.getValue(cascadeFacing());
		    if(cascadefacing == elefacing){
		    	TileEntityLaserAmplifier amplifier = (TileEntityLaserAmplifier)this.worldObj.getTileEntity(cascadepos);
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
		IBlockState state = this.worldObj.getBlockState(this.pos);
	    EnumFacing elefacing = state.getValue(eleFacing());
	    BlockPos cascadepos = this.pos.offset(elefacing.getOpposite());
	    IBlockState cascadestate = this.worldObj.getBlockState(cascadepos);
	    if(cascadestate != null && cascadestate.getBlock() instanceof LaserAmplifier){
		    EnumFacing cascadefacing = cascadestate.getValue(cascadeFacing());
		    if(cascadefacing == elefacing){
		    	TileEntityLaserAmplifier amplifier = (TileEntityLaserAmplifier)this.worldObj.getTileEntity(cascadepos);
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
		return this.worldObj.getBlockState(this.pos);
	}

	public IBlockState air(){
		return Blocks.AIR.getDefaultState();
	}

	public Block ray(){
		return ModBlocks.laserRay;
	}

}