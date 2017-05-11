package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModBlocks;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class Reference {
	// Create Mod Reference 
	public static final String MODID = "rockhounding_chemistry";
    public static final String NAME = "Rockhounding Mod: Chemistry";
    public static final String VERSION = "${version_mod}";
	public static final String CLIENT_PROXY_CLASS = "com.globbypotato.rockhounding_chemistry.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.globbypotato.rockhounding_chemistry.proxy.CommonProxy";

	//Create new Creative Tab with Icon
	public static CreativeTabs RockhoundingChemistry = new CreativeTabs("rockhoundingChemistry") {
		public Item getTabIconItem() { return Item.getItemFromBlock(ModBlocks.mineralSizer); }
	};
}