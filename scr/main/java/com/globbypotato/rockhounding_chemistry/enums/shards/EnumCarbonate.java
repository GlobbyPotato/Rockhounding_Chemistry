package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumCarbonate implements BaseEnum{
	ANKERITE(305),
	GASPEITE(371),
	ROSASITE(409),
	PARISITE(436),
	OTAVITE(503),
	SMITHSONITE(445),
	HUNTITE(269);

	private int gravity;
	private EnumCarbonate(int gravity) {
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