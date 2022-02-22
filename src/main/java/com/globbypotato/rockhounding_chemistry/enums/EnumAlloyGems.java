package com.globbypotato.rockhounding_chemistry.enums;

public enum EnumAlloyGems implements BaseEnum{
	ND_YAG("YagNd"),
	ER_YAG("YagEr"),
	YB_YAG("YagYb");

	public String formal;
	EnumAlloyGems(String formal){
		this.formal = formal;
	}

	//---------CUSTOM----------------
	public static int size(){
		return values().length;
	}

	public static String name(int index) {
		return values()[index].getName();
	}

	//---------ENUM----------------
	public static String[] getAlloys(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getAlloy(i);
		}
		return temp;
	}

	public static String getAlloy(int index){
		return name(index);
	}

	public static String[] getFormals(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = formalName(i);
		}
		return temp;
	}

	private static String formalName(int index) {
		return values()[index].formal;
	}


	public static String[] getItemNames(){
		String[] temp = new String[size()*3];
		for(int i = 0; i < size(); i++){
			temp[i*3] = getItemDust(i);
			temp[(i*3) + 1] = getItemIngot(i);
			temp[(i*3) + 2] = getItemNugget(i);
		}
		return temp;
	}

	public static String getItemName(int index){
		return getItemNames()[index].toString();
	}

	public static String[] getDust(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getDust(i);
		}
		return temp;
	}

	public static String getItemDust(int index){
		return name(index) + "_dust";
	}

	public static String getDust(int index){
		return "dust" + formalName(index);
	}

	public static String[] getIngots(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getIngot(i);
		}
		return temp;
	}

	public static String getItemIngot(int index){
		return name(index) + "_gem";
	}

	public static String getIngot(int index){
		return "gem" + formalName(index);
	}

	public static String[] getNuggets(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getNugget(i);
		}
		return temp;
	}

	public static String getItemNugget(int index){
		return name(index) + "_nugget";
	}

	public static String getNugget(int index){
		return "nugget" + formalName(index);
	}

	public static String[] getBlocks(){
		String[] temp = new String[size()];
		for(int i = 0; i < size(); i++){
			temp[i] = getBlock(i);
		}
		return temp;
	}

	public static String getBlock(int index){
		return "block" + formalName(index);
	}

}