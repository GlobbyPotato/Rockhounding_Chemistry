package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEGasifierTank extends TileEntityTank implements IToxic{

	public FluidTank inputTank;
	public static int FILL_BUCKET = 0;

	public static int inputSlots = 1;
	public static int templateSlots = 2;

	public FluidStack filter = null;

	public TEGasifierTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillFiltered(fluid);
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.inputTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if (slot == FILL_BUCKET && isValidSlurry(FluidUtil.getFluidContained(insertingStack))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.filter = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Filter"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		if(getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        this.filter.writeToNBT(filterNBT);
	        compound.setTag("Filter", filterNBT);
		}

        return compound;
	}


	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack fillSlot(){
		return this.input.getStackInSlot(FILL_BUCKET);
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<GasifierPlantRecipe> recipeList(){
		return GasifierPlantRecipes.gasifier_plant_recipes;
	}

	public static GasifierPlantRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public static boolean isValidSlurry(FluidStack fluid) {
		return recipeList().stream().anyMatch(recipe -> fluid != null && recipe.getInput() != null && fluid.isFluidEqual(recipe.getInput()));
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gasifier_tank";
	}

	@Override
 	public ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		tanks.add(this.inputTank);
		return tanks;
 	}



	//----------------------- CUSTOM -----------------------
	public FluidStack getFilter(){
		return this.filter;
	}

	public boolean hasFilter(){
		return getFilter() != null;
	}

	public boolean canFillFiltered(FluidStack fluid) {
		return hasFilter() ? getFilter().isFluidEqual(fluid) : isValidSlurry(fluid);
	}



	//----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() {
		return 20000;
	}

	public boolean hasInputFluid(){
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getInputFluid(){
		return hasInputFluid() ? this.inputTank.getFluid() : null;
	}

	public int getInputAmount() {
		return hasInputFluid() ? this.inputTank.getFluidAmount() : 0;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			emptyContainer(FILL_BUCKET, this.inputTank);
			handleToxic(this.world, this.pos);
			this.markDirtyClient();
		}
	}

}