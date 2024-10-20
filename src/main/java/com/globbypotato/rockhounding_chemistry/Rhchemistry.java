package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.proxy.CommonProxy;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, dependencies = "required-after:rockhounding_core@[1.12.2-3.30,); required-after:gbook@[2.9,);")
public class Rhchemistry {

	@Instance(Reference.MODID)
	public static Rhchemistry instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy globbypotatoProxy;

	@EventHandler
	public static void preInit(FMLPreInitializationEvent event) {
		globbypotatoProxy.preInit(event);
	}

	@EventHandler
	public static void Init(FMLInitializationEvent event) {
		globbypotatoProxy.init(event);
	}

	@EventHandler
	public static void postInit(FMLPostInitializationEvent event) {
		globbypotatoProxy.postInit(event);
	}

	@EventHandler
	public static void handleIMCMessage(FMLInterModComms.IMCEvent event) {
		globbypotatoProxy.imcInit(event);
	}

}