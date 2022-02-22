package com.globbypotato.rockhounding_chemistry.entities;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;

public class EntityLootTables {
	public static final ResourceLocation TOXIC_SLIME = new ResourceLocation(Reference.MODID + ":" + "entities/toxic_slime");

	public static void registerLootTables() {
		if(ModConfig.enableHazard){
			LootTableList.register(TOXIC_SLIME);
		}
	}

}