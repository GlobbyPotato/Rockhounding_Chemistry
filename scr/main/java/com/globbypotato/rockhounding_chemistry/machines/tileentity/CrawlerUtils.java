package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;

public class CrawlerUtils {

	public static ItemStack compileItemStack(String name, int meta, int size) {
		if(name != null && !name.matches("None") && !name.matches("")){
            return new ItemStack(tempStateFromString(name, meta).getBlock(), 1, tempStateFromString(name, meta).getBlock().getMetaFromState(tempStateFromString(name, meta)));
		}
		return null;
	}

	public static boolean isValidStack(String name, int meta, int size){
		return compileItemStack(name, meta, size) != null;
	}

	public static String getScreenName(String name, int meta, int size){
		return isValidStack(name, meta, size) ? compileItemStack(name, meta, size).getDisplayName() + " x" + size : "None";
	}

	public static String getMode(int numMode) {
		return numMode == 0 ? "Breaking" : "Coring";
	}

	public static String decompileItemStack(String nameNbt) {
		if(nameNbt.matches("None") || nameNbt.matches("") || nameNbt == null){
			return "None";
		}else{
			return nameNbt;
		}
	}

	public static IBlockState tempStateFromString(String name, int meta){
		if(!name.matches("None") ){
	   		Block tempBlock = Block.getBlockFromName(name);
			return tempBlock.getStateFromMeta(meta);
		}
		return null;
	}

	public static IBlockState tempStateFromBlock(Block block, int meta){
		return block.getStateFromMeta(meta);
	}
}