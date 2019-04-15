package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.HeatExchangerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.HeatExchangerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEHeatExchangerBase extends TileEntityInv {

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public HeatExchangerRecipe dummyRecipe;
	
	public TEHeatExchangerBase() {
		super(0, 0, templateSlots, upgradeSlots);

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
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);

	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
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
		return getRecipeList(x).getInput() != null && hasInTank() && getRecipeList(x).getInput().isFluidEqual(getInTank().inputTank.getFluid());
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

	public int powerConsume() {
		int baseConsume = (coolantTemperature() / 5) * ModConfig.basePower;
		int moddedConsume = ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
		return hasTank() ? moddedConsume : baseConsume;
	}

	public int coolantTemperature(){
		return hasTank() ? getTank().inputTank.getFluid().getFluid().getTemperature() : 301;
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
//input tank
	public TEFlotationTank getTank(){
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, this.pos, EnumFacing.UP, 2);
		return tank != null ? tank : null;
	}

	public boolean hasTank(){
		return getTank() != null && getTank().inputTank.getFluid() != null;
	}

//pressurizer
	public TEGasPressurizer getPressurizer(){
		BlockPos chillerPos = this.pos.offset(EnumFacing.UP, 1);
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, chillerPos, isFacingAt(90), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizer(){
		return getPressurizer() != null;
	}

//in tank
	public TileVessel getInTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, getFacing(), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasInTank(){
		return getInTank() != null;
	}

	public boolean hasCooling(){
		return getInTank() != null && getInTank().inputTank.getFluid() != null;
	}

//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, getFacing(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}

//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, isFacingAt(90), 1, 0);
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
			&& hasPressurizer()
			&& hasRedstonePower()
			&& hasInputGas()
			&& canStoreGas()
			&& canCool();
	}

	private boolean hasInputGas() {
		return hasInTank()
			&& this.input.canDrainFluid(getInTank().inputTank.getFluid(), getRecipeInput(), processRate())
			&& getRecipeInput().getFluid().isGaseous();
	}

	private boolean hasEnoughCoolant() {
		return hasTank() 
			&& isValidCoolant()
			&& getTank().inputTank.getFluidAmount() >= minCoolant();
	}

	private boolean canStoreGas() {
		return hasOutTank() 
			&& this.output.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), processRate())
			&& getRecipeOutput().getFluid().isGaseous();
	}

	public boolean canCool(){
		return isValidRecipe() && getRecipeOutput().getFluid().getTemperature() < getRecipeInput().getFluid().getTemperature();
	}

	private void coolAir() {
		if(isValidRecipe() && getDummyRecipe() == getCurrentRecipe()){
			this.output.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), processRate());
			this.input.drainOrCleanFluid(getInTank().inputTank, processRate(), true);
			if(hasTank() && this.rand.nextInt(consumeChance()) == 0){
				getTank().inputTank.drainInternal(minCoolant(), true);
			}
		}
		this.dummyRecipe = null;
	}

}