package de.melanx.cucurbita.api.recipe;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.ModRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public interface IHeatSources extends IRecipe<IInventory> {

    public static ResourceLocation TYPE_ID = new ResourceLocation(Cucurbita.MODID, "heat_sources");

    BlockState getHeatState();

    int getHeatValue();

    @Override
    default boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Override
    default ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }

    @Override
    default ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    default IRecipeType<?> getType() {
        return ModRecipeTypes.HEAT_SOURCES_TYPE;
    }
}
