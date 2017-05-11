package com.globbypotato.rockhounding_chemistry.fluids;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.FillBucketEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BucketHandler {
	   public static BucketHandler INSTANCE = new BucketHandler();
	   public Map<Block, Item> buckets = new HashMap<Block, Item>();

	   private BucketHandler() {}

	   public void registerFluid(Block fluidBlock, Item fluidBeaker) {buckets.put(fluidBlock, fluidBeaker);}

	   @SubscribeEvent
	   public void onBucketFill(FillBucketEvent event) {
		   if(event != null && !event.isCanceled() && event.getTarget() != null && event.getTarget().typeOfHit == RayTraceResult.Type.BLOCK
			&& event.getTarget().getBlockPos() != null && event.getFilledBucket() == null && event.getEntityPlayer() != null && event.getWorld() != null
			&& event.getWorld().isBlockModifiable(event.getEntityPlayer(), event.getTarget().getBlockPos()) && event.getEmptyBucket() != null
			&& event.getEmptyBucket().getItem() == ModFluids.beaker && event.getEmptyBucket().stackSize > 0 && event.getTarget().sideHit != null
			&& event.getEntityPlayer().canPlayerEdit(event.getTarget().getBlockPos().offset(event.getTarget().sideHit), event.getTarget().sideHit,
			   event.getEmptyBucket())){
			   ItemStack filledBucket = getFilledBucket(event.getWorld(), event.getTarget());
			   if (filledBucket != null) {
				   event.setFilledBucket(filledBucket);
				   event.setResult(Result.ALLOW);
			   }
		   }
	   }

	   private ItemStack getFilledBucket(World world, RayTraceResult pos) {
		   final BlockPos blockPos = pos.getBlockPos();
		   final Block block = world.getBlockState(blockPos).getBlock();
		   if(block instanceof BlockFluidClassic && buckets.containsKey(block) && ((BlockFluidClassic) block).isSourceBlock(world, blockPos)) {
			   final Item bucket = buckets.get(block);
			   if (bucket != null) {
				   world.setBlockToAir(blockPos);
				   return new ItemStack(bucket);
			   }
		   }
		   return null;
	   }
}