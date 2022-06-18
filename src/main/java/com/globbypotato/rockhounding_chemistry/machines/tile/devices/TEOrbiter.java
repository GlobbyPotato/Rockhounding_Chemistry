package com.globbypotato.rockhounding_chemistry.machines.tile.devices;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.items.ProbeItems;
import com.globbypotato.rockhounding_chemistry.machines.recipe.OrbiterRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.OrbiterRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileStructure;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFluidCistern;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEWashingTank;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEOrbiter extends TileEntityInv {

	public static int upgradeSlots = 1;
	public static int templateSlots = 7;
	public static final int PROBE_SLOT = 0;

	public int xpCount = 0;
	public int xpCountMax = 20000;
	public int numLevel = 1;
	public boolean offScale;
	public boolean drainXP = false;
	
	public TEOrbiter() {
		super(0, 0, templateSlots, upgradeSlots);

		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			
			@Override
			public void validateSlotIndex(int slot){
				if(upgrade.getSlots() < upgradeSlots){
					NonNullList<ItemStack> stacksCloned = stacks;
					upgrade.setSize(upgradeSlots);
					for(ItemStack stack : stacksCloned){
		                stacks.set(slot, stack);
					}
				}
				super.validateSlotIndex(slot);
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == PROBE_SLOT && ModUtils.isOrbiterProbe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}

		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		this.xpCount = compound.getInteger("XPCount");
		this.numLevel = compound.getInteger("Levels");
		this.offScale = compound.getBoolean("Scale");
		this.drainXP = compound.getBoolean("Drain");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setInteger("XPCount", getXPCount());
		compound.setInteger("Levels", getLevels());
		compound.setBoolean("Scale", getOffScale());
		compound.setBoolean("Drain", canDrain());
        return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack probeSlot(){
		return this.upgrade.getStackInSlot(PROBE_SLOT);
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return 220;
	}

	public static String getName(){
		return "orbiter";
	}

	@Override
	public boolean isComparatorSensible(){
		return false;
	}

	public boolean canDrain() {
		return this.drainXP;
	}


	//----------------------- TANK HANDLER -----------------------
	public boolean wasteHasFluid(){
		return hasCistern() && getFluidCistern().getTankFluid() != null;
	}

	public FluidStack getWasteFluid(){
		return wasteHasFluid() ? getFluidCistern().getTankFluid() : null;
	}

	public int getWasteAmount() {
		return wasteHasFluid() ? getFluidCistern().getTankAmount() : 0;
	}

	private boolean hasWaste() {
		return wasteHasFluid() && getWasteAmount() >= wasteAmount();
	}

	private FluidStack waste() {
		return BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, wasteAmount());
	}

	private FluidStack sludge() {
		return BaseRecipes.getFluid(EnumFluid.TOXIC_SLUDGE, wasteAmount());
	}

	private int wasteAmount(){
		return ModConfig.wasteConsumed;
	}

	private int recycleChance(){
		return 1 + ModConfig.recycleChance;
	}



//fluid I/O
	public boolean canFillWaste(FluidStack fluid) {
		return fluid!= null && fluid.getFluid() != null 
			&& (fluid.isFluidEqual(waste()) || fluid.isFluidEqual(sludge()))
			&& this.input.canSetOrFillFluid(getFluidCistern().inputTank, getWasteFluid(), fluid);
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<OrbiterRecipe> recipeList(){
		return OrbiterRecipes.exp_recipes;
	}

	public static OrbiterRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
	}

	public FluidStack recipeJuice(){ return isValidPreset() ? getRecipeList(getSelectedRecipe()).getInput() : null; }



	//----------------------- CUSTOM -----------------------
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
	
	private int infusingFactor() {
		return ModConfig.infusingFactor;
	}

	public int getRadius(){
		return ProbeItems.orbiterUpgrade(probeSlot());
	}



	//----------------------- STRUCTURE -----------------------
//basin
	public TEWashingTank getBasin(){
		TEWashingTank basin = TileStructure.getWasher(this.world, this.pos.down());
		return basin != null ? basin : null;
	}

	public boolean hasWasher(){
		return getBasin() != null;
	}

//cistern
	public TEFluidCistern getFluidCistern(){
		BlockPos cisternPos = this.pos.offset(getFacing());
		TEFluidCistern cistern = TileStructure.getFluidCistern(this.world, cisternPos, getFacing().getOpposite());
		return cistern != null ? cistern : null;
	}

	public boolean hasCistern(){
		return getFluidCistern() != null;
	}

	
	
	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {

			if(this.isActive()){

				//try to acquire orbs
				List orbs = this.world.getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(new BlockPos(this.pos.getX() - getRadius(), this.pos.getY() - getRadius(), this.pos.getZ() - getRadius()), new BlockPos(this.pos.getX() + getRadius(), this.pos.getY() + getRadius(), this.pos.getZ() + getRadius())), EntitySelectors.IS_ALIVE);
				if(!orbs.isEmpty() && orbs.size() > 0) {
					for(int i = 0; i < orbs.size(); i++) {
						EntityXPOrb orb = (EntityXPOrb)orbs.get(i);
						if(canAcquireOrb()) {
							int currJuice = orb.getXpValue();
							acquireOrb(currJuice);
							orb.setDead();
						}
					}
				}

				//try to infuse xp to liquid
				if(isValidPreset()){
					if(canInfuseXp()){
						infuseXP();
					}
				}

			}
			this.markDirtyClient();
		}
	}

	private boolean canAcquireOrb() {
		return this.getXPCount() < getXPCountMax();
	}

	private void acquireOrb(int currJuice) {
		//handle waste bonus
		int luckyOrb = rand.nextInt(recycleChance());
		if(hasWaste() && luckyOrb == 0){
			this.input.drainOrCleanFluid(getFluidCistern().inputTank, wasteAmount(), true);
			currJuice *= 2;
		}
		//handle xp storage
		this.xpCount += currJuice;
		if(getXPCount() > getXPCountMax()) {this.xpCount = this.xpCountMax;}
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
			if(getXPCount() >= required){
				player.addExperience(required);
				this.xpCount -= required;
			}
			
			if(calculateRetrievedXP(player, 0) > getXPCount()){
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

	private boolean canInfuseXp() {
		return getXPCount() > 0 
			&& canDrain()
			&& isValidPreset()
			&& (hasWasher() && this.input.canSetOrAddFluid(getBasin().inputTank, getBasin().getTankFluid(), recipeJuice(), infusingFactor()));
	}

	private void infuseXP() {
		//turn xp into liquid xp
		if(recipeJuice() != null) {
			this.output.setOrFillFluid(getBasin().inputTank, recipeJuice(), infusingFactor());
			this.xpCount-- ;
		}
	}
}