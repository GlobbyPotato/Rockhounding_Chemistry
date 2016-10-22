package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralSizer extends ContainerBase<TileEntityMineralSizer>{
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;

	public ContainerMineralSizer(IInventory playerInventory, TileEntityMineralSizer te) {
		super(playerInventory,te);
	}
	
	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		
		 this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 28));//input
	        this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
	        this.addSlotToContainer(new SlotItemHandler(input, 2, 81, 48));//consumable
	        this.addSlotToContainer(new SlotItemHandler(input, 3, 23, 74));//inductor
	        
	        this.addSlotToContainer(new SlotItemHandler(output, 0, 120, 48));//output
	        this.addSlotToContainer(new SlotItemHandler(output, 1, 144, 48));//secondary
	        this.addSlotToContainer(new SlotItemHandler(output, 2, 120, 72));//waste

	}
	
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