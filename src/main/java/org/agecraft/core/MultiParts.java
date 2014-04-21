package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.core.blocks.stone.BlockColoredStoneBrick;
import org.agecraft.core.blocks.stone.BlockStoneBrick;
import org.agecraft.core.multiparts.MicroMaterialColor;
import org.agecraft.core.multiparts.MicroMaterialColorOverlay;
import org.agecraft.core.multiparts.MicroMaterialLeaves;
import org.agecraft.core.registry.MetalRegistry;
import org.agecraft.core.registry.TreeRegistry;

import codechicken.microblock.BlockMicroMaterial;
import codechicken.microblock.MicroMaterialRegistry;
import elcon.mods.elconqore.EQUtil;

public class MultiParts extends ACComponent {

	public MultiParts() {
		super("multiparts", false);
	}

	@Override
	public void init() {
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stone, 0), "stone_stone");
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stone, 1), "stone_stoneCracked");
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stone, 2), "stone_stoneMossy");
		for(int i = 0; i < 8; i++) {
			MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stoneBrick, i), "stone_stoneBrick" + EQUtil.firstUpperCase(BlockStoneBrick.types[i]));
		}
		MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Stone.stoneBrickPillar, 0), "stone_stoneBrickPillar");
		for(int i = 0; i < 16; i++) {
			MicroMaterialRegistry.registerMaterial(new MicroMaterialColor(Stone.coloredStone, i), "stone_coloredStone_" + Integer.toString(i));
			MicroMaterialRegistry.registerMaterial(new MicroMaterialColor(Stone.coloredStoneCracked, i), "stone_coloredStoneCracked_" + Integer.toString(i));
			MicroMaterialRegistry.registerMaterial(new MicroMaterialColorOverlay(Stone.coloredStoneMossy, i), "stone_coloredStoneMossy_" + Integer.toString(i));
			for(int j = 0; j < 8; j++) {
				MicroMaterialRegistry.registerMaterial(new MicroMaterialColorOverlay(Stone.coloredStoneBrick, j + i * 8), "stone_coloredStoneBrick" + EQUtil.firstUpperCase(BlockColoredStoneBrick.types[j]) + "_" + Integer.toString(i));
			}
			MicroMaterialRegistry.registerMaterial(new MicroMaterialColor(Stone.coloredStoneBrickPillar, i * 4), "stone_coloredStonePillar_" + Integer.toString(i));
		}

		for(int i = 0; i < TreeRegistry.instance.getAll().length; i++) {
			if(TreeRegistry.instance.get(i) != null) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Trees.wood, i * 4), "trees_wood_" + TreeRegistry.instance.get(i).name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Trees.planks, i), "trees_planks_" + TreeRegistry.instance.get(i).name);
				MicroMaterialRegistry.registerMaterial(new MicroMaterialLeaves(Trees.leaves, i * 4), "trees_leaves_" + TreeRegistry.instance.get(i).name);
			}
		}

		for(int i = 0; i < MetalRegistry.instance.getAll().length; i++) {
			if(MetalRegistry.instance.get(i) != null && MetalRegistry.instance.get(i).hasBlock) {
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, i * 4), "metal_block_" + MetalRegistry.instance.get(i).name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 1 + i * 4), "metal_bricks_" + MetalRegistry.instance.get(i).name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 2 + i * 4), "metal_smallBricks_" + MetalRegistry.instance.get(i).name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.block, 3 + i * 4), "metal_blockCircle_" + MetalRegistry.instance.get(i).name);
				MicroMaterialRegistry.registerMaterial(new BlockMicroMaterial(Metals.blockPillar, i * 4), "metal_pillar_" + MetalRegistry.instance.get(i).name);
			}
		}
	}
}
