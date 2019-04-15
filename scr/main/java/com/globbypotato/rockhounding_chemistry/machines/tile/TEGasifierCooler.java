package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEGasifierCooler extends TileEntityInv {

	public static int upgradeSlots = 2;
	public static final int SPEED_SLOT = 0;
	public static final int CASING_SLOT = 1;

	public int temperature = 300;

	public GasifierPlantRecipe dummyRecipe;

	public TEGasifierCooler() {
		super(0, 0, 0, upgradeSlots);

		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public void validateSlotIndex(int slot){
				if(upgrade.getSlots() < upgradeSlots){
					NonNullList<ItemStack> stacksCloned = stacks;
					upgrade.setSize(upgradeSlots);
					for(ItemStack stack : stacksCloned){
		                stacks.set(slot, stack);
					}
				}
				super.validateSlotIndex(slot);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CASING_SLOT && (ItemStack.areItemsEqual(insertingStack, BaseRecipes.refractory_upd)) ){
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
		this.temperature = compound.getInteger("Temperature");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("Temperature", getTemperature());
		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}

	public ItemStack casingSlot() {
		return this.upgrade.getStackInSlot(CASING_SLOT);
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<GasifierPlantRecipe> recipeList(){
		return GasifierPlantRecipes.gasifier_plant_recipes;
	}

	public static GasifierPlantRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public GasifierPlantRecipe getCurrentRecipe(){
		if(hasFluids()){
			for(int x = 0; x < recipeList().size(); x++){
				if(isMatchingInput(x) && isMatchingReactant(x)){
					return getRecipeList(x);
				}
			}
		}
		return null;
	}

	public GasifierPlantRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	private boolean hasFluids() {
		return hasSlurry() && hasReactant();
	}

	private boolean isMatchingInput(int x) {
		return getRecipeList(x).getInput() != null && getRecipeList(x).getInput().isFluidEqual(getSlurry());
	}

	private boolean isMatchingReactant(int x) {
		return getRecipeList(x).getReactant() != null && getRecipeList(x).getReactant().isFluidEqual(getReactant());
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public FluidStack getRecipeSlurry(){ return isValidRecipe() ? getDummyRecipe().getInput() : null;}
	private int getDrainSlurry() {return isValidRecipe() ? getRecipeSlurry().amount * speedFactor(): 0;}

	public FluidStack getRecipeReactant(){ return isValidRecipe() ? getDummyRecipe().getReactant() : null; }
	private int getDrainReactant() {return isValidRecipe() ? getRecipeReactant().amount * speedFactor(): 0;}

	public FluidStack getRecipeOutput(){ return isValidRecipe() ? getDummyRecipe().getOutput() : null; }
	private int getProducedGas() {return isValidRecipe() ? getRecipeOutput().amount * speedFactor() : 0;}

	public int getThreashold(){return isValidRecipe() ? getDummyRecipe().getTemperature() : 0;}

	public ItemStack getMainSlag(){ return isValidRecipe() ? getDummyRecipe().getMainSlag() : ItemStack.EMPTY; }
	private boolean hasMainSlag(){ return !getMainSlag().isEmpty(); }

	public ItemStack getAltSlag(){ return isValidRecipe() ? getDummyRecipe().getAltSlag() : ItemStack.EMPTY; }
	private boolean hasAltSlag(){ return !getAltSlag().isEmpty(); }



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gasifier_cooler";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}



	//----------------------- FUEL-TANK HANDLER -----------------------
	public int getTemperature() {
		return this.temperature;
	}

	public int getTemperatureMax() {
		return 2000;
	}

	public int getCooktimeMax() {
		return hasRefractory() ? 30 : 80;
	}

	public int getCoolingChance() {
		return hasRefractory() ? 60 : 20;
	}

	private int heatingFactor() {
		return hasRefractory() ? 3 : 1;
	}

	private int coolingFactor() {
		return hasRefractory() ? 1 : 3;
	}

	public int powerConsume() {
		return hasRefractory() ? 100 : 300;
	}

	private int chamberStress() {
		return 4000 / speedFactor();
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRefractory(){
		return !casingSlot().isEmpty() && ItemStack.areItemsEqual(casingSlot(), BaseRecipes.refractory_upd);
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.up(), getFacing(), 1, 0);
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public void drainPower() {
		getEngine().powerCount -= powerConsume();
		getEngine().markDirtyClient();
	}

	public int getEnginePower(){
		return hasEngine() ? getEngine().getPower() : 0;
	}

//slurry tank
	public BlockPos slurryPos(){
		return this.pos.offset(EnumFacing.UP, 2);		
	}

	public TEGasifierTank getSlurryTank(){
		TileEntity te = this.world.getTileEntity(slurryPos());
		if(this.world.getBlockState(slurryPos()) != null && te instanceof TEGasifierTank){
			TEGasifierTank tank = (TEGasifierTank)te;
			return tank;
		}
		return null;
	}

	public boolean hasTank(){
		return getSlurryTank() != null;
	}

	public FluidStack getSlurry(){
		return hasTank() ? getSlurryTank().inputTank.getFluid() : null;
	}

	public boolean hasSlurry(){
		return getSlurry() != null;
	}

//burner
	public BlockPos burnerPos(){
		return this.pos.offset(EnumFacing.UP, 1);		
	}

	public TEGasifierBurner getBurner(){
		TileEntity te = this.world.getTileEntity(burnerPos());
		if(this.world.getBlockState(burnerPos()) != null && te instanceof TEGasifierBurner){
			TEGasifierBurner tank = (TEGasifierBurner)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasBurner(){
		return getBurner() != null;
	}

	public boolean hasReactant() {
		return hasBurner() && getBurner().inputTank.getFluid() != null;
	}

	public FluidStack getReactant() {
		return hasReactant() ? getBurner().inputTank.getFluid() : null;
	}

//pressurizer
	public TEGasPressurizer getPressurizer(){
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, this.pos, getFacing(), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasParts(){
		return hasBurner() && getPressurizer() != null;
	}

//particulate
	public TEParticulateCollector getParticulate(){
		TEParticulateCollector vessel = TileStructure.getCollector(this.world, this.pos, isFacingAt(90), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasParticulate(){
		return getParticulate() != null;
	}

//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, isFacingAt(270), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			doPreset();

			if(getTemperature() >= 303 && this.rand.nextInt(getCoolingChance()) == 0){
				this.temperature -= coolingFactor();
				this.markDirtyClient();
			}

			if(getDummyRecipe() == null){
				this.dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}else{
				if(!hasFluids()){
					this.dummyRecipe = null;	
				}
			}

			if(isValidRecipe()){
				if(getTemperature() < getThreashold() + 10 && getEnginePower() >= powerConsume()){
					this.temperature += heatingFactor();
					drainPower();
					this.markDirtyClient();
				}

				if(hasParticulate()){
					getParticulate().handlePreview(hasMainSlag(), getMainSlag(), hasAltSlag(), getAltSlag());
				}
			}

			if(canProcess()){
				this.cooktime++;
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				tickOff();
			}
		}
	}

	private void doPreset() {
		if(hasEngine()){
			if(!getEngine().enablePower){
				getEngine().enablePower = true;
				getEngine().markDirtyClient();
			}
			if(getEngine().enableRedstone){
				getEngine().enableRedstone = false;
				getEngine().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		return isValidRecipe()
			&& getTemperature() >= getThreashold()
			&& hasEnoughSlurry()
			&& hasEnoughReactant()
			&& canStoreOutput();
	}

	private boolean hasEnoughSlurry() {
		return hasSlurry() 
			&& getSlurry().amount >= getDrainSlurry();
	}
	
	private boolean hasEnoughReactant() {
		return hasReactant() 
			&& getReactant().amount >= getDrainReactant();
	}

	private boolean canStoreOutput() {
		return hasOutTank()
			&& this.output.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), getProducedGas());
	}

	private void process() {
		if(getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){
			this.output.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), getProducedGas());
			this.input.drainOrCleanFluid(getBurner().inputTank, getDrainReactant(), true);
			this.input.drainOrCleanFluid(getSlurryTank().inputTank, getDrainSlurry(), true);

			if(hasParticulate()){
				getParticulate().handleParticulate(hasMainSlag(), ModConfig.burner_main_slag, hasAltSlag(), ModConfig.burner_secondary_slag);
			}
			
			if(hasRefractory()){
				if(this.rand.nextInt(chamberStress()) == 0){
					this.upgrade.setStackInSlot(CASING_SLOT, ItemStack.EMPTY);
				}
			}
		}

		this.dummyRecipe = null;
	}


}