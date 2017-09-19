package com.globbypotato.rockhounding_chemistry.proxy;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.handlers.ModColors;
import com.globbypotato.rockhounding_chemistry.handlers.ModEntities;
import com.globbypotato.rockhounding_chemistry.handlers.ModRenderers;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		// Register Contents
		ModBlocks.initClient();
		ModItems.initClient();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		// Register colors
		ModColors.registerColors();

		// Register entity items
		ModEntities.registerEntity();       

		// Register renders
		ModRenderers.specialRenders();
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	@Override
	public void imcInit(IMCEvent e) {
		super.imcInit(e);
	}

	@Override
	public void initFluidModel(Block block, Fluid fluid) {
		final ModelResourceLocation fluidLocation = new ModelResourceLocation(
				Reference.MODID + ":textures/blocks" + block.getUnlocalizedName());

		ModelLoader.setCustomStateMapper(block, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState state) {
				return fluidLocation;
			}
		});
	}
}