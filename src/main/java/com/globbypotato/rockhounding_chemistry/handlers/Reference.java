package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Reference {
	// Create Mod Reference 
	public static final String MODID = "rockhounding_chemistry";
    public static final String NAME = "Rockhounding Mod: Chemistry";
	public static final String CLIENT_PROXY_CLASS = "com.globbypotato.rockhounding_chemistry.proxy.ClientProxy";
	public static final String SERVER_PROXY_CLASS = "com.globbypotato.rockhounding_chemistry.proxy.CommonProxy";
    public static final String VERSION = "${version_mod}";

	//Create new Creative Tab with Icon
	public static CreativeTabs RockhoundingChemistry = new CreativeTabs("rockhoundingChemistry") {
		@Override
		public ItemStack getTabIconItem() { 
			return new ItemStack(ModItems.CHEMICAL_ITEMS, 1, EnumChemicals.FLUORITE_COMPOUND.ordinal()); 
		}
	};
}