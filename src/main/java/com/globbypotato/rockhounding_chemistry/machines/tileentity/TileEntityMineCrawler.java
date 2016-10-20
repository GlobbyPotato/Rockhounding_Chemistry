package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.MineCrawler;

import net.minecraft.block.BlockFalling;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;

public class TileEntityMineCrawler extends TileEntity implements ITickable {
	public static int numTier;
	public static int numMode;
	public static boolean canFill;
	public static boolean canAbsorb;
	public static boolean canTunnel;
	public static boolean canLight;
	public static boolean canRail;
	public static int numStep = 10;
	public static int numUpgrade;
	public static int numCobble;
	public static int numGlass;
	public static int numTorch;
	public static int numRail;

	private int tickCount;
	private ItemStack dropCrawler;

	/**
	 	checks in which direction the machine is pointing, collects sensible data and executes the tasks
	 */
	@Override
	public void update() {
		if(numTier > 0){
			if(tickCount >= numStep * numTier){
			    IBlockState state = worldObj.getBlockState(pos);
			    EnumFacing enumfacing = state.getValue(MineCrawler.FACING);
				if(enumfacing == EnumFacing.NORTH){
					BlockPos samplePos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
					BlockPos crawlerPos = pos;
					executeDrill(samplePos, crawlerPos, state, enumfacing);
				}else if(enumfacing == EnumFacing.SOUTH){
					BlockPos samplePos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
					BlockPos crawlerPos = pos;
					executeDrill(samplePos, crawlerPos, state, enumfacing);
				}else if(enumfacing == EnumFacing.EAST){
					BlockPos samplePos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
					BlockPos crawlerPos = pos;
					executeDrill(samplePos, crawlerPos, state, enumfacing);
				}else if(enumfacing == EnumFacing.WEST){
					BlockPos samplePos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
					BlockPos crawlerPos = pos;
					executeDrill(samplePos, crawlerPos, state, enumfacing);
				}
				tickCount = 0;
			}else{
				tickCount++;
			}
		}
	}

	/**
	 	checks if the machine has a valid path in front and performs the tasks
	 */
    private void executeDrill(BlockPos samplePos, BlockPos crawlerPos, IBlockState state, EnumFacing enumfacing) {
    	if(hasPath(samplePos) && canBreak(samplePos)){
    		//handle leaking
    		if(canFill){handleFiller(samplePos, enumfacing);}

    		//handle breaking
    		handleBreaking(samplePos, enumfacing);
    		
    		//handle tunneler
    		if(canTunnel){handleTunneler(samplePos, enumfacing);}

    		//handle lighter
    		if(canLight){handleLighter(crawlerPos, enumfacing);}

    		//handle railmaker
    		if(canRail){handleRailmaker(crawlerPos, enumfacing);}

    		//advance
    		if(canAdvance(samplePos)){
    			if(!worldObj.isRemote){
	   				worldObj.setBlockState(samplePos, state); worldObj.setTileEntity(samplePos, this); 
	   				worldObj.setBlockToAir(crawlerPos);
    			}
   				this.markDirty();
    		}
    	}
	}


	/**
		analyzes the blocks in front depending on tier and breaks/picks them
    */
	private void handleBreaking(BlockPos breakingPos, EnumFacing enumfacing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); samplePos.setPos(breakingPos);

		if(numTier > 0){
			samplePos.setPos(breakingPos);
			pickTheBlock(samplePos);
		}

