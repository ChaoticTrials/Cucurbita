package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.api.recipe.builders.HeatSourcesBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HeatSourceProvider extends RecipeProvider {
    public HeatSourceProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        HeatSourcesBuilder.heatSource().setHeatState(Blocks.MAGMA_BLOCK.getDefaultState()).setHeatValue(1500).build(consumer);
        HeatSourcesBuilder.heatSource().setHeatState(Blocks.FIRE.getDefaultState()).setHeatValue(2000).build(consumer);
        HeatSourcesBuilder.heatSource().setHeatState(Blocks.LAVA.getDefaultState()).setHeatValue(2500).build(consumer);
    }
}
