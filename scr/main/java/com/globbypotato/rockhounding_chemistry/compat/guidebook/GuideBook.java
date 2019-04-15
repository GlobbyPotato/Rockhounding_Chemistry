package com.globbypotato.rockhounding_chemistry.compat.guidebook;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import gigaherz.guidebook.client.BookRegistryEvent;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class GuideBook {
    @Optional.Method(modid="gbook")
    @SubscribeEvent
    public static void registerBook(BookRegistryEvent event) {
        event.register(new ResourceLocation(Reference.MODID + ":xml/chembook.xml"));
        event.register(new ResourceLocation(Reference.MODID + ":xml/chemquest.xml"));
    }

}