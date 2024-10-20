package com.globbypotato.rockhounding_chemistry.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.materials.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_core.enums.EnumFluidNbt;
import com.globbypotato.rockhounding_core.utils.CoreUtils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.Loader;

public class ModUtils {
	public static final int HEIGHT = 200;

	public static String immersive_id = "immersiveengineering";
//	public static String rh_rocks_id = "rockhounding_rocks";
	public static String mekanism_id = "mekanism";
	public static String forestry_id = "forestry";
	public static String thermal_f_id = "thermalfoundation";
	public static String actually_id = "actuallyadditions";
	public static String openblocks_id = "openblocks";
	public static String gcplanet_id = "galacticraftplanets";
	public static String wawla_id = "wawla";
	public static String enderio_id = "enderio";

	public static String creosote = "creosote";
	public static String liquidXP = "xpjuice";
	public static String forestry_biomass = "biomass";

	public static String mek_liquid_oxygen = "liquidoxygen";
	public static String mek_brine = "brine";
	public static String mek_steam = "steam";
	public static String mek_sulfuric_acid = "sulfuricacid";
	public static String gc_sulphuric_acid = "sulphuricacid";


//Mod Internal
	public static boolean isValidSpeedUpgrade(ItemStack insertingStack) {
		return insertingStack != null && insertingStack.getItem() == ModItems.SPEED_ITEMS && insertingStack.getItemDamage() > 0;
	}

	public static int speedUpgrade(ItemStack insertingStack) {
		return isValidSpeedUpgrade(insertingStack) ? insertingStack.getItemDamage() + 1 : 1;
	}

	public static boolean isValidFilterUpgrade(ItemStack insertingStack) {
		return insertingStack != null && insertingStack.getItem() == ModItems.FILTER_ITEMS && insertingStack.getItemDamage() > 0;
	}

	public static int filterUpgrade(ItemStack insertingStack) {
		return isValidFilterUpgrade(insertingStack) ? insertingStack.getItemDamage() : 0;
	}

	public static float stepDivision(int meta){
		if (meta == 1){
			return 1.0F;
		}else if (meta == 2){
			return 0.8F;
		}else if (meta == 3){
			return 0.6F;
		}else if (meta == 4){
			return 0.4F;
		}else if (meta == 5){
			return 0.2F;
		}
		return 1.0F;
	}

	public static boolean hasWrench(EntityPlayer player) {
		return !player.getHeldItemMainhand().isEmpty() 
			&& player.getHeldItemMainhand().isItemEqual(BaseRecipes.chemistry_wrench);
	}

	public static boolean hasInductor(ItemStack insertingStack) {
		return !insertingStack.isEmpty() 
			&& (ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.HEAT_INDUCTOR)));
	}

	public static boolean hasTurbine(ItemStack insertingStack) {
		return !insertingStack.isEmpty() 
			&& (ItemStack.areItemsEqual(insertingStack, new ItemStack(ModItems.GAS_TURBINE)));
	}

	public static boolean isOrbiterProbe(ItemStack insertingStack) {
		return insertingStack != null && insertingStack.getItem() == ModItems.PROBE_ITEMS;
	}

	public static void setFluidFilter(ItemStack heldItem, NBTTagCompound gas) {
		heldItem.getTagCompound().setTag(EnumFluidNbt.FLUID.nameTag(), gas);
		if(heldItem.getTagCompound().hasKey(EnumFluidNbt.GAS.nameTag())){
			heldItem.getTagCompound().removeTag(EnumFluidNbt.GAS.nameTag());
		}
	}

	public static void setGasFilter(ItemStack heldItem, NBTTagCompound gas) {
		heldItem.getTagCompound().setTag(EnumFluidNbt.GAS.nameTag(), gas);
		if(heldItem.getTagCompound().hasKey(EnumFluidNbt.FLUID.nameTag())){
			heldItem.getTagCompound().removeTag(EnumFluidNbt.FLUID.nameTag());
		}
	}

	public static ItemStack handleFilterStack(ItemStack heldItem) {
		ItemStack filtersample = ItemStack.EMPTY;
		if(!heldItem.isEmpty()){
			filtersample = new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage());
			return filtersample;
		}
		return ItemStack.EMPTY;
	}

	public static FluidStack handleAmpoule(ItemStack heldItem, boolean acceptFluid, boolean acceptGas) {
		FluidStack filterSample = null;
		if(!heldItem.isEmpty() && heldItem.isItemEqual(BaseRecipes.sampling_ampoule)){
			if(acceptFluid){
				if(!heldItem.hasTagCompound()){heldItem.setTagCompound(new NBTTagCompound());}
				if(heldItem.hasTagCompound()){
					NBTTagCompound tag = heldItem.getTagCompound();
					if(tag.hasKey(EnumFluidNbt.FLUID.nameTag())){
						filterSample = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(EnumFluidNbt.FLUID.nameTag()));
						if(filterSample != null && filterSample.getFluid() != null && !filterSample.getFluid().isGaseous()){
							return filterSample;
						}
					}
				}
			}
			if(acceptGas){
				if(!heldItem.hasTagCompound()){heldItem.setTagCompound(new NBTTagCompound());}
				if(heldItem.hasTagCompound()){
					NBTTagCompound tag = heldItem.getTagCompound();
					if(tag.hasKey(EnumFluidNbt.GAS.nameTag())){
						filterSample = FluidStack.loadFluidStackFromNBT(tag.getCompoundTag(EnumFluidNbt.GAS.nameTag()));
						if(filterSample != null && filterSample.getFluid() != null && filterSample.getFluid().isGaseous()){
							return filterSample;
						}
					}
				}
			}
		}
		return null;
	}

