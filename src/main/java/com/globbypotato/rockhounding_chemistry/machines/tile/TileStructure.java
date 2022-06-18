package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECycloneSeparatorBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECycloneSeparatorCap;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECycloneSeparatorTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorBalance;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEFluidRouter;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGasHolderTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEShredderTable;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEUnloader;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEGasHolderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileStructure {

	public static void flipTile(World world, BlockPos tilePos, EnumFacing machineFacing, boolean direction) {
		if(world.getBlockState(tilePos) != null && world.getBlockState(tilePos).getBlock() instanceof MachineIO){
			TileEntityInv machine = null;
			if(world.getTileEntity(tilePos) instanceof TECentrifuge) {
				machine = (TECentrifuge)world.getTileEntity(tilePos);
				doTheFlip(machine, machineFacing, direction);
			}else if(world.getTileEntity(tilePos) instanceof TEGasHolderBase) {
				machine = (TEGasHolderBase)world.getTileEntity(tilePos);
				doTheFlip(machine, machineFacing, direction);
				if(world.getTileEntity(tilePos.up()) instanceof TEGasHolderTop) {
					machine = (TEGasHolderTop)world.getTileEntity(tilePos.up());
					doTheFlip(machine, machineFacing, direction);
				}
			}
		}			
	}

	private static void doTheFlip(TileEntityInv machine, EnumFacing machineFacing, boolean direction) {
		if(machine != null) {
			EnumFacing flippedDirection;
			if(direction) {
				flippedDirection = EnumFacing.fromAngle(machineFacing.getHorizontalAngle() + 270);
			}else {
				flippedDirection = EnumFacing.fromAngle(machineFacing.getHorizontalAngle() + 90);
			}
			machine.facing = flippedDirection.getIndex();
			machine.markDirtyClient();
		}
	}

	public static TEPowerGenerator getEngine(World world, BlockPos enginePos, EnumFacing engineFacing){
		TileEntity te = world.getTileEntity(enginePos);
		if(world.getBlockState(enginePos) != null && te instanceof TEPowerGenerator){
			TEPowerGenerator engine = (TEPowerGenerator)te;
			if(engine.getFacing() == engineFacing){
				return engine;
			}
		}
		return null;
	}

	public static TileVessel getHolder(World world, BlockPos tankPos, EnumFacing tankFacing){
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TileVessel){
			TileVessel vessel = (TileVessel)te;
			if(vessel.getFacing() == tankFacing){
				return vessel;
			}
		}
		return null;
	}

	public static TEBufferTank getBufferTank(World world, BlockPos tankPos){
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEBufferTank){
			TEBufferTank tank = (TEBufferTank)te;
			return tank;
		}
		return null;
	}

	public static TEFlotationTank getFlotationTank(World world, BlockPos tankPos){
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEFlotationTank){
			TEFlotationTank tank = (TEFlotationTank)te;
			return tank;
		}
		return null;
	}

	public static TEAuxiliaryEngine getPressurizer(World world, BlockPos pos, EnumFacing facing){
		TileEntity te = world.getTileEntity(pos);
		if(world.getBlockState(pos) != null && te instanceof TEAuxiliaryEngine){
			TEAuxiliaryEngine pressurizer = (TEAuxiliaryEngine)te;
			if(pressurizer.getFacing() == facing){
				return pressurizer;
			}
		}
		return null;
	}

	public static TELeachingVatTank getLeacher(World world, BlockPos tankPos, EnumFacing facing){
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TELeachingVatTank){
			TELeachingVatTank tank = (TELeachingVatTank)te;
			if(facing == tank.getFacing() || facing == tank.getFacing().getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public static TEMineralSizerTank getCrusher(World world, BlockPos tankPos, EnumFacing facing){
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEMineralSizerTank){
			TEMineralSizerTank tank = (TEMineralSizerTank)te;
			if(facing == tank.getFacing() || facing == tank.getFacing().getOpposite()){
				return tank;
			}
		}
		return null;
	}

	public static TEServer getServer(World world, BlockPos serverPos, EnumFacing serverFacing){
		TileEntity te = world.getTileEntity(serverPos);
		if(world.getBlockState(serverPos) != null && te instanceof TEServer){
			TEServer server = (TEServer)te;
			if(server.getFacing() == serverFacing){
				return server;
			}
		}
		return null;
	}

	public static boolean getFluidRouter(World world, BlockPos routerPos, EnumFacing facing) {
		int routerCouple = 0;
		TileEntity te1 = world.getTileEntity(routerPos);
		if(world.getBlockState(routerPos) != null && te1 instanceof TEFluidRouter){
			TEFluidRouter router = (TEFluidRouter)te1;
			if(router.getFacing() == facing){
				routerCouple++;
			}
		}
		TileEntity te2 = world.getTileEntity(routerPos.offset(facing));
		if(world.getBlockState(routerPos.offset(facing)) != null && te2 instanceof TEFluidRouter){
			TEFluidRouter router = (TEFluidRouter)te2;
			if(router.getFacing() == facing.getOpposite()){
				routerCouple++;
			}
		}
		return routerCouple == 2;
	}

	public static TEUnloader getUnloader(World world, BlockPos machinePos, EnumFacing machineFacing){
		TileEntity te = world.getTileEntity(machinePos);
		if(world.getBlockState(machinePos) != null && te instanceof TEUnloader){
			TEUnloader unloader = (TEUnloader)te;
			if(unloader.getFacing() == machineFacing){
				return unloader;
			}
		}
		return null;
	}

	public static TECentrifuge getCentrifuge(World world, BlockPos machinePos, EnumFacing machineFacing){
		TileEntity te = world.getTileEntity(machinePos);
		if(world.getBlockState(machinePos) != null && te instanceof TECentrifuge){
			TECentrifuge centrifuge = (TECentrifuge)te;
			if(centrifuge.getFacing() == machineFacing){
				return centrifuge;
			}
		}
		return null;
	}

	public static Block getStructure(World world, BlockPos pos, int meta){
		IBlockState blockState = world.getBlockState(pos);
		Block structure = blockState.getBlock();
		if(MachineIO.miscBlocksA(structure, blockState, meta)){
			return structure;
		}
		return null;
	}

	public static boolean getCycloneSeparator(World world, BlockPos pos, EnumFacing machineFacing){
		int cycloneParts = 0;
		TileEntity base = world.getTileEntity(pos);
		if(world.getBlockState(pos) != null && base instanceof TECycloneSeparatorBase){
			TECycloneSeparatorBase cyclonePart = (TECycloneSeparatorBase)base;
			if(cyclonePart.getFacing() == machineFacing){
				cycloneParts++;
			}
		}
		TileEntity top = world.getTileEntity(pos.offset(EnumFacing.UP, 1));
		if(world.getBlockState(pos.offset(EnumFacing.UP, 1)) != null && top instanceof TECycloneSeparatorTop){
			TECycloneSeparatorTop cyclonePart = (TECycloneSeparatorTop)top;
			if(cyclonePart.getFacing() == machineFacing){
				cycloneParts++;
			}
		}
		TileEntity cap = world.getTileEntity(pos.offset(EnumFacing.UP, 2));
		if(world.getBlockState(pos.offset(EnumFacing.UP, 2)) != null && cap instanceof TECycloneSeparatorCap){
			TECycloneSeparatorCap cyclonePart = (TECycloneSeparatorCap)cap;
			if(cyclonePart.getFacing() == machineFacing){
				cycloneParts++;
			}
		}
		EnumFacing press = EnumFacing.fromAngle(machineFacing.getHorizontalAngle() + 90);
		TileEntity te = world.getTileEntity(pos.offset(EnumFacing.UP, 2).offset(press));
		if(world.getBlockState(pos) != null && te instanceof TEAuxiliaryEngine){
			TEAuxiliaryEngine cyclonePart = (TEAuxiliaryEngine)te;
			if(cyclonePart.getFacing() == press.getOpposite()){
				cycloneParts++;
			}
		}
		return cycloneParts == 4;
	}

	public static TEElementsCabinetBase getElementCabinet(World world, BlockPos machinePos, EnumFacing machineFacing){
		TileEntity te = world.getTileEntity(machinePos);
		if(world.getBlockState(machinePos) != null && te instanceof TEElementsCabinetBase){
			TEElementsCabinetBase cabinet = (TEElementsCabinetBase)te;
			if(cabinet.getFacing() == machineFacing){
				return cabinet;
			}
		}
		return null;
	}

	public static TEMaterialCabinetBase getMaterialCabinet(World world, BlockPos machinePos, EnumFacing machineFacing){
		TileEntity te = world.getTileEntity(machinePos);
		if(world.getBlockState(machinePos) != null && te instanceof TEMaterialCabinetBase){
			TEMaterialCabinetBase cabinet = (TEMaterialCabinetBase)te;
			if(cabinet.getFacing() == machineFacing){
				return cabinet;
			}
		}
		return null;
	}

	public static TEFluidCistern getFluidCistern(World world, BlockPos machinePos, EnumFacing machineFacing){
		TileEntity te = world.getTileEntity(machinePos);
		if(world.getBlockState(machinePos) != null && te instanceof TEFluidCistern){
			TEFluidCistern tank = (TEFluidCistern)te;
			if(tank.getFacing() == machineFacing){
				return tank;
			}
		}
		return null;
	}

	public static TEShredderTable getShaker(World world, BlockPos tablePos, EnumFacing facing){
		TileEntity te = world.getTileEntity(tablePos);
		if(world.getBlockState(tablePos) != null && te instanceof TEShredderTable){
			TEShredderTable table = (TEShredderTable)te;
			if(facing == table.getFacing() || facing == table.getFacing().getOpposite()){
				return table;
			}
		}
		return null;
	}

	public static TEWashingTank getWasher(World world, BlockPos tankPos){
		TileEntity te = world.getTileEntity(tankPos);
		if(world.getBlockState(tankPos) != null && te instanceof TEWashingTank){
			TEWashingTank tank = (TEWashingTank)te;
			if(tank != null){
				return tank;
			}
		}
		return null;
	}

	public static TEExtractorBalance getBalance(World world, BlockPos pos, EnumFacing facing){
		BlockPos balancePos = pos.offset(facing);
		TileEntity te = world.getTileEntity(balancePos);
		if(world.getBlockState(balancePos) != null && te instanceof TEExtractorBalance){
			TEExtractorBalance balance = (TEExtractorBalance)te;
			if(balance.getFacing() == facing.getOpposite()){
				return balance;
			}
		}
		return null;
	}

}