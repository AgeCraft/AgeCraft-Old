package org.agecraft.core;

import org.agecraft.ACComponent;
import org.agecraft.core.DustRegistry.Dust;
import org.agecraft.core.TreeRegistry.Tree;
import org.agecraft.core.blocks.tree.BlockLeaves;
import org.agecraft.core.blocks.tree.BlockLeavesDNA;
import org.agecraft.core.blocks.tree.BlockLog;
import org.agecraft.core.blocks.tree.BlockPlanks;
import org.agecraft.core.blocks.tree.BlockSaplingDNA;
import org.agecraft.core.blocks.tree.BlockWood;
import org.agecraft.core.blocks.tree.BlockWoodDoor;
import org.agecraft.core.blocks.tree.BlockWoodFence;
import org.agecraft.core.blocks.tree.BlockWoodFenceGate;
import org.agecraft.core.blocks.tree.BlockWoodLadder;
import org.agecraft.core.blocks.tree.BlockWoodTrapdoor;
import org.agecraft.core.blocks.tree.BlockWoodWall;
import org.agecraft.core.items.ItemBlockLeaves;
import org.agecraft.core.items.ItemLog;
import org.agecraft.core.items.ItemSaplingDNA;
import org.agecraft.core.items.ItemWoodBucket;
import org.agecraft.core.items.ItemWoodDoor;
import org.agecraft.core.items.ItemWoodDust;
import org.agecraft.core.items.ItemWoodStick;
import org.agecraft.core.tileentities.TileEntityDNATree;
import org.agecraft.core.world.trees.WorldGenTreeBirch;
import org.agecraft.core.world.trees.WorldGenTreeJungle;
import org.agecraft.core.world.trees.WorldGenTreeOak;
import org.agecraft.core.world.trees.WorldGenTreeSpruce;
import org.agecraft.dna.structure.Chromosome;
import org.agecraft.dna.structure.DNAObject;
import org.agecraft.dna.structure.Gene;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import elcon.mods.core.ECUtil;
import elcon.mods.core.items.ItemBlockExtendedMetadata;
import elcon.mods.core.items.ItemBlockName;

public class Trees extends ACComponent {

	public static final int MAX_TREE_TYPE = 23;
	
	public static final int MAX_BIOME_TYPE = 22;
	
	public static final int MAX_TRUNK_SIZE = 5;
	public static final int MAX_LEAF_SIZE = 16;
	public static final int MAX_HEIGHT = 32;
	
	public static Block wood;
	public static Block log;
	public static Block planks;
	public static Block leaves;
	public static Block woodWall;
	public static Block fence;
	public static Block fenceGate;
	public static Block door;
	public static Block trapdoor;
	public static Block ladder;

	public static Block leavesDNA;
	public static Block saplingDNA;
	
	public static Item stick;
	public static Item dust;
	public static Item bucket;
	
	public static DNAObject treeDNA = new DNAObject(0, "tree", TileEntityDNATree.class, new Chromosome[] {
		new Chromosome(0, "species", new Gene[] {
			new Gene(0, "woodType", 0, MAX_TREE_TYPE),
			new Gene(1, "leafType", 0, MAX_TREE_TYPE),
			new Gene(2, "leafColor", 0, 0xFFFFFF, false, true)
		}),
		new Chromosome(1, "habitat", new Gene[] {
			new Gene(0, "biome", 0, MAX_BIOME_TYPE),
			new Gene(1, "temperature", 0, 4),
			new Gene(2, "humidity", 0, 4)
		}),
		new Chromosome(2, "growth", new Gene[] {
			new Gene(0, "saplingGrowSpeed", 0, 4),
			new Gene(1, "growSpeed", 0, 4),
			new Gene(2, "breedingSpeed", 0, 4)
		}),
		new Chromosome(3, "appearance", new Gene[] {
			new Gene(0, "trunkSize", 1, MAX_TRUNK_SIZE),
			new Gene(1, "leafSize", 1, MAX_LEAF_SIZE),
			new Gene(2, "height", 3, MAX_HEIGHT),
			new Gene(3, "generationType", 1, MAX_TREE_TYPE)
		}),
		new Chromosome(4, "drops", new Gene[] {
			new Gene(0, "saplingDropRate", 0, 2),
			new Gene(1, "sappiness", 0, 4),
			new Gene(2, "fruit"),
			new Gene(3, "fruitDropRate", 0, 2)
		})
	});
	
