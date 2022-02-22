package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumVanadate {
	LASALITE,//238
	VANOXITE,//290
	DUGGANITE,//6.3 PULP
	ALVANITE;//245

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumVanadate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}