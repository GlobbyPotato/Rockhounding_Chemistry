package com.globbypotato.rockhounding_chemistry.utils;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.fluids.ItemBeaker;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.UniversalBucket;

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
		return player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() == ModItems.miscItems && player.getHeldItem(hand).getItemDamage() == 12;
	}

	public static boolean hasinductor(ItemStack insertingStack) {
		return insertingStack != null && ItemStack.areItemsEqual(insertingStack, ToolUtils.inductor);
	}

	public static boolean hasUpgrade(ItemStack insertingStack) {
		return insertingStack != null && ItemStack.areItemsEqual(insertingStack, ToolUtils.vdcUpgrade);
	}

	public static boolean hasInsulation(ItemStack insertingStack) {
		return insertingStack != null && ItemStack.areItemsEqual(insertingStack, ToolUtils.vdcInsulation);
	}

	public static boolean hasConsumable(ItemStack consumable, ItemStack insertingStack) {
		return insertingStack != null 
			&& Utils.areItemsEqualIgnoreMeta(consumable, insertingStack)
			&& insertingStack.getItemDamage() < insertingStack.getMaxDamage();
	}

	public static boolean isBucketType(ItemStack insertingStack) {
		if(!FluidRegistry.isUniversalBucketEnabled() ){
			return insertingStack != null && (insertingStack.getItem() instanceof ItemBeaker || insertingStack.getItem() instanceof ItemBucket) ;
		}else{
			return insertingStack != null && (insertingStack.getItem() instanceof ItemBucket || insertingStack.getItem() instanceof UniversalBucket);
		}
	}

	public static ItemStack getBucketType(Fluid fluid, Item container) {
		if(!FluidRegistry.isUniversalBucketEnabled() ){
			return new ItemStack(container);
		}else{
			return UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid);
		}
	}

	public static ItemStack getFluidBucket(Fluid fluid) {
		return UniversalBucket.getFilledBucket(ForgeModContainer.getInstance().universalBucket, fluid);
	}

	public static ItemStack oreStack = new ItemStack(ModBlocks.mineralOres,1,0);
	public static ItemStack petrographer = new ItemStack(ModItems.petrographer);
	public static ItemStack inductor = new ItemStack(ModItems.miscItems, 1, 17);
	public static ItemStack cylinder = new ItemStack(ModItems.cylinder);
	public static ItemStack testTube = new ItemStack(ModItems.testTube);
	public static ItemStack gear = new ItemStack(ModItems.gear);
	public static ItemStack pattern = new ItemStack(ModItems.ingotPattern);
	public static ItemStack polymer = new ItemStack(ModItems.chemicalItems,1,0);
	public static ItemStack saltStack = new ItemStack(ModItems.chemicalItems, 1, 1);
	public static ItemStack rawSalt = new ItemStack(ModItems.chemicalItems, 1, 7);
	public static ItemStack vdcUpgrade = new ItemStack(ModItems.miscItems, 1, 34);
	public static ItemStack vdcInsulation = new ItemStack(ModItems.miscItems, 1, 35);

}