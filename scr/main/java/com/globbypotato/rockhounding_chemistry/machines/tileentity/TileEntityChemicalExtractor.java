package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityChemicalExtractor extends TileEntityMachineTank{

	public int[] elementList = new int[56];

	public boolean drainValve;
	private boolean isInhibited;

	private static final int REDSTONE_SLOT = 3;
	private static final int NITR_SLOT = 4;
	private static final int PHOS_SLOT = 5;
	private static final int CYAN_SLOT = 6;
	private static final int CYLINDER_SLOT = 7;

	private ItemStackHandler template = new TemplateStackHandler(1);

	public FluidTank nitrTank;
	public FluidTank phosTank;
	public FluidTank cyanTank;

	ChemicalExtractorRecipe currentRecipe;

	public TileEntityChemicalExtractor() {
		super(8,56,1);

		nitrTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID));
			}

			@Override
		    public boolean canDrain(){
		        return drainValve;
		    }
		};
		nitrTank.setTileEntity(this);
		nitrTank.setCanFill(true);

		phosTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.PHOSPHORIC_ACID));
			}

			@Override
		    public boolean canDrain(){
		        return drainValve;
		    }
		};
		phosTank.setTileEntity(this);
		phosTank.setCanFill(true);

		cyanTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE));
			}

			@Override
		    public boolean canDrain(){
		        return drainValve;
		    }
		};
		cyanTank.setTileEntity(this);
		cyanTank.setCanFill(true);

		input = new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && CoreUtils.isPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == REDSTONE_SLOT && hasRedstone(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && CoreUtils.hasConsumable(ToolUtils.testTube, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CYLINDER_SLOT && CoreUtils.hasConsumable(ToolUtils.cylinder, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == NITR_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.NITRIC_ACID))) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == PHOS_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.PHOSPHORIC_ACID))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CYAN_SLOT && handleBucket(insertingStack, EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiChemicalExtractor.HEIGHT;
	}

	@Override
	public boolean hasRF(){
		return true;	
	}

	@Override
	public boolean isRFGatedByBlend(){
		return true;
	}

	public int getExtractingFactor(){
		return ModConfig.factorExtractor;
	}

	public int getCookTimeMax(){
		return ModConfig.speedExtractor;
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRecipe(ItemStack stack){
		return MachineRecipes.extractorRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		for(int i = 0; i < elementList.length; i++){
			elementList[i] = compound.getInteger("element" + i);
		}
		this.drainValve = compound.getBoolean("Drain");
		this.nitrTank.readFromNBT(compound.getCompoundTag("NitrTank"));
		this.phosTank.readFromNBT(compound.getCompoundTag("PhosTank"));
		this.cyanTank.readFromNBT(compound.getCompoundTag("CyanTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		for(int i = 0; i < elementList.length; i++){
			compound.setInteger("element" + i, elementList[i]);
		}
		compound.setBoolean("Drain", this.drainValve);

		NBTTagCompound syngasTankNBT = new NBTTagCompound();
		this.nitrTank.writeToNBT(syngasTankNBT);
		compound.setTag("NitrTank", syngasTankNBT);

		NBTTagCompound phosTankNBT = new NBTTagCompound();
		this.phosTank.writeToNBT(phosTankNBT);
		compound.setTag("PhosTank", phosTankNBT);

		NBTTagCompound cyanTankNBT = new NBTTagCompound();
		this.cyanTank.writeToNBT(cyanTankNBT);
		compound.setTag("CyanTank", cyanTankNBT);

		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(lavaTank, nitrTank, phosTank, cyanTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		redstoneHandler(REDSTONE_SLOT, this.getCookTimeMax());
		lavaHandler();

		if(!worldObj.isRemote){
			emptyContainer(NITR_SLOT, nitrTank);
			emptyContainer(PHOS_SLOT, phosTank);
			emptyContainer(CYAN_SLOT, cyanTank);

			if(canExtractElements()){
				execute();
				if(CoreUtils.hasConsumable(ToolUtils.cylinder, input.getStackInSlot(CYLINDER_SLOT))){
					transferDust();
				}
			}
			this.markDirtyClient();
		}
	}

	private void execute() {
		cookTime++;
		powerCount--;
		if(!this.hasFuelBlend()){ redstoneCount--; }
		if(cookTime >= getCookTimeMax()) {
			cookTime = 0; 
			extractElements();
		}
	}

	private void extractElements() {
		if(input.getStackInSlot(INPUT_SLOT) != null){
			if(isFullRecipe() && currentRecipe != null){
				for(int x = 0; x < currentRecipe.getElements().size(); x++){
					for(int y = 0; y < EnumElement.size(); y++){
						if(currentRecipe.getElements().get(x).toLowerCase().matches(EnumElement.getName(y).toLowerCase())){
							isInhibited = false;
							for(int ix = 0; ix < MachineRecipes.inhibitedElements.size(); ix++){
								if(currentRecipe.getElements().get(x).toLowerCase().matches(MachineRecipes.inhibitedElements.get(ix).toLowerCase())){
									isInhibited = true;
								}
							}
							if(!isInhibited){
								elementList[y] += currentRecipe.getQuantities().get(x);
							}
						}
					}
				}
				handleOutput();
			}
		}
	}

	private boolean isFullRecipe() {
		for(ChemicalExtractorRecipe recipe: MachineRecipes.extractorRecipes){
			if(ItemStack.areItemsEqual(recipe.getInput(), input.getStackInSlot(INPUT_SLOT))){
				if(recipe.getElements().size() == recipe.getQuantities().size()){
					int formula = 0;
					for(int x = 0; x < recipe.getElements().size(); x++){
						for(int y = 0; y < EnumElement.size(); y++){
							if(recipe.getElements().get(x).toLowerCase().matches(EnumElement.getName(y).toLowerCase())){
								formula++;
							}
						}
					}
					if(formula == recipe.getElements().size()){
						currentRecipe = recipe;
						return true;
					}
				}
			}
		}
		return false;
	}

	private void handleOutput() {
		input.decrementSlot(INPUT_SLOT);
		input.damageSlot(CONSUMABLE_SLOT);
		input.drainOrClean(nitrTank, ModConfig.consumedNitr, false);
		input.drainOrClean(phosTank, ModConfig.consumedPhos, false);
		input.drainOrClean(cyanTank, ModConfig.consumedCyan, false);
		currentRecipe = null;
	}

	private boolean canExtractElements() {
		return hasRecipe(input.getStackInSlot(INPUT_SLOT))
			&& isFullRecipe()
			&& CoreUtils.hasConsumable(ToolUtils.testTube, input.getStackInSlot(CONSUMABLE_SLOT))
			&& CoreUtils.hasConsumable(ToolUtils.cylinder, input.getStackInSlot(CYLINDER_SLOT))
			&& getPower() >= getCookTimeMax()
			&& isRedstoneRequired(this.getCookTimeMax()) 
			&& nitrTank.getFluidAmount() >= ModConfig.consumedNitr
			&& phosTank.getFluidAmount() >= ModConfig.consumedPhos
			&& cyanTank.getFluidAmount() >= ModConfig.consumedCyan;
	}

	private void transferDust() {
		for(int i = 0; i < output.getSlots(); i++){
			if(CoreUtils.hasConsumable(ToolUtils.cylinder, input.getStackInSlot(CYLINDER_SLOT))){
				ItemStack elementStack = BaseRecipes.elements(1, i);
				if(output.canSetOrStack(output.getStackInSlot(i), elementStack)){
					if(elementList[i] >= getExtractingFactor()){
						elementList[i]-= getExtractingFactor();
						output.setOrIncrement(i, elementStack);
						input.damageSlot(CYLINDER_SLOT);
					}
				}
			}
		}
	}

}