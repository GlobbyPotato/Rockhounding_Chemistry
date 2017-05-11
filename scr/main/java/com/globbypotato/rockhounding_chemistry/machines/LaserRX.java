package com.globbypotato.rockhounding_chemistry.machines;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.MetaIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumLaser;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserRX;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class LaserRX extends Block{
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumLaser.class);
	public String[] array;

	public LaserRX(float hardness, float resistance, String name, String[] array) {
		super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
		GameRegistry.register(new MetaIB(this, EnumLaser.getNames()).setRegistryName(name));
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
		setCreativeTab(Reference.RockhoundingChemistry);
		setSoundType(SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumLaser.values()[0]));
		this.array = array;
	}

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
		int metadata = worldIn.getBlockState(pos).getBlock().getMetaFromState(worldIn.getBlockState(pos));
		if( (isPlaceableVariant(metadata) && !shouldPlace(worldIn, pos)) || (isHangingVariant(metadata) && !shouldHang(worldIn, pos)) ){
			worldIn.destroyBlock(pos, true);
		}
	}

	@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		if(isHangingVariant(stack.getItemDamage()) && shouldPlace(worldIn, pos)){
			worldIn.setBlockState(pos, ModBlocks.laserRedstoneRx.getDefaultState().withProperty(VARIANT, EnumLaser.values()[stack.getItemDamage() - 2]));
		}else if(isPlaceableVariant(stack.getItemDamage()) && shouldHang(worldIn, pos)){
			worldIn.setBlockState(pos, ModBlocks.laserRedstoneRx.getDefaultState().withProperty(VARIANT, EnumLaser.values()[stack.getItemDamage() + 2]));
		}else{
			if(!canBlockStay(worldIn, state, pos)){
				if(!worldIn.isRemote) { 
					ItemStack dropStack = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
					spawnAsEntity(worldIn, pos, dropStack);
					worldIn.setBlockToAir(pos);
				}
			}
		}
    }

	private boolean isPlaceableVariant(int meta) {
		return meta == 0 || meta == 1 || meta == 4 || meta == 5; 
	}

	private boolean isHangingVariant(int meta) {
		return meta == 2 || meta == 3 || meta == 6 || meta == 7;
	}

	private boolean shouldPlace(World worldIn, BlockPos pos){
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
	}

	private boolean shouldHang(World worldIn, BlockPos pos){
		return worldIn.getBlockState(pos.up()).isSideSolid(worldIn, pos, EnumFacing.DOWN);
	}

	private boolean canBlockStay(World worldIn, IBlockState state, BlockPos pos) {
    	return isSuspendedPart(worldIn, pos) ? shouldHang(worldIn, pos) : shouldPlace(worldIn, pos);
	}

	private boolean isSuspendedPart(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos).getValue(VARIANT) == EnumLaser.OFFPROBE || worldIn.getBlockState(pos).getValue(VARIANT) == EnumLaser.ONPROBE || worldIn.getBlockState(pos).getValue(VARIANT) == EnumLaser.PROBETX || worldIn.getBlockState(pos).getValue(VARIANT) == EnumLaser.PROBERX;
	}

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		list.add(new ItemStack(itemIn, 1, 0));
		list.add(new ItemStack(itemIn, 1, 4));
		list.add(new ItemStack(itemIn, 1, 5));
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
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumLaser.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumLaser)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return Item.getItemFromBlock(this) ;
	}

    public int damageDropped(IBlockState state){
    	if(state.getValue(VARIANT) == EnumLaser.OFFPROBE
		|| state.getValue(VARIANT) == EnumLaser.ONPROBE
		|| state.getValue(VARIANT) == EnumLaser.OFFPIN 
   		|| state.getValue(VARIANT) == EnumLaser.ONPIN){
    		return 0;
    	}else if(state.getValue(VARIANT) == EnumLaser.PINRX
    		  || state.getValue(VARIANT) == EnumLaser.PROBERX){
    		return 5;
    	}else if(state.getValue(VARIANT) == EnumLaser.PINTX
      		  || state.getValue(VARIANT) == EnumLaser.PROBETX){
      		return 4;
    	}
		return 0;
    }

	public int quantityDropped(Random rand) {
		return 1;
	}

    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

    private boolean poweredVariants(IBlockState state){
    	return state.getBlock().getMetaFromState(state) == 1 || state.getBlock().getMetaFromState(state) == 3;
    }

    @Override
    public boolean canProvidePower(IBlockState state){
        return poweredVariants(state);
    }

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return state.getBlock().getMetaFromState(state) <= 3;
    }

	@Override
    public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return poweredVariants(state) ? 1 : 0;
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityLaserRX();
	}

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
    	if(state.getBlock().getMetaFromState(state) == 5 || state.getBlock().getMetaFromState(state) == 7){
	    	TileEntityLaserRX splitter = (TileEntityLaserRX) world.getTileEntity(pos);
	        if(splitter != null){
	        	if(ToolUtils.hasWrench(player, hand)){
	            	if(!world.isRemote){
	            		if(side != EnumFacing.DOWN && side != EnumFacing.UP){
			        		if(splitter.splitSide < 4){
			        			splitter.splitSide++;
			        			splitter.markDirty();
			        		}else if(side != EnumFacing.UP){
			        			splitter.splitSide = 0;
			        			splitter.markDirty();
			        		}
	            		}else if((state.getBlock().getMetaFromState(state) == 5 && side == EnumFacing.UP  && (hitX > 0.30F && hitX < 0.65F) && (hitZ > 0.30F && hitZ < 0.65F))
              			  	  || (state.getBlock().getMetaFromState(state) == 7 && side == EnumFacing.DOWN  && (hitX > 0.30F && hitX < 0.65F) && (hitZ > 0.30F && hitZ < 0.65F)) ){
	        				splitter.isPulsing = !splitter.isPulsing;
		        		}
	            		String mode = ""; String signal = "";
	        			if(splitter.splitSide >= 0 && splitter.splitSide < 4){
	        				mode = TextFormatting.GRAY + "Node: " + TextFormatting.WHITE + "Bender";
	        			}else if(splitter.splitSide == 4){
	        				if(splitter.isPulsing){
	            				mode = TextFormatting.GRAY + "Node: " + TextFormatting.WHITE + "Sequencer";
	        				}else{
	            				mode = TextFormatting.GRAY + "Node: " + TextFormatting.WHITE + "Splitter";
	        				}
	        			}
	    				if(splitter.isPulsing){
	        				signal = TextFormatting.GRAY + "Signal: " + TextFormatting.WHITE + "Pulse";
	    				}else{
	        				signal = TextFormatting.GRAY + "Signal: " + TextFormatting.WHITE + "Steady";
	    				}
						player.addChatComponentMessage(new TextComponentString(mode + " / " + signal));
		        	}
		        }
	    	}
    	}
        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        return false;
    }
}