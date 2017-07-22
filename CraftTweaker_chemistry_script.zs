//----------------------------------
//----------Seasoning Rack----------
//----------------------------------
	//input = the input itemstack
	//output = the output itemstack

	//ADD
		mods.rockhounding_chemistry.SeasoningRack.add(input, output);
		mods.rockhounding_chemistry.SeasoningRack.add(<minecraft:wheat>, <minecraft:wheat_seeds>);

	//REMOVE
		mods.rockhounding_chemistry.SeasoningRack.remove(input);
		mods.rockhounding_chemistry.SeasoningRack.remove(<rockhounding_chemistry:chemicalItems:7>);



//----------------------------------
//----------Mineral Sizer-----------
//----------------------------------
	//input = the input itemstack
	//output = the extractible itemstack/s
	//probability = the probability for each output to be extracted

	//ADD with weighted output
		mods.rockhounding_chemistry.MineralSizer.add(input, [output1, output2, output3, output4, output5], [probability1, probability2, probability3, probability4, probability5]);
		mods.rockhounding_chemistry.MineralSizer.add(<minecraft:hardened_clay>, [<minecraft:dye:0>, <minecraft:dye:1>, <minecraft:dye:2>, <minecraft:dye:3>, <minecraft:dye:4>], [20, 20, 20, 20, 20]);

	//ADD with single output
		mods.rockhounding_chemistry.MineralSizer.add(input, output);
		mods.rockhounding_chemistry.MineralSizer.add(<minecraft:gravel>, <minecraft:dye:6>);

	//REMOVE
		mods.rockhounding_chemistry.MineralSizer.remove(input);
		mods.rockhounding_chemistry.MineralSizer.remove(<rockhounding_chemistry:miscItems:27>);



//----------------------------------
//-----------Leaching Vat-----------
//----------------------------------
	//input = the input itemstack
	//output = the extractible itemstack/s
	//probability = the probability for each output to be extracted

	//ADD with weighted output
		mods.rockhounding_chemistry.LeachingVat.add(input, [output1, output2, output3, output4, output5], [probability1, probability2, probability3, probability4, probability5]);
		mods.rockhounding_chemistry.LeachingVat.add(<minecraft:sandstone>, [<minecraft:dye:15>, <minecraft:dye:14>, <minecraft:dye:13>, <minecraft:dye:12>, <minecraft:dye:11>], [20, 20, 20, 20, 20]);

	//ADD with single output
		mods.rockhounding_chemistry.LeachingVat.add(input, output);
		mods.rockhounding_chemistry.LeachingVat.add(<minecraft:cobblestone>, <minecraft:dye:15>);

	//REMOVE
		mods.rockhounding_chemistry.LeachingVat.remove(input);
		mods.rockhounding_chemistry.LeachingVat.remove(<rockhounding_chemistry:mineralOres:2>);


//----------------------------------
//----------Metal Alloyer----------
//----------------------------------
	//name = the name of the alloy that will appear in the machine selector
	//ingredients = the oredicted inputs composing the alloy. Max 6 elements
	//quantities = the quantity of each ingredient required. Max 6 elements
	//alloy = the itemstack representing the output alloy, usually ingots
	//scrap = the itemstack representing the waste of the alloying, usually nuggets. Optional.

	//ADD with scrap output
		mods.rockhounding_chemistry.MetalAlloyer.add(name, [ingredient1, ingredient2, ingredient3],[quantity1, quantity2, quantity3], output, scrap);
		mods.rockhounding_chemistry.MetalAlloyer.add("Netherite", ["dustZinc", "dustUranium", "dustChromium", "dustBoron"],[6, 4, 2, 1], <minecraft:blaze_rod>*9, <minecraft:gold_nugget>);

	//ADD without scrap output
		mods.rockhounding_chemistry.MetalAlloyer.add(name, [ingredient1, ingredient2, ingredient3, ingredient4],[quantity1, quantity2, quantity3, quantity4], output);
		mods.rockhounding_chemistry.MetalAlloyer.add("Netherite", ["dustZinc", "dustUranium", "dustChromium", "dustBoron"],[6, 4, 2, 1], <minecraft:blaze_rod>*9);

	//REMOVE
		mods.rockhounding_chemistry.MetalAlloyer.remove(alloy);
		mods.rockhounding_chemistry.MetalAlloyer.remove(<rockhounding_chemistry:alloyItems:10>);



