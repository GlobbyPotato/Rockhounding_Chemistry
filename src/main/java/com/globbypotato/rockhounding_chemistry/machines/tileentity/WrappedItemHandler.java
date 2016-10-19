package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;

/*
 * With permission from WillieWillus, originally from ProjectE 
 * https://github.com/sinkillerj/ProjectE/blob/MC1.10.x/src/main/java/moze_intel/projecte/gameObjs/tiles/WrappedItemHandler.java
 */
public class WrappedItemHandler implements IItemHandlerModifiable
{

    private final IItemHandlerModifiable compose;
    private final WriteMode mode;

    public WrappedItemHandler(IItemHandlerModifiable compose, WriteMode mode)
    {
        this.compose = compose;
        this.mode = mode;
    }

    @Override
    public int getSlots()
    {
        return compose.getSlots();
    }

    @Override
    public ItemStack getStackInSlot(int slot)
    {
        return compose.getStackInSlot(slot);
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate)
    {
        if (mode == WriteMode.IN || mode == WriteMode.IN_OUT)
            return compose.insertItem(slot, stack, simulate);
        else return stack;
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate)
    {
        if (mode == WriteMode.OUT || mode == WriteMode.IN_OUT)
            return compose.extractItem(slot, amount, simulate);
        else return null;
    }

    @Override
    public void setStackInSlot(int slot, ItemStack stack)
    {
        compose.setStackInSlot(slot, stack);
    }

    enum WriteMode
    {
        IN,
        OUT,
        IN_OUT,
        NONE
    }
}