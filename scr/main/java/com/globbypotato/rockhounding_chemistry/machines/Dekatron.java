package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.BaseTileBlock;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDekatron;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Dekatron extends BaseTileBlock {
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 1);

    private static final AxisAlignedBB BOUNDBOX_Z = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
    private static final AxisAlignedBB BOUNDBOX_X = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.00D, 1.0D, 0.75D);

    public Dekatron(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityDekatron.class,GuiHandler.dekatronID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, Integer.valueOf(0)));
    }

	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos){
        return state.getValue(FACING).getAxis() == Axis.X ? BOUNDBOX_X : BOUNDBOX_Z;
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return state.getValue(FACING).getAxis() == Axis.X ? BOUNDBOX_X : BOUNDBOX_Z;
    }
	
	public void setDefaultFacing(World worldIn, BlockPos pos, IBlockState state){
		if (!worldIn.isRemote){
			EnumFacing enumfacing = (EnumFacing)state.getValue(FACING);
			worldIn.setBlockState(pos, state.withProperty(FACING, enumfacing).withProperty(LEVEL, Integer.valueOf(0)), 2);
		}
	}

    @Override
    public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer){
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(LEVEL, Integer.valueOf(0));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()).withProperty(LEVEL, Integer.valueOf(0)), 2);
    }

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		this.setDefaultFacing(worldIn, pos, state);
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
    public EnumBlockRenderType getRenderType(IBlockState state){
        return EnumBlockRenderType.MODEL;
    }

    protected int getStage(IBlockState state){
        return ((Integer)state.getValue(LEVEL)).intValue();
    }

    public IBlockState withStage(int stage){
        return this.getDefaultState().withProperty(LEVEL, Integer.valueOf(stage));
    }


    public BlockStateContainer createBlockState(){
        return new BlockStateContainer(this, new IProperty[] { FACING, LEVEL });
    }

    @Override
    public boolean canProvidePower(IBlockState state){
        return getStage(state) == 1;
    }

	@Override
	public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return true;
    }

	@Override
    public int getWeakPower(IBlockState state, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return getStage(state) == 1 && side == state.getValue(FACING).getOpposite() ? 1 : 0;
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
    	if(getStage(state) == 0){
    		TileEntityDekatron deka = (TileEntityDekatron) world.getTileEntity(pos);
	        if(deka != null){
	        	if(ToolUtils.hasWrench(player, hand)){
	            	if(!world.isRemote){
	            		if(side != EnumFacing.DOWN && side != EnumFacing.UP && deka.selectMode < deka.maxModes){
	            			deka.selectMode++;
	            		}else{
	            			deka.selectMode = 1;
            			}
            			deka.markDirty();
            			String counter = "";
            			if(deka.selectMode == 1){
    	            		counter = "Current Mode: Single State";
            			}else if(deka.selectMode == 2){
    	            		counter = "Current Mode: Bi-State";
            			}else if(deka.selectMode == 3){
    	            		counter = "Current Mode: Single State Cycle";
            			}else if(deka.selectMode == 4){
    	            		counter = "Current Mode: Bi-State Cycle";
            			}else if(deka.selectMode == 5){
    	            		counter = "Current Mode: Pulse Timer";
            			}else if(deka.selectMode == 6){
    	            		counter = "Current Mode: Steady Timer";
            			}
						player.addChatComponentMessage(new TextComponentString(counter));
	            	}
			        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
	        	}
	        }
    	}
    	return false;
    }

}