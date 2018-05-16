package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.machines.MachineServer;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract interface IInternalServer {
	//server
	public default TileEntityMachineServer getServer(World worldObj, BlockPos pos){
		IBlockState state = worldObj.getBlockState(pos);
		BlockPos serverPos = pos.offset(state.getValue(MachineServer.FACING));
		IBlockState server = worldObj.getBlockState(serverPos);
		if(server != null && server.getBlock() == ModBlocks.machineServer){
			if(server.getValue(MachineServer.FACING) == state.getValue(MachineServer.FACING).getOpposite()){
				TileEntity te = worldObj.getTileEntity(serverPos);
				if(te != null && te instanceof TileEntityMachineServer){
					TileEntityMachineServer serverTE = (TileEntityMachineServer)te;
					return serverTE;
				}
			}
		}
		return null;
	}

	public default boolean hasServer(World worldObj, BlockPos pos){
		return getServer(worldObj, pos) != null;
	}

	// set de server to handle the machine
	public default void initializeServer(boolean isRepeatable, boolean hasServer, TileEntityMachineServer server, int code) {
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
	public default void setServerCode(TileEntityMachineServer server, int code) {
		if(server.servedDevice() != code){
			server.applyServer(code);
		}
	}

	//decide if the cycle is repeatable
	public default void checkRepeatable(boolean isRepeatable, TileEntityMachineServer server, int code){
		isRepeatable = false;
		for(int x = 0; x < TileEntityMachineServer.FILE_SLOTS.length; x++){
			if(server.inputSlot(x) != null){
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
	}

	//reset the consumed recipes
	public default void resetFiles(TileEntityMachineServer server, int code){
		for(int k = 0; k < TileEntityMachineServer.FILE_SLOTS.length; k++){
			ItemStack fileSlot = server.inputSlot(k);
			if(fileSlot != null && fileSlot.isItemEqual(BaseRecipes.server_file)){
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
	public default boolean handleServer(boolean hasServer, TileEntityMachineServer server, int currentFile) {
		return isServerOpen(hasServer, server, currentFile)
			|| isServedClosed(hasServer, server);
	}

	// the server is turned off
	public default boolean isServedClosed(boolean hasServer, TileEntityMachineServer server) {
		return !hasServer || (hasServer && !server.isActive());
	}

	//the server is turned on and has recipes to do
	public default boolean isServerOpen(boolean hasServer, TileEntityMachineServer server, int currentFile){
		return isServerOn(hasServer, server) && hasActiveFile(currentFile);
	}

	// the server is turned on
	public default boolean isServerOn(boolean hasServer, TileEntityMachineServer server){
		return hasServer && server.isActive();
	}

	// a file has been selected
	public default boolean hasActiveFile(int currentFile){
		return currentFile > -1;
	}

	// check if can update the server
	public default void updateServer(boolean hasServer, TileEntityMachineServer server, int currentFile) {
		if(isServerOpen(hasServer, server, currentFile)){
			refreshServer(server, currentFile);
		}
	}

	// update the serve after a recipe is done
	public default void refreshServer(TileEntityMachineServer server, int currentFile) {
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