package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public abstract interface IInternalServer {

	// set de server to handle the machine
	public default void initializeServer(boolean isRepeatable, boolean hasServer, TEServer server, int code) {
		if(hasServer){
			setServerCode(server, code);
			checkRepeatable(isRepeatable, server, code);
			loadServerStatus();
		}
	}

	// load server data
	public default void loadServerStatus(){
		
	}

	//apply the device code to the server
	public default void setServerCode(TEServer server, int code) {
		if(server.servedDevice() != code){
			server.applyServer(code);
		}
	}

	//decide if the cycle is repeatable
	public default void checkRepeatable(boolean isRepeatable, TEServer server, int code){
		isRepeatable = false;
		for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
			ItemStack fileSlot = server.inputSlot(x).copy();
			if(fileSlot.hasTagCompound()){
				NBTTagCompound tag = fileSlot.getTagCompound();
				if(isValidFile(tag)){
					if(tag.getInteger("Device") == code){
						if(tag.getBoolean("Cycle")){
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
			if(!fileSlot.isEmpty() && fileSlot.isItemEqual(BaseRecipes.server_file)){
				if(fileSlot.hasTagCompound()){
					NBTTagCompound tag = fileSlot.getTagCompound();
					if(isValidFile(tag)){
						if(tag.getInteger("Device") == code){
							if(tag.getBoolean("Cycle")){
								if(tag.getInteger("Done") <= 0){
									tag.setInteger("Done", tag.getInteger("Amount"));
								}
							}
						}
					}
				}
			}
		}
	}

	//decide if the server can be used or is skipped
	public default boolean handleServer(boolean hasServer, TEServer server, int currentFile) {
		return isServerOpen(hasServer, server, currentFile)
			|| isServedClosed(hasServer, server);
	}

	// the server is turned off
	public default boolean isServedClosed(boolean hasServer, TEServer server) {
		return !hasServer || (hasServer && !server.isActive());
	}

	//the server is turned on and has recipes to do
	public default boolean isServerOpen(boolean hasServer, TEServer server, int currentFile){
		return isServerOn(hasServer, server) && hasActiveFile(currentFile);
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

	// the server is turned on
	public default boolean isServerOn(boolean hasServer, TEServer server){
		return hasServer && server.isActive();
	}

	// a file has been selected
	public default boolean hasActiveFile(int currentFile){
		return currentFile > -1;
	}

	// check if can update the server
	public default void updateServer(boolean hasServer, TEServer server, int currentFile) {
		if(isServerOpen(hasServer, server, currentFile)){
			refreshServer(server, currentFile);
		}
	}

	// update the serve after a recipe is done
	public default void refreshServer(TEServer server, int currentFile) {
		if(hasActiveFile(currentFile)){
			NBTTagCompound tag = server.inputSlot(currentFile).getTagCompound();
			if(isValidFile(tag)){
				int doRecipe = tag.getInteger("Done") - 1;
				tag.setInteger("Done", doRecipe);
			}
		}
	}

	//check if the file has all parameters
	public default boolean isValidFile(NBTTagCompound tag) {
		return tag.hasKey("Device") && tag.hasKey("Cycle") && tag.hasKey("Recipe") && tag.hasKey("Done");
	}

}