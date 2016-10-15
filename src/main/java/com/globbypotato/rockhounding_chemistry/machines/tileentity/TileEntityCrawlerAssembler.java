package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import java.util.Random;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
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
    private static final int[] SLOTS_TOP = new int[] {0};
    private static final int[] SLOTS_BOTTOM = new int[] {15};
    private static final int[] SLOTS_SIDES = new int[] {16};
    Random rand = new Random();

    private int cookTime;
    private int totalCookTime;
    private int getTier;
    
    public static int assemblingSpeed;

    private String furnaceCustomName;

    private int fillerSlot = 9;
    private int absorbSlot = 10;
    private int tunnelerSlot = 11;
    private int lighterSlot = 12;
    private int railmakerSlot = 13;
    //unused setup 14
    
    private int outputSlot = 15;
    
    private int chargeSlot = 16;
    private int loaderSlot = 17;
    private int memorySlot = 18;

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
            this.totalCookTime = this.getCookTime(stack);
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
        if (index == outputSlot){
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
            case 1: return this.totalCookTime;
            default:return 0;
        }
    }

    public void setField(int id, int value){
        switch (id){
            case 0: this.cookTime = value; break;
            case 1: this.totalCookTime = value; 
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
        this.totalCookTime = compound.getInteger("CookTimeTotal");
        if (compound.hasKey("CustomName", 8)){
            this.furnaceCustomName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CookTime", this.cookTime);
        compound.setInteger("CookTimeTotal", this.totalCookTime);
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

	private boolean canAssembly() {return slots[outputSlot] == null && hasCasing() && hasLogic() && hasArms() && hasGrid() && hasCache();}
	private boolean hasArms() {	 return slots[2] != null && slots[2].getItem() == ModContents.miscItems  && slots[2].getItemDamage() == 14 && slots[2].stackSize == 12;}
	private boolean hasLogic() { return slots[1] != null && slots[1].getItem() == ModContents.miscItems  && (slots[1].getItemDamage() == 0 || slots[1].getItemDamage() == 10)  && slots[1].stackSize == 1;}
	private boolean hasCasing() {return slots[0] != null && slots[0].getItem() == ModContents.miscItems  && slots[0].getItemDamage() == 12  && slots[0].stackSize == 1;}
	private boolean hasCache() {return slots[memorySlot] != null && slots[memorySlot].getItem() == ModContents.miscItems  && slots[memorySlot].getItemDamage() == 9  && slots[memorySlot].stackSize == 1;}
	private boolean hasGrid() {
		if(slots[3] == null && 
		   slots[4] == null && 
		   slots[5] == null && 
		   slots[6] == null &&
		   hasMiningHead(7) &&		   
		   slots[8] == null ){
			getTier = 1;
		   return true;
		}else if(slots[3] == null && 
		   hasMiningHead(4) &&		   
		   slots[5] == null && 
		   slots[6] == null &&
		   hasMiningHead(7) &&		   
		   slots[8] == null ){
			getTier = 2;
		   return true;
		}else if(slots[3] == null && 
		   slots[4] == null && 
		   slots[5] == null && 
		   hasMiningHead(6) &&		   
		   hasMiningHead(7) &&		   
		   hasMiningHead(8) ){
			getTier = 3;
		   return true;
		}else if( hasMiningHead(3) &&		   
		   hasMiningHead(4) &&		   
		   hasMiningHead(5) &&		   
		   hasMiningHead(6) &&		   
		   hasMiningHead(7) &&		   
		   hasMiningHead(8) ){		 
			getTier = 4;
		   return true;
		}
		return false;
	}
	private boolean hasMiningHead(int slot) {
		return slots[slot] != null && slots[slot].getItem() == ModContents.miscItems && slots[slot].getItemDamage() == 13;
	}

	private void handleAssembler() {
		ItemStack crawlerOut = new ItemStack(ModContents.mineCrawler);
		crawlerOut.setTagCompound(new NBTTagCompound());
		crawlerOut.getTagCompound().setInteger(ModArray.tierName, getTier);
		crawlerOut.getTagCompound().setInteger(ModArray.modeName, slots[1].getItemDamage() / 10);
		crawlerOut.getTagCompound().setInteger(ModArray.stepName, TileEntityMineCrawler.numStep);
		crawlerOut.getTagCompound().setInteger(ModArray.upgradeName, 0);
		crawlerOut.getTagCompound().setBoolean(ModArray.fillerName, hasEnabler(fillerSlot));
		crawlerOut.getTagCompound().setBoolean(ModArray.absorbName, hasEnabler(absorbSlot));
		crawlerOut.getTagCompound().setBoolean(ModArray.tunnelName, hasEnabler(tunnelerSlot));
		crawlerOut.getTagCompound().setBoolean(ModArray.lighterName, hasEnabler(lighterSlot));
		crawlerOut.getTagCompound().setBoolean(ModArray.railmakerName, hasEnabler(railmakerSlot));
		if(slots[memorySlot].hasTagCompound()){
			int getcobble = crawlerOut.getTagCompound().getInteger(ModArray.cobbleName) + slots[memorySlot].getTagCompound().getInteger(ModArray.cobbleName);
			crawlerOut.getTagCompound().setInteger(ModArray.cobbleName, getcobble);
			int getglass = crawlerOut.getTagCompound().getInteger(ModArray.glassName) + slots[memorySlot].getTagCompound().getInteger(ModArray.glassName);
			crawlerOut.getTagCompound().setInteger(ModArray.glassName, getglass);
			int gettorch = crawlerOut.getTagCompound().getInteger(ModArray.torchName) + slots[memorySlot].getTagCompound().getInteger(ModArray.torchName);
			crawlerOut.getTagCompound().setInteger(ModArray.torchName, gettorch);
			int getrail = crawlerOut.getTagCompound().getInteger(ModArray.railName) + slots[memorySlot].getTagCompound().getInteger(ModArray.railName);
			crawlerOut.getTagCompound().setInteger(ModArray.railName, getrail);
		}else{
			crawlerOut.getTagCompound().setInteger(ModArray.cobbleName, 0);
			crawlerOut.getTagCompound().setInteger(ModArray.glassName, 0);
			crawlerOut.getTagCompound().setInteger(ModArray.torchName, 0);
			crawlerOut.getTagCompound().setInteger(ModArray.railName, 0);
		}
		slots[outputSlot] = crawlerOut;
		for(int x = 0; x <= 13; x++){slots[x] = null;}
		slots[memorySlot] = null;
	}

	private boolean hasEnabler(int i) {
		return slots[i] != null && slots[i].getItem() == ModContents.miscItems  && slots[i].getItemDamage() == 11  && slots[i].stackSize == 1;
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
		return  slots[1] == null && slots[2] == null && slots[3] == null && slots[4] == null && slots[5] == null && slots[6] == null && slots[memorySlot] == null
				&& slots[7] == null && slots[8] == null && slots[fillerSlot] == null && slots[absorbSlot] == null && slots[tunnelerSlot] == null && slots[lighterSlot] == null && slots[railmakerSlot] == null  && slots[outputSlot] == null
				&& (slots[0] != null && slots[0].getItem() == Item.getItemFromBlock(ModContents.mineCrawler));
	}


	private void handleDismantler() {
		slots[outputSlot] = new ItemStack(ModContents.miscItems, 1, 12);
		slots[2]  = new ItemStack(ModContents.miscItems, 12, 14);
		if(slots[0].hasTagCompound()){
			int getNbt; boolean getEnabler;
			getNbt = slots[0].getTagCompound().getInteger(ModArray.modeName);
			slots[1]  = new ItemStack(ModContents.miscItems, 1, getNbt * 10);

			getNbt = slots[0].getTagCompound().getInteger(ModArray.tierName);
			if(getNbt > 0){
				slots[7]  = new ItemStack(ModContents.miscItems, 1, 13);
			}
			if(getNbt == 2 || getNbt == 4){
				slots[4]  = new ItemStack(ModContents.miscItems, 1, 13);
			}
			if(getNbt == 3 || getNbt == 4){
				slots[6]  = new ItemStack(ModContents.miscItems, 1, 13);
				slots[8]  = new ItemStack(ModContents.miscItems, 1, 13);
			}
			if(getNbt == 4){
				slots[3]  = new ItemStack(ModContents.miscItems, 1, 13);
				slots[5]  = new ItemStack(ModContents.miscItems, 1, 13);
			}

			getEnabler = slots[0].getTagCompound().getBoolean(ModArray.fillerName);
			if(getEnabler){slots[fillerSlot] = new ItemStack(ModContents.miscItems, 1, 11);}
			getEnabler = slots[0].getTagCompound().getBoolean(ModArray.absorbName);
			if(getEnabler){slots[absorbSlot] = new ItemStack(ModContents.miscItems, 1, 11);}
			getEnabler = slots[0].getTagCompound().getBoolean(ModArray.tunnelName);
			if(getEnabler){slots[tunnelerSlot] = new ItemStack(ModContents.miscItems, 1, 11);}
			getEnabler = slots[0].getTagCompound().getBoolean(ModArray.lighterName);
			if(getEnabler){slots[lighterSlot] = new ItemStack(ModContents.miscItems, 1, 11);}
			getEnabler = slots[0].getTagCompound().getBoolean(ModArray.railmakerName);
			if(getEnabler){slots[railmakerSlot] = new ItemStack(ModContents.miscItems, 1, 11);}

			slots[memorySlot] = new ItemStack (ModContents.miscItems, 1, 9);
			slots[memorySlot].setTagCompound(new NBTTagCompound());

			getNbt = slots[0].getTagCompound().getInteger(ModArray.cobbleName);
			slots[memorySlot].getTagCompound().setInteger(ModArray.cobbleName, getNbt);
			getNbt = slots[0].getTagCompound().getInteger(ModArray.glassName);
			slots[memorySlot].getTagCompound().setInteger(ModArray.glassName, getNbt);
			getNbt = slots[0].getTagCompound().getInteger(ModArray.torchName);
			slots[memorySlot].getTagCompound().setInteger(ModArray.torchName, getNbt);
			getNbt = slots[0].getTagCompound().getInteger(ModArray.railName);
			slots[memorySlot].getTagCompound().setInteger(ModArray.railName, getNbt);
		}
		
		slots[0] = null;
	}

	private boolean canLoad() {
		return slots[chargeSlot] != null && slots[chargeSlot].getItem() == Item.getItemFromBlock(ModContents.mineCrawler);
	}
	private void loadCrawler() {
		if(slots[chargeSlot].hasTagCompound()){
			if(slots[chargeSlot].getTagCompound().hasKey(ModArray.glassName)){
				if(slots[loaderSlot] != null && slots[loaderSlot].getItem() == Item.getItemFromBlock(Blocks.GLASS)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.glassName) + slots[loaderSlot].stackSize;
					slots[chargeSlot].getTagCompound().setInteger(ModArray.glassName, getNbt);
					slots[loaderSlot] = null;
				}
			}
			if(slots[chargeSlot].getTagCompound().hasKey(ModArray.cobbleName)){
				if(slots[loaderSlot] != null && slots[loaderSlot].getItem() == Item.getItemFromBlock(Blocks.COBBLESTONE)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.cobbleName) + slots[loaderSlot].stackSize;
					slots[chargeSlot].getTagCompound().setInteger(ModArray.cobbleName, getNbt);
					slots[loaderSlot] = null;
				}
			}
			if(slots[chargeSlot].getTagCompound().hasKey(ModArray.torchName)){
				if(slots[loaderSlot] != null && slots[loaderSlot].getItem() == Item.getItemFromBlock(Blocks.TORCH)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.torchName) + slots[loaderSlot].stackSize;
					slots[chargeSlot].getTagCompound().setInteger(ModArray.torchName, getNbt);
					slots[loaderSlot] = null;
				}
			}
			if(slots[chargeSlot].getTagCompound().hasKey(ModArray.railName)){
				if(slots[loaderSlot] != null && slots[loaderSlot].getItem() == Item.getItemFromBlock(Blocks.RAIL)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.railName) + slots[loaderSlot].stackSize;
					slots[chargeSlot].getTagCompound().setInteger(ModArray.railName, getNbt);
					slots[loaderSlot] = null;
				}
			}
			if(hasLoadedCache()){
				if(slots[loaderSlot].getTagCompound().hasKey(ModArray.glassName)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.glassName) + slots[loaderSlot].getTagCompound().getInteger(ModArray.glassName);
					slots[chargeSlot].getTagCompound().setInteger(ModArray.glassName, getNbt);
					slots[loaderSlot].getTagCompound().setInteger(ModArray.glassName, 0);
				}
				if(slots[loaderSlot].getTagCompound().hasKey(ModArray.cobbleName)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.cobbleName) + slots[loaderSlot].getTagCompound().getInteger(ModArray.cobbleName);
					slots[chargeSlot].getTagCompound().setInteger(ModArray.cobbleName, getNbt);
					slots[loaderSlot].getTagCompound().setInteger(ModArray.cobbleName, 0);
				}
				if(slots[loaderSlot].getTagCompound().hasKey(ModArray.torchName)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.torchName) + slots[loaderSlot].getTagCompound().getInteger(ModArray.torchName);
					slots[chargeSlot].getTagCompound().setInteger(ModArray.torchName, getNbt);
					slots[loaderSlot].getTagCompound().setInteger(ModArray.torchName, 0);
				}
				if(slots[loaderSlot].getTagCompound().hasKey(ModArray.railName)){
					int getNbt = slots[chargeSlot].getTagCompound().getInteger(ModArray.railName) + slots[loaderSlot].getTagCompound().getInteger(ModArray.railName);
					slots[chargeSlot].getTagCompound().setInteger(ModArray.railName, getNbt);
					slots[loaderSlot].getTagCompound().setInteger(ModArray.railName, 0);
				}
			}
		}
	}

	private boolean hasLoadedCache() {
		return slots[loaderSlot] != null && slots[loaderSlot].getItem() == ModContents.miscItems && slots[loaderSlot].getItemDamage() == 9 && slots[loaderSlot].hasTagCompound();
	}


}
