package com.globbypotato.rockhounding_chemistry.utils;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumOres;
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

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BaseRecipes {
	public static String getText(int i) {return EnumOres.getName(i).toUpperCase().substring(0, 1) + EnumOres.getName(i).substring(1);}

	public static ItemStack arsenateStack(EnumArsenate mineral) {	return new ItemStack(ModItems.arsenateShards, 1, mineral.ordinal());}
	public static ItemStack borateStack(EnumBorate mineral) {		return new ItemStack(ModItems.borateShards, 1, mineral.ordinal());}
	public static ItemStack carbonateStack(EnumCarbonate mineral) {	return new ItemStack(ModItems.carbonateShards, 1, mineral.ordinal());}
	public static ItemStack halideStack(EnumHalide mineral) {		return new ItemStack(ModItems.halideShards, 1, mineral.ordinal());}
	public static ItemStack nativeStack(EnumNative mineral) {		return new ItemStack(ModItems.nativeShards, 1, mineral.ordinal());}
	public static ItemStack oxideStack(EnumOxide mineral) {			return new ItemStack(ModItems.oxideShards, 1, mineral.ordinal());}
	public static ItemStack posphateStack(EnumPhosphate mineral) {	return new ItemStack(ModItems.phosphateShards, 1, mineral.ordinal());}
	public static ItemStack silicateStack(EnumSilicate mineral) {	return new ItemStack(ModItems.silicateShards, 1, mineral.ordinal());}
	public static ItemStack sulfateStack(EnumSulfate mineral) {		return new ItemStack(ModItems.sulfateShards, 1, mineral.ordinal());}
	public static ItemStack sulfideStack(EnumSulfide mineral) {		return new ItemStack(ModItems.sulfideShards, 1, mineral.ordinal());}

	public static ItemStack miscs(int num, int meta){				return new ItemStack(ModItems.miscItems, num, meta);}
	public static ItemStack miscBlock(int num, int meta){			return new ItemStack(ModBlocks.miscBlocks, num, meta);}
	public static ItemStack chemicals(int num, int meta){			return new ItemStack(ModItems.chemicalItems, num, meta);}
	public static ItemStack elements(int num, int meta){			return new ItemStack(ModItems.chemicalDusts, num, meta);}
	public static ItemStack alloys(int num, int meta){				return new ItemStack(ModItems.alloyItems, num, meta);}
	public static ItemStack alloysB(int num, int meta){				return new ItemStack(ModItems.alloyBItems, num, meta);}
	public static ItemStack owc(int num, int meta){					return new ItemStack(ModBlocks.owcBlocks, num, meta);}
	public static ItemStack gan(int meta){							return new ItemStack(ModBlocks.ganBlocks, 1, meta);}
	public static ItemStack fires(int num, int meta) {				return new ItemStack(ModItems.chemicalFires, num, meta);}
	public static ItemStack fireBlocks(int num, int meta) {			return new ItemStack(ModBlocks.fireBlock, num, meta);}
	public static ItemStack minerals(int type){						return new ItemStack(ModBlocks.mineralOres,1,type);}
	public static ItemStack battery(int type){						return new ItemStack(ModBlocks.ultraBattery,1,type);}
	public static ItemStack patterns(int num, int type){			return new ItemStack(ModItems.patternItems,1,type);}
	public static ItemStack speeds(int meta){						return new ItemStack(ModItems.speedItems, 1, meta);}

	public static ItemStack arsenateShards(int num, int meta) {		return new ItemStack(ModItems.arsenateShards,num,meta);}
	public static ItemStack borateShards(int num, int meta) {		return new ItemStack(ModItems.borateShards,num,meta);}
	public static ItemStack carbonateShards(int num, int meta) {	return new ItemStack(ModItems.carbonateShards,num,meta);}
	public static ItemStack halideShards(int num, int meta) {		return new ItemStack(ModItems.halideShards,num,meta);}
	public static ItemStack nativeShards(int num, int meta) {		return new ItemStack(ModItems.nativeShards,num,meta);}
	public static ItemStack oxideShards(int num, int meta) {		return new ItemStack(ModItems.oxideShards,num,meta);}
	public static ItemStack phosphateShards(int num, int meta) {	return new ItemStack(ModItems.phosphateShards,num,meta);}
	public static ItemStack silicateShards(int num, int meta) {		return new ItemStack(ModItems.silicateShards,num,meta);}
	public static ItemStack sulfateShards(int num, int meta) {		return new ItemStack(ModItems.sulfateShards,num,meta);}
	public static ItemStack sulfideShards(int num, int meta) {		return new ItemStack(ModItems.sulfideShards,num,meta);}
	
	public static int arsenateGravity(int meta){					return EnumArsenate.values()[meta].gravity();} 
	public static int borateGravity(int meta){						return EnumBorate.values()[meta].gravity();} 
	public static int carbonateGravity(int meta){					return EnumCarbonate.values()[meta].gravity();} 
	public static int halideGravity(int meta){						return EnumHalide.values()[meta].gravity();} 
	public static int nativeGravity(int meta){						return EnumNative.values()[meta].gravity();} 
	public static int oxideGravity(int meta){						return EnumOxide.values()[meta].gravity();} 
	public static int phosphateGravity(int meta){					return EnumPhosphate.values()[meta].gravity();} 
	public static int silicateGravity(int meta){					return EnumSilicate.values()[meta].gravity();} 
	public static int sulfateGravity(int meta){						return EnumSulfate.values()[meta].gravity();} 
	public static int sulfideGravity(int meta){						return EnumSulfide.values()[meta].gravity();} 

	public static ItemStack dyeShards(int i) {						return new ItemStack(Items.DYE,1,i);}
	public static ItemStack coalItem(int num, int meta) {			return new ItemStack(Items.COAL,num,meta);}
	public static ItemStack coalBlock(int num, int meta) {			return new ItemStack(Blocks.COAL_BLOCK,num,meta);}

	public static ItemStack logicChip = miscs(1,0);
	public static ItemStack cabinet = miscs(1,1);
	public static ItemStack ironNugget = miscs(1,2);
	public static ItemStack bowBarrel = miscs(1,3);
	public static ItemStack bowWheel = miscs(1,4);
	public static ItemStack fluidContainer = miscs(1,5);
	public static ItemStack crawlerMemory = miscs(1,6);
	public static ItemStack advancedChip = miscs(1,7);
	public static ItemStack setupChip = miscs(1,8);
	public static ItemStack crawlerCasing = miscs(1,9);
	public static ItemStack crawlerHead = miscs(1,10);
	public static ItemStack hastelloyArm = miscs(1,11);
	public static ItemStack modWrench = miscs(1,12);
	public static ItemStack hastelloyFoil = miscs(1,13);
	public static ItemStack yagRod = miscs(1,14);
	public static ItemStack laserResonator = miscs(1,15);
	public static ItemStack heatingElement = miscs(1,16);
	public static ItemStack platinumIngot = miscs(1,17);
	public static ItemStack cupronickelFoil = miscs(1,18);
	public static ItemStack copperCoil = miscs(1,19);
	public static ItemStack owcStator = miscs(1,20);
	public static ItemStack owcRotor = miscs(1,21);
	public static ItemStack nimonicArm = miscs(1,22);
	public static ItemStack owcFan = miscs(1,23);
	public static ItemStack nimonicFoil = miscs(1,24);
	public static ItemStack copperIngot = miscs(1,25);
	public static ItemStack energyCell = miscs(1,26);
	public static ItemStack leadIngot = miscs(1,27);
	public static ItemStack nimonicCasing = miscs(1,28);
	public static ItemStack nichromeRod = miscs(1,29);
	public static ItemStack cunifeCoil = miscs(1,30);
	public static ItemStack cathode = miscs(1,31);
	public static ItemStack cathodeSet = miscs(1,32);
	public static ItemStack hastelloyCasing = miscs(1,33);
	public static ItemStack chamberUpgrade = miscs(1,34);
	public static ItemStack insulationUpgrade = miscs(1,35);
	public static ItemStack titaniumIngot = miscs(1,36);
	public static ItemStack boundaryHead = miscs(1,37);
	public static ItemStack sienaBearing = miscs(1,38);
	public static ItemStack tiniteArm = miscs(1,39);
	public static ItemStack boundaryChip = miscs(1,40);
	public static ItemStack hydronaliumCasing = miscs(1,41);
	public static ItemStack ironCasing = miscs(1,42);
	public static ItemStack compressor = miscs(1,43);
	public static ItemStack spiral = miscs(1,44);
	public static ItemStack cupronickelCasing = miscs(1,45);
	public static ItemStack aluminumIngot = miscs(1,46);
	public static ItemStack aluminumNugget = miscs(1,47);
	public static ItemStack aluminumCasing = miscs(1,48);
	public static ItemStack leadElectrode = miscs(1,49);
	public static ItemStack carbonElectrode = miscs(1,50);
	public static ItemStack leadDioxide = miscs(1,51);
	public static ItemStack fiberglass = miscs(1,52);
	public static ItemStack plugIn = miscs(1,53);
	public static ItemStack plugOut = miscs(1,54);
	public static ItemStack carbonIngot = miscs(1,55);
	public static ItemStack cupronickelArm = miscs(1,56);
	public static ItemStack fusedGlass = miscs(1,57);
	public static ItemStack blendUnit = miscs(1,58);
	public static ItemStack vanaGear = miscs(1,59);
	public static ItemStack clockwork = miscs(1,60);
	public static ItemStack widiaFoil = miscs(1,61);
	public static ItemStack widiaCasing = miscs(1,62);
	public static ItemStack leadDioxideIngot = miscs(1,63);
	public static ItemStack ironFoil = miscs(1,64);
	public static ItemStack pipelineUpgrade = miscs(1,65);
	public static ItemStack widiaArm = miscs(1,66);
	public static ItemStack server_file = miscs(1,67);

	public static ItemStack ironNuggets = miscs(9,2);
	public static ItemStack aluminumNuggets = miscs(9,47);
	public static ItemStack aluminumIngots = miscs(9,46);

	public static ItemStack spirals = miscs(4,44);

	public static ItemStack polymer = chemicals(1,0);
	public static ItemStack saltStack = chemicals(1,1);
	public static ItemStack sulfCompost = chemicals(1,2);
	public static ItemStack crackedCoal = chemicals(1,6);
	public static ItemStack saltRaw = chemicals(1,7);
	public static ItemStack ashCompost = chemicals(1,11);
	public static ItemStack potCarbonate = chemicals(1,12);

	public static ItemStack oreStack = minerals(0);
	public static ItemStack borax = borateShards(1,0);
	public static ItemStack fluorite = halideShards(1,4);
	public static ItemStack pyrite = sulfideShards(1,6);

	public static ItemStack charcoal = new ItemStack(Items.COAL, 1, 1);
	public static ItemStack goldIngot = new ItemStack(Items.GOLD_INGOT);
	public static ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
	public static ItemStack stoneSlab = new ItemStack(Blocks.STONE_SLAB);
	public static ItemStack lapis = new ItemStack(Items.DYE, 1, 4);
	public static ItemStack rottenFlesh = new ItemStack(Items.ROTTEN_FLESH);
	public static ItemStack leather = new ItemStack(Items.LEATHER);
	public static ItemStack waterBucket = new ItemStack(Items.WATER_BUCKET);
	public static ItemStack bottle = new ItemStack(Items.GLASS_BOTTLE);
	public static ItemStack comparator = new ItemStack(Items.COMPARATOR);
	public static ItemStack repeater = new ItemStack(Items.REPEATER);
	public static ItemStack piston = new ItemStack(Blocks.PISTON);
	public static ItemStack hopper = new ItemStack(Blocks.HOPPER);

	public static ItemStack aluminumBlock = miscBlock(1, 0);
	
	public static ItemStack pipeline = new ItemStack(ModBlocks.pipelineDuct);
	public static ItemStack pump = new ItemStack(ModBlocks.pipelinePump);

}