package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesE;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEBufferTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TECatalystRegen;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEDisposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExhaustionValve;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELaserEmitter;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPrecipitationChamber;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPrecipitationController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPrecipitationReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.TESlurryDrum;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEStirredTankBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEStirredTankOut;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEStirredTankTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEWaterPump;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileTank;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_core.machines.tileentity.IFluidHandlingTile;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.globbypotato.rockhounding_core.utils.MachinesUtils;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;

public class MachinesE extends MachineIO {
	public static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumMachinesE.class);
    public static final AxisAlignedBB LASER_EMITTER_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.6D, 1.0D);
    public static final AxisAlignedBB EXHAUSTION_VALVE_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 0.7D, 0.8D);
    public static final AxisAlignedBB SLURRY_DRUM_AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 1.0D, 0.9D);
    public static final AxisAlignedBB BUFFER_TANK_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 1.0D, 0.8D);

	ItemStack compressor = new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.COMPRESSOR.ordinal());
	ItemStack iron_impeller = new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.IRON_IMPELLER.ordinal());
	ItemStack aluminum_impeller = new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.ALUMINUM_IMPELLER.ordinal());
	ItemStack stellite_impeller = new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.STELLITE_IMPELLER.ordinal());

	public MachinesE(String name) {
		super(name, Material.IRON, EnumMachinesE.getNames(), 3.0F, 5.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMachinesE.values()[0]).withProperty(FACING, EnumFacing.NORTH));
	}



	//---------- VARIANT HANDLER ----------
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumMachinesE.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMachinesE)state.getValue(VARIANT)).ordinal();
	}

	@Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos) {
    	TileEntityInv tile = (TileEntityInv)world.getTileEntity(pos);
    	return state.withProperty(FACING, EnumFacing.getFront(tile.facing));
    }

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT, FACING });
	}



	//---------- BLOCK HANDLER ----------
    @Override
	public boolean hiddenParts(int meta) {
		return meta == EnumMachinesE.STIRRED_TANK_TOP.ordinal()
			|| meta == EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal();
	}

    @Override
	public boolean baseParts(int meta) {
		return meta == EnumMachinesE.STIRRED_TANK_BASE.ordinal()
			|| meta == EnumMachinesE.PRECIPITATION_CHAMBER.ordinal();
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(world, pos, state, placer, stack);

    	int meta = stack.getItemDamage();
    	EnumFacing isFacing = EnumFacing.getFront(2);
        world.setBlockState(pos, state.withProperty(VARIANT, EnumMachinesE.values()[meta]).withProperty(FACING, isFacing), 2);

        TileEntity te = world.getTileEntity(pos);
        if(world.getTileEntity(pos) != null){

	        if(te instanceof TEStirredTankBase){
	        	TEStirredTankBase reactor = (TEStirredTankBase) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesE.STIRRED_TANK_TOP);
	        }

	        if(te instanceof TEPrecipitationChamber){
	        	TEPrecipitationChamber reactor = (TEPrecipitationChamber) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesE.PRECIPITATION_CONTROLLER);
	        }

	        if(te instanceof TEWaterPump){
	        	restoreWaterPumpNBT(stack, te);
	        }
	        
	        if(te instanceof TEDisposer){
	        	restoreDisposerNBT(stack, te);
	        }

	        if(te instanceof TESlurryDrum){
	        	restoreSlurryDrumNBT(stack, te);
	        }

	        if(te instanceof TEBufferTank){
	        	restoreBufferTankNBT(stack, te);
	        }

	        if(te instanceof TEStirredTankOut){
	        	restoreStirredTankOutNBT(stack, te);
	        }

        }
    }

	@Override
	public void checkFullBlocks(World world, BlockPos pos, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		
		if(meta == EnumMachinesE.STIRRED_TANK_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesE.STIRRED_TANK_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesE.PRECIPITATION_CHAMBER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}

	}

	private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EnumFacing facing, EntityLivingBase placer, EnumMachinesE prop) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), this.getDefaultState().withProperty(VARIANT, prop).withProperty(FACING, facing), 2);
			TileEntityInv top = (TileEntityInv)world.getTileEntity(pos.up());
			top.facing = facing.ordinal();
		}else{
			int meta = state.getBlock().getMetaFromState(state);
			if(meta == EnumMachinesE.STIRRED_TANK_BASE.ordinal()
			|| meta == EnumMachinesE.PRECIPITATION_CHAMBER.ordinal()){
				TileEntity te = world.getTileEntity(pos);
				ItemStack itemstack = this.getSilkTouchDrop(state);
				handleTileNBT(te, itemstack);
		        spawnAsEntity(world, pos, itemstack);
			}else{
	            this.dropBlockAsItem(world, pos, state, 0);
			}
            world.setBlockToAir(pos);
		}
	}

    private void checkTopBlocks(World world, IBlockState state, IBlockState stateUp, BlockPos pos) {
		int meta = state.getBlock().getMetaFromState(state);
		TileEntity te = world.getTileEntity(pos);
		TileEntity teUp = world.getTileEntity(pos.up());
		if(teUp == null || 
				(
 				    (te instanceof TEStirredTankBase && !(teUp instanceof TEStirredTankTop))
			     || (te instanceof TEPrecipitationChamber && !(teUp instanceof TEPrecipitationController))
				)
		){
			ItemStack itemstack = this.getSilkTouchDrop(state);
			if(meta == EnumMachinesE.STIRRED_TANK_BASE.ordinal()
			|| meta == EnumMachinesE.PRECIPITATION_CHAMBER.ordinal()){
				handleTileNBT(te, itemstack);
			}
	        spawnAsEntity(world, pos, itemstack);
	        world.setBlockToAir(pos);
		}
	}

	private static void checkBaseBlocks(World world, IBlockState state, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		TileEntity teDown = world.getTileEntity(pos.down());
		if(teDown == null || 
				(
					   (te instanceof TEStirredTankTop && !(teDown instanceof TEStirredTankBase))
				   ||  (te instanceof TEPrecipitationController && !(teDown instanceof TEPrecipitationChamber))
				)
		){
			world.setBlockToAir(pos);
		}
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesE.LASER_EMITTER.ordinal()){
			return LASER_EMITTER_AABB;
		}else if(meta == EnumMachinesE.EXHAUSTION_VALVE.ordinal()){
			return EXHAUSTION_VALVE_AABB;
		}else if(meta == EnumMachinesE.BUFFER_TANK.ordinal()){
			return BUFFER_TANK_AABB;
		}else if(meta == EnumMachinesE.SLURRY_DRUM.ordinal()){
			return SLURRY_DRUM_AABB;
		}
        return FULL_BLOCK_AABB;
    }

	public boolean canEmitSignal(IBlockState state){
		int meta = state.getBlock().getMetaFromState(state);
        return meta == EnumMachinesE.SLURRY_DRUM.ordinal();
	}

	@Override
    public boolean canProvidePower(IBlockState state){
        return canEmitSignal(state);
    }

	@Override
    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
        return canEmitSignal(state);
    }

	@Override
    public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return blockState.getWeakPower(blockAccess, pos, side);
    }

	@Override
    public int getWeakPower(IBlockState blockState, IBlockAccess world, BlockPos pos, EnumFacing side){
		int currentPower = 0;
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	if(te instanceof TileTank){
	        	TileTank tank = (TileTank)te;
	        	currentPower = tank.emittedPower();
        	}
        }
        return currentPower;
    }



	//---------- TILE HANDLER ----------
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesE.LASER_EMITTER.ordinal()){
			return new TELaserEmitter();
		}
		if(meta == EnumMachinesE.EXHAUSTION_VALVE.ordinal()){
			return new TEExhaustionValve();
		}
		if(meta == EnumMachinesE.WATER_PUMP.ordinal()){
			return new TEWaterPump();
		}
		if(meta == EnumMachinesE.CATALYST_REGEN.ordinal()){
			return new TECatalystRegen();
		}
		if(meta == EnumMachinesE.DISPOSER.ordinal()){
			return new TEDisposer();
		}
		if(meta == EnumMachinesE.SLURRY_DRUM.ordinal()){
			return new TESlurryDrum();
		}
		if(meta == EnumMachinesE.BUFFER_TANK.ordinal()){
			return new TEBufferTank();
		}
		if(meta == EnumMachinesE.STIRRED_TANK_BASE.ordinal()){
			return new TEStirredTankBase();
		}
		if(meta == EnumMachinesE.STIRRED_TANK_TOP.ordinal()){
			return new TEStirredTankTop();
		}
		if(meta == EnumMachinesE.STIRRED_TANK_OUT.ordinal()){
			return new TEStirredTankOut();
		}
		if(meta == EnumMachinesE.PRECIPITATION_CHAMBER.ordinal()){
			return new TEPrecipitationChamber();
		}
		if(meta == EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()){
			return new TEPrecipitationController();
		}
		if(meta == EnumMachinesE.PRECIPITATION_REACTOR.ordinal()){
			return new TEPrecipitationReactor();
		}
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			int meta = state.getBlock().getMetaFromState(state);
			if(world.getTileEntity(pos) != null){
				if(world.getTileEntity(pos) instanceof IFluidHandlingTile){
					if(!player.getHeldItemMainhand().isEmpty()){
						if (CoreUtils.isBucketType(player.getHeldItemMainhand())){
							((IFluidHandlingTile)world.getTileEntity(pos)).interactWithFluidHandler(player, hand, world, pos, facing);
							return true;
						}
					}
				}

				if(hasNullifier(player, hand)){
					handleNullifier(world, pos, player, hand, state.getBlock(), meta);
					return false;
				}
	
				if(meta == EnumMachinesE.WATER_PUMP.ordinal()){
					TEWaterPump pump = (TEWaterPump)world.getTileEntity(pos);
					if(!world.isRemote){
						if(pump.getCompressor()){ //remove upgrade
							if(CoreUtils.hasWrench(player)){
								pump.compressor = false;
								EntityItem upgrade = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), compressor);
								world.spawnEntity(upgrade);
							}
						}
						if(!pump.getCompressor()){ //add upgrade
							if(CoreUtils.hasTool(player, compressor)){
								pump.compressor = true;
								pump.markDirtyClient();
								if(!player.capabilities.isCreativeMode){
									player.getHeldItemMainhand().shrink(1);
								}
							}
						}
						if(CoreUtils.hasTool(player, iron_impeller)){
							EntityItem upgrade = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.MISC_ITEMS, 1, pump.tier + 30));
							world.spawnEntity(upgrade);
							pump.tier = 0;
							if(!player.capabilities.isCreativeMode){
								player.getHeldItemMainhand().shrink(1);
							}
						}
						if(CoreUtils.hasTool(player, aluminum_impeller)){
							EntityItem upgrade = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.MISC_ITEMS, 1, pump.tier + 30));
							world.spawnEntity(upgrade);
							pump.tier = 1;
							if(!player.capabilities.isCreativeMode){
								player.getHeldItemMainhand().shrink(1);
							}
						}
						if(CoreUtils.hasTool(player, stellite_impeller)){
							EntityItem upgrade = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(ModItems.MISC_ITEMS, 1, pump.tier + 30));
							world.spawnEntity(upgrade);
							pump.tier = 2;
							if(!player.capabilities.isCreativeMode){
								player.getHeldItemMainhand().shrink(1);
							}
						}
					}
				}
				if(meta == EnumMachinesE.CATALYST_REGEN.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.catalyst_regen_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.DISPOSER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.disposer_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.SLURRY_DRUM.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.slurry_drum_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.BUFFER_TANK.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.buffer_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.STIRRED_TANK_BASE.ordinal()){
					if(world.getTileEntity(pos.up(1)) != null && world.getTileEntity(pos.up(1)) instanceof TEStirredTankTop){
			    		player.openGui(Rhchemistry.instance, GuiHandler.stirred_tank_id, world, pos.getX(), pos.getY() + 1, pos.getZ());
					}
				}
				if(meta == EnumMachinesE.STIRRED_TANK_TOP.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.stirred_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.STIRRED_TANK_OUT.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.stirred_tank_out_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.PRECIPITATION_CHAMBER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.precipitation_chamber_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.precipitation_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		IBlockState state = world.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);

		if(CoreUtils.hasWrench(player)){
			handleRotation(world, pos, player, meta);
		}
	}



	//---------- DROP HANDLER ----------
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesE.STIRRED_TANK_TOP.ordinal()
			&& meta != EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()
			? Item.getItemFromBlock(this) : null;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesE.STIRRED_TANK_TOP.ordinal()
			&& meta != EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal();
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesE.STIRRED_TANK_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesE.STIRRED_TANK_BASE.ordinal());
		}
		if(meta == EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesE.PRECIPITATION_CHAMBER.ordinal());
		}
		return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        List<ItemStack> items = new ArrayList<ItemStack>();
        int meta = this.getMetaFromState(state);
        ItemStack itemstack = ItemStack.EMPTY;
        if(meta != EnumMachinesE.STIRRED_TANK_TOP.ordinal()
        && meta != EnumMachinesE.PRECIPITATION_CONTROLLER.ordinal()
        ){
        	itemstack = new ItemStack(this, 1, meta);
        }
        handleTileNBT(te, itemstack);
        if (!itemstack.isEmpty()){
        	items.add(itemstack);
        }
        ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){
        	spawnAsEntity(worldIn, pos, item);
        }
    }

	public void handleTileNBT(TileEntity te, ItemStack itemstack) {
        if(te != null){

    		MachinesUtils.addMachineNbt(itemstack, te);

    		if(te instanceof TEWaterPump){
        		addWaterPumpNbt(itemstack, te);
        	}
    		
    		if(te instanceof TEDisposer){
        		addDisposerNbt(itemstack, te);
        	}

    		if(te instanceof TESlurryDrum){
        		addSlurryDrumNbt(itemstack, te);
        	}

    		if(te instanceof TEBufferTank){
        		addBufferTankNbt(itemstack, te);
        	}

    		if(te instanceof TEStirredTankOut){
        		addStirredTankOutNbt(itemstack, te);
        	}

        }
	}

	private static void addWaterPumpNbt(ItemStack itemstack, TileEntity te) {
    	TEWaterPump tank = ((TEWaterPump)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		itemstack.getTagCompound().setBoolean("Compressor", tank.getCompressor());
		itemstack.getTagCompound().setInteger("Tier", tank.getTier());
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), solvent);
		}
	}
    private static void restoreWaterPumpNBT(ItemStack stack, TileEntity te) {
    	TEWaterPump tank = ((TEWaterPump)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey("Compressor")){
				boolean upg = stack.getTagCompound().getBoolean("Compressor");
				tank.compressor = upg;
			}
			if(stack.getTagCompound().hasKey("Tier")){
				int upg = stack.getTagCompound().getInteger("Tier");
				tank.tier = upg;
			}
	    	if(stack.hasTagCompound() && tank != null){
				if(stack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
					tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
				}
	    	}
    	}
	}

    private static void addDisposerNbt(ItemStack itemstack, TileEntity te) {
    	TEDisposer disposer = ((TEDisposer)te);
        NBTTagList nbttaglist = new NBTTagList();
        itemstack.getTagCompound().setInteger("Interval", disposer.getInterval());
        itemstack.getTagCompound().setBoolean("Lock", disposer.isLocked());
        for (int i = 0; i < disposer.lockList.size(); ++i){
            if (!disposer.lockList.get(i).isEmpty()){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                disposer.lockList.get(i).writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        itemstack.getTagCompound().setTag("Filter", nbttaglist);
	}
	private static void restoreDisposerNBT(ItemStack stack, TileEntity te) {
		TEDisposer disposer = (TEDisposer) te;
    	if(stack.hasTagCompound() && disposer != null){
    		disposer.lock = stack.getTagCompound().getBoolean("Lock");
    		disposer.interval = stack.getTagCompound().getInteger("Interval");
    		if(stack.getTagCompound().hasKey("Filter")){
		        NBTTagList nbttaglist = stack.getTagCompound().getTagList("Filter", 10);
		        disposer.lockList = new ArrayList<ItemStack>();
		        disposer.resetLock();
		        for (int i = 0; i < nbttaglist.tagCount(); ++i){
		            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
		            int j = nbttagcompound.getByte("Slot");
		            if (j >= 0 && j < disposer.lockList.size()){
		            	disposer.lockList.add(j, new ItemStack(nbttagcompound));
		            }
		        }
    		}
		}
	}

	private static void addSlurryDrumNbt(ItemStack itemstack, TileEntity te) {
    	TESlurryDrum tank = ((TESlurryDrum)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), solvent);
		}
		if(tank.getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        tank.filter.writeToNBT(filterNBT);
	        itemstack.getTagCompound().setTag("Filter", filterNBT);
		}
	}
    private static void restoreSlurryDrumNBT(ItemStack stack, TileEntity te) {
    	TESlurryDrum tank = ((TESlurryDrum)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
			if(stack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("Filter"));
			}
    	}
	}
    
	private static void addBufferTankNbt(ItemStack itemstack, TileEntity te) {
    	TEBufferTank tank = ((TEBufferTank)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), solvent);
		}
	}
    private static void restoreBufferTankNBT(ItemStack stack, TileEntity te) {
    	TEBufferTank tank = ((TEBufferTank)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
    	}
	}

	private static void addStirredTankOutNbt(ItemStack itemstack, TileEntity te) {
    	TEStirredTankOut tank = ((TEStirredTankOut)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), solvent);
		}
	}
    private static void restoreStirredTankOutNBT(ItemStack stack, TileEntity te) {
    	TEStirredTankOut tank = ((TEStirredTankOut)te);
    	if(stack.hasTagCompound() && tank != null){
	    	if(stack.hasTagCompound() && tank != null){
				if(stack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
					tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
				}
	    	}
    	}
	}

}