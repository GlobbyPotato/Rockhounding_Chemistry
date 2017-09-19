package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumNative implements BaseEnum{
	COHENITE(742),
	COPPER(894),
	CUPALITE(512),
	HAXONITE(770),
	PERRYITE(788),
	SILVER(1050),
	TULAMEENITE(1490),
	NIGGLIITE(1344),
	MALDONITE(1546),
	AURICUPRIDE(1150),
	OSMIUM(2000),
	IRIDIUM(2270),
	KHATYRKITE(442),
	NICKEL(800),
	FULLERITE(195),
	CHAOITE(338),
	GRAPHITE(216),
	PLATINUM(1800);

	private int gravity;
	private EnumNative(int gravity) {
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