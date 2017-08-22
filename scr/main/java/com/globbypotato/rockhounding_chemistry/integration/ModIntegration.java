package com.globbypotato.rockhounding_chemistry.integration;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;

public class ModIntegration {

/*
 * railcraft
 */
	private static String railcraftID = "railcraft";
	public static boolean railcraftLoaded(){return Loader.isModLoaded(railcraftID);}

	public static Block generics(){
		if(railcraftLoaded()){
			Block block = Block.REGISTRY.getObject(new ResourceLocation(railcraftID + ":" + "generic"));
			return block;
		}
		return null;
	}
	
	private static ItemStack getGenerics(int i) { return new ItemStack (generics(), 1, i);}
	public static ItemStack railCokeBlock(){ 			if(railcraftLoaded()){ return getGenerics(6); } return null; }

}