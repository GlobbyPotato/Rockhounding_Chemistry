package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Utils;
import com.globbypotato.rockhounding_chemistry.handlers.Enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiChemicalExtractor;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityChemicalExtractor extends TileEntityInvRFReceiver {
	
	public int[] elementList = new int[56];

	private int consumedSyng = 1;
	private int consumedFluo = 2;

	public static int extractingSpeed;
	public static int extractingFactor;
	private int redstoneCharge = extractingSpeed;

	private static final int INPUT_SLOT = 0;
	private static final int FUEL_SLOT = 1;
	private static final int REDSTONE_SLOT = 2;
	private static final int CONSUMABLE_SLOT = 3;
	private static final int SYNGAS_SLOT = 4;
	private static final int FLUO_SLOT = 5;
	ItemStack inductor = new ItemStack(ModItems.miscItems,1,17);

	public TileEntityChemicalExtractor() {
		super(6,56,1);

		input = new MachineStackHandler(6,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && hasRecipe(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FUEL_SLOT && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == REDSTONE_SLOT &&
						(insertingStack.getItem() == Items.REDSTONE || (insertingStack.isItemEqual(inductor)))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CONSUMABLE_SLOT &&
						(insertingStack.getItem()==ModItems.testTube || insertingStack.getItem() == ModItems.cylinder)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SYNGAS_SLOT && hasFluid(insertingStack,EnumFluid.SYNGAS)) {
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == FLUO_SLOT && hasFluid(insertingStack,EnumFluid.HYDROFLUORIC_ACID)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		automationInput = new WrappedItemHandler(input, WriteMode.IN_OUT);
	}

	
	//----------------------- CUSTOM -----------------------

	public boolean hasFluid(ItemStack stack, EnumFluid fluid){
		if(stack.hasTagCompound()){
			if(stack.getTagCompound().getString("Fluid").equals(fluid.getName())){
				return true;
			}
		}
		return false;
	}
	@Override
	public int getGUIHeight() {
		return GuiChemicalExtractor.HEIGHT;
	}

	public int getCookTime(@Nullable ItemStack stack){
		return this.machineSpeed();
	}

	public int machineSpeed(){
		return extractingSpeed;

	}

	public int getFieldCount() {
		return 62;
	}

	public int getField(int id){
		if(id == 0){		return this.powerCount;
		}else if(id == 1){  return this.powerMax;
		}else if(id == 2){  return this.cookTime;
		}else if(id == 3){  return this.extractingSpeed;
		}else if(id == 4){  return this.redstoneCount;
		}else if(id == 5){  return this.redstoneMax;
		}else if(id >= 6 && id <= 61){ return this.elementList[id-6];
		}else{return 0;}
	}

	public void setField(int id, int value){
		if(id == 0){	    this.powerCount = value;
		}else if(id == 1){  this.powerMax = value;
		}else if(id == 2){  this.cookTime = value;
		}else if(id == 3){  this.extractingSpeed = value;
		}else if(id == 4){  this.redstoneCount = value;
		}else if(id == 5){  this.redstoneMax = value;
		}else if(id >= 6 && id <= 61){  elementList[id-6] = value;
		}
	}

	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory){
		return inventory.getField(2) > 0;
	}

	public boolean hasFuel(){
		return input.getStackInSlot(FUEL_SLOT) != null;
	}

	public boolean hasRedstone(){
		return input.getStackInSlot(REDSTONE_SLOT) != null;
	}

	public boolean hasCylinder() {
		return Utils.areItemsEqualIgnoreMeta(input.getStackInSlot(CONSUMABLE_SLOT), new ItemStack(ModItems.cylinder));
	}

	public boolean hasTestTube(){
		return Utils.areItemsEqualIgnoreMeta(input.getStackInSlot(CONSUMABLE_SLOT), new ItemStack(ModItems.testTube));
	}

	public int getInputDamage(){
		return input.getStackInSlot(INPUT_SLOT).getItemDamage();
	}

	public Item getInputItem(){
		return input.getStackInSlot(INPUT_SLOT).getItem();
	}
	

	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		for(int i = 0; i < elementList.length; i++){
			elementList[i] = compound.getInteger("element" + i);
		}
		this.cookTime = compound.getInteger("CookTime");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		for(int i = 0; i < elementList.length; i++){
			compound.setInteger("element" + i, elementList[i]);
		}
		compound.setInteger("CookTime", this.cookTime);
		return compound;
	}



	//----------------------- PROCESS -----------------------

	@Override
	public void update(){
		if(hasFuel()){fuelHandler();}
		if(hasRedstone()){redstoneHandler();}
		if(!worldObj.isRemote){
			if(canExtractElements()){
				execute();
			}
			if(hasCylinder()){transferDust();}
		}
	}

	private void transferDust() {
		for(int i=0;i<output.getSlots();i++){
			if(elementList[i] >= extractingFactor){
				elementList[i]-= extractingFactor;
				output.setOrIncrement(i, new ItemStack(ModItems.chemicalDusts,1,i));
				input.damageSlot(CONSUMABLE_SLOT);
				this.markDirty();
			}
		}
	}



	private void execute() {
		cookTime++;
		powerCount--;
		redstoneCount -= 2;
		if(cookTime >= machineSpeed()) {
			cookTime = 0; 
			extractElements();
			input.decrementSlot(INPUT_SLOT);
			input.damageSlot(CONSUMABLE_SLOT);
			input.decrementFluid(SYNGAS_SLOT,consumedSyng);
			input.decrementFluid(FLUO_SLOT,consumedFluo);
			this.markDirty();
		}
	}

	private void redstoneHandler() {
		if(input.getStackInSlot(REDSTONE_SLOT) != null && input.getStackInSlot(REDSTONE_SLOT).getItem() == Items.REDSTONE && redstoneCount <= (redstoneMax - redstoneCharge)){
			redstoneCount += redstoneCharge;
			input.decrementSlot(REDSTONE_SLOT);
		}
	}

	@Override
	protected boolean canInduct() {
		return redstoneCount >= redstoneMax 
				&& ItemStack.areItemsEqual(inductor, input.getStackInSlot(REDSTONE_SLOT));
	}


	private boolean canExtractElements() {
		return  hasRecipe(input.getStackInSlot(INPUT_SLOT))
				&& hasTestTube()
				&& powerCount >= machineSpeed()
				&& redstoneCount >= machineSpeed()
				&& hasEnoughAcid(SYNGAS_SLOT, EnumFluid.SYNGAS, consumedSyng) && hasEnoughAcid(FLUO_SLOT, EnumFluid.HYDROFLUORIC_ACID, consumedFluo);
	}

	private boolean hasRecipe(ItemStack stack) {
		return  stack != null &&
				(stack.getItem() == ModItems.arsenateShards ||
				stack.getItem()  == ModItems.borateShards ||
				stack.getItem()  == ModItems.carbonateShards ||
				stack.getItem()  == ModItems.halideShards ||
				stack.getItem()  == ModItems.nativeShards ||
				stack.getItem()  == ModItems.oxideShards ||
				stack.getItem()  == ModItems.phosphateShards ||
				stack.getItem()  == ModItems.silicateShards ||
				stack.getItem()  == ModItems.sulfateShards ||
				stack.getItem()  == ModItems.sulfideShards);
	}

	private boolean hasTank(int slot) {
		return ItemStack.areItemsEqual(input.getStackInSlot(slot), new ItemStack(ModItems.chemicalItems));
	}

	private boolean hasEnoughAcid(int acidSlot, EnumFluid acidType, int acidConsumed) {
		if(hasTank(acidSlot)){
			if(input.getStackInSlot(acidSlot).hasTagCompound()){
				if(input.getStackInSlot(acidSlot).getTagCompound().getString("Fluid").matches(acidType.getName())){
					if(input.getStackInSlot(acidSlot).getTagCompound().getInteger("Quantity") >= acidConsumed){
						return true;
					}
				}
			}
		}
		return false;
	}



	private void extractElements() {
		if(input.getStackInSlot(INPUT_SLOT).getItem() == ModItems.arsenateShards){
			if(getInputDamage() == 0){
				elementList[17] += 36;
				elementList[41] += 22;
				elementList[19] += 6;
				elementList[1]  += 5;
				elementList[15] += 4;
				elementList[0]  += 4;
				elementList[6]  += 3;
				elementList[8]  += 3;
				elementList[32] += 2;
				elementList[3]  += 1;
				elementList[4]  += 1;
				elementList[10] += 1;
				elementList[42] += 1;
			}else if(getInputDamage() == 1){
				elementList[19] += 55;
				elementList[41] += 10;
				elementList[17] += 9;
				elementList[21] += 7;
			}
		}else if(getInputItem() == ModItems.borateShards){
			if(getInputDamage() == 0){
				elementList[30] += 12;
				elementList[22] += 11;
			}else if(getInputDamage() == 1){
				elementList[16] += 22;
				elementList[22] += 17;
				elementList[28] += 5;
				elementList[25] += 4;
			}else if(getInputDamage() == 2){
				elementList[16] += 46;
				elementList[28] += 10;
				elementList[22] += 5;
				elementList[18] += 5;
			}else if(getInputDamage() == 3){
				elementList[22] += 15;
				elementList[24] += 13;
				elementList[36] += 6;
				elementList[35] += 2;
				elementList[34] += 1;
				elementList[16] += 1;
			}else if(getInputDamage() == 4){
				elementList[18] += 40;
				elementList[25] += 19;
				elementList[22] += 8;
			}else if(getInputDamage() == 5){
				elementList[24] += 14;
				elementList[22] += 13;
				elementList[36] += 8;
				elementList[35] += 4;
			}
		}else if(getInputItem() == ModItems.carbonateShards){
			if(getInputDamage() == 0){
				elementList[32] += 19;
				elementList[16] += 16;
				elementList[28] += 4;
				elementList[25] += 3;
			}else if(getInputDamage() == 1){
				elementList[26] += 33;
				elementList[28] += 7;
				elementList[16] += 5;
			}else if(getInputDamage() == 2){
				elementList[17] += 43;
				elementList[20] += 15;
			}else if(getInputDamage() == 3){
				elementList[8]  += 23;
				elementList[6]  += 21;
				elementList[0]  += 19;
				elementList[42] += 7;
			}
		}else if(getInputItem() == ModItems.halideShards){
			if(getInputDamage() == 0){
				elementList[19] += 49;
				elementList[17] += 14;
				elementList[23] += 9;
				elementList[35] += 1;
			}else if(getInputDamage() == 1){
				elementList[35] += 14;
				elementList[28] += 9;
			}else if(getInputDamage() == 2){
				elementList[35] += 28;
				elementList[16] += 14;
				elementList[30] += 6;
			}else if(getInputDamage() == 3){
				elementList[34] += 27;
			}
		}else if(getInputItem() == ModItems.nativeShards){
			if(getInputDamage() == 0){
				elementList[16] += 55;
				elementList[26] += 29;
				elementList[27] += 10;
			}else if(getInputDamage() == 1){
				elementList[17] += 100;
			}else if(getInputDamage() == 2){
				elementList[17] += 60;
				elementList[24] += 26;
				elementList[20] += 7;
				elementList[16] += 7;
			}else if(getInputDamage() == 3){
				elementList[16] += 82;
				elementList[26] += 13;
			}else if(getInputDamage() == 4){
				elementList[26] += 64;
				elementList[16] += 20;
				elementList[42] += 11;
				elementList[33] += 4;
			}else if(getInputDamage() == 5){
				elementList[23] += 100;
			}else if(getInputDamage() == 6){
				elementList[44] += 76;
				elementList[17] += 13;
				elementList[16] += 11;
			}else if(getInputDamage() == 7){
				elementList[44] += 62;
				elementList[18] += 38;
			}else if(getInputDamage() == 8){
				elementList[45] += 65;
				elementList[38] += 35;
			}else if(getInputDamage() == 9){
				elementList[45] += 51;
				elementList[17] += 49;
			}else if(getInputDamage() == 10){
				elementList[47] += 75;
				elementList[48] += 25;
			}else if(getInputDamage() == 11){
				elementList[48] += 52;
				elementList[47] += 31;
				elementList[44] += 11;
			}
		}else if(getInputItem() == ModItems.oxideShards){
			if(getInputDamage() == 0){
				elementList[21] += 47;
				elementList[16] += 25;
			}else if(getInputDamage() == 1){
				elementList[21] += 35;
				elementList[27] += 14;
				elementList[16] += 8;
				elementList[26] += 6;
				elementList[24] += 5;
				elementList[28] += 1;
			}else if(getInputDamage() == 2){
				elementList[39] += 42;
				elementList[40] += 24;
				elementList[16] += 8;
				elementList[25] += 7;
				elementList[28] += 5;
				elementList[29] += 2;
				elementList[24] += 1;
			}else if(getInputDamage() == 3){
				elementList[39] += 33;
				elementList[15] += 16;
				elementList[0]  += 3;
				elementList[32] += 2;
				elementList[29] += 2;
			}else if(getInputDamage() == 4){
				elementList[17] += 43;
				elementList[21] += 35;
			}else if(getInputDamage() == 5){
				elementList[39] += 24;
				elementList[40] += 12;
				elementList[31] += 10;
				elementList[43] += 10;
				elementList[14] += 5;
				elementList[16] += 4;
				elementList[15] += 4;
				elementList[7]  += 4;
				elementList[13] += 4;
				elementList[5]  += 4;
				elementList[1]  += 4;
				elementList[2]  += 4;
				elementList[3]  += 4;
				elementList[12] += 4;
				elementList[32] += 2;
			}else if(getInputDamage() == 6){
				elementList[18] += 79;
			}else if(getInputDamage() == 7){
				elementList[24] += 45;
			}else if(getInputDamage() == 8){
				elementList[20] += 36;
				elementList[24] += 30;
			}else if(getInputDamage() == 9){
				elementList[24] += 40;
				elementList[16] += 6;
				elementList[32] += 5;
				elementList[29] += 3;
				elementList[6]  += 2;
				elementList[0]  += 2;
				elementList[28] += 1;
			}else if(getInputDamage() == 10){
				elementList[29] += 25;
				elementList[16] += 21;
				elementList[25] += 12;
				elementList[19] += 11;
			}else if(getInputDamage() == 11){
				elementList[29] += 25;
				elementList[31] += 24;
				elementList[43] += 24;
				elementList[32] += 2;
			}else if(getInputDamage() == 12){
				elementList[40] += 56;
				elementList[18] += 7;
				elementList[25] += 7;
				elementList[39] += 6;
				elementList[16] += 3;
			}else if(getInputDamage() == 13){
				elementList[40] += 66;
				elementList[25] += 6;
				elementList[16] += 6;
				elementList[39] += 6;
			}else if(getInputDamage() == 14){
				elementList[36] += 21;
			}else if(getInputDamage() == 15){
				elementList[36] += 36;
			}else if(getInputDamage() == 16){
				elementList[46] += 75;
			}else if(getInputDamage() == 17){
				elementList[46] += 61;
				elementList[16] += 9;
				elementList[25] += 9;
			}else if(getInputDamage() == 18){
				elementList[46] += 61;
				elementList[16] += 18;
			}else if(getInputDamage() == 19){
				elementList[49] += 88;
			}
		}else if(getInputItem() == ModItems.phosphateShards){
			if(getInputDamage() == 0){
				elementList[24] += 20;
				elementList[33] += 15;
				elementList[20] += 6;
				elementList[17] += 2;
			}else if(getInputDamage() == 1){
				elementList[33] += 20;
				elementList[24] += 18;
				elementList[28] += 8;
			}else if(getInputDamage() == 2){
				elementList[8]  += 21;
				elementList[6]  += 18;
				elementList[0]  += 16;
				elementList[4]  += 14;
				elementList[10] += 13;
				elementList[33] += 11;
				elementList[31] += 7;
			}else if(getInputDamage() == 3){
				elementList[16] += 22;
				elementList[33] += 12;
				elementList[20] += 8;
				elementList[25] += 7;
			}else if(getInputDamage() == 4){
				elementList[16] += 35;
				elementList[33] += 20;
				elementList[34] += 5;
			}else if(getInputDamage() == 5){
				elementList[24] += 20;
				elementList[33] += 15;
			}else if(getInputDamage() == 6){
				elementList[14] += 48;
				elementList[15] += 38;
				elementList[33] += 14;
			}else if(getInputDamage() == 7){
				elementList[38] += 32;
				elementList[16] += 20;
				elementList[33] += 10;
				elementList[24] += 3;
			}else if(getInputDamage() == 8){
				elementList[11] += 32;
				elementList[33] += 22;
			}else if(getInputDamage() == 9){
				elementList[16] += 32;
				elementList[33] += 18;
				elementList[34] += 4;
			}else if(getInputDamage() == 10){
				elementList[41] += 29;
				elementList[17] += 15;
				elementList[20] += 14;
				elementList[49] += 13;
				elementList[25] += 1;
				elementList[32] += 1;
			}else if(getInputDamage() == 11){
				elementList[49] += 32;
				elementList[17] += 17;
				elementList[33] += 9;
				elementList[20] += 1;
			}
		}else if(getInputItem() == ModItems.silicateShards){
			if(getInputDamage() == 0){
				elementList[42] += 20;
				elementList[32] += 14;
				elementList[24] += 10;
				elementList[25] += 10;
				elementList[16] += 10;
				elementList[28] += 5;
				elementList[22] += 2;
			}else if(getInputDamage() == 1){
				elementList[42] += 19;
				elementList[28] += 14;
				elementList[35] += 10;
				elementList[24] += 6;
				elementList[16] += 6;
			}else if(getInputDamage() == 2){
				elementList[15] += 22;
				elementList[16] += 10;
				elementList[42] += 10;
				elementList[6]  += 6;
				elementList[0]  += 6;
				elementList[9]  += 6;
				elementList[10] += 6;
				elementList[36] += 4;
				elementList[3]  += 3;
				elementList[5]  += 3;
				elementList[7]  += 3;
				elementList[12] += 3;
				elementList[13] += 3;
				elementList[22] += 1;
			}else if(getInputDamage() == 3){
				elementList[19] += 68;
				elementList[21] += 10;
				elementList[17] += 2;
				elementList[42] += 2;
			}else if(getInputDamage() == 4){
				elementList[42] += 25;
				elementList[11] += 12;
				elementList[30] += 6;
				elementList[32] += 5;
				elementList[16] += 5;
				elementList[28] += 3;
			}else if(getInputDamage() == 5){
				elementList[42] += 18;
				elementList[16] += 12;
				elementList[28] += 10;
				elementList[35] += 4;
				elementList[11] += 2;
				elementList[24] += 2;
			}else if(getInputDamage() == 6){
				elementList[42] += 28;
				elementList[15] += 11;
				elementList[29] += 7;
				elementList[35] += 6;
				elementList[1]  += 3;
				elementList[4]  += 1;
				elementList[5]  += 1;
				elementList[1]  += 1;
				elementList[12] += 1;
				elementList[10] += 1;
			}else if(getInputDamage() == 7){
				elementList[42] += 22;
				elementList[24] += 22;
				elementList[34] += 6;
			}else if(getInputDamage() == 8){
				elementList[42] += 27;
				elementList[31] += 25;
				elementList[32] += 4;
				elementList[35] += 2;
				elementList[30] += 2;
				elementList[25] += 1;
			}else if(getInputDamage() == 9){
				elementList[24] += 26;
				elementList[42] += 11;
				elementList[34] += 3;
				elementList[22] += 2;
			}else if(getInputDamage() == 10){
				elementList[25] += 29;
				elementList[18] += 16;
				elementList[44] += 15;
				elementList[22] += 3;
			}else if(getInputDamage() == 11){ //WTF
				elementList[42] += 14;
				elementList[0]  += 12;
				elementList[25] += 9;
				elementList[24] += 5;
				elementList[32] += 5;
				elementList[3]  += 4; 
				elementList[7]  += 4;
				elementList[13] += 4;
				elementList[12] += 4;
				elementList[28] += 2;
				elementList[29] += 1;
				elementList[21] += 1;
				elementList[16] += 1;
			}
		}else if(getInputItem() == ModItems.sulfateShards){
			if(getInputDamage() == 0){
				elementList[24] += 20;
				elementList[37] += 15;
				elementList[35] += 10;
			}else if(getInputDamage() == 1){
				elementList[17] += 33;
				elementList[37] += 17;
				elementList[35] += 14;
			}else if(getInputDamage() == 2){
				elementList[16] += 33;
				elementList[37] += 13;
				elementList[35] += 8;
			}else if(getInputDamage() == 3){
				elementList[20] += 34;
				elementList[27] += 8;
				elementList[26] += 8;
				elementList[37] += 4;
			}else if(getInputDamage() == 4){
				elementList[32] += 19;
				elementList[37] += 7;
				elementList[21] += 6;
				elementList[24] += 1;
			}else if(getInputDamage() == 5){
				elementList[27] += 16;
				elementList[37] += 14;
				elementList[25] += 7;
				elementList[26] += 3;
			}else if(getInputDamage() == 6){
				elementList[27] += 21;
				elementList[37] += 11;
			}else if(getInputDamage() == 7){
				elementList[46] += 64;
				elementList[32] += 14;
			}else if(getInputDamage() == 8){
				elementList[19] += 46;
				elementList[46] += 40;
			}
		}else if(getInputItem() == ModItems.sulfideShards){
			if(getInputDamage() == 0){
				elementList[19] += 37;
				elementList[37] += 21;
				elementList[38] += 17;
				elementList[18] += 12;
			}else if(getInputDamage() == 1){
				elementList[38] += 36;
				elementList[19] += 36;
				elementList[37] += 17;
				elementList[17] += 11;
			}else if(getInputDamage() == 2){
				elementList[38] += 36;
				elementList[23] += 35;
				elementList[37] += 16;
			}else if(getInputDamage() == 3){
				elementList[19] += 88;
				elementList[37] += 12;
			}else if(getInputDamage() == 4){
				elementList[18] += 32;
				elementList[17] += 27;
				elementList[37] += 27;
				elementList[20] += 10;
				elementList[16] += 3;
			}else if(getInputDamage() == 5){
				elementList[26] += 37;
				elementList[16] += 33;
				elementList[37] += 33;
			}else if(getInputDamage() == 6){
				elementList[37] += 53;
				elementList[16] += 47;
			}else if(getInputDamage() == 7){
				elementList[37] += 30;
				elementList[17] += 30;
				elementList[18] += 28;
				elementList[16] += 10;
				elementList[20] += 2;
			}else if(getInputDamage() == 8){
				elementList[16] += 26;
				elementList[37] += 26;
				elementList[17] += 24;
				elementList[28] += 9;
				elementList[24] += 7;
			}else if(getInputDamage() == 9){
				elementList[20] += 64;
				elementList[37] += 33;
				elementList[16] += 3;
			}else if(getInputDamage() == 10){
				elementList[37] += 34;
				elementList[18] += 25;
				elementList[17] += 20;
				elementList[16] += 9;
				elementList[20] += 4;
				elementList[23] += 3;
			}else if(getInputDamage() == 11){
				elementList[17] += 44;
				elementList[37] += 29;
				elementList[18] += 14;
				elementList[16] += 13;
			}else if(getInputDamage() == 12){
				elementList[46] += 74;
				elementList[37] += 26;
			}else if(getInputDamage() == 13){
				elementList[47] += 75;
				elementList[37] += 25;
			}else if(getInputDamage() == 14){
				elementList[44] += 50;
				elementList[37] += 22;
				elementList[48] += 17;
				elementList[17] += 11;
			}else if(getInputDamage() == 15){
				elementList[49] += 78;
				elementList[37] += 22;
			}else if(getInputDamage() == 16){
				elementList[37] += 27;
				elementList[17] += 26;
				elementList[18] += 24;
				elementList[49] += 23;
			}
		}
	}
}