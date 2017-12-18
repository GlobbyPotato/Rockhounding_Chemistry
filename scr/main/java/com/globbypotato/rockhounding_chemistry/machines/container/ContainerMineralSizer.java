package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMineralSizer;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerMineralSizer extends ContainerBase<TileEntityMineralSizer>{
	public ContainerMineralSizer(IInventory playerInventory, TileEntityMineralSizer te) {
		super(playerInventory,te);
	}

	@Override
	protected void addOwnSlots() {
		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		IItemHandler template = tile.getTemplate();

		this.addSlotToContainer(new SlotItemHandler(input, 0, 60, 20));//input
		this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 8));//fuel
		this.addSlotToContainer(new SlotItemHandler(input, 2, 33, 47));//consumable
		this.addSlotToContainer(new SlotItemHandler(input, 3, 8, 82));//upgrade
		this.addSlotToContainer(new SlotItemHandler(output, 0, 60, 75));//output
		this.addSlotToContainer(new SlotItemHandler(output, 1, 38, 99));//secondary
		this.addSlotToContainer(new SlotItemHandler(output, 2, 82, 99));//waste

		this.addSlotToContainer(new SlotItemHandler(template, 0, 153, 5));//hi
		this.addSlotToContainer(new SlotItemHandler(template, 1, 153, 102));//lo
		this.addSlotToContainer(new SlotItemHandler(template, 2, 39, 26));//activation

	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
    	if(slot == 7){
    		if(this.tile.hasComminution() && this.tile.getComminution() < 15 && !this.tile.isPowered()){
    			this.tile.comminution++;
    		}
			doClickSound(player, tile.getWorld(), tile.getPos());
    		return null;
    	}else if(slot == 8){ 
    		if(this.tile.hasComminution() && this.tile.getComminution() > 0 && !this.tile.isPowered()){
    			this.tile.comminution--;
    		}
			doClickSound(player, tile.getWorld(), tile.getPos());
        	return null;
    	}else if(slot == 9){ 
   			this.tile.activation = !this.tile.activation;
			doClickSound(player, tile.getWorld(), tile.getPos());
        	return null;
    	}else{
    		return super.slotClick(slot, dragType, clickTypeIn, player);
    	}
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 7, reverseDirection)){
			return true;
		}else{
			return super.mergeItemStack(stack, 10, endIndex, reverseDirection);
		}
    }

}