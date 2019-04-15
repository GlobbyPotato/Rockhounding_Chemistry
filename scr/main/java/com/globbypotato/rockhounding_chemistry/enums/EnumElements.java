package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumElements implements BaseEnum{
	//RARE
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

	//COMMON
	ALUMINUM,
	ANTIMONY,//
	ARSENIC,
	BERYLLIUM,
	BISMUTH,
	BORON,
	CADMIUM,
	CALCIUM,
	CARBON,
	COBALT,
	COPPER,
	CHROMIUM,
	GOLD,
	IRIDIUM,
	IRON,
	LEAD,
	LITHIUM,
	MAGNESIUM,
	MANGANESE,
	MOLYBDENUM,//
	NICKEL,
	NIOBIUM,
	OSMIUM,
	PHOSPHORUS,
	PLATINUM,
	POTASSIUM,
	SILICON,
	SILVER,
	SODIUM,
	SULFUR,
	TANTALUM,
	TELLURIUM,
	THORIUM,
	TIN,
	TITANIUM,
	TUNGSTEN,
	VANADIUM,
	ZINC,
	ZIRCONIUM,//

	//SPECIAL
	YELLOWCAKE;

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	private static String formalName(int index) {
		return name(index).substring(0, 1).toUpperCase() + name(index).substring(1);
	}

	//---------ENUM----------------
	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return name(index);
	}

	public static String[] getDusts(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getDust(i);
		}
		return temp;
	}

	public static String getDust(int index){
		return "dust" + formalName(index);
	}
}