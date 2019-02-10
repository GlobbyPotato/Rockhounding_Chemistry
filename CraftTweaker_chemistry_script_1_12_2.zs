//Note: the solute must be always used. the solvent and the solution cannot be null.
//display name: alternative name for the recipe selector. Can be null id not needed
//solute stack or roedict string: the main ingredient (required)
//catalyst: the damageable ingredient (optional)
//solvent fluid: the main solvent
//solution fluid: the output fluid
//precipitate: the secondary solid output (optional)
mods.rockhounding_chemistry.Precipitator.add("Magma Cream", <minecraft:slime_ball>, null, <liquid:water>*500, <liquid:sulfuric_acid>*500, <minecraft:magma_cream>*100);
mods.rockhounding_chemistry.Precipitator.add("Magma Cream", "slimeball", <rockhounding_chemistry:co_catalyst>, <liquid:water>*500, <liquid:sulfuric_acid>*500, <minecraft:magma_cream>*100);

//solute stack: the solute to remove
mods.rockhounding_chemistry.Precipitator.removeByInput(<minecraft:slime_ball>);
//solution fluid: the solution to remove
mods.rockhounding_chemistry.Precipitator.removeByOutput(<liquid:sulfuric_acid>);
//precipitate stack: the precipitate to remove
mods.rockhounding_chemistry.Precipitator.removeByPrecipitate(<minecraft:magma_cream>);



//input fluid 1: the fluid added from the left channel
//input fluid 2: the fluid added from the right channel
//output fluid: the output mixed fluid
//output gas: the output gas byproduct (optional)
//voltage: the voltage multiplier to aplly to the reaction (set zero for no voltage required)
mods.rockhounding_chemistry.StirredTank.add(<liquid:hydrochloric_acid>*1000, <liquid:methanol>*1000, <liquid:chloromethane>*1000, <liquid:hydrogen>*1000, 5);
mods.rockhounding_chemistry.StirredTank.add(<liquid:hydrochloric_acid>*1000, <liquid:methanol>*1000, <liquid:chloromethane>*1000, null, 5);



//input stack: the item to spill into fluid
//output fluid: the fluid spilled from the itemstack
mods.rockhounding_chemistry.SlurryDrum.add(<minecraft:slimeball>, <liquid:silicone>*1000);
mods.rockhounding_chemistry.SlurryDrum.add("slimeball", <liquid:lava>*1000);


//input gas: the gas to mark as pollutant
mods.rockhounding_chemistry.PollutantGas.add(<liquid:ammonia>*1000);



//input fluid: the fluid to mark as pollutant
mods.rockhounding_chemistry.PollutantFluid.add(<liquid:chloromethane>*1000);



//input stack: the item to mutate
//output stack: the mutated item
mods.rockhounding_chemistry.ToxicMutation.add(<minecraft:leather>, <minecraft:rotten_flesh>);

//input stack: the input to remove
mods.rockhounding_chemistry.ToxicMutation.remove(<minecraft:leather>);
//output stack: the output to remove
mods.rockhounding_chemistry.ToxicMutation.remove(<minecraft:rotten_flesh>);



//categoty: the category of the input
//input stack: the item to decompose
//output array: the list of elements oredict composing the input
//quantity array: the list of quantities in PartsPerCraft for each element
mods.rockhounding_chemistry.ChemicalExtractor.add("Sulfate", <minecraft:hardened_clay>, ["dustCalcium", "dustSulfur", "dustCarbon", "dustIron", "dustTin"], [20, 13, 5, 4, 3]);
mods.rockhounding_chemistry.ChemicalExtractor.add("Oxide", "oreIron", ["dustIron", "dustSulfur", "dustCarbon", "dustCopper", "dustTin"], [20, 13, 5, 4, 3]);

//input stack: the input to remove
mods.rockhounding_chemistry.ChemicalExtractor.remove(<minecraft:hardened_clay>);



//inhibited element: element not being extracted
mods.rockhounding_chemistry.InhibitElements.inhibit("dustZirconium");



