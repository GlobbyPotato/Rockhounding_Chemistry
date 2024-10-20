package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEDepositionBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TEDepositionController extends TileEntityInv implements IInternalServer{

	public static int inputSlots = 1;
	public static int templateSlots = 3;

	public int temperatureCount = 0;
	public int temperatureMax = 3000;
	public int pressureCount = 0;
	public int pressureMax = 32000;

	public int currentFile = -1;
	public boolean isRepeatable;

	public DepositionChamberRecipe dummyRecipe;

	public TEDepositionController() {
		super(inputSlots, 0, templateSlots, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isActive() && isValidInput(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot() {
		return this.input.getStackInSlot(INPUT_SLOT);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.pressureCount = compound.getInteger("PressureCount");
		this.temperatureCount = compound.getInteger("TemperatureCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("PressureCount", this.pressureCount);
		compound.setInteger("TemperatureCount", this.temperatureCount);

		return compound;
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
		return "deposition_chamber_controller";
	}

	public int speedFactor() {
		return hasChamber() && ModUtils.isValidSpeedUpgrade(getChamber().speedSlot()) ? ModUtils.speedUpgrade(getChamber().speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return hasChamber() && ModUtils.isValidSpeedUpgrade(getChamber().speedSlot()) ? ModConfig.speedDeposition / ModUtils.speedUpgrade(getChamber().speedSlot()): ModConfig.speedDeposition;
	}

	private static int deviceCode() {
		return EnumServer.DEPOSITION.ordinal();
	}

	@Override
	public BlockPos poweredPosition(){
		return chamberPos().offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}

	public ArrayList<DepositionChamberRecipe> recipeList(){
		return DepositionChamberRecipes.deposition_chamber_recipes;
	}

	public DepositionChamberRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(isValidPreset() && !stack.isEmpty()){
			if(recipeList().get(getSelectedRecipe()).getType()){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
				if(!inputOreIDs.isEmpty()){
					if(inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getSelectedRecipe()).getOredict()))){
						return true;
					}
				}
			}else{
				if(recipeList().get(getSelectedRecipe()).getInput().isItemEqual(stack)){
					return true;
				}
			}
		}
		return false;
	}

	public DepositionChamberRecipe getCurrentRecipe(){
		if(isValidPreset() && isValidInput(inputSlot()) && handleSolvent() && handleCarrier() && handleProduct() ){
			return getRecipeList(getSelectedRecipe());
		}
		return null;
	}

	public DepositionChamberRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public ItemStack recipeInput(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInput() : ItemStack.EMPTY; }
	public FluidStack recipeSolvent(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getSolvent() : null; }
	public FluidStack recipeCarrier(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getCarrier() : null; }
	public ItemStack recipeOutput(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getOutput() : ItemStack.EMPTY; }
	public boolean oredictType(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getType() : false; }

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
		
		if(isValidPreset()){
			if(hasInputVessel()){
				if(getInputVessel().getFilter() != recipeSolvent()){
					getInputVessel().filter = recipeSolvent();
				}
			}

			if(hasCarrierVessel()){
				if(getCarrierVessel().getFilter() != recipeCarrier()){
					getCarrierVessel().filter = recipeCarrier();
				}
			}
		}
	}



	//----------------------- CUSTOM -----------------------
	public int getPressure() { 	  
		return this.pressureCount; 
	}

	public int getPressureMax() { 	  
		return this.pressureMax; 
	}

	public int getTemperature() { 	  
		return this.temperatureCount; 
	}

	public int getTemperatureMax() { 	  
		return this.temperatureMax; 
	}

	public int takenRF(){
		int baseConsume = 1000 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
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

//chamber
	public BlockPos chamberPos(){
		return this.pos.offset(EnumFacing.DOWN, 1);		
	}

	public TEDepositionBase getChamber(){
		TileEntity te = this.world.getTileEntity(chamberPos());
		if(this.world.getBlockState(chamberPos()) != null && te instanceof TEDepositionBase){
			TEDepositionBase tank = (TEDepositionBase)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasChamber(){
		return getChamber() != null;
	}

//separator 1
	public BlockPos separatorPos1(){
		return this.pos.offset(EnumFacing.DOWN).offset(getFacing(), 1);
	}

	public BlockPos separatorPos2(){
		return this.pos.offset(EnumFacing.DOWN).offset(getFacing(), 2);
	}

	public Block getSeparator1(){
		Block separator = TileStructure.getStructure(this.world, separatorPos1(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public Block getSeparator2(){
		Block separator = TileStructure.getStructure(this.world, separatorPos2(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparators(){
		return getSeparator1() != null && getSeparator2() != null;
	}

//Unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(EnumFacing.DOWN).offset(getFacing(), 3);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//pressurizers
	public TEAuxiliaryEngine getPressurizer1(){
		BlockPos pressPos = this.pos.offset(getFacing()).offset(EnumFacing.DOWN).offset(isFacingAt(270));
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer2(){
		BlockPos pressPos = this.pos.offset(getFacing()).offset(EnumFacing.DOWN).offset(isFacingAt(90));
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, isFacingAt(270));
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getPressurizer1() != null && getPressurizer2() != null;
	}

//centrifuges
	public TECentrifuge getCentrifuge1(){
		TECentrifuge pressurizer = TileStructure.getCentrifuge(this.world, separatorPos2().offset(isFacingAt(270)), isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

	public TECentrifuge getCentrifuge2(){
		TECentrifuge pressurizer = TileStructure.getCentrifuge(this.world, separatorPos2().offset(isFacingAt(90)), isFacingAt(270));
		return pressurizer != null ? pressurizer : null;
	}

	public TECentrifuge getCentrifuge3(){
		TECentrifuge pressurizer = TileStructure.getCentrifuge(this.world, separatorPos2().offset(getFacing(), 2), getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasCentrifuges(){
		return getCentrifuge1() != null && getCentrifuge2() != null;
	}

//input vessel 
	public TileVessel getInputVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, separatorPos2().offset(isFacingAt(270), 2), isFacingAt(90));
		return vessel != null ? vessel : null;
	}

	public boolean hasInputVessel(){
		return getInputVessel() != null;
	}

//carrier vessel 
	public TileVessel getCarrierVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, separatorPos2().offset(isFacingAt(90), 2), isFacingAt(270));
		return vessel != null ? vessel : null;
	}

	public boolean hasCarrierVessel(){
		return getCarrierVessel() != null;
	}

//purge vessel
	public TileVessel getPurgeVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, separatorPos2().offset(getFacing(), 2), getFacing());
		return vessel != null ? vessel : null;
	}

	public boolean hasPurgeVessel(){
		return getPurgeVessel() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, chamberPos().offset(isFacingAt(270)), isFacingAt(90));
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return hasPressurizers() && hasUnloader() && hasSeparators() && hasCentrifuges();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			handlePurge();
			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, recipeList().size());

			if(isActive() && getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(isValidPreset()){
				handleParameters();

				if(canProcess()){
					this.cooktime++;
					resetOversee(getServer(), this.currentFile);
					if(this.getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0; 
						process();
					}
					this.markDirtyClient();
				}else{
					tickOversee(getServer(), this.currentFile);
					this.dummyRecipe = null;
					this.cooktime = 0;
				}
			}else{
				handleRelaxing();
			}
		}
	}

	public boolean canProcess(){
		return isActive()
			&& isAssembled()
			&& hasTemperature()
			&& hasPressure()
			&& handleServer(getServer(), this.currentFile); //server
	}

	private boolean hasPressure() {
		return isValidPreset() && this.getPressure() >= getRecipeList(getSelectedRecipe()).getPressure();
	}

	private boolean hasTemperature() {
		return isValidPreset() && this.getTemperature() >= getRecipeList(getSelectedRecipe()).getTemperature();
	}

	private void process() {
		if(getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){

			if(hasChamber()){
				((MachineStackHandler) getChamber().getOutput()).setOrStack(TileEntityInv.OUTPUT_SLOT, recipeOutput());
			}

			if(hasInputVessel()){
				if(recipeSolvent() != null){
					getInputVessel().inputTank.drainInternal(recipeSolvent(), true);
				}
			}

			if(hasCarrierVessel()){
				if(recipeCarrier() != null){
					getCarrierVessel().inputTank.drainInternal(recipeCarrier(), true);
				}
			}

			this.temperatureCount /= 3;
			this.pressureCount /= 3;
	
			this.input.decrementSlot(INPUT_SLOT);
	
			updateServer(getServer(), this.currentFile);
		}
		
		this.dummyRecipe = null;
	}

	private void handlePurge() {
		if(isActive()){
			if(!isValidPreset() || !isValidRecipe()){
				if(!inputSlot().isEmpty() && isPurgeable()){
					if(this.output.canSetOrStack(getUnloader().unloaderSlot(), inputSlot())){
						((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, inputSlot());
						this.input.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
					}
				}
				if(hasPurgeVessel()){
					if(hasInputVessel()){
						if(isWrongSolvent() && canPurgeSolvent()){
							int canReceive = getPurgeVessel().inputTank.getCapacity() - getPurgeVessel().inputTank.getFluidAmount();
							int canSend = Math.min(getInputVessel().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeVessel().inputTank, vesselSolvent(), canSend);
							this.input.drainOrCleanFluid(getInputVessel().inputTank, canSend, true);
							getPurgeVessel().updateNeighbours();
							getInputVessel().updateNeighbours();
						}
					}
					if(hasCarrierVessel()){
						if(isWrongCarrier() && canPurgeCarrier()){
							int canReceive = getPurgeVessel().inputTank.getCapacity() - getPurgeVessel().inputTank.getFluidAmount();
							int canSend = Math.min(getCarrierVessel().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeVessel().inputTank, vesselCarrier(), canSend);
							this.input.drainOrCleanFluid(getCarrierVessel().inputTank, canSend, true);
							getPurgeVessel().updateNeighbours();
							getCarrierVessel().updateNeighbours();
						}
					}
				}
			}
		}
	}

	private boolean isPurgeable() {
		if(oredictType()){
			ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
			if(!inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getSelectedRecipe()).getOredict()))){
				return true;
			}
		}else{
			if(!CoreUtils.isMatchingIngredient(recipeInput(), inputSlot())){
				return true;
			}
		}
		return false;
	}

	public FluidStack vesselSolvent(){
		return hasInputVessel() ? getInputVessel().inputTank.getFluid() : null;
	}

	public FluidStack vesselCarrier(){
		return hasCarrierVessel() ? getCarrierVessel().inputTank.getFluid() : null;
	}

	private boolean isWrongSolvent() {
		return vesselSolvent() != null
			&& (recipeSolvent() == null || (recipeSolvent() != null && !recipeSolvent().isFluidEqual(vesselSolvent())));
	}

	private boolean isWrongCarrier() {
		return vesselCarrier() != null
			&& (recipeCarrier() == null || (recipeCarrier() != null && !recipeCarrier().isFluidEqual(vesselCarrier())));
	}

	private boolean canPurgeSolvent() {
		return vesselSolvent() != null && this.input.canSetOrFillFluid(getPurgeVessel().inputTank, getPurgeVessel().inputTank.getFluid(), vesselSolvent());
	}

	private boolean canPurgeCarrier() {
		return vesselCarrier() != null && this.input.canSetOrFillFluid(getPurgeVessel().inputTank, getPurgeVessel().inputTank.getFluid(), vesselCarrier());
	}

	private boolean handleSolvent() {
		return recipeSolvent() != null 
			&& hasInputVessel() 
			&& this.input.canDrainFluid(getInputVessel().getTankFluid(), recipeSolvent());
	}

	private boolean handleCarrier() {
		return recipeCarrier() != null 
			&& hasCarrierVessel()
			&& this.input.canDrainFluid(getCarrierVessel().getTankFluid(), recipeCarrier());
	}

	private boolean handleProduct() {
		return recipeOutput() == null || (recipeOutput() != null && hasChamber() && this.input.canSetOrStack(getChamber().outputSlot(), recipeOutput()));
	}

	private void handleParameters() {
		handleRelaxing();
		if(hasEngine() && isActive()){
			int temp = getRecipeList(getSelectedRecipe()).getTemperature();
			int press = getRecipeList(getSelectedRecipe()).getPressure();
			if(getEngine().getRedstone() >= takenRF() && this.getTemperature() < temp && temp < this.getTemperatureMax() - tempYeld() ){
				getEngine().redstoneCount -= takenRF(); 
				this.temperatureCount += tempYeld();
				getEngine().markDirtyClient();
				this.markDirtyClient();
			}

			if(getEngine().getRedstone() >= takenRF() && this.getPressure() < press && press < this.getPressureMax() - pressYeld()){
				getEngine().redstoneCount -= takenRF(); 
				this.pressureCount += pressYeld();
				getEngine().markDirtyClient();
				this.markDirtyClient();
			}
		}
	}

	private void handleRelaxing() {
		if(this.getTemperature() > tempStability()){
			if(this.rand.nextInt(10) == 0){
				if(this.rand.nextInt(stabilityDelta()) == 0){
					this.temperatureCount -= tempStability();
					this.markDirtyClient();
				}
			}
		}
		if(this.getPressure() > pressStability()){
			if(this.rand.nextInt(10) == 0){
				if(this.rand.nextInt(stabilityDelta()) == 0){
					this.pressureCount -= pressStability();
					this.markDirtyClient();
				}
			}
		}
	}

	public int tempYeld(){
		return 10 * getCasing();
	}

	public int pressYeld(){
		return 30 * getCasing();
	}

	private int getCasing() {
		return hasChamber() && getChamber().hasCasing() ? 10 : 1;
	}

	public int tempStability(){
		return 300 / getInsulation();
	}

	public int pressStability(){
		return 500 / getInsulation();
	}

	public int stabilityDelta(){
		return 100 + (10 * getInsulation());
	}

	private int getInsulation() {
		return hasChamber() && getChamber().hasInsulation() ? 10 : 1;
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