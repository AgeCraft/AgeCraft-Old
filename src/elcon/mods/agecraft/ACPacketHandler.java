package elcon.mods.agecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.NetworkModHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import elcon.mods.agecraft.core.PlayerTradeManager;
import elcon.mods.agecraft.core.PlayerTradeManager.PlayerTrade;
import elcon.mods.agecraft.core.clothing.PlayerClothing;
import elcon.mods.agecraft.core.clothing.PlayerClothing.ClothingPiece;
import elcon.mods.agecraft.core.clothing.PlayerClothingServer;
import elcon.mods.agecraft.core.gui.ContainerPlayerTrade;
import elcon.mods.agecraft.core.tech.TechTreeServer;

public class ACPacketHandler implements IPacketHandler, IConnectionHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int packetID = dat.readInt();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.SERVER) {
			handlePacketServer(packetID, dat);
		}
	}

	private void handlePacketServer(int packetID, ByteArrayDataInput dat) {
		switch(packetID) {
		case 75:
			handlePlayerTrade(dat);
			break;
		case 76:
			handlePlayerTradeAccept(dat);
			break;
		case 77:
			handleOpenInventory(dat);
			break;
		case 78:
			handleClothingSelector(dat);
			break;
		}

		for(ACComponent component : AgeCraft.instance.components) {
			if(component != null) {
				IACPacketHandler packetHandler = component.getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketServer(packetID, dat);
				}
			}
		}
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				IACPacketHandler packetHandler = Age.ages[i].getPacketHandler();
				if(packetHandler != null) {
					packetHandler.handlePacketServer(packetID, dat);
				}
			}
		}
	}

	private void handlePlayerTrade(ByteArrayDataInput dat) {
		int dimensionID = dat.readInt();
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dimensionID);
		EntityPlayerMP p1 = (EntityPlayerMP) world.getPlayerEntityByName(dat.readUTF());
		EntityPlayerMP p2 = (EntityPlayerMP) world.getPlayerEntityByName(dat.readUTF());
		PlayerTrade trade = new PlayerTrade(p1.username, p2.username, (byte) 2, dimensionID);
		PlayerTradeManager.trades.put(trade.player1, trade);
		PlayerTradeManager.trades.put(trade.player2, trade);

		NetworkModHandler nmh = FMLNetworkHandler.instance().findNetworkModHandler(AgeCraft.instance);
		ModContainer mc = nmh.getContainer();

		ContainerPlayerTrade container1 = new ContainerPlayerTrade(p1.inventory, trade, (byte) 0);
		ContainerPlayerTrade container2 = new ContainerPlayerTrade(p2.inventory, trade, (byte) 1);
		container1.otherContainer = container2;
		container2.otherContainer = container1;

		p1.incrementWindowID();
		p1.closeContainer();
		int windowId = p1.currentWindowId;
		p1.openContainer = container1;
		p1.openContainer.windowId = windowId;
		p1.openContainer.addCraftingToCrafters(p1);

		p2.incrementWindowID();
		p2.closeContainer();
		windowId = p2.currentWindowId;
		p2.openContainer = container2;
		p2.openContainer.windowId = windowId;
		p2.openContainer.addCraftingToCrafters(p2);

		PacketDispatcher.sendPacketToPlayer(getPlayerTradePacket(trade.player1, trade.player2, (byte) 0, dimensionID, nmh.getNetworkId(), p1.openContainer.windowId), (Player) p1);
		PacketDispatcher.sendPacketToPlayer(getPlayerTradePacket(trade.player1, trade.player2, (byte) 1, dimensionID, nmh.getNetworkId(), p2.openContainer.windowId), (Player) p2);
	}
	
	private void handlePlayerTradeAccept(ByteArrayDataInput dat) {
		PlayerTrade trade = PlayerTradeManager.trades.get(dat.readUTF());
		if(dat.readByte() == 0) {
			trade.accepted1 = dat.readBoolean();
		} else {
			trade.accepted2 = dat.readBoolean();
		}
		World world = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(trade.dimensionID);
		EntityPlayerMP p1 = (EntityPlayerMP) world.getPlayerEntityByName(trade.player1);
		EntityPlayerMP p2 = (EntityPlayerMP) world.getPlayerEntityByName(trade.player2);
		PacketDispatcher.sendPacketToPlayer(getPlayerTradeAcceptChangePacket(trade.accepted1, trade.accepted2), (Player) p1);
		PacketDispatcher.sendPacketToPlayer(getPlayerTradeAcceptChangePacket(trade.accepted1, trade.accepted2), (Player) p2);
		((ContainerPlayerTrade) p1.openContainer).checkBothAccepted(p1, p2);
	}
	
	private void handleOpenInventory(ByteArrayDataInput dat) {
		EntityPlayer player = FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(dat.readInt()).getPlayerEntityByName(dat.readUTF());
		player.openGui(AgeCraft.instance, 0, player.worldObj, (int) player.posX, (int) player.posY, (int) player.posZ);
	}
	
	private void handleClothingSelector(ByteArrayDataInput dat) {
		System.out.println("RECEIVE");
		String player = dat.readUTF();
		PlayerClothing clothing = PlayerClothingServer.getPlayerClothing(player);
		int size = dat.readInt();
		for(int i = 0; i < size; i++) {
			ClothingPiece piece = new ClothingPiece(dat.readInt(), dat.readInt(), dat.readInt(), dat.readInt());
			System.out.println(piece.getActiveColor());
			clothing.addClothingPieceAndWear(piece, piece.getActiveColor());
		}
		//TODO: make the player actually pay
		PacketDispatcher.sendPacketToAllPlayers(getClothingUpdatePacket(clothing));
	}

	public static Packet getTechTreeComponentPacket(String player, String pageName, String name, boolean unlocked) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(0);
			dos.writeUTF(player);
			dos.writeBoolean(unlocked);
			dos.writeUTF(pageName);
			dos.writeUTF(name);
			dos.close();
			packet.channel = "ACTech";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Packet getTechTreeAllComponentsPacket(String player) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(1);
			if(TechTreeServer.players.containsKey(player)) {
				HashMap<String, ArrayList<String>> pages = TechTreeServer.players.get(player);
				dos.writeInt(pages.size());
				for(String pageName : pages.keySet()) {
					ArrayList<String> components = pages.get(pageName);
					dos.writeUTF(pageName);
					dos.writeInt(components.size());
					for(String name : components) {
						dos.writeUTF(name);
					}
				}
			} else {
				dos.writeInt(0);
			}
			dos.close();
			packet.channel = "ACTech";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Packet getClothingUpdatePacket(PlayerClothing clothing) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(2);
			dos.writeUTF(clothing.player);
			dos.writeInt(clothing.clothingPiecesOwned.size());
			for(int typeID : clothing.clothingPiecesOwned.keySet()) {
				ArrayList<ClothingPiece> pieces = clothing.clothingPiecesOwned.get(typeID);
				dos.writeInt(pieces.size());
				for(ClothingPiece piece : pieces) {
					dos.writeInt(piece.categoryID);
					dos.writeInt(piece.clothingID);
					for(int i = 0; i < 16; i++) {
						dos.writeBoolean(piece.colors[i]);
					}
					if(clothing.clothingPiecesWorn.containsValue(piece)) {
						dos.writeBoolean(true);
						dos.writeInt(clothing.clothingPiecesWornColor.get(typeID));
					} else {
						dos.writeBoolean(false);
					}
				}
			}
			dos.close();
			packet.channel = "ACClothing";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Packet getClothingAllUpdatePacket() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(3);
			dos.writeInt(PlayerClothingServer.players.size());
			for(PlayerClothing clothing : PlayerClothingServer.players.values()) {
				dos.writeUTF(clothing.player);
				dos.writeInt(clothing.clothingPiecesOwned.size());
				for(int typeID : clothing.clothingPiecesOwned.keySet()) {
					ArrayList<ClothingPiece> pieces = clothing.clothingPiecesOwned.get(typeID);
					dos.writeInt(pieces.size());
					for(ClothingPiece piece : pieces) {
						dos.writeInt(piece.categoryID);
						dos.writeInt(piece.clothingID);
						for(int i = 0; i < 16; i++) {
							dos.writeBoolean(piece.colors[i]);
						}
						if(clothing.clothingPiecesWorn.containsValue(piece)) {
							dos.writeBoolean(true);
							dos.writeInt(clothing.clothingPiecesWornColor.get(typeID));
						} else {
							dos.writeBoolean(false);
						}
					}
				}
			}
			dos.close();
			packet.channel = "ACClothing";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Packet getPlayerTradePacket(String player1, String player2, byte currentPlayer, int dimensionID, int networkID, int windowID) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(4);
			dos.writeUTF(player1);
			dos.writeUTF(player2);
			dos.writeByte(currentPlayer);
			dos.writeInt(dimensionID);
			dos.writeInt(networkID);
			dos.writeInt(windowID);
			dos.close();
			packet.channel = "AgeCraft";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Packet getPlayerTradeAcceptChangePacket(boolean accepted1, boolean accepted2) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(5);
			dos.writeBoolean(accepted1);
			dos.writeBoolean(accepted2);
			dos.close();
			packet.channel = "AgeCraft";
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			packet.isChunkDataPacket = false;
			return packet;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	// SERVER
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		PacketDispatcher.sendPacketToPlayer(getTechTreeAllComponentsPacket(netHandler.getPlayer().username), player);
		ACLog.info("[TechTree] Send all components to " + netHandler.getPlayer().username);

		PlayerClothingServer.createDefaultClothing(netHandler.getPlayer().username);
		PacketDispatcher.sendPacketToPlayer(getClothingAllUpdatePacket(), player);
		PacketDispatcher.sendPacketToAllPlayers(getClothingUpdatePacket(PlayerClothingServer.getPlayerClothing(netHandler.getPlayer().username)));
		ACLog.info("[Clothing] Send all clothing to " + netHandler.getPlayer().username);
	}

	@Override
	// SERVER
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		return null;
	}

	@Override
	// CLIENT
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
	}

	@Override
	// CLIENT
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
	}

	@Override
	// BOTH
	public void connectionClosed(INetworkManager manager) {
	}

	@Override
	// CLIENT
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
	}
}
