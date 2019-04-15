package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

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
		this.enableN = compound.getBoolean("EnableN");
		this.enableO = compound.getBoolean("EnableO");
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
		if(hasInTank()){
			if(getInTank().getFilter() != airStack()){
				getInTank().filter = airStack();
			}
		}
		if(hasFirstOutput()){
			if(getChOut1().getFilter() != nitrogenStack()){
				getChOut1().filter = nitrogenStack();
			}
		}
		if(hasSecondOutput()){
			if(getChOut2().getFilter() != oxygenStack()){
				getChOut2().filter = oxygenStack();
			}
		}

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



	//----------------------- STRUCTURE -----------------------
	public BlockPos plantInverse(){
		return this.pos.offset(isFacingAt(90).getOpposite(), 1);		
	}

	public BlockPos plantPos1(){
		return this.pos.offset(isFacingAt(90), 1);		
	}

	public BlockPos plantPos2(){
		return this.pos.offset(isFacingAt(90), 2);		
	}

	public BlockPos plantPos3(){
		return this.pos.offset(isFacingAt(90), 3);		
	}

	public BlockPos plantPos4(){
		return this.pos.offset(isFacingAt(90), 4);		
	}

	public BlockPos plantPos5(){
		return this.pos.offset(isFacingAt(90), 5);		
	}

//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, getFacing(), 1, 0);
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

//in tank
	public TileVessel getInTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, isFacingAt(90), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasInTank(){
		return getInTank() != null;
	}

	public boolean inputTankHasGas(){
		return hasInTank() 
			&& this.input.canDrainFluid(getInTank().inputTank.getFluid(), airStack());
	}

//tower 1
	public boolean hasChannel1(){
		//tower
		int countTower = 0;
		for (int x = 1; x <= 3; x++){
			BlockPos tPos = new BlockPos(plantPos1().getX(), plantPos1().getY() + x, plantPos1().getZ());
			IBlockState state = this.world.getBlockState(tPos);
			Block block = state.getBlock();
			if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.GAN_TOWER.ordinal())){
				countTower++;
			}
		}
		//towercap
		BlockPos tPos = new BlockPos(plantPos1().getX(), plantPos1().getY() + 4, plantPos1().getZ());
		IBlockState state = this.world.getBlockState(tPos);
		Block block = state.getBlock();
		if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.GAN_TOWER_TOP.ordinal())){
			countTower++;
		}
		//filter
		state = this.world.getBlockState(plantPos1());
		block = state.getBlock();
		if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.GAN_INJECTOR.ordinal())){
			countTower++;
		}

		return countTower == 5 && hasFirstPressurizer() && hasFirstOutput();
	}

//pressurizer 1
	public BlockPos pressurizer1Pos(){
		return this.plantPos1().offset(getFacing(), 1);		
	}

	public TEGasPressurizer getPressurizer1(){
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, plantPos1(), getFacing(), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasFirstPressurizer(){
		return getPressurizer1() != null;
	}

//output vessel 1
	public TileVessel getChOut1(){
		TileVessel vessel = TileStructure.getHolder(this.world, plantPos1(), getFacing().getOpposite(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasFirstOutput(){
		return getChOut1() != null;
	}

//separator
	public boolean hasSeparator(){
		IBlockState state = this.world.getBlockState(plantPos2());
		Block block = state.getBlock();
		return MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.SEPARATOR.ordinal());
	}

//pressurizer S
	public TEGasPressurizer getPressurizerS(){
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, plantPos2(), getFacing().getOpposite(), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasExpanderPressurizer(){
		return getPressurizerS() != null;
	}

//expander
	public BlockPos expanderPos(){
		return this.plantPos2().offset(getFacing(), 1);		
	}

	public TEGanExpanderBase getExpander(){
		TileEntity te = this.world.getTileEntity(expanderPos());
		if(this.world.getBlockState(expanderPos()) != null && te instanceof TEGanExpanderBase){
			TEGanExpanderBase press = (TEGanExpanderBase)te;
			if(press.getFacing() == getFacing().getOpposite()){
				return press;
			}
		}
		return null;
	}

	public boolean hasExpander(){
		return getExpander() != null && hasSeparator() && hasExpanderPressurizer();
	}

//tower 2
	public boolean hasChannel2(){
		//tower
		int countTower = 0;
		for (int x = 1; x <= 2; x++){
			BlockPos tPos = new BlockPos(plantPos3().getX(), plantPos3().getY() + x, plantPos3().getZ());
			IBlockState state = this.world.getBlockState(tPos);
			Block block = state.getBlock();
			if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.GAN_TOWER.ordinal())){
				countTower++;
			}
		}
		//towercap
		BlockPos tPos = new BlockPos(plantPos3().getX(), plantPos3().getY() + 4, plantPos3().getZ());
		IBlockState state = this.world.getBlockState(tPos);
		Block block = state.getBlock();
		if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.GAN_TOWER_TOP.ordinal())){
			countTower++;
		}
		//filter
		state = this.world.getBlockState(plantPos3());
		block = state.getBlock();
		if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.GAN_INJECTOR.ordinal())){
			countTower++;
		}

		return countTower == 3 && hasSecondPressurizer() && hasSecondOutput();
	}

