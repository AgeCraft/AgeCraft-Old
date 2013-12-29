package elcon.mods.agecraft.core;

import net.minecraft.block.Block;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.agecraft.ACComponent;
import elcon.mods.agecraft.core.DustRegistry.Dust;
import elcon.mods.agecraft.core.MetalRegistry.Metal;
import elcon.mods.agecraft.core.MetalRegistry.OreType;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalBlock;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalDoor;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFence;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFenceGate;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalFluid;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalLadder;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalPillar;
import elcon.mods.agecraft.core.blocks.metal.BlockMetalTrapdoor;
import elcon.mods.agecraft.core.blocks.metal.BlockStoneOre;
import elcon.mods.agecraft.core.items.ItemGem;
import elcon.mods.agecraft.core.items.ItemIngot;
import elcon.mods.agecraft.core.items.ItemMetalBucket;
import elcon.mods.agecraft.core.items.ItemMetalDoor;
import elcon.mods.agecraft.core.items.ItemMetalDust;
import elcon.mods.agecraft.core.items.ItemMetalStick;
import elcon.mods.agecraft.core.items.ItemNugget;
import elcon.mods.core.blocks.BlockFluidMetadata;
import elcon.mods.core.fluid.FluidMetadata;
import elcon.mods.core.items.ItemBlockExtendedMetadata;

public class Metals extends ACComponent {

	public static Block ore;
	public static Block block;
	public static Block blockPillar;
	public static Block fence;
	public static Block fenceGate;
	public static Block door;
	public static Block trapdoor;
	public static Block ladder;
	public static Block designBlock;
	public static Block fluid;

	public static Item ingot;
	public static Item gem;
	public static Item stick;
	public static Item nugget;
	public static Item dust;
	public static Item bucket;

	@Override
	public void preInit() {
		// init blocks
		ore = new BlockStoneOre(2510).setUnlocalizedName("metal_ore");
		block = new BlockMetalBlock(2511).setUnlocalizedName("metal_block");
		blockPillar = new BlockMetalPillar(2512).setUnlocalizedName("metal_blockPillar");
		fence = new BlockMetalFence(2513).setUnlocalizedName("metal_fence");
		fenceGate = new BlockMetalFenceGate(2514).setUnlocalizedName("metal_fenceGate");
		door = new BlockMetalDoor(2515).setUnlocalizedName("metal_door");
		trapdoor = new BlockMetalTrapdoor(2516).setUnlocalizedName("metal_trapdoor");
		ladder = new BlockMetalLadder(2517).setUnlocalizedName("metal_ladder");

		fluid = new BlockMetalFluid(2519).setUnlocalizedName("metal_fluid");

		// register blocks
		GameRegistry.registerBlock(ore, ItemBlockExtendedMetadata.class, "AC_metal_ore");
		GameRegistry.registerBlock(block, ItemBlockExtendedMetadata.class, "AC_metal_block");
		GameRegistry.registerBlock(blockPillar, ItemBlockExtendedMetadata.class, "AC_metal_blockPillar");
		GameRegistry.registerBlock(fence, ItemBlockExtendedMetadata.class, "AC_metal_fence");
		GameRegistry.registerBlock(fenceGate, ItemBlockExtendedMetadata.class, "AC_metal_fenceGate");
		GameRegistry.registerBlock(door, ItemMetalDoor.class, "AC_metal_door");
		GameRegistry.registerBlock(trapdoor, ItemBlockExtendedMetadata.class, "AC_metal_trapdoor");
		GameRegistry.registerBlock(ladder, ItemBlockExtendedMetadata.class, "AC_metal_ladder");

		GameRegistry.registerBlock(fluid, ItemBlockExtendedMetadata.class, "AC_metal_fluid");

		// init items
		ingot = new ItemIngot(12500).setUnlocalizedName("metal_ingot");
		gem = new ItemGem(12501).setUnlocalizedName("metal_gem");
		stick = new ItemMetalStick(12502).setUnlocalizedName("metal_stick");
		nugget = new ItemNugget(12503).setUnlocalizedName("metal_nugget");
		dust = new ItemMetalDust(12504).setUnlocalizedName("metal_dust");
		bucket = new ItemMetalBucket(12505).setUnlocalizedName("metal_bucket");

		// register items
		GameRegistry.registerItem(ingot, "AC_metal_ingot");
		GameRegistry.registerItem(gem, "AC_metal_gem");
		GameRegistry.registerItem(stick, "AC_metal_stick");
		GameRegistry.registerItem(nugget, "AC_metal_nugget");
		GameRegistry.registerItem(dust, "AC_metal_dust");
		GameRegistry.registerItem(bucket, "AC_metal_bucket");
	}

