package org.agecraft.prehistory.recipes;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.agecraft.ACUtil;
import org.agecraft.core.TreeRegistry;
import org.agecraft.core.Trees;
import org.agecraft.recipes.RecipeSimple;
import org.agecraft.recipes.RecipeType;
import org.agecraft.recipes.WrappedStack;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class RecipesCampfire {

	public static class RecipeCampfire extends RecipeSimple {
		
		public ItemStack raw;
		public ItemStack cooked;
		public ItemStack burned;
		
		public int cookTime;
		public int burnTime;
		
		public RecipeCampfire(ItemStack raw, ItemStack cooked, ItemStack burned, int cookTime, int burnTime) {
			super();
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

		@Override
		public List<WrappedStack> getInput() {
			return WrappedStack.createList(raw);
		}

		@Override
		public List<WrappedStack> getOutput() {
			return WrappedStack.createList(cooked);
		}

		@Override
		public RecipeType getRecipeType() {
			return RecipeType.COOKING;
		}

		@Override
		public int getRecipeSize() {
			return 1;
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
