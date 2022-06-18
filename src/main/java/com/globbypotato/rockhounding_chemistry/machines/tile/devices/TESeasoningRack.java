package com.globbypotato.rockhounding_chemistry.machines.tile.devices;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SeasoningRackRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SeasoningRackRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class TESeasoningRack extends TileEntityInv {

	public static int inputSlots = 1;
	public static int outputSlots = 1;

	public TESeasoningRack() {
		super(inputSlots, outputSlots, 0, 0);

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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack outputSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}


	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "seasoning_rack";
	}

	public int getCooktimeMax(){
		return ModConfig.speedSeasoning;
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<SeasoningRackRecipe> recipeList(){
		return SeasoningRackRecipes.seasoning_rack_recipes;
	}

	public SeasoningRackRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			for(SeasoningRackRecipe recipe: recipeList()){
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

	public SeasoningRackRecipe getCurrentRecipe(){
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



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			if(!inputSlot().isEmpty()){
				if(canProcess()){
					this.cooktime++;
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
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

	private boolean canProcess() {
		return isValidRecipe()
			&& this.output.canSetOrStack(outputSlot(), getCurrentRecipe().getOutput());
	}

	public void process() {
		this.output.setOrStack(OUTPUT_SLOT, getCurrentRecipe().getOutput());
		this.input.decrementSlot(INPUT_SLOT);
	}

}