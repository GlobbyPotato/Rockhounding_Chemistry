package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.GasifierPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasifierPlantRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.IToxic;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEReinforcedCistern extends TileEntityTank implements IToxic{

	public FluidTank inputTank;
	public static int FILL_BUCKET = 0;

	public static int templateSlots = 2;

	public FluidStack filter = null;

	public TEReinforcedCistern() {
		super(0, 0, templateSlots, 0);

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
		return "reinforced_cistern";
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
			handleToxic(this.world, this.pos);
			pushFluid();
			this.markDirtyClient();
		}
	}


	private void pushFluid() {
		BlockPos cisternPos = this.pos.offset(EnumFacing.DOWN);
		TileEntity te = this.world.getTileEntity(cisternPos);
		if(this.world.getBlockState(cisternPos) != null && te instanceof TEReinforcedCistern){
			TEReinforcedCistern tank = (TEReinforcedCistern)te;
			if(hasInputFluid()) {
				int wantFluid = (tank.getTankCapacity() - tank.getInputAmount());
				int amount = wantFluid <= getInputAmount() ? wantFluid : getInputAmount(); 
				if(this.input.canSetOrAddFluid(tank.inputTank, tank.inputTank.getFluid(), getInputFluid(), amount)) {
					tank.input.setOrFillFluid(tank.inputTank, getInputFluid(), amount);
					this.input.drainOrCleanFluid(inputTank, amount, true);
				}
			}
		}

	}

}