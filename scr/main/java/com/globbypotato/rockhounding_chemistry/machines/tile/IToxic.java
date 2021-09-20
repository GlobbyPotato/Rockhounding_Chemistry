package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PollutantRecipes;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidTank;

public abstract interface IToxic {

	public default ArrayList<FluidTank> hazardList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		return tanks;
 	}

	public default void handleToxic(World world, BlockPos pos){
		if(ModConfig.enableHazard){
			if(world.rand.nextInt(ModConfig.hazardChance) == 0){
				for(FluidTank tank : hazardList()){	
					if(isHazardousFluid(tank)){
						int maxRadius = (ModConfig.leakingRadius * 2) + 1;
						int xRad = world.rand.nextInt(maxRadius) - ModConfig.leakingRadius;
						int yRad = world.rand.nextInt(maxRadius) - ModConfig.leakingRadius;
						int zRad = world.rand.nextInt(maxRadius) - ModConfig.leakingRadius;
						BlockPos toxicPos = new BlockPos(pos.getX() + xRad, pos.getY() + yRad, pos.getZ() + zRad);
						if(canLeakByType(world, toxicPos) && hasSolidBase(world, toxicPos, xRad, yRad, zRad)){
				    		world.playSound(null, pos, SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.AMBIENT, 1.0F, 2.0F);
							world.setBlockState(toxicPos, ModFluids.TOXIC_SLUDGE.getBlock().getStateFromMeta(0));
				    		world.playSound(null, pos, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.AMBIENT, 0.4F, 0.2F);
							break;
						}
					}
				}
			}
		}
	}

	public default boolean canLeakByType(World world, BlockPos toxicPos) {
		Block erodedBlock = world.getBlockState(toxicPos).getBlock();
		if(ModConfig.leakingType) {
			return erodedBlock == Blocks.AIR;
		}else {
			return world.getBlockState(toxicPos).getMaterial().isReplaceable() || erodedBlock.isReplaceable(world, toxicPos);
		}
	};

	public default boolean isHazardousFluid(FluidTank tank){
		return tank.getFluid() != null 
			&& !tank.getFluid().getFluid().isGaseous()
			&& PollutantRecipes.pollutant_fluids.size() > 0
			&& PollutantRecipes.pollutant_fluids.contains(tank.getFluid())
			&& tank.getFluidAmount() >= Fluid.BUCKET_VOLUME;
	}

	public default boolean hasSolidBase(World world, BlockPos toxicPos, int xRad, int yRad, int zRad){
		return world.getBlockState(toxicPos.down()).isSideSolid(world, toxicPos.down(), EnumFacing.UP);
	}
}