package com.globbypotato.rockhounding_chemistry.fluids;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
//Courtesy of Choonster, (MIT License) https://github.com/Choonster/TestMod3

@Mod.EventBusSubscriber(Side.CLIENT)
public class FluidModelHandler {
	public static final FluidModelHandler INSTANCE = new FluidModelHandler();

	private static final String FLUID_MODEL_PATH = Reference.MODID + ":fluids";

	private FluidModelHandler() {}

	/**
	 * Register this mod's fluid, block and item models.
	 *
	 * @param event The event
	 */
	@SubscribeEvent
	public static void registerAllModels(ModelRegistryEvent event) {
		INSTANCE.registerFluidModels();
	}

	private void registerFluidModels() {
		ModFluids.MOD_FLUID_BLOCKS.forEach(this::registerFluidModel);
	}

	private void registerFluidModel(IFluidBlock fluidBlock) {
		final Item item = Item.getItemFromBlock((Block) fluidBlock);
		ModelBakery.registerItemVariants(item);
		final ModelResourceLocation modelResourceLocation = new ModelResourceLocation(FLUID_MODEL_PATH, fluidBlock.getFluid().getName());
		ModelLoader.setCustomMeshDefinition(item, MeshDefinitionFix.create(stack -> modelResourceLocation));
		ModelLoader.setCustomStateMapper((Block) fluidBlock, new StateMapperBase() {
			@Override
			protected ModelResourceLocation getModelResourceLocation(IBlockState p_178132_1_) {
				return modelResourceLocation;
			}
		});
	}
}