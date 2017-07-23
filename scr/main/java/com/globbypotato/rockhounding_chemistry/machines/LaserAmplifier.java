package com.globbypotato.rockhounding_chemistry.machines;

import javax.annotation.Nullable;

import com.globbypotato.rockhounding_chemistry.Rhchemistry;
import com.globbypotato.rockhounding_chemistry.handlers.GuiHandler;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityLaserAmplifier;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LaserAmplifier extends BaseLaser {

    public LaserAmplifier(float hardness, float resistance, String name){
        super(hardness, resistance, name, Material.IRON, SoundType.METAL);
    }

    @Override
    public AxisAlignedBB getBox(){
    	return new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.72D, 1.0D);
    }
    
	@Override
	public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileEntityLaserAmplifier();
    }

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, @Nullable ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ){
		if (!world.isRemote) {
			player.openGui(Rhchemistry.instance, GuiHandler.laserAmplifierID, world, pos.getX(), pos.getY(), pos.getZ());
		}
		return true;
	}

}