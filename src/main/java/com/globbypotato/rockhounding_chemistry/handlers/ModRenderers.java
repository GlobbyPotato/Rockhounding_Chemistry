package com.globbypotato.rockhounding_chemistry.handlers;

import com.globbypotato.rockhounding_chemistry.entities.EntityToxicSlime;
import com.globbypotato.rockhounding_chemistry.entities.renderer.RenderToxicSlime;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererExtractorBalance;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererFluidTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererGaslinePump;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererLeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererMaterialCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererPipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererProfilingBench;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererShredderTable;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererWashingTank;
import com.globbypotato.rockhounding_chemistry.machines.render.RendererWaterPump;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEProfilingBench;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWaterPump;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEGaslinePump;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelineValve;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorBalance;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEShredderTable;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;

import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class ModRenderers {

	public static void specialRenders() {
		ClientRegistry.bindTileEntitySpecialRenderer(TEMineralSizerTank.class, new RendererMineralSizerTank());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEFluidTank.class, new RendererFluidTank());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEProfilingBench.class, new RendererProfilingBench());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEEvaporationTank.class, new RendererEvaporationTank());		

		ClientRegistry.bindTileEntitySpecialRenderer(TEMultivessel.class, new RendererMultivessel());		
		ClientRegistry.bindTileEntitySpecialRenderer(TELeachingVatTank.class, new RendererLeachingVatTank());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEExtractorBalance.class, new RendererExtractorBalance());
		ClientRegistry.bindTileEntitySpecialRenderer(TEOrbiter.class, new RendererOrbiter());
		ClientRegistry.bindTileEntitySpecialRenderer(TEFlotationTank.class, new RendererFlotationTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TEContainmentTank.class, new RendererContainmentTank());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEWaterPump.class, new RendererWaterPump());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEBufferTank.class, new RendererBufferTank());
		ClientRegistry.bindTileEntitySpecialRenderer(TEShredderTable.class, new RendererShredderTable());

		ClientRegistry.bindTileEntitySpecialRenderer(TEWashingTank.class, new RendererWashingTank());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEMaterialCabinetTop.class, new RendererMaterialCabinetTop());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEElementsCabinetTop.class, new RendererElementsCabinetTop());		

		ClientRegistry.bindTileEntitySpecialRenderer(TEPipelinePump.class, new RendererPipelinePump());		
		ClientRegistry.bindTileEntitySpecialRenderer(TEPipelineValve.class, new RendererPipelineValve());
		ClientRegistry.bindTileEntitySpecialRenderer(TEGaslinePump.class, new RendererGaslinePump());
	}

	public static void mobRenders() {
		if(ModConfig.enableHazard){
			RenderingRegistry.registerEntityRenderingHandler(EntityToxicSlime.class, RenderToxicSlime::new);
		}
	}

}