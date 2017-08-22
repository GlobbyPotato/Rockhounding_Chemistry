package com.globbypotato.rockhounding_chemistry.blocks.itemblocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.EnumBattery;
import com.globbypotato.rockhounding_core.blocks.itemblocks.BaseItemBlock;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class UltraBatteryIB extends BaseItemBlock {
	private String[] enumNames;

	public UltraBatteryIB(Block block, String[] names) {
        super(block);
        this.enumNames = names;
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= this.enumNames.length){ i = 0; }
        return super.getUnlocalizedName(stack) + "." + this.enumNames[i];
    }

    private static void setItemNbt(ItemStack itemstack) {
    	itemstack.setTagCompound(new NBTTagCompound());
    	itemstack.getTagCompound().setInteger("Energy", 0);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		tooltip.add(TextFormatting.DARK_GRAY + "Capacity: " + TextFormatting.WHITE + EnumBattery.getFormalName(itemstack.getItemDamage()));
        if(itemstack.hasTagCompound()) {
        	int energy = itemstack.getTagCompound().getInteger("Energy");
        	if(energy > 0){
        		tooltip.add(TextFormatting.DARK_GRAY + "Storage: " + TextFormatting.RED + energy + " RF");
        	}
        }
    }

}