package com.globbypotato.rockhounding_chemistry.machines.tile.pipelines;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ElementsCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityBase;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

public class TEDustlinePump extends TileEntityBase  implements ITickable{
	public ArrayList<BlockPos> dustSenders = new ArrayList<BlockPos>();
	public ArrayList<BlockPos> dustUsers = new ArrayList<BlockPos>();
	public ArrayList<BlockPos> ducts = new ArrayList<BlockPos>();
	public int numducts = 0;
	public boolean activation;
	public int delay;
	private int countDelay = 0;


	//---------------- I/O ----------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
        this.activation = compound.getBoolean("Activation");
        this.delay = compound.getInteger("Delay");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
        compound.setBoolean("Activation", isActive());
        compound.setInteger("Delay", getDelay());
		return compound;
	}



	// ----------------------- CUSTOM -----------------------
	public boolean canExtract(FluidStack extractedFluid, FluidStack receivingFluid) {
		return false;
	}

	public int getFlow(){
		return 10;
	}

	public boolean isActive(){
		return this.activation;
	}

	public int getDelay() {
		return this.delay;
	}

	public boolean isAnyPipe(TileEntity checkTile) {
		return checkTile instanceof TEDustlinePump;
	}

	public static String getName(){
		return "dustline_pump";
	}



	// ----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
