package com.globbypotato.rockhounding_chemistry.blocks;

import java.util.Random;

import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyB;
import com.globbypotato.rockhounding_core.blocks.BaseMetaBlock;
import com.globbypotato.rockhounding_core.blocks.itemblocks.BaseMetaIB;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AlloyBBricks extends BlockIO {
	public static final PropertyEnum VARIANT = PropertyEnum.create("type", EnumAlloyB.class);
	Random rand = new Random();

    public AlloyBBricks(Material material, String[] array, float hardness, float resistance, String name, SoundType stepSound){
        super(material, array, hardness, resistance, name, stepSound);
		GameRegistry.register(new BaseMetaIB(this, EnumAlloyB.getAlloys()).setRegistryName(name));
		setHarvestLevel("pickaxe", 1);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloyB.values()[0]));
    }

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumAlloyB.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumAlloyB)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity){
        IBlockState state = world.getBlockState(pos);
		if(state.getBlock().getMetaFromState(state) == 0){
			EntityLivingBase player = (EntityLivingBase) entity;
			if(player!= null){
				if(player.getItemStackFromSlot(EntityEquipmentSlot.FEET) != null) {
		            double d0 = (double)pos.getX() + rand.nextDouble();
		            double d1 = (double)pos.getY() + 1D;
		            double d2 = (double)pos.getZ() + rand.nextDouble();
		            world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
    }

}