package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileStructure;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;


public class TEReformerReactor extends TileEntityInv {
    public static final int SLOT_SYSTEM_CAT[] = new int[]{0,1,2,3,4,5};
    public static final int SLOT_RECIPE_CAT[] = new int[]{6,7,8,9};
	public static int totCatalysts = SLOT_SYSTEM_CAT.length + SLOT_RECIPE_CAT.length;
	
	public int tierS1 = 0;
	public int tierS2 = 0;
	public int tierS3 = 0;

	public int tierR1 = 0;
	public int tierR2 = 0;

	private int countCat;

	public TEReformerReactor() {
		super(totCatalysts, 0, 0, 0);

		this.input =  new MachineStackHandler(totCatalysts, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_SYSTEM_CAT[0] && slot <= SLOT_SYSTEM_CAT[SLOT_SYSTEM_CAT.length-1] && isSystemCatalyst(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot >= SLOT_RECIPE_CAT[0] && slot <= SLOT_RECIPE_CAT[SLOT_RECIPE_CAT.length-1] && isRecipeCatalyst(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	public ItemStack inputSlot(int x){
		return this.input.getStackInSlot(x);
	}


	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "reformer_reactor";
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<GasReformerRecipe> recipeList(){
		return GasReformerRecipes.gas_reformer_recipes;
	}

	public GasReformerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	@Override
	public int getSelectedRecipe() {
		return hasReformer() ? getReformer().getSelectedRecipe() : -1;
	}

	public GasReformerRecipe getCurrentRecipe(){
		return hasReformer() ? getReformer().getDummyRecipe() : null;
	}

	public boolean isValidPreset(){
		return hasReformer() ? getReformer().isValidPreset() : false;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public ItemStack getRecipeCatalyst(){ return isValidPreset() ? recipeList().get(getSelectedRecipe()).getCatalyst() : ItemStack.EMPTY; }



	//----------------------- CUSTOM -----------------------
	public ItemStack catalystA(){ return BaseRecipes.nl_catalyst.copy();}
	public ItemStack catalystB(){ return BaseRecipes.gr_catalyst.copy();}
	public ItemStack catalystC(){ return BaseRecipes.wg_catalyst.copy();}
	public ItemStack catalystD(){ return BaseRecipes.au_catalyst.copy();}

	public boolean isSystemCatalyst(ItemStack insertingStack) {
		return CoreUtils.hasConsumable(catalystA(), insertingStack)
			|| CoreUtils.hasConsumable(catalystB(), insertingStack)
			|| CoreUtils.hasConsumable(catalystC(), insertingStack);
	}

	public boolean isRecipeCatalyst(ItemStack insertingStack) {
		return insertingStack.isItemEqualIgnoreDurability(getRecipeCatalyst())
			|| insertingStack.isItemEqualIgnoreDurability(catalystD());
	}



	//----------------------- STRUCTURE -----------------------
// reformer
	public TEReformerController getReformer(){
		BlockPos reactorPos = this.pos.offset(EnumFacing.DOWN, 1);
		TileEntity te = this.world.getTileEntity(reactorPos);
		if(this.world.getBlockState(reactorPos) != null && te instanceof TEReformerController){
			TEReformerController reactor = (TEReformerController)te;
			if(reactor.getFacing() == getFacing()){
				return reactor;
			}
		}
		return null;
	}

	public boolean hasReformer(){
		return getReformer() != null;
	}

//Unloader
	public TEUnloader getUnloader(){
		BlockPos unloaderPos = this.pos.offset(getFacing(), 5).offset(EnumFacing.DOWN);
		TEUnloader unloader = TileStructure.getUnloader(this.world, unloaderPos, getFacing().getOpposite());
		return unloader != null ? unloader : null;
	}

	public boolean hasUnloader(){
		return getUnloader() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
//		if(!this.world.isRemote){
//			handlePurge();
//		}
	}

	private void handlePurge() {
		if(!isValidPreset() || !isValidRecipe()){
			if(hasUnloader()) {
				if(countCat < SLOT_RECIPE_CAT.length){
					int inp = SLOT_RECIPE_CAT[countCat];
					if(!inputSlot(inp).isEmpty() && !getRecipeCatalyst().isEmpty()){
						if(!isRecipeCatalyst(inputSlot(inp)) ){
							if(((MachineStackHandler) getUnloader().getOutput()).canSetOrStack(getUnloader().unloaderSlot(), inputSlot(inp))){
								((MachineStackHandler) getUnloader().getOutput()).setOrStack(OUTPUT_SLOT, inputSlot(inp));
								this.input.setStackInSlot(inp, ItemStack.EMPTY);
								countCat++;
							}
						}
					}
				}else{
					countCat = 0;
				}
			}
		}
	}

}