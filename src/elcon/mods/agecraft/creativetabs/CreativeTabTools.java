package elcon.mods.agecraft.creativetabs;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.Tools;
import elcon.mods.core.lang.LanguageManager;

public class CreativeTabTools extends CreativeTabs {
	
	public CreativeTabTools(int id, String name) {
		super(id, name);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslatedTabLabel() {
		return LanguageManager.getLocalization("itemGroup." + getTabLabel());
	}

	@Override
	public ItemStack getIconItemStack() {
		return new ItemStack(Tools.sword, 1, 0);
	}
}
