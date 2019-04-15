package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.enums.EnumMachinesB;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEAirCompressor;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGanExpanderBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGanExpanderTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasPressurizer;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasPurifier;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierBurner;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierCooler;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasifierTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEHeatExchangerBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEHeatExchangerTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEParticulateCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPressureVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPurifierCycloneBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPurifierCycloneCap;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPurifierCycloneTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TESlurryPond;
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
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class MachinesB extends MachineIO {
	public static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumMachinesB.class);

	public MachinesB(String name) {
		super(name, Material.IRON, EnumMachinesB.getNames(), 3.0F, 5.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMachinesB.values()[0]).withProperty(FACING, EnumFacing.NORTH));
	}



	//---------- VARIANT HANDLER ----------
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumMachinesB.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMachinesB)state.getValue(VARIANT)).ordinal();
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
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items){
		super.getSubBlocks(tab, items);
		for (final Fluid gas : ModFluids.GASES) {
			ItemStack filledvessel = new ItemStack(ModBlocks.MACHINES_B, 1, EnumMachinesB.PRESSURE_VESSEL.ordinal());
			filledvessel.getItem().setCreativeTab(Reference.RockhoundingChemistry);
			filledvessel.setTagCompound(new NBTTagCompound());
			NBTTagCompound solvent = new NBTTagCompound();
			FluidStack gasstack = new FluidStack(gas, 1000);
			if(gas != null){
				gasstack.writeToNBT(solvent);
				filledvessel.getTagCompound().setTag(EnumFluidNbt.GAS.nameTag(), solvent);
			}
			items.add(filledvessel);
		}
	}

	@Override
	public boolean hiddenParts(int meta) {
		return meta == EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal() 
			|| meta == EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal() 
			|| meta == EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()
			|| meta == EnumMachinesB.GASIFIER_BURNER.ordinal();
	}

	@Override
	public boolean baseParts(int meta) {
		return meta == EnumMachinesB.GAN_TURBOEXPANDER_BASE.ordinal() 
			|| meta == EnumMachinesB.HEAT_EXCHANGER_BASE.ordinal() 
			|| meta == EnumMachinesB.PURIFIER_CYCLONE_BASE.ordinal()
			|| meta == EnumMachinesB.GASIFIER_COOLER.ordinal();
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(world, pos, state, placer, stack);

    	int meta = stack.getItemDamage();
    	EnumFacing isFacing = EnumFacing.getFront(2);
        world.setBlockState(pos, state.withProperty(VARIANT, EnumMachinesB.values()[meta]).withProperty(FACING, isFacing), 2);

        TileEntity te = world.getTileEntity(pos);
        if(world.getTileEntity(pos) != null){
	        if(te instanceof TESlurryPond){
	        	restoreSlurryPondNBT(stack, te);
	        }
	        if(te instanceof TEPurifierCycloneBase){
	        	TEPurifierCycloneBase cyclone = (TEPurifierCycloneBase) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, cyclone.getFacing(), placer, EnumMachinesB.PURIFIER_CYCLONE_TOP);
	        }
/*	        if(te instanceof TEParticulateCollector){
	        	restoreParticulateCollectorNBT(stack, te);
	        }*/
	        if(te instanceof TEHeatExchangerBase){
	        	TEHeatExchangerBase heat = (TEHeatExchangerBase) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, heat.getFacing(), placer, EnumMachinesB.HEAT_EXCHANGER_TOP);
	        }
	        if(te instanceof TEGanExpanderBase){
	        	TEGanExpanderBase turbine = (TEGanExpanderBase) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, turbine.getFacing(), placer, EnumMachinesB.GAN_TURBOEXPANDER_TOP);
	        }
	        if(te instanceof TEGasifierTank){
	        	restoreGasifierTankNBT(stack, te);
	        }
	        if(te instanceof TEGasifierCooler){
	        	TEGasifierCooler burner = (TEGasifierCooler) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, burner.getFacing(), placer, EnumMachinesB.GASIFIER_BURNER);
	        }
	        if(te instanceof TEPressureVessel){
	        	restorePressureVesselNBT(stack, te);
	        }
        }
    }

	@Override
	public void checkFullBlocks(World world, BlockPos pos, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesB.PURIFIER_CYCLONE_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesB.HEAT_EXCHANGER_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesB.GAN_TURBOEXPANDER_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesB.GASIFIER_COOLER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesB.GASIFIER_BURNER.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
	}

	private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EnumFacing facing, EntityLivingBase placer, EnumMachinesB prop) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), this.getDefaultState().withProperty(VARIANT, prop).withProperty(FACING, facing), 2);
			TileEntityInv top = (TileEntityInv)world.getTileEntity(pos.up());
			top.facing = facing.ordinal();
		}else{
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
		}
	}

    private void checkTopBlocks(World world, IBlockState state, IBlockState stateUp, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		TileEntity teUp = world.getTileEntity(pos.up());
		if(teUp == null || 
				(
					   (te instanceof TEPurifierCycloneBase && !(teUp instanceof TEPurifierCycloneTop))
					|| (te instanceof TEHeatExchangerBase && !(teUp instanceof TEHeatExchangerTop))
					|| (te instanceof TEGanExpanderBase && !(teUp instanceof TEGanExpanderTop))
					|| (te instanceof TEGasifierCooler && !(teUp instanceof TEGasifierBurner))
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
					   (te instanceof TEPurifierCycloneTop && !(teDown instanceof TEPurifierCycloneBase))
					|| (te instanceof TEHeatExchangerTop && !(teDown instanceof TEHeatExchangerBase))
					|| (te instanceof TEGanExpanderTop && !(teDown instanceof TEGanExpanderBase))
					|| (te instanceof TEGasifierBurner && !(teDown instanceof TEGasifierCooler))
				)
		){
			world.setBlockToAir(pos);
		}
	}



	//---------- TILE HANDLER ----------
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesB.GASIFIER_TANK.ordinal()){
			return new TEGasifierTank();
		}
		if(meta == EnumMachinesB.GASIFIER_BURNER.ordinal()){
			return new TEGasifierBurner();
		}
		if(meta == EnumMachinesB.GASIFIER_COOLER.ordinal()){
			return new TEGasifierCooler();
		}
		if(meta == EnumMachinesB.SLURRY_POND.ordinal()){
			return new TESlurryPond();
		}
		if(meta == EnumMachinesB.GAS_PRESSURIZER.ordinal()){
			return new TEGasPressurizer();
		}
		if(meta == EnumMachinesB.PURIFIER_CYCLONE_BASE.ordinal()){
			return new TEPurifierCycloneBase();
		}
		if(meta == EnumMachinesB.PURIFIER_CYCLONE_CAP.ordinal()){
			return new TEPurifierCycloneCap();
		}
		if(meta == EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()){
			return new TEPurifierCycloneTop();
		}
		if(meta == EnumMachinesB.PARTICULATE_COLLECTOR.ordinal()){
			return new TEParticulateCollector();
		}
		if(meta == EnumMachinesB.PRESSURE_VESSEL.ordinal()){
			return new TEPressureVessel();
		}
		if(meta == EnumMachinesB.GAS_PURIFIER.ordinal()){
			return new TEGasPurifier();
		}
		if(meta == EnumMachinesB.AIR_COMPRESSOR.ordinal()){
			return new TEAirCompressor();
		}
		if(meta == EnumMachinesB.HEAT_EXCHANGER_BASE.ordinal()){
			return new TEHeatExchangerBase();
		}
		if(meta == EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()){
			return new TEHeatExchangerTop();
		}
		if(meta == EnumMachinesB.GAN_TURBOEXPANDER_BASE.ordinal()){
			return new TEGanExpanderBase();
		}
		if(meta == EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()){
			return new TEGanExpanderTop();
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
	
				if(meta == EnumMachinesB.GASIFIER_TANK.ordinal()){
	    			player.openGui(Rhchemistry.instance, GuiHandler.gasifier_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.SLURRY_POND.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.slurry_pond_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.GASIFIER_BURNER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.gasifier_burner_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.GASIFIER_COOLER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gasifier_cooler_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.GAS_PURIFIER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gasifier_purifier_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.PARTICULATE_COLLECTOR.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.particulate_collector_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.PRESSURE_VESSEL.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.pressure_vessel_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.AIR_COMPRESSOR.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.air_compressor_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.HEAT_EXCHANGER_BASE.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gan_exchanger_base_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gan_exchanger_base_id, world, pos.getX(), pos.getY() - 1, pos.getZ());
				}
				if(meta == EnumMachinesB.GAS_PURIFIER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gasifier_purifier_id, world, pos.getX(), pos.getY(), pos.getZ());
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
		return meta != EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()
			|| meta != EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()
			|| meta != EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()				
			|| meta != EnumMachinesB.GASIFIER_BURNER.ordinal()				
			? Item.getItemFromBlock(this) : null;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()
			&& meta != EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()
			&& meta != EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()
			&& meta != EnumMachinesB.GASIFIER_BURNER.ordinal();
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesB.PURIFIER_CYCLONE_BASE.ordinal());
		}
		if(meta == EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesB.HEAT_EXCHANGER_BASE.ordinal());
		}
		if(meta == EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesB.GAN_TURBOEXPANDER_BASE.ordinal());
		}
		if(meta == EnumMachinesB.GASIFIER_BURNER.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesB.GASIFIER_COOLER.ordinal());
		}
		return super.getPickBlock(state, target, world, pos, player);
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        List<ItemStack> items = new ArrayList<ItemStack>();
        ItemStack itemstack = ItemStack.EMPTY;
        if(this.getMetaFromState(state) != EnumMachinesB.PURIFIER_CYCLONE_TOP.ordinal()
        && this.getMetaFromState(state) != EnumMachinesB.HEAT_EXCHANGER_TOP.ordinal()
        && this.getMetaFromState(state) != EnumMachinesB.GAN_TURBOEXPANDER_TOP.ordinal()
        && this.getMetaFromState(state) != EnumMachinesB.GASIFIER_BURNER.ordinal()){
        	itemstack = new ItemStack(this, 1, this.getMetaFromState(state));
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

			if(te instanceof TEGasifierTank){
        		addGasifierTankNbt(itemstack, te);
        	}
        	if(te instanceof TEPressureVessel){
        		addPressureVesselNbt(itemstack, te);
        	}
        	if(te instanceof TESlurryPond){
        		addSlurryPondNbt(itemstack, te);
        	}
/*        	if(te instanceof TEParticulateCollector){
        		addParticulateCollectorNbt(itemstack, te);
        	}*/
        }
	}

	private static void addSlurryPondNbt(ItemStack itemstack, TileEntity te) {
    	TESlurryPond tank = ((TESlurryPond)te);
		NBTTagCompound water = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(water);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), water);
		}
		NBTTagCompound slurry = new NBTTagCompound(); 
		if(tank.outputTank.getFluid() != null){
			tank.outputTank.getFluid().writeToNBT(slurry);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), slurry);
		}
		if(tank.getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        tank.filter.writeToNBT(filterNBT);
	        itemstack.getTagCompound().setTag("Filter", filterNBT);
		}
	}
    private static void restoreSlurryPondNBT(ItemStack itemstack, TileEntity te) {
    	TESlurryPond tank = ((TESlurryPond)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
			}
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.outputTank.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
			if(itemstack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Filter"));
			}
    	}
	}

	private static void addGasifierTankNbt(ItemStack itemstack, TileEntity te) {
    	TEGasifierTank tank = ((TEGasifierTank)te);
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
    private static void restoreGasifierTankNBT(ItemStack itemstack, TileEntity te) {
    	TEGasifierTank tank = ((TEGasifierTank)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
			if(itemstack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Filter"));
			}
    	}
	}
/*
	private static void addParticulateCollectorNbt(ItemStack itemstack, TileEntity te) {
    	TEParticulateCollector tank = ((TEParticulateCollector)te);
		itemstack.getTagCompound().setInteger("PrimaryCount", tank.primaryCount);
		itemstack.getTagCompound().setInteger("SecondaryCount", tank.secondaryCount);
	}
	private static void restoreParticulateCollectorNBT(ItemStack itemstack, TileEntity te) {
    	TEParticulateCollector tank = ((TEParticulateCollector)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey("PrimaryCount")){
				tank.primaryCount = itemstack.getTagCompound().getInteger("PrimaryCount");
			}
			if(itemstack.getTagCompound().hasKey("SecondaryCount")){
				tank.secondaryCount = itemstack.getTagCompound().getInteger("SecondaryCount");
			}
    	}
	}
*/
	private static void addPressureVesselNbt(ItemStack itemstack, TileEntity te) {
    	TEPressureVessel tank = ((TEPressureVessel)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.GAS.nameTag(), solvent);
		}
		if(tank.getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        tank.filter.writeToNBT(filterNBT);
	        itemstack.getTagCompound().setTag("Filter", filterNBT);
		}
		itemstack.getTagCompound().setInteger("Collapse", tank.collapseRate);
	}
    private static void restorePressureVesselNBT(ItemStack itemstack, TileEntity te) {
    	TEPressureVessel tank = ((TEPressureVessel)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.GAS.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.GAS.nameTag())));
			}

			if(itemstack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Filter"));
			}
			tank.collapseRate = itemstack.getTagCompound().getInteger("Collapse");
    	}
	}

}