package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.enums.EnumFires;
import com.globbypotato.rockhounding_chemistry.enums.EnumItems;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumArsenate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumBorate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumCarbonate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumHalide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumNative;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumOxide;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumPhosphate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSilicate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfate;
import com.globbypotato.rockhounding_chemistry.enums.shards.EnumSulfide;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.items.ChemBook;
import com.globbypotato.rockhounding_chemistry.items.ChemicalFires;
import com.globbypotato.rockhounding_chemistry.items.ChemicalItems;
import com.globbypotato.rockhounding_chemistry.items.ItemArray;
import com.globbypotato.rockhounding_chemistry.items.ItemConsumable;
import com.globbypotato.rockhounding_chemistry.items.ItemUtils;
import com.globbypotato.rockhounding_chemistry.items.MineralShards;
import com.globbypotato.rockhounding_chemistry.items.MiscItems;
import com.globbypotato.rockhounding_chemistry.items.SiliconeCartridge;
import com.globbypotato.rockhounding_chemistry.items.tools.BamShears;
import com.globbypotato.rockhounding_chemistry.items.tools.BamSword;
import com.globbypotato.rockhounding_chemistry.items.tools.CubeCrossbow;
import com.globbypotato.rockhounding_chemistry.items.tools.Petrographer;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBat;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBow;
import com.globbypotato.rockhounding_chemistry.items.tools.SplashSmoke;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fluids.FluidRegistry;

public class ModItems {

	public static Item arsenateShards;
	public static Item borateShards;
	public static Item carbonateShards;
	public static Item halideShards;
	public static Item nativeShards;
	public static Item oxideShards;
	public static Item phosphateShards;
	public static Item silicateShards;
	public static Item sulfateShards;
	public static Item sulfideShards;
	public static Item chemicalDusts;
	public static Item chemicalFires;

	public static Item chemBook;
	public static Item chemicalItems;
	public static Item miscItems;
	public static Item alloyItems;
	public static Item alloyBItems;

	public static ItemConsumable gear;
	public static ItemConsumable testTube;
	public static ItemConsumable cylinder;
	public static ItemConsumable ingotPattern;
	public static SiliconeCartridge siliconeCartridge;
	public static ItemUtils chemFlask;

	public static Item cubeCrossbow;
	public static Item scalBow;
	public static ToolMaterial scalMaterial = EnumHelper.addToolMaterial("scalMaterial", 2, 400, 2.0F, 2F, 10);
	public static Item scalBat;
	public static ToolMaterial bamMaterial = EnumHelper.addToolMaterial("bamMaterial", 2, 500, 2.0F, 2F, 10);
	public static Item bamShears;
	public static Item bamSword;
	public static Item petrographer;
	public static SplashSmoke splashSmoke;

	public static void init(){
		//items
		arsenateShards = new MineralShards("arsenateShards", EnumArsenate.getNames());
		borateShards = new MineralShards("borateShards", EnumBorate.getNames());
		carbonateShards = new MineralShards("carbonateShards", EnumCarbonate.getNames());
		halideShards = new MineralShards("halideShards", EnumHalide.getNames());
		nativeShards = new MineralShards("nativeShards", EnumNative.getNames());
		oxideShards = new MineralShards("oxideShards", EnumOxide.getNames());
		phosphateShards = new MineralShards("phosphateShards", EnumPhosphate.getNames());
		silicateShards = new MineralShards("silicateShards", EnumSilicate.getNames());
		sulfateShards = new MineralShards("sulfateShards", EnumSulfate.getNames());
		sulfideShards = new MineralShards("sulfideShards", EnumSulfide.getNames());
		chemicalDusts = new ItemArray("chemicalDusts", EnumElement.getNames());
		chemicalItems = new ChemicalItems("chemicalItems", EnumChemicals.getNames());										
		miscItems = new MiscItems("miscItems", EnumItems.getNames());
		chemBook = new ChemBook("chemBook");
		alloyItems = new ItemArray("alloyItems", EnumAlloy.getItemNames());	
		alloyBItems = new ItemArray("alloyBItems", EnumAlloyB.getItemNames());	
		chemicalFires = new ChemicalFires("chemicalFires", EnumFires.getNames());										

		gear = new ItemConsumable("gear", ModConfig.gearUses);
		testTube = new ItemConsumable("testTube", ModConfig.tubeUses);
		cylinder = new ItemConsumable("cylinder", ModConfig.tubeUses);
		ingotPattern = new ItemConsumable("ingotPattern", ModConfig.patternUses);
		chemFlask = new ItemUtils("chemFlask");
		splashSmoke = new SplashSmoke("splashSmoke");

		//tools
		cubeCrossbow = new CubeCrossbow("cubeCrossbow");
		scalBow = new ScalBow(scalMaterial, "scalBow");
		scalBat = new ScalBat(scalMaterial, "scalBat");
		bamShears = new BamShears(bamMaterial, "bamShears");
		bamSword = new BamSword(bamMaterial, "bamSword");
		petrographer = new Petrographer(ToolMaterial.DIAMOND, "petrographer");
		siliconeCartridge = new SiliconeCartridge("siliconeCartridge", 100);
	}

