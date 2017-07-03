//----------------------------------
//----------Seasoning Rack----------
//----------------------------------
//Parameters = input, output
//input = the input itemstack
//output = the output itemstack
mods.rockhounding_chemistry.SeasoningRack.add(<minecraft:wheat>, <minecraft:wheat_seeds>);

//Parameters = input
//input = the input itemstack
mods.rockhounding_chemistry.SeasoningRack.remove(<minecraft:rotten_flesh>);
mods.rockhounding_chemistry.SeasoningRack.remove(<rockhounding_chemistry:chemicalItems:7>);


//----------------------------------
//----------Mineral Sizer-----------
//----------------------------------
//Parameters = input, output
//input = the input itemstack
//output = the output itemstack
mods.rockhounding_chemistry.MineralSizer.add(<minecraft:gravel>, <minecraft:dye:6>);
mods.rockhounding_chemistry.MineralSizer.add(<minecraft:sand>, <rockhounding_chemistry:miscItems:27>);

//Parameters = input, outputs, probabilities
//input = the input itemstack
//outputs = the array of possible extractible itemstacks
//probabilities = the array of probability for each output to be extracted
mods.rockhounding_chemistry.MineralSizer.addAll(<minecraft:hardened_clay>, [<minecraft:dye:0>, <minecraft:dye:1>, <minecraft:dye:2>, <minecraft:dye:3>, <minecraft:dye:4>], [20, 20, 20, 20, 20]);

//Parameters = input
//input = the input itemstack
mods.rockhounding_chemistry.MineralSizer.remove(<minecraft:stone:1>);
mods.rockhounding_chemistry.MineralSizer.remove(<rockhounding_chemistry:miscItems:25>);
mods.rockhounding_chemistry.MineralSizer.remove(<rockhounding_chemistry:miscItems:27>);


//----------------------------------
//-----------Leaching Vat-----------
//----(Formely Mineral Analyzer)----
//----------------------------------
//Parameters = input, output
//input = the input itemstack
//output = the output itemstack
mods.rockhounding_chemistry.LeachingVat.add(<minecraft:cobblestone>, <minecraft:dye:15>);

//Parameters = input, output, probabilities
//input = the input itemstack
//output = the array of possible extractible itemstacks
//probabilities = the array of probability for each output to be extracted
//Notes: both output and probabilities arrays must have the same amount of elements.
mods.rockhounding_chemistry.LeachingVat.addAll(<minecraft:hardened_clay>, [<minecraft:dye:0>, <minecraft:dye:1>, <minecraft:dye:2>, <minecraft:dye:3>, <minecraft:dye:4>], [20, 20, 20, 20, 20]);
mods.rockhounding_chemistry.LeachingVat.addAll(<minecraft:sandstone>, [<minecraft:dye:15>, <minecraft:dye:14>, <minecraft:dye:13>, <minecraft:dye:12>, <minecraft:dye:11>], [20, 20, 20, 20, 20]);

//Parameters = input
//input = the input itemstack
mods.rockhounding_chemistry.LeachingVat.remove(<rockhounding_chemistry:mineralOres:2>);


//----------------------------------
//----------Metal Alloyer----------
//----------------------------------
//Parameters = name, ingredients, quantities, alloy, scrap
//name = the name of the alloy that will appear in the machine selector
//ingredients = the array of oredicted inputs composing the alloy. Max 6 elements
//quantities = the quantity of each ingredient required. Max 6 elements
//alloy = the itemstack representing the alloy, usually ingots
//scrap = the itemstack representing the waste of the alloying, usually nuggets. Can be null if not used
//Notes: both ingredients and quantities arrays must have the same amount of elements
//Notes: the scrap quantity will be randomized by the mod itself
mods.rockhounding_chemistry.MetalAlloyer.add("Enderite", ["dustCopper", "dustArsenic", "dustCadmium"],[7, 4, 2], <minecraft:ender_pearl>*9, <minecraft:gold_nugget>);
mods.rockhounding_chemistry.MetalAlloyer.add("Netherite", ["dustZinc", "dustUranium", "dustChromium", "dustBoron"],[6, 4, 2, 1], <minecraft:blaze_rod>*9, null);

//Parameters = alloy
//alloy = the itemstack representing the alloy to remove
mods.rockhounding_chemistry.MetalAlloyer.remove(<rockhounding_chemistry:alloyItems:10>);


//----------------------------------
//--------Chemical Extractor--------
//----------------------------------
//Parameters = category, elements, quantities
//category = the name of the category that will identify the processed item
//input = the itemstack representing the processed mineral
//elements = the array of the extracted elements composing the formula
//quantities = the quantity of each ingredient being extracted
//Notes: both elements and quantities arrays must have the same amount of elements
//Notes: the quantity is roughly represented as a percentage of the entire mineral formula where 100% is a regular dust item
mods.rockhounding_chemistry.ChemicalExtractor.add("Enderide", <minecraft:ender_pearl>, ["copper", "arsenic", "cadmium"],[40,20,10]);
mods.rockhounding_chemistry.ChemicalExtractor.add("Netheride", <minecraft:blaze_rod>, ["zinc", "uranium", "chromium", "boron"],[45,15,10,4]);

//Parameters = input
//input = the itemstack representing the mineral to remove
mods.rockhounding_chemistry.ChemicalExtractor.remove(<rockhounding_chemistry:arsenateShards:1>);

//Parameters = elements
//elements = the array of elements to be inhibited from being extracted by the machine
mods.rockhounding_chemistry.ChemicalExtractor.inhibit(["vanadium", "osmium", "iridium"]);


//----------------------------------
//------------Lab Oven--------------
//----------------------------------
//Parameters = solute, catalyst, solvent, reagent, solution
//solute = itemstack representing the solid ingredient of the solution
//catalyst = if the solution will be considered a Catalyst
//solvent = the first fluidstack representing the solvent.
//solventAmount = the needed amount of solvent.
//reagent = the second fluidstack representing the solvent. It can be null
//reagentAmount = the needed amount of reagent.
//solution = the fluidstack representing the final solution
//solutionAmount = the resulting amount of solution.
mods.rockhounding_chemistry.LabOven.add(<minecraft:slime_ball>, false, <liquid:water>, 1000, <liquid:sulfuric_acid>, 500, <liquid:silicone>, 100);

//Parameters = solution
//solution = the fluidstack representing the solution to remove
mods.rockhounding_chemistry.LabOven.remove(<liquid:chloromethane>);



//----------------------------------
//------Deposition Chamber----------
//----------------------------------
//Parameters = input, output, solvent, temperature, pressure
//input = itemstack representing the input
//output = itemstack representing the output
//solvent = the fluidstack representing the solvent.
//solventAmount = the needed amount of solvent (Max 10000).
//temperature = the working temperature (Max 3000)
//pressure = the working pressure (Max 32000).
mods.rockhounding_chemistry.DepositionChamber.add(<minecraft:slime_ball>, <minecraft:slime>, <liquid:water>, 4000, 1500, 21000);

//Parameters = output
//output = the itemstack representing the output
mods.rockhounding_chemistry.DepositionChamber.remove(<rockhounding_chemistry:alloyItems:31>);