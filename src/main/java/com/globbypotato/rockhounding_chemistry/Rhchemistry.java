package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, version = Reference.VERSION)
public class Rhchemistry {

	@Instance(Reference.MODID)
	public static Rhchemistry instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy globbypotatoProxy;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		globbypotatoProxy.preInit(event);
	}

	@EventHandler
	public void Init(FMLInitializationEvent event) {
		globbypotatoProxy.init(event);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		globbypotatoProxy.postInit(event);
	}

}