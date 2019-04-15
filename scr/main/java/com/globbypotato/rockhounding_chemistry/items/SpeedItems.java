package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.ArrayIO;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class SpeedItems extends ArrayIO{

	public SpeedItems(String name, String[] array) {
		super(name, array);
		setMaxStackSize(1);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)){
            for (int i = 0; i < this.itemArray.length; ++i){
            	if(i != 0){
            		items.add(new ItemStack(this, 1, i));
            	}
            }
        }
	}
}