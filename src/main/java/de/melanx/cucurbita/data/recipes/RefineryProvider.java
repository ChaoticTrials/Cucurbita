package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.api.recipe.builders.RefineryBuilder;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class RefineryProvider extends RecipeProvider {
    public RefineryProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        RefineryBuilder.create().setInput(Tags.Items.SEEDS)
                .setMinHeat(240)
                .setFluidOutput(Registration.FLUID_PLANT_OIL.get(), 50)
                .build(consumer);
    }
}
