package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.machines.gui.GuiEarthBreaker;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityEarthBreaker extends TileEntityMachineEnergy {

	private ItemStackHandler template = new TemplateStackHandler(1);
	public int takenRF = 1000;
    public boolean spinning;
	public int chargeCount = 0;
	public int chargeMax = 5000;

	public static boolean dropBedrock;

	public TileEntityEarthBreaker() {
		super(0,0,0);
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiEarthBreaker.HEIGHT;
	}

	@Override
	public boolean hasRF(){
		return true;	
	}

	public int getMaxCookTime(){
		return 2000;
	}

	public boolean isSpinning(){
		return isActive() && canDrill() && this.cookTime > 0;
	}

	@Override
	public boolean canInduct() {
		return false;
	}

	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.chargeCount = compound.getInteger("ChargeCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("ChargeCount", this.chargeCount);
		return compound;
	}


	
	//----------------------- CUSTOM -----------------------
	private void spinState() {
		if(this.worldObj.isRemote){
			if (isSpinning() != this.spinning) {
				this.spinning = isSpinning();
				this.worldObj.notifyBlockOfStateChange(this.pos, this.worldObj.getBlockState(this.pos).getBlock());
				this.worldObj.markBlockRangeForRenderUpdate(this.pos, this.pos);
			}
		}
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		acceptEnergy();
		if(!this.worldObj.isRemote){
			if(canDrill()){
				process();
				this.markDirtyClient();
			}else{
				handleParameters();
			}
		}
		spinState();
	}

	private boolean canDrill() {
		return isActive()
			&& this.chargeCount >= 2
			&& hasBedrock();
	}

	private void process() {
		this.cookTime++;
		this.chargeCount -= 2;
		if(this.cookTime >= getMaxCookTime()) {
			this.cookTime = 0; 
			handleOutput();
		}
	}

	private void handleOutput() {
		if(this.worldObj.getBlockState(this.pos.offset(EnumFacing.UP)).getBlock() == Blocks.BEDROCK){
			this.worldObj.destroyBlock(this.pos.offset(EnumFacing.UP), false);
			dropBedrock(this.pos.offset(EnumFacing.UP.getOpposite()));
		}else{
			if(this.worldObj.getBlockState(this.pos.offset(EnumFacing.DOWN)).getBlock() == Blocks.BEDROCK){
				this.worldObj.destroyBlock(this.pos.offset(EnumFacing.DOWN), false);
				dropBedrock(this.pos.offset(EnumFacing.DOWN.getOpposite()));
			}
		}
	}

	private void dropBedrock(BlockPos pos) {
		EntityItem bedrock = new EntityItem(this.worldObj, pos.getX() + this.rand.nextFloat(), pos.getY() + 0.5D, pos.getZ() + this.rand.nextFloat(), new ItemStack(Blocks.BEDROCK));
		if(!this.worldObj.isRemote){
			this.worldObj.spawnEntityInWorld(bedrock);
		}
	}

	private void handleParameters() {
		if(this.getRedstone() >= this.takenRF && this.chargeCount < this.chargeMax){
			this.redstoneCount -= this.takenRF; 
			this.chargeCount++;
			this.markDirtyClient();
		}
	}

	private boolean hasBedrock() {
		return this.worldObj.getBlockState(this.pos.offset(EnumFacing.UP)).getBlock() == Blocks.BEDROCK || this.worldObj.getBlockState(this.pos.offset(EnumFacing.DOWN)).getBlock() == Blocks.BEDROCK;
	}

}