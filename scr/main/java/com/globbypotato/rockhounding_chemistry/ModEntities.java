package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.entities.EntityToxicSlime;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(Reference.MODID)
public class ModEntities {
	public static int mobID = 0;

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

		@SubscribeEvent
		public static void registerEntitied(final RegistryEvent.Register<EntityEntry> event) {
			if(ModConfig.enableHazard){
				EntityRegistry.registerModEntity(new ResourceLocation(Reference.MODID, "toxic_slime"), EntityToxicSlime.class, "toxic_slime", mobID++, Rhchemistry.instance, 65, 1, false, 8359211, 10793055);
			}
		}
	}

}