package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class TEMaterialCabinetBase extends TileEntityInv {

	public int[] elementList = new int[63];
	private int currentElement;

	public TEMaterialCabinetBase() {
		super(0, 0, 0, 0);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		if(compound.hasKey("ElementsList")){
			NBTTagCompound elements = compound.getCompoundTag("ElementsList");
			for(int i = 0; i < this.elementList.length; i++){
				this.elementList[i] = elements.getInteger("element" + i);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		NBTTagCompound elements = new NBTTagCompound();
		for(int i = 0; i < this.elementList.length; i++){
			elements.setInteger("element" + i, this.elementList[i]);
		}
		compound.setTag("ElementsList", elements);
		return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "material_cabinet_base";
	}



	//----------------------- CUSTOM -----------------------
	public int getExtractingFactor(){
		return ModConfig.extractorFactor;
	}



	//----------------------- STRUCTURE -----------------------
//injector
	public TEMaterialCabinetTop getInjector(){
		BlockPos injectorPos = this.pos.offset(EnumFacing.UP, 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEMaterialCabinetTop){
			TEMaterialCabinetTop injector = (TEMaterialCabinetTop)te;
			if(injector.getFacing() == getFacing()){
				return injector;
			}
		}
		return null;
	}

	public boolean hasInjector(){
		return getInjector() != null;
	}

	private boolean hasCylinder() {
		return hasInjector() 
			&& CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, getInjector().getInput().getStackInSlot(TEElementsCabinetTop.CYLINDER_SLOT));
	}

//balance
	public TEExtractorBalance getBalance(){
		BlockPos injectorPos = this.pos.offset(isFacingAt(90), 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEExtractorBalance){
			TEExtractorBalance balance = (TEExtractorBalance)te;
			if(balance.getFacing() == isFacingAt(90).getOpposite()){
				return balance;
			}
		}
		return null;
	}

	public boolean hasBalance(){
		return getBalance() != null;
	}


	
	//----------------------- RECIPE -----------------------
	public ArrayList<MaterialCabinetRecipe> recipeList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}

	public MaterialCabinetRecipe getRecipeList(int x){
		return recipeList().get(x);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			if(hasBalance()){
				if(getCooktime() >= ModConfig.cabinetTiming){
					NonNullList<ItemStack> oredictedList = OreDictionary.getOres(getRecipeList(this.currentElement).getOredict());
					if(!oredictedList.isEmpty() && oredictedList.size() > 0){
						ItemStack elementStack = oredictedList.get(0).copy();
						if(!oredictedList.get(0).isEmpty()){
							while(hasCylinder() && this.elementList[this.currentElement] >= getExtractingFactor() && canBalance(elementStack)){
								this.elementList[this.currentElement] -= getExtractingFactor();
								((MachineStackHandler) getBalance().getOutput()).setOrIncrement(TileEntityInv.OUTPUT_SLOT, elementStack);
								((MachineStackHandler) getInjector().getInput()).damageSlot(TEMaterialCabinetTop.CYLINDER_SLOT);
							}
							if(this.currentElement < recipeList().size() - 1){
								this.currentElement++;
							}else{
								this.currentElement = 0;
							}
							this.cooktime = 0;
							this.markDirtyClient();
						}
					}else{
						this.currentElement = 0;
						this.cooktime = 0;
						this.markDirty();
					}
				}else{
					this.cooktime++;
					this.markDirty();
				}
			}
		}
	}

	private boolean canBalance(ItemStack elementStack) {
		return ((MachineStackHandler) getBalance().getOutput()).canSetOrStack(getBalance().getOutput().getStackInSlot(TileEntityInv.OUTPUT_SLOT), elementStack);
	}

}