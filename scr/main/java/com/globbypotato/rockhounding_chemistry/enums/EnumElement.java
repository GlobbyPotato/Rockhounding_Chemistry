package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumElement implements IStringSerializable{
	CERIUM,
	DYSPROSIUM,
	ERBIUM,
	EUROPIUM,
	GADOLINIUM,
	HOLMIUM,
	LANTHANUM,
	LUTETIUM,
	NEODYMIUM,
	PRASEODYMIUM,
	SAMARIUM,
	SCANDIUM,
	TERBIUM,
	THULIUM,
	YTTERBIUM,
	YTTRIUM,
	IRON,
	COPPER,
	TIN,
	LEAD,
	ZINC,
	CHROMIUM,
	BORON,
	SILVER,
	ALUMINUM,
	MANGANESE,
	NICKEL,
	COBALT,
	MAGNESIUM,
	TITANIUM,
	SODIUM,
	THORIUM,
	CALCIUM,
	PHOSPHORUS,
	LITHIUM,
	POTASSIUM,
	BERYLLIUM,
	SULFUR,
	BISMUTH,
	NIOBIUM,
	TANTALUM,
	ARSENIC,
	SILICON,
	URANIUM,
	PLATINUM,
	GOLD,
	TUNGSTEN,
	OSMIUM,
	IRIDIUM,
	CADMIUM,
	VANADIUM,
	CARBON;

	@Override
	public String getName() {
		return toString().toLowerCase();
	}

	public static int size(){
		return values().length;
	}
	
	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return EnumElement.values()[index].getName();
	}

	public static String[] getDust(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getDust(i);
		}
		return temp;
	}

	public static String getDust(int index){
		return "dust" + EnumElement.values()[index].toString().substring(0, 1).toUpperCase() + EnumElement.values()[index].getName().substring(1);
	}
	
	public static String[] getIngots(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getIngot(i);
		}
		return temp;
	}

	public static String getIngot(int index){
		return "ingot" + EnumElement.values()[index].toString().substring(0, 1).toUpperCase() + EnumElement.values()[index].getName().substring(1);
	}

}