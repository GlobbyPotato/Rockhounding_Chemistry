package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class TEFluidpedia extends TileEntityInv {

	public static int inputSlots = 1;
	public static int templateSlots = 69;

	public int charNum = 0;
	public int viewNum = -1;
	public int pageNum = 1;
	public ArrayList<Fluid> FLUID_LIST = new ArrayList<Fluid>();
	public ArrayList<Fluid> PAGED_FLUID_LIST = new ArrayList<Fluid>();

	public TEFluidpedia() {
		super(inputSlots, 0, templateSlots, 0);
		
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public void validateSlotIndex(int slot){
				if(input.getSlots() < inputSlots){
					NonNullList<ItemStack> stacksCloned = stacks;
					input.setSize(inputSlots);
					for(ItemStack stack : stacksCloned){
		                stacks.set(slot, stack);
					}
				}
				super.validateSlotIndex(slot);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && insertingStack.isItemEqual(BaseRecipes.sampling_ampoule) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);

	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charNum = compound.getInteger("Char");
		this.pageNum = compound.getInteger("Page");
		this.viewNum = compound.getInteger("View");
        this.collectFluids(this.getAlphabet(), this.getView());
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("Char", this.charNum);
		compound.setInteger("Page", this.pageNum);
		compound.setInteger("View", this.viewNum);
        return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "fluidpedia";
	}


	
	//----------------------- SLOT -----------------------
	private ItemStack sampleSlot() {
		return this.input.getStackInSlot(INPUT_SLOT);
	}



	//----------------------- CUSTOM -----------------------
	public int getChar() {
		return this.charNum;
	}

	public int getView() {
		return this.viewNum;
	}

	public int getPage() {
		return this.pageNum;
	}

	public String getAlphabet() {
		return String.valueOf((char)(this.getChar() + 65));
	}

	public void collectFluids(String chars, int views) {
		FLUID_LIST = new ArrayList<Fluid>();
		PAGED_FLUID_LIST = new ArrayList<Fluid>();
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()){
			if(fluid.getName().toLowerCase().startsWith(chars.toLowerCase())){
				if((views == 1 && !fluid.isGaseous()) || (views == 2 && fluid.isGaseous()) || views == 0){
					FLUID_LIST.add(fluid);
				}
			}
		}
		int page_split = 36 * (this.getPage() - 1);
		if(page_split < 0) {page_split = 0;}
		for(int x = page_split; x < page_split + 36; x++){
			if(x < FLUID_LIST.size()){
				PAGED_FLUID_LIST.add(FLUID_LIST.get(x));
			}
		}
		Collections.sort(PAGED_FLUID_LIST, COMPARE_BY_NAME);
	}

    public Comparator<Fluid> COMPARE_BY_NAME = new Comparator<Fluid>() {
        public int compare(Fluid one, Fluid other) {
            return one.getName().toLowerCase().compareTo(other.getName().toLowerCase());
        }
    };



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {}

	public void writeAmpoule(int slot) {
   		if(!this.PAGED_FLUID_LIST.isEmpty() && this.PAGED_FLUID_LIST.size() > 0){
			ItemStack heldItem = this.sampleSlot();
			if(!heldItem.isEmpty()){
				if(heldItem.isItemEqual(BaseRecipes.sampling_ampoule)){
					if(!heldItem.hasTagCompound()){heldItem.setTagCompound(new NBTTagCompound());}
					if(heldItem.hasTagCompound()){
				  		if(!this.PAGED_FLUID_LIST.isEmpty() && this.PAGED_FLUID_LIST.size() > 0){
							if(slot < this.PAGED_FLUID_LIST.size()){
								NBTTagCompound gas = new NBTTagCompound();
								FluidStack sample = new FluidStack(this.PAGED_FLUID_LIST.get(slot), 1000);
								if(sample != null && sample.getFluid() != null){
									sample.writeToNBT(gas);
									if(sample.getFluid().isGaseous()){
										ModUtils.setGasFilter(heldItem, gas);
									}else{
										ModUtils.setFluidFilter(heldItem, gas);
									}
								}
							}
				  		}
					}
				}
			}
   		}
	}

}