//input stack: the item to be mutated
//output stack: the mutated stack
//solvent: the gas used as dopant and its quantity. It must be "gaseous"
//temperature: the threashold temperature (max 3000K)
//pressure: the threashold pressure (max 30000 uPa)
//carrier: the gas used as carrier or additional reagent
mods.rockhounding_chemistry.DepositionChamber.add(<minecraft:hardened_clay>, <minecraft:obsidian>, <liquid:syngas>*500, 3000, 10000, <liquid:nitrogen>*500);
mods.rockhounding_chemistry.DepositionChamber.add("ingotIron", <minecraft:obsidian>, <liquid:syngas>*500, 3000, 10000, <liquid:oxygen>*200);

//input stack: the item to remove
mods.rockhounding_chemistry.DepositionChamber.removeByInput(<minecraft:hardened_clay>);
//output stack: the item to remove
mods.rockhounding_chemistry.DepositionChamber.removeByOutput(<minecraft:obsidian>);



//Note: the conversion ratio is supposed to be N:1 where N is the input quantity against a unitary output quantity. The same (reversed) recipe works for the Expansion Chamber.
//input gas: the input gas and its quantity. It must be "gaseous"
//output fluid: the output fluid and its quantity
mods.rockhounding_chemistry.GasCondenser.add(<liquid:water_vapour>*100, <liquid:water>*1);

//input gas: the gas to remove
mods.rockhounding_chemistry.GasCondenser.removeByInput(<liquid:water_vapour>);
//output fluid: the fluid to remove
mods.rockhounding_chemistry.GasCondenser.removeByOutput(<liquid:water>);



//Note: Slags can be both null.
//input fluid: the main ingredient
//solvent fluid: the secondary ingredient
//output gas: the resulting gas. It must be "gaseous"
//slag stack 1: the main by-product (optional)
//slag stack 2: the secondary by-product (optional)
//temperature: the threashold temperature (min 31K, max 2000K)
mods.rockhounding_chemistry.GasifierPlant.add(<liquid:lava>*150, <liquid:water>*400, <liquid:oxygen>*120, <minecraft:dye:14>, null, 900);

//input fluid: the fluid to remove
mods.rockhounding_chemistry.GasifierPlant.removeByInput(<liquid:lava>*);
//output gas: the gas to remove
mods.rockhounding_chemistry.GasifierPlant.removeByOutput(<liquid:oxygen>);



//Note: the quantity of the gas ingredients is not relievant. Slags can be both null.
//input gas: the raw ingredient. It must be "gaseous"
//output gas: the purified output. It must be "gaseous"
//slag stack 1: the main by-product (optional)
//slag stack 2: the secondary by-product (optional)
mods.rockhounding_chemistry.GasPurifier.add(<liquid:water_vapour>*1000, <liquid:oxygen>*1000, <minecraft:dye:4>, null);

//input gas: the gas to remove
mods.rockhounding_chemistry.GasPurifier.removeByInput(<liquid:water_vapour>*);
//output gas: the gas to remove
mods.rockhounding_chemistry.GasPurifier.removeByOutput(<liquid:oxygen>);



//Note: The catalyst must be a damageable item (any item with a durability).
//input gas A: the left gas ingredient. It must be "gaseous"
//input gas B: the right gas ingredient. It must be "gaseous"
//output gas: the combined gas
//catalyst: the damageable item
mods.rockhounding_chemistry.GasReformer.add(<liquid:water_vapour>*1000, <liquid:oxygen>*1000, <liquid:syngas>*1000, <minecraft:diamond_sword>);

//input gas A: the gas to remove
mods.rockhounding_chemistry.GasPurifier.removeByInputA(<liquid:water_vapour>*);
//input gas B: the gas to remove
mods.rockhounding_chemistry.GasPurifier.removeByInputB(<liquid:oxygen>*);
//output gas: the gas to remove
mods.rockhounding_chemistry.GasPurifier.removeByOutput(<liquid:syngas>);



//Note: the quantity of the gas ingredient is not relievant. The output must have a lower temperature than the input
//input gas: the gas to be cooled
//output gas: the cooled gas
mods.rockhounding_chemistry.HeatExchanger.add(<liquid:raw_syngas>*1000, <liquid:syngas>*1000);

//input gas: the gas to remove
mods.rockhounding_chemistry.HeatExchanger.remove(<liquid:raw_syngas>*);


