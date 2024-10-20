package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.MiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesC;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesE;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesF;
import com.globbypotato.rockhounding_chemistry.machines.MachinesA;
import com.globbypotato.rockhounding_chemistry.machines.MachinesB;
import com.globbypotato.rockhounding_chemistry.machines.MachinesC;
import com.globbypotato.rockhounding_chemistry.machines.MachinesD;
import com.globbypotato.rockhounding_chemistry.machines.MachinesE;
import com.globbypotato.rockhounding_chemistry.machines.MachinesF;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.block.Block;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileAssembler {

	static TileEntityInv machine = null;
	static BlockPos partPos = null;

	public static void loadLabOven(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.LAB_OVEN_CHAMBER, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.LAB_OVEN_CONTROLLER, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 2).offset(toLeft, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toLeft, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toRight, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1).offset(toLeft, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1).offset(toRight, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1).offset(toRight, 2);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(toLeft, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toRight, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toFront.getOpposite());
	}

	public static void loadChemicalExtractor(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_CONTROLLER, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_REACTOR, MachinesC.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.EXTRACTOR_CHARGER);
		partPos = pos.offset(toFront, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 4);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 4).offset(toLeft, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_GLASSWARE, MachinesC.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 4).offset(toRight, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_STABILIZER, MachinesC.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 6);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 7);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 5);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 3).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.ELEMENTS_CABINET_BASE, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.ELEMENTS_CABINET_TOP, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 6).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 7).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 8);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
//		partPos = pos.offset(toFront, 3).offset(toLeft, 3);
//			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_BALANCE, MachinesC.FACING, toFront, toRight);
//		partPos = pos.offset(toFront, 3).offset(toRight, 3);
//			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_BALANCE, MachinesC.FACING, toFront, toLeft);
	}

	public static void loadPrecipitator(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.PRECIPITATION_CHAMBER, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.PRECIPITATION_CONTROLLER, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.PRECIPITATION_REACTOR, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.PRECIPITATION_REACTOR, MachinesE.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 5);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toFront.getOpposite());
	}

	public static void loadLeaching(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.LEACHING_VAT_CONTROLLER, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.LEACHING_VAT_TANK, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.LEACHING_VAT_TANK, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.LEACHING_VAT_TANK, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 5);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 6);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.SPECIMEN_COLLECTOR, MachinesC.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 6).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
	}

	public static void loadRetention(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.RETENTION_VAT_CONTROLLER, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.LEACHING_VAT_TANK, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.LEACHING_VAT_TANK, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.SPECIMEN_COLLECTOR, MachinesC.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
	}

	public static void loadCondenser(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.GAS_CONDENSER, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront.getOpposite());
	}

	public static void loadExpander(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.GAS_EXPANDER, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront);
	}

	public static void loadBlender(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.LAB_BLENDER_CONTROLLER, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.LAB_BLENDER_TANK, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
	}

	public static void loadMixer(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.POWDER_MIXER_CONTROLLER, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.POWDER_MIXER_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 1).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.ELEMENTS_CABINET_BASE, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.ELEMENTS_CABINET_TOP, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_TOP, MachinesD.FACING, toFront, toFront);
	}

	public static void loadCompressor(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AIR_COMPRESSOR, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront);
	}

	public static void loadProfiling(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.PROFILING_BENCH, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toFront.getOpposite());
	}

	public static void loadPond(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.SLURRY_POND, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.WASHING_TANK, MachinesE.FACING, toFront, toFront);
	}

	public static void loadAlloyer(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.METAL_ALLOYER, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.METAL_ALLOYER_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.FLUID_CISTERN, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 3).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.ELEMENTS_CABINET_BASE, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.ELEMENTS_CABINET_TOP, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 5);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toFront.getOpposite());
	}

	public static void loadOrbiter(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos;
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.ORBITER, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.WASHING_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.FLUID_CISTERN, MachinesB.FACING, toFront, toFront.getOpposite());
	}

	public static void loadGasifier(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.GASIFIER_CONTROLLER, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.GASIFIER_BURNER, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toRight, 1).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.FLUID_CISTERN, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.REINFORCED_CISTERN, MachinesB.FACING, toFront, toFront);
