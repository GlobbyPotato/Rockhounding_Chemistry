package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.machines.PipelineDuct;
import com.globbypotato.rockhounding_chemistry.machines.PipelineValve;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityBase;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class TEPipelinePump extends TileEntityBase  implements ITickable{
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
	public boolean canExtract(IFluidHandler handler, TEPipelineValve valve, int side) {
		FluidStack extractedFluid = handler.drain(getFlow(), false);
		FluidStack filterFluid = valve.sideFilter[side];
		return (extractedFluid != null && extractedFluid.amount > 0) && (filterFluid == null || (filterFluid.getFluid() != null && extractedFluid.isFluidEqual(filterFluid)));
	}

	public int getFlow(){
		return hasUpgrade() ? 100000 : 1000;
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
		return checkTile instanceof TEPipelineValve || checkTile instanceof TEPipelinePump;
	}

	public boolean hasFluidCapability(TileEntity checkTile, EnumFacing facing) {
		return checkTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
	}

	public static String getName(){
		return "pipeline_pump";
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
						this.numducts = 0;
						handleValves();
						this.ducts.clear();
						this.fluidUsers.clear();
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
			if(checkState != null && checkState.getBlock() instanceof PipelineDuct){
				if(!this.ducts.contains(checkPos)){
					this.ducts.add(checkPos);
				}
		    }

			if(checkState != null && checkState.getBlock() instanceof PipelineValve){
				TEPipelineValve valveTile = (TEPipelineValve)this.world.getTileEntity(checkPos);
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
				TileEntity checkTile = this.world.getTileEntity(checkPos);
				if(checkTile != null){
					if(hasFluidCapability(checkTile, facing.getOpposite())){
						IFluidHandler extractHandler = checkTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
						if(extractHandler != null){
							for(BlockPos thisPos : this.fluidUsers){
								if(thisPos != null){
									TileEntity valveTile = this.world.getTileEntity(thisPos);
									if(valveTile != null && valveTile instanceof TEPipelineValve){
										TEPipelineValve valve = (TEPipelineValve)valveTile;
										if(valve.isActive()){
											int numHandlers = 0;
											for(EnumFacing valvefacing : EnumFacing.values()){
												if(valve.sideStatus[valvefacing.ordinal()]){
													TileEntity userTile = this.world.getTileEntity(thisPos.offset(valvefacing));
													if(userTile != null && hasFluidCapability(userTile, valvefacing.getOpposite())){
														IFluidHandler receiveHandler = userTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, valvefacing.getOpposite());
														if(receiveHandler != null){
															if(hasFluidCapability(userTile, facing.getOpposite())){
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
														TileEntity userTile = this.world.getTileEntity(thisPos.offset(valvefacing));
														if(userTile != null && hasFluidCapability(userTile, valvefacing.getOpposite())){
															IFluidHandler receiveHandler = userTile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, valvefacing.getOpposite());
															if(receiveHandler != null){
																if(hasFluidCapability(userTile, facing.getOpposite())){
																	if(canExtract(extractHandler, valve, valvefacing.ordinal())){
																		extractHandler.drain(receiveHandler.fill(extractHandler.drain(getFlow()/numHandlers, false), true), true);
																	}
																}
															}
														}
													}
												}
											}
										}
									}else{
										this.fluidUsers.remove(thisPos);
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