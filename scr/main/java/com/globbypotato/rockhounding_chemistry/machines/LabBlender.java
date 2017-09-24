package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabBlender;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LabBlender extends BaseMachine{
    public LabBlender(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityLabBlender.class,GuiHandler.labBlenderID);
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    	TileEntityLabBlender labBlender = (TileEntityLabBlender) worldIn.getTileEntity(pos);
    	if(stack.hasTagCompound() && labBlender != null){
			labBlender.lock = stack.getTagCompound().getBoolean("Lock");
    		if(stack.getTagCompound().hasKey("Locked")){
		        NBTTagList nbttaglist = stack.getTagCompound().getTagList("Locked", 10);
		        labBlender.lockList = new ArrayList<ItemStack>();
		        labBlender.resetLock();
		        for (int i = 0; i < nbttaglist.tagCount(); ++i){
		            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
		            int j = nbttagcompound.getByte("Slot");
		            if (j >= 0 && j < labBlender.lockList.size()){
		            	labBlender.lockList.add(j, ItemStack.loadItemStackFromNBT(nbttagcompound));
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
        if(te != null && te instanceof TileEntityLabBlender){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityLabBlender labBlender = ((TileEntityLabBlender)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
    	addPowerNbt(itemstack, tileentity);

        NBTTagList nbttaglist = new NBTTagList();
        itemstack.getTagCompound().setBoolean("Lock", labBlender.isLocked());
        for (int i = 0; i < labBlender.lockList.size(); ++i){
            if (labBlender.lockList.get(i) != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                labBlender.lockList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        itemstack.getTagCompound().setTag("Locked", nbttaglist);

	}
}