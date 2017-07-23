package com.globbypotato.rockhounding_chemistry.fluids;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

//Courtesy of Choonster, (MIT License) https://github.com/Choonster/TestMod3
public class ModFluids {
	static Random rand = new Random();
	/**
	 * The fluids registered by this mod. Includes fluids that were already registered by another mod.
	 */
	public static final Set<Fluid> FLUIDS = new HashSet<>();

	/**
	 * The fluid blocks from this mod only. Doesn't include blocks for fluids that were already registered by another mod.
	 */
	public static final Set<IFluidBlock> MOD_FLUID_BLOCKS = new HashSet<>();

	public static final Fluid SULFURIC_ACID = createFluid(EnumFluid.SULFURIC_ACID.getFluidName(), true, 0xFFFFFFFF,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SILVER)));
	public static final Fluid HYDROCHLORIC_ACID = createFluid(EnumFluid.HYDROCHLORIC_ACID.getFluidName(), true, 0xFFBBE7EE,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.ICE)));
	public static final Fluid HYDROFLUORIC_ACID = createFluid(EnumFluid.HYDROFLUORIC_ACID.getFluidName(), true, 0xFFEBEEBB,
		fluid -> fluid.setDensity(1600).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.YELLOW)));
	public static final Fluid SYNGAS = createFluid(EnumFluid.SYNGAS.getFluidName(), true, 0xFF484848,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.GRAY)));
	public static final Fluid ACRYLIC_ACID = createFluid(EnumFluid.ACRYLIC_ACID.getFluidName(), true, 0xFF919191,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SNOW)));
	public static final Fluid CHLOROMETHANE = createFluid(EnumFluid.CHLOROMETHANE.getFluidName(), true, 0xFFC8C8C8,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SILVER)));
	public static final Fluid SILICONE = createFluid(EnumFluid.SILICONE.getFluidName(), true, 0xFF98A6B3,
		fluid -> fluid.setDensity(90000).setViscosity(90000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.CLAY)));
	public static final Fluid AMMONIA = createFluid(EnumFluid.AMMONIA.getFluidName(), true, 0xFFC7DEC7,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.EMERALD)));
	public static final Fluid NITRIC_ACID = createFluid(EnumFluid.NITRIC_ACID.getFluidName(), true, 0xFFDED6C7,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.YELLOW)));
	public static final Fluid TITANIUM_TETRACHLORIDE = createFluid(EnumFluid.TITANIUM_TETRACHLORIDE.getFluidName(), true, 0xFFE9FDBF,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.LIME)){
			@Override
		    public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		    	if(!world.isRemote){
	    			world.setBlockState(pos, ModBlocks.smokeBlock.getDefaultState());
    				BlockPos smokePos;
	    			for(int y = 0; y < 5; y++){
		    			for(int x = -5; x < 5; x++){
			    			for(int z = -5; z < 5; z++){
				    	    	if(rand.nextInt(5) < 4){
				    	    		smokePos = new BlockPos(pos.getX() + x, pos.getY() + y, pos.getZ() + z);
				    	    		if(world.getBlockState(smokePos).getBlock() == Blocks.AIR){
				    	    			world.setBlockState(smokePos, ModBlocks.smokeBlock.getDefaultState());
				    	    		}
				    	    	}
			    			}
		    			}
	    			}
		    	}
		        world.scheduleBlockUpdate(pos, this, 1, 0);
		    }
	});
	public static final Fluid SODIUM_CYANIDE = createFluid(EnumFluid.SODIUM_CYANIDE.getFluidName(), true, 0xFFDCDCDC,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SNOW)));
	public static final Fluid PHOSPHORIC_ACID = createFluid(EnumFluid.PHOSPHORIC_ACID.getFluidName(), true, 0xFFDCDCDC,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SNOW)));
	public static final Fluid LIQUID_NITROGEN = createFluid(EnumFluid.LIQUID_NITROGEN.getFluidName(), true, 0xFFDCDCDC,
		fluid -> fluid.setDensity(1000).setViscosity(1000).canBePlacedInWorld(),
		fluid -> new BlockFluidClassic(fluid, new MaterialLiquid(MapColor.SNOW)){
			@Override
		    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
				super.updateTick(world, pos, state, rand);
    	    	if(rand.nextInt(100) == 0){
	    			world.setBlockState(pos, Blocks.AIR.getDefaultState());
	    		}
		        world.scheduleBlockUpdate(pos, this, 1, 0);
			}
		    @SideOnly(Side.CLIENT)
		    @Override
		    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
		        for (int i = 0; i < 8; ++i){
		            double d0 = (double)pos.getX() + rand.nextDouble();
		            double d1 = (double)pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
		            double d2 = (double)pos.getZ() + rand.nextDouble();
		            worldIn.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		        }
		    }
	});

	/**
	 * Create a {@link Fluid} and its {@link IFluidBlock}, or use the existing ones if a fluid has already been registered with the same name.
	 */
	private static <T extends Block & IFluidBlock> Fluid createFluid(String name, boolean hasFlowIcon, int color, Consumer<Fluid> fluidPropertyApplier, Function<Fluid, T> blockFactory) {
		final String texturePrefix = Reference.MODID + ":fluids/";
		final ResourceLocation still = new ResourceLocation(texturePrefix + "basefluid_still");
		final ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + "basefluid_flow") : still;
		Fluid fluid = new Fluid(name, still, flowing){
			@Override
			public int getColor() {
				return color;
			}
		};
		final boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

		if (useOwnFluid) {
			fluidPropertyApplier.accept(fluid);
			MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
		} else {
			fluid = FluidRegistry.getFluid(name);
		}
		FLUIDS.add(fluid);
		return fluid;
	}

	@Mod.EventBusSubscriber
	public static class RegistrationHandler {
		/**
		 * Register this mod's fluid {@link Block}s.
		 */
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();
			for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
				final Block block = (Block) fluidBlock;
				block.setRegistryName(Reference.MODID, "fluid." + fluidBlock.getFluid().getName());
				block.setUnlocalizedName(Reference.MODID + ":" + fluidBlock.getFluid().getUnlocalizedName());
				block.setCreativeTab(Reference.RockhoundingChemistry);
				registry.register(block);
			}
		}

		/**
		 * Register this mod's fluid {@link ItemBlock}s.
		 */
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();
			for (final IFluidBlock fluidBlock : MOD_FLUID_BLOCKS) {
				final Block block = (Block) fluidBlock;
				final ItemBlock itemBlock = new ItemBlock(block);
				itemBlock.setRegistryName(block.getRegistryName());
				registry.register(itemBlock);
			}
		}
	}

	public static void registerFluidContainers() {
		for (final Fluid fluid : FLUIDS) {
			registerBucket(fluid);
		}
	}

	private static void registerBucket(Fluid fluid) {
		FluidRegistry.addBucketForFluid(fluid);
	}

}