package com.globbypotato.rockhounding_chemistry.machines.tile.collateral;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MaterialCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MaterialCabinetRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TileStructure;
import com.globbypotato.rockhounding_chemistry.machines.tile.structure.TEExtractorBalance;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class TEMaterialCabinetBase extends TileEntityInv {

	public ArrayList<MaterialCabinetRecipe> MATERIAL_LIST = new ArrayList<MaterialCabinetRecipe>();
	public ArrayList<MaterialCabinetRecipe> TEMP_MATERIAL_LIST = new ArrayList<MaterialCabinetRecipe>();
	public ArrayList<MaterialCabinetRecipe> PAGED_MATERIAL_LIST = new ArrayList<MaterialCabinetRecipe>();
	public static int templateSlots = 59;
	public int charNum = 0;
	public int pageNum = 1;
	public boolean drain = false;
	public boolean sorting = false;
	public boolean showAll = false;
	public int currentMaterial = 0;
	private ItemStack elementStack = ItemStack.EMPTY;

	public TEMaterialCabinetBase() {
		super(0, 0, templateSlots, 0);
	}



	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.charNum = compound.getInteger("Char");
		this.pageNum = compound.getInteger("Page");
		this.drain = compound.getBoolean("Drain");
		this.sorting = compound.getBoolean("Sort");
		this.showAll = compound.getBoolean("Release");

		NBTTagList recipes = compound.getTagList("MaterialList", 10);
		if(recipes.tagCount() > 0) {
			this.MATERIAL_LIST = new ArrayList<MaterialCabinetRecipe>();
	        for (int i = 0; i < recipes.tagCount(); ++i){
	            NBTTagCompound elements = recipes.getCompoundTagAt(i);
	            int j = elements.getByte("Elements");
				String symbol = elements.getString("symbol" + j);
				String oredict = elements.getString("oredict" + j);
				String name = elements.getString("name" + j);
				int amount = elements.getInteger("amount" + j);
				boolean extraction = elements.getBoolean("extraction" + j);
				this.MATERIAL_LIST.add(j, new MaterialCabinetRecipe(symbol, oredict, name, amount, extraction));
	        }
			this.reloadCabinet();
			this.collectMaterial(this.getAlphabet());
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("Char", this.charNum);
		compound.setInteger("Page", this.pageNum);
		compound.setBoolean("Drain", this.drain);
		compound.setBoolean("Sort", this.sorting);
		compound.setBoolean("Release", this.showAll);

		NBTTagList recipes = new NBTTagList();
		for(int i = 0; i < this.MATERIAL_LIST.size(); i++){
			NBTTagCompound elements = new NBTTagCompound();
			elements.setByte("Elements", (byte)i);
			elements.setString("symbol" + i, this.MATERIAL_LIST.get(i).getSymbol());
			elements.setString("oredict" + i, this.MATERIAL_LIST.get(i).getOredict());
			elements.setString("name" + i, this.MATERIAL_LIST.get(i).getName());
			elements.setInteger("amount" + i, this.MATERIAL_LIST.get(i).getAmount());
			elements.setBoolean("extraction" + i, this.MATERIAL_LIST.get(i).getExtraction());
			recipes.appendTag(elements);
		}
		compound.setTag("MaterialList", recipes);
		return compound;
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "material_cabinet_base";
	}



	//----------------------- CUSTOM -----------------------
	public int getExtractingFactor(){
		return ModConfig.extractorFactor;
	}

	public int getChar() {
		return this.charNum;
	}

	public int getPage() {
		return this.pageNum;
	}

	public boolean getDrain() {
		return this.drain;
	}

	public boolean getSorting() {
		return this.sorting;
	}

	public boolean getRelease() {
		return this.showAll;
	}

	public String getAlphabet() {
		return String.valueOf((char)(this.getChar() + 65));
	}

	public void collectMaterial(String chars) {
		TEMP_MATERIAL_LIST.clear();
		PAGED_MATERIAL_LIST.clear();
		this.reloadCabinet();

		//select char
		if(getRelease()) {
			if(getSorting()) {
				for(int x = 0; x < MATERIAL_LIST.size(); x++){
					if(MATERIAL_LIST.get(x) != null &&  MATERIAL_LIST.get(x).getName().toLowerCase().startsWith(chars.toLowerCase())){
						TEMP_MATERIAL_LIST.add(MATERIAL_LIST.get(x));
					}
				}
			}else {
				for(int x = 0; x < MATERIAL_LIST.size(); x++){
					if(MATERIAL_LIST.get(x) != null &&  MATERIAL_LIST.get(x).getSymbol().toLowerCase().startsWith(chars.toLowerCase())){
						TEMP_MATERIAL_LIST.add(MATERIAL_LIST.get(x));
					}
				}
			}
		}else {
			for(int x = 0; x < MATERIAL_LIST.size(); x++){
				if(MATERIAL_LIST.get(x) != null && !MATERIAL_LIST.get(x).getName().isEmpty()) {
					TEMP_MATERIAL_LIST.add(MATERIAL_LIST.get(x));
				}
			}
		}

		//sort by
    	if(getSorting()) {
    		Collections.sort(TEMP_MATERIAL_LIST, new NameComparator());
    	}else {
    		Collections.sort(TEMP_MATERIAL_LIST, new SymbolComparator());
    	}

    	//divide to pages
		int page_split = 28 * (this.getPage() - 1);
		if(page_split < 0) {page_split = 0;}
		for(int x = page_split; x < page_split + 28; x++){
			if(x < TEMP_MATERIAL_LIST.size()){
				PAGED_MATERIAL_LIST.add(TEMP_MATERIAL_LIST.get(x));
			}
		}
	}
	
	public class SymbolComparator implements Comparator<MaterialCabinetRecipe>{
	    public int compare(MaterialCabinetRecipe o1, MaterialCabinetRecipe o2){
	       return o1.getSymbol().compareToIgnoreCase(o2.getSymbol());
	   }
	}
	public class NameComparator implements Comparator<MaterialCabinetRecipe>{
	    public int compare(MaterialCabinetRecipe o1, MaterialCabinetRecipe o2){
	       return o1.getName().compareToIgnoreCase(o2.getName());
	   }
	}

	public void enableBalance(int slot) {
		if(!MATERIAL_LIST.isEmpty() && !PAGED_MATERIAL_LIST.isEmpty()) {
			if(slot < this.PAGED_MATERIAL_LIST.size()) {
				for(int x = 0; x < this.MATERIAL_LIST.size(); x++) {
					if(!this.PAGED_MATERIAL_LIST.get(slot).getSymbol().isEmpty() && this.PAGED_MATERIAL_LIST.get(slot).getSymbol().matches(this.MATERIAL_LIST.get(x).getSymbol())) {
						boolean status =  this.MATERIAL_LIST.get(x).getExtraction();
						status = !status;
						this.MATERIAL_LIST.set(x, new MaterialCabinetRecipe(this.MATERIAL_LIST.get(x).getSymbol(), this.MATERIAL_LIST.get(x).getOredict(), this.MATERIAL_LIST.get(x).getName(), this.MATERIAL_LIST.get(x).getAmount(), status));
					}
				}
			}
		}
	}

	public void switchDrain() {
		if(!MATERIAL_LIST.isEmpty()) {
			for(int x = 0; x < this.MATERIAL_LIST.size(); x++) {
				if(!this.MATERIAL_LIST.get(x).getSymbol().isEmpty()) {
					boolean status =  this.MATERIAL_LIST.get(x).getExtraction();
					if(getDrain()) {
						status = true;
					}else {
						status = false;
					}
					this.MATERIAL_LIST.set(x, new MaterialCabinetRecipe(this.MATERIAL_LIST.get(x).getSymbol(), this.MATERIAL_LIST.get(x).getOredict(), this.MATERIAL_LIST.get(x).getName(), this.MATERIAL_LIST.get(x).getAmount(), status));
				}
			}
		}
	}

	public void initializaCabinet() {
		if(MATERIAL_LIST.isEmpty()) {
			for(MaterialCabinetRecipe recipe : recipeList()) {
				MATERIAL_LIST.add(recipe);
			}
		}
	}

	public void reloadCabinet() {
		if(MATERIAL_LIST.size() < recipeList().size()) {
			MaterialCabinetRecipe missingRecipe = null;
			for(int y = 0; y < recipeList().size(); y++) {
				boolean exists = false;
				String checkDict = getRecipeList(y).getOredict();
				String checkName = getRecipeList(y).getName();
				String checkSymbol = getRecipeList(y).getSymbol();
				int checkAmount = getRecipeList(y).getAmount();
				boolean checkExtract = getRecipeList(y).getExtraction();

				missingRecipe = new MaterialCabinetRecipe(checkSymbol, checkDict, checkName, checkAmount, checkExtract);
				
				for(int x = 0; x < MATERIAL_LIST.size(); x++) {
					if(MATERIAL_LIST.get(x).getOredict().matches(getRecipeList(y).getOredict())){
						exists= true;
					}
				}
				if(!exists) {
					this.MATERIAL_LIST.add(missingRecipe);
				}
			}
		}
	}



	//----------------------- RECIPE -----------------------
	public static ArrayList<MaterialCabinetRecipe> recipeList(){
		return MaterialCabinetRecipes.material_cabinet_recipes;
	}

	public static MaterialCabinetRecipe getRecipeList(int x){
		return recipeList().get(x);
	}



	//----------------------- STRUCTURE -----------------------
//injector
	public TEMaterialCabinetTop getInjector(){
		BlockPos injectorPos = this.pos.offset(EnumFacing.UP, 1);
		TileEntity te = this.world.getTileEntity(injectorPos);
		if(this.world.getBlockState(injectorPos) != null && te instanceof TEMaterialCabinetTop){
			TEMaterialCabinetTop injector = (TEMaterialCabinetTop)te;
			if(injector.getFacing() == getFacing()){
				return injector;
			}
		}
		return null;
	}

	public boolean hasInjector(){
		return getInjector() != null;
	}

	public boolean hasCylinder() {
		return hasInjector() 
			&& CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, getInjector().getInput().getStackInSlot(TEElementsCabinetTop.CYLINDER_SLOT));
	}

