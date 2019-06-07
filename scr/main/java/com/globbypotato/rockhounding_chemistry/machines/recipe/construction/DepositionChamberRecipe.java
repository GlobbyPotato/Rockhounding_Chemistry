package com.globbypotato.rockhounding_chemistry.machines.recipe.construction;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class DepositionChamberRecipe {

	private ItemStack input, output;
	private FluidStack solvent, carrier;
	private int temp, press;
	private String oredict, name;
	private boolean type;

	public DepositionChamberRecipe(String name, ItemStack input, String oredict, boolean type, ItemStack output, FluidStack solvent, int temp, int press, FluidStack carrier){
		this.name = name;
		this.input = input;
		this.output = output;
		this.solvent = solvent;
		this.temp = temp;
		this.press = press;
		this.oredict = oredict;
		this.type = type;
		this.carrier = carrier;
	}

	public DepositionChamberRecipe(String name, ItemStack input, ItemStack output, FluidStack solvent, int temp, int press, FluidStack carrier){
		this(name, input, "", false, output, solvent, temp, press, carrier);
	}

	public DepositionChamberRecipe(String name, String oredict, ItemStack output, FluidStack solvent, int temp, int press, FluidStack carrier){
		this(name, ItemStack.EMPTY, oredict, true, output, solvent, temp, press, carrier);
	}

	public String getRecipeName(){
		return this.name;
	}

	public ItemStack getInput(){
		if(!this.input.isEmpty()) return this.input.copy();
		return ItemStack.EMPTY;
	}

	public String getOredict(){
		return this.oredict;
	}

	public boolean getType(){
		return this.type;
	}

	public ItemStack getOutput() {
		if(!this.output.isEmpty()) return this.output.copy();
		return ItemStack.EMPTY;
	}

	public FluidStack getSolvent(){
		if(this.solvent != null) return this.solvent.copy();
		return null;
	}

	public FluidStack getCarrier(){
		if(this.carrier != null) return this.carrier.copy();
		return null;
	}

	public int getTemperature() {
		return this.temp;
	}

	public int getPressure() {
		return this.press;
	}

}