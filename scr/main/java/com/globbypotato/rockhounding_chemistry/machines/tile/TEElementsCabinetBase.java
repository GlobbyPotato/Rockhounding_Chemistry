package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipes;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TEElementsCabinetBase extends TileEntityInv {

	public int[] elementList = new int[56];
	private int currentElement;
	
	public TEElementsCabinetBase() {
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
		return "extractor_cabinet_base";
	}



	//----------------------- CUSTOM -----------------------
	public int getExtractingFactor(){
		return ModConfig.extractorFactor;
	}



	//----------------------- STRUCTURE -----------------------
//injector
	public TEElementsCabinetTop getInjector(){
		BlockPos injectorPos = this.pos.offset(EnumFacing.UP, 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEElementsCabinetTop){
			TEElementsCabinetTop injector = (TEElementsCabinetTop)te;
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
		BlockPos injectorPos = this.pos.offset(isFacingAt(270), 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEExtractorBalance){
			TEExtractorBalance balance = (TEExtractorBalance)te;
			if(balance.getFacing() == isFacingAt(270).getOpposite()){
				return balance;
			}
		}
		return null;
	}

	public boolean hasBalance(){
		return getBalance() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			if(hasBalance()){
				if(getCooktime() >= ModConfig.cabinetTiming){
					ItemStack elementStack = BaseRecipes.elements(1, EnumElements.values()[this.currentElement]);
					while(hasCylinder() && this.elementList[this.currentElement] >= getExtractingFactor() && canBalance(elementStack)){
						this.elementList[this.currentElement] -= getExtractingFactor();
						((MachineStackHandler) getBalance().getOutput()).setOrIncrement(TileEntityInv.OUTPUT_SLOT, elementStack);
						((MachineStackHandler) getInjector().getInput()).damageSlot(TEElementsCabinetTop.CYLINDER_SLOT);
					}
					if(this.currentElement >= this.elementList.length - 1){
						this.currentElement = 0;
					}else{
						this.currentElement++;
					}
					this.cooktime = 0;
					this.markDirtyClient();
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