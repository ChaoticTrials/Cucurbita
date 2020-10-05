package de.melanx.cucurbita.api.recipe;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.ModRecipeTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public interface IHollowedPumpkin extends IRecipe<IInventory> {

    ResourceLocation TYPE_ID = new ResourceLocation(Cucurbita.MODID, "hollowed_pumpkin");

    int getMinHeat();

    List<Pair<ItemStack, Double>> getOutputs();

    FluidStack getFluidInput();

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
        return ModRecipeTypes.HOLLOWED_PUMPKIN_TYPE;
    }
}
