package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ElementsCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ElementsCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMetalAlloyerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEMetalAlloyerController extends TileEntityInv implements IInternalServer{

	public static int inputSlots = 1;
	public static int templateSlots = 4;
	public static int upgradeSlots = 1;

	public static final int PATTERN_SLOT = 0;

	public static final int PREVIEW_SLOT = 3;

	public static final int SPEED_SLOT = 0;

	public int currentFile = -1;
	public boolean isRepeatable;
	public MetalAlloyerRecipe dummyRecipe;

	public TEMetalAlloyerController() {
		super(inputSlots, 0, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == PATTERN_SLOT && CoreUtils.hasConsumable(BaseRecipes.ingot_pattern, insertingStack) ){
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



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}

	public ItemStack patternSlot() {
		return this.input.getStackInSlot(PATTERN_SLOT);
	}

	public ItemStack previewSlot(){
		return this.template.getStackInSlot(PREVIEW_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "metal_alloyer";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedAlloyer / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedAlloyer;
	}

	private static int deviceCode() {
		return EnumServer.METAL_ALLOYER.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<MetalAlloyerRecipe> recipeList(){
		return MetalAlloyerRecipes.metal_alloyer_recipes;
	}

	public MetalAlloyerRecipe getRecipeList(int x){
		return recipeList().get(x);
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

	public MetalAlloyerRecipe getCurrentRecipe(){
		if(isActive() && isValidPreset()){
			return isFullRecipe(getRecipeList(getSelectedRecipe()));
		}
		return null;
	}

	public MetalAlloyerRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public MetalAlloyerRecipe isFullRecipe(MetalAlloyerRecipe recipe) {
		int recipeSize = recipe.getInputs().size();
		int ingrCount = 0;
		if(hasElementsCabinet() && hasMaterialCabinet()){
			for(int ingredient = 0; ingredient < recipeSize; ingredient++){
				String ingrOredict = recipe.getInputs().get(ingredient);
				int ingrQuantity = recipe.getQuantities().get(ingredient);
				for(int element = 0; element < elementsList().size(); element++){
					if(getElementsList(element).getOredict().matches(ingrOredict)){
						if(ingrQuantity <= getElementsCabinet().MATERIAL_LIST.get(element).getAmount()){
							ingrCount++;
						}
					}
				}
				for(int element = 0; element < materialList().size(); element++){
					if(getMaterialList(element).getOredict().matches(ingrOredict)){
						if(ingrQuantity <= getMaterialCabinet().MATERIAL_LIST.get(element).getAmount()){
							ingrCount++;
						}
					}
				}
			}
		}
		return ingrCount == recipeSize ? recipe : null;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}

	public ItemStack recipeOutput(){ return isValidRecipe() ? getDummyRecipe().getOutput() : ItemStack.EMPTY; }

	
	
	
	

	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		BlockPos enginePos = this.pos.offset(EnumFacing.UP).offset(getFacing());
		TEPowerGenerator engine = TileStructure.getEngine(this.world, enginePos, getFacing().getOpposite());
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 : false;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().markDirtyClient();
	}

//chamber
	public TEMetalAlloyerTank getChamber(){
		BlockPos chamberPos = this.pos.offset(EnumFacing.UP);
		TileEntity te = this.world.getTileEntity(chamberPos);
		if(this.world.getBlockState(chamberPos) != null && te instanceof TEMetalAlloyerTank){
			TEMetalAlloyerTank tank = (TEMetalAlloyerTank)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasChamber(){
		return getChamber() != null;
	}

//cistern
	public TEFluidCistern getFluidCistern(){
		BlockPos cisternPos = this.pos.offset(isFacingAt(270));
		TEFluidCistern cistern = TileStructure.getFluidCistern(this.world, cisternPos, isFacingAt(90));
		return cistern != null ? cistern : null;
	}

	public boolean hasCistern(){
		return getFluidCistern() != null;
	}

	public boolean hasReactant() {
		return hasCistern() && getFluidCistern().inputTank.getFluid() != null && getFluidCistern().inputTank.getFluid().amount >= 1000;
	}

	public FluidStack getReactant() {
		return hasReactant() ? getFluidCistern().inputTank.getFluid() : null;
	}

//centrifuge
	public TECentrifuge getElementCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, separatorPos().offset(isFacingAt(270)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getMaterialCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, separatorPos().offset(isFacingAt(90)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getMainCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getElementCentrifuge() != null && getMaterialCentrifuge() != null && getMainCentrifuge() != null;
	}
	
//separators
	public BlockPos separatorPos(){
		return this.pos.offset(getFacing(), 2);
	}

	public Block getSeparator(){
		Block separator = TileStructure.getStructure(this.world, separatorPos(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparators(){
		return getSeparator() != null;
	}

//element cabinet
	public TEElementsCabinetBase getElementsCabinet(){
		BlockPos cabinetPos = separatorPos().offset(isFacingAt(270), 2);
		TEElementsCabinetBase cabinet = TileStructure.getElementCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasElementsCabinet(){
		return getElementsCabinet() != null;
	}

	public TEMaterialCabinetBase getMaterialCabinet(){
		BlockPos cabinetPos = separatorPos().offset(isFacingAt(90), 2);
		TEMaterialCabinetBase cabinet = TileStructure.getMaterialCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasMaterialCabinet(){
		return getMaterialCabinet() != null;
	}

//Unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(getFacing(), 3);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos.offset(getFacing(), 4), getFacing().getOpposite());
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return hasChamber() && hasCentrifuges() && hasSeparators();
	}



	//----------------------- CUSTOM -----------------------
	private int getWashReactant() {
		return 1000;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, recipeList().size());

			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canProcess()){
				this.cooktime++;
				drainPower();
				resetOversee(getServer(), this.currentFile);
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				tickOversee(getServer(), this.currentFile);
				this.dummyRecipe = null;
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

	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe()
			&& isAssembled()
			&& hasReactant()
			&& hasFuelPower()
			&& hasPattern()
			&& canOutput()
			&& hasElementsCabinet() && hasMaterialCabinet()
			&& handleServer(getServer(), this.currentFile); //server
	}

	private boolean canOutput() {
		return hasUnloader() && ((MachineStackHandler)getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), recipeOutput());
	}

	private void process() {
		if(isValidRecipe()){
			if(hasUnloader()) {
				((MachineStackHandler)getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, getDummyRecipe().getOutput().copy());
			}

			int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, patternSlot());
			this.input.damageUnbreakingSlot(unbreakingLevel, PATTERN_SLOT);
	
			if(CoreUtils.hasMending(patternSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
				this.input.repairMendingSlot(PATTERN_SLOT);
			}

			if(hasReactant()) {
				this.input.drainOrCleanFluid(getFluidCistern().inputTank, getWashReactant(), true);
				getFluidCistern().updateNeighbours();
			}

			if(hasElementsCabinet() && hasMaterialCabinet()){
				for(int ingredient = 0; ingredient < getDummyRecipe().getInputs().size(); ingredient++){
					String ingrOredict = getDummyRecipe().getInputs().get(ingredient);
					int ingrQuantity = getDummyRecipe().getQuantities().get(ingredient);
					for(int element = 0; element < getElementsCabinet().MATERIAL_LIST.size(); element++){
						if(getElementsCabinet().MATERIAL_LIST.get(element).getOredict().matches(ingrOredict)){
							int storedAmount = getElementsCabinet().MATERIAL_LIST.get(element).getAmount();
							storedAmount -= ingrQuantity;
							ElementsCabinetRecipe currentMaterial = new ElementsCabinetRecipe(getElementsCabinet().MATERIAL_LIST.get(element).getSymbol(), getElementsCabinet().MATERIAL_LIST.get(element).getOredict(), getElementsCabinet().MATERIAL_LIST.get(element).getName(), storedAmount, getElementsCabinet().MATERIAL_LIST.get(element).getExtraction());
							getElementsCabinet().MATERIAL_LIST.set(element, currentMaterial);
						}
					}
					for(int element = 0; element < getMaterialCabinet().MATERIAL_LIST.size(); element++){
						if(getMaterialCabinet().MATERIAL_LIST.get(element).getOredict().matches(ingrOredict)){
							int storedAmount = getMaterialCabinet().MATERIAL_LIST.get(element).getAmount();
							storedAmount -= ingrQuantity;
							MaterialCabinetRecipe currentMaterial = new MaterialCabinetRecipe(getMaterialCabinet().MATERIAL_LIST.get(element).getSymbol(), getMaterialCabinet().MATERIAL_LIST.get(element).getOredict(), getMaterialCabinet().MATERIAL_LIST.get(element).getName(), storedAmount, getMaterialCabinet().MATERIAL_LIST.get(element).getExtraction());
							getMaterialCabinet().MATERIAL_LIST.set(element, currentMaterial);
						}
					}
				}
				getElementsCabinet().markDirtyClient();
				getMaterialCabinet().markDirtyClient();
			}

			updateServer(getServer(), this.currentFile);
		}
		this.dummyRecipe = null;
	}

	private boolean hasPattern() {
		return CoreUtils.hasConsumable(BaseRecipes.ingot_pattern, this.input.getStackInSlot(PATTERN_SLOT));
	}

	public void showPreview() {
		if(isValidPreset()){
			ItemStack previewStack = getRecipeList(getSelectedRecipe()).getOutput();
			if(!this.template.getStackInSlot(PREVIEW_SLOT).isItemEqual(previewStack)){
				previewStack.setCount(1);
				this.template.setStackInSlot(PREVIEW_SLOT, previewStack);
			}
		}else{
			if(!this.template.getStackInSlot(PREVIEW_SLOT).isEmpty()){
				this.template.setStackInSlot(PREVIEW_SLOT, ItemStack.EMPTY);
			}
		}
	}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < getServer().FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x);
				if(TEServer.isValidFile(fileSlot)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isWrittenFile(tag)){
							if(isCorrectDevice(tag, deviceCode())){
								if(getRecipe(tag) < recipeList().size()){
									if(getDone(tag) > 0){
										if(this.recipeIndex != getRecipe(tag)){
											this.recipeIndex = getRecipe(tag);
											this.showPreview();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
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