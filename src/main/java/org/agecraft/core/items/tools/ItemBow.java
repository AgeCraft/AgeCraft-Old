package org.agecraft.core.items.tools;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import org.agecraft.core.AgeCraftCoreClient;
import org.agecraft.core.Tools;
import org.agecraft.core.registry.ToolEnhancementMaterialRegistry;
import org.agecraft.core.registry.ToolRegistry;
import org.agecraft.core.registry.ToolRegistry.Tool;
import org.agecraft.core.registry.ToolRodMaterialRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.EQUtil;

public class ItemBow extends ItemTool {

	public IIcon[][] icons = new IIcon[256][4];
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World world, EntityPlayer player, int itemInUseCount) {
		/*int duration = getMaxItemUseDuration(stack) - itemInUseCount;
		boolean infiniteArrows = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
		if(infiniteArrows || player.inventory.hasItem(Tools.arrow)) {
			int arrowSlot = findArrow(player.inventory);
			ItemStack arrowStack = arrowSlot >= 0 ? player.inventory.getStackInSlot(arrowSlot) : null;
			if(arrowStack == null && player.capabilities.isCreativeMode) {
				ItemStack temp = new ItemStack(Tools.arrow, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("Type", 19);
				tag.setInteger("Material", 142);
				tag.setInteger("RodMaterial", 142);
				tag.setInteger("EnhancementMaterial", 0);
				nbt.setTag("Tool", tag);
				temp.setTagCompound(nbt);
				arrowStack = temp;
			}
			float multiplier = (float) duration / 20.0F;
			multiplier = (multiplier * multiplier + multiplier * 2.0F) / 3.0F;
			if((double) multiplier < 0.1D) {
				return;
			}
			if(multiplier > 1.0F) {
				multiplier = 1.0F;
			}
			EntityArrow entityArrow = new EntityArrow(world, player, multiplier * 2.0F);
			entityArrow.setMaterials(getToolMaterial(arrowStack), getToolRodMaterial(arrowStack));
			if(multiplier == 1.0F) {
				entityArrow.setIsCritical(true);
			}
			entityArrow.damage = Tools.arrow.getToolAttackStrength(arrowStack) * 0.5D;
			int punch = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
			if(punch > 0) {
				entityArrow.knockbackStrength = punch;
			}
			if(EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
				entityArrow.setFire(100);
			}
			stack.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + multiplier * 0.5F);
			if(infiniteArrows) {
				entityArrow.canBePickedUp = 2;
			} else {
				arrowStack.stackSize--;
				if(arrowStack.stackSize <= 0) {
					arrowStack = null;
				}
				player.inventory.setInventorySlotContents(arrowSlot, arrowStack);
			}
			if(!world.isRemote) {
				world.spawnEntityInWorld(entityArrow);
			}
		}*/
		//TODO: this
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 72000;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.bow;
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		if(player.capabilities.isCreativeMode || findArrow(player.inventory) >= 0) {
			int arrowSlot = findArrow(player.inventory);
			ItemStack arrowStack = arrowSlot >= 0 ? player.inventory.getStackInSlot(arrowSlot) : null;
			NBTTagCompound tag = new NBTTagCompound();
			if(arrowStack != null) {
				tag.setInteger("Material", Tools.arrow.getToolMaterial(arrowStack));
				tag.setInteger("RodMaterial", Tools.arrow.getToolRodMaterial(arrowStack));
			} else {
				tag.setInteger("Material", 142);
				tag.setInteger("RodMaterial", 142);
			}
			stack.getTagCompound().setTag("Arrow", tag);
			player.setItemInUse(stack, getMaxItemUseDuration(stack));
		}
		return stack;
	}
	
	public static int findArrow(InventoryPlayer inventory) {
		ItemStack stackArrow = null;
		int slot = -1;
		for(int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack stackSlot = inventory.getStackInSlot(i);
			if(stackSlot != null && Item.getIdFromItem(stackSlot.getItem()) == Item.getIdFromItem(Tools.arrow)) {
				if(stackArrow == null || Tools.arrow.getToolAttackStrength(stackSlot) > Tools.arrow.getToolAttackStrength(stackArrow)) {
					stackArrow = stackSlot;
					slot = i;
				}
			}
		}
		return slot;
	}
	
	@Override
	public int getRenderPasses(int metadata) {
		return 4;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass) {
		Tool tool = ToolRegistry.instance.get(getToolType(stack));
		if(renderPass == 0) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				if(Minecraft.getMinecraft().thePlayer.getItemInUse() != null && ItemStack.areItemStacksEqual(stack, Minecraft.getMinecraft().thePlayer.getItemInUse())) {
					int duration = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
					if(duration >= 18) {
						return icons[toolRodMaterial][3];
					} else if(duration > 13) {
						return icons[toolRodMaterial][2];
					} else if(duration > 0) {
						return icons[toolRodMaterial][1];
					}
				}
				return icons[toolRodMaterial][0];
			}
		} else if(renderPass == 1) {
			int toolEnhancement = getToolEnhancementMaterial(stack);
			if(toolEnhancement != -1 && ToolEnhancementMaterialRegistry.instance.get(toolEnhancement) != null) {
				return ToolEnhancementMaterialRegistry.instance.get(toolEnhancement).icons[tool.id];
			}
		} else if(renderPass == 2 || renderPass == 3) {
			if(Minecraft.getMinecraft().thePlayer.getItemInUse() != null && ItemStack.areItemStacksEqual(stack, Minecraft.getMinecraft().thePlayer.getItemInUse())) {
				NBTTagCompound tag = stack.getTagCompound().getCompoundTag("Arrow");
				int toolMaterial = tag.getInteger("Material");
				int toolRodMaterial = tag.getInteger("RodMaterial");
				int duration = Minecraft.getMinecraft().thePlayer.getItemInUseDuration();
				if(renderPass == 2) {
					if(duration >= 18) {
						return ((ItemArrow) Tools.arrow).iconsRod[toolRodMaterial][3];
					} else if(duration > 13) {
						return ((ItemArrow) Tools.arrow).iconsRod[toolRodMaterial][2];
					} else if(duration > 0) {
						return ((ItemArrow) Tools.arrow).iconsRod[toolRodMaterial][1];
					}
				} else if(renderPass == 3) {
					if(duration >= 18) {
						return ((ItemArrow) Tools.arrow).iconsHead[toolMaterial][3];
					} else if(duration > 13) {
						return ((ItemArrow) Tools.arrow).iconsHead[toolMaterial][2];
					} else if(duration > 0) {
						return ((ItemArrow) Tools.arrow).iconsHead[toolMaterial][1];
					}
				}
			}
		}
		return AgeCraftCoreClient.emptyTexture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining) {
		if(renderPass == 0) {
			int toolRodMaterial = getToolRodMaterial(stack);
			if(toolRodMaterial != -1 && icons[toolRodMaterial][0] != null) {
				if(usingItem != null && ItemStack.areItemStacksEqual(stack, usingItem)) {
					int duration = player.getItemInUseDuration();
					if(duration >= 18) {
						return icons[toolRodMaterial][3];
					} else if(duration > 13) {
						return icons[toolRodMaterial][2];
					} else if(duration > 0) {
						return icons[toolRodMaterial][1];
					}
				}
				return icons[toolRodMaterial][0];
			}
		} else if(renderPass == 2 || renderPass == 3) {
			if(usingItem != null && ItemStack.areItemStacksEqual(stack, usingItem)) {
				NBTTagCompound tag = usingItem.getTagCompound().getCompoundTag("Arrow");
				int toolMaterial = tag.getInteger("Material");
				int toolRodMaterial = tag.getInteger("RodMaterial");
				int duration = player.getItemInUseDuration();
				if(renderPass == 2) {
					if(duration >= 18) {
						return ((ItemArrow) Tools.arrow).iconsRod[toolRodMaterial][3];
					} else if(duration > 13) {
						return ((ItemArrow) Tools.arrow).iconsRod[toolRodMaterial][2];
					} else if(duration > 0) {
						return ((ItemArrow) Tools.arrow).iconsRod[toolRodMaterial][1];
					}
				} else if(renderPass == 3) {
					if(duration >= 18) {
						return ((ItemArrow) Tools.arrow).iconsHead[toolMaterial][3];
					} else if(duration > 13) {
						return ((ItemArrow) Tools.arrow).iconsHead[toolMaterial][2];
					} else if(duration > 0) {
						return ((ItemArrow) Tools.arrow).iconsHead[toolMaterial][1];
					}
				}
			}
		}
		return getIcon(stack, renderPass);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister iconRegister) {
		for(int i = 0; i < ToolRodMaterialRegistry.instance.getAll().length; i++) {
			if(ToolRodMaterialRegistry.instance.get(i) != null) {
				for(int j = 0; j < 4; j++) {
					icons[i][j] = iconRegister.registerIcon("agecraft:tools/sticks/bow/" + ToolRodMaterialRegistry.instance.get(i).name + "/bow" + EQUtil.firstUpperCase(ToolRodMaterialRegistry.instance.get(i).name) + Integer.toString(j));
				}
			}
		}
	}
}
