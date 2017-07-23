package com.globbypotato.rockhounding_chemistry.items.tools;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseItem;

import net.minecraft.item.ItemStack;

public class SiliconeCartridge extends BaseItem {

	public SiliconeCartridge(String name, int uses) {
		super(name);
		this.setMaxDamage(uses);
		this.setMaxStackSize(1);
		this.setNoRepair();
		setCreativeTab(Reference.RockhoundingChemistry);
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack){
		if(itemStack.getItemDamage() < itemStack.getMaxDamage() - 1){
			itemStack.setItemDamage(itemStack.getItemDamage() + 1 );
		}else{
			itemStack.stackSize--;
			if(itemStack.stackSize <= 1){
				itemStack = null;
			}
		}
		return itemStack != null ? itemStack.copy() : null;
	}

}