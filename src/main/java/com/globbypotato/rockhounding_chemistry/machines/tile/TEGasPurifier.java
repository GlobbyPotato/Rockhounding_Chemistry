package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasPurifierRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasPurifierRecipe;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEGasPurifier extends TileEntityInv {

	public static final int SPEED_SLOT = 0;

	public static int templateSlots = 1;
	public static int upgradeSlots = 1;

	public TEGasPurifier() {
		super(0, 0, templateSlots, upgradeSlots);
		
		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
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
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
        return compound;
	}



	// ----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	// ----------------------- RECIPE -----------------------
	public static ArrayList<GasPurifierRecipe> recipeList(){
		return GasPurifierRecipes.gas_purifier_recipes;
	}

	public static GasPurifierRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public GasPurifierRecipe getCurrentRecipe(){
		for(int x = 0; x < recipeList().size(); x++){
			if(isMatchingInput(x) && isRecipeGaseous(x)){
				return getRecipeList(x);
			}
		}
		return null;
	}

	private boolean isMatchingInput(int x) {
		return getRecipeList(x).getInput() != null && hasInTank() && getInTank().inputTank.getFluid() != null && getRecipeList(x).getInput().isFluidEqual(getInTank().inputTank.getFluid());
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public FluidStack getRecipeInput(){ return isValidRecipe() ? getCurrentRecipe().getInput() : null; }
	public FluidStack getRecipeOutput(){ return isValidRecipe() ? getCurrentRecipe().getOutput() : null; }

	public ItemStack getMainSlag(){ return isValidRecipe() ? getCurrentRecipe().getMainSlag() : ItemStack.EMPTY; }
	private boolean hasMainSlag(){ return !getMainSlag().isEmpty(); }

	public ItemStack getAltSlag(){ return isValidRecipe() ? getCurrentRecipe().getAltSlag() : ItemStack.EMPTY; }
	private boolean hasAltSlag(){ return !getAltSlag().isEmpty(); }

	public boolean isRecipeGaseous(int x){
		return getRecipeList(x).getInput().getFluid().isGaseous() 
			&& getRecipeList(x).getOutput().getFluid().isGaseous();
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gas_purifier";
	}

	public int getCooktimeMax(){
		return 30;
	}

	@Override
	public BlockPos poweredPosition(){
		return cycloneTopCap().offset(poweredFacing());
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 270);
	}



	//----------------------- CUSTOM -----------------------
	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int cleanRate(){
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? baseRate() * ModUtils.speedUpgrade(speedSlot()): baseRate();
	}

	private int baseRate() {
		return 50;
	}

	public int effectiveRate(){
		if(hasInTank()){
			if(getInTank().inputTank.getFluidAmount() >= cleanRate()){
				return cleanRate();
			}else{
				return getInTank().inputTank.getFluidAmount();
			}
		}
		return 0;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, isFacingAt(270), 1, 0);
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 : false;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().markDirtyClient();
	}

//cyclone base
	public BlockPos cycloneBasePos(){
		return this.pos.offset(EnumFacing.UP, 1);		
	}

	public TEPurifierCycloneBase getCycloneBase(){
		TileEntity te = this.world.getTileEntity(cycloneBasePos());
		if(this.world.getBlockState(cycloneBasePos()) != null && te instanceof TEPurifierCycloneBase){
			TEPurifierCycloneBase chamber = (TEPurifierCycloneBase)te;
			if(chamber.getFacing() == getFacing()){
				return chamber;
			}
		}
		return null;
	}

	public boolean hasCycloneBase(){
		return getCycloneBase() != null;
	}

//cyclone cap
	public BlockPos cycloneTopCap(){
		return this.pos.offset(EnumFacing.UP, 3);		
	}

	public TEPurifierCycloneCap getCycloneCap(){
		TileEntity te = this.world.getTileEntity(cycloneTopCap());
		if(this.world.getBlockState(cycloneTopCap()) != null && te instanceof TEPurifierCycloneCap){
			TEPurifierCycloneCap chamber = (TEPurifierCycloneCap)te;
			if(chamber.getFacing() == getFacing()){
				return chamber;
			}
		}
		return null;
	}

	public boolean hasCycloneCap(){
		return getCycloneCap() != null;
	}

	public boolean hasCycloneSeparator(){
		return hasCycloneBase() && hasCycloneCap();
	}

//pressurizer
	public TEGasPressurizer getPressurizer(){
		BlockPos chillerPos = this.pos.offset(EnumFacing.UP, 3);
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, chillerPos, isFacingAt(90), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizer(){
		return getPressurizer() != null;
	}

//input vessel
	public TileVessel getInTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, isFacingAt(270), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasInTank(){
		return getInTank() != null;
	}

//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, cycloneBasePos(), getFacing(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}

//particulate
	public TEParticulateCollector getParticulate(){
		TEParticulateCollector vessel = TileStructure.getCollector(this.world, this.pos, getFacing(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasParticulate(){
		return getParticulate() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if (!this.world.isRemote) {
			doPreset();

			if(hasParticulate()){
				getParticulate().handlePreview(hasMainSlag(), getMainSlag(), hasAltSlag(), getAltSlag());
			}

			if(isActive()){
				if(canProcess()){
					this.cooktime++;
					drainPower();
					process();
					if(getCooktime() >= getCooktimeMax()) {
						this.cooktime = 0;
					}
					this.markDirtyClient();
				}
			}else{
				tickOff();
			}
		}
	}

	private void doPreset() {
		if(hasEngine()){
			if(!getEngine().enablePower){
				getEngine().enablePower = true;
				getEngine().markDirtyClient();
			}
			if(getEngine().enableRedstone){
				getEngine().enableRedstone = false;
				getEngine().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		return isValidRecipe()
			&& hasFuelPower()
			&& hasCycloneSeparator() 
			&& hasPressurizer()
			&& hasOutTank()
			&& isMatchingInput()
			&& isMatchingOutput();
	}

	private boolean isMatchingInput() {
		return hasInTank()
			&& this.input.canDrainFluid(getInTank().inputTank.getFluid(), getRecipeInput(), effectiveRate());
	}

	private boolean isMatchingOutput() {
		return hasOutTank()
			&& this.output.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), effectiveRate());
	}

	private void process() {	
		this.output.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), effectiveRate());
		this.input.drainOrCleanFluid(getInTank().inputTank, effectiveRate(), true);

		if(hasParticulate()){
			if(isValidRecipe()){
				getParticulate().handleParticulate(hasMainSlag(), ModConfig.purifier_main_slag, hasAltSlag(), ModConfig.purifier_secondary_slag, speedFactor());
			}
		}
	}
}