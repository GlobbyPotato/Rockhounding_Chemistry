package com.globbypotato.rockhounding_chemistry.blocks.itemblocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public class BaseItemBlock extends ItemBlock {
	public BaseItemBlock(Block block) {
        super(block);
        this.setHasSubtypes(true);
	}

	public int getMetadata(int meta){
		return meta;
	}
}