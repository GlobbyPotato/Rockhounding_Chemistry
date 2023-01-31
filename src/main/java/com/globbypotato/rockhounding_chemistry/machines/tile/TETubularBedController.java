package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.BedReactorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedLow;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedMid;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;

public class TETubularBedController extends TileEntityInv implements IInternalServer{

	public static int templateSlots = 3;
	public static int upgradeSlots = 1;

	public static final int SPEED_SLOT = 0;

	public int currentFile = -1;
	public boolean isRepeatable = false;
	private int countCat;

	public BedReactorRecipe dummyRecipe;

	public TETubularBedController() {
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
		return "tubular_bed_controller";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedBedReactor / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedBedReactor;
	}

	private static int deviceCode() {
		return EnumServer.BED_REACTOR.ordinal();
	}

	@Override
	public BlockPos poweredPosition(){
		return chamberPos().offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 270);
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<BedReactorRecipe> recipeList(){
		return BedReactorRecipes.bed_reactor_recipes;
	}

	public BedReactorRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public BedReactorRecipe getCurrentRecipe(){
		if(isActive() && isValidPreset()){
			if(hasCatalysts()) {
				if(handleMainInput() && handleInput() && handleOutput()){
					return getRecipeList(getSelectedRecipe());
				}
			}
		}
		return null;
	}

	public BedReactorRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	private boolean hasCatalysts() {
		if(hasTubularCatalysts()) {
			for(int x = 0; x < getTubularCatalysts().SLOT_INPUTS.length; x++) {
				if(getTubularCatalysts().catalystSlot(x).isItemEqualIgnoreDurability(getRecipeCatalyst())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean handleMainInput() {
		return verifyMainInput(hasInput1Tank(), getRecipeInput1(), tankInput1(), getRecipeInput1() != null ? calculatedAmount(getRecipeInput1().amount) : 0, true);
	}

	private boolean handleInput() {
		return verifyInput(hasInput2Tank(), getRecipeInput2(), tankInput2(), getRecipeInput2() != null ? calculatedAmount(getRecipeInput2().amount) : 0, false)
			&& verifyInput(hasInput3Tank(), getRecipeInput3(), tankInput3(), getRecipeInput3() != null ? calculatedAmount(getRecipeInput3().amount) : 0, false)
			&& verifyInput(hasInput4Tank(), getRecipeInput4(), tankInput4(), getRecipeInput4() != null ? calculatedAmount(getRecipeInput4().amount) : 0, false);
	}

	private boolean verifyMainInput(boolean tank, FluidStack recipeInput, FluidStack tankInput, int drain, boolean isMain) {
		if(tank) {
			if(this.input.canDrainFluid(tankInput, recipeInput, drain)) {
				return true;
			}
		}
		return false;
	}

	private boolean verifyInput(boolean tank, FluidStack recipeInput, FluidStack tankInput, int drain, boolean isMain) {
		if(tank && recipeInput != null) {
			if(this.input.canDrainFluid(tankInput, recipeInput, drain)) {
				return true;
			}
		}
		return recipeInput == null && tankInput == null;
	}

	private boolean handleOutput() {
		return getRecipeOutput() != null 
			&& hasOutTank() 
			&& this.output.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), calculatedAmount(getRecipeOutput().amount));	
	}

	private int calculatedAmount(int amount) {
		int multi = 0;
		for(int x = 0; x < getTubularCatalysts().SLOT_INPUTS.length; x++) {
			if(getTubularCatalysts().catalystSlot(x).isItemEqualIgnoreDurability(getRecipeCatalyst())) {
				multi++;
			}
		}
		return multi * amount;
	}

	public int catSize() {
		int multi = 0;
		if(this.hasCatalysts()) {
			for(int x = 0; x < getTubularCatalysts().SLOT_INPUTS.length; x++) {
				if(getTubularCatalysts().catalystSlot(x).isItemEqualIgnoreDurability(getRecipeCatalyst())) {
					multi++;
				}
			}
		}
		return multi;
	}

	public FluidStack getRecipeInput1(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInput1() : null; }
	public FluidStack getRecipeInput2(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInput2() : null; }
	public FluidStack getRecipeInput3(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInput3() : null; }
	public FluidStack getRecipeInput4(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInput4() : null; }
	public FluidStack getRecipeOutput(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getOutput() : null; }
	public ItemStack getRecipeCatalyst(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getCatalyst() : ItemStack.EMPTY; }



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, chamberPos().offset(isFacingAt(90)), isFacingAt(270));
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
		return this.pos.down(1);		
	}

