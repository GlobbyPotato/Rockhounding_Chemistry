package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumAlloyB;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;

public class AlloyBBricks extends BaseMetaBlock {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumAlloyB.class);
	
    public AlloyBBricks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloyB.byMetadata(0)));
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumAlloyB.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumAlloyB type = (EnumAlloyB) state.getValue(VARIANT);
		return type.getMetadata();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}
}
