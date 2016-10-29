package com.globbypotato.rockhounding_chemistry.world;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;

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
import net.minecraftforge.fml.common.Loader;

public class ChemOresGenerator implements IWorldGenerator {

	public static int mineralFrequency; 
	public static int mineralMinVein; public static int mineralMaxVein;
	public static int mineralMinLevel; public static int mineralMaxLevel;
	
	public static int aromaFrequency;
	public static int aromaMinVein; public static int aromaMaxVein;
	public static int aromaMinLevel; public static int aromaMaxLevel;
	
	private String aromaID = "Aroma1997sDimension";
	public static boolean enableAromaDimension;
	public static int aromaDimension;

	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		if(world.provider.getDimension() == 0) {
			generateOverworld(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16));
		}else if (Loader.isModLoaded(aromaID) && enableAromaDimension && world.provider.getDimension() == aromaDimension){
			generateAroma(world, random, new BlockPos(chunkX * 16, 64, chunkZ * 16));
		}
	}

	private void generateOverworld(World world, Random random, BlockPos pos) {
		addNewMineral(ModBlocks.mineralOres, 0,  world, random, pos, 16, 16, mineralMinVein, mineralMaxVein, mineralFrequency, mineralMinLevel, mineralMaxLevel, Blocks.STONE);//mineral
	}
	private void generateAroma(World world, Random random, BlockPos pos) {
		addNewMineral(ModBlocks.mineralOres, 0,  world, random, pos, 16, 16, aromaMinVein, aromaMaxVein, aromaFrequency, mineralMinLevel, mineralMaxLevel, Blocks.STONE);//mineral
	}

	private void addNewMineral(Block block, int metadata, World world, Random random, BlockPos pos, int maxX, int maxZ, int minVeinSize, int maxVeinSize, int chanceToSpawn, int minY, int maxY, Block generateIn) {
		if (minY < 0 || maxY > 256 || minY > maxY) throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		int oreVeinSize = minVeinSize + random.nextInt(maxVeinSize - minVeinSize);
		for (int i = 0; i < chanceToSpawn; i++) {
			int x = pos.getX() + random.nextInt(maxX);
			int y = minY + random.nextInt(maxY - minY);
			int z = pos.getZ() + random.nextInt(maxZ);
			BlockPos blockpos = new BlockPos(x, y, z);
            IBlockState state = block.getStateFromMeta(metadata);
			WorldGenMinable mine = new WorldGenMinable(state, oreVeinSize, BlockMatcher.forBlock(generateIn));
			mine.generate(world, random, new BlockPos(x, y, z));
		}
	}

}