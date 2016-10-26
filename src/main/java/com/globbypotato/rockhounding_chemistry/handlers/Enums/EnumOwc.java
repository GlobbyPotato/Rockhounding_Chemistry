package com.globbypotato.rockhounding_chemistry.handlers.Enums;

import net.minecraft.util.IStringSerializable;

public enum EnumOwc implements IStringSerializable {
	BULKHEAD				(0,  "bulkhead"),
	CONCRETE				(1,  "concrete"),
	DUCT					(2,  "duct"),
	TURBINE					(3,  "turbine"),
	VALVE					(4,  "valve"),
	GENERATOR				(5,  "generator"),
	STORAGE					(6,  "storage"),
	INVERTER				(7,  "inverter"),
	DEFLECTOR_STEP_A		(8,  "deflectora"),
	DEFLECTOR_STEP_B		(9,  "deflectorb"),
	DEFLECTOR_STEP_C		(10, "deflectorc"),
	DEFLECTOR_STEP_D		(11, "deflectord");

	private static final EnumOwc[] META_LOOKUP = new EnumOwc[values().length];
	private int meta;
	private final String name;

	private EnumOwc(int meta, String name) {
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

    public static EnumOwc byMetadata(int meta) {
        if (meta < 0 || meta >= META_LOOKUP.length) { meta = 0; }
        return META_LOOKUP[meta];
    }

    static {
    	EnumOwc[] metas = values();
        int metaLenght = metas.length;
        for (int x = 0; x < metaLenght; ++x) {
        	EnumOwc metaIn = metas[x];
            META_LOOKUP[metaIn.getMetadata()] = metaIn;
        }
    }

}
