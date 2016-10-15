package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ChemicalDusts extends Item {
	private String[] itemArray;

	public ChemicalDusts(String name, String[] array) {
		super();
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setHasSubtypes(true);
		setCreativeTab(Reference.RockhoundingChemistry);
		this.itemArray = array;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= itemArray.length){ i = 0; }
		return super.getUnlocalizedName() + "." + itemArray[i];
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for(int i = 0; i < itemArray.length; i++){
			subItems.add(new ItemStack(itemIn, 1, i));
		}
	}

}