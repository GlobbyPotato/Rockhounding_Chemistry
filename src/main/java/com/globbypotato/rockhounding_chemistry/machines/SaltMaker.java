package com.globbypotato.rockhounding_chemistry.machines;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.IMetaBlockName;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntitySaltMaker;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SaltMaker extends Block implements ITileEntityProvider, IMetaBlockName{

	public static final PropertyEnum VARIANT = PropertyEnum.create("type", SaltMaker.EnumType.class);
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.3D, 1.0D);
    Random rand = new Random();
    public static int saltAmount;
    
	public SaltMaker(float hardness, float resistance, String name) {
		super(Material.ROCK);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(Reference.RockhoundingChemistry);
		setSoundType(SoundType.STONE);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.byMetadata(0)));
		this.useNeighborBrightness = true;
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
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(VARIANT, SaltMaker.EnumType.byMetadata(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		EnumType type = (EnumType) state.getValue(VARIANT);
		return type.getMetadata();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, 0));
	}

    @Nullable
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, World worldIn, BlockPos pos){
        return BOUNDBOX;
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDBOX;
    }

	@Override
	public String getSpecialName(ItemStack stack) {
		return ModArray.saltMakerArray[stack.getItemDamage()];
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this) ;
	}

    public int damageDropped(IBlockState state){
    	return 0;
    }

	@Override
	public int quantityDropped(Random rand) {
		return 1;
	}

	@Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
    	return false;
    }

	public enum EnumType implements IStringSerializable {
		EMPTY	(0,  "empty"),
		WATER	(1,  "water"),
		STEPA	(2,  "stepa"),
		STEPB	(3,  "stepb"),
		STEPC	(4,  "stepc"),
		STEPD	(5,  "stepd"),
		SALT	(6,  "salt");
        private static final SaltMaker.EnumType[] META_LOOKUP = new SaltMaker.EnumType[values().length];
		private int meta;
		private final String name;

		private EnumType(int meta, String name) {
			this.meta = meta;
			this.name = name;
		}

		@Override
		public String getName() {
			return this.name;
		}

        public int getMetadata() {
            return this.meta;
        }

		@Override
		public String toString() {
			return this.getName();
		}

        public static SaltMaker.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) { meta = 0; }
            return META_LOOKUP[meta];
        }

        static {
        	SaltMaker.EnumType[] metas = values();
            int metaLenght = metas.length;
            for (int x = 0; x < metaLenght; ++x) {
            	SaltMaker.EnumType metaIn = metas[x];
                META_LOOKUP[metaIn.getMetadata()] = metaIn;
            }
        }

	}

    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.TRANSLUCENT;
    }

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntitySaltMaker();
	}

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
    	if(side == EnumFacing.UP){
    		EnumType type = (EnumType) state.getValue(VARIANT);
    		if(type.getMetadata() == 0 && heldItem != null && heldItem.getItem() == Items.WATER_BUCKET){
                heldItem.stackSize--;
                playerIn.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
                if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET))){
                	playerIn.dropItem(new ItemStack(Items.BUCKET), false);
                }
  		    	state = this.getStateFromMeta(1);
  		    	worldIn.setBlockState(pos, state);
  		    }else if(type.getMetadata() == 1 && heldItem != null && heldItem.getItem() == Items.BUCKET){
                heldItem.stackSize--;
                playerIn.playSound(SoundEvents.ITEM_BUCKET_EMPTY, 1.0F, 1.0F);
                if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))){
                	playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                }
  		    	state = this.getStateFromMeta(0);
  		    	worldIn.setBlockState(pos, state);
  		    }else if(type.getMetadata() == 6 && heldItem != null && heldItem.getItem() instanceof ItemSpade){
  		    	state = this.getStateFromMeta(0);
  		    	worldIn.setBlockState(pos, state);
                playerIn.playSound(SoundEvents.BLOCK_GRAVEL_HIT, 0.5F, 1.5F);
    			if(!worldIn.isRemote) {
    				int getSalt = rand.nextInt(saltAmount) + 1; 
    				dropItemStack(worldIn, new ItemStack(ModItems.chemicalItems, getSalt, 1), pos);
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