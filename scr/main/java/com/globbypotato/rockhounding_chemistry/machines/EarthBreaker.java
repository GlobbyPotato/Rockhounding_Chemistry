package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityEarthBreaker;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EarthBreaker extends Block{
    public static final PropertyEnum POS = PropertyEnum.create("pos", EnumPos.class);

	public EarthBreaker(float hardness, float resistance, String name){
		super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(name));
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 3);
		setCreativeTab(Reference.RockhoundingChemistry);
		setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(POS, EnumPos.DOWN));
    }

    public static enum EnumPos implements IStringSerializable{
        DOWN,
        DOWN_SPIN,
        UP,
        UP_SPIN;
		@Override
		public String getName() {
			return toString().toLowerCase();
		}
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this);
	}

	@Override
    public int damageDropped(IBlockState state){
    	return 0;
    }

	@Override
    public IBlockState getStateFromMeta(int meta){
        return this.getDefaultState().withProperty(POS, EnumPos.values()[meta]);
    }

	@Override
    public int getMetaFromState(IBlockState state){
        return ((EnumPos)state.getValue(POS)).ordinal();
    }

	public static TileEntity getTileEntitySafely(IBlockAccess blockAccess, BlockPos pos) {
		if (blockAccess instanceof ChunkCache) {
			return ((ChunkCache) blockAccess).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK);
		}else{
			return blockAccess.getTileEntity(pos);
		}
    }

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return state.withProperty(POS, checkBedrock(worldIn, pos));
	}

	private EnumPos checkBedrock(IBlockAccess worldIn, BlockPos pos) {
		boolean spin = false;
		TileEntity te = getTileEntitySafely(worldIn, pos);
		if(te != null && te instanceof TileEntityEarthBreaker){
			TileEntityEarthBreaker mech = (TileEntityEarthBreaker) te;
			spin = mech.isSpinning();
		}
		if(worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.BEDROCK && worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() != Blocks.BEDROCK){
			if(spin){
				return EnumPos.UP_SPIN;
			}else{
				return EnumPos.UP;
			}
		}else if(worldIn.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() == Blocks.BEDROCK && worldIn.getBlockState(pos.offset(EnumFacing.UP)).getBlock() != Blocks.BEDROCK){
			if(spin){
				return EnumPos.DOWN_SPIN;
			}else{
				return EnumPos.DOWN;
			}
		}else{
			return EnumPos.DOWN;
		}
	}

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
    	TileEntity te = getTileEntitySafely(worldIn, pos);
		if (te != null && te instanceof TileEntityEarthBreaker){
			TileEntityEarthBreaker breaker = (TileEntityEarthBreaker) te; 
			if(breaker.isSpinning()){
				double d0 = (double)pos.getX() + 0.5D;
	            double d1 = (double)pos.getY() + 0.1D;
	            double d2 = (double)pos.getZ() + 0.5D;
	            if (rand.nextDouble() < 0.1D){
	                worldIn.playSound((double)pos.getX() + 0.5D, (double)pos.getY(), (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_ANVIL_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
	            }
	            worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
			}
		}
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			player.openGui(Rhchemistry.instance, GuiHandler.earthBreakerID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
		if(stack.hasTagCompound()){
        	int charge = stack.getTagCompound().getInteger("Charge");
        	TileEntity te = getTileEntitySafely(worldIn, pos);
    		if (te != null && te instanceof TileEntityEarthBreaker){
    			TileEntityEarthBreaker breaker = (TileEntityEarthBreaker) te; 
    			breaker.chargeCount = charge;
			}
		}
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityEarthBreaker){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityEarthBreaker breaker = ((TileEntityEarthBreaker)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setInteger("Charge", breaker.chargeCount);
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityEarthBreaker();
	}

	@Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] { POS });
    }

}