//			System.out.println(this.dustSenders.size() + " " + this.dustUsers.size());
			if(isActive()){
				if(countDelay >= getDelay()){
					//set starting pos
					if(this.ducts.size() == 0){
						this.ducts.add(this.pos);
					}
					//search connections
					if(this.numducts < this.ducts.size()){
						addConnection(this.ducts.get(this.numducts));
						this.numducts++;
					}else{
						//serve handlers
						handleValves();
						this.numducts = 0;
						this.ducts.clear();
						this.dustUsers.clear();
						this.dustSenders.clear();
					}
					countDelay = 0;
				}else{
					countDelay++;
				}
			}
			this.markDirtyClient();
		}
	}

	private void addConnection(BlockPos duct) {
		for(EnumFacing facing : EnumFacing.values()){
			BlockPos checkPos = duct.offset(facing);
			IBlockState checkState = this.world.getBlockState(checkPos);
			if(checkState != null && checkState.getBlock() == ModBlocks.DUSTLINE_DUCT){
				if(!this.ducts.contains(checkPos)){
					this.ducts.add(checkPos);
				}
		    }

			TileEntity tile = this.world.getTileEntity(checkPos);
			if(tile != null){ 
				if(tile instanceof TEElementsCabinetBase || tile instanceof TEMaterialCabinetBase){
					TileEntityInv valveTile = (TileEntityInv)tile;
					if(valveTile.getFacing() == facing.getOpposite()){
						if(!this.dustUsers.contains(checkPos)){
							this.dustUsers.add(checkPos);
						}
					}
					if(this.numducts == 0){
						if(valveTile.getFacing() == isFacingAt(facing, 270) || valveTile.getFacing() == isFacingAt(facing, 90)){
							if(!this.dustSenders.contains(checkPos)){
								this.dustSenders.add(checkPos);
							}
						}
					}
				}
			}
		}
	}

	private void handleValves() {
		if(this.dustSenders.size() > 0 && this.dustUsers.size() > 0){// if has any user
			for(BlockPos sender : dustSenders){//for every sender
				TileEntityInv senderTile = (TileEntityInv)this.world.getTileEntity(sender);
				if(cabinetIsInitialized(sender)){//make sure sender is valid
					for(BlockPos receiver : this.dustUsers){//for every receiver
						TileEntityInv receiverTile = (TileEntityInv)this.world.getTileEntity(receiver);
						if(cabinetIsInitialized(receiver)){//make sure receiver is valid
							if(senderTile instanceof TEElementsCabinetBase && receiverTile instanceof TEElementsCabinetBase) {
								TEElementsCabinetBase eSender = (TEElementsCabinetBase)senderTile;
								TEElementsCabinetBase eReceiver = (TEElementsCabinetBase)receiverTile;
								for(int e = 0; e < eSender.MATERIAL_LIST.size(); e++) {
									int senderAmount = eSender.MATERIAL_LIST.get(e).getAmount();
									String senderSymbol = eSender.MATERIAL_LIST.get(e).getSymbol();
									String senderOredict = eSender.MATERIAL_LIST.get(e).getOredict();
									String senderName = eSender.MATERIAL_LIST.get(e).getName();
									boolean senderCanSend = eSender.MATERIAL_LIST.get(e).getExtraction();
									if(senderAmount >= getFlow() && senderCanSend) {
										for(int k = 0; k < eReceiver.MATERIAL_LIST.size(); k++) {
											int receiverAmount = eReceiver.MATERIAL_LIST.get(k).getAmount();
											String receiverSymbol = eReceiver.MATERIAL_LIST.get(k).getSymbol();
											String receiverOredict = eReceiver.MATERIAL_LIST.get(k).getOredict();
											String receiverName = eReceiver.MATERIAL_LIST.get(k).getName();
											boolean receiverCanSend = eReceiver.MATERIAL_LIST.get(k).getExtraction();
											if(receiverAmount <= (ModConfig.extractorCap - getFlow()) && senderOredict.matches(receiverOredict)) {
												int addedAmount = receiverAmount += getFlow(); 
												ElementsCabinetRecipe addedElement = new ElementsCabinetRecipe(receiverSymbol, receiverOredict, receiverName, addedAmount, receiverCanSend);
												eReceiver.MATERIAL_LIST.set(k, addedElement);

												int takenAmount = senderAmount -= getFlow(); 
												ElementsCabinetRecipe takenElement = new ElementsCabinetRecipe(senderSymbol, senderOredict, senderName, takenAmount, senderCanSend);
												eSender.MATERIAL_LIST.set(e, takenElement);
											}
										}
									}
								}
							}
							if(senderTile instanceof TEMaterialCabinetBase && senderTile instanceof TEMaterialCabinetBase) {
								TEMaterialCabinetBase eSender = (TEMaterialCabinetBase)senderTile;
								TEMaterialCabinetBase eReceiver = (TEMaterialCabinetBase)receiverTile;
								for(int e = 0; e < eSender.MATERIAL_LIST.size(); e++) {
									int senderAmount = eSender.MATERIAL_LIST.get(e).getAmount();
									String senderSymbol = eSender.MATERIAL_LIST.get(e).getSymbol();
									String senderOredict = eSender.MATERIAL_LIST.get(e).getOredict();
									String senderName = eSender.MATERIAL_LIST.get(e).getName();
									boolean senderCanSend = eSender.MATERIAL_LIST.get(e).getExtraction();
									if(senderAmount >= getFlow() && senderCanSend) {
										for(int k = 0; k < eReceiver.MATERIAL_LIST.size(); k++) {
											int receiverAmount = eReceiver.MATERIAL_LIST.get(k).getAmount();
											String receiverSymbol = eReceiver.MATERIAL_LIST.get(k).getSymbol();
											String receiverOredict = eReceiver.MATERIAL_LIST.get(k).getOredict();
											String receiverName = eReceiver.MATERIAL_LIST.get(k).getName();
											boolean receiverCanSend = eReceiver.MATERIAL_LIST.get(k).getExtraction();
											if(receiverAmount <= (ModConfig.extractorCap - getFlow()) && senderOredict.matches(receiverOredict)) {
												int addedAmount = receiverAmount += getFlow(); 
												MaterialCabinetRecipe addedElement = new MaterialCabinetRecipe(receiverSymbol, receiverOredict, receiverName, addedAmount, receiverCanSend);
												eReceiver.MATERIAL_LIST.set(k, addedElement);

												int takenAmount = senderAmount -= getFlow(); 
												MaterialCabinetRecipe takenElement = new MaterialCabinetRecipe(senderSymbol, senderOredict, senderName, takenAmount, senderCanSend);
												eSender.MATERIAL_LIST.set(e, takenElement);
											}
										}
									}
								}
							}
						}else{
							this.dustUsers.remove(receiver);
							break;
						}
					}
				}else{
					this.dustSenders.remove(sender);
					break;
				}
			}
		}
	}

	private boolean cabinetIsInitialized(BlockPos sender) {
		TileEntityInv cabinet = (TileEntityInv)this.world.getTileEntity(sender);
		if(cabinet != null && cabinet instanceof TEElementsCabinetBase){
			TEElementsCabinetBase elements = (TEElementsCabinetBase)this.world.getTileEntity(sender);
			if(elements != null && elements.MATERIAL_LIST.size() > 0) {
				return true;
			}
		}else if(cabinet != null && cabinet instanceof TEMaterialCabinetBase){
			TEMaterialCabinetBase materials = (TEMaterialCabinetBase)this.world.getTileEntity(sender);
			if(materials != null && materials.MATERIAL_LIST.size() > 0) {
				return true;
			}
		}
		return false;
	}

	public EnumFacing isFacingAt(EnumFacing facing, int side){
		return EnumFacing.fromAngle(facing.getHorizontalAngle() + side);
	}
}