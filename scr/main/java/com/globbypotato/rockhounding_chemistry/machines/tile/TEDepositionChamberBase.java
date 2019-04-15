package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;

public class TEDepositionChamberBase extends TileEntityInv {

	public static int outputSlots = 1;
	public static int upgradeSlots = 3;

	public static final int CASING_SLOT = 0;
	public static final int INSULATION_SLOT = 1;
	public static final int SPEED_SLOT = 2;

	public TEDepositionChamberBase() {
		super(0, outputSlots, 0, upgradeSlots);

		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == CASING_SLOT && (isValidUpgrade(insertingStack, BaseRecipes.casing_upd)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == INSULATION_SLOT && (isValidUpgrade(insertingStack, BaseRecipes.insulation_upd)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack casingSlot() {
		return this.upgrade.getStackInSlot(CASING_SLOT);
	}

	public ItemStack insulationSlot() {
		return this.upgrade.getStackInSlot(INSULATION_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}

	public ItemStack outputSlot() {
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "deposition_chamber_base";
	}



	//----------------------- CUSTOM -----------------------
	public boolean isValidUpgrade(ItemStack insertingStack, ItemStack upgrade) {
		return !insertingStack.isEmpty() && ItemStack.areItemsEqual(insertingStack, upgrade);
	}

	public boolean hasCasing(){
		return isValidUpgrade(casingSlot(), BaseRecipes.casing_upd);
	}

	public boolean hasInsulation(){
		return isValidUpgrade(insulationSlot(), BaseRecipes.insulation_upd);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
	
	}

}