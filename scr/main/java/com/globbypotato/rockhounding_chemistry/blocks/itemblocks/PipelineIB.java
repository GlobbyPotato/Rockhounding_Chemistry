package com.globbypotato.rockhounding_chemistry.blocks.itemblocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PipelineIB extends ItemBlock {

	public PipelineIB(Block block) {
        super(block);
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
        if(itemstack.hasTagCompound()){
        	if(itemstack.getTagCompound().hasKey("Upgrade")){
        		String gettier = TextFormatting.AQUA + "Normal (1B)";
        		if(itemstack.getTagCompound().getBoolean("Upgrade")){
            		gettier = TextFormatting.DARK_AQUA + "Advanced (100B)";
        		}
        		tooltip.add(TextFormatting.DARK_GRAY + "Tier: " + TextFormatting.AQUA + gettier);
        	}
        }
    }

}