package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesA;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEEvaporationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidInputTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidOutputTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEFluidTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasExpander;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabBlenderController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabBlenderTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabOvenChamber;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELabOvenController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMineralSizerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEProfilingBench;
import com.globbypotato.rockhounding_chemistry.machines.tile.TESeasoningRack;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEServer;
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
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;

public class MachinesA extends MachineIO {
	public static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumMachinesA.class);
    public static final AxisAlignedBB TANK_BLOCK_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.4D, 1.0D);

	public MachinesA(String name) {
		super(name, Material.IRON, EnumMachinesA.getNames(), 3.0F, 5.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMachinesA.values()[0]).withProperty(FACING, EnumFacing.NORTH));
	}



	//---------- VARIANT HANDLER ----------
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumMachinesA.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMachinesA)state.getValue(VARIANT)).ordinal();
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
		return meta == EnumMachinesA.LAB_BLENDER_TANK.ordinal() 
			|| meta == EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal();
	}

    @Override
	public boolean baseParts(int meta) {
		return meta == EnumMachinesA.LAB_BLENDER_CONTROLLER.ordinal() 
			|| meta == EnumMachinesA.LAB_OVEN_CHAMBER.ordinal();
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(world, pos, state, placer, stack);

    	int meta = stack.getItemDamage();
    	EnumFacing isFacing = EnumFacing.getFront(2);
        world.setBlockState(pos, state.withProperty(VARIANT, EnumMachinesA.values()[meta]).withProperty(FACING, isFacing), 2);

        TileEntity te = world.getTileEntity(pos);
        if(world.getTileEntity(pos) != null){
	        if(te instanceof TEMineralSizerController){
	        	restoreMineralSizerControllerNBT(stack, te);
	        }
	        if(te instanceof TEFluidTank){
	        	restoreFluidTankNBT(stack, te);
	        }
	        if(te instanceof TEPowerGenerator){
	        	restorePowerGeneratorNBT(stack, te);
	        }
	        if(te instanceof TEFluidInputTank){
	        	restoreLabOvenIntankNBT(stack, te);
	        }
	        if(te instanceof TEFluidOutputTank){
	        	restoreLabOvenOuttankNBT(stack, te);
	        }
	        if(te instanceof TELabBlenderController){
	        	TELabBlenderController chamber = (TELabBlenderController) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, chamber.getFacing(), placer, EnumMachinesA.LAB_BLENDER_TANK);
	        }
	        if(te instanceof TELabOvenChamber){
	        	TELabOvenChamber chamber = (TELabOvenChamber) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, chamber.getFacing(), placer, EnumMachinesA.LAB_OVEN_CONTROLLER);
	        }
        }
    }

	private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EnumFacing facing, EntityLivingBase placer, EnumMachinesA prop) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), this.getDefaultState().withProperty(VARIANT, prop).withProperty(FACING, facing), 2);
			TileEntityInv top = (TileEntityInv)world.getTileEntity(pos.up());
			top.facing = facing.ordinal();
		}else{
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
		}
	}

	@Override
	public void checkFullBlocks(World world, BlockPos pos, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesA.LAB_OVEN_CHAMBER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		
		if(meta == EnumMachinesA.LAB_BLENDER_CONTROLLER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesA.LAB_BLENDER_TANK.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
	}

    private void checkTopBlocks(World world, IBlockState state, IBlockState stateUp, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		TileEntity teUp = world.getTileEntity(pos.up());
		if(teUp == null || 
				(
					   (te instanceof TELabOvenChamber && !(teUp instanceof TELabOvenController))
					|| (te instanceof TELabBlenderController && !(teUp instanceof TELabBlenderTank))
				)
		){
			ItemStack itemstack = this.getSilkTouchDrop(state);
	        spawnAsEntity(world, pos, itemstack);
	        world.setBlockToAir(pos);
		}
	}

	private static void checkBaseBlocks(World world, IBlockState state, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		TileEntity teDown = world.getTileEntity(pos.down());
		if(teDown == null || 
				(
					   (te instanceof TELabOvenController && !(teDown instanceof TELabOvenChamber))
				    || (te instanceof TELabBlenderTank && !(teDown instanceof TELabBlenderController))
				)
		){
			world.setBlockToAir(pos);
		}
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		int meta = state.getBlock().getMetaFromState(state);
        return meta == EnumMachinesA.EVAPORATION_TANK.ordinal() ? TANK_BLOCK_AABB : FULL_BLOCK_AABB;
    }

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof TEMineralSizerController){
			TEMineralSizerController sizer = (TEMineralSizerController)te;
			if(sizer.hasTankA()){
				sizer.getTankA().activation = false;
				sizer.getTankA().markDirtyClient();
			}
			if(sizer.hasTankB()){
				sizer.getTankB().activation = false;
				sizer.getTankB().markDirtyClient();
			}
		}
		super.breakBlock(world, pos, state);
	}



	//---------- TILE HANDLER ----------
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesA.SIZER_CONTROLLER.ordinal()){
			return new TEMineralSizerController();
		}else if(meta == EnumMachinesA.SIZER_TANK.ordinal()){
			return new TEMineralSizerTank();
		}else if(meta == EnumMachinesA.POWER_GENERATOR.ordinal()){
			return new TEPowerGenerator();
		}else if(meta == EnumMachinesA.SIZER_COLLECTOR.ordinal()){
			return new TEMineralSizerCollector();
		}else if(meta == EnumMachinesA.FLUID_TANK.ordinal()){
			return new TEFluidTank();
		}else if(meta == EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()){
			return new TELabOvenController();
		}else if(meta == EnumMachinesA.LAB_OVEN_CHAMBER.ordinal()){
			return new TELabOvenChamber();
		}else if(meta == EnumMachinesA.FLUID_INPUT_TANK.ordinal()){
			return new TEFluidInputTank();
		}else if(meta == EnumMachinesA.FLUID_OUTPUT_TANK.ordinal()){
			return new TEFluidOutputTank();
		}else if(meta == EnumMachinesA.LAB_BLENDER_CONTROLLER.ordinal()){
			return new TELabBlenderController();
		}else if(meta == EnumMachinesA.LAB_BLENDER_TANK.ordinal()){
			return new TELabBlenderTank();
		}else if(meta == EnumMachinesA.PROFILING_BENCH.ordinal()){
			return new TEProfilingBench();
		}else if(meta == EnumMachinesA.EVAPORATION_TANK.ordinal()){
			return new TEEvaporationTank();
		}else if(meta == EnumMachinesA.SEASONING_RACK.ordinal()){
			return new TESeasoningRack();
		}else if(meta == EnumMachinesA.SERVER.ordinal()){
			return new TEServer();
		}else if(meta == EnumMachinesA.GAS_EXPANDER.ordinal()){
			return new TEGasExpander();
		}
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			int meta = state.getBlock().getMetaFromState(state);
			if(world.getTileEntity(pos) != null){
	
				if(world.getTileEntity(pos) instanceof IFluidHandlingTile){
					if (CoreUtils.isBucketType(player.getHeldItemMainhand())){
						((IFluidHandlingTile)world.getTileEntity(pos)).interactWithFluidHandler(player, hand, world, pos, facing);
						return true;
					}
				}
				
				if(hasNullifier(player, hand)){
					handleNullifier(world, pos, player, hand, state.getBlock(), meta);
					return false;
				}
	
				if(meta == EnumMachinesA.SIZER_CONTROLLER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.mineral_sizer_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.SIZER_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.mineral_sizer_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.POWER_GENERATOR.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.power_generator_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.SIZER_COLLECTOR.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.mineral_sizer_collector_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.FLUID_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.fluid_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.lab_oven_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.LAB_OVEN_CHAMBER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.lab_oven_controller_id, world, pos.getX(), pos.getY() + 1, pos.getZ());
				}
				if(meta == EnumMachinesA.FLUID_INPUT_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.lab_oven_intank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.FLUID_OUTPUT_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.lab_oven_outtank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.LAB_BLENDER_CONTROLLER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.lab_blender_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.LAB_BLENDER_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.lab_blender_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.PROFILING_BENCH.ordinal()){
					TEProfilingBench casting = (TEProfilingBench)world.getTileEntity(pos);
					ItemStack outputStack = casting.outputSlot().copy();
					if(!outputStack.isEmpty()){
						EntityItem outEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, outputStack);
							outEntity.motionX = 0; outEntity.motionY = 0; outEntity.motionZ = 0;
							if(!world.isRemote){
								world.spawnEntity(outEntity);
							}
				            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.AMBIENT, 0.5F, this.rand.nextFloat() * 0.1F + 0.9F);
							casting.getOutput().extractItem(TileEntityInv.OUTPUT_SLOT, outputStack.getCount(), false);
					}else{
						ItemStack inputStack = casting.inputSlot().copy();
						if(!inputStack.isEmpty()){
							EntityItem inputEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, inputStack);
							inputEntity.motionX = 0; inputEntity.motionY = 0; inputEntity.motionZ = 0;
							if(!world.isRemote){
								world.spawnEntity(inputEntity);
							}
				            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.AMBIENT, 0.5F, this.rand.nextFloat() * 0.1F + 0.9F);
							casting.getInput().extractItem(TileEntityInv.INPUT_SLOT, inputStack.getCount(), false);
						}else{
							if(inputStack.isEmpty()){
								if(!player.getHeldItemMainhand().isEmpty()){
									if(casting.isValidInput(player.getHeldItemMainhand())){
										int possibleStack = casting.inputSlot().getMaxStackSize() - casting.inputSlot().getCount();
										int availableStack = player.getHeldItemMainhand().getCount();
										casting.getInput().insertItem(TileEntityInv.INPUT_SLOT, new ItemStack(player.getHeldItemMainhand().getItem(), 1, player.getHeldItemMainhand().getItemDamage()), false);
										player.getHeldItemMainhand().shrink(1);
							            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.AMBIENT, 0.5F, this.rand.nextFloat() * 0.1F + 0.9F);
									}
								}
							}
						}
					}
				}
				if(meta == EnumMachinesA.EVAPORATION_TANK.ordinal()){
			    	ItemStack heldItem = player.getHeldItemMainhand();
					if(!heldItem.isEmpty() && heldItem.getItem() instanceof ItemSpade && facing == EnumFacing.UP){
						TEEvaporationTank tank = (TEEvaporationTank)world.getTileEntity(pos);
						if(tank.getStage() == tank.finalStage()){
							ItemStack outputStack = tank.outputSlot().copy();
							EntityItem outEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, outputStack);
							outEntity.motionX = 0; outEntity.motionY = 0; outEntity.motionZ = 0;
							if(!world.isRemote){
								world.spawnEntity(outEntity);
							}
				            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_SAND_PLACE, SoundCategory.AMBIENT, 0.5F, this.rand.nextFloat() * 0.1F + 0.9F);
							tank.getOutput().extractItem(TileEntityInv.OUTPUT_SLOT, outputStack.getCount(), false);
							tank.stage = 0;
			    			if(!player.capabilities.isCreativeMode && heldItem.isItemStackDamageable()){
				    			int damageItem = heldItem.getItemDamage() + 1;
				    			heldItem.setItemDamage(damageItem);
				    			if(damageItem >= heldItem.getMaxDamage()){
				    				heldItem.shrink(1);
				    			}
			    			}
						}
					}
					if(CoreUtils.hasWrench(player)){
						TEEvaporationTank tank = (TEEvaporationTank)world.getTileEntity(pos);
						if(tank.getPurge() < tank.finalStage()){
							tank.purge++;
						}else{
							tank.purge = 0;
						}
					}

				}
				if(meta == EnumMachinesA.SEASONING_RACK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.seasoning_rack_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.SERVER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.server_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesA.GAS_EXPANDER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.gas_expander_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
			}
		}
		return true;
	}

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		IBlockState state = world.getBlockState(pos);
		int meta = state.getBlock().getMetaFromState(state);
		if(!CoreUtils.hasWrench(player)){
			if(meta == EnumMachinesA.PROFILING_BENCH.ordinal()){
				if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TEProfilingBench){
					TEProfilingBench bench = (TEProfilingBench)world.getTileEntity(pos);
					if(!player.isSneaking()){
						if(bench.getCurrentCast() < EnumCasting.size() - 1){
							bench.currentCast++;
						}else{
							bench.currentCast = 0;
						}
					}else{
						if(bench.getCurrentCast() > 0){
							bench.currentCast--;
						}else{
							bench.currentCast = EnumCasting.size() - 1;
						}
					}
				}
			}
		}		
		if(CoreUtils.hasWrench(player)){
			handleRotation(world, pos, player, meta);
		}
	}



	//---------- DROP HANDLER ----------
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()
			&& meta != EnumMachinesA.LAB_BLENDER_TANK.ordinal()
			? Item.getItemFromBlock(this) : null;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()
			&& meta != EnumMachinesA.LAB_BLENDER_TANK.ordinal();
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesA.LAB_OVEN_CHAMBER.ordinal());
		}
		if(meta == EnumMachinesA.LAB_BLENDER_TANK.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesA.LAB_BLENDER_CONTROLLER.ordinal());
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
        if(meta != EnumMachinesA.LAB_OVEN_CONTROLLER.ordinal()
        && meta != EnumMachinesA.LAB_BLENDER_TANK.ordinal()){
        	itemstack = new ItemStack(this, 1, this.getMetaFromState(state));
        }
        if(te != null){

        	MachinesUtils.addMachineNbt(itemstack, te);

        	if(te instanceof TEMineralSizerController){
        		addMineralSizerControllerNbt(itemstack, te);
        	}
        	if(te instanceof TEFluidTank){
        		addFluidTankNbt(itemstack, te);
        	}
        	if(te instanceof TEPowerGenerator){
        		addPowerGeneratorNbt(itemstack, te);
        	}
        	if(te instanceof TEFluidInputTank){
        		addLabOvenIntankNbt(itemstack, te);
        	}
        	if(te instanceof TEFluidOutputTank){
        		addLabOvenOuttankNbt(itemstack, te);
        	}
        }
        if (!itemstack.isEmpty()){
        	items.add(itemstack);
        }
        ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){
        	spawnAsEntity(worldIn, pos, item);
        }
    }

	private static void addFluidTankNbt(ItemStack itemstack, TileEntity te) {
    	TEFluidTank tank = ((TEFluidTank)te);
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
    private static void restoreFluidTankNBT(ItemStack stack, TileEntity te) {
    	TEFluidTank tank = ((TEFluidTank)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
			if(stack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("Filter"));
			}
    	}
	}

    private static void addPowerGeneratorNbt(ItemStack stack, TileEntity te) {
    	TEPowerGenerator engine = ((TEPowerGenerator)te);
    	stack.getTagCompound().setBoolean("FuelModule", engine.enablePower());
    	stack.getTagCompound().setBoolean("RedstoneModule", engine.enableRedstone());
    }
	private static void restorePowerGeneratorNBT(ItemStack stack, TileEntity te) {
		TEPowerGenerator engine = ((TEPowerGenerator)te);
    	if(stack.hasTagCompound() && engine != null){
    		engine.enablePower = stack.getTagCompound().getBoolean("FuelModule");
    		engine.enableRedstone = stack.getTagCompound().getBoolean("RedstoneModule");
    	}
	}

	private static void addMineralSizerControllerNbt(ItemStack itemstack, TileEntity te) {
		TEMineralSizerController controller = ((TEMineralSizerController)te);
    	itemstack.getTagCompound().setInteger("Comminution", controller.getComminution());
	}
	private static void restoreMineralSizerControllerNBT(ItemStack stack, TileEntity te) {
		TEMineralSizerController controller = ((TEMineralSizerController)te);
		if(stack.hasTagCompound() && controller != null){
			if(stack.getTagCompound().hasKey("Comminution")){
	        	int comm = stack.getTagCompound().getInteger("Comminution");
	        	controller.comminution = comm;
			}
		}
	}

    private static void addLabOvenIntankNbt(ItemStack itemstack, TileEntity te) {
    	TEFluidInputTank tank = ((TEFluidInputTank)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.solventTank.getFluid() != null){
			tank.solventTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
		if(tank.getFilterSolvent() != null){
	        NBTTagCompound filterSolventNBT = new NBTTagCompound();
	        tank.filterSolvent.writeToNBT(filterSolventNBT);
	        itemstack.getTagCompound().setTag("FilterSolvent", filterSolventNBT);
		}

		NBTTagCompound reagent = new NBTTagCompound(); 
		if(tank.reagentTank.getFluid() != null){
			tank.reagentTank.getFluid().writeToNBT(reagent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.REAGENT.nameTag(), reagent);
		}
		if(tank.getFilterSolvent() != null){
	        NBTTagCompound filterReagentNBT = new NBTTagCompound();
	        tank.filterReagent.writeToNBT(filterReagentNBT);
	        itemstack.getTagCompound().setTag("FilterReagent", filterReagentNBT);
		}

	}
    private static void restoreLabOvenIntankNBT(ItemStack stack, TileEntity te) {
    	TEFluidInputTank tank = ((TEFluidInputTank)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
				tank.solventTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
			}
			if(stack.getTagCompound().hasKey("FilterSolvent")){
				tank.filterSolvent = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("FilterSolvent"));
			}
			if(stack.getTagCompound().hasKey(EnumFluidNbt.REAGENT.nameTag())){
				tank.reagentTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.REAGENT.nameTag())));
			}
			if(stack.getTagCompound().hasKey("FilterReagent")){
				tank.filterReagent = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("FilterReagent"));
			}
    	}
	}

    private static void addLabOvenOuttankNbt(ItemStack itemstack, TileEntity te) {
    	TEFluidOutputTank tank = ((TEFluidOutputTank)te);
		NBTTagCompound solution = new NBTTagCompound(); 
		if(tank.solutionTank.getFluid() != null){
			tank.solutionTank.getFluid().writeToNBT(solution);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLUTION.nameTag(), solution);
		}

		NBTTagCompound byproduct = new NBTTagCompound(); 
		if(tank.byproductTank.getFluid() != null){
			tank.byproductTank.getFluid().writeToNBT(byproduct);
			itemstack.getTagCompound().setTag(EnumFluidNbt.BYPRODUCT.nameTag(), byproduct);
		}

	}
    private static void restoreLabOvenOuttankNBT(ItemStack stack, TileEntity te) {
    	TEFluidOutputTank tank = ((TEFluidOutputTank)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLUTION.nameTag())){
				tank.solutionTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLUTION.nameTag())));
			}
			if(stack.getTagCompound().hasKey(EnumFluidNbt.BYPRODUCT.nameTag())){
				tank.byproductTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.BYPRODUCT.nameTag())));
			}
    	}
	}

}