//input array: the list of ingredients with their quantity
//output stack: the resulting mixture
mods.rockhounding_chemistry.LabBlender.add([<minecraft:redstone>*9, <minecraft:gunpowder>*4, <minecraft:glowstone_dust>*2], <minecraft:magma_cream>*9);

//output stack: the output to remove
mods.rockhounding_chemistry.LabBlender.remove(<minecraft:magma_cream>);



//Note: the solute must be always used. the solvent and the solution cannot be null.
//display name: alternative name for the recipe selector. Can be null id not needed
//solute stack: the main ingredient (required)
//catalyst: the damageable ingredient (optional)
//solvent fluid: the main solvent
//reagent fluid: the secondary solvent (optional)
//solution fluid: the output fluid
//byproduct: the secondary output (optional)
mods.rockhounding_chemistry.LabOven.add("Silicone Plus", <minecraft:slime_ball>, null, <liquid:water>*500, null, <liquid:sulfuric_acid>*500, <liquid:silicone>*100);
mods.rockhounding_chemistry.LabOven.add("Silicone Mega", "slimeball", <rockhounding_chemistry:co_catalyst>, <liquid:water>*1000, null, <liquid:sulfuric_acid>*500, <liquid:silicone>*400);

//solute stack: the solute to remove
mods.rockhounding_chemistry.LabOven.removeByInput(<minecraft:slime_ball>);
//solution fluid: the solution to remove
mods.rockhounding_chemistry.LabOven.removeByOutput(<liquid:sulfuric_acid>);



//Note: the quantity of leachate is not relievant
//input stack: the tem to de analyzed
//output array: the list of extracted items
//gravity array: the gravity of each extracted items
//leachate: the fluid byproduct (optional)
mods.rockhounding_chemistry.LeachingVat.add(<minecraft:slime_ball>, [<minecraft:dye:15>, <minecraft:dye:14>, <minecraft:dye:13>, <minecraft:dye:12>, <minecraft:dye:11>], [3.18F, 4.88F, 7.51F, 3.30F, 12.05F], <liquid:silicone>*1000);
mods.rockhounding_chemistry.LeachingVat.add("slimeball", [<minecraft:dye:15>, <minecraft:dye:14>, <minecraft:dye:13>, <minecraft:dye:12>, <minecraft:dye:11>], [3.18F, 4.88F, 7.51F, 3.30F, 12.05F], <liquid:silicone>*1000);

//input stack: the input to remove
mods.rockhounding_chemistry.LeachingVat.remove(<minecraft:slime_ball>);



//symbol: a 2 digit symbol appearing on screen
//oredict: the element oredict to be added
//name: the display name of the added element
mods.rockhounding_chemistry.MaterialCabinet.add("Bz", "dustBronze", "Bronze");

//oredict: the oredict to remove
mods.rockhounding_chemistry.MaterialCabinet.remove("dustBronze");



//input array: list of oredicts composing the alloy
//quantity array: quantity in PartsPerCraft for each element
//output stack: the resulting alloy
mods.rockhounding_chemistry.MetalAlloyer.add(["dustZinc", "dustYellowcake", "dustChromium", "dustBoron"], [50, 15, 7, 4], <minecraft:blaze_rod>);

//output stack: the output to remove
mods.rockhounding_chemistry.MetalAlloyer.remove(<minecraft:blaze_rod>);



//input stack: the item to crush
//output array: the list of possible outputs
//comminution array: the comminution for each output (max 15)
mods.rockhounding_chemistry.MineralSizer.add(<minecraft:hardened_clay>, <minecraft:dye:0>, 9);
mods.rockhounding_chemistry.MineralSizer.add(<minecraft:hardened_clay>, [<minecraft:dye:0>, <minecraft:dye:1>, <minecraft:dye:2>, <minecraft:dye:3>, <minecraft:dye:4>], [5, 10, 12, 6, 4]);
mods.rockhounding_chemistry.MineralSizer.add("oreUninspected", [<minecraft:dye:0>, <minecraft:dye:1>, <minecraft:dye:2>, <minecraft:dye:3>, <minecraft:dye:4>], [5, 10, 12, 6, 4]);

//input stack: the input to remove
mods.rockhounding_chemistry.MineralSizer.remove(<minecraft:hardened_clay>);



