package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserSplitter;

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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LaserSplitter extends Block implements ITileEntityProvider {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    
    public LaserSplitter(float hardness, float resistance, String name){
        super(Material.IRON);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setHarvestLevel("pickaxe", 0);
		setHardness(hardness); setResistance(resistance);	
		setSoundType(SoundType.METAL);
		setCreativeTab(Reference.RockhoundingChemistry);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
        return Item.getItemFromBlock(this);
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
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos){
        return super.canPlaceBlockAt(worldIn, pos) && this.canBlockStay(worldIn, pos);
    }

	@Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn){
        if (!this.canBlockStay(worldIn, pos)){
            worldIn.destroyBlock(pos, true);
        }
    }

    private boolean canBlockStay(World worldIn, BlockPos pos) {
		return worldIn.getBlockState(pos.down()).isSideSolid(worldIn, pos, EnumFacing.UP);
	}

    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new TileEntityLaserSplitter();
    }

    @Override
    public boolean isFullCube(IBlockState state) {
    	return false;
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        return new ItemStack(this);
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
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return true;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
    	TileEntityLaserSplitter splitter = (TileEntityLaserSplitter) world.getTileEntity(pos);
        if (splitter != null){
        	if(hasWrench(player, hand)){
            	if(!world.isRemote){
            		if(side != world.getBlockState(pos).getValue(FACING).DOWN && side != world.getBlockState(pos).getValue(FACING).UP){
		        		if(splitter.splitSide < 4){
		        			splitter.splitSide++;
		        			splitter.markDirty();
		        		}else if(side != world.getBlockState(pos).getValue(FACING).UP){
		        			splitter.splitSide = 0;
		        			splitter.markDirty();
		        		}
            		}else if(side != world.getBlockState(pos).getValue(FACING).DOWN  && (hitX > 0.30F && hitX < 0.65F) && (hitZ > 0.30F && hitZ < 0.65F)     ){
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
        return false;
    }
    
	private boolean hasWrench(EntityPlayer player, EnumHand hand) {
		return player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() == ModItems.miscItems && player.getHeldItem(hand).getItemDamage() == 15;
	}

}