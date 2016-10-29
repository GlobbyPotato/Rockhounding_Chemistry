package com.globbypotato.rockhounding_chemistry.handlers;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.items.tools.Petrographer;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class GlobbyEventHandler {
	EntityPlayer player;
	ItemStack petroStack;
    ItemStack mineralStack;
	Random rand = new Random();
    Item[] 	specimenList = new Item[] {	null, 
										ModItems.arsenateShards, 
										ModItems.borateShards, 
										ModItems.carbonateShards, 
										ModItems.halideShards, 
										ModItems.nativeShards, 
										ModItems.oxideShards, 
										ModItems.phosphateShards, 
										ModItems.silicateShards, 
										ModItems.sulfateShards, 
										ModItems.sulfideShards};

	@SubscribeEvent
	public void onBlockHarvest(HarvestDropsEvent event) {

		if (event.getHarvester() != null) {
			player = event.getHarvester();
			if(hasPetrographer(player, event)) {
				int fortuneLevel = event.getFortuneLevel();
				petroStack = event.getHarvester().getHeldItem(EnumHand.MAIN_HAND);
				if(petroStack.hasTagCompound()){
			    	int nLevel = petroStack.getTagCompound().getInteger("nLevel");
			    	int nLevelUp = petroStack.getTagCompound().getInteger("nLevelUp");
			    	int nFlavor = petroStack.getTagCompound().getInteger("nFlavor");
			    	int nSpecimen = petroStack.getTagCompound().getInteger("nSpecimen");
			    	int nFortune = petroStack.getTagCompound().getInteger("nFortune");
			    	int nFinds = petroStack.getTagCompound().getInteger("nFinds");
					if(isMineral(event)){
				    	
						if(rand.nextInt(24) < nFortune){
							if(nFinds > 0){
								if(nFlavor > 0){
									int validFortune = 0;
									int validFinds = 0;
									if(fortuneLevel > 3){ validFortune = 3; }else{ validFortune = fortuneLevel; }
									if(validFortune > 0){
										validFinds = 1 + rand.nextInt(validFortune);
									}else{
										validFinds = 1;
									}
									mineralStack = new ItemStack(ModBlocks.mineralOres, validFinds, nFlavor);

									//handle skill and finds
									nFinds -= validFinds;
									if(nFinds <= 0){ 
										if(nFortune < 16){
											nFortune++;
											int totFinds = Petrographer.baseFinds + ((nFortune-1) * Petrographer.baseFinds);
											nFinds = totFinds;   
											player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1.0F, 1.0F);
											petroStack.getTagCompound().setInteger("nFortune", nFortune);
										}
									}
									petroStack.getTagCompound().setInteger("nFinds", nFinds);

									//handle xp and level
									player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.NEUTRAL, 1.0F, 1.0F);
									int getXp = 0; int newLevelUp = 0;
									if(validFortune > 0){
										newLevelUp = nLevelUp - (1 + rand.nextInt(validFortune));
									}else{
										newLevelUp = nLevelUp - 1;
									}
									if(newLevelUp <= 0){ 
										if(nLevel < 20){
											nLevel++;
											int totLevelUp = Petrographer.baseLevelUp + (nLevel * Petrographer.baseLevelUp);
											newLevelUp = totLevelUp;  
											player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.NEUTRAL, 1.0F, 1.0F);
											petroStack.getTagCompound().setInteger("nLevel", nLevel);
										}
									}
									petroStack.getTagCompound().setInteger("nLevelUp", newLevelUp);

									//handle specimen
									if(nLevel > 16){
										if(nFortune > 10){
											if(nFlavor > 0){
												if(nSpecimen > -1){
													if(rand.nextInt(32) < nFortune){
														mineralStack = new ItemStack(specimenList[nFlavor], validFinds, nSpecimen);
													}
												}
											}
										}
									}

									//handle drop exp
									for (int i = 0; i < (nLevel / 2) + 1; i++){
										double fx = this.rand.nextFloat(); double fy = this.rand.nextFloat(); double fz = this.rand.nextFloat();
										if(!player.worldObj.isRemote){ player.worldObj.spawnEntityInWorld(new EntityXPOrb(player.worldObj, player.posX + (fx - 0.5), player.posY + fy, player.posZ + (fz - 0.5), 1)); }
									}
								}else{
									mineralStack = new ItemStack(ModBlocks.mineralOres, 1, 0);
								}
							}else{
								mineralStack = new ItemStack(ModBlocks.mineralOres, 1, 0);
							}
						}else{
							mineralStack = new ItemStack(ModBlocks.mineralOres, 1, 0);
						}
						event.getDrops().clear();
						event.getDrops().add(mineralStack.copy());
					}else if(isStone(event)){
						if(nLevel > 0 && nFortune > 0){
							if(rand.nextInt(16) < nLevel){
								if(rand.nextInt(16) < nFortune){
									mineralStack = new ItemStack(ModBlocks.mineralOres, 1, 0);
									player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
									event.getDrops().clear();
									event.getDrops().add(mineralStack.copy());
								}
							}
						}
					}
				}else{
					Petrographer.setItemNbt(petroStack);
				}
			}
		}
	}

	private boolean isStone(HarvestDropsEvent event){
		Block block = event.getState().getBlock();
		ItemStack oreStack = new ItemStack(block, 1, block.getMetaFromState(event.getState()));
		if(oreStack != null) {
			int[] oreIDs = OreDictionary.getOreIDs(oreStack);
			if(oreIDs.length > 0) {
				for(int i = 0; i < oreIDs.length; i++) {
					String oreName = OreDictionary.getOreName(oreIDs[i]);
					if(oreName != null && oreName == "stone"){
						return true;
					}
				}
			}
		}
		return false;
	}

	private boolean isMineral(HarvestDropsEvent event) {
		Block block = event.getState().getBlock();
		return block != null && block == ModBlocks.mineralOres && block.getMetaFromState(event.getState()) == 0;
	}

	private boolean hasPetrographer(EntityPlayer player2, HarvestDropsEvent event) {
		return player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.petrographer;
	}
		
}

		