package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumSaltStages implements BaseEnum {
	STAGE_A("Water", 5000),
	STAGE_B("Virgin Water", 5000),
	STAGE_C("Brine", 4000),
	STAGE_D("Precipitation", 3000),
	STAGE_E("Evaporation", 2000),
	STAGE_F("Mother Liquor", 1000),
	STAGE_G("Raw Salt", 1000);

	private int yeld;
	private String stageName;
	EnumSaltStages(String stageName, int yeld){
		this.stageName = stageName;
		this.yeld = yeld;
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

	public static int[] getYelds(){
		int[] temp = new int[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getStageYeld(i);
		}
		return temp;
	}
	
	public static int getStageYeld(int index){
		return values()[index].yeld();
	}

	public int yeld(){
		return this.yeld;
	}

}