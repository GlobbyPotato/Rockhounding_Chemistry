package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.RetentionVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;
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

public class TERetentionVat extends TileEntityInv implements IInternalServer {

	public static final int SPEED_SLOT = 0;
	public static final int FILTER_SLOT = 1;
	public static int templateSlots = 3;
	public static int upgradeSlots = 2;
	public ArrayList<ItemStack> resultList;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public float gravity = 8.00F;
	public boolean isBugged = false;

	public RetentionVatRecipe dummyRecipe;

	public TERetentionVat() {
		super(0, 0, templateSlots, upgradeSlots);

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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setFloat("Gravity", getGravity());
		compound.setBoolean("Bugged", this.isBugged());
		return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "retention_vat";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedLeaching / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedLeaching;
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
		return EnumServer.RETENTION.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<RetentionVatRecipe> recipeList(){
		return RetentionVatRecipes.retention_vat_recipes;
	}

	public RetentionVatRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public RetentionVatRecipe getCurrentRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(getRecipeList(x).getInput() != null && isMatchingFluid(x)){
				return getRecipeList(x);
			}
		}
		return null;
	}

	public RetentionVatRecipe getDummyRecipe(){
		return dummyRecipe;
	}

	private boolean isMatchingFluid(int x) {
		return hasTankPulp() && getTankPulp().getSolventFluid() != null && getRecipeList(x).getInput().isFluidEqual(getTankPulp().inputTank.getFluid());
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public FluidStack recipeInput(){
		return getCurrentRecipe().getInput();
	}

	public ArrayList<ItemStack> recipeOutput(){
		return getCurrentRecipe().getOutput();
	}

	public ArrayList<Float> recipeGravity(){
		return getCurrentRecipe().getGravity();
	}

	public int calculatedPulp() {
		return getDummyRecipe() != null && getDummyRecipe().getInput() != null ? getDummyRecipe().getInput().amount : 0;
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
			if(getLeacherA().getFilter() != fluoridricAcid()){
				getLeacherA().filter = fluoridricAcid();
			}
		}

		if(hasVatB()){
			if(getLeacherB().getFilter() != CoreBasics.waterStack(1000)){
				getLeacherB().filter = CoreBasics.waterStack(1000);
			}
		}

		if(hasVesselA()){
			if(getVesselA().getFilter() != steam()){
				getVesselA().filter = steam();
			}
		}
		
		if(hasServer() && !getServer().isActive()){
			emptyFilters();
		}
	}	

	public void emptyFilters() {
		if(hasTankPulp()){
			getTankPulp().filterSolvent = null;
			getTankPulp().isFiltered = false;
		}
	}



	//----------------------- CUSTOM -----------------------
	public float getGravity(){
		return this.gravity;
	}

	public boolean isBugged(){
		return this.isBugged;
	}

	public FluidStack fluoridricAcid() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.HYDROFLUORIC_ACID), 1000);
	}

	public int calculatedFluo() {
		return getGravity() > 0 ? ((int)getGravity() * ModConfig.consumedFluo) : 1;
	}

	public int calculatedWater() {
		return 500;
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
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, isFacingAt(270), 1, 0);
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
		TELeachingVatTank tank = TileStructure.getLeacher(this.world, this.pos, getFacing(), 1);
		return tank != null ? tank : null;
	}

	public boolean hasVatA(){
		return getLeacherA() != null;
	}

//leacher2
	public TELeachingVatTank getLeacherB(){
		TELeachingVatTank tank = TileStructure.getLeacher(this.world, this.pos, getFacing(), 2);
		return tank != null ? tank : null;
	}

	public boolean hasVatB(){
		return getLeacherB() != null;
	}

//vessel1
	public TileVessel getVesselA(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing()), isFacingAt(270), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasVesselA(){
		return getVesselA() != null;
	}

//collector
	public BlockPos collectorPos(){
		return this.pos.offset(getFacing(), 3);		
	}

	public TELeachingVatCollector getCollector(){
		TileEntity te = this.world.getTileEntity(collectorPos());
		if(this.world.getBlockState(collectorPos()) != null && te instanceof TELeachingVatCollector){
			TELeachingVatCollector collector = (TELeachingVatCollector)te;
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

// tank pulp
	public TEFlotationTank getTankPulp(){
		BlockPos flotationPos = this.pos.offset(getFacing());
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, flotationPos, EnumFacing.UP, 1);
		return tank != null ? tank : null;
	}

	public boolean hasTankPulp(){
		return getTankPulp() != null;
	}

	public FluidStack getPulp(){
		return hasTankPulp() && getTankPulp().hasSolventFluid() ? getTankPulp().getSolventFluid() : null;
	}

// tank waste
	public TEBufferTank getTankWaste(){
		TEBufferTank tank = TileStructure.getBufferTank(this.world, collectorPos(), EnumFacing.UP, 1);
		return tank != null ? tank : null;
	}
	
	public boolean hasTankWaste(){
		return getTankWaste() != null;
	}

	public boolean hasWaste(){
		return hasTankWaste()
			&& this.input.canSetOrAddFluid(getTankWaste().inputTank, getTankWaste().getTankFluid(), waste(), calculatedWaste());
	}

	private int calculatedWaste() {
			return 400;
		}

	private FluidStack waste() {
		return BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 1000);
	}

