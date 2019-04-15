package com.globbypotato.rockhounding_chemistry.items.io;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseItem;

public class UtilIO extends BaseItem {
	public UtilIO(String name){
		super(Reference.MODID, name);
		setCreativeTab(Reference.RockhoundingChemistry);	
		setMaxStackSize(1);
	}
}