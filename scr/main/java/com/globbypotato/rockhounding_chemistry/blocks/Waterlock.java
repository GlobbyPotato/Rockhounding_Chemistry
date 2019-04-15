package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;
import java.util.Queue;
import java.util.Random;

import com.globbypotato.rockhounding_chemistry.blocks.io.BlockIO;
import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;
import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Waterlock extends BlockIO{

    public static final PropertyBool WET = PropertyBool.create("wet");

    public Waterlock(String name){
		super(name, Material.ROCK, 1.0F, 1.0F, SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(WET, Boolean.valueOf(false)));
    }

    @Override
    public int damageDropped(IBlockState state){
        return ((Boolean)state.getValue(WET)).booleanValue() ? 1 : 0;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        this.tryAbsorb(worldIn, pos, state);
    }

   @Override
   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos){
        this.tryAbsorb(worldIn, pos, state);
        super.neighborChanged(state, worldIn, pos, blockIn, fromPos);
    }

    public void tryAbsorb(World worldIn, BlockPos pos, IBlockState state){
        if (!((Boolean)state.getValue(WET)).booleanValue() && this.absorb(worldIn, pos)){
            worldIn.setBlockState(pos, state.withProperty(WET, Boolean.valueOf(true)), 2);
            worldIn.playEvent(2001, pos, Block.getIdFromBlock(Blocks.WATER));
        }
    }

    private boolean absorb(World worldIn, BlockPos pos){
        Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
        List<BlockPos> list = Lists.<BlockPos>newArrayList();
        queue.add(new Tuple(pos, Integer.valueOf(0)));
        int i = 0;

        while (!queue.isEmpty()){
            Tuple<BlockPos, Integer> tuple = (Tuple)queue.poll();
            BlockPos blockpos = tuple.getFirst();
            int j = ((Integer)tuple.getSecond()).intValue();

            for (EnumFacing enumfacing : EnumFacing.values()){
                BlockPos blockpos1 = blockpos.offset(enumfacing);

                if (worldIn.getBlockState(blockpos1).getMaterial() == Material.WATER){
                    worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 2);
                    list.add(blockpos1);
                    ++i;

                    if (j < 6){
                        queue.add(new Tuple(blockpos1, j + 1));
                    }
                }
            }

            if (i > 512){
                break;
            }
        }

        for (BlockPos blockpos2 : list){
            worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.AIR, false);
        }

        return i > 0;
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items){
        items.add(new ItemStack(this, 1, 0));
        items.add(new ItemStack(this, 1, 1));
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(WET, Boolean.valueOf((meta & 1) == 1));
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return ((Boolean)state.getValue(WET)).booleanValue() ? 1 : 0;
    }

    @Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {WET});
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
        if (((Boolean)stateIn.getValue(WET)).booleanValue()){
            EnumFacing enumfacing = EnumFacing.random(rand);

            if (enumfacing != EnumFacing.UP && !worldIn.getBlockState(pos.offset(enumfacing)).isTopSolid()){
                double d0 = (double)pos.getX();
                double d1 = (double)pos.getY();
                double d2 = (double)pos.getZ();

                if (enumfacing == EnumFacing.DOWN){
                    d1 = d1 - 0.05D;
                    d0 += rand.nextDouble();
                    d2 += rand.nextDouble();
                }else{
                    d1 = d1 + rand.nextDouble() * 0.8D;

                    if (enumfacing.getAxis() == EnumFacing.Axis.X){
                        d2 += rand.nextDouble();

                        if (enumfacing == EnumFacing.EAST){
                            ++d0;
                        }else{
                            d0 += 0.05D;
                        }
                    }else{
                        d0 += rand.nextDouble();

                        if (enumfacing == EnumFacing.SOUTH){
                            ++d2;
                        }else{
                            d2 += 0.05D;
                        }
                    }
                }
                worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
    
}