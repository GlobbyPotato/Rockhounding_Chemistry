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
	public boolean activator;
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

	public boolean isActive(){
		return activator;
	}

	public boolean isSpinning(){
		return isActive() && canDrill() && cookTime > 0;
	}

	@Override
	public boolean canInduct() {
		return false;
	}

	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.activator = compound.getBoolean("Activator");
		this.chargeCount = compound.getInteger("ChargeCount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Activator", this.activator);
		compound.setInteger("ChargeCount", this.chargeCount);
		return compound;
	}


	
	//----------------------- CUSTOM -----------------------
	private void spinState() {
		if(worldObj.isRemote){
			if (isSpinning() != spinning) {
				spinning = isSpinning();
				worldObj.notifyBlockOfStateChange(pos, worldObj.getBlockState(pos).getBlock());
				worldObj.markBlockRangeForRenderUpdate(pos, pos);
			}
		}
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!worldObj.isRemote){
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
		cookTime++;
		this.chargeCount -= 2;
		if(cookTime >= getMaxCookTime()) {
			cookTime = 0; 
			handleOutput();
		}
	}

	private void handleOutput() {
		if(worldObj.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.BEDROCK){
			worldObj.destroyBlock(pos.offset(EnumFacing.UP), false);
			dropBedrock(pos.offset(EnumFacing.UP.getOpposite()));
		}else{
			if(worldObj.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() == Blocks.BEDROCK){
				worldObj.destroyBlock(pos.offset(EnumFacing.DOWN), false);
				dropBedrock(pos.offset(EnumFacing.DOWN.getOpposite()));
			}
		}
	}

	private void dropBedrock(BlockPos pos) {
		EntityItem bedrock = new EntityItem(worldObj, pos.getX() + rand.nextFloat(), pos.getY() + 0.5D, pos.getZ() + rand.nextFloat(), new ItemStack(Blocks.BEDROCK));
		if(!worldObj.isRemote){
			worldObj.spawnEntityInWorld(bedrock);
		}
	}

	private void handleParameters() {
		if(this.redstoneCount >= takenRF && this.chargeCount < this.chargeMax){
			this.redstoneCount -= takenRF; 
			this.chargeCount++;
			this.markDirtyClient();
		}
	}

	private boolean hasBedrock() {
		return worldObj.getBlockState(pos.offset(EnumFacing.UP)).getBlock() == Blocks.BEDROCK || worldObj.getBlockState(pos.offset(EnumFacing.DOWN)).getBlock() == Blocks.BEDROCK;
	}

}