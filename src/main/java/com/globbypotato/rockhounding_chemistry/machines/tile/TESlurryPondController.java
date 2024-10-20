package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryPondRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryPondRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class TESlurryPondController extends TileEntityInv{

	public static int inputSlots = 1;
	public static int templateSlots = 2;

	public int concentration = 1;

	public TESlurryPondController() {
		super(inputSlots, 0, templateSlots, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == INPUT_SLOT && isValidInput(insertingStack)) {
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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("Concentration", getConcentration());

        return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
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



	//----------------------- CUSTOM -----------------------
	public int getConcentration(){
		return this.concentration;
	}

	public int actualConcentration() {
		return (11 - this.getConcentration()) * 10;
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



	// ----------------------- STRUCTURE -----------------------
// tank INPUT
	public TEFlotationTank getTankPulp(){
		TEFlotationTank tank = TileStructure.getFlotationTank(this.world, this.pos.offset(EnumFacing.UP));
		return tank != null ? tank : null;
	}

	public boolean hasTankPulp(){
		return getTankPulp() != null;
	}

	public boolean hasPulp(){
		return hasTankPulp() && this.input.canDrainFluid(getTankPulp().getSolventFluid(), calculateSolvent());
	}

	public TEWashingTank getWasher(){
		TEWashingTank centrifuge = TileStructure.getWasher(this.world, this.pos.offset(getFacing()));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasSlurry(){
		return hasWasher()
			&& this.input.canSetOrFillFluid(getWasher().inputTank, getWasher().getTankFluid(), slurry());
	}

	public boolean hasWasher(){
		return getWasher() != null;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

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
			&& hasPulp()
			&& hasSlurry();
	}

	private boolean canFillSlurry() {
		return hasWasher() && this.output.canSetOrFillFluid(getWasher().inputTank, getWasher().getTankFluid(), calculateSlurry());
	}

	private void process() {
		if(hasWasher()) {
			this.output.setOrFillFluid(getWasher().inputTank, calculateSlurry());
			getWasher().updateNeighbours();
		}
		
		if(hasTankPulp()) {
			this.input.drainOrCleanFluid(getTankPulp().inputTank, calculateSolventAmount(), true);
		}
		
		this.input.decrementSlotBy(INPUT_SLOT, calculateInputAmount());
	}

}