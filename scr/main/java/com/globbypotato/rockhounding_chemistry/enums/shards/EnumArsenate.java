package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumArsenate {

	AGARDITE,//3.72 PULP
	SCHULTENITE,//5.94 PULP
	PITTICITE,//2.5
	ZALESIITE,//3.49 PULP
	MIXITE;//3.8 PULP

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumArsenate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}