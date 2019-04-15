package com.globbypotato.rockhounding_chemistry.items.io;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_core.items.BaseContainer;

public class ContainerIO extends BaseContainer{

	public ContainerIO(String name) {
		super(Reference.MODID, name);
		setCreativeTab(Reference.RockhoundingChemistry);
	}
}