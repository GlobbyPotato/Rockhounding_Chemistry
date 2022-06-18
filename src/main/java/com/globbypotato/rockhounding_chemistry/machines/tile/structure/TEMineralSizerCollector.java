package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.item.ItemStack;

public class TEMineralSizerCollector extends TileEntityInv {

	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;

	public TEMineralSizerCollector() {
		super(0, 3, 0, 0);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack mainSlot(){
		return this.input.getStackInSlot(OUTPUT_SLOT);
	}

	public ItemStack goodSlot(){
		return this.input.getStackInSlot(GOOD_SLOT);
	}

	public ItemStack wasteSlot(){
		return this.input.getStackInSlot(WASTE_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "mineral_sizer_collector";
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){}

}