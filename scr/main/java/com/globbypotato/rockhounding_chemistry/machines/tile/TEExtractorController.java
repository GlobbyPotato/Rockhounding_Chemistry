package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class TEExtractorController extends TileEntityInv implements IInternalServer{

	public static final int PURGE_SLOT = 0;

	public static int inputSlots = 1;
	public static int outputSlots = 1;
	public static int templateSlots = 3;

	public int intensity = 8;
	public ItemStack filter = ItemStack.EMPTY;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public ChemicalExtractorRecipe dummyRecipe;

	public TEExtractorController() {
		super(inputSlots, outputSlots, templateSlots, 0);

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
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.intensity = compound.getInteger("Intensity");
		if(compound.hasKey("Filter")){
			this.filter = new ItemStack(compound.getCompoundTag("Filter"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Intensity", getIntensity());
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

	public ItemStack purgeSlot() {
		return this.output.getStackInSlot(PURGE_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "extractor_controller";
	}

	public int speedFactor() {
		return hasInjector() && ModUtils.isValidSpeedUpgrade(getInjector().speedSlot()) ? ModUtils.speedUpgrade(getInjector().speedSlot()) : 1;
	}

	public int getCooktimeMax(){
		return hasInjector() && ModUtils.isValidSpeedUpgrade(getInjector().speedSlot()) ? ModConfig.speedExtractor / getInjector().speedUpgrade(): ModConfig.speedExtractor;
	}

	private static int deviceCode() {
		return EnumServer.EXTRACTOR.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<ChemicalExtractorRecipe> recipeList(){
		return ChemicalExtractorRecipes.extractor_recipes;
	}

	public ArrayList<String> inhibitedList(){
		return ChemicalExtractorRecipes.inhibited_elements;
	}

	public ArrayList<MaterialCabinetRecipe> materialList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}

	public MaterialCabinetRecipe getMaterialList(int x){
		return materialList().get(x);
	}

	public ChemicalExtractorRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			if(!getFilter().isEmpty()){
				if(getFilter().isItemEqual(stack)){
					return true;
				}
			}else{
				for(ChemicalExtractorRecipe recipe: recipeList()){
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

	public ChemicalExtractorRecipe getCurrentRecipe(){
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

	public ChemicalExtractorRecipe getDummyRecipe(){
		return dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public ArrayList<String> recipeOutput(){
		return isValidRecipe() ? getDummyRecipe().getElements() : null;
	}

	public ArrayList<Integer> recipeQuantities(){
		return isValidRecipe() ? getDummyRecipe().getQuantities() : null;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, isFacingAt(90), 1, 0);
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 && getEngine().getRedstone() >= powerConsume(): false;
	}

//reactor
	public BlockPos reactorPos(){
		return this.pos.offset(getFacing(), 1);		
	}

	public TEExtractorReactor getReactor(){
		TileEntity te = this.world.getTileEntity(reactorPos());
		if(this.world.getBlockState(reactorPos()) != null && te instanceof TEExtractorReactor){
			TEExtractorReactor reactor = (TEExtractorReactor)te;
			if(reactor.getFacing() == getFacing().getOpposite()){
				return reactor;
			}
		}
		return null;
	}

	public boolean hasReactor(){
		return getReactor() != null;
	}

//glassware holder
	public BlockPos injectorPos(){
		return intankPos().offset(isFacingAt(90), 1);		
	}

	public TEExtractorInjector getInjector(){
		TileEntity te = this.world.getTileEntity(injectorPos());
		if(this.world.getBlockState(injectorPos()) != null && te instanceof TEExtractorInjector){
			TEExtractorInjector injector = (TEExtractorInjector)te;
			if(injector.getFacing() == getFacing().getOpposite()){
				return injector;
			}
		}
		return null;
	}

	public boolean hasInjector(){
		return getInjector() != null;
	}

	private boolean hasTube() {
		return hasInjector()
			&& CoreUtils.hasConsumable(BaseRecipes.test_tube, getInjector().getInput().getStackInSlot(TEExtractorInjector.TUBE_SLOT))
			&& CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, getInjector().getInput().getStackInSlot(TEExtractorInjector.CYLINDER_SLOT));
	}

//charger
	public Block getCharger(){
		BlockPos chargerPos = reactorPos().offset(EnumFacing.UP);
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

//intank
	public BlockPos intankPos(){
		return this.pos.offset(getFacing(), 2);		
	}

	public TEFluidInputTank getIntank(){
		TileEntity te = this.world.getTileEntity(intankPos());
		if(this.world.getBlockState(intankPos()) != null && te instanceof TEFluidInputTank){
			TEFluidInputTank tank = (TEFluidInputTank)te;
			if(tank.getFacing() == getFacing().getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public boolean hasIntank(){
		return getIntank() != null;
	}

	public boolean hasFluids() {
		return hasIntank()
			&& this.input.canDrainFluid(getIntank().solventTank.getFluid(), nitricAcid(), calculatedNitr())
			&& this.input.canDrainFluid(getIntank().reagentTank.getFluid(), cyanideAcid(), calculatedCyan());
	}

//element cabinet
	public TEElementsCabinetBase getElementCabinet(){
		BlockPos cabinetPos = reactorPos().offset(isFacingAt(270), 1);
		TileEntity te = this.world.getTileEntity(cabinetPos);
		if(this.world.getBlockState(cabinetPos) != null && te instanceof TEElementsCabinetBase){
			TEElementsCabinetBase cabinet = (TEElementsCabinetBase)te;
			if(cabinet.getFacing() == isFacingAt(270).getOpposite()){
				return cabinet;
			}
		}
		return null;
	}

	public boolean hasElementCabinet(){
		return getElementCabinet() != null;
	}

	public TEMaterialCabinetBase getMaterialCabinet(){
		BlockPos cabinetPos = this.pos.offset(isFacingAt(270), 1);
		TileEntity te = this.world.getTileEntity(cabinetPos);
		if(this.world.getBlockState(cabinetPos) != null && te instanceof TEMaterialCabinetBase){
			TEMaterialCabinetBase cabinet = (TEMaterialCabinetBase)te;
			if(cabinet.getFacing() == isFacingAt(270).getOpposite()){
				return cabinet;
			}
		}
		return null;
	}

	public boolean hasMaterialCabinet(){
		return getMaterialCabinet() != null;
	}

	private boolean isAssembled() {
		return hasElementCabinet() && hasMaterialCabinet() && hasReactor() && hasCharger();
	}

//stabilizer
	public TEExtractorStabilizer getStabilizer(){
		BlockPos stabilizerPos = reactorPos().offset(isFacingAt(90), 1);
		TileEntity te = this.world.getTileEntity(stabilizerPos);
		if(this.world.getBlockState(stabilizerPos) != null && te instanceof TEExtractorStabilizer){
			TEExtractorStabilizer stabilizer = (TEExtractorStabilizer)te;
			if(stabilizer.getFacing() == isFacingAt(90).getOpposite()){
				return stabilizer;
			}
		}
		return null;
	}

	public boolean hasStabilizer(){
		return getStabilizer() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, injectorPos(), getFacing(), 1, 0);
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- CUSTOM -----------------------
	public int getIntensity(){
		return this.intensity;
	}

	public ItemStack getFilter() {
		return this.filter;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().redstoneCount -= powerConsume();
		getEngine().markDirtyClient();
	}

	public int powerConsume() {
		int baseConsume = (10 * ModConfig.basePower) * getIntensity();
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public FluidStack nitricAcid() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID), 1000);
	}

	public int calculatedNitr() {
		return getIntensity() > 0 ? getIntensity() * ModConfig.consumedNitr : 1;
	}

	public FluidStack cyanideAcid() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE), 1000);
	}

	public int calculatedCyan() {
		return getIntensity() > 0 ? getIntensity() * ModConfig.consumedCyan : 1;
	}

	public int baseRecovery(){
		return 20;
	}

	public int calculatedRecombination(int amount){
		return (amount * recoveryFactor()) / 100;
	}

	public int recoveryFactor(){
		return 100 - (stabilizerNum() * getIntensity());
	}

	public int stabilizerNum(){
		return hasStabilizer() ? 6 - getStabilizer().getBusySlots() : 6;
	}

	public int calculatedDust(int amount){
		return (amount * normalRecovery()) / 100;
	}

	public int normalRecovery() {
		return getIntensity() * 6;
	}

	public int recombinationChance() {
		return this.rand.nextInt(18 - getIntensity());
	}

	public int dissolutionChance(){
		return (getIntensity() * 100) / 18;
	}

	private int getDurabilityModifier(int cat){
		return EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, getStabilizer().catSlot(cat));
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			handlePurge();

			initializeServer(isRepeatable, hasServer(), getServer(), deviceCode());

			if(inputSlot().isEmpty() && getDummyRecipe() != null){
				dummyRecipe = null;
				this.cooktime = 0;
			}

			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canProcess()){
				this.cooktime++;
				drainPower();
				if(getCooktime() >= getCooktimeMax()) {
					process();
					this.cooktime = 0;
				}
				this.markDirtyClient();
			}else{
				tickOff();
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
		if(hasIntank()){
			if(getIntank().getFilterSolvent() != nitricAcid()){
				getIntank().filterSolvent = nitricAcid();
			}
			if(getIntank().getFilterReagent() != cyanideAcid()){
				getIntank().filterReagent = cyanideAcid();
			}
			getIntank().filterManualSolvent = null;
			getIntank().filterManualReagent = null;
			getIntank().isFiltered = true;
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

	private boolean canProcess() {
		return isActive()
			&& getDummyRecipe() != null
			&& isAssembled()
			&& hasFuelPower()
			&& hasTube()
			&& hasFluids()
			&& handleFilter(inputSlot(), getFilter()) //server
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
	}

	private void process() {
		if(getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){
			for(int x = 0; x < getDummyRecipe().getElements().size(); x++){
				int formulaValue = getDummyRecipe().getQuantities().get(x);
				for(int y = 0; y < EnumElements.size(); y++){
					String recipeDust = getDummyRecipe().getElements().get(x);
					if(recipeDust.contains(EnumElements.getDust(y))){
						boolean isInhibited = false;
						for(int ix = 0; ix < inhibitedList().size(); ix++){
							if(recipeDust.matches(inhibitedList().get(ix))){
								isInhibited = true;
							}
						}
						if(!isInhibited){
							if(hasElementCabinet()){
								if(formulaValue > baseRecovery()){
									if(recombinationChance() == 0){
										getElementCabinet().elementList[y] += baseRecovery() + calculatedRecombination(formulaValue - baseRecovery());
										handleConsumableDamage();
									}else{
										getElementCabinet().elementList[y] += baseRecovery() + calculatedDust(formulaValue - baseRecovery());
									}
								}else{
									getElementCabinet().elementList[y] += formulaValue;
								}
								getElementCabinet().markDirtyClient();
							}
						}
					}
				}
				for(int y = 0; y < materialList().size(); y++){
					String recipeDust = getDummyRecipe().getElements().get(x);
					if(recipeDust.contains(materialList().get(y).getOredict())){
						boolean isInhibited = false;
						for(int ix = 0; ix < inhibitedList().size(); ix++){
							if(recipeDust.matches(inhibitedList().get(ix))){
								isInhibited = true;
							}
						}
						if(!isInhibited){
							if(hasMaterialCabinet()){
								if(formulaValue > baseRecovery()){
									if(recombinationChance() == 0){
										getMaterialCabinet().elementList[y] += baseRecovery() + calculatedRecombination(formulaValue - baseRecovery());
										handleConsumableDamage();
									}else{
										getMaterialCabinet().elementList[y] += baseRecovery() + calculatedDust(formulaValue - baseRecovery());
									}
								}else{
									getMaterialCabinet().elementList[y] += formulaValue;
								}
								getMaterialCabinet().markDirtyClient();
							}
						}
					}
				}
			}

			if(getIntank().hasSolventFluid() && getIntank().getSolventAmount() >= calculatedNitr() ){
				this.output.drainOrCleanFluid(getIntank().solventTank, calculatedNitr(), true);
			}
			if(getIntank().hasReagentFluid() && getIntank().getReagentAmount() >= calculatedCyan() ){
				this.output.drainOrCleanFluid(getIntank().reagentTank, calculatedCyan(), true);
			}
	
			int unbreakingLevel = 0;
			unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getInjector().tubeSlot());
			((MachineStackHandler)getInjector().getInput()).damageUnbreakingSlot(unbreakingLevel, TEExtractorInjector.TUBE_SLOT);
	
			unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getInjector().cylinderSlot());
			((MachineStackHandler)getInjector().getInput()).damageUnbreakingSlot(unbreakingLevel, TEExtractorInjector.CYLINDER_SLOT);
	
			this.input.decrementSlot(INPUT_SLOT);
	
			updateServer(hasServer(), getServer(), this.currentFile);
		}

		this.dummyRecipe = null;
	}

	private void handleConsumableDamage() {
		if(isValidRecipe() && hasStabilizer()){
			for(int x = 0; x < TEExtractorStabilizer.SLOT_INPUTS.length; x++){
				if(!getStabilizer().catSlot(x).isEmpty()){
					int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getStabilizer().catSlot(x));
					((MachineStackHandler) getStabilizer().getInput()).damageUnbreakingSlot(unbreakingLevel, x);
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
				if(!fileSlot.isEmpty() && fileSlot.isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()))){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isValidFile(tag)){
							if(tag.getInteger("Device") == deviceCode()){
								if(tag.getInteger("Recipe") < 16){
									if(tag.getInteger("Done") > 0){
										if(this.recipeIndex != tag.getInteger("Recipe")){
											this.recipeIndex = tag.getInteger("Recipe");
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