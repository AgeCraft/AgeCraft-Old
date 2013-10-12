package elcon.mods.agecraft.prehistory;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;

import com.google.common.io.ByteArrayDataInput;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.IACPacketHandlerClient;
import elcon.mods.agecraft.prehistory.tileentities.TileEntityCampfire;

@SideOnly(Side.CLIENT)
public class PrehistoryPacketHandlerClient implements IACPacketHandlerClient {

	@Override
	public void handlePacketClient(int packetID, ByteArrayDataInput dat, World world) {
		switch(packetID) {
		case 200:
			handleTileEntityCampfire(world, dat);
			break;
		}
	}

	private void handleTileEntityCampfire(World world, ByteArrayDataInput dat) {
		int x = dat.readInt();
		int y = dat.readInt();
		int z = dat.readInt();
		
		TileEntityCampfire tile = (TileEntityCampfire) world.getBlockTileEntity(x, y, z);
		if(tile == null) {
			tile = new TileEntityCampfire();
			world.setBlockTileEntity(x, y, z, tile);
		}
		
		tile.burnTime = dat.readInt();
		tile.hasFire = dat.readBoolean();
		
		tile.logCount = dat.readInt();
		tile.currentLogIndex = dat.readInt();
		
		tile.frameDirection = dat.readByte();
		tile.frameStage = dat.readByte();
		tile.frameType[0] = dat.readInt();
		tile.frameType[1] = dat.readInt();
		tile.frameType[2] = dat.readInt();
		
		if(dat.readBoolean()) {
			tile.spitStack = new ItemStack(dat.readInt(), 1, dat.readInt());
			if(dat.readBoolean()) {
				try {
					tile.spitStack.setTagCompound((NBTTagCompound) NBTBase.readNamedTag(dat));
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			tile.spitStack = null;
		}
		world.markBlockForRenderUpdate(x, y, z);
		world.updateLightByType(EnumSkyBlock.Block, x, y, z);
	}
}
