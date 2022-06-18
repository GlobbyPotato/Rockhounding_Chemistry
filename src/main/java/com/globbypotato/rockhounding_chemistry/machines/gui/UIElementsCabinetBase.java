package com.globbypotato.rockhounding_chemistry.machines.gui;

import java.awt.Color;
import java.util.List;

import com.globbypotato.rockhounding_chemistry.handlers.Reference;
import com.globbypotato.rockhounding_chemistry.machines.container.COElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.machines.recipe.ElementsCabinetRecipes;
import com.globbypotato.rockhounding_chemistry.machines.tile.collateral.TEElementsCabinetBase;
import com.globbypotato.rockhounding_chemistry.utils.ModUtils;
import com.globbypotato.rockhounding_core.machines.gui.GuiUtils;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class UIElementsCabinetBase extends GuiBase {

	public static ResourceLocation TEXTURE_REF = new ResourceLocation(Reference.MODID + ":textures/gui/guicabinet.png");

    private final TEElementsCabinetBase tile;

    public UIElementsCabinetBase(InventoryPlayer playerInv, TEElementsCabinetBase tile){
    	super(new COElementsCabinetBase(playerInv,tile), 208);
        GuiBase.TEXTURE = TEXTURE_REF;
        this.tile = tile;
		this.xSize = 208;
		this.ySize = 208;
		this.containerName = "container." + TEElementsCabinetBase.getName();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float f) {
       super.drawScreen(mouseX, mouseY, f);
	   int x = (this.width - this.xSize) / 2;
	   int y = (this.height - this.ySize) / 2;

	   List<String> tooltip;
	   String[] multiString;

	   //-- page
	   if(GuiUtils.hoveringArea(123, 99, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.prev_page_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //++ page
	   if(GuiUtils.hoveringArea(152, 99, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel(this.next_page_label, mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //chars
	   if(GuiUtils.hoveringArea(89, 99, 16, 16, mouseX, mouseY, x, y)){
		   if(this.tile.getRelease()) {
			   tooltip = GuiUtils.drawLabel("Showing by CHAR", mouseX, mouseY);
		   }else {
			   tooltip = GuiUtils.drawLabel("Showing ALL", mouseX, mouseY);
		   }
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //sort
	   if(GuiUtils.hoveringArea(106, 99, 16, 16, mouseX, mouseY, x, y)){
		   if(this.tile.getSorting()) {
			   tooltip = GuiUtils.drawLabel("Sorting by NAME", mouseX, mouseY);
		   }else {
			   tooltip = GuiUtils.drawLabel("Sorting by SYMBOL", mouseX, mouseY);
		   }
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //drain
	   if(GuiUtils.hoveringArea(9, 99, 16, 16, mouseX, mouseY, x, y)){
		   tooltip = GuiUtils.drawLabel("Force extraction", mouseX, mouseY);
		   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
	   }

	   //elements
	   int colOffset = 9 + x;
	   int rowOffset = 22 + y;
	   for(int row = 0; row < 4; row++){
		   for(int col = 0; col < 7; col++){
			   int xSpace = col * 23;
			   int ySpace = row * 19;
			   if(mouseX >= colOffset + xSpace && mouseX < colOffset + xSpace + 20 && mouseY >= rowOffset + ySpace && mouseY < rowOffset + ySpace + 16){
				   int enumDust = (row * 7) + col;
				   if(!this.tile.PAGED_MATERIAL_LIST.isEmpty() && enumDust < this.tile.PAGED_MATERIAL_LIST.size()){
					   if(!this.tile.PAGED_MATERIAL_LIST.get(enumDust).getName().isEmpty()) {
						   String formalElement = this.tile.PAGED_MATERIAL_LIST.get(enumDust).getName();
						   String dictElement = this.tile.PAGED_MATERIAL_LIST.get(enumDust).getOredict();
						   String elementText = formalElement + " : " + TextFormatting.YELLOW + this.tile.PAGED_MATERIAL_LIST.get(enumDust).getAmount() + "/" + this.tile.getExtractingFactor() + " ppc" ;
						   String oredictElement = TextFormatting.DARK_AQUA + "(" + this.tile.PAGED_MATERIAL_LIST.get(enumDust).getOredict() + ")";
						  
						   String dustCount = TextFormatting.DARK_GREEN + "> Not enough ppc for extraction";
						   int dustNum = this.tile.PAGED_MATERIAL_LIST.get(enumDust).getAmount() / this.tile.getExtractingFactor();
						   if(dustNum > 0){
							   dustCount = TextFormatting.DARK_GREEN + "> " + String.valueOf(dustNum) + " item/s available";
						   }
						   String inhibited = "";
						   for(int ix = 0; ix < ElementsCabinetRecipes.inhibited_elements.size(); ix++){
							   if(dictElement.toLowerCase().matches(ElementsCabinetRecipes.inhibited_elements.get(ix).toLowerCase())){
								   inhibited = TextFormatting.RED + "> Inhibited from production";
							   }
						   }
						   String canExtract = "";
						   boolean extractStatus = this.tile.PAGED_MATERIAL_LIST.get(enumDust).getExtraction();
						   if(extractStatus) {
							   canExtract = TextFormatting.BLUE + "> Sent to Lab Balance";
						   }
						   multiString = new String[]{elementText, oredictElement, "", dustCount, inhibited, canExtract};
						   tooltip = GuiUtils.drawMultiLabel(multiString, mouseX, mouseY);
						   drawHoveringText(tooltip, mouseX, mouseY, this.fontRenderer);
					   }
				   }
			   }
		   }			
	   }
    }

	 @Override
	public void drawGuiContainerForegroundLayer(int mouseX, int mouseY){
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		for(int vSlot = 0; vSlot < 4; vSlot++){
			for(int hSlot = 0; hSlot < 7; hSlot++){
				int slotID = (vSlot * 7) + hSlot;
				   if(!this.tile.PAGED_MATERIAL_LIST.isEmpty() && slotID < this.tile.PAGED_MATERIAL_LIST.size()){
						String symbol = this.tile.PAGED_MATERIAL_LIST.get(slotID).getSymbol();
						if(this.tile.PAGED_MATERIAL_LIST.get(slotID).getExtraction()) {
							this.fontRenderer.drawString(symbol, 13 + (23 * hSlot), 24 + (19 * vSlot), Color.BLUE.getRGB());
						}else {
							this.fontRenderer.drawString(symbol, 13 + (23 * hSlot), 24 + (19 * vSlot), 4210752);
						}
					}
			}
		}
		this.fontRenderer.drawString(String.valueOf(this.tile.getPage()), 140, 104, 4210752);
	}

    @Override
    public void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY){
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    	super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int i = (this.width - this.xSize) / 2;
        int j = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		//cabinet bars
		for(int vSlot = 0; vSlot < 4; vSlot++){
			for(int hSlot = 0; hSlot < 7; hSlot++){
				int slotID = (vSlot * 7) + hSlot;
				if(!this.tile.PAGED_MATERIAL_LIST.isEmpty() && slotID < this.tile.PAGED_MATERIAL_LIST.size()){
			        int cab = GuiUtils.getScaledValue(20, this.tile.PAGED_MATERIAL_LIST.get(slotID).getAmount(), this.tile.getExtractingFactor());
					if(cab > 20){cab = 20;}
					this.drawTexturedModalRect(i + 9 + (23 * hSlot), j + 33 + (19 * vSlot), 223, 0, cab, 5);
				}
			}
		}

		//release
        if(this.tile.getRelease()){
       		this.drawTexturedModalRect(i + 90, j + 100, 209, 0, 14, 14);
        }

		//sort
        if(this.tile.getSorting()){
       		this.drawTexturedModalRect(i + 107, j + 100, 209, 14, 14, 14);
        }

    }
}