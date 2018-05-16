package com.globbypotato.rockhounding_chemistry.machines.gui;

import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.ContainerMachineServer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MachineRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tileentity.TileEntityMachineServer;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiMachineServer extends GuiBase {
	private final InventoryPlayer playerInventory;
	private final TileEntityMachineServer server;
	public static final int WIDTH = 176;
	public static final int HEIGHT = 200;
	public static final ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiserver.png");

	public GuiMachineServer(InventoryPlayer playerInv, TileEntityMachineServer tile){
		super(tile,new ContainerMachineServer(playerInv, tile));
		this.playerInventory = playerInv;
        TEXTURE = TEXTURE_REF;
		this.server = tile;
		this.xSize = WIDTH;
		this.ySize = HEIGHT;
		this.containerName = "container.machine_server";
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float f) {
		super.drawScreen(mouseX, mouseY, f);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;

		//activation
		if(mouseX >= 79+x && mouseX <= 97+x && mouseY >= 96+y && mouseY <= 114+y){
			drawButtonLabel("Activation", mouseX, mouseY);
		}

		//prev
		if(mouseX >= 7+x && mouseX <= 24+x && mouseY >= 64+y && mouseY <= 81+y){
			drawButtonLabel("Previous Recipe", mouseX, mouseY);
		}

		//next
		if(mouseX >= 25+x && mouseX <= 42+x && mouseY >= 64+y && mouseY <= 81+y){
			drawButtonLabel("Next Recipe", mouseX, mouseY);
		}

		//-
		if(mouseX >= 133+x && mouseX <= 150+x && mouseY >= 64+y && mouseY <= 81+y){
			drawButtonLabel("Decrease amount: -1", mouseX, mouseY);
		}

		//+
		if(mouseX >= 151+x && mouseX <= 168+x && mouseY >= 64+y && mouseY <= 81+y){
			drawButtonLabel("Increase Amount: +1", mouseX, mouseY);
		}

		//--
		if(mouseX >= 133+x && mouseX <= 150+x && mouseY >= 82+y && mouseY <= 100+y){
			drawButtonLabel("Decrease amount: -10", mouseX, mouseY);
		}

		//++
		if(mouseX >= 151+x && mouseX <= 168+x && mouseY >= 82+y && mouseY <= 100+y){
			drawButtonLabel("Increase Amount: +10", mouseX, mouseY);
		}

		//regenerate
		if(mouseX >= 81+x && mouseX <= 95+x && mouseY >= 67+y && mouseY <= 82+y){
			drawButtonLabel("Regenerate File", mouseX, mouseY);
		}

		//repeatable
		if(mouseX >= 59+x && mouseX <= 76+x && mouseY >= 66+y && mouseY <= 83+y){
			drawButtonLabel("Repeatable File", mouseX, mouseY);
		}

	}

	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
    	super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		String recipeLabel = "No recipe selected";
		if(this.server.servedDevice() == EnumServer.LAB_OVEN.ordinal() && this.server.isValidInterval()){
			LabOvenRecipe recipe = MachineRecipes.labOvenRecipes.get(this.server.recipeIndex);
			recipeLabel = recipe.getOutput().getLocalizedName();
		}
/*		}else if(this.tile.servedDevice() == EnumServer.METAL_ALLOYER.ordinal() && this.tile.isValidInterval()){
			MetalAlloyerRecipe recipe = MetalAlloyerRecipes.metal_alloyer_recipes.get(this.tile.getRecipeIndex());
			recipeLabel = recipe.getOutput().getDisplayName();
		}else if(this.tile.servedDevice() == EnumServer.DEPOSITION.ordinal() && this.tile.isValidInterval()){
			DepositionChamberRecipe recipe = DepositionChamberRecipes.deposition_chamber_recipes.get(this.tile.getRecipeIndex());
			recipeLabel = recipe.getOutput().getDisplayName();
		}else if(this.tile.servedDevice() == EnumServer.SIZER.ordinal() && this.tile.isValidInterval()){
			recipeLabel = "Comminution Level: " + this.tile.getRecipeIndex();
		}else if(this.tile.servedDevice() == EnumServer.LEACHING.ordinal() && this.tile.isValidInterval()){
			float currentGravity = (this.tile.getRecipeIndex() * 2) + 2F;
			recipeLabel = "Gravity: " + (currentGravity - 2F) + " to " + (currentGravity + 2F);
		}else if(this.tile.servedDevice() == EnumServer.RETENTION.ordinal() && this.tile.isValidInterval()){
			float currentGravity = (this.tile.getRecipeIndex() * 2) + 2F;
			recipeLabel = "Gravity: " + (currentGravity - 2F) + " to " + (currentGravity + 2F);
		}else if(this.tile.servedDevice() == EnumServer.CASTING.ordinal() && this.tile.isValidInterval()){
			recipeLabel = "Pattern: " + EnumCasting.getFormalName(this.tile.getRecipeIndex());
		}else if(this.tile.servedDevice() == EnumServer.REFORMER.ordinal() && this.tile.isValidInterval()){
			GasReformerRecipe recipe = GasReformerRecipes.gas_reformer_recipes.get(this.tile.getRecipeIndex());
			recipeLabel = recipe.getOutput().getLocalizedName();*/
		this.fontRendererObj.drawString(recipeLabel, 9, 52, 0xFF000000);
		String amount = String.valueOf(this.server.getRecipeAmount());
		this.fontRendererObj.drawString(amount, 135, 52, 0xFF000000);
	}

	public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
		super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
		int i = (this.width - this.xSize) / 2;
		int j = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//activayion
        if(this.server.activation){
    		this.drawTexturedModalRect(i + 81, j + 97, 176, 10, 14, 14);
        }

		//repeatable
        if(this.server.getCycle()){
            this.drawTexturedModalRect(i + 61, j + 68, 176, 25, 14, 14);
        }

	}
}