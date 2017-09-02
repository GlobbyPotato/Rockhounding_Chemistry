package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumSaltStages implements BaseEnum {
	EMPTY("Empty"),
	WATER("Virgin Water"),
	STEPA("Brine"),
	STEPB("Precipitation"),
	STEPC("Evaporation"),
	STEPD("Mother Liquor"),
	SALT( "Raw Salt");

	private String stageName;
	EnumSaltStages(String stageName){
		this.stageName = stageName;
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
	
	public static String[] getStageNames(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getStageName(i);
		}
		return temp;
	}
	
	public static String getStageName(int index){
		return values()[index].stageName();
	}

	public String stageName(){
		return this.stageName;
	}
}