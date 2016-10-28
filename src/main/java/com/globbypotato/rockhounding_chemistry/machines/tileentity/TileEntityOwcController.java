/**
 * TODO: GUI not showing/syncing correctly rf integers/bar on server
 * TODO: Moon phases can't be obtained on server
 */

package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.OwcBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumOwc;
import com.globbypotato.rockhounding_chemistry.machines.OwcController;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TileEntityOwcController extends TileEntityOwcEnergyController {
	BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(); 

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public int getField(int id) {
        switch (id){
		    case 0: return this.powerCount;
		    case 1: return this.maxCapacity;
		    case 2: return this.yeldCount;
		    default:return 0;
        }
	}

	@Override
	public void setField(int id, int value) {
        switch (id){
	        case 0: this.powerCount = value; break;
	        case 1: this.maxCapacity = value; break;
	        case 2: this.yeldCount = value;
        }
	}

	@Override
	public void update() {
		boolean flag = false;
		if (!worldObj.isRemote){
			performSanityCheck();
	
			if(sanityCheckPassed()){
				this.maxCapacity = this.getMaxCapacity();
				if(checkConveyor()){ animateDeflectors(); }
				if(activationKey && powerCount < this.getMaxCapacity()){ acquireEnergy(); }
				flag = true;
				getDirty(flag);
			}
	
			if(extractionKey && this.hasPower()){ powerExtraction(); }
		}
	}

/**
 * Perform sanity check of the structure at different chances to don't hit the performance
 */
	private void performSanityCheck() {
		if(rand.nextInt(this.sanityCheckChance()) == 0){
			checkDevices();
			checkConduit();
		}
		if(rand.nextInt(this.sanityCheckChance() * 2) == 0){
			checkChamber();
			checkTower();
		}
		if(rand.nextInt(this.sanityCheckChance() * 4) == 0){
			checkTide();
		}
		if(rand.nextInt(this.sanityCheckChance() * 8) == 0){
			checkVolume();
		}
	}

			public boolean sanityCheckPassed(){
				return checkChamber() && checkTower() && checkDevices() && checkConduit() && checkVolume() && checkTide();
			}

			public int getMaxCapacity(){
				return this.storageMax() * this.dualityBonus();
			}

/**
 * Calculate the energy acquired by the structure
 */
	private void acquireEnergy() {
		tideInterval++;
		if(tideInterval >= maxTideInterval()) {
			tideInterval = 0;
			this.yeldCount = getYeld();
			this.powerCount += this.yeldCount;
			if(powerCount > this.maxCapacity){powerCount = this.maxCapacity;}
		}
	}

			private int maxTideInterval() {
				return tideChance() * conveyorMultiplier();
			}

		    public int actualWater(){
		    	return actualTide() + actualVolume();
		    }

	private int getYeld(){
		return accumulations() * efficiencyMultiplier();
	}

			public int accumulations(){			//	MAX		MIN
				return    biomeTicks() 			//	20		0
						+ moonTicks() 			//	30		5		to be reworked with moon phases
						+ weatherTicks() 		//	40		10
						+ conveyorTicks()  		//	30		10
						+ dualityTicks() 		//	20		10
						+ efficiencyTicks() 	//	30		10
						+ scaledVolumeForce()	//	50		1
						+ scaledTideForce();	//	30		1
												//	250		47
			}
					private int scaledVolumeForce() {
				        return this.actualVolume() > 0 && this.totalVolume() > 0 ? (this.actualVolume() * 49 / this.totalVolume()) + 1 : 1;
					}

					private int scaledTideForce() {
				        return this.actualTide() > 0 && this.totalTide() > 0 ? (this.actualTide() * 29 / this.totalTide()) + 1 : 1;
					}

					private int efficiencyTicks() {
						return 10 * this.efficiencyMultiplier();
					}

					private int dualityTicks() {
						return 10 * this.dualityBonus();
					}

					private int conveyorTicks() {
						return 15 * this.conveyorBonus();
					}

					private int weatherTicks() {
						return worldObj.isRaining() ? 40 : 10;
					}

					private int moonTicks() {
				    	//actually moon phases should be used here
						return !worldObj.isDaytime() ? 30 : 5;
					}

					private int biomeTicks() {
						Biome biome = worldObj.getBiomeGenForCoords(pos);
						if(BiomeDictionary.isBiomeOfType(biome, Type.OCEAN)){
							return 20;
						}else if(BiomeDictionary.isBiomeOfType(biome, Type.RIVER) || BiomeDictionary.isBiomeOfType(biome, Type.BEACH)){
							return 10;
						}else{
							return 0;
						}
					}

/**
 * Perform the sanity check of each upgrade and retrieve their effects for the structure
 */
	public boolean checkDuality(){
		checkPos.setPos(pos.getX(), pos.getY() + 3, pos.getZ());
		return isOwcStructure(checkPos, 6);
	}

			public int dualityBonus(){
				return checkDuality() ? 2 : 1;
			}

	public boolean checkConveyor(){
		return hasLowConveyor();
	}

			private boolean hasLowConveyor(){
				return hasConveyorLayer(getNewX(), pos.getY() - 2, getNewZ());
			}
			
			private boolean hasFullConveyor(){
				return hasConveyorLayer(getNewX(), pos.getY() - 3, getNewZ());
			}

			public int conveyorMultiplier(){
				if(hasLowConveyor() && hasFullConveyor()){
					return 1;
				}else{
					if(hasLowConveyor()){
						return 2;
					}
				}
				return 3;
			}

			public int conveyorBonus(){
				return 3 - conveyorMultiplier();
			}

			private boolean hasConveyorLayer(int newX, int newY, int newZ) {
				int deflectors = 0;
				checkPos.setPos(newX-1, newY, newZ-1); if( isAnyDeflectorType(checkPos) ){ deflectors++; }
				checkPos.setPos(newX+1, newY, newZ-1); if( isAnyDeflectorType(checkPos) ){ deflectors++; }
				checkPos.setPos(newX-1, newY, newZ+1); if( isAnyDeflectorType(checkPos) ){ deflectors++; }
				checkPos.setPos(newX+1, newY, newZ+1); if( isAnyDeflectorType(checkPos) ){ deflectors++; }
				return deflectors == 4;
			}

			//actually should try an on/off state animated block
			private void animateDeflectors() {
				if(conveyorMultiplier() < 3 && rand.nextInt(20) == 0){
					animateLayer(getNewX(), pos.getY() - 2, getNewZ());
				}
				if(conveyorMultiplier() == 1 && rand.nextInt(20) == 0){
					animateLayer(getNewX(), pos.getY() - 3, getNewZ());
				}
			}

					private void animateLayer(int newX, int newY, int newZ) {
						int deflector = 0;
						for (int movePart = 1; movePart <= 4; movePart++){
							if(movePart == 1){
								checkPos.setPos(newX-1, newY, newZ-1);
							}else if(movePart == 2){
								checkPos.setPos(newX+1, newY, newZ-1);
							}else if(movePart == 3){
								checkPos.setPos(newX-1, newY, newZ+1);
							}else if(movePart == 4){
								checkPos.setPos(newX+1, newY, newZ+1);
							}
							deflector = worldObj.getBlockState(checkPos).getBlock().getMetaFromState(worldObj.getBlockState(checkPos));
							swapDeflectors(deflector, checkPos);
						}
					}
	
					private void swapDeflectors(int deflector, MutableBlockPos samplePos) {
						if(isAnyDeflectorType(samplePos) && deflector < 11){
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(deflector + 1)));
						}else{
							worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(8)));
						}
					}

	public boolean checkEfficiency(){
		return anyEfficiency();
	}
	
			public boolean anyEfficiency(){
				BlockPos invPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
				return hasInverterOnSide(invPos, getFacing(), 90) || hasInverterOnSide(invPos, getFacing(), 270);
			}

			public boolean fullEfficiency(){
				BlockPos invPos = new BlockPos(pos.getX(), pos.getY() + 1, pos.getZ());
				return hasInverterOnSide(invPos, getFacing(), 90) && hasInverterOnSide(invPos, getFacing(), 270);
			}

			public int efficiencyMultiplier(){
				if(fullEfficiency()){
					return 3;
				}else{
					if(anyEfficiency()){
						return 2;
					}
				}
				return 1;
			}

			private boolean hasInverterOnSide(BlockPos checkPos, EnumFacing facing, int angle) {
				checkPos = checkPos.offset(facing.fromAngle(facing.getHorizontalAngle() + angle));
				return isOwcStructure(checkPos, 7);
			}

