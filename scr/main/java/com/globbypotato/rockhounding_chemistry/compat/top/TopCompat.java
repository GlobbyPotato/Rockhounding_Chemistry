package com.globbypotato.rockhounding_chemistry.compat.top;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TopCompat{
	private static String TOPID = "theoneprobe";

	public static void init(){
        if (ModConfig.enableTOP && Loader.isModLoaded(TOPID)) {
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", "com.globbypotato.rockhounding_chemistry.compat.top.TopCastingBench$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", "com.globbypotato.rockhounding_chemistry.compat.top.TopSaltTank$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", "com.globbypotato.rockhounding_chemistry.compat.top.TopUltraBattery$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", "com.globbypotato.rockhounding_chemistry.compat.top.TopGAN$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", "com.globbypotato.rockhounding_chemistry.compat.top.TopMachines$getTOP");
        }
	}

}