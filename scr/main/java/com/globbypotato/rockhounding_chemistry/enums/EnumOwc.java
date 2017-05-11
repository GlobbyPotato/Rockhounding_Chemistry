package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumOwc implements IStringSerializable {
	BULKHEAD,
	CONCRETE,
	DUCT,
	TURBINE,
	VALVE,
	GENERATOR,
	STORAGE,
	INVERTER,
	DEFLECTOR_OFF,
	DEFLECTOR_ON;

	@Override
	public String getName() {
		return toString().toLowerCase();
	}

	public static int size(){
		return values().length;
	}

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return EnumOwc.values()[index].getName();
	}

}