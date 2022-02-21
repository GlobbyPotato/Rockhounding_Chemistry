package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.google.common.base.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
	public static int outputSlots = 2;
	public static int templateSlots = 3;
	public static int upgradeSlots = 1;

	public static final int SOLUTE_SLOT = 0;
	public static final int CATALYST_SLOT = 1;

	public static final int SOLUTE_OUT_SLOT = 0;
	public static final int CATALYST_OUT_SLOT = 1;

	public static final int SPEED_SLOT = 0;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TELabOvenController() {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);

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



	//----------------------- SLOTS -----------------------
	public ItemStack soluteSlot(){
		return this.input.getStackInSlot(SOLUTE_SLOT);
	}

	public ItemStack catalystSlot(){
		return this.input.getStackInSlot(CATALYST_SLOT);
	}

	public ItemStack soluteOutSlot(){
		return this.output.getStackInSlot(SOLUTE_OUT_SLOT);
	}

	public ItemStack catalystOutSlot(){
		return this.output.getStackInSlot(CATALYST_OUT_SLOT);
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
		return chamberPos().offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 90);
	}



	//----------------------- CUSTOM -----------------------
	boolean isValidInput(ItemStack stack) {
		if(isValidPreset()){
			if(recipeList().get(getRecipeIndex()).getType()){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
				if(!inputOreIDs.isEmpty()){
					if(inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getRecipeIndex()).getOredict()))){
						return true;
					}
				}
			}else{
				if(recipeList().get(getRecipeIndex()).getSolute().isItemEqual(stack)){
					return true;
				}
			}
		}
		return false;
	}

	public boolean isValidCatalyst(ItemStack stack){
		return !stack.isEmpty() && !recipeCatalyst().isEmpty() && stack.isItemEqualIgnoreDurability(recipeCatalyst());
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
			return getRecipeList(getRecipeIndex());
		}
		return null;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public boolean isValidPreset(){
		return getRecipeIndex() > -1 && getRecipeIndex() < recipeList().size();
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
			if(hasIntank()){
				if(getIntank().getFilterSolvent() != recipeSolvent()){
					getIntank().filterSolvent = recipeSolvent();
				}
				if(getIntank().getFilterReagent() != recipeReagent()){
					getIntank().filterReagent = recipeReagent();
				}
				getIntank().filterManualSolvent = null;
				getIntank().filterManualReagent = null;
				getIntank().isFiltered = true;
			}
		}else{
			emptyFilters();
		}
	}

	public void emptyFilters() {
		if(hasIntank()){
			getIntank().filterSolvent = null;
			getIntank().filterReagent = null;
			getIntank().isFiltered = false;
		}
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
	public BlockPos chamberPos(){
		return this.pos.offset(EnumFacing.DOWN, 1);		
	}

	public TELabOvenChamber getChamber(){
		TileEntity te = this.world.getTileEntity(chamberPos());
		if(this.world.getBlockState(chamberPos()) != null && te instanceof TELabOvenChamber){
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
		return chamberPos().offset(getFacing(), 1);
	}

	public Block getSeparator(){
		IBlockState sepState = this.world.getBlockState(separatorPos());
		Block separator = sepState.getBlock();
		if(MachineIO.miscBlocksA(separator, sepState, EnumMiscBlocksA.SEPARATOR.ordinal())){
			return separator;
		}
		return null;
	}

	public boolean hasSeparator(){
		return getSeparator() != null;
	}

//in tank
	public TEFluidInputTank getIntank(){
		BlockPos intankPos = separatorPos().offset(isFacingAt(270), 1);
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

//out tank
	public TEFluidOutputTank getOuttank(){
		BlockPos outtankPos = separatorPos().offset(isFacingAt(90), 1);
		TileEntity te = this.world.getTileEntity(outtankPos);
		if(this.world.getBlockState(outtankPos) != null && te instanceof TEFluidOutputTank){
			TEFluidOutputTank tank = (TEFluidOutputTank)te;
			if(tank.getFacing() == isFacingAt(90).getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasOuttank(){
		return getOuttank() != null;
	}

//server
	public BlockPos serverPos(){
		return this.pos.offset(getFacing(), 2).offset(EnumFacing.DOWN, 1);
	}

	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, separatorPos(), getFacing(), 1, 0);
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			doPreset();
			handlePurge();

			initializeServer(isRepeatable, hasServer(), getServer(), deviceCode());

			if(isActive()){
				if(canProcess()){
					this.cooktime++;
					drainPower();
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
					}
					this.markDirtyClient();
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
		if(isActive()){
			if(!isValidPreset() || isValidRecipe()){
				if(!soluteSlot().isEmpty() && isPurgeable()){
					if(this.output.canSetOrStack(soluteOutSlot(), soluteSlot())){
						this.output.setOrStack(SOLUTE_OUT_SLOT, soluteSlot());
						this.input.setStackInSlot(SOLUTE_SLOT, ItemStack.EMPTY);
					}
				}
				if(!catalystSlot().isEmpty() && !recipeSolute().isEmpty()){
					if(!catalystSlot().isItemEqualIgnoreDurability(recipeCatalyst()) ){
						if(this.output.canSetOrStack(catalystOutSlot(), catalystSlot())){
							this.output.setOrStack(CATALYST_OUT_SLOT, catalystSlot());
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
			if(!inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getRecipeIndex()).getOredict()))){
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
			&& hasSeparator()
			&& handleSolute()
			&& handleCatalyst()
			&& handleSolvent()
			&& handleReagent()
			&& handleSolution()
			&& handleByproduct()
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
	}

	private boolean handleSolute() {
		if(isValidRecipe()){
			if(!soluteSlot().isEmpty()){
				if(getCurrentRecipe().getType()){
					ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(soluteSlot()));
					if(!inputOreIDs.isEmpty()){
						if(!Strings.isNullOrEmpty(recipeList().get(getRecipeIndex()).getOredict())){  
							if(inputOreIDs.contains(OreDictionary.getOreID(recipeList().get(getRecipeIndex()).getOredict()))){
								return true;
							}
						}
					}
				}else{
					return !recipeSolute().isEmpty() ? recipeList().get(getRecipeIndex()).getSolute().isItemEqual(soluteSlot()) : true;
				}
			}
		}
		return false;
	}

	private boolean handleCatalyst() {
		return !recipeCatalyst().isEmpty() ? isValidCatalyst(catalystSlot()) || CoreUtils.isMatchingIngredient(catalystSlot(), recipeCatalyst()) : true;
	}

	private boolean handleSolvent() {
		return recipeSolvent() != null && hasIntank() && this.input.canDrainFluid(getIntank().getSolventFluid(), recipeSolvent());
	}

	private boolean handleReagent() {
		return recipeReagent() == null || (recipeReagent() != null && hasIntank() && this.input.canDrainFluid(getIntank().getReagentFluid(), recipeReagent()));
	}

	private boolean handleSolution() {
		return recipeSolution() != null && hasOuttank() && this.input.canSetOrFillFluid(getOuttank().solutionTank, getOuttank().getSolutionFluid(), recipeSolution());
	}
	
	private boolean handleByproduct() {
		return recipeByproduct() == null || (recipeByproduct() != null && hasOuttank() && this.input.canSetOrFillFluid(getOuttank().byproductTank, getOuttank().getByproductFluid(), recipeByproduct()));
	}

	private void process() {
		if(hasIntank()){
			if(recipeSolvent() != null){
				getIntank().solventTank.drainInternal(recipeSolvent(), true);
			}
	
			if(recipeReagent() != null){
				getIntank().reagentTank.drainInternal(recipeReagent(), true);
			}
		}

		if(hasOuttank()){
			if(recipeSolution() != null){
				getOuttank().solutionTank.fillInternal(recipeSolution(), true);
			}
	
			if(recipeByproduct() != null){
				getOuttank().byproductTank.fillInternal(recipeByproduct(), true);
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

		updateServer(hasServer(), getServer(), this.currentFile);
	}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(!fileSlot.isEmpty() && fileSlot.isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()))){
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