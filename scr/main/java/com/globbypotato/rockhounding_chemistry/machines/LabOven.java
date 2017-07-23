package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;

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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

public class LabOven extends BaseMachine{
    public LabOven(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityLabOven.class,GuiHandler.labOvenID);
		setHardness(hardness); setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
    	super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    	if(stack.hasTagCompound()){
        	TileEntityLabOven te = (TileEntityLabOven) worldIn.getTileEntity(pos);
			if(te != null){
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.SOLVENT.nameTag())){
        			te.solventTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.SOLVENT.nameTag())));
        		}
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.REAGENT.nameTag())){
        			te.reagentTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.REAGENT.nameTag())));
        		}
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.ACID.nameTag())){
        			te.outputTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.ACID.nameTag())));
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
        if(te != null && te instanceof TileEntityLabOven){
        	addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityLabOven laboven = ((TileEntityLabOven)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
    	addPowerNbt(itemstack, tileentity);
		NBTTagCompound solvent = new NBTTagCompound(); 
		NBTTagCompound reagent = new NBTTagCompound(); 
		NBTTagCompound acid = new NBTTagCompound(); 
		if(laboven.solventTank.getFluid() != null){
			laboven.solventTank.getFluid().writeToNBT(solvent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.SOLVENT.nameTag(), solvent);
		}
		if(laboven.reagentTank.getFluid() != null){
			laboven.reagentTank.getFluid().writeToNBT(reagent);
			itemstack.getTagCompound().setTag(EnumFluidNbt.REAGENT.nameTag(), reagent);
		}
		if(laboven.outputTank.getFluid() != null){
			laboven.outputTank.getFluid().writeToNBT(acid);
			itemstack.getTagCompound().setTag(EnumFluidNbt.ACID.nameTag(), acid);
		}
	}
}