package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.machines.container.*;
import com.globbypotato.rockhounding_chemistry.machines.gui.*;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.*;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
			default: return null;
			case ModContents.labOvenID:
				if (entity != null && entity instanceof TileEntityLabOven){return new ContainerLabOven(player.inventory, (TileEntityLabOven) entity);}
			case ModContents.mineralSizerID:
				if (entity != null && entity instanceof TileEntityMineralSizer){return new ContainerMineralSizer(player.inventory, (TileEntityMineralSizer) entity);}
			case ModContents.mineralAnalyzerID:
				if (entity != null && entity instanceof TileEntityMineralAnalyzer){return new ContainerMineralAnalyzer(player.inventory, (TileEntityMineralAnalyzer) entity);}
			case ModContents.chemicalExtractorID:
				if (entity != null && entity instanceof TileEntityChemicalExtractor){return new ContainerChemicalExtractor(player.inventory, (TileEntityChemicalExtractor) entity);}
			case ModContents.crawlerAssemblerID:
				if (entity != null && entity instanceof TileEntityCrawlerAssembler){return new ContainerCrawlerAssembler(player.inventory, (TileEntityCrawlerAssembler) entity);}
			case ModContents.metalAlloyerID:
				if (entity != null && entity instanceof TileEntityMetalAlloyer){return new ContainerMetalAlloyer(player.inventory, (TileEntityMetalAlloyer) entity);}
		}
        return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x,y,z));
		switch(ID) {
			default: return null;
			case ModContents.labOvenID:
				if (entity != null && entity instanceof TileEntityLabOven) {return new GuiLabOven(player.inventory, (TileEntityLabOven) entity);}
			case ModContents.mineralSizerID:
				if (entity != null && entity instanceof TileEntityMineralSizer) {return new GuiMineralSizer(player.inventory, (TileEntityMineralSizer) entity);}
			case ModContents.mineralAnalyzerID:
				if (entity != null && entity instanceof TileEntityMineralAnalyzer) {return new GuiMineralAnalyzer(player.inventory, (TileEntityMineralAnalyzer) entity);}
			case ModContents.chemicalExtractorID:
				if (entity != null && entity instanceof TileEntityChemicalExtractor) {return new GuiChemicalExtractor(player.inventory, (TileEntityChemicalExtractor) entity);}
			case ModContents.crawlerAssemblerID:
				if (entity != null && entity instanceof TileEntityCrawlerAssembler) {return new GuiCrawlerAssembler(player.inventory, (TileEntityCrawlerAssembler) entity);}
			case ModContents.metalAlloyerID:
				if (entity != null && entity instanceof TileEntityMetalAlloyer) {return new GuiMetalAlloyer(player.inventory, (TileEntityMetalAlloyer) entity);}
		}
        return null;
	}

}
