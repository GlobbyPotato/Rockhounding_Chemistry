package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.HeatExchangerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.HeatExchangerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEHeatExchangerTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEHeatExchangerController extends TileEntityInv {

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 2;
	public static int upgradeSlots = 1;

	public HeatExchangerRecipe dummyRecipe;
	
	public boolean direction = false;

	public TEHeatExchangerController() {
		super(0, 0, templateSlots, upgradeSlots);

		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);

	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.direction = compound.getBoolean("Direction");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("Direction", getDirection());
        return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "heat_exchanger_base";
	}

	private int getCooktimeMax() {
		return 20;
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.UP, 4).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return isFacingAt(270);
	}



	// ----------------------- RECIPE -----------------------
	public ArrayList<HeatExchangerRecipe> recipeList(){
		return HeatExchangerRecipes.heat_exchanger_recipes;
	}

	public HeatExchangerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public HeatExchangerRecipe getCurrentRecipe(){
		if(hasCooling() && hasEnoughCoolant()){
			for(int x = 0; x < recipeList().size(); x++){
				if(isMatchingInput(x)){
					return getRecipeList(x);
				}
			}
		}
		return null;
	}

	public HeatExchangerRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	private boolean isMatchingInput(int x) {
		return getRecipeList(x).getInput() != null 
			&& hasInputVessel() 
			&& getRecipeList(x).getInput().isFluidEqual(getInputVessel().inputTank.getFluid());
	}

	public FluidStack getRecipeInput(){ return isValidRecipe() ? getDummyRecipe().getInput() : null; }
	public FluidStack getRecipeOutput(){ return isValidRecipe() ? getDummyRecipe().getOutput() : null; }

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}



	//----------------------- CUSTOM -----------------------
	public int minCoolant(){
		return 10;
	}

	public boolean getDirection() {
		return this.direction;
	}

	public int powerConsume() {
		int baseConsume = (coolantTemperature() / 5) * ModConfig.basePower;
		int moddedConsume = ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
		return hasFluidCistern() ? moddedConsume : baseConsume;
	}

	public int coolantTemperature(){
		return hasCooling() ? getFluidCistern().inputTank.getFluid().getFluid().getTemperature() : 301;
	}

	public boolean isValidCoolant(){
		return coolantTemperature() > 0 && coolantTemperature() <= 300;
	}

	public int processRate(){
		return 100 * speedFactor();
	}

	public int consumeChance(){
		return 350 - coolantTemperature();
	}

	public int consumeRate(){
		return 100 - ((consumeChance() * 100) / 350);
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() >= powerConsume() : false;
	}

	private void drainPower() {
		getEngine().redstoneCount -= powerConsume();
		getEngine().markDirtyClient();
	}

//chamber
	public TEHeatExchangerTop getChamber(){
		BlockPos chamberPos = this.pos.offset(EnumFacing.UP);
		TileEntity te = this.world.getTileEntity(chamberPos);
		if(this.world.getBlockState(chamberPos) != null && te instanceof TEHeatExchangerTop){
			TEHeatExchangerTop tank = (TEHeatExchangerTop)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasChamber(){
		return getChamber() != null;
	}

//cyclone separator
	public boolean hasCycloneSeparator(){
		return TileStructure.getCycloneSeparator(this.world, this.pos.offset(EnumFacing.UP, 2), getFacing());
	}

//pressurizer
	public TEAuxiliaryEngine getPressurizer2(){
		BlockPos pressurizerPos = this.pos.offset(EnumFacing.UP, 1).offset(getFacing());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressurizerPos, getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizer(){
		return getPressurizer2() != null;
	}

//cistern
	public TEFluidCistern getFluidCistern(){
		BlockPos cisternPos = this.pos.offset(getFacing()).offset(EnumFacing.UP, 2);
		TEFluidCistern cistern = TileStructure.getFluidCistern(this.world, cisternPos, getFacing().getOpposite());
		return cistern != null ? cistern : null;
	}

	public boolean hasFluidCistern(){
		return getFluidCistern() != null;
	}

	public boolean hasCooling() {
		return hasFluidCistern() && getFluidCistern().inputTank.getFluid() != null;
	}

	public FluidStack getCooling() {
		return hasCooling() ? getFluidCistern().inputTank.getFluid() : null;
	}

//centrifuge
	public TECentrifuge getInputCentrifuge(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(270)), directionalFacing);
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getOutputCentrifuge(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(90)), directionalFacing);
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getInputCentrifuge() != null && getOutputCentrifuge() != null;
	}

