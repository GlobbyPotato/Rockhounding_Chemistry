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
	private static final int SPEED_SLOT = 8;

	private ItemStackHandler template = new TemplateStackHandler(1);

	public FluidTank nitrTank;
	public FluidTank phosTank;
	public FluidTank cyanTank;

	ChemicalExtractorRecipe currentRecipe;

	public static int totInput = 9;
	public static int totOutput = 56;

	public TileEntityChemicalExtractor() {
		super(totInput, totOutput, 1);

		this.nitrTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.NITRIC_ACID));
			}

			@Override
		    public boolean canDrain(){
		        return TileEntityChemicalExtractor.this.drainValve;
		    }
		};
		this.nitrTank.setTileEntity(this);
		this.nitrTank.setCanFill(true);

		this.phosTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.PHOSPHORIC_ACID));
			}

			@Override
		    public boolean canDrain(){
		        return TileEntityChemicalExtractor.this.drainValve;
		    }
		};
		this.phosTank.setTileEntity(this);
		this.phosTank.setCanFill(true);

		this.cyanTank = new FluidTank(1000 + ModConfig.machineTank){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
				return fluid.getFluid().equals(EnumFluid.pickFluid(EnumFluid.SODIUM_CYANIDE));
			}

			@Override
		    public boolean canDrain(){
		        return TileEntityChemicalExtractor.this.drainValve;
		    }
		};
		this.cyanTank.setTileEntity(this);
		this.cyanTank.setCanFill(true);

		this.input = new MachineStackHandler(totInput,this){
			@Override
			public void validateSlotIndex(int slot){
				if(input.getSlots() < totInput){
					ItemStack[] stacksCloned = stacks;
					input.setSize(totInput);
					for(int x = 0; x < stacksCloned.length; x++){
						stacks[x] = stacksCloned[x];
					}
				}
				super.validateSlotIndex(slot);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == TileEntityChemicalExtractor.this.FUEL_SLOT && isGatedPowerSource(insertingStack)){
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
				if(slot == SPEED_SLOT && ToolUtils.isValidSpeedUpgrade(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot(){
		return this.input.getStackInSlot(SPEED_SLOT);
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

	public int baseSpeed(){
		return ModConfig.speedExtractor;
	}

	public int getExtractingFactor(){
		return ModConfig.factorExtractor;
	}

	public int speedAnalyzer() {
		return ToolUtils.isValidSpeedUpgrade(speedSlot()) ? baseSpeed() / ToolUtils.speedUpgrade(speedSlot()): baseSpeed();
	}

	public int getCookTimeMax() {
		return speedAnalyzer();
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
		for(int i = 0; i < this.elementList.length; i++){
			this.elementList[i] = compound.getInteger("element" + i);
		}
		this.drainValve = compound.getBoolean("Drain");
		this.nitrTank.readFromNBT(compound.getCompoundTag("NitrTank"));
		this.phosTank.readFromNBT(compound.getCompoundTag("PhosTank"));
		this.cyanTank.readFromNBT(compound.getCompoundTag("CyanTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		for(int i = 0; i < this.elementList.length; i++){
			compound.setInteger("element" + i, this.elementList[i]);
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
		return new FluidHandlerConcatenate(this.lavaTank, this.nitrTank, this.phosTank, this.cyanTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		acceptEnergy();
		fuelHandler(this.input.getStackInSlot(this.FUEL_SLOT));
		redstoneHandler(REDSTONE_SLOT, baseSpeed());
		lavaHandler();

		if(!this.worldObj.isRemote){
			emptyContainer(NITR_SLOT, this.nitrTank);
			emptyContainer(PHOS_SLOT, this.phosTank);
			emptyContainer(CYAN_SLOT, this.cyanTank);

			if(canExtractElements()){
				execute();
				if(CoreUtils.hasConsumable(ToolUtils.cylinder, this.input.getStackInSlot(CYLINDER_SLOT))){
					transferDust();
				}
			}
			this.markDirtyClient();
		}
	}

	private void execute() {
		this.cookTime++;
		this.powerCount--;
		if(!this.hasFuelBlend()){ this.redstoneCount--; }
		if(this.cookTime >= getCookTimeMax()) {
			this.cookTime = 0; 
			extractElements();
		}
	}

	private void extractElements() {
		if(this.input.getStackInSlot(INPUT_SLOT) != null){
			if(isFullRecipe() && this.currentRecipe != null){
				for(int x = 0; x < this.currentRecipe.getElements().size(); x++){
					for(int y = 0; y < EnumElement.size(); y++){
						if(this.currentRecipe.getElements().get(x).toLowerCase().matches(EnumElement.getName(y).toLowerCase())){
							this.isInhibited = false;
							for(int ix = 0; ix < MachineRecipes.inhibitedElements.size(); ix++){
								if(this.currentRecipe.getElements().get(x).toLowerCase().matches(MachineRecipes.inhibitedElements.get(ix).toLowerCase())){
									this.isInhibited = true;
								}
							}
							if(!this.isInhibited){
								this.elementList[y] += this.currentRecipe.getQuantities().get(x);
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
			if(ItemStack.areItemsEqual(recipe.getInput(), this.input.getStackInSlot(INPUT_SLOT))){
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
						this.currentRecipe = recipe;
						return true;
					}
				}
			}
		}
		return false;
	}

	private void handleOutput() {
		this.input.decrementSlot(INPUT_SLOT);
		this.input.damageSlot(CONSUMABLE_SLOT);
		this.input.drainOrClean(this.nitrTank, ModConfig.consumedNitr, false);
		this.input.drainOrClean(this.phosTank, ModConfig.consumedPhos, false);
		this.input.drainOrClean(this.cyanTank, ModConfig.consumedCyan, false);
		this.currentRecipe = null;
	}

	private boolean canExtractElements() {
		return hasRecipe(this.input.getStackInSlot(INPUT_SLOT))
			&& isFullRecipe()
			&& CoreUtils.hasConsumable(ToolUtils.testTube, this.input.getStackInSlot(CONSUMABLE_SLOT))
			&& CoreUtils.hasConsumable(ToolUtils.cylinder, this.input.getStackInSlot(CYLINDER_SLOT))
			&& getPower() >= getCookTimeMax()
			&& isRedstoneRequired(this.getCookTimeMax()) 
			&& this.nitrTank.getFluidAmount() >= ModConfig.consumedNitr
			&& this.phosTank.getFluidAmount() >= ModConfig.consumedPhos
			&& this.cyanTank.getFluidAmount() >= ModConfig.consumedCyan;
	}

	private void transferDust() {
		for(int i = 0; i < this.output.getSlots(); i++){
			if(CoreUtils.hasConsumable(ToolUtils.cylinder, this.input.getStackInSlot(CYLINDER_SLOT))){
				ItemStack elementStack = BaseRecipes.elements(1, i);
				if(this.output.canSetOrStack(this.output.getStackInSlot(i), elementStack)){
					if(this.elementList[i] >= getExtractingFactor()){
						this.elementList[i]-= getExtractingFactor();
						this.output.setOrIncrement(i, elementStack);
						this.input.damageSlot(CYLINDER_SLOT);
					}
				}
			}
		}
	}

}