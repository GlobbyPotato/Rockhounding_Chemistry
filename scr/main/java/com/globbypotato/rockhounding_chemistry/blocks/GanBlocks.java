package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.GanIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumGan;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_core.machines.tileentity.IFluidHandlingTile;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.UniversalBucket;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class GanBlocks extends BlockIO {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumGan.class);

    public GanBlocks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(new GanIB(this, EnumGan.getNames()).setRegistryName(name));
		setHarvestLevel("pickaxe", 0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumGan.values()[0]));
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
		int meta = EnumGan.values()[getMetaFromState(state)].ordinal();
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, EnumGan.getHeight(meta), 1.0D);
    }

    @Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos){
		int meta = EnumGan.values()[getMetaFromState(state)].ordinal();
        return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, EnumGan.getHeight(meta), 1.0D);
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumGan.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumGan)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
		return false;
    }

    @Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isFullyOpaque(IBlockState state) {
		return false;
	}

	@Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return isTopBlock(state, state.getBlock().getMetaFromState(state)) ? null : Item.getItemFromBlock(this);
	}

    @Override
    public int damageDropped(IBlockState state){
		return getMetaFromState(state);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player){
    	return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer(){
        return BlockRenderLayer.CUTOUT;
    }

	@Override
	public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
		for (int i = 0; i < this.array.length; i++){
			if(!GanIB.isTopBlock(i)){
				list.add(new ItemStack(itemIn, 1, i));
			}
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		int meta = getMetaFromState(state);
		return isActiveMeta(state, meta);
	}

	public static boolean isActiveMeta(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 0 || meta == 1 || meta == 4 || meta == 6 || meta == 7 || meta == 10);
	}

	public static boolean isVessel(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 0 || meta == 6);
	}

	public static boolean isChiller(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 1 || meta == 7);
	}

	public static boolean isTurbine(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 3 || meta == 9);
	}

	public static boolean isTopBlock(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 12 || meta == 13 || meta == 14);
	}

	public static boolean isTank(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 4 || meta == 10);
	}

	public static boolean isTower(IBlockState state, int meta) {
		return state.getBlock() instanceof GanBlocks && (meta == 5 || meta == 11 || meta == 15);
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = EnumGan.values()[getMetaFromState(state)].ordinal();
		if(isTank(state, meta)){
			return new TileEntityNitrogenTank();
		}else if(isVessel(state, meta)){
			return new TileEntityAirCompresser();
		}else if(isChiller(state, meta)){
			return new TileEntityAirChiller();
		}
		return null;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		int meta = state.getBlock().getMetaFromState(state);
		if (!world.isRemote) {
			if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof IFluidHandlingTile){
				if (heldItem != null){
					if (heldItem.getItem() instanceof ItemBucket || heldItem.getItem() instanceof UniversalBucket){
						((IFluidHandlingTile)world.getTileEntity(pos)).interactWithBucket(world, pos, state, player, hand, heldItem, side, hitX, hitY, hitZ);
						return true;
					}
				}
			}
			if(isTank(state, meta)){
				player.openGui(Rhchemistry.instance, GuiHandler.nitrogenTankID, world, pos.getX(), pos.getY(), pos.getZ());
			
			}else if(isVessel(state, meta)){
				player.openGui(Rhchemistry.instance, GuiHandler.airCompresserID, world, pos.getX(), pos.getY(), pos.getZ());
			
			}else if(meta == EnumGan.VESSEL_TOP.ordinal()){
				player.openGui(Rhchemistry.instance, GuiHandler.airCompresserID, world, pos.getX(), pos.getY() - 1, pos.getZ());
			
			}else if(isChiller(state, meta)){
				player.openGui(Rhchemistry.instance, GuiHandler.airChillerID, world, pos.getX(), pos.getY(), pos.getZ());
			
			}else if(meta == EnumGan.CHILLER_TOP.ordinal()){
				player.openGui(Rhchemistry.instance, GuiHandler.airChillerID, world, pos.getX(), pos.getY() - 1, pos.getZ());
			
			}else{
				return false;
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		if(isActiveMeta(state, meta)){
			TileEntity te = world.getTileEntity(pos);
			if (te instanceof TileEntityMachineInv){
				if(te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY,null)){
					IItemHandler inventory = ((TileEntityMachineInv) te).getInventory();
					int slots = inventory.getSlots();
					for(int i=0;i<slots; i++){
						if(inventory.getStackInSlot(i) != null){
							world.spawnEntityInWorld(new EntityItem(world,pos.getX(),pos.getY(),pos.getZ(),inventory.getStackInSlot(i)));
						}
					}
				}
			}
		}
		super.breakBlock(world, pos, state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		checkFullBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), world.getBlockState(pos.down()), pos);
		world.scheduleUpdate(pos, this, 1);
	}

	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn){
		checkFullBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), world.getBlockState(pos.down()), pos);
	}

    private void checkFullBlocks(World world, IBlockState state, IBlockState stateUp, IBlockState stateDown, BlockPos pos) {
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		int metabase = stateDown.getBlock().getMetaFromState(stateDown);
		if(isVessel(state, meta)){  checkOrDropBlock(world, state, stateUp, pos, EnumGan.VESSEL_TOP); }
		if(isChiller(state, meta)){ checkOrDropBlock(world, state, stateUp, pos, EnumGan.CHILLER_TOP); }
		if(isTurbine(state, meta)){ checkOrDropBlock(world, state, stateUp, pos, EnumGan.TURBINE_TOP); }

		if(isTopBlock(state, meta)){
			if(meta == EnumGan.CHILLER_TOP.ordinal()){ if(!isChiller(stateDown, metabase)){ world.setBlockToAir(pos); } }
			if(meta == EnumGan.TURBINE_TOP.ordinal()){ if(!isTurbine(stateDown, metabase)){ world.setBlockToAir(pos); } }
			if(meta == EnumGan.VESSEL_TOP.ordinal()){  if(!isVessel(stateDown, metabase)){  world.setBlockToAir(pos); } }
		}
	}

	private void checkOrDropBlock(World world, IBlockState state, IBlockState stateUp, BlockPos pos, EnumGan topBlock) {
		if(stateUp != state.withProperty(VARIANT, topBlock)){
			int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
			ItemStack itemstack = this.createStackedBlock(state);
			TileEntity te = world.getTileEntity(pos);
	        handleTileNBT(te, itemstack);
	        spawnAsEntity(world, pos, itemstack);
	        world.setBlockToAir(pos);
		}
	}

    private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EnumGan topBlock) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), state.withProperty(VARIANT, topBlock));
		}else{
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
		}
	}

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		IBlockState basestate = world.getBlockState(pos.down());
		if(isTopBlock(state, meta)){
			return new ItemStack(state.getBlock(), 1, basestate.getBlock().getMetaFromState(basestate));
		}else{
			return super.getPickBlock(state, target, world, pos, player);
		}
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        world.setBlockState(pos, state.withProperty(VARIANT, EnumGan.values()[stack.getItemDamage()]), 2);
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		if(isVessel(state, meta)){  setOrDropBlock(world, state, pos, EnumGan.VESSEL_TOP); }
		if(isChiller(state, meta)){ setOrDropBlock(world, state, pos, EnumGan.CHILLER_TOP); }
		if(isTurbine(state, meta)){ setOrDropBlock(world, state, pos, EnumGan.TURBINE_TOP); }

    	if(stack.hasTagCompound()){
			TileEntity tile = world.getTileEntity(pos);
			if(tile != null){
				if(tile instanceof TileEntityAirCompresser){
		        	TileEntityAirCompresser te = (TileEntityAirCompresser) tile;
		        	int air = stack.getTagCompound().getInteger("Air");
					if(te != null){
		            	te.airCount = air;
					}
				}
				if(tile instanceof TileEntityNitrogenTank){
		    		if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
			        	TileEntityNitrogenTank te = (TileEntityNitrogenTank) tile;
		    			te.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
		    		}
				}
				if(tile instanceof TileEntityAirChiller){
		    		if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
		    			TileEntityAirChiller te = (TileEntityAirChiller) tile;
		    			te.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
		    		}
				}
			}
		}
    }

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = null;
        if(!isTopBlock(state, state.getBlock().getMetaFromState(state))){
        	itemstack = this.createStackedBlock(state);
        }
        handleTileNBT(te, itemstack);
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void handleTileNBT(TileEntity te, ItemStack itemstack) {
        if(te != null){
        	if(te instanceof TileEntityAirCompresser){
      			addVesselNbt(itemstack, te);
        	}
        	if(te instanceof TileEntityNitrogenTank){
      			addTankNbt(itemstack, te);
        	}
        	if(te instanceof TileEntityAirChiller){
      			addChillerNbt(itemstack, te);
        	}
        }
	}

	private void addVesselNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityAirCompresser compresser = ((TileEntityAirCompresser)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setInteger("Air", compresser.airCount);
	}

	private void addTankNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityNitrogenTank tank = ((TileEntityNitrogenTank)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(tank.inputTank.getFluid() != null){
			tank.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
	}

	private void addChillerNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityAirChiller chiller = ((TileEntityAirChiller)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(chiller.inputTank.getFluid() != null){
			chiller.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
	}

}