package org.agecraft.core.blocks.metal;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import org.agecraft.ACCreativeTabs;
import org.agecraft.core.registry.MetalRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.blocks.BlockExtendedMetadata;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockMetalBlock extends BlockExtendedMetadata {

	public BlockMetalBlock() {
		super(Material.iron);
		setStepSound(Block.soundTypeMetal);
		setCreativeTab(ACCreativeTabs.metals);
	}

	@Override
	public boolean shouldDropItems(World world, int x, int y, int z, int meta, EntityPlayer player, ItemStack stack) {
		if(stack != null) {
			if(stack.getItem() instanceof ItemTool) {
				return ((ItemTool) stack.getItem()).canHarvestBlock(stack, this, meta);
			}
		}
		return false;
	}

	@Override
	public String getLocalizedName(ItemStack stack) {
		return LanguageManager.getLocalization("metals." + MetalRegistry.instance.get((stack.getItemDamage() - (stack.getItemDamage() & 3)) / 4).name) + " " + LanguageManager.getLocalization(getUnlocalizedName(stack));
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		int type = stack.getItemDamage() & 3;
		switch(type) {
		default:
		case 0:
			return "metals.block";
		case 1:
			return "metals.bricks";
		case 2:
			return "metals.smallBricks";
		case 3:
			return "metals.blockCircle";
		}
	}
	
	@Override
	public boolean canProvidePower() {
		return true;
	}
	
	@Override
	public boolean isNormalCube(IBlockAccess blockAccess, int x, int y, int z) {
		return blockMaterial.isOpaque() && renderAsNormalBlock();
	}
	
	@Override
	public boolean canConnectRedstone(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).redstonePower > 0 && side != -1;
	}
	
	@Override
	public int isProvidingWeakPower(IBlockAccess blockAccess, int x, int y, int z, int side) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).redstonePower;
	}
	
	@Override
	public int getFireSpreadSpeed(IBlockAccess blockAccess, int x, int y, int z, ForgeDirection face) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).fireSpreadSpeed;
	}
	
	@Override
	public int getFlammability(IBlockAccess blockAccess, int x, int y, int z, ForgeDirection face) {
		int meta = getMetadata(blockAccess, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).flammability;
	}
	
	@Override
	public float getBlockHardness(World world, int x, int y, int z) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).blockHardness;
	}
	
	@Override
	public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
		int meta = getMetadata(world, x, y, z);
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).blockResistance / 5.0F;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta) {
		return MetalRegistry.instance.get((meta - (meta & 3)) / 4).blocks[meta & 3];
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(Item item, CreativeTabs creativeTab, List list) {
		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).hasBlock) {
				for(int j = 0; j < 4; j++) {
					list.add(new ItemStack(item, 1, i * 4 + j));
				}
			}
		}
	}
}
