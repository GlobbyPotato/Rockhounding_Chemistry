package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.items.MineralShards;
import com.globbypotato.rockhounding_chemistry.items.tools.Petrographer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiPetrographerTable;
import com.globbypotato.rockhounding_chemistry.utils.ToolUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class TileEntityPetrographerTable extends TileEntityMachineInv {
	public static final int TOOL_SLOT = 0;
	public static final int ORE_SLOT = 1;
	public static final int SHARD_SLOT = 2;

	public TileEntityPetrographerTable(){
		super(3,0);

		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == TOOL_SLOT && ItemStack.areItemsEqualIgnoreDurability(ToolUtils.petrographer, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == ORE_SLOT && insertingStack.getItem() == Item.getItemFromBlock(ModBlocks.mineralOres) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SHARD_SLOT && insertingStack.getItem() instanceof MineralShards ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}

	@Override
	public int getGUIHeight() {
		return GuiPetrographerTable.HEIGHT;
	}

	@Override
	public void update() {
		if(hasPetro()){
			if(input.getStackInSlot(TOOL_SLOT).hasTagCompound()){
		    	int nFlavor = input.getStackInSlot(TOOL_SLOT).getTagCompound().getInteger("nFlavor");
		    	int nSpecimen = input.getStackInSlot(TOOL_SLOT).getTagCompound().getInteger("nSpecimen");

		    	if(isUndefined()){
		    		input.getStackInSlot(TOOL_SLOT).getTagCompound().setInteger("nFlavor", 0);
		    		input.getStackInSlot(TOOL_SLOT).getTagCompound().setInteger("nSpecimen", -1);
		    	}else if(isCategory()){
		    		input.getStackInSlot(TOOL_SLOT).getTagCompound().setInteger("nFlavor", input.getStackInSlot(ORE_SLOT).getItemDamage());
		    		if(hasMatchingSpecimen()){
			    		input.getStackInSlot(TOOL_SLOT).getTagCompound().setInteger("nSpecimen", input.getStackInSlot(SHARD_SLOT).getItemDamage());
		    		}else{
			    		input.getStackInSlot(TOOL_SLOT).getTagCompound().setInteger("nSpecimen", -1);
		    		}
		    	}
			}else{
				Petrographer.setItemNbt(input.getStackInSlot(TOOL_SLOT));
			}
		}
	}

	private boolean hasMatchingSpecimen() {
		return input.getStackInSlot(SHARD_SLOT) != null && input.getStackInSlot(SHARD_SLOT).getItem() == ToolUtils.specimenList[input.getStackInSlot(ORE_SLOT).getItemDamage()];
	}

	private boolean isCategory() {
		return input.getStackInSlot(ORE_SLOT) != null && input.getStackInSlot(ORE_SLOT).getItem() == Item.getItemFromBlock(ModBlocks.mineralOres) && input.getStackInSlot(ORE_SLOT).getItemDamage() > 0;
	}

	private boolean isUndefined() {
		return input.getStackInSlot(ORE_SLOT) != null && input.getStackInSlot(ORE_SLOT).getItem() == Item.getItemFromBlock(ModBlocks.mineralOres) && input.getStackInSlot(ORE_SLOT).getItemDamage() == 0;
	}

	private boolean hasPetro() {
		return input.getStackInSlot(TOOL_SLOT) != null && ItemStack.areItemsEqualIgnoreDurability(ToolUtils.petrographer, input.getStackInSlot(TOOL_SLOT));
	}
}