package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerGearRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerGearRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TEMineralSizerTank extends TileEntityInv {
    public static final int SLOT_INPUTS[] = new int[]{0,1,2,3};

    public int rotation;

	public static int inputSlots = SLOT_INPUTS.length;

	public TEMineralSizerTank() {
		super(inputSlots, 0, 0, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_INPUTS[0] && slot < inputSlots && CoreUtils.hasConsumable(isValidGear(insertingStack), insertingStack) ){
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
		this.rotation = compound.getInteger("Rolling");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Rolling", getRolling());
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
		return "mineral_sizer_tank";
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasConsumables(){
		int gearFail = 0;
		for (int gear = 0; gear < inputSlots; gear++){
			if(CoreUtils.hasConsumable(getValidGear(gear), gearSlot(gear))){
				gearFail++;	
			}
		}
		return gearFail == inputSlots;
	}

	public static ArrayList<MineralSizerGearRecipe> recipeList(){
		return MineralSizerGearRecipes.mineral_sizer_gears;
	}

	public static MineralSizerGearRecipe getRecipeList(int x){
		return recipeList().get(x);
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

	private ItemStack getValidGear(int gear) {
		if(!gearSlot(gear).isEmpty()) {
			for(MineralSizerGearRecipe recipe: recipeList()){
				if(recipe.getGear().isItemEqualIgnoreDurability(gearSlot(gear))) {
					return recipe.getGear();
				}
			}
		}
		return ItemStack.EMPTY;
	}



	public int getRolling(){
		return this.rotation;
	}


	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			if(this.isActive() && hasConsumables()){
				this.rotation += 8;
				if(this.rotation >= 359){
					this.rotation = 0;
				}
				this.markDirtyClient();
			}
		}
	}

}