//process check
	public boolean hasLeacherA(){
		return hasVatA() 
			&& this.input.canDrainFluid(getLeacherA().inputTank.getFluid(), fluoridricAcid(), calculatedFluo())
			&& getLeacherA().hasConsumables()
			&& hasVesselA() 
			&& this.input.canDrainFluid(getVesselA().inputTank.getFluid(), steam(), calculatedSteam());
	}

	public boolean hasLeacherB(){
		return hasVatB() 
			&& this.input.canDrainFluid(getLeacherB().inputTank.getFluid(), CoreBasics.waterStack(1000), calculatedWater())
			&& getLeacherB().hasConsumables();
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

			if(isActive() && getDummyRecipe() == null && hasAnyInput()){
				dummyRecipe = getCurrentRecipe();
				getResult();
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
				tickOff();
			}
		}
	}

	private boolean canProcess() {
 		return isActive()
			&& getDummyRecipe() != null
			&& isAssembled()
			&& hasFuelPower()
			&& hasEnoughInput()
			&& hasExposed()
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
	}

	private boolean isAssembled() {
		return isOutputEmpty() && hasTankWaste() && hasTankPulp()
			&& (hasLeacherA() && hasLeacherB());
	}

	private boolean hasEnoughInput() {
		return getDummyRecipe() != null && hasTankPulp() && getTankPulp().inputTank.getFluidAmount() >= calculatedPulp();
	}

	private boolean hasAnyInput() {
		return getPulp() != null;
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
	
			if(hasTankWaste() && hasWaste()){
				this.output.setOrFillFluid(getTankWaste().inputTank, waste(), calculatedWaste());
			}

			if(hasVatA()){
				this.output.drainOrCleanFluid(getLeacherA().inputTank, calculatedFluo(), true);
				int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getLeacherA().gearSlot());
				((MachineStackHandler)getLeacherA().getInput()).damageUnbreakingSlot(unbreakingLevel, TileEntityInv.INPUT_SLOT);

				if(CoreUtils.hasMending(getLeacherA().gearSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
					((MachineStackHandler)getLeacherA().getInput()).repairMendingSlot(TileEntityInv.INPUT_SLOT);
				}
			}
			if(hasVesselA()){
				this.output.drainOrCleanFluid(getVesselA().inputTank, calculatedSteam(), true);
			}
	
			if(hasVatB()){
				this.output.drainOrCleanFluid(getLeacherB().inputTank, calculatedWater(), true);
				int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getLeacherB().gearSlot());
				((MachineStackHandler)getLeacherB().getInput()).damageUnbreakingSlot(unbreakingLevel, TileEntityInv.INPUT_SLOT);

				if(CoreUtils.hasMending(getLeacherB().gearSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
					((MachineStackHandler)getLeacherB().getInput()).repairMendingSlot(TileEntityInv.INPUT_SLOT);
				}
			}
	
			if(hasTankPulp()){
				this.output.drainOrCleanFluid(getTankPulp().inputTank, calculatedPulp(), true);
			}
	
			updateServer(hasServer(), getServer(), this.currentFile);
		}else{
			this.isBugged = true;
		}

		this.dummyRecipe = null;
	}

	private ItemStack pickLevelOutput() {
		return this.resultList.get(this.rand.nextInt(this.resultList.size()));
	}

	private ItemStack pickRecipeOutput() {
		return recipeOutput().get(this.rand.nextInt(recipeOutput().size()));
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
	@Override
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
								if(tag.getInteger("Recipe") < 16){
									if(tag.getInteger("Done") > 0){
										if(this.gravity != (tag.getInteger("Recipe") * 2) + 2F){
											this.gravity = (tag.getInteger("Recipe") * 2) + 2F;
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										if(tag.hasKey("FilterFluid") && hasTankPulp()){
											FluidStack temp = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("FilterFluid"));
											if(this.getTankPulp().getFilterSolvent() == null || !this.getTankPulp().getFilterSolvent().isFluidEqual(temp)){
												if(this.getTankPulp().filterSolvent != temp){
													this.getTankPulp().filterSolvent = temp;
													this.markDirtyClient();
												}
												if(this.getTankPulp().filterManualSolvent != null){
													this.getTankPulp().filterManualSolvent = null;
													this.markDirtyClient();
												}
												if(this.getTankPulp().isFiltered != true){
													this.getTankPulp().isFiltered = true;
													this.markDirtyClient();
												}
//											}else{
//												emptyFilters();
											}
										}else{
											emptyFilters();
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