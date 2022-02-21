package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumPhosphate {
	FAUSTITE,//292
	LAZULITE,//305
	MONAZITE,//515 PULP
	SCHOONERITE,//289
	TRIPHYLITE,//350
	WAVELLITE,//234
	XENOTIME,//475
	ZAIRITE,//437
	PRETULITE,//371
	TAVORITE,//328
	KEYITE,//495
	BIRCHITE,//361
	ZIESITE,//386
	SCHODERITE,//188
	KOSNARITE;//3.19 PULP

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumPhosphate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}