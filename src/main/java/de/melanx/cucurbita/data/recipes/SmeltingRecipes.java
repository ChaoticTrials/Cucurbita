package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.core.registration.ModItems;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class SmeltingRecipes extends RecipeProvider {
    public SmeltingRecipes(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(ModItems.MELON_STEM), ModItems.MELON_WAND, 0.5F, 1000, IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("has_item", hasItem(ModItems.MELON_STEM)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(ModItems.PUMPKIN_STEM), ModItems.PUMPKIN_WAND, 0.5F, 1000, IRecipeSerializer.CAMPFIRE_COOKING)
                .addCriterion("has_item", hasItem(ModItems.PUMPKIN_STEM)).build(consumer);
    }
}
