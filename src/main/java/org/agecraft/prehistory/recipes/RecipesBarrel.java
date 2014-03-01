package org.agecraft.prehistory.recipes;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import org.agecraft.ACUtil;
import org.agecraft.core.recipes.RecipeSimple;
import org.agecraft.core.recipes.RecipeType;
import org.agecraft.core.recipes.WrappedStack;

public class RecipesBarrel {

	public static class RecipeBarrel extends RecipeSimple {

		public ItemStack input;
		public ItemStack output;
		public String fluid;
		public int ticks;
		
		public RecipeBarrel(ItemStack input, ItemStack output, String fluid, int ticks) {
			super();
			this.input = input;
			this.output = output;
			this.fluid = fluid;
			this.ticks = ticks;
		}
		
		@Override
		public List<WrappedStack> getInput() {
			return WrappedStack.createList(input);
		}

		@Override
		public List<WrappedStack> getOutput() {
			return WrappedStack.createList(output);
		}

		@Override
		public RecipeType getRecipeType() {
			return RecipeType.SOAKING;
		}

		@Override
		public int getRecipeSize() {
			return 1;
		}
	}
	
	public static LinkedList<RecipeBarrel> recipes = new LinkedList<RecipeBarrel>();
	
	public static RecipeBarrel getRecipe(ItemStack stack) {
		for(RecipeBarrel recipe : recipes) {
			if(ACUtil.areItemStacksEqualNoSize(stack, recipe.input)) {
				return recipe;
			}
		}
		return null;
	}
	
	public static void addRecipe(ItemStack input, ItemStack output, String fluid, int ticks) {
		recipes.add(new RecipeBarrel(input, output, fluid, ticks));
	}
	
	public static void addRecipes() {
		addRecipe(new ItemStack(Items.leather), new ItemStack(Items.string), "water", 1200);
	}
}
