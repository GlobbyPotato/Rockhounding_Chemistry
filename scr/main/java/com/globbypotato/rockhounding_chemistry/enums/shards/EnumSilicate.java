package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumSilicate implements BaseEnum{

	AXINITE(328),
	BIOTITE(309),
	GADOLINITE(420),
	IRANITE(580),
	JERVISITE(322),
	MAGBASITE(341),
	MOSKVINITE(291),
	EUCRYPTITE(267),
	STEACYITE(295),
	MANANDONITE(289),
	VISTEPITE(367),
	KHRISTOVITE(408),
	CAVANSITE(225);

	private int gravity;
	private EnumSilicate(int gravity) {
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