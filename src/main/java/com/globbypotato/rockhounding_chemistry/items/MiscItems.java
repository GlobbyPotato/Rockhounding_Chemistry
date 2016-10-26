package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumSetups;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MiscItems extends ItemBase{
	static int crawlerMemoryMeta = 6;
	static int fluidContainerMeta = 5;
	
	private String[] itemArray;
	
	public MiscItems(String name, String[] array) {
		super(name);
		setHasSubtypes(true);
		this.itemArray = array;
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		if(stack.getItemDamage() == 17){
			return 1;
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
	}

	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		if(itemstack.getItemDamage() == crawlerMemoryMeta){
			setItemNbt(itemstack);
		}
    }

    private static void setItemNbt(ItemStack itemstack) {
    	if(itemstack.getItemDamage() == crawlerMemoryMeta){
        	itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), 0);
            itemstack.getTagCompound().setInteger(EnumSetups.GLASS.getName(), 0);
            itemstack.getTagCompound().setInteger(EnumSetups.TORCHES.getName(), 0);
            itemstack.getTagCompound().setInteger(EnumSetups.RAILS.getName(), 0);
    	}
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
    	if(itemstack.getItemDamage() == fluidContainerMeta){
            if(itemstack.hasTagCompound()) {
            	String blockName = itemstack.getTagCompound().getString("Block");
            	tooltip.add(TextFormatting.DARK_GRAY + "Contained Fluid" + ": " + TextFormatting.YELLOW + blockName);
            }
        }
    	if(itemstack.getItemDamage() == crawlerMemoryMeta){
            if(itemstack.hasTagCompound()) {
            	int numCobble = itemstack.getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName());
            	int numGlass = itemstack.getTagCompound().getInteger(EnumSetups.GLASS.getName());
            	int numTorch = itemstack.getTagCompound().getInteger(EnumSetups.TORCHES.getName());
            	int numRail = itemstack.getTagCompound().getInteger(EnumSetups.RAILS.getName());
            	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.COBBLESTONE.getName() + ": " + TextFormatting.YELLOW + numCobble);
            	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.GLASS.getName() + ": " + TextFormatting.YELLOW + numGlass);
            	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.TORCHES.getName() + ": " + TextFormatting.YELLOW + numTorch);
            	tooltip.add(TextFormatting.DARK_GRAY + EnumSetups.RAILS.getName() + ": " + TextFormatting.YELLOW + numRail);
			}else{
				setItemNbt(itemstack);
            }
    	}
    }

	@Override
    public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(stack.getItemDamage() == fluidContainerMeta){
	        if (!playerIn.canPlayerEdit(pos.offset(facing), facing, stack)){
	            return EnumActionResult.FAIL;
	        }else{
	            pos = pos.offset(facing);
	            BlockPos basePos = new BlockPos(pos.getX(), pos.getY() - 1, pos.getZ());
	            if (worldIn.isAirBlock(pos) && worldIn.isSideSolid(basePos, EnumFacing.UP)){
	            	if(stack.hasTagCompound()){
	                	String blockName = stack.getTagCompound().getString("Block");
	                	if(blockName != ""){
	                		IBlockState block = Block.getBlockFromName(blockName).getDefaultState();
                            worldIn.setBlockState(pos, block);
	                	}
	            	}
	                if (!playerIn.capabilities.isCreativeMode){
	                    --stack.stackSize;
	                }
	            }
	        	return EnumActionResult.PASS;
	        }
		}
        return EnumActionResult.PASS;
    }
}