/**
 * Perform the sanity check of each part of the structure
 */
	public int actualTide() {
		int tide = 0;
		for (int y = -5; y <= -1; y++){
			for (int x = -3; x <= 3; x++){
				for (int z = -3; z <= 3; z++){
					checkPos.setPos(getNewX() + x, pos.getY() + y, getNewZ() + z);
					if(isWaterTile(checkPos)){
						tide++;
					}
				}
			}
		}
		return tide;
	}

			public boolean checkTide(){
				return actualTide() >= this.tideLimit();
			}

	public int actualVolume() {
		int volume = 0;
		for (int y = -9; y <= -1; y++){
			for (int x = -10; x <= 10; x++){
				for (int z = -10; z <= 10; z++){
					checkPos.setPos(getNewX() + x, pos.getY() + y, getNewZ() + z);
					if(isWaterTile(checkPos)){
						volume++;
					}
				}
			}
		}
		return volume;
	}

			public boolean checkVolume(){
				return actualVolume() >= this.volumeLimit();
			}

	public boolean checkConduit() {
		int conduit = 0; 
		checkPos.setPos(getNewX(), pos.getY()-1, getNewZ());   if(isWaterTile(checkPos)){ conduit++; }
		checkPos.setPos(getNewX(), pos.getY(), getNewZ());     if(isAir(checkPos)){ conduit++; }
		checkPos.setPos(getNewX(), pos.getY() + 1, getNewZ()); if(isAir(checkPos)){ conduit++; }
		return conduit == 3;
	}

	public boolean checkDevices() {
		int devices = 0;
		checkPos.setPos(getNewX(), pos.getY() + 2, getNewZ());   if(isOwcStructure(checkPos, 3)){ devices++; }
		checkPos.setPos(getNewX(), pos.getY() + 3, getNewZ());   if(isOwcStructure(checkPos, 2)){ devices++; }
		checkPos.setPos(getNewX(), pos.getY() + 4, getNewZ());   if(isOwcStructure(checkPos, 2)){ devices++; }
		checkPos.setPos(getNewX(), pos.getY() + 5, getNewZ());   if(isOwcStructure(checkPos, 4)){ devices++; }
		checkPos.setPos(pos.getX(), pos.getY() + 1, pos.getZ()); if(isOwcStructure(checkPos, 5)){ devices++; }
		checkPos.setPos(pos.getX(), pos.getY() + 2, pos.getZ()); if(isOwcStructure(checkPos, 6)){ devices++; }
		return devices == 6;
	}

	public boolean checkTower() {
		int concrete = 0;
		for (int y = 2; y <= 3; y++){
			for (int x = -1; x <= 1; x++){
				for (int z = -1; z <= 1; z++){
					checkPos.setPos(getNewX() + x, pos.getY() + y, getNewZ() + z);
					if(x != 0 || z != 0){
						if(isOwcStructure(checkPos, 1)){
							concrete++;
						}
					}
				}
			}
		}
		return concrete == 16;
	}

	public boolean checkChamber() {
		int bulkheads = 0;
		for (int y = -1; y <= 1; y++){
			for (int x = -1; x <= 1; x++){
				for (int z = -1; z <= 1; z++){
					checkPos.setPos(getNewX() + x, pos.getY() + y, getNewZ() + z);
					if(x != 0 || z != 0){
						if(isOwcStructure(checkPos, 0)){
							bulkheads++;
						}
					}
				}
			}
		}
		return bulkheads == 24;
	}

