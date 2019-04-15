package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.blocks.io.MetaIO;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MiscBlocksA extends MetaIO {

	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumMiscBlocksA.class);

	public MiscBlocksA(String name) {
		super(name, Material.ROCK, EnumMiscBlocksA.getNames(), 1.0F, 2.0F, SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMiscBlocksA.values()[0]));
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
		return getDefaultState().withProperty(VARIANT, EnumMiscBlocksA.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMiscBlocksA)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items){
        for (int i = 0; i < this.array.length; ++i){
        	if(i != 1){
        		items.add(new ItemStack(this, 1, i));
        	}
        }
    }

	@SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

}