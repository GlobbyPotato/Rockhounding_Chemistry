package com.globbypotato.rockhounding_chemistry.entities;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityToxicSlime extends EntitySlime{
	public EntityToxicSlime(World worldIn) {
		super(worldIn);
        this.moveHelper = new ToxicSlimeMoveHelper(this);
	}

	@Override
	public void initEntityAI(){
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new AIToxicSlimeFloat(this));
        this.tasks.addTask(2, new AIToxicSlimeAttack(this));
        this.tasks.addTask(3, new AIToxicSlimeFaceRandom(this));
        this.tasks.addTask(5, new AIToxicSlimeHop(this));
        this.targetTasks.addTask(1, new EntityAIFindEntityNearestPlayer(this));
    }

	@Override
	public void setSlimeSize(int size, boolean resetHealth){
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.4D);
        this.setHealth(6F);
        this.experienceValue = 3;
    }

	@Override
	public void onCollideWithPlayer(EntityPlayer entityIn){
        if (this.canDamagePlayer() && !entityIn.capabilities.isCreativeMode){
            this.dealDamage(entityIn);
        	entityIn.addPotionEffect(new PotionEffect(MobEffects.POISON, 400));
        }
    }

	@Override
    public int getSlimeSize(){
    	return 1;
    }

    public static void registerFixesSlime(DataFixer fixer){
        EntityLiving.registerFixesMob(fixer, EntityToxicSlime.class);
    }

	@Override
	public boolean canDamagePlayer(){
    	return true;
    }

	@Override
	public int getAttackStrength(){
        return 3;
    }

	@Override
	public EntityToxicSlime createInstance(){
        return new EntityToxicSlime(this.world);
    }

	@Override
	public Item getDropItem(){
        return null;
    }

    @Nullable
    @Override
	public ResourceLocation getLootTable(){
        return EntityLootTables.TOXIC_SLIME;
    }

    @Override
    public boolean getCanSpawnHere(){
        BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
        if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL){
            if (this.world.getBlockState(blockpos).getBlock() == ModFluids.TOXIC_WASTE.getBlock()){
                return super.getCanSpawnHere();
            }
        }
        return false;
    }

    @Override
    public void jump(){
        this.motionY = 0.2D;
        this.isAirBorne = true;
    }

    @Override
	public int getJumpDelay(){
        return this.rand.nextInt(5) + 5;
    }



    static class AIToxicSlimeAttack extends EntityAIBase{
        private final EntityToxicSlime slime;
        private int growTieredTimer;

        public AIToxicSlimeAttack(EntityToxicSlime slimeIn){
            this.slime = slimeIn;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        @Override
        public boolean shouldExecute(){
            EntityLivingBase entitylivingbase = this.slime.getAttackTarget();

            if (entitylivingbase == null){
                return false;
            }else if (!entitylivingbase.isEntityAlive()){
                return false;
            }else{
                return !(entitylivingbase instanceof EntityPlayer) || !((EntityPlayer)entitylivingbase).capabilities.disableDamage;
            }
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        @Override
        public void startExecuting(){
            this.growTieredTimer = 100;
            super.startExecuting();
        }

        /**
         * Returns whether an in-progress EntityAIBase should continue executing
         */
        @Override
        public boolean shouldContinueExecuting(){
            EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
            if (entitylivingbase == null){
                return false;
            }else if (!entitylivingbase.isEntityAlive()){
                return false;
            }else if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage){
                return false;
            }else{
                return --this.growTieredTimer > 0;
            }
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask(){
            this.slime.faceEntity(this.slime.getAttackTarget(), 10.0F, 10.0F);
            ((ToxicSlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.canDamagePlayer());
        }
    }

    static class AIToxicSlimeFaceRandom extends EntityAIBase{
        private final EntityToxicSlime slime;
        private float chosenDegrees;
        private int nextRandomizeTime;

        public AIToxicSlimeFaceRandom(EntityToxicSlime slimeIn){
            this.slime = slimeIn;
            this.setMutexBits(2);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute(){
            return this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(MobEffects.LEVITATION));
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask(){
            if (--this.nextRandomizeTime <= 0){
                this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
                this.chosenDegrees = (float)this.slime.getRNG().nextInt(360);
            }
            ((ToxicSlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
        }
    }

    static class AIToxicSlimeFloat extends EntityAIBase{
        private final EntityToxicSlime slime;

        public AIToxicSlimeFloat(EntityToxicSlime slimeIn){
            this.slime = slimeIn;
            this.setMutexBits(5);
            ((PathNavigateGround)slimeIn.getNavigator()).setCanSwim(true);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute(){
            return this.slime.isInWater() || this.slime.isInLava();
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask(){
            if (this.slime.getRNG().nextFloat() < 0.8F){
                this.slime.getJumpHelper().setJumping();
            }
            ((ToxicSlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(2.0D);
        }
    }

    static class AIToxicSlimeHop extends EntityAIBase{
        private final EntityToxicSlime slime;

        public AIToxicSlimeHop(EntityToxicSlime slimeIn){
            this.slime = slimeIn;
            this.setMutexBits(5);
        }

        /**
         * Returns whether the EntityAIBase should begin execution.
         */
        public boolean shouldExecute(){
            return true;
        }

        /**
         * Keep ticking a continuous task that has already been started
         */
        @Override
        public void updateTask(){
            ((ToxicSlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.5D);
        }
    }

    static class ToxicSlimeMoveHelper extends EntityMoveHelper{
        private float yRot;
        private int jumpDelay;
        private final EntityToxicSlime slime;
        private boolean isAggressive;

        public ToxicSlimeMoveHelper(EntityToxicSlime slimeIn){
            super(slimeIn);
            this.slime = slimeIn;
            this.yRot = 180.0F * slimeIn.rotationYaw / (float)Math.PI;
        }

        public void setDirection(float p_179920_1_, boolean p_179920_2_){
            this.yRot = p_179920_1_;
            this.isAggressive = p_179920_2_;
        }

        public void setSpeed(double speedIn){
            this.speed = speedIn;
            this.action = EntityMoveHelper.Action.MOVE_TO;
        }

        @Override
        public void onUpdateMoveHelper(){
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
            this.entity.rotationYawHead = this.entity.rotationYaw;
            this.entity.renderYawOffset = this.entity.rotationYaw;

            if (this.action != EntityMoveHelper.Action.MOVE_TO){
                this.entity.setMoveForward(0.0F);
            }else{
                this.action = EntityMoveHelper.Action.WAIT;
                if (this.entity.onGround){
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

                    if (this.jumpDelay-- <= 0){
                        this.jumpDelay = this.slime.getJumpDelay();

                        if (this.isAggressive){
                            this.jumpDelay /= 3;
                        }

                        this.slime.getJumpHelper().setJumping();

                        if (this.slime.makesSoundOnJump()){
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
                        }
                    }else{
                        this.slime.moveStrafing = 0.0F;
                        this.slime.moveForward = 0.0F;
                        this.entity.setAIMoveSpeed(0.0F);
                    }
                }else{
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                }
            }
        }
    }

}