package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PollutantRecipes;
import com.google.common.collect.Lists;

import net.minecraft.block.material.Material;
import net.minecraft.init.SoundEvents;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidTank;

public abstract interface ICollapse {
	public default ArrayList<FluidTank> collapseList(){
		ArrayList<FluidTank> tanks = new ArrayList<FluidTank>();
		return tanks;
 	}

	public default boolean handleCollapse(int collapse, World world, BlockPos pos) {
		for(FluidTank tank : collapseList()){	
			if(tank.getFluid()!= null && tank.getFluid().getFluid().isGaseous() && tank.getFluidAmount() >= tank.getCapacity()){
				return true;
			}
		}
		return false;
	}

	public default boolean handleRelease(int collapse, World world, BlockPos pos) {
		boolean full = false;
		for(FluidTank tank : collapseList()){	
			if(tank.getFluidAmount() >= tank.getCapacity()){
				full = true;
			}
		}
		return !full;
	}

	public default void doExhaustion(World world, BlockPos pos, int size) {
		for(FluidTank tank : collapseList()){	
			if(isFullTank(tank)){
				int dumpAmount = (tank.getFluidAmount() * ModConfig.exhaustRate) / 100;
				tank.drainInternal(dumpAmount, true);
				world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 1.0F, 0.2F);
				if(isHazardousGas(tank)){
					spawnCloud(world, pos, size);
				}
			}
		}
	}

	public default boolean isFullTank(FluidTank tank){
		return tank.getFluid() != null 
			&& tank.getFluid().getFluid().isGaseous()
			&& tank.getFluidAmount() >= tank.getCapacity();
	}

	public default boolean isHazardousGas(FluidTank tank){
		return PollutantRecipes.pollutant_gases.size() > 0
			&& PollutantRecipes.pollutant_gases.contains(tank.getFluid());
	}

	public default void spawnCloud(World world,BlockPos pos, int size){
        Queue<Tuple<BlockPos, Integer>> queue = Lists.<Tuple<BlockPos, Integer>>newLinkedList();
        List<BlockPos> list = Lists.<BlockPos>newArrayList();
        queue.add(new Tuple(pos, Integer.valueOf(0)));
        int i = 0;

        while (!queue.isEmpty()){
            Tuple<BlockPos, Integer> tuple = (Tuple)queue.poll();
            BlockPos blockpos = tuple.getFirst();
            int j = ((Integer)tuple.getSecond()).intValue();

            for (EnumFacing enumfacing : EnumFacing.values()){
                BlockPos blockpos1 = blockpos.offset(enumfacing);

                if (world.getBlockState(blockpos1).getMaterial() == Material.AIR){
                    world.setBlockState(blockpos1, ModBlocks.TOXIC_CLOUD.getDefaultState(), 2);
                    list.add(blockpos1);
                    ++i;

                    if (j < 6){
                        queue.add(new Tuple(blockpos1, j + 1));
                    }
                }
            }

            if (i > ModConfig.toxicCloudSize * size){
                break;
            }
        }
	}



	//----------------------- PRESSURE -----------------------
	public default TEExhaustionValve getExhaust(World world, BlockPos pos, int offset){
		BlockPos exhaustPos = pos.offset(EnumFacing.UP, offset);
		TileEntity te = world.getTileEntity(exhaustPos);
		if(world.getBlockState(exhaustPos) != null && te instanceof TEExhaustionValve){
			TEExhaustionValve chamber = (TEExhaustionValve)te;
			return chamber;
		}
		return null;
	}

	public default boolean hasExhaust(World world, BlockPos pos, int offset){
		return getExhaust(world, pos, offset) != null;
	}

}