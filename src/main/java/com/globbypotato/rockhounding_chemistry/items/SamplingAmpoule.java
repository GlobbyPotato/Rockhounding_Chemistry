package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.ItemIO;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class SamplingAmpoule extends ItemIO {

	public SamplingAmpoule(String name) {
		super(name);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		stack.setTagCompound(new NBTTagCompound());
	}
}