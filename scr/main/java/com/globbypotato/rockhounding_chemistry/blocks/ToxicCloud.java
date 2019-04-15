package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.io.BlockIO;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ToxicCloud extends BlockIO {

	public ToxicCloud(String name) {
		super(name, Material.AIR, 0.0F, 0.0F, SoundType.CLOTH);
		this.setDefaultState(this.blockState.getBaseState());
        this.setTickRandomly(true);
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	return false;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos){
        return NULL_AABB;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
    	if(!world.isRemote){
   			world.setBlockToAir(pos);
    	}
        world.scheduleBlockUpdate(pos, this, 1, 0);
    }

    @Override
    public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
    	return true;
    }

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
        entityIn.attackEntityFrom(DamageSource.WITHER, 1.0F);
        if(entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityPlayer)){
        	((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 200));
        }
        if(entityIn instanceof EntityPlayer){
        	if(!((EntityPlayer)entityIn).capabilities.isCreativeMode){
            	((EntityPlayer)entityIn).addPotionEffect(new PotionEffect(MobEffects.POISON, 200));
        	}
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        return blockState != iblockstate;
    }

}