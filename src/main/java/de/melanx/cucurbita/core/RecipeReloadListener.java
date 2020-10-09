package de.melanx.cucurbita.core;

import de.melanx.cucurbita.api.ModRecipeTypes;
import de.melanx.cucurbita.api.recipe.HeatSourcesRecipe;
import de.melanx.cucurbita.api.recipe.HollowedPumpkinRecipe;
import de.melanx.cucurbita.api.recipe.IHeatSources;
import de.melanx.cucurbita.api.recipe.IHollowedPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.resources.DataPackRegistries;
import net.minecraft.resources.IResourceManager;
import net.minecraft.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * Class inspired by Immersive Engineering
 * https://github.com/BluSunrize/ImmersiveEngineering/blob/1.16.3/src/main/java/blusunrize/immersiveengineering/common/crafting/RecipeReloadListener.java
 */
public class RecipeReloadListener implements IResourceManagerReloadListener {

    private final DataPackRegistries dataPackRegistries;

    public RecipeReloadListener(DataPackRegistries dataPackRegistries) {
        this.dataPackRegistries = dataPackRegistries;
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {
        if (this.dataPackRegistries != null) {
            RecipeManager recipeManager = dataPackRegistries.getRecipeManager();
            buildRecipeLists(recipeManager);
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onRecipesUpdated(RecipesUpdatedEvent event) {
        RecipeManager clientRecipeManager = event.getRecipeManager();
        if (!Minecraft.getInstance().isSingleplayer())
            buildRecipeLists(clientRecipeManager);
    }

    protected static void buildRecipeLists(RecipeManager recipeManager) {
        Collection<IRecipe<?>> recipes = recipeManager.getRecipes();

        if (recipes.isEmpty()) return;

        HeatSourcesRecipe.HEAT_SOURCES = filterRecipes(recipes, IHeatSources.class, ModRecipeTypes.HEAT_SOURCES_TYPE);
        HollowedPumpkinRecipe.PUMPKIN_RECIPES = filterRecipes(recipes, IHollowedPumpkin.class, ModRecipeTypes.HOLLOWED_PUMPKIN_TYPE);
    }

    private static <X extends IRecipe<?>> Map<ResourceLocation, X> filterRecipes(Collection<IRecipe<?>> recipes, Class<X> recipeClass, IRecipeType<X> recipeType) {
        return recipes.stream()
                .filter(iRecipe -> iRecipe.getType() == recipeType)
                .flatMap(Stream::of)
                .map(recipeClass::cast)
                .collect(Collectors.toMap(recipe -> recipe.getId(), recipe -> recipe));
    }
}
