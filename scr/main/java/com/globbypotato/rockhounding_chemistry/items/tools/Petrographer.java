package com.globbypotato.rockhounding_chemistry.items.tools;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Petrographer extends ItemPickaxe {
	public static int baseLevelUp = 10;
	public static int baseFinds = 20;
	ItemStack specStack;

	public Petrographer(ToolMaterial material, String name) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		GameRegistry.register(this);
        this.setMaxStackSize(1);
        this.setMaxDamage(material.getMaxUses());
		this.setCreativeTab(Reference.RockhoundingChemistry);
	}

	@Override
	public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		setItemNbt(itemstack);
	}

	public static void setItemNbt(ItemStack itemstack) {
   		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setInteger("nLevel", 0);
		itemstack.getTagCompound().setInteger("nLevelUp", baseLevelUp);
		itemstack.getTagCompound().setInteger("nFlavor", 0); //to be zero
		itemstack.getTagCompound().setInteger("nSpecimen", -1); //to be -1
		itemstack.getTagCompound().setInteger("nFortune", 1);
		itemstack.getTagCompound().setInteger("nFinds", baseFinds);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		if(itemstack.hasTagCompound()) {
	    	int nLevel = itemstack.getTagCompound().getInteger("nLevel");
	    	int nLevelUp = itemstack.getTagCompound().getInteger("nLevelUp");
	    	int nFlavor = itemstack.getTagCompound().getInteger("nFlavor");
	    	int nSpecimen = itemstack.getTagCompound().getInteger("nSpecimen");
	    	int nFortune = itemstack.getTagCompound().getInteger("nFortune");
	    	int nFinds = itemstack.getTagCompound().getInteger("nFinds");

	    	int totLevelUp = baseLevelUp + (nLevel * baseLevelUp);
	    	int totFinds = baseFinds + ((nFortune-1) * baseFinds);
	    	specStack = new ItemStack(ToolUtils.specimenList[nFlavor], 1, nSpecimen);

	    	tooltip.add(TextFormatting.DARK_GRAY + " ");
	    	tooltip.add(TextFormatting.DARK_GRAY + "Level: " + TextFormatting.AQUA + nLevel + TextFormatting.DARK_GRAY + " | " + TextFormatting.WHITE + nLevelUp + "/" + totLevelUp + " xp to levelup");
	    	tooltip.add(TextFormatting.DARK_GRAY + "Fortune: " + TextFormatting.AQUA + (nFortune) + TextFormatting.DARK_GRAY + " | " + TextFormatting.YELLOW + ((100 / 32) * (nFortune)) + "%" + TextFormatting.DARK_GRAY + " | " + TextFormatting.WHITE + nFinds + "/" + totFinds + " to find");
	    	tooltip.add(TextFormatting.DARK_GRAY + " ");

	    	tooltip.add(TextFormatting.DARK_GRAY + "Mineral Flavor: " + TextFormatting.WHITE + new ItemStack(ModBlocks.mineralOres, 1, nFlavor).getDisplayName() );
	    	if(nFlavor > 0 && nSpecimen > -1){
	    		tooltip.add(TextFormatting.DARK_GRAY + "Specimen Flavor: " + TextFormatting.WHITE + specStack.getDisplayName() );
	   		}else{
	   			tooltip.add(TextFormatting.DARK_GRAY + "Specimen Flavor: " + TextFormatting.WHITE + "None" );
	   		}
	    	tooltip.add(TextFormatting.DARK_GRAY + " ");
		}else{
			setItemNbt(itemstack);
		}
	}

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
        ItemStack mat = new ItemStack(Items.DIAMOND);
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

}
