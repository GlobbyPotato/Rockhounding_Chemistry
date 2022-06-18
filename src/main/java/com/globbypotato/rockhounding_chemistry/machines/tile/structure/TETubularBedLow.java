package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.BedReactorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TETubularBedController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class TETubularBedLow extends TileEntityInv {

	public static int inputSlots = 8;
	public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,6,7};
    public static final int SLOT_PURGE = 0;

	private int countCat;

	public TETubularBedLow() {
		super(inputSlots, 0, 0, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_INPUTS[0] && slot < inputSlots && isRecipeCatalyst(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

	}



	//----------------------- SLOTS -----------------------
	public ItemStack catalystSlot(int x){
		return this.input.getStackInSlot(x);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "tubular_bed_low";
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<BedReactorRecipe> recipeList(){
		return BedReactorRecipes.bed_reactor_recipes;
	}

	public BedReactorRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	@Override
	public int getSelectedRecipe() {
		return hasController() ? getController().getSelectedRecipe() : -1;
	}



	//----------------------- STRUCTURE -----------------------
	// controller
		public TETubularBedController getController(){
			BlockPos reactorPos = this.pos.offset(getFacing().getOpposite(), 1);
			TileEntity te = this.world.getTileEntity(reactorPos);
			if(this.world.getBlockState(reactorPos) != null && te instanceof TETubularBedController){
				TETubularBedController reactor = (TETubularBedController)te;
				if(reactor.getFacing() == getFacing()){
					return reactor;
				}
			}
			return null;
		}

		public boolean hasController(){
			return getController() != null;
		}




	//----------------------- CUSTOM -----------------------
	private boolean isRecipeCatalyst(ItemStack insertingStack) {
		return insertingStack.isItemEqualIgnoreDurability(getRecipeCatalyst());
	}

	public ItemStack getRecipeCatalyst(){ 
		return isValidPreset() ? recipeList().get(getSelectedRecipe()).getCatalyst() : ItemStack.EMPTY; 
	}

	public boolean isValidPreset(){
		return hasController() ? getController().isValidPreset() : false;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
	}

}