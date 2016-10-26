package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumSetups;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerCrawlerAssembler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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

public class TileEntityCrawlerAssembler extends TileEntityLockable implements ITickable, ISidedInventory {
    private ItemStack[] slots = new ItemStack[19];

    private int cookTime;
    private String furnaceCustomName;
    
    public static int assemblingSpeed;
    private static int getTier;

    //main slots
    private static final int SLOT_CASING = 0;
    private static final int SLOT_MODE = 1;
    private static final int SLOT_ARMS = 2;
    
    //handling slots
    private static final int SLOT_OUTPUT = 9;
    private static final int SLOT_LOADER = 10;
    private static final int SLOT_MATERIAL = 11;

    private static final int SLOT_MEMORY = 12;

    //setup slots
    private static final int SLOT_FILLER = 13;
    private static final int SLOT_ABSORBER = 14;
    private static final int SLOT_TUNNELER = 15;
    private static final int SLOT_LIGHTER = 16;
    private static final int SLOT_RAILMAKER = 17;
    //private static final int SLOT_DECORATOR = 18;
    
    private static final int[] SLOTS_TOP = new int[] {SLOT_CASING};
    private static final int[] SLOTS_BOTTOM = new int[] {SLOT_OUTPUT};
    private static final int[] SLOTS_SIDES = new int[] {SLOT_MATERIAL};

	//parts itemstacks
	ItemStack baseLogic = crawlerPart(0);
	ItemStack crawlerMemory = crawlerPart(6);
	ItemStack advancedLogic = crawlerPart(7);
	ItemStack crawlerSetup = crawlerPart(8);
	ItemStack crawlerCasing = crawlerPart(9);
	ItemStack miningHead = crawlerPart(10);
	ItemStack supportArms = crawlerPart(11);
	ItemStack anyCrawler = new ItemStack(ModBlocks.mineCrawler);

