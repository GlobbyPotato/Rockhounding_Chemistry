package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumOxide {
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

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumOxide.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}