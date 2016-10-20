package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

public class ItemGear extends ItemBase {
	public ItemGear(){
		super("gear");
		this.setMaxDamage(ModConfig.gearUses);
		this.setMaxStackSize(1);

	}
}
