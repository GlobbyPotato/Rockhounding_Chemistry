package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumSilicate {

	AXINITE,
	BIOTITE,
	GADOLINITE,
	IRANITE,
	JERVISITE,
	MAGBASITE,
	MOSKVINITE,
	EUCRYPTITE,
	STEACYITE,
	MANANDONITE,
	VISTEPITE,
	KHRISTOVITE,
	CAVANSITE;

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumSilicate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}