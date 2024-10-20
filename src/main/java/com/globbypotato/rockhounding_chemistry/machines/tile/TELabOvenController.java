package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELabOvenChamber;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.google.common.base.Strings;

import net.minecraft.block.Block;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class TELabOvenController extends TileEntityInv implements IInternalServer{

	public static int inputSlots = 2;
	public static int templateSlots = 4;
	public static int upgradeSlots = 1;

	public static final int SOLUTE_SLOT = 0;
	public static final int CATALYST_SLOT = 1;

	public static final int SPEED_SLOT = 0;

	public boolean direction = false;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TELabOvenController() {
		super(inputSlots, 0, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SOLUTE_SLOT && isActive() && isValidInput(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CATALYST_SLOT && isActive() && (isValidCatalyst(insertingStack))){
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
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.direction = compound.getBoolean("Direction");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("Direction", getDirection());
        return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack soluteSlot(){
		return this.input.getStackInSlot(SOLUTE_SLOT);
	}

	public ItemStack catalystSlot(){
		return this.input.getStackInSlot(CATALYST_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "lab_oven_controller";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedOven / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedOven;
	}

	private static int deviceCode() {
		return EnumServer.LAB_OVEN.ordinal();
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.DOWN).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 90);
	}



	//----------------------- CUSTOM -----------------------
	boolean isValidInput(ItemStack stack) {
		if(isValidPreset()){
			if(recipeList().get(getSelectedRecipe()).getType()){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
				if(!inputOreIDs.isEmpty()){
					if(inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getSelectedRecipe()).getOredict()))){
						return true;
					}
				}
			}else{
				if(recipeList().get(getSelectedRecipe()).getSolute().isItemEqual(stack)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidCatalyst(ItemStack stack){
		return !stack.isEmpty() && !recipeCatalyst().isEmpty() && stack.isItemEqualIgnoreDurability(recipeCatalyst());
	}

	public boolean getDirection() {
		return this.direction;
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<LabOvenRecipe> recipeList(){
		return LabOvenRecipes.lab_oven_recipes;
	}

	public LabOvenRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public LabOvenRecipe getCurrentRecipe(){
		if(isValidPreset()){
			return getRecipeList(getSelectedRecipe());
		}
		return null;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}

	public ItemStack recipeSolute(){ return isValidRecipe() ? getCurrentRecipe().getSolute() : ItemStack.EMPTY; }
	public ItemStack recipeCatalyst(){ return isValidRecipe() ? getCurrentRecipe().getCatalyst() : ItemStack.EMPTY; }
	public FluidStack recipeSolvent(){ return isValidRecipe() ? getCurrentRecipe().getSolvent() : null; }
	public FluidStack recipeReagent(){ return isValidRecipe() ? getCurrentRecipe().getReagent() : null; }
	public FluidStack recipeSolution(){ return isValidRecipe() ? getCurrentRecipe().getSolution() : null; }
	public FluidStack recipeByproduct(){ return isValidRecipe() ? getCurrentRecipe().getByproduct() : null; }
	public boolean oredictType(){ return isValidRecipe() ? getCurrentRecipe().getType() : false; }

	public void doPreset() {
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
		if(isValidPreset()){
			if(hasSolventTank()){
				if(getSolventTank().getFilterSolvent() != recipeSolvent()){
					getSolventTank().filterSolvent = recipeSolvent();
				}
				getSolventTank().filterManualSolvent = null;
				getSolventTank().isFiltered = true;
			}

			if(hasReagentTank()){
				if(getReagentTank().getFilterSolvent() != recipeReagent()){
					getReagentTank().filterSolvent = recipeReagent();
				}
				getReagentTank().filterManualSolvent = null;
				getReagentTank().isFiltered = true;
			}
		}else{
			emptyFilters();
		}
	}

	public void emptyFilters() {
		if(hasSolventTank()){
			getSolventTank().filterSolvent = null;
			getSolventTank().isFiltered = false;
		}
		
		if(hasReagentTank()){
			getReagentTank().filterSolvent = null;
			getReagentTank().isFiltered = false;
		}

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

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 : false;
	}

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() >= powerConsume() : false;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().redstoneCount -= powerConsume();
		getEngine().markDirtyClient();
	}

	public int powerConsume() {
		int baseConsume = 20 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

//chamber
	public TELabOvenChamber getChamber(){
		BlockPos chamberPos = this.pos.offset(EnumFacing.DOWN);
		TileEntity te = this.world.getTileEntity(chamberPos);
		if(this.world.getBlockState(chamberPos) != null && te instanceof TELabOvenChamber){
			TELabOvenChamber tank = (TELabOvenChamber)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasChamber(){
		return getChamber() != null;
	}

//separator
	public BlockPos separatorPos(){
		return this.pos.offset(EnumFacing.DOWN).offset(getFacing(), 1);
	}

	public Block getSeparator(){
		Block separator = TileStructure.getStructure(this.world, separatorPos(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparator(){
		return getSeparator() != null;
	}

//Unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(EnumFacing.DOWN).offset(getFacing(), 2);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//solvent tank
	public TEFlotationTank getSolventTank(){
		EnumFacing directionalFacing = isFacingAt(270);
		if(getDirection()) {
			directionalFacing = isFacingAt(90);
		}
		BlockPos tankPos = this.pos.offset(getFacing()).offset(directionalFacing, 2);
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, tankPos);
		return tank != null ? tank : null;
	}

	public boolean hasSolventTank(){
		return getSolventTank() != null;
	}

//reagent tank
	public TEFlotationTank getReagentTank(){
		EnumFacing directionalFacing = isFacingAt(270);
		if(getDirection()) {
			directionalFacing = isFacingAt(90);
		}
		BlockPos tankPos = this.pos.offset(getFacing()).offset(directionalFacing, 1);
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, tankPos);
		return tank != null ? tank : null;
	}

	public boolean hasReagentTank(){
		return getReagentTank() != null;
	}

//solution tank
	public TEBufferTank getSolutionTank(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		BlockPos tankPos = this.pos.offset(getFacing()).offset(directionalFacing, 1);
		TEBufferTank tank = TileStructure.getBufferTank(this.world, tankPos);
		return tank != null ? tank : null;
	}

	public boolean hasSolutionTank(){
		return getSolutionTank() != null;
	}

//byproduct tank
	public TEBufferTank getByproductTank(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		BlockPos tankPos = this.pos.offset(getFacing()).offset(directionalFacing, 2);
		TEBufferTank tank = TileStructure.getBufferTank(this.world, tankPos);
		return tank != null ? tank : null;
	}

	public boolean hasByproductTank(){
		return getByproductTank() != null;
	}

//routers
	public boolean hasRouters(){
		return TileStructure.getFluidRouter(world, separatorPos().offset(isFacingAt(270)), isFacingAt(270))
			&& TileStructure.getFluidRouter(world, separatorPos().offset(isFacingAt(90)), isFacingAt(90));
	}

//input pressurizer
	public TEAuxiliaryEngine getInputPressurizer(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, separatorPos().offset(isFacingAt(270), 3), isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

//output pressurizer
	public TEAuxiliaryEngine getOutputPressurizer(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, separatorPos().offset(isFacingAt(90), 3), isFacingAt(270));
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getInputPressurizer() != null && getOutputPressurizer() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, separatorPos().offset(getFacing(), 2), getFacing().getOpposite());
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return 	hasSeparator() && hasRouters() && hasUnloader() && hasPressurizers();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			doPreset();
			handlePurge();
			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, recipeList().size());

			if(isActive()){
				if(canProcess()){
					this.cooktime++;
					drainPower();
					resetOversee(getServer(), this.currentFile);
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
					}
					this.markDirtyClient();
				}else {
					tickOversee(getServer(), this.currentFile);
				}
			}else{
				tickOff();
			}

			if(this.soluteSlot().isEmpty() && this.catalystSlot().isEmpty()){
				tickOff();
			}

		}
	}

	private void handlePurge() {
		if(isActive() && hasUnloader()){
			if(!isValidPreset() || isValidRecipe()){
				if(!soluteSlot().isEmpty() && isPurgeable()){
					if(((MachineStackHandler) getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), soluteSlot())){
						((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, soluteSlot());
						this.input.setStackInSlot(SOLUTE_SLOT, ItemStack.EMPTY);
					}
				}
				if(!catalystSlot().isEmpty() && !recipeSolute().isEmpty()){
					if(!catalystSlot().isItemEqualIgnoreDurability(recipeCatalyst()) ){
						if(((MachineStackHandler) getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), catalystSlot())){
							((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, catalystSlot());
							this.input.setStackInSlot(CATALYST_SLOT, ItemStack.EMPTY);
						}
					}
				}
			}
		}
	}

	private boolean isPurgeable() {
		if(oredictType()){
			ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(soluteSlot()));
			if(!inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getSelectedRecipe()).getOredict()))){
				return true;
			}
		}else{
			if(!CoreUtils.isMatchingIngredient(recipeSolute(), soluteSlot())){
				return true;
			}
		}
		return false;
	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe()
			&& hasFuelPower()
			&& hasRedstonePower()
			&& isAssembled()
			&& handleSolute()
			&& handleCatalyst()
			&& handleSolvent()
			&& handleReagent()
			&& handleSolution()
			&& handleByproduct()
			&& handleServer(getServer(), this.currentFile); //server
	}

	private boolean handleSolute() {
		if(isValidRecipe()){
			if(!soluteSlot().isEmpty()){
				if(getCurrentRecipe().getType()){
					ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(soluteSlot()));
					if(!inputOreIDs.isEmpty()){
						if(!Strings.isNullOrEmpty(recipeList().get(getSelectedRecipe()).getOredict())){  
							if(inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getSelectedRecipe()).getOredict()))){
								return true;
							}
						}
					}
				}else{
					return !recipeSolute().isEmpty() ? recipeList().get(getSelectedRecipe()).getSolute().isItemEqual(soluteSlot()) : true;
				}
			}
		}
		return false;
	}

	private boolean handleCatalyst() {
		return !recipeCatalyst().isEmpty() ? isValidCatalyst(catalystSlot()) || CoreUtils.isMatchingIngredient(catalystSlot(), recipeCatalyst()) : true;
	}

	private boolean handleSolvent() {
		return recipeSolvent() != null && hasSolventTank() && this.input.canDrainFluid(getSolventTank().getSolventFluid(), recipeSolvent());
	}

	private boolean handleReagent() {
		return recipeReagent() == null || (recipeReagent() != null && hasReagentTank() && this.input.canDrainFluid(getReagentTank().getSolventFluid(), recipeReagent()));
	}

	private boolean handleSolution() {
		return recipeSolution() != null && hasSolutionTank() && this.input.canSetOrFillFluid(getSolutionTank().inputTank, getSolutionTank().getTankFluid(), recipeSolution());
	}
	
	private boolean handleByproduct() {
		return recipeByproduct() == null || (recipeByproduct() != null && hasByproductTank() && this.input.canSetOrFillFluid(getByproductTank().inputTank, getByproductTank().getTankFluid(), recipeByproduct()));
	}

	private void process() {
		if(hasSolventTank()){
			if(recipeSolvent() != null){
				getSolventTank().inputTank.drainInternal(recipeSolvent(), true);
			}
		}
		if(hasReagentTank()){
			if(recipeReagent() != null){
				getReagentTank().inputTank.drainInternal(recipeReagent(), true);
			}
		}

		if(hasSolutionTank()){
			if(recipeSolution() != null){
				getSolutionTank().inputTank.fillInternal(recipeSolution(), true);
			}
		}	
		if(hasByproductTank()){
			if(recipeByproduct() != null){
				getByproductTank().inputTank.fillInternal(recipeByproduct(), true);
			}
		}

		if(!catalystSlot().isEmpty()){
			int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, catalystSlot());
			this.input.damageUnbreakingSlot(unbreakingLevel, CATALYST_SLOT);

			if(CoreUtils.hasMending(catalystSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
				this.input.repairMendingSlot(CATALYST_SLOT);
			}
		}

		if(!soluteSlot().isEmpty()){
			this.input.decrementSlot(SOLUTE_SLOT);
		}

		updateServer(getServer(), this.currentFile);
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