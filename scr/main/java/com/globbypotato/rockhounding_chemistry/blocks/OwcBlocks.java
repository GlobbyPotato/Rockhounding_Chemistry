package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.MetaIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumOwc;

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
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class OwcBlocks extends BaseMetaBlock {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumOwc.class);

    public OwcBlocks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(this);
		GameRegistry.register(new MetaIB(this, EnumOwc.getNames()).setRegistryName(name));
		setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumOwc.values()[0]));
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumOwc.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumOwc)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
		return false;
    }
	
    @Override
	public boolean isFullCube(IBlockState state) {
		return ((EnumOwc)state.getValue(VARIANT)) == EnumOwc.BULKHEAD || ((EnumOwc)state.getValue(VARIANT)) == EnumOwc.CONCRETE;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return ((EnumOwc)state.getValue(VARIANT)) == EnumOwc.BULKHEAD || ((EnumOwc)state.getValue(VARIANT)) == EnumOwc.CONCRETE;
	}

    @Override
    public int damageDropped(IBlockState state){
    	return getMetaFromState(state) == 9 ? 8 : getMetaFromState(state) ;
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
    	return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < this.array.length; i++){
			if(i != 9){
				list.add(new ItemStack(itemIn, 1, i));
			}
		}
	}

}