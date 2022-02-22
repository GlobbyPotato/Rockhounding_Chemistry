package com.globbypotato.rockhounding_chemistry.items.io;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseArray;

public class ArrayIO extends BaseArray{

	public ArrayIO(String name, String[] array) {
		super(Reference.MODID, name, array);
		setCreativeTab(Reference.RockhoundingChemistry);
	}

}