package com.globbypotato.rockhounding_chemistry.compat.jei.chemical_extractor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.compat.jei.RHRecipeWrapper;
import com.globbypotato.rockhounding_chemistry.enums.EnumElements;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import mezz.jei.api.ingredients.IIngredients;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

public class ChemicalExtractorWrapper extends RHRecipeWrapper<ChemicalExtractorRecipe>{

	public ChemicalExtractorWrapper(@Nonnull ChemicalExtractorRecipe recipe) {
		super(recipe);
	}

	public static List<ChemicalExtractorWrapper> getRecipes() {
		List<ChemicalExtractorWrapper> recipes = new ArrayList<>();
		for (ChemicalExtractorRecipe recipe : ChemicalExtractorRecipes.extractor_recipes) {
			if(isValidRecipe(recipe)){
				recipes.add(new ChemicalExtractorWrapper(recipe));
			}
		}
		return recipes;
	}

	private static boolean isValidRecipe(ChemicalExtractorRecipe recipe){
		return ((!recipe.getType() && !recipe.getInput().isEmpty()) || (recipe.getType() && OreDictionary.getOres(recipe.getOredict()).size() > 0))
			&& recipe.getElements().size() > 0;
	}

	@Nonnull
	public List<ItemStack> getInputs() {
		ArrayList<ItemStack> inputs = new ArrayList<ItemStack>();
		if(getRecipe().getType()){
			inputs.addAll(OreDictionary.getOres(getRecipe().getOredict()));
		}else{
			inputs.add(getRecipe().getInput());
		}
		return inputs;
	}

	@Nonnull
	public List<String> getElements() {
		return getRecipe().getElements();
	}

	@Nonnull
	public List<Integer> getQuantities() {
		return getRecipe().getQuantities();
	}

	@Nonnull
	public List<FluidStack> getSolvents(){
		ArrayList<FluidStack> stacks = new ArrayList<FluidStack>();
		stacks.add(new FluidStack(ModFluids.NITRIC_ACID, 1000));
		stacks.add(new FluidStack(ModFluids.SODIUM_CYANIDE, 1000));
		return stacks;
	}

	@Nonnull
	public List<ItemStack> getOutputs() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		for(int x = 0; x < EnumElements.size(); x++){
			stacks.add(new ItemStack(ModItems.CHEMICAL_DUSTS, 1, x));
		}
		return stacks;
	}

	@Nonnull
	public List<ItemStack> getCatalysts() {
		ArrayList<ItemStack> stacks = new ArrayList<ItemStack>();
		stacks.add(BaseRecipes.test_tube);
		stacks.add(BaseRecipes.graduated_cylinder);
		stacks.add(BaseRecipes.fe_catalyst);
		return stacks;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputLists(ItemStack.class, Arrays.asList(getInputs(),getCatalysts()));
		ingredients.setOutputs(ItemStack.class, getOutputs());
		ingredients.setInputs(FluidStack.class, getSolvents());
	}

}