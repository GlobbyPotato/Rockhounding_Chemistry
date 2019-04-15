package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumAntimonate {

	BAHIANITE,//517
	PARTZITE,//595 PULP
	TRIPUHYITE,//582
	PARWELITE,//462
	CAMEROLAITE,//310 PULP
	ORDONEZITE;//663

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumAntimonate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}