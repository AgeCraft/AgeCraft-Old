package org.agecraft.prehistory.items;

import org.agecraft.ACCreativeTabs;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

import com.google.common.collect.Multimap;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.core.lang.LanguageManager;

public class ItemRockPickaxe extends Item {

	private Icon icon;

	public ItemRockPickaxe(int id) {
		super(id);
		setMaxStackSize(1);
		setMaxDamage(3);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getItemStackDisplayName(stack);
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		return LanguageManager.getLocalization(getUnlocalizedName(stack));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return getUnlocalizedName();
	}
	
	@Override
	public String getUnlocalizedName() {
		return "item.rockPickaxe.name";
	}

	@Override
	public boolean canHarvestBlock(Block block) {
		return block != null && (block.blockID == Block.stone.blockID || block.blockID == Block.cobblestone.blockID);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block) {
		return block != null && (block.blockID == Block.stone.blockID || block.blockID == Block.cobblestone.blockID) ? 1.0F : 0.0F;
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entity, EntityLivingBase entityLiving) {
		stack.damageItem(2, entityLiving);
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int id, int x, int y, int z, EntityLivingBase entity) {
		stack.damageItem(1, entity);
		return true;
	}
	
	@Override
	public boolean getIsRepairable(ItemStack stack1, ItemStack stack2) {
		return false;
	}
	
	@Override
	public Multimap getItemAttributeModifiers() {
		Multimap multimap = super.getItemAttributeModifiers();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Tool modifier", 1.0D, 0));
		return multimap;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int meta) {
		return icon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/rockPickaxe");
	}
}
