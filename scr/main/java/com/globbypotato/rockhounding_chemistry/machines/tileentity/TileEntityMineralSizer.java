package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralSizer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MineralSizerRecipe;
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
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMineralSizer extends TileEntityMachineTank {

	public static final int GOOD_SLOT = 1;
	public static final int WASTE_SLOT = 2;
	public int slider;
	private ItemStackHandler template = new TemplateStackHandler(3);

	public TileEntityMineralSizer() {
		super(3,3,1);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && isGatedPowerSource(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && CoreUtils.hasConsumable(ToolUtils.gear, insertingStack)){
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
		return GuiMineralSizer.HEIGHT;
	}

	public int getMaxCookTime(){
		return getRecipe() != null && getRecipe().getComminution() && getRecipe().getOutput().size() > 1 ? ModConfig.speedSizer + (getSlider() * 30) : ModConfig.speedSizer;
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRecipe(ItemStack stack){
		return MachineRecipes.sizerRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}

	private boolean allOutputsEmpty(){
		return (output.getStackInSlot(OUTPUT_SLOT) == null
			&& output.getStackInSlot(GOOD_SLOT) == null
			&& output.getStackInSlot(WASTE_SLOT) == null);
	}

	private MineralSizerRecipe getRecipe (int x){
		return MachineRecipes.sizerRecipes.get(x);
	}

	public int getSlider(){
		return slider;
	}

	public boolean isCorrectRange(){
		if(getRecipe() != null){
			if(getRecipe().getComminution()){
				if(getSlider() > 0 && getSlider() <= getRecipe().getOutput().size()){
					return true;
				}
			}else{
				return true;
			}
		}
		return false;
	}

	public MineralSizerRecipe getRecipe(){
		for(int x = 0; x < MachineRecipes.sizerRecipes.size(); x++){
			if(getRecipe(x).getInput() != null && ItemStack.areItemsEqual(getRecipe(x).getInput(), input.getStackInSlot(INPUT_SLOT))){
				return getRecipe(x);
			}
		}
		return null;
	}

	public boolean hasComminution(){
		return getRecipe() != null && getRecipe().getComminution();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.slider = compound.getInteger("Slider");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Slider", this.slider);
		return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(lavaTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		acceptEnergy();
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		lavaHandler();
		if(!worldObj.isRemote){
			if(canProcess(input.getStackInSlot(INPUT_SLOT))){
				cookTime++;
				powerCount--;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess(ItemStack stack) {
		return isActive()
			&& allOutputsEmpty()
			&& hasRecipe(input.getStackInSlot(INPUT_SLOT))
			&& handleConsumable()
			&& isCorrectRange()
			&& getPower() >= getMaxCookTime();
	}

	private void process() {
		if(getRecipe()!= null){
			int mix = getRecipe().getOutput().size();
			if(!getRecipe().getComminution()){
				//calculate primary output
				if(mix > 1){
					output.setStackInSlot(OUTPUT_SLOT, ProbabilityStack.calculateProbability(getRecipe().getProbabilityStack()).copy());
					output.getStackInSlot(OUTPUT_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral) + 1;
				}else{
					output.setStackInSlot(OUTPUT_SLOT, getRecipe().getOutput().get(0).copy());
				}
				//calculate secondary output
				if(rand.nextInt(100) < 25 && mix > 4){
					output.setStackInSlot(GOOD_SLOT, ProbabilityStack.calculateProbability(getRecipe().getProbabilityStack()).copy());
					output.getStackInSlot(GOOD_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral / 2) + 1;
				}
				//calculate waste output
				if(rand.nextInt(100) < 5 && mix > 1){
					output.setStackInSlot(WASTE_SLOT, ProbabilityStack.calculateProbability(getRecipe().getProbabilityStack()).copy());
					output.getStackInSlot(WASTE_SLOT).stackSize = 1;
				}
			}else{
				//calculate primary output
				output.setStackInSlot(OUTPUT_SLOT, getRecipe().getOutput().get(getSlider() - 1).copy());
				output.getStackInSlot(OUTPUT_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral) + 1;
				//calculate secondary output
				if(rand.nextInt(100) < 25 && mix > 1){
					int extra = rand.nextInt(mix);
					output.setStackInSlot(GOOD_SLOT, getRecipe().getOutput().get(extra).copy());
					output.getStackInSlot(GOOD_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral / 2) + 1;
				}
				//calculate waste output
				if(rand.nextInt(100) < 5 && mix > 1){
					int extra = rand.nextInt(mix);
					output.setStackInSlot(WASTE_SLOT, getRecipe().getOutput().get(extra).copy());
					output.getStackInSlot(WASTE_SLOT).stackSize = 1;
				}
			}
		}
		handleConsumableDamage();
		input.decrementSlot(INPUT_SLOT);
	}

	private void handleConsumableDamage() {
		if(getRecipe() != null){
			if(getRecipe().getComminution()){
				input.damageSlot(CONSUMABLE_SLOT, getSlider());
			}else{
				input.damageSlot(CONSUMABLE_SLOT);
			}
		}
	}

	private boolean handleConsumable() {
		return getRecipe() != null && getRecipe().getOutput().size() > 1 && getRecipe().getComminution() ? CoreUtils.hasConsumable(ToolUtils.gear, input.getStackInSlot(CONSUMABLE_SLOT), getSlider()) : CoreUtils.hasConsumable(ToolUtils.gear, input.getStackInSlot(CONSUMABLE_SLOT));
	}

	public boolean canConsumableHandleComminution(){
		return !hasComminution() || (couldProcess() && hasComminution() && CoreUtils.hasConsumable(ToolUtils.gear, input.getStackInSlot(CONSUMABLE_SLOT), getSlider()));
	}
	
	public boolean couldProcess() {
		return input.getStackInSlot(INPUT_SLOT) != null && input.getStackInSlot(CONSUMABLE_SLOT) != null;
	}

}