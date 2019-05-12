package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
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
	public static final int PURGE_SLOT = 0;

	public static int inputSlots = 1;
	public static int outputSlots = 1;
	public static int templateSlots = 3;
	public static int upgradeSlots = 1;

	public ArrayList<ItemStack> resultList = new ArrayList<ItemStack>();

	public int comminution;
	public ItemStack filter = ItemStack.EMPTY;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TEMineralSizerController() {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);
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

	public ItemStack purgeSlot() {
		return this.output.getStackInSlot(PURGE_SLOT);
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
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 270);
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
		return /*isPowered() ? getBlockPower() :*/ this.comminution;
	}

	public ItemStack getFilter() {
		return this.filter;
	}



	//----------------------- STRUCTURE -----------------------
	public BlockPos tankPosA(){
		return this.pos.offset(getFacing(), 1);		
	}

	public TEMineralSizerTank getTankA(){
		TileEntity te = this.world.getTileEntity(tankPosA());
		if(this.world.getBlockState(tankPosA()) != null && te instanceof TEMineralSizerTank){
			TEMineralSizerTank tank = (TEMineralSizerTank)te;
			if(getFacing() == tank.getFacing() || getFacing() == tank.getFacing().getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasTankA(){
		return getTankA() != null;
	}

	public boolean hasConsumablesA(){
		return hasTankA() ? getTankA().hasConsumables() : false;
	}

	public BlockPos tankPosB(){
		return this.pos.offset(getFacing(), 2);		
	}

	public TEMineralSizerTank getTankB(){
		TileEntity te = this.world.getTileEntity(tankPosB());
		if(this.world.getBlockState(tankPosB()) != null && te instanceof TEMineralSizerTank){
			TEMineralSizerTank tank = (TEMineralSizerTank)te;
			if(getFacing() == tank.getFacing() || getFacing() == tank.getFacing().getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasTankB(){
		return getTankB() != null;
	}

	public boolean hasConsumablesB(){
		return hasTankB() ? getTankB().hasConsumables() : false;
	}

	public boolean hasFullTank(){
		return hasTankA() && hasTankB();
	}

	public boolean hasConsumables(){
		return hasConsumablesA() && hasConsumablesB();
	}

//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, getFacing(), 4, 0);
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

//collector
	public BlockPos collectorPos(){
		return this.pos.offset(getFacing(), 3);		
	}

	public TEMineralSizerCollector getCollector(){
		TileEntity te = this.world.getTileEntity(collectorPos());
		if(this.world.getBlockState(collectorPos()) != null && te instanceof TEMineralSizerCollector){
			TEMineralSizerCollector collector = (TEMineralSizerCollector)te;
			if(collector.getFacing() == getFacing()){
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

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos, isFacingAt(90), 1, 0);
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

			initializeServer(isRepeatable, hasServer(), getServer(), deviceCode());

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
				if(this.output.canSetOrStack(purgeSlot(), inputSlot())){
					this.output.setOrStack(PURGE_SLOT, inputSlot());
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
			if(hasTankA() && !getTankA().isActive()){
				getTankA().activation = true;
				getTankA().markDirtyClient();
			}
			if(hasTankB() && !getTankB().isActive()){
				getTankB().activation = true;
				getTankA().markDirtyClient();
			}
		}
	}

	public void disableTank() {
		if(hasTankA()){
			if(getTankA().isActive()){
				getTankA().activation = false;
				getTankA().markDirtyClient();
			}
		}
		if(hasTankB()){
			if(getTankB().isActive()){
				getTankB().activation = false;
				getTankB().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		return expectResult()
			&& hasConsumables()
			&& hasFuelPower()
			&& isOutputEmpty()
			&& handleFilter(inputSlot(), getFilter()) //server
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
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
		if(hasResult()){
			((ItemStackHandler)getCollector().getOutput()).setStackInSlot(TileEntityInv.OUTPUT_SLOT, pickLevelOutput().copy());
			if(recipeOutput().size() > 1){
				((ItemStackHandler)getCollector().getOutput()).getStackInSlot(TileEntityInv.OUTPUT_SLOT).setCount(sizedQuantity());
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
			
			updateServer(hasServer(), getServer(), this.currentFile);
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
			for(int damage = 0; damage <= getComminution(); damage++){
				int gearCount = damage / 2;
				if(gearCount < 4){
					if(!getTankA().gearSlot(gearCount).isEmpty()){
						int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getTankA().gearSlot(gearCount));
						((MachineStackHandler)getTankA().getInput()).damageUnbreakingSlot(unbreakingLevel, gearCount);
					}
				}else{
					if(!getTankB().gearSlot(gearCount - 4).isEmpty()){
						int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getTankB().gearSlot(gearCount - 4));
						((MachineStackHandler)getTankB().getInput()).damageUnbreakingSlot(unbreakingLevel, gearCount - 4);
					}
				}
			}
		}
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
								if(tag.getInteger("Recipe") < 16){
									if(tag.getInteger("Done") > 0){
										if(this.comminution != tag.getInteger("Recipe")){
											this.comminution = tag.getInteger("Recipe");
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										if(tag.hasKey("FilterStack")){
											ItemStack temp = new ItemStack(tag.getCompoundTag("FilterStack"));
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
				if(x == TEServer.FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}

}