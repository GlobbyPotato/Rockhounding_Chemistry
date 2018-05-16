package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.Utils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityLabBlender extends TileEntityMachineTank {
	public static final int INPUT_SLOT[] = new int[]{0,1,2,3,4,5,6,7,8,};
	LabBlenderRecipe currentRecipe;
	public List<ItemStack> lockList = new ArrayList<ItemStack>();
	private ItemStackHandler template = new TemplateStackHandler(2);
	public boolean lock;

	public static int totInput = 11;
	public static int totOutput = 1;

	public TileEntityLabBlender() {
		super(totInput, totOutput, 1);
		FUEL_SLOT = 9;

		input =  new MachineStackHandler(totInput,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= INPUT_SLOT[0] && slot <= INPUT_SLOT.length && isActive() && handleInputs(slot, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && isGatedPowerSource(insertingStack)){
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

	public void resetLock(){
		for(Integer slot : INPUT_SLOT){
			lockList.add(null);
		}
	}

	public boolean isLocked(){
		return lock;
	}

	private boolean handleInputs(int slot, ItemStack insertingStack) {
		if(isLocked()){
			return isMatchingIngredient(slot, insertingStack);
		}else{
			return isValidIngredient(insertingStack) || isValidOredict(insertingStack);
		}
	}

	private boolean isMatchingIngredient(int slot, ItemStack insertingStack){
		if(slot < lockList.size()){
			if(insertingStack != null && lockList.get(slot) != null){
				if(insertingStack.isItemEqual(lockList.get(slot)) || isMatchingOredict(lockList.get(slot), insertingStack)){
					return true;
				}
			}
		}
		return false;
	}

	private static boolean isMatchingOredict(ItemStack recipeIngredient, ItemStack slotIngredient) {
		ArrayList<Integer> inputIDs = Utils.intArrayToList(OreDictionary.getOreIDs(slotIngredient));
		ArrayList<Integer> recipeIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipeIngredient));
		if(inputIDs.size() > 0 && recipeIDs.size() > 0){
			for(Integer recipeList: recipeIDs){
				String recipeDict = OreDictionary.getOreName(recipeList);
				for(Integer inputList: inputIDs){
					String inputDict = OreDictionary.getOreName(inputList);
					if(inputDict.matches(recipeDict)){
						return true;
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
		this.lock = compound.getBoolean("Lock");

		NBTTagList nbttaglist = compound.getTagList("Locked", 10);
        lockList = new ArrayList<ItemStack>();
        resetLock();
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");
            if (j >= 0 && j < lockList.size()){
            	lockList.add(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Lock", this.lock);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < lockList.size(); ++i){
            if (lockList.get(i) != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                lockList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Locked", nbttaglist);

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
		if(lockList.size() < 1) {resetLock(); }
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
			&& getRecipe() != null && isFullRecipe()
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