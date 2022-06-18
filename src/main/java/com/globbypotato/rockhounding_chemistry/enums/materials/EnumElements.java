package com.globbypotato.rockhounding_chemistry.enums.materials;

import com.globbypotato.rockhounding_chemistry.enums.BaseEnum;

public enum EnumElements implements BaseEnum{
	//RARE
	CERIUM("Ce"),
	DYSPROSIUM("Dy"),
	ERBIUM("Er"),
	EUROPIUM("Eu"),
	GADOLINIUM("Gd"),
	HOLMIUM("Ho"),
	LANTHANUM("La"),
	LUTETIUM("Lu"),
	NEODYMIUM("Nd"),
	PRASEODYMIUM("Pr"),
	SAMARIUM("Sa"),
	SCANDIUM("Sc"),
	TERBIUM("Tb"),
	THULIUM("Tm"),
	YTTERBIUM("Yt"),
	YTTRIUM("Y"),

	//COMMON
	ALUMINUM("Al"),
	ANTIMONY("Sb"),//
	ARSENIC("As"),
	BERYLLIUM("Be"),
	BISMUTH("BI"),
	BORON("B"),
	CADMIUM("Cd"),
	CALCIUM("Ca"),
	CARBON("C"),
	COBALT("Co"),
	COPPER("Cu"),
	CHROMIUM("Cr"),
	GOLD("Au"),
	IRIDIUM("Ir"),
	IRON("Fe"),
	LEAD("Pb"),
	LITHIUM("Li"),
	MAGNESIUM("Mg"),
	MANGANESE("Mn"),
	MOLYBDENUM("Mo"),//
	NICKEL("Ni"),
	NIOBIUM("Nb"),
	OSMIUM("Os"),
	PHOSPHORUS("P"),
	PLATINUM("Pt"),
	POTASSIUM("K"),
	SILICON("Si"),
	SILVER("Ag"),
	SODIUM("So"),
	SULFUR("S"),
	TANTALUM("Ta"),
	TELLURIUM("Te"),
	THORIUM("Th"),
	TIN("Sn"),
	TITANIUM("Ti"),
	TUNGSTEN("W"),
	VANADIUM("V"),
	ZINC("Zn"),
	ZIRCONIUM("Zr"),//

	//SPECIAL
	YELLOWCAKE("U");

	//---------CUSTOM----------------
	public String symbol;
	EnumElements(String symbol){
		this.symbol = symbol;
	}

	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	public static String formalName(int index) {
		return name(index).substring(0, 1).toUpperCase() + name(index).substring(1);
	}

	//---------ENUM----------------
	public static String[] getNames(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getName(i);
		}
		return temp;
	}

	public static String getName(int index){
		return name(index);
	}

	public static String[] getDusts(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getDust(i);
		}
		return temp;
	}

	public static String getDust(int index){
		return "dust" + formalName(index);
	}
	
	public String[] getSymbols(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getSymbol(i);
		}
		return temp;
	}

	public static String getSymbol(int index){
		return values()[index].symbol;
	}

}