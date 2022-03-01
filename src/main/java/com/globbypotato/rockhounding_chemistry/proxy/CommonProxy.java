package com.globbypotato.rockhounding_chemistry.proxy;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.compat.crafttweaker.CTSupport;
import com.globbypotato.rockhounding_chemistry.compat.tinker.TinkerSupport;
import com.globbypotato.rockhounding_chemistry.compat.top.CompatTop;
import com.globbypotato.rockhounding_chemistry.compat.waila.CompatWaila;
import com.globbypotato.rockhounding_chemistry.entities.EntityLootTables;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.GlobbyEventHandler;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModTileEntities;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		MinecraftForge.EVENT_BUS.register(new Rhchemistry());

		// Load Config
		ModConfig.loadConfig(e);

		// Register Fluids
		ModFluids.registerFluidContainers();

		// Register Loot Tables
		EntityLootTables.registerLootTables();

		// Register Events
		MinecraftForge.EVENT_BUS.register(new GlobbyEventHandler());	

		// Register tile entities
		ModTileEntities.registerTileEntities();

		// Register Spawning 
		GameRegistry.registerWorldGenerator(new ChemOresGenerator(), 1);

		// Waila/TOP compatibility
        CompatWaila.init();
        CompatTop.init();
		CTSupport.loadSupport();
		TinkerSupport.loadSupport();
	}

	public void init(FMLInitializationEvent e){
		// Register Guis
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());
	}

	public void imcInit(IMCEvent event) {

	}

	public void postInit(FMLPostInitializationEvent e){
		//
	}

}