package com.globbypotato.rockhounding_chemistry.machines.tile.pipelines;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_core.gas.CapabilityGasHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.IGasHandlingTile;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityFueledVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityPoweredVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTankVessel;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityVessel;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TEGaslinePump extends TileEntityBase  implements ITickable{
	public ArrayList<BlockPos> fluidSenders = new ArrayList<BlockPos>();
	public ArrayList<BlockPos> fluidUsers = new ArrayList<BlockPos>();
	public ArrayList<BlockPos> ducts = new ArrayList<BlockPos>();
	public int numducts = 0;
	public boolean activation;
	public boolean upgrade;
	public int delay;
	private int countDelay = 0;


	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
        this.activation = compound.getBoolean("Activation");
        this.upgrade = compound.getBoolean("Upgrade");
        this.delay = compound.getInteger("Delay");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
        compound.setBoolean("Activation", isActive());
        compound.setBoolean("Upgrade", hasUpgrade());
        compound.setInteger("Delay", getDelay());
		return compound;
	}



	// ----------------------- CUSTOM -----------------------
	public boolean canExtract(FluidStack extractedFluid, FluidStack receivingFluid) {
		return (extractedFluid != null && extractedFluid.getFluid().isGaseous() && extractedFluid.amount > 0) 
			&& (receivingFluid == null || (extractedFluid.isFluidEqual(receivingFluid) && receivingFluid.getFluid().isGaseous()));
	}

	public int getFlow(){
		return hasUpgrade() ? 10000 : 1000;
	}

	public boolean isActive(){
		return this.activation;
	}

	public boolean hasUpgrade(){
		return this.upgrade;
	}

	public int getDelay() {
		return this.delay;
	}

	public boolean isAnyPipe(TileEntity checkTile) {
		return checkTile instanceof TEGaslinePump;
	}

	public boolean hasGasCapability(TileEntity checkTile, EnumFacing facing) {
		return checkTile.hasCapability(CapabilityGasHandler.GAS_HANDLER_CAPABILITY, facing);
	}

	public static String getName(){
		return "gasline_pump";
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			if(isActive()){
				if(countDelay >= getDelay()){
					//set starting pos
					if(this.ducts.size() == 0){
						this.ducts.add(this.pos);
					}
					//search connections
					if(this.numducts < this.ducts.size()){
						addConnection(this.ducts.get(this.numducts));
						this.numducts++;
					}else{
						//serve handlers
						handleValves();
						this.numducts = 0;
						this.ducts.clear();
						this.fluidUsers.clear();
						this.fluidSenders.clear();
					}
					countDelay = 0;
				}else{
					countDelay++;
				}
			}
			this.markDirtyClient();
		}
	}

	private void addConnection(BlockPos duct) {
		for(EnumFacing facing : EnumFacing.values()){
			BlockPos checkPos = duct.offset(facing);
			IBlockState checkState = this.world.getBlockState(checkPos);
			if(checkState != null && checkState.getBlock() == ModBlocks.GASLINE_DUCT){
				if(!this.ducts.contains(checkPos)){
					this.ducts.add(checkPos);
				}
		    }

			TileEntity tile = this.world.getTileEntity(checkPos);
			if(tile != null){ 
				if(tile instanceof IGasHandlingTile){
					TileEntityInv valveTile = (TileEntityInv)tile;
					if(valveTile.getFacing() == facing){
						if(!this.fluidUsers.contains(checkPos)){
							this.fluidUsers.add(checkPos);
						}
					}
					if(this.numducts == 0){
						if(valveTile.getFacing() == facing.getOpposite()){
							if(!this.fluidSenders.contains(checkPos)){
								this.fluidSenders.add(checkPos);
							}
						}
					}
				}
			}
		}
	}

	private void handleValves() {
		if(this.fluidSenders.size() > 0 && this.fluidUsers.size() > 0){
			for(BlockPos sender : fluidSenders){
				TileEntity checkTile = this.world.getTileEntity(sender);
				if(checkTile != null){
					TileEntityInv send = (TileEntityInv)checkTile;
					IFluidHandler extractHandler = checkTile.getCapability(CapabilityGasHandler.GAS_HANDLER_CAPABILITY, send.getFacing());
					if(extractHandler != null){
						for(BlockPos receiver : this.fluidUsers){
							TileEntity valveTile = this.world.getTileEntity(receiver);
							if(valveTile != null){ 
								if(valveTile instanceof TileEntityVessel){
									TileEntityVessel valve = (TileEntityVessel)valveTile;
									IFluidHandler receiveHandler = valve.getCapability(CapabilityGasHandler.GAS_HANDLER_CAPABILITY, valve.getFacing().getOpposite());
									handleTransfer(extractHandler, receiveHandler);
								}else if(valveTile instanceof TileEntityFueledVessel){
									TileEntityFueledVessel valve = (TileEntityFueledVessel)valveTile;
									IFluidHandler receiveHandler = valve.getCapability(CapabilityGasHandler.GAS_HANDLER_CAPABILITY, valve.getFacing().getOpposite());
									handleTransfer(extractHandler, receiveHandler);
								}else if(valveTile instanceof TileEntityPoweredVessel){
									TileEntityPoweredVessel valve = (TileEntityPoweredVessel)valveTile;
									IFluidHandler receiveHandler = valve.getCapability(CapabilityGasHandler.GAS_HANDLER_CAPABILITY, valve.getFacing().getOpposite());
									handleTransfer(extractHandler, receiveHandler);
								}else if(valveTile instanceof TileEntityTankVessel){
									TileEntityTankVessel valve = (TileEntityTankVessel)valveTile;
									IFluidHandler receiveHandler = valve.getCapability(CapabilityGasHandler.GAS_HANDLER_CAPABILITY, valve.getFacing().getOpposite());
									handleTransfer(extractHandler, receiveHandler);
								}
							}else{
								this.fluidUsers.remove(receiver);
								break;
							}
						}
					}else{
						this.fluidSenders.remove(sender);
						break;
					}
				}
			}
		}
	}

	private void handleTransfer(IFluidHandler extractHandler, IFluidHandler receiveHandler) {
		if(receiveHandler != null){
			FluidStack extractedFluid = extractHandler.drain(getFlow(), false);
			FluidStack receivingFluid = receiveHandler.drain(getFlow(), false);
			if(canExtract(extractedFluid, receivingFluid)){
				extractHandler.drain(receiveHandler.fill(extractHandler.drain(getFlow(), false), true), true);
			}
		}
	}
}