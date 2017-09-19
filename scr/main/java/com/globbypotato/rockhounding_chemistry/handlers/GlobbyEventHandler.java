package com.globbypotato.rockhounding_chemistry.handlers;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.blocks.FireBlock;
import com.globbypotato.rockhounding_chemistry.blocks.OwcBlocks;
import com.globbypotato.rockhounding_chemistry.items.tools.Petrographer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.LeftClickBlock;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;

public class GlobbyEventHandler {
	ItemStack petroStack;
    ItemStack mineralStack;
	Random rand = new Random();

	@SubscribeEvent
	public void hitBlock(LeftClickBlock event){
		if(event.getFace() == EnumFacing.UP){
			if(event.getWorld().getBlockState(event.getPos().up()).getBlock() instanceof FireBlock){
				event.getWorld().setBlockToAir(event.getPos().up());
				event.getWorld().playSound((double)((float)event.getPos().up().getX() + 0.5F), (double)((float)event.getPos().up().getY() + 0.5F), (double)((float)event.getPos().up().getZ() + 0.5F), SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 2.0F, 2.0F, false);
			}
		}
	}

	@SubscribeEvent
	public void handleTooltip(ItemTooltipEvent event){
		ItemStack itemstack = event.getItemStack();
		if(itemstack != null){

			Block owcBlocks = Block.getBlockFromItem(itemstack.getItem());
			if(owcBlocks instanceof OwcBlocks){
				event.getToolTip().add(TextFormatting.YELLOW + "Element of the OWC multiblock energy system");
			}
			
	    	for(int x = 0; x < MachineRecipes.sizerRecipes.size(); x++){
	    		if(MachineRecipes.sizerRecipes.get(x).getOutput().size() > 1){
			    	for(int y = 0; y < MachineRecipes.sizerRecipes.get(x).getOutput().size(); y++){
			    		if(MachineRecipes.sizerRecipes.get(x).getComminution()){
							if(ItemStack.areItemsEqual(MachineRecipes.sizerRecipes.get(x).getOutput().get(y), itemstack)){
								event.getToolTip().add(TextFormatting.GRAY + "Comminution Level: " + TextFormatting.GREEN + MachineRecipes.sizerRecipes.get(x).getProbability().get(y).intValue());
							}
			    		}else{
							if(ItemStack.areItemsEqual(MachineRecipes.sizerRecipes.get(x).getOutput().get(y), itemstack)){
								event.getToolTip().add(TextFormatting.GRAY + "Sizing Chance: " + TextFormatting.GREEN + MachineRecipes.sizerRecipes.get(x).getProbability().get(y).intValue());
							}
			    		}
					}
	    		}
	    	}

	    	for(int x = 0; x < MachineRecipes.analyzerRecipes.size(); x++){
	    		if(MachineRecipes.analyzerRecipes.get(x).getOutput().size() > 1){
			    	for(int y = 0; y < MachineRecipes.analyzerRecipes.get(x).getOutput().size(); y++){
			    		if(MachineRecipes.analyzerRecipes.get(x).hasGravity()){
							if(ItemStack.areItemsEqual(MachineRecipes.analyzerRecipes.get(x).getOutput().get(y), itemstack)){
								float realgravity = (float)MachineRecipes.analyzerRecipes.get(x).getProbability().get(y).intValue() / 100;
								event.getToolTip().add(TextFormatting.GRAY + "Specific Gravity: " + TextFormatting.LIGHT_PURPLE + realgravity);
							}
			    		}else{
							if(ItemStack.areItemsEqual(MachineRecipes.analyzerRecipes.get(x).getOutput().get(y), itemstack)){
								event.getToolTip().add(TextFormatting.GRAY + "Leaching Chance: " + TextFormatting.LIGHT_PURPLE + MachineRecipes.analyzerRecipes.get(x).getProbability().get(y).intValue() + "%");
							}
			    		}
					}
	    		}
	    	}

	    	for(ChemicalExtractorRecipe recipe: MachineRecipes.extractorRecipes){
				if(recipe.getInput() != null && itemstack.isItemEqual(recipe.getInput())){
					event.getToolTip().add(TextFormatting.GRAY + "Category: " + TextFormatting.YELLOW + recipe.getCategory());
					for(int x = 0; x < recipe.getElements().size(); x++){
						event.getToolTip().add(TextFormatting.DARK_GRAY + recipe.getElements().get(x).substring(0, 1).toUpperCase() + recipe.getElements().get(x).substring(1) + " - " + TextFormatting.WHITE + TextFormatting.BOLD + recipe.getQuantities().get(x) + "%");
					}
				}
	    	}

		}
	}

