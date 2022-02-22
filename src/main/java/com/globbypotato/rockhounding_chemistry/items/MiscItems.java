package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.ArrayIO;

import net.minecraft.item.ItemStack;

public class MiscItems extends ArrayIO {

	public MiscItems(String name, String[] array) {
		super(name, array);
	}

	@Override
    public int getItemStackLimit(ItemStack stack){
    	return stack.getItemDamage() == 15 
    		|| stack.getItemDamage() == 16 
    		|| stack.getItemDamage() == 0 
    		|| stack.getItemDamage() == 9 
    		|| stack.getItemDamage() == 30 
    		|| stack.getItemDamage() == 31 
    	    || stack.getItemDamage() == 32 
    	    || stack.getItemDamage() == 35 
    		? 1 : 64;
    }

}