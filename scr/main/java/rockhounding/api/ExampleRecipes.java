package rockhounding.api;

import java.util.Arrays;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import rockhounding.api.machines.IReciper;

public class ExampleRecipes extends IReciper{
	public static void init(){

/**
 * ROCKHOUNDING: CHEMISTRY   -   MINERAL ANALYZER
 * The Mineral Analyzer extracts a mineral shard from a pool of shards composing a mineral category
 */

		/**
		 * description: Adds a custom recipe to the Mineral Analyzer.
		 * The input can be any itemstack. The output is an array of elements extractible from the input depending on a specific probability.
		 * 
		 * @param inputStack : the input itemstack
		 * @param elements : the list of elements extractible from the the input itemstack
		 * @param probability : the list of probability of each element to be extracted
		 */
		sendToAnalyzer(new ItemStack(Blocks.HARDENED_CLAY), Arrays.asList(new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 1), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 3), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 5)), Arrays.asList(20, 15, 15, 10, 20, 20));
		sendToAnalyzer(new ItemStack(Blocks.SAND), new ItemStack(Items.DYE, 1, 2));

		/**
		 * @param inputStack : the itemstack no longer allowed to be processed
		 */
		removeFromAnalyzer(new ItemStack(Blocks.HARDENED_CLAY));



/**
 * ROCKHOUNDING: CHEMISTRY   -   CHEMICAL EXTRACTOR
 * The Chemical Extractor produces element dusts from the inputted itemstack.
 */

		/**
		 * description: Adds a custom recipe to the Chemical Extractor.
		 * The input includes an array with the elements composing the itemstack and an array with the percentage of each element
		 * 
		 * @param category : the category to which the input itemstack belongs
		 * @param inputStack : the input itemstack
		 * @param elements : the list of elements composing the itemstack
		 * @param quantities : the list of percentage of each element added in the previous array
		 */
		sendToExtractor("Silicate", new ItemStack(Items.DYE, 1, 4), Arrays.asList("silicon", "aluminum", "sodium", "calcium", "sulfur"), Arrays.asList(17, 16, 14, 8, 6));

		/**
		 * @param elements : the list of elements inhibited from extraction
		 */
		inhibitFromExtractor(Arrays.asList("silicon", "aluminum"));



/**
 * ROCKHOUNDING: CHEMISTRY   -   METAL ALLOYER
 * The Metal Alloyer produces alloyed ingots from mixing an array of dusts.
 */
		/**
		 * description: Adds a custom recipe to the Metal Alloyer.
		 * The input is an array of 1 to 6 strings representing the oredict of the single dust and their quantity in the alloy.
		 * The order is fixed, so an alloy can have the same ingredients. Just one difference in the arrays will make it unique.
		 * 
		 * @param displayName : the name of the alloy that will be shown in the machine selector
		 * @param dusts: the array of the oredicted dusts in their desired order
		 * @param quantities: the quantity of each element of the previous array
		 * @param ingotStack: the outputted alloy result.
		 * @param scrapStack: the randomized optinal waste resulting from the alloy. It can be null if not used
		 */
		sendToAlloyer("Gold", Arrays.asList("dustCopper", "dustIron"), Arrays.asList(5,4), new ItemStack(Items.GOLD_INGOT, 9), new ItemStack(Items.GOLD_NUGGET));

		/**
		 * description: Removes a recipe from the Metal Alloyer.
		 * 
		 * @param ingotStack : the outputted alloy result.
		 */
		removeFromAlloyer(new ItemStack(Items.GOLD_INGOT));



/**
 * ROCKHOUNDING: CHEMISTRY   -   SEASONING RACK
 * The Seasoning Rack is a processing machine, returning an itemstack from another at the input.
 * By default the machine has an additional hardcoded salt processing for internal uses in the Rockhounding mod.
 */

		/**
		 * description: Adds a custom recipe to the Seasoning Rack.
		 * 
		 * @param inputStack : the itemstack being processed
		 * @param outputStack : the resulting itemstack
		 */
		sendToSeasoner(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(Items.LEATHER));

		/**
		 * description: Removes a recipe from the Seasoning Rack.
		 * 
		 * @param inputStack : the itemstack being processed
		 */
		removeFromSeasoner(new ItemStack(Items.ROTTEN_FLESH));



