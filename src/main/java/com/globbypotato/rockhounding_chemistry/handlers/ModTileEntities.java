package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.*;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityLabOven.class, "LabOven");
		GameRegistry.registerTileEntity(TileEntityMineralSizer.class, "MineralSizer");
		GameRegistry.registerTileEntity(TileEntityMineralAnalyzer.class, "MineralAnalyzer");
		GameRegistry.registerTileEntity(TileEntityChemicalExtractor.class, "ChemicalExtractor");
		GameRegistry.registerTileEntity(TileEntitySaltMaker.class, "SaltMaker");
		GameRegistry.registerTileEntity(TileEntityMineCrawler.class, "MineCrawler");
		GameRegistry.registerTileEntity(TileEntityCrawlerAssembler.class, "CrawlerAssembler");
		GameRegistry.registerTileEntity(TileEntityMetalAlloyer.class, "MetalAlloyer");
		GameRegistry.registerTileEntity(TileEntityLaserTX.class, "LaserTX");
		GameRegistry.registerTileEntity(TileEntityLaserRX.class, "LaserRX");
		GameRegistry.registerTileEntity(TileEntityLaserBeam.class, "LaserBeam");
		GameRegistry.registerTileEntity(TileEntityLaserSplitter.class, "LaserSplitter");
	}

}
