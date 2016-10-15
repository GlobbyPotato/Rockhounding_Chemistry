package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MineralShards extends Item {
	private String[] itemArray;

	public MineralShards(String name, String[] array) {
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

    @SideOnly(Side.CLIENT)
	@Override
    public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean held) {
    	if(itemstack.getItem() == ModContents.arsenateShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Arsenate");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "36%");
    			list.add(TextFormatting.DARK_GRAY + "Arsenic: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Dysprosium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Yttrium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Lanthanum: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Neodymium" + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Europium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Gadolinium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Samarium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Arsenate");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "55%");
    			list.add(TextFormatting.DARK_GRAY + "Arsenic: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "9%");
    			list.add(TextFormatting.DARK_GRAY + "Chromium: " + TextFormatting.WHITE + "7%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.borateShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Borate");
    			list.add(TextFormatting.DARK_GRAY + "Sodium: " + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "11%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Borate");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "17%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "4%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Borate");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "46%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "5%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Borate");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "15%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "13%");
    			list.add(TextFormatting.DARK_GRAY + "Beryllium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Lithium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Borate");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "40%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "19%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "8%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Borate");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "13%");
    			list.add(TextFormatting.DARK_GRAY + "Beryllium: " + TextFormatting.WHITE + "8%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "4%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.carbonateShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Carbonate");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "19%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "16%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Carbonate");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "33%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "5%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Carbonate");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "43%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "15%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Carbonate");
    			list.add(TextFormatting.DARK_GRAY + "Neodymium: " + TextFormatting.WHITE + "23%");
    			list.add(TextFormatting.DARK_GRAY + "Lanthanum: " + TextFormatting.WHITE + "21%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "19%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "7%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.halideShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Halide");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "49%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Silver: " + TextFormatting.WHITE + "9%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Halide");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "9%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Halide");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "28%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Sodium: " + TextFormatting.WHITE + "6%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Halide");
    			list.add(TextFormatting.DARK_GRAY + "Lithium: " + TextFormatting.WHITE + "27%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Halide");
    			list.add(TextFormatting.DARK_GRAY + "Fluorite: " + TextFormatting.WHITE + "100%");
    		}
    	}    	
    	if(itemstack.getItem() == ModContents.nativeShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "55%");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "29%");
    			list.add(TextFormatting.DARK_GRAY + "Cobalt" + TextFormatting.WHITE + "10%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "100%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "60%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "26%");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "7%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "82%");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "13%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "64%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "11%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "4%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Silver: " + TextFormatting.WHITE + "100%");
    		}
    		if(itemstack.getItemDamage() == 6){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Platinum: " + TextFormatting.WHITE + "76%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "13%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "11%");
    		}
    		if(itemstack.getItemDamage() == 7){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Platinum: " + TextFormatting.WHITE + "62%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "38%");
    		}
    		if(itemstack.getItemDamage() == 8){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Gold: " + TextFormatting.WHITE + "65%");
    			list.add(TextFormatting.DARK_GRAY + "Bismuth: " + TextFormatting.WHITE + "35%");
    		}
    		if(itemstack.getItemDamage() == 9){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Gold: " + TextFormatting.WHITE + "51%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "49%");
    		}
    		if(itemstack.getItemDamage() == 10){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Osmium: " + TextFormatting.WHITE + "75%");
    			list.add(TextFormatting.DARK_GRAY + "Iridium: " + TextFormatting.WHITE + "25%");
    		}
    		if(itemstack.getItemDamage() == 11){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Native");
    			list.add(TextFormatting.DARK_GRAY + "Iridium: " + TextFormatting.WHITE + "52%");
    			list.add(TextFormatting.DARK_GRAY + "Osmium: " + TextFormatting.WHITE + "31%");
    			list.add(TextFormatting.DARK_GRAY + "Platinum: " + TextFormatting.WHITE + "11%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.oxideShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Chromium: " + TextFormatting.WHITE + "47%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "25%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Chromium: " + TextFormatting.WHITE + "35%");
    			list.add(TextFormatting.DARK_GRAY + "Cobalt: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "8%");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Niobium: " + TextFormatting.WHITE + "42%");
    			list.add(TextFormatting.DARK_GRAY + "Tantalum: " + TextFormatting.WHITE + "24%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "8%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Titanium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Niobium: " + TextFormatting.WHITE + "33%");
    			list.add(TextFormatting.DARK_GRAY + "Yttrium: " + TextFormatting.WHITE + "16%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Titanium: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "43%");
    			list.add(TextFormatting.DARK_GRAY + "Chromium" + TextFormatting.WHITE + "35%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Niobium: " + TextFormatting.WHITE + "24%");
    			list.add(TextFormatting.DARK_GRAY + "Tantalum" + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Uranium" + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Thorium" + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Ytterbium" + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Iron" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Yttrium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Dysprosium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Erbium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Lutetium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Thulium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Holmium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Europium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Terbium" + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium" + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 6){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "100%");
    		}
    		if(itemstack.getItemDamage() == 7){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "100%");
    		}
    		if(itemstack.getItemDamage() == 8){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "36%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "30%");
    		}
    		if(itemstack.getItemDamage() == 9){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "40%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Titanium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Lanthanum: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 10){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Titanium: " + TextFormatting.WHITE + "25%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "21%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "11%");
    		}
    		if(itemstack.getItemDamage() == 11){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Titanium: " + TextFormatting.WHITE + "25%");
    			list.add(TextFormatting.DARK_GRAY + "Thorium: " + TextFormatting.WHITE + "24%");
    			list.add(TextFormatting.DARK_GRAY + "Uranium: " + TextFormatting.WHITE + "24%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 12){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Tantalum: " + TextFormatting.WHITE + "56%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Niobium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 13){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Tantalum: " + TextFormatting.WHITE + "66%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Niobium: " + TextFormatting.WHITE + "6%");
    		}
    		if(itemstack.getItemDamage() == 14){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Beryllium: " + TextFormatting.WHITE + "21%");
    		}
    		if(itemstack.getItemDamage() == 15){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Beryllium: " + TextFormatting.WHITE + "36%");
    		}
    		if(itemstack.getItemDamage() == 16){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Tungsten: " + TextFormatting.WHITE + "75%");
    		}
    		if(itemstack.getItemDamage() == 17){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Tungsten: " + TextFormatting.WHITE + "61%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "9%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "9%");
    		}
    		if(itemstack.getItemDamage() == 18){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Tungsten: " + TextFormatting.WHITE + "61%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "18%");
    		}
    		if(itemstack.getItemDamage() == 19){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Oxide");
    			list.add(TextFormatting.DARK_GRAY + "Cadmium: " + TextFormatting.WHITE + "88%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.phosphateShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "15%");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "18%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "8%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Neodymium: " + TextFormatting.WHITE + "21%");
    			list.add(TextFormatting.DARK_GRAY + "Lanthanum: " + TextFormatting.WHITE + "18%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "16%");
    			list.add(TextFormatting.DARK_GRAY + "Gadolinium: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Samarium: " + TextFormatting.WHITE + "13%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "11%");
    			list.add(TextFormatting.DARK_GRAY + "Thorium: " + TextFormatting.WHITE + "7%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "8%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "7%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "35%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Lithium: " + TextFormatting.WHITE + "5%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "15%");
    		}
    		if(itemstack.getItemDamage() == 6){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Ytterbium: " + TextFormatting.WHITE + "65%");
    			list.add(TextFormatting.DARK_GRAY + "Yttrium: " + TextFormatting.WHITE + "48%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "14%");
    		}
    		if(itemstack.getItemDamage() == 7){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Bismuth: " + TextFormatting.WHITE + "32%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 8){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
    			list.add(TextFormatting.DARK_GRAY + "Scandium: " + TextFormatting.WHITE + "32%");
    			list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "22%");
    		}
    		if(itemstack.getItemDamage() == 9){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
				list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "32%");
				list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "18%");
				list.add(TextFormatting.DARK_GRAY + "Lithium: " + TextFormatting.WHITE + "4%");
    		}
    		if(itemstack.getItemDamage() == 10){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
				list.add(TextFormatting.DARK_GRAY + "Arsenic: " + TextFormatting.WHITE + "29%");
				list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "15%");
				list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "14%");
				list.add(TextFormatting.DARK_GRAY + "Cadmium: " + TextFormatting.WHITE + "13%");
				list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "1%");
				list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 11){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Phosphate");
				list.add(TextFormatting.DARK_GRAY + "Cadmium: " + TextFormatting.WHITE + "32%");
				list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "17%");
				list.add(TextFormatting.DARK_GRAY + "Phosphorus: " + TextFormatting.WHITE + "9%");
				list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "1%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.silicateShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "19%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "6%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Yttrium: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Lanthanum: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Praseodymium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Samarium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Beryllium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Europium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Holmium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Lutetium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Terbium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Thulium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "68%");
    			list.add(TextFormatting.DARK_GRAY + "Chromium: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "25%");
    			list.add(TextFormatting.DARK_GRAY + "Scandium: " + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Sodium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "18%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Scandium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 6){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "28%");
    			list.add(TextFormatting.DARK_GRAY + "Sodium: " + TextFormatting.WHITE + "11%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Dysprosium: " + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Gadolinium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Holmium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Erbium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Terbium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Samarium: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 7){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Lithium: " + TextFormatting.WHITE + "6%");
    		}
    		if(itemstack.getItemDamage() == 8){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "27%");
    			list.add(TextFormatting.DARK_GRAY + "Thorium: " + TextFormatting.WHITE + "25%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Sodium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 9){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "26%");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "11%");
    			list.add(TextFormatting.DARK_GRAY + "Lithium: " + TextFormatting.WHITE + "3%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 10){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "29%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "16%");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "15%");
    			list.add(TextFormatting.DARK_GRAY + "Boron: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 11){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Silicate");
    			list.add(TextFormatting.DARK_GRAY + "Silicon: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Cerium: " + TextFormatting.WHITE + "12%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese: " + TextFormatting.WHITE + "9%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "5%");
    			list.add(TextFormatting.DARK_GRAY + "Europium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Lutetium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Thulium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Therbium: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "2%");
    			list.add(TextFormatting.DARK_GRAY + "Titanium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Chromium: " + TextFormatting.WHITE + "1%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "1%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.sulfateShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "15%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "10%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "33%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "17%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "14%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "33%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "13%");
    			list.add(TextFormatting.DARK_GRAY + "Potassium: " + TextFormatting.WHITE + "8%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "34%");
    			list.add(TextFormatting.DARK_GRAY + "Cobalt" + TextFormatting.WHITE + "8%");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "8%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "4%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "19%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Chromium" + TextFormatting.WHITE + "6%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "1%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Cobalt: " + TextFormatting.WHITE + "16%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Manganese" + TextFormatting.WHITE + "7%");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 6){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Cobalt: " + TextFormatting.WHITE + "21%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "11%");
    		}
    		if(itemstack.getItemDamage() == 7){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Tungsten: " + TextFormatting.WHITE + "64%");
    			list.add(TextFormatting.DARK_GRAY + "Calcium: " + TextFormatting.WHITE + "14%");
    		}
    		if(itemstack.getItemDamage() == 8){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfate");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "46%");
    			list.add(TextFormatting.DARK_GRAY + "Tungsten: " + TextFormatting.WHITE + "40%");
    		}
    	}
    	if(itemstack.getItem() == ModContents.sulfideShards){
    		if(itemstack.getItemDamage() == 0){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "37%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "21%");
    			list.add(TextFormatting.DARK_GRAY + "Bismuth: " + TextFormatting.WHITE + "17%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "12%");
    		}
    		if(itemstack.getItemDamage() == 1){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Bismuth: " + TextFormatting.WHITE + "36%");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "36%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "17%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "11%");
    		}
    		if(itemstack.getItemDamage() == 2){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "36%");
    			list.add(TextFormatting.DARK_GRAY + "Silver: " + TextFormatting.WHITE + "35%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "16%");
    		}
    		if(itemstack.getItemDamage() == 3){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Lead: " + TextFormatting.WHITE + "88%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "12%");
    		}
    		if(itemstack.getItemDamage() == 4){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "32%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "27%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "27%");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 5){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Nickel: " + TextFormatting.WHITE + "37%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "33%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "33%");
    		}
    		if(itemstack.getItemDamage() == 6){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "53%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "47%");
    		}
    		if(itemstack.getItemDamage() == 7){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "30%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "30%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "28%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "10%");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "2%");
    		}
    		if(itemstack.getItemDamage() == 8){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "26%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "26%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "24%");
    			list.add(TextFormatting.DARK_GRAY + "Magnesium: " + TextFormatting.WHITE + "9%");
    			list.add(TextFormatting.DARK_GRAY + "Aluminum: " + TextFormatting.WHITE + "7%");
    		}
    		if(itemstack.getItemDamage() == 9){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "64%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "33%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 10){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "34%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "25%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "20%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "9%");
    			list.add(TextFormatting.DARK_GRAY + "Zinc: " + TextFormatting.WHITE + "4%");
    			list.add(TextFormatting.DARK_GRAY + "Silver: " + TextFormatting.WHITE + "3%");
    		}
    		if(itemstack.getItemDamage() == 11){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "44%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "29%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "14%");
    			list.add(TextFormatting.DARK_GRAY + "Iron: " + TextFormatting.WHITE + "13%");
    		}
    		if(itemstack.getItemDamage() == 12){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Tungsten: " + TextFormatting.WHITE + "74%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "26%");
    		}
    		if(itemstack.getItemDamage() == 13){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Osmium: " + TextFormatting.WHITE + "75%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "25%");
    		}
    		if(itemstack.getItemDamage() == 14){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Platinum: " + TextFormatting.WHITE + "50%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "22%");
    			list.add(TextFormatting.DARK_GRAY + "Iridium: " + TextFormatting.WHITE + "17%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "11%");
    		}
    		if(itemstack.getItemDamage() == 15){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Cadmium: " + TextFormatting.WHITE + "78%");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "22%");
    		}
    		if(itemstack.getItemDamage() == 16){
    			list.add(TextFormatting.DARK_GRAY + "Category: " + TextFormatting.YELLOW + "Sulfide");
    			list.add(TextFormatting.DARK_GRAY + "Sulfur: " + TextFormatting.WHITE + "27%");
    			list.add(TextFormatting.DARK_GRAY + "Copper: " + TextFormatting.WHITE + "26%");
    			list.add(TextFormatting.DARK_GRAY + "Tin: " + TextFormatting.WHITE + "24%");
    			list.add(TextFormatting.DARK_GRAY + "Cadmium: " + TextFormatting.WHITE + "23%");
    		}
    	}


   	}

}