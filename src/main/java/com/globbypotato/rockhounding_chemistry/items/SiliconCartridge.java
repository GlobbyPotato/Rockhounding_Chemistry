package com.globbypotato.rockhounding_chemistry.items;

import com.globbypotato.rockhounding_chemistry.items.io.UtilIO;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class SiliconCartridge extends UtilIO {

	public SiliconCartridge(String name, int uses) {
		super(name);
		this.setMaxDamage(uses);
		this.setMaxStackSize(1);
		this.setNoRepair();
	}

	@Override
	public boolean hasContainerItem() {
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack){
		if(itemStack.getItemDamage() < itemStack.getMaxDamage() - 1){
			itemStack.setItemDamage(itemStack.getItemDamage() + 1 );
		}else{
			itemStack.shrink(1);
		}
		return itemStack != ItemStack.EMPTY ? itemStack.copy() : ItemStack.EMPTY;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack helditem = playerIn.getHeldItemMainhand();
		if(helditem != ItemStack.EMPTY){
			if(ItemStack.areItemsEqualIgnoreDurability(helditem, BaseRecipes.silicone_cartridge)){
				if(helditem.getItemDamage() < helditem.getMaxDamage()){
			        if(playerIn.isSneaking()){
				        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_SLIME_STEP, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				        if (!worldIn.isRemote){
				        	EntityItem slimeball = new EntityItem(worldIn, playerIn.posX, playerIn.posY, playerIn.posZ, new ItemStack(Items.SLIME_BALL));
				            worldIn.spawnEntity(slimeball);
					        if (!playerIn.capabilities.isCreativeMode){
				        		int damage = helditem.getItemDamage() + 1;
				        		helditem.setItemDamage(damage);
				        		if(damage >= helditem.getMaxDamage()){
				        			helditem.shrink(1);
				        		}
					        }
					        playerIn.addStat(StatList.getObjectUseStats(this));
				        }
					}
				}
			}
		}
		return new ActionResult(EnumActionResult.PASS, helditem);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack helditem = playerIn.getHeldItemMainhand();
		if(helditem != ItemStack.EMPTY){
			if(ItemStack.areItemsEqualIgnoreDurability(helditem, BaseRecipes.silicone_cartridge)){
				if(helditem.getItemDamage() < helditem.getMaxDamage() - 9){
			        worldIn.playSound((EntityPlayer)null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.BLOCK_SLIME_PLACE, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			        if (!worldIn.isRemote){
			        	BlockPos checkPos = pos.offset(facing);
			        	if(worldIn.getBlockState(checkPos) == Blocks.AIR.getDefaultState()){
			        		worldIn.setBlockState(checkPos, Blocks.SLIME_BLOCK.getDefaultState(), 2);
			    	        if (!playerIn.capabilities.isCreativeMode){
				        		int damage = helditem.getItemDamage() + 9;
				        		helditem.setItemDamage(damage);
				        		if(damage >= helditem.getMaxDamage()){
				        			helditem.shrink(1);
				        		}
			    	        }
			        		playerIn.addStat(StatList.getObjectUseStats(this));
			        	}
			        }
				}
			}
		}
		return EnumActionResult.PASS;
	}
}