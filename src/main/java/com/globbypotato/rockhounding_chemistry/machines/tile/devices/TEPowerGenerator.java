package com.globbypotato.rockhounding_chemistry.machines.tile.devices;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEPowerGenerator extends TileEntityPoweredVessel {
	public static final int REDSTONE_SLOT = 1;

	public static final int INDUCTOR_SLOT = 0;
	public static final int TURBINE_SLOT = 1;
	
	public boolean enablePower = false;
	public boolean enableRedstone = false;

	public static int inputSlots = 2;
	public static int templateSlots = 1;
	public static int upgradeSlots = 2;

	public TEPowerGenerator() {
		super(inputSlots, 0, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == fuelID() && isFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == REDSTONE_SLOT && hasRedstone(insertingStack)){
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
				if(slot == TURBINE_SLOT && ModUtils.hasTurbine(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.enablePower = compound.getBoolean("EnablePower");
		this.enableRedstone = compound.getBoolean("EnableRedstone");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("EnablePower", enablePower());
		compound.setBoolean("EnableRedstone", enableRedstone());
		return compound;
	}

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

	public ItemStack redstoneSlot(){
		return this.input.getStackInSlot(REDSTONE_SLOT);
	}

	public ItemStack inductorSlot(){
		return this.upgrade.getStackInSlot(INDUCTOR_SLOT);
	}

	public ItemStack turbineSlot(){
		return this.upgrade.getStackInSlot(TURBINE_SLOT);
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



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "power_generator";
	}

	@Override
	public boolean hasRF() {
		return true;
	}

	@Override
	public boolean isComparatorSensible(){
		return false;
	}



	//----------------------- CUSTOM -----------------------
	public boolean enablePower() {
		return this.enablePower;
	}

	public boolean enableRedstone() {
		return this.enableRedstone;
	}

	@Override
	public int gasBurntime(){
		return ModConfig.gasBurntime;
	}

	@Override
	public int gasEnergizer(){
		return ModConfig.gasEnergizer;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){

			if(enablePower()){
				handlePowerSupplies();
			}

			if(enableRedstone()){
				handleRedstoneSupplies();
			}

			if(isActive()){
				provideEnergy();
				this.markDirtyClient();
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

	private void handleRedstoneSupplies() {
		if(!isFullRedstone()){
			if(!this.redstoneSlot().isEmpty()){
				if(this.redstoneSlot().getItem() == Item.getItemFromBlock(Blocks.REDSTONE_BLOCK) || this.redstoneSlot().getItem() == Items.REDSTONE){
					redstoneHandler(REDSTONE_SLOT, getRFConsume());
					this.markDirtyClient();
				}
			}
		}
		if(ModUtils.hasTurbine(turbineSlot())){
			turbineHandler();
		}

		injectEnergy();
	}

	private void provideEnergy() {
		if(this.getRedstone() > 0){
			TileEntity checkTile = this.world.getTileEntity(this.pos.offset(getFacing()));
			if(checkTile != null){
				sendEnergy(checkTile, getFacing());
			}
		}
	}

}