//input vessel
	public TileVessel getInputVessel(){
		EnumFacing positionFacing = isFacingAt(270);
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			positionFacing = isFacingAt(90);
			directionalFacing = isFacingAt(270);
		}
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(positionFacing, 2), directionalFacing);
		return vessel != null ? vessel : null;
	}

	public boolean hasInputVessel(){
		return getInputVessel() != null;
	}

//output vessel
	public TileVessel getOutputVessel(){
		EnumFacing positionFacing = isFacingAt(90);
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			positionFacing = isFacingAt(270);
			directionalFacing = isFacingAt(270);
		}
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(positionFacing, 2), directionalFacing);
		return vessel != null ? vessel : null;
	}

	public boolean hasOutputVessel(){
		return getOutputVessel() != null;
	}

	private boolean isAssembled() {
		return hasChamber() && hasCycloneSeparator() && hasPressurizer() && hasCentrifuges();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			doPreset();

			if(isActive() && getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canCoolAir()){
				this.cooktime++;
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					drainPower();
					coolAir();
				}
				this.markDirtyClient();
			}else{
				tickOff();
			}
		}
	}



	private void doPreset() {
		if(hasEngine()){
			if(getEngine().enablePower){
				getEngine().enablePower = false;
				getEngine().markDirtyClient();
			}
			if(!getEngine().enableRedstone){
				getEngine().enableRedstone = true;
				getEngine().markDirtyClient();
			}
		}
	}

	public boolean canCoolAir() {
		return isActive()
			&& isValidRecipe()
			&& isAssembled()
			&& hasRedstonePower()
			&& hasInputGas()
			&& canStoreGas()
			&& canCool();
	}

	private boolean hasInputGas() {
		return hasInputVessel()
			&& this.input.canDrainFluid(getInputVessel().inputTank.getFluid(), getRecipeInput(), processRate())
			&& getRecipeInput().getFluid().isGaseous();
	}

	private boolean hasEnoughCoolant() {
		return hasFluidCistern() 
			&& isValidCoolant()
			&& getFluidCistern().inputTank.getFluidAmount() >= minCoolant();
	}

	private boolean canStoreGas() {
		return hasOutputVessel() 
			&& this.output.canSetOrAddFluid(getOutputVessel().inputTank, getOutputVessel().inputTank.getFluid(), getRecipeOutput(), processRate())
			&& getRecipeOutput().getFluid().isGaseous();
	}

	public boolean canCool(){
		return isValidRecipe() 
			&& getRecipeOutput().getFluid().getTemperature() < getRecipeInput().getFluid().getTemperature();
	}

	private void coolAir() {
		if(isValidRecipe() && getDummyRecipe() == getCurrentRecipe()){
			this.output.setOrFillFluid(getOutputVessel().inputTank, getRecipeOutput(), processRate());
			this.input.drainOrCleanFluid(getInputVessel().inputTank, processRate(), true);
			if(hasCooling() && this.rand.nextInt(consumeChance()) == 0){
				getFluidCistern().inputTank.drainInternal(minCoolant(), true);
			}
		}
		this.dummyRecipe = null;
	}

	public void flipResources() {
		TileStructure.flipTile(this.world, this.pos.offset(isFacingAt(90), 1), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(isFacingAt(270), 1), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(isFacingAt(90), 2), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(isFacingAt(270), 2), getFacing(), getDirection());
	}

}