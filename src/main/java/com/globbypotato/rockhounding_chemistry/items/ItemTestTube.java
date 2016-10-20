package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

public class ItemTestTube extends ItemBase {
	public ItemTestTube(){
		super("testTube");
		this.setMaxDamage(ModConfig.tubeUses);
		this.setMaxStackSize(1);
	}
}
