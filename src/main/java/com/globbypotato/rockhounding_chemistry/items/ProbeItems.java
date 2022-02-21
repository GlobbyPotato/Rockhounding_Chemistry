package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.ArrayIO;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;

import net.minecraft.item.ItemStack;

public class ProbeItems extends ArrayIO{

	public ProbeItems(String name, String[] array) {
		super(name, array);
		setMaxStackSize(1);
	}

	public static int orbiterUpgrade(ItemStack insertingStack) {
		if(ModUtils.isOrbiterProbe(insertingStack)){
			switch(insertingStack.getItemDamage()){
				case 0: return 1;
				case 1: return 4;
				case 2: return 12;
				case 3: return 24;
				default: return 0;
			}
		}
		return 0;
	}
}