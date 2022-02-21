package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMiscBlocksA implements BaseEnum {
	SCAFFOLD,
	PRESSER,
	RAW_SALT,
	SEPARATOR,
	EXTRACTOR_CHARGER,
	GAN_TOWER,
	GAN_TOWER_TOP,
	GAN_INJECTOR,
	REFORMER_TOWER,
	REFORMER_TOWER_TOP,
	PULLING_CRUCIBLE_CAP,
	CHARCOAL_BLOCK,
	POOR_RAW_SALT,
	REINFORCED_GLASS,
	GAS_ROUTER;

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	//---------ENUM----------------
	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return name(index);
	}

}