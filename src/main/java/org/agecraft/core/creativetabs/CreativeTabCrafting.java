package org.agecraft.core.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class CreativeTabCrafting extends CreativeTabs {
	
	public CreativeTabCrafting(int id, String name) {
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		//TODO
		return Item.getItemFromBlock(Blocks.crafting_table);
		//return Item.getItemFromBlock(Crafting.workbench);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		//TODO
		return new ItemStack(Blocks.crafting_table);
		//return new ItemStack(Crafting.workbench, 1, 15);
	}
}
