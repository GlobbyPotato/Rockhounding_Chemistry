package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityLabBlender extends TileEntityMachineTank {
	public static final int INPUT_SLOT[] = new int[]{0,1,2,3,4,5,6,7,8,};
	LabBlenderRecipe currentRecipe;
	private ItemStackHandler template = new TemplateStackHandler(1);
    
	public TileEntityLabBlender() {
		super(11,1,1);
		FUEL_SLOT = 9;

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= INPUT_SLOT[0] && slot <= INPUT_SLOT.length && (isValidIngredient(insertingStack) || isValidOredict(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && CoreUtils.isPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiLabBlender.HEIGHT;
	}

	public int getMaxCookTime(){
		return ModConfig.speedBlender;
	}



	//----------------------- CUSTOM -----------------------
	public boolean isActive(){
		return activation;
	}

	private LabBlenderRecipe recipe(int x){
		return MachineRecipes.blenderRecipes.get(x);
	}

	private boolean isValidIngredient(ItemStack insertingStack){
		if(insertingStack != null){
			for(int x = 0; x < MachineRecipes.blenderRecipes.size(); x++){
				for(int y = 0; y < recipe(x).getInputs().size(); y++){
					ItemStack ingr = recipe(x).getInputs().get(y);
					if(ingr != null && ingr.isItemEqual(insertingStack)){
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isValidOredict(ItemStack insertingStack) {
		if(insertingStack != null){
			ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(insertingStack));
			for(int x = 0; x < MachineRecipes.blenderRecipes.size(); x++){
				for(int y = 0; y < recipe(x).getInputs().size(); y++){
					ItemStack ingr = recipe(x).getInputs().get(y);
					if(ingr != null){
						ArrayList<Integer> ingrIDs = Utils.intArrayToList(OreDictionary.getOreIDs(ingr));
						if(ingrIDs.size() > 0){
							for(Integer ores: ingrIDs){
								String ingrDict = OreDictionary.getOreName(ores);
								for(Integer inputs: inputIDs){
									String inputDict = OreDictionary.getOreName(inputs);
									if(inputDict.matches(ingrDict)){
										return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.activation = compound.getBoolean("Activation");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Activation", this.activation);
		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(lavaTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		acceptEnergy();
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		lavaHandler();
		if(!worldObj.isRemote){
			if(canProcess()){
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

	private boolean canProcess() {
		return isActive()
			&& getPower() >= getMaxCookTime()
			&& getRecipe() != null
			&& output.canSetOrStack(output.getStackInSlot(OUTPUT_SLOT), getRecipe().getOutput());
	}

	private boolean isFullRecipe() {
		int totIngr = 0;
		for(int x = 0; x < currentRecipe.getInputs().size(); x++){
			for(int y = 0; y < INPUT_SLOT.length; y++){
				if(currentRecipe.getInputs().get(x) != null && input.getStackInSlot(y) != null){
					if(currentRecipe.getInputs().get(x).isItemEqual(input.getStackInSlot(y))){
						if(currentRecipe.getInputs().get(x).stackSize <= input.getStackInSlot(y).stackSize){
							totIngr++;
						}
					}
				}
			}
		}
		return currentRecipe.getInputs().size() == totIngr;
	}



	private void process() {
		if(currentRecipe != null){
			ItemStack outputStack = currentRecipe.getOutput();
			if(outputStack != null){
				output.setOrStack(OUTPUT_SLOT, outputStack);
			}
			for(int y = 0; y < currentRecipe.getInputs().size(); y++){
				for(int z = 0; z < INPUT_SLOT.length; z++){
					ItemStack ingr = currentRecipe.getInputs().get(y);
					if(input.getStackInSlot(z) != null && currentRecipe.getInputs().get(y) != null){
						if((isRecipeIngredient(ingr, z) || isRecipeOredict(ingr, z)) && input.getStackInSlot(z).stackSize >= currentRecipe.getInputs().get(y).stackSize){
							input.decrementSlotBy(z, currentRecipe.getInputs().get(y).stackSize);
						}
					}
				}
			}
			currentRecipe = null;
		}
	}

	public LabBlenderRecipe getRecipe(){
		for(int x = 0; x < MachineRecipes.blenderRecipes.size(); x++){
			int numIngr = 0;
			for(int y = 0; y < INPUT_SLOT.length; y++){
				if(y < recipe(x).getInputs().size() && recipe(x).getInputs().get(y) != null){
					ItemStack ingr = recipe(x).getInputs().get(y);
					ItemStack inputStack = input.getStackInSlot(y);
					if(ingr != null && inputStack != null){ 
						if((isRecipeIngredient(ingr, y) || isRecipeOredict(ingr, y)) && inputStack.stackSize >= recipe(x).getInputs().get(y).stackSize){
							numIngr++;
						}
					}
				}
			}
			if(numIngr == recipe(x).getInputs().size()){
				currentRecipe = recipe(x);
				return currentRecipe;
			}
		}			
		return null;
	}

	private boolean isRecipeIngredient(ItemStack ingr, int z) {
		if(ingr != null && input.getStackInSlot(z) != null && ingr.isItemEqual(input.getStackInSlot(z))){
			return true;
		}
		return false;
	}

	private boolean isRecipeOredict(ItemStack ingr, int z) {
		ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(input.getStackInSlot(z)));
		ArrayList<Integer> ingrIDs = Utils.intArrayToList(OreDictionary.getOreIDs(ingr));
		if(ingrIDs.size() > 0){
			for(Integer ores: ingrIDs){
				String ingrDict = OreDictionary.getOreName(ores);
				for(Integer inputs: inputIDs){
					String inputDict = OreDictionary.getOreName(inputs);
					if(inputDict.matches(ingrDict)){
						return true;
					}
				}
			}
		}
		return false;
	}

}