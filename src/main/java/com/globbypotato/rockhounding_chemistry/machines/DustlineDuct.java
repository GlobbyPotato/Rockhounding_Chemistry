package com.globbypotato.rockhounding_chemistry.machines;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_core.machines.PipelineBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DustlineDuct extends PipelineBase {

	public DustlineDuct(String name) {
		super(Reference.MODID, name);
		setCreativeTab(Reference.RockhoundingChemistry);
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return block instanceof DustlineHalt || block instanceof DustlineDuct || block instanceof DustlinePump || isOrientedCabinet(worldIn, sidePos, facing) ? true : false;
    }

	public boolean isOrientedCabinet(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null){ 
			if( tile instanceof TEElementsCabinetBase || tile instanceof TEMaterialCabinetBase){
				TileEntityInv cabinet = (TileEntityInv)tile;
				if (cabinet.getFacing() == facing.getOpposite()){
					return true;
				}
			}
		}
		return false;
	}

	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (redstoneIsActivated(world, pos)) {
			onRedstoneActivated(world, pos);
		}
	}


	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (redstoneIsActivated(worldIn, pos)) {
			onRedstoneActivated(worldIn, pos);
		}
	}

	private boolean redstoneIsActivated(World world, BlockPos pos) {
		if (isBlockPowered(world, pos)) {
			return true;
		}
		return false;
	}

	protected static final void onRedstoneActivated (World worldIn, BlockPos pos) {
		worldIn.setBlockState(pos, ModBlocks.DUSTLINE_HALT.getDefaultState(), 2);
	}

	public boolean isBlockPowered(IBlockAccess world, BlockPos pos)
	{
		if (this.getRedstoneWeakPower(world, pos.down(), EnumFacing.DOWN) > 0)
		{
			return true;
		}
		else if (this.getRedstoneWeakPower(world, pos.up(), EnumFacing.UP) > 0)
		{
			return true;
		}
		else if (this.getRedstoneWeakPower(world, pos.north(), EnumFacing.NORTH) > 0)
		{
			return true;
		}
		else if (this.getRedstoneWeakPower(world, pos.south(), EnumFacing.SOUTH) > 0)
		{
			return true;
		}
		else if (this.getRedstoneWeakPower(world, pos.west(), EnumFacing.WEST) > 0)
		{
			return true;
		}
		else
		{
			return this.getRedstoneWeakPower(world, pos.east(), EnumFacing.EAST) > 0;
		}
	}

	public int getRedstoneWeakPower(IBlockAccess world, BlockPos pos, EnumFacing facing)
	{
		IBlockState iblockstate = world.getBlockState(pos);
		return iblockstate.getWeakPower(world, pos, facing);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){
			if(CoreUtils.hasWrench(playerIn)){
				worldIn.setBlockState(pos, ModBlocks.DUSTLINE_HALT.getDefaultState(), 2);
			}else{
				return false;
			}
		}
		return true;
	}

}