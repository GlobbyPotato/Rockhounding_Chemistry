package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.blocks.io.MetaIO;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyGems;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AlloyBricksGems extends MetaIO {

	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumAlloyGems.class);

	public AlloyBricksGems(String name) {
		super(name, Material.IRON, EnumAlloyGems.getAlloys(), 1.0F, 2.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloyGems.values()[0]));
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	return false;
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumAlloyGems.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumAlloyGems)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

    @Override
	@SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

}