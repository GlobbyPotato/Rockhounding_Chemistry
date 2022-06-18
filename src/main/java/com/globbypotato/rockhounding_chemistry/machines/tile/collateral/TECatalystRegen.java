package com.globbypotato.rockhounding_chemistry.machines.tile.collateral;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumChemicals;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabOvenController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileStructure;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELabOvenChamber;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedLow;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TETubularBedTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TECatalystRegen extends TileEntityInv {
	public static int outputSlots = 1;
	public static int templateSlots = 1;

	public TECatalystRegen() {
		super(0, outputSlots, templateSlots, 0);

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



	//----------------------- CUSTOM -----------------------
	public int consumedGas(){
		return 200;
	}

	public ItemStack cokeStack() {
		return new ItemStack(ModItems.CHEMICAL_ITEMS, 1, EnumChemicals.COKE_COMPOUND.ordinal());
	}



	//----------------------- STRUCTURE -----------------------
//centrifuge
	public TECentrifuge getCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuge(){
		return getCentrifuge() != null;
	}

//input vessel
	public TileVessel getInTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(getFacing(), 2), getFacing().getOpposite());
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

//cistern
	public TEFluidCistern getFluidCistern(){
		BlockPos cisternPos = this.pos.offset(isFacingAt(270));
		TEFluidCistern cistern = TileStructure.getFluidCistern(this.world, cisternPos, isFacingAt(90));
		return cistern != null ? cistern : null;
	}

	public boolean hasCistern(){
		return getFluidCistern() != null;
	}

	public boolean hasWasher() {
		return hasCistern() && getFluidCistern().inputTank.getFluid().isFluidEqual(CoreBasics.waterStack(1000)) && getFluidCistern().inputTank.getFluid().amount >= 1000;
	}

	public FluidStack getReactant() {
		return hasWasher() ? getFluidCistern().inputTank.getFluid() : null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){

			if(this.isActive()){
				if(this.world.rand.nextInt(6) == 0){
					TileEntity tile = this.world.getTileEntity(this.pos.up(2));
					if(tile != null){
						if(tile instanceof TEExtractorStabilizer){
							TEExtractorStabilizer served = (TEExtractorStabilizer)tile;
							for(int x = 0; x < TEExtractorStabilizer.SLOT_INPUTS.length; x++){
								ItemStack thisCat = served.catSlot(x);
								regenCat(thisCat);
							}
						}else if(tile instanceof TEReformerController){
							TileEntity reactor = this.world.getTileEntity(this.pos.up(3));
							if(reactor != null){
								TEReformerReactor served = (TEReformerReactor)reactor;
								for(int x = 0; x < TEReformerReactor.totCatalysts; x++){
									ItemStack thisCat = served.inputSlot(x);
									regenCat(thisCat);
								}
							}
						}else if(tile instanceof TELabOvenChamber){
							TileEntity reactor = this.world.getTileEntity(this.pos.up(3));
							if(reactor != null){
								TELabOvenController served = (TELabOvenController)reactor;
								if(canRegen()){
									ItemStack thisCat = served.catalystSlot();
									regenCat(thisCat);
								}
							}
						}else if(tile instanceof TETubularBedTank){
							BlockPos tubularLowPos = this.pos.up(2).offset(getFacing(), 2);
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
		return hasWasher() 
			&& hasFluegas() 
			&& this.output.canSetOrStack(outputSlot(), cokeStack());
	}

	private boolean shouldRegen(ItemStack thisCat) {
		return (thisCat.getMaxDamage() - thisCat.getItemDamage()) < (thisCat.getMaxDamage() / 4);
	}

	private void consumeStats() {
		if(hasCistern()) {
			this.input.drainOrCleanFluid(getFluidCistern().inputTank, 1000, true);
		}

		if(hasInTank()) {
			((MachineStackHandler)getInTank().getInput()).drainOrCleanFluid(getInTank().inputTank, consumedGas(), true);
		}

		if(this.world.rand.nextInt(3) == 0){
			this.output.setOrIncrement(OUTPUT_SLOT, cokeStack());
		}
	}

}