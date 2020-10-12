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
        HeatSourcesBuilder.create().setHeatBlock(Blocks.TORCH).setHeatValue(200).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.SOUL_TORCH).setHeatValue(230).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.MAGMA_BLOCK).setHeatValue(250).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.CAMPFIRE).setHeatValue(300).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.SOUL_CAMPFIRE).setHeatValue(450).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.FIRE).setHeatValue(750).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.SOUL_FIRE).setHeatValue(1000).build(consumer);
        HeatSourcesBuilder.create().setHeatBlock(Blocks.LAVA).setHeatValue(1200).build(consumer);
    }
}
