package com.globbypotato.rockhounding_chemistry.enums;

import net.minecraft.util.IStringSerializable;

public enum EnumFluidNbt implements IStringSerializable {
	SULF("Sulf"),
	CHLO("Chlo"),
	FLUO("Fluo"),
	NITR("Nitr"),
	PHOS("Phos"),
	CYAN("Cyan"),
	SOLVENT("Solvent"),
	REAGENT("Reagent"),
	ACID("Acid");

	private String nameTag;
	private EnumFluidNbt(String nameTag) {
		this.nameTag = nameTag;
	}

	@Override
	public String getName() {
		return toString().toLowerCase();
	}

	public static int size(){
		return values().length;
	}

	public String nameTag(){
		return this.nameTag;
	}

}