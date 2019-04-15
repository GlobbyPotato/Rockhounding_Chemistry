package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumCarbonate {
	ANKERITE, //305 PULP
	GASPEITE,//371
	ROSASITE,//409
	PARISITE,//436 PULP
	OTAVITE,//503 PULP
	SMITHSONITE,//445
	HUNTITE; //269

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumCarbonate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}