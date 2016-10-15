package com.globbypotato.rockhounding_chemistry.world;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.ModContents;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunkGenerator;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fml.common.IWorldGenerator;

public class ChemOresGenerator implements IWorldGenerator {
// Coal Ores
	public static int mineralFrequency; public static int mineralMinVein; public static int mineralMaxVein;
	public static int mineralMinLevel; public static int mineralMaxLevel;
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		switch (world.provider.getDimension()) {
			case 1: generateEnd(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16)); break;
			case 0: generateOverworld(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16)); break;
			case -1: generateNether(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16)); break;
		}
	}

	private void generateEnd(World world, Random random, BlockPos pos) { }
	private void generateNether(World world, Random random, BlockPos pos) { }

	private void generateOverworld(World world, Random random, BlockPos pos) {
		addNewMineral(ModContents.mineralOres, 0,  world, random, pos, 16, 16, mineralMaxVein, mineralFrequency, mineralMinLevel, mineralMaxLevel, Blocks.STONE);//mineral
	}

	private void addNewMineral(Block block, int metadata, World world, Random random, BlockPos pos, int maxX, int maxZ, int maxVeinSize, int chanceToSpawn, int minY, int maxY, Block generateIn) {
		if (minY < 0 || maxY > 256 || minY > maxY) throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		int ironVeinSize = mineralMinVein + random.nextInt(mineralMaxVein - mineralMinVein);
		for (int i = 0; i < chanceToSpawn; i++) {
			int x = pos.getX() + random.nextInt(maxX);
			int y = minY + random.nextInt(maxY - minY);
			int z = pos.getZ() + random.nextInt(maxZ);
			BlockPos blockpos = new BlockPos(x, y, z);
            IBlockState state = block.getStateFromMeta(metadata);
			WorldGenMinable mine = new WorldGenMinable(state, ironVeinSize, BlockMatcher.forBlock(generateIn));
			mine.generate(world, random, new BlockPos(x, y, z));
		}
	}

}