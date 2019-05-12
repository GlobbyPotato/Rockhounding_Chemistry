package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEAirCompressor extends TileEntityPoweredVessel {

	public static int inputSlots = 1;
	public static int templateSlots = 1;

	public TEAirCompressor() {
		super(inputSlots, 0, templateSlots, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == fuelID() && isGatedPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
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



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "air_compressor";
	}

	@Override
	public int fuelID() {
		return 0;
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 90);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fuelSlot(){
		return this.input.getStackInSlot(fuelID());
	}



	//----------------------- CUSTOM -----------------------
	public int getCooktimeMax(){
		return 20;
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

			handleSupplies();

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

	private void handleSupplies() {
		if(!fuelSlot().isEmpty()){
			if(fuelSlot().isItemEqual(CoreBasics.heat_inductor)){
				powerHandler(fuelSlot());
				if(isActive()){
					injectFuel();
				}
				this.markDirtyClient();
			}else{
				if(isActive()){
					fuelHandler(fuelSlot());
				}
			}
		}
		if(isActive() && isInductionActive()){
			injectFuel();
		}
		if(isActive()){
			lavaHandler();
			gasHandler();
		}
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