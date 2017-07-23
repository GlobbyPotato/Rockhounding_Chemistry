package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.enums.EnumOres;
import com.globbypotato.rockhounding_core.blocks.BaseMetaBlock;
import com.globbypotato.rockhounding_core.blocks.itemblocks.BaseMetaIB;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MineralOres extends BlockIO{

	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumOres.class);

	public MineralOres(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound) {
		super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(new BaseMetaIB(this, EnumOres.getNames()).setRegistryName(name));
		setHarvestLevel("pickaxe", 1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumOres.values()[0]));
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return ((EnumOres)state.getValue(VARIANT)).ordinal() == 0 ? true : false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	return ((EnumOres)state.getValue(VARIANT)).ordinal() == 0 ? true : false;
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumOres.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumOres)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

}