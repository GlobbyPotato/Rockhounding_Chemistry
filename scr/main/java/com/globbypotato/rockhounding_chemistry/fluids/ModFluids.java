package com.globbypotato.rockhounding_chemistry.fluids;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import com.globbypotato.rockhounding_chemistry.entities.EntityToxicSlime;
import com.globbypotato.rockhounding_chemistry.enums.EnumFluid;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ToxicMutationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.ToxicMutationRecipe;
import com.globbypotato.rockhounding_core.utils.CoreUtils;
import com.google.common.base.Strings;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.IForgeRegistry;

public class ModFluids {
	static Random rand = new Random();
	/**
	 * The fluids registered by this mod. Includes fluids that were already registered by another mod.
	 */
	public static final Set<Fluid> FLUIDS = new HashSet<>();
	public static final Set<Fluid> GASES = new HashSet<>();

	/**
	 * The fluid blocks from this mod only. Doesn't include blocks for fluids that were already registered by another mod.
	 */
	public static final Set<BlockFluidClassic> MOD_FLUID_BLOCKS = new HashSet<>();

	public static DamageSource ACID = new DamageSource("acid").setDamageBypassesArmor();
	public static DamageSource CHEMICALS = new DamageSource("chemicals").setDamageBypassesArmor();
	public static DamageSource SPILL = new DamageSource("spill").setDamageBypassesArmor();

//AQUEOUS
	public static final Fluid SULFURIC_ACID = createFluid(EnumFluid.SULFURIC_ACID.getFluidName(), true, 0xFFFFFFFF, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
		        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
				applyDamage(ACID, entityIn, 8F);
			}
	});
	public static final Fluid SODIUM_HYDROXIDE = createFluid(EnumFluid.SODIUM_HYDROXIDE.getFluidName(), true, 0xFFFFFFFF, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(CHEMICALS, entityIn, 4F);
			}
	});
	public static final Fluid HYDROCHLORIC_ACID = createFluid(EnumFluid.HYDROCHLORIC_ACID.getFluidName(), true, 0xFFBBE7EE, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(ACID, entityIn, 3F);
			}
	});
	public static final Fluid HYDROFLUORIC_ACID = createFluid(EnumFluid.HYDROFLUORIC_ACID.getFluidName(), true, 0xFFEBEEBB, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(ACID, entityIn, 3F);
			}
	});
	public static final Fluid CHLOROMETHANE = createFluid(EnumFluid.CHLOROMETHANE.getFluidName(), true, 0xFFC8C8C8, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(CHEMICALS, entityIn, 2F);
			}
	});
	public static final Fluid LIQUID_AMMONIA = createFluid(EnumFluid.LIQUID_AMMONIA.getFluidName(), true, 0xFFC7DEC7, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(ACID, entityIn, 5F);
			}
	});
	public static final Fluid NITRIC_ACID = createFluid(EnumFluid.NITRIC_ACID.getFluidName(), true, 0xFFDED6C7, "blur", 1000, 1000, 350, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(ACID, entityIn, 16F);
			}
	});
	public static final Fluid SODIUM_CYANIDE = createFluid(EnumFluid.SODIUM_CYANIDE.getFluidName(), true, 0xFFDCDCDC, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(ACID, entityIn, 6F);
			}
	});
	public static final Fluid LIQUID_NITROGEN = createFluid(EnumFluid.LIQUID_NITROGEN.getFluidName(), true, 0xFFDCDCDC, "blur", 10, 10, 70, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
		@Override
	    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
			applyDamage(CHEMICALS, entityIn, 4F);
		}

		@SideOnly(Side.CLIENT)
	    @Override
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
			doVaporize(pos, worldIn, rand, EnumParticleTypes.EXPLOSION_NORMAL);
	    }

		@Override
		public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
			super.updateTick(worldIn, pos, state, rand);
			vaporizeGas(pos, worldIn, 10);
			worldIn.scheduleBlockUpdate(pos, this, 1, 0);
		}
	});
	public static final Fluid LIQUID_OXYGEN = createFluid(EnumFluid.LIQUID_OXYGEN.getFluidName(), true, 0xFFDCDCDC, "blur", 10, 10, 60, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
		@Override
	    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
			applyDamage(CHEMICALS, entityIn, 4F);
		}

		@SideOnly(Side.CLIENT)
	    @Override
	    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
			doVaporize(pos, worldIn, rand, EnumParticleTypes.EXPLOSION_NORMAL);
	    }

		@Override
		public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
			super.updateTick(worldIn, pos, state, rand);
			vaporizeGas(pos, worldIn, 10);
			worldIn.scheduleBlockUpdate(pos, this, 1, 0);
		}
	});
	public static final Fluid METHANOL = createFluid(EnumFluid.METHANOL.getFluidName(), true, 0xFFC8C8C8, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(CHEMICALS, entityIn, 2F);
			}
	});
	public static final Fluid ACRYLIC_ACID = createFluid(EnumFluid.ACRYLIC_ACID.getFluidName(), true, 0xFFFFFFFF, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(ACID, entityIn, 4F);
			}
	});
	public static final Fluid VIRGIN_WATER = 	createFluid(EnumFluid.VIRGIN_WATER.getFluidName(), 	true, 0xFF79BEDE, "blur", 1000, 1000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER){});
	public static final Fluid SALT_BRINE = 		createFluid(EnumFluid.SALT_BRINE.getFluidName(), 	true, 0xFF7196A7, "blur", 1000, 1000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER){});
	public static final Fluid DENSE_BRINE = 	createFluid(EnumFluid.DENSE_BRINE.getFluidName(), 	true, 0xFFD5A6A6, "blur", 1000, 1000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER){});
	public static final Fluid MOTHER_LIQUOR = 	createFluid(EnumFluid.MOTHER_LIQUOR.getFluidName(), true, 0xFFFFD6D6, "fill", 1000, 1000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER){});



