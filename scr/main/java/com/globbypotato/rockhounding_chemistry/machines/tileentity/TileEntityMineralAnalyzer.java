package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.ModRecipes;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;
import com.globbypotato.rockhounding_chemistry.utils.FuelUtils;
import com.globbypotato.rockhounding_chemistry.utils.ProbabilityStack;
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

public class TileEntityMineralAnalyzer extends TileEntityMachineEnergy implements IFluidHandlingTile{

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
		        return fluid.getFluid() == ModFluids.SULFURIC_ACID;
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
		        return fluid.getFluid() == ModFluids.HYDROCHLORIC_ACID;
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
		        return fluid.getFluid() == ModFluids.HYDROFLUORIC_ACID;
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
				if(slot == FUEL_SLOT && (FuelUtils.isItemFuel(insertingStack) || ToolUtils.hasinductor(insertingStack))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT && ToolUtils.hasConsumable(ToolUtils.testTube, insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SULFUR_SLOT && handleBucket(insertingStack, ModFluids.SULFURIC_ACID, ModFluids.sulfuricAcidBeaker) ) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CHLOR_SLOT && handleBucket(insertingStack, ModFluids.HYDROCHLORIC_ACID, ModFluids.hydrochloricAcidBeaker) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FLUO_SLOT && handleBucket(insertingStack, ModFluids.HYDROFLUORIC_ACID, ModFluids.hydrofluoricAcidBeaker) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
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
		return ModRecipes.analyzerRecipes.stream().anyMatch(
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
	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand,
			ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
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
		return new FluidHandlerConcatenate(sulfTank,chloTank,fluoTank);
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		fuelHandler(input.getStackInSlot(FUEL_SLOT));
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
			&& ToolUtils.hasConsumable(ToolUtils.testTube, input.getStackInSlot(CONSUMABLE_SLOT))
			&& getPower() >= getCookTimeMax()
			&& this.sulfTank.getFluidAmount() >= consumedSulf
			&& this.chloTank.getFluidAmount() >= consumedChlo
			&& this.fluoTank.getFluidAmount() >= consumedFluo;
	}

	private void analyze(){
		for(int x = 0; x < ModRecipes.analyzerRecipes.size(); x++){
			if(ModRecipes.analyzerRecipes.get(x).getInput() != null && ItemStack.areItemsEqual(ModRecipes.analyzerRecipes.get(x).getInput(), input.getStackInSlot(INPUT_SLOT))){
				int mix = ModRecipes.analyzerRecipes.get(x).getOutput().size();
				if(mix > 1){
					output.setStackInSlot(OUTPUT_SLOT, ProbabilityStack.calculateProbability(ModRecipes.analyzerRecipes.get(x).getProbabilityStack()).copy());
					output.getStackInSlot(OUTPUT_SLOT).stackSize = rand.nextInt(ModConfig.maxMineral) + 1;
				}else{
					output.setStackInSlot(OUTPUT_SLOT, ModRecipes.analyzerRecipes.get(x).getOutput().get(0).copy());
				}
			}
		}
		input.damageSlot(CONSUMABLE_SLOT);
		input.decrementSlot(INPUT_SLOT);
		this.sulfTank.getFluid().amount-= consumedSulf;
		this.chloTank.getFluid().amount-= consumedChlo;
		this.fluoTank.getFluid().amount-= consumedFluo;
	}

}