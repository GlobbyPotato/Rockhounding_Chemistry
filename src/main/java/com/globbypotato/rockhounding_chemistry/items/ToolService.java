package com.globbypotato.rockhounding_chemistry.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public class ToolService {

	public static boolean hasWrench(EntityPlayer player, EnumHand hand) {
		return player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() == ModItems.miscItems && player.getHeldItem(hand).getItemDamage() == 12;
	}

}
