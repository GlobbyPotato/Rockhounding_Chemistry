package com.globbypotato.rockhounding_chemistry.machines.recipe;

import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumFluid;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class LabOvenRecipe {

	private final ItemStack solute;
	private final EnumFluid solvent;
	private final EnumFluid output;
	
	public LabOvenRecipe(ItemStack solute, EnumFluid solvent, EnumFluid output){
		this.solute = solute;
		this.solvent = solvent;
		this.output = output;
	}
	
	public LabOvenRecipe(Item solute, int meta, EnumFluid solvent, EnumFluid output){
		this(new ItemStack(solute,1,meta),solvent,output);

	}
	
	public ItemStack getSolute(){
		return this.solute.copy();
	}
	
	public EnumFluid getSolvent(){
		return this.solvent;
	}
	
	public EnumFluid getOutput(){
		return this.output;
	}
}
