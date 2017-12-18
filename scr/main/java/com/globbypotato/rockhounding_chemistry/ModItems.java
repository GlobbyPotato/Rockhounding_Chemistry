package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloy;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.enums.EnumFires;
import com.globbypotato.rockhounding_chemistry.enums.EnumItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumSpeeds;
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
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.items.ArrayIO;
import com.globbypotato.rockhounding_chemistry.items.ChemicalFires;
import com.globbypotato.rockhounding_chemistry.items.ChemicalItems;
import com.globbypotato.rockhounding_chemistry.items.ConsumableIO;
import com.globbypotato.rockhounding_chemistry.items.MineralShards;
import com.globbypotato.rockhounding_chemistry.items.MiscItems;
import com.globbypotato.rockhounding_chemistry.items.PatternItems;
import com.globbypotato.rockhounding_chemistry.items.SpeedItems;
import com.globbypotato.rockhounding_chemistry.items.UtilIO;
import com.globbypotato.rockhounding_chemistry.items.tools.BamShears;
import com.globbypotato.rockhounding_chemistry.items.tools.BamSword;
import com.globbypotato.rockhounding_chemistry.items.tools.CubeCrossbow;
import com.globbypotato.rockhounding_chemistry.items.tools.Petrographer;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBat;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBow;
import com.globbypotato.rockhounding_chemistry.items.tools.SiliconeCartridge;
import com.globbypotato.rockhounding_chemistry.items.tools.SplashSmoke;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;

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

	public static Item chemicalItems;
	public static Item miscItems;
	public static Item alloyItems;
	public static Item alloyBItems;
	public static Item speedItems;

	public static ConsumableIO gear;
	public static ConsumableIO testTube;
	public static ConsumableIO cylinder;
	public static ConsumableIO ingotPattern;
	public static ConsumableIO agitator;
	public static ConsumableIO feCatalyst;
	public static ConsumableIO ptCatalyst;
	public static SiliconeCartridge siliconeCartridge;
	public static UtilIO chemFlask;

	public static Item cubeCrossbow;
	public static Item scalBow;
	public static ToolMaterial scalMaterial = EnumHelper.addToolMaterial("scalMaterial", 2, 400, 2.0F, 2F, 10);
	public static Item scalBat;
	public static ToolMaterial bamMaterial = EnumHelper.addToolMaterial("bamMaterial", 2, 500, 2.0F, 2F, 10);
	public static Item bamShears;
	public static Item bamSword;
	public static Item petrographer;
	public static SplashSmoke splashSmoke;

	public static Item patternItems;

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
		chemicalDusts = new ArrayIO("chemicalDusts", EnumElement.getNames());
		chemicalItems = new ChemicalItems("chemicalItems", EnumChemicals.getNames());										
		miscItems = new MiscItems("miscItems", EnumItems.getNames());
		alloyItems = new ArrayIO("alloyItems", EnumAlloy.getItemNames());	
		alloyBItems = new ArrayIO("alloyBItems", EnumAlloyB.getItemNames());	
		chemicalFires = new ChemicalFires("chemicalFires", EnumFires.getNames());										
		speedItems = new SpeedItems("speedItems", EnumSpeeds.getNames());	

		gear = new ConsumableIO("gear", ModConfig.gearUses);
		testTube = new ConsumableIO("testTube", ModConfig.tubeUses);
		cylinder = new ConsumableIO("cylinder", ModConfig.tubeUses);
		ingotPattern = new ConsumableIO("ingotPattern", ModConfig.patternUses);
		agitator = new ConsumableIO("agitator", ModConfig.agitatorUses);
		feCatalyst = new ConsumableIO("feCatalyst", ModConfig.catalystUses);
		ptCatalyst = new ConsumableIO("ptCatalyst", ModConfig.catalystUses);
		chemFlask = new UtilIO("chemFlask");
		splashSmoke = new SplashSmoke("splashSmoke");

		//tools
		cubeCrossbow = new CubeCrossbow("cubeCrossbow");
		scalBow = new ScalBow(scalMaterial, "scalBow");
		scalBat = new ScalBat(scalMaterial, "scalBat");
		bamShears = new BamShears(bamMaterial, "bamShears");
		bamSword = new BamSword(bamMaterial, "bamSword");
		petrographer = new Petrographer(ToolMaterial.DIAMOND, "petrographer");
		siliconeCartridge = new SiliconeCartridge("siliconeCartridge", 100);
		
		//dummy
		patternItems = new PatternItems("patternItems", EnumCasting.getNames());										

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
		for(int i = 0; i < EnumCasting.size(); i++){				registerMetaItemRender(patternItems, i, EnumCasting.getName(i));		}
		for(int i = 0; i < EnumSpeeds.size(); i++){					registerMetaItemRender(speedItems, i, EnumSpeeds.getName(i));			}
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
		agitator.initModel();
		feCatalyst.initModel();
		ptCatalyst.initModel();
		chemFlask.initModel();
		splashSmoke.initModel();
		siliconeCartridge.initModel();
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