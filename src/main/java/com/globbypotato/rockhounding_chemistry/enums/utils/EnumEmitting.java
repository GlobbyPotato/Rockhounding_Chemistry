package com.globbypotato.rockhounding_chemistry.enums.utils;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumEmitting implements BaseEnum {
	DISABLED("Disabled"),
	LEVEL("By Redstone Level"),
	ON("High Past Threashold"),
	OFF("Low Past Threashold");

	public String formal;
	EnumEmitting(String formal){
		this.formal = formal;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	//---------ENUM----------------
	public static String[] getFormals(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = formalName(i);
		}
		return temp;
	}

	private static String formalName(int index) {
		return values()[index].formal;
	}


}