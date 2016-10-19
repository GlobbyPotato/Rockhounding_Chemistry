package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.SaltMaker;
import com.globbypotato.rockhounding_chemistry.machines.SaltMaker.EnumType;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

public class TileEntitySaltMaker extends TileEntity implements ITickable {
	private int evaporateCount = -1;
	public static int evaporationMultiplier;
	private int evaporationSpeed = 2000 * evaporationMultiplier;

	@Override
	public void update() {
		if(!worldObj.isRemote){
		    IBlockState state = worldObj.getBlockState(pos);
			Biome biome = worldObj.getBiomeGenForCoords(pos);
			EnumType type = (EnumType) state.getValue(SaltMaker.VARIANT);

		    //fill tank on rain
			if(type.getMetadata() == 0){
				evaporateCount = -1;
				if(canRainRefill(biome)){
	  		    	state = ModBlocks.saltMaker.getStateFromMeta(1);
					worldObj.setBlockState(pos, state);
				}
			}
			
			//lose profress on rain
			if(type.getMetadata() > 0){
				if(canRainMelt(biome)){
					evaporateCount = -1;
	  		    	state = ModBlocks.saltMaker.getStateFromMeta(1);
					worldObj.setBlockState(pos, state);
				}
			}

			//do evaporation
			if(type.getMetadata() > 0 && type.getMetadata() < 6){
				if(evaporateCount >= calculatedEvaporation(biome)){
					evaporateCount = -1;
	  		    	state = ModBlocks.saltMaker.getStateFromMeta(type.getMetadata() + 1);
					worldObj.setBlockState(pos, state);
				}else{
					if(canEvaporate()){
						evaporateCount++;
					}else{
						if(canRainRefill(biome)){
							evaporateCount = -1;
			  		    	state = ModBlocks.saltMaker.getStateFromMeta(1);
							worldObj.setBlockState(pos, state);
						}
					}
				}
			}

		}
	}

	private boolean canEvaporate() {
		return worldObj.isDaytime() && worldObj.canSeeSky(pos) && !worldObj.isRaining();
	}

	private int calculatedEvaporation(Biome biome) {
		if(BiomeDictionary.isBiomeOfType(biome, Type.SANDY)){
			return evaporationSpeed / 2 ;
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.WET) || BiomeDictionary.isBiomeOfType(biome, Type.WATER) || BiomeDictionary.isBiomeOfType(biome, Type.FOREST) || BiomeDictionary.isBiomeOfType(biome, Type.CONIFEROUS) || BiomeDictionary.isBiomeOfType(biome, Type.SWAMP)){
			return evaporationSpeed * 2 ;
		}else if(BiomeDictionary.isBiomeOfType(biome, Type.COLD) || BiomeDictionary.isBiomeOfType(biome, Type.MOUNTAIN)){
			return evaporationSpeed * 4 ;
		}else{
			return evaporationSpeed;
		}
	}

	private boolean canRainMelt(Biome biome) {
		return worldObj.isRaining() && !BiomeDictionary.isBiomeOfType(biome, Type.SANDY);
	}

	private boolean canRainRefill(Biome biome) {
		return worldObj.isRaining() && ModConfig.enableRainRefill && !BiomeDictionary.isBiomeOfType(biome, Type.SANDY);
	}

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.evaporateCount = compound.getInteger("EvaporateCount");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("EvaporateCount", this.evaporateCount);
        NBTTagList nbttaglist = new NBTTagList();
        compound.setTag("Items", nbttaglist);
        return compound;
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

}

