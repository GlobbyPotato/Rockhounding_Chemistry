package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.PullingCrucibleRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PullingCrucibleRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.oredict.OreDictionary;

public class TEPullingCrucibleTop extends TileEntityInv {

	public static int inputSlots = 1;
	
	public TEPullingCrucibleTop() {
		super(inputSlots, 0, 0, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isValidInput(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}



	//----------------------- HANDLERS -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "pulling_crucible_top";
	}



	//----------------------- RECIPE -----------------------
	public static ArrayList<PullingCrucibleRecipe> recipeList(){
		return PullingCrucibleRecipes.pulling_crucible_recipes;
	}

	public static PullingCrucibleRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public static boolean isValidInput(ItemStack stack) {
		if(!stack.isEmpty()){
			for(PullingCrucibleRecipe recipe: recipeList()){
				if(recipe.getType2()){
					ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
					if(!inputOreIDs.isEmpty()){
						if(inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict2()))){
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



	//----------------------- STRUCTURE -----------------------
	//base
		public TEPullingCrucibleBase getBase(){
			TileEntity te = this.world.getTileEntity(this.pos.down());
			if(this.world.getBlockState(this.pos.down()) != null && te instanceof TEPullingCrucibleBase){
				TEPullingCrucibleBase injector = (TEPullingCrucibleBase)te;
				if(injector.getFacing() == getFacing()){
					return injector;
				}
			}
			return null;
		}

		public boolean hasBase(){
			return getBase() != null;
		}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {}


}