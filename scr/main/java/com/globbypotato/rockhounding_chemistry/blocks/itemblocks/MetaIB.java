package com.globbypotato.rockhounding_chemistry.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;

public class MetaIB extends BaseItemBlock {
	private String[] enumNames;

	public MetaIB(Block block, String[] names) {
        super(block);
        this.enumNames = names;
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= this.enumNames.length){ i = 0; }
        return super.getUnlocalizedName(stack) + "." + this.enumNames[i];
    }

}