package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumOres;

import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MineralOres extends BlockOre implements IMetaBlockName{

	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumOres.class);
    private static final AxisAlignedBB CAT = new AxisAlignedBB(0.3125D, 0.0D, 0.3125D, 0.6875D, 0.375D, 0.6875D);
    private static final AxisAlignedBB MIN = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

	public MineralOres(float hardness, float resistance, String name) {
		super();
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 1);
		setCreativeTab(Reference.RockhoundingChemistry);
		setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumOres.byMetadata(0)));
	}
	
    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
		EnumOres type = (EnumOres) state.getValue(VARIANT);
    	return type.getMetadata() == 0 ? true : false;
    }

    @Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos){
		EnumOres type = (EnumOres) state.getValue(VARIANT);
    	return type.getMetadata() == 0 ? MIN : CAT;
    }

	@Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        return super.canPlaceBlockAt(worldIn, pos);
    }

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
        if (!this.canBlockStay(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
		return isMainMineral(worldIn, pos) || (!isMainMineral(worldIn, pos) && hasSurface(worldIn, pos));
	}

	private boolean hasSurface(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP) && !worldIn.getBlockState(pos.down()).getBlock().isAir(worldIn.getBlockState(pos.down()), worldIn, pos);
	}

	private boolean isMainMineral(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos)) == 0;
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        if (!this.canBlockStay(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		EnumOres type = (EnumOres) state.getValue(VARIANT);
    	return type.getMetadata() == 0 ? MIN : CAT;
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, EnumOres.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumOres type = (EnumOres) state.getValue(VARIANT);
		return type.getMetadata();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < EnumOres.values().length; i++){
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public String getSpecialName(ItemStack stack) {
		return ModArray.mineralOresArray[stack.getItemDamage()];
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this) ;
	}

    public int damageDropped(IBlockState state){
    	return getMetaFromState(state);
    }

	public int quantityDropped(Random rand) {
		return 1;
	}

    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

}