package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumNative {
	COHENITE,//742 PULP
	CUPALITE,//512
	HAXONITE,//770
	PERRYITE,//788 PULP
	TULAMEENITE,//1490
	NIGGLIITE,//1344
	MALDONITE,//1546
	AURICUPRIDE,//1150
	KHATYRKITE,//442
	FULLERITE,//195
	CHAOITE,//338
	GRAPHITE,//216
	OSMIRIDIUM;//2200

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