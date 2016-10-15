package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModDictionary;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.handlers.ModTileEntities;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.proxy.CommonProxy;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class Rhchemistry {

	@Instance(Reference.MODID)
	public static Rhchemistry instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy globbypotatoProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		// Load Config
		ModConfig.loadConfig(event);

		// Load Arrays
		ModArray.loadArray();

		// Register Contents
		ModContents.init();
		ModContents.register();

		// Register Spawning 
		GameRegistry.registerWorldGenerator(new ChemOresGenerator(), 1);

		// Register oreDictionary
		ModDictionary.loadDictionary();

		// Register new Renders
		globbypotatoProxy.registerRenders(); 
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
        // Register Recipes
		ModRecipes.init();

		//Register Guis
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());

		// Register tile entities
		ModTileEntities.registerTileEntities();

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}

}