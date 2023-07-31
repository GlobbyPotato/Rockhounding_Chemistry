package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

public class TEReformerController extends TileEntityInv implements IInternalServer{

	public static final int SPEED_SLOT = 0;

	public static int upgradeSlots = 1;
	public static int templateSlots = 4;

	public int currentFile = -1;
	public boolean isRepeatable = false;
	public boolean direction = false;
	private int countCat;

	public int[] catList = new int[5];

	public GasReformerRecipe dummyRecipe;

	public TEReformerController() {
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
		this.direction = compound.getBoolean("Direction");
		if(compound.hasKey("Catalysts")){
			NBTTagCompound elements = compound.getCompoundTag("Catalysts");
			for(int i = 0; i < this.catList.length; i++){
				this.catList[i] = elements.getInteger("catalyst" + i);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		NBTTagCompound elements = new NBTTagCompound();
		for(int i = 0; i < this.catList.length; i++){
			elements.setInteger("catalyst" + i, this.catList[i]);
		}
		compound.setTag("Catalysts", elements);
		compound.setBoolean("Direction", getDirection());
		return compound;
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
		return "reformer_controller";
	}

	public int getCooktimeMax(){
		return 60;
	}

	private static int deviceCode() {
		return EnumServer.REFORMER.ordinal();
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.UP, 3).offset(getFacing(), 1).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return isFacingAt(90);
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<GasReformerRecipe> recipeList(){
		return GasReformerRecipes.gas_reformer_recipes;
	}

	public GasReformerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public GasReformerRecipe getCurrentRecipe(){
		if(isActive() && isValidPreset() && hasCatalysts()){
			if(handleInput() && handleOutput()){
				return getRecipeList(getSelectedRecipe());
			}
		}
		return null;
	}

	public GasReformerRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public FluidStack getRecipeInputA(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInputA() : null; }
	public FluidStack getRecipeInputB(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInputB() : null; }
	public FluidStack getRecipeOutput(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getOutput() : null; }
	public ItemStack getRecipeCatalyst(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getCatalyst() : ItemStack.EMPTY; }

	public boolean isOutputGaseous(){
		return getRecipeOutput() != null
			&& getRecipeOutput().getFluid().isGaseous();
	}



	//----------------------- CATALYST -----------------------
	public ItemStack catalystA(){ return BaseRecipes.nl_catalyst.copy();}
	public ItemStack catalystB(){ return BaseRecipes.gr_catalyst.copy();}
	public ItemStack catalystC(){ return BaseRecipes.wg_catalyst.copy();}
	public ItemStack catalystD(){ return BaseRecipes.au_catalyst.copy();}

	public boolean hasSystemCatalysts() {
		this.catList[0] = 0;
		this.catList[1] = 0;
		this.catList[2] = 0;
		for(int x = 0; x < TEReformerReactor.SLOT_SYSTEM_CAT.length; x++){
			ItemStack systemSlot = getReactor().getInput().getStackInSlot(TEReformerReactor.SLOT_SYSTEM_CAT[x]);
			if(isSystemCatalyst(systemSlot)){
				if(systemSlot.isItemEqualIgnoreDurability(catalystA())){
					this.catList[0]++;
				}else if(systemSlot.isItemEqualIgnoreDurability(catalystB())){
					this.catList[1]++;
				}else if(systemSlot.isItemEqualIgnoreDurability(catalystC())){
					this.catList[2]++;
				}
			}
		}
		return this.catList[0] == 6 || this.catList[1] == 6 || this.catList[2] == 6;
	}

	public int systemCatalystFactor(){
		if(this.catList[0] == 6){
			return 1;
		}else if(this.catList[1] == 6){
			return 2;
		}else if(this.catList[2] == 6){
			return 3;
		}else{
			return 0;
		}
	}

	public boolean hasRecipeCatalysts() {
		this.catList[3] = 0;
		this.catList[4] = 0;
		for(int x = 0; x < TEReformerReactor.SLOT_RECIPE_CAT.length; x++){
			ItemStack recipeSlot = getReactor().getInput().getStackInSlot(TEReformerReactor.SLOT_RECIPE_CAT[x]);
			if(isRecipeCatalyst(recipeSlot)){
				if(recipeSlot.isItemEqualIgnoreDurability(catalystD())){
					this.catList[3]++;
				}else if(recipeSlot.isItemEqualIgnoreDurability(getRecipeCatalyst())){
					this.catList[4]++;
				}
			}
		}
		return this.catList[3] == 4 || this.catList[4] == 4;
	}

	public int recipeCatalystFactor(){
		if(this.catList[3] == 4){
			return 1;
		}else if(this.catList[4] == 4){
			return 2;
		}else{
			return 0;
		}
	}

	public boolean isSystemCatalyst(ItemStack insertingStack) {
		return CoreUtils.hasConsumable(catalystA(), insertingStack)
			|| CoreUtils.hasConsumable(catalystB(), insertingStack)
			|| CoreUtils.hasConsumable(catalystC(), insertingStack);
	}

	public boolean isRecipeCatalyst(ItemStack insertingStack) {
		return insertingStack.isItemEqualIgnoreDurability(getRecipeCatalyst())
			|| insertingStack.isItemEqualIgnoreDurability(catalystD());
	}

	public boolean hasCatalysts(){
		return hasReactor() && hasSystemCatalysts() && systemCatalystFactor() > 0 && hasRecipeCatalysts() && recipeCatalystFactor() > 0;
	}

	public int inputTier(){
		return hasReactor() ? systemCatalystFactor() : 0;
	}

	public int outputTier(){
		return hasReactor() ? recipeCatalystFactor() : 0;
	}

	public boolean getDirection() {
		return this.direction;
	}



	//----------------------- CUSTOM -----------------------
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
			if(hasInputVessel1()){
				if(getInputVessel1().getFilter() != getRecipeInputA()){
					getInputVessel1().filter = getRecipeInputA();
				}
			}
			if(hasInputVessel2()){
				if(getInputVessel2().getFilter() != getRecipeInputB()){
					getInputVessel2().filter = getRecipeInputB();
				}
			}
		}else{
			if(hasInputVessel1()){
				if(getInputVessel1().getFilter() != null){
					getInputVessel1().filter = null;
				}
			}
			if(hasInputVessel1()){
				if(getInputVessel2().getFilter() != null){
					getInputVessel2().filter = null;
				}
			}
		}
	}

