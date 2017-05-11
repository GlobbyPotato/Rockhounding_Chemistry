package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumCrawler;
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
import net.minecraftforge.fluids.BlockFluidClassic;

public class TileEntityMineCrawler extends TileEntity implements ITickable {
	public static boolean activator;

	public static int numTier;
	public static int numMode;
	public static boolean canFill;
	public static boolean canAbsorb;
	public static boolean canTunnel;
	public static boolean canLight;
	public static boolean canRail;
	public static boolean canDeco;
	public static boolean canPath;
	public static boolean canStore;
	public static int numStep = 10;
	public static int numUpgrade;

	public static String fillBlockName = "None";
	public static int fillBlockMeta;
	public static int fillBlockSize;

	public static String absorbBlockName = "None";
	public static int absorbBlockMeta;
	public static int absorbBlockSize;

	public static String lighterBlockName = "None";
	public static int lighterBlockMeta;
	public static int lighterBlockSize;

	public static String railBlockName = "None";
	public static int railBlockMeta;
	public static int railBlockSize;

	public static String decoBlockName = "None";
	public static int decoBlockMeta;
	public static int decoBlockSize;

	private int tickCount;
	ItemStack crawlerDrop;
	ItemStack fluidCan = new ItemStack(ModItems.miscItems, 1, 5);

