package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabBlenderRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TELabBlenderTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class TELabBlenderController extends TileEntityInv {
	public static int templateSlots = 2;

	public static final int PREVIEW_SLOT = 1;

	public LabBlenderRecipe dummyRecipe;
	private int tankslot; 
	private boolean loadedIngr;

	public TELabBlenderController() {
		super(0, 1, templateSlots, 0);
	}



	//----------------------- SLOTS -----------------------
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
		return "lab_blender_controller";
	}

	public int getCooktimeMax(){
		return ModConfig.speedBlender;
	}

	@Override
	public EnumFacing poweredFacing(){
		return EnumFacing.fromAngle(getFacing().getHorizontalAngle() + 270);
	}



	//----------------------- CUSTOM -----------------------
	private static boolean isValidIngredient(ItemStack recipeIngredient, ItemStack slotIngredient) {
		return CoreUtils.isMatchingIngredient(recipeIngredient, slotIngredient);
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<LabBlenderRecipe> recipeList(){
		return LabBlenderRecipes.lab_blender_recipes;
	}

	public LabBlenderRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public LabBlenderRecipe getCurrentRecipe(){
		if(hasTank()){
			for(int x = 0; x < recipeList().size(); x++){
				int recipeSize = getRecipeList(x).getElements().size(); 
				if(!getRecipeList(x).getElements().isEmpty() && recipeSize > 0){ 
					int correctIngredient = 0;
					for(int y = 0; y < recipeSize; y++){
						if(y < 7) {
							String recipeDict = getRecipeList(x).getElements().get(y);
							int neededSize = getRecipeList(x).getQuantities().get(y); 
							int totAvailable = 0;
							for(int z = 0; z < ingredients(); z++){
								ItemStack slotIngredient = getTank().inputSlot(z);
								if(!slotIngredient.isEmpty()){
									int availableSize = slotIngredient.getCount();
									ArrayList<Integer> inputIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(slotIngredient));
									if(!inputIDs.isEmpty() && inputIDs.size() > 0){
										for(Integer input : inputIDs){
											String inputDict = OreDictionary.getOreName(input);
											if(inputDict.matches(recipeDict)){
												totAvailable += availableSize;
											}
										}
									}
								}
							}
							if(totAvailable >= neededSize){
								correctIngredient++; 
							}
						}
					}
					if(correctIngredient == recipeSize && recipeSize == uniqueIngredients()){ 
						return getRecipeList(x);
					}
				}				
			}
		}
		return null;
	}
	private int uniqueIngredients() {
		ArrayList<ItemStack> uniques = new ArrayList<ItemStack>();
		for(int y = 0; y < ingredients(); y++){ 
			boolean exists = false;
			for(ItemStack unique : uniques){
				if(!getTank().inputSlot(y).isEmpty() && uniques.size() > 0 && !unique.isEmpty()){
					if(isValidIngredient(unique, getTank().inputSlot(y))){
						exists = true;
					}
				}
			}
			if(!exists){
				if(!getTank().inputSlot(y).isEmpty()){
					uniques.add(getTank().inputSlot(y));
				}
			}
		}
		return uniques.size();
	}

	public LabBlenderRecipe getDummyRecipe(){
		return this.dummyRecipe;
	}

	public boolean isValidRecipe() {
		return getDummyRecipe() != null;
	}



	//----------------------- STRUCTURE -----------------------
//engine
	public TEPowerGenerator getEngine(){
		TEPowerGenerator engine = TileStructure.getEngine(this.world, this.pos.offset(getFacing()), getFacing().getOpposite());
		return engine != null ? engine : null;
	}

	public boolean hasEngine(){
		return getEngine() != null;
	}

	public boolean hasRedstonePower(){
		return hasEngine() ? getEngine().getRedstone() > 0 : false;
	}

	private void drainPower() {
		getEngine().redstoneCount--;
		getEngine().markDirtyClient();
	}

//tank
	public BlockPos chamberPos(){
		return this.pos.offset(EnumFacing.UP, 1);		
	}

	public TELabBlenderTank getTank(){
		TileEntity te = this.world.getTileEntity(chamberPos());
		if(this.world.getBlockState(chamberPos()) != null && te instanceof TELabBlenderTank){
			TELabBlenderTank chamber = (TELabBlenderTank)te;
			if(chamber.getFacing() == getFacing()){
				return chamber;
			}
		}
		return null;
	}

	public boolean hasTank(){
		return getTank() != null;
	}

	public int ingredients(){
		return hasTank() ? TELabBlenderTank.INPUT_SLOT.length : 0;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			doPreset();

			if(getDummyRecipe() == null){
				this.tankslot++;
				if(isAnythingLoaded()){
					dummyRecipe = getCurrentRecipe();
					handlePreview();
					this.cooktime = 0;
				}
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

	private boolean isAnythingLoaded() {
		if(this.tankslot > getTank().INPUT_SLOT.length - 1){
			this.tankslot = 0;
			this.loadedIngr = false;
		}
		if(!getTank().inputSlot(this.tankslot).isEmpty()){
			this.loadedIngr = true;
		}
		return this.tankslot == getTank().INPUT_SLOT.length - 1 && this.loadedIngr;
	}

	private void doPreset() {
		if(hasEngine()){
			if(getEngine().enablePower){
				getEngine().enablePower = false;
				getEngine().markDirtyClient();
			}
			if(!getEngine().enableRedstone){
				getEngine().enableRedstone = true;
				getEngine().markDirtyClient();
			}
		}
	}

	private boolean canProcess() {
		return isActive()
			&& isValidRecipe()
			&& hasRedstonePower()
			&& handleOutput();
	}

	private void handlePreview() {
		if(hasTank() && isValidRecipe()){
			this.template.setStackInSlot(PREVIEW_SLOT, getDummyRecipe().getOutput());
		}else{
			this.template.setStackInSlot(PREVIEW_SLOT, ItemStack.EMPTY);
		}
	}

	private boolean handleOutput() {
		return this.output.canSetOrStack(this.output.getStackInSlot(OUTPUT_SLOT), getDummyRecipe().getOutput());
	}

	private void process() {
		if(hasTank() && getDummyRecipe() != null && getDummyRecipe() == getCurrentRecipe()){
			if(!getDummyRecipe().getOutput().isEmpty()){
				this.output.setOrStack(OUTPUT_SLOT, getDummyRecipe().getOutput());
			}

			for(int x = 0; x < getDummyRecipe().getElements().size(); x++){
				String recipeDict = getDummyRecipe().getElements().get(x);
				int neededSize = getDummyRecipe().getQuantities().get(x);
				for(int z = 0; z < ingredients(); z++){
					if(neededSize > 0){
						ItemStack slotIngredient = getTank().inputSlot(z);
						if(!slotIngredient.isEmpty()){
							ArrayList<Integer> inputIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(slotIngredient));
							if(!inputIDs.isEmpty() && inputIDs.size() > 0){
								for(Integer input : inputIDs){
									String inputDict = OreDictionary.getOreName(input);
									if(inputDict.matches(recipeDict)){
										int availableSize = slotIngredient.getCount();
										int sizeToDrain = Math.min(neededSize, availableSize);
										((MachineStackHandler) getTank().getInput()).decrementSlotBy(z, sizeToDrain);
										neededSize -= sizeToDrain;
									}
								}
							}
						}
					}
				}
			}
		}

		this.dummyRecipe = null;
		handlePreview();
	}
}