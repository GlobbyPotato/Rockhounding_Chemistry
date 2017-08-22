package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.blocks.GanBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.enums.EnumGan;
import com.globbypotato.rockhounding_chemistry.machines.GanController;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiGanController;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityGanController extends TileEntityMachineEnergy {

	private ItemStackHandler template = new TemplateStackHandler(2);

	public static final int MATRIX_SLOT = 0;
    public boolean activationKey;
    public boolean compressKey;
    public int tier;

	BlockPos.MutableBlockPos checkPos = new BlockPos.MutableBlockPos(); 

	public TileEntityGanController() {
		super(1,0,0);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				// TODO matrix slot
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}

	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiGanController.HEIGHT;
	}

	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public boolean hasRF() {
		return true;
	}

	public boolean isActivated(){
		return activationKey;
	}

	public boolean isProducing(){
		return compressKey;
	}

	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
        this.activationKey = compound.getBoolean("Activation");
        this.compressKey = compound.getBoolean("Production");
        this.tier = compound.getInteger("Tier");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
        compound.setBoolean("Activation", this.activationKey);
        compound.setBoolean("Production", this.compressKey);
        compound.setInteger("Tier", this.tier);
		return compound;
	}



	//----------------------- CUSTOM -----------------------
	private EnumFacing getGanFacing() {
		return worldObj.getBlockState(pos).getValue(GanController.FACING);
	}

	private int getNewX() {
		return pos.offset(getGanFacing(), 1).getX();
	}

	private int getNewZ() {
		return pos.offset(getGanFacing(), 1).getZ();
	}

	private BlockPos towerPos(){
		return new BlockPos(getNewX(), pos.getY(), getNewZ());
	}

	public int getTier(){
		return this.tier == 18 ? 2 : 1;
	}

	public boolean checkTier() {
		return getTier() == 2;
	}

	public int getNitrogenAmount() {
		return checkTier() ? 100 : 10;
	}

	public int getAirAmount() {
		return checkTier() ? 10 : 1;
	}

	public int getRefrigerantAmount() {
		return 10;
	}

	public String getTierName(){
		return checkTier() ? "Reinforced" : "Iron";
	}

	public int getChillingCost() {
		if(getChillerTank() != null && getRefrigerant() != null){
			return 1 + (getTemperature() / 3);
		}
		return 110;
	}

	public int getTemperature() {
		return getRefrigerant().getFluid().getTemperature();
	}

	public int getRedstoneCost() {
		return checkTier() ? 10 : 50;
	}



	//----------------------- CONNECTION -----------------------
	private TileEntityAirCompresser airCompressorTE(){
		checkPos.setPos(pos.offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 90))); // vessel
		if(worldObj.getTileEntity(checkPos) != null && worldObj.getTileEntity(checkPos) instanceof TileEntityAirCompresser){
			TileEntityAirCompresser tile = (TileEntityAirCompresser)worldObj.getTileEntity(checkPos);
			return tile;
		}
		return null;
	}

	private int getCompressedAir(){
		return airCompressorTE() != null ? airCompressorTE().getAir() : 0;
	}

	private TileEntityNitrogenTank nitrogenTankTE(){
		checkPos.setPos(pos.offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 270)));
		checkPos.setPos(checkPos.getX(), checkPos.getY() + 1, checkPos.getZ()); // tank
		if(worldObj.getTileEntity(checkPos) != null && worldObj.getTileEntity(checkPos) instanceof TileEntityNitrogenTank){
			TileEntityNitrogenTank tile = (TileEntityNitrogenTank)worldObj.getTileEntity(checkPos);
			return tile;
		}
		return null;
	}

	private FluidTank getNitrogenTank(){
		return nitrogenTankTE() != null ? nitrogenTankTE().inputTank : null;
	}

	private FluidStack getNitrogen(){
		return getNitrogenTank().getFluid();
	}

	private FluidStack getProduct(){
		return new FluidStack(EnumFluid.pickFluid(EnumFluid.LIQUID_NITROGEN), getNitrogenAmount());
	}

	private TileEntityAirChiller airChillerTE(){
		checkPos.setPos(towerPos().offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 90))); // chiller
		if(worldObj.getTileEntity(checkPos) != null && worldObj.getTileEntity(checkPos) instanceof TileEntityAirChiller){
			TileEntityAirChiller tile = (TileEntityAirChiller)worldObj.getTileEntity(checkPos);
			return tile;
		}
		return null;
	}

	public FluidTank getChillerTank(){
		return airChillerTE() != null ? airChillerTE().inputTank : null;
	}

	public FluidStack getRefrigerant() {
		return getChillerTank().getFluid();
	}

	public boolean hasRefrigerant() {
		return getChillerTank() != null && getChillerTank().getFluid() != null && getChillerTank().getFluidAmount() >= getRefrigerantAmount();
	}

	public boolean isValidTemperature(){
		return  getChillerTank().getFluid().getFluid().getTemperature() <= 300;
	}

	private FluidStack getChillFluid(String fluid) {
		return FluidRegistry.getFluid(fluid) != null ? new FluidStack(FluidRegistry.getFluid(fluid), 1000) : null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!worldObj.isRemote){
			if(isActivated()){
				performSanityCheck();

				if(checkDevices()){
					if(!isProducing()){
						if(canCompress()){
							compress();
						}
					}else{
						if(canProcess()){
							process();
						}
					}
				}
				this.markDirtyClient();
			}
		}
	}

	private boolean canCompress() {
		return (hasRefrigerant() && isValidTemperature())
			&& this.getRedstone() >= getChillingCost()
			&& (airCompressorTE() != null && getCompressedAir() <= airCompressorTE().getAirMax() - getAirAmount());
	}

	private void compress() {
		redstoneCount -= getChillingCost();
		if(airCompressorTE() != null) {
			airCompressorTE().airCount += getAirAmount();
		}
		if(rand.nextInt((10 * getTier()) + tempCoeff()) == 0){
			getChillerTank().getFluid().amount -= getRefrigerantAmount();
			if(getChillerTank().getFluidAmount() <= 0){
				getChillerTank().setFluid(null);
			}
		}
	}

	private int tempCoeff() {
		return 300 - getChillerTank().getFluid().getFluid().getTemperature();
	}

	private boolean canProcess() {
		return this.getRedstone() >= getRedstoneCost()
			&& (getCompressedAir() > 0)
			&& canInsertFluid();
	}

	private boolean canInsertFluid() {
		if(getNitrogenTank() != null){
			if(getNitrogen() == null){
				return true;
			}else{
				if(getNitrogen().isFluidEqual(getProduct()) && getNitrogen().amount <= getNitrogenTank().getCapacity() - getNitrogenAmount()){
					return true;
				}
			}
		}
		return false;
	}

	private void process() {
		redstoneCount -= getRedstoneCost();
		if(airCompressorTE() != null) { airCompressorTE().airCount--;}
		getNitrogenTank().fillInternal(getProduct(), true);
	}



	//----------------------- SANITY CHECK -----------------------
	private void performSanityCheck() {
		if(rand.nextInt(60) == 0){
			checkDevices();
		}
	}

	public boolean checkDevices() {
		int devices = 0; this.tier = 0;
		checkPos.setPos(pos.offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 90))); // vessel
		if(isGanStructure(checkPos, 0) || isGanStructure(checkPos, 6)){ 
			devices++;
			this.tier += getGanTier(checkPos);
		}

		checkPos.setPos(pos.offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 270))); // condenser
		if(isGanStructure(checkPos, 2) || isGanStructure(checkPos, 8)){ 
			devices++;
			this.tier += getGanTier(checkPos);
		}

		for(int x = 0; x < 4; x++){
			checkPos.setPos(getNewX(), pos.getY() + x, getNewZ()); // tower
			if(isGanStructure(checkPos, 5) || isGanStructure(checkPos, 11)){ 
				devices++;
				this.tier += getGanTier(checkPos);
			}
		}

		checkPos.setPos(pos.offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 270)));
		checkPos.setPos(checkPos.getX(), checkPos.getY() + 1, checkPos.getZ()); // tank
		if(isGanStructure(checkPos, 4) || isGanStructure(checkPos, 10)){ 
			devices++;
			this.tier += getGanTier(checkPos);
		}

		checkPos.setPos(towerPos().offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 90))); // chiller
		if(isGanStructure(checkPos, 1) || isGanStructure(checkPos, 7)){ 
			devices++;
			this.tier += getGanTier(checkPos);
		}

		checkPos.setPos(towerPos().offset(getGanFacing().fromAngle(getGanFacing().getHorizontalAngle() + 270))); // turbine
		if(isGanStructure(checkPos, 3) || isGanStructure(checkPos, 9)){ 
			devices++;
			this.tier += getGanTier(checkPos);
		}

		return devices == 9;
	}

	private int getGanTier(MutableBlockPos checkPos) {
		int meta = worldObj.getBlockState(checkPos).getBlock().getMetaFromState(worldObj.getBlockState(checkPos));
		return  meta > 5 ? 2 : 1;
	}

	private boolean isGanStructure(BlockPos checkPos, int i) {
		return worldObj.getBlockState(checkPos) == ModBlocks.ganBlocks.getDefaultState().withProperty(GanBlocks.VARIANT, EnumGan.values()[i]);
	}

}