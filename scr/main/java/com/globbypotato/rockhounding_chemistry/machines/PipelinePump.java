package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelinePump;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityPipelineValve;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.Block;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class PipelinePump extends PipelineBase {

	public PipelinePump(float hardness, float resistance, String name) {
		super(hardness, resistance, name);
		setCreativeTab(Reference.RockhoundingChemistry);
	}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return PLUG_AABB;
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityPipelinePump();
	}

	@Override
    public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, BlockPos sidePos, EnumFacing facing){
        IBlockState state = worldIn.getBlockState(sidePos);
        Block block = state.getBlock();
        return hasFluidCapability(state, block, worldIn, sidePos) 
        	|| (block instanceof PipelineDuct 
        		|| (block instanceof PipelineValve) && isSideEnabled(worldIn, sidePos, facing.getOpposite())) ? true : false;
    }

	private boolean isSideEnabled(IBlockAccess worldIn, BlockPos pos, EnumFacing facing) {
		TileEntityPipelineValve valve = (TileEntityPipelineValve)worldIn.getTileEntity(pos);
		return valve.sideStatus[facing.ordinal()];
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		TileEntityPipelinePump pump = (TileEntityPipelinePump)world.getTileEntity(pos);
		if(player.isSneaking()){
			if(!world.isRemote){
				if(player.getHeldItemMainhand() == null){
					pump.activation = !pump.activation;
				}
			}
	        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
		}

		if(pump.hasUpgrade()){
			if(ToolUtils.hasWrench(player, hand)){
				EntityItem upgrade = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), pipelineUpgrade());
				upgrade.setPosition(pos.getX(), pos.getY() + 0.5D, pos.getZ());
    			if(!world.isRemote) {
    				world.spawnEntityInWorld(upgrade);
					pump.upgrade = false;
					pump.markDirtyClient();
    			}
                player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF, 0.4F, 2.0F);
			}
		}else{
			if(player.getHeldItemMainhand() != null && player.getHeldItemMainhand().isItemEqual(pipelineUpgrade())){
    			if(!world.isRemote) {
					pump.upgrade = true;
					pump.markDirtyClient();
					if(!player.capabilities.isCreativeMode){
						player.getHeldItemMainhand().stackSize--;
					}
    			}
                player.playSound(SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, 0.4F, 2.0F);
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		TileEntity tile = worldIn.getTileEntity(pos);
		if(tile != null && tile instanceof TileEntityPipelinePump){
			TileEntityPipelinePump pump = (TileEntityPipelinePump)tile;
			if(ToolUtils.hasWrench(playerIn, EnumHand.MAIN_HAND)){
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

    private ItemStack pipelineUpgrade() {
		return new ItemStack(ModItems.miscItems, 1, 65);
	}

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityPipelinePump){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityPipelinePump tile = ((TileEntityPipelinePump)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setBoolean("Upgrade", tile.hasUpgrade());
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        if(worldIn.getTileEntity(pos) != null && worldIn.getTileEntity(pos) instanceof TileEntityPipelinePump){
        	TileEntityPipelinePump te = (TileEntityPipelinePump) worldIn.getTileEntity(pos);
			if(stack.hasTagCompound() && te != null){
				if(stack.getTagCompound().hasKey("Upgrade")){
					boolean upg = stack.getTagCompound().getBoolean("Upgrade");
	            	te.upgrade = upg;
				}
			}
        }
    }
}