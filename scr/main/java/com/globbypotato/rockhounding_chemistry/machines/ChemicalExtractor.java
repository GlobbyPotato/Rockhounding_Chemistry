package com.globbypotato.rockhounding_chemistry.machines;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.BaseTileBlock;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityChemicalExtractor;

import net.minecraft.block.material.Material;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;

public class ChemicalExtractor extends BaseTileBlock{
    public ChemicalExtractor(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityChemicalExtractor.class, GuiHandler.chemicalExtractorID);
		setHardness(hardness); 
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }
    
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack){
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing()), 2);
		if(stack.hasTagCompound()){
        	int fuel = stack.getTagCompound().getInteger("Fuel");
        	int energy = stack.getTagCompound().getInteger("Energy");
        	boolean induction = stack.getTagCompound().getBoolean("Induction");
        	TileEntityChemicalExtractor te = (TileEntityChemicalExtractor) worldIn.getTileEntity(pos);
			if(te != null){
            	te.powerCount = fuel;
            	te.redstoneCount = energy;
            	te.permanentInductor = induction;
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.NITR.nameTag())){
        			te.nitrTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.NITR.nameTag())));
        		}
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.PHOS.nameTag())){
        			te.phosTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.PHOS.nameTag())));
        		}
        		if(stack.getTagCompound().hasKey(EnumFluidNbt.CYAN.nameTag())){
        			te.cyanTank.setFluid(FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(EnumFluidNbt.CYAN.nameTag())));
        		}
        		if(stack.getTagCompound().hasKey("Elements")){
        			NBTTagList quantityList = stack.getTagCompound().getTagList("Elements", Constants.NBT.TAG_COMPOUND);
        			List<Integer> quantities = new ArrayList<Integer>();
        			for(int i = 0; i < quantityList.tagCount(); i++){
        				NBTTagCompound getQuantities = quantityList.getCompoundTagAt(i);
        				te.elementList[i] = getQuantities.getInteger("Element" + i);
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
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityChemicalExtractor){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityChemicalExtractor extractor = ((TileEntityChemicalExtractor)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
		NBTTagCompound sulf = new NBTTagCompound();
		NBTTagCompound phos = new NBTTagCompound();
		NBTTagCompound cyan = new NBTTagCompound();
		NBTTagList quantityList = new NBTTagList();
		itemstack.getTagCompound().setInteger("Fuel", extractor.powerCount);
		itemstack.getTagCompound().setInteger("Energy", extractor.redstoneCount);
		itemstack.getTagCompound().setBoolean("Induction", extractor.permanentInductor);
		if(extractor.nitrTank.getFluid() != null){
			extractor.nitrTank.getFluid().writeToNBT(sulf);
			itemstack.getTagCompound().setTag(EnumFluidNbt.NITR.nameTag(), sulf);
		}
		if(extractor.phosTank.getFluid() != null){
			extractor.phosTank.getFluid().writeToNBT(phos);
			itemstack.getTagCompound().setTag(EnumFluidNbt.PHOS.nameTag(), phos);
		}
		if(extractor.cyanTank.getFluid() != null){
			extractor.cyanTank.getFluid().writeToNBT(cyan);
			itemstack.getTagCompound().setTag(EnumFluidNbt.CYAN.nameTag(), cyan);
		}
		for(int i = 0; i < extractor.elementList.length; i++){
			NBTTagCompound tagQuantity = new NBTTagCompound();
			tagQuantity.setInteger("Element" + i, extractor.elementList[i]);
			quantityList.appendTag(tagQuantity);
		}
		itemstack.getTagCompound().setTag("Elements", quantityList);
	}
}