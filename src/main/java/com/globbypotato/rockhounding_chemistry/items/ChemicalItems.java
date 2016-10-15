package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChemicalItems extends Item {
	private String[] itemArray;
	private int fillTank;

	public ChemicalItems(String name, String[] array) {
		super();
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
		setHasSubtypes(true);
		setCreativeTab(Reference.RockhoundingChemistry);
		this.itemArray = array;
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int i = stack.getItemDamage();
		if( i < 0 || i >= itemArray.length){ i = 0; }
		return super.getUnlocalizedName() + "." + itemArray[i];
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item itemIn, CreativeTabs tab, List subItems) {
		for(int i = 0; i < itemArray.length; i++){subItems.add(new ItemStack(itemIn, 1, i));}
		for(int x = 1; x < TileEntityLabOven.acidNames.length; x++){
	        ItemStack tank = new ItemStack(itemIn, 1, 0);
	        subItems.add(makeTanks(tank, ModArray.chemTankName, TileEntityLabOven.acidNames[x], ModArray.chemTankQuantity, TileEntityLabOven.tankMax));
		}
	}

	public static ItemStack makeTanks(ItemStack itemstack, String fluidLabel, String fluidName, String quantityLabel, int capacity) {
		if(!itemstack.hasTagCompound()) {setItemNbt(itemstack);}
    	itemstack.getTagCompound().setString(fluidLabel, fluidName);
        itemstack.getTagCompound().setInteger(quantityLabel, capacity);
        return itemstack.copy();
    }

	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		if(itemstack.getItemDamage() == 0){
			setItemNbt(itemstack);
		}
    }

    private static void setItemNbt(ItemStack itemstack) {
    	if(itemstack.getItemDamage() == 0){
        	itemstack.setTagCompound(new NBTTagCompound());
        	itemstack.getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.acidNames[0]);
            itemstack.getTagCompound().setInteger(ModArray.chemTankQuantity, 0);
    	}
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
    	if(itemstack.getItemDamage() == 0){
            if(itemstack.hasTagCompound()) {
            	String fluid = itemstack.getTagCompound().getString(ModArray.chemTankName);
            	int quantity = itemstack.getTagCompound().getInteger(ModArray.chemTankQuantity);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.chemTankName + ": " + TextFormatting.YELLOW + fluid);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.chemTankQuantity +": " + TextFormatting.WHITE + quantity + "/" + TileEntityLabOven.tankMax + " beakers");
            }else{
            	setItemNbt(itemstack);
            }
    	}
    }

	@Override
	public void onUpdate(ItemStack itemstack, World world, Entity entityIn, int itemSlot, boolean isSelected){
		if(itemstack.getItemDamage() == 0){
            if(itemstack.hasTagCompound()) {
            	if(isSelected){
	            	String fluid = itemstack.getTagCompound().getString(ModArray.chemTankName);
	            	int quantity = itemstack.getTagCompound().getInteger(ModArray.chemTankQuantity);
	            	if(fluid.matches(TileEntityLabOven.solventNames[0]) || fluid.matches(TileEntityLabOven.solventNames[1])){
	            		if(quantity < TileEntityLabOven.tankMax){
	            			if(entityIn != null && entityIn instanceof EntityPlayer){
	                            EntityPlayer entityplayer = (EntityPlayer)entityIn;
	            				if(entityplayer.isInWater()){
	            					if(fillTank >= 40){
	            						quantity++;
	            			            itemstack.getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
	            			            if(fluid.matches(TileEntityLabOven.solventNames[0])){itemstack.getTagCompound().setString(ModArray.chemTankName, TileEntityLabOven.solventNames[1]);}
	            						fillTank = 0;
	            						entityplayer.playSound(SoundEvents.ITEM_BUCKET_FILL, 1.0F, 1.0F);
	            				        BlockPos.MutableBlockPos blockPos = new BlockPos.MutableBlockPos();
	            				        if(!world.isRemote){
		            				        blockPos.setPos(entityplayer.posX, entityplayer.posY, entityplayer.posZ);
		            				        if(world.getBlockState(blockPos).getBlock() == Blocks.WATER){world.setBlockToAir(blockPos);}
		            				        blockPos.setPos(entityplayer.posX, entityplayer.posY + 1, entityplayer.posZ);
		            						if(world.getBlockState(blockPos).getBlock() == Blocks.WATER){world.setBlockToAir(blockPos);}
	            				        }
	            					}else{
	            						fillTank++;
	            					}
	            				}
                            }
            			}
            		}
	            }
            }
		}
    }

}