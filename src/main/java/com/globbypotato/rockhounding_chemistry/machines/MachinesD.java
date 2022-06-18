package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesD;
import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEDepositionController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPullingCrucibleController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileVessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEFluidpedia;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TETransposer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEWasteDumper;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEDepositionBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEGasHolderTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEMetalAlloyerTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEPullingCrucibleTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEContainmentTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEFlotationTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.utilities.TEGasHolderBase;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
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
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class MachinesD extends MachineIO {
	public static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumMachinesD.class);
    public static final AxisAlignedBB FLOTATION_BLOCK_AABB = new AxisAlignedBB(0.2D, 0.0D, 0.2D, 0.8D, 1.0D, 0.8D);

	public MachinesD(String name) {
		super(name, Material.IRON, EnumMachinesD.getNames(), 3.0F, 5.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMachinesD.values()[0]).withProperty(FACING, EnumFacing.NORTH));
	}



	//---------- VARIANT HANDLER ----------
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumMachinesD.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMachinesD)state.getValue(VARIANT)).ordinal();
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
			ItemStack filledvessel = new ItemStack(ModBlocks.MACHINES_D, 1, EnumMachinesD.GAS_HOLDER_BASE.ordinal());
			filledvessel.getItem().setCreativeTab(Reference.RockhoundingChemistry);
			filledvessel.setTagCompound(new NBTTagCompound());
			NBTTagCompound solvent = new NBTTagCompound();
			FluidStack gasstack = new FluidStack(gas, 1000000);
			if(gas != null){
				gasstack.writeToNBT(solvent);
				filledvessel.getTagCompound().setTag(EnumFluidNbt.GAS.nameTag(), solvent);
			}
			items.add(filledvessel);
		}
	}

	@Override
	public boolean hiddenParts(int meta) {
		return meta == EnumMachinesD.METAL_ALLOYER_TANK.ordinal()
			|| meta == EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()
			|| meta == EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()
			|| meta == EnumMachinesD.GAS_HOLDER_TOP.ordinal()
			|| meta == EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal();
	}

    @Override
	public boolean baseParts(int meta) {
		return meta == EnumMachinesD.METAL_ALLOYER.ordinal()
			|| meta == EnumMachinesD.MATERIAL_CABINET_BASE.ordinal()
			|| meta == EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal()
			|| meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal()
			|| meta == EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal();
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(world, pos, state, placer, stack);

    	int meta = stack.getItemDamage();
    	EnumFacing isFacing = EnumFacing.getFront(2);
        world.setBlockState(pos, state.withProperty(VARIANT, EnumMachinesD.values()[meta]).withProperty(FACING, isFacing), 2);

        TileEntity te = world.getTileEntity(pos);
        if(world.getTileEntity(pos) != null){
	        if(te instanceof TEMaterialCabinetBase){
	        	TEMaterialCabinetBase cabinet = (TEMaterialCabinetBase) world.getTileEntity(pos);
	        	restoreCabinetNBT(stack, te);
	        	setOrDropBlock(world, state, pos, cabinet.getFacing(), placer, EnumMachinesD.MATERIAL_CABINET_TOP);
	        }
	        if(te instanceof TEMetalAlloyerController){
	        	TEMetalAlloyerController reactor = (TEMetalAlloyerController) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesD.METAL_ALLOYER_TANK);
	        }
	        if(te instanceof TEDepositionBase){
	        	TEDepositionBase reactor = (TEDepositionBase) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER);
	        }
	        if(te instanceof TEGasHolderBase){
	        	TEGasHolderBase reactor = (TEGasHolderBase) world.getTileEntity(pos);
	        	restoreGasHolderNBT(stack, te);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesD.GAS_HOLDER_TOP);
	        }
	        if(te instanceof TEPullingCrucibleController){
	        	TEPullingCrucibleController reactor = (TEPullingCrucibleController) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesD.PULLING_CRUCIBLE_TOP);
	        }
	        if(te instanceof TEOrbiter){
	        	restoreOrbiterNBT(stack, te);
	        }
	        if(te instanceof TETransposer){
	        	restoreTransposerNBT(stack, te);
	        }
	        if(te instanceof TEWasteDumper){
	        	restoreWasteDumperNBT(stack, te);
	        }
	        if(te instanceof TEFlotationTank){
	        	restoreFlotationTankNBT(stack, te);
	        }
	        if(te instanceof TEContainmentTank){
	        	restoreContainmentTankNBT(stack, te);
	        }

        }
    }

	@Override
	public void checkFullBlocks(World world, BlockPos pos, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesD.MATERIAL_CABINET_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesD.METAL_ALLOYER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesD.METAL_ALLOYER_TANK.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesD.GAS_HOLDER_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
	}

	private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EnumFacing facing, EntityLivingBase placer, EnumMachinesD prop) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), this.getDefaultState().withProperty(VARIANT, prop).withProperty(FACING, facing), 2);
			TileEntityInv top = (TileEntityInv)world.getTileEntity(pos.up());
			top.facing = facing.ordinal();
		}else{
			int meta = state.getBlock().getMetaFromState(state);
			if(meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal()
			|| meta == EnumMachinesD.MATERIAL_CABINET_BASE.ordinal()){
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
					   (te instanceof TEMaterialCabinetBase && !(teUp instanceof TEMaterialCabinetTop))
 				    || (te instanceof TEMetalAlloyerController && !(teUp instanceof TEMetalAlloyerTank))
 				    || (te instanceof TEDepositionBase && !(teUp instanceof TEDepositionController))
 				    || (te instanceof TEGasHolderBase && !(teUp instanceof TEGasHolderTop))
 				    || (te instanceof TEPullingCrucibleController && !(teUp instanceof TEPullingCrucibleTop))
				)
		){
			ItemStack itemstack = this.getSilkTouchDrop(state);
			if(meta == EnumMachinesD.MATERIAL_CABINET_BASE.ordinal()
			|| meta == EnumMachinesD.METAL_ALLOYER.ordinal()
			|| meta == EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal()
			|| meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal()
			|| meta == EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal()){
				handleTileNBT(te, itemstack);
			}
	        spawnAsEntity(world, pos, itemstack);
	        world.setBlockToAir(pos);
		}
	}

	private void checkBaseBlocks(World world, IBlockState state, BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		TileEntity teDown = world.getTileEntity(pos.down());
		if(teDown == null || 
				(
					   (te instanceof TEMaterialCabinetTop && !(teDown instanceof TEMaterialCabinetBase))
					|| (te instanceof TEMetalAlloyerTank && !(teDown instanceof TEMetalAlloyerController))
					|| (te instanceof TEDepositionController && !(teDown instanceof TEDepositionBase))
 				    || (te instanceof TEGasHolderTop && !(teDown instanceof TEGasHolderBase))
 				    || (te instanceof TEPullingCrucibleTop && !(teDown instanceof TEPullingCrucibleController))
				)
		){
			world.setBlockToAir(pos);
		}
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		int meta = state.getBlock().getMetaFromState(state);
        return meta == EnumMachinesD.FLOTATION_TANK.ordinal() ? FLOTATION_BLOCK_AABB : FULL_BLOCK_AABB;
    }

	public boolean canEmitSignal(IBlockState state){
		int meta = state.getBlock().getMetaFromState(state);
        return meta == EnumMachinesD.CONTAINMENT_TANK.ordinal() 
        	|| meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal();
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
    public int getWeakPower(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing side){
		int currentPower = 0;
        TileEntity te = world.getTileEntity(pos);
        if(te != null){
        	if(te instanceof TileTank){
	        	TileTank tank = (TileTank)te;
	        	currentPower = tank.emittedPower();
        	}
        	if(te instanceof TileVessel){
        		TileVessel tank = (TileVessel)te;
	        	currentPower = tank.emittedPower();
        	}
        }
        return currentPower;
    }



	//---------- TILE HANDLER ----------
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesD.MATERIAL_CABINET_BASE.ordinal()){
			return new TEMaterialCabinetBase();
		}
		if(meta == EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()){
			return new TEMaterialCabinetTop();
		}
		if(meta == EnumMachinesD.METAL_ALLOYER.ordinal()){
			return new TEMetalAlloyerController();
		}
		if(meta == EnumMachinesD.METAL_ALLOYER_TANK.ordinal()){
			return new TEMetalAlloyerTank();
		}
		if(meta == EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal()){
			return new TEDepositionBase();
		}
		if(meta == EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()){
			return new TEDepositionController();
		}
		if(meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal()){
			return new TEGasHolderBase();
		}
		if(meta == EnumMachinesD.GAS_HOLDER_TOP.ordinal()){
			return new TEGasHolderTop();
		}
		if(meta == EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal()){
			return new TEPullingCrucibleController();
		}
		if(meta == EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()){
			return new TEPullingCrucibleTop();
		}
		if(meta == EnumMachinesD.ORBITER.ordinal()){
			return new TEOrbiter();
		}
		if(meta == EnumMachinesD.TRANSPOSER.ordinal()){
			return new TETransposer();
		}
		if(meta == EnumMachinesD.FLUIDPEDIA.ordinal()){
			return new TEFluidpedia();
		}
		if(meta == EnumMachinesD.WASTE_DUMPER.ordinal()){
			return new TEWasteDumper();
		}
		if(meta == EnumMachinesD.FLOTATION_TANK.ordinal()){
			return new TEFlotationTank();
		}
		if(meta == EnumMachinesD.CONTAINMENT_TANK.ordinal()){
			return new TEContainmentTank();
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

				if(meta == EnumMachinesD.MATERIAL_CABINET_BASE.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertConsumable(world, player, pos.up(), BaseRecipes.graduated_cylinder, TEElementsCabinetTop.CYLINDER_SLOT)) {
				    		player.openGui(Rhchemistry.instance, GuiHandler.material_cabinet_id, world, pos.getX(), pos.getY(), pos.getZ());
							TEMaterialCabinetBase cabinet = (TEMaterialCabinetBase)world.getTileEntity(pos);
							cabinet.initializaCabinet();
						}
					}else {
						tryExtractConsumable(world, player, pos.up(), TEElementsCabinetTop.CYLINDER_SLOT);
					}
				}
				if(meta == EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertConsumable(world, player, pos, BaseRecipes.graduated_cylinder, TEMaterialCabinetTop.CYLINDER_SLOT)) {
							player.openGui(Rhchemistry.instance, GuiHandler.material_cabinet_injector_id, world, pos.getX(), pos.getY(), pos.getZ());
							TEMaterialCabinetBase cabinet = (TEMaterialCabinetBase)world.getTileEntity(pos.down());
							cabinet.initializaCabinet();
						}
					}else {
						tryExtractConsumable(world, player, pos, TEMaterialCabinetTop.CYLINDER_SLOT);
					}
				}
				if(meta == EnumMachinesD.METAL_ALLOYER.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertConsumable(world, player, pos, BaseRecipes.ingot_pattern, TEMetalAlloyerController.PATTERN_SLOT)) {
							player.openGui(Rhchemistry.instance, GuiHandler.metal_alloyer_id, world, pos.getX(), pos.getY(), pos.getZ());
							TEMetalAlloyerController alloyer = (TEMetalAlloyerController)world.getTileEntity(pos);
							alloyer.showPreview();
						}
					}else {
						tryExtractConsumable(world, player, pos, TEMetalAlloyerController.PATTERN_SLOT);
					}
				}
				if(meta == EnumMachinesD.METAL_ALLOYER_TANK.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertConsumable(world, player, pos.down(), BaseRecipes.ingot_pattern, TEMetalAlloyerController.PATTERN_SLOT)) {
							if(world.getTileEntity(pos.down()) != null && world.getTileEntity(pos.down()) instanceof TEMetalAlloyerController){
								player.openGui(Rhchemistry.instance, GuiHandler.metal_alloyer_id, world, pos.getX(), pos.getY() - 1, pos.getZ());
								TEMetalAlloyerController alloyer = (TEMetalAlloyerController)world.getTileEntity(pos.down());
								alloyer.showPreview();
							}
						}
					}else {
						tryExtractConsumable(world, player, pos.down(), TEMetalAlloyerController.PATTERN_SLOT);
					}
				}
				if(meta == EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertUpgrade(world, player, pos, BaseRecipes.speed_upgrade, TEDepositionBase.SPEED_SLOT) && !canInsertUpgrade(world, player, pos, BaseRecipes.insulating_upgrade, TEDepositionBase.INSULATION_SLOT) && !canInsertUpgrade(world, player, pos, BaseRecipes.casing_upgrade, TEDepositionBase.CASING_SLOT) ) {
							player.openGui(Rhchemistry.instance, GuiHandler.deposition_chamber_id, world, pos.getX(), pos.getY(), pos.getZ());
						}
					}else {
						tryExtractUpgrade(world, player, pos, TEDepositionBase.SPEED_SLOT);
					}
				}
				if(meta == EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertUpgrade(world, player, pos.down(), BaseRecipes.speed_upgrade, TEDepositionBase.SPEED_SLOT) && !canInsertUpgrade(world, player, pos.down(), BaseRecipes.casing_upgrade, TEDepositionBase.CASING_SLOT) && !canInsertUpgrade(world, player, pos.down(), BaseRecipes.insulating_upgrade, TEDepositionBase.INSULATION_SLOT) ) {
							player.openGui(Rhchemistry.instance, GuiHandler.deposition_chamber_top_id, world, pos.getX(), pos.getY(), pos.getZ());
						}
					}else {
						tryExtractUpgrade(world, player, pos.down(), TEDepositionBase.SPEED_SLOT);
					}
				}
				if(meta == EnumMachinesD.GAS_HOLDER_BASE.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gas_holder_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.GAS_HOLDER_TOP.ordinal()){
					if(world.getTileEntity(pos.down(1)) != null && world.getTileEntity(pos.down(1)) instanceof TEGasHolderBase){
						player.openGui(Rhchemistry.instance, GuiHandler.gas_holder_id, world, pos.getX(), pos.getY() - 1, pos.getZ());
					}
				}
				if(meta == EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.pulling_crucible_base_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.pulling_crucible_top_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.ORBITER.ordinal()){
					if(!player.isSneaking()) {
						if(!canInsertUpgrade(world, player, pos, BaseRecipes.probe_upgrade, TEOrbiter.PROBE_SLOT)) {
							player.openGui(Rhchemistry.instance, GuiHandler.orbiter_id, world, pos.getX(), pos.getY(), pos.getZ());
							TEOrbiter orbiter = (TEOrbiter)world.getTileEntity(pos);
							if(orbiter.calculateRetrievedXP(player, 0) > orbiter.getXPCount()){
								orbiter.offScale = true;
							}else{
								orbiter.offScale = false;
							}
						}
					}else {
						tryExtractUpgrade(world, player, pos, TEOrbiter.PROBE_SLOT);
					}
				}
				if(meta == EnumMachinesD.TRANSPOSER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.transposer_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.FLUIDPEDIA.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.fluidpedia_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.WASTE_DUMPER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.waste_dumper_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.FLOTATION_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.flotation_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesD.CONTAINMENT_TANK.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.containment_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
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
		return meta != EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()
			&& meta != EnumMachinesD.METAL_ALLOYER_TANK.ordinal()
			&& meta != EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()
			&& meta != EnumMachinesD.GAS_HOLDER_TOP.ordinal()
			&& meta != EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()
			? Item.getItemFromBlock(this) : null;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()
			&& meta != EnumMachinesD.METAL_ALLOYER_TANK.ordinal()
			&& meta != EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()
			&& meta != EnumMachinesD.GAS_HOLDER_TOP.ordinal()
			&& meta != EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal();
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesD.MATERIAL_CABINET_BASE.ordinal());
		}
		if(meta == EnumMachinesD.METAL_ALLOYER_TANK.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesD.METAL_ALLOYER.ordinal());
		}
		if(meta == EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesD.DEPOSITION_CHAMBER_BASE.ordinal());
		}
		if(meta == EnumMachinesD.GAS_HOLDER_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesD.GAS_HOLDER_BASE.ordinal());
		}
		if(meta == EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesD.PULLING_CRUCIBLE_CONTROLLER.ordinal());
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
        if(meta != EnumMachinesD.MATERIAL_CABINET_TOP.ordinal()
        && meta != EnumMachinesD.METAL_ALLOYER_TANK.ordinal()
        && meta != EnumMachinesD.DEPOSITION_CHAMBER_CONTROLLER.ordinal()
        && meta != EnumMachinesD.GAS_HOLDER_TOP.ordinal()
        && meta != EnumMachinesD.PULLING_CRUCIBLE_TOP.ordinal()
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

    		if(te instanceof TEMaterialCabinetBase){
        		addCabinetNbt(itemstack, te);
        	}
        	if(te instanceof TEGasHolderBase){
        		addGasHolderNbt(itemstack, te);
        	}
        	if(te instanceof TEOrbiter){
        		addOrbiterNbt(itemstack, te);
        	}
        	if(te instanceof TETransposer){
        		addTransposerNbt(itemstack, te);
        	}
        	if(te instanceof TEWasteDumper){
        		addWasteDumperNbt(itemstack, te);
        	}
        	if(te instanceof TEFlotationTank){
        		addFlotationTankNbt(itemstack, te);
        	}
        	if(te instanceof TEContainmentTank){
        		addContainmentTankNbt(itemstack, te);
        	}
        }
	}

	private static void addCabinetNbt(ItemStack itemstack, TileEntity te) {
    	TEMaterialCabinetBase cabinet = ((TEMaterialCabinetBase)te);
		NBTTagList recipes = new NBTTagList();
		for(int i = 0; i < cabinet.MATERIAL_LIST.size(); i++){
			NBTTagCompound elements = new NBTTagCompound();
			elements.setByte("Elements", (byte)i);
			elements.setString("symbol" + i, cabinet.MATERIAL_LIST.get(i).getSymbol());
			elements.setString("oredict" + i, cabinet.MATERIAL_LIST.get(i).getOredict());
			elements.setString("name" + i, cabinet.MATERIAL_LIST.get(i).getName());
			elements.setInteger("amount" + i, cabinet.MATERIAL_LIST.get(i).getAmount());
			elements.setBoolean("extraction" + i, cabinet.MATERIAL_LIST.get(i).getExtraction());
			recipes.appendTag(elements);
		}
		itemstack.getTagCompound().setTag("MaterialList", recipes);
	}
	private static void restoreCabinetNBT(ItemStack itemstack, TileEntity te) {
		TEMaterialCabinetBase cabinet = ((TEMaterialCabinetBase)te);
		if(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("MaterialList")){
			NBTTagList recipes = itemstack.getTagCompound().getTagList("MaterialList", Constants.NBT.TAG_COMPOUND);
			if(recipes.tagCount() > 0) {
				cabinet.MATERIAL_LIST.clear();
				for(int i = 0; i < recipes.tagCount(); i++){
					NBTTagCompound elements = recipes.getCompoundTagAt(i);
		            int j = elements.getByte("Elements");
					String symbol = elements.getString("symbol" + j);
					String oredict = elements.getString("oredict" + j);
					String name = elements.getString("name" + j);
					int amount = elements.getInteger("amount" + j);
					boolean extraction = elements.getBoolean("extraction" + j);
					cabinet.MATERIAL_LIST.add(j, new MaterialCabinetRecipe(symbol, oredict, name, amount, extraction));
				}
				cabinet.reloadCabinet();
			}
		}
	}

	private static void addGasHolderNbt(ItemStack itemstack, TileEntity te) {
		TEGasHolderBase tank = ((TEGasHolderBase)te);
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
    private static void restoreGasHolderNBT(ItemStack itemstack, TileEntity te) {
    	TEGasHolderBase tank = ((TEGasHolderBase)te);
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
    
	private void addOrbiterNbt(ItemStack itemstack, TileEntity te) {
		TEOrbiter tank = ((TEOrbiter)te);
		itemstack.getTagCompound().setInteger("XPCount", tank.getXPCount());
	}
	private void restoreOrbiterNBT(ItemStack itemstack, TileEntity te) {
		TEOrbiter orbiter = ((TEOrbiter)te);
    	if(itemstack.hasTagCompound() && orbiter != null){
			if(itemstack.getTagCompound().hasKey("XPCount")){
				orbiter.xpCount = itemstack.getTagCompound().getInteger("XPCount");
			}
    	}
	}

	private static void addTransposerNbt(ItemStack itemstack, TileEntity te) {
		TETransposer tank = ((TETransposer)te);
		NBTTagCompound fluidin = new NBTTagCompound(); 
		if(tank.inputTankMain.getFluid() != null){
			tank.inputTankMain.getFluid().writeToNBT(fluidin);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID_IN.nameTag(), fluidin);
		}
		if(tank.getFilterMain() != null){
	        NBTTagCompound filterFluidNBT = new NBTTagCompound();
	        tank.filterMain.writeToNBT(filterFluidNBT);
	        itemstack.getTagCompound().setTag("Filter", filterFluidNBT);
		}
		NBTTagCompound fluidout = new NBTTagCompound(); 
		if(tank.outputTankFluid.getFluid() != null){
			tank.outputTankFluid.getFluid().writeToNBT(fluidout);
			itemstack.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), fluidout);
		}
		NBTTagCompound gasout = new NBTTagCompound(); 
		if(tank.outputTankGas.getFluid() != null){
			tank.outputTankGas.getFluid().writeToNBT(gasout);
			itemstack.getTagCompound().setTag(EnumFluidNbt.GAS.nameTag(), gasout);
		}
		itemstack.getTagCompound().setInteger("Collapse", tank.collapseRate);
	}
    private static void restoreTransposerNBT(ItemStack itemstack, TileEntity te) {
    	TETransposer tank = ((TETransposer)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.FLUID_IN.nameTag())){
				tank.inputTankMain.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID_IN.nameTag())));
			}
			if(itemstack.getTagCompound().hasKey("Filter")){
				tank.filterMain = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Filter"));
			}
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.outputTankFluid.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.GAS.nameTag())){
				tank.outputTankGas.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.GAS.nameTag())));
			}
			tank.collapseRate = itemstack.getTagCompound().getInteger("Collapse");
    	}
	}

	private static void addWasteDumperNbt(ItemStack itemstack, TileEntity te) {
		TEWasteDumper tank = ((TEWasteDumper)te);
		if(tank.getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        tank.filter.writeToNBT(filterNBT);
	        itemstack.getTagCompound().setTag("Filter", filterNBT);
		}
	}
    private static void restoreWasteDumperNBT(ItemStack itemstack, TileEntity te) {
    	TEWasteDumper tank = ((TEWasteDumper)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Filter"));
			}
    	}
	}

    private static void addFlotationTankNbt(ItemStack itemstack, TileEntity te) {
    	TEFlotationTank tank = ((TEFlotationTank)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
		if(tank.getFilterSolvent() != null){
	        NBTTagCompound filterSolventNBT = new NBTTagCompound();
	        tank.filterSolvent.writeToNBT(filterSolventNBT);
	        itemstack.getTagCompound().setTag("FilterSolvent", filterSolventNBT);
		}
	}
    private static void restoreFlotationTankNBT(ItemStack stack, TileEntity te) {
    	TEFlotationTank tank = ((TEFlotationTank)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
			}
			if(stack.getTagCompound().hasKey("FilterSolvent")){
				tank.filterSolvent = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("FilterSolvent"));
			}
    	}
	}

	private static void addContainmentTankNbt(ItemStack itemstack, TileEntity te) {
		TEContainmentTank tank = ((TEContainmentTank)te);
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
    private static void restoreContainmentTankNBT(ItemStack stack, TileEntity te) {
    	TEContainmentTank tank = ((TEContainmentTank)te);
    	if(stack.hasTagCompound() && tank != null){
			if(stack.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.FLUID.nameTag())));
			}
			if(stack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag("Filter"));
			}
    	}
	}

}