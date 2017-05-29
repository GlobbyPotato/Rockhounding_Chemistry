package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.FuelUtils;
import com.globbypotato.rockhounding_chemistry.utils.ProbabilityStack;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_chemistry.utils.Utils;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMineralSizer extends TileEntityMachineEnergy {

	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;

	public TileEntityMineralSizer() {
		super(3,3,1);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && (hasRecipe(insertingStack) || isValidOredict(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && (FuelUtils.isItemFuel(insertingStack) || ToolUtils.hasinductor(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && ToolUtils.hasConsumable(ToolUtils.gear, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}

	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiMineralSizer.HEIGHT;
	}

	public int getMaxCookTime(){
		return ModConfig.speedSizer;
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRecipe(ItemStack stack){
		return ModRecipes.sizerRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}

	public static boolean isValidOredict(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));
			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				ArrayList<Integer> recipeIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipe.getInput()));
				for(Integer ores: recipeIDs){
					if(inputIDs.contains(ores)) return true;
				}
			}
		}
		return false;
	}

	private boolean allOutputsEmpty(){
		return (output.getStackInSlot(OUTPUT_SLOT) == null
			&& output.getStackInSlot(GOOD_SLOT) == null
			&& output.getStackInSlot(WASTE_SLOT) == null);
	}

	private MineralSizerRecipe getRecipe (int x){
		return ModRecipes.sizerRecipes.get(x);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		if(!worldObj.isRemote){
			if(canProcess(input.getStackInSlot(INPUT_SLOT))){
				cookTime++;
				powerCount--;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
				this.markDirtyClient();
			}
		}
	}

	private boolean canProcess(ItemStack stack) {
		return allOutputsEmpty()
			&& hasRecipe(input.getStackInSlot(INPUT_SLOT))
			&& ToolUtils.hasConsumable(ToolUtils.gear, input.getStackInSlot(CONSUMABLE_SLOT))
			&& getPower() >= getMaxCookTime();
	}

	private void process() {
		for(int x = 0; x < ModRecipes.sizerRecipes.size(); x++){
			if(getRecipe(x).getInput() != null && ItemStack.areItemsEqual(getRecipe(x).getInput(), input.getStackInSlot(INPUT_SLOT))){
				//calculate primary output
				int mix = getRecipe(x).getOutput().size();
				if(mix > 1){
					output.setStackInSlot(OUTPUT_SLOT, ProbabilityStack.calculateProbability(getRecipe(x).getProbabilityStack()).copy());
					output.getStackInSlot(OUTPUT_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral) + 1;
				}else{
					output.setStackInSlot(OUTPUT_SLOT, getRecipe(x).getOutput().get(0).copy());
				}
				//calculate secondary output
				if(rand.nextInt(100) < 25 && mix > 4){
					output.setStackInSlot(GOOD_SLOT, ProbabilityStack.calculateProbability(getRecipe(x).getProbabilityStack()).copy());
					output.getStackInSlot(GOOD_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral / 2) + 1;
				}
				//calculate waste output
				if(rand.nextInt(100) < 5 && mix > 1){
					output.setStackInSlot(WASTE_SLOT, ProbabilityStack.calculateProbability(getRecipe(x).getProbabilityStack()).copy());
					output.getStackInSlot(WASTE_SLOT).stackSize = 1;
				}
			}
		}
		input.damageSlot(CONSUMABLE_SLOT);
		input.decrementSlot(INPUT_SLOT);
	}

}