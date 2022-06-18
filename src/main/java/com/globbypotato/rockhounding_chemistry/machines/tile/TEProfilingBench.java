package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ProfilingBenchRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ProfilingBenchRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

public class TEProfilingBench extends TileEntityInv implements IInternalServer{

	public static final int PURGE_SLOT = 1;

	public int currentCast;
	public ItemStack filter = ItemStack.EMPTY;

	public static int inputSlots = 1;
	public static int outputSlots = 2;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public TEProfilingBench() {
		super(inputSlots, outputSlots, 0, 0);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isInputEmpty() && isValidInput(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

	}



	// ----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.currentCast = compound.getInteger("Casting");
		if(hasFilterItem(compound)){
			this.filter = getFilterItem(compound);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        compound.setInteger("Casting", getCasting());
		if(!this.getFilter().isEmpty()){
			NBTTagCompound filterstack = new NBTTagCompound(); 
			this.filter.writeToNBT(filterstack);
			compound.setTag(tag_filter_item, filterstack);
		}
		return compound;
	}



	// ----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}
	
	public ItemStack outputSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}	

	public ItemStack purgeSlot() {
		return this.output.getStackInSlot(PURGE_SLOT);
	}



	//----------------------- HANDLER -----------------------
	public int getCooktimeMax(){
		return 30;
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public int getCurrentCast(){
		return this.currentCast;
	}

	public static String getName(){
		return "profiling_bench";
	}

	//server
	private static int deviceCode() {
		return EnumServer.CASTING.ordinal();
	}



	//----------------------- CUSTOM -----------------------
	public int getCasting(){
		return this.currentCast;
	}

	public boolean isProcessing(){
		return canProcess() && getCooktime() > 0;
	}

	boolean isInputEmpty() {
		return inputSlot().isEmpty();
	}

	private boolean isOutputEmpty() {
		return outputSlot().isEmpty();
	}

	public ItemStack getFilter() {
		return this.filter;
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<ProfilingBenchRecipe> recipeList(){
		return ProfilingBenchRecipes.profiling_bench_recipes;
	}

	public ProfilingBenchRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public boolean isValidInput(ItemStack stack){
		if(!stack.isEmpty()){
			if(!getFilter().isEmpty()){
				if(getFilter().isItemEqual(stack)){
					return true;
				}
			}else{
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(stack));
				for(ProfilingBenchRecipe recipe: recipeList()){
					if(recipe.getType()){
						if(!inputOreIDs.isEmpty()){
							if(inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict()))){
								return true;
							}
						}
					}else{
						if(recipe.getInput().isItemEqual(stack)){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public ProfilingBenchRecipe getCurrentRecipe(){
		if(!inputSlot().isEmpty()){
			for(int x = 0; x < recipeList().size(); x++){
				ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
				if(getRecipeList(x).getType()){
					if(inputOreIDs.contains(OreDictionary.getOreID(getRecipeList(x).getOredict()))){
						if(getRecipeList(x).getCasting() == getCurrentCast()){
							return getRecipeList(x);
						}
					}
				}else{
					if(getRecipeList(x).getInput().isItemEqual(inputSlot())){
						if(getRecipeList(x).getCasting() == getCurrentCast()){
							return getRecipeList(x);
						}
					}
				}
			}
		}
		return null;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}



	//----------------------- SERVER -----------------------
//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			handlePurge();

			//server
			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, EnumCasting.size());

			if(!inputSlot().isEmpty()){
				if(canProcess()){
					this.cooktime++;
					if(this.cooktime >= getCooktimeMax()) {
						this.cooktime = 0;
						process();
					}
				}
			}
			this.markDirtyClient();
		}
	}

	private void handlePurge() {
		if(!this.inputSlot().isEmpty() && !this.getFilter().isEmpty() && !CoreUtils.isMatchingIngredient(inputSlot(), getFilter())){
			if(this.output.canSetOrStack(purgeSlot(), inputSlot())){
				this.output.setOrStack(PURGE_SLOT, inputSlot());
				this.input.setStackInSlot(INPUT_SLOT, ItemStack.EMPTY);
			}
		}
	}

	private boolean canProcess() {
		return isOutputEmpty()
			&& isValidRecipe()
			&& handleFilter(inputSlot(), getFilter()) //server
			&& handleServer(getServer(), this.currentFile); //server
	}

	private void process() {
		this.output.setStackInSlot(OUTPUT_SLOT, getCurrentRecipe().getOutput());
		this.input.decrementSlot(INPUT_SLOT);

		//server
		updateServer(getServer(), this.currentFile);

	}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	@Override
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(getServer().isValidFile(fileSlot)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isWrittenFile(tag)){
							if(isCorrectDevice(tag, deviceCode())){
								if(getRecipe(tag) < 16){
									if(getDone(tag) > 0){
										this.currentCast = getRecipe(tag);
										this.currentFile = x;
										if(hasFilterItem(tag)){
											ItemStack temp = getFilterItem(tag);
											if(this.getFilter().isEmpty() || !this.getFilter().isItemEqual(temp)){
												this.filter = temp;
											}
										}
										break;
									}
								}
							}
						}
					}
				}
				if(x == TEServer.FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}

}