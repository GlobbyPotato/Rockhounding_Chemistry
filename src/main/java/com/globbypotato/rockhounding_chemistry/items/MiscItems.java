package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;

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
	static int ingotPatternMeta = 13;
	
	private String[] itemArray;
	
	public MiscItems(String name, String[] array) {
		super(name);
		setHasSubtypes(true);
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
	}

	@Override
    public void onCreated(ItemStack itemstack, World world, EntityPlayer player) {
		if(itemstack.getItemDamage() == crawlerMemoryMeta || itemstack.getItemDamage() == ingotPatternMeta){
			setItemNbt(itemstack);
		}
    }

    private static void setItemNbt(ItemStack itemstack) {
    	if(itemstack.getItemDamage() == crawlerMemoryMeta){
        	itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setInteger(ModArray.cobbleName, 0);
            itemstack.getTagCompound().setInteger(ModArray.glassName, 0);
            itemstack.getTagCompound().setInteger(ModArray.torchName, 0);
            itemstack.getTagCompound().setInteger(ModArray.railName, 0);
    	}
    	if(itemstack.getItemDamage() == ingotPatternMeta){
        	itemstack.setTagCompound(new NBTTagCompound());
            itemstack.getTagCompound().setInteger(ModArray.toolUses, ModConfig.patternUses);
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
            	int numCobble = itemstack.getTagCompound().getInteger(ModArray.cobbleName);
            	int numGlass = itemstack.getTagCompound().getInteger(ModArray.glassName);
            	int numTorch = itemstack.getTagCompound().getInteger(ModArray.torchName);
            	int numRail = itemstack.getTagCompound().getInteger(ModArray.railName);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.cobbleName + ": " + TextFormatting.YELLOW + numCobble);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.glassName + ": " + TextFormatting.YELLOW + numGlass);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.torchName + ": " + TextFormatting.YELLOW + numTorch);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.railName + ": " + TextFormatting.YELLOW + numRail);
            }
    	}
    	if(itemstack.getItemDamage() == ingotPatternMeta){
            if(itemstack.hasTagCompound()) {
            	int uses = itemstack.getTagCompound().getInteger(ModArray.toolUses);
            	tooltip.add(TextFormatting.DARK_GRAY + ModArray.toolUses + ": " + TextFormatting.WHITE + uses + "/" + ModConfig.patternUses);
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