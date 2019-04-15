package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumSulfate {

	ALUNITE,//274
	FEDOTOVITE,//320
	JAROSITE,//309
	GUARINOITE,//280 PULP
	BENTORITE,//202
	APLOWITE,//233
	BIEBERITE,//190
	SCHEELITE,//601
	STOLZITE,//805
	KAMCHATKITE,//348 PULP
	CHILUITE,//365 PULP
	POWELLITE,//434
	SEDOVITE;//420 PULP

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumSulfate.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}
}