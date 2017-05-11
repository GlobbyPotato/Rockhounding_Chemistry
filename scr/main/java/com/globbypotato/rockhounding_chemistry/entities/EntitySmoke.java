package com.globbypotato.rockhounding_chemistry.entities;

import com.globbypotato.rockhounding_chemistry.ModBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class EntitySmoke extends EntityThrowable{

    public EntitySmoke(World worldIn){
        super(worldIn);
    }

    public EntitySmoke(World worldIn, EntityLivingBase throwerIn){
        super(worldIn, throwerIn);
    }

    public EntitySmoke(World worldIn, double x, double y, double z){
        super(worldIn, x, y, z);
    }

    public static void registerFixesScreenSmoke(DataFixer fixer){
        EntityThrowable.registerFixesThrowable(fixer, "ScreenSmoke");
    }

    @Override
    public void onImpact(RayTraceResult result){
        if(!worldObj.isRemote){
            BlockPos pos = result.getBlockPos();
            int x,y,z;
            Entity entity = result.entityHit;
            if (result.entityHit != null){
                x = (int) entity.posX;
                y = (int) entity.posY;
                z = (int) entity.posZ;
            }else{
                x = pos.getX();
                y = pos.getY();
                z = pos.getZ();
            }
            pos = new BlockPos(x, y, z);

            worldObj.playSound(null, pos, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 0.5F, 0.4F / (rand.nextFloat() * 0.4F + 0.8F));
			BlockPos smokePos;
			for(int j = -2; j < 3; j++){
    			for(int i = -3; i < 3; i++){
	    			for(int k = -3; k < 3; k++){
		    	    	if(rand.nextInt(5) < 4){
		    	    		smokePos = new BlockPos(pos.getX() + i, pos.getY() + j, pos.getZ() + k);
		    	    		if(worldObj.getBlockState(smokePos).getBlock() == Blocks.AIR){
		    	    			worldObj.setBlockState(smokePos, ModBlocks.smokeBlock.getDefaultState());
		    	    		}
		    	    	}
	    			}
    			}
			}
        }

        for (int j = 0; j < 8; ++j){
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
        }

        if (!this.worldObj.isRemote){
            this.setDead();
        }
    }
}