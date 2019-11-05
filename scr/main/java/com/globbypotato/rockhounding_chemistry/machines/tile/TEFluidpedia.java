package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class TEFluidpedia extends TileEntityInv {

	public static int templateSlots = 43;

	public int charNum = 0;
	public int viewNum = -1;
	public int pageNum = 1;
	public ArrayList<Fluid> FLUID_LIST = new ArrayList<Fluid>();
	public ArrayList<Fluid> PAGED_FLUID_LIST = new ArrayList<Fluid>();

	public TEFluidpedia() {
		super(0, 0, templateSlots, 0);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.charNum = compound.getInteger("Char");
//		this.pageNum = compound.getInteger("Page");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("Char", this.charNum);
//		compound.setInteger("Page", this.pageNum);
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

}