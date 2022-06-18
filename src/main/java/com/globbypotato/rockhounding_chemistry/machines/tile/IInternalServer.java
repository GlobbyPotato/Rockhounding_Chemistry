package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public abstract interface IInternalServer {
	String tag_device = "Device";
	String tag_cycle = "Cycle";
	String tag_done = "Done";
	String tag_type = "Type";
	String tag_amount = "Amount";
	String tag_recipe = "Recipe";
	String tag_filter_fluid = "FilterFluid";
	String tag_filter_item = "FilterStack";

	// set the server to handle the machine
	public default void initializeServer(boolean isRepeatable, TEServer server, int code, float serverStep, float serverMax) {
		if(server != null){
			setServerCode(server, code, serverStep, serverMax);
			checkRepeatable(isRepeatable, server, code);
			assignRanges(server, code, serverStep, serverMax);
			loadServerStatus();
		}
	}

	//assign min/max, steps and recipe indexes
	public default void assignRanges(TEServer server, int code, float serverStep, float serverMax) {
		if(server.recipeStep != serverStep) {
			server.recipeStep = serverStep;
		}
		if(server.recipeMax != serverMax) {
			server.recipeMax = serverMax;
		}

	}

	// load server data
	public default void loadServerStatus(){
		
	}

	//apply the device code to the server
	public default void setServerCode(TEServer server, int code, float serverStep, float serverMax) {
		if(server.servedDevice() != code){
			server.applyServer(code, serverStep, serverMax);
		}
	}

	//decide if the cycle is repeatable
	public default void checkRepeatable(boolean isRepeatable, TEServer server, int code){
		isRepeatable = false;
		for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
			ItemStack fileSlot = server.inputSlot(x);
			if(fileSlot.hasTagCompound()){
				NBTTagCompound tag = fileSlot.getTagCompound();
				if(isWrittenFile(tag)){
					if(isCorrectDevice(tag, code)){
						if(getCycle(tag)){
							isRepeatable = true;
							break;
						}
					}
				}
			}
		}
	}

	//reset the consumed recipes
	public default void resetFiles(TEServer server, int code){
		for(int k = 0; k < TEServer.FILE_SLOTS.length; k++){
			ItemStack fileSlot = server.inputSlot(k);
			if(TEServer.isValidFile(fileSlot)){
				if(fileSlot.hasTagCompound()){
					NBTTagCompound tag = fileSlot.getTagCompound();
					if(isWrittenFile(tag)){
						if(isCorrectDevice(tag, code)){
							if(getCycle(tag)){
								if(getDone(tag) <= 0){
									tag.setInteger(tag_done, getAmount(tag));
								}
							}
						}
					}
				}
			}
		}
	}

	//decide if the server can be used or is skipped
	public default boolean handleServer(TEServer server, int currentFile) {
		return isServerOpen(server, currentFile) || isServedClosed(server);
	}

	// the server is turned off
	public default boolean isServedClosed(TEServer server) {
		return server == null || (server != null && !server.isActive());
	}

	//the server is turned on and has recipes to do
	public default boolean isServerOpen(TEServer server, int currentFile){
		return server != null && server.isActive() && hasActiveFile(currentFile);
	}

	//if the item being processed is the requested one
	public default boolean handleFilter(ItemStack inputstack, ItemStack filterstack) {
		if(!filterstack.isEmpty()){
			if(CoreUtils.isMatchingIngredient(inputstack, filterstack)){
				return true;
			}
			return false;
		}
		return true;
	}

	// a file has been selected
	public default boolean hasActiveFile(int currentFile){
		return currentFile > -1;
	}

	// check if can update the server
	public default void updateServer(TEServer server, int currentFile) {
		if(isServerOpen(server, currentFile)){
			refreshServer(server, currentFile);
		}
	}

	// update the server after a recipe is done
	public default void refreshServer(TEServer server, int currentFile) {
		if(hasActiveFile(currentFile)){
			NBTTagCompound tag = server.inputSlot(currentFile).getTagCompound();
			if(isWrittenFile(tag)){
				int doRecipe = getDone(tag) - 1;
				tag.setInteger(tag_done, doRecipe);
			}
		}
	}

	//check if the file has all parameters
	public default boolean isWrittenFile(NBTTagCompound tag) {
		return hasDevice(tag) && hasCycle(tag) && hasRecipe(tag) && hasDone(tag);
	}

	public default boolean hasType(NBTTagCompound tag) {
		return tag.hasKey(tag_type);
	}
	public default int getType(NBTTagCompound tag) {
		return tag.getInteger(tag_type);
	}

	public default boolean hasAmount(NBTTagCompound tag) {
		return tag.hasKey(tag_amount);
	}
	public default int getAmount(NBTTagCompound tag) {
		return tag.getInteger(tag_amount);
	}

	public default boolean hasDone(NBTTagCompound tag) {
		return tag.hasKey(tag_done);
	}
	public default int getDone(NBTTagCompound tag) {
		return tag.getInteger(tag_done);
	}

	public default boolean hasRecipe(NBTTagCompound tag) {
		return tag.hasKey(tag_recipe);
	}
	public default int getRecipe(NBTTagCompound tag) {
		return tag.getInteger(tag_recipe);
	}

	public default boolean hasCycle(NBTTagCompound tag) {
		return tag.hasKey(tag_cycle);
	}
	public default boolean getCycle(NBTTagCompound tag) {
		return tag.getBoolean(tag_cycle);
	}

	public default boolean hasDevice(NBTTagCompound tag) {
		return tag.hasKey(tag_device);
	}
	public default int getDevice(NBTTagCompound tag) {
		return tag.getInteger(tag_device);
	}
	public default boolean isCorrectDevice(NBTTagCompound tag, int device) {
		return tag.getInteger(tag_device) == device;
	}

	public default boolean hasFilterFluid(NBTTagCompound tag) {
		return tag.hasKey(tag_filter_fluid);
	}
	public default FluidStack getFilterFluid(NBTTagCompound tag) {
		return FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(tag_filter_fluid));
	}

	public default boolean hasFilterItem(NBTTagCompound tag) {
		return tag.hasKey(tag_filter_item);
	}
	public default ItemStack getFilterItem(NBTTagCompound tag) {
		return new ItemStack(tag.getCompoundTag(tag_filter_item));
	}

}