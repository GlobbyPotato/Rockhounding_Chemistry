package com.globbypotato.rockhounding_chemistry.items;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

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

public class ChemicalItems extends ArrayIO {
	public ChemicalItems(String name, String[] array) {
		super(name, array);
	}

	@Override
	public boolean onEntityItemUpdate(EntityItem entityItem) {
		if(entityItem.getEntityItem().getItemDamage() == 0){
			World world = entityItem.worldObj;
			if (!world.isRemote && entityItem.isInsideOfMaterial(Material.WATER)) {
				if(rand.nextInt(50) == 0){
					BlockPos pos = entityItem.getPosition();
					world.playSound(null, pos, SoundEvents.BLOCK_SNOW_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
					world.setBlockState(pos, Blocks.SNOW.getDefaultState());
					entityItem.getEntityItem().stackSize--;
					int polSize = entityItem.getEntityItem().stackSize;
					if(entityItem.getEntityItem().stackSize <= 0){
						entityItem.setDead();
					}
				}
			}
		}
		return super.onEntityItemUpdate(entityItem);
	}

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();
        if(block == Blocks.CAULDRON){
            int i = ((Integer)iblockstate.getValue(BlockCauldron.LEVEL)).intValue();
            if(i == 3){
	        	if(playerIn.getHeldItem(EnumHand.MAIN_HAND) != null && playerIn.getHeldItemMainhand().isItemEqual(BaseRecipes.polymer)){
	                if (!worldIn.isRemote){
                        if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Blocks.SNOW))){
                        	playerIn.dropItem(new ItemStack(Blocks.SNOW), false);
                        }
                		((BlockCauldron) block).setWaterLevel(worldIn, pos, iblockstate, 0);
        		        playerIn.addStat(StatList.CAULDRON_USED);
	                }
                    playerIn.playSound(SoundEvents.BLOCK_SNOW_PLACE, 0.5F, 1.5F);
                    spawnParticles(worldIn, iblockstate, pos);
        			playerIn.getHeldItem(EnumHand.MAIN_HAND).stackSize--;
			        return EnumActionResult.SUCCESS;
	        	}else{
	    	        return EnumActionResult.FAIL;
	        	}
        	}else{
    	        return EnumActionResult.FAIL;
        	}
	    }
        return EnumActionResult.FAIL;
    }

    Random rand = new Random();
	private void spawnParticles(World worldIn, IBlockState iblockstate, BlockPos pos) {
		for (int p = 0; p < 8; p++){
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + 1D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            worldIn.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

}