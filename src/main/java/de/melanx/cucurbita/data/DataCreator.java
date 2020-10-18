package de.melanx.cucurbita.data;

import de.melanx.cucurbita.data.recipes.*;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataCreator {

    public static void onGatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer()) {
            generator.addProvider(new LootModifierProvider(generator));
            generator.addProvider(new ModTags.FluidModTags(generator));
            generator.addProvider(new LootTables(generator));
            generator.addProvider(new ModRecipeProvider(generator));
            generator.addProvider(new HeatSourceProvider(generator));
            generator.addProvider(new HollowedPumpkinProvider(generator));
            generator.addProvider(new RefineryProvider(generator));
            generator.addProvider(new SmeltingRecipes(generator));
        }
        if (event.includeClient()) {
            generator.addProvider(new BlockStates(generator, helper));
            generator.addProvider(new ItemModels(generator, helper));
        }
    }
}
