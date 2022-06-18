package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ElementsCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ElementsCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorGlassware;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class TEExtractorController extends TileEntityInv implements IInternalServer{

	public static final int SPEED_SLOT = 0;

	public static int inputSlots = 1;
	public static int templateSlots = 3;
	public static int upgradeSlots = 1;

	public int intensity = 8;
	public ItemStack filter = ItemStack.EMPTY;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public ChemicalExtractorRecipe dummyRecipe;

	public TEExtractorController() {
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
		this.intensity = compound.getInteger("Intensity");
		if(compound.hasKey("Filter")){
			this.filter = new ItemStack(compound.getCompoundTag("Filter"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Intensity", getIntensity());
		if(!this.getFilter().isEmpty()){
			NBTTagCompound filterstack = new NBTTagCompound(); 
			this.filter.writeToNBT(filterstack);
			compound.setTag("Filter", filterstack);
		}
		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack speedSlot(){
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "extractor_controller";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax(){
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedAlloyer / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedAlloyer;
	}

	private static int deviceCode() {
		return EnumServer.EXTRACTOR.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<ChemicalExtractorRecipe> recipeList(){
		return ChemicalExtractorRecipes.extractor_recipes;
	}
	public ChemicalExtractorRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public ArrayList<String> inhibitedElements(){
		return ElementsCabinetRecipes.inhibited_elements;
	}
	public ArrayList<String> inhibitedMaterial(){
		return MaterialCabinetRecipes.inhibited_material;
	}

	public ArrayList<ElementsCabinetRecipe> elementsList(){
		return ElementsCabinetRecipes.elements_cabinet_recipes;
	}
	public ElementsCabinetRecipe getElementsList(int x){
		return elementsList().get(x);
	}

	public ArrayList<MaterialCabinetRecipe> materialList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}
	public MaterialCabinetRecipe getMaterialList(int x){
		return materialList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			if(!getFilter().isEmpty()){
				if(getFilter().isItemEqual(stack)){
					return true;
				}
			}else{
				for(ChemicalExtractorRecipe recipe: recipeList()){
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
		}
		return false;
	}

	public ChemicalExtractorRecipe getCurrentRecipe(){
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

	public ChemicalExtractorRecipe getDummyRecipe(){
		return dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public ArrayList<String> recipeOutput(){
		return isValidRecipe() ? getDummyRecipe().getElements() : null;
	}

	public ArrayList<Integer> recipeQuantities(){
		return isValidRecipe() ? getDummyRecipe().getQuantities() : null;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(isFacingAt(90)), isFacingAt(270));
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 && getEngine().getRedstone() >= powerConsume(): false;
	}

//reactor
	public TEExtractorReactor getReactor(){
		BlockPos reactorPos = this.pos.offset(getFacing(), 1);
		TileEntity te = this.world.getTileEntity(reactorPos);
		if(this.world.getBlockState(reactorPos) != null && te instanceof TEExtractorReactor){
			TEExtractorReactor reactor = (TEExtractorReactor)te;
			if(reactor.getFacing() == getFacing().getOpposite()){
				return reactor;
			}
		}
		return null;
	}

	public boolean hasReactor(){
		return getReactor() != null;
	}

//charger
	public Block getCharger(){
		BlockPos chargerPos= this.pos.offset(getFacing(), 1).offset(EnumFacing.UP);
		Block charger = TileStructure.getStructure(this.world, chargerPos, EnumMiscBlocksA.EXTRACTOR_CHARGER.ordinal());
		return charger != null ? charger : null;
	}

	public boolean hasCharger(){
		return getCharger() != null;
	}

//separators
	public BlockPos separatorPos1(){
		return this.pos.offset(getFacing(), 2);
	}

	public BlockPos separatorPos2(){
		return this.pos.offset(getFacing(), 3);
	}

	public Block getSeparator(){
		Block separator = TileStructure.getStructure(this.world, separatorPos1(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public Block getSeparator2(){
		Block separator = TileStructure.getStructure(this.world, separatorPos2(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparators(){
		return getSeparator() != null && getSeparator2() != null;
	}

//glassware holder
	public TEExtractorGlassware getInjector(){
		BlockPos injectorPos = separatorPos2().offset(isFacingAt(270), 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEExtractorGlassware){
			TEExtractorGlassware injector = (TEExtractorGlassware)te;
			if(injector.getFacing() == isFacingAt(90)){
				return injector;
			}
		}
		return null;
	}

	public boolean hasInjector(){
		return getInjector() != null;
	}

//stabilizer
	public TEExtractorStabilizer getStabilizer(){
		BlockPos injectorPos = separatorPos2().offset(isFacingAt(90), 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEExtractorStabilizer){
			TEExtractorStabilizer stabilizer = (TEExtractorStabilizer)te;
			if(stabilizer.getFacing() == isFacingAt(270)){
				return stabilizer;
			}
		}
		return null;
	}

	public boolean hasStabilizer(){
		return getStabilizer() != null;
	}

//centrifuge
	public TECentrifuge getElementCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, separatorPos1().offset(isFacingAt(270)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getMaterialCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, separatorPos1().offset(isFacingAt(90)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getElementCentrifuge() != null && getMaterialCentrifuge() != null;
	}
	
//element cabinet
	public TEElementsCabinetBase getElementsCabinet(){
		BlockPos cabinetPos = separatorPos1().offset(isFacingAt(270), 2);
		TEElementsCabinetBase cabinet = TileStructure.getElementCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasElementsCabinet(){
		return getElementsCabinet() != null;
	}

	public TEMaterialCabinetBase getMaterialCabinet(){
		BlockPos cabinetPos = separatorPos1().offset(isFacingAt(90), 2);
		TEMaterialCabinetBase cabinet = TileStructure.getMaterialCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasMaterialCabinet(){
		return getMaterialCabinet() != null;
	}

	private boolean isAssembled() {
		return hasSeparators() && hasPressurizer() && hasElementsCabinet() && hasMaterialCabinet() && hasReactor() && hasCharger() && hasCentrifuges() && hasFluidRouter() && hasStabilizer() && hasUnloader() && hasInjector();
	}

//Unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(getFacing(), 4);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//fluid router
	public boolean hasFluidRouter(){
		return TileStructure.getFluidRouter(world, this.pos.offset(getFacing(), 5), getFacing());
	}

//flotation tanks
	public TEFlotationTank getFlotationTank1(){
		BlockPos flotationPos = pos.offset(getFacing(), 5).offset(EnumFacing.UP);
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, flotationPos);
		return tank != null ? tank : null;
	}

	public TEFlotationTank getFlotationTank2(){
		BlockPos flotationPos = pos.offset(getFacing(), 6).offset(EnumFacing.UP);
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, flotationPos);
		return tank != null ? tank : null;
	}

	public boolean hasFlotationTank1(){
		return getFlotationTank1() != null;
	}

	public boolean hasFlotationTank2(){
		return getFlotationTank2() != null;
	}

	public boolean hasFluids() {
		return (hasFlotationTank1() && this.input.canDrainFluid(getFlotationTank1().inputTank.getFluid(), nitricAcid(), calculatedNitr())) 
			&& (hasFlotationTank2() && this.input.canDrainFluid(getFlotationTank2().inputTank.getFluid(), cyanideAcid(), calculatedCyan()));
	}

//pressurizer
	public TEAuxiliaryEngine getPressurizer(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing(), 7), getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizer(){
		return getPressurizer() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(90));
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- CUSTOM -----------------------
	public int getIntensity(){
		return this.intensity;
	}

	public ItemStack getFilter() {
		return this.filter;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().redstoneCount -= powerConsume();
		getEngine().markDirtyClient();
	}

	public int powerConsume() {
		int baseConsume = (10 * ModConfig.basePower) * getIntensity();
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public FluidStack nitricAcid() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID), 1000);
	}

	public int calculatedNitr() {
		return getIntensity() > 0 ? getIntensity() * ModConfig.consumedNitr : 1;
	}

	public FluidStack cyanideAcid() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE), 1000);
	}

	public int calculatedCyan() {
		return getIntensity() > 0 ? getIntensity() * ModConfig.consumedCyan : 1;
	}

	public int baseRecovery(){
		return 20;
	}

	public int calculatedRecombination(int amount){
		return (amount * recoveryFactor()) / 100;
	}

	public int recoveryFactor(){
		return 100 - (stabilizerNum() * getIntensity());
	}

	public int stabilizerNum(){
		return hasStabilizer() ? 6 - getStabilizer().getBusySlots() : 6;
	}

	public int calculatedDust(int amount){
		return (amount * normalRecovery()) / 100;
	}

	public int normalRecovery() {
		return getIntensity() * 6;
	}

	public int recombinationChance() {
		return this.rand.nextInt(18 - getIntensity());
	}

	public int dissolutionChance(){
		return (getIntensity() * 100) / 18;
	}

	private int getDurabilityModifier(int cat){
		return EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, getStabilizer().catSlot(cat));
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			handlePurge();

			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, 16);

			if(inputSlot().isEmpty() && getDummyRecipe() != null){
				dummyRecipe = null;
				this.cooktime = 0;
			}

			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canProcess()){
				this.cooktime++;
				drainPower();
				if(getCooktime() >= getCooktimeMax()) {
					process();
					this.cooktime = 0;
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
			if(!getEngine().enableRedstone){
				getEngine().enableRedstone = true;
				getEngine().markDirtyClient();
			}
		}
		if(hasFlotationTank1()){
			if(getFlotationTank1().getFilterSolvent() != nitricAcid()){
				getFlotationTank1().filterSolvent = nitricAcid();
			}
			getFlotationTank1().filterManualSolvent = null;
			getFlotationTank1().isFiltered = true;
		}
		if(hasFlotationTank2()){
			if(getFlotationTank2().getFilterSolvent() != cyanideAcid()){
				getFlotationTank2().filterSolvent = cyanideAcid();
			}
			getFlotationTank2().filterManualSolvent = null;
			getFlotationTank2().isFiltered = true;
		}

		if(hasServer() && !getServer().isActive()){
			this.filter = ItemStack.EMPTY;
		}
	}

	private void handlePurge() {
		if(isActive() && hasUnloader()){
			if(!this.inputSlot().isEmpty() && !this.getFilter().isEmpty() && !CoreUtils.isMatchingIngredient(inputSlot(), getFilter())){
				if(((MachineStackHandler) getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), inputSlot())){
					((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, inputSlot());
					this.input.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
				}
			}
		}
	}

	private boolean canProcess() {
		return isActive()
			&& getDummyRecipe() != null
			&& isAssembled()
			&& hasFuelPower()
			&& hasFluids()
			&& handleFilter(inputSlot(), getFilter()) //server
			&& handleServer(getServer(), this.currentFile); //server
	}

	private void process() {
		if(getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){
			for(int x = 0; x < getDummyRecipe().getElements().size(); x++){
				int formulaValue = getDummyRecipe().getQuantities().get(x);
				
				for(int y = 0; y < elementsList().size(); y++){
					String recipeDust = getDummyRecipe().getElements().get(x);
					if(recipeDust.contains(elementsList().get(y).getOredict())){
						boolean isInhibited = false;
						for(int ix = 0; ix < inhibitedElements().size(); ix++){
							if(recipeDust.toLowerCase().matches(inhibitedElements().get(ix).toLowerCase())){
								isInhibited = true;
							}
						}
						if(!isInhibited){
							if(hasElementsCabinet()){
								int storedAmount = getElementsCabinet().MATERIAL_LIST.get(y).getAmount();
								if(formulaValue > baseRecovery()){
									if(recombinationChance() == 0){
										storedAmount += baseRecovery() + calculatedRecombination(formulaValue - baseRecovery());
										ElementsCabinetRecipe currentMaterial = new ElementsCabinetRecipe(getElementsCabinet().MATERIAL_LIST.get(y).getSymbol(), getElementsCabinet().MATERIAL_LIST.get(y).getOredict(), getElementsCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getElementsCabinet().MATERIAL_LIST.get(y).getExtraction());
										getElementsCabinet().MATERIAL_LIST.set(y, currentMaterial);

										handleCatalystDamage();
									}else{
										storedAmount += baseRecovery() + calculatedDust(formulaValue - baseRecovery());
										ElementsCabinetRecipe currentMaterial = new ElementsCabinetRecipe(getElementsCabinet().MATERIAL_LIST.get(y).getSymbol(), getElementsCabinet().MATERIAL_LIST.get(y).getOredict(), getElementsCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getElementsCabinet().MATERIAL_LIST.get(y).getExtraction());
										getElementsCabinet().MATERIAL_LIST.set(y, currentMaterial);
									}
								}else{
									storedAmount += formulaValue;
									ElementsCabinetRecipe currentMaterial = new ElementsCabinetRecipe(getElementsCabinet().MATERIAL_LIST.get(y).getSymbol(), getElementsCabinet().MATERIAL_LIST.get(y).getOredict(), getElementsCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getElementsCabinet().MATERIAL_LIST.get(y).getExtraction());
									getElementsCabinet().MATERIAL_LIST.set(y, currentMaterial);
								}
								getElementsCabinet().markDirtyClient();
							}
						}
					}
				}
				for(int y = 0; y < materialList().size(); y++){
					String recipeDust = getDummyRecipe().getElements().get(x);
					if(recipeDust.contains(materialList().get(y).getOredict())){
						boolean isInhibited = false;
						for(int ix = 0; ix < inhibitedElements().size(); ix++){
							if(recipeDust.toLowerCase().matches(inhibitedElements().get(ix).toLowerCase())){
								isInhibited = true;
							}
						}
						if(!isInhibited){
							if(hasMaterialCabinet()){
								int storedAmount = getMaterialCabinet().MATERIAL_LIST.get(y).getAmount();
								if(formulaValue > baseRecovery()){
									if(recombinationChance() == 0){
										storedAmount += baseRecovery() + calculatedRecombination(formulaValue - baseRecovery());
										MaterialCabinetRecipe currentMaterial = new MaterialCabinetRecipe(getMaterialCabinet().MATERIAL_LIST.get(y).getSymbol(), getMaterialCabinet().MATERIAL_LIST.get(y).getOredict(), getMaterialCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getMaterialCabinet().MATERIAL_LIST.get(y).getExtraction());
										getMaterialCabinet().MATERIAL_LIST.set(y, currentMaterial);

										handleCatalystDamage();
									}else{
										storedAmount += baseRecovery() + calculatedDust(formulaValue - baseRecovery());
										MaterialCabinetRecipe currentMaterial = new MaterialCabinetRecipe(getMaterialCabinet().MATERIAL_LIST.get(y).getSymbol(), getMaterialCabinet().MATERIAL_LIST.get(y).getOredict(), getMaterialCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getMaterialCabinet().MATERIAL_LIST.get(y).getExtraction());
										getMaterialCabinet().MATERIAL_LIST.set(y, currentMaterial);
									}
								}else{
									storedAmount += formulaValue;
									MaterialCabinetRecipe currentMaterial = new MaterialCabinetRecipe(getMaterialCabinet().MATERIAL_LIST.get(y).getSymbol(), getMaterialCabinet().MATERIAL_LIST.get(y).getOredict(), getMaterialCabinet().MATERIAL_LIST.get(y).getName(), storedAmount, getMaterialCabinet().MATERIAL_LIST.get(y).getExtraction());
									getMaterialCabinet().MATERIAL_LIST.set(y, currentMaterial);
								}
								getMaterialCabinet().markDirtyClient();
							}
						}
					}
				}
			}

			if(hasFlotationTank1()) {
				if(getFlotationTank1().hasSolventFluid() && getFlotationTank1().getSolventAmount() >= calculatedNitr() ){
					this.output.drainOrCleanFluid(getFlotationTank1().inputTank, calculatedNitr(), true);
				}
			}
			if(hasFlotationTank2()) {
				if(getFlotationTank2().hasSolventFluid() && getFlotationTank2().getSolventAmount() >= calculatedCyan() ){
					this.output.drainOrCleanFluid(getFlotationTank2().inputTank, calculatedCyan(), true);
				}
			}

			this.input.decrementSlot(INPUT_SLOT);
	
			updateServer(getServer(), this.currentFile);
		}

		this.dummyRecipe = null;
	}

	private void handleCatalystDamage() {
		if(isValidRecipe() && hasStabilizer()){
			for(int x = 0; x < getStabilizer().SLOT_INPUTS.length; x++){
				if(!getStabilizer().catSlot(x).isEmpty()){
					damageOrRepairConsumable(x);
				}
			}
		}
	}

	private void damageOrRepairConsumable(int cats) {
		int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getStabilizer().catSlot(cats));
		((MachineStackHandler)getStabilizer().getInput()).damageUnbreakingSlot(unbreakingLevel, cats);

		if(CoreUtils.hasMending(getStabilizer().catSlot(cats)) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
			((MachineStackHandler)getStabilizer().getInput()).repairMendingSlot(cats);
		}
	}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < getServer().FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(getServer().isValidFile(fileSlot)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isWrittenFile(tag)){
							if(isCorrectDevice(tag, deviceCode())){
								if(getRecipe(tag) < 16){
									if(getDone(tag) > 0){
										if(this.intensity != 1 + (getRecipe(tag))){
											this.intensity = 1 + (getRecipe(tag));
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										if(hasFilterItem(tag)){
											ItemStack temp = getFilterItem(tag);
											if(this.getFilter().isEmpty() || !this.getFilter().isItemEqual(temp)){
												this.filter = temp;
											}
										}
										break;
									}
								}
							}
						}
					}
				}
				if(x == getServer().FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}
}