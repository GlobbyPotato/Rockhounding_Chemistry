package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumItems implements BaseEnum {
	LOGIC_CHIP,
	CABINET,
	IRON_NUGGET,
	BOW_BARREL,
	BOW_WHEEL,
	FLUID_CAN,
	MEMORY_CARD,
	ADVANCED_CHIP,
	SETUP_CARD,
	CRAWLER_CASING,
	CRAWLER_HEAD,
	CRAWLER_ARM,
	WRENCH,
	HASTELLOY_FOIL,
	YAG_ROD,
	RESONATOR,
	HEATER,
	PLATINUM_INGOT,
	CUPRONICKEL_FOIL,
	COPPER_COIL,
	STATOR,
	ROTOR,
	ARM,
	FAN,
	NIMONIC_FOIL,
	COPPER_INGOT,
	ENERGY_CELL,
	LEAD_INGOT,
	NIMONIC_CASING,
	NICHROME_ROD,
	CUNIFE_WIRE,
	CATHODE,
	CATHODE_SET,
	HASTELLOY_CASING,
	CHAMBER_UPGRADE,
	INSULATION_UPGRADE,
	TITANIUM_INGOT,
	BOUNDARY_HEAD,
	SIENA_BEARING,
	TINITE_ARM,
	BOUNDARY_CHIP,
	BOUNDARY_CASING,
	IRON_CASING,
	COMPRESSOR,
	SPIRAL,
	CUPRONICKEL_CASING,
	ALUMINUM_INGOT,
	ALUMINUM_NUGGET,
	ALUMINUM_CASING;

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