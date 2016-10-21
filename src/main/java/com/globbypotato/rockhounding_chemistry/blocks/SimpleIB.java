package com.globbypotato.rockhounding_chemistry.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;

public class SimpleIB extends ItemBlock {
	public SimpleIB(Block block) {
        super(block);
        this.setMaxDamage(0);
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

	public int getMetadata(int meta){
		return meta;
	}
	
}