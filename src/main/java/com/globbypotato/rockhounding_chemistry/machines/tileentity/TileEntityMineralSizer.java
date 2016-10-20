package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Utils;
import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.OreDictionary;
import scala.actors.threadpool.Arrays;

public class TileEntityMineralSizer extends TileEntityInvReceiver {

	public static int crushingSpeed;

	public static final int INPUT_SLOT = 0;
	//						FUEL_SLOT = 1;
	public static final int GEAR_SLOT = 2;
	public static final int INDUCTOR_SLOT = 3;

	public static final int OUTPUT_SLOT = 0;
	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;


	public TileEntityMineralSizer() {
		super(4,3,1);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				//input slots
				if(slot == 0 && (hasRecipe(insertingStack) || isIngotOredicted(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 1 && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 2 && Utils.areItemsEqualIgnoreMeta(new ItemStack(ModItems.gear), insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 3 && ItemStack.areItemsEqual(new ItemStack(ModItems.inductor),insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralSizer.HEIGHT;
	}

	//----------------------- CUSTOM -----------------------
	public int getCookTime(@Nullable ItemStack stack){
		return this.machineSpeed();
	}

	public int machineSpeed(){
		return crushingSpeed;
	}

	public int getFieldCount() {
		return 4;
	}

	public int getField(int id){
		switch (id){
		case 0: return this.powerCount;
		case 1: return this.powerMax;
		case 2: return this.cookTime;
		case 3: return this.totalCookTime;
		default:return 0;
		}
	}

	public void setField(int id, int value){
		switch (id){
		case 0: this.powerCount = value; break;
		case 1: this.powerMax = value; break;
		case 2: this.cookTime = value; break;
		case 3: this.totalCookTime = value; 
		}
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory){
		return inventory.getField(2) > 0;
	}

	private boolean allOutputsEmpty(){
		return (output.getStackInSlot(OUTPUT_SLOT) == null
				&& output.getStackInSlot(GOOD_SLOT) == null
				&& output.getStackInSlot(WASTE_SLOT) == null);
	}

	private boolean canOutput(ItemStack stack){

		if(ItemStack.areItemsEqual(new ItemStack(ModBlocks.mineralOres,1,0), stack)
				&& allOutputsEmpty()){
			return true;
		}
		else{
			if(stack != null && output.getStackInSlot(OUTPUT_SLOT) != null){
				ItemStack recipeOutput = getRecipeOutput(new ItemStack(stack.getItem(),1,stack.getItemDamage()));
				return ItemHandlerHelper.canItemStacksStack(output.getStackInSlot(OUTPUT_SLOT), recipeOutput);
			}
			else return true;
		}
	}

	private boolean hasGear(){
		return !(input.getStackInSlot(GEAR_SLOT) == null);
	}

	@Override
	protected boolean canInduct(){
		return !(input.getStackInSlot(INDUCTOR_SLOT) == null);
	}


	public boolean hasRecipe(ItemStack stack){
		if(stack != null){
			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				if(ItemStack.areItemsEqual(recipe.getInput(), stack)) return true;
			}
		}
		return false;
	}

	public ItemStack getRecipeOutput(){
		return getRecipeOutput(input.getStackInSlot(INPUT_SLOT));
	}


	public static ItemStack getRecipeOutput(ItemStack inputStack){
		if(inputStack != null){
			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				if(ItemStack.areItemsEqual(recipe.getInput(), inputStack)){
					return recipe.getOutput();
				}
			}
		}
		return null;
	}


	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("CookTimeTotal", this.totalCookTime);
		return compound;
	}



	//----------------------- PROCESS -----------------------

	@Override
	public void update(){
		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(!worldObj.isRemote){
			if(canProcess(input.getStackInSlot(INPUT_SLOT))){
				cookTime++;
				powerCount--;
				if(cookTime >= machineSpeed()) {
					cookTime = 0;
					process();
				}
			}
		}
	}

	private boolean canProcess(ItemStack stack) {
		return  (canOutput(stack)
				&& (hasRecipe(stack) || isIngotOredicted(stack))
				&& hasGear()
				&& powerCount >= machineSpeed());
	}

	private void process() {
		ItemStack recipeOutput = getRecipeOutput();
		//Special case for unidentified minerals
		if(recipeOutput == null){
			//calculate primary output
			ItemStack primaryStack = new ItemStack(ModBlocks.mineralOres, (rand.nextInt(4) + 1), extractCategory());
			output.setStackInSlot(OUTPUT_SLOT,primaryStack);
			//calculate secondary output
			if(rand.nextInt(100) < 25){
				ItemStack secondaryStack = new ItemStack(ModBlocks.mineralOres, (rand.nextInt(2) + 1), extractCategory());
				output.setStackInSlot(GOOD_SLOT,secondaryStack);
			}
			//calculate waste output
			if(rand.nextInt(100) < 5){
				ItemStack wasteStack = new ItemStack(ModBlocks.mineralOres, 1, extractCategory());
				output.setStackInSlot(WASTE_SLOT,wasteStack);
			}
		}
		else{ //All other cases
			if(output.internalInsertion(OUTPUT_SLOT, recipeOutput, true) == recipeOutput){
				return;
			}
			else{
				output.internalInsertion(OUTPUT_SLOT, recipeOutput, false);
			}
		}

		input.decrementSlot(INPUT_SLOT);
		input.damageSlot(GEAR_SLOT);
		this.markDirty();
	}


	private int extractCategory() {
		int getCategory = rand.nextInt(158);
		if(getCategory < 5){ return 1;										//arsenate
		}else if(getCategory >= 5   && getCategory < 17){  return 2;		//borate
		}else if(getCategory >= 17  && getCategory < 24){  return 3;		//carbonate
		}else if(getCategory >= 24  && getCategory < 33){  return 4;		//halide
		}else if(getCategory >= 33  && getCategory < 53){  return 5;		//native
		}else if(getCategory >= 53  && getCategory < 85){  return 6;		//oxide
		}else if(getCategory >= 85  && getCategory < 103){ return 7;		//phosphate
		}else if(getCategory >= 103  && getCategory < 119){return 8;		//silicate
		}else if(getCategory >= 119 && getCategory < 130){ return 9;		//sulfate
		}else if(getCategory >= 130 ){ return 10;							//sulfide
		} return 0;
	}




	private boolean isIngotOredicted(ItemStack stack) {
		if(stack != null){
			ArrayList<Integer> inputOreIDs = Utils.intArrayToList(OreDictionary.getOreIDs(stack));

			for(MineralSizerRecipe recipe: ModRecipes.sizerRecipes){
				ArrayList<Integer> recipeOreIDs = Utils.intArrayToList(OreDictionary.getOreIDs(recipe.getInput()));
				for(Integer oreID: recipeOreIDs){
					if(inputOreIDs.contains(oreID)) return true;
				}
				//if(oreName != null && oreName.contains("ingot")){
			}
		}
		return false;
	}


}