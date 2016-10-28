package com.globbypotato.rockhounding_chemistry.handlers;


import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class Reference {
	// Create Mod Reference 
	public static final String MODID = "rockhounding_chemistry";
	public static final String VERSION = "v1.05";
	public static final String CLIENT_PROXY_CLASS = "com.globbypotato.rockhounding_chemistry.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.globbypotato.rockhounding_chemistry.proxy.CommonProxy";

	//Create new Creative Tab with Icon
	public static CreativeTabs RockhoundingChemistry = new CreativeTabs("rockhoundingChemistry") {
		public Item getTabIconItem() { return Items.IRON_INGOT; }
	};
}
