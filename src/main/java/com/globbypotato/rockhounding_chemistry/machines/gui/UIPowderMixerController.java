package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COPowderMixerController;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEPowderMixerController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIPowderMixerController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guipowdermixercontroller.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guipowdermixerjei.png");

    private final TEPowderMixerController tile;

    public UIPowderMixerController(InventoryPlayer playerInv, TEPowderMixerController tile){
    	super(new COPowderMixerController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEPowderMixerController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

		//activation
		if(GuiUtils.hoveringArea(91, 63, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		
		//prev
		if(GuiUtils.hoveringArea(7, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.prev_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		
		//next
		if(GuiUtils.hoveringArea(25, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.next_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
 
		//ingredients
		if(this.tile.isValidPreset() && this.tile.isValidRecipe() && tile.hasMaterialCabinet() && tile.hasElementsCabinet()) {
			for(int i = 0;  i < this.tile.getCurrentRecipe().getElements().size(); i++){
				if(i < 8) {
					String recipeIngredient = this.tile.getCurrentRecipe().getElements().get(i);
					int recipeQuantity = this.tile.getCurrentRecipe().getQuantities().get(i);
					String readyLabel = TextFormatting.RED + this.ingr_missing_label;
					
					for(int j = 0; j < this.tile.getElementsCabinet().MATERIAL_LIST.size(); j++) {
						String cabinetIngredient = this.tile.getElementsCabinet().MATERIAL_LIST.get(j).getOredict();
						String cabinetName = this.tile.getElementsCabinet().MATERIAL_LIST.get(j).getName();
						if(cabinetIngredient.matches(recipeIngredient)){
							if(GuiUtils.hoveringArea(7 + (i*20), 40, 20, 16, mouseX, mouseY, x, y)){
								int cabinetQuantity = this.tile.getElementsCabinet().MATERIAL_LIST.get(j).getAmount();
								if(cabinetQuantity >= recipeQuantity) {
									readyLabel = TextFormatting.GREEN + this.ingr_ready_label; 
								}
								multistring = new String[]{TextFormatting.YELLOW + cabinetName + TextFormatting.WHITE + " (" + cabinetIngredient + ")", readyLabel};
								tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
								drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
							}
						}
					}
					
					for(int j = 0; j < this.tile.getMaterialCabinet().MATERIAL_LIST.size(); j++) {
						String cabinetIngredient = this.tile.getMaterialCabinet().MATERIAL_LIST.get(j).getOredict();
						String cabinetName = this.tile.getMaterialCabinet().MATERIAL_LIST.get(j).getName();
						if(cabinetIngredient.matches(recipeIngredient)){
							if(GuiUtils.hoveringArea(7 + (i*20), 40, 20, 16, mouseX, mouseY, x, y)){
								int cabinetQuantity = this.tile.getMaterialCabinet().MATERIAL_LIST.get(j).getAmount();
								if(cabinetQuantity >= recipeQuantity) {
									readyLabel = TextFormatting.GREEN + this.ingr_ready_label; 
								}
								multistring = new String[]{TextFormatting.YELLOW + cabinetName + TextFormatting.WHITE + " (" + cabinetIngredient + ")", readyLabel};
								tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
								drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
							}
						}
					}
				}
			}
		}

    }

	@Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = "No recipe selected";
		if(this.tile.isValidPreset()){
			if(!this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOutput().isEmpty()){
				recipeLabel = this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOutput().getDisplayName();
			}
		}
		this.fontRenderer.drawString(recipeLabel, 44, 25, 4210752);

		if(this.tile.isValidPreset() && this.tile.isValidRecipe() && tile.hasMaterialCabinet() && tile.hasElementsCabinet()) {
			for(int x = 0;  x < this.tile.getCurrentRecipe().getElements().size(); x++){
				if(x < 8) {
					String recipeIngredient = this.tile.getCurrentRecipe().getElements().get(x);
	
					for(int y = 0; y < this.tile.getElementsCabinet().MATERIAL_LIST.size(); y++) {
						String cabinetIngredient = this.tile.getElementsCabinet().MATERIAL_LIST.get(y).getOredict();
						if(cabinetIngredient.matches(recipeIngredient)){
							this.fontRenderer.drawString(this.tile.getElementsCabinet().MATERIAL_LIST.get(y).getSymbol(), 11 + (x * 20), 42, 4210752);
						}
					}
					
					for(int y = 0; y < this.tile.getMaterialCabinet().MATERIAL_LIST.size(); y++) {
						String cabinetIngredient = this.tile.getMaterialCabinet().MATERIAL_LIST.get(y).getOredict();
						if(cabinetIngredient.matches(recipeIngredient)){
							this.fontRenderer.drawString(this.tile.getMaterialCabinet().MATERIAL_LIST.get(y).getSymbol(), 11 + (x * 20), 42, 4210752);
						}
					}
				}
			}
		}

	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//activation
        if(this.tile.isActive()){
        	if(this.tile.isPowered()){
        		this.drawTexturedModalRect(i + 81, j + 97, 190, 0, 14, 14);
        	}else{
        		this.drawTexturedModalRect(i + 81, j + 97, 176, 0, 14, 14);
        	}
        }

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(31, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 20, j + 66, 176, 43, 31, k);
        }

        //quantities
		if(this.tile.isValidPreset() && this.tile.isValidRecipe() && tile.hasMaterialCabinet() && tile.hasElementsCabinet()) {
			for(int x = 0;  x < this.tile.getCurrentRecipe().getElements().size(); x++){
				if(x < 8) {
					String recipeIngredient = this.tile.getCurrentRecipe().getElements().get(x);
					int recipeQuantity = this.tile.getCurrentRecipe().getQuantities().get(x);
	
					for(int y = 0; y < this.tile.getElementsCabinet().MATERIAL_LIST.size(); y++) {
						String cabinetIngredient = this.tile.getElementsCabinet().MATERIAL_LIST.get(y).getOredict();
						if(cabinetIngredient.matches(recipeIngredient)){
							int cabinetQuantity = this.tile.getElementsCabinet().MATERIAL_LIST.get(y).getAmount();
							if(cabinetQuantity >= recipeQuantity) {
					            this.drawTexturedModalRect(i + 7 + (x*20), j + 51, 204, 0, 20, 5);
							}else {
					            this.drawTexturedModalRect(i + 7 + (x*20), j + 51, 204, 5, 20, 5);
							}	
						}
					}
	
					for(int y = 0; y < this.tile.getMaterialCabinet().MATERIAL_LIST.size(); y++) {
						String cabinetIngredient = this.tile.getMaterialCabinet().MATERIAL_LIST.get(y).getOredict();
						if(cabinetIngredient.matches(recipeIngredient)){
							int cabinetQuantity = this.tile.getMaterialCabinet().MATERIAL_LIST.get(y).getAmount();
							if(cabinetQuantity >= recipeQuantity) {
					            this.drawTexturedModalRect(i + 7 + (x*20), j + 51, 204, 0, 20, 5);
							}else {
					            this.drawTexturedModalRect(i + 7 + (x*20), j + 51, 204, 5, 20, 5);
							}
						}
					}
				}
			}
		}

    }

}