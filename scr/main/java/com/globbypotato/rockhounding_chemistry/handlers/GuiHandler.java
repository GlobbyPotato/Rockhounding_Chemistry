package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.container.ContainerAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerDisposer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerGanController;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabOven;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerNitrogenTank;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcController;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerSaltSeasoner;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerUltraBattery;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDisposer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiGanController;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiNitrogenTank;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiOwcController;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiSaltSeasoner;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiUltraBattery;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDisposer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityGanController;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcController;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltSeasoner;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityUltraBattery;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	public static final int labOvenID = 0;
	public static final int mineralSizerID = 1;
	public static final int mineralAnalyzerID = 2;
	public static final int chemicalExtractorID = 3;
	public static final int crawlerAssemblerID = 4;
	public static final int metalAlloyerID = 5;
	public static final int owcAssemblerID = 6;
	public static final int owcControllerID = 7;
	public static final int petrographerTableID = 8;
	public static final int saltSeasonerID = 9;
	public static final int dekatronID = 10;
	public static final int depositionChamberID = 11;
	public static final int earthBreakerID = 12;
	public static final int ganControllerID = 13;
	public static final int nitrogenTankID = 14;
	public static final int airCompresserID = 15;
	public static final int airChillerID = 16;
	public static final int laserAmplifierID = 17;
	public static final int electroLaserID = 18;
	public static final int ultraBatteryID = 19;
	public static final int castingBenchID = 20;
	public static final int labBlenderID = 21;
	public static final int disposerID = 22;
	public static final int pipelineValveID = 23;

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
			default: return null;
			case labOvenID:
				if (entity != null && entity instanceof TileEntityLabOven){return new ContainerLabOven(player.inventory, (TileEntityLabOven) entity);}
			case mineralSizerID:
				if (entity != null && entity instanceof TileEntityMineralSizer){return new ContainerMineralSizer(player.inventory, (TileEntityMineralSizer) entity);}
			case mineralAnalyzerID:
				if (entity != null && entity instanceof TileEntityMineralAnalyzer){return new ContainerMineralAnalyzer(player.inventory, (TileEntityMineralAnalyzer) entity);}
			case chemicalExtractorID:
				if (entity != null && entity instanceof TileEntityChemicalExtractor){return new ContainerChemicalExtractor(player.inventory, (TileEntityChemicalExtractor) entity);}
			case crawlerAssemblerID:
				if (entity != null && entity instanceof TileEntityMineCrawlerAssembler){return new ContainerMineCrawlerAssembler(player.inventory, (TileEntityMineCrawlerAssembler) entity);}
			case metalAlloyerID:
				if (entity != null && entity instanceof TileEntityMetalAlloyer){return new ContainerMetalAlloyer(player.inventory, (TileEntityMetalAlloyer) entity);}
			case owcAssemblerID:
				if (entity != null && entity instanceof TileEntityOwcAssembler){return new ContainerOwcAssembler(player.inventory, (TileEntityOwcAssembler) entity);}
			case owcControllerID:
				if (entity != null && entity instanceof TileEntityOwcController){return new ContainerOwcController(player.inventory, (TileEntityOwcController) entity);}
			case petrographerTableID:
				if (entity != null && entity instanceof TileEntityPetrographerTable){return new ContainerPetrographerTable(player.inventory, (TileEntityPetrographerTable) entity);}
			case saltSeasonerID:
				if (entity != null && entity instanceof TileEntitySaltSeasoner){return new ContainerSaltSeasoner(player.inventory, (TileEntitySaltSeasoner) entity);}
			case depositionChamberID:
				if (entity != null && entity instanceof TileEntityDepositionChamber){return new ContainerDepositionChamber(player.inventory, (TileEntityDepositionChamber) entity);}
			case earthBreakerID:
				if (entity != null && entity instanceof TileEntityEarthBreaker){return new ContainerEarthBreaker(player.inventory, (TileEntityEarthBreaker) entity);}
			case ganControllerID:
				if (entity != null && entity instanceof TileEntityGanController){return new ContainerGanController(player.inventory, (TileEntityGanController) entity);}
			case nitrogenTankID:
				if (entity != null && entity instanceof TileEntityNitrogenTank){return new ContainerNitrogenTank(player.inventory, (TileEntityNitrogenTank) entity);}
			case airCompresserID:
				if (entity != null && entity instanceof TileEntityAirCompresser){return new ContainerAirCompresser(player.inventory, (TileEntityAirCompresser) entity);}
			case airChillerID:
				if (entity != null && entity instanceof TileEntityAirChiller){return new ContainerAirChiller(player.inventory, (TileEntityAirChiller) entity);}
			case laserAmplifierID:
				if (entity != null && entity instanceof TileEntityLaserAmplifier){return new ContainerLaserAmplifier(player.inventory, (TileEntityLaserAmplifier) entity);}
			case electroLaserID:
				if (entity != null && entity instanceof TileEntityElectroLaser){return new ContainerElectroLaser(player.inventory, (TileEntityElectroLaser) entity);}
			case ultraBatteryID:
				if (entity != null && entity instanceof TileEntityUltraBattery){return new ContainerUltraBattery(player.inventory, (TileEntityUltraBattery) entity);}
			case castingBenchID:
				if (entity != null && entity instanceof TileEntityCastingBench){return new ContainerCastingBench(player.inventory, (TileEntityCastingBench) entity);}
			case labBlenderID:
				if (entity != null && entity instanceof TileEntityLabBlender){return new ContainerLabBlender(player.inventory, (TileEntityLabBlender) entity);}
			case disposerID:
				if (entity != null && entity instanceof TileEntityDisposer){return new ContainerDisposer(player.inventory, (TileEntityDisposer) entity);}
			case pipelineValveID:
				if (entity != null && entity instanceof TileEntityPipelineValve){return new ContainerPipelineValve(player.inventory, (TileEntityPipelineValve) entity);}
		}
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
			default: return null;
			case labOvenID:
				if (entity != null && entity instanceof TileEntityLabOven) {return new GuiLabOven(player.inventory, (TileEntityLabOven) entity);}
			case mineralSizerID:
				if (entity != null && entity instanceof TileEntityMineralSizer) {return new GuiMineralSizer(player.inventory, (TileEntityMineralSizer) entity);}
			case mineralAnalyzerID:
				if (entity != null && entity instanceof TileEntityMineralAnalyzer) {return new GuiMineralAnalyzer(player.inventory, (TileEntityMineralAnalyzer) entity);}
			case chemicalExtractorID:
				if (entity != null && entity instanceof TileEntityChemicalExtractor) {return new GuiChemicalExtractor(player.inventory, (TileEntityChemicalExtractor) entity);}
			case crawlerAssemblerID:
				if (entity != null && entity instanceof TileEntityMineCrawlerAssembler) {return new GuiMineCrawlerAssembler(player.inventory, (TileEntityMineCrawlerAssembler) entity);}
			case metalAlloyerID:
				if (entity != null && entity instanceof TileEntityMetalAlloyer) {return new GuiMetalAlloyer(player.inventory, (TileEntityMetalAlloyer) entity);}
			case owcAssemblerID:
				if (entity != null && entity instanceof TileEntityOwcAssembler) {return new GuiOwcAssembler(player.inventory, (TileEntityOwcAssembler) entity);}
			case owcControllerID:
				if (entity != null && entity instanceof TileEntityOwcController) {return new GuiOwcController(player.inventory, (TileEntityOwcController) entity);}
			case petrographerTableID:
				if (entity != null && entity instanceof TileEntityPetrographerTable) {return new GuiPetrographerTable(player.inventory, (TileEntityPetrographerTable) entity);}
			case saltSeasonerID:
				if (entity != null && entity instanceof TileEntitySaltSeasoner) {return new GuiSaltSeasoner(player.inventory, (TileEntitySaltSeasoner) entity);}
			case depositionChamberID:
				if (entity != null && entity instanceof TileEntityDepositionChamber) {return new GuiDepositionChamber(player.inventory, (TileEntityDepositionChamber) entity);}
			case earthBreakerID:
				if (entity != null && entity instanceof TileEntityEarthBreaker) {return new GuiEarthBreaker(player.inventory, (TileEntityEarthBreaker) entity);}
			case ganControllerID:
				if (entity != null && entity instanceof TileEntityGanController) {return new GuiGanController(player.inventory, (TileEntityGanController) entity);}
			case nitrogenTankID:
				if (entity != null && entity instanceof TileEntityNitrogenTank){return new GuiNitrogenTank(player.inventory, (TileEntityNitrogenTank) entity);}
			case airCompresserID:
				if (entity != null && entity instanceof TileEntityAirCompresser){return new GuiAirCompresser(player.inventory, (TileEntityAirCompresser) entity);}
			case airChillerID:
				if (entity != null && entity instanceof TileEntityAirChiller){return new GuiAirChiller(player.inventory, (TileEntityAirChiller) entity);}
			case laserAmplifierID:
				if (entity != null && entity instanceof TileEntityLaserAmplifier){return new GuiLaserAmplifier(player.inventory, (TileEntityLaserAmplifier) entity);}
			case electroLaserID:
				if (entity != null && entity instanceof TileEntityElectroLaser){return new GuiElectroLaser(player.inventory, (TileEntityElectroLaser) entity);}
			case ultraBatteryID:
				if (entity != null && entity instanceof TileEntityUltraBattery){return new GuiUltraBattery(player.inventory, (TileEntityUltraBattery) entity);}
			case castingBenchID:
				if (entity != null && entity instanceof TileEntityCastingBench){return new GuiCastingBench(player.inventory, (TileEntityCastingBench) entity);}
			case labBlenderID:
				if (entity != null && entity instanceof TileEntityLabBlender){return new GuiLabBlender(player.inventory, (TileEntityLabBlender) entity);}
			case disposerID:
				if (entity != null && entity instanceof TileEntityDisposer){return new GuiDisposer(player.inventory, (TileEntityDisposer) entity);}
			case pipelineValveID:
				if (entity != null && entity instanceof TileEntityPipelineValve){return new GuiPipelineValve(player.inventory, (TileEntityPipelineValve) entity);}
		}
        return null;
	}

}