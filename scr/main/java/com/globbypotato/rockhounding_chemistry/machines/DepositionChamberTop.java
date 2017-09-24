package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DepositionChamberTop extends Block {
	public static final PropertyDirection FACING = BlockHorizontal.FACING;

	public DepositionChamberTop(float hardness, float resistence, String name) {
		super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(name));
		setHarvestLevel("pickaxe", 0);
		setHardness(hardness); setResistance(resistence);	
		setSoundType(SoundType.METAL);
		setCreativeTab(null);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		this.setDefaultFacing(worldIn, pos, state);
	}

	public void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state){
		if (!worldIn.isRemote){
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing());
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return null;
    }

    @Override
    public int damageDropped(IBlockState state){
		return 0;
    }

    @Override
    public int quantityDropped(Random random){
        return 0;
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
	public boolean isFullyOpaque(IBlockState state) {
		return false;
	}

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
    	return false;
    }

    @Override
    public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        if (enumfacing.getAxis() == EnumFacing.Axis.Y){
            enumfacing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        return new ItemStack(ModBlocks.depositionChamber);
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			player.openGui(Rhchemistry.instance, GuiHandler.depositionChamberID, world, pos.getX(), pos.getY() - 1, pos.getZ());
		}
		return true;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		checkDepositionBase(world, world.getBlockState(pos.down()), pos);
		world.scheduleUpdate(pos, this, 1);
	}

	private void checkDepositionBase(World world, IBlockState blockState, BlockPos pos) {
		if(world.getBlockState(pos.down()) == null || !(world.getBlockState(pos.down()).getBlock() instanceof DepositionChamber)){
			world.setBlockToAir(pos);
		}
	}

	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn){
		checkDepositionBase(world, world.getBlockState(pos.down()), pos);
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModBlocks.depositionChamber);
    }

}