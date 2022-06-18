package com.globbypotato.rockhounding_chemistry.machines.tile.structure;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.recipe.VatAgitatorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.VatAgitatorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.IToxic;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TELeachingVatTank extends TileEntityTank implements IToxic{

	public static int inputSlots = 1;
	public static int templateSlots = 2;

	public FluidTank inputTank;
	public float rotation;

	public FluidStack filter = null;

	public TELeachingVatTank() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(1000) {
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
				if(slot == INPUT_SLOT && CoreUtils.hasConsumable(isValidAgitator(insertingStack), insertingStack) ){
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
	public void readFromNBT(NBTTagCompound compound){
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.rotation = compound.getFloat("Rotation");
		this.filter = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("Filter"));

		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);

		compound.setFloat("Rotation", getRotation());

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
	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(this.inputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack gearSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "leaching_vat_tank";
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

	public boolean hasConsumables(){
		return CoreUtils.hasConsumable(getValidGear(INPUT_SLOT), gearSlot());
	}

	public ItemStack getValidGear(int gear) {
		if(!gearSlot().isEmpty()) {
			for(VatAgitatorRecipe recipe: recipeList()){
				if(recipe.getAgitator().isItemEqualIgnoreDurability(gearSlot())) {
					return recipe.getAgitator();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public static ArrayList<VatAgitatorRecipe> recipeList(){
		return VatAgitatorRecipes.leaching_vat_agitator;
	}

	public static VatAgitatorRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public ItemStack isValidAgitator(ItemStack insertingStack) {
		if(!insertingStack.isEmpty()) {
			for(VatAgitatorRecipe recipe: recipeList()){
				if(recipe.getAgitator().isItemEqualIgnoreDurability(insertingStack)) {
					return recipe.getAgitator();
				}
			}
		}
		return ItemStack.EMPTY;
	}

	public float getRotation(){
		return this.rotation;
	}

	public boolean hasTankFluid() {
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getTankFluid(){
		return hasTankFluid() ? this.inputTank.getFluid() : null;
	}

	public int getTankAmount() {
		return hasTankFluid() ? this.inputTank.getFluidAmount() : 0;
	}

	public int getTankCapacity() {
		return this.inputTank.getCapacity();
	}

	public boolean canFillFiltered(FluidStack fluid) {
		return hasFilter() ? getFilter().isFluidEqual(fluid) : true;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			handleToxic(this.world, this.pos);

			if(this.hasTankFluid() && this.hasConsumables()){
				this.rotation += 15;
				if(this.rotation >= 179){
					this.rotation = 0;
				}
			}
			this.markDirtyClient();
		}
	}

}