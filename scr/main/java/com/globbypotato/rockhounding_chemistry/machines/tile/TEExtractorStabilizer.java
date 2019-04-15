package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;

public class TEExtractorStabilizer extends TileEntityInv {
    public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5};

	public static int inputSlots = SLOT_INPUTS.length;

	public TEExtractorStabilizer() {
		super(inputSlots, 0, 0, 0);
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_INPUTS[0] && slot < SLOT_INPUTS.length && CoreUtils.hasConsumable(BaseRecipes.fe_catalyst, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "extractor_stabilizer";
	}



	//----------------------- SLOTS -----------------------
	public ItemStack catSlot(int slot){
		return this.input.getStackInSlot(slot);
	}



	//----------------------- CUSTOM -----------------------
	public int getBusySlots(){
		int ptNum = 0;
		for (int cat = 0; cat < SLOT_INPUTS.length; cat++){
			if(CoreUtils.hasConsumable(BaseRecipes.fe_catalyst, catSlot(cat))){
				ptNum++;	
			}
		}
		return ptNum;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {	}

}