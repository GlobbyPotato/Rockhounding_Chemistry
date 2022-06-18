package com.globbypotato.rockhounding_chemistry.enums.utils;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumServer implements BaseEnum {
	LAB_OVEN("Lab Oven", false),
	METAL_ALLOYER("Metal Alloyer", false),
	DEPOSITION("Deposition Chamber", false),
	SIZER("Mineral Sizer", true),
	LEACHING("Leaching Vat", true),
	RETENTION("Retention Vat", true),
	CASTING("Profiling Bench", true),
	REFORMER("Gas Reforming Reactor", false),
	EXTRACTOR("Chemical Extractor", true),
	PRECIPITATOR("Precipitation Chamber", false),
	BED_REACTOR("Fixed Bed Reactor", false),
	POWDER_MIXER("Powder Mixer", false);

	private String name;
	private boolean filter;

	EnumServer(String name, boolean filter){
		this.name = name;
		this.filter = filter;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	//---------ENUM----------------
	@Override
	public String getName(){
		return this.name;
	}

	public boolean getFilter(){
		return this.filter;
	}


}