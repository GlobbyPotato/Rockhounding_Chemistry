package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Utils;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMetalAlloyer;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMetalAlloyer extends TileEntityInvReceiver {

	public int recipeDisplayIndex;
    public static int alloyingSpeed;
    public boolean recipeScan;

	//		SLOT_FUEL = 0;
    public static final int SLOT_INPUTS[] = new int[]{0,1,2,3,4,5,6};
    public static final int SLOT_CONSUMABLE = 7;
    public static final int SLOT_INDUCTOR = 8;
	ItemStack inductor = new ItemStack(ModItems.miscItems,1,17);

	public static final int SLOT_OUTPUT = 0;
	public static final int SLOT_SCRAP = 1;

	private ItemStackHandler template = new TemplateStackHandler(7);
	public static int SLOT_FAKE[] = new int[]{0,1,2,3,4,5,6};

	public static String[] alloyNames = new String[]{"CuBe", "ScAl", "BAM", "YAG", "Cupronickel", "Nimonic", "Hastelloy", "Nichrome", "Mischmetal", "Rose Gold", "Green Gold", "White Gold", "Shibuichi", "Tombak", "Pewter", "Corten Steel"};
	public int maxAlloys = alloyNames.length;
	String[] cubeRecipe = new String[]{"dustCopper", "dustBeryllium"}; 																	int[] cubeQuantity = new int[]{7, 2};
	String[] scalRecipe = new String[]{"dustAluminum", "dustScandium"}; 																int[] scalQuantity = new int[]{7, 2};
	String[] bamRecipe = new String[]{"dustBoron", "dustAluminum", "dustMagnesium"}; 													int[] bamQuantity = new int[]{6, 2, 1};
	String[] yagRecipe = new String[]{"dustYttrium", "dustAluminum", "dustNeodymium", "dustChromium"}; 									int[] yagQuantity = new int[]{4, 2, 2, 1};
	String[] cupronickelRecipe = new String[]{"dustCopper", "dustNickel", "dustManganese", "dustIron"}; 								int[] cupronickelQuantity = new int[]{5, 2, 1, 1};
	String[] nimonicRecipe = new String[]{"dustNickel", "dustCobalt", "dustChromium"}; 													int[] nimonicQuantity = new int[]{5, 2, 2};
	String[] hastelloyRecipe = new String[]{"dustIron", "dustNickel", "dustChromium"}; 													int[] hastelloyQuantity = new int[]{5, 3, 1};
	String[] nichromeRecipe = new String[]{"dustNickel", "dustChromium", "dustIron",}; 													int[] nichromeQuantity = new int[]{6, 2, 1};
	private int decoSplit = 7;
	String[] mischmetalRecipe = new String[]{"dustCerium", "dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustIron"}; 			int[] mischmetalQuantity = new int[]{4, 2, 1, 1, 1};
	String[] rosegoldRecipe = new String[]{"dustGold", "dustCopper", "dustSilver"}; 													int[] rosegoldQuantity = new int[]{5, 3, 1};
	String[] greengoldRecipe = new String[]{"dustGold", "dustSilver", "dustCopper", "dustCadmium"}; 									int[] greengoldQuantity = new int[]{5, 2, 1, 1};
	String[] whitegoldRecipe = new String[]{"dustGold", "dustSilver", "dustCopper", "dustManganese"}; 									int[] whitegoldQuantity = new int[]{5, 2, 1, 1};
	String[] shibuichiRecipe = new String[]{"dustCopper", "dustSilver", "dustGold"}; 													int[] shibuichiQuantity = new int[]{7, 2, 1};
	String[] tombakRecipe = new String[]{"dustCopper", "dustZinc", "dustArsenic"}; 														int[] tombakQuantity = new int[]{6, 2, 1};
	String[] pewterRecipe = new String[]{"dustTin", "dustCopper", "dustBismuth", "dustLead"}; 											int[] pewterQuantity = new int[]{5, 1, 1, 1};
	String[] cortenRecipe = new String[]{"dustNickel", "dustSilicon", "dustChromium", "dustPhosphorus", "dustManganese", "dustCopper"}; int[] cortenQuantity = new int[]{2, 2, 2, 1, 1, 1};

	public TileEntityMetalAlloyer() {
		super(11, 2, 0);
		this.recipeDisplayIndex = -1;
		
		input =  new MachineStackHandler(INPUT_SLOTS,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot >= SLOT_INPUTS[1] && slot < SLOT_INPUTS.length && isMatchingOredict(insertingStack, slot)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_CONSUMABLE && isValidConsumable(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_INDUCTOR && ItemStack.areItemsEqual(insertingStack, inductor)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input,WriteMode.IN_OUT);
	}
	
	private boolean isValidConsumable(ItemStack insertingStack) {
		return Utils.areItemsEqualIgnoreMeta(new ItemStack(ModItems.ingotPattern), insertingStack);
	}

	public boolean isMatchingOredict(ItemStack stack, int slot) {
		if(stack != null){
			int[] oreIDs = OreDictionary.getOreIDs(stack);
			if(oreIDs.length > 0) {
				for(int i = 0; i < oreIDs.length; i++) {
					if(oreIDs[i] > -1) {
						String oreName = OreDictionary.getOreName(oreIDs[i]);
						if(template.getStackInSlot(slot) != null){
							int[] tempIDs = OreDictionary.getOreIDs(template.getStackInSlot(slot));
							if(tempIDs.length > 0) {
								for(int j = 0; j < tempIDs.length; j++) {
									if(tempIDs[j] > -1) {
										String tempName = OreDictionary.getOreName(tempIDs[j]);
										if(oreName != null && tempName != null && oreName.matches(tempName)){
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	public ItemStackHandler getTemplate(){
		return this.template;
	}

	public int getCookTime(@Nullable ItemStack stack){
		return this.machineSpeed();
	}

	public int machineSpeed(){
		return alloyingSpeed;
	}

	public int getFieldCount() {
		return 4;
	}

	public int getField(int id){
		switch (id){
			case 0: return this.powerCount;
			case 1: return this.powerMax;
			case 2: return this.cookTime;
			case 3: return this.alloyingSpeed;
			default:return 0;
		}
	}

	public void setField(int id, int value){
		switch (id){
			case 0: this.powerCount = value; break;
			case 1: this.powerMax = value; break;
			case 2: this.cookTime = value; break;
			case 3: this.alloyingSpeed = value; break;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.recipeDisplayIndex = compound.getInteger("RecipeCount");
		this.cookTime = compound.getInteger("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("RecipeCount", this.recipeDisplayIndex);
		compound.setInteger("CookTime", this.cookTime);
		return compound;
	}

	@Override
	public void update(){
		if(input.getStackInSlot(FUEL_SLOT) != null){
			fuelHandler();
		}
		if(!worldObj.isRemote){
			//show allor
			showAlloy(countRecipes());
			if((countRecipes() >= 0 && recipeScan)
				|| (!recipeScan && countRecipes() >= 0 && template.getStackInSlot(SLOT_FAKE[0]) != null && template.getStackInSlot(SLOT_FAKE[1]) == null) ){ 
				showIngredients(countRecipes());
			}
			//reset grid
			if(countRecipes() < 0 && template.getStackInSlot(SLOT_FAKE[0]) != null){
				resetGrid();
			}
			//cast alloy
			if(canAlloy(getAlloy(countRecipes()), getQuantity(countRecipes()))){
				execute(getAlloy(countRecipes()), getQuantity(countRecipes()));
			}
		}
	}

	private void execute(String[] recipe, int[] quantity) {
		boolean flag = false;
		cookTime++; powerCount--;
		if(cookTime >= machineSpeed()) {
			cookTime = 0; 
			handleOutput(recipe, quantity);
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private void handleOutput(String[] recipe, int[] quantity) {
		int uses = 0;
		//decrease input 
		for (int x = 0; x < recipe.length; x++){input.getStackInSlot(x + 1).stackSize -= quantity[x]; if(input.getStackInSlot(x + 1).stackSize <= 0) { input.setStackInSlot(x + 1, null); }}
		//decrease consumable
		input.damageSlot(SLOT_CONSUMABLE);
		//add output
		if(output.getStackInSlot(SLOT_OUTPUT) == null){
			output.setStackInSlot(SLOT_OUTPUT, template.getStackInSlot(SLOT_FAKE[0]).copy()); output.getStackInSlot(SLOT_OUTPUT).stackSize = 8;
		}else if(output.getStackInSlot(SLOT_OUTPUT).isItemEqual(template.getStackInSlot(SLOT_FAKE[0])) && output.getStackInSlot(SLOT_OUTPUT).stackSize <= output.getStackInSlot(SLOT_OUTPUT).getMaxStackSize() - 8){
			output.getStackInSlot(SLOT_OUTPUT).stackSize += 8;
			if(output.getStackInSlot(SLOT_OUTPUT).stackSize > output.getStackInSlot(SLOT_OUTPUT).getMaxStackSize()){output.getStackInSlot(SLOT_OUTPUT).stackSize = output.getStackInSlot(SLOT_OUTPUT).getMaxStackSize();}
		}
		//add scrap
		ItemStack nuggetStack = new ItemStack (output.getStackInSlot(SLOT_OUTPUT).getItem(), 1, template.getStackInSlot(SLOT_FAKE[0]).getItemDamage() + 1);
		int randNuggets = rand.nextInt(3) + 1;
		if(output.getStackInSlot(SLOT_SCRAP) == null){
			output.setStackInSlot(SLOT_SCRAP, nuggetStack.copy()); output.getStackInSlot(SLOT_SCRAP).stackSize = randNuggets;
		}else if(output.getStackInSlot(SLOT_SCRAP) != null && output.getStackInSlot(SLOT_SCRAP).isItemEqual(nuggetStack) && output.getStackInSlot(SLOT_SCRAP).stackSize < output.getStackInSlot(SLOT_SCRAP).getMaxStackSize() - randNuggets){
			output.getStackInSlot(SLOT_SCRAP).stackSize += randNuggets;
			if(output.getStackInSlot(SLOT_SCRAP).stackSize > output.getStackInSlot(SLOT_SCRAP).getMaxStackSize()){output.getStackInSlot(SLOT_SCRAP).stackSize = output.getStackInSlot(SLOT_SCRAP).getMaxStackSize();}
		}
	}

	private boolean canAlloy(String[] recipe, int[] quantity) {
		return  countRecipes() >= 0 &&
				isFullRecipe(recipe) &&
				hasConsumable() &&
				(output.getStackInSlot(SLOT_OUTPUT) == null 
				|| (output.getStackInSlot(SLOT_OUTPUT) != null && template.getStackInSlot(SLOT_FAKE[0]) != null && output.getStackInSlot(SLOT_OUTPUT).isItemEqual(template.getStackInSlot(SLOT_FAKE[0])) 
				&& output.getStackInSlot(SLOT_OUTPUT).stackSize <= output.getStackInSlot(SLOT_OUTPUT).getMaxStackSize() - 8))
				&& powerCount >= machineSpeed();
	}

	private boolean isFullRecipe(String[] recipe) {
		int full = 0;
		for(int x = 0; x < recipe.length; x++){
			if(template.getStackInSlot(x + 1) != null && input.getStackInSlot(x + 1) != null
				&& isComparableOredict(x, x+1, recipe) 
				&& input.getStackInSlot(x + 1).stackSize >= template.getStackInSlot(x + 1).stackSize){
				full++;
			}
		}
		return full == recipe.length;
	}

	private boolean isComparableOredict(int x, int j, String[] recipe) {
		if(recipe[x] != null){
			int[] oreIDs = OreDictionary.getOreIDs(template.getStackInSlot(j));
			if(oreIDs.length > 0) {
				for(int i = 0; i < oreIDs.length; i++) {
					if(oreIDs[i] > -1) {
						String oreName = OreDictionary.getOreName(oreIDs[i]);
						if(oreName != null && oreName.matches(recipe[x])){
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	public int countRecipes(){
		return recipeDisplayIndex;
	}

	private void showAlloy(int countRecipes) {
		if(countRecipes >= 0){
			if(countRecipes <= decoSplit){
				template.setStackInSlot(SLOT_FAKE[0], new ItemStack(ModItems.alloyItems, 1, 1 + (countRecipes * 3)));
			}else{
				template.setStackInSlot(SLOT_FAKE[0], new ItemStack(ModItems.alloyBItems, 1, 1 + ((countRecipes - decoSplit - 1) * 3)));
			}
		}else{
			template.setStackInSlot(SLOT_FAKE[0], null);
		}
	}

	public void showIngredients(int countRecipes) {
		countIngredients(countRecipes, getAlloy(countRecipes()), getQuantity(countRecipes()) );
	    recipeScan = false;
	}

    private void countIngredients(int countRecipes, String[] recipe, int[] quantity) {
		for(int x = 0; x < recipe.length; x++){
			for(ItemStack dust : OreDictionary.getOres(recipe[x])) {
	    		if(dust != null){
	    			template.setStackInSlot(x + 1, dust);
	    			template.getStackInSlot(x + 1).stackSize = quantity[x];
	    		}
			}
		}
	}

	private String[] getAlloy(int countRecipes) {
			  if(countRecipes == 0){  return cubeRecipe;
		}else if(countRecipes == 1){  return scalRecipe;
		}else if(countRecipes == 2){  return bamRecipe;
		}else if(countRecipes == 3){  return yagRecipe;
		}else if(countRecipes == 4){  return cupronickelRecipe;
		}else if(countRecipes == 5){  return nimonicRecipe;
		}else if(countRecipes == 6){  return hastelloyRecipe;
		}else if(countRecipes == 7){  return nichromeRecipe;
		}else if(countRecipes == 8){  return mischmetalRecipe;
		}else if(countRecipes == 9){  return rosegoldRecipe;
		}else if(countRecipes == 10){ return greengoldRecipe;
		}else if(countRecipes == 11){ return whitegoldRecipe;
		}else if(countRecipes == 12){ return shibuichiRecipe;
		}else if(countRecipes == 13){ return tombakRecipe;
		}else if(countRecipes == 14){ return pewterRecipe;
		}else if(countRecipes == 15){ return cortenRecipe;}
		return null;
	}
	
	private int[] getQuantity(int countRecipes) {
			  if(countRecipes == 0){  return cubeQuantity;
		}else if(countRecipes == 1){  return scalQuantity;
		}else if(countRecipes == 2){  return bamQuantity;
		}else if(countRecipes == 3){  return yagQuantity;
		}else if(countRecipes == 4){  return cupronickelQuantity;
		}else if(countRecipes == 5){  return nimonicQuantity;
		}else if(countRecipes == 6){  return hastelloyQuantity;
		}else if(countRecipes == 7){  return nichromeQuantity;
		}else if(countRecipes == 8){  return mischmetalQuantity;
		}else if(countRecipes == 9){  return rosegoldQuantity;
		}else if(countRecipes == 10){ return greengoldQuantity;
		}else if(countRecipes == 11){ return whitegoldQuantity;
		}else if(countRecipes == 12){ return shibuichiQuantity;
		}else if(countRecipes == 13){ return tombakQuantity;
		}else if(countRecipes == 14){ return pewterQuantity;
		}else if(countRecipes == 15){ return cortenQuantity;}
		return null;
	}

	public void resetGrid(){
		for(int x = 1; x < SLOT_FAKE.length; x++){ template.setStackInSlot(x, null);}
	}

	private boolean hasConsumable() {
		return input.getStackInSlot(SLOT_CONSUMABLE) != null && isValidConsumable(input.getStackInSlot(SLOT_CONSUMABLE));
	}

	@Override
	public int getGUIHeight() {
		return GuiMetalAlloyer.HEIGHT;
	}

	public int currentRecipeIndex(){
		return recipeDisplayIndex;
	}

	@Override
	protected boolean canInduct(){
		return !(input.getStackInSlot(SLOT_INDUCTOR) == null);
	}
}
