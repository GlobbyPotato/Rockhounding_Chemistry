package com.globbypotato.rockhounding_chemistry.compat.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class WailaCompat {
	private static String part1 = "com.globbypotato.rockhounding_chemistry.compat.waila.";
	private static String part2 = ".callbackRegister";

	public static void init() {
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaUltraBattery" + part2);
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaOWCController" + part2);
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaCastingBench" + part2);
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaSaltTank" + part2);
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaGAN" + part2);
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaPipelinePump" + part2);
		FMLInterModComms.sendMessage("Waila", "register", part1 + "WailaPipelineValve" + part2);
	}

}