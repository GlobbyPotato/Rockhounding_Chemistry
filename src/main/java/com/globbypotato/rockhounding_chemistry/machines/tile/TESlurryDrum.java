package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryDrumRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryDrumRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.oredict.OreDictionary;

public class TESlurryDrum extends TileTank{

	public TESlurryDrum() {
		super();
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == FILL_BUCKET && (canFillFiltered(FluidUtil.getFluidContained(insertingStack)) || isValidInput(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == DRAIN_BUCKET && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
	}



	// ----------------------- HANDLER -----------------------
	public static String getName(){
		return "slurry_drum";
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<SlurryDrumRecipe> recipeList(){
		return SlurryDrumRecipes.slurry_drum_recipes;
	}

	public static SlurryDrumRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public static boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			for(SlurryDrumRecipe recipe: recipeList()){
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

	public SlurryDrumRecipe getCurrentRecipe(){
		if(!fillSlot().isEmpty()){
			for(int x = 0; x < recipeList().size(); x++){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(fillSlot()));
				if(getRecipeList(x).getType()){
					if(inputOreIDs.contains(OreDictionary.getOreID(getRecipeList(x).getOredict()))){
						return getRecipeList(x);
					}
				}else{
					if(getRecipeList(x).getInput().isItemEqual(fillSlot())){
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

	public ItemStack recipeInput(){return isValidRecipe() ? getCurrentRecipe().getInput() : ItemStack.EMPTY;}
	public FluidStack slurry(){return isValidRecipe() ? getCurrentRecipe().getOutput() : null;}



	// ----------------------- CUSTOM -----------------------
	@Override
	public boolean isValidSubstance(FluidStack fluid) {
		return ModUtils.isHeavyFluid(fluid.getFluid());
	}

	@Override
	public int getTankCapacity() {
		return 40000;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			emptyContainer(FILL_BUCKET, this.inputTank);
			fillContainer(DRAIN_BUCKET, this.inputTank);

			if(!this.fillSlot().isEmpty()){
				if(canSpill()){
					spill();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canSpill() {
		return isValidRecipe()
			&& ItemStack.areItemsEqual(fillSlot(), recipeInput())
			&& this.input.canSetOrFillFluid(this.inputTank, getTankFluid(), slurry());
	}

	private void spill() {
		this.output.setOrFillFluid(this.inputTank, slurry());
		this.input.decrementSlot(FILL_BUCKET);
	}

}