//		partPos = pos.offset(toFront, 1).offset(toRight, 3);
//			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_BALANCE, MachinesC.FACING, toFront, toLeft);
	}

	public static void loadSizer(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SIZER_TRANSMISSION, MachinesF.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SIZER_CONTROLLER, MachinesF.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SIZER_TANK, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SIZER_TANK, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 5);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SIZER_TANK, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 6);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SIZER_TANK, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SIZER_CABINET, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 8);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 7);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SIZER_COLLECTOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 9);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toFront.getOpposite());
	}

	public static void loadExchanger(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.HEAT_EXCHANGER_BASE, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.HEAT_EXCHANGER_TOP, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_BASE, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_TOP, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 4);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_CAP, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.FLUID_CISTERN, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 1).offset(EnumFacing.UP, 4);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
	}

	public static void loadCSTR(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.STIRRED_TANK_OUT, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.STIRRED_TANK_BASE, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 2);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.STIRRED_TANK_CONTROLLER, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.EXTRACTOR_CHARGER);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toRight, 1).offset(EnumFacing.UP, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 3);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1).offset(EnumFacing.UP, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.FLUID_ROUTER, MachinesF.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 3).offset(EnumFacing.UP, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3).offset(toLeft, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.FLOTATION_TANK, MachinesD.FACING, toFront, toFront);
	}

	public static void loadPulling(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.PULLING_CRUCIBLE_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.PULLING_CRUCIBLE_CAP);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.EXTRACTOR_CHARGER);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toRight, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2).offset(EnumFacing.UP, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
	}

	public static void loadShredder(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SHREDDER_BASE, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SHREDDER_CONTROLLER, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.FLUID_CISTERN, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SHREDDER_TABLE, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SHREDDER_TABLE, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 5);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.SHREDDER_TABLE, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 6);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.WASHING_TANK, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 7);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 6).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 6).offset(toLeft, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 6).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 6).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 6).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_TOP, MachinesD.FACING, toFront, toFront);
//		partPos = pos.offset(toFront, 6).offset(toRight, 3);
//			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_BALANCE, MachinesC.FACING, toFront, toLeft);
	}

	public static void loadDeposition(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.DEPOSITION_CHAMBER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 5);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 6);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 6).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront);
	}

	public static void loadPurifier(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.GAS_PURIFIER, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_BASE, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_TOP, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_CAP, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.MATERIAL_CABINET_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 3);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
