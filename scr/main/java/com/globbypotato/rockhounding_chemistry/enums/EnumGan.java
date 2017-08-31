package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumGan implements BaseEnum {
	VESSEL_I(1.0F),
	CHILLER_I(1.0F),
	CONDENSER_I(1.0F),
	TURBINE_I(1.0F),
	TANK_I(1.0F),
	TOWER_I(1.0F),
	VESSEL_II(1.0F),
	CHILLER_II(1.0F),
	CONDENSER_II(1.0F),
	TURBINE_II(1.0F),
	TANK_II(1.0F),
	TOWER_II(1.0F),
	VESSEL_TOP(1.0F),
	CHILLER_TOP(1.0F),
	TURBINE_TOP(1.0F),
	TOWER_TOP(1.0F);

	private float height;
	EnumGan(float height){
		this.height = height;
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

	public static float[] getHeights(){
		float[] temp = new float[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getHeight(i);
		}
		return temp;
	}

	public static float getHeight(int index){
		return values()[index].height;
	}

	public float getHeight(){
		return this.height;
	}
}