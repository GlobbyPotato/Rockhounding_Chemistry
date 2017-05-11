package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;
public enum EnumItems implements IStringSerializable {
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
	INDUCTOR,
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
	OWC_CASING,
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
	BOUNDARY_CASING;

	@Override
	public String getName() {
		return toString().toLowerCase();
	}

	public static int size(){
		return values().length;
	}

	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i=0;i<size();i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return EnumItems.values()[index].getName();
	}

}