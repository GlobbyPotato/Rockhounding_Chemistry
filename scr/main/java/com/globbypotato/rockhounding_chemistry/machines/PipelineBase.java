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
    public static final AxisAlignedBB PLUG_AABB = new AxisAlignedBB(	0.250D, 0.250D, 0.250D, 0.750D, 0.750D, 0.750D);

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
        IBlockState actualstate = state.getActualState(worldIn, pos);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getHitbox(actualstate));
    }

    private static AxisAlignedBB getHitbox(IBlockState actualstate ){
        double xmin = 0.375D; double xmax = 0.625D;
        double zmin = 0.375D; double zmax = 0.625D;
        double ymin = 0.375D; double ymax = 0.625D;

        if(actualstate.getValue(DOWN).booleanValue()){	ymin = 0.000D; }
        if(actualstate.getValue(UP).booleanValue()){	ymax = 1.000D; }
        if(actualstate.getValue(NORTH).booleanValue()){	zmin = 0.000D; }
        if(actualstate.getValue(SOUTH).booleanValue()){	zmax = 1.000D; }
        if(actualstate.getValue(WEST).booleanValue()){	xmin = 0.000D; }
        if(actualstate.getValue(EAST).booleanValue()){	xmax = 1.000D; }
        
        return new AxisAlignedBB(xmin, ymin, zmin, xmax, ymax, zmax);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        IBlockState actualstate = this.getActualState(state, source, pos);
        return getHitbox(actualstate);
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