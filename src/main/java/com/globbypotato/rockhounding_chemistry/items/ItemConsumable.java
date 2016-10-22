package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

public class ItemConsumable extends ItemBase {
	public ItemConsumable(String name, int uses){
		super(name);
		this.setMaxDamage(uses);
		this.setMaxStackSize(1);
	}
}
