package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumPhosphate implements BaseEnum{
	FAUSTITE(292),
	LAZULITE(305),
	MONAZITE(515),
	SCHOONERITE(289),
	TRIPHYLITE(350),
	WAVELLITE(234),
	XENOTIME(475),
	ZAIRITE(437),
	PRETULITE(371),
	TAVORITE(328),
	KEYITE(495),
	BIRCHITE(361),
	ZIESITE(386),
	SCHODERITE(188);

	private int gravity;
	private EnumPhosphate(int gravity) {
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