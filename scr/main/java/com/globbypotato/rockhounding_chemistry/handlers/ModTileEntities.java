package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserRX;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserSplitter;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserTX;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltMaker;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySaltMaker.class, "SaltMaker");
		GameRegistry.registerTileEntity(TileEntityMineCrawler.class, "MineCrawler");
		GameRegistry.registerTileEntity(TileEntityLaserTX.class, "LaserTX");
		GameRegistry.registerTileEntity(TileEntityLaserRX.class, "LaserRX");
		GameRegistry.registerTileEntity(TileEntityLaserBeam.class, "LaserBeam");
		GameRegistry.registerTileEntity(TileEntityLaserSplitter.class, "LaserSplitter");
		GameRegistry.registerTileEntity(TileEntityEarthBreaker.class, "EarthBreaker");
	}
}