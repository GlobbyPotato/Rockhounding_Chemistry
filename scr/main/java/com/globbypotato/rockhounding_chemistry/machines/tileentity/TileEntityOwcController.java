package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.OwcBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumOwc;
import com.globbypotato.rockhounding_chemistry.machines.OwcController;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiOwcController;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityOwcController extends TileEntityMachineEnergy implements IEnergyProvider{
	public static final int ACQUIRE_SLOT = 0;
	public static final int EXTRACT_SLOT = 1;
	
	private ItemStackHandler template = new TemplateStackHandler(2);

    public boolean activationKey;
    public boolean extractionKey;
    private int tideInterval;

	BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(); 

	public TileEntityOwcController() {
		super(0, 0, 0);
		
		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiOwcController.HEIGHT;
	}

	@Override
	public boolean hasRF(){
		return false;	
	}

	@Override
	public boolean canInduct() {
		return false;
	}



	//----------------------- CUSTOM -----------------------
	public boolean isExtracting(){
		return extractionKey && this.hasRedstone();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
        this.tideInterval = compound.getInteger("TideInterval");
        this.activationKey = compound.getBoolean("Activation");
        this.extractionKey = compound.getBoolean("Extraction");
		this.yeldCount = compound.getInteger("YeldCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
        compound.setBoolean("Activation", this.activationKey);
        compound.setBoolean("Extraction", this.extractionKey);
        compound.setInteger("TideInterval", this.tideInterval);
		compound.setInteger("YeldCount", this.yeldCount);
		return compound;
	}

	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!worldObj.isRemote){
			performSanityCheck();
			if(redstoneCount > this.getChargeMax()){redstoneCount = this.getChargeMax();}

			if(activationKey){
				if(sanityCheckPassed()){
					animateDeflectors();
					if(this.getRedstone() < this.getChargeMax()){ 
						powerAcquiring();
					}
				}
			}else{
				stopDeflectors();
			}
			if(isExtracting()){
				provideEnergy();
			}
			this.markDirtyClient();
		}
	}

	//Perform maintenance
	private void performSanityCheck() {
		if(activationKey){
			if(rand.nextInt(this.sanityCheckChance() * 2) == 0){
				checkDevices();
				checkConduit();
			}
			if(rand.nextInt(this.sanityCheckChance() * 4) == 0){
				checkChamber();
				checkTower();
			}
			if(rand.nextInt(this.sanityCheckChance() * 6) == 0){
				checkTide();
			}
			if(rand.nextInt(this.sanityCheckChance() * 10) == 0){
				checkVolume();
			}
		}
	}

	public boolean sanityCheckPassed(){
		return checkChamber() && checkTower() && checkDevices() && checkConduit() && checkVolume() && checkTide();
	}

	private void animateDeflectors() {
		if(checkConveyor()){ 
			moveDeflectors(); 
		}
	}


// Acquire energy
	private void powerAcquiring() {
		tideInterval++;
		if(tideInterval >= maxTideInterval()) {
			tideInterval = 0;
			yeldCount = (accumulations() * efficiencyMultiplier()) + (rand.nextInt(36) + 5);
			redstoneCount += yeldCount;
		}
	}
	private int maxTideInterval() {
		return tideChance() * conveyorMultiplier();
	}