//Mod Integration
	public static boolean hasMekanism() {
		return Loader.isModLoaded(mekanism_id);
	}

	public static boolean hasImmersiveEng() {
		return Loader.isModLoaded(immersive_id);
	}

	public static boolean hasOpenBlocks() {
		return Loader.isModLoaded(openblocks_id);
	}

	public static boolean hasEnderIO() {
		return Loader.isModLoaded(enderio_id);
	}

	public static boolean hasForestry() {
		return Loader.isModLoaded(forestry_id);
	}
/*
	public static boolean hasRocks() {
		return Loader.isModLoaded(rh_rocks_id);
	}

	public static boolean hasRhRocksIntegtation() {
		return hasRocks() && ModConfig.hasRhRocks;
	}
*/
	public static boolean hasTFoundation() {
		return Loader.isModLoaded(thermal_f_id);
	}

	public static boolean hasActuallyAdd() {
		return Loader.isModLoaded(actually_id);
	}

	public static boolean hasGCPlanets() {
		return Loader.isModLoaded(gcplanet_id);
	}

	public static boolean hasWawla() {
		return Loader.isModLoaded(wawla_id);
	}

	public static FluidStack creosoteFix() {
		if(hasImmersiveEng()){
			if(CoreUtils.fluidExists(creosote)){
				return CoreUtils.getFluid(creosote, 50);
			}
		}
		return BaseRecipes.getFluid(EnumFluid.TOXIC_WASTE, 50);
	}

	public static FluidStack liquidXP() {
		if(hasOpenBlocks() || hasEnderIO()){
			if(CoreUtils.fluidExists(liquidXP)){
				return CoreUtils.getFluid(liquidXP, 1000);
			}
		}
		return null;
	}

	public static ItemStack mekanism_biofuel(){
		if(hasMekanism()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(mekanism_id + ":" + "biofuel"));
			ItemStack temp = CoreUtils.getModdedStack(material, 1, 0);
			if(!temp.isEmpty()){
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack forestry_compost(){
		if(hasForestry()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(forestry_id + ":" + "fertilizer_bio"));
			ItemStack temp = CoreUtils.getModdedStack(material, 1, 0);
			if(!temp.isEmpty()){
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}
/*
	public static Item rhrocks_a(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_a"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_b(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_b"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_c(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_c"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_d(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_d"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_e(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_e"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_f(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_f"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_g(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_g"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
	public static Item rhrocks_h(){
		if(hasRocks()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(rh_rocks_id + ":" + "rocks_h"));
			if(material != null){
				return material;
			}
		}
		return null;
	}
*/
	public static ItemStack forestry_charcoal(){
		if(hasForestry()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(forestry_id + ":" + "charcoal"));
			ItemStack temp = CoreUtils.getModdedStack(material, 1, 0);
			if(!temp.isEmpty()){
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack thermal_f_tar(){
		if(hasTFoundation()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(thermal_f_id + ":" + "material"));
			ItemStack temp = CoreUtils.getModdedStack(material, 1, 833);
			if(!temp.isEmpty()){
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack thermal_f_wrench(){
		if(hasTFoundation()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(thermal_f_id + ":" + "wrench"));
			ItemStack temp = CoreUtils.getModdedStack(material, 1, 0);
			if(!temp.isEmpty()){
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}

	public static ItemStack actually_biomass(){
		if(hasActuallyAdd()){
			Item material = Item.REGISTRY.getObject(new ResourceLocation(actually_id + ":" + "item_misc"));
			ItemStack temp = CoreUtils.getModdedStack(material, 1, 21);
			if(!temp.isEmpty()){
				return temp;
			}
		}
		return ItemStack.EMPTY;
	}

	public static int substanceCoeff(Fluid fluid) {
		return fluid != null ? (fluid.getDensity() + fluid.getViscosity()) / 2 : 0;
	}

	public static double kinematicCoeff(Fluid fluid) {
		return fluid != null ? (double)fluid.getViscosity() / (double)fluid.getDensity() : 0;
	}

	public static String doubleTranslate(double amount) {
	    DecimalFormat decForm = new DecimalFormat("0.00", new DecimalFormatSymbols());
	    decForm.setRoundingMode(RoundingMode.CEILING);
		return decForm.format(amount);		
	}

	public static boolean isLightFluid(Fluid fluid) {
		return ModUtils.substanceCoeff(fluid) <= ModConfig.tankSubstance
			&& ModUtils.kinematicCoeff(fluid) <= ModConfig.kinematicPar;
	}

	public static boolean isHeavyFluid(Fluid fluid) {
		return ModUtils.substanceCoeff(fluid) > ModConfig.tankSubstance
			|| ModUtils.kinematicCoeff(fluid) > ModConfig.kinematicPar;
	}

}