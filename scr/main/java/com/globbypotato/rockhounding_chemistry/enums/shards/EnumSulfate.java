package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumSulfate {

	ALUNITE,
	FEDOTOVITE,
	JAROSITE,
	GUARINOITE,
	BENTORITE,
	APLOWITE,
	BIEBERITE,
	SCHEELITE,
	STOLZITE,
	LOPEZITE,
	CROCOITE,
	KAMCHATKITE;

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumSulfate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}
}