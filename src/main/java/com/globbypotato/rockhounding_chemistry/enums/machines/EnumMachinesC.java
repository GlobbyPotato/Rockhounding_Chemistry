package com.globbypotato.rockhounding_chemistry.enums.machines;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumMachinesC implements BaseEnum {
	GAN_CONTROLLER,
	GAS_CONDENSER,
	MULTIVESSEL,
	LEACHING_VAT_CONTROLLER,
	LEACHING_VAT_TANK,
	SPECIMEN_COLLECTOR,
	RETENTION_VAT_CONTROLLER,
	EXTRACTOR_CONTROLLER,
	EXTRACTOR_REACTOR,
	ELEMENTS_CABINET_BASE,
	ELEMENTS_CABINET_TOP,
	EXTRACTOR_GLASSWARE,
	EXTRACTOR_BALANCE,
	REFORMER_CONTROLLER,
	REFORMER_REACTOR,
	EXTRACTOR_STABILIZER;

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