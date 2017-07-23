package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserSplitter;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class LaserSplitter extends BaseLaser {
    
    public LaserSplitter(float hardness, float resistance, String name){
        super(hardness, resistance, name, Material.IRON, SoundType.METAL);
    }

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityLaserSplitter();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
    	TileEntityLaserSplitter splitter = (TileEntityLaserSplitter) world.getTileEntity(pos);
        if (splitter != null){
        	if(ToolUtils.hasWrench(player, hand)){
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
        world.playSound(player, pos, SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
        return false;
    }

}