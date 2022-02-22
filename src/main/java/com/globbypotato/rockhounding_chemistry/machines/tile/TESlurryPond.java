package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryPondRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryPondRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.oredict.OreDictionary;

public class TESlurryPond extends TileEntityTank implements IToxic{

	public FluidTank inputTank;
	public FluidTank outputTank;
	public static final int FILL_BUCKET = 1;
	public static final int DRAIN_BUCKET = 2;

	public static int inputSlots = 3;
	public static int templateSlots = 5;

	public int concentration = 1;
	public FluidStack filter = null;

	public TESlurryPond() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillFiltered(fluid);
			}

			@Override
			public boolean canDrain() {
				return false;
			}
		};
		this.inputTank.setTileEntity(this);

		this.outputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return false;
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.outputTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == INPUT_SLOT && isValidInput(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == FILL_BUCKET && isValidBath(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == DRAIN_BUCKET && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.concentration = compound.getInteger("Concentration");
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.outputTank.readFromNBT(compound.getCompoundTag("OutputTank"));
		this.filter = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Filter"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("Concentration", getConcentration());

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		NBTTagCompound outputTankNBT = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankNBT);
		compound.setTag("OutputTank", outputTankNBT);

		if(getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        this.filter.writeToNBT(filterNBT);
	        compound.setTag("Filter", filterNBT);
		}

        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank, this.outputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}
	
	public ItemStack fillSlot(){
		return this.input.getStackInSlot(FILL_BUCKET);
	}

	public ItemStack drainSlot(){
		return this.input.getStackInSlot(DRAIN_BUCKET);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "slurry_pond";
	}

	public int getCooktimeMax(){
		return ModConfig.speedPond + (15 * getConcentration());
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		tanks.add(this.outputTank);
		return tanks;
 	}



	//----------------------- CUSTOM -----------------------
	public int getConcentration(){
		return this.concentration;
	}

	public int actualConcentration() {
		return (11 - this.getConcentration()) * 10;
	}

	public FluidStack getFilter(){
		return this.filter;
	}

	public boolean hasFilter(){
		return getFilter() != null;
	}

	public boolean canFillFiltered(FluidStack fluid) {
		return hasFilter() ? getFilter().isFluidEqual(fluid) : isValidBath(fluid);
	}



	//----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() {
		return 10000;
	}

	public boolean hasInputFluid(){
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getInputFluid(){
		return this.inputTank.getFluid();
	}

	public int getInputAmount() {
		return this.inputTank.getFluidAmount();
	}

	public boolean hasOutputFluid(){
		return this.outputTank.getFluid() != null;
	}

	public FluidStack getOutputFluid(){
		return this.outputTank.getFluid();
	}

	public int getOutputAmount() {
		return this.outputTank.getFluidAmount();
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<SlurryPondRecipe> recipeList(){
		return SlurryPondRecipes.slurry_pond_recipes;
	}

	public static SlurryPondRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public static boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			for(SlurryPondRecipe recipe: recipeList()){
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
		return false;
	}

	public static boolean isValidBath(FluidStack fluid) {
		return recipeList().stream().anyMatch(recipe -> fluid != null && recipe.getBath() != null && fluid.isFluidEqual(recipe.getBath()));
	}

	public SlurryPondRecipe getCurrentRecipe(){
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

	public ItemStack input(){return getCurrentRecipe().getInput();}

	public int calculateInputAmount(){
		if(getConcentration() == 2 || getConcentration() == 3){
			return 2;
		}else if(getConcentration() >= 4 && getConcentration() <= 6){
			return 3;
		}else if(getConcentration() >= 7 && getConcentration() <= 10){
			return 4;
		}
		return 1;
	}

	public ItemStack calculateInput(){return new ItemStack(input().getItem(), calculateInputAmount(), input().getItemDamage());}

	public FluidStack solvent(){return isValidRecipe() ? getCurrentRecipe().getBath() : null;}
	public FluidStack slurry(){return isValidRecipe() ? getCurrentRecipe().getOutput() : null;}

	public int calculateSolventAmount(){
		return solvent() != null ? (solvent().amount * (getConcentration()*10) / 100) : 0;
	}

	public int calculateSlurryAmount(){
		return slurry() != null ? (slurry().amount *  (getConcentration()*10) / 100) : 0;
	}

	public FluidStack calculateSolvent(){return solvent() != null ? new FluidStack(solvent(), calculateSolventAmount()) : null;}
	public FluidStack calculateSlurry(){return slurry() != null ? new FluidStack(slurry(), calculateSlurryAmount()) : null;}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			emptyContainer(FILL_BUCKET, this.inputTank);
			fillContainer(DRAIN_BUCKET, this.outputTank);
			handleToxic(this.world, this.pos);
			if(this.getConcentration() < 1){ this.concentration = 1;}

			if(!this.inputSlot().isEmpty()){
				if(canProcess()){
					this.cooktime++;
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
					}
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess() {
		return isValidRecipe()
			&& this.inputSlot().getCount() >= calculateInputAmount()
			&& this.input.canDrainFluid(getInputFluid(), calculateSolvent())
			&& this.output.canSetOrFillFluid(this.outputTank, getOutputFluid(), calculateSlurry());
	}

	private void process() {
		this.output.setOrFillFluid(outputTank, calculateSlurry());
		this.input.drainOrCleanFluid(inputTank, calculateSolventAmount(), true);
		this.input.decrementSlotBy(INPUT_SLOT, calculateInputAmount());
	}

}