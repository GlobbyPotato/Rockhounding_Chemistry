package com.globbypotato.rockhounding_chemistry.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;

public class RegistryHandler {

	/**
	 * Register the model for the given block
	 * 
	 * @param block
	 */
	public static void registerSingleModel(Block block) {
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, 0, model );
	}

	/**
	 * Register the models for every block variant
	 * 
	 * @param block
	 * @param variant
	 */
	public static void registerMetaModel(Block block, String[] variant) {
		Item item = Item.getItemFromBlock(block);
		for(int meta = 0; meta < variant.length; meta ++){
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(block.getRegistryName() + "_" + variant[meta], "inventory"));
		}
	}

	/**
	 * Register the model for the given item
	 * 
	 * @param item
	 */
	public static void registerSingleModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}

	/**
	 * Register the models for every item variant
	 * 
	 * @param item
	 * @param variant
	 */
	public static void registerMetaModel(Item item, String[] variant) {
		for(int meta = 0; meta < variant.length; meta ++){
			ModelLoader.setCustomModelResourceLocation(item, meta, new ModelResourceLocation(item.getRegistryName() + "_" + variant[meta], "inventory"));
		}
	}
}