	@Override
	public void init() {
		// init metals
		MetalRegistry.registerMetal(new Metal(0, "copper", OreType.METAL, 3.0F, 5.0F, 1, 5.0F, 10.0F, new ItemStack(ore, 1, 0), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xC9571C, 8, 15, 0, 96));
		MetalRegistry.registerMetal(new Metal(1, "tin", OreType.METAL, 3.0F, 5.0F, 1, 5.0F, 10.0F, new ItemStack(ore, 1, 1), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xABABAB, 8, 10, 0, 96));
		MetalRegistry.registerMetal(new Metal(2, "bronze", OreType.METAL, 3.0F, 5.0F, 1, 5.0F, 10.0F, new ItemStack(ore, 1, 2), 1, 1, false, false, true, true, true, true, 0, 0, 0, 0x764F37, 0, 0, 0, 0));
		MetalRegistry.registerMetal(new Metal(3, "silver", OreType.METAL, 3.0F, 5.0F, 6, 5.0F, 10.0F, new ItemStack(ore, 1, 3), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xD7D7D7, 6, 5, 0, 64));
		MetalRegistry.registerMetal(new Metal(4, "iron", OreType.METAL, 3.0F, 5.0F, 3, 5.0F, 10.0F, new ItemStack(ore, 1, 4), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xEFEFEF, 8, 15, 0, 64));
		MetalRegistry.registerMetal(new Metal(5, "gold", OreType.METAL, 3.0F, 5.0F, 7, 5.0F, 10.0F, new ItemStack(ore, 1, 5), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xFDFD51, 6, 2, 0, 32));
		MetalRegistry.registerMetal(new Metal(6, "zinc", OreType.METAL, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(ore, 1, 6), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xE0DACE, 12, 15, 0, 96));
		MetalRegistry.registerMetal(new Metal(7, "nickel", OreType.METAL, 3.0F, 5.0F, 3, 5.0F, 10.0F, new ItemStack(ore, 1, 7), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0x8E9F9F, 8, 5, 0, 64));
		MetalRegistry.registerMetal(new Metal(8, "aluminium", OreType.METAL, 3.0F, 5.0F, 2, 5.0F, 10.0F, new ItemStack(ore, 1, 8), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xCECECE, 8, 5, 10, 64));
		MetalRegistry.registerMetal(new Metal(9, "platinum", OreType.METAL, 3.0F, 5.0F, 5, 5.0F, 10.0F, new ItemStack(ore, 1, 9), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0xC0C0C0, 4, 3, 0, 16));
		MetalRegistry.registerMetal(new Metal(10, "lead", OreType.METAL, 3.0F, 5.0F, 5, 5.0F, 10.0F, new ItemStack(ore, 1, 10), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0x454552, 6, 5, 0, 96));
		MetalRegistry.registerMetal(new Metal(11, "steel", OreType.METAL, 3.0F, 5.0F, 6, 5.0F, 10.0F, new ItemStack(ore, 1, 11), 1, 1, false, false, true, true, true, true, 0, 0, 0, 0x7B7B7B, 0, 0, 0, 0));
		MetalRegistry.registerMetal(new Metal(12, "cobalt", OreType.METAL, 3.0F, 5.0F, 7, 5.0F, 10.0F, new ItemStack(ore, 1, 12), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0x30A0CD, 8, 4, 0, 52));
		MetalRegistry.registerMetal(new Metal(13, "mithril", OreType.METAL, 3.0F, 5.0F, 8, 5.0F, 10.0F, new ItemStack(ore, 1, 13), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0x004A8A, 6, 1, 0, 14));
		MetalRegistry.registerMetal(new Metal(14, "adamantite", OreType.METAL, 3.0F, 5.0F, 9, 5.0F, 10.0F, new ItemStack(ore, 1, 14), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0x4E8155, 4, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(15, "tungsten", OreType.METAL, 3.0F, 5.0F, 10, 5.0F, 10.0F, new ItemStack(ore, 1, 15), 1, 1, false, true, true, true, true, true, 0, 0, 0, 0x444444, 4, 5, 0, 16));
		MetalRegistry.registerMetal(new Metal(16, "uranium", OreType.METAL, 3.0F, 5.0F, 5, 5.0F, 10.0F, new ItemStack(ore, 1, 16), 1, 1, false, true, true, true, false, true, 0, 0, 0, 0x00B12C, 4, 6, 0, 96));

		MetalRegistry.registerMetal(new Metal(32, "coal", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(gem, 1, 32), 1, 1, true, true, true, true, false, true, 0, 5, 5, 0x0D0D0D, 16, 15, 0, 128));
		MetalRegistry.registerMetal(new Metal(33, "amethyst", OreType.GEM, 3.0F, 5.0F, 9, 5.0F, 10.0F, new ItemStack(gem, 1, 33), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0xA575C7, 8, 2, 0, 26));
		MetalRegistry.registerMetal(new Metal(34, "berylRed", OreType.GEM, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(gem, 1, 34), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0xF76B6B, 5, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(35, "berylYellow", OreType.GEM, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(gem, 1, 35), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0xFDD24E, 6, 2, 10, 64));
		MetalRegistry.registerMetal(new Metal(36, "berylBlue", OreType.GEM, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(gem, 1, 36), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x6A8BFD, 6, 2, 10, 32));
		MetalRegistry.registerMetal(new Metal(37, "berylGreen", OreType.GEM, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(gem, 1, 37), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x54E36A, 6, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(38, "diamond", OreType.GEM, 3.0F, 5.0F, 10, 5.0F, 10.0F, new ItemStack(gem, 1, 38), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x87E5E1, 8, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(39, "emerald", OreType.GEM, 3.0F, 5.0F, 9, 5.0F, 10.0F, new ItemStack(gem, 1, 39), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x48E073, 6, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(40, "jade", OreType.GEM, 3.0F, 5.0F, 8, 5.0F, 10.0F, new ItemStack(gem, 1, 40), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x07E1AA, 6, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(41, "lapisLazuli", OreType.GEM, 3.0F, 5.0F, 7, 5.0F, 10.0F, new ItemStack(gem, 1, 41), 4, 8, true, true, true, true, false, true, 0, 0, 0, 0x1542CC, 6, 2, 0, 28));
		MetalRegistry.registerMetal(new Metal(42, "onyx", OreType.GEM, 3.0F, 5.0F, 10, 5.0F, 10.0F, new ItemStack(gem, 1, 42), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x2D2D2D, 10, 3, 0, 16));
		MetalRegistry.registerMetal(new Metal(43, "opal", OreType.GEM, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(gem, 1, 43), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x7E90ED, 8, 2, 0, 32));
		MetalRegistry.registerMetal(new Metal(44, "quartz", OreType.GEM, 3.0F, 5.0F, 10, 5.0F, 10.0F, new ItemStack(gem, 1, 44), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0xF0EEE8, 8, 3, 0, 32));
		MetalRegistry.registerMetal(new Metal(45, "ruby", OreType.GEM, 3.0F, 5.0F, 6, 5.0F, 10.0F, new ItemStack(gem, 1, 45), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0xD8484, 6, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(46, "sapphire", OreType.GEM, 3.0F, 5.0F, 6, 5.0F, 10.0F, new ItemStack(gem, 1, 46), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x4860BC, 6, 1, 0, 16));
		MetalRegistry.registerMetal(new Metal(47, "tigerEye", OreType.GEM, 3.0F, 5.0F, 8, 5.0F, 10.0F, new ItemStack(gem, 1, 47), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x892604, 8, 3, 20, 96));
		MetalRegistry.registerMetal(new Metal(48, "topaz", OreType.GEM, 3.0F, 5.0F, 6, 5.0F, 10.0F, new ItemStack(gem, 1, 48), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0xD8CF48, 8, 2, 40, 128));
		MetalRegistry.registerMetal(new Metal(49, "turquoise", OreType.GEM, 3.0F, 5.0F, 4, 5.0F, 10.0F, new ItemStack(gem, 1, 49), 1, 1, true, true, true, true, false, true, 0, 0, 0, 0x6AD6CE, 8, 1, 0, 32));
		MetalRegistry.registerMetal(new Metal(50, "redstone", OreType.GEM, 3.0F, 5.0F, 5, 5.0F, 10.0F, new ItemStack(dust, 1, 50), 4, 5, true, true, false, true, false, true, 15, 0, 0, 0xE3260C, 8, 8, 0, 26));
		MetalRegistry.registerMetal(new Metal(51, "salt", OreType.GEM, 3.0F, 5.0F, 0, 5.0F, 10.0F, new ItemStack(dust, 1, 51), 1, 1, true, true, false, false, false, true, 0, 0, 0, 0xD8D2D4, 12, 12, 20, 128));
		MetalRegistry.registerMetal(new Metal(52, "sulphur", OreType.GEM, 3.0F, 5.0F, 6, 5.0F, 10.0F, new ItemStack(dust, 1, 52), 1, 1, true, true, false, false, false, true, 0, 0, 0, 0xF4D41F, 12, 12, 20, 128));

		FluidMetadata[] fluids = new FluidMetadata[MetalRegistry.metals.length];
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				DustRegistry.registerDust(new Dust(128 + i, metal.name, "metals." + metal.name, new ItemStack(dust.itemID, 1, i)));

				// TODO: set density, viscosity and temperature
				metal.fluid = (FluidMetadata) new FluidMetadata(metal.name, i).setLuminosity(7).setRarity(EnumRarity.common).setTemperature(1000).setBlockID(fluid);
				fluids[i] = metal.fluid;
				FluidRegistry.registerFluid(metal.fluid);
			}
		}
		((BlockFluidMetadata) fluid).setFluids(fluids);
	}

	@Override
	public void postInit() {
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			for(int i = 0; i < MetalRegistry.metals.length; i++) {
				if(MetalRegistry.metals[i] != null && MetalRegistry.metals[i].hasIngot) {
					FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket.itemID, 1, i));
				}
			}
		}
	}
}
