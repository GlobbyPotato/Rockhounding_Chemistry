package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumOxide implements BaseEnum{
	CHROMITE,
	COCHROMITE,
	COLUMBITE,
	EUXENITE,
	MCCONNELLITE,
	SAMARSKITE,
	CASSITERITE,
	BOEHMITE,
	GHANITE,
	HIBONITE,
	SENAITE,
	THORUTITE,
	IXIOLITE,
	TAPIOLITE,
	BEHOITE,
	BROMELLITE,
	TUNGSTITE,
	WOLFRAMITE,
	FERBERITE,
	MONTEPONITE,
	LOPARITE,
	BUNSENITE,
	LASALITE,
	VANOXITE,
	RUTILE,
	URANINITE;

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