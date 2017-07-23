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

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class BaseRecipes {
	protected static String getText(int i) {return EnumOres.getName(i).toUpperCase().substring(0, 1) + EnumOres.getName(i).substring(1);}

	protected static ItemStack arsenateStack(EnumArsenate mineral) {	return new ItemStack(ModItems.arsenateShards, 1, mineral.ordinal());}
	protected static ItemStack borateStack(EnumBorate mineral) {		return new ItemStack(ModItems.borateShards, 1, mineral.ordinal());}
	protected static ItemStack carbonateStack(EnumCarbonate mineral) {	return new ItemStack(ModItems.carbonateShards, 1, mineral.ordinal());}
	protected static ItemStack halideStack(EnumHalide mineral) {		return new ItemStack(ModItems.halideShards, 1, mineral.ordinal());}
	protected static ItemStack nativeStack(EnumNative mineral) {		return new ItemStack(ModItems.nativeShards, 1, mineral.ordinal());}
	protected static ItemStack oxideStack(EnumOxide mineral) {			return new ItemStack(ModItems.oxideShards, 1, mineral.ordinal());}
	protected static ItemStack posphateStack(EnumPhosphate mineral) {	return new ItemStack(ModItems.phosphateShards, 1, mineral.ordinal());}
	protected static ItemStack silicateStack(EnumSilicate mineral) {	return new ItemStack(ModItems.silicateShards, 1, mineral.ordinal());}
	protected static ItemStack sulfateStack(EnumSulfate mineral) {		return new ItemStack(ModItems.sulfateShards, 1, mineral.ordinal());}
	protected static ItemStack sulfideStack(EnumSulfide mineral) {		return new ItemStack(ModItems.sulfideShards, 1, mineral.ordinal());}

	protected static ItemStack miscs(int num, int meta){		return new ItemStack(ModItems.miscItems, num, meta);}
	protected static ItemStack chemicals(int num, int meta){	return new ItemStack(ModItems.chemicalItems, num, meta);}
	protected static ItemStack elements(int num, int meta){		return new ItemStack(ModItems.chemicalDusts, num, meta);}
	protected static ItemStack alloys(int num, int meta){		return new ItemStack(ModItems.alloyItems, num, meta);}
	protected static ItemStack alloysB(int num, int meta){		return new ItemStack(ModItems.alloyBItems, num, meta);}
	protected static ItemStack owc(int num, int meta){			return new ItemStack(ModBlocks.owcBlocks, num, meta);}
	protected static ItemStack gan(int meta){					return new ItemStack(ModBlocks.ganBlocks, 1, meta);}
	protected static ItemStack fires(int num, int meta) {		return new ItemStack(ModItems.chemicalFires, num, meta);}
	protected static ItemStack minerals(int type){				return new ItemStack(ModBlocks.mineralOres,1,type);}

	protected static ItemStack arsenateShards(int i) {	return new ItemStack(ModItems.arsenateShards,1,i);}
	protected static ItemStack borateShards(int i) {	return new ItemStack(ModItems.borateShards,1,i);}
	protected static ItemStack carbonateShards(int i) {	return new ItemStack(ModItems.carbonateShards,1,i);}
	protected static ItemStack halideShards(int i) {	return new ItemStack(ModItems.halideShards,1,i);}
	protected static ItemStack nativeShards(int i) {	return new ItemStack(ModItems.nativeShards,1,i);}
	protected static ItemStack oxideShards(int i) {		return new ItemStack(ModItems.oxideShards,1,i);}
	protected static ItemStack phosphateShards(int i) {	return new ItemStack(ModItems.phosphateShards,1,i);}
	protected static ItemStack silicateShards(int i) {	return new ItemStack(ModItems.silicateShards,1,i);}
	protected static ItemStack sulfateShards(int i) {	return new ItemStack(ModItems.sulfateShards,1,i);}
	protected static ItemStack sulfideShards(int i) {	return new ItemStack(ModItems.sulfideShards,1,i);}
	protected static ItemStack dyeShards(int i) {		return new ItemStack(Items.DYE,1,i);}

	public static ItemStack logicChip = miscs(1,0);
	public static ItemStack cabinet = miscs(1,1);
	//2 iron nugget
	public static ItemStack bowBarrel = miscs(1,3);
	public static ItemStack bowWheel = miscs(1,4);
	public static ItemStack fluidContainer = miscs(1,5);
	public static ItemStack crawlerMemory = miscs(1,6);
	public static ItemStack advancedChip = miscs(1,7);
	public static ItemStack setupChip = miscs(1,8);
	public static ItemStack crawlerCasing = miscs(1,9);
	public static ItemStack crawlerHead = miscs(1,10);
	public static ItemStack crawlerArm = miscs(1,11);
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
	public static ItemStack owcArm = miscs(1,22);
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

	public static ItemStack crawlerArms = miscs(4,11);
	public static ItemStack owcArms = miscs(4,22);

	public static ItemStack yagRods = miscs(4,14);
	public static ItemStack nichromeRods = miscs(4,29);

	public static ItemStack ironNuggets = miscs(9,2);
	public static ItemStack aluminumNuggets = miscs(9,47);

	public static ItemStack hastelloyFoils = miscs(8,13);
	public static ItemStack cupronickelFoils = miscs(8,18);
	public static ItemStack nimonicFoils = miscs(8,24);
	public static ItemStack copperCoils = miscs(8,19);
	public static ItemStack cunifeCoils = miscs(8,30);
	
	public static ItemStack nimonicCasings = miscs(8,28);
	public static ItemStack aluminumCasings = miscs(8,48);
	public static ItemStack cupronickelCasings = miscs(8,45);
	public static ItemStack hastelloyCasings = miscs(8,33);
	public static ItemStack hydronaliumCasings = miscs(8,41);
	public static ItemStack ironCasings = miscs(8,42);

	public static ItemStack spirals = miscs(4,44);

	public static ItemStack sulfCompost = chemicals(1,2);
	public static ItemStack crackedCoal = chemicals(1,6);
	public static ItemStack ashCompost = chemicals(1,11);
	public static ItemStack potCarbonate = chemicals(1,12);
	public static ItemStack potNitrate = chemicals(1,13);
	public static ItemStack crackedCharcoal = chemicals(1,14);

	public static ItemStack platinumShard = nativeShards(17);
	public static ItemStack tulaShard = nativeShards(6);
	public static ItemStack niggliiteShard = nativeShards(7);
	public static ItemStack malaniteShard = sulfideShards(14);
	public static ItemStack rutileShard = oxideShards(24);
	public static ItemStack borax = borateShards(0);

	public static ItemStack charcoal = new ItemStack(Items.COAL, 1, 1);
	public static ItemStack goldIngot = new ItemStack(Items.GOLD_INGOT);
	public static ItemStack ironIngot = new ItemStack(Items.IRON_INGOT);
}