package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumStructure;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PlanningTableRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PlanningTableRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;

public class TEPlanningTable extends TileEntityInv {
	
	public static int inputSlots = 16;
	public static int templateSlots = 19;

	public TEPlanningTable() {
		super(inputSlots, 0, templateSlots, 0);
		
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= 0 && slot < 16 && isValidInput(slot, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(int slot){
		return this.input.getStackInSlot(slot);
	}

	public ItemStack previewSlot(int slot){
		return this.template.getStackInSlot(slot);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "planning_table";
	}



	//----------------------- CUSTOM -----------------------
	boolean isValidInput(int slot, ItemStack stack) {
		if(isValidPreset() && slot < totalInputs().size()){
			if(stack.isItemEqual(previewSlot(slot)) && inputSlot(slot).getCount() < previewSlot(slot).getCount()) {
				return true;
			}
		}
		return false;
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<PlanningTableRecipe> recipeList(){
		return PlanningTableRecipes.planning_table_recipes;
	}

	public PlanningTableRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public ArrayList<ItemStack> totalInputs() {
		return getRecipeList(getSelectedRecipe()).getInputs();
	}

	private int getOrder() {
		return getRecipeList(getSelectedRecipe()).getOrder();
	}


	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!world.isRemote) {

		}
		
	}

	public void shrinkInputs() {
		for(int x = 0; x < inputSlots; x++) {
			this.input.decrementSlotBy(x, previewSlot(x).getCount());
		}
	}

	public void showPreview() {
		//clean preview
		for(int x = 0; x < templateSlots; x++) {
			this.template.setStackInSlot(x, ItemStack.EMPTY);
		}

		if(isValidPreset()) {
			//load new preview
			for(int x = 0; x < totalInputs().size(); x++) {
				this.template.setStackInSlot(x, totalInputs().get(x));
			}
		}
	}

	public boolean countResources() {
		int totWant = 0; int totHave = 0;
		if(isValidPreset()) {
			for(int x = 0; x < totalInputs().size(); x++) {
				totWant += previewSlot(x).getCount();
				if(!inputSlot(x).isEmpty()) {
					totHave += inputSlot(x).getCount();
				}else {
					return false;
				}
			}
		}
		return isValidPreset() && totHave == totWant;
	}

	public void assemblyStructure() {
		if(countResources()) {
			if(!world.isRemote) {
					if(getOrder() == EnumStructure.OVEN.ordinal()) {
					TileAssembler.loadLabOven(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.EXTRACTOR.ordinal()) {
					TileAssembler.loadChemicalExtractor(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.PRECIPITATOR.ordinal()) {
					TileAssembler.loadPrecipitator(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.LEACHING.ordinal()) {
					TileAssembler.loadLeaching(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.RETENTION.ordinal()) {
					TileAssembler.loadRetention(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.CONDENSER.ordinal()) {
					TileAssembler.loadCondenser(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.EXPANDER.ordinal()) {
					TileAssembler.loadExpander(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.BLENDER.ordinal()) {
					TileAssembler.loadBlender(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.MIXER.ordinal()) {
					TileAssembler.loadMixer(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.COMPRESSOR.ordinal()) {
					TileAssembler.loadCompressor(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.PROFILING.ordinal()) {
					TileAssembler.loadProfiling(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.POND.ordinal()) {
					TileAssembler.loadPond(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.ALLOYER.ordinal()) {
					TileAssembler.loadAlloyer(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.ORBITER.ordinal()) {
					TileAssembler.loadOrbiter(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.GASIFIER.ordinal()) {
					TileAssembler.loadGasifier(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.SIZER.ordinal()) {
					TileAssembler.loadSizer(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.EXCHANGER.ordinal()) {
					TileAssembler.loadExchanger(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.CSTR.ordinal()) {
					TileAssembler.loadCSTR(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.PULLING.ordinal()) {
					TileAssembler.loadPulling(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.SHREDDER.ordinal()) {
					TileAssembler.loadShredder(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.DEPOSITION.ordinal()) {
					TileAssembler.loadDeposition(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.PURIFIER.ordinal()) {
					TileAssembler.loadPurifier(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.REFORMER.ordinal()) {
					TileAssembler.loadReformer(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.GAN.ordinal()) {
					TileAssembler.loadGan(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.TUBULAR.ordinal()) {
					TileAssembler.loadTubular(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}else if(getOrder() == EnumStructure.REGEN.ordinal()) {
					TileAssembler.loadRegen(pos, world, getFacing(), isFacingAt(270), isFacingAt(90));
				}

				shrinkInputs();
				markDirtyClient();
			}
		}
	}

}