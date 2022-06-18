package com.globbypotato.rockhounding_chemistry.enums.utils;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumStructure implements BaseEnum {
	OVEN("Lab Oven"),
	EXTRACTOR("Chemical Extractor"),
	PRECIPITATOR("Precipitation Chamber"),
	LEACHING("Leaching Vat"),
	RETENTION("Retention Vat"),
	CONDENSER("Gas Condenser"),
	EXPANDER("Gas Expansion Chamber"),
	BLENDER("Lab Blender"),
	MIXER("Powder Mixer"),
	COMPRESSOR("Air Compressor"),
	PROFILING("Profiling Bench"),
	POND("Slurry Pond"),
	ALLOYER("Metal Alloyer"),
	ORBITER("Orbiter"),
	GASIFIER("Gasification Plant"),
	SIZER("Mineral Sizer"),
	EXCHANGER("Heat Exchanger"),
	CSTR("Electrochemical CSTR"),
	PULLING("Crystal Pulling Crucible"),
	SHREDDER("Shaking Table Separator"),
	DEPOSITION("Vapor Deposition Chamber"),
	PURIFIER("Gas Purifier"),
	REFORMER("Gas Reforming Tower"),
	GAN("Cryogenic Distillation"),
	TUBULAR("Fixed Bed Reactor"),
	REGEN("Catalyst Renegerator");

	private String name;
	EnumStructure(String name){
		this.name = name;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	//---------ENUM----------------
	public static String[] getStructures(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getStructure(i);
		}
		return temp;
	}
	
	public static String getStructure(int index){
		return values()[index].name;
	}

}