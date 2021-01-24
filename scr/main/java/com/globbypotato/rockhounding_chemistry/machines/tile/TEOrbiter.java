package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.items.ProbeItems;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityTank;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.templates.FluidHandlerConcatenate;
import net.minecraftforge.fml.common.Loader;

public class TEOrbiter extends TileEntityTank {

	public static int inputSlots = 2;
	public static int templateSlots = 4;

	public FluidTank inputTank;
	public FluidTank outputTank;
	public static int DRAIN_BUCKET = 1;
	public int xpCount = 0;
	public int xpCountMax = 20000;
	public int numLevel = 1;
	public boolean offScale;
	
	public TEOrbiter() {
		super(inputSlots, 0, templateSlots, 0);

		this.inputTank = new FluidTank(getWasteCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillWaste(fluid);
			}
		};
		this.inputTank.setCanDrain(false);
		this.inputTank.setTileEntity(this);

		this.outputTank = new FluidTank(getJuiceCapacity()) {
			@Override
			public boolean canFillFluidType(FluidStack fluid) {
				return canFillJuice(fluid);
			}
			@Override
			public boolean canDrainFluidType(FluidStack fluid) {
				return canDrainJuice(fluid);
			}
		};
		this.outputTank.setTileEntity(this);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && ModUtils.isOrbiterProbe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if (slot == DRAIN_BUCKET && CoreUtils.isEmptyBucket(insertingStack)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		this.markDirtyClient();
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.inputTank.readFromNBT(compound.getCompoundTag("InputTank"));
		this.outputTank.readFromNBT(compound.getCompoundTag("OutputTank"));
		this.xpCount = compound.getInteger("XPCount");
		this.numLevel = compound.getInteger("Levels");
		this.offScale = compound.getBoolean("Scale");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		NBTTagCompound inputTankNBT = new NBTTagCompound();
		this.inputTank.writeToNBT(inputTankNBT);
		compound.setTag("InputTank", inputTankNBT);

		NBTTagCompound outputTankNBT = new NBTTagCompound();
		this.outputTank.writeToNBT(outputTankNBT);
		compound.setTag("OutputTank", outputTankNBT);

		compound.setInteger("XPCount", getXPCount());
		compound.setInteger("Levels", getLevels());
		compound.setBoolean("Scale", getOffScale());
        return compound;
	}

	@Override
	public FluidHandlerConcatenate getCombinedTank() {
		return new FluidHandlerConcatenate(this.inputTank, this.outputTank);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack probeSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack drainSlot(){
		return this.input.getStackInSlot(DRAIN_BUCKET);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "orbiter";
	}

	@Override
	public boolean isComparatorSensible(){
		return false;
	}



	//----------------------- TANK HANDLER -----------------------
	public boolean wasteHasFluid(){
		return this.inputTank.getFluid() != null;
	}

	public FluidStack getWasteFluid(){
		return wasteHasFluid() ? this.inputTank.getFluid() : null;
	}

	public int getWasteAmount() {
		return wasteHasFluid() ? this.inputTank.getFluidAmount() : 0;
	}

	public boolean juiceHasFluid(){
		return this.outputTank.getFluid() != null;
	}

	public FluidStack getJuiceFluid(){
		return juiceHasFluid() ? this.outputTank.getFluid() : null;
	}

	public int getJuiceAmount() {
		return juiceHasFluid() ? this.outputTank.getFluidAmount() : 0;
	}

	private boolean hasWaste() {
		return wasteHasFluid() && getWasteAmount() >= wasteAmount();
	}

	private FluidStack waste() {
		return BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 5);
	}

	private int wasteAmount(){
		return 5;
	}

//fluid I/O
	public boolean canFillWaste(FluidStack fluid) {
		return fluid!= null && fluid.getFluid() != null 
			&& fluid.isFluidEqual(waste())
			&& this.input.canSetOrFillFluid(this.inputTank, getWasteFluid(), fluid);
	}

	public boolean xpJuiceExists(){
		return Loader.isModLoaded(ModUtils.openblocks_id) && ModUtils.liquidXP() != null;
	}

	public boolean canFillJuice(FluidStack fluid) {
		return xpJuiceExists()
			&& fluid!= null && fluid.getFluid() != null 
			&& fluid.isFluidEqual(ModUtils.liquidXP())
			&& this.input.canSetOrFillFluid(this.outputTank, getJuiceFluid(), fluid);
	}

