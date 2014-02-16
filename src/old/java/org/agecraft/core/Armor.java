package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.core.ArmorRegistry.ArmorMaterial;
import org.agecraft.core.ArmorRegistry.ArmorType;
import org.agecraft.core.items.armor.ItemArmor;
import org.agecraft.core.items.armor.ItemBoots;
import org.agecraft.core.items.armor.ItemChestplate;
import org.agecraft.core.items.armor.ItemHelmet;
import org.agecraft.core.items.armor.ItemLeggings;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.ItemStack;

public class Armor extends ACComponent {

	public static ItemArmor helmet;
	public static ItemArmor chestplate;
	public static ItemArmor leggings;
	public static ItemArmor boots;
	
	@Override
	public void preInit() {
		//init items
		helmet = (ItemArmor) new ItemHelmet(12600).setUnlocalizedName("armor_helmet");
		chestplate = (ItemArmor) new ItemChestplate(12601).setUnlocalizedName("armor_chestplate");
		leggings = (ItemArmor) new ItemLeggings(12602).setUnlocalizedName("armor_leggings");
		boots = (ItemArmor) new ItemBoots(12603).setUnlocalizedName("armor_boots");
		
		//register items
		GameRegistry.registerItem(helmet, "AC_armor_helmet");
		GameRegistry.registerItem(chestplate, "AC_armor_cheastplate");
		GameRegistry.registerItem(leggings, "AC_armor_leggings");
		GameRegistry.registerItem(boots, "AC_armor_boots");
	}
	
	@Override
	public void init() {
		//register armor types
		ArmorRegistry.registerArmorType(new ArmorType(0, "helmet", true));
		ArmorRegistry.registerArmorType(new ArmorType(1, "chestplate", true));
		ArmorRegistry.registerArmorType(new ArmorType(2, "leggings", false));
		ArmorRegistry.registerArmorType(new ArmorType(3, "boots", false));
		//ArmorRegistry.registerArmorType(new ArmorType(4, "cape", false));
		//ArmorRegistry.registerArmorType(new ArmorType(5, "gloves", false));
		//ArmorRegistry.registerArmorType(new ArmorType(6, "necklace", false));
		//ArmorRegistry.registerArmorType(new ArmorType(7, "bracelet", false));
		//ArmorRegistry.registerArmorType(new ArmorType(8, "ring", false));
		
		//register armor materials
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(128, "copper", "metals.copper", new ItemStack(Metals.ingot, 1, 0), false, false, 0xFFFFFF, 153));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(130, "bronze", "metals.bronze", new ItemStack(Metals.ingot, 1, 2), false, false, 0xFFFFFF, 277));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(131, "silver", "metals.silver", new ItemStack(Metals.ingot, 1, 3), false, false, 0xFFFFFF, 260));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(132, "iron", "metals.iron", new ItemStack(Metals.ingot, 1, 4), false, false, 0xFFFFFF, 314));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(133, "gold", "metals.gold", new ItemStack(Metals.ingot, 1, 5), false, false, 0xFFFFFF, 40));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(137, "platinum", "metals.platinum", new ItemStack(Metals.ingot, 1, 9), false, false, 0xFFFFFF, 443));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(139, "steel", "metals.steel", new ItemStack(Metals.ingot, 1, 11), false, false, 0xFFFFFF, 820));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(140, "cobalt", "metals.cobalt", new ItemStack(Metals.ingot, 1, 12), false, false, 0xFFFFFF, 1168));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(141, "mithril", "metals.mithril", new ItemStack(Metals.ingot, 1, 13), false, false, 0xFFFFFF, 1807));
		ArmorRegistry.registerArmorMaterial(new ArmorMaterial(142, "adamantite", "metals.adamantite", new ItemStack(Metals.ingot, 1, 14), false, false, 0xFFFFFF, 2352));
	}
}
