package com.globbypotato.rockhounding_chemistry.machines.tile.devices;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.utils.CoreBasics;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEWaterPump extends TileEntityTank{
	public FluidTank inputTank;
	public int tier;
	public boolean compressor;

	public TEWaterPump() {
		super(0, 0, 0, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return fluid.isFluidEqual(CoreBasics.waterStack(1000));
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		};
		this.inputTank.setTileEntity(this);
		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.tier = compound.getInteger("Tier");
		this.compressor = compound.getBoolean("Compressor");
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setInteger("Tier", getTier());
		compound.setBoolean("Compressor", getCompressor());

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "water_pump";
	}



	//----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() {
		return 10000;
	}

	public FluidStack getTankFluid(){
		return this.inputTank.getFluid();
	}

	public int getTankAmount() {
		return this.inputTank.getFluidAmount();
	}

	public boolean hasTankFluid(){
		return this.inputTank.getFluid() != null;
	}



	// ----------------------- CUSTOM -----------------------
	public boolean getCompressor() {
		return this.compressor;
	}

	public int getTier() {
		return this.tier;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			if(isPowered()){
				if(isValidWaterTile()){
					if(world.rand.nextInt(compressorFactor()) == 0){
						if(this.input.canSetOrAddFluid(inputTank, getTankFluid(), water(), tierFactor())){
							this.input.setOrFillFluid(inputTank, water(), tierFactor());
							if(ModConfig.consumeWater && this.world.rand.nextInt(ModConfig.consumeWaterChance) == 0){
								this.world.setBlockToAir(pos.down());
							}
						}
					}
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean isValidWaterTile() {
		IBlockState waterstate = world.getBlockState(pos.down());
		Block waterblock = waterstate.getBlock();
		return waterblock == Blocks.WATER && waterblock.getMetaFromState(waterstate)  == 0;
	}

	private FluidStack water() {
		return CoreBasics.waterStack(1000);
	}

	public int tierFactor() {
		if(getTier() == 0 && getCompressor()){
			return 200;
		}else if(getTier() == 1 && getCompressor()){
			return 1000;
		}else if(getTier() == 1 && !getCompressor()){
			return 500;
		}else if(getTier() == 2 && getCompressor()){
			return 2000;
		}else if(getTier() == 2 && !getCompressor()){
			return 1000;
		}
		return 100;
	}

	private int compressorFactor() {
		return getCompressor() ? 1 : 16;
	}


}