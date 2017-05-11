package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumCrawler {

	TIERS("Tier", "", "", "", "Tier"),
	MODES("Mode", "", "", "", "Mode"),
	STEP("Step", "", "", "", "Step"),
	UPGRADE("Upgrade", "", "", "", "Upgrades"),
	ACTIVATOR("Activator", "", "", "", ""),

	FILLER("Filler", "", "", "", "Filler"),
	ABSORBER("Absorber", "", "", "", "Absorber"),
	TUNNELER("Tunneler", "", "", "", "Tunneler"),
	LIGHTER("Lighter", "", "", "", "Torcher"),
	RAILMAKER("RailMaker", "", "", "", "Rail Maker"),
	DECORATOR("Decorator", "", "", "", "Decorator"),
	PATHFINDER("Pathfinder", "", "", "", "Pathfinder"),
	STORAGE("Storage", "", "", "", "Storage"),
	
	FILLER_BLOCK("FillerBlock", "FillerBlockName", "FillerBlockMeta", "FillerBlockSize", "Filling Block"),
	ABSORBER_BLOCK("AbsorbBlock", "AbsorbBlockName", "AbsorbBlockMeta", "AbsorbBlockSize", "Absorbing Block"),
	LIGHTER_BLOCK("LighterBlock", "LighterBlockName", "LighterBlockMeta", "LighterBlockSize", "Lighting Block"),
	RAILMAKER_BLOCK("RailMakerBlock", "RailMakerBlockName", "RailMakerBlockMeta", "RailMakerBlockSize", "Railing Block"),
	DECORATOR_BLOCK("DecoratorBlock", "DecoratorBlockName", "DecoratorBlockMeta", "DecoratorBlockSize", "Decorating Block");

	private String setupName;
	private String blockName;
	private String blockMeta;
	private String blockSize;
	private String screenName;

	EnumCrawler(String name, String blockName, String blockMeta, String blockSize, String screenName){
		this.setupName = name;
		this.screenName = screenName;
		this.blockName = blockName;
		this.blockMeta = blockMeta;
		this.blockSize = blockSize;
	}

	public String getName(){
		return this.setupName;
	}

	public String getBlockName(){
		return this.blockName;
	}

	public String getBlockMeta(){
		return this.blockMeta;
	}

	public String getBlockStacksize(){
		return this.blockSize;
	}

	public String getScreenName(){
		return this.screenName;
	}

}