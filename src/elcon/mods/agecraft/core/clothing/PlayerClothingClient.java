package elcon.mods.agecraft.core.clothing;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;

import javax.imageio.ImageIO;

import net.minecraft.client.renderer.texture.TextureUtil;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PlayerClothingClient {
	
	public static File clothingDir;
	public static File clothingFileDir;
	public static HashMap<String, PlayerClothing> players = new HashMap<String, PlayerClothing>();
	
	public static PlayerClothing getPlayerClothing(String username) {
		if(players.containsKey(username)) {
			return players.get(username);
		}
		return null;
	}
	
	public static void addPlayerClothing(PlayerClothing clothing) {
		players.put(clothing.player, clothing);
		try {
			if(clothing.glTextureID == -1) {
				clothing.glTextureID = TextureUtil.glGenTextures();
			}
			clothing.createClothingFile(clothingDir, clothingFileDir);
			BufferedImage image = ImageIO.read(clothing.getClothingFile(clothingFileDir));
			TextureUtil.uploadTextureImageAllocate(clothing.glTextureID, image, false, false);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void updatePlayerClothing(String username) {
		if(players.containsKey(username)) {
			try {
				PlayerClothing clothing = players.get(username);
				if(clothing.glTextureID == -1) {
					clothing.glTextureID = TextureUtil.glGenTextures();
				}
				clothing.createClothingFile(clothingDir, clothingFileDir);
				BufferedImage image = ImageIO.read(clothing.getClothingFile(clothingFileDir));
				TextureUtil.uploadTextureImageAllocate(clothing.glTextureID, image, false, false);
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void removePlayerClothing(String username) {
		if(players.containsKey(username)) {
			players.remove(username);
		}
	}
}
