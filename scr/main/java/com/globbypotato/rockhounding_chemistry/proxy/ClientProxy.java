package com.globbypotato.rockhounding_chemistry.proxy;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.entities.EntitySmoke;
import com.globbypotato.rockhounding_chemistry.enums.EnumFires;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
	private static final Minecraft minecraft = Minecraft.getMinecraft();

	@Override
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);
		ModBlocks.initClient();
		ModItems.initClient();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		final BlockColors blockColors = minecraft.getBlockColors();
		final ItemColors itemColors = minecraft.getItemColors();

		blockColors.registerBlockColorHandler(new IBlockColor() {
			@Override
			public int colorMultiplier(IBlockState state, @Nullable IBlockAccess blockAccess, @Nullable BlockPos pos, int tintIndex) {
				return tintIndex == 1 ? EnumFires.getColor(state.getBlock().getMetaFromState(state)) : 0;
			}
		}, ModBlocks.fireBlock);

		itemColors.registerItemColorHandler(new IItemColor() {
			@Override
			public int getColorFromItemstack(ItemStack stack, int tintIndex) {
				@SuppressWarnings("deprecation")
				IBlockState iblockstate = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
				return blockColors.colorMultiplier(iblockstate, (IBlockAccess)null, (BlockPos)null, 1);
			}
		}, ModBlocks.fireBlock);


		final RenderManager renderManager = minecraft.getRenderManager();
        final RenderItem renderItem = minecraft.getRenderItem();
        RenderingRegistry.registerEntityRenderingHandler(EntitySmoke.class, new RenderSnowball(renderManager, ModItems.splashSmoke, renderItem));
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