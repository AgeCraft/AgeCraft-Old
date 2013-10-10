package elcon.mods.agecraft.prehistory.recipes;

import java.util.HashMap;
import java.util.LinkedList;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import elcon.mods.agecraft.ACUtil;
import elcon.mods.agecraft.core.TreeRegistry;
import elcon.mods.agecraft.core.Trees;

public class RecipesCampfire {

	public static class RecipeCampfire {
		
		public ItemStack raw;
		public ItemStack cooked;
		public ItemStack burned;
		
		public int cookTime;
		public int burnTime;
		
		public RecipeCampfire(ItemStack raw, ItemStack cooked, ItemStack burned, int cookTime, int burnTime) {
			this.raw = raw;
			this.cooked = cooked;
			this.burned = burned;
			
			this.cookTime = cookTime;
			this.burnTime = burnTime;
		}
		
		public static RecipeCampfire readFromNBT(NBTTagCompound nbt) {
			ItemStack raw = ItemStack.loadItemStackFromNBT(nbt.getCompoundTag("Raw"));
			for(RecipeCampfire recipe : RecipesCampfire.recipes) {
				if(ACUtil.areItemStacksEqualNoSize(raw, recipe.raw)) {
					return recipe;
				}
			}
			return null;
		}
		
		public void writeToNBT(NBTTagCompound nbt) {
			NBTTagCompound tag = new NBTTagCompound();
			raw.writeToNBT(tag);
			nbt.setTag("Raw", tag);
		}
	}
	
	public static HashMap<ItemStack, Integer> fuel = new HashMap<ItemStack, Integer>();
	public static LinkedList<RecipeCampfire> recipes = new LinkedList<RecipeCampfire>();
	
	public static int getFuelValue(ItemStack s) {
		for(ItemStack stack : fuel.keySet()) {
			if(ACUtil.areItemStacksEqualNoSize(stack, s)) {
				return fuel.get(stack);
			}
		}
		return 0;
	}
	
	public static void addFuel(ItemStack stack, int fuelValue) {
		fuel.put(stack, fuelValue);
	}
	
	public static RecipeCampfire getRecipe(ItemStack stack) {
		for(RecipeCampfire recipe : recipes) {
			if(ACUtil.areItemStacksEqualNoSize(stack, recipe.raw)) {
				return recipe;
			}
		}
		return null;
	}
	
	public static void addRecipe(ItemStack raw, ItemStack cooked, ItemStack burned) {
		recipes.add(new RecipeCampfire(raw, cooked, burned, 200, 100));
	}
	
	public static void addRecipes() {
		for(int i = 0; i < TreeRegistry.trees.length; i++) {
			if(TreeRegistry.trees[i] != null) {
				addFuel(new ItemStack(Trees.log.blockID, 1, i), 100);
			}
		}
		
		addRecipe(new ItemStack(Item.beefRaw), new ItemStack(Item.beefCooked), new ItemStack(Item.gunpowder));
	}
}
