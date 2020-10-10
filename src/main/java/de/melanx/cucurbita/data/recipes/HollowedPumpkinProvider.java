package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.builders.HollowedPumpkinBuilder;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HollowedPumpkinProvider extends RecipeProvider {
    public HollowedPumpkinProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        HollowedPumpkinBuilder.create().addOutput(Items.DIAMOND, 2, 0.1D)
                .addOutput(Items.EMERALD, 3)
                .addIngredient(Items.GRASS)
                .addIngredient(Tags.Items.INGOTS)
                .setFluidInput(Registration.FLUID_PLANT_OIL.get(), 400)
                .setMinHeat(30)
                .build(consumer, new ResourceLocation(Cucurbita.MODID, "test_recipe"));
    }
}