//DENSE
	public static final Fluid LEACHATE = 		createFluid(EnumFluid.LEACHATE.getFluidName(), 			true,  0xFF4D4011, "fill", 3000, 3000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER));
	public static final Fluid COAL_SLURRY = 	createFluid(EnumFluid.COAL_SLURRY.getFluidName(), 		false, 0xFF000000, "fill", 3000, 4000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER));
	public static final Fluid ORGANIC_SLURRY = 	createFluid(EnumFluid.ORGANIC_SLURRY.getFluidName(), 	true,  0xFF8B8B68, "fill", 2000, 2000, 300, false, 0, fluid -> new BlockFluidClassic(fluid, Material.WATER));

	public static final Fluid COAL_TAR = createFluid(EnumFluid.COAL_TAR.getFluidName(), false, 0xFF000000, "fill", 5000, 7000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				entityIn.setInWeb();
			}
	});
	public static final Fluid SILICONE = createFluid(EnumFluid.SILICONE.getFluidName(), false, 0xFF98A6B3, "fill", 9000, 9000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				entityIn.setInWeb();
			}
	});
	public static final Fluid TOXIC_WASTE = createFluid(EnumFluid.TOXIC_WASTE.getFluidName(), true, 0xFF91AA71, "molten", 1500, 2025, 350, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(SPILL, entityIn, 12F);
			}

			@SideOnly(Side.CLIENT)
		    @Override
		    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
				doSparks(pos, worldIn, rand, EnumParticleTypes.SMOKE_NORMAL, 32);
		    }

			@Override
			public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
				if(this.isSourceBlock(world, pos)){
					List entities = world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(new BlockPos(pos.getX(), pos.getY(), pos.getZ()), new BlockPos(pos.getX() + 1, pos.getY() + 1, pos.getZ() + 1)), EntitySelectors.IS_ALIVE);
					if(!entities.isEmpty() && entities.size() > 0){
						for (int i = 0; i < entities.size(); i++) {
							EntityItem drop = (EntityItem)entities.get(i);
							ItemStack dropStack = drop.getItem();
							if(!dropStack.isEmpty()){
								for(ToxicMutationRecipe recipe : ToxicMutationRecipes.toxic_mutation_recipes){
									if(!recipe.getType()){
										if(!recipe.getInput().isEmpty() && recipe.getInput().isItemEqual(dropStack)){
											doMutation(recipe, world, pos, dropStack, drop);
											break;
										}
									}else{
										ArrayList<Integer> inputOreIDs = CoreUtils.intArrayToList(OreDictionary.getOreIDs(dropStack));
										if(!inputOreIDs.isEmpty()){
											if(!Strings.isNullOrEmpty(recipe.getOredict()) && inputOreIDs.contains(OreDictionary.getOreID(recipe.getOredict()))){
												doMutation(recipe, world, pos, dropStack, drop);
												break;
											}
										}
									}
								}
							}
						}
					}
					world.scheduleBlockUpdate(pos, this, 10, 0);
				}
				super.updateTick(world, pos, state, rand);
			}

			private void doMutation(ToxicMutationRecipe recipe, World world, BlockPos pos, ItemStack dropStack, EntityItem drop) {
				if(!world.isRemote){
					ItemStack mutationDrop = recipe.getOutput();
					mutationDrop.setCount(dropStack.getCount());
					EntityItem mutation = new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(), mutationDrop);
					mutation.setPosition(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
					mutation.motionX = 0; mutation.motionY = 0; mutation.motionZ = 0;
					drop.setDead();
					world.spawnEntity(mutation);		
				}
				doClickSound(null, world, pos, SoundEvents.ENTITY_CREEPER_HURT, 0.3F, 2.0F);
			}
	});
	public static final Fluid TOXIC_SLUDGE = createFluid(EnumFluid.TOXIC_SLUDGE.getFluidName(), true, 0xFF91AA71, "blur", 1000, 1000, 300, false, 0,
		fluid -> new BlockFluidClassic(fluid, Material.WATER){
			@Override
		    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn){
				applyDamage(SPILL, entityIn, 8F);
			}

			@SideOnly(Side.CLIENT)
		    @Override
		    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
				doSparks(pos, worldIn, rand, EnumParticleTypes.CRIT_MAGIC, 16);
		    }

			@Override
			public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand){
	        	if(ModConfig.enableHazard){
					if(this.isSourceBlock(worldIn, pos)){
				        if(worldIn.getDifficulty() != EnumDifficulty.PEACEFUL){
							if(worldIn.rand.nextInt(ModConfig.slimeChance + 1)  == 0){
								if(!worldIn.isRemote){
									EntityToxicSlime slime = new EntityToxicSlime(worldIn);
									slime.setPosition(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D);
									NBTTagCompound tag = new NBTTagCompound();
									slime.writeEntityToNBT(tag);
						        	tag.setInteger("Size", 1);
						        	slime.readEntityFromNBT(tag);
									worldIn.spawnEntity(slime);
									slime.playLivingSound();
								}
							}
						}
						worldIn.scheduleBlockUpdate(pos, this, 10, 0);
					}
				}
				super.updateTick(worldIn, pos, state, rand);
			}
	});


