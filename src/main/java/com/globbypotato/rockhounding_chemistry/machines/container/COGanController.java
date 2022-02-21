package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumAirGases;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GanPlantRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGanController;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class COGanController extends ContainerBase<TEGanController>{
	public COGanController(IInventory playerInventory, TEGanController te) {
		super(playerInventory,te);
	}

	@Override
	public void addOwnSlots() {
		IItemHandler template = this.tile.getTemplate();
		IItemHandler upgrade = this.tile.getUpgrade();

		this.addSlotToContainer(new SlotItemHandler(upgrade, 0, 8,  68));//speed

    	this.addSlotToContainer(new SlotItemHandler(template, 0, 80, 96));//activation
		this.addSlotToContainer(new SlotItemHandler(template, 1,  8, 29));//N2
		this.addSlotToContainer(new SlotItemHandler(template, 2, 27, 29));//O2
		this.addSlotToContainer(new SlotItemHandler(template, 3, 46, 29));//X
	}

	@Override
	public ItemStack slotClick(int slot, int dragType, ClickType clickTypeIn, EntityPlayer player){
		if(slot == 1){ 
			this.tile.activation = !this.tile.activation;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}else if(slot == 2){ 
			if(!GanPlantRecipes.inhibited_gases.contains(EnumAirGases.name(6))){
				this.tile.enableN = !this.tile.enableN;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
    	}else if(slot == 3){ 
			if(!GanPlantRecipes.inhibited_gases.contains(EnumAirGases.name(7))){
				this.tile.enableO = !this.tile.enableO;
				doClickSound(player, this.tile.getWorld(), this.tile.getPos());
			}
    		return ItemStack.EMPTY;
    	}else if(slot == 4){ 
			this.tile.enableX = !this.tile.enableX;
			doClickSound(player, this.tile.getWorld(), this.tile.getPos());
    		return ItemStack.EMPTY;
    	}
		return super.slotClick(slot, dragType, clickTypeIn, player);
	}

	@Override
	protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection){
		if(super.mergeItemStack(stack, startIndex, 1, reverseDirection)){
			return true;
		}
		return super.mergeItemStack(stack, 5, endIndex, reverseDirection);
    }

}