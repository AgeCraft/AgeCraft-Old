package org.agecraft.core.gui;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;

import org.agecraft.core.AgeCraftCoreClient;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

@SideOnly(Side.CLIENT)
public class GuiWorkbench extends GuiContainer {

	public GuiWorkbench(Container container) {
		super(container);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(LanguageManager.getLocalization("container.workbench"), 8, 6, 0x404040);
		fontRendererObj.drawString(LanguageManager.getLocalization("container.inventory"), 8, ySize - 96 + 2, 0x404040);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(AgeCraftCoreClient.guiWorkbench);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}
}
