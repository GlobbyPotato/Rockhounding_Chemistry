package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumElement;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ChemicalExtractorRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_chemistry.utils.FuelUtils;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityChemicalExtractor extends TileEntityMachineEnergy implements IFluidHandlingTile{

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
				return fluid.getFluid() == ModFluids.NITRIC_ACID;
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
				return fluid.getFluid() == ModFluids.PHOSPHORIC_ACID;
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
				return fluid.getFluid() == ModFluids.SODIUM_CYANIDE;
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
				if(slot == FUEL_SLOT && (FuelUtils.isItemFuel(insertingStack) || ToolUtils.hasinductor(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == REDSTONE_SLOT && hasRedstone(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && ToolUtils.hasConsumable(ToolUtils.testTube, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == NITR_SLOT && handleBucket(insertingStack, ModFluids.NITRIC_ACID, ModFluids.nitricAcidBeaker)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == PHOS_SLOT && handleBucket(insertingStack, ModFluids.PHOSPHORIC_ACID, ModFluids.phosphoricAcidBeaker)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CYAN_SLOT && handleBucket(insertingStack, ModFluids.SODIUM_CYANIDE, ModFluids.sodiumCyanideBeaker)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CYLINDER_SLOT && ToolUtils.hasConsumable(ToolUtils.cylinder, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN_OUT);
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

	public int getExtractingFactor(){
		return ModConfig.factorExtractor;
	}

	public int getCookTimeMax(){
		return ModConfig.speedExtractor;
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRecipe(ItemStack stack){
		return ModRecipes.extractorRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}

	private boolean handleBucket(ItemStack insertingStack, Fluid fluid, Item beaker){
		return ToolUtils.isBucketType(insertingStack) 
			&& FluidUtil.getFluidContained(insertingStack).containsFluid(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
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
	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) return true;
		else return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return (T) getCombinedTank();
		return super.getCapability(capability, facing);
	}

	public FluidHandlerConcatenate getCombinedTank(){
		return new FluidHandlerConcatenate(nitrTank,phosTank,cyanTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
		redstoneHandler(REDSTONE_SLOT, this.getCookTimeMax());

		if(!worldObj.isRemote){
			emptyContainer(NITR_SLOT, nitrTank);
			emptyContainer(PHOS_SLOT, phosTank);
			emptyContainer(CYAN_SLOT, cyanTank);

			if(canExtractElements()){
				execute();
				if(ToolUtils.hasConsumable(ToolUtils.cylinder, input.getStackInSlot(CYLINDER_SLOT))){
					transferDust();
				}
				this.markDirtyClient();
			}
		}
	}

	private void execute() {
		cookTime++;
		powerCount--;
		redstoneCount--;
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
							for(int ix = 0; ix < ModRecipes.inhibitedElements.size(); ix++){
								if(currentRecipe.getElements().get(x).toLowerCase().matches(ModRecipes.inhibitedElements.get(ix).toLowerCase())){
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
		for(ChemicalExtractorRecipe recipe: ModRecipes.extractorRecipes){
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
		nitrTank.getFluid().amount-= consumedNitr;
		phosTank.getFluid().amount-= consumedPhos;
		cyanTank.getFluid().amount-= consumedCyan;
		currentRecipe = null;
	}

	private boolean canExtractElements() {
		return hasRecipe(input.getStackInSlot(INPUT_SLOT))
			&& isFullRecipe()
			&& ToolUtils.hasConsumable(ToolUtils.testTube, input.getStackInSlot(CONSUMABLE_SLOT))
			&& ToolUtils.hasConsumable(ToolUtils.cylinder, input.getStackInSlot(CYLINDER_SLOT))
			&& getPower() >= getCookTimeMax()
			&& getRedstone() >= getCookTimeMax()
			&& nitrTank.getFluidAmount() >= consumedNitr
			&& phosTank.getFluidAmount() >= consumedPhos
			&& cyanTank.getFluidAmount() >= consumedCyan;
	}

	private void transferDust() {
		for(int i=0;i<output.getSlots();i++){
			if(ToolUtils.hasConsumable(ToolUtils.cylinder, input.getStackInSlot(CYLINDER_SLOT))){
				if(elementList[i] >= getExtractingFactor()){
					elementList[i]-= getExtractingFactor();
					output.setOrIncrement(i, new ItemStack(ModItems.chemicalDusts,1,i));
					input.damageSlot(CYLINDER_SLOT);
				}
			}
		}
	}

}