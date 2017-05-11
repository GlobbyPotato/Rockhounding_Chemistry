package com.globbypotato.rockhounding_chemistry.utils;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SaltSeasonerRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;

public class IMCUtils {
	public static String DEPOSITION_KEY = "addToDeposition";
	public static String DEPOSITION_KEY_REMOVER = "removeFromDeposition";
	public static String ANALYZER_KEY = "addToAnalyzer";
	public static String ANALYZER_KEY_REMOVER = "removeFromAnalyzer";
	public static String EXTRACTOR_KEY = "addToExtractor";
	public static String EXTRACTOR_KEY_INHIBITOR = "inhibitFromExtractor";
	public static String OVEN_KEY = "addToOven";
	public static String OVEN_KEY_REMOVER = "removeFromOven";
	public static String SIZER_KEY = "addToSizer";
	public static String SIZER_KEY_REMOVER = "removeFromSizer";
	public static String SEASONER_KEY = "addToSeasoner";
	public static String SEASONER_KEY_REMOVER = "removeFromSeasoner";
	public static String ALLOYER_KEY = "addToAlloyer";
	public static String ALLOYER_KEY_REMOVER = "removeFromAlloyer";
	static List<ItemStack> elements;
	static List<String> dusts;
	static List<Integer> quantities;

