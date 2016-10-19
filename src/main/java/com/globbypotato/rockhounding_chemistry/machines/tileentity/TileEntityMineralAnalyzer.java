package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Utils;
import com.globbypotato.rockhounding_chemistry.blocks.ModBlocks;
import com.globbypotato.rockhounding_chemistry.handlers.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModArray;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.items.ModItems;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineralAnalyzer;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMineralAnalyzer extends TileEntityInvReceiver {

	private int consumedSulf = 1;
	private int consumedChlo = 3;
	private int consumedFluo = 2;

	public static int analyzingSpeed;

	private static final int INPUT_SLOT = 0;
	//						 FUEL_SLOT = 1;
	private static final int CONSUMABLE_SLOT = 2;
	private static final int SULFUR_SLOT = 3; //Modarray[2]
	private static final int CHLOR_SLOT = 4; //+1
	private static final int FLUO_SLOT = 5; //+1
	private static final int INDUCTOR_SLOT = 6;

	private static final int OUTPUT_SLOT = 0;

	public TileEntityMineralAnalyzer() {
		super(7,1,1);

		input =  new MachineStackHandler(7,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				String fluidName = "";
				if(insertingStack.hasTagCompound()) fluidName = insertingStack.getTagCompound().getString("Fluid");

				if(slot == INPUT_SLOT && insertingStack.getMetadata() != 0 && Utils.areItemsEqualIgnoreMeta(new ItemStack(ModBlocks.mineralOres), insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 1 && Utils.isItemFuel(insertingStack)){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 2 && insertingStack.getItem() == ModItems.testTube){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 3 && fluidName.equals("Sulfuric Acid") && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 4 && fluidName.equals("Hydrochloric Acid") && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 5 && fluidName.equals("Hydrofluoric Acid") && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.chemicalItems,1,0))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == 6 && ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.miscItems,1,17))){
					return super.insertItem(slot, insertingStack, simulate);
				}
				
				return insertingStack;
			}
		};

		this.automationInput = new WrappedItemHandler(input, WrappedItemHandler.WriteMode.IN_OUT);
	}



	private boolean isOutputEmpty(){
		return output.getStackInSlot(OUTPUT_SLOT) == null;
	}

	private boolean hasConsumable(){
		return !(input.getStackInSlot(CONSUMABLE_SLOT) == null);
	}

	private boolean hasInput(){
		return !(input.getStackInSlot(INPUT_SLOT) == null);
	}

	//----------------------- CUSTOM -----------------------
	public int getCookTime(@Nullable ItemStack stack){
		return this.machineSpeed();
	}

	public int machineSpeed(){
		return analyzingSpeed;

	}


	public int getFieldCount() {
		return 4;
	}

	public int getField(int id){
		switch (id){
		case 0: return this.powerCount;
		case 1: return this.powerMax;
		case 2: return this.cookTime;
		case 3: return this.totalCookTime;
		default:return 0;
		}
	}

	public void setField(int id, int value){
		switch (id){
		case 0: this.powerCount = value; break;
		case 1: this.powerMax = value; break;
		case 2: this.cookTime = value; break;
		case 3: this.totalCookTime = value;
		}
	}


	@SideOnly(Side.CLIENT)
	public static boolean isBurning(IInventory inventory){
		return inventory.getField(2) > 0;
	}




	//----------------------- I/O -----------------------
	@Override
	public void readFromNBT(NBTTagCompound compound){
		super.readFromNBT(compound);
		this.cookTime = compound.getInteger("CookTime");
		this.totalCookTime = compound.getInteger("CookTimeTotal");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		super.writeToNBT(compound);
		compound.setInteger("CookTime", this.cookTime);
		compound.setInteger("CookTimeTotal", this.totalCookTime);
		return compound;
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update(){
		if(input.getStackInSlot(FUEL_SLOT) != null){fuelHandler();}
		if(!worldObj.isRemote){
			if(canAnalyze()){
				cookTime++; powerCount--;
				if(cookTime >= machineSpeed()) {
					cookTime = 0; 
					handleSlots();
					this.markDirty();
				}
			}
		}
	}

	private void handleSlots() {
		int quantity = 0;
		
		calculateOutput(input.getStackInSlot(INPUT_SLOT).getItemDamage());
		input.damageSlot(CONSUMABLE_SLOT);
		input.decrementSlot(INPUT_SLOT);

		//decrease acids
		quantity = input.getStackInSlot(SULFUR_SLOT).getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedSulf;
		input.getStackInSlot(SULFUR_SLOT).getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){input.getStackInSlot(SULFUR_SLOT).getTagCompound().setString(ModArray.chemTankName, EnumFluid.EMPTY.getName());}
		quantity = input.getStackInSlot(CHLOR_SLOT).getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedChlo;
		input.getStackInSlot(CHLOR_SLOT).getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){input.getStackInSlot(CHLOR_SLOT).getTagCompound().setString(ModArray.chemTankName, EnumFluid.EMPTY.getName());}
		quantity = input.getStackInSlot(FLUO_SLOT).getTagCompound().getInteger(ModArray.chemTankQuantity) - consumedFluo;
		input.getStackInSlot(FLUO_SLOT).getTagCompound().setInteger(ModArray.chemTankQuantity, quantity);
		if(quantity <= 0){input.getStackInSlot(FLUO_SLOT).getTagCompound().setString(ModArray.chemTankName, EnumFluid.EMPTY.getName());}
	}

	private void calculateOutput(int entryMineral) {
		int	mineralChance = rand.nextInt(100);
		int outputType = 0; Item outputMineral = null;

		if(entryMineral == 1){  
			outputMineral = ModItems.arsenateShards;
			if(mineralChance < 80){
				outputType = 0;
			}else{
				outputType = 1;
			}
		}else if(entryMineral == 2){   
			outputMineral = ModItems.borateShards;
			if(mineralChance < 25){
				outputType = 0;
			}else if(mineralChance >= 25 && mineralChance < 40){
				outputType = 1;
			}else if(mineralChance >= 40 && mineralChance < 55){
				outputType = 2;
			}else if(mineralChance >= 55 && mineralChance < 75){
				outputType = 3;
			}else if(mineralChance >= 75 && mineralChance < 85){
				outputType = 4;
			}else if(mineralChance >= 85){
				outputType = 5;
			}
		}else if(entryMineral == 3){   
			outputMineral = ModItems.carbonateShards;
			if(mineralChance < 55){
				outputType = 0;
			}else if(mineralChance >= 55 && mineralChance < 75){
				outputType = 1;
			}else if(mineralChance >= 75 && mineralChance < 95){
				outputType = 2;
			}else{
				outputType = 3;
			}
		}else if(entryMineral == 4){   
			outputMineral = ModItems.halideShards;
			if(mineralChance < 25){
				outputType = 0;
			}else if(mineralChance >= 25 && mineralChance < 40){
				outputType = 1;
			}else if(mineralChance >= 40 && mineralChance < 55){
				outputType = 2;
			}else if(mineralChance >= 55 && mineralChance < 70){
				outputType = 3;
			}else{
				outputType = 4;
			}
		}else if(entryMineral == 5){   
			outputMineral = ModItems.nativeShards;
			if(mineralChance < 6){
				outputType = 0;
			}else if(mineralChance >= 6 && mineralChance < 12){
				outputType = 1;
			}else if(mineralChance >= 12 && mineralChance < 22){
				outputType = 2;
			}else if(mineralChance >= 22 && mineralChance < 30){
				outputType = 3;
			}else if(mineralChance >= 30 && mineralChance < 38){
				outputType = 4;
			}else if(mineralChance >= 38 && mineralChance < 48){
				outputType = 5;
			}else if(mineralChance >= 48 && mineralChance < 56){
				outputType = 6;
			}else if(mineralChance >= 56 && mineralChance < 66){
				outputType = 7;
			}else if(mineralChance >= 66 && mineralChance < 75){
				outputType = 8;
			}else if(mineralChance >= 75 && mineralChance < 83){
				outputType = 9;
			}else if(mineralChance >= 83 && mineralChance < 92){
				outputType = 10;
			}else{
				outputType = 11;
			}
		}else if(entryMineral == 6){   
			outputMineral = ModItems.oxideShards;
			if(mineralChance < 6){
				outputType = 0;
			}else if(mineralChance >= 6 && mineralChance < 11){
				outputType = 1;
			}else if(mineralChance >= 11 && mineralChance < 16){
				outputType = 2;
			}else if(mineralChance >= 16 && mineralChance < 21){
				outputType = 3;
			}else if(mineralChance >= 21 && mineralChance < 26){
				outputType = 4;
			}else if(mineralChance >= 26 && mineralChance < 31){
				outputType = 5;
			}else if(mineralChance >= 31 && mineralChance < 36){
				outputType = 6;
			}else if(mineralChance >= 36 && mineralChance < 41){
				outputType = 7;
			}else if(mineralChance >= 41 && mineralChance < 46){
				outputType = 8;
			}else if(mineralChance >= 46 && mineralChance < 51){
				outputType = 9;
			}else if(mineralChance >= 51 && mineralChance < 56){
				outputType = 10;
			}else if(mineralChance >= 56 && mineralChance < 61){
				outputType = 11;
			}else if(mineralChance >= 61 && mineralChance < 66){
				outputType = 12;
			}else if(mineralChance >= 66 && mineralChance < 71){
				outputType = 13;
			}else if(mineralChance >= 71 && mineralChance < 76){
				outputType = 14;
			}else if(mineralChance >= 76 && mineralChance < 81){
				outputType = 15;
			}else if(mineralChance >= 81 && mineralChance < 86){
				outputType = 16;
			}else if(mineralChance >= 86 && mineralChance < 91){
				outputType = 17;
			}else if(mineralChance >= 91 && mineralChance < 95){
				outputType = 18;
			}else{
				outputType = 19;
			}
		}else if(entryMineral == 7){   
			outputMineral = ModItems.phosphateShards;
			if(mineralChance < 7){
				outputType = 0;
			}else if(mineralChance >= 7 && mineralChance < 16){
				outputType = 1;
			}else if(mineralChance >= 16 && mineralChance < 25){
				outputType = 2;
			}else if(mineralChance >= 25 && mineralChance < 34){
				outputType = 3;
			}else if(mineralChance >= 34 && mineralChance < 42){
				outputType = 4;
			}else if(mineralChance >= 42 && mineralChance < 50){
				outputType = 5;
			}else if(mineralChance >= 50 && mineralChance < 58){
				outputType = 6;
			}else if(mineralChance >= 58 && mineralChance < 67){
				outputType = 7;
			}else if(mineralChance >= 67 && mineralChance < 75){
				outputType = 8;
			}else if(mineralChance >= 75 && mineralChance < 85){
				outputType = 9;
			}else if(mineralChance >= 85 && mineralChance < 93){
				outputType = 10;
			}else{
				outputType = 11;
			}
		}else if(entryMineral == 8){   
			outputMineral = ModItems.silicateShards;
			if(mineralChance < 8){
				outputType = 0;
			}else if(mineralChance >= 8 && mineralChance < 16){
				outputType = 1;
			}else if(mineralChance >= 16 && mineralChance < 23){
				outputType = 2;
			}else if(mineralChance >= 23 && mineralChance < 31){
				outputType = 3;
			}else if(mineralChance >= 31 && mineralChance < 39){
				outputType = 4;
			}else if(mineralChance >= 39 && mineralChance < 47){
				outputType = 5;
			}else if(mineralChance >= 47 && mineralChance < 55){
				outputType = 6;
			}else if(mineralChance >= 55 && mineralChance < 66){
				outputType = 7;
			}else if(mineralChance >= 66 && mineralChance < 75){
				outputType = 8;
			}else if(mineralChance >= 75 && mineralChance < 83){
				outputType = 9;
			}else if(mineralChance >= 83 && mineralChance < 91){
				outputType = 10;
			}else{
				outputType = 11;
			}
		}else if(entryMineral == 9){   
			outputMineral = ModItems.sulfateShards;
			if(mineralChance < 11){
				outputType = 0;
			}else if(mineralChance >= 11 && mineralChance < 22){
				outputType = 1;
			}else if(mineralChance >= 22 && mineralChance < 33){
				outputType = 2;
			}else if(mineralChance >= 33 && mineralChance < 44){
				outputType = 3;
			}else if(mineralChance >= 44 && mineralChance < 55){
				outputType = 4;
			}else if(mineralChance >= 55 && mineralChance < 68){
				outputType = 5;
			}else if(mineralChance >= 68 && mineralChance < 80){
				outputType = 6;
			}else if(mineralChance >= 80 && mineralChance < 91){
				outputType = 7;
			}else{
				outputType = 8;
			}
		}else if(entryMineral == 10){   
			outputMineral = ModItems.sulfideShards;
			if(mineralChance < 3){
				outputType = 0;
			}else if(mineralChance >= 3 && mineralChance < 9){
				outputType = 1;
			}else if(mineralChance >= 9 && mineralChance < 15){
				outputType = 2;
			}else if(mineralChance >= 15 && mineralChance < 21){
				outputType = 3;
			}else if(mineralChance >= 21 && mineralChance < 26){
				outputType = 4;
			}else if(mineralChance >= 26 && mineralChance < 33){
				outputType = 5;
			}else if(mineralChance >= 33 && mineralChance < 40){
				outputType = 6;
			}else if(mineralChance >= 40 && mineralChance < 46){
				outputType = 7;
			}else if(mineralChance >= 46 && mineralChance < 52){
				outputType = 8;
			}else if(mineralChance >= 52 && mineralChance < 59){
				outputType = 9;
			}else if(mineralChance >= 59 && mineralChance < 65){
				outputType = 10;
			}else if(mineralChance >= 65 && mineralChance < 71){
				outputType = 11;
			}else if(mineralChance >= 71 && mineralChance < 77){
				outputType = 12;
			}else if(mineralChance >= 77 && mineralChance < 83){
				outputType = 13;
			}else if(mineralChance >= 83 && mineralChance < 89){
				outputType = 14;
			}else if(mineralChance >= 89 && mineralChance < 94){
				outputType = 15;
			}else{
				outputType = 16;
			}
		}	
		handleOutput(outputMineral, outputType);
	}

	private void handleOutput(Item mineral, int meta) {
		ItemStack outputStack = new ItemStack(mineral, rand.nextInt(ModConfig.maxMineral) + 1, meta);
		if(isOutputEmpty()){
			output.setStackInSlot(OUTPUT_SLOT, outputStack.copy());
		}else if(output.getStackInSlot(OUTPUT_SLOT).isItemEqual(outputStack) && output.getStackInSlot(OUTPUT_SLOT).stackSize < output.getStackInSlot(OUTPUT_SLOT).getMaxStackSize()){
			//slots[outputSlot].stackSize += outPutStack.stackSize;
			//if(slots[outputSlot].stackSize > slots[outputSlot].getMaxStackSize()){slots[outputSlot].stackSize = output.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();}
		}
	}

	private boolean canAnalyze() {
		return  (output.getStackInSlot(OUTPUT_SLOT)== null
				&& hasInput()
				&& hasConsumable()
				&& powerCount >= machineSpeed()
				&& hasAcid(SULFUR_SLOT, EnumFluid.SULFURIC_ACID, consumedSulf)
				&& hasAcid(CHLOR_SLOT, EnumFluid.HYDROCHLORIC_ACID, consumedChlo)
				&& hasAcid(FLUO_SLOT, EnumFluid.HYDROFLUORIC_ACID, consumedFluo));
	}

	private boolean hasTank(int acidSlot) {
		return input.getStackInSlot(acidSlot) != null && input.getStackInSlot(acidSlot).getItem() == ModItems.chemicalItems && input.getStackInSlot(acidSlot).getItemDamage() == 0;
	}
	private boolean hasAcid(int acidSlot, EnumFluid acidType, int acidConsumed) {
		if(hasTank(acidSlot)){
			if(input.getStackInSlot(acidSlot).hasTagCompound()){
				if(input.getStackInSlot(acidSlot).getTagCompound().getString(ModArray.chemTankName).matches(acidType.getName())){
					if(input.getStackInSlot(acidSlot).getTagCompound().getInteger(ModArray.chemTankQuantity) >= acidConsumed){
						return true;
					}
				}
			}
		}
		return false;
	}


	@Override
	protected boolean canInduct(){
		return !(input.getStackInSlot(INDUCTOR_SLOT) == null);
	}

	@Override
	public int getGUIHeight() {
		return GuiMineralAnalyzer.HEIGHT;
	}
}