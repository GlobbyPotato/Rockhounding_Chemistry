package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.SaltMaker;
import com.globbypotato.rockhounding_core.machines.tileentity.IFluidHandlingTile;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineBase;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

public class TileEntitySaltMaker extends TileEntityMachineBase implements ITickable, IFluidHandlingTile {
	private int evaporateCount = -1;
	private int meltingTime;
	boolean doParticles;

	public FluidTank inputTank;
	
	public TileEntitySaltMaker(){
		inputTank = new FluidTank(Fluid.BUCKET_VOLUME){
			@Override  
			public boolean canFillFluidType(FluidStack fluid){
		        return fluid.getFluid() == FluidRegistry.WATER;
		    }
		};
		inputTank.setTileEntity(this);
		inputTank.setCanDrain(false);
	}

	private int evaporationSpeed(){
		return ModConfig.evaporationBase * ModConfig.evaporationMultiplier;
	}

	@Override
	public void update() {
		if(!worldObj.isRemote){
			Biome biome = worldObj.getBiome(pos);

			//sync tank
			if(inputTank.getFluidAmount() >= Fluid.BUCKET_VOLUME){
				if(getStage() == 0){
					setNewStage(1);
					meltingTime = 0;
				}
			}

			//fill the tank on rain
			if(getStage() == 0){
				evaporateCount = -1;
				if(canRainRefill(biome)){
					inputTank.fillInternal(new FluidStack(FluidRegistry.WATER, 5), true);
					meltingTime = 0;
				}
			}

			//lose progress on rain
			if(getStage() != 0){
				if(canRainMelt(biome)){
					countdownLoss();
				}else{
					meltingTime = 0;
				}
			}

			//do evaporation
			if(getStage() > 0 && getStage() < 6){
				if(mustDesublimate()){
					evaporateCount = -1;
					setNewStage(6);
					worldObj.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 1.0F, 1.0F);
					doParticles = true;
					meltingTime = 0;
				}else{
					if(evaporateCount >= calculatedEvaporation(biome)){
						evaporateCount = -1;
						setNewStage(getStage() + 1);
						meltingTime = 0;
					}else{
						if(canEvaporate()){
							evaporateCount++;
							meltingTime = 0;
						}else{
							if(canRainRefill(biome)){
								countdownLoss();
							}else{
								meltingTime = 0;
							}
						}
					}
				}
			}
			this.markDirtyClient();
		}else{
			if(doParticles){
				worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, pos.getX(), pos.getY(), pos.getZ(), 0.5, 0.5, 0.5, new int[0]);
				doParticles = false;
			}
		}
	}

	private boolean mustDesublimate(){
		if(isValidDimension()){
			boolean desublibate = false;
			for(int x = 0; x < ModConfig.spacesaltdimensions.length; x++){
				if(worldObj.provider.getDimension() == ModConfig.spacesaltdimensions[x]){
					desublibate = true;
				}
			}
			return desublibate;
		}
		return false;
	}

	private void countdownLoss() {
		if(getStage() != 1){
			meltingTime++;
			if(meltingTime == ModConfig.meltingTime){
				evaporateCount = -1;
				setNewStage(1);
				meltingTime = 0;
			}
		}
	}

	private int getStage() {
		return worldObj.getBlockState(pos).getValue(this.getAgeProperty());
	}

	private void setNewStage(int i) {
		worldObj.setBlockState(pos, this.withStage(i));
	}

	private IBlockState withStage(int stage){
        return worldObj.getBlockState(pos).withProperty(this.getAgeProperty(), Integer.valueOf(stage));
    }

	private PropertyInteger getAgeProperty(){
        return SaltMaker.STAGE;
    }

	private boolean canEvaporate() {
		return worldObj.isDaytime() && worldObj.canBlockSeeSky(pos) && !worldObj.isRaining() && isValidDimension();
	}

	private boolean isValidDimension() {
		boolean isBacklisted = false;
		for(int x = 0; x < ModConfig.saltdimensions.length; x++){
			if(worldObj.provider.getDimension() == ModConfig.saltdimensions[x]){
				isBacklisted = true;
			}
		}
		return !isBacklisted;
	}

	private int calculatedEvaporation(Biome biome) {
		if(BiomeDictionary.isBiomeOfType(biome, Type.SANDY)){
			if(pos.getY() > evapLevel()){
				return evaporationSpeed() / 3;
			}else{
				return evaporationSpeed() / 2;
			}
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.WET) || BiomeDictionary.isBiomeOfType(biome, Type.WATER) || BiomeDictionary.isBiomeOfType(biome, Type.FOREST) || BiomeDictionary.isBiomeOfType(biome, Type.CONIFEROUS) || BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)){
			if(pos.getY() > evapLevel()){
				return evaporationSpeed();
			}else{
				return evaporationSpeed() * 2;
			}
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.COLD) || BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)){
			if(pos.getY() > evapLevel()){
				return evaporationSpeed() * 2;
			}else{
				return evaporationSpeed() * 4;
			}
		}else{
			if(pos.getY() > evapLevel()){
				return evaporationSpeed() / 2;
			}else{
				return evaporationSpeed();
			}
		}
	}

	private int evapLevel() {
		return 120;
	}

	private boolean canRainMelt(Biome biome) {
		return worldObj.isRaining() && !BiomeDictionary.isBiomeOfType(biome, Type.SANDY);
	}

	private boolean canRainRefill(Biome biome) {
		return worldObj.isRaining() && ModConfig.enableRainRefill && !BiomeDictionary.isBiomeOfType(biome, Type.SANDY);
	}

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.evaporateCount = compound.getInteger("EvaporateCount");
        this.meltingTime = compound.getInteger("MeltingCount");
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("EvaporateCount", this.evaporateCount);    
        compound.setInteger("MeltingCount", this.meltingTime);    
        
		NBTTagCompound sulfTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(sulfTankNBT);
		compound.setTag("InputTank", sulfTankNBT);

		return compound;
    }

	@Override
	public boolean interactWithBucket(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		boolean didFill = FluidUtil.interactWithFluidHandler(heldItem, this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side), player);
		this.markDirtyClient();
		return didFill;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return getStage() == 0;
		}
		return super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			if(getStage() == 0){
				return (T) inputTank;
			}
		}
		return super.getCapability(capability, facing);
	}

}