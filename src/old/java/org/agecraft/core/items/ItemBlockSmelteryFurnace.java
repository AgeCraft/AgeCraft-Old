package org.agecraft.core.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockSmelteryFurnace extends ItemBlockWithMetadata {

	public ItemBlockSmelteryFurnace(int id, Block block) {
		super(id, block);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getLocalizedName();
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return Block.blocksList[getBlockID()].getUnlocalizedName();
	}
}
