package com.globbypotato.rockhounding_chemistry.handlers.Enums;

public enum EnumSetups {

	TIERS("Tier"),
	MODES("Mode"),
	STEP("Step"),
	UPGRADE("Upgrade"),
	
	FILLER("Filler"),
	ABSORBER("Absorber"),
	TUNNELER("Tunneler"),
	LIGHTER("Lighter"),
	RAILMAKER("RailMaker"),
	
	COBBLESTONE("Cobblestone"),
	GLASS("Glass"),
	TORCHES("Torch"),
	RAILS("Rails");
	
	
	private String name;
	
	EnumSetups(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
}