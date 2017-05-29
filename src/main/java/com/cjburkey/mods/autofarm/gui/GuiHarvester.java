package com.cjburkey.mods.autofarm.gui;

import java.awt.Rectangle;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import com.cjburkey.mods.autofarm.container.ContainerHarvester;
import com.cjburkey.mods.autofarm.gui.tooltip.GuiToolTip;
import com.cjburkey.mods.autofarm.gui.tooltip.ToolTipManager;
import com.cjburkey.mods.autofarm.gui.tooltip.ToolTipManager.ToolTipRenderer;
import com.cjburkey.mods.autofarm.packet.ModPackets;
import com.cjburkey.mods.autofarm.packet.PacketHarvesterToServer;
import com.cjburkey.mods.autofarm.tile.TileEntityHarvester;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiHarvester extends GuiContainer implements ToolTipRenderer {
	
	private IInventory playerInventory;
	private TileEntityHarvester farmer;
	private GuiProgressBar progBar;
	protected ToolTipManager ttm;
	protected NumberFormat nf;
	
	public static int energy;
	public static int maxEnergy;
	
	public GuiHarvester(EntityPlayer player, TileEntityHarvester te) {
		super(new ContainerHarvester(player.inventory, te));
		progBar = new GuiProgressBar(26, 68, 16, 52, 0, 100);
		ttm = new ToolTipManager();
		nf = NumberFormat.getInstance(Locale.getDefault());
		
		playerInventory = player.inventory;
		farmer = te;
		
		xSize = 176;
		ySize = 166;
	}
	
	public void drawScreen(int x, int y, float partial) {
		super.drawScreen(x, y, partial);
		ttm.drawTooltips(this, x, y);
	}

	protected void drawGuiContainerBackgroundLayer(float partialTicks, int x, int y) {
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(new ResourceLocation("autofarm:textures/gui/container/gui_harvester.png"));
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

	protected void drawGuiContainerForegroundLayer(int x, int y) {
		super.drawGuiContainerForegroundLayer(x, y);
		String name = farmer.getDisplayName().getUnformattedText();
		fontRendererObj.drawString(name, (xSize / 2) - (fontRendererObj.getStringWidth(name) / 2), 6, 0x404040);
		fontRendererObj.drawString(playerInventory.getDisplayName().getUnformattedText(), 8, 72, 0x404040);
		ttm.clear();
		updateEnergy();
	}
	
	private void updateEnergy() {
		ModPackets.getNetwork().sendToServer(new PacketHarvesterToServer(farmer.getPos()));
		progBar.setMax(maxEnergy);
		progBar.setValue(energy);
		progBar.render(mc, this);
		ttm.addToolTip(new GuiToolTip(new Rectangle(26, 17, 16, 52), "Energy", nf.format(energy) + "RF / " + nf.format(maxEnergy) + "RF"));
	}

	public FontRenderer getFontRenderer() {
		return fontRendererObj;
	}

	public void drawTextS(List<String> par1List, int par2, int par3, FontRenderer font) {
		drawHoveringText(par1List, par2, par3, font);
	}
	
}