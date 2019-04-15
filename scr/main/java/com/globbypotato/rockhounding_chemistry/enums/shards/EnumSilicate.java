package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumSilicate {

	AXINITE,//382
	BIOTITE,//309
	GADOLINITE,//420 PULP
	JERVISITE,//322 PULP
	MAGBASITE,//341
	MOSKVINITE,//291 PULP
	EUCRYPTITE,//267
	STEACYITE,//295
	MANANDONITE,//289
	VISTEPITE,//367
	KHRISTOVITE,//408 PULP
	CAVANSITE,//225
	PHENAKITE,//298
	CALDERITE,//408
	HUTTONITE,//7.1
	ZIRCON,//465
	KELDYSHITE;//3.3 PULP

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