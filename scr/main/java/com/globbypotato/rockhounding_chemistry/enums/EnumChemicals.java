package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumChemicals implements BaseEnum {
	ACRYLIC_COMPOST,
	SALT,
	SULFUR_COMPOST,
	SALT_COMPOST,
	FLUORITE_COMPOST,
	CARBON_COMPOST,
	CRACKED_COAL,
	RAW_SALT,
	SILICON_COMPOST,
	FERROUS_CATALYST,
	PLATINUM_CATALYST,
	ASH_COMPOST,
	POTASSIUM_COMPOST,
	POTASSIUM_NITRATE,
	CRACKED_CHARCOAL,
	RUTILE_COMPOST;

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