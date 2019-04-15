package com.globbypotato.rockhounding_chemistry.world;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ChemOresGenerator implements IWorldGenerator {

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(ModConfig.dimensions.length > 0){
			for(int x = 0; x < ModConfig.dimensions.length; x++){
				if(world.provider.getDimension() == ModConfig.dimensions[x]){
					generateMineral(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16));
				}
			}
		}
	}

	public void generateMineral(World world, Random random, BlockPos pos) {
		if(ModConfig.mineralFrequency > 0){
			addNewMineral(ModBlocks.UNINSPECTED_MINERAL, 0,  world, random, pos, 16, 16, ModConfig.mineralMinVein, ModConfig.mineralMaxVein, ModConfig.mineralFrequency, ModConfig.mineralMinLevel, ModConfig.mineralMaxLevel, Blocks.STONE);//mineral
		}
	}

	public void addNewMineral(Block block, int metadata, World world, Random random, BlockPos pos, int maxX, int maxZ, int minVeinSize, int maxVeinSize, int chanceToSpawn, int minY, int maxY, Block generateIn) {
		if (minY < 0 || maxY > 256 || minY > maxY) throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		int oreVeinSize = minVeinSize + random.nextInt(1 + (maxVeinSize - minVeinSize));
		for (int i = 0; i < chanceToSpawn; i++) {
			int x = pos.getX() + random.nextInt(maxX);
			int y = minY + random.nextInt(1 + (maxY - minY));
			int z = pos.getZ() + random.nextInt(maxZ);
            IBlockState state = block.getStateFromMeta(metadata);
			WorldGenMinable mine = new WorldGenMinable(state, oreVeinSize, BlockMatcher.forBlock(generateIn));
			mine.generate(world, random, new BlockPos(x, y, z));
		}
	}

}