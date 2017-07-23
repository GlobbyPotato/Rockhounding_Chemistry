package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserRX;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserRay;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserSplitter;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserTX;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltMaker;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModTileEntities {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitySaltMaker.class, "RH_SaltMaker");

		GameRegistry.registerTileEntity(TileEntityMineCrawler.class, "RH_MineCrawler");

		GameRegistry.registerTileEntity(TileEntityLaserTX.class, "RH_LaserTX");
		GameRegistry.registerTileEntity(TileEntityLaserRX.class, "RH_LaserRX");
		GameRegistry.registerTileEntity(TileEntityLaserBeam.class, "RH_LaserBeam");
		GameRegistry.registerTileEntity(TileEntityLaserSplitter.class, "RH_LaserSplitter");
		GameRegistry.registerTileEntity(TileEntityLaserAmplifier.class, "RH_LaserAmplifier");
		GameRegistry.registerTileEntity(TileEntityLaserStabilizer.class, "RH_LaserStabilizer");
		GameRegistry.registerTileEntity(TileEntityElectroLaser.class, "RH_ElectroLaser");
		GameRegistry.registerTileEntity(TileEntityLaserRay.class, "RH_LaserRay");

		GameRegistry.registerTileEntity(TileEntityEarthBreaker.class, "RH_EarthBreaker");

		GameRegistry.registerTileEntity(TileEntityNitrogenTank.class, "RH_NitrogenTank");
		GameRegistry.registerTileEntity(TileEntityAirCompresser.class, "RH_AirCompresser");
		GameRegistry.registerTileEntity(TileEntityAirChiller.class, "RH_AirChiller");
	}
}