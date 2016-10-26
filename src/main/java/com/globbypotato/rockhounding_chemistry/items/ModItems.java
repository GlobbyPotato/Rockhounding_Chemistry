package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.items.tools.BamShears;
import com.globbypotato.rockhounding_chemistry.items.tools.BamSword;
import com.globbypotato.rockhounding_chemistry.items.tools.CubeCrossbow;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBat;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBow;

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

	public static Item chemBook;
	public static Item chemicalItems;
	public static Item miscItems;
	public static Item alloyItems;
	public static Item alloyBItems;

	public static ItemConsumable gear;
	public static ItemConsumable testTube;
	public static ItemConsumable cylinder;
	public static ItemConsumable ingotPattern;

	public static Item cubeCrossbow;
	public static Item scalBow;
	public static ToolMaterial scalMaterial = EnumHelper.addToolMaterial("scalMaterial", 2, 400, 2.0F, 2F, 10);
	public static Item scalBat;
	public static ToolMaterial bamMaterial = EnumHelper.addToolMaterial("bamMaterial", 2, 500, 2.0F, 2F, 10);
	public static Item bamShears;
	public static Item bamSword;

	public static void init(){
		//items
		arsenateShards = new MineralShards("arsenateShards", ModArray.arsenateShardsArray);
		borateShards = new MineralShards("borateShards", ModArray.borateShardsArray);
		carbonateShards = new MineralShards("carbonateShards", ModArray.carbonateShardsArray);
		halideShards = new MineralShards("halideShards", ModArray.halideShardsArray);
		nativeShards = new MineralShards("nativeShards", ModArray.nativeShardsArray);
		oxideShards = new MineralShards("oxideShards", ModArray.oxideShardsArray);
		phosphateShards = new MineralShards("phosphateShards", ModArray.phosphateShardsArray);
		silicateShards = new MineralShards("silicateShards", ModArray.silicateShardsArray);
		sulfateShards = new MineralShards("sulfateShards", ModArray.sulfateShardsArray);
		sulfideShards = new MineralShards("sulfideShards", ModArray.sulfideShardsArray);
		chemicalDusts = new ChemicalDusts("chemicalDusts", EnumElement.getNames());
		chemicalItems = new ChemicalItems("chemicalItems", ModArray.chemicalItemsArray);										
		miscItems = new MiscItems("miscItems", ModArray.miscItemsArray);
		chemBook = new ChemBook("chemBook");
		alloyItems = new AlloyItems("alloyItems", ModArray.alloyItemArray);	
		alloyBItems = new AlloyItems("alloyBItems", ModArray.alloyBItemArray);	
		
		gear = new ItemConsumable("gear", ModConfig.gearUses);
		testTube = new ItemConsumable("testTube", ModConfig.tubeUses);
		cylinder = new ItemConsumable("cylinder", ModConfig.tubeUses);
		ingotPattern = new ItemConsumable("ingotPattern", ModConfig.patternUses);
		
		//tools
		cubeCrossbow = new CubeCrossbow("cubeCrossbow");
		scalBow = new ScalBow(scalMaterial, "scalBow");
		scalBat = new ScalBat(scalMaterial, "scalBat");
		bamShears = new BamShears(bamMaterial, "bamShears");
		bamSword = new BamSword(bamMaterial, "bamSword");
	}
	
	public static void initClient(){
		//items
		for(int i = 0; i < ModArray.arsenateShardsArray.length; i++){	registerMetaItemRender(arsenateShards, i, ModArray.arsenateShardsArray[i]);		}
		for(int i = 0; i < ModArray.borateShardsArray.length; i++){		registerMetaItemRender(borateShards, i, ModArray.borateShardsArray[i]);			}
		for(int i = 0; i < ModArray.carbonateShardsArray.length; i++){	registerMetaItemRender(carbonateShards, i, ModArray.carbonateShardsArray[i]);	}
		for(int i = 0; i < ModArray.halideShardsArray.length; i++){		registerMetaItemRender(halideShards, i, ModArray.halideShardsArray[i]);			}
		for(int i = 0; i < ModArray.nativeShardsArray.length; i++){		registerMetaItemRender(nativeShards, i, ModArray.nativeShardsArray[i]);			}
		for(int i = 0; i < ModArray.oxideShardsArray.length; i++){		registerMetaItemRender(oxideShards, i, ModArray.oxideShardsArray[i]);			}
		for(int i = 0; i < ModArray.phosphateShardsArray.length; i++){	registerMetaItemRender(phosphateShards, i, ModArray.phosphateShardsArray[i]);	}
		for(int i = 0; i < ModArray.silicateShardsArray.length; i++){	registerMetaItemRender(silicateShards, i, ModArray.silicateShardsArray[i]);		}
		for(int i = 0; i < ModArray.sulfateShardsArray.length; i++){	registerMetaItemRender(sulfateShards, i, ModArray.sulfateShardsArray[i]);		}
		for(int i = 0; i < ModArray.sulfideShardsArray.length; i++){	registerMetaItemRender(sulfideShards, i, ModArray.sulfideShardsArray[i]);		}
		for(int i = 0; i < EnumElement.size(); i++){	registerMetaItemRender(chemicalDusts, i, EnumElement.getName(i));		}
		for(int i = 0; i < ModArray.chemicalItemsArray.length; i++){	registerMetaItemRender(chemicalItems, i, ModArray.chemicalItemsArray[i]);		}
		for(int i = 0; i < ModArray.miscItemsArray.length; i++){		registerMetaItemRender(miscItems, i, ModArray.miscItemsArray[i]);				}
		for(int i = 0; i < ModArray.alloyItemArray.length; i++){		registerMetaItemRender(alloyItems, i, ModArray.alloyItemArray[i]);				}
		for(int i = 0; i < ModArray.alloyBItemArray.length; i++){		registerMetaItemRender(alloyBItems, i, ModArray.alloyBItemArray[i]);			}
		registerSimpleItemRender(chemBook, 0, "chemBook");
		registerSimpleItemRender(cubeCrossbow, 0, "cubeCrossbow");
		registerSimpleItemRender(scalBow, 0, "scalBow");
		registerSimpleItemRender(scalBat, 0, "scalBat");
		registerSimpleItemRender(bamShears, 0, "bamShears");
		registerSimpleItemRender(bamSword, 0, "bamSword");
		//registerSimpleItemRender(gear,0,"gear");
		gear.initModel();
		testTube.initModel();
		cylinder.initModel();
		ingotPattern.initModel();
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