// Extract energy
	private void provideEnergy() {
		int energyExtracted = 0;
	    IBlockState state = worldObj.getBlockState(pos);
	    EnumFacing front = EnumFacing.getFront(getBlockMetadata());
		for(int side = 0; side < 6; side++){
			if(this.getRedstone() >= rfTransfer()){
			    EnumFacing facing = front.getFront(side);
				TileEntity checkTile = this.worldObj.getTileEntity(pos.offset(facing));
				if(checkTile != null){
					if(checkTile instanceof TileEntityMachineEnergy){
						TileEntityMachineEnergy rhTile = (TileEntityMachineEnergy)checkTile;
						if(rhTile.isRedstoneFilled() || rhTile.canRefillOnlyPower()){
							energyExtracted = Math.min(this.getChargeMax() - this.getRedstone(), rfTransfer());
						}else if(rhTile.redstoneIsRefillable()){
							energyExtracted = Math.min(this.getChargeMax() - this.getRedstone(), rfTransfer());
						}
						rhTile.receiveEnergy(facing, energyExtracted, false);
						this.redstoneCount -= energyExtracted;
					}else{
						if(checkTile instanceof IEnergyReceiver) {
							IEnergyReceiver te = (IEnergyReceiver) checkTile;
							if (te.getEnergyStored(facing) < te.getMaxEnergyStored(facing)) {
								energyExtracted = Math.min(this.getChargeMax() - this.getRedstone(), rfTransfer());
								te.receiveEnergy(facing.getOpposite(), energyExtracted, false);
								this.redstoneCount -= energyExtracted;
							}
						}else{
							if(checkTile.hasCapability(CapabilityEnergy.ENERGY, facing)){
								if(checkTile.getCapability(CapabilityEnergy.ENERGY, facing).getEnergyStored() < checkTile.getCapability(CapabilityEnergy.ENERGY, facing).getMaxEnergyStored()){
									energyExtracted = Math.min(this.getChargeMax() - this.getRedstone(), rfTransfer());
									checkTile.getCapability(CapabilityEnergy.ENERGY, facing).receiveEnergy(energyExtracted, false);
									this.redstoneCount -= energyExtracted;
								}
							}
						}
					}
				}
			}
		}
	}



// Calculate yeld
    @Override
	public int getChargeMax() { 
		return chargeMax * this.dualityBonus();
	}

	public int accumulations(){			//	MAX		MIN
		return    biomeTicks() 			//	20		0
				+ moonTicks() 			//	40		5
				+ weatherTicks() 		//	40		10
				+ conveyorTicks()  		//	30		10
				+ dualityTicks() 		//	20		10
				+ efficiencyTicks() 	//	30		10
				+ scaledVolumeForce()	//	50		1
				+ scaledTideForce();	//	30		1
			//  + random factor				40		5
										//	300		47
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
		int phase = 40 - (int) (((worldObj.getWorldTime()/24000)%8) * 5);
		return phase;
	}
	private int biomeTicks() {
		Biome biome = worldObj.getBiome(pos);
		if(BiomeDictionary.isBiomeOfType(biome, Type.OCEAN)){
			return 20;
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.RIVER) || BiomeDictionary.isBiomeOfType(biome, Type.BEACH)){
			return 10;
		}else{
			return 0;
		}
	}



