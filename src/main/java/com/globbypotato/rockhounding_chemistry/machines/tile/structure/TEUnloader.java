package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.item.ItemStack;

public class TEUnloader extends TileEntityInv{
	
	public TEUnloader() {
		super(0, 1, 0, 0);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "unloader";
	}



	//----------------------- SLOTS -----------------------
	public ItemStack unloaderSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {}

}