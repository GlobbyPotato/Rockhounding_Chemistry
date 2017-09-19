package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumSulfide implements BaseEnum{

	ABRAMOVITE(900),
	AIKINITE(644),
	BALKANITE(631),
	GALENA(740),
	KESTERITE(456),
	PENTLANDITE(480),
	PYRITE(501),
	STANNITE(440),
	VALLERIITE(311),
	SPHALERITE(405),
	PETRUKITE(461),
	MAWSONITE(466),
	TUNGSTENITE(740),
	ERLICHMANITE(959),
	MALANITE(740),
	GREENOCKITE(449),
	CERNYITE(477),
	SULVANITE(400),
	PATRONITE(282);

	private int gravity;
	private EnumSulfide(int gravity) {
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