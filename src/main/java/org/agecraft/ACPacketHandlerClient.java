package org.agecraft;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.agecraft.core.PlayerTradeManager;
import org.agecraft.core.PlayerTradeManager.PlayerTrade;
import org.agecraft.core.clothing.ClothingCategory;
import org.agecraft.core.clothing.ClothingRegistry;
import org.agecraft.core.clothing.ClothingUpdater;
import org.agecraft.core.clothing.PlayerClothing;
import org.agecraft.core.clothing.PlayerClothingClient;
import org.agecraft.core.clothing.PlayerClothing.ClothingPiece;
import org.agecraft.core.gui.GuiClothingSelector;
import org.agecraft.core.tech.TechTreeClient;
import org.agecraft.core.tileentities.TileEntityDNA;
import org.agecraft.core.tileentities.TileEntitySmelteryFurnace;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ACPacketHandlerClient implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput dat = ByteStreams.newDataInput(packet.data);
		int packetID = dat.readInt();

		Side side = FMLCommonHandler.instance().getEffectiveSide();
		if(side == Side.CLIENT) {
			handlePacketClient(packetID, dat);
		}
	}

	private void handlePacketClient(int packetID, ByteArrayDataInput dat) {
		World world = (World) Minecraft.getMinecraft().theWorld;

		switch(packetID) {
		case 0:
			handleTechTreeComponent(dat);
			break;
		case 1:
			handleTechTreeAllComponents(dat);
			break;
		case 2:
			handleClothingUpdate(dat);
			break;
		case 3:
			handleClothingAllUpdate(dat);
			break;
		case 4:
			handlePlayerTrade(dat);
			break;
		case 5:
			handlePlayerTradeAcceptChange(dat);
			break;
		case 6:
			handleClothingSelectorOpen(dat);
			break;
		case 7:
			handleClothingList(dat);
			break;
		case 8:
			handleClothingUnlocks(dat);
			break;
		case 90:
			handleTileEntityDNA(world, dat);
			break;
		case 100:
			handleTileEntitySmelteryFurnace(world, dat);
			break;
		}

		for(ACComponent component : AgeCraft.instance.components) {
			if(component != null) {
				IACPacketHandlerClient packetHandler = component.getPacketHandlerClient();
				if(packetHandler != null) {
					packetHandler.handlePacketClient(packetID, dat, world);
				}
			}
		}
		for(int i = 0; i < Age.ages.length; i++) {
			if(Age.ages[i] != null) {
				IACPacketHandlerClient packetHandler = Age.ages[i].getPacketHandlerClient();
				if(packetHandler != null) {
					packetHandler.handlePacketClient(packetID, dat, world);
				}
			}
		}
	}

	private void handleTechTreeComponent(ByteArrayDataInput dat) {
		String player = dat.readUTF();
		boolean unlocked = dat.readBoolean();
		if(Minecraft.getMinecraft().thePlayer.username.equals(player)) {
			if(unlocked) {
				TechTreeClient.unlockComponent(dat.readUTF(), dat.readUTF());
			} else {
				TechTreeClient.lockComponent(dat.readUTF(), dat.readUTF());
			}
		}
	}

	private void handleTechTreeAllComponents(ByteArrayDataInput dat) {
		int pages = dat.readInt();
		for(int i = 0; i < pages; i++) {
			String pageName = dat.readUTF();
			int components = dat.readInt();
			for(int j = 0; j < components; j++) {
				TechTreeClient.unlockComponent(pageName, dat.readUTF());
			}
		}
	}

	private void handleClothingUpdate(ByteArrayDataInput dat) {
		String player = dat.readUTF();
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(player);
		if(clothing == null) {
			clothing = new PlayerClothing(player);
		}
		clothing.clothingPiecesOwned.clear();
		clothing.clothingPiecesWorn.clear();
		clothing.clothingPiecesWornColor.clear();
		int types = dat.readInt();
		for(int i = 0; i < types; i++) {
			String type = dat.readUTF();
			int pieces = dat.readInt();
			for(int j = 0; j < pieces; j++) {
				ClothingPiece piece = new ClothingPiece(type, dat.readUTF(), dat.readUTF());
				for(int k = 0; k < 16; k++) {
					piece.colors[k] = dat.readBoolean();
				}
				clothing.addClothingPiece(piece);
				if(dat.readBoolean()) {
					clothing.setCurrentClothingPiece(piece, dat.readInt());
				}
			}
		}
		PlayerClothingClient.addPlayerClothing(clothing);
	}

	private void handleClothingAllUpdate(ByteArrayDataInput dat) {
		int players = dat.readInt();
		for(int i = 0; i < players; i++) {
			String player = dat.readUTF();
			PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(player);
			if(clothing == null) {
				clothing = new PlayerClothing(player);
			}
			int types = dat.readInt();
			for(int j = 0; j < types; j++) {
				String type = dat.readUTF();
				int pieces = dat.readInt();
				for(int k = 0; k < pieces; k++) {
					ClothingPiece piece = new ClothingPiece(type, dat.readUTF(), dat.readUTF());
					for(int l = 0; l < 16; l++) {
						piece.colors[l] = dat.readBoolean();
					}
					clothing.addClothingPiece(piece);
					if(dat.readBoolean()) {
						clothing.setCurrentClothingPiece(piece, dat.readInt());
					}
				}
			}
			PlayerClothingClient.addPlayerClothing(clothing);
		}
	}

	private void handlePlayerTrade(ByteArrayDataInput dat) {
		String player1 = dat.readUTF();
		String player2 = dat.readUTF();
		PlayerTrade trade = new PlayerTrade(player1, player2, dat.readByte(), dat.readInt());
		PlayerTradeManager.tradesClient.put(trade.player1, trade);
		PlayerTradeManager.tradesClient.put(trade.player2, trade);

		Minecraft mc = Minecraft.getMinecraft();
		mc.thePlayer.openGui(dat.readInt(), 1, mc.theWorld, (int) mc.thePlayer.posX, (int) mc.thePlayer.posY, (int) mc.thePlayer.posZ);
		mc.thePlayer.openContainer.windowId = dat.readInt();
	}
	
	private void handlePlayerTradeAcceptChange(ByteArrayDataInput dat) {
		PlayerTrade trade = PlayerTradeManager.tradesClient.get(Minecraft.getMinecraft().thePlayer.username);
		trade.accepted1 = dat.readBoolean();
		trade.accepted2 = dat.readBoolean();
	}
	
	private void handleClothingSelectorOpen(ByteArrayDataInput dat) {
		ArrayList<String> changeable = new ArrayList<String>();
		int size = dat.readInt();
		for(int i = 0; i < size; i++) {
			changeable.add(dat.readUTF());
		}
		Minecraft.getMinecraft().displayGuiScreen(new GuiClothingSelector(changeable));
	}
	
	private void handleClothingList(ByteArrayDataInput dat) {
		for(ClothingCategory category : ClothingRegistry.categories.values()) {
			category.enabled = false;
		}
		int size = dat.readInt();
		final LinkedList<ClothingCategory> categories = new LinkedList<ClothingCategory>();
		for(int i = 0; i < size; i++) {
			String name = dat.readUTF();
			String versionURL = dat.readUTF();
			String updateURL = dat.readUTF();
			int expansions = dat.readInt();
			ArrayList<String> expansionURLs = new ArrayList<String>();
			for(int j = 0; j < expansions; j++) {
				expansionURLs.add(dat.readUTF());
			}
			if(ClothingRegistry.getClothingCategory(name) == null) {
				ClothingCategory category = new ClothingCategory(name, versionURL, updateURL);
				category.expansionURLs.addAll(expansionURLs);
				ClothingRegistry.registerClothingCategory(category);
				ClothingUpdater.instance.localCategories.add(category);
				categories.add(category);
				ACLog.info("[Clothing] Category: " + name + " can't be found locally, so downloading it");
			} else {
				ClothingRegistry.getClothingCategory(name).enabled = true;
			}
		}
		new Thread() {
			@Override
			public void run() {
				ClothingUpdater.instance.saveLocalCategories();
				ClothingUpdater.instance.downloadCateogries(categories);
				PlayerClothingClient.updatePlayerClothingAll();
			}
		};
	}
	
	private void handleClothingUnlocks(ByteArrayDataInput dat) {
		PlayerClothing clothing = PlayerClothingClient.getPlayerClothing(Minecraft.getMinecraft().thePlayer.username);
		clothing.unlockedCategories.clear();
		clothing.unlockedClothing.clear();
		int categories = dat.readInt();
		for(int i = 0; i < categories; i++) {
			String category = dat.readUTF();
			clothing.unlockCategory(category);
			int pieces = dat.readInt();
			for(int j = 0; j < pieces; j++) {
				clothing.unlockClothing(category, dat.readUTF());
			}
		}
	}

	private void handleTileEntityDNA(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		TileEntityDNA tile = (TileEntityDNA) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			if(Block.blocksList[world.getBlockId(x, y, z)] == null) {
				return;
			}
			tile = (TileEntityDNA) Block.blocksList[world.getBlockId(x, y, z)].createTileEntity(world, world.getBlockMetadata(x, y, z));
			world.setBlockTileEntity(x, y, z, tile);
		}
		NBTTagCompound nbt = new NBTTagCompound();
		try {
			NBTBase nbtbase;
			while((nbtbase = NBTBase.readNamedTag(dat)).getId() != 0) {
				nbt.setTag(nbtbase.getName(), nbtbase);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		tile.nbt = nbt;
		tile.getDNA().readFromNBT(tile.nbt);
		world.markBlockForUpdate(x, y, z);
	}
	
	private void handleTileEntitySmelteryFurnace(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		TileEntitySmelteryFurnace tile = (TileEntitySmelteryFurnace) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntitySmelteryFurnace();
			world.setBlockTileEntity(x, y, z, tile);
		}
		tile.hasStructure = dat.readBoolean();
		tile.color = dat.readByte();
		
		if(dat.readBoolean()) {
			tile.setSize(dat.readByte());
			tile.temperature = dat.readInt();
			int oreLength = dat.readInt();
			for(int i = 0; i < oreLength; i++) {
				try {
					tile.ores[i] = Packet.readItemStack(dat);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
			int fuelLength = dat.readInt();
			for(int i = 0; i < fuelLength; i++) {
				try {
					tile.fuel[i] = Packet.readItemStack(dat);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		world.markBlockForUpdate(x, y, z);
	}

	public static Packet getTradePacket(int dimensionID, String player1, String player2) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(75);
			dos.writeInt(dimensionID);
			dos.writeUTF(player1);
			dos.writeUTF(player2);
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

	public static Packet getTradeAcceptPacket(String username, byte player, boolean accept) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(76);
			dos.writeUTF(username);
			dos.writeByte(player);
			dos.writeBoolean(accept);
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

	public static Packet getInventoryOpenPacket(int dimensionID, String player) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(77);
			dos.writeInt(dimensionID);
			dos.writeUTF(player);
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

	public static Packet getClothingSelectorPacket(String player, HashMap<String, ClothingPiece> clothingPieces) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(78);
			dos.writeUTF(player);
			dos.writeInt(clothingPieces.size());
			for(ClothingPiece piece : clothingPieces.values()) {
				dos.writeUTF(piece.typeID);
				dos.writeUTF(piece.categoryID);
				dos.writeUTF(piece.clothingID);
				dos.writeInt(piece.getActiveColor());
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
	
	public static Packet getClothingUnlockRequest() {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(79);
			dos.writeUTF(Minecraft.getMinecraft().thePlayer.username);
			dos.writeInt(Minecraft.getMinecraft().thePlayer.worldObj.provider.dimensionId);
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
	
	public static Packet getSmelterySlotClickPacket(int dimensionID, String player, boolean isTopSlots, byte index, byte slotX, byte slotY, boolean isRightClick, boolean pressedShift) {
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bos);
			Packet250CustomPayload packet = new Packet250CustomPayload();
			dos.writeInt(80);
			dos.writeInt(dimensionID);
			dos.writeUTF(player);
			dos.writeBoolean(isTopSlots);
			dos.writeByte(slotX);
			dos.writeByte(slotY + index);
			dos.writeBoolean(isRightClick);
			dos.writeBoolean(pressedShift);
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
}
