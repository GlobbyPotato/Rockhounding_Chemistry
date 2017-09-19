package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumHalide implements BaseEnum{
	BOLEITE(494),
	CARNALLITE(160),
	RINNEITE(230),
	GRICEITE(262),
	FLUORITE(313),
	HEKLAITE(269),
	CREEDITE(271);

	private int gravity;
	private EnumHalide(int gravity) {
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