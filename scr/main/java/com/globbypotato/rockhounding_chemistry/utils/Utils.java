package com.globbypotato.rockhounding_chemistry.utils;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class Utils {

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

	public static ArrayList<Integer> intArrayToList(int[] array){
		ArrayList<Integer> temp = new ArrayList<Integer>(array.length);
		for(int i=0;i<array.length;i++){
			temp.add(array[i]);
		}
		return temp;
	}

}
