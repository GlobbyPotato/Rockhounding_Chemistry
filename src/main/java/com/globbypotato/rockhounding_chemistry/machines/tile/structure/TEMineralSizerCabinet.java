package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerGearRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerGearRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEMineralSizerCabinet extends TileEntityInv {

    public int rotation;

	public static int inputSlots = 45;

	public TEMineralSizerCabinet() {
		super(inputSlots, 0, 0, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot < inputSlots && CoreUtils.hasConsumable(isValidGear(insertingStack), insertingStack) ){
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
	public ItemStack gearSlot(int slot){
		return this.input.getStackInSlot(slot);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "mineral_sizer_cabinet";
	}



	//----------------------- CUSTOM -----------------------
	public static ArrayList<MineralSizerGearRecipe> recipeList(){
		return MineralSizerGearRecipes.mineral_sizer_gears;
	}

	private ItemStack isValidGear(ItemStack insertingStack) {
		if(!insertingStack.isEmpty()) {
			for(MineralSizerGearRecipe recipe: recipeList()){
				if(recipe.getGear().isItemEqualIgnoreDurability(insertingStack)) {
					return recipe.getGear();
				}
			}
		}
		return ItemStack.EMPTY;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){

	}

}