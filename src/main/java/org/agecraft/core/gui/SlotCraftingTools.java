package org.agecraft.core.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class SlotCraftingTools extends SlotCrafting {

	public IContainerTools container;
	
	public SlotCraftingTools(EntityPlayer player, IContainerTools container, IInventory craftMatrix, IInventory result, int id, int x, int y) {
		super(player, craftMatrix, result, id, x, y);
		this.container = container;
	}
	
	@Override
	public void onPickupFromSlot(EntityPlayer currentPlayer, ItemStack stack) {
		super.onPickupFromSlot(currentPlayer, stack);
		container.damageTools();
	}
}