/**
 * Retrieve the facing of the structure and the coordinates of its centre
 */
	private EnumFacing getFacing() {
		return worldObj.getBlockState(pos).getValue(OwcController.FACING);
	}
	private int getNewX() {
		return pos.offset(getFacing(), 2).getX();
	}
	private int getNewZ() {
		return pos.offset(getFacing(), 2).getZ();
	}

/**
 * Retrieve the parts of the structure for check
 */
	private boolean isOwcStructure(MutableBlockPos checkPos, int i) {
		return worldObj.getBlockState(checkPos) != null && worldObj.getBlockState(checkPos) == ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(i));
	}
	private boolean isOwcStructure(BlockPos checkPos, int i) {
		return worldObj.getBlockState(checkPos) != null && worldObj.getBlockState(checkPos) == ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.byMetadata(i));
	}
	private boolean isWaterTile(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos) != null && worldObj.getBlockState(checkPos) == Blocks.WATER.getDefaultState(); 
	}
	private boolean isAir(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos).getBlock().isAir(worldObj.getBlockState(checkPos), worldObj, checkPos);
	}
	private boolean isAnyDeflectorType(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos) != null 
				&& worldObj.getBlockState(checkPos).getBlock() == ModBlocks.owcBlocks 
				&& (worldObj.getBlockState(checkPos).getBlock().getMetaFromState(worldObj.getBlockState(checkPos)) >= 8 && worldObj.getBlockState(checkPos).getBlock().getMetaFromState(worldObj.getBlockState(checkPos)) <= 11);
	}

/**
 * save when needed
 */
	private void getDirty(boolean flag) {
		if(flag){this.markDirty();}
	}
	

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.powerCount = compound.getInteger("PowerCount");
        this.maxCapacity = compound.getInteger("MaxCapacity");
        this.yeldCount = compound.getInteger("YeldCount");
        this.activationKey = compound.getBoolean("Activation");
        this.extractionKey = compound.getBoolean("Extraction");
        this.tideInterval = compound.getInteger("TideInterval");
        if (compound.hasKey("CustomName", 8)){
            this.inventoryName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("PowerCount", this.powerCount);
        compound.setInteger("MaxCapacity", this.maxCapacity);
        compound.setInteger("YeldCount", this.yeldCount);
        compound.setBoolean("Activation", this.activationKey);
        compound.setBoolean("Extraction", this.extractionKey);
        compound.setInteger("TideInterval", this.tideInterval);
        if (this.hasCustomName()){
            compound.setString("CustomName", this.inventoryName);
        }
        return compound;
    }

}