package com.globbypotato.rockhounding_chemistry.machines;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDepositionChamber;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class DepositionChamber extends BaseMachine{
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);

    public DepositionChamber(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityDepositionChamber.class,GuiHandler.depositionChamberID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos){
        return BOUNDBOX;
    }

    @Nullable
	@Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World worldIn, BlockPos pos){
        return BOUNDBOX;
    }

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		checkFullBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), world.getBlockState(pos.down()), pos);
		world.scheduleUpdate(pos, this, 1);
	}

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
        setOrDropBlock(worldIn, state, pos, placer);
        if(stack.hasTagCompound()){
        	TileEntityDepositionChamber te = (TileEntityDepositionChamber) worldIn.getTileEntity(pos);
			if(te != null){
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
        			te.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
        		}
			}
		}
    }

    private void setOrDropBlock(World world, IBlockState state, BlockPos pos, EntityLivingBase placer) {
		if(world.getBlockState(pos.up()).getBlock().isAir(world.getBlockState(pos.up()), world, pos)){
			world.setBlockState(pos.up(), ModBlocks.depositionChamberTop.getDefaultState().withProperty(FACING, placer.getHorizontalFacing()), 2);
		}else{
            this.dropBlockAsItem(world, pos, state, 0);
            world.setBlockToAir(pos);
		}
	}

	@Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn){
		checkFullBlocks(world, world.getBlockState(pos), world.getBlockState(pos.up()), world.getBlockState(pos.down()), pos);
	}

    private void checkFullBlocks(World world, IBlockState state, IBlockState stateUp, IBlockState stateDown, BlockPos pos) {
		if(stateUp.getBlock() == null || !(stateUp.getBlock() instanceof DepositionChamberTop)){
			ItemStack itemstack = this.createStackedBlock(state);
			TileEntity te = world.getTileEntity(pos);
			addNbt(itemstack, te);
	        spawnAsEntity(world, pos, itemstack);
	        world.setBlockToAir(pos);
		}
	}

	@Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityDepositionChamber){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityDepositionChamber chamber = ((TileEntityDepositionChamber)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
    	addPowerNbt(itemstack, tileentity);
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(chamber.inputTank.getFluid() != null){
			chamber.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
	}
}