/**
 * ROCKHOUNDING: CHEMISTRY   -   MINERAL SIZER
 * The Mineral Sizer is meant to be a crusher to obtain a crushed product from an input object.
 * By default the machine has an additional hardcoded function for internal uses in the Rockhounding mod.
 */

		/**
		 * description: Adds a custom recipe to the Mineral Sizer.
		 * 
		 * @param inputStack : the input itemstack
		 * @param elements : the list of elements extractible from the the input itemstack
		 * @param probability : the list of probability of each element to be extracted
		 */
		sendToSizer(new ItemStack(Blocks.HARDENED_CLAY), Arrays.asList(new ItemStack(Items.DYE, 1, 0), new ItemStack(Items.DYE, 1, 1), new ItemStack(Items.DYE, 1, 2), new ItemStack(Items.DYE, 1, 3), new ItemStack(Items.DYE, 1, 4), new ItemStack(Items.DYE, 1, 5)), Arrays.asList(20, 15, 15, 10, 20, 20));
		sendToSizer(new ItemStack(Blocks.SAND), new ItemStack(Items.DYE, 1, 9));

		/**
		 * description: Removes a recipe from the Mineral Sizer.
		 * 
		 * @param inputStack : the object being crushed
		 */
		removeFromSizer(new ItemStack(Blocks.STONE, 1, 1));



/**
 * ROCKHOUNDING: CHEMISTRY   -   LAB OVEN
 * The Lab Oven produces fluids by the combination of an itemstack and one or two fluids
 */

		/**
		 * description: Adds a custom fluid recipe to the Lab Oven.
		 * 
		 * @param soluteStack : the input itemstack representing the solid ingredient 
		 * @param solventA : the main fluid solvent. This must be always used
		 * @param solventB : the secondary fluid solvet. It can be null if not used
		 * @param outputFluid : the output fluid
		 */
		sendToOven(new ItemStack(Items.CLAY_BALL), new FluidStack(FluidRegistry.getFluid("chloromethane"), 500), null, new FluidStack(FluidRegistry.getFluid("silicone"), 500));
		
		/**
		 * description: Removes a fluid recipe from the Lab Oven.
		 * 
		 * @param outputFluid : the output fluid to remove. Amount will be ignored
		 */
		removeFromOven(new FluidStack(FluidRegistry.getFluid("syngas"), 1000));



/**
 * ROCKHOUNDING: CHEMISTRY   -   DEPOSITION CHAMBER
 * The Deposition Chamber enriches an element into another compound
 */

	/**
	 * description: Adds a custom recipe to the Deposition Chamber.
	 * Max fluid amount can be 10000mB. Temperature can be any integer up to 3000, pressure can be any integer up to 32000.
	 * 
	 * @param inputStack : the input itemstack
	 * @param outputStack : the output itemstack
	 * @param solvent : the fluid solvent. The amount will count for the recipe
	 * @param temperature : the working temperature
	 * @param pressure : the wotking pressure
	 */
	sendToDeposition(new ItemStack(Items.CLAY_BALL), new ItemStack(Items.NETHERBRICK), new FluidStack(FluidRegistry.getFluid("sulfuric_acid"), 5000), 1000, 24000);

	/**
	 * description: Removes a recipe from the Deposition Chamber.
	 * 
	 * @param outputStack : the outputted result.
	 */
	removeFromDeposition(new ItemStack(Items.NETHERBRICK));

		
		
/**
 * ROCKHOUNDING: SURFACE   -   WOOD INCUBATOR
 * The Wood Incubator transforms an object into another by the effect of a solid and a fluid ingredient.
 */
		/**
		 * description: Adds a custom recipe to the Wood Incubator.
		 * 
		 * @param inputStack : the object to be transformed
		 * @param soluteStack : the solid ingredient
		 * @param canSoluteOredict : if the solute can include oredicted equivalents
		 * @param solventStack : the fluid ingredient
		 * @param outputStack : the final object
		 */
		sendToIncubator(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.ROTTEN_FLESH), false, new FluidStack(FluidRegistry.WATER, 500), new ItemStack(Blocks.SLIME_BLOCK));

		/**
		 * description: Removes a recipe from the Wood Incubator.
		 * 
		 * @param outputStack : the final object
		 */
		removeFromIncubator(new ItemStack(Blocks.SLIME_BLOCK));



