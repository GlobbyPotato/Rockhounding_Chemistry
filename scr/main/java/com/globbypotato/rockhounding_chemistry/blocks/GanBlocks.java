package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.GanIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumGan;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirChiller;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityAirCompresser;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityNitrogenTank;
import com.globbypotato.rockhounding_core.blocks.BaseMetaBlock;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_core.machines.tileentity.IFluidHandlingTile;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;

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
    public int damageDropped(IBlockState state){
    	return getMetaFromState(state) ;
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
			list.add(new ItemStack(itemIn, 1, i));
		}
	}

	@Override
	public boolean hasTileEntity(IBlockState state) {
		int meta = getMetaFromState(state);
		return isActiveMeta(meta);
	}

	public static boolean isActiveMeta(int meta) {
		return meta == 0 || meta == 1 || meta == 4 || meta == 6 || meta == 7 || meta == 10;
	}

	public static boolean isVessel(int meta) {
		return meta == 0 || meta == 6;
	}

	public static boolean isChiller(int meta) {
		return meta == 1 || meta == 7;
	}

	public static boolean isTank(int meta) {
		return meta == 4 || meta == 10;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		int meta = EnumGan.values()[getMetaFromState(state)].ordinal();
		if(isTank(meta)){
			return new TileEntityNitrogenTank();
		}else if(isVessel(meta)){
			return new TileEntityAirCompresser();
		}else if(isChiller(meta)){
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
			if(isTank(meta)){
				player.openGui(Rhchemistry.instance, GuiHandler.nitrogenTankID, world, pos.getX(), pos.getY(), pos.getZ());
			}else if(isVessel(meta)){
				player.openGui(Rhchemistry.instance, GuiHandler.airCompresserID, world, pos.getX(), pos.getY(), pos.getZ());
			}else if(isChiller(meta)){
				player.openGui(Rhchemistry.instance, GuiHandler.airChillerID, world, pos.getX(), pos.getY(), pos.getZ());
			}else{
				return false;
			}
		}
		return true;
	}

	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state){
		int meta = world.getBlockState(pos).getBlock().getMetaFromState(world.getBlockState(pos));
		if(isActiveMeta(meta)){
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
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
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
        ItemStack itemstack = this.createStackedBlock(state);
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
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
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