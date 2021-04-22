package com.globbypotato.rockhounding_chemistry.machines.tile;

import java.util.ArrayList;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.utils.BaseRecipes;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.oredict.OreDictionary;

public class TEElementsCabinetTop extends TileEntityInv {

	public static int inputSlots = 2;
	public static final int CYLINDER_SLOT = 1;

	public TEElementsCabinetTop() {
		super(inputSlots, 0, 0, 0);
		this.input =  new MachineStackHandler(inputSlots, this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == INPUT_SLOT && isValidOredict(insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == CYLINDER_SLOT && CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(this.input, WriteMode.IN);
	}



	//----------------------- SLOTS -----------------------
	public ItemStack inputSlot(){
		return this.input.getStackInSlot(INPUT_SLOT);
	}

	public ItemStack cylinderSlot(){
		return this.input.getStackInSlot(CYLINDER_SLOT);
	}



	//----------------------- HANDLER -----------------------
	@Override
	public int getGUIHeight() {
		return ModUtils.HEIGHT;
	}

	public static String getName(){
		return "extractor_cabinet_top";
	}



	//----------------------- STRUCTURE -----------------------
	public TEElementsCabinetBase getCollector(){
		BlockPos cabinetPos = this.pos.offset(EnumFacing.DOWN);
		TileEntity te = this.world.getTileEntity(cabinetPos);
		if(this.world.getBlockState(cabinetPos) != null && te instanceof TEElementsCabinetBase){
			TEElementsCabinetBase collector = (TEElementsCabinetBase)te;
			if(collector.getFacing() == getFacing()){
				return collector;
			}
		}
		return null;
	}

	public boolean hasCollector(){
		return getCollector() != null;
	}



	//----------------------- CUSTOM -----------------------
	public boolean isValidOredict(ItemStack insertingStack) {
		if(hasCollector()){
			if(!insertingStack.isEmpty()){
				ArrayList<Integer> inputIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(insertingStack));
				if(!inputIDs.isEmpty() && inputIDs.size() > 0){
					for(Integer input : inputIDs){
						String inputDict = OreDictionary.getOreName(input);
						for(int x = 0; x < EnumElements.size(); x++){
							if(inputDict.matches(EnumElements.getDust(x))){
								if(getCollector().elementList[x] <= (ModConfig.extractorCap - ModConfig.extractorFactor)){
									return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean hasCylinder() {
		return !cylinderSlot().isEmpty() && CoreUtils.hasConsumable(BaseRecipes.graduated_cylinder, cylinderSlot());
	}

	public void isMatchingOredict() {
		if(hasCollector() && hasCylinder()){
			if(!inputSlot().isEmpty()){
				ArrayList<Integer> inputIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(inputSlot()));
				if(!inputIDs.isEmpty() && inputIDs.size() > 0){
					for(Integer input : inputIDs){
						String inputDict = OreDictionary.getOreName(input);
						for(int x = 0; x < EnumElements.size(); x++){
							if(inputDict.matches(EnumElements.getDust(x))){
								if(getCollector().elementList[x] <= (ModConfig.extractorCap - ModConfig.extractorFactor)){
									getCollector().elementList[x] += ModConfig.extractorFactor;
	
									int unbreakingLevel = CoreUtils.getEnchantmentLevel(Enchantments.UNBREAKING, cylinderSlot());
									this.input.damageUnbreakingSlot(unbreakingLevel, CYLINDER_SLOT);
	
									if(CoreUtils.hasMending(cylinderSlot()) && this.rand.nextInt(CoreUtils.mendingFactor) == 0) {
										this.input.repairMendingSlot(CYLINDER_SLOT);
									}

									this.input.decrementSlot(INPUT_SLOT);
									
									getCollector().markDirtyClient();
								}
							}
						}
					}
				}
			}
		}
	}



	//----------------------- PROCESS -----------------------
	@Override
	public void update() {
		if(!this.world.isRemote){
			if(!this.inputSlot().isEmpty()){
				isMatchingOredict();
			}
		}
	}

}