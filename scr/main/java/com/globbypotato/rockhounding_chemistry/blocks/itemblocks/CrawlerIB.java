package com.globbypotato.rockhounding_chemistry.blocks.itemblocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.EnumCrawler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.CrawlerUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class CrawlerIB extends BaseItemBlock {
	public CrawlerIB(Block block) {
        super(block);
		this.setMaxStackSize(1);
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
        if(itemstack.hasTagCompound()) {
        	checkForOldNBT(itemstack);
        	int numTier = itemstack.getTagCompound().getInteger(EnumCrawler.TIERS.getName());
        	int numMode = itemstack.getTagCompound().getInteger(EnumCrawler.MODES.getName());
        	int numStep = itemstack.getTagCompound().getInteger(EnumCrawler.STEP.getName());
        	int numUpgrade = itemstack.getTagCompound().getInteger(EnumCrawler.UPGRADE.getName());
        	boolean canFill = itemstack.getTagCompound().getBoolean(EnumCrawler.FILLER.getName());
        	boolean canAbsorb = itemstack.getTagCompound().getBoolean(EnumCrawler.ABSORBER.getName());
        	boolean canTunnel = itemstack.getTagCompound().getBoolean(EnumCrawler.TUNNELER.getName());
        	boolean canLight = itemstack.getTagCompound().getBoolean(EnumCrawler.LIGHTER.getName());
        	boolean canRail = itemstack.getTagCompound().getBoolean(EnumCrawler.RAILMAKER.getName());
        	boolean canDeco = itemstack.getTagCompound().getBoolean(EnumCrawler.DECORATOR.getName());
        	boolean canPath = itemstack.getTagCompound().getBoolean(EnumCrawler.PATHFINDER.getName());
        	boolean canStore = itemstack.getTagCompound().getBoolean(EnumCrawler.STORAGE.getName());

        	String fillBlockName = itemstack.getTagCompound().getString(EnumCrawler.FILLER_BLOCK.getBlockName());
        	int fillBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta());
        	int fillBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize());

        	String absorbBlockName = itemstack.getTagCompound().getString(EnumCrawler.ABSORBER_BLOCK.getBlockName());
        	int absorbBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta());
        	int absorbBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());

        	String lighterBlockName = itemstack.getTagCompound().getString(EnumCrawler.LIGHTER_BLOCK.getBlockName());
        	int lighterBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta());
        	int lighterBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());

        	String railBlockName = itemstack.getTagCompound().getString(EnumCrawler.RAILMAKER_BLOCK.getBlockName());
        	int railBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta());
        	int railBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());

        	String decoBlockName = itemstack.getTagCompound().getString(EnumCrawler.DECORATOR_BLOCK.getBlockName());
        	int decoBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta());
        	int decoBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());

        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.TIERS.getName() + ": " + TextFormatting.YELLOW + numTier);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.MODES.getName() + ": " + TextFormatting.YELLOW + CrawlerUtils.getMode(numMode));
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.STEP.getName() + ": " + TextFormatting.AQUA + numStep + " tB");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.UPGRADE.getName() + ": " + TextFormatting.DARK_GRAY + numUpgrade);
        	tooltip.add(TextFormatting.DARK_GRAY + "");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.FILLER.getName() + ": " + TextFormatting.RED + canFill);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.TUNNELER.getName() + ": " + TextFormatting.RED + canTunnel);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.PATHFINDER.getName() + ": " + TextFormatting.RED + canPath);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.STORAGE.getName() + ": " + TextFormatting.RED + canStore);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.FILLER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(fillBlockName, fillBlockMeta, fillBlockSize));
        	tooltip.add(TextFormatting.DARK_GRAY + "");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.ABSORBER.getName() + ": " + TextFormatting.RED + canAbsorb);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.ABSORBER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(absorbBlockName, absorbBlockMeta, absorbBlockSize));
        	tooltip.add(TextFormatting.DARK_GRAY + "");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.LIGHTER.getName() + ": " + TextFormatting.RED + canLight);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.LIGHTER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(lighterBlockName, lighterBlockMeta, lighterBlockSize));
        	tooltip.add(TextFormatting.DARK_GRAY + "");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.RAILMAKER.getName() + ": " + TextFormatting.RED + canRail);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.RAILMAKER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(railBlockName, railBlockMeta, railBlockSize));
        	tooltip.add(TextFormatting.DARK_GRAY + "");
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.DECORATOR.getName() + ": " + TextFormatting.RED + canDeco);
        	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.DECORATOR_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(decoBlockName, decoBlockMeta, decoBlockSize));
        }
    }

	public static void checkForOldNBT(ItemStack itemstack) {
		if(itemstack.getTagCompound().hasKey("Cobblestone")){
			itemstack.getTagCompound().setString(EnumCrawler.FILLER_BLOCK.getBlockName(), Blocks.COBBLESTONE.getRegistryName().toString());
			itemstack.getTagCompound().setInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta(), 0);
			itemstack.getTagCompound().setInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize(), itemstack.getTagCompound().getInteger("Cobblestone"));
			itemstack.getTagCompound().removeTag("Cobblestone");
		}
		if(itemstack.getTagCompound().hasKey("Glass")){
			itemstack.getTagCompound().setString(EnumCrawler.ABSORBER_BLOCK.getBlockName(), Blocks.GLASS.getRegistryName().toString());
			itemstack.getTagCompound().setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), 0);
			itemstack.getTagCompound().setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize(), itemstack.getTagCompound().getInteger("Glass"));
			itemstack.getTagCompound().removeTag("Glass");
		}
		if(itemstack.getTagCompound().hasKey("Torch")){
			itemstack.getTagCompound().setString(EnumCrawler.LIGHTER_BLOCK.getBlockName(), Blocks.TORCH.getRegistryName().toString());
			itemstack.getTagCompound().setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), 0);
			itemstack.getTagCompound().setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize(), itemstack.getTagCompound().getInteger("Torch"));
			itemstack.getTagCompound().removeTag("Torch");
		}
		if(itemstack.getTagCompound().hasKey("Rails")){
			itemstack.getTagCompound().setString(EnumCrawler.RAILMAKER_BLOCK.getBlockName(), Blocks.RAIL.getRegistryName().toString());
			itemstack.getTagCompound().setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), 0);
			itemstack.getTagCompound().setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize(), itemstack.getTagCompound().getInteger("Rails"));
			itemstack.getTagCompound().removeTag("Rails");
		}
	}
}