	public int powerConsume() {
		int baseConsume = 60 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int baseDrainA() {
		return getRecipeInputA() != null ? getRecipeInputA().amount : 0;
	}

	public int calculatedDrainA() {
		return hasReactor() ? ((4 - systemCatalystFactor()) * baseDrainA()) * speedFactor() : 0;
	}

	public int baseDrainB() {
		return getRecipeInputB() != null ? getRecipeInputB().amount : 0;
	}

	public int calculatedDrainB() {
		return hasReactor() ? ((4 - systemCatalystFactor()) * baseDrainB()) * speedFactor() : 0;
	}

	public int baseProduct() {
		return getRecipeOutput() != null ? getRecipeOutput().amount : 0;
	}

	public int calculatedProduct() {
		return hasReactor() ? (recipeCatalystFactor() * baseProduct()) * speedFactor() : 0;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(isFacingAt(90)), isFacingAt(270));
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() >= powerConsume() : false;
	}

	private void drainPower() {
		getEngine().redstoneCount-= powerConsume();
		getEngine().markDirtyClient();
	}

//reactor
	public BlockPos reactorPos(){
		return this.pos.offset(EnumFacing.UP, 1);
	}

	public TEReformerReactor getReactor(){
		BlockPos reactorPos = this.pos.offset(EnumFacing.UP, 1);
		TileEntity te = this.world.getTileEntity(reactorPos);
		if(this.world.getBlockState(reactorPos) != null && te instanceof TEReformerReactor){
			TEReformerReactor reactor = (TEReformerReactor)te;
			if(reactor.getFacing() == getFacing()){
				return reactor;
			}
		}
		return null;
	}

