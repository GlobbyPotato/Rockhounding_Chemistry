package com.globbypotato.rockhounding_chemistry.machines;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class PipelineDuct extends PipelineBase {

	public PipelineDuct(float hardness, float resistance, String name) {
		super(hardness, resistance, name);
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return block instanceof PipelineDuct || block instanceof PipelinePump || block instanceof PipelineValve ? true : false;
    }

}