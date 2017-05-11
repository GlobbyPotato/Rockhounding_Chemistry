package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.MetaIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AlloyBricks extends BaseMetaBlock {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumAlloy.class);
	
    public AlloyBricks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(this);
		GameRegistry.register(new MetaIB(this, EnumAlloy.getAlloys()).setRegistryName(name));
		setHarvestLevel("pickaxe", 1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloy.values()[0]));
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumAlloy.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumAlloy)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

}