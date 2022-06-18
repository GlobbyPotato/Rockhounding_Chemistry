package com.globbypotato.rockhounding_chemistry.machines.tile.utilities;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumSaltStages;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;

import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;

public class TEEvaporationTank extends TileEntityTank {

	public FluidTank inputTank;
	public int stage;
	private int meltingTime;
	public int purge = finalStage();
	
	public TEEvaporationTank() {
		super(0, 1, 0, 0);

		this.inputTank = new FluidTank(getTankCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return handleFilling(fluid);
			}

			@Override
			public boolean canDrain() {
				return isAllowedStage();
			}
		};
		this.inputTank.setTileEntity(this);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.stage = compound.getInteger("Stage");
        this.meltingTime = compound.getInteger("MeltingCount");
		this.purge = compound.getInteger("Purge");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);
        compound.setInteger("MeltingCount", this.meltingTime);    
		compound.setInteger("Stage", getStage());
		compound.setInteger("Purge", getPurge());
        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank);
	}



	// ----------------------- SLOTS -----------------------
	public ItemStack outputSlot() {
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "evaporation_tank";
	}

	public int evaporationSpeed(){
		return ModConfig.speedEvaporation;
	}



	//----------------------- TANK HANDLER -----------------------
	public int getTankCapacity() {
		return 5000;
	}

	public boolean hasTankFluid(){
		return getTankFluid() != null;
	}

	public FluidStack getTankFluid(){
		return this.inputTank.getFluid();
	}

	public int getTankAmount() {
		return hasTankFluid() ? this.inputTank.getFluidAmount() : 0;
	}

	public boolean isTankEmpty(){
		return getTankAmount() == 0;
	}

	private boolean isFullTank() {
		return hasTankFluid() && getTankAmount() == getTankCapacity();
	}

	boolean handleFilling(FluidStack fluid) {
		return getStage() == 0 && fluid.isFluidEqual(new FluidStack(FluidRegistry.WATER, 1000));
	}

	public boolean isAllowedStage() {
		return getStage() == getPurge() && getPurge() < this.finalStage();
	}



	//----------------------- CUSTOM -----------------------
	public int getStage() {
		return this.stage;
	}

	public int getPurge() {
		return this.purge;
	}

	private int evapLevel() {
		return 120;
	}

	public int initialStage() {
		return EnumSaltStages.STAGE_A.ordinal();
	}

	public int denseStage() {
		return EnumSaltStages.STAGE_D.ordinal();
	}

	public int finalStage() {
		return EnumSaltStages.STAGE_G.ordinal();
	}

	private int calculatedEvaporation(Biome biome){
		if(BiomeDictionary.hasType(biome, Type.SANDY)){
			if(this.pos.getY() > evapLevel()){
				return evaporationSpeed() / 3;
			}else{
				return evaporationSpeed() / 2;
			}
		}else if(BiomeDictionary.hasType(biome, Type.WET) || BiomeDictionary.hasType(biome, Type.WATER) || BiomeDictionary.hasType(biome, Type.FOREST) || BiomeDictionary.hasType(biome, Type.CONIFEROUS) || BiomeDictionary.hasType(biome, Type.SWAMP)){
			if(pos.getY() > evapLevel()){
				return evaporationSpeed();
			}else{
				return evaporationSpeed() * 2;
			}
		}else if(BiomeDictionary.hasType(biome, Type.COLD) || BiomeDictionary.hasType(biome, Type.MOUNTAIN)){
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

	private boolean isValidDimension() {
		boolean isBacklisted = false;
		for(int x = 0; x < ModConfig.salt_dim_blacklist.length; x++){
			if(this.world.provider.getDimension() == ModConfig.salt_dim_blacklist[x]){
				isBacklisted = true;
			}
		}
		return !isBacklisted;
	}

	private boolean mustDesublimate(){
		if(isValidDimension()){
			boolean desublibate = false;
			for(int x = 0; x < ModConfig.salt_dim_space.length; x++){
				if(this.world.provider.getDimension() == ModConfig.salt_dim_space[x]){
					desublibate = true;
				}
			}
			return desublibate;
		}
		return false;
	}

	private boolean canEvaporate() {
		return !this.world.isRaining() && this.world.isDaytime() && this.world.canBlockSeeSky(this.pos) && isValidDimension() && hasEnoughFluid();
	}

	private boolean hasEnoughFluid() {
		return getTankAmount() >= EnumSaltStages.getStageYeld(getStage());
	}

	private boolean canFillFromRain(Biome biome) {
		return this.world.canBlockSeeSky(this.pos) && this.world.isRaining() && !biome.getEnableSnow() && biome.getRainfall() >= 0.2F;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			Biome biome = this.world.getBiome(this.pos);

			//fill with rain
			//melt into water
			if(canFillFromRain(biome)){
				if(getStage() == initialStage()){
					this.inputTank.fillInternal(new FluidStack(FluidRegistry.WATER, 5), true);
					if(this.meltingTime != 0){this.meltingTime = 0;}
				}else{
					this.meltingTime++;
					if(this.meltingTime >= ModConfig.meltingTime){
						this.stage = initialStage();
						this.inputTank.setFluid(new FluidStack(FluidRegistry.WATER, 5000));
						this.meltingTime = 0;
					}
				}
			}

			//desublimate
			//evaporate
			if(mustDesublimate()){
				if((getStage() == 0 && getTankAmount() >= 1000)){
					this.cooktime = 0;
					this.stage = finalStage();
					this.world.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.AMBIENT, 1.0F, 1.0F);
					setPoorSalt();
					this.meltingTime = 0;
				}
			}else{
				if(canProcess()){
					this.cooktime++;
					this.meltingTime = 0;
					if(getCooktime() >= calculatedEvaporation(biome)) {
						this.cooktime = 0;
						process();
					}
				}else{
					this.cooktime = 0;
				}
			}

			//reset tank
			if(getStage() == finalStage()){
				if(outputSlot().isEmpty()){
					this.stage = initialStage();
				}
			}else{
				if(getTankAmount() == 0){
					this.stage = initialStage();
				}
			}
			
			this.markDirtyClient();
		}
	}

	private void setRawSalt() {
		this.inputTank.setFluid(null);
		this.output.setStackInSlot(OUTPUT_SLOT, new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.RAW_SALT.ordinal()));
	}

	private void setPoorSalt() {
		this.inputTank.setFluid(null);
		this.output.setStackInSlot(OUTPUT_SLOT, new ItemStack(ModBlocks.MISC_BLOCKS_A, 1, EnumMiscBlocksA.POOR_RAW_SALT.ordinal()));
	}

	private boolean canProcess() {
		return getStage() < finalStage()
			&& ((getStage() > 0 && hasTankFluid()) || (getStage() == 0 && isFullTank()))
			&& canEvaporate();
	}

	private void process() {
		if(getStage() == initialStage()){
			this.inputTank.setFluid(BaseRecipes.getFluid(EnumFluid.VIRGIN_WATER, EnumSaltStages.getStageYeld(EnumSaltStages.STAGE_B.ordinal())));
		}else if(getStage() == EnumSaltStages.STAGE_B.ordinal()){
			this.inputTank.setFluid(BaseRecipes.getFluid(EnumFluid.SALT_BRINE, EnumSaltStages.getStageYeld(EnumSaltStages.STAGE_C.ordinal())));
		}else if(getStage() == EnumSaltStages.STAGE_C.ordinal()){
			this.inputTank.setFluid(BaseRecipes.getFluid(EnumFluid.SALT_BRINE, EnumSaltStages.getStageYeld(EnumSaltStages.STAGE_D.ordinal())));
		}else if(getStage() == EnumSaltStages.STAGE_D.ordinal()){
			this.inputTank.setFluid(BaseRecipes.getFluid(EnumFluid.DENSE_BRINE, EnumSaltStages.getStageYeld(EnumSaltStages.STAGE_E.ordinal())));
		}else if(getStage() == EnumSaltStages.STAGE_E.ordinal()){
			this.inputTank.setFluid(BaseRecipes.getFluid(EnumFluid.MOTHER_LIQUOR, EnumSaltStages.getStageYeld(EnumSaltStages.STAGE_F.ordinal())));
		}else if(getStage() == EnumSaltStages.STAGE_F.ordinal()){
			setRawSalt();
		}

		if(getStage() < finalStage()){
			this.stage++;
		}
	}

	
}