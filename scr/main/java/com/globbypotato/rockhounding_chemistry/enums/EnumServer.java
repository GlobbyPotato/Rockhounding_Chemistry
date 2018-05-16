package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumServer implements BaseEnum {
	LAB_OVEN("Lab Oven");

	private String name;

	EnumServer(String name){
		this.name = name;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	//---------ENUM----------------
	@Override
	public String getName(){
		return this.name;
	}


}