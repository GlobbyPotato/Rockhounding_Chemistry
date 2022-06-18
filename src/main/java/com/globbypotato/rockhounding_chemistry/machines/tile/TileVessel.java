package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEGaslinePump;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityVessel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TileVessel extends TileEntityVessel implements ICollapse{
	public int emitType;
	public int emitThreashold;
	public int capacity;

	public static int templateSlots = 5;

	public FluidTank inputTank;
	public FluidStack filter = null;

	public int collapseRate = 0;

	public TileVessel(int capacity) {
		super(0, 0, templateSlots, 0);
		this.capacity = capacity;

		this.inputTank = new FluidTank(capacity){

			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return canFillFiltered(gas);
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return isValidPump(gas);
			}
			
			@Override
			public void onContentsChanged(){
				updateNeighbours();
		    }

		};
		this.inputTank.setTileEntity(this);
		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputGas"));
		this.filter = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Filter"));
		this.collapseRate = compound.getInteger("Collapse");
		this.emitThreashold = compound.getInteger("EmitThreashold");
		this.emitType = compound.getInteger("EmitType");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		compound.setInteger("Collapse", getCollapse());
		compound.setInteger("EmitType", this.emitType);
		compound.setInteger("EmitThreashold", this.emitThreashold);

		NBTTagCompound storage = new NBTTagCompound();
		this.inputTank.writeToNBT(storage);
		compound.setTag("InputGas", storage);

		if(getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        this.filter.writeToNBT(filterNBT);
	        compound.setTag("Filter", filterNBT);
		}

		return compound;
	}

	@Override
	public GasHandlerConcatenate getCombinedGasTank(){
		return new GasHandlerConcatenate(this.inputTank);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public int getCollapse(){
		return this.collapseRate;
	}

	@Override
 	public ArrayList<FluidTank> collapseList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}

	public int exhaustOffset(){
		return 1;
	}

	public int getTankCapacity() {
		return this.capacity;
	}

	public int getTankAmount() {
		return hasTankFluid() ? this.inputTank.getFluidAmount() : 0;
	}



	// ----------------------- CUSTOM -----------------------
	public void updateNeighbours() {
		this.world.notifyNeighborsOfStateChange(this.pos, this.world.getBlockState(this.pos).getBlock(), true);
	}

	public int getEmitType() {
		return this.emitType;
	}

	public int getEmitThreashold() {
		return this.emitThreashold;
	}

	public int emittedPower() {
		int powerFraction = getTankCapacity() / 16;
		// by level
		if(getEmitType() == 1){
			return ((15 * getTankAmount()) / getTankCapacity());

		// on over threashold
		}else if(getEmitType() == 2){
			return getTankAmount() >= ((getTankCapacity() / 100) * getEmitThreashold()) ? 15 : 0;

		// off over threashold
		}else if(getEmitType() == 3){
			return getTankAmount() < ((getTankCapacity() / 100) * getEmitThreashold()) ? 15 : 0;
		}

		return 0;
	}



	//----------------------- CUSTOM -----------------------
	public FluidStack getFilter(){
		return this.filter;
	}

	public boolean hasFilter(){
		return getFilter() != null;
	}

	public boolean inputTankHasGas(){
		return this.inputTank.getFluid() != null && this.inputTank.getFluidAmount() > 0;
	}

	public boolean hasTankFluid(){
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getTankFluid(){
		return hasTankFluid() ? this.inputTank.getFluid() : null;
	}

	public boolean canFillFiltered(FluidStack gas) {
		return gas.getFluid().isGaseous() 
			&& isMatchingFilter(gas) 
			&& this.input.canSetOrAddFluid(this.inputTank, getTankFluid(), gas, 1);
	}

	private boolean isMatchingFilter(FluidStack fluid) {
		return hasFilter() ? getFilter().isFluidEqual(fluid) : true;
	}

	public boolean isValidPump(FluidStack gas) {
		TileEntity tile = this.world.getTileEntity(this.pos.offset(getFacing()));
		if(tile != null && tile instanceof TEGaslinePump){
			TEGaslinePump pump = (TEGaslinePump)tile;
			return pump.isActive() && gas != null && gas.getFluid().isGaseous();
		}
		return false;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {}

	public void checkCollapse() {
		if(ModConfig.enableHazard){
			if(world.rand.nextInt(ModConfig.hazardChance) == 0){
				if(handleCollapse(getCollapse(), world, pos)){
					collapseRate++;
					if(getCollapse() >= ModConfig.pressureTolerance){
						if(hasExhaust(world, pos, exhaustOffset())){
							doExhaustion(world, pos, exhaustOffset());
							collapseRate /= 2;
						}else{
							world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (2.0F * exhaustOffset()), true);
						}
					}
				}else{
					if(handleRelease(getCollapse(), world, pos)){
						if(getCollapse() > 0){
							collapseRate--;
						}
					}
				}
			}
		}
	}

}