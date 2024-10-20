package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LeachingVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TESpecimenCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreBasics;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TELeachingVatController extends TileEntityInv implements IInternalServer{

	public static final int SPEED_SLOT = 0;
	public static final int FILTER_SLOT = 1;
	public static int inputSlots = 1;
	public static int templateSlots = 3;
	public static int upgradeSlots = 2;
	public ArrayList<ItemStack> resultList;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public ItemStack filter = ItemStack.EMPTY;
	public float gravity = 8.00F;
	public boolean isBugged = false;

	public LeachingVatRecipe dummyRecipe;

	public TELeachingVatController() {
		super(inputSlots, 0, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isValidInput(insertingStack)  ){
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
				if(slot == FILTER_SLOT && ModUtils.isValidFilterUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}

	public ItemStack filterSlot() {
		return this.upgrade.getStackInSlot(FILTER_SLOT);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.gravity = compound.getFloat("Gravity");
		this.isBugged = compound.getBoolean("Bugged");
		if(compound.hasKey("Filter")){
			this.filter = new ItemStack(compound.getCompoundTag("Filter"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setFloat("Gravity", getGravity());
		compound.setBoolean("Bugged", this.isBugged());
		if(!this.getFilter().isEmpty()){
			NBTTagCompound filterstack = new NBTTagCompound(); 
			this.filter.writeToNBT(filterstack);
			compound.setTag("Filter", filterstack);
		}
		return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "leaching_vat_controller";
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedLeaching / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedLeaching;
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	private static int sizedQuantity() {
		return ModConfig.maxLeachable > 0 ? ModConfig.maxLeachable : 1;
	}

	public int filterFactor() {
		return ModUtils.isValidFilterUpgrade(filterSlot()) ? ModUtils.filterUpgrade(filterSlot()) : 0;
	}

	public float filterRange(){
		return 2.6F - (0.5F * filterFactor());
	}

	public float filterMove(){
		return ModUtils.isValidFilterUpgrade(filterSlot()) ? ModUtils.stepDivision(filterSlot().getItemDamage()) : 1.0F;
	}

	private static int deviceCode() {
		return EnumServer.LEACHING.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<LeachingVatRecipe> recipeList(){
		return LeachingVatRecipes.leaching_vat_recipes;
	}

	public LeachingVatRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			if(!getFilter().isEmpty()){
				if(getFilter().isItemEqual(stack)){
					return true;
				}
			}else{
				for(LeachingVatRecipe recipe: recipeList()){
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

	public LeachingVatRecipe getCurrentRecipe(){
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

	public LeachingVatRecipe getDummyRecipe(){
		return dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public ArrayList<ItemStack> recipeOutput(){
		return getCurrentRecipe().getOutput();
	}

	public ArrayList<Float> recipeGravity(){
		return getCurrentRecipe().getGravity();
	}

	public FluidStack recipePulp(){
		return getCurrentRecipe().getPulp();
	}

	public boolean hasPulp(){
		return hasTankPulp()
			&& recipePulp() != null
			&& recipePulp().getFluid() != null
			&& this.input.canSetOrAddFluid(getTankPulp().inputTank, getTankPulp().getTankFluid(), recipePulp(), calculatedPulp());
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
		if(hasVatA()){
			if(getLeacherA().getFilter() != sodiumHydroxide()){
				getLeacherA().filter = sodiumHydroxide();
			}
		}
		if(hasVatB()){
			if(getLeacherB().getFilter() != hydrochloricAcid()){
				getLeacherB().filter = hydrochloricAcid();
			}
		}
		if(hasVatC()){
			if(getLeacherC().getFilter() != CoreBasics.waterStack(1000)){
				getLeacherC().filter = CoreBasics.waterStack(1000);
			}
		}

		if(hasVesselA()){
			if(getVesselA().getFilter() != steam()){
				getVesselA().filter = steam();
			}
		}
		if(hasVesselB()){
			if(getVesselB().getFilter() != steam()){
				getVesselB().filter = steam();
			}
		}

	}



	//----------------------- CUSTOM -----------------------
	public float getGravity(){
		return this.gravity;
	}

	public boolean isBugged(){
		return this.isBugged;
	}

	public ItemStack getFilter() {
		return this.filter;
	}

	public FluidStack sodiumHydroxide() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.SODIUM_HYDROXIDE), 1000);
	}

	public FluidStack hydrochloricAcid() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID), 1000);
	}

	public int calculatedSulf() {
		return getGravity() > 0 ? ((int)getGravity() * ModConfig.consumedHydr) : 1;
	}

	public int calculatedChlo() {
		return getGravity() > 0 ? ((int)getGravity() * ModConfig.consumedChlo) : 1;
	}

	public int calculatedWater() {
		return 500;
	}

	public int calculatedPulp() {
		return (calculatedSulf() + calculatedChlo() + calculatedWater()) / 3;
	}

	private static FluidStack steam() {
		return BaseRecipes.getFluid(EnumFluid.WATER_VAPOUR, 1000);
	}

	public int calculatedSteam() {
		return 100;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(90));
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

//leacher1
	public TELeachingVatTank getLeacherA(){
		TELeachingVatTank tank = TileStructure.getLeacher(this.world, this.pos.offset(getFacing(), 1), getFacing());
		return tank != null ? tank : null;
	}

	public boolean hasVatA(){
		return getLeacherA() != null;
	}

//leacher2
	public TELeachingVatTank getLeacherB(){
		TELeachingVatTank tank = TileStructure.getLeacher(this.world, this.pos.offset(getFacing(), 2), getFacing());
		return tank != null ? tank : null;
	}

	public boolean hasVatB(){
		return getLeacherB() != null;
	}

//leacher3
	public TELeachingVatTank getLeacherC(){
		TELeachingVatTank tank = TileStructure.getLeacher(this.world, this.pos.offset(getFacing(), 3), getFacing());
		return tank != null ? tank : null;
	}

	public boolean hasVatC(){
		return getLeacherC() != null;
	}

//vessel1
	public TileVessel getVesselA(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing(), 1).offset(isFacingAt(90), 2), isFacingAt(270));
		return vessel != null ? vessel : null;
	}

	public boolean hasVesselA(){
		return getVesselA() != null && hasCentrifuge1();
	}

//vessel2
	public TileVessel getVesselB(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing(), 2).offset(isFacingAt(90), 2), isFacingAt(270));
		return vessel != null ? vessel : null;
	}

	public boolean hasVesselB(){
		return getVesselB() != null && hasCentrifuge2();
	}

//centrifuge
	public TECentrifuge getSteamCentrifuge1(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing(), 1).offset(isFacingAt(90)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getSteamCentrifuge2(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing(), 2).offset(isFacingAt(90)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuge1(){
		return getSteamCentrifuge1() != null;
	}

	public boolean hasCentrifuge2(){
		return getSteamCentrifuge2() != null;
	}

//collector
	public BlockPos collectorPos(){
		return this.pos.offset(getFacing(), 5);		
	}

	public TESpecimenCollector getCollector(){
		TileEntity te = this.world.getTileEntity(collectorPos());
		if(this.world.getBlockState(collectorPos()) != null && te instanceof TESpecimenCollector){
			TESpecimenCollector collector = (TESpecimenCollector)te;
			if(collector.getFacing() == getFacing().getOpposite()){
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
		BlockPos unloaderPos = this.pos.offset(getFacing(), 4);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}


// tank pulp
	public TEBufferTank getTankPulp(){
		BlockPos tankPos = this.pos.offset(getFacing(), 5).offset(EnumFacing.UP);
		TEBufferTank tank = TileStructure.getBufferTank(this.world, tankPos);
		return tank != null ? tank : null;
	}

	public boolean hasTankPulp(){
		return getTankPulp() != null;
	}

//process check
	public boolean hasLeacherA(){
		return hasVatA() 
			&& this.input.canDrainFluid(getLeacherA().inputTank.getFluid(), sodiumHydroxide(), calculatedSulf())
			&& getLeacherA().hasConsumables()
			&& hasVesselA() 
			&& this.input.canDrainFluid(getVesselA().inputTank.getFluid(), steam(), calculatedSteam());
	}

	public boolean hasLeacherB(){
		return hasVatB() 
			&& this.input.canDrainFluid(getLeacherB().inputTank.getFluid(), hydrochloricAcid(), calculatedChlo())
			&& getLeacherB().hasConsumables()
			&& hasVesselB() 
			&& this.input.canDrainFluid(getVesselB().inputTank.getFluid(), steam(), calculatedSteam());
	}

	public boolean hasLeacherC(){
		return hasVatC() 
			&& this.input.canDrainFluid(getLeacherC().inputTank.getFluid(), CoreBasics.waterStack(1000), calculatedWater())
			&& getLeacherC().hasConsumables();
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos.offset(isFacingAt(90)), isFacingAt(270));
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

			initializeServer(isRepeatable, getServer(), deviceCode(), filterMove(), 16);

			if(inputSlot().isEmpty() && getDummyRecipe() != null){
				dummyRecipe = null;
				this.cooktime = 0;
			}

			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				getResult();
				this.cooktime = 0;
			}
				
			if(canProcess()){
				this.cooktime++;
				drainPower();
				resetOversee(getServer(), this.currentFile);
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				tickOversee(getServer(), this.currentFile);
				tickOff();
			}
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

	private boolean canProcess() {
		return isActive()
			&& getDummyRecipe() != null
			&& hasExposed()
			&& hasFuelPower()
			&& isAssembled()
			&& handleFilter(inputSlot(), getFilter()) //server
			&& handleServer(getServer(), this.currentFile); //server
	}

	private boolean isAssembled() {
		return isOutputEmpty() && hasTankPulp()
			&& (hasLeacherA() && hasLeacherB() && hasLeacherC())
			&& hasUnloader();
	}

	private void process() {
		if(hasResult()){
			this.isBugged = false;

			if(hasCollector()){
				((ItemStackHandler)getCollector().getOutput()).setStackInSlot(TileEntityInv.OUTPUT_SLOT, pickLevelOutput().copy());
				if(recipeOutput().size() > 1){
					((ItemStackHandler)getCollector().getOutput()).getStackInSlot(TileEntityInv.OUTPUT_SLOT).setCount(sizedQuantity());
				}
				if(this.resultList.size() > 1){
					if(this.rand.nextInt(100) < 25){
						((ItemStackHandler)getCollector().getOutput()).setStackInSlot(TEMineralSizerCollector.GOOD_SLOT, pickLevelOutput().copy());
						((ItemStackHandler)getCollector().getOutput()).getStackInSlot(TEMineralSizerCollector.GOOD_SLOT).setCount(this.rand.nextInt(1 + (sizedQuantity() / 2)));
	
						if(this.rand.nextInt(100) < 5){
							((ItemStackHandler)getCollector().getOutput()).setStackInSlot(TEMineralSizerCollector.WASTE_SLOT, pickRecipeOutput().copy());
							((ItemStackHandler)getCollector().getOutput()).getStackInSlot(TEMineralSizerCollector.WASTE_SLOT).setCount(1);
						}
					}
				}
			}

			if(hasPulp()){
				this.output.setOrFillFluid(getTankPulp().inputTank, recipePulp(), calculatedPulp());
			}

			if(hasVatA()){
				this.output.drainOrCleanFluid(getLeacherA().inputTank, calculatedSulf(), true);
				int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getLeacherA().gearSlot());
				((MachineStackHandler)getLeacherA().getInput()).damageUnbreakingSlot(unbreakingLevel, TileEntityInv.INPUT_SLOT);

				if(CoreUtils.hasMending(getLeacherA().gearSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
					((MachineStackHandler)getLeacherA().getInput()).repairMendingSlot(TileEntityInv.INPUT_SLOT);
				}
			}
			if(hasVesselA()){
				this.output.drainOrCleanFluid(getVesselA().inputTank, calculatedSteam(), true);
				getVesselA().updateNeighbours();
			}
	
			if(hasVatB()){
				this.output.drainOrCleanFluid(getLeacherB().inputTank, calculatedChlo(), true);
				int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getLeacherB().gearSlot());
				((MachineStackHandler)getLeacherB().getInput()).damageUnbreakingSlot(unbreakingLevel, TileEntityInv.INPUT_SLOT);

				if(CoreUtils.hasMending(getLeacherB().gearSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
					((MachineStackHandler)getLeacherB().getInput()).repairMendingSlot(TileEntityInv.INPUT_SLOT);
				}
			}
			if(hasVesselB()){
				this.output.drainOrCleanFluid(getVesselB().inputTank, calculatedSteam(), true);
				getVesselB().updateNeighbours();
			}
	
			if(hasVatC()){
				this.output.drainOrCleanFluid(getLeacherC().inputTank, calculatedWater(), true);
				int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getLeacherC().gearSlot());
				((MachineStackHandler)getLeacherC().getInput()).damageUnbreakingSlot(unbreakingLevel, TileEntityInv.INPUT_SLOT);

				if(CoreUtils.hasMending(getLeacherC().gearSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
					((MachineStackHandler)getLeacherC().getInput()).repairMendingSlot(TileEntityInv.INPUT_SLOT);
				}
			}

			this.input.decrementSlot(INPUT_SLOT);

			updateServer(getServer(), this.currentFile);
		}else{
			this.isBugged = true;
		}
		
		dummyRecipe = null;
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

	public void getResult(){
		this.resultList = new ArrayList<ItemStack>();
		if(isValidRecipe()){
			if(recipeOutput().size() > 0){
				for(int stack = 0; stack < recipeOutput().size(); stack++){
					if(recipeGravity().get(stack) >= minGravity() && recipeGravity().get(stack) <= maxGravity()){
						this.resultList.add(recipeOutput().get(stack));
					}
				}
			}
		}
	}

	public boolean hasResult() {
		getResult();
		return hasExposed();
	}

	private boolean hasExposed() {
		return resultList != null && !this.resultList.isEmpty() && this.resultList.size() > 0;
	}

	public float minGravity(){return (getGravity() - filterRange()) >= 0 ? getGravity() - filterRange() : 0;}
	public float maxGravity(){return getGravity() + filterRange();}
	public float currentGravity(int y){return (float)getCurrentRecipe().getGravity().get(y).intValue() / 100;}



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
										if(this.gravity != (getRecipe(tag))){
											this.gravity = (getRecipe(tag));
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										if(hasFilterItem(tag)){
											ItemStack temp = getFilterItem(tag);
											if(this.getFilter().isEmpty() || !this.getFilter().isItemEqual(temp)){
												if(this.filter != temp){
													this.filter = temp;
													this.markDirtyClient();
												}
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