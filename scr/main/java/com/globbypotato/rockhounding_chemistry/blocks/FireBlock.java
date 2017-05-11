package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.MetaIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumFires;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FireBlock extends BaseMetaBlock{
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumFires.class);
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.3D, 0.0D, 0.3D, 0.7D, 0.3D, 0.7D);
    Random rand = new Random();

	public FireBlock(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound) {
        super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(this);
		GameRegistry.register(new MetaIB(this, EnumFires.getNames()).setRegistryName(name));
		setLightLevel(1F);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumFires.values()[0]));
        disableStats();
        this.setTickRandomly(true);
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		worldIn.extinguishFire(playerIn, pos, EnumFacing.UP);
	}

	@Override
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		if(entityIn.isImmuneToFire()){
			entityIn.setFire(20);
			entityIn.isBurning();
		}
	}

	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
        if((isExposed(worldIn, pos) && !isEndless(worldIn, pos)) 
        || !canStay(worldIn, pos)
        || (!isEndless(worldIn, pos) && randomCheck())
        ){
            worldIn.setBlockToAir(pos);
        }
        worldIn.scheduleUpdate(pos, this, 1);
    }

	private boolean randomCheck() {
		return rand.nextInt(8) == 0;
	}

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
        if (!canStay(worldIn, pos)){
            worldIn.setBlockToAir(pos);
        }
    }

	private boolean canStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos.down(), EnumFacing.UP);
	}

	private boolean isEndless(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()) == Blocks.NETHERRACK.getDefaultState();
	}

	private boolean isExposed(World worldIn, BlockPos pos) {
		return (worldIn.isRaining() && worldIn.canSeeSky(pos));
	}

	@Override
	public boolean isBurning(IBlockAccess world, BlockPos pos) {
		return true;
	}

	@Override
    public boolean isCollidable(){
        return false;
    }

	@Override
    public MapColor getMapColor(IBlockState state){
        return EnumFires.getMapColor(getMetaFromState(state));
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
        if (rand.nextInt(24) == 0){
            worldIn.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + rand.nextFloat(), rand.nextFloat() * 0.7F + 0.3F, false);
        }
        for (int i = 0; i < 3; ++i){
            double d0 = (double)pos.getX() + rand.nextDouble();
            double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double d2 = (double)pos.getZ() + rand.nextDouble();
            worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
        }
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDBOX;
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return null;
	}

	@Override
	public int quantityDropped(Random rand) {
		return 0;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return false;
	}

    @Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos){
        return NULL_AABB;
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumFires.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFires)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

}