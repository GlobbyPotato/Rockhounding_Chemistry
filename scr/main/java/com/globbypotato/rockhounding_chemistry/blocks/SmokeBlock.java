package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SmokeBlock extends Block {

	public SmokeBlock(String name) {
		super(Material.AIR);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this), getRegistryName());
		setCreativeTab(null);
		setTickRandomly(true);
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	return false;
    }

    @Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos){
        return NULL_AABB;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    	if(!world.isRemote){
    		world.setBlockState(pos, Blocks.AIR.getDefaultState());
    	}
        world.scheduleBlockUpdate(pos, this, 1, 0);
    }

}