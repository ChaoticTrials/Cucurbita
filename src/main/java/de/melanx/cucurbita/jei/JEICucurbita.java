package de.melanx.cucurbita.jei;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.IHollowedPumpkin;
import de.melanx.cucurbita.api.recipe.IRefinery;
import de.melanx.cucurbita.core.registration.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;
import java.util.stream.Collectors;

@JeiPlugin
public class JEICucurbita implements IModPlugin {

    public static final ResourceLocation UID = new ResourceLocation(Cucurbita.getInstance().modid, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return UID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(
                new HollowedPumpkinCategory(registration.getJeiHelpers().getGuiHelper()),
                new RefineryCategory(registration.getJeiHelpers().getGuiHelper())
        );
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ClientWorld world = Minecraft.getInstance().world;
        RecipeManager recipes = Objects.requireNonNull(world).getRecipeManager();

        registration.addRecipes(recipes.getRecipes().stream().filter(recipe -> recipe instanceof IHollowedPumpkin).collect(Collectors.toList()), HollowedPumpkinCategory.UID);
        registration.addRecipes(recipes.getRecipes().stream().filter(recipe -> recipe instanceof IRefinery).collect(Collectors.toList()), RefineryCategory.UID);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HOLLOWED_PUMPKIN), HollowedPumpkinCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HOMEMADE_REFINERY), RefineryCategory.UID);
    }
}
