package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.utils.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PowderMixerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ElementsCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabBlenderRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PowderMixerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEMaterialCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEServer;
import com.globbypotato.rockhounding_chemistry.machines.tile.devices.TEPowerGenerator;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TECentrifuge;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEPowderMixerTank;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

public class TEPowderMixerController extends TileEntityInv implements IInternalServer{
	public static int outputSlots = 1;
	public static int templateSlots = 3;

	public LabBlenderRecipe dummyRecipe;
	private int tankslot; 
	private boolean loadedIngr;
	public boolean isRepeatable;
	public int currentFile = -1;

	public TEPowderMixerController() {
		super(0, outputSlots, templateSlots, 0);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack outputSlot(){
		return this.output.getStackInSlot(OUTPUT_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "powder_mixer_controller";
	}

	public int getCooktimeMax(){
		return ModConfig.speedMixer;
	}

	private static int deviceCode() {
		return EnumServer.POWDER_MIXER.ordinal();
	}

	@Override
	public EnumFacing poweredFacing(){
		return getFacing().getOpposite();
	}



	//----------------------- CUSTOM -----------------------
	private static boolean isValidIngredient(ItemStack recipeIngredient, ItemStack slotIngredient) {
		return CoreUtils.isMatchingIngredient(recipeIngredient, slotIngredient);
	}



	//----------------------- RECIPE -----------------------
	public ArrayList<PowderMixerRecipe> recipeList(){
		return PowderMixerRecipes.powder_mixer_recipes;
	}

	public PowderMixerRecipe getRecipeList(int x){
		return recipeList().get(x);
	}

	public PowderMixerRecipe getCurrentRecipe(){
		if(isValidPreset()){
			return getRecipeList(getSelectedRecipe());
		}
		return null;
	}

	public boolean isValidRecipe() {
		return getCurrentRecipe() != null;
	}

	public boolean isValidPreset(){
		return getSelectedRecipe() > -1 && getSelectedRecipe() < recipeList().size();
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

	public TEPowderMixerTank getTank(){
		TileEntity te = this.world.getTileEntity(chamberPos());
		if(this.world.getBlockState(chamberPos()) != null && te instanceof TEPowderMixerTank){
			TEPowderMixerTank chamber = (TEPowderMixerTank)te;
			if(chamber.getFacing() == getFacing()){
				return chamber;
			}
		}
		return null;
	}

	public boolean hasTank(){
		return getTank() != null;
	}

//centrifuge
	public TECentrifuge getElementCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, chamberPos().offset(isFacingAt(270)), isFacingAt(270));
		return centrifuge != null ? centrifuge : null;
	}

	public TECentrifuge getMaterialCentrifuge(){
		TECentrifuge centrifuge = TileStructure.getCentrifuge(this.world, chamberPos().offset(isFacingAt(90)), isFacingAt(90));
		return centrifuge != null ? centrifuge : null;
	}

	public boolean hasCentrifuges(){
		return getElementCentrifuge() != null && getMaterialCentrifuge() != null;
	}
	
//element cabinet
	public TEElementsCabinetBase getElementsCabinet(){
		BlockPos cabinetPos = chamberPos().offset(isFacingAt(270), 2);
		TEElementsCabinetBase cabinet = TileStructure.getElementCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasElementsCabinet(){
		return getElementsCabinet() != null;
	}

	public TEMaterialCabinetBase getMaterialCabinet(){
		BlockPos cabinetPos = chamberPos().offset(isFacingAt(90), 2);
		TEMaterialCabinetBase cabinet = TileStructure.getMaterialCabinet(this.world, cabinetPos, getFacing());
		return cabinet != null ? cabinet : null;
	}

	public boolean hasMaterialCabinet(){
		return getMaterialCabinet() != null;
	}

	//server
	public TEServer getServer(){
		TEServer server = TileStructure.getServer(this.world, this.pos.offset(isFacingAt(270)), isFacingAt(90));
		return server != null ? server : null;
	}

	public boolean hasServer(){
		return getServer() != null;
	}

	private boolean isAssembled() {
		return hasElementsCabinet() && hasMaterialCabinet() && hasTank() && hasCentrifuges();
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(!this.world.isRemote){
			doPreset();
			initializeServer(isRepeatable, getServer(), deviceCode(), this.recipeStep, recipeList().size());

			if(canProcess()){
				this.cooktime++;
				drainPower();
				resetOversee(getServer(), this.currentFile);
				if(getCooktime() >= getCooktimeMax()) {
					this.cooktime = 0;
					process();
				}
				this.markDirtyClient();
			}else{
				tickOversee(getServer(), this.currentFile);
				tickOff();
			}
		}
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
			&& isFullRecipe()
			&& hasRedstonePower()
			&& canOutput()
			&& handleServer(getServer(), this.currentFile);
	}

	private boolean isFullRecipe() {
		int recipeTotal = getCurrentRecipe().getElements().size();
		int countedIngr = 0;
	
		for(int i = 0;  i < recipeTotal; i++){
			String recipeIngredient = getRecipeList(getSelectedRecipe()).getElements().get(i);
			int recipeQuantity = getCurrentRecipe().getQuantities().get(i);

			for(int j = 0; j < getElementsCabinet().MATERIAL_LIST.size(); j++) {
				String cabinetIngredient = getElementsCabinet().MATERIAL_LIST.get(j).getOredict();
				if(cabinetIngredient.matches(recipeIngredient)){
					int cabinetQuantity = getElementsCabinet().MATERIAL_LIST.get(j).getAmount();
					if(cabinetQuantity >= recipeQuantity) {
						countedIngr++;
					}
				}
			}

			for(int j = 0; j < getMaterialCabinet().MATERIAL_LIST.size(); j++) {
				String cabinetIngredient = getMaterialCabinet().MATERIAL_LIST.get(j).getOredict();
				if(cabinetIngredient.matches(recipeIngredient)){
					int cabinetQuantity = getMaterialCabinet().MATERIAL_LIST.get(j).getAmount();
					if(cabinetQuantity >= recipeQuantity) {
						countedIngr++;
					}
				}
			}
		}
		return countedIngr == recipeTotal;
	}

	private boolean canOutput() {
		return this.output.canSetOrStack(this.output.getStackInSlot(OUTPUT_SLOT), getCurrentRecipe().getOutput());
	}

	private void process() {
		if(isValidPreset() && isValidRecipe()) {
			this.output.setOrStack(OUTPUT_SLOT, getCurrentRecipe().getOutput());
	
			int recipeTotal = getCurrentRecipe().getElements().size();
			for(int i = 0;  i < recipeTotal; i++){
				if(i < 8) {
					String recipeIngredient = getRecipeList(getSelectedRecipe()).getElements().get(i);
					int recipeQuantity = getCurrentRecipe().getQuantities().get(i);
		
					for(int j = 0; j < getElementsCabinet().MATERIAL_LIST.size(); j++) {
						String cabinetIngredient = getElementsCabinet().MATERIAL_LIST.get(j).getOredict();
						if(cabinetIngredient.matches(recipeIngredient)){
							int cabinetQuantity = getElementsCabinet().MATERIAL_LIST.get(j).getAmount();
							if(cabinetQuantity >= recipeQuantity) {
								int storedAmount = cabinetQuantity - recipeQuantity;
								ElementsCabinetRecipe currentMaterial = new ElementsCabinetRecipe(getElementsCabinet().MATERIAL_LIST.get(j).getSymbol(), getElementsCabinet().MATERIAL_LIST.get(j).getOredict(), getElementsCabinet().MATERIAL_LIST.get(j).getName(), storedAmount, getElementsCabinet().MATERIAL_LIST.get(j).getExtraction());
								getElementsCabinet().MATERIAL_LIST.set(j, currentMaterial);
		
							}
						}
					}
		
					for(int j = 0; j < getMaterialCabinet().MATERIAL_LIST.size(); j++) {
						String cabinetIngredient = getMaterialCabinet().MATERIAL_LIST.get(j).getOredict();
						if(cabinetIngredient.matches(recipeIngredient)){
							int cabinetQuantity = getMaterialCabinet().MATERIAL_LIST.get(j).getAmount();
							if(cabinetQuantity >= recipeQuantity) {
								int storedAmount = cabinetQuantity - recipeQuantity;
								MaterialCabinetRecipe currentMaterial = new MaterialCabinetRecipe(getMaterialCabinet().MATERIAL_LIST.get(j).getSymbol(), getMaterialCabinet().MATERIAL_LIST.get(j).getOredict(), getMaterialCabinet().MATERIAL_LIST.get(j).getName(), storedAmount, getMaterialCabinet().MATERIAL_LIST.get(j).getExtraction());
								getMaterialCabinet().MATERIAL_LIST.set(j, currentMaterial);
		
							}
						}
					}
				}
			}
		
			updateServer(getServer(), this.currentFile);
		}
	}



	//----------------------- SERVER -----------------------
	//if there is any file with remaining recipes, get its slot
	//at the end of the cycle reset all anyway
	public void loadServerStatus() {
		this.currentFile = -1;
		if(getServer().isActive()){
			for(int x = 0; x < getServer().FILE_SLOTS.length; x++){
				ItemStack fileSlot = getServer().inputSlot(x);
				if(TEServer.isValidFile(fileSlot)){
					if(fileSlot.hasTagCompound()){
						NBTTagCompound tag = fileSlot.getTagCompound();
						if(isWrittenFile(tag)){
							if(isCorrectDevice(tag, deviceCode())){
								if(getRecipe(tag) < recipeList().size()){
									if(getDone(tag) > 0){
										if(this.recipeIndex != getRecipe(tag)){
											this.recipeIndex = getRecipe(tag);
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
				if(x == getServer().FILE_SLOTS.length - 1){
					resetFiles(getServer(), deviceCode());
				}
			}
		}
	}
}