package elcon.mods.agecraft.core.player;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.ItemInWorldManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.world.World;
import elcon.mods.core.ElConCore;
import elcon.mods.core.player.PlayerCoreServer;

public class ACPlayerServer extends PlayerCoreServer {
	
	public ACPlayerServer(MinecraftServer mcServer, World world, String username, ItemInWorldManager itemInWorldManager, int playerCoreIndex, PlayerCoreServer entityPlayerMP) {
		super(mcServer, world, username, itemInWorldManager, playerCoreIndex, entityPlayerMP);
		player.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.maxHealth).setAttribute(100.0D);
		player.dimension = 10;
		ChunkCoordinates spawn = world.getSpawnPoint();
		player.posX = spawn.posX;
		player.posZ = spawn.posZ;
		player.posY = ElConCore.getFirstUncoveredBlock(world, (int) player.posX, (int) player.posZ) + 3;
	}
}