// Perform Update check
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
	private void moveDeflectors() {
		if(hasLowConveyor()){
			animateLayer(getNewX(), pos.getY() - 2, getNewZ());
		}else{
			stopLayer(getNewX(), pos.getY() - 2, getNewZ());
		}
		if(hasFullConveyor()){
			animateLayer(getNewX(), pos.getY() - 3, getNewZ());
		}else{
			stopLayer(getNewX(), pos.getY() - 3, getNewZ());
		}
	}
	private void animateLayer(int newX, int newY, int newZ) {
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
			swapDeflectors(checkPos);
		}
	}
	private void swapDeflectors(MutableBlockPos samplePos) {
		if(deflectorIsHalted(samplePos)){
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[9]));
		}
	}
	private void stopDeflectors() {
		stopLayer(getNewX(), pos.getY() - 2, getNewZ());
		stopLayer(getNewX(), pos.getY() - 3, getNewZ());
	}
	private void stopLayer(int newX, int newY, int newZ) {
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
			haltDeflectors(checkPos);
		}
	}
	private void haltDeflectors(MutableBlockPos samplePos) {
		if(deflectorIsMoving(samplePos)){
			worldObj.setBlockState(samplePos, ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[8]));
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

	public boolean checkDuality(){
		checkPos.setPos(pos.getX(), pos.getY() + 3, pos.getZ());
		return isOwcStructure(checkPos, 6);
	}
	public int dualityBonus(){
		return checkDuality() ? 2 : 1;
	}



// Perform sanity check
	public boolean checkDevices() {
		int devices = 0;
		checkPos.setPos(getNewX(),  pos.getY() + 2, getNewZ());  if(isOwcStructure(checkPos, 3)){ devices++; }
		checkPos.setPos(getNewX(),  pos.getY() + 3, getNewZ());  if(isOwcStructure(checkPos, 2)){ devices++; }
		checkPos.setPos(getNewX(),  pos.getY() + 4, getNewZ());  if(isOwcStructure(checkPos, 2)){ devices++; }
		checkPos.setPos(getNewX(),  pos.getY() + 5, getNewZ());  if(isOwcStructure(checkPos, 4)){ devices++; }
		checkPos.setPos(pos.getX(), pos.getY() + 1, pos.getZ()); if(isOwcStructure(checkPos, 5)){ devices++; }
		checkPos.setPos(pos.getX(), pos.getY() + 2, pos.getZ()); if(isOwcStructure(checkPos, 6)){ devices++; }
		return devices == 6;
	}
	public boolean checkConduit() {
		int conduit = 0; 
		checkPos.setPos(getNewX(), pos.getY() - 1, getNewZ()); if(isWaterTile(checkPos)){ conduit++; }
		checkPos.setPos(getNewX(), pos.getY(), 	   getNewZ()); if(isAir(checkPos)){ conduit++; }
		checkPos.setPos(getNewX(), pos.getY() + 1, getNewZ()); if(isAir(checkPos)){ conduit++; }
		return conduit == 3;
	}
	public boolean checkChamber() {
		int bulkheads = 0;
		for(int y = -1; y <= 1; y++){
			for(int x = -1; x <= 1; x++){
				for(int z = -1; z <= 1; z++){
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
	public boolean checkTower() {
		int concrete = 0;
		for(int y = 2; y <= 3; y++){
			for(int x = -1; x <= 1; x++){
				for(int z = -1; z <= 1; z++){
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



// Retrieve the parts of the structure for check
	private boolean isOwcStructure(MutableBlockPos checkPos, int i) {
		return worldObj.getBlockState(checkPos) == ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[i]);
	}
	private boolean isOwcStructure(BlockPos checkPos, int i) {
		return worldObj.getBlockState(checkPos) == ModBlocks.owcBlocks.getDefaultState().withProperty(OwcBlocks.VARIANT, EnumOwc.values()[i]);
	}
	private boolean isWaterTile(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos) == Blocks.WATER.getDefaultState(); 
	}
	private boolean isAir(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos).getBlock().isAir(worldObj.getBlockState(checkPos), worldObj, checkPos);
	}
	private boolean isAnyDeflectorType(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos).getBlock() == ModBlocks.owcBlocks 
			&& (getVariant(checkPos) == EnumOwc.DEFLECTOR_OFF || getVariant(checkPos) == EnumOwc.DEFLECTOR_ON);
	}
	private boolean deflectorIsHalted(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos).getBlock() == ModBlocks.owcBlocks 
			&& getVariant(checkPos) == EnumOwc.DEFLECTOR_OFF;
	}
	private boolean deflectorIsMoving(MutableBlockPos checkPos) {
		return worldObj.getBlockState(checkPos).getBlock() == ModBlocks.owcBlocks 
			&& getVariant(checkPos) == EnumOwc.DEFLECTOR_ON;
	}

	private EnumOwc getVariant(MutableBlockPos checkPos){
		return (EnumOwc)worldObj.getBlockState(checkPos).getValue(OwcBlocks.VARIANT);
	}


// Retrieve the facing of the structure and the coordinates of its centre
	private EnumFacing getFacing() {
		return worldObj.getBlockState(pos).getValue(OwcController.FACING);
	}
	private int getNewX() {
		return pos.offset(getFacing(), 2).getX();
	}
	private int getNewZ() {
		return pos.offset(getFacing(), 2).getZ();
	}
	
	
	
// Constants
	public int sanityCheckChance(){
    	return 60;
    }

    public int tideLimit(){
    	return 90;
    }
    
    public int volumeLimit(){
    	return 1000;
    }

    public int actualWater(){
    	return actualTide() + actualVolume();
    }

    public int totalWater(){
    	return totalTide() + totalVolume();
    }

    public int totalTide(){
    	return 240;
    }

    public int totalVolume(){
    	return 3960;
    }

    public int tideChance(){
    	return 120;
    }



	//---------------- COFH ----------------
	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return getChargeMax();
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		return 0;
	}



	//---------------- FORGE ----------------
	@Override
	public int getMaxEnergyStored() {
		return getChargeMax();
	}

	@Override
	public boolean canExtract() {
		return isExtracting();
	}

	@Override
	public boolean canReceive() {
		return false;
	}

	@Override
	public int receiveEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

}