package com.globbypotato.rockhounding_chemistry.compat.jei;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower.DistillationRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower.DistillationRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower.DistillationRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.flametest.FlameRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.flametest.FlameRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.flametest.FlameRecipeWrapper;
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
import com.globbypotato.rockhounding_chemistry.compat.jei.petrographer.PetroRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.petrographer.PetroRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.petrographer.PetroRecipeWrapper;
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
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPetrographerTable;
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
				new DepositionRecipeCategory(guiHelper),
				new DistillationRecipeCategory(guiHelper),
				new PetroRecipeCategory(guiHelper),
				new FlameRecipeCategory(guiHelper));

		registry.addRecipeHandlers(
				new SizerRecipeHandler(),
				new AnalyzerRecipeHandler(),
				new ExtractorRecipeHandler(),
				new LabOvenRecipeHandler(),
				new SeasonerRecipeHandler(),
				new AlloyerRecipeHandler(),
				new DepositionRecipeHandler(),
				new DistillationRecipeHandler(),
				new PetroRecipeHandler(),
				new FlameRecipeHandler());

		registry.addRecipes(SizerRecipeWrapper.getRecipes());
		registry.addRecipes(AnalyzerRecipeWrapper.getRecipes());
		registry.addRecipes(ExtractorRecipeWrapper.getRecipes());
		registry.addRecipes(LabOvenRecipeWrapper.getRecipes());
		registry.addRecipes(SeasonerRecipeWrapper.getRecipes());
		registry.addRecipes(AlloyerRecipeWrapper.getRecipes());
		registry.addRecipes(DepositionRecipeWrapper.getRecipes());
		registry.addRecipes(DistillationRecipeWrapper.getRecipes());
		registry.addRecipes(PetroRecipeWrapper.getRecipes());
		registry.addRecipes(FlameRecipeWrapper.getRecipes());

		registry.addRecipeClickArea(GuiMineralSizer.class, 65, 15, 31, 31, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(GuiMineralAnalyzer.class, 27, 52, 33, 60, RHRecipeUID.ANALYZER);
		registry.addRecipeClickArea(GuiLabOven.class, 64, 57, 14, 15, RHRecipeUID.LAB_OVEN);
		registry.addRecipeClickArea(GuiSaltSeasoner.class, 72, 23, 32, 52, RHRecipeUID.SALT_SEASONER);
		registry.addRecipeClickArea(GuiMetalAlloyer.class, 99, 74, 14, 17, RHRecipeUID.ALLOYER);
		registry.addRecipeClickArea(GuiChemicalExtractor.class, 49, 47, 14, 32, RHRecipeUID.EXTRACTOR);
		registry.addRecipeClickArea(GuiDepositionChamber.class, 57, 56, 62, 14, RHRecipeUID.VAPOR_DEPOSITION);
		registry.addRecipeClickArea(GuiPetrographerTable.class, 68, 45, 40, 27, RHRecipeUID.PETROGRAPHER);

		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralSizer), RHRecipeUID.SIZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.mineralAnalyzer), RHRecipeUID.ANALYZER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.chemicalExtractor), RHRecipeUID.EXTRACTOR);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.labOven), RHRecipeUID.LAB_OVEN);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.saltSeasoner), RHRecipeUID.SALT_SEASONER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.metalAlloyer), RHRecipeUID.ALLOYER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.depositionChamber), RHRecipeUID.VAPOR_DEPOSITION);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.ganController), RHRecipeUID.DISTILLATION_TOWER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModItems.petrographer), RHRecipeUID.PETROGRAPHER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.petrographerTable), RHRecipeUID.PETROGRAPHER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.fireBlock), RHRecipeUID.FLAME_TEST);

		for(int i=0;i<EnumElement.size();i++){
			registry.addDescription(new ItemStack(ModItems.chemicalDusts,1,i), "Dusts are produced inside the Chemical Extractor by processing shards. All the elements composing a shard will be collected and when a cabinet bar will fill up, a dust will be produced. To operate, the machine needs two conumables to be placed in their related slots: a Test Tube and a Graduated Cylinder.");
		}
		registry.addDescription(new ItemStack(ModBlocks.saltMaker), 
				"The Evaporation tank allows to obtain raw salt by letting evaporate a certain quantity of water.\n"
				+"It consists in filling the tank with a bucket of water and waiting it to dry out through the various phases. Evaporation will take some time depending on the biome type. In case of rain, any content of the tank will melt back into water. The final product of the tabk is though a raw salt that needs to be cleaned to be used.\n"
				+"Raw salt can be harvested from the tank manually by right clicking the top if the tank with a shovel.\n"
				+"The entire process can be automatized by piping water into the tank from any side, excluding the top. Then, placing next to the tank a Seasoning Rack, it will extract the raw salt smelting it into salt.");
		registry.addDescription(new ItemStack(ModBlocks.mineCrawler), "This device can be created inside the Mine Crawler Assembly Table. It can automatically mine paths up to a 3x2 grid with additional features. It uses the mod wrench to change the mining direction and be quickly removed from the ground.");
		registry.addDescription(new ItemStack(ModBlocks.saltSeasoner), "This device allows to acquire and turn the raw salt pruduced by the Evaporation Tank into the final salt used by recipes. It can handle up to four tanks placed on its sides. It can be also used as generic item processor.");
		registry.addDescription(new ItemStack(ModItems.petrographer), "This tools allows to improve the mineral mining by spotting the desired mineral category and specimen out of the undefined mineral. It has a leveling system which will increase its discovery ability. To set the tool it requires the Petrographer table where to put the items to learn. It is also required to enchant the tool as soon as possible with Fortune to make it work efficiently. When enchanted, the tool has also a chance to spot undefined minerals while mining stone.");
		registry.addDescription(new ItemStack(ModBlocks.mineralAnalyzer), "This device allows to clean and expose shards of mineral from the raw ore containing them. To operate it requires three acids (sulfuric acid, hydrochloric acid and hydrofluoric acid) and an agitator to keep the processed slurry suspended in the solvent.");
		registry.addDescription(new ItemStack(ModBlocks.chemicalExtractor), "This device allows to produce dusts for the elements listed in the cabinet. It takes three Fluids (phosphoric acid, nitric acid and sodium cyanyde) and two consumables (Test tube and Graduated Cylinder) to operate. When processing a shard, the percentages of each element composing the sample, will be stored in the cabinet. As the cabinet bar will fill, a regular dust will be produced.");
		registry.addDescription(new ItemStack(ModBlocks.mineralSizer), "This device allows to crack the Uninspected Mineral to let its category be identified. It also allows to perform other generic crushes. It is strongly suggested to apply an hopper under the device to free the slots for a continous working.");
		registry.addDescription(new ItemStack(ModItems.chemicalItems, 1, 0), "Sodium Polyacrylate is a polymer used to produce fake snow. Used on a filled cauldron or tossed in water it will absarb the water in the tile turning it into a snow block.");
		registry.addDescription(new ItemStack(ModItems.splashSmoke), "Throwing it in the world will create a small dense screen smoke, slowly vanishing.");
		registry.addDescription(new ItemStack(ModBlocks.electroLaser), "The electrolaser is a system allowing to charge the laser beam emitted by a Redstone Laser emitter and amplify it. At this stage it is used as weapon, as it deals damage on any entity crossing the beam. The system can be modular with a cascade of amplifiers (up to 5) to increase the beam power hence the damage (up to 30HP/tick). The system is powered by RF and requires nitrogen to be ionized by the ray being shot. Each element consumes 20RF/tick of energy and 10mB/tick of liquid nitrogen, so it's necessary to have an energy supplier able to pull out more than required to make sure the storage is being filled and let the system work steadly. This is a scheme as seen from top of an electrolaser system:\n"
				+ "\n"
				+ "\n"
				+ "rR----SAAAAAE-----\n"
				+ "\n"
				+ "r = repeater\n"
				+ "R = Redstone Laser Emitter\n"
				+ "S = Laser Stabilizer\n"
				+ "A = Laser Amplifier\n"
				+ "E = Electrolaser\n"
				+ "- = The laser beams\n"
				+ "\n"
				+ "All the elements must face the same direction. The Stabilizer is not powered, while in each Amplifier added in the row and in the Electrolaser it must be supplied RF. To the Electrolaser must be also connected a tank with Liquid nitrogen. A gui will give informations about the resources being supplied and about the current state of the devices.");
		registry.addDescription(new ItemStack(ModBlocks.ganController), "The Cryogenic Distillation Plant is a multiblock structure that allows to extract elements by cooling down and processing air. It is composed by various parts, some of which have a gui to show what their work is about. The main block is the GAN Controller which is delegated to control the entire system and acquire the RF used to keep the structure working. The Pressure Vessel is the component acquiring and compressing the air. The Heat Exchanger cools down the air to be processed by using refrigerant liquids. The Nitrogen Tank is the final container where the produced nitrogen will be stored. These are the active blocks, the other ones complete the structure.\n"
				+ "This is a scheme as seen from top, of how to assembly the structure:\n"
				+ "XTE\n"
				+ "TCV\n"
				+ "\n"
				+ "X = TurboExchanger\n"
				+ "T = Distillation Tower x4\n"
				+ "E = Heat Exchanger\n"
				+ "V = Pressure Vessel\n"
				+ "T = Condenser + Tank above it\n"
				+ "C = GAN Controller\n"
				+ "The Heat Exchanger accepts any liquid and allows to reduce the energy required for the air processing in the Compression phase. By default the energy cost is 100RF/tick. By using Chloromethane (from this mod), the cost will be cut to 50RF/tick. By using Gelid Cryotheum (from Thermal Expansion) the energy cost will be only 10RF/tick.\n"
				+ "The Structure has two tiers, one allowing to build it straight away with basic materials, another to reinforce it with stronger materials. The Iron Tier acquires 1 unit of air per tick and produces 10mB of liquid nitrogen per tick. The Reinforced Tier allows to increase ten times these values.\n"
				+ "The system can work in two separated phases, selected by a switch in the controller. The first phase consists in acquiring and compressing the air, the second phase consists in producing the liquid nitrogen from the stored air. It is also available a general switch to turn on/off the structure and a Sanity Check telling if the system is correctly assembled.\n"
				+ "The liquid nitrogen can be extracted by connecting any fluid transport system to the Nitrogen tank. The system consumes 50RF/tick when at Iron Tier and 20RF/tick when at Reinforced Tier");

		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.laserBeam));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.laserRay));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.smokeBlock));
	}
}