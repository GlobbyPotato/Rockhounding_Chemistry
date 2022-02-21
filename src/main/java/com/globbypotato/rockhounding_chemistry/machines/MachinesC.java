package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.enums.machines.EnumMachinesC;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.io.MachineIO;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEElementsCabinetTop;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorBalance;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorInjector;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEExtractorStabilizer;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGanController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEGasCondenser;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELeachingVatCollector;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELeachingVatController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TELeachingVatTank;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMultivessel;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEReformerReactor;
import com.globbypotato.rockhounding_chemistry.machines.tile.TERetentionVat;
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
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidStack;

public class MachinesC extends MachineIO {
	public static PropertyEnum VARIANT = PropertyEnum.create("variant", EnumMachinesC.class);

	public MachinesC(String name) {
		super(name, Material.IRON, EnumMachinesC.getNames(), 3.0F, 5.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumMachinesC.values()[0]).withProperty(FACING, EnumFacing.NORTH));
	}



	//---------- VARIANT HANDLER ----------
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumMachinesC.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumMachinesC)state.getValue(VARIANT)).ordinal();
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
		return meta == EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()
			|| meta == EnumMachinesC.REFORMER_REACTOR.ordinal();
	}

    @Override
	public boolean baseParts(int meta) {
		return meta == EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal()
			|| meta == EnumMachinesC.REFORMER_CONTROLLER.ordinal();
	}

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(world, pos, state, placer, stack);

    	int meta = stack.getItemDamage();
    	EnumFacing isFacing = EnumFacing.getFront(2);
        world.setBlockState(pos, state.withProperty(VARIANT, EnumMachinesC.values()[meta]).withProperty(FACING, isFacing), 2);

        TileEntity te = world.getTileEntity(pos);
        if(world.getTileEntity(pos) != null){
	        if(te instanceof TEMultivessel){
	        	restoreMultivesselNBT(stack, te);
	        }
	        if(te instanceof TELeachingVatController){
	        	restoreLeachingVatControllerNBT(stack, te);
	        }
	        if(te instanceof TELeachingVatTank){
	        	restoreLeachingVatTankNBT(stack, te);
	        }
	        if(te instanceof TEElementsCabinetBase){
	        	TEElementsCabinetBase cabinet = (TEElementsCabinetBase) world.getTileEntity(pos);
	        	restoreCabinetNBT(stack, te);
	        	setOrDropBlock(world, state, pos, cabinet.getFacing(), placer, EnumMachinesC.ELEMENTS_CABINET_TOP);
	        }
	        if(te instanceof TEReformerController){
	        	TEReformerController reactor = (TEReformerController) world.getTileEntity(pos);
	        	setOrDropBlock(world, state, pos, reactor.getFacing(), placer, EnumMachinesC.REFORMER_REACTOR);
	        }
        }
    }

	@Override
	public void checkFullBlocks(World world, BlockPos pos, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
		if(meta == EnumMachinesC.REFORMER_CONTROLLER.ordinal()){
			checkTopBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), pos);
		}
		if(meta == EnumMachinesC.REFORMER_REACTOR.ordinal()){
			checkBaseBlocks(world, world.getBlockState(pos.down()), pos);
		}
	}

	private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EnumFacing facing, EntityLivingBase placer, EnumMachinesC prop) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), this.getDefaultState().withProperty(VARIANT, prop).withProperty(FACING, facing), 2);
			TileEntityInv top = (TileEntityInv)world.getTileEntity(pos.up());
			top.facing = facing.ordinal();
		}else{
			int meta = state.getBlock().getMetaFromState(state);
			if(meta == EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal()){
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
					   (te instanceof TEElementsCabinetBase && !(teUp instanceof TEElementsCabinetTop))
 				    || (te instanceof TEReformerController && !(teUp instanceof TEReformerReactor))
				)
		){
			ItemStack itemstack = this.getSilkTouchDrop(state);
			if(meta == EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal()
			|| meta == EnumMachinesC.REFORMER_CONTROLLER.ordinal()){
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
					   (te instanceof TEElementsCabinetTop && !(teDown instanceof TEElementsCabinetBase))
					|| (te instanceof TEReformerReactor && !(teDown instanceof TEReformerController))
				)
		){
			world.setBlockToAir(pos);
		}
	}



	//---------- TILE HANDLER ----------
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesC.GAN_CONTROLLER.ordinal()){
			return new TEGanController();
		}
		if(meta == EnumMachinesC.GAS_CONDENSER.ordinal()){
			return new TEGasCondenser();
		}
		if(meta == EnumMachinesC.MULTIVESSEL.ordinal()){
			return new TEMultivessel();
		}
		if(meta == EnumMachinesC.LEACHING_VAT_CONTROLLER.ordinal()){
			return new TELeachingVatController();
		}
		if(meta == EnumMachinesC.LEACHING_VAT_TANK.ordinal()){
			return new TELeachingVatTank();
		}
		if(meta == EnumMachinesC.LEACHING_VAT_COLLECTOR.ordinal()){
			return new TELeachingVatCollector();
		}
		if(meta == EnumMachinesC.RETENTION_VAT.ordinal()){
			return new TERetentionVat();
		}
		if(meta == EnumMachinesC.EXTRACTOR_CONTROLLER.ordinal()){
			return new TEExtractorController();
		}
		if(meta == EnumMachinesC.EXTRACTOR_REACTOR.ordinal()){
			return new TEExtractorReactor();
		}
		if(meta == EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal()){
			return new TEElementsCabinetBase();
		}
		if(meta == EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()){
			return new TEElementsCabinetTop();
		}
		if(meta == EnumMachinesC.EXTRACTOR_INJECTOR.ordinal()){
			return new TEExtractorInjector();
		}
		if(meta == EnumMachinesC.EXTRACTOR_BALANCE.ordinal()){
			return new TEExtractorBalance();
		}
		if(meta == EnumMachinesC.REFORMER_CONTROLLER.ordinal()){
			return new TEReformerController();
		}
		if(meta == EnumMachinesC.REFORMER_REACTOR.ordinal()){
			return new TEReformerReactor();
		}
		if(meta == EnumMachinesC.EXTRACTOR_STABILIZER.ordinal()){
			return new TEExtractorStabilizer();
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
	
				if(meta == EnumMachinesC.GAN_CONTROLLER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gan_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.MULTIVESSEL.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.multivessel_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.GAS_CONDENSER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.gas_condenser_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.LEACHING_VAT_CONTROLLER.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.leaching_vat_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.LEACHING_VAT_TANK.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.leaching_vat_tank_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.LEACHING_VAT_COLLECTOR.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.leaching_vat_collector_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.RETENTION_VAT.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.retention_vat_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.EXTRACTOR_INJECTOR.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.extraction_injector_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal()){
		    		player.openGui(Rhchemistry.instance, GuiHandler.extraction_cabinet_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.extraction_cabinet_injector_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.EXTRACTOR_CONTROLLER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.extraction_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.EXTRACTOR_STABILIZER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.extraction_stabilizer_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.REFORMER_CONTROLLER.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.reformer_controller_id, world, pos.getX(), pos.getY(), pos.getZ());
				}
				if(meta == EnumMachinesC.REFORMER_REACTOR.ordinal()){
					player.openGui(Rhchemistry.instance, GuiHandler.reformer_reactor_id, world, pos.getX(), pos.getY(), pos.getZ());
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
		return meta != EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()
			&& meta != EnumMachinesC.REFORMER_REACTOR.ordinal()
			? Item.getItemFromBlock(this) : null;
	}

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
		int meta = state.getBlock().getMetaFromState(state);
		return meta != EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()
			&& meta != EnumMachinesC.REFORMER_REACTOR.ordinal();
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = state.getBlock().getMetaFromState(state);
		if(meta == EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesC.ELEMENTS_CABINET_BASE.ordinal());
		}
		if(meta == EnumMachinesC.REFORMER_REACTOR.ordinal()){
			return new ItemStack(Item.getItemFromBlock(this), 1, EnumMachinesC.REFORMER_CONTROLLER.ordinal());
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
        if(meta != EnumMachinesC.ELEMENTS_CABINET_TOP.ordinal()
        && meta != EnumMachinesC.REFORMER_REACTOR.ordinal()){
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

    		if(te instanceof TEMultivessel){
        		addMultivesselNbt(itemstack, te);
        	}
        	if(te instanceof TELeachingVatController){
        		addLeachingVatControllerNbt(itemstack, te);
        	}
        	if(te instanceof TELeachingVatTank){
        		addLeachingVatTankNbt(itemstack, te);
        	}
        	if(te instanceof TEElementsCabinetBase){
        		addCabinetNbt(itemstack, te);
        	}
        }
	}

	private static void addMultivesselNbt(ItemStack itemstack, TileEntity te) {
    	TEMultivessel tank = ((TEMultivessel)te);
		NBTTagCompound tank_Ar = new NBTTagCompound(); 
		if(tank.tank_Ar.getFluid() != null){
			tank.tank_Ar.getFluid().writeToNBT(tank_Ar);
			itemstack.getTagCompound().setTag("Tank_Ar", tank_Ar);
		}
		
		NBTTagCompound tank_CO = new NBTTagCompound(); 
		if(tank.tank_CO.getFluid() != null){
			tank.tank_CO.getFluid().writeToNBT(tank_CO);
			itemstack.getTagCompound().setTag("Tank_CO", tank_CO);
		}

		NBTTagCompound tank_Ne = new NBTTagCompound(); 
		if(tank.tank_Ne.getFluid() != null){
			tank.tank_Ne.getFluid().writeToNBT(tank_Ne);
			itemstack.getTagCompound().setTag("Tank_Ne", tank_Ne);
		}

		NBTTagCompound tank_He = new NBTTagCompound(); 
		if(tank.tank_He.getFluid() != null){
			tank.tank_He.getFluid().writeToNBT(tank_He);
			itemstack.getTagCompound().setTag("Tank_He", tank_He);
		}

		NBTTagCompound tank_Kr = new NBTTagCompound(); 
		if(tank.tank_Kr.getFluid() != null){
			tank.tank_Kr.getFluid().writeToNBT(tank_Kr);
			itemstack.getTagCompound().setTag("Tank_Kr", tank_Kr);
		}

		NBTTagCompound tank_Xe = new NBTTagCompound(); 
		if(tank.tank_Xe.getFluid() != null){
			tank.tank_Xe.getFluid().writeToNBT(tank_Xe);
			itemstack.getTagCompound().setTag("Tank_Xe", tank_Xe);
		}
		itemstack.getTagCompound().setInteger("Collapse", tank.collapseRate);
	}
	private static void restoreMultivesselNBT(ItemStack itemstack, TileEntity te) {
		TEMultivessel tank = ((TEMultivessel)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey("Tank_Ar")){
				tank.tank_Ar.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Tank_Ar")));
			}

			if(itemstack.getTagCompound().hasKey("Tank_CO")){
				tank.tank_CO.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Tank_CO")));
			}

			if(itemstack.getTagCompound().hasKey("Tank_Ne")){
				tank.tank_Ne.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Tank_Ne")));
			}

			if(itemstack.getTagCompound().hasKey("Tank_He")){
				tank.tank_He.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Tank_He")));
			}

			if(itemstack.getTagCompound().hasKey("Tank_Kr")){
				tank.tank_Kr.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Tank_Kr")));
			}

			if(itemstack.getTagCompound().hasKey("Tank_Xe")){
				tank.tank_Xe.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Tank_Xe")));
			}
			tank.collapseRate = itemstack.getTagCompound().getInteger("Collapse");
    	}
	}

	private static void addLeachingVatControllerNbt(ItemStack itemstack, TileEntity te) {
		TELeachingVatController controller = ((TELeachingVatController)te);
    	itemstack.getTagCompound().setFloat("Gravity", controller.getGravity());
	}
	private static void restoreLeachingVatControllerNBT(ItemStack stack, TileEntity te) {
		TELeachingVatController controller = ((TELeachingVatController)te);
		if(stack.hasTagCompound() && controller != null){
			if(stack.getTagCompound().hasKey("Gravity")){
	        	float comm = stack.getTagCompound().getFloat("Gravity");
	        	controller.gravity = comm;
			}
		}
	}

	private static void addLeachingVatTankNbt(ItemStack itemstack, TileEntity te) {
		TELeachingVatTank tank = ((TELeachingVatTank)te);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
		if(tank.getFilter() != null){
	        NBTTagCompound filterNBT = new NBTTagCompound();
	        tank.filter.writeToNBT(filterNBT);
	        itemstack.getTagCompound().setTag("Filter", filterNBT);
		}
	}
    private static void restoreLeachingVatTankNBT(ItemStack itemstack, TileEntity te) {
    	TELeachingVatTank tank = ((TELeachingVatTank)te);
    	if(itemstack.hasTagCompound() && tank != null){
			if(itemstack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
				tank.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
			}
			
			if(itemstack.getTagCompound().hasKey("Filter")){
				tank.filter = FluidStack.loadFluidStackFromNBT(itemstack.getTagCompound().getCompoundTag("Filter"));
			}

    	}
	}

	private static void addCabinetNbt(ItemStack itemstack, TileEntity te) {
    	TEElementsCabinetBase cabinet = ((TEElementsCabinetBase)te);
		NBTTagList quantityList = new NBTTagList();
		for(int i = 0; i < cabinet.elementList.length; i++){
			NBTTagCompound tagQuantity = new NBTTagCompound();
			tagQuantity.setInteger("Element" + i, cabinet.elementList[i]);
			quantityList.appendTag(tagQuantity);
		}
		itemstack.getTagCompound().setTag("ElementsList", quantityList);
	}
	private static void restoreCabinetNBT(ItemStack itemstack, TileEntity te) {
    	TEElementsCabinetBase cabinet = ((TEElementsCabinetBase)te);
		if(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("ElementsList")){
			NBTTagList quantityList = itemstack.getTagCompound().getTagList("ElementsList", Constants.NBT.TAG_COMPOUND);
			for(int i = 0; i < quantityList.tagCount(); i++){
				NBTTagCompound getQuantities = quantityList.getCompoundTagAt(i);
				cabinet.elementList[i] = getQuantities.getInteger("Element" + i);
			}
		}
	}

}