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

	public static int totInput = 1;
	public static int totOutput = 1;

	public TileEntityCastingBench() {
		super(totInput, totOutput, 0);

		this.input =  new MachineStackHandler(totInput, this){

			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isSlotEmpty() && isValidOredict(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

	}



	//----------------------- HANDLER -----------------------
	public int getMaxCookTime(){
		return 30;
	}

	public int getCurrentCast(){
		return this.currentCast;
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
		return this.cookTime;
	}

	public boolean isProcessing(){
		return canProcess() && getprogress() > 0;
	}

	public boolean isSlotEmpty() {
		return this.input.getStackInSlot(INPUT_SLOT) == null;
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
		if(!this.worldObj.isRemote){
			if(canProcess()){
				this.cookTime++;
				if(this.cookTime >= getMaxCookTime()) {
					this.cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess() {
		return this.output.getStackInSlot(OUTPUT_SLOT) == null
			&& getRecipe(this.input.getStackInSlot(INPUT_SLOT)) != null;
	}

	private void process() {
		ItemStack inputStack = this.input.getStackInSlot(INPUT_SLOT);
		ItemStack outputStack = getRecipe(inputStack).getOutput();
		this.output.setStackInSlot(OUTPUT_SLOT, outputStack);
		this.input.decrementSlot(INPUT_SLOT);
	}


}