package elcon.mods.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class InventoryCraftResult implements IInventory {

	private ItemStack stack;
	
	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return stack;
	}
	

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(stack != null) {
			ItemStack oldStack = stack;
			stack = null;
			return oldStack;
		}
		return null;
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		if(stack != null) {
			ItemStack oldStack = stack;
			stack = null;
			return oldStack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {
		this.stack = stack;
	}

	@Override
	public String getInvName() {
		return "Result";
	}

	@Override
	public boolean isInvNameLocalized() {
		return true;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}
	
	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}
}
