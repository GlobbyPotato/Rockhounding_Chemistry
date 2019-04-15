package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.enums.EnumCasting;
import com.globbypotato.rockhounding_chemistry.enums.EnumServer;
import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COServer;
import com.globbypotato.rockhounding_chemistry.machines.recipe.DepositionChamberRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.GasReformerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.LabOvenRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.MetalAlloyerRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.PrecipitationRecipes;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.DepositionChamberRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.GasReformerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.LabOvenRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.MetalAlloyerRecipe;
import com.globbypotato.rockhounding_chemistry.machines.recipe.construction.PrecipitationRecipe;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEServer;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;
import com.google.common.base.Strings;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIServer extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiserver.png");

    private final TEServer tile;

    public UIServer(InventoryPlayer playerInv, TEServer tile){
    	super(new COServer(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEServer.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;
	   String[] multifilter;

	   //activation
	   if(GuiUtils.hoveringArea(79, 96, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //recipebook
	   if(GuiUtils.hoveringArea(59, 66, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Repeatable Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }
       
	   //prev
	   if(GuiUtils.hoveringArea(7, 64, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //next
	   if(GuiUtils.hoveringArea(25, 64, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //-
	   if(GuiUtils.hoveringArea(133, 64, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Decrease amount: -1", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //+
	   if(GuiUtils.hoveringArea(151, 64, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Increase Amount: +1", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //--
	   if(GuiUtils.hoveringArea(133, 82, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Decrease amount: -10", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //++
	   if(GuiUtils.hoveringArea(151, 82, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Increase Amount: +10", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //regenerate
	   if(GuiUtils.hoveringArea(80, 67, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Regenerate File", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //stack filter
	   if(GuiUtils.hoveringArea(16, 83, 18, 18, mouseX, mouseY, x, y)){
		   String filterstring = "";
		   if(this.tile.hasStackFilter()){
			   if(this.tile.getFilterStack().isEmpty() && this.tile.getFilterFluid() == null){
				   filterstring = TextFormatting.WHITE + "Set a filter according to the selected task";
				   tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			   }else if(this.tile.getFilterStack().isEmpty() && this.tile.getFilterFluid() != null){
					filterstring = TextFormatting.GRAY + "Filter: " + TextFormatting.WHITE + this.tile.getFilterFluid().getLocalizedName();
					tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
					drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			   }
		   }else{
			   filterstring = TextFormatting.RED + "Filter not required for this machine";
			   tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

    }

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = "No recipe selected";
		if(this.tile.servedDevice() == EnumServer.LAB_OVEN.ordinal() && this.tile.isValidInterval()){
			LabOvenRecipe recipe = LabOvenRecipes.lab_oven_recipes.get(this.tile.getRecipeIndex());
			if(Strings.isNullOrEmpty(recipe.getRecipeName())){
				recipeLabel = recipe.getSolution().getLocalizedName();
			}else{
				recipeLabel = recipe.getRecipeName();
			}
		}else if(this.tile.servedDevice() == EnumServer.METAL_ALLOYER.ordinal() && this.tile.isValidInterval()){
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
			recipeLabel = recipe.getOutput().getLocalizedName();
		}else if(this.tile.servedDevice() == EnumServer.EXTRACTOR.ordinal() && this.tile.isValidInterval()){
			recipeLabel = "Intensity Level: " + this.tile.getRecipeIndex();
		}else if(this.tile.servedDevice() == EnumServer.PRECIPITATOR.ordinal() && this.tile.isValidInterval()){
			PrecipitationRecipe recipe = PrecipitationRecipes.precipitation_recipes.get(this.tile.getRecipeIndex());
			if(Strings.isNullOrEmpty(recipe.getRecipeName())){
				recipeLabel = recipe.getSolution().getLocalizedName();
			}else{
				recipeLabel = recipe.getRecipeName();
			}
		}
		this.fontRenderer.drawString(recipeLabel, 9, 52, 0xFF000000);
		String amount = String.valueOf(this.tile.getRecipeAmount());
		this.fontRenderer.drawString(amount, 135, 52, 0xFF000000);
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

        //close filter
        if(!this.tile.hasStackFilter()){
            this.drawTexturedModalRect(i + 18, j + 85, 176, 41, 14, 14);
        }

		//cycle
        if(this.tile.getCycle()){
            this.drawTexturedModalRect(i + 61, j + 68, 176, 25, 14, 14);
        }

		//filter
		if(this.tile.getFilterFluid() != null){
			GuiUtils.renderFluidBar(this.tile.getFilterFluid(), 1000, 1000, i + 17, j + 84, 16, 16);
		}

    }

}