	private static ItemStack crawlerPart(int meta){
		return new ItemStack(ModItems.miscItems, 1, meta);
	}

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
        return this.hasCustomName() ? this.furnaceCustomName : "container.crawlerAssembler";
    }

    public boolean hasCustomName(){
        return this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty();
    }

    public void setCustomInventoryName(String string){
        this.furnaceCustomName = string;
    }

    public static void func_189676_a(DataFixer fixer){
        fixer.registerWalker(FixTypes.BLOCK_ENTITY, new ItemStackDataLists("CrawlerAssembler", new String[] {"Items"}));
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

    public boolean isItemValidForSlot(int index, ItemStack stack){
        if (index == SLOT_OUTPUT){
            return false;
        }else{
            return true;
        }
    }

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
    public int getCookTime(@Nullable ItemStack stack){
        return this.machineSpeed();
    }

    public int machineSpeed(){
		return assemblingSpeed;
    	
    }

    public String getGuiID(){
        return Reference.MODID + ":crawlerAssembler";
    }

    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn){
        return new ContainerCrawlerAssembler(playerInventory, this);
    }

    public int getFieldCount() {
        return 2;
    }

    public int getField(int id){
        switch (id){
            case 0: return this.cookTime;
            case 1: return this.assemblingSpeed;
            default:return 0;
        }
    }

    public void setField(int id, int value){
        switch (id){
            case 0: this.cookTime = value; break;
            case 1: this.assemblingSpeed = value; 
        }
    }

    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction){
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
        this.cookTime = compound.getInteger("CookTime");
        if (compound.hasKey("CustomName", 8)){
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
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
    @Override
    public void update(){
    	if(!worldObj.isRemote){
	    	if(canAssembly()){assemblyCrawler();}
	    	if(canDismantle()){dismantleCrawler();}
	    	if(canLoad()){loadCrawler();}
    	}
    }


	private void assemblyCrawler() {
    	boolean flag = false;
		cookTime++;
		if(cookTime >= machineSpeed()) {
			cookTime = 0;
			handleAssembler();			
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private boolean canAssembly() {
		return slots[SLOT_OUTPUT] == null 
				&& hasCasing() 
				&& hasLogic() 
				&& hasArms() 
				&& hasGrid() 
				&& hasMemory();
	}
	
	private boolean hasArms() {	 
		return slots[SLOT_ARMS] != null 
				&& slots[SLOT_ARMS].isItemEqual(supportArms) 
				&& slots[SLOT_ARMS].stackSize == 12;
	}
	
	private boolean hasLogic() { 
		return slots[SLOT_MODE] != null 
				&& (slots[SLOT_MODE].isItemEqual(baseLogic) || slots[SLOT_MODE].isItemEqual(advancedLogic))
				&& slots[SLOT_MODE].stackSize == 1;
	}
	
	private boolean hasCasing() {
		return slots[SLOT_CASING] != null 
				&& slots[SLOT_CASING].isItemEqual(crawlerCasing) 
				&& slots[SLOT_CASING].stackSize == 1;
	}
	
	private boolean hasMemory() {
		return slots[SLOT_MEMORY] != null 
				&& slots[SLOT_MEMORY].isItemEqual(crawlerMemory)  
				&& slots[SLOT_MEMORY].stackSize == 1;
	}

	private boolean hasGrid() {
		if(slots[3] == null &&  slots[4] == null && slots[5] == null && slots[6] == null && hasMiningHead(7) && slots[8] == null ){
			getTier = 1;
		   return true;
		}else if(slots[3] == null && hasMiningHead(4) && slots[5] == null && slots[6] == null && hasMiningHead(7) && slots[8] == null ){
			getTier = 2;
		   return true;
		}else if(slots[3] == null && slots[4] == null && slots[5] == null && hasMiningHead(6) && hasMiningHead(7) && hasMiningHead(8) ){
			getTier = 3;
		   return true;
		}else if( hasMiningHead(3) && hasMiningHead(4) && hasMiningHead(5) && hasMiningHead(6) && hasMiningHead(7) && hasMiningHead(8) ){		 
			getTier = 4;
		   return true;
		}
		return false;
	}

	private boolean hasMiningHead(int slot) {
		return slots[slot] != null 
				&& slots[slot].isItemEqual(miningHead)
				&& slots[slot].stackSize == 1;
	}

	private void handleAssembler() {
		ItemStack crawlerOut = anyCrawler.copy();
		crawlerOut.setTagCompound(new NBTTagCompound());
		crawlerOut.getTagCompound().setInteger(EnumSetups.TIERS.getName(), getTier);
		crawlerOut.getTagCompound().setInteger(EnumSetups.MODES.getName(), getMode());
		crawlerOut.getTagCompound().setInteger(EnumSetups.STEP.getName(), TileEntityMineCrawler.numStep);
		crawlerOut.getTagCompound().setInteger(EnumSetups.UPGRADE.getName(), 0);
		crawlerOut.getTagCompound().setBoolean(EnumSetups.FILLER.getName(), hasSetupEnabled(SLOT_FILLER));
		crawlerOut.getTagCompound().setBoolean(EnumSetups.ABSORBER.getName(), hasSetupEnabled(SLOT_ABSORBER));
		crawlerOut.getTagCompound().setBoolean(EnumSetups.TUNNELER.getName(), hasSetupEnabled(SLOT_TUNNELER));
		crawlerOut.getTagCompound().setBoolean(EnumSetups.LIGHTER.getName(), hasSetupEnabled(SLOT_LIGHTER));
		crawlerOut.getTagCompound().setBoolean(EnumSetups.RAILMAKER.getName(), hasSetupEnabled(SLOT_RAILMAKER));
		if(slots[SLOT_MEMORY].hasTagCompound()){
			int getcobble = crawlerOut.getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName()) + slots[SLOT_MEMORY].getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName());
			crawlerOut.getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), getcobble);
			int getglass = crawlerOut.getTagCompound().getInteger(EnumSetups.GLASS.getName()) + slots[SLOT_MEMORY].getTagCompound().getInteger(EnumSetups.GLASS.getName());
			crawlerOut.getTagCompound().setInteger(EnumSetups.GLASS.getName(), getglass);
			int gettorch = crawlerOut.getTagCompound().getInteger(EnumSetups.TORCHES.getName()) + slots[SLOT_MEMORY].getTagCompound().getInteger(EnumSetups.TORCHES.getName());
			crawlerOut.getTagCompound().setInteger(EnumSetups.TORCHES.getName(), gettorch);
			int getrail = crawlerOut.getTagCompound().getInteger(EnumSetups.RAILS.getName()) + slots[SLOT_MEMORY].getTagCompound().getInteger(EnumSetups.RAILS.getName());
			crawlerOut.getTagCompound().setInteger(EnumSetups.RAILS.getName(), getrail);
		}else{
			crawlerOut.getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), 0);
			crawlerOut.getTagCompound().setInteger(EnumSetups.GLASS.getName(), 0);
			crawlerOut.getTagCompound().setInteger(EnumSetups.TORCHES.getName(), 0);
			crawlerOut.getTagCompound().setInteger(EnumSetups.RAILS.getName(), 0);
		}
		slots[SLOT_OUTPUT] = crawlerOut;
		for(int x = 0; x < slots.length; x++){
			if(x < 9 || x > 11){
				slots[x] = null;
			}
		}
	}

	private int getMode() {
		return slots[SLOT_MODE].isItemEqual(advancedLogic) ? 1 : 0;
	}

	private boolean hasSetupEnabled(int i) {
		return slots[i] != null 
				&& slots[i].isItemEqual(crawlerSetup)
				&& slots[i].stackSize == 1;
	}

	private void dismantleCrawler() {
    	boolean flag = false;
		cookTime++;
		if(cookTime >= machineSpeed()) {
			cookTime = 0;
			handleDismantler();			
			flag = true;
		}
		if(flag){this.markDirty();}
	}

	private boolean canDismantle() {
		return  slots[SLOT_MODE] == null 
				&& slots[SLOT_ARMS] == null 
				&& (slots[3] == null && slots[4] == null && slots[5] == null && slots[6] == null && slots[7] == null && slots[8] == null) 
				&& slots[SLOT_MEMORY] == null
				&& slots[SLOT_FILLER] == null 
				&& slots[SLOT_ABSORBER] == null 
				&& slots[SLOT_TUNNELER] == null 
				&& slots[SLOT_LIGHTER] == null 
				&& slots[SLOT_RAILMAKER] == null  
				&& slots[SLOT_OUTPUT] == null
				&& (slots[SLOT_CASING] != null && slots[SLOT_CASING].isItemEqual(anyCrawler));
	}

	private void handleDismantler() {
		slots[SLOT_OUTPUT] = crawlerCasing.copy();
		slots[SLOT_ARMS] = supportArms; slots[SLOT_ARMS].stackSize = 12;
		if(slots[SLOT_CASING].hasTagCompound()){
			int getNbt; boolean getEnabler;
			getNbt = slots[SLOT_CASING].getTagCompound().getInteger(EnumSetups.MODES.getName());
			slots[SLOT_MODE] = setMode(getNbt).copy();

			getNbt = slots[SLOT_CASING].getTagCompound().getInteger(EnumSetups.TIERS.getName());

			if(getNbt > 0){
				slots[7] = miningHead.copy();
			}
			if(getNbt == 2 || getNbt == 4){
				slots[4] = miningHead.copy();
			}
			if(getNbt == 3 || getNbt == 4){
				slots[6] = miningHead.copy();
				slots[8] = miningHead.copy();
			}
			if(getNbt == 4){
				slots[3] = miningHead.copy();
				slots[5] = miningHead.copy();
			}

			getEnabler = slots[SLOT_CASING].getTagCompound().getBoolean(EnumSetups.FILLER.getName());
			if(getEnabler){slots[SLOT_FILLER] = crawlerSetup.copy();}
			getEnabler = slots[SLOT_CASING].getTagCompound().getBoolean(EnumSetups.ABSORBER.getName());
			if(getEnabler){slots[SLOT_ABSORBER] = crawlerSetup.copy();}
			getEnabler = slots[SLOT_CASING].getTagCompound().getBoolean(EnumSetups.TUNNELER.getName());
			if(getEnabler){slots[SLOT_TUNNELER] = crawlerSetup.copy();}
			getEnabler = slots[SLOT_CASING].getTagCompound().getBoolean(EnumSetups.LIGHTER.getName());
			if(getEnabler){slots[SLOT_LIGHTER] = crawlerSetup.copy();}
			getEnabler = slots[SLOT_CASING].getTagCompound().getBoolean(EnumSetups.RAILMAKER.getName());
			if(getEnabler){slots[SLOT_RAILMAKER] = crawlerSetup.copy();}

			slots[SLOT_MEMORY] = crawlerMemory.copy();
			slots[SLOT_MEMORY].setTagCompound(new NBTTagCompound());
				getNbt = slots[SLOT_CASING].getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName());
				slots[SLOT_MEMORY].getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), getNbt);
				getNbt = slots[SLOT_CASING].getTagCompound().getInteger(EnumSetups.GLASS.getName());
				slots[SLOT_MEMORY].getTagCompound().setInteger(EnumSetups.GLASS.getName(), getNbt);
				getNbt = slots[SLOT_CASING].getTagCompound().getInteger(EnumSetups.TORCHES.getName());
				slots[SLOT_MEMORY].getTagCompound().setInteger(EnumSetups.TORCHES.getName(), getNbt);
				getNbt = slots[SLOT_CASING].getTagCompound().getInteger(EnumSetups.RAILS.getName());
				slots[SLOT_MEMORY].getTagCompound().setInteger(EnumSetups.RAILS.getName(), getNbt);
		}
		slots[SLOT_CASING] = null;
	}

	private ItemStack setMode(int getNbt) {
		return getNbt == 1 ? advancedLogic : baseLogic;
	}

	private boolean canLoad() {
		return slots[SLOT_LOADER] != null && (slots[SLOT_LOADER].isItemEqual(anyCrawler) || slots[SLOT_LOADER].isItemEqual(crawlerMemory)) && slots[SLOT_LOADER].stackSize == 1 && slots[SLOT_LOADER].hasTagCompound();
	}

	private void loadCrawler() {
		if(slots[SLOT_MATERIAL] != null && slots[SLOT_MATERIAL].getItem() == Item.getItemFromBlock(Blocks.GLASS)){
			int getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.GLASS.getName()) + slots[SLOT_MATERIAL].stackSize;
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.GLASS.getName(), getNbt);
			slots[SLOT_MATERIAL] = null;
		}

		if(slots[SLOT_MATERIAL] != null && slots[SLOT_MATERIAL].getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)){
			int getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName()) + slots[SLOT_MATERIAL].stackSize;
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), getNbt);
			slots[SLOT_MATERIAL] = null;
		}

		if(slots[SLOT_MATERIAL] != null && slots[SLOT_MATERIAL].getItem() == Item.getItemFromBlock(Blocks.TORCH)){
			int getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.TORCHES.getName()) + slots[SLOT_MATERIAL].stackSize;
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.TORCHES.getName(), getNbt);
			slots[SLOT_MATERIAL] = null;
		}

		if(slots[SLOT_MATERIAL] != null && slots[SLOT_MATERIAL].getItem() == Item.getItemFromBlock(Blocks.RAIL)){
			int getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.RAILS.getName()) + slots[SLOT_MATERIAL].stackSize;
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.RAILS.getName(), getNbt);
			slots[SLOT_MATERIAL] = null;
		}

		if(hasLoadedCache()){
			int getNbt = 0;
			getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.GLASS.getName()) + slots[SLOT_MATERIAL].getTagCompound().getInteger(EnumSetups.GLASS.getName());
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.GLASS.getName(), getNbt);
			slots[SLOT_MATERIAL].getTagCompound().setInteger(EnumSetups.GLASS.getName(), 0);

			getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName()) + slots[SLOT_MATERIAL].getTagCompound().getInteger(EnumSetups.COBBLESTONE.getName());
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), getNbt);
			slots[SLOT_MATERIAL].getTagCompound().setInteger(EnumSetups.COBBLESTONE.getName(), 0);

			getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.TORCHES.getName()) + slots[SLOT_MATERIAL].getTagCompound().getInteger(EnumSetups.TORCHES.getName());
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.TORCHES.getName(), getNbt);
			slots[SLOT_MATERIAL].getTagCompound().setInteger(EnumSetups.TORCHES.getName(), 0);

			getNbt = slots[SLOT_LOADER].getTagCompound().getInteger(EnumSetups.RAILS.getName()) + slots[SLOT_MATERIAL].getTagCompound().getInteger(EnumSetups.RAILS.getName());
			slots[SLOT_LOADER].getTagCompound().setInteger(EnumSetups.RAILS.getName(), getNbt);
			slots[SLOT_MATERIAL].getTagCompound().setInteger(EnumSetups.RAILS.getName(), 0);
		}
	}

	private boolean hasLoadedCache() {
		return slots[SLOT_MATERIAL] != null && slots[SLOT_MATERIAL].isItemEqual(crawlerMemory) && slots[SLOT_MATERIAL].stackSize == 1 && slots[SLOT_MATERIAL].hasTagCompound();
	}


}
