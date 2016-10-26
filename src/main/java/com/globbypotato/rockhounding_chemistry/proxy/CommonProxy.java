package com.globbypotato.rockhounding_chemistry.proxy;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModDictionary;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.handlers.ModTileEntities;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Load Config
		ModConfig.loadConfig(e);

		// Load Arrays
		ModArray.loadArray();

		// Register Contents
		ModBlocks.init();
		ModBlocks.register();
		ModItems.init();

		// Register tile entities
		ModTileEntities.registerTileEntities();
		
		// Register Spawning 
		GameRegistry.registerWorldGenerator(new ChemOresGenerator(), 1);

		// Register oreDictionary
		ModDictionary.loadDictionary();
	}

	public void init(FMLInitializationEvent e){
		// Register Recipes
		ModRecipes.init();

		//Register Guis
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
	}

	public void postInit(FMLPostInitializationEvent e){

	}

	public void registerTileEntitySpecialRenderer() {

	}

	public void registerRenderInformation() {

	}
}
