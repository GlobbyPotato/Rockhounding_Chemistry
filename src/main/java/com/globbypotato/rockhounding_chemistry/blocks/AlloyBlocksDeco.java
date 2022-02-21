package com.globbypotato.rockhounding_chemistry.blocks;

import com.globbypotato.rockhounding_chemistry.blocks.io.MetaIO;
import com.globbypotato.rockhounding_chemistry.enums.EnumAlloyDeco;

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

public class AlloyBlocksDeco extends MetaIO {

	public static final PropertyEnum VARIANT = PropertyEnum.create("variant", EnumAlloyDeco.class);

	public AlloyBlocksDeco(String name) {
		super(name, Material.IRON, EnumAlloyDeco.getAlloys(), 2.0F, 5.0F, SoundType.METAL);
		this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumAlloyDeco.values()[0]));
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return getDefaultState().withProperty(VARIANT, EnumAlloyDeco.values()[meta]);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumAlloyDeco)state.getValue(VARIANT)).ordinal();
	}

	@Override
	public BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { VARIANT });
	}

	@Override
    public void onEntityWalk(World world, BlockPos pos, Entity entity){
        IBlockState state = world.getBlockState(pos);
		if(state.getBlock().getMetaFromState(state) == EnumAlloyDeco.MISCHMETAL.ordinal()){
			EntityLivingBase player = (EntityLivingBase) entity;
			if(player!= null){
				if(!player.getItemStackFromSlot(EntityEquipmentSlot.FEET).isEmpty()) {
		            double d0 = (double)pos.getX() + world.rand.nextDouble();
		            double d1 = (double)pos.getY() + 1D;
		            double d2 = (double)pos.getZ() + world.rand.nextDouble();
		            world.spawnParticle(EnumParticleTypes.FLAME, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
				}
			}
		}
    }

}