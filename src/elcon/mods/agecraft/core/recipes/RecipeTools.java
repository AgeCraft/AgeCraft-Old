package elcon.mods.agecraft.core.recipes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import elcon.mods.agecraft.core.Stone;
import elcon.mods.agecraft.core.ToolRegistry;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;
import elcon.mods.agecraft.core.gui.InventoryCraftMatrix;
import elcon.mods.agecraft.recipes.Recipe;
import elcon.mods.agecraft.recipes.RecipeType;
import elcon.mods.agecraft.recipes.WrappedStack;

public class RecipeTools extends Recipe {
	
	public static HashMap<String, Integer> patterns = new HashMap<String, Integer>();
	static {
		patterns.put("MMM_R__R_", 1);
		patterns.put("MM_MR__R_", 2);
		patterns.put("_MM_RM_R_", 2);
		patterns.put("M__R__R__", 3);
		patterns.put("_M__R__R_", 3);
		patterns.put("__M__R__R", 3);
		patterns.put("MM__R__R_", 4);
		patterns.put("_MM_R__R_", 4);
		patterns.put("MMMMRM_R_", 5);
		patterns.put("M__M__R__", 8);
		patterns.put("_M__M__R_", 8);
		patterns.put("__M__M__R", 8);
		patterns.put("M__R_____", 9);
		patterns.put("_M__R____", 9);
		patterns.put("__M__R___", 9);
		patterns.put("___M__R__", 9);
		patterns.put("____M__R_", 9);
		patterns.put("_____M__R", 9);
		//TODO: check recipes 13 and 14
		patterns.put("MM__R____", 13);
		patterns.put("_MM__R___", 13);
		patterns.put("___MM__R_", 13);
		patterns.put("____MM_R_", 13);
		patterns.put("_MM_R____", 13);
		patterns.put("__MM_R___", 13);
		patterns.put("____MM_R_", 13);
		patterns.put("____MM__R", 13);
		patterns.put("M___R____", 14);
		patterns.put("_M___R___", 14);
		patterns.put("___M___R_", 14);
		patterns.put("____M__R_", 14);
		patterns.put("__M_R____", 14);
		patterns.put("___M_R___", 14);
		patterns.put("_____M_R_", 14);
		patterns.put("____M___R", 14);	
		patterns.put("M__R__F__", 20);
		patterns.put("_M__R__F_", 20);
		patterns.put("__M__R__F", 20);
	}
	
	public boolean matches(InventoryCraftMatrix inventory) {
		return getCraftingResult(inventory) != null;
	}
	
	public ItemStack getCraftingResult(InventoryCraftMatrix inventory) {
		for(String pattern : patterns.keySet()) {
			boolean matches = true;
			int toolRodMaterial = -1;
			for(int i = 0; i < 9; i++) {
				char c = pattern.charAt(i);
				ItemStack stack = inventory.getStackInSlot(i);
				if(c == '_' && stack != null) {
					matches = false;
					continue;
				}
				if(stack != null) {
					if(c == 'M' && (stack.itemID != Stone.stone.blockID || stack.getItemDamage() != 1)) {
						matches = false;
						continue;
					}
					if(c == 'R') {
						if(stack.itemID == Trees.stick.itemID) {
							if(toolRodMaterial == -1) {
								toolRodMaterial = stack.getItemDamage();
							} else {
								if(toolRodMaterial != stack.getItemDamage()) {
									matches = false;
									continue;
								}
							}
						} else {
							matches = false;
							continue;
						}
					}
					if(c == 'F' && stack.itemID != Item.feather.itemID) {
						matches = false;
						continue;
					}
				} else if(c == 'M' || c == 'R' || c == 'F') {
					matches = false;
					continue;
				}
			}
			if(matches && toolRodMaterial >= 0) {
				int toolID = patterns.get(pattern);
				ItemStack stack = new ItemStack(ToolRegistry.tools[toolID].item.itemID, 1, 0);
				NBTTagCompound nbt = new NBTTagCompound();
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("Type", toolID);
				tag.setInteger("Material", 127);
				tag.setInteger("RodMaterial", toolRodMaterial);
				tag.setInteger("EnhancementMaterial", 0);
				nbt.setCompoundTag("Tool", tag);
				stack.setTagCompound(nbt);
				return stack;
			}
		}
		return null;
	}
	
	@Override
	public List<List<WrappedStack>> getInputs() {
		ArrayList<List<WrappedStack>> list = new ArrayList<List<WrappedStack>>();
		boolean[] done = new boolean[ToolRegistry.tools.length];
		for(String pattern : patterns.keySet()) {
			if(!done[patterns.get(pattern)]) {
				int materials = 0;
				int rodMaterials = 0;
				int feathers = 0;
				for(int i = 0; i < 9; i++) {
					char c = pattern.charAt(i);
					if(c == 'M') {
						materials++;
					} else if(c == 'R') {
						rodMaterials++;
					} else if(c == 'F') {
						feathers++;
					}
				}
				for(int i = 0; i < TreeRegistry.trees.length; i++) {
					if(TreeRegistry.trees[i] != null) {
						list.add(WrappedStack.createList(Arrays.asList(new ItemStack(Stone.stone.blockID, materials, 1), new ItemStack(Trees.stick.itemID, rodMaterials, i), new ItemStack(Item.feather.itemID, feathers, 0))));
					}
				}
				done[patterns.get(pattern)] = true;
			}
		}
		return list;
	}

	@Override
	public List<WrappedStack> getOutput(List<WrappedStack> input) {
		return null;
	}

	@Override
	public RecipeType getRecipeType() {
		return RecipeType.CRAFTING;
	}

	@Override
	public int getRecipeSize() {
		return 9;
	}
}
