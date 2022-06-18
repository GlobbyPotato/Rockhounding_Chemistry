package com.globbypotato.rockhounding_chemistry.compat.waila;

import net.minecraftforge.fml.common.event.FMLInterModComms;

public class CompatWaila {
	private static String part1 = "com.globbypotato.rockhounding_chemistry.compat.waila.";
	private static String part2 = ".callbackRegister";
	private static String wailaID = "waila";

	public static void init() {
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaMachinesA" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaMachinesB" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaMachinesC" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaMachinesD" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaMachinesE" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaMachinesF" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaPipelinePump" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaPipelineValve" + part2);
		FMLInterModComms.sendMessage(wailaID, "register", part1 + "WailaGaslinePump" + part2);
	}

}