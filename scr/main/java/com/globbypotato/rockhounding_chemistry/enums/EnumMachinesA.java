package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMachinesA implements BaseEnum {
	SIZER_CONTROLLER,
	SIZER_TANK,
	POWER_GENERATOR,
	SIZER_COLLECTOR,
	FLUID_TANK,
	LAB_OVEN_CONTROLLER,
	LAB_OVEN_CHAMBER,
	FLUID_INPUT_TANK,
	FLUID_OUTPUT_TANK,
	LAB_BLENDER_CONTROLLER,
	LAB_BLENDER_TANK,
	PROFILING_BENCH,
	EVAPORATION_TANK,
	SEASONING_RACK,
	SERVER,
	GAS_EXPANDER;

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