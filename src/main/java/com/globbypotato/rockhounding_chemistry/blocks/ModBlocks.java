package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
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
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;
// PIN sends the signal vertically

public class ModBlocks {
	public static Block mineralOres;
	public static Block alloyBlocks;
	public static Block alloyBBlocks;
	public static Block alloyBricks;
	public static Block alloyBBricks;


	public static Block labOven;

	public static Block mineralSizer;

	public static Block mineralAnalyzer;

	public static Block crawlerAssembler;

	public static Block metalAlloyer;


	public static Block chemicalExtractor;
	public static Block saltMaker;
	public static Block mineCrawler;
	public static Block laserRedstoneTx;
	public static Block laserRedstoneRx;
	public static Block laserBeam;
	public static Block laserSplitter;


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
		
	}

	//recall the registry
	public static void register() {
		//blocks
		registerMetaBlock(alloyBlocks, "alloyBlocks");
		registerMetaBlock(alloyBBlocks, "alloyBBlocks");
		registerMetaBlock(alloyBricks, "alloyBricks");
		registerMetaBlock(alloyBBricks, "alloyBBricks");
		registerMetaBlock(mineralOres, "mineralOres");
		registerMetaBlock(saltMaker, "saltMaker");
		registerCrawlerBlock(mineCrawler, "mineCrawler");
		registerMetaBlock(laserRedstoneRx, "laserRedstoneRx");
		registerSimpleBlock(laserRedstoneTx, "laserRedstoneTx");
		registerSimpleBlock(laserSplitter, "laserSplitter");
		registerSimpleBlock(laserBeam, "laserBeam");
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



	//recall the renders
	public static void initClient(){
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

}
