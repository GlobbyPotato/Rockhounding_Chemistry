package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGear extends ItemBase {
	public ItemGear(){
		super("gear");
		this.setMaxDamage(ModConfig.gearUses);
		this.setMaxStackSize(1);

	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		int uses = getMaxDamage(itemstack) - getDamage(itemstack);
		tooltip.add(TextFormatting.DARK_GRAY + ModArray.toolUses + ": " + TextFormatting.WHITE + uses + "/" + this.getMaxDamage(itemstack));
	}
}
