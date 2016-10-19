package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.LaserRX;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityLaserRX extends TileEntity implements ITickable{

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
			if(state.getBlock().getMetaFromState(state) == 0){
				if(isReceivingLaser(state)){
					worldObj.setBlockState(pos, ModBlocks.laserRedstoneRx.getDefaultState().withProperty(LaserRX.VARIANT, LaserRX.EnumType.byMetadata(1)));
				}
			}else if(state.getBlock().getMetaFromState(state) == 1){
				if(!isReceivingLaser(state)){
					worldObj.setBlockState(pos, ModBlocks.laserRedstoneRx.getDefaultState().withProperty(LaserRX.VARIANT, LaserRX.EnumType.byMetadata(0)));
				}
			}
		}
	}

	private boolean isReceivingLaser(IBlockState state) {
		return worldObj.getBlockState(pos.offset(EnumFacing.EAST)).getBlock() == ModBlocks.laserBeam ||
			   worldObj.getBlockState(pos.offset(EnumFacing.WEST)).getBlock() == ModBlocks.laserBeam ||
			   worldObj.getBlockState(pos.offset(EnumFacing.NORTH)).getBlock() == ModBlocks.laserBeam ||
			   worldObj.getBlockState(pos.offset(EnumFacing.SOUTH)).getBlock() == ModBlocks.laserBeam;
	}

}