/**
 * ROCKHOUNDING: SURFACE   -   COMPOST BIN
 * The Compost Bin produces a compost item used for the Rockhounding mod purposes.
 */

		/**
		 * description: Adds a custom recipe to the Compost Bin.
		 * 
		 * @param inputStack : the object being composted
		 * @param canInputOredict : if the input can include oredicted equivalents
		 */
		sendToCompost(new ItemStack(Blocks.BONE_BLOCK), true);

		/**
		 * description: Removes a recipe from the Compost Bin.
		 * 
		 * @param inputStack : object no longer allowed to be processed
		 */
		removeFromCompost(new ItemStack(Blocks.MELON_BLOCK));



/**
 * ROCKHOUNDING: ORE TIERS   -   BLOOMERY
 * The Bloomery can separately melt an ore into its molten form and then cast the fluid into an ingot.
 */

		/**
		 * description: Adds a custom recipe to the Bloomery.
		 * 
		 * @param inputStack : ore to be smelted
		 * @param moltenStack : the fluid obtained melting the ore
		 * @param outputStack : the item obtained casting the fluid
		 */
		sendToBloomery(new ItemStack(Blocks.MAGMA), new FluidStack(FluidRegistry.LAVA, 250), new ItemStack(Items.MAGMA_CREAM));

		/**
		 * description: Removes a custom recipe to the Bloomery.
		 * 
		 * @param inputStack : ore to be smelted
		 */
		removeFromBloomery(new ItemStack(Blocks.IRON_ORE));



/**
 * ROCKHOUNDING: ORE TIERS   -   BASIC COAL REFINERY
 * The Basic coal refinery allows to improve coals used by the mod into better coals.
 */

		/**
		 * description: Adds a custom recipe to the refinery.
		 * 
		 * @param inputStack : coal to be improved
		 * @param outputStack : improved coal
		 */
		sendToRefinery(new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.COAL));

		/**
		 * description: Removes a custom recipe to the refinery.
		 * 
		 * @param inputStack : coal to be improved
		 */
		removeFromRefinery(new ItemStack(Items.COAL));



/**
 * ROCKHOUNDING: ORE TIERS   -   PEAT DRYING PALLET
 * The Peat drying pallet allows to dry moist peat chunks into the proper coal.
 */

		/**
		 * description: Adds a custom recipe to the refinery.
		 * 
		 * @param inputStack : item to be improved
		 * @param outputStack : improved item
		 */
		sendToPallet(new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.COAL));

		/**
		 * description: Removes a custom recipe to the refinery.
		 * 
		 * @param inputStack : item to be improved
		 */
		removeFromPallet(new ItemStack(Items.COAL));



/**
 * ROCKHOUNDING: ROCKS   -   ROCKS VENDING SYSTEM
 * The Rocks Vending Systems allows to purchase the rocks provided by the mod
 */

		/**
		 * Description: Add a custom fee item to the Rocks Vending System
		 * 
		 * @param inputStack : the itemstack allowed to be used as currency
		 * @param canInputOredict : if the iput can include oredicted equivalents
		 * @param outputStacksize : how many rocks can be purchased with one item
		 */
		sendToVendor(new ItemStack(Items.NETHER_STAR), false, 64);

		/**
		 * Description: Remove an itemstack the list of allowed currency
		 * 
		 * @param inputStack : the itemstack to remove from the list of allowed currency
		 */
		removeFromVendor(new ItemStack(Items.GOLD_INGOT));



/**
 * ROCKHOUNDING: ROCKS   -   CUTTING STATION
 * The Cutting Station allows to cut and carve the rocks provided by the mod
 */

		/**
		 * Description: Add a custom recipe to the Cutting Station
		 * 
		 * @param inputStack : the block that will be cut
		 * @param cutCode : The code of the cut being performed (see gui switches). Zero if no code matches
		 * @param outputStacksize : the resulting cut block
		 */
		sendToCutting(new ItemStack(Blocks.STONE, 1, 1), 2, new ItemStack(Blocks.STONE, 1, 2));

	}

}