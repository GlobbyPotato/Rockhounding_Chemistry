package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.ItemIO;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SodiumPolyacrylate extends ItemIO {

	public SodiumPolyacrylate(String name) {
		super(name);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		World world = entityItem.world;
		if (!world.isRemote && entityItem.isInsideOfMaterial(Material.WATER)) {
			if(world.rand.nextInt(50) == 0){
				BlockPos pos = entityItem.getPosition();
				world.playSound(null, pos, SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
				world.setBlockState(pos, Blocks.SNOW.getDefaultState());
				entityItem.getItem().shrink(1);
				int polSize = entityItem.getItem().getCount();
				if(polSize <= 0){
					entityItem.setDead();
				}
			}
		}
		return super.onEntityItemUpdate(entityItem);
	}

    @Override
    public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        if(block == Blocks.CAULDRON){
            int i = iblockstate.getValue(BlockCauldron.LEVEL).intValue();
            if(i == 3){
                if (!worldIn.isRemote){
                    if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.SNOW))){
                       	playerIn.dropItem(new ItemStack(Blocks.SNOW), false);
                    }
	    	        if (!playerIn.capabilities.isCreativeMode){
	    	        	((BlockCauldron) block).setWaterLevel(worldIn, pos, iblockstate, 0);
	    	        }
       		        playerIn.addStat(StatList.CAULDRON_USED);
                }
                playerIn.playSound(SoundEvents.BLOCK_SNOW_PLACE, 0.5F, 1.5F);
                spawnParticles(worldIn, iblockstate, pos);
      			playerIn.getHeldItem(EnumHand.MAIN_HAND).shrink(1);
        	}
	    }
        return EnumActionResult.PASS;
    }

	private void spawnParticles(World worldIn, IBlockState iblockstate, BlockPos pos) {
		for (int p = 0; p < 8; p++){
            double d0 = pos.getX() + worldIn.rand.nextDouble();
            double d1 = pos.getY() + 1D;
            double d2 = pos.getZ() + worldIn.rand.nextDouble();
            worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

}