		if(numTier == 2 || numTier == 4 ){
			samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ());
			pickTheBlock(samplePos);
		}
		
		if(numTier > 2){
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY(), breakingPos.getZ() + 1);
				pickTheBlock(samplePos);
				samplePos.setPos(breakingPos.getX(), breakingPos.getY(), breakingPos.getZ() - 1);
				pickTheBlock(samplePos);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() + 1, breakingPos.getY(), breakingPos.getZ());
				pickTheBlock(samplePos);
				samplePos.setPos(breakingPos.getX() - 1, breakingPos.getY(), breakingPos.getZ());
				pickTheBlock(samplePos);
			}
		}
		
		if(numTier == 4){
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ() + 1);
				pickTheBlock(samplePos);
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ() - 1);
				pickTheBlock(samplePos);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() + 1, breakingPos.getY() + 1, breakingPos.getZ());
				pickTheBlock(samplePos);
				samplePos.setPos(breakingPos.getX() - 1, breakingPos.getY() + 1, breakingPos.getZ());
				pickTheBlock(samplePos);
			}
		}
		this.markDirty();
	}

	/**
		fills the holes around the tunnel/path
	*/
	private void handleFiller(BlockPos breakingPos, EnumFacing enumfacing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); samplePos.setPos(breakingPos);
		if(numTier == 1 || numTier == 3){
			//top 1 middle
			samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ());
			if(isFillable(samplePos)){
				pickTheBlock(samplePos); 
				if(!worldObj.isRemote){
					worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
				}
			}
		}

		if(numTier == 2 || numTier == 4){
			//top 2 middle
			samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 2, breakingPos.getZ());
			if(isFillable(samplePos)){
				pickTheBlock(samplePos); 
				if(!worldObj.isRemote){
					worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
				}
			}
		}

		if(numTier == 2 || numTier == 3 ){
			//sides 1 hi
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ() - 1);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ() + 1);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() - 1, breakingPos.getY() + 1, breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX() + 1, breakingPos.getY() + 1, breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}
		}

		if(numTier == 1 || numTier == 2){
			//sides 1 lo
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY(), breakingPos.getZ() - 1);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX(), breakingPos.getY(), breakingPos.getZ() + 1);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() - 1, breakingPos.getY(), breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX() + 1, breakingPos.getY(), breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}			
		}

		if(numTier == 4){
			//sides 2 hi
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ() - 2);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 1, breakingPos.getZ() + 2);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() - 2, breakingPos.getY() + 1, breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX() + 2, breakingPos.getY() + 1, breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}
			//top 2 hi
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 2, breakingPos.getZ() - 1);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 2, breakingPos.getZ() + 1);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() - 1, breakingPos.getY() + 2, breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX() + 1, breakingPos.getY() + 2, breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}
		}

		if(numTier == 3 || numTier == 4){
			//sides 2 low
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				samplePos.setPos(breakingPos.getX(), breakingPos.getY(), breakingPos.getZ() - 2);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX(), breakingPos.getY(), breakingPos.getZ() + 2);
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				samplePos.setPos(breakingPos.getX() - 2, breakingPos.getY(), breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
				samplePos.setPos(breakingPos.getX() + 2, breakingPos.getY(), breakingPos.getZ());
				if(isFillable(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
					}
				}
			}
		}
		this.markDirty();
	}

	/**
		fills the vacuum around the path making a tunnel
	*/
    private void handleTunneler(BlockPos breakingPos, EnumFacing enumfacing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); samplePos.setPos(breakingPos);
		if(numTier == 4){
			//sides
			for(int j = 0; j <= 1; j++){
				if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
					samplePos.setPos(breakingPos.getX(), breakingPos.getY() + j, breakingPos.getZ() - 2);
					if(isTunneling(samplePos)){
						if(!worldObj.isRemote){
							worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
						}
					}
					samplePos.setPos(breakingPos.getX(), breakingPos.getY() + j, breakingPos.getZ() + 2);
					if(isTunneling(samplePos)){
						if(!worldObj.isRemote){
							worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
						}
					}
				}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
					samplePos.setPos(breakingPos.getX() - 2, breakingPos.getY() + j, breakingPos.getZ());
					if(isTunneling(samplePos)){
						if(!worldObj.isRemote){
							worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
						}
					}
					samplePos.setPos(breakingPos.getX() + 2, breakingPos.getY() + j, breakingPos.getZ());
					if(isTunneling(samplePos)){
						if(!worldObj.isRemote){
							worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
						}
					}
				}
			}
			//top
			for(int j = -1; j <= 1; j++){
				if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
					samplePos.setPos(breakingPos.getX(), breakingPos.getY() + 2, breakingPos.getZ() + j);
					if(isTunneling(samplePos)){
						if(!worldObj.isRemote){
							worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
						}
					}
				}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
					samplePos.setPos(breakingPos.getX() + j, breakingPos.getY() + 2, breakingPos.getZ());
					if(isTunneling(samplePos)){
						if(!worldObj.isRemote){
							worldObj.setBlockState(samplePos, Blocks.COBBLESTONE.getDefaultState()); numCobble--;
						}
					}
				}
			}
			this.markDirty();
		}
	}

    
    /**
 		places rails after mining a layer
 	*/
	private void handleRailmaker(BlockPos crawlerPos, EnumFacing enumfacing) {
		if(isTracing(crawlerPos, enumfacing)){
			if(!worldObj.isRemote){
				worldObj.setBlockState(pos.offset(enumfacing.getOpposite()), Blocks.RAIL.getDefaultState()); numRail--;
			}
		}
	}

    /**
		leaves a torch when light goes down
	*/
    private void handleLighter(BlockPos crawlerPos, EnumFacing enumfacing) {
		if(isTorching(crawlerPos, enumfacing)){
			BlockPos torchPos = crawlerPos;
			if(numTier > 2 || canRail){
				torchPos = pos.offset(enumfacing.fromAngle(enumfacing.getHorizontalAngle() + 90));
			}else{
				torchPos = pos.offset(enumfacing.getOpposite());
			}
			if(worldObj.getBlockState(torchPos).getBlock().isAir(worldObj.getBlockState(torchPos), worldObj, torchPos)){
				if(!worldObj.isRemote){
					worldObj.setBlockState(torchPos, Blocks.TORCH.getDefaultState()); numTorch--;
				}
			}
		}
	}

	/**
		removes the blocks
	*/
	private void pickTheBlock(MutableBlockPos samplePos) {
		if(!worldObj.getBlockState(samplePos).getBlock().hasTileEntity(worldObj.getBlockState(samplePos))){
			if(numMode == 0){
				worldObj.destroyBlock(samplePos, true);
			}else{
				if(!worldObj.isRemote){
					IBlockState state = worldObj.getBlockState(samplePos); dropItemStack(state, samplePos, pos);
				}
			}
		}
	}

	/**
		drops silk touched blocks. deals with fluids as item drops
	*/
	private void dropItemStack(IBlockState state, BlockPos samplePos, BlockPos pos) {
		if(state.getMaterial().isLiquid()){
			if(canAbsorb && numGlass > 0 && state.getBlock().getMetaFromState(state) == 0){
				String blockName = state.getBlock().getRegistryName().toString();
				if(!blockName.contains("flowing")){
					dropCrawler = new ItemStack(ModItems.miscItems, 1, 8);
					dropCrawler.setTagCompound(new NBTTagCompound());
					dropCrawler.getTagCompound().setString("Block", blockName);
					numGlass--;
				}
			}else{
				dropCrawler = null;
			}
		}else{
			dropCrawler = state.getBlock().getPickBlock(state, null, worldObj, pos, null);
		}
		if(dropCrawler != null && dropCrawler.getItem() != null){
			EntityItem entityitem = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), dropCrawler);
			entityitem.setPosition(samplePos.getX(), samplePos.getY() + 0.5D, samplePos.getZ());
			worldObj.spawnEntityInWorld(entityitem);
		}
		worldObj.setBlockToAir(samplePos);
	}

	/**
	 	checks what types of blocks can be filled and has material to fill
	 */
	private boolean isFillable(MutableBlockPos samplePos) {
		return  numTier > 1 && (worldObj.getBlockState(samplePos).getMaterial().isLiquid() || worldObj.getBlockState(samplePos).getBlock() instanceof BlockFalling) && numCobble > 0;
	}

	/**
 		checks for empty blocks to make a tunnel wall and has material to fill
	*/
	private boolean isTunneling(MutableBlockPos samplePos) {
		return numTier > 3 && worldObj.getBlockState(samplePos).getBlock() == Blocks.AIR && numCobble > 0;
	}

	/**
		checks surface behind to place a rail
	*/
	private boolean isTracing(BlockPos crawlerPos, EnumFacing enumfacing) {
		BlockPos railpos = new BlockPos(crawlerPos.offset(enumfacing.getOpposite()).getX(), crawlerPos.offset(enumfacing.getOpposite()).getY() - 1, crawlerPos.offset(enumfacing.getOpposite()).getZ());
		return numTier > 1 && worldObj.getBlockState(railpos).isSideSolid(worldObj, railpos, EnumFacing.DOWN) && worldObj.getBlockState(crawlerPos.offset(enumfacing.getOpposite())).getBlock() == Blocks.AIR && numRail > 0;
	}

	/**
		checks light value in the path to place a torch
	*/
	private boolean isTorching(BlockPos crawlerPos, EnumFacing enumfacing) {
		return numTier > 2 && worldObj.getLightFromNeighbors(crawlerPos) < 6 && worldObj.getBlockState(crawlerPos.offset(enumfacing.getOpposite())).getBlock() == Blocks.AIR && numTorch > 0;
	}

	/**
		cannot break bedrock or any tileentity
	 */
	private boolean canBreak(BlockPos samplePos) {
		return  worldObj.getBlockState(samplePos).getBlock() != Blocks.BEDROCK 
				&& !worldObj.getBlockState(samplePos).getBlock().hasTileEntity(worldObj.getBlockState(samplePos));
	}

	/**
	 	checks the path in front and beneath the machine to see if can advance
	 */
	private boolean hasPath(BlockPos samplePos) {
		BlockPos pathPos = new BlockPos(samplePos.getX(), samplePos.getY() - 1, samplePos.getZ());
		return  worldObj.getBlockState(pathPos).getBlock() != Blocks.AIR
				&& worldObj.getBlockState(pathPos).isSideSolid(worldObj, pathPos, EnumFacing.UP)
				&& !worldObj.getBlockState(samplePos).getBlock().hasTileEntity(worldObj.getBlockState(samplePos))
				&& !worldObj.getBlockState(pathPos).getMaterial().isLiquid();
	}

	/**
		checks if can advance once broken the blocks
	 */
	private boolean canAdvance(BlockPos samplePos) {
		BlockPos pathPos = new BlockPos(samplePos.getX(), samplePos.getY() - 1, samplePos.getZ());
		return  hasPath(samplePos) && worldObj.getBlockState(samplePos).getBlock() == Blocks.AIR;
	}


	
	//----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.numTier = compound.getInteger(ModArray.tierName);
        this.numMode = compound.getInteger(ModArray.modeName);
        this.numStep = compound.getInteger(ModArray.stepName);
        this.numUpgrade = compound.getInteger(ModArray.upgradeName);
        this.canFill = compound.getBoolean(ModArray.fillerName);
        this.canAbsorb = compound.getBoolean(ModArray.absorbName);
        this.canTunnel = compound.getBoolean(ModArray.tunnelName);
        this.canLight = compound.getBoolean(ModArray.lighterName);
        this.canRail = compound.getBoolean(ModArray.railmakerName);
        this.numCobble = compound.getInteger(ModArray.cobbleName);
        this.numGlass = compound.getInteger(ModArray.glassName);
        this.numTorch = compound.getInteger(ModArray.torchName);
        this.numRail = compound.getInteger(ModArray.railName);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger(ModArray.tierName, this.numTier);
        compound.setInteger(ModArray.modeName, this.numMode);
        compound.setInteger(ModArray.stepName, this.numStep);
        compound.setInteger(ModArray.upgradeName, this.numUpgrade);
        compound.setInteger(ModArray.cobbleName, this.numCobble);
        compound.setInteger(ModArray.torchName, this.numTorch);
        compound.setInteger(ModArray.glassName, this.numGlass);
        compound.setInteger(ModArray.railName, this.numRail);
        compound.setBoolean(ModArray.fillerName, this.canFill);
        compound.setBoolean(ModArray.absorbName, this.canAbsorb);
        compound.setBoolean(ModArray.tunnelName, this.canTunnel);
        compound.setBoolean(ModArray.lighterName, this.canLight);
        compound.setBoolean(ModArray.railmakerName, this.canRail);
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
