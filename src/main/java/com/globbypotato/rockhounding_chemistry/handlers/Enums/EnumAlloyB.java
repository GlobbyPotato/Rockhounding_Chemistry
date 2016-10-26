package com.globbypotato.rockhounding_chemistry.handlers.Enums;

import net.minecraft.util.IStringSerializable;

public enum EnumAlloyB implements IStringSerializable {
	MISCHMETAL			(0,  "mischmetal"),
	ROSEGOLD			(1,  "rosegold"),
	GREENGOLD			(2,  "greengold"),
	WHITEGOLD			(3,  "whitegold"),
	SHIBUICHI			(4,  "shibuichi"),
	TOMBAK				(5,  "tombak"),
	PEWTER				(6,  "pewter"),
	CORTEN				(7,  "corten");
    private static final EnumAlloyB[] META_LOOKUP = new EnumAlloyB[values().length];
	private int meta;
	private final String name;

	private EnumAlloyB(int meta, String name) {
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

    public static EnumAlloyB byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) { meta = 0; }
        return META_LOOKUP[meta];
    }

    static {
    	EnumAlloyB[] metas = values();
        int metaLenght = metas.length;
        for (int x = 0; x < metaLenght; ++x) {
        	EnumAlloyB metaIn = metas[x];
            META_LOOKUP[metaIn.getMetadata()] = metaIn;
        }
    }

}
