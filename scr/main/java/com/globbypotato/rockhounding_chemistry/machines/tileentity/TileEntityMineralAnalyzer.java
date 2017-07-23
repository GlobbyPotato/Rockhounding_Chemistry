package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralAnalyzerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.ProbabilityStack;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMineralAnalyzer extends TileEntityMachineTank{

	private ItemStackHandler template = new TemplateStackHandler(1);

	private static final int SULFUR_SLOT = 3;
	private static final int CHLOR_SLOT = 4;
	private static final int FLUO_SLOT = 5;

	public FluidTank sulfTank;
	public FluidTank chloTank;
	public FluidTank fluoTank;
	public boolean drainValve;

	public TileEntityMineralAnalyzer() {
		super(6,1,1);

		sulfTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID));
		    }

			@Override
		    public boolean canDrain(){
		        return drainValve;
		    }
		};
		sulfTank.setTileEntity(this);

		chloTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID));
		    }

			@Override
		    public boolean canDrain(){
		        return drainValve;
		    }
		};
		chloTank.setTileEntity(this);

		fluoTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.HYDROFLUORIC_ACID));
		    }

			@Override
		    public boolean canDrain(){
		        return drainValve;
		    }
		};
		fluoTank.setTileEntity(this);

		input =  new MachineStackHandler(INPUT_SLOTS, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && CoreUtils.isPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && CoreUtils.hasConsumable(ToolUtils.agitator, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SULFUR_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.SULFURIC_ACID)) ) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CHLOR_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.HYDROCHLORIC_ACID)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FLUO_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.HYDROFLUORIC_ACID)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralAnalyzer.HEIGHT;
	}

	public int getCookTimeMax(){
		return ModConfig.speedAnalyzer;
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRecipe(ItemStack stack){
		return MachineRecipes.analyzerRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}

	private MineralAnalyzerRecipe getRecipe (int x){
		return MachineRecipes.analyzerRecipes.get(x);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.drainValve = compound.getBoolean("Drain");
		this.sulfTank.readFromNBT(compound.getCompoundTag("SulfTank"));
		this.chloTank.readFromNBT(compound.getCompoundTag("ChloTank"));
		this.fluoTank.readFromNBT(compound.getCompoundTag("FluoTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Drain", this.drainValve);

		NBTTagCompound sulfTankNBT = new NBTTagCompound();
		this.sulfTank.writeToNBT(sulfTankNBT);
		compound.setTag("SulfTank", sulfTankNBT);

		NBTTagCompound chloTankNBT = new NBTTagCompound();
		this.chloTank.writeToNBT(chloTankNBT);
		compound.setTag("ChloTank", chloTankNBT);

		NBTTagCompound fluoTankNBT = new NBTTagCompound();
		this.fluoTank.writeToNBT(fluoTankNBT);
		compound.setTag("FluoTank", fluoTankNBT);

		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(lavaTank, sulfTank, chloTank, fluoTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		lavaHandler();
		if(!worldObj.isRemote){
			emptyContainer(SULFUR_SLOT, sulfTank);
			emptyContainer(CHLOR_SLOT, chloTank);
			emptyContainer(FLUO_SLOT, fluoTank);

			if(canAnalyze()){
				cookTime++; powerCount--;
				if(cookTime >= getCookTimeMax()) {
					cookTime = 0; 
					analyze();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canAnalyze() {
		return output.getStackInSlot(OUTPUT_SLOT) == null
			&& hasRecipe(input.getStackInSlot(INPUT_SLOT))
			&& CoreUtils.hasConsumable(ToolUtils.agitator, input.getStackInSlot(CONSUMABLE_SLOT))
			&& getPower() >= getCookTimeMax()
			&& this.sulfTank.getFluidAmount() >= ModConfig.consumedSulf
			&& this.chloTank.getFluidAmount() >= ModConfig.consumedChlo
			&& this.fluoTank.getFluidAmount() >= ModConfig.consumedFluo;
	}

	private void analyze(){
		for(int x = 0; x < MachineRecipes.analyzerRecipes.size(); x++){
			if(getRecipe(x).getInput() != null && ItemStack.areItemsEqual(getRecipe(x).getInput(), input.getStackInSlot(INPUT_SLOT))){
				int mix = getRecipe(x).getOutput().size();
				if(mix > 1){
					output.setStackInSlot(OUTPUT_SLOT, ProbabilityStack.calculateProbability(getRecipe(x).getProbabilityStack()).copy());
					output.getStackInSlot(OUTPUT_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral) + 1;
				}else{
					output.setStackInSlot(OUTPUT_SLOT, getRecipe(x).getOutput().get(0).copy());
				}
			}
		}
		input.damageSlot(CONSUMABLE_SLOT);
		input.decrementSlot(INPUT_SLOT);
		input.drainOrClean(sulfTank, ModConfig.consumedSulf, false);
		input.drainOrClean(chloTank, ModConfig.consumedChlo, false);
		input.drainOrClean(fluoTank, ModConfig.consumedFluo, false);
	}

}