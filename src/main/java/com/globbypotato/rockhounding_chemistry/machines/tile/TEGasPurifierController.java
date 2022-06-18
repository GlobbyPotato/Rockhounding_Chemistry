package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasPurifierRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEGasPurifierController extends TileEntityInv {

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public GasPurifierRecipe dummyRecipe;

	public TEGasPurifierController() {
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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        return compound;
	}



	// ----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<GasPurifierRecipe> recipeList(){
		return GasPurifierRecipes.gas_purifier_recipes;
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

	public static GasPurifierRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public GasPurifierRecipe getCurrentRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(isMatchingInput(x) && isRecipeGaseous(x)){
				return getRecipeList(x);
			}
		}
		return null;
	}

	public GasPurifierRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	private boolean isMatchingInput(int x) {
		return getRecipeList(x).getInput() != null && hasInTank() && getInTank().inputTank.getFluid() != null && getRecipeList(x).getInput().isFluidEqual(getInTank().inputTank.getFluid());
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public FluidStack getRecipeInput(){ return isValidRecipe() ? getDummyRecipe().getInput() : null; }
	public FluidStack getRecipeOutput(){ return isValidRecipe() ? getDummyRecipe().getOutput() : null; }

	public ArrayList<String> recipeOutput(){
		return isValidRecipe() ? getDummyRecipe().getElements() : null;
	}

	public ArrayList<Integer> recipeQuantities(){
		return isValidRecipe() ? getDummyRecipe().getQuantities() : null;
	}

	public boolean isRecipeGaseous(int x){
		return getRecipeList(x).getInput().getFluid().isGaseous() 
			&& getRecipeList(x).getOutput().getFluid().isGaseous();
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gas_purifier";
	}

	public int getCooktimeMax(){
		return 30;
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.UP, 3).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return isFacingAt(270);
	}



	//----------------------- CUSTOM -----------------------
	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int cleanRate(){
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? baseRate() * ModUtils.speedUpgrade(speedSlot()): baseRate();
	}

	private int baseRate() {
		return 50;
	}

	public int effectiveRate(){
		if(hasInTank()){
			if(getInTank().inputTank.getFluidAmount() >= cleanRate()){
				return cleanRate();
			}else{
				return getInTank().inputTank.getFluidAmount();
			}
		}
		return 0;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(90));
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

//cyclone separator
	public boolean hasCycloneSeparator(){
		return TileStructure.getCycloneSeparator(this.world, this.pos.offset(EnumFacing.UP), getFacing());
	}

//pressurizer
	public TEAuxiliaryEngine getPressurizer2(){
		BlockPos pressurizerPos = this.pos.offset(getFacing()).offset(isFacingAt(90));
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressurizerPos, isFacingAt(270));
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer3(){
		BlockPos pressurizerPos = this.pos.offset(EnumFacing.UP).offset(getFacing());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressurizerPos, getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getPressurizer2() != null && getPressurizer3() != null;
	}

// centrifuge
	public TECentrifuge getInputCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing(), 2), getFacing().getOpposite());
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getOutputCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing()).offset(isFacingAt(270)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getMaterialCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(90)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getInputCentrifuge() != null && getOutputCentrifuge() != null && getMaterialCentrifuge() != null;
	}

// separator
	public Block getSeparator(){
		Block separator = TileStructure.getStructure(this.world, this.pos.offset(getFacing()), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparator(){
		return getSeparator() != null;
	}

//input vessel
	public TileVessel getInTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing(), 3), getFacing().getOpposite());
		return vessel != null ? vessel : null;
	}

	public boolean hasInTank(){
		return getInTank() != null;
	}

	public boolean hasGas(){
		return hasInTank() && getInTank().inputTank.getFluid() != null;
	}

//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing()).offset(isFacingAt(270),2), isFacingAt(270));
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
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

	private boolean isAssembled() {
		return hasCycloneSeparator() && hasPressurizers() && hasCentrifuges() && hasSeparator();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			doPreset();

			if(isActive()){
				if(getDummyRecipe() == null){
					this.dummyRecipe = getCurrentRecipe();
					this.cooktime = 0;
				}else{
					if(!hasGas()){
						this.dummyRecipe = null;	
					}
				}

				if(canProcess()){
					this.cooktime++;
					drainPower();
					if(getCooktime() >= getCooktimeMax()) {
						process();
						this.cooktime = 0;
					}
					this.markDirtyClient();
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
	}

	private boolean canProcess() {
		return isValidRecipe()
			&& hasFuelPower()
			&& isAssembled()
			&& isMatchingInput()
			&& isMatchingOutput();
	}

	private boolean isMatchingInput() {
		return hasInTank()
			&& this.input.canDrainFluid(getInTank().inputTank.getFluid(), getRecipeInput(), effectiveRate());
	}

	private boolean isMatchingOutput() {
		return hasOutTank()
			&& this.output.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), effectiveRate());
	}

	private void process() {
		if(getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){
			if(hasInTank()) {
				this.output.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), effectiveRate());
			}
	
			if(hasOutTank()) {
				this.input.drainOrCleanFluid(getInTank().inputTank, effectiveRate(), true);
			}
	
			if(hasMaterialCabinet()) {
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
									if(y < getMaterialCabinet().MATERIAL_LIST.size()) {
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
			}
		}

		this.dummyRecipe = null;
	}
}