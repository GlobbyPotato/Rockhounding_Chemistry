package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.ElectroLaser;
import com.globbypotato.rockhounding_chemistry.machines.LaserBeam;
import com.globbypotato.rockhounding_chemistry.machines.LaserSplitter;
import com.globbypotato.rockhounding_chemistry.machines.LaserStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.LaserTX;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneRepeater;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;

public class TileEntityLaserBase extends TileEntity implements ITickable{

	public int countBeam;
	public int maxRange = 32;
	public boolean firstObstacle;
	public boolean isPulsing;
	public int splitSide;
	public boolean[] firstObstacles = new boolean[4];

	@Override
	public void update() {}

	public IBlockState state(){
		return worldObj.getBlockState(pos);
	}

	public Block beam(){
		return ModBlocks.laserBeam;
	}

	public Block tx(){
		return ModBlocks.laserRedstoneTx;
	}

	public Block rx(){
		return ModBlocks.laserRedstoneRx;
	}

	public Block splitter(){
		return ModBlocks.laserSplitter;
	}

	public IBlockState air(){
		return Blocks.AIR.getDefaultState();
	}

	public PropertyDirection txFacing(){
		return LaserTX.FACING;
	}

	public PropertyDirection electroFacing(){
		return ElectroLaser.FACING;
	}

	public PropertyDirection nodeFacing(){
		return LaserSplitter.FACING;
	}

	public PropertyDirection beamFacing(){
		return LaserBeam.FACING;
	}

	public Block repeater(){
		return Blocks.POWERED_REPEATER;
	}

	public PropertyDirection repeaterFacing(){
		return BlockRedstoneRepeater.FACING;
	}

	public int emittingSide(){
		return splitSide;
	}

	public boolean pulseMode(){
		return isPulsing;
	}
	
	public Block electro(){
		return ModBlocks.electroLaser;
	}

	public Block amplifier(){
		return ModBlocks.laserAmplifier;
	}

	public PropertyDirection ampliFacing(){
		return LaserStabilizer.FACING;
	}

	public Block ray(){
		return ModBlocks.laserRay;
	}

}