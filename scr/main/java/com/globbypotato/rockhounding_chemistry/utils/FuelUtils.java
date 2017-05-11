package com.globbypotato.rockhounding_chemistry.utils;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public class FuelUtils {

	public static boolean isItemFuel(ItemStack stack){
		return FuelUtils.getItemBurnTime(stack) > 0;
	}

	public static int getItemBurnTime(ItemStack stack){
		if (stack == null){
			return 0;
		}else{
			Item item = stack.getItem();
			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR){
				Block block = Block.getBlockFromItem(item);
				if (block == Blocks.WOODEN_SLAB){return 150;}
				if (block.getDefaultState().getMaterial() == Material.WOOD){return 300;}
				if (block == Blocks.COAL_BLOCK){return 14400;}
			}
			if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
			if (item == Items.STICK) return 100;
			if (item == Items.COAL) return 1600;
			if (item == Items.LAVA_BUCKET) return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
			if (item == Items.BLAZE_ROD) return 2400;
			return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
		}
	}
}