	public TETubularBedTank getChamber(){
		TileEntity te = this.world.getTileEntity(chamberPos());
		if(this.world.getBlockState(chamberPos()) != null && te instanceof TETubularBedTank){
			TETubularBedTank tank = (TETubularBedTank)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasChamber(){
		return getChamber() != null;
	}

//tubular base
	public BlockPos tubularBasePos(){
		return chamberPos().offset(getFacing(), 1);		
	}

	public TETubularBedBase getTubularBase(){
		TileEntity te = this.world.getTileEntity(tubularBasePos());
		if(this.world.getBlockState(tubularBasePos()) != null && te instanceof TETubularBedBase){
			TETubularBedBase tank = (TETubularBedBase)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

//tubular low -catalysts
	public TETubularBedLow getTubularCatalysts(){
		TileEntity te = this.world.getTileEntity(tubularBasePos().up());
		if(this.world.getBlockState(tubularBasePos()) != null && te instanceof TETubularBedLow){
			TETubularBedLow tank = (TETubularBedLow)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

//tubular mid
	public TETubularBedMid getTubularMid(){
		BlockPos tubularMidPos = tubularBasePos().up(2);
		TileEntity te = this.world.getTileEntity(tubularMidPos);
		if(this.world.getBlockState(tubularMidPos) != null && te instanceof TETubularBedMid){
			TETubularBedMid tank = (TETubularBedMid)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

//tubular top
	public TETubularBedTop getTubularTop(){
		BlockPos tubularTopPos = tubularBasePos().up(3);
		TileEntity te = this.world.getTileEntity(tubularTopPos);
		if(this.world.getBlockState(tubularTopPos) != null && te instanceof TETubularBedTop){
			TETubularBedTop tank = (TETubularBedTop)te;
			if(tank.getFacing() == getFacing()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasTubularCatalysts(){
		return getTubularCatalysts() != null;
	}

	public boolean hasTubularSystem(){
		return getTubularBase() != null && getTubularCatalysts() != null && getTubularMid() != null && getTubularTop() != null;
	}

//router
	public BlockPos routerPos(){
		return tubularBasePos().up(4);
	}

	public Block getRouter1(){
		Block router = TileStructure.getStructure(this.world, routerPos(), EnumMiscBlocksA.GAS_ROUTER.ordinal());
		return router != null ? router : null;
	}

	public boolean hasRouter(){
		return getRouter1() != null;
	}

//centrifuge
	public TECentrifuge getCentrifuge1(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos().offset(getFacing().getOpposite()), getFacing());
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getCentrifuge2(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos().offset(isFacingAt(270)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getCentrifuge3(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos().offset(getFacing()), getFacing().getOpposite());
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getCentrifuge4(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, routerPos().offset(isFacingAt(90)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getCentrifuge5(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, router2Pos().offset(getFacing().getOpposite()), getFacing().getOpposite());
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getCentrifuge6(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, router2Pos().offset(getFacing()), getFacing());
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getCentrifuge1() != null && getCentrifuge2() != null && getCentrifuge3() != null && getCentrifuge4() != null && getCentrifuge5() != null && getCentrifuge6() != null;
	}


//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, router2Pos().offset(getFacing().getOpposite(), 2), getFacing().getOpposite());
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}

	public FluidStack outGas(){
		return hasOutTank() ? getOutTank().inputTank.getFluid() : null;
	}

//purge vessel
	public TileVessel getPurgeTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, router2Pos().offset(getFacing(), 2), getFacing());
		return vessel != null ? vessel : null;
	}

	public boolean hasPurgeTank(){
		return getPurgeTank() != null;
	}

	public FluidStack purgeGas(){
		return hasPurgeTank() ? getPurgeTank().inputTank.getFluid() : null;
	}

//input 1 vessel
	public TileVessel getInput1Tank(){
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos().offset(getFacing().getOpposite(), 2), getFacing());
		return vessel != null ? vessel : null;
	}

	public boolean hasInput1Tank(){
		return getInput1Tank() != null;
	}

	public FluidStack tankInput1(){
		return hasInput1Tank() ? getInput1Tank().inputTank.getFluid() : null;
	}

//input 2 vessel
	public TileVessel getInput2Tank(){
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos().offset(isFacingAt(270), 2), isFacingAt(90));
		return vessel != null ? vessel : null;
	}

	public boolean hasInput2Tank(){
		return getInput2Tank() != null;
	}

	public FluidStack tankInput2(){
		return hasInput2Tank() ? getInput2Tank().inputTank.getFluid() : null;
	}

//input 3 vessel
	public TileVessel getInput3Tank(){
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos().offset(getFacing(), 2), getFacing().getOpposite());
		return vessel != null ? vessel : null;
	}

	public boolean hasInput3Tank(){
		return getInput3Tank() != null;
	}

	public FluidStack tankInput3(){
		return hasInput3Tank() ? getInput3Tank().inputTank.getFluid() : null;
	}

//input 4 vessel
	public TileVessel getInput4Tank(){
		TileVessel vessel = TileStructure.getHolder(this.world, routerPos().offset(isFacingAt(90), 2), isFacingAt(270));
		return vessel != null ? vessel : null;
	}

	public boolean hasInput4Tank(){
		return getInput4Tank() != null;
	}

	public FluidStack tankInput4(){
		return hasInput4Tank() ? getInput4Tank().inputTank.getFluid() : null;
	}

//separator
	public BlockPos separatorPos(){
		return tubularBasePos().offset(isFacingAt(270));
	}

	public Block getSeparator(){
		Block separator = TileStructure.getStructure(this.world, separatorPos(), EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public BlockPos router2Pos(){
		return separatorPos().offset(isFacingAt(270));
	}

	public Block getRouter2(){
		BlockPos routerPos = separatorPos().offset(isFacingAt(270));
		Block router = TileStructure.getStructure(this.world, routerPos, EnumMiscBlocksA.GAS_ROUTER.ordinal());
		return router != null ? router : null;
	}

	public TEAuxiliaryEngine getPressurizer1(){
		BlockPos pressPos = chamberPos().offset(isFacingAt(270));
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing());
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer2(){
		BlockPos pressPos = chamberPos().offset(isFacingAt(270), 3).offset(getFacing());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, isFacingAt(90));
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer3(){
		BlockPos pressPos = chamberPos().offset(isFacingAt(270)).offset(getFacing(), 2);
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasSeparators(){
		return getSeparator() != null && getRouter2() != null && getPressurizer1() != null && getPressurizer2() != null  && getPressurizer3() != null;
	}

//unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = chamberPos().offset(getFacing(), 2);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, chamberPos().offset(getFacing(), 3), getFacing().getOpposite());
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return hasChamber() && hasTubularSystem() && hasRouter() && hasCentrifuges() && hasUnloader() && hasSeparators();
	}



	//----------------------- CUSTOM -----------------------
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
			if(isValidPreset()){
				if(hasInput1Tank()){
					if(getInput1Tank().getFilter() != getRecipeInput1()){
						getInput1Tank().filter = getRecipeInput1();
					}
				}
				if(hasInput2Tank()){
					if(getInput2Tank().getFilter() != getRecipeInput2()){
						getInput2Tank().filter = getRecipeInput2();
					}
					if(getRecipeInput2() == null) {
						getInput2Tank().filter = thin_air();
					}
				}
				if(hasInput3Tank()){
					if(getInput3Tank().getFilter() != getRecipeInput3()){
						getInput3Tank().filter = getRecipeInput3();
					}
					if(getRecipeInput3() == null) {
						getInput3Tank().filter = thin_air();
					}
				}
				if(hasInput4Tank()){
					if(getInput4Tank().getFilter() != getRecipeInput4()){
						getInput4Tank().filter = getRecipeInput4();
					}
					if(getRecipeInput4() == null) {
						getInput4Tank().filter = thin_air();
					}
				}
			}else{
				if(hasInput1Tank()){
					if(getInput1Tank().getFilter() != null){
						getInput1Tank().filter = null;
					}
				}
				if(hasInput2Tank()){
					if(getInput2Tank().getFilter() != null){
						getInput2Tank().filter = null;
					}
				}
				if(hasInput3Tank()){
					if(getInput3Tank().getFilter() != null){
						getInput3Tank().filter = null;
					}
				}
				if(hasInput4Tank()){
					if(getInput4Tank().getFilter() != null){
						getInput4Tank().filter = null;
					}
				}
			}
		}

		public FluidStack thin_air() {
			return new FluidStack(ModFluids.THIN_AIR, 1000);
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
			&& isAssembled()
			&& isValidRecipe()
			&& hasFuelPower()
			&& hasRedstonePower()
			&& handleServer(getServer(), this.currentFile); //server;
	}

	private void process() {
		if(isValidRecipe()){
			if(hasOutTank()){
				this.output.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), calculatedAmount(getRecipeOutput().amount));
				getOutTank().updateNeighbours();
			}

			if(hasTubularCatalysts()){
				for(int cats = 0; cats < getTubularCatalysts().SLOT_INPUTS.length; cats++){
					if(getTubularCatalysts().catalystSlot(cats).isItemEqualIgnoreDurability(getRecipeCatalyst())) {

						damageOrRepairConsumable(cats);

					}
				}
			}

			if(hasInput1Tank() && getRecipeInput1() != null){
				this.input.drainOrCleanFluid(getInput1Tank().inputTank, calculatedAmount(getRecipeInput1().amount), true);
				getInput1Tank().updateNeighbours();
			}

			if(hasInput2Tank() && getRecipeInput2() != null){
				this.input.drainOrCleanFluid(getInput2Tank().inputTank, calculatedAmount(getRecipeInput2().amount), true);
				getInput2Tank().updateNeighbours();
			}

			if(hasInput3Tank() && getRecipeInput3() != null){
				this.input.drainOrCleanFluid(getInput3Tank().inputTank, calculatedAmount(getRecipeInput3().amount), true);
				getInput3Tank().updateNeighbours();
			}

			if(hasInput4Tank() && getRecipeInput4() != null){
				this.input.drainOrCleanFluid(getInput4Tank().inputTank, calculatedAmount(getRecipeInput4().amount), true);
				getInput4Tank().updateNeighbours();
			}

			updateServer(getServer(), this.currentFile);
		}

		this.dummyRecipe = null;
	}

	private void damageOrRepairConsumable(int cats) {
		int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getTubularCatalysts().catalystSlot(cats));
		((MachineStackHandler)getTubularCatalysts().getInput()).damageUnbreakingSlot(unbreakingLevel, cats);

		if(CoreUtils.hasMending(getTubularCatalysts().catalystSlot(cats)) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
			((MachineStackHandler)getTubularCatalysts().getInput()).repairMendingSlot(cats);
		}
	}

	private void handlePurge() {
		if(isActive()){
			if(!isValidPreset() || !isValidRecipe()){
				if(hasPurgeTank()){
					if(hasInput1Tank()){
						if(isWrongInput1() && canPurge1()){
							int canReceive = getPurgeTank().inputTank.getCapacity() - getPurgeTank().inputTank.getFluidAmount();
							int canSend = Math.min(getInput1Tank().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeTank().inputTank, tankInput1(), canSend);
							this.input.drainOrCleanFluid(getInput1Tank().inputTank, canSend, true);

							getPurgeTank().updateNeighbours();
							getInput1Tank().updateNeighbours();
						}
					}
					if(hasInput2Tank()){
						if(isWrongInput2() && canPurge2()){
							int canReceive = getPurgeTank().inputTank.getCapacity() - getPurgeTank().inputTank.getFluidAmount();
							int canSend = Math.min(getInput2Tank().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeTank().inputTank, tankInput2(), canSend);
							this.input.drainOrCleanFluid(getInput2Tank().inputTank, canSend, true);

							getPurgeTank().updateNeighbours();
							getInput2Tank().updateNeighbours();
						}
					}
					if(hasInput3Tank()){
						if(isWrongInput3() && canPurge3()){
							int canReceive = getPurgeTank().inputTank.getCapacity() - getPurgeTank().inputTank.getFluidAmount();
							int canSend = Math.min(getInput3Tank().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeTank().inputTank, tankInput3(), canSend);
							this.input.drainOrCleanFluid(getInput3Tank().inputTank, canSend, true);

							getPurgeTank().updateNeighbours();
							getInput3Tank().updateNeighbours();
						}
					}
					if(hasInput4Tank()){
						if(isWrongInput4() && canPurge4()){
							int canReceive = getPurgeTank().inputTank.getCapacity() - getPurgeTank().inputTank.getFluidAmount();
							int canSend = Math.min(getInput4Tank().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeTank().inputTank, tankInput4(), canSend);
							this.input.drainOrCleanFluid(getInput4Tank().inputTank, canSend, true);

							getPurgeTank().updateNeighbours();
							getInput4Tank().updateNeighbours();
						}
					}
				}
				
				if(hasTubularCatalysts() && hasUnloader()) {
					if(countCat < getTubularCatalysts().SLOT_INPUTS.length){
						if(!getTubularCatalysts().catalystSlot(countCat).isEmpty()){
							if(getRecipeCatalyst().isEmpty() || (!getTubularCatalysts().catalystSlot(countCat).isItemEqualIgnoreDurability(getRecipeCatalyst()))){
								if(((MachineStackHandler) getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), getTubularCatalysts().catalystSlot(countCat))){
									((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, getTubularCatalysts().catalystSlot(countCat));
									((ItemStackHandler) getTubularCatalysts().getInput()).setStackInSlot(countCat, ItemStack.EMPTY);
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
	}

				private boolean isWrongInput1() {
					return tankInput1() != null
						&& (getRecipeInput1() == null || (getRecipeInput1() != null && !getRecipeInput1().isFluidEqual(tankInput1())));
				}

				private boolean isWrongInput2() {
					return tankInput2() != null
						&& (getRecipeInput2() == null || (getRecipeInput2() != null && !getRecipeInput2().isFluidEqual(tankInput2())));
				}

				private boolean isWrongInput3() {
					return tankInput3() != null
						&& (getRecipeInput3() == null || (getRecipeInput3() != null && !getRecipeInput3().isFluidEqual(tankInput3())));
				}

				private boolean isWrongInput4() {
					return tankInput4() != null
						&& (getRecipeInput4() == null || (getRecipeInput4() != null && !getRecipeInput4().isFluidEqual(tankInput4())));
				}

				private boolean canPurge1() {
					return tankInput1() != null && this.input.canSetOrFillFluid(getPurgeTank().inputTank, purgeGas(), tankInput1());
				}

				private boolean canPurge2() {
					return tankInput2() != null && this.input.canSetOrFillFluid(getPurgeTank().inputTank, purgeGas(), tankInput2());
				}

				private boolean canPurge3() {
					return tankInput3() != null && this.input.canSetOrFillFluid(getPurgeTank().inputTank, purgeGas(), tankInput3());
				}

				private boolean canPurge4() {
					return tankInput4() != null && this.input.canSetOrFillFluid(getPurgeTank().inputTank, purgeGas(), tankInput4());
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