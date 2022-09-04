package com.globbypotato.rockhounding_chemistry.machines.tile.collateral;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.IInternalServer;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class TEServer extends TileEntityInv implements IInternalServer {

	public static int inputsSlots = 10;
	public static int templateSlots = 10;

	public static final int[] FILE_SLOTS = new int[]{0,1,2,3,4,5,6,7,8};
	public static final int PRINT_SLOT = 9;
	public static final int FILTER_SLOT = 9;

	public boolean cycle;
	public int device = -1;
	public int recipeAmount = 0;
	public ItemStack filterStack = ItemStack.EMPTY;
	public FluidStack filterFluid = null;

	public TEServer() {
		super(inputsSlots, 0, templateSlots, 0);

		this.input =  new MachineStackHandler(inputsSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot < FILE_SLOTS.length && (isValidFile(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == PRINT_SLOT && (isValidFile(insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};

	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cycle = compound.getBoolean(tag_cycle);
		this.device = compound.getInteger(tag_device);
		this.recipeAmount = compound.getInteger(tag_amount);

		if(hasFilterItem(compound)){
			this.filterStack = getFilterItem(compound);
		}
		if(hasFilterFluid(compound)){
			this.filterFluid = getFilterFluid(compound);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean(tag_cycle, getCycle());
		compound.setInteger(tag_device, servedDevice());
		compound.setInteger(tag_amount, getRecipeAmount());

		if(!this.getFilterStack().isEmpty()){
			NBTTagCompound filterstack = new NBTTagCompound(); 
			this.filterStack.writeToNBT(filterstack);
			compound.setTag(tag_filter_item, filterstack);
		}
		if(this.getFilterFluid() != null){
	        NBTTagCompound filterfluidNBT = new NBTTagCompound();
	        this.filterFluid.writeToNBT(filterfluidNBT);
	        compound.setTag(tag_filter_fluid, filterfluidNBT);
		}

        return compound;
	}



	//----------------------- SLOTS -----------------------
	public ItemStack filterSlot(){
		return this.template.getStackInSlot(FILTER_SLOT);
	}

	public ItemStack printSlot(){
		return this.input.getStackInSlot(PRINT_SLOT);
	}

	public ItemStack inputSlot(int x){
		return this.input.getStackInSlot(x);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "server";
	}

	@Override
	public boolean isComparatorSensible(){
		return false;
	}



	//----------------------- CUSTOM -----------------------
	public boolean getCycle() {
		return this.cycle;
	}

	public ItemStack getFilterStack() {
		return this.filterStack;
	}

	public FluidStack getFilterFluid() {
		return this.filterFluid;
	}

	public int servedDevice() {
		return this.device;
	}

	public int getRecipeAmount() {
		return this.recipeAmount;
	}

	public boolean isValidInterval(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < (int)getRecipeMax();
	}

	public boolean isValidParameter(){
		return getRecipeIndex() > -1 && getRecipeIndex() < getRecipeMax();
	}

	public boolean hasStackFilter() {
		return servedDevice() > -1 && EnumServer.values()[servedDevice()].getFilter();
	}

	public static boolean isValidFile(ItemStack insertingStack) {
		return !insertingStack.isEmpty() && insertingStack.isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()));
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			loadFilter();
			loadFile();
			this.markDirtyClient();
		}
	}

	private void loadFilter() {
		if(this.hasStackFilter()){
			if(!this.getFilterStack().isEmpty()){
				if(this.filterSlot().isEmpty() || !this.filterSlot().isItemEqual(getFilterStack())){
					this.template.setStackInSlot(FILTER_SLOT, getFilterStack());
				}
			}
		}

		if(!this.hasStackFilter() || (this.hasStackFilter() && this.getFilterStack().isEmpty())){
			this.template.setStackInSlot(FILTER_SLOT, ItemStack.EMPTY);
		}
	}

	private void loadFile() {
		if(isValidFile(printSlot())){
			if(printSlot().hasTagCompound()){
				NBTTagCompound tag = printSlot().getTagCompound();
				if(isCorrectDevice(tag, servedDevice())){
					this.device = getDevice(tag);
					if(hasAmount(tag)){
						this.recipeAmount = getAmount(tag);
					}
					if(hasCycle(tag)){
						this.cycle = getCycle();
					}
					if(hasRecipe(tag)){
						this.recipeIndex = getRecipe(tag);
					}
					if(hasFilterItem(tag)){
						ItemStack temp = getFilterItem(tag);
						if(!temp.isEmpty()){
							this.filterStack = temp;
						}
					}
					if(hasFilterFluid(tag)){
						FluidStack temp = getFilterFluid(tag);
						if(temp != null){
							this.filterFluid = temp;
						}
					}

				}
			}
		}
	}

	public void writeFile() {
		if(!this.world.isRemote){
			if(isValidFile(printSlot()) && getRecipeIndex() > -1 ){
				if(!printSlot().hasTagCompound()){printSlot().setTagCompound(new NBTTagCompound());}
				NBTTagCompound tag = printSlot().getTagCompound();
				tag.setInteger(tag_device, servedDevice());
				tag.setFloat(tag_recipe, getRecipeIndex());
				tag.setInteger(tag_amount, getRecipeAmount());
				tag.setBoolean(tag_cycle, getCycle());
				tag.setInteger(tag_done, getRecipeAmount());
				if(hasStackFilter()){
					if(!this.getFilterStack().isEmpty()){
						NBTTagCompound filterstackNBT = new NBTTagCompound(); 
						this.getFilterStack().writeToNBT(filterstackNBT);
						tag.setTag(tag_filter_item, filterstackNBT);
					}else{
						if(hasFilterItem(tag)){
							tag.removeTag(tag_filter_item);
						}
					}
					if(this.getFilterFluid() != null){
						NBTTagCompound filterfluidNBT = new NBTTagCompound(); 
						this.getFilterFluid().writeToNBT(filterfluidNBT);
						tag.setTag(tag_filter_fluid, filterfluidNBT);
					}else{
						if(hasFilterFluid(tag)){
							tag.removeTag(tag_filter_fluid);
						}
					}

				}
			}
		}
	}

	public void applyServer(int i, float serverStep, float serverMax) {
		if(i == EnumServer.LAB_OVEN.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.METAL_ALLOYER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.DEPOSITION.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.SIZER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.LEACHING.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.RETENTION.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.CASTING.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.REFORMER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.POWDER_MIXER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.EXTRACTOR.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.PRECIPITATOR.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else if(i == EnumServer.BED_REACTOR.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.recipeStep = serverStep;
				this.recipeMax = serverMax;
			}
		}else{
			this.device = -1;
		}
	}

	public boolean requiresParameter() {
		return this.servedDevice() == EnumServer.LEACHING.ordinal() || this.servedDevice() == EnumServer.RETENTION.ordinal(); 
	}
}
