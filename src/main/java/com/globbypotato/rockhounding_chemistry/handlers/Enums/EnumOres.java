package com.globbypotato.rockhounding_chemistry.handlers.Enums;

import net.minecraft.util.IStringSerializable;

public enum EnumOres implements IStringSerializable {
	UNINSPECTED		(0,  "uninspected"),
	ARSENATE		(1,  "arsenate"),
	BORATE			(2,  "borate"),
	CARBONATE		(3,  "carbonate"),
	HALIDE			(4,  "halide"),
	NATIVE			(5,  "native"),
	OXIDE			(6,  "oxide"),
	PHOSPHATE		(7,  "phosphate"),
	SILICATE		(8,  "silicate"),
	SULFATE			(9,  "sulfate"),
	SULFIDE			(10, "sulfide");

	private static final EnumOres[] META_LOOKUP = new EnumOres[values().length];
	private int meta;
	private final String name;

	private EnumOres(int meta, String name) {
		this.meta = meta;
		this.name = name;
	}

	@Override
	public String getName() {
		return this.name;
	}

    public int getMetadata() {
        return this.meta;
    }

	@Override
	public String toString() {
		return this.getName();
	}

    public static EnumOres byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) { meta = 0; }
        return META_LOOKUP[meta];
    }

    static {
    	EnumOres[] metas = values();
        int metaLenght = metas.length;
        for (int x = 0; x < metaLenght; ++x) {
        	EnumOres metaIn = metas[x];
            META_LOOKUP[metaIn.getMetadata()] = metaIn;
        }
    }

}
