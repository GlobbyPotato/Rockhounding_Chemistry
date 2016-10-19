package com.globbypotato.rockhounding_chemistry;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModDictionary;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.handlers.ModTileEntities;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.world.ChemOresGenerator;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class CommonProxy {

	public static final ArrayList<MineralSizerRecipe> sizerRecipes = new ArrayList<MineralSizerRecipe>();
	public static final ArrayList<LabOvenRecipe> labOvenRecipes = new ArrayList<LabOvenRecipe>();
	
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

		// Register new Renders
		//this.registerRenders(); 
	}

	public void init(FMLInitializationEvent e){
		// Register Recipes
		ModRecipes.init();
		this.initMachineRecipes();

		//Register Guis
		NetworkRegistry.INSTANCE.registerGuiHandler(Reference.MODID, new GuiHandler());



	}
	public void postInit(FMLPostInitializationEvent e){

	}



	public void registerTileEntitySpecialRenderer() {

	}

	public void registerRenderInformation() {

	}

	//TODO: Move recipe stuff to separate class rather than clutter commonproxy
	public void initMachineRecipes(){
		sizerRecipes.add(new MineralSizerRecipe(ModBlocks.mineralOres,null));
		sizerRecipes.add(new MineralSizerRecipe(Items.IRON_INGOT, 0, ModItems.chemicalDusts,16));
		sizerRecipes.add(new MineralSizerRecipe(Items.GOLD_INGOT,0,ModItems.chemicalDusts,45));
		sizerRecipes.add(new MineralSizerRecipe(Blocks.STONE,1,ModItems.chemicalItems,0));

		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,2,EnumFluid.WATER,EnumFluid.SULFURIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,3,EnumFluid.SULFURIC_ACID,EnumFluid.HYDROCHLORIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,4,EnumFluid.SULFURIC_ACID,EnumFluid.HYDROFLUORIC_ACID));
		labOvenRecipes.add(new LabOvenRecipe(ModItems.chemicalItems,5,EnumFluid.WATER,EnumFluid.SYNGAS));

	}
	
	public static EnumFluid getLabOvenSolvent(ItemStack input){
		for(LabOvenRecipe recipe: labOvenRecipes){
			if(ItemStack.areItemsEqual(input, recipe.getSolute())){
				return recipe.getSolvent();
			}
		}
		return EnumFluid.EMPTY;
	}
}
