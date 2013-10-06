package elcon.mods.agecraft.assets.resources;

import net.minecraft.client.renderer.IconFlipped;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import elcon.mods.agecraft.core.ArmorRegistry;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorMaterial;
import elcon.mods.agecraft.core.ArmorRegistry.ArmorType;
import elcon.mods.agecraft.core.MetalRegistry;
import elcon.mods.agecraft.core.MetalRegistry.Metal;
import elcon.mods.agecraft.core.MetalRegistry.OreType;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.ToolRegistry.Armor;
import elcon.mods.agecraft.core.ToolRegistry.ToolEnhancementMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolMaterial;
import elcon.mods.agecraft.core.ToolRegistry.ToolRodMaterial;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.TreeRegistry.Tree;
import elcon.mods.core.ElConCore;

@SideOnly(Side.CLIENT)
public class ResourcesCore extends Resources {
	
	public static ResourceLocation guiIcons = new ResourceLocation("agecraft", "textures/gui/icons.png");
	
	public static ResourceLocation guiTechTree = new ResourceLocation("agecraft", "textures/gui/tech_tree.png");
	public static ResourceLocation guiTechTreeIcons = new ResourceLocation("agecraft", "textures/gui/tech_tree_icons.png");
	
	public static ResourceLocation ageTeleporterBeam = new ResourceLocation("agecraft", "textures/misc/beam.png");
	public static ResourceLocation ageTeleporterChest = new ResourceLocation("agecraft", "textures/tile/teleporter_chest.png");
	
	public static Icon missingTexture;
	public static Icon emptyTexture;
	
	public static Icon[][][] doorWoodIcons = new Icon[4][2][2];
	public static Icon[] trapdoorWoodIcons = new Icon[2];
	
	public static Icon[][][] doorMetalIcons = new Icon[4][2][2];
	public static Icon[] trapdoorMetalIcons = new Icon[2];
	
