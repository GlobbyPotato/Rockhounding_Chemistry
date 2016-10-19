package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralAnalyzer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralAnalyzer extends ContainerBase<TileEntityMineralAnalyzer>{
	private int cookTime;
	private int totalCookTime;
	private int powerCount;
	private int powerCurrent;

	public ContainerMineralAnalyzer(InventoryPlayer playerInventory, TileEntityMineralAnalyzer tile){
		super(playerInventory, tile);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 54, 24));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 90, 46));//consumable
		this.addSlotToContainer(new SlotItemHandler(input, 3, 144, 24));//sulf
		this.addSlotToContainer(new SlotItemHandler(input, 4, 144, 46));//chlo
		this.addSlotToContainer(new SlotItemHandler(input, 5, 144, 68));//fluo
		this.addSlotToContainer(new SlotItemHandler(input, 6, 23, 74));//inductor
		
		this.addSlotToContainer(new SlotItemHandler(output, 0, 90, 72));//output
	}


	public void addListener(IContainerListener listener){
		super.addListener(listener);
		//listener.sendAllWindowProperties(this, this.tile);
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	@Override
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for (int i = 0; i < this.listeners.size(); ++i){
			IContainerListener icontainerlistener = (IContainerListener)this.listeners.get(i);
			if (this.powerCount != this.tile.getField(0)){
				icontainerlistener.sendProgressBarUpdate(this, 0, this.tile.getField(0));
			}
			if (this.powerCurrent != this.tile.getField(1)){
				icontainerlistener.sendProgressBarUpdate(this, 1, this.tile.getField(1));
			}
			if (this.cookTime != this.tile.getField(2)){
				icontainerlistener.sendProgressBarUpdate(this, 2, this.tile.getField(2));
			}
			if (this.totalCookTime != this.tile.getField(3)){
				icontainerlistener.sendProgressBarUpdate(this, 3, this.tile.getField(3));
			}
		}
		this.cookTime = this.tile.getField(2);
		this.totalCookTime = this.tile.getField(3);
		this.powerCount = this.tile.getField(0);
		this.powerCurrent = this.tile.getField(1);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data){
		this.tile.setField(id, data);
	}

}