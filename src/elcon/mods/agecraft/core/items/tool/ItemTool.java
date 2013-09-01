package elcon.mods.agecraft.core.items.tool;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.ACCreativeTabs;
import elcon.mods.agecraft.assets.resources.ResourcesCore;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.ToolRegistry.Tool;
import elcon.mods.agecraft.core.ToolRegistry.ToolCreativeEntry;
import elcon.mods.agecraft.lang.LanguageManager;

public class ItemTool extends Item {

	public ItemTool(int id) {
		super(id - 256);
		setMaxStackSize(1);
		setCreativeTab(ACCreativeTabs.tools);
	}

	@Override
	public String getItemDisplayName(ItemStack stack) {
		return getLocalizedName(stack);
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization(ToolRegistry.toolMaterials[getToolMaterial(stack)].localization) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return "tools.type." + ToolRegistry.tools[getToolType(stack)].name;
	}

	@Override
	public String getUnlocalizedName() {
		return "tools.type.default";
	}

	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase entityLiving, EntityLivingBase entity) {
		stack.damageItem(ToolRegistry.tools[getToolType(stack)].damageEntity, entity);
		return true;
	}

	@Override
	public boolean onBlockDestroyed(ItemStack stack, World world, int blockID, int x, int y, int z, EntityLivingBase entity) {
		stack.damageItem(ToolRegistry.tools[getToolType(stack)].damageBlock, entity);
		return true;
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		return getToolDurability(stack);
	}

	@Override
	public float getStrVsBlock(ItemStack stack, Block block, int metadata) {
		Block[] blocksEffectiveAgainst = ToolRegistry.tools[getToolType(stack)].blocksEffectiveAgainst;
		for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
			if(blocksEffectiveAgainst[i] == block) {
				return getToolEfficiency(stack);
			}
		}
		return 1.0F;
	}

	public boolean canHarvestBlock(ItemStack stack, Block block, int meta) {
		return false;
	}
	
	@Override
	public boolean canHarvestBlock(Block block, ItemStack stack) {
		Block[] blocksEffectiveAgainst = ToolRegistry.tools[getToolType(stack)].blocksEffectiveAgainst;
		for(int i = 0; i < blocksEffectiveAgainst.length; ++i) {
			if(blocksEffectiveAgainst[i] == block) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(entity.canAttackWithItem()) {
			if(!entity.func_85031_j(player)) {
				float damage = (float) getToolAttackStrength(stack);
				int i = 0;
				float extraDamage = 0.0F;
				if(entity instanceof EntityLivingBase) {
					extraDamage = EnchantmentHelper.getEnchantmentModifierLiving(player, (EntityLivingBase) entity);
					i += EnchantmentHelper.getKnockbackModifier(player, (EntityLivingBase) entity);
				}
				if(player.isSprinting()) {
					i++;
				}
				if(damage > 0.0F || extraDamage > 0.0F) {
					boolean criricalHit = player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater() && !player.isPotionActive(Potion.blindness) && player.ridingEntity == null && entity instanceof EntityLivingBase;
					if(criricalHit && damage > 0.0F) {
						damage *= 1.5F;
					}
					damage += extraDamage;
					boolean fire = false;
					int j = EnchantmentHelper.getFireAspectModifier(player);
					if(entity instanceof EntityLivingBase && j > 0 && !entity.isBurning()) {
						fire = true;
						entity.setFire(1);
					}
					boolean attack = entity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
					if(attack) {
						if(i > 0) {
							entity.addVelocity((double) (-MathHelper.sin(player.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F), 0.1D, (double) (MathHelper.cos(player.rotationYaw * (float) Math.PI / 180.0F) * (float) i * 0.5F));
							player.motionX *= 0.6D;
							player.motionZ *= 0.6D;
							player.setSprinting(false);
						}
						if(criricalHit) {
							player.onCriticalHit(entity);
						}
						if(extraDamage > 0.0F) {
							player.onEnchantmentCritical(entity);
						}
						if(damage >= 18.0F) {
							player.triggerAchievement(AchievementList.overkill);
						}
						player.func_130011_c(entity);
						if(entity instanceof EntityLivingBase) {
							EnchantmentThorns.func_92096_a(player, (EntityLivingBase) entity, player.worldObj.rand);
						}
					}

					ItemStack itemstack = player.getCurrentEquippedItem();
					Object object = entity;
					
					if(entity instanceof EntityDragonPart) {
						IEntityMultiPart entityMultipart = ((EntityDragonPart) entity).entityDragonObj;
						if(entityMultipart != null && entityMultipart instanceof EntityLivingBase) {
							object = (EntityLivingBase) entityMultipart;
						}
					}
					if(itemstack != null && object instanceof EntityLivingBase) {
						itemstack.hitEntity((EntityLivingBase) object, player);
						if(itemstack.stackSize <= 0) {
							player.destroyCurrentEquippedItem();
						}
					}
					if(entity instanceof EntityLivingBase) {
						player.addStat(StatList.damageDealtStat, Math.round(damage * 10.0F));
						if(j > 0 && attack) {
							entity.setFire(j * 4);
						} else if(fire) {
							entity.extinguish();
						}
					}
					player.addExhaustion(0.3F);
				}
			}
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isFull3D() {
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses() {
		return true;
	}

	@Override
	public int getRenderPasses(int metadata) {
		return 3;
	}

	@Override
	public Icon getIcon(ItemStack stack, int pass) {
		Tool tool = ToolRegistry.tools[getToolType(stack)];
		if(pass == 0 && tool.hasRod) {
			return ToolRegistry.toolRodMaterials[getToolMaterial(stack)].icons[tool.id];
		} else if(pass == 1 && tool.hasHead) {
			return ToolRegistry.toolMaterials[getToolMaterial(stack)].icons[tool.id];
		} else if(pass == 2 && tool.hasEnhancements) {
			return ResourcesCore.emptyTexture;
			//TODO
			//return ToolRegistry.toolEnhancementMaterials[getToolMaterial(stack)].icons[tool.id];
		}
		return ResourcesCore.emptyTexture;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister iconRegister) {
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs creativeTabs, List list) {
		if(ToolRegistry.toolCreativeEntries.containsKey(id - 12520)) {
			ArrayList<ToolCreativeEntry> entries = ToolRegistry.toolCreativeEntries.get(id - 12520);
			for(ToolCreativeEntry entry : entries) {
				ItemStack stack = new ItemStack(id, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound nbt2 = new NBTTagCompound();
				nbt2.setInteger("Type", entry.tool);
				nbt2.setInteger("Material", entry.toolMaterial);
				nbt2.setInteger("RodMaterial", entry.toolRodMaterial);
				nbt2.setInteger("EnhancementMaterial", entry.toolEnhancement);
				nbt.setTag("Tool", nbt2);
				stack.setTagCompound(nbt);
				list.add(stack);
			}
		}
	}

	public NBTTagCompound getToolNBT(ItemStack stack) {
		NBTTagCompound nbt = stack.stackTagCompound;
		if(nbt == null) {
			nbt = new NBTTagCompound();
			stack.setTagCompound(nbt);
		}
		if(!nbt.hasKey("Tool")) {
			NBTTagCompound nbt2 = new NBTTagCompound();
			nbt2.setInteger("Type", 0);
			nbt2.setInteger("Material", 0);
			nbt2.setInteger("RodMaterial", 0);
			nbt2.setInteger("EnhancementMaterial", 0);
			nbt.setCompoundTag("Tool", nbt2);
		}
		return nbt.getCompoundTag("Tool");
	}

	public int getToolType(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("Type");
	}

	public int getToolMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("Material");
	}

	public int getToolRodMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("RodMaterial");
	}

	// TODO: add enhancement functionality
	public int getToolEnhancementMaterial(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return nbt.getInteger("EnhancementMaterial");
	}

	public int getToolDurability(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].durability + ToolRegistry.toolRodMaterials[getToolRodMaterial(stack)].durability;
	}

	public float getToolEfficiency(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].efficiency + ToolRegistry.toolRodMaterials[getToolRodMaterial(stack)].efficiency;
	}

	public int getToolAttackStrength(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].attackStrength + ToolRegistry.toolRodMaterials[getToolRodMaterial(stack)].attackStrength;
	}
	
	public int getToolHarvestLevel(ItemStack stack) {
		NBTTagCompound nbt = getToolNBT(stack);
		return ToolRegistry.toolMaterials[getToolMaterial(stack)].harvestLevel;
	}
}
