package com.globbypotato.rockhounding_chemistry.machines;

import java.util.List;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.PipelineIB;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PipelineBase extends Block{
    public static final PropertyBool NORTH = PropertyBool.create("north");
    public static final PropertyBool EAST = PropertyBool.create("east");
    public static final PropertyBool SOUTH = PropertyBool.create("south");
    public static final PropertyBool WEST = PropertyBool.create("west");
    public static final PropertyBool UP = PropertyBool.create("up");
    public static final PropertyBool DOWN = PropertyBool.create("down");
    public static final AxisAlignedBB CUBE_AABB = new AxisAlignedBB(	0.375D, 0.375D, 0.375D, 0.625D, 0.625D, 0.625D);
    public static final AxisAlignedBB PLUG_AABB = new AxisAlignedBB(	0.250D, 0.250D, 0.250D, 0.750D, 0.750D, 0.750D);
    public static final AxisAlignedBB DOWN_AABB = new AxisAlignedBB(	0.375D, 0.000D, 0.375D, 0.625D, 0.500D, 0.625D);
    public static final AxisAlignedBB UP_AABB = new AxisAlignedBB(		0.375D, 0.500D, 0.375D, 0.625D, 1.000D, 0.625D);
    public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(	0.375D, 0.375D, 0.687D, 0.625D, 0.625D, 1.000D);
    public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(	0.000D, 0.375D, 0.375D, 0.312D, 0.628D, 0.625D);
    public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(	0.375D, 0.375D, 0.000D, 0.625D, 0.625D, 0.312D);
    public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(	0.687D, 0.375D, 0.375D, 1.000D, 0.625D, 0.625D);

    public PipelineBase(float hardness, float resistance, String name){
        super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new PipelineIB(this).setRegistryName(name));
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(DOWN, Boolean.valueOf(false)).withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return null;
	}

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn){
        state = state.getActualState(worldIn, pos);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, CUBE_AABB);

        if (((Boolean)state.getValue(DOWN)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, DOWN_AABB);
        }

        if (((Boolean)state.getValue(UP)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, UP_AABB);
        }

        if (((Boolean)state.getValue(NORTH)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
        }

        if (((Boolean)state.getValue(EAST)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
        }

        if (((Boolean)state.getValue(WEST)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }
        
        if (((Boolean)state.getValue(WEST)).booleanValue()){
            addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
        }

    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return CUBE_AABB;
    }

    private static int getBoundingBoxIdx(IBlockState state){
        int i = 0;

        if (((Boolean)state.getValue(DOWN)).booleanValue()){
            i |= 1 << EnumFacing.DOWN.getFront(0).ordinal();
        }

        if (((Boolean)state.getValue(UP)).booleanValue()){
            i |= 1 << EnumFacing.UP.getFront(1).ordinal();
        }

        if (((Boolean)state.getValue(NORTH)).booleanValue()){
            i |= 1 << EnumFacing.NORTH.getFront(2).ordinal();
        }

        if (((Boolean)state.getValue(SOUTH)).booleanValue()){
            i |= 1 << EnumFacing.SOUTH.getFront(3).ordinal();
        }

        if (((Boolean)state.getValue(WEST)).booleanValue()){
            i |= 1 << EnumFacing.WEST.getFront(4).ordinal();
        }

        if (((Boolean)state.getValue(EAST)).booleanValue()){
            i |= 1 << EnumFacing.EAST.getFront(5).ordinal();
        }

        return i;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return true;
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos){
        return state.withProperty(DOWN, Boolean.valueOf(	this.canConnectTo(worldIn, pos, pos.down(),  EnumFacing.DOWN)))
        			.withProperty(UP, Boolean.valueOf(		this.canConnectTo(worldIn, pos, pos.up(), 	 EnumFacing.UP)))
        			.withProperty(NORTH, Boolean.valueOf(	this.canConnectTo(worldIn, pos, pos.north(), EnumFacing.NORTH)))
        			.withProperty(EAST, Boolean.valueOf(	this.canConnectTo(worldIn, pos, pos.east(),  EnumFacing.EAST)))
        			.withProperty(SOUTH, Boolean.valueOf(	this.canConnectTo(worldIn, pos, pos.south(), EnumFacing.SOUTH)))
        			.withProperty(WEST, Boolean.valueOf(	this.canConnectTo(worldIn, pos, pos.west(),  EnumFacing.WEST)));
    }

    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        return false;
    }

    public boolean hasFluidCapability(IBlockState state, Block block, IBlockAccess worldIn, BlockPos pos) {
		return worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos).hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
	}

    @Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {DOWN, UP, NORTH, EAST, WEST, SOUTH});
    }

}