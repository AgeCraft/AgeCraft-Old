package elcon.mods.agecraft.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.lang.LanguageManager;

public class CreativeTabAgeCraft extends CreativeTabs {

	public CreativeTabAgeCraft(int id) {
		super(id, "AgeCraft");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}
	
	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Item.paper.itemID, 1, 0);
	}
}
