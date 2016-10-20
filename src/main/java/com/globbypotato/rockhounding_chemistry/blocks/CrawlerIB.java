package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrawlerIB extends ItemBlock {
	public CrawlerIB(Block block) {
        super(block);
        this.setMaxDamage(0);
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

	public int getMetadata(int meta){
		return meta;
	}
	
	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
//		setItemNbt(itemstack);
    }
/*
    private static void setItemNbt(ItemStack itemstack) {
    	itemstack.setTagCompound(new NBTTagCompound());
    	itemstack.getTagCompound().setInteger(ModArray.tierName, 4);
		itemstack.getTagCompound().setInteger(ModArray.modeName, 1);
		itemstack.getTagCompound().setInteger(ModArray.stepName, 10);
		itemstack.getTagCompound().setInteger(ModArray.upgradeName, 0);
		itemstack.getTagCompound().setBoolean(ModArray.fillerName, false);
		itemstack.getTagCompound().setBoolean(ModArray.absorbName, false);
		itemstack.getTagCompound().setBoolean(ModArray.tunnelName, false);
		itemstack.getTagCompound().setBoolean(ModArray.lighterName, false);
		itemstack.getTagCompound().setBoolean(ModArray.railmakerName, false);
		itemstack.getTagCompound().setInteger(ModArray.cobbleName, 64);
		itemstack.getTagCompound().setInteger(ModArray.glassName, 64);
		itemstack.getTagCompound().setInteger(ModArray.torchName, 64);
		itemstack.getTagCompound().setInteger(ModArray.railName, 64);
	}
*/
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
        if(itemstack.hasTagCompound()) {
        	int numTier = itemstack.getTagCompound().getInteger(ModArray.tierName);
        	int numMode = itemstack.getTagCompound().getInteger(ModArray.modeName);
        	int numStep = itemstack.getTagCompound().getInteger(ModArray.stepName);
        	int numUpgrade = itemstack.getTagCompound().getInteger(ModArray.upgradeName);
        	boolean canFill = itemstack.getTagCompound().getBoolean(ModArray.fillerName);
        	boolean canAbsorb = itemstack.getTagCompound().getBoolean(ModArray.absorbName);
        	boolean canTunnel = itemstack.getTagCompound().getBoolean(ModArray.tunnelName);
        	boolean canLight = itemstack.getTagCompound().getBoolean(ModArray.lighterName);
        	boolean canRail = itemstack.getTagCompound().getBoolean(ModArray.railmakerName);
        	int numCobble = itemstack.getTagCompound().getInteger(ModArray.cobbleName);
        	int numGlass = itemstack.getTagCompound().getInteger(ModArray.glassName);
        	int numTorch = itemstack.getTagCompound().getInteger(ModArray.torchName);
        	int numRail = itemstack.getTagCompound().getInteger(ModArray.railName);
        	
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.tierName + ": " + TextFormatting.YELLOW + numTier);
        	String writeMode = "";if(numMode == 0){writeMode = "Breaking";}else if(numMode == 1){writeMode = "Coring";}
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.modeName + ": " + TextFormatting.YELLOW + writeMode);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.stepName + ": " + TextFormatting.AQUA + numStep + " tB");
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.upgradeName + ": " + TextFormatting.DARK_GRAY + numUpgrade);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.fillerName + ": " + TextFormatting.RED + canFill);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.absorbName + ": " + TextFormatting.RED + canAbsorb);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.tunnelName + ": " + TextFormatting.RED + canTunnel);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.lighterName + ": " + TextFormatting.RED + canLight);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.railmakerName + ": " + TextFormatting.RED + canRail);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.cobbleName + ": " + TextFormatting.WHITE + numCobble);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.glassName + ": " + TextFormatting.WHITE + numGlass);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.torchName + ": " + TextFormatting.WHITE + numTorch);
        	tooltip.add(TextFormatting.DARK_GRAY + ModArray.railName + ": " + TextFormatting.WHITE + numRail);
        }else{
//        	setItemNbt(itemstack);
        }
    }

}