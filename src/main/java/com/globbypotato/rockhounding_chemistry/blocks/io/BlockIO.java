package com.globbypotato.rockhounding_chemistry.blocks.io;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.blocks.BaseBlock;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockIO extends BaseBlock {

	public BlockIO(String name, Material material, float hardness, float resistance, SoundType stepSound) {
		super(Reference.MODID, name, material);
		setCreativeTab(Reference.RockhoundingChemistry);
		setHardness(hardness); 
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 1);
		setSoundType(stepSound);
	}

}