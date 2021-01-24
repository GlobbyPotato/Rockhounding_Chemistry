package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PullingCrucibleRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PullingCrucibleRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class TEPullingCrucibleBase extends TileEntityInv {

	public static int inputSlots = 1;
	public static int outputSlots = 1;
	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public int meltcount;

	public static final int SPEED_SLOT = 0;

	public PullingCrucibleRecipe dummyRecipe;
	
	
	public TEPullingCrucibleBase() {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isValidInput(insertingStack)){
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
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack outputSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.meltcount = compound.getInteger("Melting");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Melting", getMelting());
		return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "pulling_crucible_base";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedPulling / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedPulling;
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public static ArrayList<PullingCrucibleRecipe> recipeList(){
		return PullingCrucibleRecipes.pulling_crucible_recipes;
	}

	public static PullingCrucibleRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public static boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			for(PullingCrucibleRecipe recipe: recipeList()){
				if(recipe.getType1()){
					ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
					if(!inputOreIDs.isEmpty()){
						if(inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict1()))){
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
		return false;
	}

	public PullingCrucibleRecipe getCurrentRecipe(){
		if(!inputSlot().isEmpty() && (hasDopant() && !getDopant().inputSlot().isEmpty())){
			for(int x = 0; x < recipeList().size(); x++){
				if(hasInputIngredient(x) && hasDopantIngredient(x)){
					return getRecipeList(x);
				}
			}
		}
		return null;
	}

	private boolean hasInputIngredient(int x) {
		ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
		if(getRecipeList(x).getType1()){
			if(inputOreIDs.contains(OreDictionary.getOreID(getRecipeList(x).getOredict1()))){
				return true;
			}
		}else{
			if(getRecipeList(x).getInput().isItemEqual(inputSlot())){
				return true;
			}
		}
		return false;
	}

	private boolean hasDopantIngredient(int x) {
		ArrayList<Integer> dopantOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(getDopant().inputSlot()));
		if(getRecipeList(x).getType2()){
			if(dopantOreIDs.contains(OreDictionary.getOreID(getRecipeList(x).getOredict2()))){
				return true;
			}
		}else{
			if(getRecipeList(x).getDopant().isItemEqual(getDopant().inputSlot())){
				return true;
			}
		}
		return false;
	}

	public PullingCrucibleRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public ItemStack input(){return isValidRecipe() ? getDummyRecipe().getInput() : ItemStack.EMPTY;}
	public ItemStack dopant(){return isValidRecipe() ? getDummyRecipe().getDopant() : ItemStack.EMPTY;}
	public ItemStack output(){return isValidRecipe() ? getDummyRecipe().getOutput() : ItemStack.EMPTY;}

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
		if(hasInTank()){
			if(getInTank().getFilter() != argon()){
				getInTank().filter = argon();
			}
		}
	}



	//----------------------- CUSTOM -----------------------
	public int powerConsume() {
		int baseConsume = 10 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int getMelting() {
		return this.meltcount;
	}

	public int getMeltingMax() {
		return 2500;
	}

	public int getMeltingLev() {
		return 2000;
	}

	public int consumedArgon(){
		return 100;
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

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() >= powerConsume() : false;
	}

	private void drainPower() {
		getEngine().redstoneCount-= powerConsume();
		getEngine().markDirtyClient();
	}

//input vessel
	public TileVessel getInTank(){
		BlockPos vesselpos = this.pos.offset(EnumFacing.UP);
		TileVessel vessel = TileStructure.getHolder(this.world, vesselpos, isFacingAt(90), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasInTank(){
		return getInTank() != null;
	}

//tower cap
	public boolean hasCap(){
		IBlockState state = this.world.getBlockState(this.pos.up(2));
		Block block = state.getBlock();
		return MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.PULLING_CRUCIBLE_CAP.ordinal());
	}

//pressurizer
	public TEGasPressurizer getPressurizer(){
		BlockPos pPos = this.pos.offset(EnumFacing.UP);
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, pPos, getFacing(), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizer(){
		return getPressurizer() != null;
	}

//dopant
	public TEPullingCrucibleTop getDopant(){
		TileEntity te = this.world.getTileEntity(this.pos.up());
		if(this.world.getBlockState(this.pos.up()) != null && te instanceof TEPullingCrucibleTop){
			TEPullingCrucibleTop injector = (TEPullingCrucibleTop)te;
			if(injector.getFacing() == getFacing()){
				return injector;
			}
		}
		return null;
	}

	public boolean hasDopant(){
		return getDopant() != null;
	}


	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){

			doPreset();

			if(getDummyRecipe() == null){
				this.dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canMelt()){
				this.meltcount += powerConsume();
				drainPower();
				this.markDirtyClient();
			}

			if(getMelting() >= 100 && this.rand.nextInt(32) == 0){
				this.meltcount -= 100;
				this.markDirtyClient();
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

	private boolean canMelt(){
		return isActive()
			&& isValidRecipe() 
			&& hasRedstonePower()
			&& getMelting() <= getMeltingMax() - powerConsume()
			&& canOutput();
	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe() 
			&& (hasPressurizer() && hasCap())
			&& hasArgon()
			&& getMelting() >= getMeltingLev()
			&& canOutput();
	}

				private boolean canOutput() {
					return this.output.canSetOrStack(outputSlot(), getDummyRecipe().getOutput());
				}

				private boolean hasArgon() {
					return hasInTank() ? this.input.canDrainFluid(getInTank().inputTank.getFluid(), argon(), consumedArgon()) : false;
				}

				private static FluidStack argon() {
					return new FluidStack(EnumFluid.pickFluid(EnumFluid.ARGON), 1000);
				}

	private void process() {
		if(getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){

			this.output.setOrStack(OUTPUT_SLOT, output());

			if(hasInTank()){
				this.input.drainOrCleanFluid(getInTank().inputTank, consumedArgon(), true);
			}

			if(hasDopant()){
				((MachineStackHandler)getDopant().getInput()).decrementSlot(TEPullingCrucibleTop.INPUT_SLOT);
			}

			this.meltcount /= 6;

			this.input.decrementSlot(INPUT_SLOT);
		}
		
		this.dummyRecipe = null;
	}

}