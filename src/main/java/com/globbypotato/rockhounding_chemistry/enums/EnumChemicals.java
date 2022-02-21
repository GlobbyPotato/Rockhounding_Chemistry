package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumChemicals implements BaseEnum {
	CRACKED_COAL,
	FLYASH_COMPOUND,
	SULFUR_COMPOUND,
	SALT,
	FLUORITE,
	FLUORITE_COMPOUND,
	CHLORIDE_COMPOUND,
	COAL_TAR_COMPOUND,
	GRAPHITE_COMPOUND,
	ZEOLITE_COMPOUND,
	ZEOLITE_PELLET,
	SILICON_COMPOUND,
	POTASSIUM_NITRATE,
	CRACKED_CHARCOAL,
	POTASSIUM_CARBONATE,
	WIDIA_COMPOUND,
	YAG_COMPOUND,
	PURE_YAG_COMPOUND,
	SAND_COMPOUND,
	COKE_COMPOUND,
	CRACKED_LIME,
	AMMONIUM_CHLORIDE;

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
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

}