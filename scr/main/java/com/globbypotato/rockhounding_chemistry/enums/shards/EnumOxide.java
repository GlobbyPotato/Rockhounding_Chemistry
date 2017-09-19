package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumOxide implements BaseEnum{
	CHROMITE(479),
	COCHROMITE(522),
	COLUMBITE(630),
	EUXENITE(484),
	MCCONNELLITE(549),
	SAMARSKITE(569),
	CASSITERITE(690),
	BOEHMITE(303),
	GAHNITE(430),
	HIBONITE(384),
	SENAITE(530),
	THORUTITE(582),
	IXIOLITE(708),
	TAPIOLITE(782),
	BEHOITE(192),
	BROMELLITE(301),
	TUNGSTITE(550),
	WOLFRAMITE(730),
	FERBERITE(745),
	MONTEPONITE(814),
	LOPARITE(877),
	BUNSENITE(630),
	LASALITE(238),
	VANOXITE(290),
	RUTILE(425),
	URANINITE(872);

	private int gravity;
	private EnumOxide(int gravity) {
		this.gravity = gravity;
	}

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
	
	public static int[] getGravities(){
		int[] temp = new int[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getGravity(i);
		}
		return temp;
	}
	
	public static int getGravity(int index){
		return values()[index].gravity;
	}

	public int gravity(){
		return this.gravity;
	}

}