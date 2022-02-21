package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumChromate {
	LOPEZITE,//269
	CROCOITE,//60
	CHROMATITE,//3.142
	IRANITE,//580
	FORNACITE,//6.27 PULP
	MACQUARTITE;//549 PULP

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumChromate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}