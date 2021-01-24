package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumMiscItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TEMetalAlloyer extends TileEntityInv implements IInternalServer{

	public static int inputSlots = 1;
	public static int outputSlots = 1;
	public static int templateSlots = 4;
	public static int upgradeSlots = 1;

	public static final int PATTERN_SLOT = 0;

	public static final int PREVIEW_SLOT = 3;

	public static final int SPEED_SLOT = 0;

	public int currentFile = -1;
	public boolean isRepeatable;
	public MetalAlloyerRecipe dummyRecipe;

	public TEMetalAlloyer() {
		super(inputSlots, outputSlots, templateSlots, upgradeSlots);

		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == PATTERN_SLOT && CoreUtils.hasConsumable(BaseRecipes.ingot_pattern, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
		
		this.upgrade =  new MachineStackHandler(upgradeSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SPEED_SLOT && ModUtils.isValidSpeedUpgrade(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationUpgrade = new WrappedItemHandler(this.upgrade, WriteMode.IN);

	}



	//----------------------- SLOTS -----------------------
	public ItemStack speedSlot() {
		return this.upgrade.getStackInSlot(SPEED_SLOT);
	}

	public ItemStack patternSlot() {
		return this.input.getStackInSlot(PATTERN_SLOT);
	}

	public ItemStack outputSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}

	public ItemStack previewSlot(){
		return this.template.getStackInSlot(PREVIEW_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "metal_alloyer";
	}

	public int speedFactor() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModUtils.speedUpgrade(speedSlot()) : 1;
	}

	public int getCooktimeMax() {
		return ModUtils.isValidSpeedUpgrade(speedSlot()) ? ModConfig.speedAlloyer / ModUtils.speedUpgrade(speedSlot()): ModConfig.speedAlloyer;
	}

	private static int deviceCode() {
		return EnumServer.METAL_ALLOYER.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<MetalAlloyerRecipe> recipeList(){
		return MetalAlloyerRecipes.metal_alloyer_recipes;
	}

	public MetalAlloyerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public ArrayList<MaterialCabinetRecipe> materialList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}

	public MaterialCabinetRecipe getMaterialList(int x){
		return materialList().get(x);
	}

	public MetalAlloyerRecipe getCurrentRecipe(){
		if(isActive() && isValidPreset()){
			return isFullRecipe(getRecipeList(getRecipeIndex()));
		}
		return null;
	}

	public MetalAlloyerRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	private MetalAlloyerRecipe isFullRecipe(MetalAlloyerRecipe recipe) {
		int recipeSize = recipe.getInputs().size();
		int ingrCount = 0;
		if(hasCabinet() && hasMaterial()){
			for(int ingredient = 0; ingredient < recipeSize; ingredient++){
				String ingrOredict = recipe.getInputs().get(ingredient);
				int ingrQuantity = recipe.getQuantities().get(ingredient);
				for(int element = 0; element < getCabinet().elementList.length; element++){
					if(EnumElements.getDust(element).matches(ingrOredict)){
						if(ingrQuantity <= getCabinet().elementList[element]){
							ingrCount++;
						}
					}
				}
				for(int element = 0; element < materialList().size(); element++){
					if(getMaterialList(element).getOredict().matches(ingrOredict)){
						if(ingrQuantity <= getMaterial().elementList[element]){
							ingrCount++;
						}
					}
				}
			}
		}
		return ingrCount == recipeSize ? recipe : null;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}

	public boolean isValidPreset(){
		return getRecipeIndex() > -1 && getRecipeIndex() < recipeList().size();
	}

	public ItemStack recipeOutput(){ return isValidRecipe() ? getDummyRecipe().getOutput() : ItemStack.EMPTY; }

	
	
	
	

	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos, isFacingAt(90), 1, 0);
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasFuelPower(){
		return hasEngine() ? getEngine().getPower() > 0 : false;
	}

	private void drainPower() {
		getEngine().powerCount--;
		getEngine().markDirtyClient();
	}



//element cabinet
	public TEElementsCabinetBase getCabinet(){
		BlockPos cabinetPos = this.pos.offset(getFacing(), 1);
		TileEntity te = this.world.getTileEntity(cabinetPos);
		if(this.world.getBlockState(cabinetPos) != null && te instanceof TEElementsCabinetBase){
			TEElementsCabinetBase cabinet = (TEElementsCabinetBase)te;
			if(cabinet.getFacing() == getFacing().getOpposite()){
				return cabinet;
			}
		}
		return null;
	}

	public boolean hasCabinet(){
		return getCabinet() != null;
	}

//material cabinet
	public TEMaterialCabinetBase getMaterial(){
		BlockPos cabinetPos = this.pos.offset(getFacing(), 1).offset(isFacingAt(270), 1);
		TileEntity te = this.world.getTileEntity(cabinetPos);
		if(this.world.getBlockState(cabinetPos) != null && te instanceof TEMaterialCabinetBase){
			TEMaterialCabinetBase cabinet = (TEMaterialCabinetBase)te;
			if(cabinet.getFacing() == getFacing().getOpposite()){
				return cabinet;
			}
		}
		return null;
	}

	public boolean hasMaterial(){
		return getMaterial() != null;
	}

//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos, isFacingAt(270), 1, 0);
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}



	//----------------------- CUSTOM -----------------------



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			doPreset();

			initializeServer(isRepeatable, hasServer(), getServer(), deviceCode());
			
			if(getDummyRecipe() == null){
				dummyRecipe = getCurrentRecipe();
				this.cooktime = 0;
			}

			if(canProcess()){
				this.cooktime++;
				drainPower();
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				tickOff();
			}
		}
	}

	private void doPreset() {
		if(hasEngine()){
			if(!getEngine().enablePower){
				getEngine().enablePower = true;
				getEngine().markDirtyClient();
			}
			if(getEngine().enableRedstone){
				getEngine().enableRedstone = false;
				getEngine().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe()
			&& hasFuelPower()
			&& hasPattern()
			&& canOutput()
			&& hasCabinet() && hasMaterial()
			&& handleServer(hasServer(), getServer(), this.currentFile); //server
	}

	private boolean canOutput() {
		return this.output.canSetOrStack(outputSlot(), recipeOutput());
	}

	private void process() {
		if(isValidRecipe()){
			this.output.setOrStack(OUTPUT_SLOT, getDummyRecipe().getOutput().copy());
	
			int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, patternSlot());
			this.input.damageUnbreakingSlot(unbreakingLevel, PATTERN_SLOT);
	
			if(hasCabinet() && hasMaterial()){
				for(int ingredient = 0; ingredient < getDummyRecipe().getInputs().size(); ingredient++){
					String ingrOredict = getDummyRecipe().getInputs().get(ingredient);
					int ingrQuantity = getDummyRecipe().getQuantities().get(ingredient);
					for(int element = 0; element < getCabinet().elementList.length; element++){
						if(EnumElements.getDust(element).matches(ingrOredict)){
							getCabinet().elementList[element] -= ingrQuantity;
						}
					}
					for(int element = 0; element < materialList().size(); element++){
						if(getMaterialList(element).getOredict().matches(ingrOredict)){
							getMaterial().elementList[element] -= ingrQuantity;
						}
					}
				}
				getCabinet().markDirtyClient();
				getMaterial().markDirtyClient();
			}

			updateServer(hasServer(), getServer(), this.currentFile);
		}
		this.dummyRecipe = null;
	}

	private boolean hasPattern() {
		return CoreUtils.hasConsumable(BaseRecipes.ingot_pattern, this.input.getStackInSlot(PATTERN_SLOT));
	}

	public void showPreview() {
		if(isValidPreset()){
			ItemStack previewStack = getRecipeList(getRecipeIndex()).getOutput();
			if(!this.template.getStackInSlot(PREVIEW_SLOT).isItemEqual(previewStack)){
				previewStack.setCount(1);
				this.template.setStackInSlot(PREVIEW_SLOT, previewStack);
			}
		}else{
			if(!this.template.getStackInSlot(PREVIEW_SLOT).isEmpty()){
				this.template.setStackInSlot(PREVIEW_SLOT, ItemStack.EMPTY);
			}
		}
	}

	

	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < TEServer.FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x).copy();
				if(!fileSlot.isEmpty() && fileSlot.isItemEqual(new ItemStack(ModItems.MISC_ITEMS, 1, EnumMiscItems.SERVER_FILE.ordinal()))){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isValidFile(tag)){
							if(tag.getInteger("Device") == deviceCode()){
								if(tag.getInteger("Recipe") < recipeList().size()){
									if(tag.getInteger("Done") > 0){
										if(this.recipeIndex != tag.getInteger("Recipe")){
											this.recipeIndex = tag.getInteger("Recipe");
											this.markDirtyClient();
										}
										if(this.currentFile != x){
											this.currentFile = x;
											this.markDirtyClient();
										}
										break;
									}
								}
							}
						}
					}
				}
				if(x == TEServer.FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}

}