	@Override
	public void registerBlockIcons(IconRegister iconRegister) {
		String[] doorTypes = new String[]{"Standard", "Solid", "Double", "Full"};
		for(int i = 0; i < doorTypes.length; i++) {
			ResourcesCore.doorWoodIcons[i][0][0] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i] + "Lower");
			ResourcesCore.doorWoodIcons[i][1][0] = iconRegister.registerIcon("agecraft:door/wood/door" + doorTypes[i] + "Upper");
			ResourcesCore.doorWoodIcons[i][0][1] = new IconFlipped(ResourcesCore.doorWoodIcons[i][0][0], true, false);
			ResourcesCore.doorWoodIcons[i][1][1] = new IconFlipped(ResourcesCore.doorWoodIcons[i][1][0], true, false);
		}
		ResourcesCore.trapdoorWoodIcons[0] = iconRegister.registerIcon("agecraft:door/wood/trapdoorStandard");
		ResourcesCore.trapdoorWoodIcons[1] = iconRegister.registerIcon("agecraft:door/wood/trapdoorSolid");
		
		for(int i = 0; i < doorTypes.length; i++) {
			ResourcesCore.doorMetalIcons[i][0][0] = iconRegister.registerIcon("agecraft:door/metal/doorMetal" + doorTypes[i] + "Lower");
			ResourcesCore.doorMetalIcons[i][1][0] = iconRegister.registerIcon("agecraft:door/metal/doorMetal" + doorTypes[i] + "Upper");
			ResourcesCore.doorMetalIcons[i][0][1] = new IconFlipped(ResourcesCore.doorMetalIcons[i][0][0], true, false);
			ResourcesCore.doorMetalIcons[i][1][1] = new IconFlipped(ResourcesCore.doorMetalIcons[i][1][0], true, false);
		}
		ResourcesCore.trapdoorMetalIcons[0] = iconRegister.registerIcon("agecraft:door/metal/trapdoorMetalStandard");
		ResourcesCore.trapdoorMetalIcons[1] = iconRegister.registerIcon("agecraft:door/metal/trapdoorMetalSolid");
		
		//trees
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.wood = iconRegister.registerIcon("agecraft:wood/wood" + ElConCore.firstUpperCase(tree.name));
				tree.woodTop = iconRegister.registerIcon("agecraft:wood/woodTop" + ElConCore.firstUpperCase(tree.name));
				tree.logTop = iconRegister.registerIcon("agecraft:wood/logTop" + ElConCore.firstUpperCase(tree.name));
				tree.planks = iconRegister.registerIcon("agecraft:wood/planks" + ElConCore.firstUpperCase(tree.name));
				tree.leaves = iconRegister.registerIcon("agecraft:leaves/leaves" + ElConCore.firstUpperCase(tree.name));
				tree.leavesFast = iconRegister.registerIcon("agecraft:leaves/leavesFast" + ElConCore.firstUpperCase(tree.name));
				tree.smallSapling = iconRegister.registerIcon("agecraft:sapling/smallSapling" + ElConCore.firstUpperCase(tree.name));
				tree.sapling = iconRegister.registerIcon("agecraft:sapling/sapling" + ElConCore.firstUpperCase(tree.name));
			}
		}
		
		//metals
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/metals/ore" + ElConCore.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.block = iconRegister.registerIcon("agecraft:metals/blocks/metals/block" + ElConCore.firstUpperCase(metal.name));
					}
				} else if(metal.type == OreType.GEM) {
					if(metal.hasOre) {
						metal.ore = iconRegister.registerIcon("agecraft:metals/ores/gems/ore" + ElConCore.firstUpperCase(metal.name));
					}
					if(metal.hasBlock) {
						metal.block = iconRegister.registerIcon("agecraft:metals/blocks/gems/block" + ElConCore.firstUpperCase(metal.name));
					}
				}
			}
		}
	}
	
	@Override
	public void registerItemIcons(IconRegister iconRegister) {
		ResourcesCore.missingTexture = iconRegister.registerIcon("agecraft:missingTexture");
		ResourcesCore.emptyTexture = iconRegister.registerIcon("agecraft:emptyTexture");
		
		//trees
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			Tree tree = TreeRegistry.trees[i];
			if(tree != null) {
				tree.log = iconRegister.registerIcon("agecraft:wood/logs/log" + ElConCore.firstUpperCase(tree.name));
				tree.stick = iconRegister.registerIcon("agecraft:wood/sticks/stick" + ElConCore.firstUpperCase(tree.name));
			}
		}
		
		//metals
		for(int i = 0; i < MetalRegistry.metals.length; i++) {
			Metal metal = MetalRegistry.metals[i];
			if(metal != null) {
				if(metal.type == OreType.METAL) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/ingots/ingot" + ElConCore.firstUpperCase(metal.name));
						metal.stick = iconRegister.registerIcon("agecraft:metals/sticks/stick" + ElConCore.firstUpperCase(metal.name));
						metal.nugget = iconRegister.registerIcon("agecraft:metals/nuggets/nugget" + ElConCore.firstUpperCase(metal.name));
						if(metal.hasDust) {
							metal.dust = iconRegister.registerIcon("agecraft:metals/dusts/metals/dust" + ElConCore.firstUpperCase(metal.name));
						}
					}
				} else if(metal.type == OreType.GEM) {
					if(metal.hasIngot) {
						metal.ingot = iconRegister.registerIcon("agecraft:metals/gems/gem" + ElConCore.firstUpperCase(metal.name));
						if(metal.hasDust) {
							metal.dust = iconRegister.registerIcon("agecraft:metals/dusts/gems/dust" + ElConCore.firstUpperCase(metal.name));
						}
					}
				}
			}
		}
		
		//tools
		for(int i = 0; i < ToolRegistry.tools.length; i++) {
			Armor tool = ToolRegistry.tools[i];
			if(tool != null) {
				if(tool.id != 16 && tool.id != 17) {
					if(tool.hasHead) {
						for(int j = 0; j < ToolRegistry.toolMaterials.length; j++) {
							ToolMaterial toolMaterial = ToolRegistry.toolMaterials[j];
							if(toolMaterial != null) {
								toolMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/" + tool.name + "/" + tool.name + ElConCore.firstUpperCase(toolMaterial.name));
							}
						}
					}
					if(tool.hasRod) {
						for(int j = 0; j < ToolRegistry.toolRodMaterials.length; j++) {
							ToolRodMaterial toolRodMaterial = ToolRegistry.toolRodMaterials[j];
							if(toolRodMaterial != null) {
								toolRodMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/sticks/" + tool.name + "/" + tool.name + ElConCore.firstUpperCase(toolRodMaterial.name));
							}
						}
					}
				}
				if(tool.hasEnhancements) {
					for(int j = 0; j < ToolRegistry.toolEnhancementMaterials.length; j++) {
						ToolEnhancementMaterial toolEnhancementMaterial = ToolRegistry.toolEnhancementMaterials[j];
						if(toolEnhancementMaterial != null) {
							toolEnhancementMaterial.icons[i] = iconRegister.registerIcon("agecraft:tools/enhancements/" + tool.name + "/" + tool.name + ElConCore.firstUpperCase(toolEnhancementMaterial.name));
						}
					}
				}
			}
		}
		
		//armor
		for(int i = 0; i < ArmorRegistry.armorTypes.length; i++) {
			ArmorType armorType = ArmorRegistry.armorTypes[i];
			if(armorType != null) {
				for(int j = 0; j < ArmorRegistry.armorMaterials.length; j++) {
					ArmorMaterial armorMaterial = ArmorRegistry.armorMaterials[j];
					if(armorMaterial != null) {
						armorMaterial.icons[i] = iconRegister.registerIcon("agecraft:armor/" + armorType.name + "/" + armorType.name + ElConCore.firstUpperCase(armorMaterial.name));
						if(armorMaterial.hasOverlay) {
							armorMaterial.iconsOverlay[i] = iconRegister.registerIcon("agecraft:armor/" + armorType.name + "/" + armorType.name + ElConCore.firstUpperCase(armorMaterial.name) + "Overlay");
						}
					}
				}
			}
		}
	}
}