	@Override
	public void preInit() {
		//init blocks
		wood = new BlockWood(2520).setUnlocalizedName("trees_wood");
		log = new BlockLog(2521).setUnlocalizedName("trees_log");
		planks = new BlockPlanks(2522).setUnlocalizedName("trees_planks");
		leaves = new BlockLeaves(2523).setUnlocalizedName("trees_leaves");
		woodWall = new BlockWoodWall(2524).setUnlocalizedName("trees_woodWall");
		fence = new BlockWoodFence(2525).setUnlocalizedName("trees_fence");
		fenceGate = new BlockWoodFenceGate(2526).setUnlocalizedName("trees_fenceGate");
		door = new BlockWoodDoor(2527).setUnlocalizedName("trees_door");
		trapdoor = new BlockWoodTrapdoor(2528).setUnlocalizedName("trees_trapdoor");
		ladder = new BlockWoodLadder(2529).setUnlocalizedName("trees_ladder");
		
		leavesDNA = new BlockLeavesDNA(2540).setUnlocalizedName("tree_leavesDNA");
		saplingDNA = new BlockSaplingDNA(2541).setUnlocalizedName("tree_saplingDNA");
		
		//register blocks
		GameRegistry.registerBlock(wood, ItemBlockExtendedMetadata.class, "AC_trees_wood");
		GameRegistry.registerBlock(log, ItemLog.class, "AC_trees_log");
		GameRegistry.registerBlock(planks, ItemBlockExtendedMetadata.class, "AC_trees_planks");
		GameRegistry.registerBlock(leaves, ItemBlockLeaves.class, "AC_trees_leaves");
		GameRegistry.registerBlock(woodWall, ItemBlockExtendedMetadata.class, "AC_trees_woodWall");
		GameRegistry.registerBlock(fence, ItemBlockExtendedMetadata.class, "AC_trees_fence");
		GameRegistry.registerBlock(fenceGate, ItemBlockExtendedMetadata.class, "AC_trees_fenceGate");
		GameRegistry.registerBlock(door, ItemWoodDoor.class, "AC_trees_door");
		GameRegistry.registerBlock(trapdoor, ItemBlockExtendedMetadata.class, "AC_trees_trapdoor");
		GameRegistry.registerBlock(ladder, ItemBlockExtendedMetadata.class, "AC_trees_ladder");
		
		GameRegistry.registerBlock(leavesDNA, ItemBlockName.class, "AC_trees_leavesDNA");
		GameRegistry.registerBlock(saplingDNA, ItemSaplingDNA.class, "AC_trees_saplingDNA");
		
		//register block flammability
		Block.setBurnProperties(wood.blockID, 5, 5);
		Block.setBurnProperties(log.blockID, 5, 5);
		Block.setBurnProperties(planks.blockID, 5, 20);
		Block.setBurnProperties(leaves.blockID, 30, 60);
		Block.setBurnProperties(woodWall.blockID, 5, 5);
		Block.setBurnProperties(fence.blockID, 5, 20);
		Block.setBurnProperties(fenceGate.blockID, 5, 20);
		Block.setBurnProperties(door.blockID, 5, 20);
		Block.setBurnProperties(trapdoor.blockID, 5, 20);
		Block.setBurnProperties(ladder.blockID, 5, 20);
		
		Block.setBurnProperties(leavesDNA.blockID, 30, 60);
		
		//init items
		stick = new ItemWoodStick(12510).setUnlocalizedName("trees_stick");
		dust = new ItemWoodDust(12511).setUnlocalizedName("trees_dust");
		bucket = new ItemWoodBucket(12512).setUnlocalizedName("trees_bucket");
		
		//register items
		GameRegistry.registerItem(stick, "AC_trees_stick");
		GameRegistry.registerItem(dust, "AC_trees_dust");
		GameRegistry.registerItem(bucket, "AC_trees_bucket");
		
		//register tileentities
		GameRegistry.registerTileEntity(TileEntityDNATree.class, "TileDNATree");
	}
	
	@Override
	public void init() {
		//register trees
		//TODO: colors and world gen
		TreeRegistry.registerTree(new Tree(0, "acacia", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(1, "alder", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(2, "beech", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(3, "birch", 0x80A755, false, 0xD7C185, new WorldGenTreeBirch()));
		TreeRegistry.registerTree(new Tree(4, "buckeye", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(5, "cherryBlossom", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(6, "coniferous", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(7, "darkOak", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(8, "dogwood", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(9, "elm", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(10, "eucalyptus", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(11, "fir", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(12, "filbert", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(13, "jungle", 0x48B518, true, 0xB1805C, new WorldGenTreeJungle()));
		TreeRegistry.registerTree(new Tree(14, "maple", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(15, "oak", 0x48B518, true, 0xB4905A, new WorldGenTreeOak()));
		TreeRegistry.registerTree(new Tree(16, "palm", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(17, "pine", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(18, "poplar", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(19, "spruce", 0x619961, false, 0x785836, new WorldGenTreeSpruce()));
		TreeRegistry.registerTree(new Tree(20, "tupelo", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(21, "willow", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(22, "yew", 0xFFFFFF, true, 0xFFFFFF, null));
		TreeRegistry.registerTree(new Tree(23, "yellow", 0xFFFFFF, true, 0xFFFFFF, null));
		
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				DustRegistry.registerDust(new Dust(i, "wood" + ECUtil.firstUpperCase(TreeRegistry.trees[i].name), "trees." + TreeRegistry.trees[i].name, new ItemStack(dust.itemID, 1, i)));
			}
		}
	}
	
	@Override
	public void postInit() {
		for(Fluid fluid : FluidRegistry.getRegisteredFluids().values()) {
			for(int i = 0; i < TreeRegistry.trees.length; i++) {
				if(TreeRegistry.trees[i] != null) {
					FluidContainerRegistry.registerFluidContainer(fluid, new ItemStack(bucket.itemID, 1, i));
				}
			}
		}
	}
}
