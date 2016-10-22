package com.globbypotato.rockhounding_chemistry.machines.container;

import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IContainerListener;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerChemicalExtractor extends ContainerBase<TileEntityChemicalExtractor>{
    private int cookTime;
    private int totalCookTime;
    private int powerCount;
    private int powerCurrent;
    private int redstoneCount;
    private int redstoneCurrent;
    private int[] elementsCount = new int [56];
    public ContainerChemicalExtractor(InventoryPlayer playerInventory, TileEntityChemicalExtractor tile){
    	super(playerInventory,tile);
    }
    
	
	@Override
	protected void addOwnSlots() {

		IItemHandler input = tile.getInput();
		IItemHandler output = tile.getOutput();
		
        this.addSlotToContainer(new SlotItemHandler(input, 0, 57, 52));//input
        this.addSlotToContainer(new SlotItemHandler(input, 1, 8, 20));//fuel
        this.addSlotToContainer(new SlotItemHandler(input, 2, 28, 20));//redstone
        this.addSlotToContainer(new SlotItemHandler(input, 3, 57, 74));//consumable
        this.addSlotToContainer(new SlotItemHandler(input, 4, 48, 20));//syngas
        this.addSlotToContainer(new SlotItemHandler(input, 5, 68, 20));//fluo

        
        //cabinets
		for(int x=0; x<=6; x++){
			for(int y=0; y<=7; y++){
		        this.addSlotToContainer(new SlotItemHandler(output, (x*8)+y, 88 + (19 * y) , 22 + (21* x)));//output
			}
		}
	}

	@Override
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
            if (this.redstoneCount != this.tile.getField(4)){
                icontainerlistener.sendProgressBarUpdate(this, 4, this.tile.getField(4));
            }
            if (this.redstoneCurrent != this.tile.getField(5)){
                icontainerlistener.sendProgressBarUpdate(this, 5, this.tile.getField(5));
            }
            for (int el = 0; el < elementsCount.length; el++){
                if (this.elementsCount[el] != this.tile.getField(6+el)){
                    icontainerlistener.sendProgressBarUpdate(this, el+6, this.tile.getField(6+el));
                }
            }

        }
        this.cookTime = this.tile.getField(2);
        this.totalCookTime = this.tile.getField(3);
        this.powerCount = this.tile.getField(0);
        this.powerCurrent = this.tile.getField(1);
        this.redstoneCount = this.tile.getField(4);
        this.redstoneCurrent = this.tile.getField(5);
        for (int el = 0; el < elementsCount.length; el++){
            this.elementsCount[el] = this.tile.getField(6+el);
        }
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int id, int data){
        this.tile.setField(id, data);
    }
}