//GASSES
	public static final Fluid NITROGEN =		createFluid(EnumFluid.NITROGEN.getFluidName(), 			true, 0xFFDCDCDC, "blur", 10, 10,  120, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid OXYGEN = 			createFluid(EnumFluid.OXYGEN.getFluidName(), 			true, 0xFFDCDCDC, "blur", 10, 10,  110, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid RAW_SYNGAS = 		createFluid(EnumFluid.RAW_SYNGAS.getFluidName(), 		true, 0xFF5A605B, "blur", 10, 10,  400, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid SYNGAS = 			createFluid(EnumFluid.SYNGAS.getFluidName(), 			true, 0xFF484848, "blur", 10, 10,  305, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid COMPRESSED_AIR = 	createFluid(EnumFluid.COMPRESSED_AIR.getFluidName(), 	true, 0xFFCAFFFC, "blur", 10, 10,  280, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid REFINED_AIR = 	createFluid(EnumFluid.REFINED_AIR.getFluidName(), 		true, 0xFF8FC8E9, "blur", 10, 10,  250, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid COOLED_AIR = 		createFluid(EnumFluid.COOLED_AIR.getFluidName(), 		true, 0xFF73A7EE, "blur", 10, 10,  140, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid ARGON = 			createFluid(EnumFluid.ARGON.getFluidName(), 			true, 0xFF834A9A, "blur", 10, 10,  60,  true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid CARBON_DIOXIDE = 	createFluid(EnumFluid.CARBON_DIOXIDE.getFluidName(), 	true, 0xFF818A99, "blur", 10, 10,  200, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid HELIUM = 			createFluid(EnumFluid.HELIUM.getFluidName(), 			true, 0xFFFF6C00, "blur", 10, 10,  50,  true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid NEON = 			createFluid(EnumFluid.NEON.getFluidName(), 				true, 0xFFFF0000, "blur", 10, 10,  70,  true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid KRYPTON = 		createFluid(EnumFluid.KRYPTON.getFluidName(), 			true, 0xFF638ACA, "blur", 10, 10,  60,  true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid XENON = 			createFluid(EnumFluid.XENON.getFluidName(), 			true, 0xFF24279E, "blur", 10, 10,  70,  true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid WATER_VAPOUR = 	createFluid(EnumFluid.WATER_VAPOUR.getFluidName(), 		true, 0xFFDCDCDC, "blur", 10, 10,  340, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid AMMONIA = 		createFluid(EnumFluid.AMMONIA.getFluidName(), 			true, 0xFFC7DEC7, "blur", 10, 10,  300, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid HYDROGEN = 		createFluid(EnumFluid.HYDROGEN.getFluidName(), 			true, 0xFFDCDCDC, "blur", 10, 10,  110, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid RAW_FLUE_GAS = 	createFluid(EnumFluid.RAW_FLUE_GAS.getFluidName(), 		true, 0xFFA5B2BA, "blur", 10, 10,  450, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));
	public static final Fluid FLUE_GAS = 		createFluid(EnumFluid.FLUE_GAS.getFluidName(), 			true, 0xFFBFCED8, "blur", 10, 10,  310, true, 0,fluid -> new BlockFluidClassic(fluid, Material.AIR));



//MOLTEN
	public static final Fluid MOLTEN_VANADIUM = 	createFluid(EnumFluid.MOLTEN_VANADIUM.getFluidName(), 		false, 0xFFCAE483, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_TITANIUM = 	createFluid(EnumFluid.MOLTEN_TITANIUM.getFluidName(), 		false, 0xFFA6A6A6, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_CUBE = 		createFluid(EnumFluid.MOLTEN_CUBE.getFluidName(), 			false, 0xFFFF5400, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_SCAL = 		createFluid(EnumFluid.MOLTEN_SCAL.getFluidName(), 			false, 0xFFFFFFFF, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_BAM = 			createFluid(EnumFluid.MOLTEN_BAM.getFluidName(), 			false, 0xFF82858B, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_STELLITE = 	createFluid(EnumFluid.MOLTEN_STELLITE.getFluidName(), 		false, 0xFFD9CF87, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_CUPRONICKEL = 	createFluid(EnumFluid.MOLTEN_CUPRONICKEL.getFluidName(), 	false, 0xFFB59B8D, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_NIMONIC = 		createFluid(EnumFluid.MOLTEN_NIMONIC.getFluidName(), 		false, 0xFFFFFFFF, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_HASTELLOY = 	createFluid(EnumFluid.MOLTEN_HASTELLOY.getFluidName(), 		false, 0xFF545454, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_NICHROME = 	createFluid(EnumFluid.MOLTEN_NICHROME.getFluidName(), 		false, 0xFF7A7168, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_CUNIFE = 		createFluid(EnumFluid.MOLTEN_CUNIFE.getFluidName(), 		false, 0xFFFFE5BF, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_HYDRONALIUM = 	createFluid(EnumFluid.MOLTEN_HYDRONALIUM.getFluidName(), 	false, 0xFFABB0B2, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_VANASTEEL = 	createFluid(EnumFluid.MOLTEN_VANASTEEL.getFluidName(), 		false, 0xFFCFD3A4, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_TANTALOY = 	createFluid(EnumFluid.MOLTEN_TANTALOY.getFluidName(), 		false, 0xFF3E463A, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_CORTEN = 		createFluid(EnumFluid.MOLTEN_CORTEN.getFluidName(), 		false, 0xFF7B360C, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_PEWTER = 		createFluid(EnumFluid.MOLTEN_PEWTER.getFluidName(), 		false, 0xFFB3C0B6, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_NIAL = 		createFluid(EnumFluid.MOLTEN_NIAL.getFluidName(), 			false, 0xFFC2BBA3, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_INCONEL = 		createFluid(EnumFluid.MOLTEN_INCONEL.getFluidName(), 		false, 0xFFB1A96A, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));
	public static final Fluid MOLTEN_ZIRCALOY = 	createFluid(EnumFluid.MOLTEN_ZIRCALOY.getFluidName(), 		false, 0xFFD5D7BF, "molten", 2000, 10000, 1000, false, 10, fluid -> new BlockFluidClassic(fluid, Material.LAVA));



	static void applyDamage(DamageSource type, Entity entityIn, float damage) {
    	if(ModConfig.fluidDamage){
			if(entityIn != null && entityIn instanceof EntityLivingBase && !(entityIn instanceof EntityToxicSlime)){
				entityIn.attackEntityFrom(type, damage);
			}
    	}
	}

	static void doSparks(BlockPos pos, World worldIn, Random rand, EnumParticleTypes particles, int i) {
		if(rand.nextInt(i) == 0){
            double d0 = pos.getX() + rand.nextDouble();
            double d1 = pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
            double d2 = pos.getZ() + rand.nextDouble();
            worldIn.spawnParticle(particles, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

	static void doVaporize(BlockPos pos, World worldIn, Random rand, EnumParticleTypes particles) {
		for (int i = 0; i < 8; ++i){
			double d0 = pos.getX() + rand.nextDouble();
			double d1 = pos.getY() + rand.nextDouble() * 0.5D + 0.5D;
			double d2 = pos.getZ() + rand.nextDouble();
			worldIn.spawnParticle(particles, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[0]);
		}
	}

    static void vaporizeGas(BlockPos pos, World world, int i) {
    	if(rand.nextInt(i) == 0){
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
		}
	}

	static void doClickSound(EntityPlayer player, World world, BlockPos pos, SoundEvent sound, float volume, float pitch) {
		world.playSound(player, pos, sound, SoundCategory.BLOCKS, volume, pitch);
	}



	/**
	 * Create a {Fluid} and its {IFluidBlock}, or use the existing ones if a fluid has already been registered with the same name.
	 */
	private static <T extends BlockFluidClassic> Fluid createFluid(String name, boolean hasFlowIcon, int fluidcolor, String alpha, int fluiddensity, int fluidviscosity, int fluidtemperature, boolean fluidgaseous, int fluidluminosity, Function<Fluid, T> blockFactory) {
		String texturePrefix = Reference.MODID + ":fluids/";
		ResourceLocation still = new ResourceLocation(texturePrefix + alpha + "_basefluid_still");
		ResourceLocation flowing = hasFlowIcon ? new ResourceLocation(texturePrefix + alpha + "_basefluid_flow") : still;

		Fluid fluid = new Fluid(name, still, flowing);
									fluid.setColor(fluidcolor);
		if(fluiddensity != 1000){	fluid.setDensity(fluiddensity);}
		if(fluidviscosity != 1000){	fluid.setViscosity(fluidviscosity);}
		if(fluidtemperature != 300){fluid.setTemperature(fluidtemperature);}
		if(fluidgaseous){			fluid.setGaseous(fluidgaseous);}
		if(fluidluminosity > 0){	fluid.setLuminosity(fluidluminosity);}

		boolean useOwnFluid = FluidRegistry.registerFluid(fluid);

		if (useOwnFluid) {
			MOD_FLUID_BLOCKS.add(blockFactory.apply(fluid));
		}else{
			fluid = FluidRegistry.getFluid(name);
		}

		FLUIDS.add(fluid);

		if(fluid.isGaseous()){
			GASES.add(fluid);
		}

		return fluid;
	}

	@Mod.EventBusSubscriber(modid = Reference.MODID)
	public static class RegistrationHandler {

		/**
		 * Register this mod's fluid {@link Block}s.
		 */
		@SubscribeEvent
		public static void registerBlocks(RegistryEvent.Register<Block> event) {
			final IForgeRegistry<Block> registry = event.getRegistry();

			for (final BlockFluidClassic fluidBlock : MOD_FLUID_BLOCKS) {
				fluidBlock.setRegistryName(Reference.MODID, "fluid." + fluidBlock.getFluid().getName());
				fluidBlock.setUnlocalizedName(Reference.MODID + ":" + fluidBlock.getFluid().getUnlocalizedName());
				if(!fluidBlock.getFluid().isGaseous()){
					fluidBlock.setCreativeTab(Reference.RockhoundingChemistry);
				}else{
					fluidBlock.setCreativeTab(null);
				}
				registry.register(fluidBlock);
			}
		}

		/**
		 * Register this mod's fluid {@link ItemBlock}s.
		 */
		@SubscribeEvent
		public static void registerItems(RegistryEvent.Register<Item> event) {
			final IForgeRegistry<Item> registry = event.getRegistry();

			for (final BlockFluidClassic fluidBlock : MOD_FLUID_BLOCKS) {
				final ItemBlock itemBlock = new ItemBlock(fluidBlock);
				itemBlock.setRegistryName(fluidBlock.getRegistryName());
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