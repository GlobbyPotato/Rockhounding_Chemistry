package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ShakingTableRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ShakingTableRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEShredderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEShredderTable;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TEShredderController extends TileEntityInv{
	public static final int SPEED_SLOT = 0;

	public static int inputSlots = 1;
	public static int templateSlots = 3;
	public static int upgradeSlots = 1;

	public ArrayList<ItemStack> resultList = new ArrayList<ItemStack>();

	public int comminution;
	public ItemStack filter = ItemStack.EMPTY;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TEShredderController() {
		super(inputSlots, 0, templateSlots, upgradeSlots);
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isValidInput(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

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
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "shredder_controller";
	}

	public int baseSpeed(){
		return ModConfig.speedShredder;
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax(){
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? baseSpeed() / ModUtils.speedUpgrade(speedSlot()): baseSpeed();
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.DOWN).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return isFacingAt(270);
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<ShakingTableRecipe> recipeList(){
		return ShakingTableRecipes.shaking_table_recipes;
	}
	public ShakingTableRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public ArrayList<MaterialCabinetRecipe> materialList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}
	public MaterialCabinetRecipe getMaterialList(int x){
		return materialList().get(x);
	}

	public ArrayList<String> inhibitedList(){
		return MaterialCabinetRecipes.inhibited_material;
	}

	boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			for(ShakingTableRecipe recipe: recipeList()){
				if(recipe.getType()){
					ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
					if(!inputOreIDs.isEmpty()){
						if(inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict()))){
							return true;
						}
					}
				}else{
					if(recipe.getInput().isItemEqual(stack)){
						return true;
					}
				}
			}
		}
		return false;
	}

	public ShakingTableRecipe getCurrentRecipe(){
		if(!inputSlot().isEmpty()){
			for(int x = 0; x < recipeList().size(); x++){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
				if(getRecipeList(x).getType()){
					if(inputOreIDs.contains(OreDictionary.getOreID(getRecipeList(x).getOredict()))){
						return getRecipeList(x);
					}
				}else{
					if(getRecipeList(x).getInput().isItemEqual(inputSlot())){
						return getRecipeList(x);
					}
				}
			}
		}
		return null;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public ArrayList<String> recipeOutput(){
		return isValidRecipe() ? getCurrentRecipe().getElements() : null;
	}

	public ArrayList<Integer> recipeQuantities(){
		return isValidRecipe() ? getCurrentRecipe().getQuantities() : null;
	}

	public ItemStack recipeSlag(){ 
		return isValidRecipe() ? getCurrentRecipe().getSlag() : ItemStack.EMPTY; 
	}

	public FluidStack recipePulp(){
		return getCurrentRecipe().getLeachate();
	}



	//----------------------- STRUCTURE -----------------------
//engine
	BlockPos startPos() {
		return this.pos.offset(EnumFacing.DOWN);
	}

	public TEPowerGenerator getEngine(){
		BlockPos enginePos = startPos().offset(getFacing().getOpposite());
		TEPowerGenerator engine = TileStructure.getEngine(this.world, enginePos, getFacing());
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() >= getCooktimeMax() : false;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().markDirtyClient();
	}

//transmission
	public TEShredderBase getTransmission(){
		TileEntity te = this.world.getTileEntity(startPos());
		if(this.world.getBlockState(startPos()) != null && te instanceof TEShredderBase){
			TEShredderBase engine = (TEShredderBase)te;
			if(engine.getFacing() == engine.getFacing()){
				return engine;
			}
		}
		return null;
	}

	public boolean hasTransmission(){
		return getTransmission() != null;
	}

//tables
	public TEShredderTable getTable1(){
		TEShredderTable crusher = TileStructure.getShaker(this.world, startPos().offset(getFacing(), 1), getFacing());
		return crusher != null ? crusher : null;
	}

	public TEShredderTable getTable2(){
		TEShredderTable crusher = TileStructure.getShaker(this.world, startPos().offset(getFacing(), 2), getFacing());
		return crusher != null ? crusher : null;
	}

	public TEShredderTable getTable3(){
		TEShredderTable crusher = TileStructure.getShaker(this.world, startPos().offset(getFacing(), 3), getFacing());
		return crusher != null ? crusher : null;
	}

	public boolean hasTables(){
		return getTable1() != null && getTable2() != null && getTable3() != null;
	}

//washer 
	public BlockPos washerPos() {
		return startPos().offset(getFacing(), 4);
	}

	public TEWashingTank getWasher(){
		TEWashingTank basin = TileStructure.getWasher(this.world, washerPos());
		return basin != null ? basin : null;
	}

	public boolean hasWaste(){
		return hasWasher()
			&& this.input.canSetOrFillFluid(getWasher().inputTank, getWasher().getTankFluid(), recipePulp());
	}

	public boolean hasWasher(){
		return getWasher() != null;
	}

//centrifuge
	public TECentrifuge getMaterialCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, washerPos().offset(isFacingAt(90)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getMaterialCentrifuge() != null;
	}

//material cabinet
	public TEMaterialCabinetBase getMaterialCabinet(){
		BlockPos cabinetPos = washerPos().offset(isFacingAt(90), 2);
		TEMaterialCabinetBase cabinet = TileStructure.getMaterialCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasMaterialCabinet(){
		return getMaterialCabinet() != null;
	}

//cistern
	public TEFluidCistern getFluidCistern(){
		BlockPos cisternPos = startPos().offset(isFacingAt(90));
		TEFluidCistern cistern = TileStructure.getFluidCistern(this.world, cisternPos, isFacingAt(270));
		return cistern != null ? cistern : null;
	}

	public boolean hasCistern(){
		return getFluidCistern() != null;
	}

	public boolean hasWater() {
		return hasCistern() && getFluidCistern().inputTank.getFluid() != null && getFluidCistern().inputTank.getFluid().amount >= 1000;
	}

	public FluidStack getReactant() {
		return hasWater() ? getFluidCistern().inputTank.getFluid() : null;
	}


//unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = washerPos().offset(isFacingAt(270));
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, isFacingAt(90));
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//pressurizer
	public TEAuxiliaryEngine getPressurizer1(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, startPos().offset(getFacing(), 5), getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer2(){
		BlockPos enginePos = startPos().offset(getFacing(), 4).offset(isFacingAt(270), 2);
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, enginePos, isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getPressurizer2() != null && getPressurizer2() != null;
	}

	private boolean isAssembled() {
		return hasCistern() && hasPressurizers() && hasWasher() && hasCentrifuges() && hasMaterialCabinet() && hasTransmission() && hasUnloader();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			doPreset();

			if(isActive()){
				if(canProcess()){
					this.cooktime++;
					drainPower();
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
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
		
		if(hasCistern()){
			if(getFluidCistern().getFilter() != CoreBasics.waterStack(1000)){
				getFluidCistern().filter = CoreBasics.waterStack(1000);
			}
		}

		if(rand.nextInt(20) == 0) {
			switchWater();
		}

	}

	private boolean canProcess() {
		return hasFuelPower()
			&& isAssembled()
			&& isValidRecipe()
			&& hasWaste()
			&& hasWater()
			&& canOutput();
	}

	private boolean canOutput() {
		return hasUnloader()
			&& ((MachineStackHandler) getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), getCurrentRecipe().getSlag());
	}

	public void process() {
		if(isValidRecipe()) {
			if(hasUnloader()) {
				((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, recipeSlag());
			}

			if(hasWasher()){
				this.output.setOrFillFluid(getWasher().inputTank, recipePulp());
				getWasher().updateNeighbours();
			}
			
			if(hasMaterialCabinet()) {
				if(getCurrentRecipe().getElements().size() > 0) {
					for(int x = 0; x < getCurrentRecipe().getElements().size(); x++){
						int formulaValue = getCurrentRecipe().getQuantities().get(x);
						for(int y = 0; y < materialList().size(); y++){
							String recipeDust = getCurrentRecipe().getElements().get(x);
							if(recipeDust.contains(materialList().get(y).getOredict())){
								boolean isInhibited = false;
								for(int ix = 0; ix < inhibitedList().size(); ix++){
									if(recipeDust.toLowerCase().matches(inhibitedList().get(ix).toLowerCase())){
										isInhibited = true;
									}
								}
								if(!isInhibited){
									int storedAmount = getMaterialCabinet().MATERIAL_LIST.get(y).getAmount();
									if(storedAmount <= ModConfig.extractorCap - formulaValue) {
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
			}
			
			if(hasWater()){
				this.input.drainOrCleanFluid(getFluidCistern().inputTank, 1000, true);
				getFluidCistern().updateNeighbours();
			}

			this.input.decrementSlot(INPUT_SLOT);
		}
	}



	public void switchWater() {
		if(hasTables()) {
			if(getTable1().activation != activation) {
				getTable1().activation = activation; getTable1().markDirtyClient();
			}
			if(getTable2().activation != activation) {
				getTable2().activation = activation; getTable2().markDirtyClient();
			}
			if(getTable3().activation != activation) {
				getTable3().activation = activation; getTable3().markDirtyClient();
			}
		}else {
			if(getTable1() != null) {
				if(getTable1().isActive()) {
					getTable1().activation = false; getTable1().markDirtyClient();
				}
			}
			if(getTable2() != null) {
				if(getTable2().isActive()) {
					getTable2().activation = false; getTable2().markDirtyClient();
				}
			}
			if(getTable3() != null) {
				if(getTable3().isActive()) {
					getTable3().activation = false; getTable3().markDirtyClient();
				}
			}
		}
	}

}