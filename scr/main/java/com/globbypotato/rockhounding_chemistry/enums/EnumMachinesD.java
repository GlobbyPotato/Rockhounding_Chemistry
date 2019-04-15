package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumMachinesD implements BaseEnum {
	METAL_ALLOYER,
	METAL_ALLOYER_TANK,
	MATERIAL_CABINET_BASE,
	MATERIAL_CABINET_TOP,
	DEPOSITION_CHAMBER_BASE,
	DEPOSITION_CHAMBER_TOP,
	GAS_HOLDER_BASE,
	GAS_HOLDER_TOP,
	PULLING_CRUCIBLE_BASE,
	PULLING_CRUCIBLE_TOP,
	ORBITER,
	TRANSPOSER,
	FLUIDPEDIA,
	WASTE_DUMPER,
	FLOTATION_TANK,
	CONTAINMENT_TANK;

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