package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumNative {
	COHENITE,
	COPPER,
	CUPALITE,
	HAXONITE,
	PERRYITE,
	SILVER,
	TULAMEENITE,
	NIGGLIITE,
	MALDONITE,
	AURICUPRITE,
	OSMIUM,
	IRIDIUM,
	KHATYRKITE,
	NICKEL,
	FULLERITE,
	CHAOITE,
	GRAPHITE,
	PLATINUM;

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumNative.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}