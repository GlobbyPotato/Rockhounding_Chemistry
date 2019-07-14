package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.StirredTankRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.StirredTankRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEStirredTankTop extends TileEntityInv{

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public TEStirredTankTop() {
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
		return "stirred_tank_top";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public BlockPos poweredPosition(){
		return chamberPos().offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<StirredTankRecipe> recipeList(){
		return StirredTankRecipes.stirred_tank_recipes;
	}

	public StirredTankRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public StirredTankRecipe getCurrentRecipe(){
		if(hasIntank()){
			for(int x = 0; x < recipeList().size(); x++){
				if(getIntank().getSolventFluid() != null && getIntank().getReagentFluid() != null && getRecipeList(x).getSolvent() != null && getRecipeList(x).getReagent() != null){
					if(getIntank().getSolventFluid().isFluidEqual(getRecipeList(x).getSolvent()) && getIntank().getReagentFluid().isFluidEqual(getRecipeList(x).getReagent())){
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
//chamber
	public BlockPos chamberPos(){
		return this.pos.offset(EnumFacing.DOWN, 1);		
	}

//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, getFacing(), 1, 0);
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

//in tank
	public TEFluidInputTank getIntank(){
		BlockPos intankPos = this.pos.offset(isFacingAt(270), 1);
		TileEntity te = this.world.getTileEntity(intankPos);
		if(this.world.getBlockState(intankPos) != null && te instanceof TEFluidInputTank){
			TEFluidInputTank tank = (TEFluidInputTank)te;
			if(tank.getFacing() == isFacingAt(270).getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasIntank(){
		return getIntank() != null;
	}

//output vessel
	public TileVessel getFumeTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, isFacingAt(90), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasFumeTank(){
		return getFumeTank() != null;
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

//charger
	public Block getCharger(){
		BlockPos chargerPos = this.pos.offset(EnumFacing.UP);
		IBlockState chargerState = this.world.getBlockState(chargerPos);
		Block charger = chargerState.getBlock();
		if(MachineIO.miscBlocksA(charger, chargerState, EnumMiscBlocksA.EXTRACTOR_CHARGER.ordinal())){
			return charger;
		}
		return null;
	}

	public boolean hasCharger(){
		return getCharger() != null;
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
		return getCurrentRecipe().getSolvent().amount * speedFactor();
	}
	
	public int getCalculatedReagent(){
		return getCurrentRecipe().getReagent().amount * speedFactor();
	}

	public int getCalculatedSolution(){
		return getCurrentRecipe().getSolution().amount * speedFactor();
	}

	public int getCalculatedFume(){
		return getCurrentRecipe().getFume().amount * speedFactor();
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
			&& canExtract()
			&& hasCharger() && hasOuttank()
			&& canOutput()
			&& ((hasFume() && hasFumeTank() && this.output.canSetOrAddFluid(getFumeTank().inputTank, getFumeTank().getTankFluid(), recipeFume(), getCalculatedFume())) || !hasFume())
			){
				return true;
			}
		}
		return false;
	}

	private boolean canOutput() {
		return this.output.canSetOrAddFluid(getOuttank().inputTank, getOuttank().getTankFluid(), getCurrentRecipe().getSolution(), getCalculatedSolution());
	}

	private boolean canExtract() {
		return this.input.canDrainFluid(getIntank().getSolventFluid(), getCurrentRecipe().getSolvent(), getCalculatedSolvent())
			&& this.input.canDrainFluid(getIntank().getReagentFluid(), getCurrentRecipe().getReagent(), getCalculatedReagent());
	}

	private void process() {
		this.output.setOrFillFluid(getOuttank().inputTank, getCurrentRecipe().getSolution(), getCalculatedSolution());
		this.input.drainOrCleanFluid(getIntank().solventTank, getCalculatedSolvent(), true);
		this.input.drainOrCleanFluid(getIntank().reagentTank, getCalculatedReagent(), true);

		if(hasFume() && hasFumeTank() && this.output.canSetOrFillFluid(getFumeTank().inputTank, getFumeTank().getTankFluid(), recipeFume())){
			this.output.setOrFillFluid(getFumeTank().inputTank, recipeFume(), getCalculatedFume());
		}
	}

}