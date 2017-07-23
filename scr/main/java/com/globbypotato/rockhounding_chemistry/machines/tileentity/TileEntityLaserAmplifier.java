package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.machines.LaserAmplifier;
import com.globbypotato.rockhounding_chemistry.machines.LaserStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiLaserAmplifier;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserAmplifier extends TileEntityMachineEnergy {

    private int updating;
    
	public TileEntityLaserAmplifier() {
		super(0, 0, 0);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiLaserAmplifier.HEIGHT;
	}

	@Override
	public boolean hasRF() {
		return true;
	}



	// ----------------------- CUSTOM -----------------------
	public int redstoneCost(){
		return 20;
	}

	public int getLevelMax(){
		return 5;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!worldObj.isRemote){
			
			if(isPowered()){
				this.redstoneCount -= redstoneCost();
			}

			if (getRedstone() != updating) {
				updating = getRedstone();
				this.markDirtyClient();
			}
		}
	}

	public int getStage() {
		IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing ampfacing = state.getValue(ampFacing());
	    BlockPos cascadepos = pos.offset(ampfacing.getOpposite());
	    IBlockState cascadestate = worldObj.getBlockState(cascadepos);
	    if(cascadestate != null && cascadestate.getBlock() instanceof LaserAmplifier){
		    EnumFacing cascadefacing = cascadestate.getValue(cascadeFacing());
		    if(cascadefacing == ampfacing){
		    	TileEntityLaserAmplifier amplifier = (TileEntityLaserAmplifier)worldObj.getTileEntity(cascadepos);
		    	if(amplifier.isPowered() && this.getRedstone() >= redstoneCost()){ 
		    		if(amplifier.getStage() < getLevelMax()){
		    			return amplifier.getStage() + 1;
		    		}else{
		    			return amplifier.getStage();
		    		}
		    	}
		    }
	    }
	    return 0;
	}

	public boolean isPowered() {
		IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing ampfacing = state.getValue(ampFacing());
	    BlockPos cascadepos = pos.offset(ampfacing.getOpposite());
	    IBlockState cascadestate = worldObj.getBlockState(cascadepos);
	    if(cascadestate != null && cascadestate.getBlock() instanceof LaserAmplifier){
		    EnumFacing cascadefacing = cascadestate.getValue(cascadeFacing());
		    if(cascadefacing == ampfacing){
		    	TileEntityLaserAmplifier amplifier = (TileEntityLaserAmplifier)worldObj.getTileEntity(cascadepos);
		    	if(amplifier.isPowered() && this.getRedstone() >= redstoneCost()){
		    		return true;
		    	}
		    }
	    }else if(cascadestate != null && cascadestate.getBlock() instanceof LaserStabilizer){
		    EnumFacing cascadefacing = cascadestate.getValue(stabilizerFacing());
		    if(cascadefacing == ampfacing){
		    	TileEntityLaserStabilizer stabilizer = (TileEntityLaserStabilizer)worldObj.getTileEntity(cascadepos);
		    	if(stabilizer.isPowered() && this.getRedstone() >= redstoneCost()){
		    		return true;
		    	}
		    }
	    }
		return false;
	}

	public PropertyDirection ampFacing(){
		return LaserAmplifier.FACING;
	}

	public PropertyDirection cascadeFacing(){
		return LaserAmplifier.FACING;
	}

	public PropertyDirection stabilizerFacing(){
		return LaserStabilizer.FACING;
	}

}