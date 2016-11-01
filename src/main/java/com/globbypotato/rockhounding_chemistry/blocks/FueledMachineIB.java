package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumSetups;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FueledMachineIB extends ItemBlock {
	public FueledMachineIB(Block block) {
        super(block);
        this.setMaxDamage(0);
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack);
    }

	public int getMetadata(int meta){
		return meta;
	}
	
	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		setItemNbt(itemstack);
    }

    private static void setItemNbt(ItemStack itemstack) {
    	itemstack.setTagCompound(new NBTTagCompound());
    	itemstack.getTagCompound().setInteger("Fuel", 0);
    	itemstack.getTagCompound().setInteger("Energy", 0);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
        if(itemstack.hasTagCompound()) {
        	int fuel = itemstack.getTagCompound().getInteger("Fuel");
        	int energy = itemstack.getTagCompound().getInteger("Energy");
        	if(fuel > 0){
        		tooltip.add(TextFormatting.DARK_GRAY + "Stored Fuel: " + TextFormatting.YELLOW + fuel + " ticks");
        	}
        	if(energy > 0){
        		tooltip.add(TextFormatting.DARK_GRAY + "Stored Energy: " + TextFormatting.RED + energy + " RF");
        	}
        }else{
        	setItemNbt(itemstack);
        }
    }

}