//pressurizer 2
	public TEGasPressurizer getPressurizer2(){
		TEGasPressurizer pressurizer = TileStructure.getPressurizer(this.world, plantPos3(), getFacing(), 1, 0);
		return pressurizer != null ? pressurizer : null;
	}

	public boolean hasSecondPressurizer(){
		return getPressurizer2() != null;
	}

//output vessel 2
	public TileVessel getChOut2(){
		TileVessel vessel = TileStructure.getHolder(this.world, plantPos3(), getFacing().getOpposite(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasSecondOutput(){
		return getChOut2() != null;
	}

//multivessel
	public TEMultivessel getMultivessel(){
		TileEntity te = this.world.getTileEntity(plantPos4());
		if(this.world.getBlockState(plantPos5()) != null && te instanceof TEMultivessel){
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

	public boolean hasChannel3(){
		return hasRareOutput();
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
		return hasChannel1() && hasExpander()
			&& this.output.canSetOrFillFluid(getChOut1().inputTank, getChOut1().inputTank.getFluid(), nitrogenStack());
	}

	private boolean handleCh2() {
		return handleCh1() && hasChannel2()
			&& this.output.canSetOrFillFluid(getChOut2().inputTank, getChOut2().inputTank.getFluid(), oxygenStack());
	}

	private boolean handleCh3() {
		return hasChannel2() && hasChannel3()
			&& this.output.canSetOrFillFluid(getMultivessel().tank_Ar, getMultivessel().tank_Ar.getFluid(), stack_Ar())
			&& this.output.canSetOrFillFluid(getMultivessel().tank_CO, getMultivessel().tank_CO.getFluid(), stack_CO())
			&& this.output.canSetOrFillFluid(getMultivessel().tank_Ne, getMultivessel().tank_Ne.getFluid(), stack_Ne())
			&& this.output.canSetOrFillFluid(getMultivessel().tank_He, getMultivessel().tank_He.getFluid(), stack_He())
			&& this.output.canSetOrFillFluid(getMultivessel().tank_Kr, getMultivessel().tank_Kr.getFluid(), stack_Kr())
			&& this.output.canSetOrFillFluid(getMultivessel().tank_Xe, getMultivessel().tank_Xe.getFluid(), stack_Xe());
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

	private void distillate() {
		if(doOutput1()){
			this.output.setOrFillFluid(getChOut1().inputTank, nitrogenStack());
		}
		if(doOutput2()){
			this.output.setOrFillFluid(getChOut2().inputTank, oxygenStack());
		}
		if(doOutput3()){
			if(getMultivessel().rareEnabler[0]){
				this.rareGases[0]++;
				if(this.rareGases[0] >= 10 ){
					this.output.setOrFillFluid(getMultivessel().tank_Ar, stack_Ar());
					this.rareGases[0] = 0;
				}
			}	
			if(getMultivessel().rareEnabler[1]){
				this.rareGases[1]++;
				if(this.rareGases[1] >= 20 ){
					this.output.setOrFillFluid(getMultivessel().tank_CO, stack_CO());
					this.rareGases[1] = 0;
				}
			}
			
			if(getMultivessel().rareEnabler[2]){
				this.rareGases[2]++;
				if(this.rareGases[2] >= 40 ){
					this.output.setOrFillFluid(getMultivessel().tank_Ne, stack_Ne());
					this.rareGases[2] = 0;
				}
			}

			if(getMultivessel().rareEnabler[3]){
				this.rareGases[3]++;
				if(this.rareGases[3] >= 100 ){
					this.output.setOrFillFluid(getMultivessel().tank_He, stack_He());
					this.rareGases[3] = 0;
				}
			}

			if(getMultivessel().rareEnabler[4]){
				this.rareGases[4]++;
				if(this.rareGases[4] >= 200 ){
					this.output.setOrFillFluid(getMultivessel().tank_Kr, stack_Kr());
					this.rareGases[4] = 0;
				}
			}

			if(getMultivessel().rareEnabler[5]){
				this.rareGases[5]++;
				if(this.rareGases[5] >= 400 ){
					this.output.setOrFillFluid(getMultivessel().tank_Xe, stack_Xe());
					this.rareGases[5] = 0;
				}
			}
		}

		if(hasInTank()){
			this.input.drainOrCleanFluid(getInTank().inputTank, num_air(), true);	
		}

		drainPower();
	}

}