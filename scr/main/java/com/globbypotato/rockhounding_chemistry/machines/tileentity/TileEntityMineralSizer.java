package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.ProbabilityStack;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TileEntityMineralSizer extends TileEntityMachineTank {

	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;

	public TileEntityMineralSizer() {
		super(3,3,1);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && CoreUtils.isPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && CoreUtils.hasConsumable(ToolUtils.gear, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
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
		return MachineRecipes.sizerRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}

	private boolean allOutputsEmpty(){
		return (output.getStackInSlot(OUTPUT_SLOT) == null
			&& output.getStackInSlot(GOOD_SLOT) == null
			&& output.getStackInSlot(WASTE_SLOT) == null);
	}

	private MineralSizerRecipe getRecipe (int x){
		return MachineRecipes.sizerRecipes.get(x);
	}



	//----------------------- I/O -----------------------
	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(lavaTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		lavaHandler();
		if(!worldObj.isRemote){
			if(canProcess(input.getStackInSlot(INPUT_SLOT))){
				cookTime++;
				powerCount--;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess(ItemStack stack) {
		return allOutputsEmpty()
			&& hasRecipe(input.getStackInSlot(INPUT_SLOT))
			&& CoreUtils.hasConsumable(ToolUtils.gear, input.getStackInSlot(CONSUMABLE_SLOT))
			&& getPower() >= getMaxCookTime();
	}

	private void process() {
		for(int x = 0; x < MachineRecipes.sizerRecipes.size(); x++){
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