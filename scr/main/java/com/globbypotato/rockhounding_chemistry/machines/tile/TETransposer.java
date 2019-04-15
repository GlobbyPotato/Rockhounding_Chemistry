package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.TransposerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.TransposerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.gas.GasHandlerConcatenate;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTankVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TETransposer extends TileEntityTankVessel implements IToxic, ICollapse{

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 6;
	public static int upgradeSlots = 1;

	public FluidTank inputTankMain, outputTankFluid, outputTankGas;
	public FluidStack filterMain = null;
	public boolean isFluidActive, isGasActive;
	public int collapseRate = 0;

	public TETransposer() {
		super(0, 0, templateSlots, upgradeSlots);

		this.inputTankMain = new FluidTank(getFluidCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillMainFiltered(fluid);
			}

			@Override
			public boolean canDrain() {
				return false;
			}
		};
		this.inputTankMain.setTileEntity(this);

		this.outputTankFluid = new FluidTank(getFluidCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return false;
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.outputTankFluid.setTileEntity(this);

		this.outputTankGas = new FluidTank(getGasCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack gas) {
				return false;
			}

			@Override
			public boolean canDrainFluidType(FluidStack gas) {
				return true;//isValidPump(gas);
			}
		};
		this.outputTankGas.setTileEntity(this);

	
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

		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTankMain.readFromNBT(compound.getCompoundTag("InputTankMain"));
		this.outputTankFluid.readFromNBT(compound.getCompoundTag("OutputTankFluid"));
		this.outputTankGas.readFromNBT(compound.getCompoundTag("OutputTankGas"));
		this.filterMain = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("FilterMain"));
		this.isFluidActive = compound.getBoolean("EnableFluid");
		this.isGasActive = compound.getBoolean("EnableGas");
		this.collapseRate = compound.getInteger("Collapse");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound inputTankMainNBT = new NBTTagCompound();
		this.inputTankMain.writeToNBT(inputTankMainNBT);
		compound.setTag("InputTankMain", inputTankMainNBT);

		NBTTagCompound outputTankFluidNBT = new NBTTagCompound();
		this.outputTankFluid.writeToNBT(outputTankFluidNBT);
		compound.setTag("OutputTankFluid", outputTankFluidNBT);

		NBTTagCompound outputTankGasNBT = new NBTTagCompound();
		this.outputTankGas.writeToNBT(outputTankGasNBT);
		compound.setTag("OutputTankGas", outputTankGasNBT);


		if(hasFilterMain()){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        this.filterMain.writeToNBT(filterNBT);
	        compound.setTag("FilterMain", filterNBT);
		}

		compound.setBoolean("EnableFluid", this.isFluidActive);
		compound.setBoolean("EnableGas", this.isGasActive);
		compound.setInteger("Collapse", getCollapse());

		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTankMain, this.outputTankFluid, this.outputTankGas);
	}

	@Override
	public GasHandlerConcatenate getCombinedGasTank() {
		return new GasHandlerConcatenate(this.outputTankGas);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "transposer";
	}

	public boolean isFluidActive(){
		return this.isFluidActive;
	}

	public boolean isGasActive(){
		return this.isGasActive;
	}

	public int getCollapse(){
		return this.collapseRate;
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTankMain);
		tanks.add(this.outputTankFluid);
		return tanks;
 	}

	@Override
 	public ArrayList<FluidTank> collapseList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTankMain);
		tanks.add(this.outputTankGas);
		return tanks;
 	}


	//----------------------- TANK HANDLER -----------------------
	public int getFluidCapacity() {
		return 5000;
	}

	public int getGasCapacity() {
		return 5000;
	}

// MAIN
	public boolean hasInputTankMain(){return this.inputTankMain.getFluid() != null;}
	public FluidStack getInputTankMain(){return hasInputTankMain() ? this.inputTankMain.getFluid() : null;}
	public int getInputTankMainAmount() {return hasInputTankMain() ? this.inputTankMain.getFluidAmount() : 0;}
	public FluidStack getFilterMain(){return this.filterMain;}
	public boolean hasFilterMain(){return getFilterMain() != null;}