	public static void extraRecipes(List<IMCMessage> messages) {
		for(IMCMessage message : messages) {
			if(message.isNBTMessage()){
				try {
		    		NBTTagCompound tag = message.getNBTValue();
	    			/**
		    		 * REMOVE RECIPES
		    		 */
		    		if(message.key.equalsIgnoreCase(OVEN_KEY_REMOVER)){
		    			FluidStack solution = null;
		        		if(tag.hasKey("Solution")){
			        		solution = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Solution"));
		        		}
		        		if(solution != null){
		        			for(int x = 0; x < ModRecipes.labOvenRecipes.size(); x++){
		        				if(ModRecipes.labOvenRecipes.get(x).getOutput().getFluid() == solution.getFluid()){
		        					ModRecipes.labOvenRecipes.remove(x);
		        				}
		        			}
		        		}
		    		}else if(message.key.equalsIgnoreCase(SEASONER_KEY_REMOVER)){
		    			ItemStack seasIn = null;
		        		if(tag.hasKey("Input")){
		        			seasIn = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(seasIn != null){
		        			for(int x = 0; x < ModRecipes.seasonerRecipes.size(); x++){
		        				if(ModRecipes.seasonerRecipes.get(x).getInput().isItemEqual(seasIn)){
		        					ModRecipes.seasonerRecipes.remove(x);
		        				}
		        			}
		        		}
		    		}else if(message.key.equalsIgnoreCase(ALLOYER_KEY_REMOVER)){
		    			ItemStack alloy = null;
		        		if(tag.hasKey("Ingot")){
		        			alloy = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Ingot"));
		        		}
		        		if(alloy != null){
		        			for(int x = 0; x < ModRecipes.alloyerRecipes.size(); x++){
		        				if(ModRecipes.alloyerRecipes.get(x).getOutput().isItemEqual(alloy)){
		        					ModRecipes.alloyerRecipes.remove(x);
		        				}
		        			}
		        		}
		    		}else if(message.key.equalsIgnoreCase(SIZER_KEY_REMOVER)){
		    			ItemStack crush = null;
		        		if(tag.hasKey("Input")){
		        			crush = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(crush != null){
		        			for(int x = 0; x < ModRecipes.sizerRecipes.size(); x++){
		        				if(ModRecipes.sizerRecipes.get(x).getInput().isItemEqual(crush)){
		        					ModRecipes.sizerRecipes.remove(x);
		        				}
		        			}
		        		}
		    		}else if(message.key.equalsIgnoreCase(ANALYZER_KEY_REMOVER)){
		    			ItemStack analyzed = null;
		        		if(tag.hasKey("Input")){
		        			analyzed = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(analyzed != null){
		        			for(int x = 0; x < ModRecipes.analyzerRecipes.size(); x++){
		        				if(ModRecipes.analyzerRecipes.get(x).getInput().isItemEqual(analyzed)){
		        					ModRecipes.analyzerRecipes.remove(x);
		        				}
		        			}
		        		}
		    		}else if(message.key.equalsIgnoreCase(EXTRACTOR_KEY_INHIBITOR)){
		        		if(tag.hasKey("Elements")){
		        			  NBTTagList dustList = tag.getTagList("Elements", Constants.NBT.TAG_COMPOUND);
		        			  dusts = new ArrayList<String>();
		        			  for(int i = 0; i < dustList.tagCount(); i++){
		        				  NBTTagCompound getDusts = dustList.getCompoundTagAt(i);
		        				  dusts.add(getDusts.getString("Element" + i));
		        			  }
		        		}
		        		if(dusts.size() > 0){
			        		for(int x = 0; x < dusts.size(); x++){
			        			if(!dusts.get(x).matches("")){
			        				ModRecipes.inhibitedElements.add(dusts.get(x));
			        			}
			        		}
		        		}
		    		}else if(message.key.equalsIgnoreCase(DEPOSITION_KEY_REMOVER)){
		    			ItemStack output = null;
		        		if(tag.hasKey("Output")){
		        			output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Output"));
		        		}
		        		if(output != null){
		        			for(int x = 0; x < ModRecipes.depositionRecipes.size(); x++){
		        				if(ModRecipes.depositionRecipes.get(x).getOutput().isItemEqual(output)){
		        					ModRecipes.depositionRecipes.remove(x);
		        				}
		        			}
		        		}



	    			/**
		    		 * ADD RECIPES
		    		 */
		    		}else if(message.key.equalsIgnoreCase(ANALYZER_KEY)){
		    			ItemStack input = null;
		        		if(tag.hasKey("Input")){
		        			input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(tag.hasKey("Elements")){
		        			  NBTTagList dustList = tag.getTagList("Elements", Constants.NBT.TAG_COMPOUND);
		        			  elements = new ArrayList<ItemStack>();
		        			  for(int i = 0; i < dustList.tagCount(); i++){
		        				  NBTTagCompound getDusts = dustList.getCompoundTagAt(i);
		        				  elements.add(ItemStack.loadItemStackFromNBT(getDusts));
		        			  }
		        		}
		        		if(tag.hasKey("Probabilities")){
		        			  NBTTagList quantityList = tag.getTagList("Probabilities", Constants.NBT.TAG_COMPOUND);
		        			  quantities = new ArrayList<Integer>();
		        			  for(int i = 0; i < quantityList.tagCount(); i++){
		        				  NBTTagCompound getQuantities = quantityList.getCompoundTagAt(i);
		        				  quantities.add(getQuantities.getInteger("Probability" + i));
		        			  }
		        		}
		        		if(input != null && elements.size() > 0 && quantities.size() > 0 && elements.size() == quantities.size()){
		        			ModRecipes.analyzerRecipes.add(new MineralAnalyzerRecipe(input, elements, quantities));
		        		}
		    		}else if(message.key.equalsIgnoreCase(EXTRACTOR_KEY)){
		    			ItemStack input = null;
		    			String display = "";
		        		if(tag.hasKey("Category")){
		        			display = tag.getString("Category");
		        		}
		        		if(tag.hasKey("Input")){
		        			input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(tag.hasKey("Elements")){
		        			  NBTTagList dustList = tag.getTagList("Elements", Constants.NBT.TAG_COMPOUND);
		        			  dusts = new ArrayList<String>();
		        			  for(int i = 0; i < dustList.tagCount(); i++){
		        				  NBTTagCompound getDusts = dustList.getCompoundTagAt(i);
		        				  dusts.add(getDusts.getString("Element" + i));
		        			  }
		        		}
		        		if(tag.hasKey("Quantities")){
		        			  NBTTagList quantityList = tag.getTagList("Quantities", Constants.NBT.TAG_COMPOUND);
		        			  quantities = new ArrayList<Integer>();
		        			  for(int i = 0; i < quantityList.tagCount(); i++){
		        				  NBTTagCompound getQuantities = quantityList.getCompoundTagAt(i);
		        				  quantities.add(getQuantities.getInteger("Quantity" + i));
		        			  }
		        		}
		        		if(input != null && dusts.size() > 0 && quantities.size() > 0 && dusts.size() == quantities.size()){
		        			ModRecipes.extractorRecipes.add(new ChemicalExtractorRecipe(display, input, dusts, quantities));
		        		}
					}else if(message.key.equalsIgnoreCase(OVEN_KEY)){
						ItemStack solute = null;
						FluidStack solvent1 = null;
						FluidStack solvent2 = null;
						FluidStack solution = null;
						if(tag.hasKey("Solute")){
		        			solute = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Solute"));
		        		}
		        		if(tag.hasKey("Solvent1")){
		        			solvent1 = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Solvent1"));
		        		}
		        		if(tag.hasKey("Solvent2")){
		        			solvent2 = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Solvent2"));
		        		}else{
		        			solvent2 = null;
		        		}
		        		if(tag.hasKey("Solution")){
			        		solution = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Solution"));
		        		}
		        		if(solute != null && solvent1.getFluid() != null && (!tag.hasKey("Solvent2") || (tag.hasKey("Solvent2") && solvent2.getFluid() != null)) && solution.getFluid() != null){
		        			ModRecipes.labOvenRecipes.add(new LabOvenRecipe(solute, solvent1, solvent2,	solution));
		        		}
					}else if(message.key.equalsIgnoreCase(SIZER_KEY)){
		    			ItemStack input = null;
		        		if(tag.hasKey("Input")){
		        			input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(tag.hasKey("Elements")){
		        			  NBTTagList dustList = tag.getTagList("Elements", Constants.NBT.TAG_COMPOUND);
		        			  elements = new ArrayList<ItemStack>();
		        			  for(int i = 0; i < dustList.tagCount(); i++){
		        				  NBTTagCompound getDusts = dustList.getCompoundTagAt(i);
		        				  elements.add(ItemStack.loadItemStackFromNBT(getDusts));
		        			  }
		        		}
		        		if(tag.hasKey("Probabilities")){
		        			  NBTTagList quantityList = tag.getTagList("Probabilities", Constants.NBT.TAG_COMPOUND);
		        			  quantities = new ArrayList<Integer>();
		        			  for(int i = 0; i < quantityList.tagCount(); i++){
		        				  NBTTagCompound getQuantities = quantityList.getCompoundTagAt(i);
		        				  quantities.add(getQuantities.getInteger("Probability" + i));
		        			  }
		        		}
		        		if(input != null && elements.size() > 0 && quantities.size() > 0 && elements.size() == quantities.size()){
		        			ModRecipes.sizerRecipes.add(new MineralSizerRecipe(input, elements, quantities));
		        		}
		        	}else if(message.key.equalsIgnoreCase(SEASONER_KEY)){
		    			ItemStack input = null;
		    			ItemStack output = null;
		        		if(tag.hasKey("Input")){
		        			input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(tag.hasKey("Output")){
		        			output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Output"));
		        		}
		        		if(input != null && output != null){
		        			ModRecipes.seasonerRecipes.add(new SaltSeasonerRecipe(input, output));
		        		}
					}else if(message.key.equalsIgnoreCase(ALLOYER_KEY)){
						String display = "";
						ItemStack ingot = null;
						ItemStack scrap = null;
		        		if(tag.hasKey("Display")){
		        			display = tag.getString("Display");
		        		}
		        		if(tag.hasKey("Ingot")){
		        			ingot = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Ingot"));
		        		}
		        		if(tag.hasKey("Scrap")){
		        			scrap = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Scrap"));
		        		}else{
		        			scrap = null;
		        		}
		        		if(tag.hasKey("Dusts")){
		        			  NBTTagList dustList = tag.getTagList("Dusts", Constants.NBT.TAG_COMPOUND);
		        			  dusts = new ArrayList<String>();
		        			  for(int i = 0; i < dustList.tagCount(); i++){
		        				  NBTTagCompound getDusts = dustList.getCompoundTagAt(i);
		        				  dusts.add(getDusts.getString("Dust" + i));
		        			  }
		        		}
		        		if(tag.hasKey("Quantities")){
		        			  NBTTagList quantityList = tag.getTagList("Quantities", Constants.NBT.TAG_COMPOUND);
		        			  quantities = new ArrayList<Integer>();
		        			  for(int i = 0; i < quantityList.tagCount(); i++){
		        				  NBTTagCompound getQuantities = quantityList.getCompoundTagAt(i);
		        				  quantities.add(getQuantities.getInteger("Quantity" + i));
		        			  }
		        		}
		        		if(!display.matches("") && ingot != null && dusts.size() > 0 && quantities.size() > 0 && dusts.size() == quantities.size()){
		        			ModRecipes.alloyerRecipes.add(new MetalAlloyerRecipe(display, dusts, quantities, ingot, scrap));
		        		}
					}else if(message.key.equalsIgnoreCase(DEPOSITION_KEY)){
						ItemStack input = null;
						FluidStack solvent = null;
						ItemStack output = null;
						int temperature = 0;
						int pressure = 0;
						if(tag.hasKey("Input")){
							input = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Input"));
		        		}
		        		if(tag.hasKey("Solvent")){
		        			solvent = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("Solvent"));
		        		}
		        		if(tag.hasKey("Output")){
							output = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("Output"));
		        		}
		        		if(tag.hasKey("Temperature")){
		        			temperature = tag.getInteger("Temperature");
		        		}
		        		if(tag.hasKey("Pressure")){
		        			pressure = tag.getInteger("Pressure");
		        		}
		        		if(input != null && solvent.getFluid() != null && output != null && temperature > 0 && pressure > 0){
		        			ModRecipes.depositionRecipes.add(new DepositionChamberRecipe(input, output, solvent, temperature, pressure));
		        		}
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}
	}
}