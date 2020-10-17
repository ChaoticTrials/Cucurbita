package de.melanx.cucurbita.data.recipes;

import de.melanx.cucurbita.api.recipe.builders.HollowedPumpkinBuilder;
import de.melanx.cucurbita.core.registration.ModFluids;
import de.melanx.cucurbita.core.registration.ModItems;
import de.melanx.cucurbita.core.registration.ModPotions;
import io.github.noeppi_noeppi.libx.crafting.ingredient.NbtIngredient;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.PotionUtils;
import net.minecraft.potion.Potions;
import net.minecraftforge.common.Tags;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class HollowedPumpkinProvider extends RecipeProvider {
    public HollowedPumpkinProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void registerRecipes(@Nonnull Consumer<IFinishedRecipe> consumer) {
        HollowedPumpkinBuilder.create().addOutput(Items.ENCHANTED_GOLDEN_APPLE)
                .setMinHeat(900)
                .setFluidInput(ModFluids.PLANT_OIL_SOURCE, 2000)
                .addIngredient(new NbtIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.LONG_FIRE_RESISTANCE)))
                .addIngredient(new NbtIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), Potions.STRONG_REGENERATION)))
                .addIngredient(new NbtIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModPotions.LONG_RESISTANCE)))
                .addIngredient(new NbtIngredient(PotionUtils.addPotionToItemStack(new ItemStack(Items.POTION), ModPotions.ABSORPTION_IV)))
                .addIngredient(Items.GOLD_BLOCK, 8)
                .addIngredient(Items.GOLDEN_APPLE)
                .build(consumer);
        HollowedPumpkinBuilder.create().addOutput(ModItems.PUMPKIN_STEW)
                .setMinHeat(240)
                .setFluidInput(ModFluids.PLANT_OIL_SOURCE, 200)
                .addIngredient(Items.BOWL)
                .addIngredient(ModItems.PUMPKIN_PULP, 2)
                .addIngredient(Items.POTATO)
                .build(consumer);
        HollowedPumpkinBuilder.create().addOutput(ModItems.PUMPKIN_JAM)
                .addOutput(ModItems.PUMPKIN_PULP, 1, 0.1D)
                .setMinHeat(300)
                .setFluidInput(ModFluids.PLANT_OIL_SOURCE, 100)
                .addIngredient(ModItems.PUMPKIN_PULP, 5)
                .addIngredient(Items.GLASS_BOTTLE)
                .build(consumer);
        HollowedPumpkinBuilder.create().addOutput(Items.HEART_OF_THE_SEA)
                .addOutput(Items.SEAGRASS, 2, 0.3)
                .setMinHeat(80)
                .setFluidInput(Fluids.WATER, 1500)
                .addIngredient(Tags.Items.ENDER_PEARLS)
                .addIngredient(Tags.Items.GEMS_PRISMARINE, 4)
                .addIngredient(Tags.Items.DUSTS_PRISMARINE, 4)
                .addIngredient(Items.KELP, 4)
                .build(consumer);
        HollowedPumpkinBuilder.create().addOutput(Items.KELP, 3, 0.8)
                .setMinHeat(300)
                .setFluidInput(Fluids.WATER)
                .addIngredient(Items.SEAGRASS, 5)
                .build(consumer);
        HollowedPumpkinBuilder.create().addOutput(Items.RED_MUSHROOM)
                .setMinHeat(400)
                .setFluidInput(Fluids.WATER)
                .addIngredient(Items.CRIMSON_FUNGUS)
                .build(consumer);
        HollowedPumpkinBuilder.create().addOutput(Items.BROWN_MUSHROOM)
                .setMinHeat(400)
                .setFluidInput(Fluids.WATER)
                .addIngredient(Items.WARPED_FUNGUS)
                .build(consumer);
        ItemStack melanHead = new ItemStack(Items.PLAYER_HEAD);
        melanHead.getOrCreateTag().putString("SkullOwner", "MelanX");
        HollowedPumpkinBuilder.create().addOutput(melanHead)
                .setMinHeat(1000)
                .setFluidInput(Fluids.LAVA)
                .addIngredient(Items.CREEPER_HEAD)
                .addIngredient(Items.ZOMBIE_HEAD)
                .addIngredient(Items.SKELETON_SKULL)
                .addIngredient(Items.WITHER_SKELETON_SKULL)
                .addIngredient(Items.DRAGON_HEAD)
                .build(consumer);
    }
}
