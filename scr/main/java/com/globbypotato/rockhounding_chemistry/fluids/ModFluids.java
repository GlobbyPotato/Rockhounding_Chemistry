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
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

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

	public static Item beaker;
	public static ItemBeaker waterBeaker;
	public static ItemBeaker lavaBeaker;
	public static Item sulfuricAcidBeaker;
	public static Item hydrochloricAcidBeaker;
	public static Item hydrofluoricAcidBeaker;
	public static Item syngasBeaker;
	public static Item acrylicAcidBeaker;
	public static Item chloromethaneBeaker;
	public static Item siliconeBeaker;
	public static Item ammoniaBeaker;
	public static Item nitricAcidBeaker;
	public static Item titaniumTetrachlorideBeaker;
	public static Item sodiumCyanideBeaker;
	public static Item phosphoricAcidBeaker;

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

	public static void loadBeakers(){
		beaker = new ItemBeaker("beaker", Blocks.AIR, true);
		waterBeaker = new ItemBeaker("waterBeaker", Blocks.WATER, false);
		lavaBeaker = new ItemBeaker("lavaBeaker", Blocks.LAVA, false);
		sulfuricAcidBeaker = new ItemBeaker("sulfuricAcidBeaker", ModFluids.SULFURIC_ACID.getBlock(), false).setContainerItem(beaker);
		hydrochloricAcidBeaker = new ItemBeaker("hydrochloricAcidBeaker", ModFluids.HYDROCHLORIC_ACID.getBlock(), false).setContainerItem(beaker);
		hydrofluoricAcidBeaker = new ItemBeaker("hydrofluoricAcidBeaker", ModFluids.HYDROFLUORIC_ACID.getBlock(), false).setContainerItem(beaker);
		syngasBeaker = new ItemBeaker("syngasBeaker", ModFluids.SYNGAS.getBlock(), false).setContainerItem(beaker);
		acrylicAcidBeaker = new ItemBeaker("acrylicAcidBeaker", ModFluids.ACRYLIC_ACID.getBlock(), false).setContainerItem(beaker);
		chloromethaneBeaker = new ItemBeaker("chloromethaneBeaker", ModFluids.CHLOROMETHANE.getBlock(), false).setContainerItem(beaker);
		siliconeBeaker = new ItemBeaker("siliconeBeaker", ModFluids.SILICONE.getBlock(), false).setContainerItem(beaker);
		ammoniaBeaker = new ItemBeaker("ammoniaBeaker", ModFluids.AMMONIA.getBlock(), false).setContainerItem(beaker);
		nitricAcidBeaker = new ItemBeaker("nitricAcidBeaker", ModFluids.NITRIC_ACID.getBlock(), false).setContainerItem(beaker);
		titaniumTetrachlorideBeaker = new ItemBeaker("titaniumTetrachlorideBeaker", ModFluids.TITANIUM_TETRACHLORIDE.getBlock(), false).setContainerItem(beaker);
		sodiumCyanideBeaker = new ItemBeaker("sodiumCyanideBeaker", ModFluids.SODIUM_CYANIDE.getBlock(), false).setContainerItem(beaker);
		phosphoricAcidBeaker = new ItemBeaker("phosphoricAcidBeaker", ModFluids.PHOSPHORIC_ACID.getBlock(), false).setContainerItem(beaker);
	}

	public static void registerFluidBeakers() {
	    FluidContainerRegistry.registerFluidContainer(FluidRegistry.WATER, new ItemStack(waterBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(FluidRegistry.LAVA, new ItemStack(lavaBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(SULFURIC_ACID, new ItemStack(sulfuricAcidBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(HYDROCHLORIC_ACID, new ItemStack(hydrochloricAcidBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(HYDROFLUORIC_ACID, new ItemStack(hydrofluoricAcidBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(SYNGAS, new ItemStack(syngasBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(ACRYLIC_ACID, new ItemStack(acrylicAcidBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(CHLOROMETHANE, new ItemStack(chloromethaneBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(SILICONE, new ItemStack(siliconeBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(AMMONIA, new ItemStack(ammoniaBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(NITRIC_ACID, new ItemStack(nitricAcidBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(TITANIUM_TETRACHLORIDE, new ItemStack(titaniumTetrachlorideBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(SODIUM_CYANIDE, new ItemStack(sodiumCyanideBeaker), new ItemStack(beaker));
	    FluidContainerRegistry.registerFluidContainer(PHOSPHORIC_ACID, new ItemStack(phosphoricAcidBeaker), new ItemStack(beaker));
		BucketHandler.INSTANCE.registerFluid(Blocks.WATER, waterBeaker);
		BucketHandler.INSTANCE.registerFluid(Blocks.LAVA, lavaBeaker);
		BucketHandler.INSTANCE.registerFluid(SULFURIC_ACID.getBlock(), sulfuricAcidBeaker);
		BucketHandler.INSTANCE.registerFluid(HYDROCHLORIC_ACID.getBlock(), hydrochloricAcidBeaker);
		BucketHandler.INSTANCE.registerFluid(HYDROFLUORIC_ACID.getBlock(), hydrofluoricAcidBeaker);
		BucketHandler.INSTANCE.registerFluid(SYNGAS.getBlock(), syngasBeaker);
		BucketHandler.INSTANCE.registerFluid(ACRYLIC_ACID.getBlock(), acrylicAcidBeaker);
		BucketHandler.INSTANCE.registerFluid(CHLOROMETHANE.getBlock(), chloromethaneBeaker);
		BucketHandler.INSTANCE.registerFluid(SILICONE.getBlock(), siliconeBeaker);
		BucketHandler.INSTANCE.registerFluid(AMMONIA.getBlock(), ammoniaBeaker);
		BucketHandler.INSTANCE.registerFluid(NITRIC_ACID.getBlock(), nitricAcidBeaker);
		BucketHandler.INSTANCE.registerFluid(TITANIUM_TETRACHLORIDE.getBlock(), titaniumTetrachlorideBeaker);
		BucketHandler.INSTANCE.registerFluid(SODIUM_CYANIDE.getBlock(), sodiumCyanideBeaker);
		BucketHandler.INSTANCE.registerFluid(PHOSPHORIC_ACID.getBlock(), phosphoricAcidBeaker);
	}

}