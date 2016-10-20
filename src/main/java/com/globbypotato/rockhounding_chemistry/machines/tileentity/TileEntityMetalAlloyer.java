package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMetalAlloyer;

import cofh.api.energy.IEnergyReceiver;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.SlotFurnaceFuel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntityLockable;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.walkers.ItemStackDataLists;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityMetalAlloyer extends TileEntityLockable implements ITickable, ISidedInventory, IEnergyReceiver, IEnergyStorage {
    private ItemStack[] slots = new ItemStack[20];
    private static final int[] SLOTS_TOP = new int[] {1,2,3,4,5,6};
    private static final int[] SLOTS_BOTTOM = new int[] {7,8};
    private static final int[] SLOTS_SIDES = new int[] {0};
    Random rand = new Random();

    public int powerCount;
    public int powerMax = 32000;

    private int cookTime;
    private String furnaceCustomName;

    public static int alloyingSpeed;
    public int recipeCount;
    public boolean recipeScan;

	private int SLOT_FUEL = 0;
	private int SLOT_OUTPUT = 7;
	private int SLOT_SCRAP = 8;
	private int SLOT_CONSUMABLE = 9;
	private int SLOT_TEMPLATE = 10;
	private int SLOT_INDUCTOR = 19;

	private int SLOT_RECIPE_START = 11;
	private int SLOT_RECIPE_END = 16;

	public static String[] alloyNames = new String[]{"No Recipe", "CuBe", "ScAl", "BAM", "YAG", "Cupronickel", "Nimonic", "Hastelloy", "Nichrome", "Mischmetal", "Rose Gold", "Green Gold", "White Gold", "Shibuichi", "Tombak", "Pewter"};
    	public int maxAlloys = alloyNames.length - 1;
	String[] cubeRecipe = new String[]{"dustCopper", "dustBeryllium"}; 															int[] cubeQuantity = new int[]{7, 2};
	String[] scalRecipe = new String[]{"dustAluminum", "dustScandium"}; 														int[] scalQuantity = new int[]{7, 2};
	String[] bamRecipe = new String[]{"dustBoron", "dustAluminum", "dustMagnesium"}; 											int[] bamQuantity = new int[]{6, 2, 1};
	String[] yagRecipe = new String[]{"dustYttrium", "dustAluminum", "dustNeodymium", "dustChromium"}; 							int[] yagQuantity = new int[]{4, 2, 2, 1};
	String[] cupronickelRecipe = new String[]{"dustCopper", "dustNickel", "dustManganese", "dustIron"}; 						int[] cupronickelQuantity = new int[]{5, 2, 1, 1};
	String[] nimonicRecipe = new String[]{"dustNickel", "dustCobalt", "dustChromium"}; 											int[] nimonicQuantity = new int[]{5, 2, 2};
	String[] hastelloyRecipe = new String[]{"dustIron", "dustNickel", "dustChromium"}; 											int[] hastelloyQuantity = new int[]{5, 3, 1};
	String[] nichromeRecipe = new String[]{"dustNickel", "dustChromium", "dustIron",}; 											int[] nichromeQuantity = new int[]{6, 2, 1};
	private int decoSplit = 8;
	String[] mischmetalRecipe = new String[]{"dustCerium", "dustLanthanum", "dustNeodymium", "dustPraseodymium", "dustIron"}; 	int[] mischmetalQuantity = new int[]{4, 2, 1, 1, 1};
	String[] rosegoldRecipe = new String[]{"dustGold", "dustCopper", "dustSilver"}; 											int[] rosegoldQuantity = new int[]{5, 3, 1};
	String[] greengoldRecipe = new String[]{"dustGold", "dustSilver", "dustCopper", "dustCadmium"}; 							int[] greengoldQuantity = new int[]{5, 2, 1, 1};
	String[] whitegoldRecipe = new String[]{"dustGold", "dustSilver", "dustCopper", "dustManganese"}; 							int[] whitegoldQuantity = new int[]{5, 2, 1, 1};
	String[] shibuichiRecipe = new String[]{"dustCopper", "dustSilver", "dustGold"}; 											int[] shibuichiQuantity = new int[]{7, 2, 1};
	String[] tombakRecipe = new String[]{"dustCopper", "dustZinc", "dustArsenic"}; 												int[] tombakQuantity = new int[]{6, 2, 1};
	String[] pewterRecipe = new String[]{"dustTin", "dustCopper", "dustBismuth", "dustLead"}; 									int[] pewterQuantity = new int[]{5, 1, 1, 1};




    //----------------------- STANDARD -----------------------
    public int getSizeInventory(){
        return this.slots.length;
    }

    @Nullable
    public ItemStack getStackInSlot(int index){
        return this.slots[index];
    }

    @Nullable
    public ItemStack decrStackSize(int index, int count){
        return ItemStackHelper.getAndSplit(this.slots, index, count);
    }

    @Nullable
    public ItemStack removeStackFromSlot(int index){
        return ItemStackHelper.getAndRemove(this.slots, index);
    }

    public void setInventorySlotContents(int index, @Nullable ItemStack stack){
        boolean flag = stack != null && stack.isItemEqual(this.slots[index]) && ItemStack.areItemStackTagsEqual(stack, this.slots[index]);
        this.slots[index] = stack;
        if (stack != null && stack.stackSize > this.getInventoryStackLimit()){
            stack.stackSize = this.getInventoryStackLimit();
        }
        if (index == 0 && !flag){
            this.cookTime = 0;
            this.markDirty();
        }
    }

    public String getName(){
        return this.hasCustomName() ? this.furnaceCustomName : "container.metalAlloyer";
    }

    public boolean hasCustomName(){
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String p_145951_1_){
        this.furnaceCustomName = p_145951_1_;
    }

    public static void func_189676_a(DataFixer p_189676_0_){
        p_189676_0_.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("MetalAlloyer", new String[] {"Items"}));
    }

    public void clear() {
        for (int i = 0; i < this.slots.length; ++i) {
            this.slots[i] = null;
        }
    }

    public int getInventoryStackLimit(){
        return 64;
    }

    public boolean isUseableByPlayer(EntityPlayer player){
        return this.worldObj.getTileEntity(this.pos) != this ? false : player.getDistanceSq((double)this.pos.getX() + 0.5D, (double)this.pos.getY() + 0.5D, (double)this.pos.getZ() + 0.5D) <= 64.0D;
    }

    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction){
        return this.isItemValidForSlot(index, itemStackIn);
    }

    public int[] getSlotsForFace(EnumFacing side){
        return side == EnumFacing.DOWN ? SLOTS_BOTTOM : (side == EnumFacing.UP ? SLOTS_TOP : SLOTS_SIDES);
    }

    public void openInventory(EntityPlayer player){}

    public void closeInventory(EntityPlayer player){}

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, net.minecraft.util.EnumFacing facing){
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 0, tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
		super.onDataPacket(net, packet);
        readFromNBT(packet.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}


    
    //----------------------- CUSTOM -----------------------
    public boolean isItemValidForSlot(int index, ItemStack stack){
        if (index == 7 || index == 8 || (index >= 10 && index <= 18)){
            return false;
        }else if (index != 0){
            return true;
        }else{
            ItemStack itemstack = this.slots[0];
            return isItemFuel(stack) || SlotFurnaceFuel.isBucket(stack) && (itemstack == null || itemstack.getItem() != Items.BUCKET);
        }
    }

    public int getCookTime(@Nullable ItemStack stack){
        return this.machineSpeed();
    }

    public int machineSpeed(){
		return alloyingSpeed;
    }

    public String getGuiID(){
        return Reference.MODID + ":metalAlloyer";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerMetalAlloyer(playerInventory, this);
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
            case 3: this.alloyingSpeed = value;
        }
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
        if (direction == EnumFacing.DOWN && index == 0){
            Item item = stack.getItem();
            if (item != Items.WATER_BUCKET && item != Items.BUCKET){
                return false;
            }
        }
        return true;
    }

    
    
    
    //----------------------- I/O -----------------------
    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        NBTTagList nbttaglist = compound.getTagList("Items", 10);
        this.slots = new ItemStack[this.getSizeInventory()];
        for (int i = 0; i < nbttaglist.tagCount(); ++i){
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound.getByte("Slot");

            if (j >= 0 && j < this.slots.length){
                this.slots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound);
            }
        }
        this.powerCount = compound.getInteger("PowerCount");
        this.recipeCount = compound.getInteger("RecipeCount");
        this.cookTime = compound.getInteger("CookTime");
        if (compound.hasKey("CustomName", 8)){
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("RecipeCount", this.recipeCount);
        compound.setInteger("PowerCount", this.powerCount);
        compound.setInteger("CookTime", this.cookTime);
        NBTTagList nbttaglist = new NBTTagList();
        for (int i = 0; i < this.slots.length; ++i){
            if (this.slots[i] != null){
                NBTTagCompound nbttagcompound = new NBTTagCompound();
                nbttagcompound.setByte("Slot", (byte)i);
                this.slots[i].writeToNBT(nbttagcompound);
                nbttaglist.appendTag(nbttagcompound);
            }
        }
        compound.setTag("Items", nbttaglist);
        if (this.hasCustomName()){
            compound.setString("CustomName", this.furnaceCustomName);
        }
        return compound;
    }




    //----------------------- PROCESS -----------------------
    public static boolean isItemFuel(ItemStack stack){
        return getItemBurnTime(stack) > 0;
    }

    public static int getItemBurnTime(ItemStack stack){
        if (stack == null){
            return 0;
        }else{
            Item item = stack.getItem();
            if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR){
                Block block = Block.getBlockFromItem(item);
                if (block == Blocks.WOODEN_SLAB){return 150;}
                if (block.getDefaultState().getMaterial() == Material.WOOD){return 300;}
                if (block == Blocks.COAL_BLOCK){return 14400;}
            }
            if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
            if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
            if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
            if (item == Items.STICK) return 100;
            if (item == Items.COAL) return 1600;
            if (item == Items.LAVA_BUCKET) return 20000;
            if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
            if (item == Items.BLAZE_ROD) return 2400;
            return net.minecraftforge.fml.common.registry.GameRegistry.getFuelValue(stack);
        }
    }

    @Override
    public void update(){
    	if(slots[SLOT_FUEL] != null){fuelHandler();}
    	if(!worldObj.isRemote){
		//sroll alloy icons
		showAlloy(countRecipes());
		//show recipes
		if(countRecipes() > 0 && recipeScan){ showIngredients(countRecipes());}
		//force reset
		if(countRecipes() == 0 && slots[11] != null){resetGrid();}
		//cast alloys
		if(canAlloy(getAlloy(countRecipes()), getQuantity(countRecipes()))){execute(getAlloy(countRecipes()), getQuantity(countRecipes()));}
    	}
    }

	private void showAlloy(int countRecipes) {
		if(countRecipes > 0){
			if(countRecipes <= decoSplit){
				slots[SLOT_TEMPLATE] = new ItemStack(ModItems.alloyItems, 1, 1 + ((countRecipes - 1) * 3));
			}else{
				slots[SLOT_TEMPLATE] = new ItemStack(ModItems.alloyBItems, 1, 1 + ((countRecipes - decoSplit - 1) * 3));
			}
		}else{
			slots[SLOT_TEMPLATE] = null;
		}
	}

	public void showIngredients(int countRecipes) {
		countIngredients(countRecipes, getAlloy(countRecipes()), getQuantity(countRecipes()) );
	    	recipeScan = false;
	}

	public void resetGrid(){
		for(int x = SLOT_RECIPE_START; x <= SLOT_RECIPE_END; x++){ slots[x] = null; }
	}

        private void countIngredients(int countRecipes, String[] recipe, int[] quantity) {
		for(int x = 0; x < recipe.length; x++){
			for(ItemStack dust : OreDictionary.getOres(recipe[x])) {
	            		if(dust != null){
	            			slots[x + 11] = dust; slots[x + 11].stackSize = quantity[x];
	            		}
			}
		}
	}

	private String[] getAlloy(int countRecipes) {
			  if(countRecipes == 1){  return cubeRecipe;
		}else if(countRecipes == 2){  return scalRecipe;
		}else if(countRecipes == 3){  return bamRecipe;
		}else if(countRecipes == 4){  return yagRecipe;
		}else if(countRecipes == 5){  return cupronickelRecipe;
		}else if(countRecipes == 6){  return nimonicRecipe;
		}else if(countRecipes == 7){  return hastelloyRecipe;
		}else if(countRecipes == 8){  return nichromeRecipe;
		}else if(countRecipes == 9){  return mischmetalRecipe;
		}else if(countRecipes == 10){ return rosegoldRecipe;
		}else if(countRecipes == 11){ return greengoldRecipe;
		}else if(countRecipes == 12){ return whitegoldRecipe;
		}else if(countRecipes == 13){ return shibuichiRecipe;
		}else if(countRecipes == 14){ return tombakRecipe;
		}else if(countRecipes == 15){ return pewterRecipe;}
		return null;
	}

	private int[] getQuantity(int countRecipes) {
			  if(countRecipes == 1){  return cubeQuantity;
		}else if(countRecipes == 2){  return scalQuantity;
		}else if(countRecipes == 3){  return bamQuantity;
		}else if(countRecipes == 4){  return yagQuantity;
		}else if(countRecipes == 5){  return cupronickelQuantity;
		}else if(countRecipes == 6){  return nimonicQuantity;
		}else if(countRecipes == 7){  return hastelloyQuantity;
		}else if(countRecipes == 8){  return nichromeQuantity;
		}else if(countRecipes == 9){  return mischmetalQuantity;
		}else if(countRecipes == 10){ return rosegoldQuantity;
		}else if(countRecipes == 11){ return greengoldQuantity;
		}else if(countRecipes == 12){ return whitegoldQuantity;
		}else if(countRecipes == 13){ return shibuichiQuantity;
		}else if(countRecipes == 14){ return tombakQuantity;
		}else if(countRecipes == 15){ return pewterQuantity;}
		return null;
	}

	private boolean canAlloy(String[] recipe, int[] quantity) {
		return  countRecipes() > 0 &&
			isFullRecipe(recipe) &&
			hasConsumable() &&
			(slots[7] == null || (slots[7] != null && slots[SLOT_TEMPLATE] != null && slots[7].isItemEqual(slots[SLOT_TEMPLATE]) && slots[7].stackSize <= slots[7].getMaxStackSize() - 8)) &&
			powerCount >= machineSpeed();
	}

	private boolean isFullRecipe(String[] recipe) {
		int full = 0;
		for(int x = 0; x < recipe.length; x++){
			if(slots[x + 1] != null && isComparableOredict(x, x+1, recipe) && slots[x + 1].stackSize >= slots[x + 11].stackSize){
				full++;
			}
		}
		return full == recipe.length;
	}

	private boolean isComparableOredict(int x, int j, String[] recipe) {
		if(recipe[x] != null){
			int[] oreIDs = OreDictionary.getOreIDs(slots[j]);
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

	private boolean hasConsumable() {
		return slots[SLOT_CONSUMABLE] != null && slots[SLOT_CONSUMABLE].getItem() == ModItems.ingotPattern && slots[SLOT_CONSUMABLE].getItemDamage() < slots[SLOT_CONSUMABLE].getMaxDamage();
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

	public int countRecipes(){
		return recipeCount;
	}

	private void handleOutput(String[] recipe, int[] quantity) {
		int uses = 0;
		//decrease input
		for (int x = 0; x < recipe.length; x++){slots[x+1].stackSize -= quantity[x]; if(slots[x+1].stackSize <= 0) { slots[x+1] = null; }}
		//decrease consumable
		int damageItem = slots[SLOT_CONSUMABLE].getItemDamage() + 1;
		slots[SLOT_CONSUMABLE].setItemDamage(damageItem);
		if(damageItem >= slots[SLOT_CONSUMABLE].getMaxDamage()){slots[SLOT_CONSUMABLE] = null;}
		//add output
		if(slots[SLOT_OUTPUT] == null){
			slots[SLOT_OUTPUT] = slots[SLOT_TEMPLATE].copy(); slots[SLOT_OUTPUT].stackSize = 8;
		}else if(slots[SLOT_OUTPUT].isItemEqual(slots[SLOT_TEMPLATE]) && slots[SLOT_OUTPUT].stackSize < slots[SLOT_OUTPUT].getMaxStackSize() - 8){
			slots[SLOT_OUTPUT].stackSize += 8;
			if(slots[SLOT_OUTPUT].stackSize > slots[SLOT_OUTPUT].getMaxStackSize()){slots[SLOT_OUTPUT].stackSize = slots[SLOT_OUTPUT].getMaxStackSize();}
		}
		//add scrap
		ItemStack nuggetStack = new ItemStack (slots[SLOT_TEMPLATE].getItem(), 1, slots[SLOT_TEMPLATE].getItemDamage() + 1);
		int randNuggets = rand.nextInt(3) + 1;
		if(slots[SLOT_SCRAP] == null){
			slots[SLOT_SCRAP] = nuggetStack.copy(); slots[SLOT_SCRAP].stackSize = randNuggets;
		}else if(slots[SLOT_SCRAP] != null && slots[SLOT_SCRAP].isItemEqual(nuggetStack) && slots[SLOT_SCRAP].stackSize < slots[SLOT_SCRAP].getMaxStackSize() - randNuggets){
			slots[SLOT_SCRAP].stackSize += randNuggets;
			if(slots[SLOT_SCRAP].stackSize > slots[SLOT_SCRAP].getMaxStackSize()){slots[SLOT_SCRAP].stackSize = slots[SLOT_SCRAP].getMaxStackSize();}
		}
	}

	//FUELING
	private void fuelHandler() {
		if(slots[SLOT_FUEL] != null && isItemFuel(slots[SLOT_FUEL]) ){
			if( powerCount <= (powerMax - getItemBurnTime(slots[SLOT_FUEL])) ){
				burnFuel();
			}
		}
	}
	private void burnFuel() {
		powerCount += getItemBurnTime(slots[SLOT_FUEL]); slots[SLOT_FUEL].stackSize--;
		if(slots[SLOT_FUEL].stackSize <= 0){slots[SLOT_FUEL] = slots[SLOT_FUEL].getItem().getContainerItem(slots[SLOT_FUEL]);}
	}

	
	private boolean canInduct() {
		return slots[SLOT_INDUCTOR] != null && slots[SLOT_INDUCTOR].getItem() == ModItems.inductor && slots[SLOT_INDUCTOR].stackSize == 1;
	}

	
	// COFH SUPPORT
	@Override
	public int getEnergyStored(EnumFacing from) {
		if(canInduct()){
			return powerCount;
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return powerMax;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (powerCount == -1) return 0;
			received = Math.min(powerMax - powerCount, maxReceive);
			if (!simulate) {
				powerCount += received;
				if(powerCount >= powerMax){powerCount = powerMax;}
			}
		}
		return received;
	}

	
	
	// FORGE ENERGY SUPPORT
	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int received = 0;
		if(canInduct()){
			if (powerCount == -1) return 0;
			received = Math.min(powerMax - powerCount, maxReceive);
			if (!simulate) {
				powerCount += received;
				if(powerCount >= powerMax){powerCount = powerMax;}
			}
		}
		return received;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored() {
		if(canInduct()){
			return powerCount;
		}else{
			return 0;
		}
	}

	@Override
	public int getMaxEnergyStored() {
		return powerMax;
	}

	@Override
	public boolean canExtract() {
		return false;
	}

	@Override
	public boolean canReceive() {
		return true;
	}

}