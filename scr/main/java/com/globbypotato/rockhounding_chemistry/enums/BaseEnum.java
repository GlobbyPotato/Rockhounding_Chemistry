package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;

public interface BaseEnum extends IStringSerializable{

	@Override
	public default String getName() {
		return toString().toLowerCase();
	}

}