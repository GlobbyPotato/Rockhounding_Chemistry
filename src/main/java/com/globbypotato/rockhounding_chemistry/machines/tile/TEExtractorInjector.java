package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;

public class TEExtractorInjector extends TileEntityInv {
	public static final int TUBE_SLOT = 0;
	public static final int CYLINDER_SLOT = 1;
	public static final int SPEED_SLOT = 0;

	public static int inputSlots = 2;
	public static int upgradeSlots = 1;

	public TEExtractorInjector() {
		super(inputSlots, 0, 0, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == TUBE_SLOT && CoreUtils.hasConsumable(BaseRecipes.test_tube, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CYLINDER_SLOT && CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		
		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "extractor_injector";
	}



	//----------------------- SLOTS -----------------------
	public ItemStack tubeSlot() {
		return this.input.getStackInSlot(TUBE_SLOT);
	}

	public ItemStack cylinderSlot() {
		return this.input.getStackInSlot(CYLINDER_SLOT);
	}

	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasTube(){
		return CoreUtils.hasConsumable(BaseRecipes.test_tube, tubeSlot());
	}

	public boolean hasCylynder(){
		return CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, cylinderSlot());
	}

	public boolean hasConsumables(){
		return hasTube() && hasCylynder();
	}

	public int speedUpgrade(){
		return ModUtils.speedUpgrade(speedSlot());
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {}


}