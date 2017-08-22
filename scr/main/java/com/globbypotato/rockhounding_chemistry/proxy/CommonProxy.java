package com.globbypotato.rockhounding_chemistry.proxy;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.compat.crafttweaker.CTSupport;
import com.globbypotato.rockhounding_chemistry.compat.waila.WailaCompat;
import com.globbypotato.rockhounding_chemistry.entities.EntitySmoke;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.GlobbyEventHandler;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModDictionary;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.handlers.ModTileEntities;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.IMCUtils;
import com.globbypotato.rockhounding_chemistry.utils.ShapedNbtRecipe;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraft.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public void preInit(FMLPreInitializationEvent e){
		// Load Config
		ModConfig.loadConfig(e);

		// Register Fluids
		ModFluids.registerFluidContainers();

		// Register Contents
		ModBlocks.init();
		ModItems.init();

		// Regidter Events
		MinecraftForge.EVENT_BUS.register(new GlobbyEventHandler());	

		// Register tile entities
		ModTileEntities.registerTileEntities();

		// Register Spawning 
		GameRegistry.registerWorldGenerator(new ChemOresGenerator(), 1);

		// Register oreDictionary
		ModDictionary.loadDictionary();

		// Waila compatilbility
        WailaCompat.init();
	}

	public void init(FMLInitializationEvent e){
		// Register Recipes
		ModRecipes.init();
		MachineRecipes.machineRecipes();
		ShapedNbtRecipe.register();

		// Register Guis
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());

		// Register entities
        EntityRegistry.registerModEntity(EntitySmoke.class, "Screen Smoke", 0, Rhchemistry.instance, 64, 10, true);
	}

	public void imcInit(IMCEvent event) {
		// Add custom recipes
		IMCUtils.extraRecipes(event.getMessages());
	}

	public void postInit(FMLPostInitializationEvent e){
		// Register Craft Tweaker Support
		CTSupport.init();
	}

	public void registerTileEntitySpecialRenderer() {}

	public void registerRenderInformation() {}

	public void initFluidModel(Block block, Fluid fluid) {}

}