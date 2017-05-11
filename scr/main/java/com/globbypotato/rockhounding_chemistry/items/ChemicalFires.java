package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.ModBlocks;

import net.minecraft.block.BlockFire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChemicalFires extends ItemArray {
	public ChemicalFires(String name, String[] array) {
		super(name, array);
	}

    @Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(facing == EnumFacing.UP && player.getHeldItemMainhand() != null && player.getHeldItemMainhand().getItem() instanceof ChemicalFires){
			BlockPos firePos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
			IBlockState flameState = world.getBlockState(firePos);
			if(flameState.getBlock() instanceof BlockFire){
				IBlockState fireState = ModBlocks.fireBlock.getDefaultState();
				world.setBlockState(firePos, fireState.getBlock().getStateFromMeta(stack.getItemDamage()), 3);
				player.getHeldItemMainhand().stackSize--;
			}
		}
		return EnumActionResult.SUCCESS;
    }

}