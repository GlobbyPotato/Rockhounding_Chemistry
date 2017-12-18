package com.globbypotato.rockhounding_chemistry.utils;

import com.globbypotato.rockhounding_chemistry.ModItems;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ToolUtils extends BaseRecipes {
	public static Item[] specimenList = new Item[] {null, 
													ModItems.arsenateShards, 
													ModItems.borateShards, 
													ModItems.carbonateShards, 
													ModItems.halideShards, 
													ModItems.nativeShards, 
													ModItems.oxideShards, 
													ModItems.phosphateShards, 
													ModItems.silicateShards, 
													ModItems.sulfateShards, 
													ModItems.sulfideShards};

	public static boolean hasWrench(EntityPlayer player, EnumHand hand) {
		return player.getHeldItemMainhand() != null 
			&& player.getHeldItemMainhand().isItemEqual(modWrench);
	}

	public static boolean hasUpgrade(ItemStack insertingStack) {
		return insertingStack != null 
			&& ItemStack.areItemsEqual(insertingStack, chamberUpgrade);
	}

	public static boolean hasInsulation(ItemStack insertingStack) {
		return insertingStack != null 
			&& ItemStack.areItemsEqual(insertingStack, insulationUpgrade);
	}

	public static boolean isValidSpeedUpgrade(ItemStack insertingStack) {
		return insertingStack != null && insertingStack.getItem() == ModItems.speedItems && insertingStack.getItemDamage() > 0;
	}

	public static int speedUpgrade(ItemStack insertingStack) {
		return isValidSpeedUpgrade(insertingStack) ? insertingStack.getItemDamage() + 1 : 1;
	}

	public static ItemStack petrographer = new ItemStack(ModItems.petrographer);
	public static ItemStack cylinder = new ItemStack(ModItems.cylinder);
	public static ItemStack testTube = new ItemStack(ModItems.testTube);
	public static ItemStack gear = new ItemStack(ModItems.gear);
	public static ItemStack agitator = new ItemStack(ModItems.agitator);
	public static ItemStack pattern = new ItemStack(ModItems.ingotPattern);
	public static ItemStack flask = new ItemStack(ModItems.chemFlask);
	public static ItemStack feCatalyst = new ItemStack(ModItems.feCatalyst);
	public static ItemStack ptCatalyst = new ItemStack(ModItems.ptCatalyst);


}