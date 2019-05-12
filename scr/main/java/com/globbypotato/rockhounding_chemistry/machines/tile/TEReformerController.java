package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.EnumMiscBlocksA;
import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEReformerController extends TileEntityInv implements IInternalServer{

	public static final int SPEED_SLOT = 0;

	public static int upgradeSlots = 1;
	public static int templateSlots = 3;

	public int currentFile = -1;
	public boolean isRepeatable = false;

	public int[] catList = new int[5];

	public GasReformerRecipe dummyRecipe;

	public TEReformerController() {
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
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		if(compound.hasKey("Catalysts")){
			NBTTagCompound elements = compound.getCompoundTag("Catalysts");
			for(int i = 0; i < this.catList.length; i++){
				this.catList[i] = elements.getInteger("catalyst" + i);
			}
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		NBTTagCompound elements = new NBTTagCompound();
		for(int i = 0; i < this.catList.length; i++){
			elements.setInteger("catalyst" + i, this.catList[i]);
		}
		compound.setTag("Catalysts", elements);
		return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "reformer_controller";
	}

	public int getCooktimeMax(){
		return 60;
	}

	private static int deviceCode() {
		return EnumServer.REFORMER.ordinal();
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<GasReformerRecipe> recipeList(){
		return GasReformerRecipes.gas_reformer_recipes;
	}

	public GasReformerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public GasReformerRecipe getCurrentRecipe(){
		if(isActive() && isValidPreset() && hasCatalysts()){
			if(handleInput() && handleOutput()){
				return getRecipeList(getRecipeIndex());
			}
		}
		return null;
	}

	public GasReformerRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidPreset(){
		return getRecipeIndex() > -1 && getRecipeIndex() < recipeList().size();
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public FluidStack getRecipeInputA(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getInputA() : null; }
	public FluidStack getRecipeInputB(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getInputB() : null; }
	public FluidStack getRecipeOutput(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getOutput() : null; }
	public ItemStack getRecipeCatalyst(){ return isValidPreset() ? getRecipeList(getRecipeIndex()).getCatalyst() : ItemStack.EMPTY; }

	public boolean isOutputGaseous(){
		return getRecipeOutput() != null
			&& getRecipeOutput().getFluid().isGaseous();
	}



	//----------------------- CATALYST -----------------------
	public ItemStack catalystA(){ return BaseRecipes.nl_catalyst.copy();}
	public ItemStack catalystB(){ return BaseRecipes.gr_catalyst.copy();}
	public ItemStack catalystC(){ return BaseRecipes.wg_catalyst.copy();}
	public ItemStack catalystD(){ return BaseRecipes.au_catalyst.copy();}

	public boolean hasSystemCatalysts() {
		this.catList[0] = 0;
		this.catList[1] = 0;
		this.catList[2] = 0;
		for(int x = 0; x < TEReformerReactor.SLOT_SYSTEM_CAT.length; x++){
			ItemStack systemSlot = getReactor().getInput().getStackInSlot(TEReformerReactor.SLOT_SYSTEM_CAT[x]);
			if(isSystemCatalyst(systemSlot)){
				if(systemSlot.isItemEqualIgnoreDurability(catalystA())){
					this.catList[0]++;
				}else if(systemSlot.isItemEqualIgnoreDurability(catalystB())){
					this.catList[1]++;
				}else if(systemSlot.isItemEqualIgnoreDurability(catalystC())){
					this.catList[2]++;
				}
			}
		}
		return this.catList[0] == 6 || this.catList[1] == 6 || this.catList[2] == 6;
	}

	public int systemCatalystFactor(){
		if(this.catList[0] == 6){
			return 1;
		}else if(this.catList[1] == 6){
			return 2;
		}else if(this.catList[2] == 6){
			return 3;
		}else{
			return 0;
		}
	}

	public boolean hasRecipeCatalysts() {
		this.catList[3] = 0;
		this.catList[4] = 0;
		for(int x = 0; x < TEReformerReactor.SLOT_RECIPE_CAT.length; x++){
			ItemStack recipeSlot = getReactor().getInput().getStackInSlot(TEReformerReactor.SLOT_RECIPE_CAT[x]);
			if(isRecipeCatalyst(recipeSlot)){
				if(recipeSlot.isItemEqualIgnoreDurability(catalystD())){
					this.catList[3]++;
				}else if(recipeSlot.isItemEqualIgnoreDurability(getRecipeCatalyst())){
					this.catList[4]++;
				}
			}
		}
		return this.catList[3] == 4 || this.catList[4] == 4;
	}

	public int recipeCatalystFactor(){
		if(this.catList[3] == 4){
			return 1;
		}else if(this.catList[4] == 4){
			return 2;
		}else{
			return 0;
		}
	}

	public boolean isSystemCatalyst(ItemStack insertingStack) {
		return CoreUtils.hasConsumable(catalystA(), insertingStack)
			|| CoreUtils.hasConsumable(catalystB(), insertingStack)
			|| CoreUtils.hasConsumable(catalystC(), insertingStack);
	}

	public boolean isRecipeCatalyst(ItemStack insertingStack) {
		return insertingStack.isItemEqualIgnoreDurability(getRecipeCatalyst())
			|| insertingStack.isItemEqualIgnoreDurability(catalystD());
	}

	public boolean hasCatalysts(){
		return hasReactor() && hasSystemCatalysts() && systemCatalystFactor() > 0 && hasRecipeCatalysts() && recipeCatalystFactor() > 0;
	}

	public int inputTier(){
		return hasReactor() ? systemCatalystFactor() : 0;
	}

	public int outputTier(){
		return hasReactor() ? recipeCatalystFactor() : 0;
	}



	//----------------------- CUSTOM -----------------------
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
		if(isValidPreset()){
			if(hasATank()){
				if(getATank().getFilter() != getRecipeInputA()){
					getATank().filter = getRecipeInputA();
				}
			}
			if(hasBTank()){
				if(getBTank().getFilter() != getRecipeInputB()){
					getBTank().filter = getRecipeInputB();
				}
			}
		}else{
			if(hasATank()){
				if(getATank().getFilter() != null){
					getATank().filter = null;
				}
			}
			if(hasBTank()){
				if(getBTank().getFilter() != null){
					getBTank().filter = null;
				}
			}
		}
	}

	public int powerConsume() {
		int baseConsume = 60 * ModConfig.basePower;
		return ModConfig.speedMultiplier ? baseConsume * speedFactor() : baseConsume;
	}

	public int baseDrainA() {
		return getRecipeInputA() != null ? getRecipeInputA().amount : 0;
	}

	public int calculatedDrainA() {
		return hasReactor() ? ((4 - systemCatalystFactor()) * baseDrainA()) * speedFactor() : 0;
	}

	public int baseDrainB() {
		return getRecipeInputB() != null ? getRecipeInputB().amount : 0;
	}

	public int calculatedDrainB() {
		return hasReactor() ? ((4 - systemCatalystFactor()) * baseDrainB()) * speedFactor() : 0;
	}

	public int baseProduct() {
		return getRecipeOutput() != null ? getRecipeOutput().amount : 0;
	}

	public int calculatedProduct() {
		return hasReactor() ? (recipeCatalystFactor() * baseProduct()) * speedFactor() : 0;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, getFacing(), 1, 0);
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() >= powerConsume() : false;
	}

	private void drainPower() {
		getEngine().redstoneCount-= powerConsume();
		getEngine().markDirtyClient();
	}

//reactor
	public BlockPos reactorPos(){
		return this.pos.offset(EnumFacing.UP, 1);
	}

	public TEReformerReactor getReactor(){
		TileEntity te = this.world.getTileEntity(reactorPos());
		if(this.world.getBlockState(reactorPos()) != null && te instanceof TEReformerReactor){
			TEReformerReactor reactor = (TEReformerReactor)te;
			if(reactor.getFacing() == getFacing()){
				return reactor;
			}
		}
		return null;
	}

	public boolean hasReactor(){
		return getReactor() != null;
	}

//input vessel 1
	public TileVessel getATank(){
		TileVessel vessel = TileStructure.getHolder(this.world, separatorPos(), isFacingAt(90), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasATank(){
		return getATank() != null;
	}

	public FluidStack tankAInput(){
		return hasATank() ? getATank().inputTank.getFluid() : null;
	}

//input vessel 2
	public TileVessel getBTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, separatorPos(), isFacingAt(270), 1, 180);
		return vessel != null ? vessel : null;
	}

	public boolean hasBTank(){
		return getBTank() != null;
	}

	public FluidStack tankBInput(){
		return hasBTank() ? getBTank().inputTank.getFluid() : null;
	}

//output vessel
	public TileVessel getOutTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, separatorPos(), getFacing(), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasOutTank(){
		return getOutTank() != null;
	}

	public FluidStack outGas(){
		return hasOutTank() ? getOutTank().inputTank.getFluid() : null;
	}

//purge vessel
	public TileVessel getPurgeTank(){
		TileVessel vessel = TileStructure.getHolder(this.world, this.pos, isFacingAt(90), 1, 0);
		return vessel != null ? vessel : null;
	}

	public boolean hasPurgeTank(){
		return getPurgeTank() != null;
	}

	public FluidStack purgeGas(){
		return hasPurgeTank() ? getPurgeTank().inputTank.getFluid() : null;
	}

//output tank
	public TEBufferTank getTank(){
		TEBufferTank tank = TileStructure.getBufferTank(this.world, this.pos, EnumFacing.UP, 5);
		return tank != null ? tank : null;
	}

	public boolean hasTank(){
		return getTank() != null;
	}

//separator
	public BlockPos separatorPos(){
		return reactorPos().offset(getFacing(), 1);
	}

	public Block getSeparator(){
		IBlockState sepState = this.world.getBlockState(separatorPos());
		Block separator = sepState.getBlock();
		if(MachineIO.miscBlocksA(separator, sepState, EnumMiscBlocksA.SEPARATOR.ordinal())){
			return separator;
		}
		return null;
	}

//tower
	public boolean hasTower(){
		//tower
		int countTower = 0;
		for (int x = 2; x <= 3; x++){
			BlockPos tPos = new BlockPos(this.pos.getX(), this.pos.getY() + x, this.pos.getZ());
			IBlockState state = this.world.getBlockState(tPos);
			Block block = state.getBlock();
			if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.REFORMER_TOWER.ordinal())){
				countTower++;
			}
		}
		//towercap
		BlockPos tPos = new BlockPos(this.pos.getX(), this.pos.getY() + 4, this.pos.getZ());
		IBlockState state = this.world.getBlockState(tPos);
		Block block = state.getBlock();
		if(MachineIO.miscBlocksA(block, state, EnumMiscBlocksA.REFORMER_TOWER_TOP.ordinal())){
			countTower++;
		}

		return countTower == 3 && getSeparator() != null;
	}


