package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumAirGases;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GanPlantRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityVessel;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TEMultivessel extends TileEntityVessel implements ICollapse {

	public static int templateSlots = 12;

	public FluidTank tank_Ar;
	public FluidTank tank_CO;
	public FluidTank tank_Ne;
	public FluidTank tank_He;
	public FluidTank tank_Kr;
	public FluidTank tank_Xe;

	public boolean[] drainValve = new boolean[6];
	public boolean[] rareEnabler = new boolean[]{true, true, true, true, true, true};

	public int collapseRate = 0;

	public TEMultivessel() {
		super(0, 0, templateSlots, 0);

		this.tank_Ar = new FluidTank(1000){
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return isValidGas(tank_Ar, gas, EnumFluid.pickFluid(EnumFluid.ARGON));
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return canDrainGas(0, tank_Ar);
			}

		};
		this.tank_Ar.setTileEntity(this);

		this.tank_CO = new FluidTank(1000){
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return isValidGas(tank_CO, gas, EnumFluid.pickFluid(EnumFluid.CARBON_DIOXIDE));
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return canDrainGas(1, tank_CO);
			}
		};
		this.tank_CO.setTileEntity(this);

		this.tank_Ne = new FluidTank(1000){
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return isValidGas(tank_Ne, gas, EnumFluid.pickFluid(EnumFluid.NEON));
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return canDrainGas(2, tank_Ne);
			}
		};
		this.tank_Ne.setTileEntity(this);

		this.tank_He = new FluidTank(1000){
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return isValidGas(tank_He, gas, EnumFluid.pickFluid(EnumFluid.HELIUM));
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return canDrainGas(3, tank_He);
			}
		};
		this.tank_He.setTileEntity(this);

		this.tank_Kr = new FluidTank(1000){
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return isValidGas(tank_Kr, gas, EnumFluid.pickFluid(EnumFluid.KRYPTON));
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return canDrainGas(4, tank_Kr);
			}
		};
		this.tank_Kr.setTileEntity(this);

		this.tank_Xe = new FluidTank(1000){
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return isValidGas(tank_Xe, gas, EnumFluid.pickFluid(EnumFluid.XENON));
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return canDrainGas(5, tank_Xe);
			}
		};
		this.tank_Xe.setTileEntity(this);

		this.markDirtyClient();
	}

	@Override
	public GasHandlerConcatenate getCombinedGasTank(){
		return new GasHandlerConcatenate(this.tank_Xe, this.tank_Kr, this.tank_He, this.tank_Ne, this.tank_CO, this.tank_Ar);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.tank_Ar.readFromNBT(compound.getCompoundTag("Tank_Ar"));
		this.tank_CO.readFromNBT(compound.getCompoundTag("Tank_CO"));
		this.tank_Ne.readFromNBT(compound.getCompoundTag("Tank_Ne"));
		this.tank_He.readFromNBT(compound.getCompoundTag("Tank_He"));
		this.tank_Kr.readFromNBT(compound.getCompoundTag("Tank_Kr"));
		this.tank_Xe.readFromNBT(compound.getCompoundTag("Tank_Xe"));
		
        NBTTagCompound drainList = compound.getCompoundTag("Drains");
		for(int i = 0; i < drainList.getSize(); i++){
			this.drainValve[i] = drainList.getBoolean("Drain" + i);
		}

        NBTTagCompound enableList = compound.getCompoundTag("Enablers");
		for(int i = 0; i < enableList.getSize(); i++){
			if(!GanPlantRecipes.inhibited_gases.contains(EnumAirGases.name(i))){
				this.rareEnabler[i] = enableList.getBoolean("Enabler" + i);
			}else{
				this.rareEnabler[i] = false;
			}
		}

		this.collapseRate = compound.getInteger("Collapse");

	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound tank_Ar = new NBTTagCompound();
		this.tank_Ar.writeToNBT(tank_Ar); compound.setTag("Tank_Ar", tank_Ar);

		NBTTagCompound tank_CO = new NBTTagCompound();
		this.tank_CO.writeToNBT(tank_CO); compound.setTag("Tank_CO", tank_CO);

		NBTTagCompound tank_Ne = new NBTTagCompound();
		this.tank_Ne.writeToNBT(tank_Ne); compound.setTag("Tank_Ne", tank_Ne);

		NBTTagCompound tank_He = new NBTTagCompound();
		this.tank_He.writeToNBT(tank_He); compound.setTag("Tank_He", tank_He);

		NBTTagCompound tank_Kr = new NBTTagCompound();
		this.tank_Kr.writeToNBT(tank_Kr); compound.setTag("Tank_Kr", tank_Kr);

		NBTTagCompound tank_Xe = new NBTTagCompound();
		this.tank_Xe.writeToNBT(tank_Xe); compound.setTag("Tank_Xe", tank_Xe);

        NBTTagCompound drainList = new NBTTagCompound();
		for(int i = 0; i < this.drainValve.length; i++){
			drainList.setBoolean("Drain" + i, this.drainValve[i]);
		}
		compound.setTag("Drains", drainList);

        NBTTagCompound enableList = new NBTTagCompound();
		for(int i = 0; i < this.rareEnabler.length; i++){
			enableList.setBoolean("Enabler" + i, this.rareEnabler[i]);
		}
		compound.setTag("Enablers", enableList);

		compound.setInteger("Collapse", getCollapse());

		return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "multivessel";
	}

	public int getCollapse(){
		return this.collapseRate;
	}

	@Override
 	public ArrayList<FluidTank> collapseList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.tank_Ar);
		tanks.add(this.tank_CO);
		tanks.add(this.tank_Ne);
		tanks.add(this.tank_He);
		tanks.add(this.tank_Kr);
		tanks.add(this.tank_Xe);
		return tanks;
 	}



	//----------------------- CUSTOM -----------------------
	public boolean has_Ar(){
		return this.tank_Ar.getFluid() != null && this.tank_Ar.getFluidAmount() > 0;
	}

	public boolean has_CO(){
		return this.tank_CO.getFluid() != null && this.tank_CO.getFluidAmount() > 0;
	}

	public boolean has_Ne(){
		return this.tank_Ne.getFluid() != null && this.tank_Ne.getFluidAmount() > 0;
	}

	public boolean has_He(){
		return this.tank_He.getFluid() != null && this.tank_He.getFluidAmount() > 0;
	}

	public boolean has_Kr(){
		return this.tank_Kr.getFluid() != null && this.tank_Kr.getFluidAmount() > 0;
	}

	public boolean has_Xe(){
		return this.tank_Xe.getFluid() != null && this.tank_Xe.getFluidAmount() > 0;
	}

	public boolean isValidGas(FluidTank tank, FluidStack gas, Fluid fluid) {
		return gas.getFluid().isGaseous()
			&& gas.getFluid().equals(fluid)
			&& canAddGas(tank, gas);
	}

	private boolean canAddGas(FluidTank tank, FluidStack gas) {
		return this.input.canSetOrAddFluid(tank, tank.getFluid(), gas, 1);
	}

	public boolean canDrainGas(int i, FluidTank tank) {
		return isValidPump()	
			&& this.drainValve[i] 
			&& tank.getFluidAmount() > 0;
	}

	public boolean isValidPump() {
		TileEntity tile = this.world.getTileEntity(this.pos.offset(getFacing()));
		if(tile != null && tile instanceof TEGaslinePump){
			TEGaslinePump pump = (TEGaslinePump)tile;
			return pump.isActive();
		}
		return false;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			checkCollapse();
			this.markDirtyClient();
		}
	}

	public void checkCollapse() {
		if(ModConfig.enableHazard){
			if(world.rand.nextInt(ModConfig.hazardChance) == 0){
				if(handleCollapse(getCollapse(), world, pos)){
					collapseRate++;
					if(getCollapse() >= ModConfig.pressureTolerance){
						if(hasExhaust(world, pos, 1)){
							doExhaustion(world, pos, 1);
							collapseRate /= 2;
						}else{
							world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (2.0F * 1), true);
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