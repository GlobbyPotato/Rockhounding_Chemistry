package com.globbypotato.rockhounding_chemistry.enums.machines;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumMachinesF implements BaseEnum {
	TUBULAR_BED_BASE,
	TUBULAR_BED_LOW,
	TUBULAR_BED_MID,
	TUBULAR_BED_TOP,
	TUBULAR_BED_TANK,
	TUBULAR_BED_CONTROLLER;

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