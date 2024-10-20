package com.globbypotato.rockhounding_chemistry.enums.utils;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumStructure implements BaseEnum {
	BLENDER("Lab Blender", 0, 0, 2, 2),
	MIXER("Powder Mixer", 2, 2, 3, 2),
	OVEN("Lab Oven", 3, 3, 2, 4),
	PRECIPITATOR("Precipitation Chamber", 1, 0, 2, 5),
	LEACHING("Leaching Vat", 1, 2, 2, 6),
	RETENTION("Retention Vat", 1, 2, 2, 4),
	EXTRACTOR("Chemical Extractor", 2, 2, 2, 8),
	ALLOYER("Metal Alloyer", 2, 2, 2, 5),
	GASIFIER("Gasification Plant", 2, 2, 3, 2),
	PURIFIER("Gas Purifier", 2, 2, 3, 4),
	SIZER("Mineral Sizer", 1, 0, 2, 9),
	SHREDDER("Shaking Table Separator", 2, 2, 2, 7),
	EXCHANGER("Heat Exchanger", 2, 2, 4, 2),
	CSTR("Electrochemical CSTR", 3, 2, 3, 2),
	DEPOSITION("Deposition Chamber", 2, 2, 2, 6),
	REFORMER("Gas Reforming Tower", 2, 2, 5, 7),
	GAN("Cryogenic Distillation", 2, 1, 6, 7),
	TUBULAR("Fixed Bed Reactor", 3, 1, 5, 4),
	PULLING("Crystal Pulling Crucible", 2, 2, 3, 2),
	CONDENSER("Gas Condenser", 0, 0, 2, 4),
	EXPANDER("Gas Expansion Chamber", 0, 0, 2, 4),
	PROFILING("Profiling Bench", 0, 0, 1, 2),
	POND("Slurry Pond", 0, 0, 2, 2),
	COMPRESSOR("Air Compressor", 0, 0, 1, 2),
	REGEN("Catalyst Renegerator", 1, 0, 2, 3),
	ORBITER("Orbiter", 0, 0, 2, 2);

	private String name;
	private int xLeft, xRight, yUp, zFront;
	EnumStructure(String name, int xLeft, int xRight, int yUp, int zFront){
		this.name = name;
		this.xLeft = xLeft;
		this.xRight = xRight;
		this.yUp = yUp;
		this.zFront = zFront;
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

	public static int getLeftBlocks(int index){
		return values()[index].xLeft;
	}

	public static int getRightBlocks(int index){
		return values()[index].xRight;
	}

	public static int getUpBlocks(int index){
		return values()[index].yUp;
	}

	public static int getFrontBlocks(int index){
		return values()[index].zFront;
	}

}