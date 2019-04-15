package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMachinesE implements BaseEnum {
	LASER_EMITTER,
	EXHAUSTION_VALVE,
	WATER_PUMP,
	CATALYST_REGEN,
	DISPOSER,
	SLURRY_DRUM,
	BUFFER_TANK,
	STIRRED_TANK_BASE,
	STIRRED_TANK_TOP,
	STIRRED_TANK_OUT,
	PRECIPITATION_CONTROLLER,
	PRECIPITATION_CHAMBER,
	PRECIPITATION_REACTOR;

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