//main I/O
	private boolean isMatchingFilterMain(FluidStack fluid) {
		return hasFilterMain() ? getFilterMain().isFluidEqual(fluid) : true;
	}

	public boolean canFillMainFiltered(FluidStack fluid) {
		return fluid != null && isMatchingFilterMain(fluid) && this.input.canSetOrFillFluid(this.inputTankMain, getInputTankMain(), fluid);
	}

// FLUID
	public boolean hasOutputTankFluid(){return this.outputTankFluid.getFluid() != null;}
	public FluidStack getOutputTankFluid(){return hasOutputTankFluid() ? this.outputTankFluid.getFluid() : null;}
	public int getOutputTankFluidAmount() {return hasOutputTankFluid() ? this.outputTankFluid.getFluidAmount() : 0;}

// GAS
	public boolean hasOutputTankGas(){return this.outputTankGas.getFluid() != null;}
	public FluidStack getOutputTankGas(){return hasOutputTankGas() ? this.outputTankGas.getFluid() : null;}
	public int getOutputTankGasAmount() {return hasOutputTankGas() ? this.outputTankGas.getFluidAmount() : 0;}

	public boolean isValidPump(FluidStack gas) {
		TileEntity tile = this.world.getTileEntity(this.pos.offset(getFacing()));
		if(tile != null && tile instanceof TEGaslinePump){
			TEGaslinePump pump = (TEGaslinePump)tile;
			return pump.isActive() && gas != null && gas.getFluid().isGaseous();
		}
		return false;
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<TransposerRecipe> recipeList(){
		return TransposerRecipes.transposer_recipes;
	}

	public static TransposerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public TransposerRecipe getCurrentFluidRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(isMatchingFluidInput(x)){
				return getRecipeList(x);
			}
		}
		return null;
	}

	public TransposerRecipe getCurrentGasRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(isMatchingGasInput(x)){
				return getRecipeList(x);
			}
		}
		return null;
	}

	private boolean isMatchingFluidInput(int x) {
		FluidStack inputstack = getRecipeList(x).getInput();
		if(inputstack != null){
			Fluid inputfluid = inputstack.getFluid();
			if(inputfluid != null){
				if(!inputfluid.isGaseous()){
					if(hasInputTankMain() && getInputTankMain() != null && getRecipeList(x).getInput().isFluidEqual(getInputTankMain())){
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isMatchingGasInput(int x) {
		FluidStack inputstack = getRecipeList(x).getInput();
		if(inputstack != null){
			Fluid inputfluid = inputstack.getFluid();
			if(inputfluid != null){
				if(inputfluid.isGaseous()){
					if(hasInputTankMain() && getInputTankMain() != null && getRecipeList(x).getInput().isFluidEqual(getInputTankMain())){
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean isValidFluidRecipe() {
		return getCurrentFluidRecipe() != null;
	}

	public boolean isValidGasRecipe() {
		return getCurrentGasRecipe() != null;
	}

	public FluidStack recipeFluidInput(){
		return isValidFluidRecipe() ? getCurrentFluidRecipe().getInput() : null;
	}

	public FluidStack recipeFluidOutput(){
		return isValidFluidRecipe() ? getCurrentFluidRecipe().getOutput() : null;
	}

	public FluidStack recipeGasInput(){
		return isValidGasRecipe() ? getCurrentGasRecipe().getInput() : null;
	}

	public FluidStack recipeGasOutput(){
		return isValidGasRecipe() ? getCurrentGasRecipe().getOutput() : null;
	}



	//----------------------- CUSTOM -----------------------
	public int transposeRate(){
		return 125 * speedFactor();
	}


	//----------------------- STRUCTURE -----------------------
	//input vessel
		public TileVessel getInTank(){
			TileVessel vessel = TileStructure.getHolder(this.world, this.pos, getFacing(), 1, 180);
			return vessel != null ? vessel : null;
		}

		public boolean hasInTank(){
			return getInTank() != null;
		}


	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			handleToxic(this.world, this.pos);
			checkCollapse();

			if(hasInTank()){
				if(getInTank().inputTank.getFluidAmount() > 0){
					if(canPushGas(getInTank())){
						pushGas(getInTank());
					}
				}
			}

			if(canProcessFluid()){
				processFluid();
			}

			if(canProcessGas()){
				processGas();
			}
			this.markDirtyClient();
		}
	}

	private boolean canPushGas(TileVessel vess) {
		int fluidCan = this.inputTankMain.getCapacity() - this.inputTankMain.getFluidAmount();
		int fluidHas = getInTank().inputTank.getFluidAmount() >= fluidCan ? fluidCan : getInTank().inputTank.getFluidAmount();
		return this.input.canSetOrAddFluid(this.inputTankMain, getInputTankMain(), getInTank().getTankFluid(), fluidHas);
	}

	private void pushGas(TileVessel vess) {
		if(getInTank().getTankFluid() != null){
			int fluidCan = this.inputTankMain.getCapacity() - this.inputTankMain.getFluidAmount();
			int fluidHas = getInTank().inputTank.getFluidAmount() >= fluidCan ? fluidCan : getInTank().inputTank.getFluidAmount();
			this.input.setOrFillFluid(this.inputTankMain, getInTank().getTankFluid(), fluidHas);
			this.input.drainOrCleanFluid(getInTank().inputTank, fluidHas, true);
		}
	}

	private boolean canProcessFluid() {
		int fluidCan = this.outputTankFluid.getCapacity() - getOutputTankFluidAmount();
		int fluidHas = getInputTankMainAmount() >= transposeRate() ? transposeRate() : getInputTankMainAmount();
		int fluidWant = fluidHas >= fluidCan ? fluidCan : fluidHas;
		return isFluidActive() 
			&& this.inputTankMain.getFluid() != null
			&& isValidFluidRecipe()
			&& fluidCan > 0
			&& this.input.canDrainFluid(getInputTankMain(), recipeFluidInput(), transposeRate())
			&& this.output.canSetOrAddFluid(this.outputTankFluid, getOutputTankFluid(), recipeFluidOutput(), transposeRate());
	}

	private void processFluid() {
		int fluidCan = this.outputTankFluid.getCapacity() - getOutputTankFluidAmount();
		int fluidHas = getInputTankMainAmount() >= transposeRate() ? transposeRate() : getInputTankMainAmount();
		int fluidWant = fluidHas >= fluidCan ? fluidCan : fluidHas;
		this.output.setOrFillFluid(outputTankFluid, recipeFluidOutput(), fluidWant);
		this.input.drainOrCleanFluid(inputTankMain, fluidWant, true);
	}

	private boolean canProcessGas() {
		int fluidCan = this.outputTankGas.getCapacity() - getOutputTankGasAmount();
		int fluidHas = getInputTankMainAmount() >= transposeRate() ? transposeRate() : getInputTankMainAmount();
		int fluidWant = fluidHas >= fluidCan ? fluidCan : fluidHas;
		return isGasActive()
			&& isValidGasRecipe()
			&& fluidCan > 0
			&& this.input.canDrainFluid(getInputTankMain(), recipeGasInput(), fluidWant)
			&& this.output.canSetOrAddFluid(this.outputTankGas, getOutputTankGas(), recipeGasOutput(), fluidWant);
	}

	private void processGas() {
		int fluidCan = this.outputTankGas.getCapacity() - getOutputTankGasAmount();
		int fluidHas = getInputTankMainAmount() >= transposeRate() ? transposeRate() : getInputTankMainAmount();
		int fluidWant = fluidHas >= fluidCan ? fluidCan : fluidHas;
		this.output.setOrFillFluid(outputTankGas, recipeGasOutput(), fluidWant);
		this.input.drainOrCleanFluid(inputTankMain, fluidWant, true);
	}

	public void checkCollapse() {
		if(ModConfig.enableHazard){
			if(world.rand.nextInt(ModConfig.hazardChance) == 0){
				if(handleCollapse(getCollapse(), world, pos)){
					collapseRate++;
					if(getCollapse() >= ModConfig.pressureTolerance){
						if(hasExhaust(world, pos, 1)){
							doExhaustion(world, pos, 1);
							collapseRate /= 2;
						}else{
							world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), (2.0F * 1), true);
						}
					}
				}else{
					if(handleRelease(getCollapse(), world, pos)){
						if(getCollapse() > 0){
							collapseRate--;
						}
					}
				}
			}
		}
	}

}