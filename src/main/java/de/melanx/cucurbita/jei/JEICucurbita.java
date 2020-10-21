package de.melanx.cucurbita.jei;

import com.google.common.collect.Lists;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.recipe.IHollowedPumpkin;
import de.melanx.cucurbita.api.recipe.IRefinery;
import de.melanx.cucurbita.core.registration.ModBlocks;
import de.melanx.cucurbita.core.registration.ModItems;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;

import java.util.Collections;
import java.util.List;
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

        addInfoPage(registration, Lists.newArrayList(ModItems.MELON_WAND, ModItems.PUMPKIN_WAND), "wands");
        addInfoPage(registration, Collections.singletonList(ModBlocks.HOLLOWED_PUMPKIN.asItem()), "hollowed_pumpkin_crafting");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HOLLOWED_PUMPKIN), HollowedPumpkinCategory.UID);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.HOMEMADE_REFINERY), RefineryCategory.UID);
    }

    private static void addInfoPage(IRecipeRegistration reg, List<Item> items, String name) {
        if (items.isEmpty()) return;
        String key = getDescKey(new ResourceLocation(Cucurbita.getInstance().modid, name));
        List<ItemStack> stacks = items.stream().map(ItemStack::new).collect(Collectors.toList());
        reg.addIngredientInfo(stacks, VanillaTypes.ITEM, key);
    }

    private static String getDescKey(ResourceLocation name) {
        return "jei." + name.getNamespace() + "." + name.getPath() + ".desc";
    }
}