	@SubscribeEvent
	public void onBlockHarvest(HarvestDropsEvent event) {
		if(event.getHarvester() != null) {
			EntityPlayer player = event.getHarvester();
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
			    	
					if(isMineral(event, event.getState().getBlock())){
						if(rand.nextInt(24) < nFortune){
							if(nFlavor > 0){
								//handle item drops
								int validFortune = fortuneLevel;
								int validFinds = 0;
								if(fortuneLevel > 3){ 
									validFortune = 3; 
								}
								if(validFortune > 0){
									validFinds = 1 + rand.nextInt(validFortune);
								}else{
									validFinds = 1;
								}
								mineralStack = new ItemStack(ModBlocks.mineralOres, validFinds, nFlavor);

								//handle skill and finds
								if(nFortune <= 16){
									nFinds -= validFinds;
									if(nFinds < 0) nFinds = 0;
									if(nFinds <= 0){ 
										if(nFortune < 16){
											nFortune++;
											int totFinds = Petrographer.baseFinds + ((nFortune-1) * Petrographer.baseFinds);
											nFinds = totFinds;   
											player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
											petroStack.getTagCompound().setInteger("nFortune", nFortune);
										}
									}
									petroStack.getTagCompound().setInteger("nFinds", nFinds);
								}

								//handle xp and level
								if(nLevel <= 20){
									int getXp = 0; int newLevelUp = 0;
									player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
									if(validFortune > 0){
										newLevelUp = nLevelUp - (1 + rand.nextInt(validFortune));
									}else{
										newLevelUp = nLevelUp - 1;
									}
									if(newLevelUp < 0) newLevelUp = 0;
									if(nLevel < 20){
										if(newLevelUp <= 0){
											nLevel++;
											int totLevelUp = Petrographer.baseLevelUp + (nLevel * Petrographer.baseLevelUp);
											newLevelUp = totLevelUp;
											player.worldObj.playSound(player, player.getPosition(), SoundEvents.ENTITY_PLAYER_LEVELUP, SoundCategory.PLAYERS, 1.0F, 1.0F);
											petroStack.getTagCompound().setInteger("nLevel", nLevel);
										}
									}
									petroStack.getTagCompound().setInteger("nLevelUp", newLevelUp);
								}

								//handle specimen
								if(nLevel > 16){
									if(nFortune > 10){
										if(nFlavor > 0){
											if(nSpecimen > -1){
												if(rand.nextInt(32) < nFortune){
													mineralStack = new ItemStack(ToolUtils.specimenList[nFlavor], validFinds, nSpecimen);
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
						event.getDrops().clear();
						event.getDrops().add(mineralStack.copy());
					}else if(isStone(event, event.getState().getBlock())){
						if(nLevel > 0 && nFortune > 0){
							if(rand.nextInt(24) < nLevel){
								if(rand.nextInt(24) < nFortune){
									mineralStack = BaseRecipes.oreStack;
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

			private boolean isStone(HarvestDropsEvent event, Block block){
				ItemStack oreStack = event.getState().getBlock().getPickBlock(event.getState(), null, event.getWorld(), event.getPos(), null);
				if(oreStack != null) {
					int[] oreIDs = OreDictionary.getOreIDs(oreStack);
					if(oreIDs.length > 0) {
						for(int i = 0; i < oreIDs.length; i++) {
							String oreName = OreDictionary.getOreName(oreIDs[i]);
							if(oreName != null && oreName.matches("stone")){
								return true;
							}
						}
					}
				}
				return false;
			}
		
			private boolean isMineral(HarvestDropsEvent event, Block block) {
				return block != null && block == ModBlocks.mineralOres && block.getMetaFromState(event.getState()) == 0;
			}
		
			private boolean hasPetrographer(EntityPlayer player, HarvestDropsEvent event) {
				return player.getHeldItem(EnumHand.MAIN_HAND) != null && player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.petrographer;
			}

}