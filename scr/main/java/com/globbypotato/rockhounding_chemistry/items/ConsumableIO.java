package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseConsumable;

public class ConsumableIO extends BaseConsumable{

	public ConsumableIO(String name, int uses) {
		super(name, uses);
		setCreativeTab(Reference.RockhoundingChemistry);
	}
}