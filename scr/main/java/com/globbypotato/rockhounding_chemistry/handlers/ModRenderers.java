package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.machines.renders.RendererCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.renders.RendererPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.renders.RendererPipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.renders.RendererPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.renders.RendererUltraBattery;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityUltraBattery;

import net.minecraftforge.fml.client.registry.ClientRegistry;

public class ModRenderers {

	public static void specialRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityUltraBattery.class, new RendererUltraBattery());		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCastingBench.class, new RendererCastingBench());		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipelinePump.class, new RendererPipelinePump());		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPipelineValve.class, new RendererPipelineValve());		
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPetrographerTable.class, new RendererPetrographerTable());		
	}
}