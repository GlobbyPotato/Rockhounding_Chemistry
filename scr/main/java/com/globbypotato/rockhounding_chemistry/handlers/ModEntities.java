package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.entities.EntitySmoke;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModEntities {
	private static final Minecraft minecraft = Minecraft.getMinecraft();

	public static void registerEntity() {
		final RenderManager renderManager = minecraft.getRenderManager();
        final RenderItem renderItem = minecraft.getRenderItem();
        RenderingRegistry.registerEntityRenderingHandler(EntitySmoke.class, new RenderSnowball(renderManager, ModItems.splashSmoke, renderItem));
	}

}