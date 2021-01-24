package com.globbypotato.rockhounding_chemistry.enums.materials;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumAirGases implements BaseEnum{
	//RARE
	ARGON,
	CARBON_DIOXIDE,
	NEON,
	HELIUM,
	KRYPTON,
	XENON,
	NITROGEN,
	OXYGEN;

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName().toLowerCase();
	}

	//---------ENUM----------------
	public String[] getNames(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public String getName(int index){
		return name(index).toLowerCase();
	}

}