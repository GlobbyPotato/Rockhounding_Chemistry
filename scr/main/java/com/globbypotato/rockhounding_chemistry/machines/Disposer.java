package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDisposer;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockPistonBase;
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
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class Disposer extends Block {
    public static PropertyDirection FACING = BlockDirectional.FACING;

    public Disposer(float hardness, float resistance, String name, SoundType metal){
        super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new ItemBlock(this).setRegistryName(name));
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(Reference.RockhoundingChemistry);
		setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
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
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this);
	}

	@Override
    public int damageDropped(IBlockState state){
    	return 0;
    }

	public void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state){
		if (!worldIn.isRemote){
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing), 2);
		}
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		this.setDefaultFacing(worldIn, pos, state);
	}

    @Override
    public IBlockState getStateFromMeta(int meta){
        EnumFacing enumfacing = EnumFacing.getFront(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot){
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer));
    }

	@Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] { FACING });
    }

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return true;
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityDisposer();
	}

	@Override
	public boolean isSideSolid(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side) {
		return state.getValue(FACING) == side.getOpposite();
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			player.openGui(Rhchemistry.instance, GuiHandler.disposerID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, BlockPistonBase.getFacingFromEntity(pos, placer)), 2);
        if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityDisposer){
        	TileEntityDisposer disposer = (TileEntityDisposer) worldIn.getTileEntity(pos);
			if(stack.hasTagCompound() && disposer != null){
        		if(stack.getTagCompound().hasKey("Lock")){
        			disposer.lock = stack.getTagCompound().getBoolean("Locked");
        			disposer.interval = stack.getTagCompound().getInteger("Interval");
			        NBTTagList nbttaglist = stack.getTagCompound().getTagList("Lock", 10);
        			disposer.lockList = new ArrayList<ItemStack>();
        			disposer.resetLock();
			        for (int i = 0; i < nbttaglist.tagCount(); ++i){
			            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
			            int j = nbttagcompound.getByte("Slot");
			            if (j >= 0 && j < disposer.lockList.size()){
			            	disposer.lockList.add(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
			            }
			        }
        		}
			}
        }

    }
 
    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityDisposer){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityDisposer disposer = ((TileEntityDisposer)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
        NBTTagList nbttaglist = new NBTTagList();
        itemstack.getTagCompound().setBoolean("Locked", disposer.isLocked());
        itemstack.getTagCompound().setInteger("Interval", disposer.getInterval());
        for (int i = 0; i < disposer.lockList.size(); ++i){
            if (disposer.lockList.get(i) != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                disposer.lockList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        itemstack.getTagCompound().setTag("Lock", nbttaglist);

	}

}