package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.PipelineDuct;
import com.globbypotato.rockhounding_chemistry.machines.PipelineValve;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TileEntityPipelinePump extends TileEntityMachineBase  implements ITickable{
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
	public boolean canExtract(IFluidHandler handler, TileEntityPipelineValve valve, int side) {
		FluidStack extractedFluid = handler.drain(getFlow(), false);
		FluidStack filterFluid = valve.sideFilter[side];
		return (extractedFluid != null && extractedFluid.amount > 0) && (filterFluid == null || (filterFluid != null && extractedFluid.isFluidEqual(filterFluid)));
	}

	public int getFlow(){
		return hasUpgrade() ? 100000 : 1000;
	}

	public boolean isActive(){
		return activation;
	}

	public boolean hasUpgrade(){
		return upgrade;
	}

	public int getDelay() {
		return this.delay;
	}

	public boolean isAnyPipe(TileEntity checkTile) {
		return checkTile instanceof TileEntityPipelineValve || checkTile instanceof TileEntityPipelinePump;
	}

	public boolean hasFluidCapability(TileEntity checkTile, EnumFacing facing) {
		return checkTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!worldObj.isRemote){
			if(isActive()){
				if(countDelay >= getDelay()){
					//set starting pos
					if(this.ducts.size() == 0){
						this.ducts.add(this.pos);
					}
					//search connections
					if(numducts < ducts.size()){
						addConnection(ducts.get(numducts));
						numducts++;
					}else{
						//serve handlers
						numducts = 0;
						handleValves();
						ducts.clear();
						fluidUsers.clear();
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
			IBlockState checkState = this.worldObj.getBlockState(checkPos);
			if(checkState != null && checkState.getBlock() instanceof PipelineDuct){
				if(!this.ducts.contains(checkPos)){
					this.ducts.add(checkPos);
				}
		    }

			if(checkState != null && checkState.getBlock() instanceof PipelineValve){
				TileEntityPipelineValve valveTile = (TileEntityPipelineValve)worldObj.getTileEntity(checkPos);
				if(valveTile != null){
					if(valveTile.sideStatus[facing.getOpposite().ordinal()]){
						if(!this.fluidUsers.contains(checkPos)){
							this.fluidUsers.add(checkPos);
						}
					}
				}
			}
		}
	}

	private void handleValves() {
		if(!this.fluidUsers.isEmpty() && this.fluidUsers.size() > 0){
			for(EnumFacing facing : EnumFacing.values()){
				BlockPos checkPos = this.pos.offset(facing);
				TileEntity checkTile = this.worldObj.getTileEntity(checkPos);
				if(checkTile != null){
					if(hasFluidCapability(checkTile, facing.getOpposite()) && !isAnyPipe(checkTile)){
						IFluidHandler extractHandler = checkTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
						if(extractHandler != null){
							for(BlockPos thisPos : this.fluidUsers){
								if(thisPos != null){
									TileEntity valveTile = this.worldObj.getTileEntity(thisPos);
									if(valveTile != null && valveTile instanceof TileEntityPipelineValve){
										TileEntityPipelineValve valve = (TileEntityPipelineValve)valveTile;
										if(valve.isActive()){
											int numHandlers = 0;
											for(EnumFacing valvefacing : EnumFacing.values()){
												if(valve.sideStatus[valvefacing.ordinal()]){
													TileEntity userTile = this.worldObj.getTileEntity(thisPos.offset(valvefacing));
													if(userTile != null && hasFluidCapability(userTile, valvefacing.getOpposite()) && !isAnyPipe(userTile)){
														IFluidHandler receiveHandler = userTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, valvefacing.getOpposite());
														if(receiveHandler != null){
															if(hasFluidCapability(userTile, facing.getOpposite()) && !isAnyPipe(userTile)){
																if(canExtract(extractHandler, valve, valvefacing.ordinal())){
																	if(valve.hasRoundRobin()){
																		numHandlers++;
																	}else{
																		extractHandler.drain(receiveHandler.fill(extractHandler.drain(getFlow(), false), true), true);
																	}
																}
															}
														}
													}
												}
											}

											if(valve.hasRoundRobin()){
												for(EnumFacing valvefacing : EnumFacing.values()){
													if(valve.sideStatus[valvefacing.ordinal()]){
														TileEntity userTile = this.worldObj.getTileEntity(thisPos.offset(valvefacing));
														if(userTile != null && hasFluidCapability(userTile, valvefacing.getOpposite())){
															IFluidHandler receiveHandler = userTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, valvefacing.getOpposite());
															if(receiveHandler != null){
																if(hasFluidCapability(userTile, facing.getOpposite())){
																	if(canExtract(extractHandler, valve, valvefacing.ordinal())){
																		int extr = extractHandler.drain(getFlow(), false).amount;
																		extractHandler.drain(receiveHandler.fill(extractHandler.drain(getFlow() / numHandlers, false), true), true);
																	}
																}
															}
														}
													}
												}
											}

										}
									}else{
										fluidUsers.remove(thisPos);
										break;
									}
								}
							}
						}
					}
				}
			}
		}
	}
}