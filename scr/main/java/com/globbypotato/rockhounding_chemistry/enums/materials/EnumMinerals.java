package com.globbypotato.rockhounding_chemistry.enums.materials;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumMinerals implements BaseEnum {
	ANTIMONATE,
	ARSENATE,
	BORATE,
	CARBONATE,
	CHROMATE,
	HALIDE,
	NATIVE,
	OXIDE,
	PHOSPHATE,
	SILICATE,
	SULFATE,
	SULFIDE,
	VANADATE;

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	private static String formalName(int index) {
		return name(index).substring(0, 1).toUpperCase() + name(index).substring(1);
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

	public static String[] getOredicts(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getOredict(i);
		}
		return temp;
	}

	public static String getOredict(int index){
		return "ore" + formalName(index);
	}

}