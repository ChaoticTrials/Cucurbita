package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModBlocks;
import de.melanx.cucurbita.core.registration.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.recipe.RecipeProviderBase;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProviderBase {

    public ModRecipeProvider(DataGenerator generator) {
        super(Cucurbita.getInstance(), generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapelessRecipe(Items.MELON_SEEDS, 2)
                .addIngredient(ModItems.MELON_STEM)
                .addCriterion("has_item", hasItem(ModItems.MELON_STEM)).build(consumer, loc(Items.MELON_SEEDS));
        ShapelessRecipeBuilder.shapelessRecipe(Items.PUMPKIN_SEEDS, 2)
                .addIngredient(ModItems.PUMPKIN_STEM)
                .addCriterion("has_item", hasItem(ModItems.PUMPKIN_STEM)).build(consumer, loc(Items.PUMPKIN_SEEDS));
        ShapedRecipeBuilder.shapedRecipe(ModItems.SPOON)
                .patternLine(" I")
                .patternLine("S ")
                .key('I', Tags.Items.INGOTS_IRON)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(Items.IRON_INGOT))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModItems.SUPER_COOL_SPOON)
                .patternLine(" N")
                .patternLine("S ")
                .key('N', Tags.Items.INGOTS_NETHERITE)
                .key('S', Tags.Items.RODS_WOODEN)
                .addCriterion("has_item", hasItem(Items.NETHERITE_INGOT))
                .build(consumer);
        ShapedRecipeBuilder.shapedRecipe(ModBlocks.HOMEMADE_REFINERY)
                .patternLine("III")
                .patternLine("RBR")
                .patternLine("SXS")
                .key('I', Tags.Items.INGOTS_IRON)
                .key('R', Items.IRON_BARS)
                .key('B', Items.BUCKET)
                .key('S', Items.SMOOTH_STONE)
                .key('X', Tags.Items.STORAGE_BLOCKS_IRON)
                .addCriterion("has_item", hasItem(Items.SMOOTH_STONE))
                .build(consumer);
    }
}
