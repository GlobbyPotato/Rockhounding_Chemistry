package com.globbypotato.rockhounding_chemistry.utils;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasCondenserRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasPurifierRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.HeatExchangerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LeachingVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PollutantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ProfilingBenchRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.RetentionVatRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SeasoningRackRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SlurryPondRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ToxicMutationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.TransposerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasCondenserRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.HeatExchangerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LeachingVatRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MineralSizerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantFluidRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PollutantGasRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ProfilingBenchRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.RetentionVatRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SeasoningRackRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.SlurryPondRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ToxicMutationRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.TransposerRecipe;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;
import rockhounding.api.IReciperBase;

public class IMCUtils extends IReciperBase{
	static ItemStack input = ItemStack.EMPTY;
	static ItemStack solute = ItemStack.EMPTY;
	static ItemStack output = ItemStack.EMPTY;
	static ItemStack catalyst = ItemStack.EMPTY;
	static FluidStack solvent = null;
	static FluidStack reagent = null;
	static FluidStack solution = null;
	static int pattern = 0;

	public static void extraRecipes(List<IMCMessage> messages) {
		for(IMCMessage message : messages) {
    		if(message.isNBTMessage()){
				try {
		    		NBTTagCompound tag = message.getNBTValue();
		    		//POLLUTANT GAS
	    			if(message.key.equalsIgnoreCase(add_pollutant_gas_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
		        		PollutantRecipes.pollutant_gases.add(solvent);
		        		PollutantRecipes.pollutant_gas_recipes.add(new PollutantGasRecipe(solvent));
	    			}
		    		//POLLUTANT FLUID
	    			if(message.key.equalsIgnoreCase(add_pollutant_fluid_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
		        		PollutantRecipes.pollutant_fluids.add(solvent);
		        		PollutantRecipes.pollutant_fluid_recipes.add(new PollutantFluidRecipe(solvent));
	    			}
		    		//TOXIC MUTATION
	    			if(message.key.equalsIgnoreCase(add_toxic_mutation_key)){
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			output =  new ItemStack(tag.getCompoundTag(tagOutput));
		        		}
		        		ToxicMutationRecipes.toxic_mutation_recipes.add(new ToxicMutationRecipe(input, output));
	    			}
		    		//TRANSPOSER
	    			if(message.key.equalsIgnoreCase(add_transposer_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		TransposerRecipes.transposer_recipes.add(new TransposerRecipe(solvent, solution));
	    			}
		    		//SLURRY POND
	    			if(message.key.equalsIgnoreCase(add_slurry_pond_key)){
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagSolvent)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagSolvent));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		SlurryPondRecipes.slurry_pond_recipes.add(new SlurryPondRecipe(input, solvent, solution));
	    			}
		    		//SEASONING RACK
	    			if(message.key.equalsIgnoreCase(add_seasoning_rack_key)){
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			output =  new ItemStack(tag.getCompoundTag(tagOutput));
		        		}
		        		SeasoningRackRecipes.seasoning_rack_recipes.add(new SeasoningRackRecipe(input, output));
	    			}
	    			//RETENTION VAT
	    			if(message.key.equalsIgnoreCase(add_retention_vat_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
	       			  	ArrayList<ItemStack> elements = new ArrayList<ItemStack>();
		        		if(tag.hasKey(tagElements)){
		        			  NBTTagList elementTag = tag.getTagList(tagElements, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < elementTag.tagCount(); i++){
		        				  NBTTagCompound elementNBT = elementTag.getCompoundTagAt(i);
		        				  elements.add(new ItemStack(elementNBT));
		        			  }
		        		}
	       			  	ArrayList<Float> gravities = new ArrayList<Float>();
		        		if(tag.hasKey(tagWeights)){
		        			  NBTTagList weightsTAG = tag.getTagList(tagWeights, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < weightsTAG.tagCount(); i++){
		        				  NBTTagCompound weightsNBT = weightsTAG.getCompoundTagAt(i);
		        				  gravities.add(weightsNBT.getFloat(tagWeights + i));
		        			  }
		        		}
		        		RetentionVatRecipes.retention_vat_recipes.add(new RetentionVatRecipe(solvent, elements, gravities));
	    			}
	    			//PROFILING BENCH
	    			if(message.key.equalsIgnoreCase(add_profiling_bench_key)){
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			output =  new ItemStack(tag.getCompoundTag(tagOutput));
		        		}
						if(tag.hasKey(tagPattern)){
							pattern = tag.getInteger(tagPattern);
		        		}
		        		ProfilingBenchRecipes.profiling_bench_recipes.add(new ProfilingBenchRecipe(input, output, pattern));
	    			}
	    			//MINERAL SIZER
	    			if(message.key.equalsIgnoreCase(add_mineral_sizer_key)){
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
	       			  	ArrayList<ItemStack> elements = new ArrayList<ItemStack>();
		        		if(tag.hasKey(tagElements)){
		        			  NBTTagList elementTag = tag.getTagList(tagElements, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < elementTag.tagCount(); i++){
		        				  NBTTagCompound elementNBT = elementTag.getCompoundTagAt(i);
		        				  elements.add(new ItemStack(elementNBT));
		        			  }
		        		}
	       			  	ArrayList<Integer> comminutions = new ArrayList<Integer>();
		        		if(tag.hasKey(tagWeights)){
		        			  NBTTagList weightsTAG = tag.getTagList(tagWeights, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < weightsTAG.tagCount(); i++){
		        				  NBTTagCompound weightsNBT = weightsTAG.getCompoundTagAt(i);
		        				  comminutions.add(weightsNBT.getInteger(tagWeights + i));
		        			  }
		        		}
		        		MineralSizerRecipes.mineral_sizer_recipes.add(new MineralSizerRecipe(input, elements, comminutions));
	    			}
	    			//METAL ALLOYER
	    			if(message.key.equalsIgnoreCase(add_metal_alloyer_key)){
		        		ArrayList<String> oredicts = new ArrayList<String>();
		        		if(tag.hasKey(tagElements)){
		        			  NBTTagList elementsTAG = tag.getTagList(tagElements, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < elementsTAG.tagCount(); i++){
		        				  NBTTagCompound elementsNBT = elementsTAG.getCompoundTagAt(i);
		        				  oredicts.add(elementsNBT.getString(tagElements + i));
		        			  }
		        		}
	       			  	ArrayList<Integer> quantities = new ArrayList<Integer>();
		        		if(tag.hasKey(tagWeights)){
		        			  NBTTagList weightsTAG = tag.getTagList(tagWeights, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < weightsTAG.tagCount(); i++){
		        				  NBTTagCompound weightsNBT = weightsTAG.getCompoundTagAt(i);
		        				  quantities.add(weightsNBT.getInteger(tagWeights + i));
		        			  }
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			output =  new ItemStack(tag.getCompoundTag(tagOutput));
		        		}
		        		MetalAlloyerRecipes.metal_alloyer_recipes.add(new MetalAlloyerRecipe(oredicts, quantities, output));
	    			}
	    			//MATERIAL CABINET
	    			if(message.key.equalsIgnoreCase(add_material_cabinet_key)){
		        		String pattern = null;
						if(tag.hasKey(tagPattern)){
							pattern = tag.getString(tagPattern);
		        		}
		        		String oredict = null;
						if(tag.hasKey(tagOredict)){
							oredict = tag.getString(tagOredict);
		        		}
		        		String display = null;
						if(tag.hasKey(tagDisplay)){
							display = tag.getString(tagDisplay);
		        		}
		        		MaterialCabinetRecipes.material_cabinet_recipes.add(new MaterialCabinetRecipe(pattern, oredict, display));
	    			}
	    			//LEACHING VAT
	    			if(message.key.equalsIgnoreCase(add_leaching_vat_key)){
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
	       			  	ArrayList<ItemStack> elements = new ArrayList<ItemStack>();
		        		if(tag.hasKey(tagElements)){
		        			  NBTTagList elementTag = tag.getTagList(tagElements, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < elementTag.tagCount(); i++){
		        				  NBTTagCompound elementNBT = elementTag.getCompoundTagAt(i);
		        				  elements.add(new ItemStack(elementNBT));
		        			  }
		        		}
	       			  	ArrayList<Float> gravities = new ArrayList<Float>();
		        		if(tag.hasKey(tagWeights)){
		        			  NBTTagList weightsTAG = tag.getTagList(tagWeights, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < weightsTAG.tagCount(); i++){
		        				  NBTTagCompound weightsNBT = weightsTAG.getCompoundTagAt(i);
		        				  gravities.add(weightsNBT.getFloat(tagWeights + i));
		        			  }
		        		}
		        		if(tag.hasKey(tagSolvent)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagSolvent));
		        		}
		        		LeachingVatRecipes.leaching_vat_recipes.add(new LeachingVatRecipe(input, elements, gravities, solvent));
	    			}
	    			//LAB OVEN
	    			if(message.key.equalsIgnoreCase(add_lab_oven_key)){
		        		String display = null;
						if(tag.hasKey(tagDisplay)){
							display = tag.getString(tagDisplay);
		        		}
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagPattern)){
		        			catalyst =  new ItemStack(tag.getCompoundTag(tagPattern));
		        		}
		        		if(tag.hasKey(tagSolvent)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagSolvent));
		        		}
		        		if(tag.hasKey(tagReagent)){
		        			reagent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagReagent));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		FluidStack byproduct = null;
		        		if(tag.hasKey(tagByproduct)){
		        			byproduct =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagByproduct));
		        		}
		        		LabOvenRecipes.lab_oven_recipes.add(new LabOvenRecipe(display, input, catalyst, solvent, reagent, solution, byproduct));
					}
	    			//LAB BLENDER
	    			if(message.key.equalsIgnoreCase(add_lab_blender_key)){
	       			  	ArrayList<ItemStack> elements = new ArrayList<ItemStack>();
		        		if(tag.hasKey(tagElements)){
		        			  NBTTagList elementTag = tag.getTagList(tagElements, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < elementTag.tagCount(); i++){
		        				  NBTTagCompound elementNBT = elementTag.getCompoundTagAt(i);
		        				  elements.add(new ItemStack(elementNBT));
		        			  }
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			output =  new ItemStack(tag.getCompoundTag(tagOutput));
		        		}
		        		LabBlenderRecipes.lab_blender_recipes.add(new LabBlenderRecipe(elements, output));
					}
	    			//HEAT EXCHANGER
	    			if(message.key.equalsIgnoreCase(add_heat_exchanger_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		HeatExchangerRecipes.heat_exchanger_recipes.add(new HeatExchangerRecipe(solvent, solution));
	    			}
	    			//REFORMING REACTOR
	    			if(message.key.equalsIgnoreCase(add_reforming_reactor_key)){
		        		if(tag.hasKey(tagSolvent)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagSolvent));
		        		}
		        		if(tag.hasKey(tagReagent)){
		        			reagent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagReagent));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		if(tag.hasKey(tagPattern)){
		        			catalyst =  new ItemStack(tag.getCompoundTag(tagPattern));
		        		}
		        		GasReformerRecipes.gas_reformer_recipes.add(new GasReformerRecipe(solvent, reagent, solution, catalyst));
	    			}
	    			//GAS PURIFIER
	    			if(message.key.equalsIgnoreCase(add_gas_purifier_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		ItemStack slag1 = ItemStack.EMPTY;
		        		if(tag.hasKey(tagSlag1)){
		        			slag1 =  new ItemStack(tag.getCompoundTag(tagSlag1));
		        		}
		        		ItemStack slag2 = ItemStack.EMPTY;
		        		if(tag.hasKey(tagSlag2)){
		        			slag2 =  new ItemStack(tag.getCompoundTag(tagSlag2));
		        		}
		        		GasPurifierRecipes.gas_purifier_recipes.add(new GasPurifierRecipe(solvent, solution, slag1, slag2));
	    			}
	    			//GAS CONDENSER
	    			if(message.key.equalsIgnoreCase(add_gas_condenser_key)){
		        		if(tag.hasKey(tagInput)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		GasCondenserRecipes.gas_condenser_recipes.add(new GasCondenserRecipe(solvent, solution));
	    			}
	    			//GASIFICATION PLANT
	    			if(message.key.equalsIgnoreCase(add_gasification_plant_key)){
		        		if(tag.hasKey(tagSolvent)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagSolvent));
		        		}
		        		if(tag.hasKey(tagReagent)){
		        			reagent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagReagent));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			solution =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagOutput));
		        		}
		        		ItemStack slag1 = ItemStack.EMPTY;
		        		if(tag.hasKey(tagSlag1)){
		        			slag1 =  new ItemStack(tag.getCompoundTag(tagSlag1));
		        		}
		        		ItemStack slag2 = ItemStack.EMPTY;
		        		if(tag.hasKey(tagSlag2)){
		        			slag2 =  new ItemStack(tag.getCompoundTag(tagSlag2));
		        		}
						if(tag.hasKey(tagTemperature)){
							pattern = tag.getInteger(tagTemperature);
		        		}
		        		GasifierPlantRecipes.gasifier_plant_recipes.add(new GasifierPlantRecipe(solvent, reagent, solution, slag1, slag2, pattern));
	    			}
	    			//DEPOSITION CHAMBER
	    			if(message.key.equalsIgnoreCase(add_deposition_chamber_key)){
		        		String display = null;
						if(tag.hasKey(tagDisplay)){
							display = tag.getString(tagDisplay);
		        		}
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		if(tag.hasKey(tagOutput)){
		        			output =  new ItemStack(tag.getCompoundTag(tagOutput));
		        		}
		        		if(tag.hasKey(tagSolvent)){
		        			solvent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagSolvent));
		        		}
		        		int temperature = 0;
						if(tag.hasKey(tagTemperature)){
							temperature = tag.getInteger(tagTemperature);
		        		}
		        		int pressure = 0;
						if(tag.hasKey(tagPressure)){
							pressure = tag.getInteger(tagPressure);
		        		}
		        		if(tag.hasKey(tagReagent)){
		        			reagent =  FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tagReagent));
		        		}else{
		        			reagent = BaseRecipes.getFluid(EnumFluid.OXYGEN, 1000);
		        		}
		        		DepositionChamberRecipes.deposition_chamber_recipes.add(new DepositionChamberRecipe(display, input, output, solvent, temperature, pressure, reagent));
	    			}
	    			//CHEMICAL EXTRACTOR
	    			if(message.key.equalsIgnoreCase(add_chemical_extractor_key)){
		        		String display = null;
						if(tag.hasKey(tagDisplay)){
							display = tag.getString(tagDisplay);
		        		}
		        		if(tag.hasKey(tagInput)){
		        			input =  new ItemStack(tag.getCompoundTag(tagInput));
		        		}
		        		ArrayList<String> oredicts = new ArrayList<String>();
		        		if(tag.hasKey(tagElements)){
		        			  NBTTagList elementsTAG = tag.getTagList(tagElements, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < elementsTAG.tagCount(); i++){
		        				  NBTTagCompound elementsNBT = elementsTAG.getCompoundTagAt(i);
		        				  oredicts.add(elementsNBT.getString(tagElements + i));
		        			  }
		        		}
	       			  	ArrayList<Integer> quantities = new ArrayList<Integer>();
		        		if(tag.hasKey(tagWeights)){
		        			  NBTTagList weightsTAG = tag.getTagList(tagWeights, Constants.NBT.TAG_COMPOUND);
		        			  for(int i = 0; i < weightsTAG.tagCount(); i++){
		        				  NBTTagCompound weightsNBT = weightsTAG.getCompoundTagAt(i);
		        				  quantities.add(weightsNBT.getInteger(tagWeights + i));
		        			  }
		        		}
		        		ChemicalExtractorRecipes.extractor_recipes.add(new ChemicalExtractorRecipe(display, input, oredicts, quantities));
	    			}
	    			//INHIBIT ELEMENT
	    			if(message.key.equalsIgnoreCase(inhibit_chemical_extractor_key)){
		        		String oredict = null;
						if(tag.hasKey(tagOredict)){
							oredict = tag.getString(tagOredict);
		        		}
		        		ChemicalExtractorRecipes.inhibited_elements.add(oredict);
	    			}
				}catch (Exception e){
					e.printStackTrace();
				}
    		}
		}
	}
}