package com.globbypotato.rockhounding_chemistry.blocks.itemblocks;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.blocks.GanBlocks;
import com.globbypotato.rockhounding_core.blocks.itemblocks.BaseItemBlock;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class GanIB extends BaseItemBlock {
	private String[] enumNames;

	public GanIB(Block block, String[] names) {
        super(block);
        this.enumNames = names;
	}

    @Override
    public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= this.enumNames.length){ i = 0; }
        return super.getUnlocalizedName(stack) + "." + this.enumNames[i];
    }

	@Override
	public int getItemStackLimit(ItemStack stack) {
		if(GanBlocks.isActiveMeta(stack.getItemDamage())){
			return 1;
		}
		return this.getItemStackLimit();
	}

    private static void setItemNbt(ItemStack itemstack) {
		int meta = itemstack.getItemDamage();
    	if(GanBlocks.isActiveMeta(meta)){
        	itemstack.setTagCompound(new NBTTagCompound());
        	if(GanBlocks.isVessel(meta)){
            	itemstack.getTagCompound().setInteger("Air", 0);
        	}
    	}
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		int meta = itemstack.getItemDamage();
        if(itemstack.hasTagCompound()) {
        	if(GanBlocks.isVessel(meta)){
            	int air = itemstack.getTagCompound().getInteger("Air");
	        	if(air > 0){
	        		tooltip.add(TextFormatting.DARK_GRAY + "Compressed Air: " + TextFormatting.AQUA + air + " units");
	        	}
        	}
        	if(GanBlocks.isTank(meta)){
        		String getTag = EnumFluidNbt.SOLVENT.nameTag();
        		if(itemstack.getTagCompound().hasKey(getTag)){
        			FluidStack fluid = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(getTag));
        			if(fluid != null && fluid.amount > 0){
                		tooltip.add(TextFormatting.DARK_GRAY + "Stored Fluid: " + TextFormatting.AQUA + fluid.getLocalizedName() + " (" + fluid.amount + " mB)");
        			}
        		}
        	}
        	if(GanBlocks.isChiller(meta)){
        		String getTag = EnumFluidNbt.SOLVENT.nameTag();
        		if(itemstack.getTagCompound().hasKey(getTag)){
        			FluidStack fluid = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(getTag));
        			if(fluid != null && fluid.amount > 0){
                		tooltip.add(TextFormatting.DARK_GRAY + "Stored Fluid: " + TextFormatting.AQUA + fluid.getLocalizedName() + " (" + fluid.amount + " mB)");
        			}
        		}
        	}
        }
    }

}