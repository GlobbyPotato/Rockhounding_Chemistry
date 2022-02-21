package com.globbypotato.rockhounding_chemistry.enums.shards;

public enum EnumOxide {
	CHROMITE,//479
	COCHROMITE,//522 PULP
	COLUMBITE,//630
	EUXENITE,//484 PULP
	MCCONNELLITE,//549 PULP
	SAMARSKITE,//569
	CASSITERITE,//690
	BOEHMITE,//303
	GAHNITE,//430
	HIBONITE,//384 PULP
	SENAITE,//530
	THORUTITE,//582
	IXIOLITE,//708
	TAPIOLITE,//782 PULP
	BEHOITE,//192
	BROMELLITE,//301
	TUNGSTITE,//550
	WOLFRAMITE,//730
	FERBERITE,//745
	MONTEPONITE,//814 PULP
	LOPARITE,//877
	BUNSENITE,//630
	RUTILE,//425
	URANINITE,//872
	ZIRKELITE,//4.7
	BIEHLITE,//524 PULP
	KAMIOKITE,//596
	MOURITE;//4.23 PULP

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}
	
	public static String getName(int index){
		return EnumOxide.values()[index].toString().toLowerCase();
	}
	
	public static int size(){
		return values().length;
	}

}