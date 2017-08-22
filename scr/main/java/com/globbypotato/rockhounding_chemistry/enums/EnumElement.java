package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumElement implements BaseEnum{
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
	SAMARIUM,//10
	SCANDIUM,
	TERBIUM,
	THULIUM,
	YTTERBIUM,
	YTTRIUM,
	IRON,
	COPPER,
	TIN,
	LEAD,
	ZINC,//20
	CHROMIUM,
	BORON,
	SILVER,
	ALUMINUM,
	MANGANESE,
	NICKEL,
	COBALT,
	MAGNESIUM,
	TITANIUM,
	SODIUM,//30
	THORIUM,
	CALCIUM,
	PHOSPHORUS,
	LITHIUM,
	POTASSIUM,
	BERYLLIUM,
	SULFUR,
	BISMUTH,
	NIOBIUM,
	TANTALUM,//40
	ARSENIC,
	SILICON,
	URANIUM,
	PLATINUM,
	GOLD,
	TUNGSTEN,
	OSMIUM,
	IRIDIUM,
	CADMIUM,
	VANADIUM,//50
	CARBON;

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

	public static String[] getDust(){
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