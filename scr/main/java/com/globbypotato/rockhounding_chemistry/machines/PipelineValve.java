package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelineValve;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PipelineValve extends PipelineBase {

	public PipelineValve(float hardness, float resistance, String name) {
		super(hardness, resistance, name);
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return PLUG_AABB;
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityPipelineValve();
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return ((hasFluidCapability(state, block, worldIn, sidePos) && !(block instanceof PipelineValve)) || block instanceof PipelineDuct) && isSideEnabled(worldIn, pos, facing) ? true : false;
    }

	private boolean isSideEnabled(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		TileEntityPipelineValve valve = (TileEntityPipelineValve)worldIn.getTileEntity(pos);
		return valve.sideStatus[facing.ordinal()];
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if(player.isSneaking()){
			TileEntityPipelineValve pump = (TileEntityPipelineValve)world.getTileEntity(pos);
			if(!world.isRemote){
				pump.activation = !pump.activation;
				pump.markDirtyClient();
			}
	        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		}else{
			if (!world.isRemote) {
				player.openGui(Rhchemistry.instance, GuiHandler.pipelineValveID, world, pos.getX(), pos.getY(), pos.getZ());
			}
		}
		return true;
	}

}