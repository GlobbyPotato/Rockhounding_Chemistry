package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumAirGases;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GanPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEAuxiliaryEngine;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGanExpanderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEMultivessel;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;

public class TEGanController extends TileEntityInv {

	public boolean enableN;
	public boolean enableO;
	public boolean enableX;
	public int rareGases[] = new int[6];

	public static final int SPEED_SLOT = 0;
	public static int upgradeSlots = 1;
	public static int templateSlots = 4;

	public TEGanController() {
		super(0, 0, templateSlots,upgradeSlots);

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



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(!GanPlantRecipes.inhibited_gases.contains(EnumAirGases.name(6))){
			this.enableN = compound.getBoolean("EnableN");
		}else{
			this.enableN = false;
		}
		if(!GanPlantRecipes.inhibited_gases.contains(EnumAirGases.name(7))){
			this.enableO = compound.getBoolean("EnableO");
		}else{
			this.enableO = false;
		}			
		this.enableX = compound.getBoolean("EnableX");
		
        NBTTagCompound drainList = compound.getCompoundTag("Gases");
		for(int i = 0; i < drainList.getSize(); i++){
			this.rareGases[i] = drainList.getInteger("Gas" + i);
		}

		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		compound.setBoolean("EnableN", enableN());
		compound.setBoolean("EnableO", enableO());
		compound.setBoolean("EnableX", enableX());
		
        NBTTagCompound drainList = new NBTTagCompound();
		for(int i = 0; i < this.rareGases.length; i++){
			drainList.setInteger("Gas" + i, this.rareGases[i]);
		}
		compound.setTag("Gases", drainList);

        return compound;
	}



	// ----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "gan_controller";
	}

	public int getCooktimeMax(){
		return 30;
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	private void doPreset() {
		if(hasEngine()){
			if(getEngine().enablePower){
				getEngine().enablePower = false;
				getEngine().markDirtyClient();
			}
			if(!getEngine().enableRedstone){
				getEngine().enableRedstone = true;
				getEngine().markDirtyClient();
			}
		}
		if(hasInputVessel()){
			if(getInputVessel().getFilter() != airStack()){
				getInputVessel().filter = airStack();
			}
		}
		if(hasOutputVessel1()){
			if(getOutputVessel1().getFilter() != nitrogenStack()){
				getOutputVessel1().filter = nitrogenStack();
			}
		}
		if(hasOutputVessel2()){
			if(getOutputVessel2().getFilter() != oxygenStack()){
				getOutputVessel2().filter = oxygenStack();
			}
		}

	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	// ----------------------- CUSTOM -----------------------
	public boolean enableN() {
		return this.enableN;
	}

	public boolean enableO() {
		return this.enableO;
	}

	public boolean enableX() {
		return this.enableX;
	}

	public int consumeN(){
		int baseConsume = 10 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int consumeO(){
		int baseConsume = 30 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int consumeX(){
		int baseConsume = 60 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int calculateConsume(){
		int totConsume = 0;
		if(enableN()){
			totConsume += consumeN();
		}
		if(enableO()){
			totConsume += consumeO();
		}
		if(enableX()){
			totConsume += consumeX();
		}
		return totConsume;
	}

	private FluidStack airStack() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.COOLED_AIR), num_air());
	}
	public int num_air(){
		return 20 * speedFactor();
	}

	private FluidStack nitrogenStack() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.NITROGEN), num_N());
	}
	private int num_N(){
		return 15 * speedFactor();
	}

	private FluidStack oxygenStack() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.OXYGEN), num_O());
	}
	private int num_O(){
		return 5 * speedFactor();
	}

	private FluidStack stack_Ar() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.ARGON), num_Ar());
	}
	private int num_Ar(){
		return 3 * speedFactor();
	}

	private FluidStack stack_CO() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.CARBON_DIOXIDE), num_CO());
	}
	private int num_CO(){
		return 3 * speedFactor();
	}

	private FluidStack stack_Ne() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.NEON), num_Ne());
	}
	private int num_Ne(){
		return 3 * speedFactor();
	}

	private FluidStack stack_He() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.HELIUM), num_He());
	}
	private int num_He(){
		return 3 * speedFactor();
	}

	private FluidStack stack_Kr() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.KRYPTON), num_Kr());
	}
	private int num_Kr(){
		return 3 * speedFactor();
	}

	private FluidStack stack_Xe() {
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.XENON), num_Xe());
	}
	private int num_Xe(){
		return 3 * speedFactor();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<String> inhibitedList(){
		return GanPlantRecipes.inhibited_gases;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() >= calculateConsume() : false;
	}

	private void drainPower() {
		getEngine().redstoneCount-= calculateConsume();
		getEngine().markDirtyClient();
	}

//centrifuge
	public TECentrifuge getInputCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getOutputCentrifuge1(){
		BlockPos startPos = this.pos.offset(isFacingAt(90), 2).offset(getFacing());
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, startPos, getFacing());
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getOutputCentrifuge2(){
		BlockPos startPos = this.pos.offset(isFacingAt(90), 4).offset(getFacing());
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, startPos, getFacing());
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getInputCentrifuge() != null && getOutputCentrifuge1() != null && getOutputCentrifuge2() != null;
	}

