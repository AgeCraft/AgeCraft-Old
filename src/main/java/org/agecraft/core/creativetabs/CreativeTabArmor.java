package org.agecraft.core.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.agecraft.core.Armor;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class CreativeTabArmor extends CreativeTabs {

	public CreativeTabArmor(int id, String name) {
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Item getTabIconItem() {
		return Armor.chestplate;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		ItemStack stack = new ItemStack(Armor.chestplate, 1, 0);
		NBTTagCompound nbt = new NBTTagCompound();
		NBTTagCompound nbt2 = new NBTTagCompound();
		nbt2.setInteger("Type", 0);
		nbt2.setInteger("Material", 128);
		nbt.setTag("Armor", nbt2);
		stack.setTagCompound(nbt);
		return stack;
	}
}
