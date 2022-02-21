package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.blocks.io.BlockIO;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class UninspectedMineral extends BlockIO {

	public UninspectedMineral(String name) {
		super(name, Material.ROCK, 2.0F, 5.0F, SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState());
	}

}