//		partPos = pos.offset(toFront, 1).offset(toRight, 3);
//			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.EXTRACTOR_BALANCE, MachinesC.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toRight, 1).offset(EnumFacing.UP, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
	}

	public static void loadReformer(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.REFORMER_CONTROLLER, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.REFORMER_REACTOR, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 2);;
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.REFORMER_TOWER);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 3);;
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.REFORMER_TOWER);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 4);;
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.REFORMER_TOWER);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 5);;
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.REFORMER_TOWER_TOP);
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 4);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAS_ROUTER);
		partPos = pos.offset(toFront, 5);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAS_ROUTER);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_BASE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 2);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_TOP, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.CYCLONE_SEPARATOR_CAP, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 4).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 4).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toLeft, 1).offset(EnumFacing.UP, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 5).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 5).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 5).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 5).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 5).offset(toRight, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 5).offset(toRight, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 6);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 7);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 6).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.BUFFER_TANK, MachinesE.FACING, toFront, toFront);
	}

	public static void loadGan(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.GAN_CONTROLLER, MachinesC.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 3).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 4);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 6);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 5);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_INJECTOR);
		partPos = pos.offset(toFront, 7);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_INJECTOR);
		partPos = pos.offset(toFront, 4).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 4).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 5).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 7).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 5).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 7).offset(toLeft, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 5).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 5).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 7).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 7).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 6).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 6).offset(toRight, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.GAN_TURBOEXPANDER_BASE, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 6).offset(toRight, 1).offset(EnumFacing.UP, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.GAN_TURBOEXPANDER_TOP, MachinesB.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 8);
			setMachineC(partPos, world, ModBlocks.MACHINES_C, MachinesC.VARIANT, EnumMachinesC.MULTIVESSEL, MachinesC.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 5).offset(EnumFacing.UP, 1);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 5).offset(EnumFacing.UP, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 5).offset(EnumFacing.UP, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 5).offset(EnumFacing.UP, 4);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 5).offset(EnumFacing.UP, 5);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 5).offset(EnumFacing.UP, 6);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER_TOP);
		partPos = pos.offset(toFront, 7).offset(EnumFacing.UP, 1);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 7).offset(EnumFacing.UP, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 7).offset(EnumFacing.UP, 3);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER);
		partPos = pos.offset(toFront, 7).offset(EnumFacing.UP, 4);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAN_TOWER_TOP);
	}

	public static void loadTubular(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.TUBULAR_BED_TANK, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.TUBULAR_BED_CONTROLLER, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.TUBULAR_BED_BASE, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 1);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.TUBULAR_BED_LOW, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 2);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.TUBULAR_BED_MID, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 3);
			setMachineF(partPos, world, ModBlocks.MACHINES_F, MachinesF.VARIANT, EnumMachinesF.TUBULAR_BED_TOP, MachinesF.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.UNLOADER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.SERVER, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toRight, 1);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.POWER_GENERATOR, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(toLeft, 2);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAS_ROUTER);
		partPos = pos.offset(toFront, 1).offset(toLeft, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 3).offset(toLeft, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4).offset(toLeft, 2);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4).offset(toLeft, 2).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2).offset(toLeft, 3);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.AUXILIARY_ENGINE, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toLeft, 1);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.SEPARATOR);
		partPos = pos.offset(toFront, 2).offset(EnumFacing.UP, 4);
			setBlockA(partPos, world, ModBlocks.MISC_BLOCKS_A, MiscBlocksA.VARIANT, EnumMiscBlocksA.GAS_ROUTER);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 3).offset(EnumFacing.UP, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(toLeft, 1).offset(EnumFacing.UP, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toRight, 1).offset(EnumFacing.UP, 4);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toLeft);
		partPos = pos.offset(EnumFacing.UP, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(EnumFacing.UP, 5);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 4).offset(EnumFacing.UP, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 4).offset(EnumFacing.UP, 5);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 2).offset(toRight, 2).offset(EnumFacing.UP, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toRight, 2).offset(EnumFacing.UP, 5);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toLeft);
		partPos = pos.offset(toFront, 2).offset(toLeft, 2).offset(EnumFacing.UP, 4);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 2).offset(toLeft, 2).offset(EnumFacing.UP, 5);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toRight);
	}

	public static void loadRegen(BlockPos pos, World world, EnumFacing toFront, EnumFacing toLeft, EnumFacing toRight) {
		partPos = pos.offset(toFront, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.CATALYST_REGEN, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 1).offset(EnumFacing.UP, 1);
			setMachineE(partPos, world, ModBlocks.MACHINES_E, MachinesE.VARIANT, EnumMachinesE.CATALYST_REGEN_PIPES, MachinesE.FACING, toFront, toFront);
		partPos = pos.offset(toFront, 2);
			setMachineA(partPos, world, ModBlocks.MACHINES_A, MachinesA.VARIANT, EnumMachinesA.CENTRIFUGE, MachinesA.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 1).offset(toLeft, 1);
			setMachineB(partPos, world, ModBlocks.MACHINES_B, MachinesB.VARIANT, EnumMachinesB.FLUID_CISTERN, MachinesB.FACING, toFront, toRight);
		partPos = pos.offset(toFront, 3);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_BASE, MachinesD.FACING, toFront, toFront.getOpposite());
		partPos = pos.offset(toFront, 3).offset(EnumFacing.UP, 1);
			setMachineD(partPos, world, ModBlocks.MACHINES_D, MachinesD.VARIANT, EnumMachinesD.GAS_HOLDER_TOP, MachinesD.FACING, toFront, toFront.getOpposite());
	}



	private static void setBlockA(BlockPos partPos, World world, Block block, PropertyEnum variant, EnumMiscBlocksA component) {
		world.setBlockState(partPos, block.getDefaultState().withProperty(variant, component), 2);
	}

	private static void setMachineA(BlockPos partPos, World world, Block machineBlock, PropertyEnum variant, EnumMachinesA component, PropertyDirection facing, EnumFacing toFront, EnumFacing orientation) {
		world.setBlockState(partPos, machineBlock.getDefaultState().withProperty(variant, component).withProperty(facing, toFront), 2);
		addOrientation(partPos, world, orientation.getIndex());
	}

	private static void setMachineB(BlockPos partPos, World world, Block machineBlock, PropertyEnum variant, EnumMachinesB component, PropertyDirection facing, EnumFacing toFront, EnumFacing orientation) {
		world.setBlockState(partPos, machineBlock.getDefaultState().withProperty(variant, component).withProperty(facing, toFront), 2);
		addOrientation(partPos, world, orientation.getIndex());
	}

	private static void setMachineC(BlockPos partPos, World world, Block machineBlock, PropertyEnum variant, EnumMachinesC component, PropertyDirection facing, EnumFacing toFront, EnumFacing orientation) {
		world.setBlockState(partPos, machineBlock.getDefaultState().withProperty(variant, component).withProperty(facing, toFront), 2);
		addOrientation(partPos, world, orientation.getIndex());
		TileEntityInv te = (TileEntityInv)world.getTileEntity(partPos);
		if(te instanceof TEElementsCabinetBase) {
			((TEElementsCabinetBase)te).initializaCabinet();
		}
	}

	private static void setMachineD(BlockPos partPos, World world, Block machineBlock, PropertyEnum variant, EnumMachinesD component, PropertyDirection facing, EnumFacing toFront, EnumFacing orientation) {
		world.setBlockState(partPos, machineBlock.getDefaultState().withProperty(variant, component).withProperty(facing, toFront), 2);
		addOrientation(partPos, world, orientation.getIndex());
		TileEntityInv te = (TileEntityInv)world.getTileEntity(partPos);
		if(te instanceof TEMaterialCabinetBase) {
			((TEMaterialCabinetBase)te).initializaCabinet();
		}
	}

	private static void setMachineE(BlockPos partPos, World world, Block machineBlock, PropertyEnum variant, EnumMachinesE component, PropertyDirection facing, EnumFacing toFront, EnumFacing orientation) {
		world.setBlockState(partPos, machineBlock.getDefaultState().withProperty(variant, component).withProperty(facing, toFront), 2);
		addOrientation(partPos, world, orientation.getIndex());
	}

	private static void setMachineF(BlockPos partPos, World world, Block machineBlock, PropertyEnum variant, EnumMachinesF component, PropertyDirection facing, EnumFacing toFront, EnumFacing orientation) {
		world.setBlockState(partPos, machineBlock.getDefaultState().withProperty(variant, component).withProperty(facing, toFront), 2);
		addOrientation(partPos, world, orientation.getIndex());
	}

	private static void addOrientation(BlockPos partPos, World world, int index) {
		machine = (TileEntityInv)world.getTileEntity(partPos);
		machine.facing = index;
	}

}
