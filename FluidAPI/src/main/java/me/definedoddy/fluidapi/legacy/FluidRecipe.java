package me.definedoddy.fluidapi.legacy;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.List;
import java.util.Map;

public class FluidRecipe {
    public static ShapelessRecipe createShapeless(String key, ItemStack result, List<Material> ingredients) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, result);
        for (Material ingredient : ingredients) {
            recipe.addIngredient(ingredient);
        }
        Bukkit.addRecipe(recipe);
        return recipe;
    }

    public static ShapelessRecipe createShapelessExact(String key, ItemStack result, List<ItemStack> ingredients) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapelessRecipe recipe = new ShapelessRecipe(namespacedKey, result);
        for (ItemStack ingredient : ingredients) {
            recipe.addIngredient(new RecipeChoice.ExactChoice(ingredient));
        }
        Bukkit.addRecipe(recipe);
        return recipe;
    }

    public static ShapedRecipe createShaped(String key, ItemStack result, Map<Character, Material> map, String... shape) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, result);
        recipe.shape(shape);
        for (Character character : map.keySet()) {
            recipe.setIngredient(character, map.get(character));
        }
        Bukkit.addRecipe(recipe);
        return recipe;
    }

    public static ShapedRecipe createShapedExact(String key, ItemStack result, Map<Character, ItemStack> map, String... shape) {
        NamespacedKey namespacedKey = new NamespacedKey(FluidPlugin.getPlugin(), key);
        ShapedRecipe recipe = new ShapedRecipe(namespacedKey, result);
        recipe.shape(shape);
        for (Character character : map.keySet()) {
            recipe.setIngredient(character, new RecipeChoice.ExactChoice(map.get(character)));
        }
        Bukkit.addRecipe(recipe);
        return recipe;
    }
}
