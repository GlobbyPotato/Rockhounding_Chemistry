package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCabinet;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTransmission;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TEMineralSizerController extends TileEntityInv implements IInternalServer{
	public static final int SPEED_SLOT = 0;

	public static int inputSlots = 1;
	public static int templateSlots = 3;
	public static int upgradeSlots = 1;

	public ArrayList<ItemStack> resultList = new ArrayList<ItemStack>();

	public int comminution;
	public ItemStack filter = ItemStack.EMPTY;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TEMineralSizerController() {
		super(inputSlots, 0, templateSlots, upgradeSlots);
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isValidInput(insertingStack) ){
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
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.comminution = compound.getInteger("Comminution");
		if(compound.hasKey("Filter")){
			this.filter = new ItemStack(compound.getCompoundTag("Filter"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Comminution", getComminution());
		if(!this.getFilter().isEmpty()){
			NBTTagCompound filterstack = new NBTTagCompound(); 
			this.filter.writeToNBT(filterstack);
			compound.setTag("Filter", filterstack);
		}

		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
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
		return "mineral_sizer_controller";
	}

	public int baseSpeed(){
		return ModConfig.speedSizer + (getComminution() * comminutionFactor());
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax(){
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? baseSpeed() / ModUtils.speedUpgrade(speedSlot()): baseSpeed();
	}

	public int comminutionFactor(){
		return 40;
	}

	private static int sizedQuantity() {
		return ModConfig.maxSizeable > 0 ? ModConfig.maxSizeable : 1;
	}

	private static int deviceCode() {
		return EnumServer.SIZER.ordinal();
	}

	@Override
	public BlockPos poweredPosition(){
		return this.pos.offset(EnumFacing.DOWN).offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<MineralSizerRecipe> recipeList(){
		return MineralSizerRecipes.mineral_sizer_recipes;
	}

	public MineralSizerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			if(!getFilter().isEmpty()){
				if(getFilter().isItemEqual(stack)){
					return true;
				}
			}else{
				for(MineralSizerRecipe recipe: recipeList()){
					if(recipe.getType()){
						ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
						if(!inputOreIDs.isEmpty()){
							if(inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict()))){
								return true;
							}
						}
					}else{
						if(recipe.getInput().isItemEqual(stack)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public MineralSizerRecipe getCurrentRecipe(){
		if(!inputSlot().isEmpty()){
			for(int x = 0; x < recipeList().size(); x++){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
				if(getRecipeList(x).getType()){
					if(inputOreIDs.contains(OreDictionary.getOreID(getRecipeList(x).getOredict()))){
						return getRecipeList(x);
					}
				}else{
					if(getRecipeList(x).getInput().isItemEqual(inputSlot())){
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

	public ArrayList<ItemStack> recipeOutput(){
		return isValidRecipe() ? getCurrentRecipe().getOutput() : null;
	}

	public ArrayList<Integer> recipeComminution(){
		return isValidRecipe() ? getCurrentRecipe().getComminution() : null;
	}



	//----------------------- CUSTOM -----------------------
	public int getComminution(){
		return this.comminution;
	}

	public ItemStack getFilter() {
		return this.filter;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	BlockPos startPos() {
		return this.pos.offset(EnumFacing.DOWN);
	}

	public TEPowerGenerator getEngine(){
		BlockPos enginePos = this.pos.offset(EnumFacing.DOWN).offset(isFacingAt(270));
		TEPowerGenerator engine = TileStructure.getEngine(this.world, enginePos, isFacingAt(90));
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() >= getCooktimeMax() : false;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().markDirtyClient();
	}

//transmission
	public TEMineralSizerTransmission getTransmission(){
		TileEntity te = this.world.getTileEntity(startPos());
		if(this.world.getBlockState(startPos()) != null && te instanceof TEMineralSizerTransmission){
			TEMineralSizerTransmission engine = (TEMineralSizerTransmission)te;
			if(engine.getFacing() == engine.getFacing()){
				return engine;
			}
		}
		return null;
	}

	public boolean hasTransmission(){
		return getTransmission() != null;
	}

//tanks
	public TEMineralSizerTank getTank1(){
		TEMineralSizerTank crusher = TileStructure.getCrusher(this.world, startPos().offset(isFacingAt(90), 1), isFacingAt(90));
		return crusher != null ? crusher : null;
	}

	public TEMineralSizerTank getTank2(){
		TEMineralSizerTank crusher = TileStructure.getCrusher(this.world, startPos().offset(isFacingAt(90), 2), isFacingAt(90));
		return crusher != null ? crusher : null;
	}

	public TEMineralSizerTank getTank3(){
		TEMineralSizerTank crusher = TileStructure.getCrusher(this.world, startPos().offset(isFacingAt(90), 3), isFacingAt(90));
		return crusher != null ? crusher : null;
	}

	public TEMineralSizerTank getTank4(){
		TEMineralSizerTank crusher = TileStructure.getCrusher(this.world, startPos().offset(isFacingAt(90), 4), isFacingAt(90));
		return crusher != null ? crusher : null;
	}

	public boolean hasTanks(){
		return getTank1() != null && getTank2() != null && getTank3() != null && getTank4() != null;
	}

	public boolean hasConsumables(){
		return hasTanks() ? getTank1().hasConsumables() && getTank2().hasConsumables()  && getTank3().hasConsumables() && getTank4().hasConsumables(): false;
	}

//collector
	public TEMineralSizerCollector getCollector(){
		BlockPos collectorPos = startPos().offset(isFacingAt(90), 5);
		TileEntity te = this.world.getTileEntity(collectorPos);
		if(this.world.getBlockState(collectorPos) != null && te instanceof TEMineralSizerCollector){
			TEMineralSizerCollector collector = (TEMineralSizerCollector)te;
			if(collector.getFacing() == isFacingAt(270)){
				return collector;
			}
		}
		return null;
	}

	public boolean hasCollector(){
		return getCollector() != null;
	}

	public boolean isOutputEmpty(){
		return hasCollector() ? (slotEmpty(0) && slotEmpty(1) && slotEmpty(2)) : false;
	}

	private boolean slotEmpty(int i) {
		return getCollector().getOutput().getStackInSlot(i).isEmpty();
	}

//unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(EnumFacing.DOWN).offset(isFacingAt(90), 6);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, isFacingAt(270));
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}

//cabinet
	public TEMineralSizerCabinet getCabinet(){
		BlockPos cabinetPos = startPos().offset(getFacing(), 1);
		TileEntity te = this.world.getTileEntity(cabinetPos);
		if(this.world.getBlockState(cabinetPos) != null && te instanceof TEMineralSizerCabinet){
			TEMineralSizerCabinet collector = (TEMineralSizerCabinet)te;
			if(collector.getFacing() == collector.getFacing()){
				return collector;
			}
		}
		return null;
	}

	public boolean hasCabinet(){
		return getCabinet() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, startPos().offset(isFacingAt(90), 7), isFacingAt(270));
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return hasUnloader() && hasCabinet() && hasTransmission() && hasCollector();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			doPreset();
			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, 16);

			handlePurge();

			if(isActive()){
				if(canProcess()){
					this.cooktime++;
					drainPower();
					activateTank();
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
						disableTank();
					}
					this.markDirtyClient();
				}else{
					tickOff();		
				}
			}else{
				tickOff();		
			}
		}
	}

	@Override
	public void tickOff() {
		if(this.getCooktime() > 0){
			this.cooktime = 0;
			disableTank();
			this.markDirtyClient();
		}
	}

	private void handlePurge() {
		if(isActive()){
			if(!this.inputSlot().isEmpty() && !this.getFilter().isEmpty() && !CoreUtils.isMatchingIngredient(inputSlot(), getFilter())){
				if(((MachineStackHandler)getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), inputSlot())){
					((MachineStackHandler)getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, inputSlot());
					this.input.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
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
			if(getEngine().enableRedstone){
				getEngine().enableRedstone = false;
				getEngine().markDirtyClient();
			}
		}
	}

	public void activateTank() {
		if(this.getCooktime() > 0){
			if(hasTanks()) {
				if(!getTank1().isActive()) {
					getTank1().activation = true;
					getTank1().markDirtyClient();
				}
				if(!getTank2().isActive()) {
					getTank2().activation = true;
					getTank2().markDirtyClient();
				}
				if(!getTank3().isActive()) {
					getTank3().activation = true;
					getTank3().markDirtyClient();
				}
				if(!getTank4().isActive()) {
					getTank4().activation = true;
					getTank4().markDirtyClient();
				}
			}
		}
	}

	public void disableTank() {
		if(hasTanks()) {
			if(getTank1().isActive()) {
				getTank1().activation = false;
				getTank1().markDirtyClient();
			}
			if(getTank2().isActive()) {
				getTank2().activation = false;
				getTank2().markDirtyClient();
			}
			if(getTank3().isActive()) {
				getTank3().activation = false;
				getTank3().markDirtyClient();
			}
			if(getTank4().isActive()) {
				getTank4().activation = false;
				getTank4().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		return expectResult()
			&& hasFuelPower()
			&& isAssembled()
			&& isOutputEmpty()
			&& handleFilter(inputSlot(), getFilter()) //server
			&& handleServer(getServer(), this.currentFile); //server
	}

	public void getResult(){
		this.resultList = new ArrayList<ItemStack>();
		if(isValidRecipe()){
			for(int stack = 0; stack < recipeOutput().size(); stack++){
				if(recipeComminution().get(stack) == getComminution()){
					this.resultList.add(recipeOutput().get(stack));
				}
			}
		}
	}

	public boolean expectResult(){
		if(isValidRecipe()){
			for(int stack = 0; stack < recipeOutput().size(); stack++){
				if(recipeComminution().get(stack) == getComminution()){
					return true;
				}
			}
		}
		return false;
	}

	public boolean hasResult() {
		getResult();
		return hasExposed();
	}

	public boolean hasExposed() {
		return resultList != null && !this.resultList.isEmpty() && this.resultList.size() > 0;
	}

	public void process() {
		if(hasConsumables()) {
			if(hasResult()){
				((ItemStackHandler)getCollector().getOutput()).setStackInSlot(OUTPUT_SLOT, pickLevelOutput().copy());
				if(recipeOutput().size() > 1){
					((ItemStackHandler)getCollector().getOutput()).getStackInSlot(OUTPUT_SLOT).setCount(sizedQuantity());
				}
		
				if(this.resultList.size() > 1){
					if(this.world.rand.nextInt(100) < 25){
						((ItemStackHandler)getCollector().getOutput()).setStackInSlot(TEMineralSizerCollector.GOOD_SLOT, pickLevelOutput().copy());
						((ItemStackHandler)getCollector().getOutput()).getStackInSlot(TEMineralSizerCollector.GOOD_SLOT).setCount(this.rand.nextInt(1 + (sizedQuantity() / 2)));
		
						if(this.world.rand.nextInt(100) < 5){
							((ItemStackHandler)getCollector().getOutput()).setStackInSlot(TEMineralSizerCollector.WASTE_SLOT, pickRecipeOutput().copy());
							((ItemStackHandler)getCollector().getOutput()).getStackInSlot(TEMineralSizerCollector.WASTE_SLOT).setCount(1);
						}
					}
				}
		
				handleConsumableDamage();
				this.input.decrementSlot(INPUT_SLOT);
				
				updateServer(getServer(), this.currentFile);
			}
		}
		pushGears();
	}

	public void pushGears() {
		if(hasCabinet() && hasTanks()) {
			deliverGearsToTank(getTank1());
			deliverGearsToTank(getTank2());
			deliverGearsToTank(getTank3());
			deliverGearsToTank(getTank4());
		}
	}

	public void deliverGearsToTank(TEMineralSizerTank tank) {
		for(int x = 0; x <=3; x++) {
			if(tank.gearSlot(x).isEmpty()) {
				for(int y = 0; y < getCabinet().inputSlots; y++) {
					if(!getCabinet().gearSlot(y).isEmpty()) {
						((ItemStackHandler)tank.getInput()).setStackInSlot(x, getCabinet().gearSlot(y).copy());
						((MachineStackHandler) getCabinet().getInput()).decrementSlot(y);
						tank.markDirtyClient();
						break;
					}
				}
			}
		}
	}



	private ItemStack pickLevelOutput() {
		return this.resultList.get(this.rand.nextInt(this.resultList.size()));
	}

	private ItemStack pickRecipeOutput() {
		return isValidRecipe() ? recipeOutput().get(this.rand.nextInt(recipeOutput().size())) : ItemStack.EMPTY;
	}

	public boolean isRecipeMulti(){
		return !this.resultList.isEmpty() && this.resultList.size() > 1;
	}

	private void handleConsumableDamage() {
		if(isValidRecipe()){
			for(int gearCount = 0; gearCount <= getComminution(); gearCount++){
				if(gearCount <= 3){
					damageGears(gearCount, getTank1());
				}else if(gearCount >= 4 && gearCount <= 7){
					damageGears(gearCount- 4, getTank2());
				}else if(gearCount >= 8 && gearCount <= 11){
					damageGears(gearCount- 8, getTank3());
				}else if(gearCount >= 12 && gearCount <= 15){
					damageGears(gearCount- 12, getTank4());
				}
			}
		}
	}

	private void damageGears(int gearCount, TEMineralSizerTank tank) {
		if(!tank.gearSlot(gearCount).isEmpty()){
			int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, tank.gearSlot(gearCount));
			((MachineStackHandler)tank.getInput()).damageUnbreakingSlot(unbreakingLevel, gearCount);
	
			if(CoreUtils.hasMending(tank.gearSlot(gearCount)) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
				((MachineStackHandler)tank.getInput()).repairMendingSlot(gearCount);
			}
		}
	}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < getServer().FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(getServer().isValidFile(fileSlot)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isWrittenFile(tag)){
							if(isCorrectDevice(tag, deviceCode())){
								if(getRecipe(tag) < 16){
									if(getDone(tag) > 0){
										if(this.comminution != getRecipe(tag)){
											this.comminution = getRecipe(tag);
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										if(hasFilterItem(tag)){
											ItemStack temp = getFilterItem(tag);
											if(this.getFilter().isEmpty() || !this.getFilter().isItemEqual(temp)){
												this.filter = temp;
											}
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