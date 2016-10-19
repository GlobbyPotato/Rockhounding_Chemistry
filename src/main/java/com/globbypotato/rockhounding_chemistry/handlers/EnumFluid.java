package com.globbypotato.rockhounding_chemistry.handlers;

public enum EnumFluid {

	EMPTY("Empty"),
	WATER("Water"),
	SULFURIC_ACID("Sulfuric Acid"),
	HYDROCHLORIC_ACID("Hydrochloric Acid"),
	HYDROFLUORIC_ACID("Hydrofluoric Acid"),
	SYNGAS("Syngas");
	
	
	private String name;
	
	EnumFluid(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}