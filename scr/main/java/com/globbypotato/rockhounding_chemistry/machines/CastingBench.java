package com.globbypotato.rockhounding_chemistry.machines;


import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityCastingBench;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class CastingBench extends BaseMachine{
    Random rand = new Random();

    public CastingBench(float hardness, float resistance, String name){
        super(name, Material.IRON, TileEntityCastingBench.class,GuiHandler.castingBenchID);
		setHardness(hardness);
		setResistance(resistance);	
		setHarvestLevel("pickaxe", 0);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
    }

	@Override
	public void onBlockClicked(World world, BlockPos pos, EntityPlayer player) {
		if(player.getHeldItemMainhand() == null){
			IBlockState state = world.getBlockState(pos);
			if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityCastingBench){
				TileEntityCastingBench bench = (TileEntityCastingBench)world.getTileEntity(pos);
				if(bench.getCurrentCast() < EnumCasting.size() - 1){
					bench.currentCast++;
				}else{
					bench.currentCast = 0;
				}
			}
		}
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
		if(world.getTileEntity(pos) != null && world.getTileEntity(pos) instanceof TileEntityCastingBench){
			TileEntityCastingBench casting = (TileEntityCastingBench)world.getTileEntity(pos);

			ItemStack outputStack = casting.getOutput().getStackInSlot(casting.OUTPUT_SLOT);
			if(outputStack != null){
				EntityItem outEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, outputStack);
				if (outEntity != null) {
					outEntity.motionX = 0; outEntity.motionY = 0; outEntity.motionZ = 0;
					if(!world.isRemote){
						world.spawnEntityInWorld(outEntity);
					}
		            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.AMBIENT, 0.5F, rand.nextFloat() * 0.1F + 0.9F);
					casting.getOutput().extractItem(casting.OUTPUT_SLOT, outputStack.stackSize, false);
				}
			}else{
				ItemStack inputStack = casting.getInput().getStackInSlot(casting.INPUT_SLOT);
				if(inputStack != null){
					EntityItem inputEntity = new EntityItem(world, pos.getX() + 0.5, pos.getY() + 1.5, pos.getZ() + 0.5, inputStack);
					if (inputEntity != null) {
						inputEntity.motionX = 0; inputEntity.motionY = 0; inputEntity.motionZ = 0;
						if(!world.isRemote){
							world.spawnEntityInWorld(inputEntity);
						}
			            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.AMBIENT, 0.5F, rand.nextFloat() * 0.1F + 0.9F);
						casting.getInput().extractItem(casting.INPUT_SLOT, inputStack.stackSize, false);
					}
				}else{
					if(inputStack == null){
						if(player.getHeldItemMainhand() != null){
							if(casting.isValidOredict(player.getHeldItemMainhand())){
								casting.getInput().insertItem(casting.INPUT_SLOT, new ItemStack(player.getHeldItemMainhand().getItem(), 1, player.getHeldItemMainhand().getItemDamage()), false);
								player.getHeldItemMainhand().stackSize--;
					            world.playSound((EntityPlayer)null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_ITEMFRAME_PLACE, SoundCategory.AMBIENT, 0.5F, rand.nextFloat() * 0.1F + 0.9F);
							}
						}
					}
				}
			}
		}
		return true;
	}

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, @Nullable ItemStack stack){
        player.addStat(StatList.getBlockStats(this));
        player.addExhaustion(0.025F);
        java.util.List<ItemStack> items = new java.util.ArrayList<ItemStack>();
        ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this));
        if(te != null && te instanceof TileEntityCastingBench){
  			addNbt(itemstack, te);
        }
        if (itemstack != null){ items.add(itemstack); }
        net.minecraftforge.event.ForgeEventFactory.fireBlockHarvesting(items, worldIn, pos, state, 0, 1.0f, true, player);
        for (ItemStack item : items){ spawnAsEntity(worldIn, pos, item); }
    }

	private void addNbt(ItemStack itemstack, TileEntity tileentity) {
		TileEntityCastingBench sizer = ((TileEntityCastingBench)tileentity);
		itemstack.setTagCompound(new NBTTagCompound());
    	addPowerNbt(itemstack, tileentity);
	}
}