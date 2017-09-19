package com.globbypotato.rockhounding_chemistry.compat.top;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TopCompat{
	private static String TOPID = "theoneprobe";
	private static String modpath = "com.globbypotato.rockhounding_chemistry.compat.top.";

	public static void init(){
        if (ModConfig.enableTOP && Loader.isModLoaded(TOPID)) {
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopCastingBench$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopSaltTank$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopUltraBattery$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopGAN$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopMachines$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopPipelinePump$getTOP");
            FMLInterModComms.sendFunctionMessage(TOPID, "getTheOneProbe", modpath + "TopPipelineValve$getTOP");
        }
	}

}