//tower 1
	public boolean hasTower1(){
		//tower
		int countTower = 0;
		BlockPos startPos = this.pos.offset(isFacingAt(90), 2);
		for (int x = 1; x <= 5; x++){
			BlockPos tPos = new BlockPos(startPos.getX(), startPos.getY() + x, startPos.getZ());
			Block tower = TileStructure.getStructure(this.world, tPos, EnumMiscBlocksA.GAN_TOWER.ordinal());
			if(tower != null) {
				countTower++;
			}
		}

		//towercap
		BlockPos tPos = new BlockPos(startPos.getX(), startPos.getY() + 6, startPos.getZ());
		Block tower = TileStructure.getStructure(this.world, tPos, EnumMiscBlocksA.GAN_TOWER_TOP.ordinal());
		if(tower != null) {
			countTower++;
		}

		//filter
		Block injector = TileStructure.getStructure(this.world, startPos, EnumMiscBlocksA.GAN_INJECTOR.ordinal());
		if(injector != null) {
			countTower++;
		}

		return countTower == 7;
	}

//tower 2
	public boolean hasTower2(){
		//tower
		int countTower = 0;
		BlockPos startPos = this.pos.offset(isFacingAt(90), 4);
		for (int x = 1; x <= 3; x++){
			BlockPos tPos = new BlockPos(startPos.getX(), startPos.getY() + x, startPos.getZ());
			Block tower = TileStructure.getStructure(this.world, tPos, EnumMiscBlocksA.GAN_TOWER.ordinal());
			if(tower != null) {
				countTower++;
			}
		}

		//towercap
		BlockPos tPos = new BlockPos(startPos.getX(), startPos.getY() + 4, startPos.getZ());
		Block tower = TileStructure.getStructure(this.world, tPos, EnumMiscBlocksA.GAN_TOWER_TOP.ordinal());
		if(tower != null) {
			countTower++;
		}

		//filter
		Block injector = TileStructure.getStructure(this.world, startPos, EnumMiscBlocksA.GAN_INJECTOR.ordinal());
		if(injector != null) {
			countTower++;
		}

		return countTower == 5;
	}

