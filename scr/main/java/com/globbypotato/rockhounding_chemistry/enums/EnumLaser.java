package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumLaser implements IStringSerializable {
	OFFPIN,
	ONPIN,
	OFFPROBE,
	ONPROBE,
	PINTX,
	PINRX,
	PROBETX,
	PROBERX;

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
		return EnumLaser.values()[index].getName();
	}

}