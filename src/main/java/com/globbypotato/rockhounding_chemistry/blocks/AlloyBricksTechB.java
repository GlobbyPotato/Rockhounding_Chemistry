package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.blocks.io.MetaIO;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyTechB;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class AlloyBricksTechB extends MetaIO {

	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumAlloyTechB.class);

	public AlloyBricksTechB(String name) {
		super(name, Material.IRON, EnumAlloyTechB.getAlloys(), 3.0F, 7.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloyTechB.values()[0]));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumAlloyTechB.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumAlloyTechB)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

}