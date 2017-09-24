package com.globbypotato.rockhounding_chemistry.compat.jei;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.castingbench.CastingRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.castingbench.CastingRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.castingbench.CastingRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.chemicalextractor.ExtractorRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower.DistillationRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower.DistillationRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.distillationtower.DistillationRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.flametest.FlameRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.flametest.FlameRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.flametest.FlameRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.gan.GanRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.gan.GanRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.gan.GanRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.compat.jei.labblender.BlenderRecipeCategory;
import com.globbypotato.rockhounding_chemistry.compat.jei.labblender.BlenderRecipeHandler;
import com.globbypotato.rockhounding_chemistry.compat.jei.labblender.BlenderRecipeWrapper;
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
import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiCastingBench;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiDepositionChamber;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabBlender;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLabOven;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPetrographerTable;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiSaltSeasoner;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

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
				new FlameRecipeCategory(guiHelper),
				new CastingRecipeCategory(guiHelper),
				new BlenderRecipeCategory(guiHelper),
				new GanRecipeCategory(guiHelper));

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
				new FlameRecipeHandler(),
				new CastingRecipeHandler(),
				new BlenderRecipeHandler(),
				new GanRecipeHandler());

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
		registry.addRecipes(CastingRecipeWrapper.getRecipes());
		registry.addRecipes(BlenderRecipeWrapper.getRecipes());
		registry.addRecipes(GanRecipeWrapper.getRecipes());

		registry.addRecipeClickArea(GuiMineralSizer.class, 52, 39, 31, 31, RHRecipeUID.SIZER);
		registry.addRecipeClickArea(GuiMineralAnalyzer.class, 73, 29, 22, 22, RHRecipeUID.ANALYZER);
		registry.addRecipeClickArea(GuiLabOven.class, 64, 57, 14, 15, RHRecipeUID.LAB_OVEN);
		registry.addRecipeClickArea(GuiSaltSeasoner.class, 72, 23, 32, 52, RHRecipeUID.SALT_SEASONER);
		registry.addRecipeClickArea(GuiMetalAlloyer.class, 41, 53, 128, 13, RHRecipeUID.ALLOYER);
		registry.addRecipeClickArea(GuiMetalAlloyer.class, 40, 66, 18, 13, RHRecipeUID.ALLOYER);
		registry.addRecipeClickArea(GuiChemicalExtractor.class, 49, 47, 14, 32, RHRecipeUID.EXTRACTOR);
		registry.addRecipeClickArea(GuiDepositionChamber.class, 57, 56, 62, 14, RHRecipeUID.VAPOR_DEPOSITION);
		registry.addRecipeClickArea(GuiPetrographerTable.class, 68, 45, 40, 27, RHRecipeUID.PETROGRAPHER);
		registry.addRecipeClickArea(GuiCastingBench.class, 72, 33, 32, 14, RHRecipeUID.CASTING);
		registry.addRecipeClickArea(GuiLabBlender.class, 108, 39, 31, 31, RHRecipeUID.BLENDER);

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
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.castingBench), RHRecipeUID.CASTING);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.labBlender), RHRecipeUID.BLENDER);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.ganController), RHRecipeUID.GAN);
		registry.addRecipeCategoryCraftingItem(new ItemStack(ModBlocks.ganBlocks, 1, OreDictionary.WILDCARD_VALUE), RHRecipeUID.GAN);

		for(int i=0;i<EnumElement.size();i++){
			registry.addDescription(BaseRecipes.elements(1, i), "Dusts are produced inside the Chemical Extractor by processing shards. All the elements composing a shard will be collected and when a cabinet bar will fill up, a dust will be produced. To operate, the machine needs two conumables to be placed in their related slots: a Test Tube and a Graduated Cylinder.");
		}
		registry.addDescription(new ItemStack(ModBlocks.saltMaker), 
				"The Evaporation tank allows to obtain raw salt by letting evaporate a certain quantity of water.\n"
				+"It consists in filling the tank with a bucket of water and waiting it to dry out through the various phases. Evaporation will take some time depending on the biome type. In case of rain, any content of the tank will melt back into water. The final product of the tabk is though a raw salt that needs to be cleaned to be used.\n"
				+"Raw salt can be harvested from the tank manually by right clicking the top if the tank with a shovel.\n"
				+"The entire process can be automatized by piping water into the tank from any side, excluding the top. Then, placing next to the tank a Seasoning Rack, it will extract the raw salt smelting it into salt.");
		registry.addDescription(BaseRecipes.saltRaw, "This is the raw for of the salt as it is produced by the evaporation tank. It must be placed inside the Seasoning rack to be dried or let the block acquire it itself as per the salt automation process.");
		String crawlerText = "The Mine Crawler is a mining device that can automatically mine paths up to a 3x2 grid with additional features. It is an automatic machine, hence no power is required and can be created inside the Mine Crawler Assembly Table out of the various parts composing it. It uses the mod wrench to change the mining direction and to be quickly removed from the ground.\n"
				+ "Inside the assembly table is possible to define the amount of mined blocks by setting a tier. The tier depends on the placements of the mining heads on the grid.\n"
				+ "This is the list of tiers.\n"
				+ "The 'X' represents a mining head on the grid:\n" 		
				+ "T 1 - T 2  - T 3  - T 4\n"
				+ "OOO - OXO - OOO - XXX\n"
				+ "OXO - OXO - XXX - XXX\n";
		registry.addDescription(new ItemStack(ModBlocks.mineCrawler), crawlerText);
		registry.addDescription(new ItemStack(ModBlocks.crawlerAssembler), crawlerText);
		registry.addDescription(new ItemStack(ModBlocks.saltSeasoner), "This device allows to acquire and turn the raw salt pruduced by the Evaporation Tank into the final salt used by recipes. It can handle up to four tanks placed on its sides. It can be also used as generic item processor.");
		registry.addDescription(new ItemStack(ModItems.petrographer), "This tools allows to improve the mineral mining by spotting the desired mineral category and specimen out of the undefined mineral. It has a leveling system which will increase its discovery ability. To set the tool it requires the Petrographer table where to put the items to learn. It is also required to enchant the tool as soon as possible with Fortune to make it work efficiently. When enchanted, the tool has also a chance to spot undefined minerals while mining stone.");
		registry.addDescription(new ItemStack(ModBlocks.mineralAnalyzer), "This device allows to clean and expose shards of minerals from the gangues containing them. To operate it requires three acids (sulfuric acid, hydrochloric acid and hydrofluoric acid) and a Slurry Agitator to keep the processed slurry suspended in the solvent. Since a gangue may contain many specimen, it is possible to restrict the filter and capture only the specimens having a specific range of density. This will require a variable amount of solvents to perform the process.");
		registry.addDescription(new ItemStack(ModBlocks.chemicalExtractor), "This device allows to produce dusts for the elements listed in the cabinet. It takes three Fluids (phosphoric acid, nitric acid and sodium cyanyde) and two consumables (Test tube and Graduated Cylinder) to operate. When processing a shard, the percentages of each element composing the sample, will be stored in the cabinet. As the cabinet bar will fill, a regular dust will be produced.");
		registry.addDescription(new ItemStack(ModBlocks.mineralSizer), "This device allows to crack the Uninspected Mineral to let the bearing gangues be exposed and identified. Different classes of mineral may require an heavier treatment due to the size or the rarity of their occurrence. Each class of mineral is then identified by a level of comminution, determining how fine the crushed grain will be. A selector in the gui and JEI tooltips will provide several informations about the required settings. Heavier passages will require more fuel and will consume the gear faster. The sizer also allows to perform other generic crushing recipes related to the mod. In this cases the comminution is nor relievant. It is strongly suggested to apply an hopper or an extraction system to the device, freeing the slots for a continous working.");
		registry.addDescription(BaseRecipes.polymer, "Sodium Polyacrylate is a polymer used to produce fake snow. Used on a filled cauldron or tossed in water it will absarb the water in the tile turning it into a snow block.");
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
		registry.addDescription(new ItemStack(ModBlocks.ganController), "The Cryogenic Distillation Plant is a multiblock structure that allows to extract elements by cooling down and processing air. It is composed by various parts, some of which have a gui to show what their work is about. The main block is the GAN Controller which is delegated to control the entire system and acquire the RF used to keep the structure working. The Pressure Vessel is the component acquiring and compressing the air. The Heat Exchanger cools down the air to be processed by using refrigerant liquids. The Nitrogen Tank is the final container where the produced nitrogen will be stored. A JAI tab will show how to place the various blocks.\n"
				+ "The Heat Exchanger accepts any liquid having a Temperature not higher than 300K and allows to reduce the energy required for the air processing in the Compression phase. The temperature of the liquid will determine the amount of RF required to cool down the air.\n"
				+ "The Structure has two tiers, one allowing to build it straight away with basic materials, another to reinforce it with stronger materials. The Iron Tier acquires 1 unit of air per tick and produces 10mB of liquid nitrogen per tick. The Reinforced Tier allows to increase ten times these values.\n"
				+ "The system can work in two separated phases, selected by a switch in the controller. The first phase consists in acquiring and compressing the air, the second phase consists in producing the liquid nitrogen from the stored air. It is also available a general switch to turn on/off the structure and a Sanity Check telling if the system is correctly assembled.\n"
				+ "An addituinal function allows to perform a continous production alternating both phases. This function requires of course more energy.\n"
				+ "The liquid nitrogen can be extracted by connecting any fluid transport system to the Nitrogen tank. The system consumes 50RF/tick when at Iron Tier and 20RF/tick when at Reinforced Tier");
		registry.addDescription(new ItemStack(ModBlocks.castingBench), "This is an automatic machine used to trim some of the metal furnitures used by the mod. The purpose of this machine is to get rid of many little crafting recipes that could be redundant or possibly conflicting with others. It has different types of cuts and can be automated with pipes or manually interacted, no gui is available. Punching the machine with bare hand, it will scroll through the various cuts and the selected one will be indicated by a label in front of it. Using a valid recipe ingredient on it, will place the item inside the machine to start operating. Right-clicking again the machine will take out the ingredient or the processed output.");
		String piplineText = "The pipeline allows to transfer fluids between fluid handlers. It is composed of 3 parts, a pump to extract the fluid, a valve to deliver the fluid and ducts for connections. When placed, pump and valve are disabled by default. To activate them it requires to be right-clicked with bare hands while sneaking. The valve is provided of a gui in which is possible to disable each of the ejecting sides or filter a side to a specific fluid. The pump comes in two tiers: the normal tier allows to transfer 1 bucket at time, while the advanced tier allows to transfer 100 buckets at time. The upgrade is given by a crafted item applied by righr-clicking the pump while holding it in hand. Longer pipline may require a slightly longer time to deliver. For a matter of balance, both pump and valve are at least require to form a pipline. A valce can be directly connected to a pump for the shorted setup possible. So far the fluids are delivered by the Valve routing between the available directions D-U-N-S-W-E";
		registry.addDescription(new ItemStack(ModBlocks.pipelinePump), piplineText);
		registry.addDescription(new ItemStack(ModBlocks.pipelineDuct), piplineText);
		registry.addDescription(new ItemStack(ModBlocks.pipelineValve), piplineText);
		registry.addDescription(BaseRecipes.pipelineUpgrade, piplineText);

		
		
		IIngredientBlacklist itemBlacklist = registry.getJeiHelpers().getIngredientBlacklist();
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.laserBeam));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.laserRay));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.smokeBlock));
		itemBlacklist.addIngredientToBlacklist(new ItemStack(ModBlocks.depositionChamberTop));
		itemBlacklist.addIngredientToBlacklist(BaseRecipes.miscs(1,53));
		itemBlacklist.addIngredientToBlacklist(BaseRecipes.miscs(1,54));
		for(int i=0;i<EnumCasting.size();i++){
			itemBlacklist.addIngredientToBlacklist(BaseRecipes.patterns(1,i));
		}
		itemBlacklist.addIngredientToBlacklist(BaseRecipes.gan(12));
		itemBlacklist.addIngredientToBlacklist(BaseRecipes.gan(13));
		itemBlacklist.addIngredientToBlacklist(BaseRecipes.gan(14));

	}
}