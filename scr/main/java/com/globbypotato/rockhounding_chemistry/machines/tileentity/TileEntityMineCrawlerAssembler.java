package com.globbypotato.rockhounding_chemistry.machines.tileentity;

import com.globbypotato.rockhounding_chemistry.ModBlocks;
import com.globbypotato.rockhounding_chemistry.ModItems;
import com.globbypotato.rockhounding_chemistry.enums.EnumCrawler;
import com.globbypotato.rockhounding_chemistry.handlers.ModConfig;
import com.globbypotato.rockhounding_chemistry.machines.gui.GuiMineCrawlerAssembler;
import com.globbypotato.rockhounding_core.machines.tileentity.MachineStackHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.TileEntityMachineInv;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler;
import com.globbypotato.rockhounding_core.machines.tileentity.WrappedItemHandler.WriteMode;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TileEntityMineCrawlerAssembler extends TileEntityMachineInv {
	public int cookTime;
    private static int getTier;

    //main slots
    public static final int SLOT_CASING = 0;
    public static final int SLOT_MODE = 1;
    public static final int SLOT_ARMS = 2;

    //grid
    public static final int SLOT_GRID_1 = 3;
    public static final int SLOT_GRID_2 = 4;
    public static final int SLOT_GRID_3 = 5;
    public static final int SLOT_GRID_4 = 6;
    public static final int SLOT_GRID_5 = 7;
    public static final int SLOT_GRID_6 = 8;
    public static final int SLOT_GRID_MIN = 3;
    public static final int SLOT_GRID_MAX = 8;

    //handling slots
    public static final int SLOT_MEMORY = 9;

    //setup slots
    public static final int SLOT_FILLER = 10;
    public static final int SLOT_ABSORBER = 11;
    public static final int SLOT_TUNNELER = 12;
    public static final int SLOT_LIGHTER = 13;
    public static final int SLOT_RAILMAKER = 14;
    public static final int SLOT_DECORATOR = 15;
    public static final int SLOT_PATHFINDER = 16;
    public static final int SLOT_STORAGE = 17;
    public static final int SLOT_SETUP_MIN = 10;
    public static final int SLOT_SETUP_MAX = 17;

    //interface slots
    public static final int SLOT_LOADER = 18;
    public static final int SLOT_MATERIAL = 19;

    //output slot
    public static final int SLOT_OUTPUT = 0;

	//parts itemstacks
	ItemStack baseLogic = crawlerPart(0);
	ItemStack crawlerMemory = crawlerPart(6);
	ItemStack advancedLogic = crawlerPart(7);
	ItemStack crawlerSetup = crawlerPart(8);
	ItemStack crawlerCasing = crawlerPart(9);
	ItemStack miningHead = crawlerPart(10);
	ItemStack supportArms = crawlerPart(66);
	ItemStack anyCrawler = new ItemStack(ModBlocks.mineCrawler);

	public static int totInput = 20;
	public static int totOutput = 1;

	public TileEntityMineCrawlerAssembler() {
		super(totInput, totOutput);

		input =  new MachineStackHandler(totInput,this){
			@Override
			public ItemStack insertItem(int slot, ItemStack insertingStack, boolean simulate){
				if(slot == SLOT_CASING && ItemStack.areItemsEqual(crawlerCasing, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_MODE && (ItemStack.areItemsEqual(baseLogic, insertingStack) || ItemStack.areItemsEqual(advancedLogic, insertingStack)) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_ARMS && ItemStack.areItemsEqual(supportArms, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if((slot >= SLOT_GRID_MIN && slot <= SLOT_GRID_MAX) && ItemStack.areItemsEqual(miningHead, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if((slot == SLOT_MEMORY || slot == SLOT_MATERIAL || slot == SLOT_LOADER) && ItemStack.areItemsEqual(crawlerMemory, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if((slot >= SLOT_SETUP_MIN && slot <= SLOT_SETUP_MAX) && ItemStack.areItemsEqual(crawlerSetup, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if(slot == SLOT_MATERIAL && insertingStack != null && insertingStack.getItem() instanceof ItemBlock){
					return super.insertItem(slot, insertingStack, simulate);
				}
				if((slot == SLOT_LOADER || slot == SLOT_CASING) && ItemStack.areItemsEqual(anyCrawler, insertingStack) ){
					return super.insertItem(slot, insertingStack, simulate);
				}
				return insertingStack;
			}
		};
		this.automationInput = new WrappedItemHandler(input, WriteMode.IN);
	}

	private static ItemStack crawlerPart(int meta){
		return new ItemStack(ModItems.miscItems, 1, meta);
	}

	@Override
	public int getGUIHeight() {
		return GuiMineCrawlerAssembler.HEIGHT;
	}

	public int getMaxCookTime(){
		return ModConfig.speedAssembling;
	}

    @Override
    public void update(){
    	if(!worldObj.isRemote){
	    	if(canAssembly()){	assemblyCrawler(); this.markDirtyClient();}
	    	if(canDismantle()){	dismantleCrawler(); this.markDirtyClient();}
	    	if(canLoad()){		loadCrawler(); this.markDirtyClient();}
    	}
    }

	private boolean canAssembly() {
		return output.getStackInSlot(SLOT_OUTPUT) == null 
			&& hasBodyPart(SLOT_CASING, crawlerCasing, 1) 
			&& (hasBodyPart(SLOT_MODE, baseLogic, 1) || hasBodyPart(SLOT_MODE, advancedLogic, 1)) 
			&& hasBodyPart(SLOT_ARMS, supportArms, 12) 
			&& hasBodyPart(SLOT_MEMORY, crawlerMemory, 1) 
			&& hasGrid();
	}

			private boolean hasBodyPart(int slot, ItemStack part, int num) {
				return input.getStackInSlot(slot) != null && input.getStackInSlot(slot).isItemEqual(part) && input.getStackInSlot(slot).stackSize == num;
			}

			private boolean hasGrid() {
				if(input.getStackInSlot(SLOT_GRID_1) == null && input.getStackInSlot(SLOT_GRID_2) == null && input.getStackInSlot(SLOT_GRID_3) == null && input.getStackInSlot(SLOT_GRID_4) == null && hasBodyPart(SLOT_GRID_5, miningHead, 1) && input.getStackInSlot(SLOT_GRID_6) == null ){
					getTier = 1; return true;
				}else if(input.getStackInSlot(SLOT_GRID_1) == null && hasBodyPart(SLOT_GRID_2, miningHead, 1) && input.getStackInSlot(SLOT_GRID_3) == null && input.getStackInSlot(SLOT_GRID_4) == null  && hasBodyPart(SLOT_GRID_5, miningHead, 1) && input.getStackInSlot(SLOT_GRID_6) == null ){
					getTier = 2; return true;
				}else if(input.getStackInSlot(SLOT_GRID_1) == null && input.getStackInSlot(SLOT_GRID_2) == null && input.getStackInSlot(SLOT_GRID_3) == null && hasBodyPart(SLOT_GRID_4, miningHead, 1) && hasBodyPart(SLOT_GRID_5, miningHead, 1) && hasBodyPart(SLOT_GRID_6, miningHead, 1) ){		 
					getTier = 3; return true;
				}else if(hasBodyPart(SLOT_GRID_1, miningHead, 1) && hasBodyPart(SLOT_GRID_2, miningHead, 1) && hasBodyPart(SLOT_GRID_3, miningHead, 1) && hasBodyPart(SLOT_GRID_4, miningHead, 1) && hasBodyPart(SLOT_GRID_5, miningHead, 1) && hasBodyPart(SLOT_GRID_6, miningHead, 1) ){		 
					getTier = 4; return true;
				}
				return false;
			}

		private void assemblyCrawler() {
	    	boolean flag = false;
			cookTime++;
			if(cookTime >= getMaxCookTime()) {
				cookTime = 0;
				writeAssembler();			
				flag = true;
			}
			if(flag){this.markDirty();}
		}

		private void writeAssembler() {
			//write crawler
			ItemStack crawlerOut = anyCrawler.copy();
			crawlerOut.setTagCompound(new NBTTagCompound());
			crawlerOut.getTagCompound().setInteger(EnumCrawler.TIERS.getName(), 	getTier);
			crawlerOut.getTagCompound().setInteger(EnumCrawler.MODES.getName(), 	getMode());
			crawlerOut.getTagCompound().setInteger(EnumCrawler.STEP.getName(), 		TileEntityMineCrawler.numStep);
			crawlerOut.getTagCompound().setInteger(EnumCrawler.UPGRADE.getName(), 	0);
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.FILLER.getName(), 	hasBodyPart(SLOT_FILLER,		crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.ABSORBER.getName(), 	hasBodyPart(SLOT_ABSORBER, 		crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.TUNNELER.getName(), 	hasBodyPart(SLOT_TUNNELER, 		crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.LIGHTER.getName(), 	hasBodyPart(SLOT_LIGHTER, 		crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.RAILMAKER.getName(), hasBodyPart(SLOT_RAILMAKER, 	crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.DECORATOR.getName(), hasBodyPart(SLOT_DECORATOR, 	crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.PATHFINDER.getName(),hasBodyPart(SLOT_PATHFINDER, 	crawlerSetup, 1));
			crawlerOut.getTagCompound().setBoolean(EnumCrawler.STORAGE.getName(), 	hasBodyPart(SLOT_STORAGE, 		crawlerSetup, 1));
			//apply material
			if(input.getStackInSlot(SLOT_MEMORY).hasTagCompound()){
				writeMemory(crawlerOut, EnumCrawler.FILLER_BLOCK.getBlockName(), EnumCrawler.FILLER_BLOCK.getBlockMeta(), EnumCrawler.FILLER_BLOCK.getBlockStacksize());
				writeMemory(crawlerOut, EnumCrawler.ABSORBER_BLOCK.getBlockName(), EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());
				writeMemory(crawlerOut, EnumCrawler.LIGHTER_BLOCK.getBlockName(), EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());
				writeMemory(crawlerOut, EnumCrawler.RAILMAKER_BLOCK.getBlockName(), EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());
				writeMemory(crawlerOut, EnumCrawler.DECORATOR_BLOCK.getBlockName(), EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());		
			}else{
				initializeMemory(crawlerOut, EnumCrawler.FILLER_BLOCK.getBlockName(), EnumCrawler.FILLER_BLOCK.getBlockMeta(), EnumCrawler.FILLER_BLOCK.getBlockStacksize());
				initializeMemory(crawlerOut, EnumCrawler.ABSORBER_BLOCK.getBlockName(), EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());
				initializeMemory(crawlerOut, EnumCrawler.LIGHTER_BLOCK.getBlockName(), EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());
				initializeMemory(crawlerOut, EnumCrawler.RAILMAKER_BLOCK.getBlockName(), EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());
				initializeMemory(crawlerOut, EnumCrawler.DECORATOR_BLOCK.getBlockName(), EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());		
			}
			handleSlots(crawlerOut);
		}

			private int getMode() {
				return input.getStackInSlot(SLOT_MODE).isItemEqual(advancedLogic) ? 1 : 0;
			}

			private void writeMemory(ItemStack crawlerOut, String name, String meta, String size) {
				String fillBlockName = crawlerOut.getTagCompound().getString(name) + input.getStackInSlot(SLOT_MEMORY).getTagCompound().getString(name);
				crawlerOut.getTagCompound().setString(name, fillBlockName);
				int fillBlockMeta = crawlerOut.getTagCompound().getInteger(meta) + input.getStackInSlot(SLOT_MEMORY).getTagCompound().getInteger(meta);
				crawlerOut.getTagCompound().setInteger(meta, fillBlockMeta);
				int fillBlockSize = crawlerOut.getTagCompound().getInteger(size) + input.getStackInSlot(SLOT_MEMORY).getTagCompound().getInteger(size);
				crawlerOut.getTagCompound().setInteger(size, fillBlockSize);
			}

			private void initializeMemory(ItemStack crawlerOut, String name, String meta, String size) {
				crawlerOut.getTagCompound().setString(name, "None");
				crawlerOut.getTagCompound().setInteger(meta, 0);
				crawlerOut.getTagCompound().setInteger(size, 0);
			}

			private void handleSlots(ItemStack outputStack){
				output.setStackInSlot(SLOT_OUTPUT, outputStack.copy());
				for(int x = 0; x < totInput; x++){
					if(x < SLOT_LOADER){
						input.setStackInSlot(x, null);
					}
				}
			}

	private boolean canDismantle() {
		return input.getStackInSlot(SLOT_MODE) == null 
			&& input.getStackInSlot(SLOT_ARMS) == null 
			&& (input.getStackInSlot(SLOT_GRID_1) == null && input.getStackInSlot(SLOT_GRID_2) == null && input.getStackInSlot(SLOT_GRID_3) == null 
			&& input.getStackInSlot(SLOT_GRID_4) == null && input.getStackInSlot(SLOT_GRID_5) == null && input.getStackInSlot(SLOT_GRID_6) == null) 
			&& input.getStackInSlot(SLOT_MEMORY) == null
			&& (input.getStackInSlot(SLOT_FILLER) == null && input.getStackInSlot(SLOT_ABSORBER) == null && input.getStackInSlot(SLOT_TUNNELER) == null && input.getStackInSlot(SLOT_LIGHTER) == null 
			&& input.getStackInSlot(SLOT_RAILMAKER) == null && input.getStackInSlot(SLOT_DECORATOR) == null && input.getStackInSlot(SLOT_PATHFINDER) == null && input.getStackInSlot(SLOT_STORAGE) == null)     
			&& output.getStackInSlot(SLOT_OUTPUT) == null
			&& (input.getStackInSlot(SLOT_CASING) != null && input.getStackInSlot(SLOT_CASING).isItemEqual(anyCrawler) && input.getStackInSlot(SLOT_CASING).stackSize == 1);
	}

	private void dismantleCrawler() {
    	boolean flag = false;
		cookTime++;
		if(cookTime >= getMaxCookTime()) {
			cookTime = 0;
			handleDismantler();			
			flag = true;
		}
		if(flag){this.markDirty();}
	}

		private void handleDismantler() {
			output.setStackInSlot(SLOT_OUTPUT, crawlerCasing.copy());
			input.setStackInSlot(SLOT_ARMS, supportArms); input.getStackInSlot(SLOT_ARMS).stackSize = 12;
			if(input.getStackInSlot(SLOT_CASING).hasTagCompound()){
				int getNbt; boolean getEnabler;
				getNbt = input.getStackInSlot(SLOT_CASING).getTagCompound().getInteger(EnumCrawler.MODES.getName());
				input.setStackInSlot(SLOT_MODE, setMode(getNbt).copy());

				getNbt = input.getStackInSlot(SLOT_CASING).getTagCompound().getInteger(EnumCrawler.TIERS.getName());

				if(getNbt > 0){input.setStackInSlot(SLOT_GRID_5, miningHead.copy());}
				if(getNbt == 2 || getNbt == 4){input.setStackInSlot(SLOT_GRID_2, miningHead.copy());}
				if(getNbt == 3 || getNbt == 4){input.setStackInSlot(SLOT_GRID_4, miningHead.copy()); input.setStackInSlot(SLOT_GRID_6, miningHead.copy());}
				if(getNbt == 4){input.setStackInSlot(SLOT_GRID_1, miningHead.copy()); input.setStackInSlot(SLOT_GRID_3, miningHead.copy());}

				dismantleEnabler(SLOT_TUNNELER, EnumCrawler.TUNNELER.getName());
				dismantleEnabler(SLOT_PATHFINDER, EnumCrawler.PATHFINDER.getName());
				dismantleEnabler(SLOT_STORAGE, EnumCrawler.STORAGE.getName());

				input.setStackInSlot(SLOT_MEMORY, crawlerMemory.copy());
				input.getStackInSlot(SLOT_MEMORY).setTagCompound(new NBTTagCompound());
				dismantleSetup(SLOT_FILLER, EnumCrawler.FILLER.getName(), EnumCrawler.FILLER_BLOCK.getBlockName(), EnumCrawler.FILLER_BLOCK.getBlockMeta(), EnumCrawler.FILLER_BLOCK.getBlockStacksize());
				dismantleSetup(SLOT_ABSORBER, EnumCrawler.ABSORBER.getName(), EnumCrawler.ABSORBER_BLOCK.getBlockName(), EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());
				dismantleSetup(SLOT_LIGHTER, EnumCrawler.LIGHTER.getName(), EnumCrawler.LIGHTER_BLOCK.getBlockName(), EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());
				dismantleSetup(SLOT_RAILMAKER, EnumCrawler.RAILMAKER.getName(), EnumCrawler.RAILMAKER_BLOCK.getBlockName(), EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());
				dismantleSetup(SLOT_DECORATOR, EnumCrawler.DECORATOR.getName(), EnumCrawler.DECORATOR_BLOCK.getBlockName(), EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());
			}
			input.setStackInSlot(SLOT_CASING, null);
		}

				private ItemStack setMode(int getNbt) {
					return getNbt == 1 ? advancedLogic : baseLogic;
				}

				private void dismantleSetup(int slot, String enabler, String name, String meta, String size) {
					boolean getEnabler;
					getEnabler = input.getStackInSlot(SLOT_CASING).getTagCompound().getBoolean(enabler);
					if(getEnabler){input.setStackInSlot(slot, crawlerSetup.copy());}
					String nameNbt; int metaNbt; int sizeNbt; 
					nameNbt = input.getStackInSlot(SLOT_CASING).getTagCompound().getString(name);
					input.getStackInSlot(SLOT_MEMORY).getTagCompound().setString(name, CrawlerUtils.decompileItemStack(nameNbt));
					metaNbt = input.getStackInSlot(SLOT_CASING).getTagCompound().getInteger(meta);
					input.getStackInSlot(SLOT_MEMORY).getTagCompound().setInteger(meta, metaNbt);
					sizeNbt = input.getStackInSlot(SLOT_CASING).getTagCompound().getInteger(size);
					input.getStackInSlot(SLOT_MEMORY).getTagCompound().setInteger(size, sizeNbt);
				}

				private void dismantleEnabler(int setup, String name) {
					boolean getEnabler = input.getStackInSlot(SLOT_CASING).getTagCompound().getBoolean(name);
					if(getEnabler){
						input.setStackInSlot(setup, crawlerSetup.copy());
					}
				}

		private boolean canLoad() {
			return input.getStackInSlot(SLOT_LOADER) != null && (input.getStackInSlot(SLOT_LOADER).isItemEqual(anyCrawler) || input.getStackInSlot(SLOT_LOADER).isItemEqual(crawlerMemory)) && input.getStackInSlot(SLOT_LOADER).stackSize == 1 && input.getStackInSlot(SLOT_LOADER).hasTagCompound();
		}

		private void loadCrawler() {
			if(!hasLoadedCache()){
				if(input.getStackInSlot(SLOT_MATERIAL) != null){
		            IBlockState fillerState = CrawlerUtils.tempStateFromBlock(Block.getBlockFromItem(input.getStackInSlot(SLOT_MATERIAL).getItem()), input.getStackInSlot(SLOT_MATERIAL).getItemDamage());
		            if(fillerState.isFullCube() && !(fillerState.getBlock() instanceof BlockFalling) && !fillerState.getBlock().hasTileEntity(fillerState)){
		            	handleCharging(fillerState, fillerState.getBlock(), EnumCrawler.FILLER_BLOCK.getBlockName(), EnumCrawler.FILLER_BLOCK.getBlockMeta(), EnumCrawler.FILLER_BLOCK.getBlockStacksize(), input.getStackInSlot(SLOT_MATERIAL).stackSize );
		            }
				}
				if(input.getStackInSlot(SLOT_MATERIAL) != null){
		            IBlockState absorbState = CrawlerUtils.tempStateFromBlock(Block.getBlockFromItem(input.getStackInSlot(SLOT_MATERIAL).getItem()), input.getStackInSlot(SLOT_MATERIAL).getItemDamage());
		            if(absorbState.getBlock() instanceof BlockBreakable && !absorbState.getBlock().hasTileEntity(absorbState)){
		            	handleCharging(absorbState, absorbState.getBlock(), EnumCrawler.ABSORBER_BLOCK.getBlockName(), EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), EnumCrawler.ABSORBER_BLOCK.getBlockStacksize(), input.getStackInSlot(SLOT_MATERIAL).stackSize );
		            }
				}
				if(input.getStackInSlot(SLOT_MATERIAL) != null){
		            IBlockState lighterState = CrawlerUtils.tempStateFromBlock(Block.getBlockFromItem(input.getStackInSlot(SLOT_MATERIAL).getItem()), input.getStackInSlot(SLOT_MATERIAL).getItemDamage());
		            if(lighterState.getBlock() instanceof BlockTorch && !lighterState.getBlock().hasTileEntity(lighterState)){
		            	handleCharging(lighterState, lighterState.getBlock(), EnumCrawler.LIGHTER_BLOCK.getBlockName(), EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), EnumCrawler.LIGHTER_BLOCK.getBlockStacksize(), input.getStackInSlot(SLOT_MATERIAL).stackSize );
		            }
				}
				if(input.getStackInSlot(SLOT_MATERIAL) != null){
		            IBlockState railState = CrawlerUtils.tempStateFromBlock(Block.getBlockFromItem(input.getStackInSlot(SLOT_MATERIAL).getItem()), input.getStackInSlot(SLOT_MATERIAL).getItemDamage());
		            if(railState.getBlock() instanceof BlockRailBase && !railState.getBlock().hasTileEntity(railState)){
		            	handleCharging(railState, railState.getBlock(), EnumCrawler.RAILMAKER_BLOCK.getBlockName(), EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize(), input.getStackInSlot(SLOT_MATERIAL).stackSize );
		            }
				}
				if(input.getStackInSlot(SLOT_MATERIAL) != null){
		            IBlockState decoState = CrawlerUtils.tempStateFromBlock(Block.getBlockFromItem(input.getStackInSlot(SLOT_MATERIAL).getItem()), input.getStackInSlot(SLOT_MATERIAL).getItemDamage());
		            if(decoState.isFullCube() && !(decoState.getBlock() instanceof BlockFalling) && !decoState.getBlock().hasTileEntity(decoState)){
		            	handleCharging(decoState, decoState.getBlock(), EnumCrawler.DECORATOR_BLOCK.getBlockName(), EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), EnumCrawler.DECORATOR_BLOCK.getBlockStacksize(), input.getStackInSlot(SLOT_MATERIAL).stackSize );
		            }
				}
			}

			if(hasLoadedCache()){
				handleTransfering(EnumCrawler.FILLER_BLOCK.getBlockName(), EnumCrawler.FILLER_BLOCK.getBlockMeta(), EnumCrawler.FILLER_BLOCK.getBlockStacksize());
				handleTransfering(EnumCrawler.ABSORBER_BLOCK.getBlockName(), EnumCrawler.ABSORBER_BLOCK.getBlockMeta(), EnumCrawler.ABSORBER_BLOCK.getBlockStacksize());
				handleTransfering(EnumCrawler.LIGHTER_BLOCK.getBlockName(), EnumCrawler.LIGHTER_BLOCK.getBlockMeta(), EnumCrawler.LIGHTER_BLOCK.getBlockStacksize());
				handleTransfering(EnumCrawler.RAILMAKER_BLOCK.getBlockName(), EnumCrawler.RAILMAKER_BLOCK.getBlockMeta(), EnumCrawler.RAILMAKER_BLOCK.getBlockStacksize());
				handleTransfering(EnumCrawler.DECORATOR_BLOCK.getBlockName(), EnumCrawler.DECORATOR_BLOCK.getBlockMeta(), EnumCrawler.DECORATOR_BLOCK.getBlockStacksize());
			}
		}

					private boolean hasLoadedCache() {
						return input.getStackInSlot(SLOT_MATERIAL) != null && input.getStackInSlot(SLOT_MATERIAL).isItemEqual(crawlerMemory) && input.getStackInSlot(SLOT_MATERIAL).stackSize == 1 && input.getStackInSlot(SLOT_MATERIAL).hasTagCompound();
					}

					private void handleCharging(IBlockState state, Block block, String name, String meta, String size, int num) {
						String blockName = block.getRegistryName().toString();
						String stateName = input.getStackInSlot(SLOT_LOADER).getTagCompound().getString(name);
						if(isNewChip(stateName) || (!isNewChip(blockName) && blockName.matches(input.getStackInSlot(SLOT_LOADER).getTagCompound().getString(name)) && block.getMetaFromState(state) == input.getStackInSlot(SLOT_LOADER).getTagCompound().getInteger(meta))){
							if(canMemoryCharge(name, size) ){
								int getNbt = input.getStackInSlot(SLOT_LOADER).getTagCompound().getInteger(size) + num;
								input.getStackInSlot(SLOT_LOADER).getTagCompound().setString(name, blockName);
								input.getStackInSlot(SLOT_LOADER).getTagCompound().setInteger(meta, block.getMetaFromState(state));
								input.getStackInSlot(SLOT_LOADER).getTagCompound().setInteger(size, getNbt);
								input.setStackInSlot(SLOT_MATERIAL, null);
							}
						}
					}

					private boolean isNewChip(String stateName) {
						return stateName.matches("None") || stateName.matches("") || stateName == null;
					}

					private boolean canMemoryCharge(String name, String size){
						if(input.getStackInSlot(SLOT_LOADER).getTagCompound().getInteger(size) + input.getStackInSlot(SLOT_MATERIAL).stackSize <= 32000 ){
							return true;
						}
						return false;
					}

					private void handleTransfering(String name, String meta, String size) {
						int getNbt = 0;
						if( canMemoryLoad(name, meta, size) ){
							getNbt = input.getStackInSlot(SLOT_LOADER).getTagCompound().getInteger(size) + input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getInteger(size);
							transferBlocks(name, meta, size, getNbt);
						}
					}

					private boolean canMemoryLoad(String name, String meta, String size){
						if(!input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getString(name).matches("None")){
							if((input.getStackInSlot(SLOT_LOADER).getTagCompound().getString(name).matches(input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getString(name)) && input.getStackInSlot(SLOT_LOADER).getTagCompound().getInteger(meta) == input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getInteger(meta) ) || input.getStackInSlot(SLOT_LOADER).getTagCompound().getString(name).matches("None") ){
								if(input.getStackInSlot(SLOT_LOADER).getTagCompound().getInteger(size) + input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getInteger(size) <= 32000 ){
									return true;
								}
							}
						}
						return false;
					}

					private void transferBlocks(String name, String meta, String stacksize, int num) {
						if(input.getStackInSlot(SLOT_LOADER).getTagCompound().getString(name).matches("None")){
							input.getStackInSlot(SLOT_LOADER).getTagCompound().setString(name, input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getString(name));
							input.getStackInSlot(SLOT_LOADER).getTagCompound().setInteger(meta, input.getStackInSlot(SLOT_MATERIAL).getTagCompound().getInteger(meta));
						}
						input.getStackInSlot(SLOT_LOADER).getTagCompound().setInteger(stacksize, num);
						input.getStackInSlot(SLOT_MATERIAL).getTagCompound().setString(name, "None");
						input.getStackInSlot(SLOT_MATERIAL).getTagCompound().setInteger(meta, 0);
						input.getStackInSlot(SLOT_MATERIAL).getTagCompound().setInteger(stacksize, 0);
					}

    @Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);
        this.cookTime = compound.getInteger("CookTime");
    }
    
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound){
        super.writeToNBT(compound);
        compound.setInteger("CookTime", this.cookTime);
        return compound;
    }
}