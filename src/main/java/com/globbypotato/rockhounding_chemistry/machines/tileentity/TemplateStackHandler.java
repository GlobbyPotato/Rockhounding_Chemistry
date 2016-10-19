package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class TemplateStackHandler extends ItemStackHandler{

	public TemplateStackHandler(int slots) {
		super(slots);

	}
	@Override
    protected int getStackLimit(int slot, ItemStack stack){
		return 1;
	}


	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		return null;
	}
}