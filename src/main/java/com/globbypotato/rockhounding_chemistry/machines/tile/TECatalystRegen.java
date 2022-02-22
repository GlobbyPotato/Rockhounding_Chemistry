package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TECatalystRegen extends TileEntityTank {
	public static int outputSlots = 1;
	public static int templateSlots = 1;

	public FluidTank inputTank;
	public int catSlot;
	public int systSlot;

	public TECatalystRegen() {
		super(0, outputSlots, templateSlots, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return fluid.isFluidEqual(CoreBasics.waterStack(1000));
			}

			@Override
			public boolean canDrain() {
				return false;
			}
		};
		this.inputTank.setTileEntity(this);

	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		
		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

        return compound;
	}


	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack outputSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "catalyst_regen";
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing();
	}



	//----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() {
		return 5000;
	}

	public boolean hasInputFluid(){
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getInputFluid(){
		return hasInputFluid() ? this.inputTank.getFluid() : null;
	}

	public int getInputAmount() {
		return hasInputFluid() ? this.inputTank.getFluidAmount() : 0;
	}



	//----------------------- CUSTOM -----------------------
	public int consumedGas(){
		return 200;
	}

	public int consumedWater(){
		return 1000;
	}



	//----------------------- RECIPE -----------------------



	//----------------------- STRUCTURE -----------------------
	//input vessel
		public TileVessel getInTank(){
			TileVessel vessel = TileStructure.getHolder(this.world, this.pos, getFacing(), 1, 180);
			return vessel != null ? vessel : null;
		}

		public boolean hasInTank(){
			return getInTank() != null;
		}

		private boolean hasFluegas() {
			return hasInTank() && this.input.canDrainFluid(getInTank().inputTank.getFluid(), fluegas(), consumedGas());
		}

		private static FluidStack fluegas() {
			return BaseRecipes.getFluid(EnumFluid.FLUE_GAS, 1000);
		}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			if(this.isActive()){
				if(this.world.rand.nextInt(6) == 0){
					TileEntity tile = this.world.getTileEntity(this.pos.up());
					if(tile != null){
						if(tile instanceof TEExtractorStabilizer){
							TEExtractorStabilizer served = (TEExtractorStabilizer)tile;
							for(int x = 0; x < TEExtractorStabilizer.SLOT_INPUTS.length; x++){
								ItemStack thisCat = served.catSlot(x);
								regenCat(thisCat);
							}
						}else if(tile instanceof TEReformerController){
							TileEntity reactor = this.world.getTileEntity(this.pos.up(2));
							if(reactor != null){
								TEReformerReactor served = (TEReformerReactor)reactor;
								for(int x = 0; x < TEReformerReactor.totCatalysts; x++){
									ItemStack thisCat = served.inputSlot(x);
									regenCat(thisCat);
								}
							}
						}else if(tile instanceof TELabOvenChamber){
							TileEntity reactor = this.world.getTileEntity(this.pos.up(2));
							if(reactor != null){
								TELabOvenController served = (TELabOvenController)reactor;
								if(canRegen()){
									ItemStack thisCat = served.catalystSlot();
									regenCat(thisCat);
								}
							}
						}else if(tile instanceof TETubularBedTank){
							BlockPos tubularLowPos = this.pos.up(2).offset(getFacing(), 1);
							TileEntity reactor = this.world.getTileEntity(tubularLowPos);
							if(reactor != null){
								TETubularBedLow served = (TETubularBedLow)reactor;
								for(int x = 0; x < TETubularBedLow.SLOT_INPUTS.length; x++){
									ItemStack thisCat = served.catalystSlot(x);
									regenCat(thisCat);
								}
							}
						}
					}
				}
			}
			this.markDirtyClient();
		}
	}

	private void regenCat(ItemStack thisCat) {
		if(canRegen()){
			if(!thisCat.isEmpty()){
				if(shouldRegen(thisCat)){
					thisCat.setItemDamage(0);
					consumeStats();
				}
			}
		}
	}

	private boolean canRegen() {
		return getInputAmount() >= consumedWater() && hasFluegas();
	}

	private boolean shouldRegen(ItemStack thisCat) {
		return (thisCat.getMaxDamage() - thisCat.getItemDamage()) < (thisCat.getMaxDamage() / 4);
	}

	private void consumeStats() {
		this.input.drainOrCleanFluid(this.inputTank, consumedWater(), true);
		((MachineStackHandler)getInTank().getInput()).drainOrCleanFluid(getInTank().inputTank, consumedGas(), true);

		if(this.world.rand.nextInt(3) == 0){
			ItemStack coke_compound = new ItemStack(ModItems.CHEMICAL_ITEMS, 1, EnumChemicals.COKE_COMPOUND.ordinal());
			if(this.output.canSetOrStack(this.outputSlot(), coke_compound)){
				this.output.setOrIncrement(OUTPUT_SLOT, coke_compound);
			}
		}
	}

}