//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos, isFacingAt(270), 1, 0);
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();
			handlePurge();

			initializeServer(isRepeatable, hasServer(), getServer(), deviceCode());

			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canProcess()){
				this.cooktime++;
				drainPower();
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				tickOff();
			}
		}
	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe()
			&& hasRedstonePower()
			&& hasTower()
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
	}

				private boolean handleInput() {
					return getRecipeInputA() != null && hasATank() && this.input.canDrainFluid(tankAInput(), getRecipeInputA(), calculatedDrainA())
						&& getRecipeInputB() != null && hasBTank() && this.input.canDrainFluid(tankBInput(), getRecipeInputB(), calculatedDrainB());
				}

				private boolean handleOutput() {
					if(isOutputGaseous()){
						return getRecipeOutput() != null && hasOutTank() && this.input.canSetOrAddFluid(getOutTank().inputTank, getOutTank().inputTank.getFluid(), getRecipeOutput(), calculatedProduct());	
					}
					return getRecipeOutput() != null && hasTank() && this.input.canSetOrAddFluid(getTank().inputTank, getTank().inputTank.getFluid(), getRecipeOutput(), calculatedProduct());	
				}

	private void process() {

		if(isValidRecipe() && hasCatalysts()){
			if(isOutputGaseous()){
				if(hasOutTank()){
					this.input.setOrFillFluid(getOutTank().inputTank, getRecipeOutput(), calculatedProduct());
				}
			}else{
				if(hasTank()){
					this.input.setOrFillFluid(getTank().inputTank, getRecipeOutput(), calculatedProduct());
				}
			}

			if(hasReactor()){
				for(int cats = 0; cats < TEReformerReactor.totCatalysts; cats++){
					int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, getReactor().inputSlot(cats));
					((MachineStackHandler)getReactor().getInput()).damageUnbreakingSlot(unbreakingLevel, cats);
				}
			}

			if(hasATank()){
				this.input.drainOrCleanFluid(getATank().inputTank, calculatedDrainA(), true);
			}

			if(hasBTank()){
				this.input.drainOrCleanFluid(getBTank().inputTank, calculatedDrainB(), true);
			}

			updateServer(hasServer(), getServer(), this.currentFile);
		}
		
		this.dummyRecipe = null;

	}

	private void handlePurge() {
		if(isActive()){
			if(!isValidPreset() || !isValidRecipe()){
				if(hasPurgeTank()){
					if(hasATank()){
						if(isWrongInputA() && canPurgeA()){
							int canReceive = getPurgeTank().inputTank.getCapacity() - getPurgeTank().inputTank.getFluidAmount();
							int canSend = Math.min(getATank().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeTank().inputTank, tankAInput(), canSend);
							this.input.drainOrCleanFluid(getATank().inputTank, canSend, true);
						}
					}
					if(hasBTank()){
						if(isWrongInputB() && canPurgeB()){
							int canReceive = getPurgeTank().inputTank.getCapacity() - getPurgeTank().inputTank.getFluidAmount();
							int canSend = Math.min(getBTank().inputTank.getFluidAmount(), canReceive);
							this.input.setOrFillFluid(getPurgeTank().inputTank, tankBInput(), canSend);
							this.input.drainOrCleanFluid(getBTank().inputTank, canSend, true);
						}
					}
				}
			}
		}
	}

				private boolean isWrongInputA() {
					return tankAInput() != null
						&& (getRecipeInputA() == null || (getRecipeInputA() != null && !getRecipeInputA().isFluidEqual(tankAInput())));
				}

				private boolean isWrongInputB() {
					return tankBInput() != null
						&& (getRecipeInputB() == null || (getRecipeInputB() != null && !getRecipeInputB().isFluidEqual(tankBInput())));
				}

				private boolean canPurgeA() {
					return tankAInput() != null && this.input.canSetOrFillFluid(getPurgeTank().inputTank, purgeGas(), tankAInput());
				}

				private boolean canPurgeB() {
					return tankBInput() != null && this.input.canSetOrFillFluid(getPurgeTank().inputTank, purgeGas(), tankBInput());
				}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(!fileSlot.isEmpty() && fileSlot.isItemEqual(BaseRecipes.server_file)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isValidFile(tag)){
							if(tag.getInteger("Device") == deviceCode()){
								if(tag.getInteger("Recipe") < recipeList().size()){
									if(tag.getInteger("Done") > 0){
										if(this.recipeIndex != tag.getInteger("Recipe")){
											this.recipeIndex = tag.getInteger("Recipe");
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										break;
									}
								}
							}
						}
					}
				}
				if(x == TEServer.FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}

}