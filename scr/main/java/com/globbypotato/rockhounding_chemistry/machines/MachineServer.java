package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMachineServer;

import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

public class MachineServer extends BaseMachine{
    public MachineServer(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityMachineServer.class,GuiHandler.serverID);
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

}