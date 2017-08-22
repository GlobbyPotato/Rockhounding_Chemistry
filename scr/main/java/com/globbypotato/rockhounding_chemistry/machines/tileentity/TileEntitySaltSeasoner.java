package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.SaltMaker;
import com.globbypotato.rockhounding_chemistry.machines.SaltSeasoner;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiSaltSeasoner;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.SaltSeasonerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntitySaltSeasoner extends TileEntityMachineEnergy {

	private int cycleDelay;
	private int cycleScroll;
	private int directionScan;

	public TileEntitySaltSeasoner() {
		super(1,1,0);
		
		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack) || ItemStack.areItemsEqual(insertingStack, BaseRecipes.saltRaw) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return GuiSaltSeasoner.HEIGHT;
	}

	public int getMaxCookTime(){
		return ModConfig.speedSeasoner;
	}



	//----------------------- CUSTOM -----------------------
	public boolean hasRecipe(ItemStack stack){
		return MachineRecipes.seasonerRecipes.stream().anyMatch(
				recipe -> stack != null && recipe.getInput() != null && stack.isItemEqual(recipe.getInput()));
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
	    EnumFacing facing = worldObj.getBlockState(pos).getValue(SaltSeasoner.FACING);
		if(!worldObj.isRemote){
			if(cycleDelay >= 20){
				if(directionScan <= 3){
					if(cycleScroll >= 20){
						acquireSalt(facing, directionScan, 1 + rand.nextInt(ModConfig.saltAmount));
						directionScan++;
						cycleScroll = 0;
					}else{
						cycleScroll++;
					}
				}else{
					cycleDelay = 0;
					directionScan = 0;
				}
			}else{
				cycleDelay++;
			}

			if(canProcess()){
				cookTime++;
				if(cookTime >= getMaxCookTime()) {
					cookTime = 0;
					process();
				}
			}
			this.markDirtyClient();
		}
	}

	private boolean canProcess() {
		return (hasRecipe(input.getStackInSlot(INPUT_SLOT)) || ItemStack.areItemsEqual(input.getStackInSlot(INPUT_SLOT), BaseRecipes.saltRaw))
			&& canOutput(output.getStackInSlot(OUTPUT_SLOT));
	}

	private void process() {
		if(input.getStackInSlot(INPUT_SLOT).isItemEqual(BaseRecipes.saltRaw)){
			output.setOrIncrement(OUTPUT_SLOT, BaseRecipes.saltStack);
		}else{
			ItemStack recipeOutput = getRecipeOutput(input.getStackInSlot(INPUT_SLOT));
			output.setOrStack(OUTPUT_SLOT, recipeOutput);
		}
		input.decrementSlot(INPUT_SLOT);
	}

	private void acquireSalt(EnumFacing facing, int side, int num) {
	    BlockPos tankPos = pos.offset(facing.fromAngle(facing.getHorizontalAngle() + (side * 90) ));
	    IBlockState checkState = worldObj.getBlockState(tankPos);
	    if(checkState == ModBlocks.saltMaker.getDefaultState().withProperty(SaltMaker.STAGE, Integer.valueOf(6))){
			if(canInput()){
				if(input.getStackInSlot(INPUT_SLOT) == null){
					input.setStackInSlot(INPUT_SLOT, BaseRecipes.saltRaw); 
					input.getStackInSlot(INPUT_SLOT).stackSize = num;
				}else{
					if(input.canStack(input.getStackInSlot(INPUT_SLOT), BaseRecipes.saltRaw)){
						for(int x = 0; x < num; x++){
							if(input.getStackInSlot(INPUT_SLOT).stackSize < input.getStackInSlot(INPUT_SLOT).getMaxStackSize()){
								input.getStackInSlot(INPUT_SLOT).stackSize++;
							}else{
								dropItemStack(worldObj, BaseRecipes.saltRaw, tankPos, num);
							}
						}
					}
				}
			}else{
				dropItemStack(worldObj, BaseRecipes.saltRaw, tankPos, num);
			}
			worldObj.setBlockState(tankPos, checkState.withProperty(SaltMaker.STAGE, Integer.valueOf(0)));
	    }
	}

	private void dropItemStack(World worldIn, ItemStack itemStack, BlockPos pos, int num) {
		itemStack.stackSize = num;
		EntityItem entityitem = new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), itemStack);
		entityitem.setPosition(pos.getX(), pos.getY() + 1.0D, pos.getZ());
		worldIn.spawnEntityInWorld(entityitem);
	}

	private boolean canInput(){
		return input.canSetOrStack(input.getStackInSlot(INPUT_SLOT), BaseRecipes.saltRaw);
	}

	private boolean canOutput(ItemStack stack) {
		return output.canSetOrStack(output.getStackInSlot(OUTPUT_SLOT), getRecipeOutput(input.getStackInSlot(INPUT_SLOT)));
	}

	public ItemStack getRecipeOutput(ItemStack inputStack){
		if(input.getStackInSlot(INPUT_SLOT).isItemEqual(BaseRecipes.saltRaw)){
			return BaseRecipes.saltStack;
		}else{
			if(inputStack != null){
				for(SaltSeasonerRecipe recipe: MachineRecipes.seasonerRecipes){
					if(recipe.getInput() != null && ItemStack.areItemsEqual(recipe.getInput(), inputStack)){
						return recipe.getOutput();
					}
				}
			}
		}
		return null;
	}

}