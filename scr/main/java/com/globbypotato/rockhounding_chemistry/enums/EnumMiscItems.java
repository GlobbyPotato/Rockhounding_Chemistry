package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMiscItems implements BaseEnum {
	SERVER_FILE,
	BASE_CHIP,
	IRON_ROD,
	IRON_FOIL,
	IRON_ARM,
	IRON_CASING,
	BLENDING_UNIT,
	GLASS_PIPE,
	IRON_COIL,
	COMPRESSOR,
	ADVANCED_CHIP,
	ITEM_FILTER,
	CABINET,
	HEATER,
	NICHROME_ROD,
	DEPOSITION_CASING,
	DEPOSITION_INSULATION,
	HYDRONALIUM_CASING,
	HASTELLOY_CASING,
	COPPER_COIL,
	TURBINE_FAN,
	ROTOR,
	STATOR,
	DIDYMIUM_PANEL,
	COPPER_ROD,
	NIB_ROD,
	COSM_ROD,
	HASTELLOY_ROD,
	NIMONIC_COIL,
	NIMONIC_CASING,
	IRON_IMPELLER,
	ALUMINUM_IMPELLER,
	STELLITE_IMPELLER,
	ALUMINUM_FOIL,
	STELLITE_FOIL,
	REFRACTORY_CASING,
	FLYASH_BALL,
	FLYASH_BRICK;

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