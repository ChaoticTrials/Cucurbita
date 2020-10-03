package de.melanx.spookyjam2020.api;

import de.melanx.spookyjam2020.api.recipe.HeatSourcesRecipe;
import de.melanx.spookyjam2020.api.recipe.IHeatSources;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class ModRecipeTypes {

    public static final IRecipeType<IHeatSources> HEAT_SOURCES_TYPE = IRecipeType.register(IHeatSources.TYPE_ID.toString());
    public static final IRecipeSerializer<IHeatSources> HEAT_SOURCES_SERIALIZER = new HeatSourcesRecipe.Serializer();

    public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        Registry.register(Registry.RECIPE_TYPE, IHeatSources.TYPE_ID, HEAT_SOURCES_TYPE);
        event.getRegistry().register(HEAT_SOURCES_SERIALIZER.setRegistryName(IHeatSources.TYPE_ID));
    }

}
