package de.melanx.cucurbita.api;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.*;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.event.RegistryEvent;

public class ModRecipeTypes {

    public static final IRecipeType<IHeatSources> HEAT_SOURCES_TYPE = IRecipeType.register(IHeatSources.TYPE_ID.toString());
    public static final IRecipeSerializer<IHeatSources> HEAT_SOURCES_SERIALIZER = new HeatSourcesRecipe.Serializer();

    public static final IRecipeType<IHollowedPumpkin> HOLLOWED_PUMPKIN_TYPE = IRecipeType.register(IHollowedPumpkin.TYPE_ID.toString());
    public static final IRecipeSerializer<IHollowedPumpkin> HOLLOWED_PUMPKIN_SERIALIZER = new HollowedPumpkinRecipe.Serializer();

    public static final IRecipeType<IRefinery> REFINERY_TYPE = IRecipeType.register(IRefinery.TYPE_ID.toString());
    public static final IRecipeSerializer<IRefinery> REFINERY_SERIALIZER = new RefineryRecipe.Serializer();

    public static void register() {
        Cucurbita.getInstance().register(IHeatSources.TYPE_ID.getPath(), HEAT_SOURCES_SERIALIZER);
        Cucurbita.getInstance().register(IHollowedPumpkin.TYPE_ID.getPath(), HOLLOWED_PUMPKIN_SERIALIZER);
        Cucurbita.getInstance().register(IRefinery.TYPE_ID.getPath(), REFINERY_SERIALIZER);
    }
}
