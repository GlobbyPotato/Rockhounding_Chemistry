package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_chemistry.utils.Utils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityDepositionChamber extends TileEntityMachineEnergy implements IFluidHandlingTile{

	private ItemStackHandler template = new TemplateStackHandler(3);

	public static final int SOLVENT_SLOT = 1;
	public static final int CASING_SLOT = 2;
	public static final int INSULATION_SLOT = 3;
	public int temperatureCount = 0;
	public int temperatureMax = 3000;
	public int pressureCount = 0;
	public int pressureMax = 32000;

	public int takenRF = 1000;

	public FluidTank inputTank;

	public TileEntityDepositionChamber() {
		super(4, 1, 0);

		inputTank = new FluidTank(10000){
			@Override
			public boolean canFillFluidType(FluidStack fluid){
				return activation && hasSolvent(fluid) && isValidInterval() && isCorrectSolvent(fluid);
			}
			@Override
		    public boolean canDrain(){
		        return !isValidInterval() || (isValidInterval() && isWrongSolvent(inputTank));
		    }
		};
		inputTank.setTileEntity(this);
		inputTank.setCanFill(true);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && activation && isValidInterval() && (hasRecipe(insertingStack) || isValidOredict(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CASING_SLOT && ToolUtils.hasUpgrade(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == INSULATION_SLOT && ToolUtils.hasInsulation(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SOLVENT_SLOT && isValidInterval() && hasSolvent(FluidUtil.getFluidContained(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
		this.markDirtyClient();
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	public int getCookTimeMax(){
		return ModConfig.speedDeposition;
	}

	@Override
	public int getGUIHeight() {
		return GuiDepositionChamber.HEIGHT;
	}

	@Override
	public boolean hasRF(){
		return true;	
	}

	@Override
	public boolean canInduct() {
		return false;
	}



	//----------------------- CUSTOM ------------------------
	public boolean isValidInterval() {
		return recipeIndex >= 0 && recipeIndex <= ModRecipes.depositionRecipes.size() - 1;
	}

	public DepositionChamberRecipe getRecipe(){
		return isValidInterval() ? ModRecipes.depositionRecipes.get(recipeIndex) : null;
	}

	public static boolean isValidOredict(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));
			for(DepositionChamberRecipe recipe: ModRecipes.depositionRecipes){
				ArrayList<Integer> recipeIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipe.getInput()));
				for(Integer ores: recipeIDs){
					if(inputIDs.contains(ores)) return true;
				}
			}
		}
		return false;
	}

	private boolean hasRecipe(ItemStack stack){
		return isValidInterval() && ModRecipes.depositionRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(getRecipe().getInput()));
	}

	private boolean hasSolvent(FluidStack stack){
		return isValidInterval() && ModRecipes.depositionRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getSolvent()!= null && stack.isFluidEqual(getRecipe().getSolvent()));
	}

	private boolean isWrongSolvent(FluidTank tank) {
		return isValidInterval() && tank.getFluid() != null && !tank.getFluid().isFluidEqual(getRecipe().getSolvent()) && tank.getFluidAmount() > 0;
	}

	private boolean isCorrectSolvent(FluidStack fluid) {
		return isValidInterval() && fluid.isFluidEqual(getRecipe().getSolvent());
	}

	private boolean hasValidContainer(ItemStack insertingStack) {
		return ItemStack.areItemsEqual(insertingStack, new ItemStack(Items.BUCKET))
			|| (!FluidRegistry.isUniversalBucketEnabled() && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModFluids.beaker)));
	}

	public int getPressure() { 	  
		return this.pressureCount; 
	}

	public int getPressureMax() { 	  
		return this.pressureMax; 
	}

	public int getTemperature() { 	  
		return this.temperatureCount; 
	}

	public int getTemperatureMax() { 	  
		return this.temperatureMax; 
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.recipeIndex = compound.getInteger("RecipeScan");
		this.activation = compound.getBoolean("Activation");
		this.pressureCount = compound.getInteger("PressureCount");
		this.temperatureCount = compound.getInteger("TemperatureCount");
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("RecipeScan", this.recipeIndex);
		compound.setBoolean("Activation", this.activation);
		compound.setInteger("PressureCount", this.pressureCount);
		compound.setInteger("TemperatureCount", this.temperatureCount);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		return compound;
	}

	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}

	private FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(inputTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!isValidInterval()){ recipeIndex = -1; }

		if(!worldObj.isRemote){
			emptyContainer(SOLVENT_SLOT, inputTank);

			if(isValidInterval()){
				handleParameters();

				if(canDeposit()){
					execute();
				}
			}else{
				handleRelaxing();
			}
			this.markDirtyClient();
		}
	}

	public boolean canDeposit(){
		return activation
			&& getRecipe() != null
			&& ItemStack.areItemsEqual(getRecipe().getInput(), input.getStackInSlot(INPUT_SLOT))
			&& (inputTank.getFluid() != null && inputTank.getFluid().containsFluid(getRecipe().getSolvent()) && inputTank.getFluidAmount() >= getRecipe().getSolvent().amount)
			&& this.getTemperature() >= getRecipe().getTemperature()
			&& this.getPressure() >= getRecipe().getPressure()
			&& canOutput(output.getStackInSlot(OUTPUT_SLOT));
	}

	private void execute() {
		cookTime++;
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			handleOutput();
		}
	}

	private void handleOutput() {
		output.setOrStack(OUTPUT_SLOT, getRecipe().getOutput());
		inputTank.getFluid().amount -= getRecipe().getSolvent().amount;
		if(inputTank.getFluid().amount <= 0){inputTank.setFluid(null);}
		this.temperatureCount /= 3;
		this.pressureCount /= 3;
		input.decrementSlot(INPUT_SLOT);
	}

	private boolean canOutput(ItemStack stack) {
		return stack == null 
			|| (stack != null && stack.isItemEqual(getRecipe().getOutput()) && stack.stackSize < stack.getMaxStackSize());
	}

	private void handleRelaxing() {
		if(this.getTemperature() > tempStability() && rand.nextInt(stabilityDelta()) == 0){
			this.temperatureCount -= tempStability();
		}
		if(this.getPressure() > pressStability() && rand.nextInt(stabilityDelta()) == 0){
			this.pressureCount -= pressStability();
		}
	}

	private void handleParameters() {
		handleRelaxing();
		int temp = getRecipe().getTemperature();
		if(this.redstoneCount >= takenRF && this.getTemperature() < temp && temp < this.getTemperatureMax() - tempYeld() ){
			this.redstoneCount -= takenRF; 
			this.temperatureCount += tempYeld();
		}

		int press = getRecipe().getPressure();
		if(this.getRedstone() >= takenRF && this.getPressure() < press && press < this.getPressureMax() - pressYeld()){
			this.redstoneCount -= takenRF; 
			this.pressureCount += pressYeld();
		}
	}

	public int tempYeld(){
		return 10 * getUpgrade();
	}

	public int pressYeld(){
		return 30 * getUpgrade();
	}

	private int getUpgrade() {
		return ToolUtils.hasUpgrade(input.getStackInSlot(CASING_SLOT))? 10 : 1;
	}

	public int tempStability(){
		return 300 / getInsulation();
	}

	public int pressStability(){
		return 500 / getInsulation();
	}

	public int stabilityDelta(){
		return 100 + (10 * getInsulation());
	}

	private int getInsulation() {
		return ToolUtils.hasInsulation(input.getStackInSlot(INSULATION_SLOT)) ? 10 : 1;
	}

}