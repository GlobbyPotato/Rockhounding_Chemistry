package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.ArrayIO;

public class FilterItems extends ArrayIO{

	public FilterItems(String name, String[] array) {
		super(name, array);
		setMaxStackSize(1);
	}
}