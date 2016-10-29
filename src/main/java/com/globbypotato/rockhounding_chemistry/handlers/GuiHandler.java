package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.container.ContainerChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerLabOven;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerOwcController;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiOwcController;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcAssembler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityOwcController;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;

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
				if (entity != null && entity instanceof TileEntityCrawlerAssembler){return new ContainerCrawlerAssembler(player.inventory, (TileEntityCrawlerAssembler) entity);}
			case metalAlloyerID:
				if (entity != null && entity instanceof TileEntityMetalAlloyer){return new ContainerMetalAlloyer(player.inventory, (TileEntityMetalAlloyer) entity);}
			case owcAssemblerID:
				if (entity != null && entity instanceof TileEntityOwcAssembler){return new ContainerOwcAssembler(player.inventory, (TileEntityOwcAssembler) entity);}
			case owcControllerID:
				if (entity != null && entity instanceof TileEntityOwcController){return new ContainerOwcController(player.inventory, (TileEntityOwcController) entity);}
			case petrographerTableID:
				if (entity != null && entity instanceof TileEntityPetrographerTable){return new ContainerPetrographerTable(player.inventory, (TileEntityPetrographerTable) entity);}
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
				if (entity != null && entity instanceof TileEntityCrawlerAssembler) {return new GuiCrawlerAssembler(player.inventory, (TileEntityCrawlerAssembler) entity);}
			case metalAlloyerID:
				if (entity != null && entity instanceof TileEntityMetalAlloyer) {return new GuiMetalAlloyer(player.inventory, (TileEntityMetalAlloyer) entity);}
			case owcAssemblerID:
				if (entity != null && entity instanceof TileEntityOwcAssembler) {return new GuiOwcAssembler(player.inventory, (TileEntityOwcAssembler) entity);}
			case owcControllerID:
				if (entity != null && entity instanceof TileEntityOwcController) {return new GuiOwcController(player.inventory, (TileEntityOwcController) entity);}
			case petrographerTableID:
				if (entity != null && entity instanceof TileEntityPetrographerTable) {return new GuiPetrographerTable(player.inventory, (TileEntityPetrographerTable) entity);}
		}
        return null;
	}

}
