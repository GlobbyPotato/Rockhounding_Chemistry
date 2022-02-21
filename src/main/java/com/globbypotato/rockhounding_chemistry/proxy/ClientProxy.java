package com.globbypotato.rockhounding_chemistry.proxy;

import com.globbypotato.rockhounding_chemistry.handlers.ModRenderers;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	@Override
	public void preInit(FMLPreInitializationEvent e){
		super.preInit(e);

		//Register mobs render
		ModRenderers.mobRenders();
	}

	@Override
	public void init(FMLInitializationEvent e) {
		super.init(e);
		// Register colors
//		ModColors.registerColors();

		// Register entity items
//		ModEntities.registerEntity();       

		// Register renders
		ModRenderers.specialRenders();
	}

	@Override
	public void postInit(FMLPostInitializationEvent e) {
		super.postInit(e);
	}
	
	@Override
	public void imcInit(IMCEvent e) {
		super.imcInit(e);
	}

}