	public boolean hasReactor(){
		return getReactor() != null;
	}

//tower
	public boolean hasTower(){
		//tower
		int countTower = 0;
		for (int x = 2; x <= 4; x++){
			BlockPos tPos = new BlockPos(this.pos.getX(), this.pos.getY() + x, this.pos.getZ());
			Block tower = TileStructure.getStructure(this.world, tPos, EnumMiscBlocksA.REFORMER_TOWER.ordinal());
			if(tower != null) {
				countTower++;
			}
		}
		//towercap
		BlockPos tPos = new BlockPos(this.pos.getX(), this.pos.getY() + 5, this.pos.getZ());
		Block tower = TileStructure.getStructure(this.world, tPos, EnumMiscBlocksA.REFORMER_TOWER_TOP.ordinal());
		if(tower != null) {
			countTower++;
		}

		return countTower == 4;
	}

//separators
	public Block getSeparator(){
		Block separator = TileStructure.getStructure(this.world, this.pos.offset(getFacing(), 1), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public Block getSeparator2(){
		Block separator = TileStructure.getStructure(this.world, this.pos.offset(getFacing(), 3), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparators(){
		return getSeparator() != null && getSeparator2() != null;
	}

//routers
	public BlockPos routerPos1(){
		return this.pos.offset(getFacing(), 2);
	}

	public BlockPos routerPos2(){
		return this.pos.offset(getFacing(), 4);
	}

	public Block getRouter1(){
		IBlockState sepState = this.world.getBlockState(routerPos1());
		Block separator = sepState.getBlock();
		if(MachineIO.miscBlocksA(separator, sepState, EnumMiscBlocksA.GAS_ROUTER.ordinal())){
			return separator;
		}
		return null;
	}

	public Block getRouter2(){
		IBlockState sepState = this.world.getBlockState(routerPos2());
		Block separator = sepState.getBlock();
		if(MachineIO.miscBlocksA(separator, sepState, EnumMiscBlocksA.GAS_ROUTER.ordinal())){
			return separator;
		}
		return null;
	}

	public boolean hasRouters(){
		return getRouter1() != null && getRouter2() != null;
	}

//Unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(getFacing(), 5);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//centrifuge
	public TECentrifuge getInputCentrifuge1(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos1().offset(isFacingAt(270)), directionalFacing);
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getInputCentrifuge2(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos2().offset(isFacingAt(270)), directionalFacing);
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getOutputCentrifuge1(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos1().offset(isFacingAt(90)), directionalFacing);
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getOutputCentrifuge2(){
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			directionalFacing = isFacingAt(270);
		}
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos2().offset(isFacingAt(90)), directionalFacing);
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getInputCentrifuge1() != null && getInputCentrifuge2() != null && getOutputCentrifuge1() != null && getOutputCentrifuge2() != null;
	}

//main pressurizer
	public TEAuxiliaryEngine getPressurizer1(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing(), 6), getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}
	public TEAuxiliaryEngine getPressurizer2(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing(), 1).offset(isFacingAt(90)), isFacingAt(270));
		return pressurizer != null ? pressurizer : null;
	}
	public TEAuxiliaryEngine getPressurizer3(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing(), 1).offset(isFacingAt(270)), isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}
	public TEAuxiliaryEngine getPressurizer4(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing(), 3).offset(isFacingAt(90)), isFacingAt(270));
		return pressurizer != null ? pressurizer : null;
	}
	public TEAuxiliaryEngine getPressurizer5(){
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, this.pos.offset(getFacing(), 3).offset(isFacingAt(270)), isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getPressurizer1() != null && getPressurizer2() != null && getPressurizer3() != null && getPressurizer4() != null && getPressurizer5() != null;
	}

//cyclone base
	public boolean hasCycloneSeparator(){
		return TileStructure.getCycloneSeparator(this.world, this.pos.offset(EnumFacing.UP).offset(getFacing()), getFacing().getOpposite());
	}

//output tank
	public TEBufferTank getTank(){
		BlockPos tankPos = this.pos.offset(getFacing(), 5).offset(EnumFacing.UP);
		TEBufferTank tank = TileStructure.getBufferTank(this.world, tankPos);
		return tank != null ? tank : null;
	}

	public boolean hasTank(){
		return getTank() != null;
	}

