package org.agecraft.core.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class CreativeTabMetal extends CreativeTabs {
	
	public CreativeTabMetal(int id, String name) {
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Item.getItemFromBlock(Metals.ore);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Metals.ore, 1, 0);
	}
}
