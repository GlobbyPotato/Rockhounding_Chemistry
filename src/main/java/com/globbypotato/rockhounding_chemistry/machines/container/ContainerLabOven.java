package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.CommonProxy;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerLabOven extends ContainerBase<TileEntityLabOven> {
	private int cookTime;
	private int totalCookTime;
	private int powerCount;
	private int powerCurrent;
	private int redstoneCount;
	private int redstoneCurrent;
	Slot templateSlot;

	public ContainerLabOven(InventoryPlayer playerInventory, TileEntityLabOven tile){
		super(playerInventory,tile);

	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 44, 59));//input solute
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 116, 59));//input solvent
		this.addSlotToContainer(new SlotItemHandler(input, 3, 80, 59));//output
		this.addSlotToContainer(new SlotItemHandler(input, 4, 152, 20));//input redstone
		this.addSlotToContainer(new SlotItemHandler(input, 5, 28, 20));//Previous recipe
		this.addSlotToContainer(new SlotItemHandler(input, 6, 132, 20));//Next recipe

		templateSlot = this.addSlotToContainer(new SlotItemHandler(template, 0, 44, 42));//solute template
	}

	@Override
	public void addListener(IContainerListener listener){
		super.addListener(listener);
		// listener.sendAllWindowProperties(this, this.tile);
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
			if (this.redstoneCount != this.tile.getField(4)){
				icontainerlistener.sendProgressBarUpdate(this, 4, this.tile.getField(4));
			}
			if (this.redstoneCurrent != this.tile.getField(5)){
				icontainerlistener.sendProgressBarUpdate(this, 5, this.tile.getField(5));
			}
		}
		this.cookTime = this.tile.getField(2);
		this.totalCookTime = this.tile.getField(3);
		this.powerCount = this.tile.getField(0);
		this.powerCurrent = this.tile.getField(1);
		this.redstoneCount = this.tile.getField(4);
		this.redstoneCurrent = this.tile.getField(5);
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data){
		this.tile.setField(id, data);
	}


	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 7){
			return null;
		}else if(slot == 5 && this.tile.recipeDisplayIndex >= 0){
			this.tile.recipeDisplayIndex--;
			this.tile.resetGrid();
			this.tile.recipeScan = true;
			return null;
		}else if(slot == 6 && this.tile.recipeDisplayIndex < CommonProxy.labOvenRecipes.size()-1){
			this.tile.recipeDisplayIndex++;
			this.tile.resetGrid();
			this.tile.recipeScan = true;
			return null;
		}else{
			return super.slotClick(slot, dragType, clickTypeIn, player);
		}
	}
	
	//Prevent from merging into stack 7, the stack with the recipe template
	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection)
    {
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}
		else {
			return super.mergeItemStack(stack, 8, endIndex, reverseDirection);
		}
    }
}