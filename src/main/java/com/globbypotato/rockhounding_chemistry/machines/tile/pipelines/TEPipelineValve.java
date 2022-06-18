package com.globbypotato.rockhounding_chemistry.machines.tile.pipelines;

import com.globbypotato.rockhounding_chemistry.machines.PipelineDuct;
import com.globbypotato.rockhounding_chemistry.machines.PipelineValve;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

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

public class TEPipelineValve extends TileEntityInv{
	private ItemStackHandler template = new TemplateStackHandler(13);
	public boolean[] sideStatus = new boolean[]{true, true, true, true, true, true};
	public boolean[] tiltStatus = new boolean[]{false, false, false, false, false, false};
	public FluidStack[] sideFilter = new FluidStack[]{null, null, null, null, null, null};
	public boolean activation;
	public boolean robin;

	public TEPipelineValve() {
		super(0, 0, 0, 0);

	}



	//---------------- HANDLER ----------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "pipeline_valve";
	}



	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
        this.activation = compound.getBoolean("Activation");
        this.robin = compound.getBoolean("Robin");

        NBTTagCompound lockList = compound.getCompoundTag("Locks");
		for(int i = 0; i < lockList.getSize(); i++){
			this.sideStatus[i] = lockList.getBoolean("Lock" + i);
		}

        NBTTagCompound tiltList = compound.getCompoundTag("Tilts");
		for(int i = 0; i < tiltList.getSize(); i++){
			this.tiltStatus[i] = tiltList.getBoolean("Tilt" + i);
		}

        NBTTagList nbttaglist = compound.getTagList("Filters", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Side");
            if (j >= 0 && j < this.sideFilter.length){
            	this.sideFilter[j] = FluidStack.loadFluidStackFromNBT(nbttagcompound);
            }
        }
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
        compound.setBoolean("Activation", isActive());
        compound.setBoolean("Robin", hasRoundRobin());

        NBTTagCompound lockList = new NBTTagCompound();
		for(int i = 0; i < this.sideStatus.length; i++){
			lockList.setBoolean("Lock" + i, this.sideStatus[i]);
		}
		compound.setTag("Locks", lockList);

        NBTTagCompound tiltList = new NBTTagCompound();
		for(int i = 0; i < this.tiltStatus.length; i++){
			tiltList.setBoolean("Tilt" + i, this.tiltStatus[i]);
		}
		compound.setTag("Tilts", tiltList);

        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.sideFilter.length; ++i){
            if (this.sideFilter[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Side", (byte)i);
                this.sideFilter[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Filters", nbttaglist);

		return compound;
	}



	//---------------- CUSTOM ----------------
	@Override
	public boolean isActive(){
		return this.activation;
	}

	public boolean hasRoundRobin(){
		return this.robin;
	}



	//---------------- PROCESS ----------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			for(EnumFacing facing : EnumFacing.values()){
				BlockPos checkPos = this.pos.offset(facing);
				IBlockState checkState = this.world.getBlockState(checkPos);
				TileEntity checkTile = this.world.getTileEntity(checkPos);
				if(checkState != null){
					if(isInternalPump(checkState) || isInternalPipe(checkState) || (isAnyBlock(checkState) && !hasCapability(checkTile, facing))){
						if(!this.tiltStatus[facing.ordinal()]){
							this.tiltStatus[facing.ordinal()] = true;
						}
					}else{
						if(this.tiltStatus[facing.ordinal()]){
							this.tiltStatus[facing.ordinal()] = false;
						}
					}
				}
			}
			this.markDirtyClient();
		}
	}

	private static boolean isAnyBlock(IBlockState checkState) {
		return checkState.getBlock() != Blocks.AIR;
	}

	private static boolean hasCapability(TileEntity checkTile, EnumFacing facing) {
		return checkTile != null && checkTile.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
	}

	private static boolean isInternalPipe(IBlockState checkState) {
		return checkState.getBlock() instanceof PipelineDuct;
	}

	private static boolean isInternalPump(IBlockState checkState) {
		return checkState.getBlock() instanceof PipelineValve;
	}

}