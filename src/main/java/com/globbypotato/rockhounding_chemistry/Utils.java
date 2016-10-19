package com.globbypotato.rockhounding_chemistry;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraftforge.items.ItemStackHandler;

public class Utils {

	public static ItemStack damage(ItemStack inStack){
		ItemStack temp = inStack;
		temp.setItemDamage(inStack.getItemDamage()+1);
		
		if(temp.getItemDamage() >= temp.getMaxDamage()) return null;
		else return temp;
	}
	

	
	
	public static void increment(ItemStackHandler handler, int slot){
		ItemStack temp = handler.getStackInSlot(slot);

	}
	public static void decrement(ItemStackHandler handler, int slot){
		if(handler.extractItem(slot, 1, true) == null){
			handler.setStackInSlot(slot, null);
		}
		else {
			ItemStack temp = handler.getStackInSlot(slot);
			temp.stackSize--;
			handler.setStackInSlot(slot, temp);
		}
	}

	public static boolean isItemFuel(ItemStack stack){
		return Utils.getItemBurnTime(stack) > 0;
	}


	public static int getItemBurnTime(ItemStack stack){
		if (stack == null){
			return 0;
		}else{
			Item item = stack.getItem();
			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR){
				Block block = Block.getBlockFromItem(item);
				if (block == Blocks.WOODEN_SLAB){return 150;}
				if (block.getDefaultState().getMaterial() == Material.WOOD){return 300;}
				if (block == Blocks.COAL_BLOCK){return 14400;}
			}
			if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
			if (item == Items.STICK) return 100;
			if (item == Items.COAL) return 1600;
			if (item == Items.LAVA_BUCKET) return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
			if (item == Items.BLAZE_ROD) return 2400;
			return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
		}
	}

	public static boolean isHandlerEmpty(ItemStackHandler handler){
		boolean output = true;
		for(int i = 0; i< handler.getSlots(); i++){
			if(handler.getStackInSlot(i) != null) output = false;
		}
		return output;
	}

	public static boolean areItemsEqualIgnoreMeta(ItemStack stack1, ItemStack stack2){
		if(stack1 == null && stack2 == null) return true;
		if(stack1 != null && stack2 != null){
			return stack1.getItem() == stack2.getItem();
		}
		return false;
	}

	public static boolean implies(boolean bool1, boolean bool2){
		if(!bool1) return true;
		if(bool1 && bool2) return true;
		return false;
	}

}