//input vessel
	public TileVessel getInputVessel1(){
		EnumFacing positionFacing = isFacingAt(270);
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			positionFacing = isFacingAt(90);
			directionalFacing = isFacingAt(270);
		}
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos1().offset(positionFacing, 2), directionalFacing);
		return vessel != null ? vessel : null;
	}

	public TileVessel getInputVessel2(){
		EnumFacing positionFacing = isFacingAt(270);
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			positionFacing = isFacingAt(90);
			directionalFacing = isFacingAt(270);
		}
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos2().offset(positionFacing, 2), directionalFacing);
		return vessel != null ? vessel : null;
	}

	public boolean hasInputVessel1(){
		return getInputVessel1() != null;
	}

	public boolean hasInputVessel2(){
		return getInputVessel2() != null;
	}

	public FluidStack vesselInput1(){
		return hasInputVessel1() ? getInputVessel1().inputTank.getFluid() : null;
	}

	public FluidStack vesselInput2(){
		return hasInputVessel2() ? getInputVessel2().inputTank.getFluid() : null;
	}

//output vessel
	public TileVessel getOutputVessel(){
		EnumFacing positionFacing = isFacingAt(90);
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			positionFacing = isFacingAt(270);
			directionalFacing = isFacingAt(270);
		}
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos1().offset(positionFacing, 2), directionalFacing);
		return vessel != null ? vessel : null;
	}

	public boolean hasOutputVessel(){
		return getOutputVessel() != null;
	}

	public FluidStack outGas(){
		return hasOutputVessel() ? getOutputVessel().inputTank.getFluid() : null;
	}

