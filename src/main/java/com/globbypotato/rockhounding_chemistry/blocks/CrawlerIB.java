package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumSetups;

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
    	itemstack.getTagCompound().setInteger(EnumSetups.TIERS.getName(), 4);
		itemstack.getTagCompound().setInteger(EnumSetups.MODES.getName(), 1);
		itemstack.getTagCompound().setInteger(EnumSetups.STEP.getName(), 10);
		itemstack.getTagCompound().setInteger(EnumSetups.UPGRADE.getName(), 0);
		itemstack.getTagCompound().setBoolean(EnumSetups.FILLER.getName(), false);
		itemstack.getTagCompound().setBoolean(EnumSetups.ABSORBER.getName(), false);
		itemstack.getTagCompound().setBoolean(EnumSetups.TUNNELER.getName(), false);
		itemstack.getTagCompound().setBoolean(EnumSetups.LIGHTER.getName(), false);
		itemstack.getTagCompound().setBoolean(EnumSetups.RAILMAKER.getName(), false);
		itemstack.getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), 64);
		itemstack.getTagCompound().setInteger(EnumSetups.GLASS.getName(), 64);
		itemstack.getTagCompound().setInteger(EnumSetups.TORCHES.getName(), 64);
		itemstack.getTagCompound().setInteger(EnumSetups.RAILS.getName(), 64);
	}
*/
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
        if(itemstack.hasTagCompound()) {
        	int numTier = itemstack.getTagCompound().getInteger(EnumSetups.TIERS.getName());
        	int numMode = itemstack.getTagCompound().getInteger(EnumSetups.MODES.getName());
        	int numStep = itemstack.getTagCompound().getInteger(EnumSetups.STEP.getName());
        	int numUpgrade = itemstack.getTagCompound().getInteger(EnumSetups.UPGRADE.getName());
        	boolean canFill = itemstack.getTagCompound().getBoolean(EnumSetups.FILLER.getName());
        	boolean canAbsorb = itemstack.getTagCompound().getBoolean(EnumSetups.ABSORBER.getName());
        	boolean canTunnel = itemstack.getTagCompound().getBoolean(EnumSetups.TUNNELER.getName());
        	boolean canLight = itemstack.getTagCompound().getBoolean(EnumSetups.LIGHTER.getName());
        	boolean canRail = itemstack.getTagCompound().getBoolean(EnumSetups.RAILMAKER.getName());
        	int numCobble = itemstack.getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName());
        	int numGlass = itemstack.getTagCompound().getInteger(EnumSetups.GLASS.getName());
        	int numTorch = itemstack.getTagCompound().getInteger(EnumSetups.TORCHES.getName());
        	int numRail = itemstack.getTagCompound().getInteger(EnumSetups.RAILS.getName());
        	
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.TIERS.getName() + ": " + TextFormatting.YELLOW + numTier);
        	String writeMode = "";if(numMode == 0){writeMode = "Breaking";}else if(numMode == 1){writeMode = "Coring";}
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.MODES.getName() + ": " + TextFormatting.YELLOW + writeMode);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.STEP.getName() + ": " + TextFormatting.AQUA + numStep + " tB");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.UPGRADE.getName() + ": " + TextFormatting.DARK_GRAY + numUpgrade);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.FILLER.getName() + ": " + TextFormatting.RED + canFill);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.ABSORBER.getName() + ": " + TextFormatting.RED + canAbsorb);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.TUNNELER.getName() + ": " + TextFormatting.RED + canTunnel);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.LIGHTER.getName() + ": " + TextFormatting.RED + canLight);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.RAILMAKER.getName() + ": " + TextFormatting.RED + canRail);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.COBBLESTONE.getName() + ": " + TextFormatting.WHITE + numCobble);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.GLASS.getName() + ": " + TextFormatting.WHITE + numGlass);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.TORCHES.getName() + ": " + TextFormatting.WHITE + numTorch);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.RAILS.getName() + ": " + TextFormatting.WHITE + numRail);
        }else{
//        	setItemNbt(itemstack);
        }
    }

}