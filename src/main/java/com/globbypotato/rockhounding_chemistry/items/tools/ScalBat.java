package com.globbypotato.rockhounding_chemistry.items.tools;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import com.globbypotato.rockhounding_chemistry.ModContents;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.google.common.collect.Multimap;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ScalBat extends ItemSword {
    private final Item.ToolMaterial material;

	public ScalBat(ToolMaterial material, String name) {
		super(material);
		setRegistryName(name);
		setUnlocalizedName(getRegistryName().toString());
        this.maxStackSize = 1;
		this.setCreativeTab(Reference.RockhoundingChemistry);
        this.material = material;
	}

    public float getDamageVsEntity(){
        return this.material.getDamageVsEntity();
    }

    @Override
    public float getStrVsBlock(ItemStack stack, IBlockState state){
        Block block = state.getBlock();
        if (block == Blocks.MELON_BLOCK || block == Blocks.PUMPKIN || block == Blocks.LIT_PUMPKIN){
            return 15.0F;
        }else{
            Material material = state.getMaterial();
            return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
        stack.damageItem(1, attacker);
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving){
        if ((double)state.getBlockHardness(worldIn, pos) != 0.0D){
            stack.damageItem(1, entityLiving);
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState blockIn){
        return blockIn.getBlock() == Blocks.MELON_BLOCK || blockIn.getBlock() == Blocks.PUMPKIN || blockIn.getBlock() == Blocks.LIT_PUMPKIN ;
    }

    @SideOnly(Side.CLIENT)
    public boolean isFull3D(){
        return true;
    }

    @Override
    public int getItemEnchantability(){
        return this.material.getEnchantability();
    }

    @Override
    public String getToolMaterialName(){
        return this.material.toString();
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair){
        ItemStack mat = new ItemStack(ModContents.alloyItems, 1, 4);
        if (mat != null && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false)) return true;
        return super.getIsRepairable(toRepair, repair);
    }

    
    /**
     * the following 2 methods are taken from @Choonster from #minecraftforum
     */

	@Override
	public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
		final Multimap<String, AttributeModifier> modifiers = super.getAttributeModifiers(slot, stack);

		if (slot == EntityEquipmentSlot.MAINHAND) {
			replaceModifier(modifiers, SharedMonsterAttributes.ATTACK_SPEED, ATTACK_SPEED_MODIFIER, 0);
		}
		return modifiers;
	}

	private void replaceModifier(Multimap<String, AttributeModifier> modifierMultimap, IAttribute attribute, UUID id, double multiplier) {
		// Get the modifiers for the specified attribute
		final Collection<AttributeModifier> modifiers = modifierMultimap.get(attribute.getAttributeUnlocalizedName());

		// Find the modifier with the specified ID, if any
		final Optional<AttributeModifier> modifierOptional = modifiers.stream().filter(attributeModifier -> attributeModifier.getID().equals(id)).findFirst();

		if (modifierOptional.isPresent()) { // If it exists,
			final AttributeModifier modifier = modifierOptional.get();
			modifiers.remove(modifier); // Remove it
			modifiers.add(new AttributeModifier(modifier.getID(), modifier.getName(), modifier.getAmount() * multiplier, modifier.getOperation())); // Add the new modifier
		}
	}

}
