package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumOres implements IStringSerializable {
	UNINSPECTED,
	ARSENATE,
	BORATE,
	CARBONATE,
	HALIDE,
	NATIVE,
	OXIDE,
	PHOSPHATE,
	SILICATE,
	SULFATE,
	SULFIDE;

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
		return EnumOres.values()[index].getName();
	}

}