package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEAirCompressor extends TileEntityPoweredVessel {

	public static final int INDUCTOR_SLOT = 0;

	public static int inputSlots = 1;
	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public TEAirCompressor() {
		super(inputSlots, 0, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == fuelID() && isFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INDUCTOR_SLOT && ModUtils.hasInductor(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);

	}



	//----------------------- I/O -----------------------
	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.lavaTank);
	}

	@Override
	public GasHandlerConcatenate getCombinedGasTank(){
		return new GasHandlerConcatenate(this.gasTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fuelSlot(){
		return this.input.getStackInSlot(fuelID());
	}

	public ItemStack inductorSlot(){
		return this.upgrade.getStackInSlot(INDUCTOR_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "air_compressor";
	}



	//----------------------- FUEL -----------------------
	@Override
	public boolean canInduct() {
		return ModUtils.hasInductor(inductorSlot());
	}

	@Override
	public int fuelID() {
		return 0;
	}

	@Override
	public int getRFToFuel() {
		return this.storage.getEnergyStored() * ModConfig.rfToFuelFactor;
	}

	@Override
	public boolean hasRF() {
		return true;
	}



	//----------------------- CUSTOM -----------------------
	public int getCooktimeMax(){
		return 20;
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 90);
	}



	//----------------------- STRUCTURE -----------------------
	public TileVessel getVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, getFacing(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasVessel(){
		return getVessel() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){

			handlePowerSupplies();

			if(isActive()){
				if(canCompress()){
					compress();
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
					}
					this.markDirtyClient();
				}else{
					tickOff();
				}
			}else{
				tickOff();
			}
		}
	}

	private void handlePowerSupplies() {
		if(!fuelSlot().isEmpty()){
			fuelHandler(fuelSlot());
		}

		if(canInduct()){
			injectFuel();
		}

		lavaHandler();

		gasHandler();
	}

	private boolean canCompress() {
		return getPower() > 0
			&& canFillAir()
			&& this.output.canSetOrFillFluid(getVessel().inputTank, getVessel().inputTank.getFluid(), compressedAir());
	}

	private boolean canFillAir() {
		return hasVessel() ? getVessel().canFillFiltered(compressedAir()) : false;
	}

	private void compress() {
		this.output.setOrFillFluid(getVessel().inputTank, compressedAir());
		this.cooktime++;
		drainPower();
	}

	private static FluidStack compressedAir() {
		return BaseRecipes.getFluid(EnumFluid.COMPRESSED_AIR, 100);
	}

	public void drainPower() {
		this.powerCount --;
	}

}