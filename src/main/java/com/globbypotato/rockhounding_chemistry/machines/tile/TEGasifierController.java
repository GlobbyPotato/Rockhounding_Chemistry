package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGasifierBurner;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEReinforcedCistern;
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

public class TEGasifierController extends TileEntityInv {

	public static int templateSlots = 1;
	public static int upgradeSlots = 2;
	public static final int SPEED_SLOT = 0;
	public static final int CASING_SLOT = 1;

	public int temperature = 300;

	public GasifierPlantRecipe dummyRecipe;

	ItemStack refractory_upd = new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.REFRACTORY_CASING.ordinal());

	public TEGasifierController() {
		super(0, 0, templateSlots, upgradeSlots);

		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CASING_SLOT && (ItemStack.areItemsEqual(insertingStack, refractory_upd)) ){
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

	public ArrayList<String> inhibitedList(){
		return MaterialCabinetRecipes.inhibited_material;
	}

	public ArrayList<MaterialCabinetRecipe> materialList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}
	public MaterialCabinetRecipe getMaterialList(int x){
		return materialList().get(x);
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

	public ArrayList<String> recipeOutput(){
		return isValidRecipe() ? getDummyRecipe().getElements() : null;
	}

	public ArrayList<Integer> recipeQuantities(){
		return isValidRecipe() ? getDummyRecipe().getQuantities() : null;
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gasifier_controller";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
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
		return !casingSlot().isEmpty() && ItemStack.areItemsEqual(casingSlot(), refractory_upd);
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		BlockPos enginePos = this.pos.up().offset(getFacing());
		TEPowerGenerator engine = TileStructure.getEngine(this.world, enginePos, getFacing().getOpposite());
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

//burner
	public TEGasifierBurner getBurner(){
		BlockPos burnerPos = this.pos.offset(EnumFacing.UP);		
		TileEntity te = this.world.getTileEntity(burnerPos);
		if(this.world.getBlockState(burnerPos) != null && te instanceof TEGasifierBurner){
			TEGasifierBurner tank = (TEGasifierBurner)te;
			return tank;
		}
		return null;
	}

	public boolean hasBurner(){
		return getBurner() != null;
	}

//slurry tank
	public TEReinforcedCistern getSlurryTank(){
		BlockPos slurryPos = this.pos.offset(EnumFacing.UP, 2);		
		TileEntity te = this.world.getTileEntity(slurryPos);
		if(this.world.getBlockState(slurryPos) != null && te instanceof TEReinforcedCistern){
			TEReinforcedCistern tank = (TEReinforcedCistern)te;
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

//cistern
	public TEFluidCistern getFluidCistern(){
		BlockPos cisternPos = this.pos.offset(isFacingAt(90)).offset(EnumFacing.UP, 1);
		TEFluidCistern cistern = TileStructure.getFluidCistern(this.world, cisternPos, isFacingAt(270));
		return cistern != null ? cistern : null;
	}

	public boolean hasCistern(){
		return getFluidCistern() != null;
	}

	public boolean hasReactant() {
		return hasCistern() && getFluidCistern().inputTank.getFluid() != null;
	}

	public FluidStack getReactant() {
		return hasReactant() ? getFluidCistern().inputTank.getFluid() : null;
	}

//pressurizer
	public TEAuxiliaryEngine getPressurizer(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizer(){
		return getPressurizer() != null;
	}

// centrifuge
	public TECentrifuge getOutputCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getMaterialCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(90)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getOutputCentrifuge() != null && getMaterialCentrifuge() != null;
	}

//cabinet
	public TEMaterialCabinetBase getMaterialCabinet(){
		BlockPos cabinetPos = this.pos.offset(isFacingAt(90), 2);
		TEMaterialCabinetBase cabinet = TileStructure.getMaterialCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasMaterialCabinet(){
		return getMaterialCabinet() != null;
	}

//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(isFacingAt(270), 2), isFacingAt(270));
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}

	private boolean isAssembled() {
		return hasPressurizer() && hasCentrifuges() && hasBurner();
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

			if(isActive()){
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
			&& isAssembled()
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
			this.input.drainOrCleanFluid(getFluidCistern().inputTank, getDrainReactant(), true);
			this.input.drainOrCleanFluid(getSlurryTank().inputTank, getDrainSlurry(), true);

			if(hasMaterialCabinet()){
				if(getDummyRecipe().getElements().size() > 0) {
					for(int x = 0; x < getDummyRecipe().getElements().size(); x++){
						int formulaValue = getDummyRecipe().getQuantities().get(x);
						for(int y = 0; y < materialList().size(); y++){
							String recipeDust = getDummyRecipe().getElements().get(x);
							if(recipeDust.contains(materialList().get(y).getOredict())){
								boolean isInhibited = false;
								for(int ix = 0; ix < inhibitedList().size(); ix++){
									if(recipeDust.toLowerCase().matches(inhibitedList().get(ix).toLowerCase())){
										isInhibited = true;
									}
								}
								if(!isInhibited){
									int storedAmount = getMaterialCabinet().MATERIAL_LIST.get(y).getAmount();
									storedAmount += formulaValue;
									MaterialCabinetRecipe currentMaterial = new MaterialCabinetRecipe(getMaterialCabinet().MATERIAL_LIST.get(y).getSymbol(), getMaterialCabinet().MATERIAL_LIST.get(y).getOredict(), getMaterialCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getMaterialCabinet().MATERIAL_LIST.get(y).getExtraction());
									getMaterialCabinet().MATERIAL_LIST.set(y, currentMaterial);
									getMaterialCabinet().markDirtyClient();
								}
							}
						}
					}
				}
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