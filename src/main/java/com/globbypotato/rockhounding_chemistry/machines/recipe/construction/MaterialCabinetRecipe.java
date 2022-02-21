package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

public class MaterialCabinetRecipe {

	private String symbol,oredict, name;

	public MaterialCabinetRecipe(String symbol, String oredict, String name){
		this.symbol = symbol;
		this.oredict = oredict;
		this.name = name;
	}

	public String getSymbol(){
		return this.symbol;
	}

	public String getOredict(){
		return this.oredict;
	}

	public String getName(){
		return this.name;
	}
}