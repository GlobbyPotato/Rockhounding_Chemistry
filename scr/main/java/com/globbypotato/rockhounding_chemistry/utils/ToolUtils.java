package com.globbypotato.rockhounding_chemistry.utils;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

public class ToolUtils {
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
		return player.getHeldItem(hand) != null 
			&& player.getHeldItem(hand).getItem() == ModItems.miscItems 
			&& player.getHeldItem(hand).getItemDamage() == 12;
	}

	public static boolean hasUpgrade(ItemStack insertingStack) {
		return insertingStack != null && ItemStack.areItemsEqual(insertingStack, vdcUpgrade);
	}

	public static boolean hasInsulation(ItemStack insertingStack) {
		return insertingStack != null && ItemStack.areItemsEqual(insertingStack, vdcInsulation);
	}

	public static ItemStack oreStack = new ItemStack(ModBlocks.mineralOres,1,0);
	public static ItemStack petrographer = new ItemStack(ModItems.petrographer);
	public static ItemStack cylinder = new ItemStack(ModItems.cylinder);
	public static ItemStack testTube = new ItemStack(ModItems.testTube);
	public static ItemStack gear = new ItemStack(ModItems.gear);
	public static ItemStack agitator = new ItemStack(ModItems.agitator);
	public static ItemStack pattern = new ItemStack(ModItems.ingotPattern);
	public static ItemStack polymer = new ItemStack(ModItems.chemicalItems,1,0);
	public static ItemStack saltStack = new ItemStack(ModItems.chemicalItems, 1, 1);
	public static ItemStack rawSalt = new ItemStack(ModItems.chemicalItems, 1, 7);
	public static ItemStack vdcUpgrade = new ItemStack(ModItems.miscItems, 1, 34);
	public static ItemStack vdcInsulation = new ItemStack(ModItems.miscItems, 1, 35);
	public static ItemStack flask = new ItemStack(ModItems.chemFlask);
	public static ItemStack feCatalyst = new ItemStack(ModItems.feCatalyst);
	public static ItemStack ptCatalyst = new ItemStack(ModItems.ptCatalyst);


}