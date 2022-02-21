package com.globbypotato.rockhounding_chemistry.compat.tinker;

import com.globbypotato.rockhounding_chemistry.fluids.ModFluids;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInterModComms;

public class TinkerSupport {

	public static void loadSupport() {
		if(Loader.isModLoaded("tconstruct")){
			NBTTagCompound tagVanadium = new NBTTagCompound();
			tagVanadium.setString("fluid", ModFluids.MOLTEN_VANADIUM.getName());
			tagVanadium.setString("ore", "Vanadium");
			tagVanadium.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagVanadium);

			NBTTagCompound tagTitanium = new NBTTagCompound();
			tagTitanium.setString("fluid", ModFluids.MOLTEN_TITANIUM.getName());
			tagTitanium.setString("ore", "Titanium");
			tagTitanium.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagTitanium);
			
			NBTTagCompound tagCube = new NBTTagCompound();
			tagCube.setString("fluid", ModFluids.MOLTEN_CUBE.getName());
			tagCube.setString("ore", "Cube");
			tagCube.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagCube);

			NBTTagCompound tagScal = new NBTTagCompound();
			tagScal.setString("fluid", ModFluids.MOLTEN_SCAL.getName());
			tagScal.setString("ore", "Scal");
			tagScal.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagScal);

			NBTTagCompound tagBam = new NBTTagCompound();
			tagBam.setString("fluid", ModFluids.MOLTEN_BAM.getName());
			tagBam.setString("ore", "Bam");
			tagBam.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagBam);

			NBTTagCompound tagStellite = new NBTTagCompound();
			tagStellite.setString("fluid", ModFluids.MOLTEN_STELLITE.getName());
			tagStellite.setString("ore", "Stellite");
			tagStellite.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagStellite);

			NBTTagCompound tagCupronickel = new NBTTagCompound();
			tagCupronickel.setString("fluid", ModFluids.MOLTEN_CUPRONICKEL.getName());
			tagCupronickel.setString("ore", "Cupronickel");
			tagCupronickel.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagCupronickel);

			NBTTagCompound tagNimonic = new NBTTagCompound();
			tagNimonic.setString("fluid", ModFluids.MOLTEN_NIMONIC.getName());
			tagNimonic.setString("ore", "Nimonic");
			tagNimonic.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagNimonic);

			NBTTagCompound tagHastelloy = new NBTTagCompound();
			tagHastelloy.setString("fluid", ModFluids.MOLTEN_HASTELLOY.getName());
			tagHastelloy.setString("ore", "Hastelloy");
			tagHastelloy.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagHastelloy);

			NBTTagCompound tagNichrome = new NBTTagCompound();
			tagNichrome.setString("fluid", ModFluids.MOLTEN_NICHROME.getName());
			tagNichrome.setString("ore", "Nichrome");
			tagNichrome.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagNichrome);

			NBTTagCompound tagCunife = new NBTTagCompound();
			tagCunife.setString("fluid", ModFluids.MOLTEN_CUNIFE.getName());
			tagCunife.setString("ore", "Cunife");
			tagCunife.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagCunife);

			NBTTagCompound tagHydronalium = new NBTTagCompound();
			tagHydronalium.setString("fluid", ModFluids.MOLTEN_HYDRONALIUM.getName());
			tagHydronalium.setString("ore", "Hydronalium");
			tagHydronalium.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagHydronalium);

			NBTTagCompound tagVanasteel = new NBTTagCompound();
			tagVanasteel.setString("fluid", ModFluids.MOLTEN_VANASTEEL.getName());
			tagVanasteel.setString("ore", "Vanasteel");
			tagVanasteel.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagVanasteel);

			NBTTagCompound tagTantaloy = new NBTTagCompound();
			tagTantaloy.setString("fluid", ModFluids.MOLTEN_TANTALOY.getName());
			tagTantaloy.setString("ore", "Tantaloy");
			tagTantaloy.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagTantaloy);

			NBTTagCompound tagCorten = new NBTTagCompound();
			tagCorten.setString("fluid", ModFluids.MOLTEN_CORTEN.getName());
			tagCorten.setString("ore", "Corten");
			tagCorten.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagCorten);

			NBTTagCompound tagPewter = new NBTTagCompound();
			tagPewter.setString("fluid", ModFluids.MOLTEN_PEWTER.getName());
			tagPewter.setString("ore", "Pewter");
			tagPewter.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagPewter);

			NBTTagCompound tagNial = new NBTTagCompound();
			tagNial.setString("fluid", ModFluids.MOLTEN_NIAL.getName());
			tagNial.setString("ore", "Nial");
			tagNial.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagNial);

			NBTTagCompound tagInconel = new NBTTagCompound();
			tagInconel.setString("fluid", ModFluids.MOLTEN_INCONEL.getName());
			tagInconel.setString("ore", "Inconel");
			tagInconel.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagInconel);

			NBTTagCompound tagZircaloy = new NBTTagCompound();
			tagZircaloy.setString("fluid", ModFluids.MOLTEN_ZIRCALOY.getName());
			tagZircaloy.setString("ore", "Zircaloy");
			tagZircaloy.setBoolean("toolforge", false);
			FMLInterModComms.sendMessage("tconstruct", "integrateSmeltery", tagZircaloy);

		}

	}

}