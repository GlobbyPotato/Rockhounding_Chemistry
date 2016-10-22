package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLabOven;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChemicalItems extends ItemBase {
	private String[] itemArray;
	private int fillTank;

	public ChemicalItems(String name, String[] array) {
		super(name);
		setHasSubtypes(true);

		this.itemArray = array;
	}


	@Override
	public int getItemStackLimit(ItemStack stack) {
		if(stack.hasTagCompound()){
			if(stack.getItemDamage() == 0){
				if(!stack.getTagCompound().getString("Fluid").equals(EnumFluid.EMPTY.getName())){
					return 1;
				}
			}
		}
		return this.getItemStackLimit();
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
		for(int x = 1; x < EnumFluid.values().length; x++){
			ItemStack tank = new ItemStack(itemIn, 1, 0);
			subItems.add(makeTanks(tank, "Fluid", EnumFluid.values()[x].getName(), "Quantity", TileEntityLabOven.tankMax));
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
			itemstack.getTagCompound().setString("Fluid", EnumFluid.EMPTY.getName());
			itemstack.getTagCompound().setInteger("Quantity", 0);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
		if(itemstack.getItemDamage() == 0){
			if(itemstack.hasTagCompound()) {
				String fluid = itemstack.getTagCompound().getString("Fluid");
				int quantity = itemstack.getTagCompound().getInteger("Quantity");
				tooltip.add(TextFormatting.DARK_GRAY + "Fluid" + ": " + TextFormatting.YELLOW + fluid);
				tooltip.add(TextFormatting.DARK_GRAY + "Quantity" +": " + TextFormatting.WHITE + quantity + "/" + TileEntityLabOven.tankMax + " beakers");
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
					String fluid = itemstack.getTagCompound().getString("Fluid");
					int quantity = itemstack.getTagCompound().getInteger("Quantity");
					if(fluid.matches(EnumFluid.EMPTY.getName()) || fluid.matches(EnumFluid.WATER.getName())){
						if(quantity < TileEntityLabOven.tankMax){
							if(entityIn != null && entityIn instanceof EntityPlayer){
								EntityPlayer entityplayer = (EntityPlayer)entityIn;
								if(entityplayer.isInWater()){
									if(fillTank >= 40){
										quantity++;
										itemstack.getTagCompound().setInteger("Quantity", quantity);
										if(fluid.matches(EnumFluid.EMPTY.getName())){itemstack.getTagCompound().setString("Fluid", EnumFluid.WATER.getName());}
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