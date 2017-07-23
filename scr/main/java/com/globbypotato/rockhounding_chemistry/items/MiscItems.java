package com.globbypotato.rockhounding_chemistry.items;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.blocks.itemblocks.CrawlerIB;
import com.globbypotato.rockhounding_chemistry.enums.EnumCrawler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.CrawlerUtils;
import com.globbypotato.rockhounding_core.items.BaseArray;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MiscItems extends ArrayIO{
	static int crawlerMemoryMeta = 6;
	static int fluidContainerMeta = 5;

	public MiscItems(String name, String[] array) {
		super(name, array);
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {
		if(stack.getItemDamage() == 0 || stack.getItemDamage() == 6 || stack.getItemDamage() == 7 
		|| stack.getItemDamage() == 8 || stack.getItemDamage() == 9 || stack.getItemDamage() == 10 
		|| stack.getItemDamage() == 12 || stack.getItemDamage() == 34
		|| stack.getItemDamage() == 35){
			return 1;
		}
		return this.getItemStackLimit();
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
            itemstack.getTagCompound().setString(EnumCrawler.FILLER_BLOCK.getBlockName(), "None");
            itemstack.getTagCompound().setInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta(), 0);
            itemstack.getTagCompound().setInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize(), 0);
            itemstack.getTagCompound().setString(EnumCrawler.ABSORBER_BLOCK.getBlockName(), "None");
            itemstack.getTagCompound().setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), 0);
            itemstack.getTagCompound().setInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize(), 0);
            itemstack.getTagCompound().setString(EnumCrawler.LIGHTER_BLOCK.getBlockName(), "None");
            itemstack.getTagCompound().setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), 0);
            itemstack.getTagCompound().setInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize(), 0);
            itemstack.getTagCompound().setString(EnumCrawler.RAILMAKER_BLOCK.getBlockName(), "None");
            itemstack.getTagCompound().setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), 0);
            itemstack.getTagCompound().setInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize(), 0);
            itemstack.getTagCompound().setString(EnumCrawler.DECORATOR_BLOCK.getBlockName(), "None");
            itemstack.getTagCompound().setInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), 0);
            itemstack.getTagCompound().setInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize(), 0);
    	}
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemstack, EntityPlayer player, List<String> tooltip, boolean held) {
    	if(itemstack.getItemDamage() == fluidContainerMeta){
            if(itemstack.hasTagCompound()) {
            	String blockName = itemstack.getTagCompound().getString("Block");
        		if(blockName != null && !blockName.matches("")){
                	Block tempBlock = Block.getBlockFromName(blockName);
                	if(tempBlock instanceof BlockFluidClassic){
	                	FluidStack crawledFluid = new FluidStack(((BlockFluidClassic) tempBlock).getFluid(), Fluid.BUCKET_VOLUME);                	
	                	tooltip.add(TextFormatting.DARK_GRAY + "Contained Fluid" + ": " + TextFormatting.YELLOW + crawledFluid.getFluid().getLocalizedName(crawledFluid));
                	}else if(blockName.matches("minecraft:water")){
	                	tooltip.add(TextFormatting.DARK_GRAY + "Contained Fluid" + ": " + TextFormatting.YELLOW + "Water");
                	}else if(blockName.matches("minecraft:lava")){
	                	tooltip.add(TextFormatting.DARK_GRAY + "Contained Fluid" + ": " + TextFormatting.YELLOW + "Lava");
                	}
        		}
            }
        }

    	if(itemstack.getItemDamage() == crawlerMemoryMeta){
            if(itemstack.hasTagCompound()) {
            	CrawlerIB.checkForOldNBT(itemstack);

            	String fillBlockName = itemstack.getTagCompound().getString(EnumCrawler.FILLER_BLOCK.getBlockName());
            	int fillBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.FILLER_BLOCK.getBlockMeta());
            	int fillBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.FILLER_BLOCK.getBlockStacksize());

            	String absorbBlockName = itemstack.getTagCompound().getString(EnumCrawler.ABSORBER_BLOCK.getBlockName());
            	int absorbBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockMeta());
            	int absorbBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());

            	String lighterBlockName = itemstack.getTagCompound().getString(EnumCrawler.LIGHTER_BLOCK.getBlockName());
            	int lighterBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockMeta());
            	int lighterBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());

            	String railBlockName = itemstack.getTagCompound().getString(EnumCrawler.RAILMAKER_BLOCK.getBlockName());
            	int railBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockMeta());
            	int railBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());

            	String decoBlockName = itemstack.getTagCompound().getString(EnumCrawler.DECORATOR_BLOCK.getBlockName());
            	int decoBlockMeta = itemstack.getTagCompound().getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockMeta());
            	int decoBlockSize = itemstack.getTagCompound().getInteger(EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());

            	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.FILLER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(fillBlockName, fillBlockMeta, fillBlockSize));
            	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.ABSORBER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(absorbBlockName, absorbBlockMeta, absorbBlockSize));
            	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.LIGHTER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(lighterBlockName, lighterBlockMeta, lighterBlockSize));
            	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.RAILMAKER_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(railBlockName, railBlockMeta, railBlockSize));
            	tooltip.add(TextFormatting.DARK_GRAY + EnumCrawler.DECORATOR_BLOCK.getScreenName() + ": " + TextFormatting.WHITE + CrawlerUtils.getScreenName(decoBlockName, decoBlockMeta, decoBlockSize));
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
	                	if(blockName != null && blockName != ""){
	                		IBlockState block = Block.getBlockFromName(blockName).getDefaultState();
                            worldIn.setBlockState(pos, block);
                            worldIn.notifyBlockOfStateChange(pos, block.getBlock());
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