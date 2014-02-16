package org.agecraft;

import java.io.File;

import org.agecraft.assets.resources.Resources;
import org.agecraft.assets.resources.ResourcesCore;
import org.agecraft.assets.resources.ResourcesPrehistory;
import org.agecraft.core.ACBlockRenderingHandler;
import org.agecraft.core.ACBlockRenderingHandlerWithIcon;
import org.agecraft.core.PlayerTradeManager;
import org.agecraft.core.PlayerTradeManager.PlayerTrade;
import org.agecraft.core.clothing.PlayerClothingClient;
import org.agecraft.core.entity.EntityArrow;
import org.agecraft.core.entity.EntityBolt;
import org.agecraft.core.gui.ContainerPlayerTrade;
import org.agecraft.core.gui.ContainerSmeltery;
import org.agecraft.core.gui.ContainerWorkbench;
import org.agecraft.core.gui.GuiInventory;
import org.agecraft.core.gui.GuiPlayerTrade;
import org.agecraft.core.gui.GuiSmeltery;
import org.agecraft.core.gui.GuiWorkbench;
import org.agecraft.core.player.ACPlayerClient;
import org.agecraft.core.player.ACPlayerRender;
import org.agecraft.core.player.ACPlayerServer;
import org.agecraft.core.render.entity.RenderArrow;
import org.agecraft.core.render.entity.RenderBolt;
import org.agecraft.core.tileentities.TileEntityAgeTeleporterBeam;
import org.agecraft.core.tileentities.TileEntityAgeTeleporterChest;
import org.agecraft.core.tileentities.TileEntitySmelteryFurnace;
import org.agecraft.core.tileentities.TileEntityWorkbench;
import org.agecraft.core.tileentities.renderers.TileEntityAgeTeleporterBeamRenderer;
import org.agecraft.core.tileentities.renderers.TileEntityAgeTeleporterChestRenderer;
import org.agecraft.prehistory.PrehistoryBlockRenderingHandler;
import org.agecraft.prehistory.PrehistoryBlockRenderingHandlerWithIcon;
import org.agecraft.prehistory.gui.GuiSharpener;
import org.agecraft.prehistory.gui.InventorySharpener;
import org.agecraft.prehistory.tileentities.TileEntityBarrel;
import org.agecraft.prehistory.tileentities.TileEntityBox;
import org.agecraft.prehistory.tileentities.TileEntityCampfire;
import org.agecraft.prehistory.tileentities.TileEntityPot;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererBarrel;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererBox;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererCampfire;
import org.agecraft.prehistory.tileentities.renderers.TileEntityRendererPot;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;
import elcon.mods.core.ECConfig;
import elcon.mods.core.ElConCore;
import elcon.mods.core.player.PlayerAPI;
import elcon.mods.core.player.PlayerAPI.PlayerCoreType;
import elcon.mods.core.render.BlockRenderingHandlerOverlay;

public class ACClientProxy extends ACCommonProxy {

	@Override
	public void registerResources() {
		Resources.instance = new Resources();
		new ResourcesCore();
		new ResourcesPrehistory();
	}

	@Override
	public void registerRenderInformation() {
		// register client tick handler
		AgeCraft.instance.tickHandlerClient = new ACTickHandlerClient();
		TickRegistry.registerTickHandler(AgeCraft.instance.tickHandlerClient, Side.CLIENT);

		// register event handler
		ACEventHandlerClient eventHandler = new ACEventHandlerClient();
		MinecraftForge.EVENT_BUS.register(eventHandler);

		// register block handlers
		RenderingRegistry.registerBlockHandler(ECConfig.BLOCK_OVERLAY_RENDER_ID, new BlockRenderingHandlerOverlay());
		
		// register key handler
		KeyBindingRegistry.registerKeyBinding(new ACKeyHandler());

		// init player clothing client
		PlayerClothingClient.clothingDir = new File(ElConCore.minecraftDir, File.separator + "clothing");
		PlayerClothingClient.clothingFileDir = new File(ElConCore.minecraftDir, File.separator + "playerSkins");

		// register block rendering handler
		ACBlockRenderingHandler blockRenderingHandler = new ACBlockRenderingHandler();
		ACBlockRenderingHandlerWithIcon blockRenderingHandlerWithIcon = new ACBlockRenderingHandlerWithIcon();
		RenderingRegistry.registerBlockHandler(90, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(91, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(99, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(100, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(101, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(102, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(103, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(104, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(105, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(106, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(107, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(108, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(109, blockRenderingHandler);
		RenderingRegistry.registerBlockHandler(110, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(111, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(115, blockRenderingHandler);

		// register item rendering handler
		// ACItemRenderingHandler itemRenderingHandler = new ACItemRenderingHandler();
		// MinecraftForgeClient.registerItemRenderer(itemID, itemRenderingHandler);

		// register tileentity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAgeTeleporterBeam.class, new TileEntityAgeTeleporterBeamRenderer());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAgeTeleporterChest.class, new TileEntityAgeTeleporterChestRenderer());
		
		//register entity renderers
		RenderingRegistry.registerEntityRenderingHandler(EntityArrow.class, new RenderArrow());
		RenderingRegistry.registerEntityRenderingHandler(EntityBolt.class, new RenderBolt());
		
		//register ages
		registerPrehistory();
	}
	
	private void registerPrehistory() {
		PrehistoryBlockRenderingHandler blockRenderingHandler = new PrehistoryBlockRenderingHandler();
		PrehistoryBlockRenderingHandlerWithIcon blockRenderingHandlerWithIcon = new PrehistoryBlockRenderingHandlerWithIcon();
		
		//register block rendering handler
		RenderingRegistry.registerBlockHandler(200, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(201, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(202, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(203, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(204, blockRenderingHandlerWithIcon);
		RenderingRegistry.registerBlockHandler(205, blockRenderingHandlerWithIcon);
		
		//register tile entity renderers
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCampfire.class, new TileEntityRendererCampfire());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPot.class, new TileEntityRendererPot());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBarrel.class, new TileEntityRendererBarrel());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBox.class, new TileEntityRendererBox());
	}

	@Override
	public void registerPlayerAPI() {
		PlayerAPI.register(PlayerCoreType.CLIENT, ACPlayerClient.class);
		PlayerAPI.register(PlayerCoreType.SERVER, ACPlayerServer.class);
		PlayerAPI.register(PlayerCoreType.RENDER, ACPlayerRender.class);
		ACLog.info("Registered PlayerAPI classes");
	}

	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		if(id == 0) {
			return new GuiInventory(player);
		} else if(id == 1) {
			PlayerTrade trade = PlayerTradeManager.tradesClient.get(player.username);
			return new GuiPlayerTrade(new ContainerPlayerTrade(player.inventory, trade, trade.currentPlayer));
		} else if(id == 10) {
			return new GuiChest(player.inventory, (TileEntityAgeTeleporterChest) world.getBlockTileEntity(x, y, z));
		} else if(id == 11) {
			return new GuiWorkbench(new ContainerWorkbench(player, player.inventory, (TileEntityWorkbench) world.getBlockTileEntity(x, y, z), world, x, y, z));
		} else if(id == 12) {
			return new GuiSmeltery(new ContainerSmeltery(player, (TileEntitySmelteryFurnace) world.getBlockTileEntity(x, y, z)));
		} else if(id == 30) {
			return new GuiSharpener(new InventorySharpener());
		}
		return null;
	}
}
