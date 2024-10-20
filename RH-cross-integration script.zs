var oretiers_peat_chunk = <rockhounding_oretiers:tier_items:3>;
var oretiers_peat_dried = <rockhounding_oretiers:tier_items:6>;
var chemistry_neodymium_dust = <rockhounding_chemistry:chemical_items:8>;
var chemistry_sulfur_compound = <rockhounding_chemistry:chemical_items:2>;
var chemistry_cracked_coal = <rockhounding_chemistry:chemical_items:0>;
var surface_gypsum_dust = <rockhounding_surface:gypsum_items:0>;
var surface_enriched_bonemeal = <rockhounding_surface:gypsum_items:1>;
var surface_soil_conditioner = <rockhounding_surface:gypsum_items:2>;
var surface_soil_amendment = <rockhounding_surface:gypsum_items:3>;
var surface_compost = <rockhounding_surface:gypsum_items:4>;
var surface_organic_compound = <rockhounding_surface:aging_items:0>;
var surface_contaminating_compound = <rockhounding_surface:aging_items:1>;
var surface_ferrous_compound = <rockhounding_surface:aging_items:2>;
var surface_copper_compound = <rockhounding_surface:aging_items:3>;
var surface_chromium_compound = <rockhounding_surface:aging_items:4>;
var surface_manganese_compound = <rockhounding_surface:aging_items:5>;
var surface_rainbow_compound = <rockhounding_surface:aging_items:6>;
var surface_lapis_compound = <rockhounding_surface:aging_items:7>;
var surface_chalcedony_compound = <rockhounding_surface:aging_items:8>;
var surface_contaminated_slag = <rockhounding_surface:aging_items:11>;

//----------------------------------------------------------------------------------------------------------------
//FROM ORETIERS TO CHEMISTRY
//----------------------------------------------------------------------------------------------------------------
mods.rockhounding_chemistry.LabBlender.add(["itemAnthracite"], [3], chemistry_sulfur_compound*5);
mods.rockhounding_chemistry.LabBlender.add(["itemBituminous"], [3], chemistry_sulfur_compound*2);

mods.rockhounding_chemistry.SlurryPond.add(oretiers_peat_chunk, <liquid:water>*400, <liquid:organic_slurry>*400);

mods.rockhounding_chemistry.MineralSizer.add("blockAnthracite", [chemistry_cracked_coal*64], [8]);
mods.rockhounding_chemistry.MineralSizer.add("blockAnthracite", [chemistry_cracked_coal*48], [8]);
//----------------------------------------------------------------------------------------------------------------





//----------------------------------------------------------------------------------------------------------------
//FROM ORETIERS TO SURFACE
//----------------------------------------------------------------------------------------------------------------
mods.rockhounding_surface.CompostBin.add(oretiers_peat_chunk, 5, 0);
mods.rockhounding_surface.CompostBin.add(oretiers_peat_dried, 5, 0);
//----------------------------------------------------------------------------------------------------------------





//----------------------------------------------------------------------------------------------------------------
//FROM SURFACE TO CHEMISTRY
//----------------------------------------------------------------------------------------------------------------
mods.rockhounding_chemistry.SlurryPond.add(surface_compost, <liquid:water>*400, <liquid:organic_slurry>*300);


mods.rockhounding_chemistry.LabBlender.add(["bone", "dustGypsum"], [1, 6], surface_enriched_bonemeal);
mods.rockhounding_chemistry.LabBlender.add(["dustGypsum", "bonemealEnriched", "bonemealCompost", "dustNeodymium"], [6, 1, 5, 1], surface_soil_conditioner);
mods.rockhounding_chemistry.LabBlender.add(["dustGypsum", "bonemealConditioner", "bonemealCompost", "gemQuartz", "dustNeodymium"], [8, 1, 5, 2, 2], surface_soil_amendment);

mods.rockhounding_chemistry.SlurryPond.add("compoundChloride", <liquid:water>*1000, <liquid:acidic_water>*1000);
mods.rockhounding_chemistry.LabOven.add(null, surface_organic_compound, null, <liquid:acidic_water>*1000, null, <liquid:aging_bath>*1000, null);
mods.rockhounding_chemistry.Precipitator.add("Casting Bath", surface_contaminating_compound, null, <liquid:aging_bath>*1000, <liquid:casting_bath>*500, surface_contaminated_slag);

mods.rockhounding_chemistry.MaterialCabinet.add("CS", "compoundSlag", "Contaminated Slag");

mods.rockhounding_chemistry.LabBlender.add(["bonemealCompost", "dustGypsum"], [2, 2], surface_organic_compound);
mods.rockhounding_chemistry.PowderMixer.add(["compoundQuartz", "dustIron", "dustCarbon", "dustCalcium"], [25, 10, 25, 40], surface_contaminating_compound);
mods.rockhounding_chemistry.PowderMixer.add(["dustIron", "dustCarbon", "compoundQuartz"], [30, 50, 20], surface_ferrous_compound);
mods.rockhounding_chemistry.PowderMixer.add(["dustIron", "dustCopper", "dustMagnesium", "compoundQuartz"], [10, 50, 10, 30], surface_copper_compound);
mods.rockhounding_chemistry.PowderMixer.add(["dustIron", "dustChromium", "compoundQuartz"], [15, 50, 35], surface_chromium_compound);
mods.rockhounding_chemistry.PowderMixer.add(["dustIron", "dustManganese", "compoundQuartz"], [20, 50, 30], surface_manganese_compound);
mods.rockhounding_chemistry.PowderMixer.add(["dustIron", "dustCobalt", "dustCopper", "dustManganese", "compoundQuartz"], [15, 10, 20, 15, 40], surface_rainbow_compound);
mods.rockhounding_chemistry.LabBlender.add(["gemLapis", "gemQuartz"], [2, 3], surface_lapis_compound);
mods.rockhounding_chemistry.PowderMixer.add(["dustIron", "dustSilicon", "dustCopper", "dustCalcium", "compoundQuartz"], [15, 15, 10, 20, 40], surface_chalcedony_compound);

mods.rockhounding_chemistry.ChemicalExtractor.add("Sulfate", surface_gypsum_dust, ["dustCalcium", "dustSulfur"], [23,19]);
//----------------------------------------------------------------------------------------------------------------





//----------------------------------------------------------------------------------------------------------------
//FROM ROCKS TO CHEMISTRY
//----------------------------------------------------------------------------------------------------------------

//to do

//----------------------------------------------------------------------------------------------------------------
