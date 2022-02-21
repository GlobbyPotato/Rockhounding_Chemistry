package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COOrbiter;
import com.globbypotato.rockhounding_chemistry.machines.tile.TEOrbiter;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIOrbiter extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guiorbiter.png");

    private final TEOrbiter tile;

    public UIOrbiter(InventoryPlayer playerInv, TEOrbiter tile){
    	super(new COOrbiter(playerInv,tile), ModUtils.HEIGHT);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.containerName = "container." + TEOrbiter.getName();
		this.ySize = 220;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   String[] multistring;
	   List<String> tooltip;

		//drain
	    if(GuiUtils.hoveringArea(152, 117, 16, 16, mouseX, mouseY, x, y)){
			if(this.tile.isValidPreset()){
				String voidstring = TextFormatting.GREEN + "Can Drain Liquid XP";
				tooltip = GuiUtils.drawLabel(voidstring, mouseX, mouseY);
			}else{
				tooltip = GuiUtils.drawLabel(TextFormatting.RED + "No Liquid XP available", mouseX, mouseY);
			}
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	    }

	   //-lev
	   if(GuiUtils.hoveringArea(59, 58, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Decrease levels", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //+lev
	   if(GuiUtils.hoveringArea(99, 58, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Increase levels", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //retrieve
	   if(GuiUtils.hoveringArea(79, 38, 18, 18, mouseX, mouseY, x, y)){
		   String retLabel = TextFormatting.GREEN + "Retrieve Levels";
			if(this.tile.getOffScale()){
				retLabel = TextFormatting.RED + "Not enough XP for these levels";
			}
		   tooltip = GuiUtils.drawLabel(retLabel, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //prev
	   if(GuiUtils.hoveringArea(8, 109, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Previous Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //next
	   if(GuiUtils.hoveringArea(25, 109, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Next Recipe", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //activation
	   if(GuiUtils.hoveringArea(79, 116, 18, 18, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.activation_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //probe
	   if(GuiUtils.hoveringArea(22, 42, 18, 18, mouseX, mouseY, x, y)){
		   if(this.tile.probeSlot().isEmpty()){
			   tooltip = GuiUtils.drawLabel("Insert any Probe here", mouseX, mouseY);
			   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		   }
	   }

		//input waste
		if(GuiUtils.hoveringArea(157, 26, 12, 40, mouseX, mouseY, x, y)){
			tooltip = GuiUtils.drawFluidTankInfo(this.tile.inputTank, mouseX, mouseY);
			drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}

		//output juice
		if(GuiUtils.hoveringArea(36, 78, 102, 12, mouseX, mouseY, x, y)){
			if(this.tile.isValidPreset()){
				tooltip = GuiUtils.drawFluidTankInfo(this.tile.outputTank, mouseX, mouseY);
			}else{
				String xpTot = TextFormatting.GRAY + "Stored Experience: " + TextFormatting.YELLOW + this.tile.getXPCount() + "/" + this.tile.getXPCountMax() + " xp";
			   	tooltip = GuiUtils.drawLabel(xpTot, mouseX, mouseY);
			}
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
		}
		
		//juice filter
	    if(GuiUtils.hoveringArea(115, 116, 18, 18, mouseX, mouseY, x, y)){
			String filterstring = TextFormatting.BLUE + "Filter: " + TextFormatting.WHITE + "Currently selected Liquid XP";
			if(this.tile.isValidPreset() && this.tile.recipeJuice() != null){
				filterstring = TextFormatting.GRAY + "Filter: " + TextFormatting.GREEN + this.tile.recipeJuice().getLocalizedName();
				tooltip = GuiUtils.drawLabel(filterstring, mouseX, mouseY);
				drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
			}
		}

    }

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		String recipeLabel = "No Liquid XP selected";
		if(this.tile.isValidPreset() && this.tile.getRecipeList(this.tile.getRecipeIndex()).getInput() != null){
			recipeLabel = getMod(FluidRegistry.getDefaultFluidName(this.tile.getRecipeList(this.tile.getRecipeIndex()).getInput().getFluid()).split(":")[0]);
		}
		this.fontRenderer.drawString(String.valueOf(this.tile.getLevels()), 80, 64, 4210752);
		this.fontRenderer.drawString(String.valueOf(recipeLabel), 9, 99, 4210752);
	}

	private String getMod(String id) {
	    ModContainer mods = Loader.instance().getIndexedModList().get(id);
	    return mods != null ? mods.getName() : id;
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
        		this.drawTexturedModalRect(i + 81, j + 117, 190, 10, 14, 14);
        	}else{
        		this.drawTexturedModalRect(i + 81, j + 117, 176, 10, 14, 14);
        	}
        }

        // drain icon
		if(this.tile.isValidPreset()){
    		this.drawTexturedModalRect(i + 153, j + 118, 176, 25, 14, 14);
		}

		//offscale icon
		if(this.tile.getOffScale()){
            this.drawTexturedModalRect(i + 81, j + 40, 176, 39, 14, 14);
		}

		// output bar
		if(!this.tile.isValidPreset()){
			if(this.tile.getXPCount() > 0){
	            int k = GuiUtils.getScaledValue(100, this.tile.getXPCount(), this.tile.getXPCountMax());
	            this.drawTexturedModalRect(i + 37, j + 79, 0, 220, k, 10);
			}
		}

		//input waste
		if(this.tile.wasteHasFluid() && this.tile.getWasteAmount() > 0){
			GuiUtils.renderFluidBar(this.tile.getWasteFluid(), this.tile.getWasteAmount(), this.tile.getWasteCapacity(), i + 158, j + 27, 10, 38);
		}

		//output liquid xp
		if(this.tile.isValidPreset()){
			if(this.tile.juiceHasFluid() && this.tile.getJuiceAmount() > 0){
				GuiUtils.renderFluidBar(this.tile.getJuiceFluid(), this.tile.getJuiceAmount(), this.tile.getJuiceCapacity(), i + 37, j + 79, 100, 10);
			}
		}

		//output juice
		if(this.tile.isValidPreset() && this.tile.recipeJuice() != null){
			GuiUtils.renderFluidBar(this.tile.recipeJuice(), 1000, 1000, i + 116, j + 117, 16, 16);
		}

    }
}