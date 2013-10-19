package elcon.mods.agecraft.prehistory.tileentities;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidContainerRegistry;
import elcon.mods.agecraft.core.fluids.FluidTankTile;

public class TileEntityPot extends TileEntity {
	
	public boolean hasLid;
	public FluidTankTile fluid;
	public ItemStack dust;
	
	public TileEntityPot() {
		fluid = new FluidTankTile("pot", this, FluidContainerRegistry.BUCKET_VOLUME);
	}
	
	@Override
	public Packet getDescriptionPacket() {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		try {
			dos.writeInt(201);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			
			dos.writeBoolean(hasLid);
			
			dos.writeBoolean(fluid.getFluid() != null && fluid.getFluid().getFluid() != null);
			if(fluid.getFluid() != null && fluid.getFluid().getFluid() != null) {
				dos.writeInt(fluid.getFluid().getFluid().getID());
				dos.writeInt(fluid.getFluid().amount);
			}
			
			dos.writeBoolean(dust != null);
			if(dust != null) {
				dos.writeInt(dust.itemID);
				dos.writeInt(dust.stackSize);
				dos.writeInt(dust.getItemDamage());
				
				dos.writeBoolean(dust.hasTagCompound());
				if(dust.hasTagCompound()) {
					try {
						NBTBase.writeNamedTag(dust.stackTagCompound, dos);
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "ACTile";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		packet.isChunkDataPacket = true;
		return packet;
	}
	
	public boolean isEmpty() {
		return !hasLiquid() && !hasDust();
	}
	
	public boolean hasLiquid() {
		return fluid != null && !fluid.isEmpty();
	}
	
	public boolean hasDust() {
		return dust != null;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		hasLid = nbt.getBoolean("HasLid");
		if(nbt.hasKey("Fluid")) {
			fluid.readFromNBT(nbt.getCompoundTag("Fluid"));
		}
		if(nbt.hasKey("Dust")) {
			dust = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Dust"));
		}
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);
		nbt.setBoolean("HasLid", hasLid);
		if(fluid != null) {
			NBTTagCompound tag = new NBTTagCompound();
			fluid.writeToNBT(tag);
			nbt.setCompoundTag("Fluid", tag);
		}
		if(dust != null) {
			NBTTagCompound tag = new NBTTagCompound();
			dust.writeToNBT(tag);
			nbt.setCompoundTag("Dust", tag);
		}
	}
}
