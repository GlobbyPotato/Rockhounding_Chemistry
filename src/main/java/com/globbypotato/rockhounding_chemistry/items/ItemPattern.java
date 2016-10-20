package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

public class ItemPattern extends ItemBase {
	public ItemPattern(){
		super("ingotPattern");
		this.setMaxDamage(ModConfig.patternUses);
		this.setMaxStackSize(1);
	}
}
