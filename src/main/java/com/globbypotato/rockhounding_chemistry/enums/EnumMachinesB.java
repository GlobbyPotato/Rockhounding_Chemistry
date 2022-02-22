package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMachinesB implements BaseEnum {
	SLURRY_POND,
	GAS_PRESSURIZER,
	GASIFIER_TANK,
	GASIFIER_BURNER,
	GASIFIER_COOLER,
	GAS_PURIFIER,
	PURIFIER_CYCLONE_BASE,
	PURIFIER_CYCLONE_TOP,
	PARTICULATE_COLLECTOR,
	PRESSURE_VESSEL,
	AIR_COMPRESSOR,
	HEAT_EXCHANGER_BASE,
	HEAT_EXCHANGER_TOP,
	GAN_TURBOEXPANDER_BASE,
	GAN_TURBOEXPANDER_TOP,
	PURIFIER_CYCLONE_CAP;

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