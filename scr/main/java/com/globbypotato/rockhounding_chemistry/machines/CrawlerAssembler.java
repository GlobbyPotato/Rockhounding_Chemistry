package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawlerAssembler;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class CrawlerAssembler extends BaseMachine{

    public CrawlerAssembler(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityMineCrawlerAssembler.class, GuiHandler.crawlerAssemblerID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
}