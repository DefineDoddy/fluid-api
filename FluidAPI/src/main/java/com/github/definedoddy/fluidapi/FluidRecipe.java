package com.github.definedoddy.fluidapi;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.*;

import java.util.HashMap;
import java.util.Map;

public class FluidRecipe {
    private Recipe recipe;

    public FluidRecipe(String key, ItemStack result, Material[] ingredients) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, result);
        for (Material ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }
        Bukkit.addRecipe(recipe);
        this.recipe = recipe;
    }

    public FluidRecipe(String key, ItemStack result, ItemStack[] ingredients) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, result);
        for (ItemStack ingredient : ingredients) {
            recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient));
        }
        Bukkit.addRecipe(recipe);
        this.recipe = recipe;
    }

    public FluidRecipe(String key, ItemStack result, Map<Character, Material> map, String... shape) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, result);
        recipe.shape(shape);
        for (Character character : map.keySet()) {
            recipe.setIngredient(character, map.get(character));
        }
        Bukkit.addRecipe(recipe);
        this.recipe = recipe;
    }

    public FluidRecipe(String key, ItemStack result, HashMap<Character, ItemStack> map, String... shape) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, result);
        recipe.shape(shape);
        for (Character character : map.keySet()) {
            recipe.setIngredient(character, new RecipeChoice.ExactChoice(map.get(character)));
        }
        Bukkit.addRecipe(recipe);
        this.recipe = recipe;
    }

    public Recipe build() {
        return recipe;
    }
}
