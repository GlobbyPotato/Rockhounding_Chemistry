package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEPipelineValve;
import com.globbypotato.rockhounding_core.machines.PipelineBase;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PipelineValve extends PipelineBase {

	public PipelineValve(String name) {
		super(Reference.MODID, name);
		setCreativeTab(Reference.RockhoundingChemistry);
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return PLUG_AABB;
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TEPipelineValve();
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return (block instanceof PipelineDuct || block instanceof PipelinePump) 
        	|| (hasFluidCapability(state, block, worldIn, sidePos) && isSideEnabled(worldIn, pos, facing)) ? true : false;
    }

	private static boolean isSideEnabled(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		TEPipelineValve valve = (TEPipelineValve)worldIn.getTileEntity(pos);
		return valve.sideStatus[facing.ordinal()];
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(player.isSneaking()){
			TEPipelineValve pump = (TEPipelineValve)world.getTileEntity(pos);
			if(!world.isRemote){
				pump.activation = !pump.activation;
				pump.markDirtyClient();
			}
	        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		}else{
			if (!world.isRemote) {
				player.openGui(Rhchemistry.instance, GuiHandler.pipeline_valve_ID, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

}