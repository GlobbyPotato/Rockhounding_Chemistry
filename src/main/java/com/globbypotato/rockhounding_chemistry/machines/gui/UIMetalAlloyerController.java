package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.ArrayList;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.materials.EnumElements;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEMetalAlloyerController;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIMetalAlloyerController extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guimetalalloyer.png");
	public static ResourceLocation TEXTURE_JEI = new ResourceLocation(Reference.MODID + ":textures/gui/jei/guimetalalloyerjei.png");

    private final TEMetalAlloyerController tile;
    private List<String> alloy;

    public UIMetalAlloyerController(InventoryPlayer playerInv, TEMetalAlloyerController tile){
    	super(new COMetalAlloyerController(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEMetalAlloyerController.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;
	   String[] multistring;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
       
	   //prev
	   if(GuiUtils.hoveringArea(7, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.prev_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //prev
	   if(GuiUtils.hoveringArea(25, 20, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.next_recipe_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //preview
	   if(this.tile.previewSlot().isEmpty()){
		   if(GuiUtils.hoveringArea(14, 60, 18, 18, mouseX, mouseY, x, y)){
			   tooltip = GuiUtils.drawLabel("Output Preview", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

	   //recipe
	   this.alloy = new ArrayList<String>();
	   String recipeready = TextFormatting.GOLD + "Uncomplete recipe:";
	   if(this.tile.isValidPreset() && this.tile.isFullRecipe(this.tile.getRecipeList(this.tile.getSelectedRecipe())) != null){
		   recipeready = TextFormatting.DARK_GREEN + "Complete recipe:";
	   }

	   if(this.tile.isValidPreset()){
		   this.alloy.add(recipeready);
		   MetalAlloyerRecipe recipe = this.tile.getRecipeList(this.tile.getSelectedRecipe());
		   for(int ingr = 0; ingr < recipe.getInputs().size(); ingr++){
			   boolean ingredientFound = false;
				String ingrOredict = recipe.getInputs().get(ingr);
				int ingrQuantity = recipe.getQuantities().get(ingr);
				if(this.tile.hasElementsCabinet() && this.tile.hasMaterialCabinet()){
					for(int element = 0; element < this.tile.elementsList().size(); element++){
						if(this.tile.getElementsList(element).getOredict().matches(ingrOredict)){
							if(ingrQuantity <= this.tile.getElementsCabinet().MATERIAL_LIST.get(element).getAmount()){
								ingredientFound = true;
							}
						}
					}
					for(int element = 0; element < this.tile.materialList().size(); element++){
						if(this.tile.getMaterialList(element).getOredict().matches(ingrOredict)){
							if(ingrQuantity <= this.tile.getMaterialCabinet().MATERIAL_LIST.get(element).getAmount()){
								ingredientFound = true;
							}
						}
					}
					String ingredient = "";
				   if(ingredientFound){
					   ingredient = TextFormatting.GREEN + ingrOredict + ": " + TextFormatting.YELLOW + ingrQuantity + " ppc";
				   }else{
					   ingredient = TextFormatting.DARK_RED + ingrOredict + ": " + TextFormatting.YELLOW + ingrQuantity + " ppc";
				   }
				   this.alloy.add(ingredient);
		   		}
	   		}
	   }else{
		   recipeready = TextFormatting.RED + "Invalid recipe";
		   this.alloy.add(recipeready);
	   }
	   if(GuiUtils.hoveringArea(16, 79, 14, 14, mouseX, mouseY, x, y)){
		   drawHoveringText(this.alloy, mouseX, mouseY, this.fontRenderer);
	   }

	   //monitor
	   if(GuiUtils.hoveringArea(49, 85, 14, 14, mouseX, mouseY, x, y)){
		   String sf = TextFormatting.GRAY + "Tier: " + TextFormatting.AQUA + this.tile.speedFactor() + "x"; 
		   String tk = TextFormatting.GRAY + "Process: " + TextFormatting.YELLOW + this.tile.getCooktimeMax() + " ticks"; 
		   multistring = new String[]{sf, tk};
		   tooltip = GuiUtils.drawMultiLabel(multistring, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //speed upgrade
	   if(GuiUtils.hoveringArea(144, 60, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.speedSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel(this.speed_label, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

    }

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = this.no_recipe_label;
		if(this.tile.isValidPreset()){
			recipeLabel = this.tile.getRecipeList(this.tile.getSelectedRecipe()).getOutput().getDisplayName();
		}
		this.fontRenderer.drawString(recipeLabel, 44, 25, 4210752);
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
        		this.drawTexturedModalRect(i + 81, j + 97, 190, 10, 14, 14);
        	}else{
        		this.drawTexturedModalRect(i + 81, j + 97, 176, 10, 14, 14);
        	}
        }

        //smelt bar
        if (this.tile.getCooktime() > 0){
            int k = GuiUtils.getScaledValue(22, this.tile.getCooktime(), this.tile.getCooktimeMax());
            this.drawTexturedModalRect(i + 111, j + 58, 176, 42, 22, k);
        }

		//valid recipe
        if(this.tile.isValidRecipe()){
            this.drawTexturedModalRect(i + 16, j + 79, 176, 65, 14, 14);
        }

    }

}