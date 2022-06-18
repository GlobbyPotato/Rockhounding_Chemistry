package com.globbypotato.rockhounding_chemistry.handlers;

import java.util.ArrayList;
import java.util.Random;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSpeeds;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.items.ProbeItems;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ElementsCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LeachingVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PowderMixerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PrecipitationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.RetentionVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.google.common.base.Strings;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.RightClickBlock;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class GlobbyEventHandler {
	ItemStack petroStack;
    ItemStack mineralStack;
	Random rand = new Random();

	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public void handleTooltip(ItemTooltipEvent event){
		ItemStack itemstack = event.getItemStack();
		if(itemstack != ItemStack.EMPTY){

			if(itemstack.getItem() == ModItems.SPEED_ITEMS && itemstack.getItemDamage() > 0){
				event.getToolTip().add(TextFormatting.GREEN + "Increases the machine speed by " + (itemstack.getItemDamage() + 1));
			}

			if(itemstack.getItem() == ModItems.FILTER_ITEMS && itemstack.getItemDamage() > 0){
				event.getToolTip().add(TextFormatting.GREEN + "Thickens the leaching filter mesh by " + (itemstack.getItemDamage() * 0.5F));
				event.getToolTip().add(TextFormatting.GREEN + "Sets the filter step to " + ModUtils.stepDivision(itemstack.getItemDamage()));
			}

			if(itemstack.getItem() == ModItems.PROBE_ITEMS){
				event.getToolTip().add(TextFormatting.GREEN + "Increases the Orbiter radius to " + ProbeItems.orbiterUpgrade(itemstack) + " block/s");
			}

	    	for(MineralSizerRecipe recipe : MineralSizerRecipes.mineral_sizer_recipes){
		    	for(int y = 0; y < recipe.getOutput().size(); y++){
					if(ItemStack.areItemsEqual(recipe.getOutput().get(y), itemstack)){
						if((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0 )){
							String comText = TextFormatting.GRAY + "Required Comminution Level: " + TextFormatting.GREEN + recipe.getComminution().get(y);
				    		if(!event.getToolTip().contains(comText)){
				    			event.getToolTip().add(comText);
							}
						}
					}
				}
	    	}

	    	for(LeachingVatRecipe recipe : LeachingVatRecipes.leaching_vat_recipes){
	    		if(recipe.getOutput().size() >= 1){
			    	for(int y = 0; y < recipe.getOutput().size(); y++){
						if(ItemStack.areItemsEqual(recipe.getOutput().get(y), itemstack)){
							float realgravity = recipe.getGravity().get(y);
							event.getToolTip().add(TextFormatting.GRAY + "Specific Gravity: " + TextFormatting.LIGHT_PURPLE + realgravity);
						}
					}
	    		}
	    	}

	    	for(RetentionVatRecipe recipe : RetentionVatRecipes.retention_vat_recipes){
	    		if(recipe.getOutput().size() >= 1){
			    	for(int y = 0; y < recipe.getOutput().size(); y++){
						if(ItemStack.areItemsEqual(recipe.getOutput().get(y), itemstack)){
							float realgravity = recipe.getGravity().get(y);
							String gravText = TextFormatting.GRAY + "Specific Gravity: " + TextFormatting.LIGHT_PURPLE + realgravity;
							if(!event.getToolTip().contains(gravText)){
								event.getToolTip().add(gravText);
							}
						}
					}
	    		}
	    	}

	    	for(ChemicalExtractorRecipe recipe: ChemicalExtractorRecipes.extractor_recipes){
	    		if(recipe.getType()){
					ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(itemstack));
					if(inputOreIDs.size() > 0 && inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict()))){
						addExtractorRecipe(recipe, event);
					}
	    		}else{
					if(!recipe.getInput().isEmpty() && itemstack.isItemEqual(recipe.getInput())){
						addExtractorRecipe(recipe, event);
					}
	    		}
	    	}

			if(itemstack.hasTagCompound()){
				if(itemstack.isItemEqual(BaseRecipes.sampling_ampoule)){
					NBTTagCompound tag = itemstack.getTagCompound();
					if(tag.hasKey(EnumFluidNbt.GAS.nameTag())){
						String sampleText = TextFormatting.GRAY + "Sample: " + TextFormatting.BOLD + TextFormatting.AQUA + "Empty";
						FluidStack sampledGas = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.GAS.nameTag()));
						if(sampledGas != null && sampledGas.getFluid() != null && sampledGas.getFluid().isGaseous()){
							sampleText = TextFormatting.GRAY + "Sampled Gas: " + TextFormatting.BOLD + TextFormatting.AQUA + sampledGas.getLocalizedName();
							event.getToolTip().add(sampleText);
						}
					}else if(tag.hasKey(EnumFluidNbt.FLUID.nameTag())){
						String sampleText = TextFormatting.GRAY + "Sample: " + TextFormatting.BOLD + TextFormatting.AQUA + "Empty";
						FluidStack sampledGas = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag()));
						if(sampledGas != null && sampledGas.getFluid() != null){
							sampleText = TextFormatting.GRAY + "Sampled Fluid: " + TextFormatting.BOLD + TextFormatting.AQUA + sampledGas.getLocalizedName();
							event.getToolTip().add(sampleText);
						}
					}
				}

				if(itemstack.isItemEqual(new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.ORBITER.ordinal()))){
					NBTTagCompound tag = itemstack.getTagCompound();
					if(tag.hasKey("XPCount")){
						int xpCount = tag.getInteger("XPCount");
						if(xpCount > 0){
							String xp = TextFormatting.GRAY + "Stored Experience: " + TextFormatting.BOLD + TextFormatting.DARK_GREEN + xpCount + " xp";
							event.getToolTip().add(xp);
						}
					}
				}

				if(itemstack.isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()))){
					if(itemstack.hasTagCompound()){
						NBTTagCompound tag = itemstack.getTagCompound();
			    		if(isValidNBT(tag)){
				        	int device = tag.getInteger("Device");
				        	boolean cycle = tag.getBoolean("Cycle");
				        	int recipe = tag.getInteger("Recipe");
				        	int amount = tag.getInteger("Amount");
				        	int done = tag.getInteger("Done");
			        		event.getToolTip().add(TextFormatting.GRAY + "Served Device: " + TextFormatting.BOLD + TextFormatting.YELLOW + EnumServer.values()[device].getName());
			        		event.getToolTip().add(TextFormatting.GRAY + "Repeatable: " + TextFormatting.BOLD + TextFormatting.YELLOW + cycle);
			        		if(device == EnumServer.LAB_OVEN.ordinal()){
			        			if(Strings.isNullOrEmpty(LabOvenRecipes.lab_oven_recipes.get(recipe).getRecipeName())){
					        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + LabOvenRecipes.lab_oven_recipes.get(recipe).getSolution().getLocalizedName());
			        			}else{
					        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + LabOvenRecipes.lab_oven_recipes.get(recipe).getRecipeName());
			        			}
			        		}else if(device == EnumServer.METAL_ALLOYER.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + MetalAlloyerRecipes.metal_alloyer_recipes.get(recipe).getOutput().getDisplayName());
			        		}else if(device == EnumServer.DEPOSITION.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + DepositionChamberRecipes.deposition_chamber_recipes.get(recipe).getOutput().getDisplayName());
			        		}else if(device == EnumServer.SIZER.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Comminution Level: " + TextFormatting.BOLD +  TextFormatting.YELLOW + recipe);
			        		}else if(device == EnumServer.LEACHING.ordinal()){
			        			float currentGravity = (recipe * 2) + 2F;
			        			event.getToolTip().add(TextFormatting.GRAY + "Gravity: " + TextFormatting.BOLD +  TextFormatting.YELLOW + (currentGravity - 2F) + " to " + (currentGravity + 2F));
			        		}else if(device == EnumServer.RETENTION.ordinal()){
			        			float currentGravity = (recipe * 2) + 2F;
				        		event.getToolTip().add(TextFormatting.GRAY + "Gravity: " + TextFormatting.BOLD +  TextFormatting.YELLOW + (currentGravity - 2F) + " to " + (currentGravity + 2F));
			        		}else if(device == EnumServer.CASTING.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Pattern: " + TextFormatting.BOLD +  TextFormatting.YELLOW + EnumCasting.getFormalName(recipe));
			        		}else if(device == EnumServer.REFORMER.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + GasReformerRecipes.gas_reformer_recipes.get(recipe).getOutput().getLocalizedName());
			        		}else if(device == EnumServer.POWDER_MIXER.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + PowderMixerRecipes.powder_mixer_recipes.get(recipe).getOutput().getDisplayName());
			        		}else if(device == EnumServer.EXTRACTOR.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Intensity Level: " + TextFormatting.BOLD +  TextFormatting.YELLOW + recipe);
			        		}else if(device == EnumServer.PRECIPITATOR.ordinal()){
			        			if(Strings.isNullOrEmpty(PrecipitationRecipes.precipitation_recipes.get(recipe).getRecipeName())){
					        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + PrecipitationRecipes.precipitation_recipes.get(recipe).getPrecipitate().getDisplayName());
			        			}else{
					        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + PrecipitationRecipes.precipitation_recipes.get(recipe).getRecipeName());
			        			}
			        		}else if(device == EnumServer.BED_REACTOR.ordinal()){
				        		event.getToolTip().add(TextFormatting.GRAY + "Recipe: " + TextFormatting.BOLD +  TextFormatting.YELLOW + BedReactorRecipes.bed_reactor_recipes.get(recipe).getOutput().getLocalizedName());
			        		}
			        		if(tag.hasKey("FilterStack")){
			        			ItemStack filter = new ItemStack(tag.getCompoundTag("FilterStack"));
			        			if(!filter.isEmpty()){
					        		event.getToolTip().add(TextFormatting.GRAY + "Filter: " + TextFormatting.BOLD +  TextFormatting.DARK_GREEN + filter.getDisplayName());
			        			}
			        		}
			        		if(tag.hasKey("FilterFluid")){
								FluidStack filter = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("FilterFluid"));
			        			if(filter != null){
					        		event.getToolTip().add(TextFormatting.GRAY + "Filter: " + TextFormatting.BOLD +  TextFormatting.DARK_GREEN + filter.getLocalizedName());
			        			}
			        		}
			        		event.getToolTip().add(TextFormatting.GRAY + "Amount: " + TextFormatting.BOLD +  TextFormatting.YELLOW + amount + " scheduled");
			        		event.getToolTip().add(TextFormatting.GRAY + "Process: " + TextFormatting.BOLD +  TextFormatting.YELLOW + done + " to do");
			    		}
					}
		    	}

		    	if(itemstack.isItemEqual(new ItemStack(ModItems.SPEED_ITEMS, 1, EnumSpeeds.BASE.ordinal()))){
		    		if(itemstack.getTagCompound().hasKey("Title")){
		    			event.getToolTip().add(itemstack.getTagCompound().getString("Title"));
		    		}
		    		if(itemstack.getTagCompound().hasKey("DustList")){
		    			NBTTagList dustList = itemstack.getTagCompound().getTagList("DustList", Constants.NBT.TAG_COMPOUND);
		    			for(int i = 0; i < dustList.tagCount(); i++){
		    				NBTTagCompound getQuantities = dustList.getCompoundTagAt(i);
		    				event.getToolTip().add(getQuantities.getString("Ingr" + i));
		    			}
		    		}
		    	}

		    	if(!ModUtils.hasWawla()){
			    	if(itemstack.getItem() instanceof UniversalBucket){
						if(FluidUtil.getFluidContained(itemstack) != null){
							FluidStack filterfluid = FluidUtil.getFluidContained(itemstack);
			        		event.getToolTip().add(TextFormatting.GRAY + "Temperature: " + TextFormatting.BOLD +  TextFormatting.YELLOW + filterfluid.getFluid().getTemperature() + "K");
						}
			    	}
		    	}

		    	if(itemstack.isItemEqual(new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.PRESSURE_VESSEL.ordinal()))){
		    		if(itemstack.getTagCompound().hasKey(EnumFluidNbt.GAS.nameTag())){
						FluidStack filterfluid = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.GAS.nameTag()));
						if(filterfluid != null){
							event.getToolTip().add(TextFormatting.GRAY + "Temperature: " + TextFormatting.BOLD +  TextFormatting.YELLOW + filterfluid.getFluid().getTemperature() + "K");
						}
		    		}
		    	}
			}else {
				if(itemstack.isItemEqual(BaseRecipes.sampling_ampoule)){
					itemstack.setTagCompound(new NBTTagCompound());
				}
			}
		}
	}

	private void addExtractorRecipe(ChemicalExtractorRecipe recipe, ItemTooltipEvent event) {
		String inhibit = "";
		event.getToolTip().add(TextFormatting.GRAY + "Category: " + TextFormatting.YELLOW + recipe.getCategory());
		ArrayList<ItemStack> firstDict = new ArrayList<ItemStack>();
		for(int x = 0; x < recipe.getElements().size(); x++){
			inhibit = "";
			String recipeDict = recipe.getElements().get(x);
			firstDict.clear();
			firstDict.addAll(OreDictionary.getOres(recipeDict));
			if(firstDict.size() > 0){
			   for(int ix = 0; ix < ElementsCabinetRecipes.inhibited_elements.size(); ix++){
				   if(recipeDict.toLowerCase().matches(ElementsCabinetRecipes.inhibited_elements.get(ix).toLowerCase())){
					   inhibit = " - (Inhibited)";
				   }
			   }
			   for(int ix = 0; ix < MaterialCabinetRecipes.inhibited_material.size(); ix++){
				   if(recipeDict.toLowerCase().matches(MaterialCabinetRecipes.inhibited_material.get(ix).toLowerCase())){
					   inhibit = " - (Inhibited)";
				   }
			   }
			   event.getToolTip().add(TextFormatting.GRAY + firstDict.get(0).getDisplayName() + " - " + TextFormatting.WHITE + TextFormatting.BOLD + recipe.getQuantities().get(x) + "%" + TextFormatting.RESET + TextFormatting.RED + inhibit);
			}
		}
	}

	@SubscribeEvent
	public void onBlockBurn(FurnaceFuelBurnTimeEvent event) {
		if(event.getItemStack().isItemEqual(new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.CHARCOAL_BLOCK.ordinal())) ){
			event.setBurnTime(16000);
		}
	}

	@SubscribeEvent
	public void onInteract(RightClickBlock event){
		if(Loader.isModLoaded(ModUtils.thermal_f_id)){
        	if(event.getWorld().getBlockState(event.getPos()) != null && event.getWorld().getBlockState(event.getPos()).getBlock() instanceof MachineIO){
	            if (!event.getItemStack().isEmpty() && !ModUtils.thermal_f_wrench().isEmpty() && event.getItemStack().isItemEqual(ModUtils.thermal_f_wrench())){
	            	event.setCanceled(true);
	            }
        	}
		}
	}

    @SubscribeEvent
    public void killDrops(LivingDeathEvent event){
    	if(ModConfig.fluidDamage && ModConfig.xpDrop){
	    	EntityLivingBase entity = event.getEntityLiving();
	    	World world = entity.getEntityWorld();
	    	if(event.getSource() == ModFluids.SPILL){
				double fx = world.rand.nextDouble();
				double fy = world.rand.nextDouble();
				double fz = world.rand.nextDouble();    		
				EntityXPOrb orb = new EntityXPOrb(world, entity.posX + fx, entity.posY + fy, entity.posZ + fz, world.rand.nextInt(3)+1);
				if(!world.isRemote){
					world.spawnEntity(orb);
				}
	    	}
    	}
    }

    private static boolean isValidNBT(NBTTagCompound tag) {
		return (tag.hasKey("Device") && tag.getInteger("Device") > -1) && tag.hasKey("Cycle") && tag.hasKey("Recipe") && tag.hasKey("Amount") && tag.hasKey("Done");
	}

}