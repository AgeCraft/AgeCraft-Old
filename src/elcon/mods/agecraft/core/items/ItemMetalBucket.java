package elcon.mods.agecraft.core.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraftforge.fluids.Fluid;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.core.lang.LanguageManager;

public class ItemMetalBucket extends ItemBucket {

	public ItemMetalBucket(int id) {
		super(id);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization("trees." + MetalRegistry.metals[stack.getItemDamage()].name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tools.bucket";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconIndex(ItemStack stack) {
		return MetalRegistry.metals[stack.getItemDamage()].bucket;
	}
	
	@Override
	public List<Fluid> getFluidBlacklist() {
		return new ArrayList<Fluid>();
	}
}
