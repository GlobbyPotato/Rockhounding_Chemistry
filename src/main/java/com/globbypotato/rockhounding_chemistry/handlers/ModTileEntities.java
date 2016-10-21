package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.*;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySaltMaker.class, "SaltMaker");
		GameRegistry.registerTileEntity(TileEntityMineCrawler.class, "MineCrawler");
		GameRegistry.registerTileEntity(TileEntityLaserTX.class, "LaserTX");
		GameRegistry.registerTileEntity(TileEntityLaserRX.class, "LaserRX");
		GameRegistry.registerTileEntity(TileEntityLaserBeam.class, "LaserBeam");
		GameRegistry.registerTileEntity(TileEntityLaserSplitter.class, "LaserSplitter");
	}

}
