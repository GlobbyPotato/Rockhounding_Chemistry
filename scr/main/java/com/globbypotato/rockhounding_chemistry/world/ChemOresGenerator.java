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
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ChemOresGenerator implements IWorldGenerator {

	public static int mineralFrequency; 
	public static int mineralMinVein; public static int mineralMaxVein;
	public static int mineralMinLevel; public static int mineralMaxLevel;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.getDimension() == 0) {
			generateMineral(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16));
		}else{
			for(int X = 0; X < ModConfig.dimensions.length; X++ ){
				if(world.provider.getDimension() == ModConfig.dimensions[X]){
					generateMineral(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16));
				}
			}
		}
	}

	private static void generateMineral(World world, Random random, BlockPos pos) {
		if(mineralFrequency > 0){
			addNewMineral(ModBlocks.mineralOres, 0,  world, random, pos, 16, 16, mineralMinVein, mineralMaxVein, mineralFrequency, mineralMinLevel, mineralMaxLevel, Blocks.STONE);//mineral
		}
	}

	private static void addNewMineral(Block block, int metadata, World world, Random random, BlockPos pos, int maxX, int maxZ, int minVeinSize, int maxVeinSize, int chanceToSpawn, int minY, int maxY, Block generateIn) {
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