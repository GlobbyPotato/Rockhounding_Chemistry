package com.globbypotato.rockhounding_chemistry.enums.materials;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public enum EnumFluid {

//GENERIC
	SULFURIC_ACID("Sulfuric Acid", "sulfuric_acid"),
	SODIUM_HYDROXIDE("Sodium Hydroxide", "sodium_hydroxide"),
	HYDROCHLORIC_ACID("Hydrochloric Acid", "hydrochloric_acid"),
	HYDROFLUORIC_ACID("Hydrofluoric Acid", "hydrofluoric_acid"),
	CHLOROMETHANE("Chloromethane", "chloromethane"),
	ACRYLIC_ACID("Acrylic Acid", "acrylic_acid"),
	LIQUID_AMMONIA("Liquid Ammonia", "liquid_ammonia"),
	NITRIC_ACID("Nitric Acid", "nitric_acid"),
	SODIUM_CYANIDE("Sodium Cyanide", "sodium_cyanide"),
	LIQUID_NITROGEN("Liquid Nitrogen", "liquid_nitrogen"),
	LIQUID_OXYGEN("Liquid Oxygen", "liquid_oxygen"),
	METHANOL("Methanol", "methanol"),
	VIRGIN_WATER("Virgin Water", "virgin_water"),
	SALT_BRINE("Brine", "salt_brine"),
	DENSE_BRINE("Brine", "dense_brine"),
	MOTHER_LIQUOR("Mother Liquor", "mother_liquor"),
	XPJUICE("Liquid Experience", "xpjuice"),

//DENSE
	COAL_TAR("Coal Tar", "coal_tar"),
	COAL_SLURRY("Coal Slurry", "coal_slurry"),
	SILICONE("Silicone", "silicone"),
	LEACHATE("Medium Grade Leachate", "leachate"),
	HIGH_LEACHATE("High Grade Leachate", "high_leachate"),
	LOW_LEACHATE("Low Grade Leachate", "low_leachate"),
	ORGANIC_SLURRY("Organic Slurry", "organic_slurry"),
	TOXIC_WASTE("Toxic Waste", "toxic_waste"),
	TOXIC_SLUDGE("Toxic Sludge", "toxic_sludge"),

//GASSES
	NITROGEN("Nitrogen", "nitrogen"),
	HYDROGEN("Hydrogen", "hydrogen"),
	OXYGEN("Oxygen", "oxygen"),
	RAW_SYNGAS("Raw Syngas", "raw_syngas"),
	SYNGAS("Syngas", "syngas"),
	COMPRESSED_AIR("Compressed Air", "compressed_air"),
	COOLED_AIR("Cooled Air", "cooled_air"),
	REFINED_AIR("Refined Air", "refined_air"),
	ARGON("Argon", "argon"),
	CARBON_DIOXIDE("Carbon Dioxide", "carbon_dioxide"),
	NEON("Neon", "neon"),
	HELIUM("Helium", "helium"),
	KRYPTON("Krypton", "krypton"),
	XENON("Xenon", "xenon"),
	AMMONIA("Ammonia", "ammonia"),
	WATER_VAPOUR("Steam", "water_vapour"),
	RAW_FLUE_GAS("Raw Flue Gas", "raw_flue_gas"),
	FLUE_GAS("Flue Gas", "flue_gas"),
	PROPYLENE("Propylene", "propylene"),
	THIN_AIR("Thin Air", "thin_air"),

//MOLTEN
	MOLTEN_TITANIUM("Molten Titanium", "molten_titanium"),
	MOLTEN_VANADIUM("Molten Vanadium", "molten_vanadium"),
	MOLTEN_CUBE("Molten CuBe", "molten_cube"),
	MOLTEN_SCAL("Molten ScAl", "molten_scal"),
	MOLTEN_BAM("Molten BAM", "molten_bam"),
	MOLTEN_STELLITE("Molten Stellite", "molten_stellite"),
	MOLTEN_CUPRONICKEL("Molten Cupronickel", "molten_cupronickel"),
	MOLTEN_NIMONIC("Molten Nimonic", "molten_nimonic"),
	MOLTEN_HASTELLOY("Molten Hastelloy", "molten_hastelloy"),
	MOLTEN_NICHROME("Molten Nichrome", "molten_nichrome"),
	MOLTEN_CUNIFE("Molten CuNiFe", "molten_cunife"),
	MOLTEN_HYDRONALIUM("Molten Hydronalium", "molten_hydronalium"),
	MOLTEN_VANASTEEL("Molten Vanasteel", "molten_vanasteel"),
	MOLTEN_TANTALOY("Molten Tantaloy", "molten_tantaloy"),
	MOLTEN_CORTEN("Molten Corten", "molten_corten"),
	MOLTEN_NIAL("Molten NiAl", "molten_nial"),
	MOLTEN_INCONEL("Molten Inconel", "molten_inconel"),
	MOLTEN_ZIRCALOY("Molten Zircaloy", "molten_zircaloy"),
	MOLTEN_PEWTER("Molten Pewter", "molten_pewter");

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

	public static Fluid pickFluid(EnumFluid fluid){
		return FluidRegistry.getFluid(fluid.getFluidName());
	}
}