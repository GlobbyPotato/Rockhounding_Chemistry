package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMachineServer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_core.machines.tileentity.TemplateStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineEnergy;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineServer extends TileEntityMachineEnergy{

	public static int inputsSlots = 10;
	public static int templateSlots = 9;
	private ItemStackHandler template = new TemplateStackHandler(templateSlots);

	public static final int[] FILE_SLOTS = new int[]{0,1,2,3,4,5,6,7,8};
	public static final int PRINT_SLOT = 9;

	public boolean cycle;
	public int device = -1;
	public int deviceMax;
	public int recipeAmount = 0;

	public TileEntityMachineServer() {
		super(inputsSlots, 0, 0);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack printSlot(){
		return this.input.getStackInSlot(PRINT_SLOT);
	}

	public ItemStack inputSlot(int x){
		return this.input.getStackInSlot(x);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cycle = compound.getBoolean("Cycle");
		this.device = compound.getInteger("Device");
		this.deviceMax = compound.getInteger("MaxRecipes");
		this.recipeAmount = compound.getInteger("Amount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setBoolean("Cycle", getCycle());
		compound.setInteger("Device", servedDevice());
		compound.setInteger("MaxRecipes", servedMaxRecipes());
		compound.setInteger("Amount", getRecipeAmount());
        return compound;
	}



	//----------------------- HANDLER -----------------------
	public ItemStackHandler getTemplate(){
		return this.template;
	}

	@Override
	public int getGUIHeight() {
		return GuiMachineServer.HEIGHT;
	}



	//----------------------- CUSTOM -----------------------
	public boolean getCycle() {
		return this.cycle;
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
		return this.recipeIndex > -1 && this.recipeIndex < servedMaxRecipes();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		loadFile();
/*		if(!worldObj.isRemote){
			this.markDirtyClient();
		}*/
	}

	private void loadFile() {
//		if(!worldObj.isRemote){
			if(printSlot() != null && printSlot().isItemEqual(BaseRecipes.server_file)){
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
					}
				}
			}
//		}
	}

	public void writeFile() {
//		if(!worldObj.isRemote){
			if(printSlot() != null && printSlot().isItemEqual(BaseRecipes.server_file)){
				if(!printSlot().hasTagCompound()){printSlot().setTagCompound(new NBTTagCompound());}
				printSlot().getTagCompound().setInteger("Device", servedDevice());
				printSlot().getTagCompound().setInteger("Recipe", this.recipeIndex);
				printSlot().getTagCompound().setInteger("Amount", getRecipeAmount());
				printSlot().getTagCompound().setBoolean("Cycle", getCycle());
				printSlot().getTagCompound().setInteger("Done", getRecipeAmount());
			}
//		}
	}

	public void applyServer(int i) {
		if(i == EnumServer.LAB_OVEN.ordinal()){
			if(this.servedDevice() != i){
				this.device = i;
				this.deviceMax = MachineRecipes.labOvenRecipes.size();
			}
/*		}else if(i == EnumServer.METAL_ALLOYER.ordinal()){
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
			}*/
		}else{
			this.device = -1;
		}
	}

}