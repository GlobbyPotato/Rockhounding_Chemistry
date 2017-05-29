package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.IFluidHandlingTile;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltMaker;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SaltMaker extends Block{

    public static final PropertyInteger STAGE = PropertyInteger.create("stage", 0, 6);
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);
    Random rand = new Random();

	public SaltMaker(float hardness, float resistance, String name) {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(name));
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(Reference.RockhoundingChemistry);
		setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(STAGE, Integer.valueOf(0)));
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDBOX;
    }

    private int getStage(IBlockState state){
        return ((Integer)state.getValue(STAGE)).intValue();
    }

    public IBlockState withStage(int stage){
        return this.getDefaultState().withProperty(STAGE, Integer.valueOf(stage));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntitySaltMaker();
	}

	@Override
    public IBlockState getStateFromMeta(int meta){
        return this.withStage(meta);
    }

	@Override
    public int getMetaFromState(IBlockState state){
        return this.getStage(state);
    }

	@Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] { STAGE });
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (!worldIn.isRemote) {
	    	if(side == EnumFacing.UP){
				if(worldIn.getTileEntity(pos) instanceof IFluidHandlingTile){
					TileEntitySaltMaker saltMaker = (TileEntitySaltMaker)worldIn.getTileEntity(pos);
					if (this.getStage(state) == 0 && heldItem != null && saltMaker.inputTank.getFluidAmount() < Fluid.BUCKET_VOLUME){
						if (heldItem.getItem() instanceof ItemBucket || heldItem.getItem() instanceof UniversalBucket){
							((IFluidHandlingTile)worldIn.getTileEntity(pos)).interactWithBucket(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY, hitZ);
			  		    	worldIn.setBlockState(pos, worldIn.getBlockState(pos).withProperty(STAGE, Integer.valueOf(1)));
			                playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
			                heldItem.stackSize--;
			                if(heldItem.getItem().hasContainerItem(heldItem)){
				                if (!playerIn.inventory.addItemStackToInventory(new ItemStack(heldItem.getItem().getContainerItem()))){
				                	playerIn.dropItem(new ItemStack(heldItem.getItem().getContainerItem()), false);
				                }
			                }
							return true;
						}
					}
				}
			}

    		if(getStage(state) == 6 && heldItem != null && heldItem.getItem() instanceof ItemSpade){
  		    	worldIn.setBlockState(pos, this.withStage(0));
                playerIn.playSound(SoundEvents.BLOCK_GRAVEL_HIT, 0.5F, 1.5F);
    			if(!worldIn.isRemote) {
    				int getSalt = rand.nextInt(ModConfig.saltAmount) + 1; 
    				dropItemStack(worldIn, new ItemStack(ModItems.chemicalItems, getSalt, 7), pos);
    			}
    			if(!playerIn.capabilities.isCreativeMode){
	    			int damageItem = heldItem.getItemDamage() + 1;
	    			heldItem.setItemDamage(damageItem);
	    			if(damageItem >= heldItem.getMaxDamage()){heldItem.stackSize--;}
    			}
  		    }
    	}
    	return false;
    }

	private void dropItemStack(World worldIn, ItemStack itemStack, BlockPos pos) {
		EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
		entityitem.setPosition(pos.getX(), pos.getY() + 0.5D, pos.getZ());
		worldIn.spawnEntityInWorld(entityitem);
	}

}