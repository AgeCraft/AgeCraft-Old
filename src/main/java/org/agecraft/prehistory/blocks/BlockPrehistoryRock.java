package org.agecraft.prehistory.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import org.agecraft.ACCreativeTabs;
import org.agecraft.prehistory.PrehistoryAge;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.elconqore.lang.LanguageManager;

public class BlockPrehistoryRock extends Block {

	public static class RockShape {
		
		public double minX;
		public double minY;
		public double minZ;
		public double maxX;
		public double maxY;
		public double maxZ;
		
		public RockShape(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
			this.minX = minX;
			this.minY = minY;
			this.minZ = minZ;
			this.maxX = maxX;
			this.maxY = maxY;
			this.maxZ = maxZ;
		}
	}
	
	public static RockShape[] shapes = new RockShape[] {
		new RockShape(0.35D, 0.0D, 0.35D, 0.75D, 0.2D, 0.75D),
		new RockShape(0.1D, 0.0D, 0.1D, 0.5D, 0.2D, 0.5D),
		new RockShape(0.5D, 0.0D, 0.5D, 0.9D, 0.2D, 0.9D),
		new RockShape(0.1D, 0.0D, 0.5D, 0.5D, 0.2D, 0.9D),
		new RockShape(0.5D, 0.0D, 0.1D, 0.9D, 0.2D, 0.4D),
		new RockShape(0.15D, 0.0D, 0.15D, 0.85D, 0.25D, 0.85D),
		new RockShape(0.1D, 0.0D, 0.1D, 0.3D, 0.2D, 0.3D),
		new RockShape(0.7D, 0.0D, 0.7D, 0.9D, 0.2D, 0.9D),
		new RockShape(0.1D, 0.0D, 0.7D, 0.3D, 0.2D, 0.9D),
		new RockShape(0.7D, 0.0D, 0.1D, 0.9D, 0.2D, 0.3D),
		new RockShape(0.35D, 0.0D, 0.55D, 0.75D, 0.2D, 0.75D),
		new RockShape(0.35D, 0.0D, 0.35D, 0.65D, 0.2D, 0.75D),
		new RockShape(0.35D, 0.0D, 0.35D, 0.75D, 0.2D, 0.55D),
		new RockShape(0.4D, 0.0D, 0.35D, 0.75D, 0.2D, 0.75D),
		new RockShape(0.45D, 0.0D, 0.45D, 0.55D, 0.2D, 0.55D),
		new RockShape(0.35D, 0.0D, 0.75D, 0.65D, 0.2D, 0.95D)
	};
	
	private IIcon icon;
	
	public BlockPrehistoryRock() {
		super(Material.rock);
		setHardness(0.4F);
		setStepSound(Block.soundTypeStone);
		setBlockBounds(0.35F, 0.0F, 0.35F, 0.75F, 0.2F, 0.75F);
		setCreativeTab(ACCreativeTabs.prehistoryAge);
	}
	
	@Override
	public String getLocalizedName() {
		return LanguageManager.getLocalization(getUnlocalizedName());
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile.rock.name";
	}
	
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axisAlignedBB, List list, Entity entity) {
		setBlockBoundsBasedOnState(world, x, y, z);
		super.addCollisionBoxesToList(world, x, y, z, axisAlignedBB, list, entity);
	}
	
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess blockAccess, int x, int y, int z) {
		RockShape shape = shapes[blockAccess.getBlockMetadata(x, y, z)];
		setBlockBounds((float) shape.minX, (float) shape.minY, (float) shape.minZ, (float) shape.maxX, (float) shape.maxY, (float) shape.maxZ);
	}
	
	@Override
	public boolean canHarvestBlock(EntityPlayer player, int meta) {
		return true;
	}
	
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z) {
		return super.canPlaceBlockAt(world, x, y, z) && canBlockStay(world, x, y, z);
	}
	
	@Override
	public boolean canBlockStay(World world, int x, int y, int z) {
		Block block = world.getBlock(x, y - 1, z);
		if(block != null && block != this && world.getBlock(x, y - 1, z).isOpaqueCube()) {
			return true;
		}
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, new ItemStack(PrehistoryAge.rock, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		if(!canBlockStay(world, x, y, z)) {
			dropBlockAsItem(world, x, y, z, new ItemStack(PrehistoryAge.rock, 1, 0));
            world.setBlockToAir(x, y, z);
		}
	}
	
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
		if(!world.isRemote) {
			return world.rand.nextInt(16);
		}
		return 0;
	}
	
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune) {
		ArrayList<ItemStack> list = super.getDrops(world, x, y, z, metadata, fortune);
		if(world.rand.nextFloat() < 0.1F) {
			list.add(new ItemStack(Items.flint, 1, 0));
		}
		return list;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int i, int j) {
		return icon;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		icon = iconRegister.registerIcon("agecraft:ages/prehistory/rock");
	}
	
	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return 200;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
