package org.agecraft.creativetabs;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.lang.LanguageManager;

public class CreativeTabAgePrehistory extends CreativeTabs {
	
	public CreativeTabAgePrehistory(int id, String name) {
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Block.dirt.blockID, 1, 0);
	}
}
