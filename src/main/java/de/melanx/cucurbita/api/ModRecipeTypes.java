package de.melanx.cucurbita.api;

import de.melanx.cucurbita.api.recipe.HeatSourcesRecipe;
import de.melanx.cucurbita.api.recipe.HollowedPumpkinRecipe;
import de.melanx.cucurbita.api.recipe.IHeatSources;
import de.melanx.cucurbita.api.recipe.IHollowedPumpkin;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.event.RegistryEvent;

public class ModRecipeTypes {

    public static final IRecipeType<IHeatSources> HEAT_SOURCES_TYPE = IRecipeType.register(IHeatSources.TYPE_ID.toString());
    public static final IRecipeSerializer<IHeatSources> HEAT_SOURCES_SERIALIZER = new HeatSourcesRecipe.Serializer();

    public static final IRecipeType<IHollowedPumpkin> HOLLOWED_PUMPKIN_TYPE = IRecipeType.register(IHollowedPumpkin.TYPE_ID.toString());
    public static final IRecipeSerializer<IHollowedPumpkin> HOLLOWED_PUMPKIN_SERIALIZER = new HollowedPumpkinRecipe.Serializer();

    public static void register(RegistryEvent.Register<IRecipeSerializer<?>> event) {
        event.getRegistry().register(HEAT_SOURCES_SERIALIZER.setRegistryName(IHeatSources.TYPE_ID));
        event.getRegistry().register(HOLLOWED_PUMPKIN_SERIALIZER.setRegistryName(IHollowedPumpkin.TYPE_ID));
    }

}
