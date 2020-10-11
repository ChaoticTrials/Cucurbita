package de.melanx.cucurbita.api.recipe;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.ModRecipeTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public interface IRefinery extends IRecipe<IInventory> {
    ResourceLocation TYPE_ID = new ResourceLocation(Cucurbita.MODID, "refinery");

    FluidStack getFluidOutput();

    int getMinHeat();

    Ingredient getInput();

    @Override
    default ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    default boolean canFit(int width, int height) {
        return false;
    }

    @Override
    default IRecipeType<?> getType() {
        return ModRecipeTypes.REFINERY_TYPE;
    }
}