//----------------------------------
//--------Chemical Extractor--------
//----------------------------------
	//category = the name of the category that will identify the processed item
	//input = the itemstack representing the processed mineral
	//elements = the array of the extracted elements composing the formula
	//percentages = the quantity of each ingredient being extracted

	//ADD
		mods.rockhounding_chemistry.ChemicalExtractor.add(category, input, [element1, element2, element3, element4],[percentage1, percentage2, percentage3, percentage4]);
		mods.rockhounding_chemistry.ChemicalExtractor.add("Netheride", <minecraft:blaze_rod>, ["zinc", "uranium", "chromium", "boron"],[40,20,10,10]);

	//REMOVE
		mods.rockhounding_chemistry.ChemicalExtractor.remove(input);
		mods.rockhounding_chemistry.ChemicalExtractor.remove(<rockhounding_chemistry:arsenateShards:1>);

	//INHIBIT
		mods.rockhounding_chemistry.ChemicalExtractor.inhibit([element...]);
		mods.rockhounding_chemistry.ChemicalExtractor.inhibit(["vanadium", "osmium", "iridium"]);



//----------------------------------
//------------Lab Oven--------------
//----------------------------------
	//solute = itemstack representing the solid ingredient of the solution
	//catalyst = if the solution will be considered a Catalyst
	//solvent = the first fluidstack representing the solvent.
	//solventAmount = the needed amount of solvent.
	//reagent = the second fluidstack representing the solvent. It can be null
	//reagentAmount = the needed amount of reagent.
	//solution = the fluidstack representing the final solution
	//solutionAmount = the resulting amount of solution.

	//ADD with reagent
		mods.rockhounding_chemistry.LabOven.add(solute, catalyst, solvent, solventAmount, reagent, reagentAmount, solution, solutionAmount);
		mods.rockhounding_chemistry.LabOven.add(<minecraft:slime_ball>, false, <liquid:water>, 1000, <liquid:sulfuric_acid>, 500, <liquid:silicone>, 100);

	//ADD without reagent
		mods.rockhounding_chemistry.LabOven.add(solute, catalyst, solvent, solventAmount,  solution, solutionAmount);
		mods.rockhounding_chemistry.LabOven.add(<minecraft:slime_ball>, false, <liquid:water>, 1000, <liquid:silicone>, 100);

	//REMOVE
		mods.rockhounding_chemistry.LabOven.remove(solution);
		mods.rockhounding_chemistry.LabOven.remove(<liquid:chloromethane>);



//----------------------------------
//------Deposition Chamber----------
//----------------------------------
	//input = itemstack representing the input
	//output = itemstack representing the output
	//solvent = the fluidstack representing the solvent.
	//solventAmount = the needed amount of solvent (Max 10000).
	//temperature = the working temperature (Max 3000)
	//pressure = the working pressure (Max 32000).

	//ADD
		mods.rockhounding_chemistry.DepositionChamber.add(input, output, solvent, solventAmount, temperature, pressure);
		mods.rockhounding_chemistry.DepositionChamber.add(<minecraft:slime_ball>, <minecraft:slime>, <liquid:water>, 4000, 1500, 21000);

	//REMOVE
		mods.rockhounding_chemistry.DepositionChamber.remove(output);
		mods.rockhounding_chemistry.DepositionChamber.remove(<rockhounding_chemistry:alloyItems:31>);
