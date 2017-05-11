package com.globbypotato.rockhounding_chemistry.items.tools;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.item.ItemShears;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BamShears extends ItemShears {

	public BamShears(ToolMaterial material, String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(Reference.RockhoundingChemistry);
	}
}