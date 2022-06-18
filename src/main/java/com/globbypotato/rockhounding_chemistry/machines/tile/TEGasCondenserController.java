package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasCondenserRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasCondenserRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;

public class TEGasCondenserController extends TileEntityInv {
	public static final int SPEED_SLOT = 0;

	public static int upgradeSlots = 1;
	public static int templateSlots = 1;

	public TEGasCondenserController() {
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
		return "gas_condenser";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 90);
	}



	//----------------------- CUSTOM -----------------------
	public int getCooktimeMax(){
		return 30;
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<GasCondenserRecipe> recipeList(){
		return GasCondenserRecipes.gas_condenser_recipes;
	}

	public static GasCondenserRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public GasCondenserRecipe getCurrentRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(isMatchingInput(x) && isRecipeGaseous(x)){
				return getRecipeList(x);
			}
		}
		return null;
	}

	private boolean isMatchingInput(int x) {
		return getRecipeList(x).getInput() != null && hasInTank() && getInTank().inputTank.getFluid() != null && getRecipeList(x).getInput().isFluidEqual(getInTank().inputTank.getFluid());
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public FluidStack getRecipeInput(){ return isValidRecipe() ? getCurrentRecipe().getInput() : null; }
	public FluidStack getRecipeOutput(){ return isValidRecipe() ? getCurrentRecipe().getOutput() : null; }

	public boolean isRecipeGaseous(int x){
		return  getRecipeList(x).getInput().getFluid().isGaseous() 
			&& !getRecipeList(x).getOutput().getFluid().isGaseous();
	}



//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(getFacing().getOpposite()), getFacing());
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

//centrifuge
	public TECentrifuge getCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuge(){
		return getCentrifuge() != null;
	}

//input vessel
	public TileVessel getInTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing(), 2), getFacing().getOpposite());
		return vessel != null ? vessel : null;
	}

	public boolean hasInTank(){
		return getInTank() != null;
	}

// tank
	public TEBufferTank getOutTank(){
		TEBufferTank tank = TileStructure.getBufferTank(this.world, this.pos.offset(EnumFacing.UP));
		return tank != null ? tank : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}

	public boolean isAssembled(){
		return hasCentrifuge();
	}

//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			doPreset();

			if(isActive()){
				if(canProcess()){
					this.cooktime++;
					drainPower();
					process();
					if(getCooktime() >= getCooktimeMax()) {
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
		return isAssembled()
			&& hasFuelPower()
			&& canGetInput()
			&& canStoreOutput();
	}

	private boolean canGetInput() {
		return isValidRecipe()
			&& hasInTank()
			&& this.input.canDrainFluid(getInTank().inputTank.getFluid(), getRecipeInput(), calculateDrain());
	}

	private boolean canStoreOutput() {
		return isValidRecipe() 
			&& hasOutTank()
			&& this.input.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), calculateFill());
	}

	private void process() {
		this.output.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), calculateFill());
		this.input.drainOrCleanFluid(getInTank().inputTank, calculateDrain(), true);
	}

	private int calculateFill() {
		return getRecipeOutput().amount * speedFactor();
	}

	private int calculateDrain() {
		return getRecipeInput().amount * speedFactor();
	}

}