//purge vessel
	public TileVessel getPurgeVessel(){
		EnumFacing positionFacing = isFacingAt(90);
		EnumFacing directionalFacing = isFacingAt(90);
		if(getDirection()) {
			positionFacing = isFacingAt(270);
			directionalFacing = isFacingAt(270);
		}
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos2().offset(positionFacing, 2), directionalFacing);
		return vessel != null ? vessel : null;
	}

	public boolean hasPurgeVessel(){
		return getPurgeVessel() != null;
	}

	public FluidStack purgeGas(){
		return hasPurgeVessel() ? getPurgeVessel().inputTank.getFluid() : null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(90));
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return hasReactor() && hasTower() && hasRouters() && hasSeparators() && hasUnloader() 
			&& hasCentrifuges() && hasPressurizers() && hasCycloneSeparator();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			handlePurge();

			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, recipeList().size());

			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canProcess()){
				this.cooktime++;
				drainPower();
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				this.dummyRecipe = null;
				tickOff();
			}
		}
	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe()
			&& hasRedstonePower()
			&& isAssembled()
			&& handleServer(getServer(), this.currentFile); //server
	}


				private boolean handleInput() {
					return getRecipeInputA() != null && hasInputVessel1() && this.input.canDrainFluid(vesselInput1(), getRecipeInputA(), calculatedDrainA())
						&& getRecipeInputB() != null && hasInputVessel2() && this.input.canDrainFluid(vesselInput2(), getRecipeInputB(), calculatedDrainB());
				}

				private boolean handleOutput() {
					if(isOutputGaseous()){
						return getRecipeOutput() != null && hasOutputVessel() && this.input.canSetOrAddFluid(getOutputVessel().inputTank, getOutputVessel().inputTank.getFluid(), getRecipeOutput(), calculatedProduct());	
					}
					return getRecipeOutput() != null && hasTank() && this.input.canSetOrAddFluid(getTank().inputTank, getTank().inputTank.getFluid(), getRecipeOutput(), calculatedProduct());	
				}

	private void process() {

		if(isValidRecipe() && hasCatalysts()){
			if(isOutputGaseous()){
				if(hasOutputVessel()){
					this.input.setOrFillFluid(getOutputVessel().inputTank, getRecipeOutput(), calculatedProduct());
					getOutputVessel().updateNeighbours();
				}
			}else{
				if(hasTank()){
					this.input.setOrFillFluid(getTank().inputTank, getRecipeOutput(), calculatedProduct());
				}
			}

			if(hasReactor()){
				for(int cats = 0; cats < TEReformerReactor.totCatalysts; cats++){
					
					damageOrRepairConsumable(cats);

				}
			}

			if(hasInputVessel1()){
				this.input.drainOrCleanFluid(getInputVessel1().inputTank, calculatedDrainA(), true);
				getInputVessel1().updateNeighbours();
			}

			if(hasInputVessel2()){
				this.input.drainOrCleanFluid(getInputVessel2().inputTank, calculatedDrainB(), true);
				getInputVessel2().updateNeighbours();
			}

			updateServer(getServer(), this.currentFile);
		}
		
		this.dummyRecipe = null;

	}

	private void damageOrRepairConsumable(int cats) {
		int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getReactor().inputSlot(cats));
		((MachineStackHandler)getReactor().getInput()).damageUnbreakingSlot(unbreakingLevel, cats);

		if(CoreUtils.hasMending(getReactor().inputSlot(cats)) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
			((MachineStackHandler)getReactor().getInput()).repairMendingSlot(cats);
		}
	}

	private void handlePurge() {
		if(isActive()){
			if(!isValidPreset() || !isValidRecipe()){
				if(hasPurgeVessel()){
					if(hasInputVessel1()){
						if(isWrongInputA() && canPurgeA()){
							int canReceive = getPurgeVessel().inputTank.getCapacity() - getPurgeVessel().inputTank.getFluidAmount();
							int canSend = Math.min(getInputVessel1().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeVessel().inputTank, vesselInput1(), canSend);
							this.input.drainOrCleanFluid(getInputVessel1().inputTank, canSend, true);

							getPurgeVessel().updateNeighbours();
							getInputVessel1().updateNeighbours();
						}
					}
					if(hasInputVessel2()){
						if(isWrongInputB() && canPurgeB()){
							int canReceive = getPurgeVessel().inputTank.getCapacity() - getPurgeVessel().inputTank.getFluidAmount();
							int canSend = Math.min(getInputVessel2().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeVessel().inputTank, vesselInput2(), canSend);
							this.input.drainOrCleanFluid(getInputVessel2().inputTank, canSend, true);

							getPurgeVessel().updateNeighbours();
							getInputVessel2().updateNeighbours();
						}
					}
				}
				if(countCat < getReactor().SLOT_RECIPE_CAT.length){
					int inp = getReactor().SLOT_RECIPE_CAT[countCat];
					if(!getReactor().inputSlot(inp).isEmpty() && !getRecipeCatalyst().isEmpty()){
						if(!isRecipeCatalyst(getReactor().inputSlot(inp)) ){
							if(((MachineStackHandler)getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), getReactor().inputSlot(inp))){
								((MachineStackHandler)getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, getReactor().inputSlot(inp));
								((ItemStackHandler)getReactor().getInput()).setStackInSlot(inp, ItemStack.EMPTY);
								countCat++;
							}
						}
					}
				}else{
					countCat = 0;
				}

			}
		}
	}

				private boolean isWrongInputA() {
					return vesselInput1() != null
						&& (getRecipeInputA() == null || (getRecipeInputA() != null && !getRecipeInputA().isFluidEqual(vesselInput1())));
				}

				private boolean isWrongInputB() {
					return vesselInput2() != null
						&& (getRecipeInputB() == null || (getRecipeInputB() != null && !getRecipeInputB().isFluidEqual(vesselInput2())));
				}

				private boolean canPurgeA() {
					return vesselInput1() != null && this.input.canSetOrFillFluid(getPurgeVessel().inputTank, purgeGas(), vesselInput1());
				}

				private boolean canPurgeB() {
					return vesselInput2() != null && this.input.canSetOrFillFluid(getPurgeVessel().inputTank, purgeGas(), vesselInput2());
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

	public void flipResources() {
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 2).offset(isFacingAt(90), 1), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 2).offset(isFacingAt(270), 1), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 4).offset(isFacingAt(90), 1), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 4).offset(isFacingAt(270), 1), getFacing(), getDirection());

		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 2).offset(isFacingAt(90), 2), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 2).offset(isFacingAt(270), 2), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 4).offset(isFacingAt(90), 2), getFacing(), getDirection());
		TileStructure.flipTile(this.world, this.pos.offset(getFacing(), 4).offset(isFacingAt(270), 2), getFacing(), getDirection());
	}

}