//input stack: the specific item to be shaped
//output stack: the reshaped item
//pattern: the casting pattern (0:generic, 1:coil, 2:rod, 3:foil, 4:arm, 5:casing, 6:gear, 7:ingot, 8:gauze, 9:coin, 10:plate )
mods.rockhounding_chemistry.ProfilingBench.add(<minecraft:iron_block>, <minecraft:iron_ingot>*9, 7);
//input stack: the oredict-based items to be shaped
mods.rockhounding_chemistry.ProfilingBench.add("blockGlass", <minecraft:empty_bottle>*4, 0);

//input stack: the input to remove
mods.rockhounding_chemistry.ProfilingBench.removeByInput(<minecraft:iron_block>);
//output stack: the output to remove
mods.rockhounding_chemistry.ProfilingBench.removeByOutput(<minecraft:iron_ingot>);



//base input: the base ingredient
//dopant: the dopant element
//output: the grown crystal
mods.rockhounding_chemistry.PullingCrucible.add(<minecraft:iron_nugget>, <minecraft:glowstone_dust>, <minecraft:ghast_tear>);
mods.rockhounding_chemistry.PullingCrucible.add("nuggetIron", <minecraft:glowstone_dust>, <minecraft:ghast_tear>);
mods.rockhounding_chemistry.PullingCrucible.add(<minecraft:iron_nugget>, "dustGlowstone", <minecraft:ghast_tear>);
mods.rockhounding_chemistry.PullingCrucible.add("nuggetIron", "dustGlowstone", <minecraft:ghast_tear>);

//input stack: the input to remove
mods.rockhounding_chemistry.PullingCrucible.removeByInput(<minecraft:iron_nugget>);
//output stack: the output to remove
mods.rockhounding_chemistry.PullingCrucible.removeByOutput(<minecraft:ghast_tear>);



//input fluid: the input fluid and its quantity
//output array: the list of extracted items
//gravity array: the gravity of each extracted items
mods.rockhounding_chemistry.RetentionVat.add(<liquid:silicone>*500, [<minecraft:dye:15>, <minecraft:dye:14>, <minecraft:dye:13>, <minecraft:dye:12>, <minecraft:dye:11>], [3.18F, 4.88F, 7.51F, 3.30F, 12.05F]);

//input fluid: the fluid to remove
mods.rockhounding_chemistry.RetentionVat.remove(<liquid:silicone>);



//input stack: the item being processed
//output stack: the processed item
mods.rockhounding_chemistry.SeasoningRack.add(<minecraft:wheat>, <minecraft:wheat_seeds>);
mods.rockhounding_chemistry.SeasoningRack.add("seedWheat", <minecraft:wheat_seeds>);

//input stack: the input to remove
mods.rockhounding_chemistry.SeasoningRack.removeByInput(<minecraft:wheat>);
//output stack: the output to remove
mods.rockhounding_chemistry.SeasoningRack.removeByOutput(<minecraft:wheat_seeds>);



//input stack: the solid ingredient
//solvent: the fluid solvent
//slurry: the resulting slurry
mods.rockhounding_chemistry.SlurryPond.add(<minecraft:slime_ball>, <liquid:water>*500, <liquid:silicone>*500);
mods.rockhounding_chemistry.SlurryPond.add("slimeball", <liquid:water>*500, <liquid:silicone>*500);

//input stack: the input to remove
mods.rockhounding_chemistry.SlurryPond.removeByInput(<minecraft:slime_ball>);
//slurry: the output to remove
mods.rockhounding_chemistry.SlurryPond.removeByOutput(<liquid:silicone>);



//Note: to just allow the Gas Pipeline recognize gases from other mods, input and output below must be set same
//input: the fluid/gas input
//output: the converted fluid/gas
mods.rockhounding_chemistry.Transposer.add(<liquid:sulphuricacid>*1000, <liquid:sulfuric_acid>*1000);
mods.rockhounding_chemistry.Transposer.add(<liquid:ic2superheated_steam>*1000, <liquid:ic2superheated_steam>*1000);

//input: the input to remove
mods.rockhounding_chemistry.Transposer.removeByInput(<liquid:sulphuricacid>*1000);
//output: the output to remove
mods.rockhounding_chemistry.Transposer.removeByOutput(<liquid:sulfuric_acid>*1000));
