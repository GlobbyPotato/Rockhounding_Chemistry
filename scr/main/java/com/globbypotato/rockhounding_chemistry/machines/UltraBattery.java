package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.blocks.BlockIO;
import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.UltraBatteryIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumBattery;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityUltraBattery;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class UltraBattery extends BlockIO {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumBattery.class);

	public UltraBattery(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound) {
		super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(new UltraBatteryIB(this, EnumBattery.getNames()).setRegistryName(name));
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumBattery.values()[0]));
	}

    @Override
    public boolean isOpaqueCube(IBlockState state) {
    	return false;
    }

	@Override
    public boolean isFullCube(IBlockState state){
        return false;
    }

	@Override
	public boolean hasTileEntity(IBlockState state) {
		return true;
	}

	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
		return new TileEntityUltraBattery();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumBattery.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumBattery)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			player.openGui(Rhchemistry.instance, GuiHandler.ultraBatteryID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
		if(stack.hasTagCompound()){
        	int energy = stack.getTagCompound().getInteger("Energy");
        	TileEntityUltraBattery te = (TileEntityUltraBattery) worldIn.getTileEntity(pos);
			if(te != null){
            	te.redstoneCount = energy;
			}
		}
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = this.createStackedBlock(state);
        if(te != null && te instanceof TileEntityUltraBattery){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityUltraBattery battery = ((TileEntityUltraBattery)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		itemstack.getTagCompound().setInteger("Energy", battery.redstoneCount);
	}

}