//balance
	public TEExtractorBalance getBalance(){
		TEExtractorBalance balance = TileStructure.getBalance(this.world, this.pos, isFacingAt(90));
		return balance != null ? balance : null;
	}

	public boolean hasBalance(){
		return getBalance() != null;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){

			if(hasBalance() && hasCylinder() && !this.MATERIAL_LIST.isEmpty()){
				if(getCooktime() >= ModConfig.cabinetTiming){
					String checkDict = this.MATERIAL_LIST.get(this.currentMaterial).getOredict();
					String checkName = this.MATERIAL_LIST.get(this.currentMaterial).getName();
					String checkSymbol = this.MATERIAL_LIST.get(this.currentMaterial).getSymbol();
					int checkAmount = this.MATERIAL_LIST.get(this.currentMaterial).getAmount();
					boolean checkExtract = this.MATERIAL_LIST.get(this.currentMaterial).getExtraction();

					if(OreDictionary.getOres(checkDict).size() > 0) {
						elementStack = OreDictionary.getOres(checkDict).get(0).copy();
					}

					if(checkExtract && canBalance(elementStack) && checkAmount >= getExtractingFactor()) {
						checkAmount -= getExtractingFactor();
						MaterialCabinetRecipe currentRecipe = new MaterialCabinetRecipe(checkSymbol, checkDict, checkName, checkAmount, checkExtract);
						this.MATERIAL_LIST.set(currentMaterial, currentRecipe);
						((MachineStackHandler) getBalance().getOutput()).setOrStack(OUTPUT_SLOT, elementStack);
						((MachineStackHandler) getInjector().getInput()).damageSlot(TEMaterialCabinetTop.CYLINDER_SLOT);
					}
					if(this.currentMaterial < this.MATERIAL_LIST.size() -1) {
						this.currentMaterial++;
					}else {
						this.currentMaterial = 0;
					}
					this.cooktime = 0;
				}else {
					this.cooktime++;
				}
			}
			this.markDirtyClient();
		}
	}

	public boolean canBalance(ItemStack elementStack) {
		return ((MachineStackHandler) getBalance().getOutput()).canSetOrStack(getBalance().mainSlot(), elementStack);
	}

}