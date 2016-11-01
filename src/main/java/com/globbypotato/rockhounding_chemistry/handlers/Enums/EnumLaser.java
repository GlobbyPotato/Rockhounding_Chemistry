package com.globbypotato.rockhounding_chemistry.handlers.Enums;

import net.minecraft.util.IStringSerializable;

public enum EnumLaser implements IStringSerializable {
	OFF_PIN			(0,  "offpin"),
	ON_PIN			(1,  "onpin"),
	OFF_PROBE		(2,  "offprobe"),
	ON_PROBE		(3,  "onprobe"),
	PIN_TX			(4,  "pintx"),
	PIN_RX			(5,  "pinrx"),
	PROBE_TX		(6,  "probetx"),
	PROBE_RX		(7,  "proberx");
	private static final EnumLaser[] META_LOOKUP = new EnumLaser[values().length];
	private int meta;
	private final String name;

	private EnumLaser(int meta, String name) {
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

    public static EnumLaser byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) { meta = 0; }
        return META_LOOKUP[meta];
    }

    static {
    	EnumLaser[] metas = values();
        int metaLenght = metas.length;
        for (int x = 0; x < metaLenght; ++x) {
        	EnumLaser metaIn = metas[x];
            META_LOOKUP[metaIn.getMetadata()] = metaIn;
        }
    }

}
