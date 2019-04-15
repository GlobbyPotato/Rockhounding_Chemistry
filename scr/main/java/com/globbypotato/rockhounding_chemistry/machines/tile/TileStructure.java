package com.globbypotato.rockhounding_chemistry.machines.tile;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileStructure {

	public static TEPowerGenerator getEngine(World world, BlockPos pos, EnumFacing facing, int offset, int rotation){
		EnumFacing engineFacing = EnumFacing.fromAngle(facing.getHorizontalAngle() + rotation);
		BlockPos enginePos = pos.offset(engineFacing, offset);
		TileEntity te = world.getTileEntity(enginePos);
		if(world.getBlockState(enginePos) != null && te instanceof TEPowerGenerator){
			TEPowerGenerator engine = (TEPowerGenerator)te;
			if(engine.getFacing() == facing.getOpposite()){
				return engine;
			}
		}
		return null;
	}

	public static TEPressureVessel getVessel(World world, BlockPos pos, EnumFacing facing, int offset, int rotation){
		EnumFacing vesselFacing = EnumFacing.fromAngle(facing.getHorizontalAngle() + rotation);
		BlockPos vesselPos = pos.offset(vesselFacing, offset);
		TileEntity te = world.getTileEntity(vesselPos);
		if(world.getBlockState(vesselPos) != null && te instanceof TEPressureVessel){
			TEPressureVessel vessel = (TEPressureVessel)te;
			if(vessel.getFacing() == facing){
				return vessel;
			}
		}
		return null;
	}

	public static TileVessel getHolder(World world, BlockPos pos, EnumFacing facing, int offset, int rotation){
		EnumFacing vesselFacing = EnumFacing.fromAngle(facing.getHorizontalAngle() + rotation);
		BlockPos vesselPos = pos.offset(vesselFacing, offset);
		TileEntity te = world.getTileEntity(vesselPos);
		if(world.getBlockState(vesselPos) != null && te instanceof TileVessel){
			TileVessel vessel = (TileVessel)te;
			if(vessel.getFacing() == facing){
				return vessel;
			}
		}
		return null;
	}

	public static TEFluidTank getTank(World world, BlockPos pos, EnumFacing facing, int offset){
		BlockPos tankPos = pos.offset(facing, offset);
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEFluidTank){
			TEFluidTank tank = (TEFluidTank)te;
			return tank;
		}
		return null;
	}

	public static TileTank getAnyTank(World world, BlockPos pos, EnumFacing facing, int offset){
		BlockPos tankPos = pos.offset(facing, offset);
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TileTank){
			TileTank tank = (TileTank)te;
			return tank;
		}
		return null;
	}

	public static TEBufferTank getBufferTank(World world, BlockPos pos, EnumFacing facing, int offset){
		BlockPos tankPos = pos.offset(facing, offset);
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEBufferTank){
			TEBufferTank tank = (TEBufferTank)te;
			return tank;
		}
		return null;
	}

	public static TEFlotationTank getFlotationTank(World world, BlockPos pos, EnumFacing facing, int offset){
		BlockPos tankPos = pos.offset(facing, offset);
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEFlotationTank){
			TEFlotationTank tank = (TEFlotationTank)te;
			return tank;
		}
		return null;
	}

	public static TEGasPressurizer getPressurizer(World world, BlockPos pos, EnumFacing facing, int offset, int rotation){
		EnumFacing engineFacing = EnumFacing.fromAngle(facing.getHorizontalAngle() + rotation);
		BlockPos enginePos = pos.offset(engineFacing, offset);
		TileEntity te = world.getTileEntity(enginePos);
		if(world.getBlockState(enginePos) != null && te instanceof TEGasPressurizer){
			TEGasPressurizer engine = (TEGasPressurizer)te;
			if(engine.getFacing() == facing.getOpposite()){
				return engine;
			}
		}
		return null;
	}

	public static TEParticulateCollector getCollector(World world, BlockPos pos, EnumFacing facing, int offset, int rotation){
		EnumFacing collectorFacing = EnumFacing.fromAngle(facing.getHorizontalAngle() + rotation);
		BlockPos collectorPos = pos.offset(collectorFacing, offset);
		TileEntity te = world.getTileEntity(collectorPos);
		if(world.getBlockState(collectorPos) != null && te instanceof TEParticulateCollector){
			TEParticulateCollector collector = (TEParticulateCollector)te;
			if(collector.getFacing() == facing.getOpposite()){
				return collector;
			}
		}
		return null;
	}

	public static TELeachingVatTank getLeacher(World world, BlockPos pos, EnumFacing facing, int offset){
		BlockPos tankPos = pos.offset(facing, offset);
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TELeachingVatTank){
			TELeachingVatTank tank = (TELeachingVatTank)te;
			if(facing == tank.getFacing() || facing == tank.getFacing().getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public static TEServer getServer(World world, BlockPos pos, EnumFacing facing, int offset, int rotation){
		EnumFacing serverFacing = EnumFacing.fromAngle(facing.getHorizontalAngle() + rotation);
		BlockPos serverPos = pos.offset(serverFacing, offset);
		TileEntity te = world.getTileEntity(serverPos);
		if(world.getBlockState(serverPos) != null && te instanceof TEServer){
			TEServer server = (TEServer)te;
			if(server.getFacing() == facing.getOpposite()){
				return server;
			}
		}
		return null;
	}

}