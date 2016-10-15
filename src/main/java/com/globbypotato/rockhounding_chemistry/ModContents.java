package com.globbypotato.rockhounding_chemistry;

import com.globbypotato.rockhounding_chemistry.blocks.AlloyBBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBBricks;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.AlloyBricks;
import com.globbypotato.rockhounding_chemistry.blocks.CrawlerIB;
import com.globbypotato.rockhounding_chemistry.blocks.MineralOres;
import com.globbypotato.rockhounding_chemistry.blocks.SimpleIB;
import com.globbypotato.rockhounding_chemistry.blocks.TiersIB;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.items.AlloyItems;
import com.globbypotato.rockhounding_chemistry.items.ChemBook;
import com.globbypotato.rockhounding_chemistry.items.ChemicalDusts;
import com.globbypotato.rockhounding_chemistry.items.ChemicalItems;
import com.globbypotato.rockhounding_chemistry.items.MineralShards;
import com.globbypotato.rockhounding_chemistry.items.MiscItems;
import com.globbypotato.rockhounding_chemistry.items.tools.BamShears;
import com.globbypotato.rockhounding_chemistry.items.tools.BamSword;
import com.globbypotato.rockhounding_chemistry.items.tools.CubeCrossbow;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBat;
import com.globbypotato.rockhounding_chemistry.items.tools.ScalBow;
import com.globbypotato.rockhounding_chemistry.machines.ChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.CrawlerAssembler;
import com.globbypotato.rockhounding_chemistry.machines.LabOven;
import com.globbypotato.rockhounding_chemistry.machines.LaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.LaserRX;
import com.globbypotato.rockhounding_chemistry.machines.LaserSplitter;
import com.globbypotato.rockhounding_chemistry.machines.LaserTX;
import com.globbypotato.rockhounding_chemistry.machines.MetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.MineCrawler;
import com.globbypotato.rockhounding_chemistry.machines.MineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.MineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.SaltMaker;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
// PIN sends the signal vertically

public class ModContents {
	public static Block mineralOres;
	public static Block alloyBlocks;
	public static Block alloyBBlocks;
	public static Block alloyBricks;
	public static Block alloyBBricks;

	public static final int labOvenID = 0;
	public static Block labOven;
	public static final int mineralSizerID = 1;
	public static Block mineralSizer;
	public static final int mineralAnalyzerID = 2;
	public static Block mineralAnalyzer;
	public static final int chemicalExtractorID = 3;
	public static Block crawlerAssembler;
	public static final int crawlerAssemblerID = 4;
	public static Block metalAlloyer;
	public static final int metalAlloyerID = 5;

	public static Block chemicalExtractor;
	public static Block saltMaker;
	public static Block mineCrawler;
	public static Block laserRedstoneTx;
	public static Block laserRedstoneRx;
	public static Block laserBeam;
	public static Block laserSplitter;

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

	public static Item cubeCrossbow;
	public static Item scalBow;
	public static ToolMaterial scalMaterial = EnumHelper.addToolMaterial("scalMaterial", 2, 400, 2.0F, 2F, 10);
	public static Item scalBat;
	public static ToolMaterial bamMaterial = EnumHelper.addToolMaterial("bamMaterial", 2, 500, 2.0F, 2F, 10);
	public static Item bamShears;
	public static Item bamSword;

	//config
	public static int maxMineral;
	public static int gearUses;
	public static int tubeUses;
	public static int patternUses;
	public static boolean enableRainRefill;
	public static boolean forceSmelting;

	//initialize the block
	public static void init() {
		//blocks
		mineralOres = new MineralOres(3.0F, 5.0F, "mineralOres");
		alloyBlocks = new AlloyBlocks(3.0F, 5.0F, "alloyBlocks");
		alloyBBlocks = new AlloyBBlocks(3.0F, 5.0F, "alloyBBlocks");
		alloyBricks = new AlloyBricks(3.0F, 5.0F, "alloyBricks");
		alloyBBricks = new AlloyBBricks(3.0F, 5.0F, "alloyBBricks");

		labOven = new LabOven(3.0F, 5.0F, "labOven");
		mineralSizer = new MineralSizer(3.0F, 5.0F, "mineralSizer");
		mineralAnalyzer = new MineralAnalyzer(3.0F, 5.0F, "mineralAnalyzer");
		chemicalExtractor = new ChemicalExtractor(3.0F, 5.0F, "chemicalExtractor");
		saltMaker = new SaltMaker(3.0F, 5.0F, "saltMaker");
		mineCrawler = new MineCrawler("mineCrawler");
		crawlerAssembler = new CrawlerAssembler(3.0F, 5.0F, "crawlerAssembler");
		metalAlloyer = new MetalAlloyer(3.0F, 5.0F, "metalAlloyer");
		laserRedstoneTx = new LaserTX(3.0F, 5.0F,"laserRedstoneTx");
		laserRedstoneRx = new LaserRX(3.0F, 5.0F,"laserRedstoneRx");
		laserBeam = new LaserBeam("laserBeam");
		laserSplitter = new LaserSplitter(3.0F, 5.0F,"laserSplitter");

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
		chemicalDusts = new ChemicalDusts("chemicalDusts", ModArray.chemicalDustsArray);
		chemicalItems = new ChemicalItems("chemicalItems", ModArray.chemicalItemsArray);										
		miscItems = new MiscItems("miscItems", ModArray.miscItemsArray);
		chemBook = new ChemBook("chemBook");
		alloyItems = new AlloyItems("alloyItems", ModArray.alloyItemArray);	
		alloyBItems = new AlloyItems("alloyBItems", ModArray.alloyBItemArray);	

		//tools
		cubeCrossbow = new CubeCrossbow("cubeCrossbow");
		scalBow = new ScalBow(scalMaterial, "scalBow");
		scalBat = new ScalBat(scalMaterial, "scalBat");
		bamShears = new BamShears(bamMaterial, "bamShears");
		bamSword = new BamSword(bamMaterial, "bamSword");
	}

