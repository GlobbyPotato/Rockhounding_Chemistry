package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumFluid {

	SULFURIC_ACID("Sulfuric Acid", "sulfuric_acid"),
	HYDROCHLORIC_ACID("Hydrochloric Acid", "hydrochloric_acid"),
	HYDROFLUORIC_ACID("Hydrofluoric Acid", "hydrofluoric_acid"),
	SYNGAS("Syngas", "syngas"),
	SILICONE("Silicone", "silicone"),
	CHLOROMETHANE("Chloromethane", "chloromethane"),
	ACRYLIC_ACID("Acrylic Acid", "acrylic_acid"),
	AMMONIA("Ammonia", "ammonia"),
	NITRIC_ACID("Nitric Acid", "nitric_acid"),
	TITANIUM_TETRACHLORIDE("Titanium Tetrachloride", "titanium_tetrachloride"),
	SODIUM_CYANIDE("Sodium Cyanide", "sodium_cyanide"),
	PHOSPHORIC_ACID("Phosphoric Acid", "phosphoric_acid");

	private String name;
	private String fluidName;

	EnumFluid(String name, String fluidName){
		this.name = name;
		this.fluidName = fluidName;
	}

	public String getName(){
		return this.name;
	}

	public String getFluidName(){
		return this.fluidName;
	}

}