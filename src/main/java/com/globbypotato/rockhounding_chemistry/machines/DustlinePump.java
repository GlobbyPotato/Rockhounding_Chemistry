package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.pipelines.TEDustlinePump;
import com.globbypotato.rockhounding_core.machines.PipelineBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class DustlinePump extends PipelineBase {

	public DustlinePump(String name) {
		super(Reference.MODID, name);
		setCreativeTab(Reference.RockhoundingChemistry);
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return PLUG_AABB;
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TEDustlinePump();
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return block instanceof DustlineDuct || isOrientedCabinet(worldIn, sidePos, facing.getOpposite()) ? true : false;
    }

	public boolean isOrientedCabinet(IBlockAccess world, BlockPos pos, EnumFacing facing) {
		TileEntity tile = world.getTileEntity(pos);
		if(tile != null){ 
			if( tile instanceof TEElementsCabinetBase || tile instanceof TEMaterialCabinetBase){
				TileEntityInv cabinet = (TileEntityInv)tile;
				if (cabinet.getFacing() == facing || cabinet.getFacing() == isFacingAt(facing, 270) || cabinet.getFacing() == isFacingAt(facing, 90)){
					return true;
				}
			}
		}
		return false;
	}

	public EnumFacing isFacingAt(EnumFacing facing, int side){
		return EnumFacing.fromAngle(facing.getHorizontalAngle() + side);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		TEDustlinePump pump = (TEDustlinePump)world.getTileEntity(pos);
		if(player.isSneaking()){
			if(!world.isRemote){
				if(player.getHeldItemMainhand().isEmpty()){ //turn on/off
					pump.activation = !pump.activation;
				}
			}
		}
        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		return true;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile != null && tile instanceof TEDustlinePump){
			TEDustlinePump pump = (TEDustlinePump)tile;
			if(CoreUtils.hasWrench(playerIn)){
				if(!playerIn.isSneaking()){
					if(!worldIn.isRemote){
						pump.delay++;
					}
				}else{
					if(!worldIn.isRemote){
						if(pump.getDelay() > 0){
							pump.delay--;
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
        if(te != null && te instanceof TEDustlinePump){
  			addNbt(itemstack, te);
        }
        if (!itemstack.isEmpty()){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private static void addNbt(ItemStack itemstack, TileEntity tileentity) {
//		TEDustlinePump tile = ((TEDustlinePump)tileentity);
//		itemstack.setTagCompound(new NBTTagCompound());
//		itemstack.getTagCompound().setBoolean("Upgrade", tile.hasUpgrade());
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
 /*       if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TEDustlinePump){
        	TEDustlinePump te = (TEDustlinePump) worldIn.getTileEntity(pos);
			if(stack.hasTagCompound() && te != null){
				if(stack.getTagCompound().hasKey("Upgrade")){
					boolean upg = stack.getTagCompound().getBoolean("Upgrade");
	            	te.upgrade = upg;
				}
			}
        }*/
    }

}