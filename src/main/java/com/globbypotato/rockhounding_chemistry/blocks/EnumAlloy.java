package com.globbypotato.rockhounding_chemistry.blocks;

import net.minecraft.util.IStringSerializable;

public enum EnumAlloy implements IStringSerializable {
	CUBE				(0,  "cube"),
	SCAL				(1,  "scal"),
	BAM					(2,  "bam"),
	YAG					(3,  "yag"),
	CUPRONICKEL			(4,  "cupronickel"),
	NIMONIC				(5,  "nimonic"),
	HASTELLOY			(6,  "hastelloy"),
	NICHROME			(7,  "nichrome");
	private static final EnumAlloy[] META_LOOKUP = new EnumAlloy[values().length];
	private int meta;
	private final String name;

	private EnumAlloy(int meta, String name) {
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

    public static EnumAlloy byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) { meta = 0; }
        return META_LOOKUP[meta];
    }

    static {
    	EnumAlloy[] metas = values();
        int metaLenght = metas.length;
        for (int x = 0; x < metaLenght; ++x) {
        	EnumAlloy metaIn = metas[x];
            META_LOOKUP[metaIn.getMetadata()] = metaIn;
        }
    }

}
