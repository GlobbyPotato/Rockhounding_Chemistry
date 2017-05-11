package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.BaseTileBlock;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityDepositionChamber;

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

public class DepositionChamber extends BaseTileBlock{
    private static final AxisAlignedBB BOUNDBOX = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.3D, 1.0D);

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
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
		if(stack.hasTagCompound()){
        	TileEntityDepositionChamber te = (TileEntityDepositionChamber) worldIn.getTileEntity(pos);
			if(te != null){
	        	int recipe = stack.getTagCompound().getInteger("Recipe");
        		if(stack.getTagCompound().hasKey("Recipe")){
        			te.recipeIndex = recipe;
        		}
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
        			te.inputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
        		}
			}
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
		NBTTagCompound solvent = new NBTTagCompound(); 
		if(chamber.recipeIndex >= 0){
			itemstack.getTagCompound().setInteger("Recipe", chamber.recipeIndex);
		}
		if(chamber.inputTank.getFluid() != null){
			chamber.inputTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
	}
}