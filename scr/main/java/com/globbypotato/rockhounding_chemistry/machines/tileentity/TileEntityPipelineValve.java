package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.machines.PipelineDuct;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPipelineValve;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityPipelineValve extends TileEntityMachineInv{
	private ItemStackHandler template = new TemplateStackHandler(12);
	public boolean[] sideStatus = new boolean[]{true, true, true, true, true, true};
	public boolean[] tiltStatus = new boolean[]{false, false, false, false, false, false};
	public FluidStack[] sideFilter = new FluidStack[]{null, null, null, null, null, null};
	public boolean activation;

	public TileEntityPipelineValve() {
		super(0, 0);

	}



	//---------------- HANDLER ----------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiPipelineValve.HEIGHT;
	}



	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
        this.activation = compound.getBoolean("Activation");

        NBTTagCompound lockList = compound.getCompoundTag("Locks");
		for(int i = 0; i < lockList.getSize(); i++){
			sideStatus[i] = lockList.getBoolean("Lock" + i);
		}

        NBTTagCompound tilrList = compound.getCompoundTag("Tilts");
		for(int i = 0; i < tilrList.getSize(); i++){
			tiltStatus[i] = tilrList.getBoolean("Tilt" + i);
		}

        NBTTagList nbttaglist = compound.getTagList("Filters", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Side");
            if (j >= 0 && j < sideFilter.length){
            	sideFilter[j] = FluidStack.loadFluidStackFromNBT(nbttagcompound);
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
        compound.setBoolean("Activation", isActive());

        NBTTagCompound lockList = new NBTTagCompound();
		for(int i = 0; i < sideStatus.length; i++){
			lockList.setBoolean("Lock" + i, sideStatus[i]);
		}
		compound.setTag("Locks", lockList);

        NBTTagCompound tiltList = new NBTTagCompound();
		for(int i = 0; i < tiltStatus.length; i++){
			tiltList.setBoolean("Tilt" + i, tiltStatus[i]);
		}
		compound.setTag("Tilts", tiltList);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < sideFilter.length; ++i){
            if (sideFilter[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Side", (byte)i);
                sideFilter[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Filters", nbttaglist);

		return compound;
	}



	//---------------- CUSTOM ----------------
	public boolean isActive(){
		return activation;
	}



	//---------------- PROCESS ----------------
	@Override
	public void update() {
		if(!worldObj.isRemote){
			for(EnumFacing facing : EnumFacing.values()){
				BlockPos checkPos = pos.offset(facing);
				IBlockState checkState = worldObj.getBlockState(checkPos);
				TileEntity checkTile = worldObj.getTileEntity(checkPos);
				if(checkState != null){
					if(isInternalPipe(checkState) || (isAnyBlock(checkState) && !hasCapability(checkTile, facing))){
						tiltStatus[facing.ordinal()] = true;
					}else{
						tiltStatus[facing.ordinal()] = false;
					}
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean isAnyBlock(IBlockState checkState) {
		return checkState.getBlock() != Blocks.AIR;
	}

	private boolean hasCapability(TileEntity checkTile, EnumFacing facing) {
		return checkTile != null && checkTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
	}

	private boolean isInternalPipe(IBlockState checkState) {
		return checkState.getBlock() instanceof PipelineDuct;
	}

}