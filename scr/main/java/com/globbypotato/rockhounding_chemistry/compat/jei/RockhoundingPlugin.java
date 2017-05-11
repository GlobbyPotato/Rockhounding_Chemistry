package com.globbypotato.rockhounding_chemistry.compat.jei;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.laboven.LabOvenRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.laboven.LabOvenRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.laboven.LabOvenRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer.AlloyerRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer.AlloyerRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.metalalloyer.AlloyerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer.AnalyzerRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer.AnalyzerRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineralanalyzer.AnalyzerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer.SizerRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer.SizerRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.mineralsizer.SizerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning.SeasonerRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning.SeasonerRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.saltseasoning.SeasonerRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition.DepositionRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition.DepositionRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.vapordeposition.DepositionRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiSaltSeasoner;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;

@JEIPlugin
public class RockhoundingPlugin extends BlankModPlugin{

	public static IJeiHelpers jeiHelpers;

	@Override
	public void register(IModRegistry registry) {

		jeiHelpers = registry.getJeiHelpers();
		IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

		registry.addRecipeCategories(
				new SizerRecipeCategory(guiHelper),
				new AnalyzerRecipeCategory(guiHelper),
				new ExtractorRecipeCategory(guiHelper),
				new LabOvenRecipeCategory(guiHelper),
				new SeasonerRecipeCategory(guiHelper),
				new AlloyerRecipeCategory(guiHelper),
				new DepositionRecipeCategory(guiHelper));

		registry.addRecipeHandlers(
				new SizerRecipeHandler(),
				new AnalyzerRecipeHandler(),
				new ExtractorRecipeHandler(),
				new LabOvenRecipeHandler(),
				new SeasonerRecipeHandler(),
				new AlloyerRecipeHandler(),
				new DepositionRecipeHandler());

		registry.addRecipes(SizerRecipeWrapper.getRecipes());
		registry.addRecipes(AnalyzerRecipeWrapper.getRecipes());
		registry.addRecipes(ExtractorRecipeWrapper.getRecipes());
		registry.addRecipes(LabOvenRecipeWrapper.getRecipes());
		registry.addRecipes(SeasonerRecipeWrapper.getRecipes());
		registry.addRecipes(AlloyerRecipeWrapper.getRecipes());
		registry.addRecipes(DepositionRecipeWrapper.getRecipes());

		registry.addRecipeClickArea(GuiMineralSizer.class, 65, 15, 31, 31, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(GuiMineralAnalyzer.class, 27, 42, 33, 60, RHRecipeUID.ANALYZER);
		registry.addRecipeClickArea(GuiLabOven.class, 64, 57, 14, 15, RHRecipeUID.LAB_OVEN);
		registry.addRecipeClickArea(GuiSaltSeasoner.class, 72, 23, 32, 52, RHRecipeUID.SALT_SEASONER);
		registry.addRecipeClickArea(GuiMetalAlloyer.class, 99, 74, 14, 17, RHRecipeUID.ALLOYER);
		registry.addRecipeClickArea(GuiChemicalExtractor.class, 49, 47, 14, 32, RHRecipeUID.EXTRACTOR);
		registry.addRecipeClickArea(GuiDepositionChamber.class, 57, 56, 62, 14, RHRecipeUID.VAPOR_DEPOSITION);

		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralSizer), RHRecipeUID.SIZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralAnalyzer), RHRecipeUID.ANALYZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.chemicalExtractor), RHRecipeUID.EXTRACTOR);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.labOven), RHRecipeUID.LAB_OVEN);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.saltSeasoner), RHRecipeUID.SALT_SEASONER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.metalAlloyer), RHRecipeUID.ALLOYER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.depositionChamber), RHRecipeUID.VAPOR_DEPOSITION);

		for(int i=0;i<EnumElement.size();i++){
			registry.addDescription(new ItemStack(ModItems.chemicalDusts,1,i), 
				"Dusts are produced inside the Chemical Extractor by processing shards. All the elements composing a shard will be collected and when a cabinet bar will fill up, a dust will be produced. To operate, the machine needs two conumables to be placed in their related slots: a Test Tube and a Graduated Cylinder.");
		}
		registry.addDescription(new ItemStack(ModBlocks.saltMaker), 
				"The Evaporation tank allows to obtain raw salt by letting evaporate a certain quantity of water.\n"
				+"It consists in filling the tank with a bucket of water and waiting it to dry out through the various phases. Evaporation will take some time depending on the biome type. In case of rain, any content of the tank will melt back into water. The final product of the tabk is though a raw salt that needs to be cleaned to be used.\n"
				+"Raw salt can be harvested from the tank manually by right clicking the top if the tank with a shovel.\n"
				+"The entire process can be automatized by piping water into the tank from any side, excluding the top. Then, placing next to the tank a Seasoning Rack, it will extract the raw salt smelting it into salt.");
		registry.addDescription(new ItemStack(ModBlocks.mineCrawler),
				"This device can be created inside the Mine Crawler Assembly Table. It can automatically mine paths up to a 3x2 grid with additional features. It uses the mod wrench to change the mining direction and be quickly removed from the ground.");
		registry.addDescription(new ItemStack(ModItems.miscItems, 1, 17), "Placed in the fuel slot of the mod machines it will also refill the power bar by converting the incoming RF. By default it can be moved through machines but from config, it can be set as a permanent upgrade for the machine.");
		registry.addDescription(new ItemStack(ModBlocks.saltSeasoner),
				"This device allows to acquire and turn the raw salt pruduced by the Evaporation Tank into the final salt used by recipes. It can handle up to four tanks placed on its sides. It can be also used as generic item processor.");
		registry.addDescription(new ItemStack(ModItems.petrographer),
				"This tools allows to improve the mineral mining by spotting the desired mineral category and specimen out of the undefined mineral. It has a leveling system which will increase its discovery ability. To set the tool it requires the Petrographer table where to put the items to learn. It is also required to enchant the tool as soon as possible with Fortune to make it work efficiently. When enchanted, the tool has also a chance to spot undefined minerals while mining stone.");
		registry.addDescription(new ItemStack(ModBlocks.chemicalExtractor),
				"This device allows to produce dusts for the elements listed in the cabinet. It takes two Fluids (Syngas and Hydrofluoric aacid) and two consumables (Test tube and Graduated Cylinder) to operate. processing a shard, the percentages of each element composing it will be stored in the cabinet. As the cabinet bar will fill, a regular dust will be produced.");
		registry.addDescription(new ItemStack(ModBlocks.mineralSizer),
				"This device allows to crack the Uninspected Mineral to let its category be identified. It also allows to perform other generic crushes. It is strongly suggested to apply an hopper under the device to free the slots for a continous working.");
		registry.addDescription(new ItemStack(ModItems.chemicalItems, 1, 0), "Sodium Polyacrylate is a polymer used to produce fake snow. Used on a filled cauldron or tossed in water it will absarb the water in the tile turning it into a snow block.");
		registry.addDescription(new ItemStack(ModItems.splashSmoke), "Throwing it in the world will create a small dense screen smoke, slowly vanishing.");

		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.laserBeam));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.smokeBlock));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModItems.chemBook));
	}
}