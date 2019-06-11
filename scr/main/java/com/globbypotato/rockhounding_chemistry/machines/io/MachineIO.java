package com.globbypotato.rockhounding_chemistry.machines.io;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.machines.BaseRotatingMachine;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.MachinesUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MachineIO extends BaseRotatingMachine {
	public static final PropertyDirection FACING = BlockDirectional.FACING;
	public Random rand = new Random();

	public MachineIO(String name, Material material, String[] array, float hardness, float resistance, SoundType stepSound) {
		super(Reference.MODID, name, material, array);
		setCreativeTab(Reference.RockhoundingChemistry);
		setHardness(hardness); 
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 1);
		setSoundType(stepSound);
	}

	@Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items){
        for (int i = 0; i < this.array.length; ++i){
        	if(!hiddenParts(i)){
        		items.add(new ItemStack(this, 1, i));
        	}
        }
    }

	protected boolean hiddenParts(int meta) {
		return false;
	}

	protected boolean baseParts(int meta) {
		return false;
	}

	@SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

	@Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face){
        return BlockFaceShape.UNDEFINED;
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		checkFullBlocks(world, pos, state);
		world.scheduleUpdate(pos, this, 1);
	}

	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		checkFullBlocks(world, pos, state);
	}

	protected void checkFullBlocks(World world, BlockPos pos, IBlockState state) {}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        TileEntity te = world.getTileEntity(pos);
    	int facingIndex = placer.getHorizontalFacing().ordinal();
        if(te != null){
        	MachinesUtils.restoreMachineNBT(stack, te, facingIndex);
        }
    }

	public static boolean miscBlocksA(Block block, IBlockState state, int meta) {
		return block != null && block == ModBlocks.MISC_BLOCKS_A && block.getMetaFromState(state) == meta;
	}

	public boolean hasNullifier(EntityPlayer player, EnumHand hand) {
		if(!player.getHeldItem(hand).isEmpty()){
			if(player.getHeldItem(hand).isItemEqual(BaseRecipes.tile_nullifier)){
				return true;
			}
		}
		return false;
	}

	public void handleNullifier(World world, BlockPos pos, EntityPlayer player, EnumHand hand, Block block, int meta) {
		if(baseParts(meta)){
			spawnTheBase(world, pos, block);
			cleanTheTop(world, pos.up());
		}else if(hiddenParts(meta)){
			spawnTheBase(world, pos.down(), block);
			cleanTheTop(world, pos);
		}else{
			spawnTheBase(world, pos, block);
		}
	}

	public void cleanTheTop(World world, BlockPos pos) {
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
	}

	public void spawnTheBase(World world, BlockPos pos, Block block) {
		IBlockState state = world.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);
		ItemStack stack = new ItemStack(block, 1, meta);
		EntityItem entity = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), stack);
		world.spawnEntity(entity);
		world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
	}

	public void handleRotation(World world, BlockPos pos, EntityPlayer player, int meta) {
		rotateTheBlock(world, pos);
		if(baseParts(meta)){
			rotateTheBlock(world, pos.up());
		}else if(hiddenParts(meta)){
			rotateTheBlock(world, pos.down());
		}
	}

	public void rotateTheBlock(World world, BlockPos pos) {
		TileEntity tile = world.getTileEntity(pos);
		if(world.getBlockState(pos) != null && world.getBlockState(pos).getBlock() instanceof MachineIO && tile != null && tile instanceof TileEntityInv){
			TileEntityInv machine = (TileEntityInv)tile;
			switch(machine.facing){//2 4 5 3
				case 2: machine.facing = 5;break;
				case 5: machine.facing = 3;break;
				case 3: machine.facing = 4;break;
				case 4: machine.facing = 2;break;
				default: machine.facing = 2;
			}
			machine.markDirtyClient();
		}
	}

}