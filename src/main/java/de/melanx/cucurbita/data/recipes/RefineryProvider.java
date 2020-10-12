package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.builders.RefineryBuilder;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
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
//                .setOutput(Registration.ITEM_BIO_MASS.get())
                .build(consumer, loc("plant_oil_from_seeds"));
        RefineryBuilder.create().setInput(ItemTags.SMALL_FLOWERS)
                .setMinHeat(100)
                .setFluidOutput(Fluids.WATER, 20)
//                .setOutput(Registration.ITEM_BIO_MASS.get())
                .build(consumer, loc("water_from_small_flowers"));
        RefineryBuilder.create().setInput(ItemTags.TALL_FLOWERS)
                .setMinHeat(120)
                .setFluidOutput(Fluids.WATER, 50)
//                .setOutput(Registration.ITEM_BIO_MASS.get())
                .build(consumer, loc("water_from_tall_flowers"));
        RefineryBuilder.create().setInput(Items.NETHER_WART)
                .setMinHeat(300)
                .setFluidOutput(Fluids.LAVA, 100)
                .build(consumer, loc("lava_from_nether_wart"));
//        RefineryBuilder.create().setInput(Registration.ITEM_PUMPKIN_PULP.get())
//                .setMinHeat(200)
//                .setFluidOutput(Registration.FLUID_PLANT_OIL.get())
//                .setOutput(Registration.ITEM_BIO_MASS.get())
//                .build(consumer, loc("plant_oil_from_pumpkin_pulp"));
        RefineryBuilder.create().setInput(ItemTags.SAPLINGS)
                .setMinHeat(110)
                .setFluidOutput(Fluids.WATER, 100)
//                .setOutput(Registration.ITEM_BIO_MASS.get())
                .build(consumer, loc("water_from_saplings"));
        RefineryBuilder.create().setInput(ItemTags.LEAVES)
                .setMinHeat(130)
                .setFluidOutput(Fluids.WATER, 200)
//                .setOutput(Registration.ITEM_BIO_MASS.get())
                .build(consumer, loc("water_from_leaves"));
    }

    private ResourceLocation loc(String name) {
        return new ResourceLocation(Cucurbita.MODID, name);
    }
}