	//recall the registry
	public static void register() {
		//blocks
		registerMetaBlock(alloyBlocks, "alloyBlocks");
		registerMetaBlock(alloyBBlocks, "alloyBBlocks");
		registerMetaBlock(alloyBricks, "alloyBricks");
		registerMetaBlock(alloyBBricks, "alloyBBricks");
		registerMetaBlock(mineralOres, "mineralOres");
		registerSimpleBlock(labOven, "labOven");
		registerSimpleBlock(mineralSizer, "mineralSizer");
		registerSimpleBlock(mineralAnalyzer, "mineralAnalyzer");
		registerSimpleBlock(chemicalExtractor, "chemicalExtractor");
		registerMetaBlock(saltMaker, "saltMaker");
		registerCrawlerBlock(mineCrawler, "mineCrawler");
		registerSimpleBlock(crawlerAssembler, "crawlerAssembler");
		registerSimpleBlock(metalAlloyer, "metalAlloyer");
		registerSimpleBlock(laserRedstoneTx, "laserRedstoneTx");
		registerMetaBlock(laserRedstoneRx, "laserRedstoneRx");
		registerSimpleBlock(laserBeam, "laserBeam");
		registerSimpleBlock(laserSplitter, "laserSplitter");

		//items
		registerItem(arsenateShards);
		registerItem(borateShards);
		registerItem(carbonateShards);
		registerItem(halideShards);
		registerItem(nativeShards);
		registerItem(oxideShards);
		registerItem(phosphateShards);
		registerItem(silicateShards);
		registerItem(sulfateShards);
		registerItem(sulfideShards);
		registerItem(chemicalDusts);
		registerItem(chemicalItems);
		registerItem(miscItems);
		registerItem(chemBook);
		registerItem(alloyItems);
		registerItem(alloyBItems);
		registerItem(cubeCrossbow);
		registerItem(scalBow);
		registerItem(scalBat);
		registerItem(bamShears);
		registerItem(bamSword);
	}

	//register blocks and itemblocks
	public static void registerMetaBlock(Block block, String name) {
		GameRegistry.register(block);
		GameRegistry.register(new TiersIB(block).setRegistryName(name));
	}
	//register simple blocks and itemblocks
	public static void registerSimpleBlock(Block block, String name) {
		GameRegistry.register(block);
		GameRegistry.register(new SimpleIB(block).setRegistryName(name));
	}
	//register specific blocks and itemblocks
	public static void registerCrawlerBlock(Block block, String name) {
		GameRegistry.register(block);
		GameRegistry.register(new CrawlerIB(block).setRegistryName(name));
	}


	//register items
	private static void registerItem(Item item) {
		GameRegistry.register(item);
	}


	//recall the renders
	public static void registerRenders(){
		//blocks
		for(int i = 0; i < ModArray.mineralOresArray.length; i++){		registerMetaBlockRender(mineralOres, i, ModArray.mineralOresArray[i]);			}
		for(int i = 0; i < ModArray.saltMakerArray.length; i++){		registerMetaBlockRender(saltMaker, i, ModArray.saltMakerArray[i]);				}
		for(int i = 0; i < ModArray.alloyArray.length; i++){			registerMetaBlockRender(alloyBlocks, i, ModArray.alloyArray[i]);				}
		for(int i = 0; i < ModArray.alloyBArray.length; i++){			registerMetaBlockRender(alloyBBlocks, i, ModArray.alloyBArray[i]);				}
		for(int i = 0; i < ModArray.alloyArray.length; i++){			registerMetaBlockRender(alloyBricks, i, ModArray.alloyArray[i]);				}
		for(int i = 0; i < ModArray.alloyBArray.length; i++){			registerMetaBlockRender(alloyBBricks, i, ModArray.alloyBArray[i]);				}
		for(int i = 0; i < ModArray.laserArray.length; i++){			registerMetaBlockRender(laserRedstoneRx, i, ModArray.laserArray[i]);			}
		registerSingleBlockRender(labOven, 0, "labOven");
		registerSingleBlockRender(mineralSizer, 0, "mineralSizer");
		registerSingleBlockRender(mineralAnalyzer, 0, "mineralAnalyzer");
		registerSingleBlockRender(chemicalExtractor, 0, "chemicalExtractor");
		registerSingleBlockRender(mineCrawler, 0, "mineCrawler");
		registerSingleBlockRender(crawlerAssembler, 0, "crawlerAssembler");
		registerSingleBlockRender(metalAlloyer, 0, "metalAlloyer");
		registerSingleBlockRender(laserRedstoneTx, 0, "laserRedstoneTx");
		registerSingleBlockRender(laserBeam, 0, "laserBeam");
		registerSingleBlockRender(laserSplitter, 0, "laserSplitter");

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
		for(int i = 0; i < ModArray.chemicalDustsArray.length; i++){	registerMetaItemRender(chemicalDusts, i, ModArray.chemicalDustsArray[i]);		}
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
	}


	//render meta block
	public static void registerMetaBlockRender(Block block, int meta, String fileName){
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName() + "_" + fileName, "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
	}
	//render single block
	public static void registerSingleBlockRender(Block block, int meta, String fileName){
		Item item = Item.getItemFromBlock(block);
		ModelResourceLocation model = new ModelResourceLocation(block.getRegistryName(), "inventory");
		ModelLoader.setCustomModelResourceLocation(item, meta, model );
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
