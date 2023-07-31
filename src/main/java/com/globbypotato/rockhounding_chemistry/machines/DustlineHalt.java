package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.machines.PipelineBase;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DustlineHalt extends PipelineBase {

	public DustlineHalt(String name) {
		super(Reference.MODID, name);
		setCreativeTab(null);
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return block instanceof DustlineDuct || block instanceof DustlineHalt ? true : false;
    }

	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if (redstoneIsActivated(worldIn, pos)) {
			onRedstoneActivated(worldIn, pos, state);
		}
		else {
			onRedstoneDeactivated(worldIn, pos, state);
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		if (redstoneIsActivated(worldIn, pos)) {
			onRedstoneActivated(worldIn, pos, state);
		}
		else {
			onRedstoneDeactivated(worldIn, pos, state);
		}
	}

	private boolean redstoneIsActivated(World world, BlockPos pos) {
		if (isBlockPowered(world, pos)) {
			return true;
		}
		return false;
	}

	protected static final void onRedstoneActivated (World worldIn, BlockPos pos, IBlockState state) {
		if (!state.getValue(ISREDSTONEPOWERED).booleanValue())
			worldIn.setBlockState(pos, state.withProperty(ISREDSTONEPOWERED, Boolean.TRUE), 2);
	}

	protected static final void onRedstoneDeactivated (World worldIn, BlockPos pos, IBlockState state) {
		if (state.getValue(ISREDSTONEPOWERED).booleanValue())
			worldIn.setBlockState(pos, ModBlocks.DUSTLINE_DUCT.getDefaultState(), 2);
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
	public IBlockState getStateFromMeta(int meta)
	{
		return getDefaultState().withProperty(ISREDSTONEPOWERED, meta > 0);
	}

	@Override
	public int getMetaFromState(IBlockState state)
	{
		return (state.getValue(ISREDSTONEPOWERED).booleanValue()) ? 1 :0;
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote){
			if(CoreUtils.hasWrench(playerIn)){
				worldIn.setBlockState(pos, ModBlocks.DUSTLINE_DUCT.getDefaultState(), 2);
			}else{
				return false;
			}
		}
		return true;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.DUSTLINE_DUCT);
	}

}