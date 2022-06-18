package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.StirredTankRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.StirredTankRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEStirredTankBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEStirredTankController extends TileEntityInv{

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public TEStirredTankController() {
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
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot(){
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "stirred_tank_controller";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.DOWN).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return isFacingAt(90);
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<StirredTankRecipe> recipeList(){
		return StirredTankRecipes.stirred_tank_recipes;
	}

	public StirredTankRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public StirredTankRecipe getCurrentRecipe(){
		if(hasSolventTank() && hasReagentTank()){
			for(int x = 0; x < recipeList().size(); x++){
				if(getSolventTank().getSolventFluid() != null && getReagentTank().getSolventFluid() != null && getRecipeList(x).getSolvent() != null && getRecipeList(x).getReagent() != null){
					if(getSolventTank().getSolventFluid().isFluidEqual(getRecipeList(x).getSolvent()) && getReagentTank().getSolventFluid().isFluidEqual(getRecipeList(x).getReagent())){
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

	public FluidStack recipeSolvent(){ return isValidRecipe() ? getCurrentRecipe().getSolvent() : null; }
	public FluidStack recipeReagent(){ return isValidRecipe() ? getCurrentRecipe().getReagent() : null; }
	public FluidStack recipeSolution(){ return isValidRecipe() ? getCurrentRecipe().getSolution() : null; }
	public FluidStack recipeFume(){ return isValidRecipe() ? getCurrentRecipe().getFume() : null; }
	private boolean hasFume() {return recipeFume() != null;}
	public boolean hasVoltage(){return isValidRecipe() ? getCurrentRecipe().getVoltage() > 0 : false;}
	public int voltageLevel(){return isValidRecipe() ? getCurrentRecipe().getVoltage() : 0;}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 && getEngine().getRedstone() >= powerConsume(): false;
	}
	
	public boolean hasRedstonePower(){
		if(hasEngine()){
			if(hasVoltage()){
				return getEngine().getRedstone() >= powerConsume();
			}else{
				return true;
			}
		}
		return false;
	}

//chamber
	public TEStirredTankBase getChamber(){
		BlockPos chamberPos = this.pos.offset(EnumFacing.DOWN);
		TileEntity te = this.world.getTileEntity(chamberPos);
		if(this.world.getBlockState(chamberPos) != null && te instanceof TEStirredTankBase){
			TEStirredTankBase tank = (TEStirredTankBase)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasChamber(){
		return getChamber() != null;
	}

//charger
	public Block getCharger(){
		BlockPos chargerPos= this.pos.offset(EnumFacing.UP);
		Block charger = TileStructure.getStructure(this.world, chargerPos, EnumMiscBlocksA.EXTRACTOR_CHARGER.ordinal());
		return charger != null ? charger : null;
	}

	public boolean hasCharger(){
		return getCharger() != null;
	}

//routers	
	public boolean hasRouters(){
		return TileStructure.getFluidRouter(world, this.pos.offset(isFacingAt(270)), isFacingAt(270));
	}

//solvent tank
	public TEFlotationTank getSolventTank(){
		BlockPos solventTankPos = this.pos.offset(isFacingAt(270), 2).offset(EnumFacing.UP);
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, solventTankPos);
		return tank != null ? tank : null;
	}

	public boolean hasSolventTank(){
		return getSolventTank() != null;
	}

//reagent tank
	public TEFlotationTank getReagentTank(){
		BlockPos reagentTankPos = this.pos.offset(isFacingAt(270), 1).offset(EnumFacing.UP);
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, reagentTankPos);
		return tank != null ? tank : null;
	}

	public boolean hasReagentTank(){
		return getReagentTank() != null;
	}

//input pressurizer
	public TEAuxiliaryEngine getInputPressurizer(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(isFacingAt(270), 3), isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

//output vessel
	public TileVessel getFumeTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(isFacingAt(90), 2), isFacingAt(90));
		return vessel != null ? vessel : null;
	}

	public boolean hasFumeTank(){
		return getFumeTank() != null;
	}

//centrifuge
	public TECentrifuge getCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(90)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuge(){
		return getCentrifuge() != null;
	}

//output tank
	public TEStirredTankOut getOuttank(){
		BlockPos tankPos = this.pos.offset(EnumFacing.DOWN, 2);
		TileEntity te = this.world.getTileEntity(tankPos);
		if(this.world.getBlockState(tankPos) != null && te instanceof TEStirredTankOut){
			TEStirredTankOut tank = (TEStirredTankOut)te;
			return tank;
		}
		return null;
	}

	public boolean hasOuttank(){
		return getOuttank() != null;
	}

	private boolean isAssembled() {
		return hasCharger() && hasOuttank() && hasChamber() && hasRouters() && hasCentrifuge();
	}



	//----------------------- CUSTOM -----------------------
	private void drainPower() {
		getEngine().powerCount--;
		if(hasVoltage()){
			getEngine().redstoneCount -= powerConsume();
		}
		getEngine().markDirtyClient();
	}

	public int powerConsume() {
		int baseConsume = ((10 * voltageLevel()) * ModConfig.basePower);
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedCstr / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedCstr;
	}
	
	public int getCalculatedSolvent(){
		return isValidRecipe() ? getCurrentRecipe().getSolvent().amount * speedFactor(): 0;
	}
	
	public int getCalculatedReagent(){
		return isValidRecipe() ? getCurrentRecipe().getReagent().amount * speedFactor() : 0;
	}

	public int getCalculatedSolution(){
		return isValidRecipe() ? getCurrentRecipe().getSolution().amount * speedFactor() : 0;
	}

	public int getCalculatedFume(){
		return isValidRecipe() ? getCurrentRecipe().getFume().amount * speedFactor(): 0;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();

			if(isActive()){
				if(canProcess()){
					drainPower();
					this.cooktime++;
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
					}
					this.markDirtyClient();
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
			if(!getEngine().enableRedstone){
				getEngine().enableRedstone = true;
				getEngine().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		if(isValidRecipe()){
			if(hasFuelPower() && hasRedstonePower()
				&& isAssembled()
				&& hasFluids()
				&& canOutput()
				&& canOutputFumes()
			){
				return true;
			}
		}
		return false;
	}

	private boolean hasFluids() {
		return hasSolventTank() && hasReagentTank()
			&& this.input.canDrainFluid(getSolventTank().getSolventFluid(), getCurrentRecipe().getSolvent(), getCalculatedSolvent())
			&& this.input.canDrainFluid(getReagentTank().getSolventFluid(), getCurrentRecipe().getReagent(), getCalculatedReagent());
	}

	private boolean canOutput() {
		return this.output.canSetOrAddFluid(getOuttank().inputTank, getOuttank().getTankFluid(), getCurrentRecipe().getSolution(), getCalculatedSolution());
	}

	private boolean canOutputFumes() {
		return ((hasFume() 
					&& hasFumeTank() 
					&& this.output.canSetOrAddFluid(getFumeTank().inputTank, getFumeTank().getTankFluid(), recipeFume(), getCalculatedFume())) 
			|| !hasFume());
	}

	private void process() {
		if(hasOuttank() && hasSolventTank() && hasReagentTank()) {
			this.output.setOrFillFluid(getOuttank().inputTank, getCurrentRecipe().getSolution(), getCalculatedSolution());
			this.input.drainOrCleanFluid(getSolventTank().inputTank, getCalculatedSolvent(), true);
			this.input.drainOrCleanFluid(getReagentTank().inputTank, getCalculatedReagent(), true);
		}
		if(hasFume() && hasFumeTank() && this.output.canSetOrFillFluid(getFumeTank().inputTank, getFumeTank().getTankFluid(), recipeFume())){
			this.output.setOrFillFluid(getFumeTank().inputTank, getCurrentRecipe().getFume(), getCalculatedFume());
		}
	}

}