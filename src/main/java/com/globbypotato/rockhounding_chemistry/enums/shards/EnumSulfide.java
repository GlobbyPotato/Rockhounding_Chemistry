package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumSulfide {

	ABRAMOVITE,//900 PULP
	AIKINITE,//644
	BALKANITE,//631
	GALENA,//740
	KESTERITE,//456
	PENTLANDITE,//480
	PYRITE,//501
	STANNITE,//440
	VALLERIITE,//311
	SPHALERITE,//405 PULP
	PETRUKITE,//461
	MAWSONITE,//466 PULP
	TUNGSTENITE,//768
	ERLICHMANITE,//959
	MALANITE,//740
	GREENOCKITE,//449
	CERNYITE,//477
	SULVANITE,//400
	PATRONITE,//282
	INAGLYITE,//579
	MELONITE,//7.3
	VAVRINITE,//7.8 PULP
	MASLOVITE,//11 PULP
	JORDISITE;//4.97 PULP

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return EnumSulfide.values()[index].toString().toLowerCase();
	}

	public static int size(){
		return values().length;
	}

}