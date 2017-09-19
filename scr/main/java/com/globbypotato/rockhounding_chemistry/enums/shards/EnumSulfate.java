package com.globbypotato.rockhounding_chemistry.enums.shards;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumSulfate implements BaseEnum{

	ALUNITE(274),
	FEDOTOVITE(320),
	JAROSITE(309),
	GUARINOITE(280),
	BENTORITE(202),
	APLOWITE(233),
	BIEBERITE(190),
	SCHEELITE(601),
	STOLZITE(805),
	LOPEZITE(269),
	CROCOITE(60),
	KAMCHATKITE(348);

	private int gravity;
	private EnumSulfate(int gravity) {
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