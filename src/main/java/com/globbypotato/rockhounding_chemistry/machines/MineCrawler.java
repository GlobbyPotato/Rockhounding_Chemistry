package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.EnumSetups;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawler;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class MineCrawler extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.7D, 1.0D);

    public MineCrawler(String name){
        super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setHarvestLevel("pickaxe", 0);
		setHardness(10.F); setResistance(10.0F);	
		setSoundType(SoundType.METAL);
		setCreativeTab(Reference.RockhoundingChemistry);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos){
        return BOUNDBOX;
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDBOX;
    }

    @Nullable
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return Item.getItemFromBlock(ModBlocks.mineCrawler);
    }

    @Override
    public int quantityDropped(Random random){
        return 1;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }
    
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityMineCrawler();
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        return new ItemStack(ModBlocks.mineCrawler);
    }

    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
        this.setDefaultFacing(worldIn, pos, state);
    }

    private void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state){
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
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
		if(stack.hasTagCompound()){
        	int numTier = stack.getTagCompound().getInteger(EnumSetups.TIERS.getName());
        	int numMode = stack.getTagCompound().getInteger(EnumSetups.MODES.getName());
        	int numStep = stack.getTagCompound().getInteger(EnumSetups.STEP.getName());
        	int numUpgrade = stack.getTagCompound().getInteger(EnumSetups.UPGRADE.getName());
        	int numCobble = stack.getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName());
        	int numGlass = stack.getTagCompound().getInteger(EnumSetups.GLASS.getName());
        	int numTorch = stack.getTagCompound().getInteger(EnumSetups.TORCHES.getName());
        	int numRail = stack.getTagCompound().getInteger(EnumSetups.RAILS.getName());
        	boolean canFill = stack.getTagCompound().getBoolean(EnumSetups.FILLER.getName());
        	boolean canAbsorb = stack.getTagCompound().getBoolean(EnumSetups.ABSORBER.getName());
        	boolean canTunnel = stack.getTagCompound().getBoolean(EnumSetups.TUNNELER.getName());
        	boolean canLight = stack.getTagCompound().getBoolean(EnumSetups.LIGHTER.getName());
        	boolean canRail = stack.getTagCompound().getBoolean(EnumSetups.RAILMAKER.getName());
			TileEntityMineCrawler te = (TileEntityMineCrawler) worldIn.getTileEntity(pos);
			if(te != null){
            	te.numTier = numTier;
            	te.numMode = numMode;
            	te.numStep = numStep;
            	te.numUpgrade = numUpgrade;
            	te.canFill = canFill;
            	te.canAbsorb = canAbsorb;
            	te.canTunnel = canTunnel;
            	te.canLight = canLight;
            	te.canRail = canRail;
            	te.numCobble = numCobble;
            	te.numGlass = numGlass;
            	te.numTorch = numTorch;
            	te.numRail = numRail;
			}
		}

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
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] {FACING});
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
        TileEntity tileentity = world.getTileEntity(pos);
        if(tileentity != null && tileentity instanceof TileEntityMineCrawler){
        	if(hasWrench(player, hand)){
    			ItemStack dropCrawler = new ItemStack(ModBlocks.mineCrawler);
    			addNbt(dropCrawler, tileentity);
    			if(!world.isRemote) { dropItemStack(world, dropCrawler, pos); }
				((TileEntityMineCrawler)tileentity).invalidate(); world.setBlockToAir(pos);
			}
        }
    	return false;
    }

	private void addNbt(ItemStack dropCrawler, TileEntity tileentity) {
		dropCrawler.setTagCompound(new NBTTagCompound());
		dropCrawler.getTagCompound().setInteger(EnumSetups.TIERS.getName(), ((TileEntityMineCrawler)tileentity).numTier);
		dropCrawler.getTagCompound().setInteger(EnumSetups.MODES.getName(), ((TileEntityMineCrawler)tileentity).numMode);
		dropCrawler.getTagCompound().setInteger(EnumSetups.STEP.getName(), ((TileEntityMineCrawler)tileentity).numStep);
		dropCrawler.getTagCompound().setInteger(EnumSetups.UPGRADE.getName(), ((TileEntityMineCrawler)tileentity).numUpgrade);
		dropCrawler.getTagCompound().setBoolean(EnumSetups.FILLER.getName(), ((TileEntityMineCrawler)tileentity).canFill);
		dropCrawler.getTagCompound().setBoolean(EnumSetups.ABSORBER.getName(), ((TileEntityMineCrawler)tileentity).canAbsorb);
		dropCrawler.getTagCompound().setBoolean(EnumSetups.TUNNELER.getName(), ((TileEntityMineCrawler)tileentity).canTunnel);
		dropCrawler.getTagCompound().setBoolean(EnumSetups.LIGHTER.getName(), ((TileEntityMineCrawler)tileentity).canLight);
		dropCrawler.getTagCompound().setBoolean(EnumSetups.RAILMAKER.getName(), ((TileEntityMineCrawler)tileentity).canRail);
		dropCrawler.getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), ((TileEntityMineCrawler)tileentity).numCobble);
		dropCrawler.getTagCompound().setInteger(EnumSetups.GLASS.getName(), ((TileEntityMineCrawler)tileentity).numGlass);
		dropCrawler.getTagCompound().setInteger(EnumSetups.TORCHES.getName(), ((TileEntityMineCrawler)tileentity).numTorch);
		dropCrawler.getTagCompound().setInteger(EnumSetups.RAILS.getName(), ((TileEntityMineCrawler)tileentity).numRail);
	}

	private boolean hasWrench(EntityPlayer player, EnumHand hand) {
		return player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() == ModItems.miscItems && player.getHeldItem(hand).getItemDamage() == 12;
	}

	private void dropItemStack(World worldIn, ItemStack itemStack, BlockPos pos) {
		EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
		entityitem.setPosition(pos.getX(), pos.getY() + 0.5D, pos.getZ());
		worldIn.spawnEntityInWorld(entityitem);
	}



    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = this.createStackedBlock(state);
        if(te != null && te instanceof TileEntityMineCrawler){
        	if(((TileEntityMineCrawler)te).numTier > 0){
    			addNbt(itemstack, te);
        	}
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }


}
