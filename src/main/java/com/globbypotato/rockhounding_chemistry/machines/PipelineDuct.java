package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.machines.PipelineBase;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PipelineDuct extends PipelineBase {

	public PipelineDuct(String name) {
		super(Reference.MODID, name);
		setCreativeTab(Reference.RockhoundingChemistry);
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return block instanceof PipelineHalt || block instanceof PipelineDuct || block instanceof PipelinePump || block instanceof PipelineValve ? true : false;
    }

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){
			if(CoreUtils.hasWrench(playerIn)){
				worldIn.setBlockState(pos, ModBlocks.PIPELINE_HALT.getDefaultState(), 2);
			}else{
				return false;
			}
		}
		return true;
	}

}