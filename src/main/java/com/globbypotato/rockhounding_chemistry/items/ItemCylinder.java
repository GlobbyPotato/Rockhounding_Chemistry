package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

public class ItemCylinder extends ItemBase {
	public ItemCylinder(){
		super("cylinder");
		this.setMaxDamage(ModConfig.tubeUses);
		this.setMaxStackSize(1);
	}
}