	private boolean canDrainJuice(FluidStack fluid) {
		return xpJuiceExists()
			&& fluid!= null && fluid.getFluid() != null 
			&& fluid.isFluidEqual(ModUtils.liquidXP());
	}



	//----------------------- CUSTOM -----------------------
	public int getJuiceCapacity() {
		return 400000;
	}

	public int getWasteCapacity() {
		return 1000;
	}

	public int getXPCount() {
		return this.xpCount > getXPCountMax() ? getXPCountMax() : this.xpCount;
	}

	public int getXPCountMax() {
		return this.xpCountMax;
	}

	public int getLevels(){
		return this.numLevel;
	}

	public boolean getOffScale(){
		return this.offScale;
	}
	
	public int getRadius(){
		return ProbeItems.orbiterUpgrade(probeSlot());
	}

	public int getstoredXP(){
		return xpJuiceExists() && juiceHasFluid() && getJuiceAmount() > 0 ? getJuiceAmount() / 20 : this.getXPCount(); 
	}

	public int getstoredXPMax(){
		return xpJuiceExists() ? getJuiceCapacity() : this.getXPCountMax(); 
	}

	private boolean canAbsorb(int currJuice) {
		return getstoredXP() <= getstoredXPMax() - currJuice;
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			fillContainer(DRAIN_BUCKET, this.outputTank);

			if(this.isActive()){
				List orbs = this.world.getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(new BlockPos(this.pos.getX() - getRadius(), this.pos.getY() - getRadius(), this.pos.getZ() - getRadius()), new BlockPos(this.pos.getX() + getRadius(), this.pos.getY() + getRadius(), this.pos.getZ() + getRadius())), EntitySelectors.IS_ALIVE);
				if(!orbs.isEmpty() && orbs.size() > 0) {
					for(int i = 0; i < orbs.size(); i++) {
						EntityXPOrb orb = (EntityXPOrb)orbs.get(i);
						int currJuice = orb.getXpValue();
						int luckyOrb = rand.nextInt(2);

						//handle waste modifier
						if(hasWaste() && luckyOrb == 0){
							currJuice *= 2;
						}

						if(canAbsorb(currJuice)){
							//handle waste bonus
							if(luckyOrb == 0){
								if(this.input.canDrainFluid(getWasteFluid(), waste())){
									this.input.drainOrCleanFluid(inputTank, wasteAmount(), true);
								}
							}

							//handle xp storage
							this.xpCount += currJuice;
							int xpJuice = 20 * currJuice;
							if(xpJuiceExists()){
								if(this.output.canSetOrAddFluid(outputTank, getJuiceFluid(), ModUtils.liquidXP(), xpJuice)){
									this.output.setOrFillFluid(outputTank, ModUtils.liquidXP(), xpJuice);
									this.xpCount -= currJuice;
								}
							}
							orb.setDead();
						}
					}
				}
			}

			//adding openblocks
			if(xpJuiceExists() && getXPCount() > 0){
				for(int x = 0; x < getXPCount(); x++){
					if(this.output.canSetOrAddFluid(outputTank, getJuiceFluid(), ModUtils.liquidXP(), 20)){
						this.output.setOrFillFluid(outputTank, ModUtils.liquidXP(), 20);
						this.xpCount--;
						this.markDirtyClient();
					}
				}
			}

			this.markDirtyClient();

		}
	}


	public int calculateRetrievedXP(EntityPlayer player, int offset){
	    int exp = 0;
		if(!player.getEntityWorld().isRemote){
		    for (int i = player.experienceLevel; i < player.experienceLevel + getLevels() + offset; i++) {
		    	exp += xpBarCap(i);
		    }
		}
		return exp;
	}

	public void retrieveXP(EntityPlayer player){
		if(!player.getEntityWorld().isRemote){
			int required = calculateRetrievedXP(player, 0);
			int xpJuice = required * 20;
			if(xpJuiceExists()){
				if(juiceHasFluid() && getJuiceAmount() >= xpJuice){
					player.addExperience(required);
					this.output.drainOrCleanFluid(outputTank, xpJuice, true);
				}
			}else{
				if(getXPCount() >= required){
					player.addExperience(required);
					this.xpCount -= required;
				}
			}
			
			if(calculateRetrievedXP(player, 0) > getstoredXP()){
				this.offScale = true;
			}else{
				this.offScale = false;
			}
		}
	}

    public int xpBarCap(int levels){
        if (levels >= 30){
            return 112 + (levels - 30) * 9;
        }else{
            return levels >= 15 ? 37 + (levels - 15) * 5 : 7 + levels * 2;
        }
    }

}