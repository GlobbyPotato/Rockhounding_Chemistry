package com.globbypotato.rockhounding_chemistry.integration;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class TiersSupport {
	private static String rhTiersID = "rockhounding_oretiers";

	public static boolean rhTiersLoaded(){return Loader.isModLoaded(rhTiersID);}

	public static ItemStack anthracite(){
		if(rhTiersLoaded()){
			Item anthracite = Item.REGISTRY.getObject(new ResourceLocation(rhTiersID + ":" + "tiersItems"));
			return new ItemStack(anthracite,1,0);
		}
		return null;
	}

	public static ItemStack bituminous(){
		if(rhTiersLoaded()){
			Item bituminous = Item.REGISTRY.getObject(new ResourceLocation(rhTiersID + ":" + "tiersItems"));
			return new ItemStack(bituminous,1,1);
		}
		return null;
	}

	public static ItemStack lignite(){
		if(rhTiersLoaded()){
			Item lignite = Item.REGISTRY.getObject(new ResourceLocation(rhTiersID + ":" + "tiersItems"));
			return new ItemStack(lignite,1,2);
		}
		return null;
	}

}