//separator
	public Block getSeparator1(){
		BlockPos separatorPos = this.pos.offset(isFacingAt(90), 1);
		Block separator = TileStructure.getStructure(this.world, separatorPos, EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public Block getSeparator2(){
		BlockPos separatorPos = this.pos.offset(isFacingAt(90), 3);
		Block separator = TileStructure.getStructure(this.world, separatorPos, EnumMiscBlocksA.SEPARATOR.ordinal());
		return separator != null ? separator : null;
	}

	public boolean hasSeparators(){
		return getSeparator1() != null && getSeparator2() != null;
	}

//expander
	public TEGanExpanderBase getExpander(){
		BlockPos expanderPos = this.pos.offset(isFacingAt(90), 3).offset(getFacing().getOpposite());
		TileEntity te = this.world.getTileEntity(expanderPos);
		if(this.world.getBlockState(expanderPos) != null && te instanceof TEGanExpanderBase){
			TEGanExpanderBase press = (TEGanExpanderBase)te;
			if(press.getFacing() == getFacing()){
				return press;
			}
		}
		return null;
	}

	public boolean hasExpander(){
		return getExpander() != null;
	}

//pressurizer
	public TEAuxiliaryEngine getPressurizer1(){
		BlockPos pressPos = this.pos.offset(isFacingAt(90)).offset(getFacing());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer3(){
		BlockPos pressPos = this.pos.offset(isFacingAt(90)).offset(getFacing().getOpposite());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing());
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer4(){
		BlockPos pressPos = this.pos.offset(isFacingAt(90), 2).offset(getFacing().getOpposite());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing());
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer2(){
		BlockPos pressPos = this.pos.offset(isFacingAt(90), 3).offset(getFacing());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing().getOpposite());
		return pressurizer != null ? pressurizer : null;
	}

	public TEAuxiliaryEngine getPressurizer5(){
		BlockPos pressPos = this.pos.offset(isFacingAt(90), 4).offset(getFacing().getOpposite());
		TEAuxiliaryEngine pressurizer = TileStructure.getPressurizer(this.world, pressPos, getFacing());
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasPressurizers(){
		return getPressurizer1() != null && getPressurizer2() != null && getPressurizer3() != null && getPressurizer4() != null && getPressurizer5() != null;
	}

//input vessel
	public TileVessel getInputVessel(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos.offset(isFacingAt(270), 2), isFacingAt(90));
		return vessel != null ? vessel : null;
	}

	public boolean hasInputVessel(){
		return getInputVessel() != null;
	}

	public boolean inputTankHasGas(){
		return hasInputVessel() 
			&& this.input.canDrainFluid(getInputVessel().inputTank.getFluid(), airStack());
	}

//output vessel 1
	public TileVessel getOutputVessel1(){
		BlockPos tankPos = this.pos.offset(isFacingAt(90), 2).offset(getFacing(), 2);
		TileVessel vessel = TileStructure.getHolder(this.world, tankPos, getFacing());
		return vessel != null ? vessel : null;
	}

	public boolean hasOutputVessel1(){
		return getOutputVessel1() != null;
	}

//output vessel 2
	public TileVessel getOutputVessel2(){
		BlockPos tankPos = this.pos.offset(isFacingAt(90), 4).offset(getFacing(), 2);
		TileVessel vessel = TileStructure.getHolder(this.world, tankPos, getFacing());
		return vessel != null ? vessel : null;
	}

	public boolean hasOutputVessel2(){
		return getOutputVessel2() != null;
	}

//multivessel
	public TEMultivessel getMultivessel(){
		BlockPos tankPos = this.pos.offset(isFacingAt(90), 5);
		TileEntity te = this.world.getTileEntity(tankPos);
		if(this.world.getBlockState(tankPos) != null && te instanceof TEMultivessel){
			TEMultivessel press = (TEMultivessel)te;
			if(press.getFacing() == isFacingAt(90)){
				return press;
			}
		}
		return null;
	}

	public boolean hasRareOutput(){
		return getMultivessel() != null;
	}

	private boolean isAssembled() {
		return hasTower1() && hasTower2() && hasCentrifuges() && hasSeparators() && hasExpander() && hasPressurizers();
	}



// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();

			if(isActive()){
				if(canDistillate()){
					this.cooktime++;
					distillate();
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

	private boolean canDistillate() {
		return hasRedstonePower()
			&& isAssembled()
			&& inputTankHasGas()
			&& hasCorrectSetup();
	}

	private boolean hasCorrectSetup() {
		if(enableN()){
			return handleCh1();
		}else if(enableO()){
			return handleCh2();
		}else if(enableX()){
			return handleCh3();
		}
		return false;
	}

	private boolean handleCh1() {
		return hasOutputVessel1()
			&& this.output.canSetOrFillFluid(getOutputVessel1().inputTank, getOutputVessel1().inputTank.getFluid(), nitrogenStack());
	}

	private boolean handleCh2() {
		return hasOutputVessel2()
			&& this.output.canSetOrFillFluid(getOutputVessel2().inputTank, getOutputVessel2().inputTank.getFluid(), oxygenStack());
	}

	private boolean handleCh3() {
		return hasRareOutput();
	}

	public boolean doOutput1(){
		return enableN() && handleCh1();
	}

	public boolean doOutput2(){
		return enableO() && handleCh2();
	}

	public boolean doOutput3(){
		return enableX() && handleCh3();
	}

	public boolean isGasInhibited(int i) {
		return inhibitedList().contains(EnumAirGases.name(i));
	}

	private boolean canFillChannel(FluidTank tank, FluidStack gas) {
		return this.output.canSetOrFillFluid(tank, tank.getFluid(), gas);
	}

	private void distillate() {
		if(doOutput1() && !isGasInhibited(6)){
			this.output.setOrFillFluid(getOutputVessel1().inputTank, nitrogenStack());
			getOutputVessel1().updateNeighbours();
		}

		if(doOutput2() && !isGasInhibited(7)){
			this.output.setOrFillFluid(getOutputVessel2().inputTank, oxygenStack());
			getOutputVessel2().updateNeighbours();
		}

		if(doOutput3()){
			if(!isGasInhibited(0) && canFillChannel(getMultivessel().tank_Ar, stack_Ar())){
				if(getMultivessel().rareEnabler[0]){
					this.rareGases[0]++;
					if(this.rareGases[0] >= 10 ){
						this.output.setOrFillFluid(getMultivessel().tank_Ar, stack_Ar());
						this.rareGases[0] = 0;
					}
				}
			}
			if(!isGasInhibited(1) && canFillChannel(getMultivessel().tank_CO, stack_CO())){
				if(getMultivessel().rareEnabler[1]){
					this.rareGases[1]++;
					if(this.rareGases[1] >= 20 ){
						this.output.setOrFillFluid(getMultivessel().tank_CO, stack_CO());
						this.rareGases[1] = 0;
					}
				}
			}			
			if(!isGasInhibited(2) && canFillChannel(getMultivessel().tank_Ne, stack_Ne())){
				if(getMultivessel().rareEnabler[2]){
					this.rareGases[2]++;
					if(this.rareGases[2] >= 40 ){
						this.output.setOrFillFluid(getMultivessel().tank_Ne, stack_Ne());
						this.rareGases[2] = 0;
					}
				}
			}
			if(!isGasInhibited(3) && canFillChannel(getMultivessel().tank_He, stack_He())){
				if(getMultivessel().rareEnabler[3]){
					this.rareGases[3]++;
					if(this.rareGases[3] >= 100 ){
						this.output.setOrFillFluid(getMultivessel().tank_He, stack_He());
						this.rareGases[3] = 0;
					}
				}
			}
			if(!isGasInhibited(4) && canFillChannel(getMultivessel().tank_Kr, stack_Kr())){
				if(getMultivessel().rareEnabler[4]){
					this.rareGases[4]++;
					if(this.rareGases[4] >= 200 ){
						this.output.setOrFillFluid(getMultivessel().tank_Kr, stack_Kr());
						this.rareGases[4] = 0;
					}
				}
			}
			if(!isGasInhibited(5) && canFillChannel(getMultivessel().tank_Xe, stack_Xe())){
				if(getMultivessel().rareEnabler[5]){
					this.rareGases[5]++;
					if(this.rareGases[5] >= 400 ){
						this.output.setOrFillFluid(getMultivessel().tank_Xe, stack_Xe());
						this.rareGases[5] = 0;
					}
				}
			}
		}

		if(hasInputVessel()){
			this.input.drainOrCleanFluid(getInputVessel().inputTank, num_air(), true);
			getInputVessel().updateNeighbours();
		}

		drainPower();
	}


}