	/**
	 	checks in which direction the machine is pointing, collects sensible data and executes the tasks
	 */
	@Override
	public void update() {
		if(activator){
			if(numTier > 0){
				if(tickCount >= numStep * numTier){
				    IBlockState state = worldObj.getBlockState(pos);
				    EnumFacing enumfacing = state.getValue(MineCrawler.FACING);
					if(enumfacing == EnumFacing.NORTH){
						BlockPos samplePos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() - 1);
						executeDrill(samplePos, pos, state, enumfacing);
					}else if(enumfacing == EnumFacing.SOUTH){
						BlockPos samplePos = new BlockPos(pos.getX(), pos.getY(), pos.getZ() + 1);
						executeDrill(samplePos, pos, state, enumfacing);
					}else if(enumfacing == EnumFacing.EAST){
						BlockPos samplePos = new BlockPos(pos.getX() + 1, pos.getY(), pos.getZ());
						executeDrill(samplePos, pos, state, enumfacing);
					}else if(enumfacing == EnumFacing.WEST){
						BlockPos samplePos = new BlockPos(pos.getX() - 1, pos.getY(), pos.getZ());
						executeDrill(samplePos, pos, state, enumfacing);
					}
					tickCount = 0;
				}else{
					tickCount++;
				}
			}
		}
	}

	/**
	 *	checks if the machine has a valid path in front and performs the tasks
	 */
    private void executeDrill(BlockPos samplePos, BlockPos crawlerPos, IBlockState state, EnumFacing enumfacing) {
    	if(hasPath(samplePos) && canBreak(samplePos)){
    		//handleDeco
    		if(canDeco){handleDecorator(samplePos, enumfacing);}

    		//handle borders
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
    	}else{
    		if(canPath){
    			handlePathfinder(samplePos);
    		}
    	}
	}

	/**
	 *	analyzes the blocks in front depending on tier and breaks/picks them
	 */
	private void handleBreaking(BlockPos breakingPos, EnumFacing enumfacing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); samplePos.setPos(breakingPos);

		if(numTier > 0){
			breakTheBlock(samplePos, breakingPos, 0, 0, 0);
		}

		if(numTier == 2 || numTier == 4 ){
			breakTheBlock(samplePos, breakingPos, 0, 1, 0);
		}

		if(numTier > 2){
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				breakTheBlock(samplePos, breakingPos, 0, 0, 1);
				breakTheBlock(samplePos, breakingPos, 0, 0, -1);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				breakTheBlock(samplePos, breakingPos, 1, 0, 0);
				breakTheBlock(samplePos, breakingPos, -1, 0, 0);
			}
		}

		if(numTier == 4){
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				breakTheBlock(samplePos, breakingPos, 0, 1, 1);
				breakTheBlock(samplePos, breakingPos, 0, 1, -1);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				breakTheBlock(samplePos, breakingPos, 1, 1, 0);
				breakTheBlock(samplePos, breakingPos, -1, 1, 0);
			}
		}
	}

			/**
			 * 	break the block at the given coords
			 */
			private void breakTheBlock(MutableBlockPos samplePos, BlockPos breakingPos, int x, int y, int z) {
				samplePos.setPos(breakingPos.getX() + x, breakingPos.getY() + y, breakingPos.getZ() + z);
				pickTheBlock(samplePos);
				this.markDirty();
			}

	/**
	 *	fills the holes around the path
	 */
	private void handleFiller(BlockPos breakingPos, EnumFacing enumfacing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); samplePos.setPos(breakingPos);
		if(numTier == 1 || numTier == 3){
			fillTheBlock(samplePos, breakingPos, 0, 1, 0); //top 1 middle
		}

		if(numTier == 2 || numTier == 4){
			fillTheBlock(samplePos, breakingPos, 0, 2, 0);//top 2 middle
		}

		if(numTier == 2 || numTier == 3 ){
			//sides 1 hi
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				fillTheBlock(samplePos, breakingPos, 0, 1, -1);
				fillTheBlock(samplePos, breakingPos, 0, 1, 1);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				fillTheBlock(samplePos, breakingPos, -1, 1, 0);
				fillTheBlock(samplePos, breakingPos, 1, 1, 0);
			}
		}

		if(numTier == 1 || numTier == 2){
			//sides 1 lo
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				fillTheBlock(samplePos, breakingPos, 0, 0, -1);
				fillTheBlock(samplePos, breakingPos, 0, 1, 1);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				fillTheBlock(samplePos, breakingPos, -1, 0, 0);
				fillTheBlock(samplePos, breakingPos, 1, 1, 0);
			}			
		}

		if(numTier == 4){
			//sides 2 hi
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				fillTheBlock(samplePos, breakingPos, 0, 1, -2);
				fillTheBlock(samplePos, breakingPos, 0, 1, 2);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				fillTheBlock(samplePos, breakingPos, -2, 1, 0);
				fillTheBlock(samplePos, breakingPos, 2, 1, 0);
			}
			//top 2 hi
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				fillTheBlock(samplePos, breakingPos, 0, 2, -1);
				fillTheBlock(samplePos, breakingPos, 0, 2, 1);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				fillTheBlock(samplePos, breakingPos, -1, 2, 0);
				fillTheBlock(samplePos, breakingPos, 1, 2, 0);
			}
		}

		if(numTier == 3 || numTier == 4){
			//sides 2 low
			if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
				fillTheBlock(samplePos, breakingPos, 0, 0, -2);
				fillTheBlock(samplePos, breakingPos, 0, 0, 2);
			}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
				fillTheBlock(samplePos, breakingPos, -2, 0, 0);
				fillTheBlock(samplePos, breakingPos, 2, 0, 0);
			}
		}
	}

			/**
     	 	 *	check for blocks to fill
			 */
			private void fillTheBlock(MutableBlockPos samplePos, BlockPos breakingPos, int x, int y, int z){
				samplePos.setPos(breakingPos.getX() + x, breakingPos.getY() + y, breakingPos.getZ() + z);
				checkFiller(samplePos);
			}
	
			/**
			 * checks borders for filling leaks
			 */
			private void checkFiller(MutableBlockPos samplePos){
				if(isFillable(samplePos) && !isAlreadyDecorated(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, CrawlerUtils.tempStateFromString(fillBlockName, fillBlockMeta));
						fillBlockSize--;
						if(fillBlockSize <= 0){
							fillBlockName = "None"; fillBlockMeta = 0; fillBlockSize = 0;
						}
					}
					this.markDirty();
				}
			}

	/**
	 *	decorate the path
	 */
	private void handleDecorator(BlockPos breakingPos, EnumFacing enumfacing) {
		BlockPos.MutableBlockPos samplePos = new BlockPos.MutableBlockPos(); samplePos.setPos(breakingPos);
		if(numTier == 4){
			if(canFill){
				//sides
				for(int j = 0; j <= 1; j++){
					if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
						decorateTheBlock(samplePos, breakingPos, 0, j, -2);
						decorateTheBlock(samplePos, breakingPos, 0, j, 2);
					}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
						decorateTheBlock(samplePos, breakingPos, -2, j, 0);
						decorateTheBlock(samplePos, breakingPos, 2, j, 0);
					}
				}
			}

			if(canTunnel){
				//floor
				decorateTheBlock(samplePos, breakingPos, 0, -1, 0);
				if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
					decorateTheBlock(samplePos, breakingPos, 0, -1, -1);
					decorateTheBlock(samplePos, breakingPos, 0, -1, 1);
				}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
					decorateTheBlock(samplePos, breakingPos, -1, -1, 0);
					decorateTheBlock(samplePos, breakingPos, 1, -1, 0);
				}

				//top
				for(int j = -1; j <= 1; j++){
					if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
						decorateTheBlock(samplePos, breakingPos, 0, 2, j);
					}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
						decorateTheBlock(samplePos, breakingPos, j, 2, 0);
					}
				}
			}
		}
	}

			/**
			 *	check for blocks to fill
			 */
			private void decorateTheBlock(MutableBlockPos samplePos, BlockPos breakingPos, int x, int y, int z){
				samplePos.setPos(breakingPos.getX() + x, breakingPos.getY() + y, breakingPos.getZ() + z);
				checkDecorator(samplePos);
			}

			/**
			 * checks borders to decorate
			 */
			private void checkDecorator(MutableBlockPos samplePos){
				if(isDecorable(samplePos) && !isAlreadyDecorated(samplePos)){
					pickTheBlock(samplePos); 
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, CrawlerUtils.tempStateFromString(decoBlockName, decoBlockMeta));
						decoBlockSize--;
						if(decoBlockSize <= 0){
							decoBlockName = "None"; decoBlockMeta = 0; decoBlockSize = 0;
						}
					}
					this.markDirty();
				}
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
					fillTheTunnel(samplePos, breakingPos, 0, j, -2);
					fillTheTunnel(samplePos, breakingPos, 0, j, 2);
				}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
					fillTheTunnel(samplePos, breakingPos, -2, j, 0);
					fillTheTunnel(samplePos, breakingPos, 2, j, 0);
				}
			}
			//top
			for(int j = -1; j <= 1; j++){
				if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
					fillTheTunnel(samplePos, breakingPos, 0, 2, j);
				}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
					fillTheTunnel(samplePos, breakingPos, j, 2, 0);
				}
			}
			//bottom
			if(canPath){
				for(int j = -1; j <= 1; j++){
					if(enumfacing == EnumFacing.WEST || enumfacing == EnumFacing.EAST){
						fillTheTunnel(samplePos, breakingPos, 0, -1, j);
					}else if(enumfacing == EnumFacing.NORTH || enumfacing == EnumFacing.SOUTH){
						fillTheTunnel(samplePos, breakingPos, j, -1, 0);
					}
				}
			}
		}
	}

		    /**
		     *	 check for blocks to fill
		     */
		    private void fillTheTunnel(MutableBlockPos samplePos, BlockPos breakingPos, int x, int y, int z){
				samplePos.setPos(breakingPos.getX() + x, breakingPos.getY() + y, breakingPos.getZ() + z);
				checkTunneler(samplePos);
		    }

		    /**
		     *	adds a tunnel block
		     */
		    private void checkTunneler(MutableBlockPos samplePos) {
				if(isTunnelable(samplePos)){
					if(!worldObj.isRemote){
						worldObj.setBlockState(samplePos, CrawlerUtils.tempStateFromString(fillBlockName, fillBlockMeta));
						fillBlockSize--;
						if(fillBlockSize <= 0){
							fillBlockName = "None"; fillBlockMeta = 0; fillBlockSize = 0;
						}
					}
					this.markDirty();
				}
			}

	/**
 	 *	places rails after mining a layer
 	 */
	private void handleRailmaker(BlockPos crawlerPos, EnumFacing enumfacing) {
		if(isTracing(crawlerPos, enumfacing)){
			if(!worldObj.isRemote){
				worldObj.setBlockState(pos.offset(enumfacing.getOpposite()), CrawlerUtils.tempStateFromString(railBlockName, railBlockMeta));
				railBlockSize--;
				if(railBlockSize <= 0){
					railBlockName = "None"; railBlockMeta = 0; railBlockSize = 0;
				}
			}
			this.markDirty();
		}
	}

    /**
	 *	leaves a torch when light goes down
	 */
    private void handleLighter(BlockPos crawlerPos, EnumFacing enumfacing) {
		BlockPos torchPos = crawlerPos;
		if(numTier > 2 || canRail){
			torchPos = pos.offset(enumfacing.fromAngle(enumfacing.getHorizontalAngle() + 90));
		}else{
			torchPos = pos.offset(enumfacing.getOpposite());
		}
		if(isTorching(crawlerPos, enumfacing, torchPos)){
			if(worldObj.getBlockState(torchPos).getBlock().isAir(worldObj.getBlockState(torchPos), worldObj, torchPos)){
				if(!worldObj.isRemote){
					worldObj.setBlockState(torchPos, CrawlerUtils.tempStateFromString(lighterBlockName, lighterBlockMeta));
					lighterBlockSize--;
					if(lighterBlockSize <= 0){
						lighterBlockName = "None"; lighterBlockMeta = 0; lighterBlockSize = 0;
					}
				}
			}
			this.markDirty();
		}
	}

	/**
	 *	tries to place a pathway
	 */
	private void handlePathfinder(BlockPos samplePos){
		MutableBlockPos pathPos = new BlockPos.MutableBlockPos(samplePos.getX(), samplePos.getY() - 1, samplePos.getZ());
		checkPathfinder(pathPos);
	}

	/**
	 * checks borders for filling leaks
	 */
	private void checkPathfinder(MutableBlockPos pathPos){
		if(isWalkable(pathPos)){
			pickTheBlock(pathPos);
			if(!worldObj.isRemote){
				if(CrawlerUtils.tempStateFromString(fillBlockName, fillBlockMeta) != null){
					worldObj.setBlockState(pathPos, CrawlerUtils.tempStateFromString(fillBlockName, fillBlockMeta));
					fillBlockSize--;
					if(fillBlockSize <= 0){
						fillBlockName = "None"; fillBlockMeta = 0; fillBlockSize = 0;
					}
					this.markDirty();
				}
			}
		}
	}

	/**
	 *	checks how to remove the block
	 */
	private void pickTheBlock(MutableBlockPos samplePos) {
		IBlockState minedBlock = worldObj.getBlockState(samplePos);
		if(canStore){
			handleStorage(minedBlock, samplePos);
		}else{
			removeBlock(minedBlock, samplePos);
		}
	}

	/**
	 *	remove the block from world
	 */
	private void removeBlock(IBlockState minedBlock, MutableBlockPos samplePos) {
		if(!minedBlock.getBlock().hasTileEntity(minedBlock)){
			if(numMode == 0){
				worldObj.destroyBlock(samplePos, true);
			}else{
				if(!worldObj.isRemote){
					dropItemStack(minedBlock, samplePos, pos);
				}
			}
		}
	}

	/**
	 *	checks if a block can be reused for memory
	 */
	private void handleStorage(IBlockState minedBlock, MutableBlockPos samplePos) {
		if(!worldObj.isRemote && canStoreAsMaterial(minedBlock, samplePos, fillBlockName, fillBlockMeta, fillBlockSize)){
			fillBlockSize++; worldObj.setBlockToAir(samplePos);
		}else if(!worldObj.isRemote && canStoreAsMaterial(minedBlock, samplePos, absorbBlockName, absorbBlockMeta, absorbBlockSize)){
			absorbBlockSize++; worldObj.setBlockToAir(samplePos);
		}else if(!worldObj.isRemote && canStoreAsMaterialIgnoreMeta(minedBlock, samplePos, lighterBlockName, lighterBlockMeta, lighterBlockSize)){
			lighterBlockSize++; worldObj.setBlockToAir(samplePos);
		}else if(!worldObj.isRemote && canStoreAsMaterialIgnoreMeta(minedBlock, samplePos, railBlockName, railBlockMeta, railBlockSize)){
			railBlockSize++; worldObj.setBlockToAir(samplePos);
		}else if(!worldObj.isRemote && canStoreAsMaterial(minedBlock, samplePos, decoBlockName, decoBlockMeta, decoBlockSize)){
			decoBlockSize++; worldObj.setBlockToAir(samplePos);
		}else{
			removeBlock(minedBlock, samplePos);	
		}
		this.markDirty();
	}

	/**
	 *	store in memory the mined block
	 */
	private boolean canStoreAsMaterial(IBlockState minedBlock, MutableBlockPos samplePos, String name, int meta, int size) {
		return minedBlock.getBlock().getRegistryName().toString().matches(name)
			&& minedBlock.getBlock().getMetaFromState(minedBlock) == meta
			&& size < 32000;
	}
	private boolean canStoreAsMaterialIgnoreMeta(IBlockState minedBlock, MutableBlockPos samplePos, String name, int meta, int size) {
		return minedBlock.getBlock().getRegistryName().toString().matches(name)
			&& size < 32000;
	}

	/**
	 *	drops silk touched blocks. deals with fluids as item drops
	 */
	private void dropItemStack(IBlockState state, BlockPos samplePos, BlockPos pos) {
		if(state.getMaterial().isLiquid()){
			String blockName = state.getBlock().getRegistryName().toString();
			if(canAbsorb && this.absorbBlockSize > 0 && state.getBlock().getMetaFromState(state) == 0){
				fluidCan.setTagCompound(new NBTTagCompound());
				fluidCan.getTagCompound().setString("Block", blockName);
				spawnDroppedItem(fluidCan);
				absorbBlockSize--;
				if(absorbBlockSize <= 0){
					absorbBlockName = "None"; absorbBlockMeta = 0; absorbBlockSize = 0;
				}
			}
		}else{
			crawlerDrop = state.getBlock().getPickBlock(state, null, worldObj, samplePos, null);
			spawnDroppedItem(crawlerDrop);
		}
		worldObj.setBlockToAir(samplePos);
	}

			/**
			 * 	spawn in world the mined block
			 */
			private void spawnDroppedItem(ItemStack stack) {
				if(stack != null){
					EntityItem entityitem = new EntityItem(worldObj, pos.getX(), pos.getY(), pos.getZ(), stack);
					worldObj.spawnEntityInWorld(entityitem);
				}
			}

			/**
			 * checks if the mined fluid is a source block
			 */
			private boolean isSourceFluid(IBlockState state, BlockPos samplePos) {
				return state.getBlock() instanceof BlockFluidClassic && ((BlockFluidClassic) state.getBlock()).isSourceBlock(worldObj, samplePos);
			}

	/**
	 *	checks if can place a path
	 */
	private boolean isWalkable(MutableBlockPos pathPos) {
		return numTier > 1 && !isAlreadyDecorated(pathPos) && !isAlreadyFilled(pathPos) && fillBlockSize > 0;
	}

	/**
	 *	checks what types of blocks can be filled and has material to fill
	 */
	private boolean isFillable(MutableBlockPos samplePos) {
		return numTier > 1 
			&& (worldObj.getBlockState(samplePos).getMaterial().isLiquid() || worldObj.getBlockState(samplePos).getBlock() instanceof BlockFalling) 
			&& fillBlockSize > 0;
	}

	/**
 	 *	checks if the given block has been already managed for decoration
	 */
	private boolean isAlreadyDecorated(MutableBlockPos samplePos) {
		return worldObj.getBlockState(samplePos).getBlock().getRegistryName().toString().matches(decoBlockName)
			&& worldObj.getBlockState(samplePos).getBlock().getMetaFromState(worldObj.getBlockState(samplePos)) == decoBlockMeta;
	}

	/**
 	 *	checks if the given block has been already managed for flling
	 */
	private boolean isAlreadyFilled(MutableBlockPos pathPos) {
		return worldObj.getBlockState(pathPos).getBlock().getRegistryName().toString().matches(fillBlockName)
			&& worldObj.getBlockState(pathPos).getBlock().getMetaFromState(worldObj.getBlockState(pathPos)) == fillBlockMeta;
	}

	/**
 	 *	checks what types of blocks can be decorated
	 */
	private boolean isDecorable(MutableBlockPos samplePos) {
		return numTier > 3 
			&& (worldObj.getBlockState(samplePos).getBlock() != Blocks.BEDROCK) 
			&& !worldObj.getBlockState(samplePos).getBlock().hasTileEntity(worldObj.getBlockState(samplePos)) 
			&& decoBlockSize > 0;
	}

	/**
 	 *	checks for empty blocks to make a tunnel wall and has material to fill
	 */
	private boolean isTunnelable(MutableBlockPos samplePos) {
		return numTier > 3 
			&& worldObj.getBlockState(samplePos).getBlock() == Blocks.AIR 
			&& fillBlockSize > 0;
	}

	/**
	 *	checks surface behind to place a rail
	 */
	private boolean isTracing(BlockPos crawlerPos, EnumFacing enumfacing) {
		BlockPos railpos = new BlockPos(crawlerPos.offset(enumfacing.getOpposite()).getX(), crawlerPos.offset(enumfacing.getOpposite()).getY() - 1, crawlerPos.offset(enumfacing.getOpposite()).getZ());
		return numTier > 1 
			&& worldObj.getBlockState(railpos).isSideSolid(worldObj, railpos, EnumFacing.DOWN) && worldObj.getBlockState(crawlerPos.offset(enumfacing.getOpposite())).getBlock() == Blocks.AIR 
			&& railBlockSize > 0;
	}

	/**
	 *	checks light value in the path to place a torch
	 */
	private boolean isTorching(BlockPos crawlerPos, EnumFacing enumfacing, BlockPos torchPos) {
		return numTier > 2 
			&& worldObj.getLightFromNeighbors(crawlerPos) < 6 
			&& worldObj.getBlockState(torchPos).getBlock() == Blocks.AIR 
			&& lighterBlockSize > 0;
	}

	/**
	 *	cannot break bedrock or any tileentity
	 */
	private boolean canBreak(BlockPos samplePos) {
		return worldObj.getBlockState(samplePos).getBlock() != Blocks.BEDROCK 
			&& !worldObj.getBlockState(samplePos).getBlock().hasTileEntity(worldObj.getBlockState(samplePos));
	}

	/**
	 *	checks the path in front and beneath the machine to see if can advance
	 */
	private boolean hasPath(BlockPos samplePos) {
		BlockPos pathPos = new BlockPos(samplePos.getX(), samplePos.getY() - 1, samplePos.getZ());
		return worldObj.getBlockState(pathPos).getBlock() != Blocks.AIR
			&& worldObj.getBlockState(pathPos).isSideSolid(worldObj, pathPos, EnumFacing.UP)
			&& !worldObj.getBlockState(samplePos).getBlock().hasTileEntity(worldObj.getBlockState(samplePos))
			&& !worldObj.getBlockState(pathPos).getMaterial().isLiquid();
	}

	/**
	 *	checks if can advance once broken the blocks
	 */
	private boolean canAdvance(BlockPos samplePos) {
		BlockPos pathPos = new BlockPos(samplePos.getX(), samplePos.getY() - 1, samplePos.getZ());
		return hasPath(samplePos) 
			&& worldObj.getBlockState(samplePos).getBlock() == Blocks.AIR;
	}

	//----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.activator = compound.getBoolean(EnumCrawler.ACTIVATOR.getName());
        this.numTier = compound.getInteger(EnumCrawler.TIERS.getName());
        this.numMode = compound.getInteger(EnumCrawler.MODES.getName());
        this.numStep = compound.getInteger(EnumCrawler.STEP.getName());
        this.numUpgrade = compound.getInteger(EnumCrawler.UPGRADE.getName());
        this.canFill = compound.getBoolean(EnumCrawler.FILLER.getName());
        this.canAbsorb = compound.getBoolean(EnumCrawler.ABSORBER.getName());
        this.canTunnel = compound.getBoolean(EnumCrawler.TUNNELER.getName());
        this.canLight = compound.getBoolean(EnumCrawler.LIGHTER.getName());
        this.canRail = compound.getBoolean(EnumCrawler.RAILMAKER.getName());
        this.canDeco = compound.getBoolean(EnumCrawler.DECORATOR.getName());
        this.canPath = compound.getBoolean(EnumCrawler.PATHFINDER.getName());
        this.canStore = compound.getBoolean(EnumCrawler.STORAGE.getName());
        this.fillBlockName = compound.getString(EnumCrawler.FILLER_BLOCK.getBlockName());
        this.fillBlockMeta = compound.getInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta());
        this.fillBlockSize = compound.getInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize());
        this.absorbBlockName = compound.getString(EnumCrawler.ABSORBER_BLOCK.getBlockName());
        this.absorbBlockMeta = compound.getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta());
        this.absorbBlockSize = compound.getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());
        this.lighterBlockName = compound.getString(EnumCrawler.LIGHTER_BLOCK.getBlockName());
        this.lighterBlockMeta = compound.getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta());
        this.lighterBlockSize = compound.getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());
        this.railBlockName = compound.getString(EnumCrawler.RAILMAKER_BLOCK.getBlockName());
        this.railBlockMeta = compound.getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta());
        this.railBlockSize = compound.getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());
        this.decoBlockName = compound.getString(EnumCrawler.DECORATOR_BLOCK.getBlockName());
        this.decoBlockMeta = compound.getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta());
        this.decoBlockSize = compound.getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setBoolean(EnumCrawler.ACTIVATOR.getName(), this.activator);
        compound.setInteger(EnumCrawler.TIERS.getName(), this.numTier);
        compound.setInteger(EnumCrawler.MODES.getName(), this.numMode);
        compound.setInteger(EnumCrawler.STEP.getName(), this.numStep);
        compound.setInteger(EnumCrawler.UPGRADE.getName(), this.numUpgrade);
        compound.setBoolean(EnumCrawler.FILLER.getName(), this.canFill);
        compound.setBoolean(EnumCrawler.ABSORBER.getName(), this.canAbsorb);
        compound.setBoolean(EnumCrawler.TUNNELER.getName(), this.canTunnel);
        compound.setBoolean(EnumCrawler.LIGHTER.getName(), this.canLight);
        compound.setBoolean(EnumCrawler.RAILMAKER.getName(), this.canRail);
        compound.setBoolean(EnumCrawler.DECORATOR.getName(), this.canDeco);
        compound.setBoolean(EnumCrawler.PATHFINDER.getName(), this.canPath);
        compound.setBoolean(EnumCrawler.STORAGE.getName(), this.canStore);
        compound.setString(EnumCrawler.FILLER_BLOCK.getBlockName(), this.fillBlockName);
        compound.setInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta(), this.fillBlockMeta);
        compound.setInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize(), this.fillBlockSize);
        compound.setString(EnumCrawler.ABSORBER_BLOCK.getBlockName(), this.absorbBlockName);
        compound.setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), this.absorbBlockMeta);
        compound.setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize(), this.absorbBlockSize);
        compound.setString(EnumCrawler.LIGHTER_BLOCK.getBlockName(), this.lighterBlockName);
        compound.setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), this.lighterBlockMeta);
        compound.setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize(), this.lighterBlockSize);
        compound.setString(EnumCrawler.RAILMAKER_BLOCK.getBlockName(), this.railBlockName);
        compound.setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), this.railBlockMeta);
        compound.setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize(), this.railBlockSize);
        compound.setString(EnumCrawler.DECORATOR_BLOCK.getBlockName(), this.decoBlockName);
        compound.setInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), this.decoBlockMeta);
        compound.setInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize(), this.decoBlockSize);
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