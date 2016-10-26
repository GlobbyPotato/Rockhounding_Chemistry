package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumOwc;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class OwcBlocks extends BaseMetaBlock {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumOwc.class);
	
    public OwcBlocks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumOwc.byMetadata(0)));
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumOwc.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumOwc type = (EnumOwc) state.getValue(VARIANT);
		return type.getMetadata();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	EnumOwc type = (EnumOwc) state.getValue(VARIANT);
    	return type.getMetadata() < 2 ? true : false;
    }
    
    @Override
    public int damageDropped(IBlockState state){
    	return getMetaFromState(state) >= 9 && getMetaFromState(state) <= 11 ? 8 : getMetaFromState(state) ;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
    	return false;
    }

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < this.array.length; i++){
			if(i < 9 || i > 11){
				list.add(new ItemStack(itemIn, 1, i));
			}
		}
	}

}