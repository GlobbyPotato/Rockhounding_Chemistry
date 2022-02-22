package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMetalItems implements BaseEnum{
	VANADIUM_INGOT("vanadium"),
	ZIRCONIUM_INGOT("zirconium"),
	OSMIUM_INGOT("osmium"),
	ALUMINUM_INGOT("aluminum"),
	TITANIUM_INGOT("titanium"),
	LEAD_INGOT("lead"),
	PLATINUM_INGOT("platinum"),
	GRAPHITE_INGOT("graphite"),
	ZEOLITE_INGOT("zeolite"),
	ZINC_INGOT("zinc"),
	COBALT_INGOT("cobalt"),
	COPPER_INGOT("copper"),
	MOLYBDENUM_INGOT("molybdenum"),
	NICKEL_INGOT("nickel");

	public String metalName;
	EnumMetalItems(String metalName){
		this.metalName = metalName;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	public static String metal(int index) {
		return values()[index].getMetal();
	}

	private static String formalName(int index) {
		return metal(index).substring(0, 1).toUpperCase() + metal(index).substring(1);
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

	public String getMetal(){
		return this.metalName;
	}

	public static String[] getOredicts(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getOredict(i);
		}
		return temp;
	}

	public static String getOredict(int index){
		return "ingot" + formalName(index);
	}

	public static String[] getSmeltDicts(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getSmeltDict(i);
		}
		return temp;
	}

	public static String getSmeltDict(int index){
		return "dust" + formalName(index);
	}

}