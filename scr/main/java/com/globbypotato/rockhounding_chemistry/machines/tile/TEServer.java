package com.globbypotato.rockhounding_chemistry.machines.tile;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.BedReactorRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PrecipitationRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidStack;

public class TEServer extends TileEntityInv {

	public static int inputsSlots = 10;
	public static int templateSlots = 10;

	public static final int[] FILE_SLOTS = new int[]{0,1,2,3,4,5,6,7,8};
	public static final int PRINT_SLOT = 9;
	public static final int FILTER_SLOT = 9;

	public boolean cycle;
	public int device = -1;
	public int deviceMax;
	public int recipeAmount = 0;
	public ItemStack filterStack = ItemStack.EMPTY;
	public FluidStack filterFluid = null;

	public TEServer() {
		super(inputsSlots, 0, templateSlots, 0);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cycle = compound.getBoolean("Cycle");
		this.device = compound.getInteger("Device");
		this.deviceMax = compound.getInteger("MaxRecipes");
		this.recipeAmount = compound.getInteger("Amount");
		if(compound.hasKey("FilterStack")){
			this.filterStack = new ItemStack(compound.getCompoundTag("FilterStack"));
		}
		if(compound.hasKey("FilterFluid")){
			this.filterFluid = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("FilterFluid"));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Cycle", getCycle());
		compound.setInteger("Device", servedDevice());
		compound.setInteger("MaxRecipes", servedMaxRecipes());
		compound.setInteger("Amount", getRecipeAmount());

		if(!this.getFilterStack().isEmpty()){
			NBTTagCompound filterstack = new NBTTagCompound(); 
			this.filterStack.writeToNBT(filterstack);
			compound.setTag("FilterStack", filterstack);
		}

		if(this.getFilterFluid() != null){
	        NBTTagCompound filterfluidNBT = new NBTTagCompound();
	        this.filterFluid.writeToNBT(filterfluidNBT);
	        compound.setTag("FilterFluid", filterfluidNBT);
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

	public int servedMaxRecipes() {
		return this.deviceMax;
	}

	public int getRecipeAmount() {
		return this.recipeAmount;
	}

	public boolean isValidInterval(){
		return getRecipeIndex() > -1 && getRecipeIndex() < servedMaxRecipes();
	}

	public boolean hasStackFilter() {
		return servedDevice() > -1 && EnumServer.values()[servedDevice()].getFilter();
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
		if(!printSlot().isEmpty() && printSlot().isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()))){
			if(printSlot().hasTagCompound()){
				NBTTagCompound tag = printSlot().getTagCompound();
				if(tag.hasKey("Device") && tag.getInteger("Device") == servedDevice()){
					this.device = tag.getInteger("Device");
					if(tag.hasKey("Amount")){
						this.recipeAmount = tag.getInteger("Amount");
					}
					if(tag.hasKey("Cycle")){
						this.cycle = tag.getBoolean("Cycle");
					}
					if(tag.hasKey("Recipe")){
						this.recipeIndex = tag.getInteger("Recipe");
					}
					if(tag.hasKey("FilterStack")){
						ItemStack temp = new ItemStack(tag.getCompoundTag("FilterStack"));
						if(!temp.isEmpty()){
							this.filterStack = temp;
						}
					}
					if(tag.hasKey("FilterFluid")){
						FluidStack temp = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag("FilterFluid"));
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
			if(!printSlot().isEmpty() && printSlot().isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()))){
				if(!printSlot().hasTagCompound()){printSlot().setTagCompound(new NBTTagCompound());}
				NBTTagCompound tag = printSlot().getTagCompound();
				tag.setInteger("Device", servedDevice());
				tag.setInteger("Recipe", getRecipeIndex());
				tag.setInteger("Amount", getRecipeAmount());
				tag.setBoolean("Cycle", getCycle());
				tag.setInteger("Done", getRecipeAmount());
				if(hasStackFilter()){
					if(!this.getFilterStack().isEmpty()){
						NBTTagCompound filterstackNBT = new NBTTagCompound(); 
						this.getFilterStack().writeToNBT(filterstackNBT);
						tag.setTag("FilterStack", filterstackNBT);
					}else{
						if(tag.hasKey("FilterStack")){
							tag.removeTag("FilterStack");
						}
					}
					if(this.getFilterFluid() != null){
						NBTTagCompound filterfluidNBT = new NBTTagCompound(); 
						this.getFilterFluid().writeToNBT(filterfluidNBT);
						tag.setTag("FilterFluid", filterfluidNBT);
					}else{
						if(tag.hasKey("FilterFluid")){
							tag.removeTag("FilterFluid");
						}
					}

				}
			}
		}
	}

	public void applyServer(int i) {
		if(i == EnumServer.LAB_OVEN.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = LabOvenRecipes.lab_oven_recipes.size();
			}
		}else if(i == EnumServer.METAL_ALLOYER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = MetalAlloyerRecipes.metal_alloyer_recipes.size();
			}
		}else if(i == EnumServer.DEPOSITION.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = DepositionChamberRecipes.deposition_chamber_recipes.size();
			}
		}else if(i == EnumServer.SIZER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = 16;
			}
		}else if(i == EnumServer.LEACHING.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = 16;
			}
		}else if(i == EnumServer.RETENTION.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = 16;
			}
		}else if(i == EnumServer.CASTING.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = EnumCasting.size();
			}
		}else if(i == EnumServer.REFORMER.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = GasReformerRecipes.gas_reformer_recipes.size();
			}
		}else if(i == EnumServer.EXTRACTOR.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = 16;
			}
		}else if(i == EnumServer.PRECIPITATOR.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = PrecipitationRecipes.precipitation_recipes.size();
			}
		}else if(i == EnumServer.BED_REACTOR.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = BedReactorRecipes.bed_reactor_recipes.size();
			}
		}else{
			this.device = -1;
		}
	}

}