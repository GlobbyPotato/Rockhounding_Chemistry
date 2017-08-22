package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.CastingRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityCastingBench extends TileEntityMachineTank {

	public int currentCast;

	public TileEntityCastingBench() {
		super(1,1,0);

		input =  new MachineStackHandler(INPUT_SLOTS, this){

			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isSlotEmpty() && isValidOredict(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN);

	}



	//----------------------- HANDLER -----------------------
	public int getMaxCookTime(){
		return 30;
	}

	public int getCurrentCast(){
		return currentCast;
	}



	//----------------------- CUSTOM -----------------------
	public boolean isValidOredict(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));
			for(CastingRecipe recipe: MachineRecipes.castingRecipes){
				String recipeDict = recipe.getInput();
				for(Integer ores: inputIDs){
					String inputDict = OreDictionary.getOreName(ores);
					if(inputDict.matches(recipeDict)) return true;
				}
			}
		}
		return false;
	}

	public CastingRecipe getRecipe(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));
			for(CastingRecipe recipe: MachineRecipes.castingRecipes){
				String recipeDict = recipe.getInput();
				for(Integer ores: inputIDs){
					String inputDict = OreDictionary.getOreName(ores);
					if(inputDict.matches(recipeDict)){
						if(recipe.getCasting() == getCurrentCast()){
							return recipe;
						}
					}
				}
			}
		}
		return null;
	}

	public int getprogress(){
		return cookTime;
	}

	public boolean isProcessing(){
		return canProcess() && getprogress() > 0;
	}

	private boolean isSlotEmpty() {
		return input.getStackInSlot(INPUT_SLOT) == null;
	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.currentCast = compound.getInteger("CurrentCast");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        compound.setInteger("CurrentCast", this.currentCast);
		return compound;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!worldObj.isRemote){
			if(canProcess()){
				cookTime++;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess() {
		return output.getStackInSlot(OUTPUT_SLOT) == null
			&& getRecipe(input.getStackInSlot(INPUT_SLOT)) != null;
	}

	private void process() {
		ItemStack inputStack = input.getStackInSlot(INPUT_SLOT);
		ItemStack outputStack = getRecipe(inputStack).getOutput();
		output.setStackInSlot(OUTPUT_SLOT, outputStack);
		input.decrementSlot(INPUT_SLOT);
	}


}