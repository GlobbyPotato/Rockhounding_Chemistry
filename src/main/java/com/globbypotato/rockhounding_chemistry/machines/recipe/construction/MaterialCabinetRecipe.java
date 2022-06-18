package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

public class MaterialCabinetRecipe {

	private String symbol,oredict, name;
	private int amount;
	private boolean extraction;

	public MaterialCabinetRecipe(String symbol, String oredict, String name, int amount, boolean extraction){
		this.symbol = symbol;
		this.oredict = oredict;
		this.name = name;
		this.amount = amount;
		this.extraction = extraction;
	}

	public MaterialCabinetRecipe(String symbol, String oredict, String name){
		this(symbol, oredict, name, 0, false);
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

	public int getAmount(){
		return this.amount;
	}

	public boolean getExtraction(){
		return this.extraction;
	}

}