	public static void initClient(){
		//items
		for(int i = 0; i < EnumArsenate.size(); i++){				registerMetaItemRender(arsenateShards, i, EnumArsenate.getName(i));		}
		for(int i = 0; i < EnumBorate.size(); i++){					registerMetaItemRender(borateShards, i, EnumBorate.getName(i));			}
		for(int i = 0; i < EnumCarbonate.size(); i++){				registerMetaItemRender(carbonateShards, i, EnumCarbonate.getName(i));	}
		for(int i = 0; i < EnumHalide.size(); i++){					registerMetaItemRender(halideShards, i, EnumHalide.getName(i));			}
		for(int i = 0; i < EnumNative.size(); i++){					registerMetaItemRender(nativeShards, i, EnumNative.getName(i));			}
		for(int i = 0; i < EnumOxide.size(); i++){					registerMetaItemRender(oxideShards, i, EnumOxide.getName(i));			}
		for(int i = 0; i < EnumPhosphate.size(); i++){				registerMetaItemRender(phosphateShards, i, EnumPhosphate.getName(i));	}
		for(int i = 0; i < EnumSilicate.size(); i++){				registerMetaItemRender(silicateShards, i, EnumSilicate.getName(i));		}
		for(int i = 0; i < EnumSulfate.size(); i++){				registerMetaItemRender(sulfateShards, i, EnumSulfate.getName(i));		}
		for(int i = 0; i < EnumSulfide.size(); i++){				registerMetaItemRender(sulfideShards, i, EnumSulfide.getName(i));		}
		for(int i = 0; i < EnumElement.size(); i++){				registerMetaItemRender(chemicalDusts, i, EnumElement.getName(i));		}
		for(int i = 0; i < EnumChemicals.size(); i++){				registerMetaItemRender(chemicalItems, i, EnumChemicals.getName(i));		}
		for(int i = 0; i < EnumItems.size(); i++){					registerMetaItemRender(miscItems, i, EnumItems.getName(i));				}
		for(int i = 0; i < EnumAlloy.getItemNames().length; i++){	registerMetaItemRender(alloyItems, i, EnumAlloy.getItemName(i));		}
		for(int i = 0; i < EnumAlloyB.getItemNames().length; i++){	registerMetaItemRender(alloyBItems, i, EnumAlloyB.getItemName(i));		}
		for(int i = 0; i < EnumFires.size(); i++){					registerMetaItemRender(chemicalFires, i, EnumFires.getName(i));			}
		registerSimpleItemRender(chemBook, 0, "chemBook");
		registerSimpleItemRender(cubeCrossbow, 0, "cubeCrossbow");
		registerSimpleItemRender(scalBow, 0, "scalBow");
		registerSimpleItemRender(scalBat, 0, "scalBat");
		registerSimpleItemRender(bamShears, 0, "bamShears");
		registerSimpleItemRender(bamSword, 0, "bamSword");
		registerSimpleItemRender(petrographer, 0, "petrographer");
		gear.initModel();
		testTube.initModel();
		cylinder.initModel();
		ingotPattern.initModel();
		chemFlask.initModel();
		splashSmoke.initModel();
		siliconeCartridge.initModel();

		if( !FluidRegistry.isUniversalBucketEnabled() ){
			registerSimpleItemRender(ModFluids.beaker, 0, "beaker");
			registerSimpleItemRender(ModFluids.waterBeaker, 0, "waterBeaker");
			registerSimpleItemRender(ModFluids.lavaBeaker, 0, "lavaBeaker");
			registerSimpleItemRender(ModFluids.sulfuricAcidBeaker, 0, "sulfuricAcidBeaker");
			registerSimpleItemRender(ModFluids.hydrochloricAcidBeaker, 0, "hydrochloricAcidBeaker");
			registerSimpleItemRender(ModFluids.hydrofluoricAcidBeaker, 0, "hydrofluoricAcidBeaker");
			registerSimpleItemRender(ModFluids.syngasBeaker, 0, "syngasBeaker");
			registerSimpleItemRender(ModFluids.acrylicAcidBeaker, 0, "acrylicAcidBeaker");
			registerSimpleItemRender(ModFluids.chloromethaneBeaker, 0, "chloromethaneBeaker");
			registerSimpleItemRender(ModFluids.siliconeBeaker, 0, "siliconeBeaker");
			registerSimpleItemRender(ModFluids.ammoniaBeaker, 0, "ammoniaBeaker");
			registerSimpleItemRender(ModFluids.nitricAcidBeaker, 0, "nitricAcidBeaker");
			registerSimpleItemRender(ModFluids.titaniumTetrachlorideBeaker, 0, "titaniumTetrachlorideBeaker");
			registerSimpleItemRender(ModFluids.sodiumCyanideBeaker, 0, "sodiumCyanideBeaker");
			registerSimpleItemRender(ModFluids.phosphoricAcidBeaker, 0, "phosphoricAcidBeaker");
		}

	}
	
	
	//render meta item
	public static void registerMetaItemRender(Item item, int meta, String fileName){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName() + "_" + fileName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
	//render simple item
	public static void registerSimpleItemRender(Item item, int meta, String fileName){
		ModelResourceLocation model = new ModelResourceLocation(item.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
}