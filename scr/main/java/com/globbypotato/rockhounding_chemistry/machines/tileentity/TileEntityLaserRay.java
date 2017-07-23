package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.List;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

public class TileEntityLaserRay extends TileEntityLaserStorage {
	public int stage;

    //----------------------- CUSTOM -----------------------
	public int getStage(){
		return stage;
	}



    //----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.stage = compound.getInteger("Stage");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("Stage", stage);
        return compound;
    }



    //----------------------- PROCESS -----------------------
	@Override
	public void update() {
	    EnumFacing enumfacing = state().getValue(beamFacing());
	    if(!worldObj.isRemote){
	    	if( !isSameBeamDirection(state(), enumfacing) && !isValidEmitter(state(), enumfacing)){ 
	    		worldObj.setBlockState(pos, air());
	    	}
			List mobs = worldObj.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)));
			if(mobs != null && !mobs.isEmpty()) {
				for(int i = 0; i < mobs.size(); i++) {
					if(mobs.get(i) != null){
						EntityLivingBase mob = (EntityLivingBase) mobs.get(i);
						if(!isPlayerCreative(mob)){
							mob.attackEntityFrom(DamageSource.inFire, getStage());
						}
					}
				}
			}
	    }
	}

	private boolean isPlayerCreative(EntityLivingBase mob) {
		if(mob instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) mob;
			if(player.capabilities.isCreativeMode){
				return true;
			}
		}
		return false;
	}

	private boolean isValidEmitter(IBlockState state, EnumFacing enumfacing) {
		BlockPos txPos = pos.offset(enumfacing.getOpposite());
		IBlockState checkstate = worldObj.getBlockState(txPos);
		if(checkstate != null && checkstate.getBlock() == electro()){
		    EnumFacing electrofacing = checkstate.getValue(electroFacing());
			if(electrofacing == enumfacing ){
				TileEntityElectroLaser electro = (TileEntityElectroLaser) worldObj.getTileEntity(txPos);
				if(electro.isPowered()){
					return true;
				}
			}
		}
		return false;
	}

	private boolean isSameBeamDirection(IBlockState state, EnumFacing enumfacing){
		IBlockState checkstate = worldObj.getBlockState(pos.offset(enumfacing.getOpposite()));
		if(checkstate != null && checkstate.getBlock() == ray()){
		    EnumFacing beamfacing = checkstate.getValue(beamFacing());
			if(beamfacing == enumfacing ){
				return true;
			}
		}
		return false;
	}

}