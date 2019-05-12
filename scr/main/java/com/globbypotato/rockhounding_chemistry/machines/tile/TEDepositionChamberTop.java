package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TEDepositionChamberTop extends TileEntityInv implements IInternalServer{

	public static int inputSlots = 1;
	public static int outputSlots = 1;
	public static int templateSlots = 3;

	public int temperatureCount = 0;
	public int temperatureMax = 3000;
	public int pressureCount = 0;
	public int pressureMax = 32000;

	public int currentFile = -1;
	public boolean isRepeatable;

	public DepositionChamberRecipe dummyRecipe;

	public TEDepositionChamberTop() {
		super(inputSlots, outputSlots, templateSlots, 0);

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

	public ItemStack outputSlot() {
		return this.output.getStackInSlot(OUTPUT_SLOT);
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
		return "deposition_chamber_top";
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
		return getRecipeIndex() > -1 && getRecipeIndex() < recipeList().size();
	}

	public ArrayList<DepositionChamberRecipe> recipeList(){
		return DepositionChamberRecipes.deposition_chamber_recipes;
	}

	public DepositionChamberRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(isValidPreset() && !stack.isEmpty()){
			if(recipeList().get(getRecipeIndex()).getType()){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
				if(!inputOreIDs.isEmpty()){
					if(inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getRecipeIndex()).getOredict()))){
						return true;
					}
				}
			}else{
				if(recipeList().get(getRecipeIndex()).getInput().isItemEqual(stack)){
					return true;
				}
			}
		}
		return false;
	}

	public DepositionChamberRecipe getCurrentRecipe(){
		if(isValidPreset() && isValidInput(inputSlot()) && handleSolvent() && handleCarrier() && handleProduct() ){
			return getRecipeList(getRecipeIndex());
		}
		return null;
	}

	public DepositionChamberRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public ItemStack recipeInput(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getInput() : ItemStack.EMPTY; }
	public FluidStack recipeSolvent(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getSolvent() : null; }
	public FluidStack recipeCarrier(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getCarrier() : null; }
	public ItemStack recipeOutput(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getOutput() : ItemStack.EMPTY; }
	public boolean oredictType(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getType() : false; }

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
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, getFacing(), 1, 0);
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

//chamber
	public BlockPos chamberPos(){
		return this.pos.offset(EnumFacing.DOWN, 1);		
	}

	public TEDepositionChamberBase getChamber(){
		TileEntity te = this.world.getTileEntity(chamberPos());
		if(this.world.getBlockState(chamberPos()) != null && te instanceof TEDepositionChamberBase){
			TEDepositionChamberBase tank = (TEDepositionChamberBase)te;
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
	public Block getSeparator1(){
		BlockPos separatorPos = chamberPos().offset(getFacing(), 1);
		IBlockState sepState = this.world.getBlockState(separatorPos);
		Block separator = sepState.getBlock();
		if(MachineIO.miscBlocksA(separator, sepState, EnumMiscBlocksA.SEPARATOR.ordinal())){
			return separator;
		}
		return null;
	}
//separator 2
	public Block getSeparator2(){
		BlockPos separatorPos = chamberPos().offset(getFacing(), 2);
		IBlockState sepState = this.world.getBlockState(separatorPos);
		Block separator = sepState.getBlock();
		if(MachineIO.miscBlocksA(separator, sepState, EnumMiscBlocksA.SEPARATOR.ordinal())){
			return separator;
		}
		return null;
	}

//input vessel 
	public TileVessel getInputVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, chamberPos(), isFacingAt(270), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasInputVessel(){
		return getInputVessel() != null;
	}

//carrier vessel 
	public TileVessel getCarrierVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, isFacingAt(90), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasCarrierVessel(){
		return getCarrierVessel() != null;
	}

//purge vessel
	public TileVessel getPurgeVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, chamberPos(), getFacing(), 3, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasPurgeVessel(){
		return getPurgeVessel() != null;
	}

//pressurizer1
	public TEGasPressurizer getPressurizer1(){
		BlockPos pressurizerPos = chamberPos().offset(getFacing(), 1);
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, pressurizerPos, isFacingAt(90), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

//pressurizer2
	public TEGasPressurizer getPressurizer2(){
		BlockPos pressurizerPos = chamberPos().offset(getFacing(), 1);
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, pressurizerPos, isFacingAt(270), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getPressurizer1() != null && getPressurizer2() != null && getSeparator1() != null && getSeparator2() != null && getChamber() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, chamberPos(), isFacingAt(270), 1, 0);
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			handlePurge();

			initializeServer(isRepeatable, hasServer(), getServer(), deviceCode());

			if(isActive() && getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(isValidPreset()){
				handleParameters();

				if(canProcess()){
					this.cooktime++;
					if(this.getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0; 
						process();
					}
					this.markDirtyClient();
				}else{
					this.cooktime = 0;
				}
			}else{
				handleRelaxing();
			}
		}
	}

	public boolean canProcess(){
		return isActive()
			&& hasPressurizers()
			&& hasTemperature()
			&& hasPressure()
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
	}

	private boolean hasPressure() {
		return isValidPreset() && this.getPressure() >= getRecipeList(getRecipeIndex()).getPressure();
	}

	private boolean hasTemperature() {
		return isValidPreset() && this.getTemperature() >= getRecipeList(getRecipeIndex()).getTemperature();
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
	
			updateServer(hasServer(), getServer(), this.currentFile);
		}
		
		this.dummyRecipe = null;
	}

	private void handlePurge() {
		if(isActive()){
			if(!isValidPreset() || !isValidRecipe()){
				if(!inputSlot().isEmpty() && isPurgeable()){
					if(this.output.canSetOrStack(outputSlot(), inputSlot())){
						this.output.setOrStack(OUTPUT_SLOT, inputSlot());
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
						}
					}
					if(hasCarrierVessel()){
						if(isWrongCarrier() && canPurgeCarrier()){
							int canReceive = getPurgeVessel().inputTank.getCapacity() - getPurgeVessel().inputTank.getFluidAmount();
							int canSend = Math.min(getCarrierVessel().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeVessel().inputTank, vesselCarrier(), canSend);
							this.input.drainOrCleanFluid(getCarrierVessel().inputTank, canSend, true);
						}
					}
				}
			}
		}
	}

	private boolean isPurgeable() {
		if(oredictType()){
			ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
			if(!inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getRecipeIndex()).getOredict()))){
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
			int temp = getRecipeList(getRecipeIndex()).getTemperature();
			int press = getRecipeList(getRecipeIndex()).getPressure();
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
			for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(!fileSlot.isEmpty() && fileSlot.isItemEqual(BaseRecipes.server_file)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isValidFile(tag)){
							if(tag.getInteger("Device") == deviceCode()){
								if(tag.getInteger("Recipe") < recipeList().size()){
									if(tag.getInteger("Done") > 0){
										if(this.recipeIndex != tag.getInteger("Recipe")){
											this.recipeIndex = tag.getInteger("Recipe");
											this.markDirtyClient();
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
				if(x == TEServer.FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}

}