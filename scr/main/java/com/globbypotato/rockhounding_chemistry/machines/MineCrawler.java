package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.CrawlerIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumCrawler;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineCrawler;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class MineCrawler extends Block {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.7D, 1.0D);

    public MineCrawler(String name){
        super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new CrawlerIB(this).setRegistryName(name));
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

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
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
        	int numTier = stack.getTagCompound().getInteger(EnumCrawler.TIERS.getName());
        	int numMode = stack.getTagCompound().getInteger(EnumCrawler.MODES.getName());
        	int numStep = stack.getTagCompound().getInteger(EnumCrawler.STEP.getName());
        	int numUpgrade = stack.getTagCompound().getInteger(EnumCrawler.UPGRADE.getName());
        	
        	String fillBlockName = stack.getTagCompound().getString(EnumCrawler.FILLER_BLOCK.getBlockName());
        	int fillBlockMeta = stack.getTagCompound().getInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta());
        	int fillBlockSize = stack.getTagCompound().getInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize());
        	
        	String absorbBlockName = stack.getTagCompound().getString(EnumCrawler.ABSORBER_BLOCK.getBlockName());
        	int absorbBlockMeta = stack.getTagCompound().getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta());
        	int absorbBlockSize = stack.getTagCompound().getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());

        	String lighterBlockName = stack.getTagCompound().getString(EnumCrawler.LIGHTER_BLOCK.getBlockName());
        	int lighterBlockMeta = stack.getTagCompound().getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta());
        	int lighterBlockSize = stack.getTagCompound().getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());

        	String railBlockName = stack.getTagCompound().getString(EnumCrawler.RAILMAKER_BLOCK.getBlockName());
        	int railBlockMeta = stack.getTagCompound().getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta());
        	int railBlockSize = stack.getTagCompound().getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());

        	String decoBlockName = stack.getTagCompound().getString(EnumCrawler.DECORATOR_BLOCK.getBlockName());
        	int decoBlockMeta = stack.getTagCompound().getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta());
        	int decoBlockSize = stack.getTagCompound().getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());

        	boolean canFill = stack.getTagCompound().getBoolean(EnumCrawler.FILLER.getName());
        	boolean canAbsorb = stack.getTagCompound().getBoolean(EnumCrawler.ABSORBER.getName());
        	boolean canTunnel = stack.getTagCompound().getBoolean(EnumCrawler.TUNNELER.getName());
        	boolean canLight = stack.getTagCompound().getBoolean(EnumCrawler.LIGHTER.getName());
        	boolean canRail = stack.getTagCompound().getBoolean(EnumCrawler.RAILMAKER.getName());
        	boolean canDeco = stack.getTagCompound().getBoolean(EnumCrawler.DECORATOR.getName());
        	boolean canPath = stack.getTagCompound().getBoolean(EnumCrawler.PATHFINDER.getName());
        	boolean canStore = stack.getTagCompound().getBoolean(EnumCrawler.STORAGE.getName());

        	TileEntityMineCrawler te = (TileEntityMineCrawler) worldIn.getTileEntity(pos);
			if(te != null){
            	te.activator = false;
            	te.numTier = numTier;
            	te.numMode = numMode;
            	te.numStep = numStep;
            	te.numUpgrade = numUpgrade;
            	te.canFill = canFill;
            	te.canAbsorb = canAbsorb;
            	te.canTunnel = canTunnel;
            	te.canLight = canLight;
            	te.canRail = canRail;
            	te.canDeco = canDeco;
            	te.canPath = canPath;
            	te.canStore = canStore;
            	te.fillBlockName = fillBlockName;
            	te.fillBlockMeta = fillBlockMeta;
            	te.fillBlockSize = fillBlockSize;
            	te.absorbBlockName = absorbBlockName;
            	te.absorbBlockMeta = absorbBlockMeta;
            	te.absorbBlockSize = absorbBlockSize;
            	te.lighterBlockName = lighterBlockName;
            	te.lighterBlockMeta = lighterBlockMeta;
            	te.lighterBlockSize = lighterBlockSize;
            	te.railBlockName = railBlockName;
            	te.railBlockMeta = railBlockMeta;
            	te.railBlockSize = railBlockSize;
            	te.decoBlockName = decoBlockName;
            	te.decoBlockMeta = decoBlockMeta;
            	te.decoBlockSize = decoBlockSize;
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
        TileEntityMineCrawler crawler = (TileEntityMineCrawler) world.getTileEntity(pos);
        if(!world.isRemote){
	        if(crawler != null){
	        	if(ToolUtils.hasWrench(player, hand)){
	    			if(!crawler.activator && side == EnumFacing.UP){
		    			ItemStack dropCrawler = new ItemStack(ModBlocks.mineCrawler);
		    			storeNbt(dropCrawler, crawler);
		    			if(!world.isRemote) { dropItemStack(world, dropCrawler, pos); }
		    			crawler.invalidate(); world.setBlockToAir(pos);
	    			}else if(side.getIndex() > 1){
	        			world.setBlockState(pos, state.withProperty(FACING, EnumFacing.getHorizontal(side.getHorizontalIndex())));
	        		}
				}else if(player.getHeldItem(hand) == null){
					crawler.activator = !crawler.activator;
					crawler.markDirty();
    				String signal = TextFormatting.GRAY + "Activation: " + crawler.activator;
    				player.addChatComponentMessage(new TextComponentString(signal));
				}
	        }
        }
        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
    	return true;
    }

	private void storeNbt(ItemStack dropCrawler, TileEntity tileentity) {
		dropCrawler.setTagCompound(new NBTTagCompound());
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.ACTIVATOR.getName(), false);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.TIERS.getName(), ((TileEntityMineCrawler)tileentity).numTier);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.MODES.getName(), ((TileEntityMineCrawler)tileentity).numMode);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.STEP.getName(), ((TileEntityMineCrawler)tileentity).numStep);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.UPGRADE.getName(), ((TileEntityMineCrawler)tileentity).numUpgrade);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.FILLER.getName(), ((TileEntityMineCrawler)tileentity).canFill);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.ABSORBER.getName(), ((TileEntityMineCrawler)tileentity).canAbsorb);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.TUNNELER.getName(), ((TileEntityMineCrawler)tileentity).canTunnel);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.LIGHTER.getName(), ((TileEntityMineCrawler)tileentity).canLight);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.RAILMAKER.getName(), ((TileEntityMineCrawler)tileentity).canRail);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.DECORATOR.getName(), ((TileEntityMineCrawler)tileentity).canDeco);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.PATHFINDER.getName(), ((TileEntityMineCrawler)tileentity).canPath);
		dropCrawler.getTagCompound().setBoolean(EnumCrawler.STORAGE.getName(), ((TileEntityMineCrawler)tileentity).canStore);
		dropCrawler.getTagCompound().setString(EnumCrawler.FILLER_BLOCK.getBlockName(), ((TileEntityMineCrawler)tileentity).fillBlockName);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta(), ((TileEntityMineCrawler)tileentity).fillBlockMeta);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize(), ((TileEntityMineCrawler)tileentity).fillBlockSize);
		dropCrawler.getTagCompound().setString(EnumCrawler.ABSORBER_BLOCK.getBlockName(), ((TileEntityMineCrawler)tileentity).absorbBlockName);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), ((TileEntityMineCrawler)tileentity).absorbBlockMeta);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize(), ((TileEntityMineCrawler)tileentity).absorbBlockSize);
		dropCrawler.getTagCompound().setString(EnumCrawler.LIGHTER_BLOCK.getBlockName(), ((TileEntityMineCrawler)tileentity).lighterBlockName);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), ((TileEntityMineCrawler)tileentity).lighterBlockMeta);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize(), ((TileEntityMineCrawler)tileentity).lighterBlockSize);
		dropCrawler.getTagCompound().setString(EnumCrawler.RAILMAKER_BLOCK.getBlockName(), ((TileEntityMineCrawler)tileentity).railBlockName);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), ((TileEntityMineCrawler)tileentity).railBlockMeta);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize(), ((TileEntityMineCrawler)tileentity).railBlockSize);
		dropCrawler.getTagCompound().setString(EnumCrawler.DECORATOR_BLOCK.getBlockName(), ((TileEntityMineCrawler)tileentity).decoBlockName);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), ((TileEntityMineCrawler)tileentity).decoBlockMeta);
		dropCrawler.getTagCompound().setInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize(), ((TileEntityMineCrawler)tileentity).decoBlockSize);
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
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityMineCrawler){
        	if(((TileEntityMineCrawler)te).numTier > 0){
        		storeNbt(itemstack, te);
        	}
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

}