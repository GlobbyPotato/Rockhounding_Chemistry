package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumBattery implements BaseEnum {
	LOW(500000),
	MEDIUM(2000000),
	HIGH(8000000),
	HUGE(32000000);

	private int capacity;
	EnumBattery(int capacity){
		this.capacity = capacity;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	public static String formalName(int index) {
		return name(index).substring(0, 1).toUpperCase() + name(index).substring(1);
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

	public static String[] getFormalNames(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getFormalName(i);
		}
		return temp;
	}

	public static String getFormalName(int index){
		return formalName(index);
	}

	public int getCapacity(){
		return this.capacity;
	}
}