package de.melanx.cucurbita.api.recipe;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import de.melanx.cucurbita.api.ModRecipeTypes;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;

public class RefineryRecipe implements IRefinery {
    public static Map<ResourceLocation, IRefinery> REFINERY_RECIPES = Collections.emptyMap();

    private final ResourceLocation id;
    private final int minHeat;
    private final Ingredient input;
    private final ItemStack output;
    private final FluidStack fluidOutput;

    public RefineryRecipe(ResourceLocation id, int minHeat, Ingredient input, ItemStack output, FluidStack fluidOutput) {
        Preconditions.checkArgument(minHeat >= 0);
        Preconditions.checkArgument(!fluidOutput.isEmpty());
        this.id = id;
        this.minHeat = minHeat;
        this.input = input;
        this.output = output;
        this.fluidOutput = fluidOutput;
    }

    @Override
    public FluidStack getFluidOutput() {
        return this.fluidOutput;
    }

    @Override
    public int getMinHeat() {
        return this.minHeat;
    }

    @Override
    public Ingredient getInput() {
        return this.input;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return this.input.test(inv.getStackInSlot(0));
    }

    @Override
    public ItemStack getRecipeOutput() {
        return this.output;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.REFINERY_SERIALIZER;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IRefinery> {
        @Override
        public IRefinery read(ResourceLocation id, JsonObject json) {
            int heat = 0;
            if (JSONUtils.hasField(json, "heat")) {
                heat = JSONUtils.getInt(json, "heat");
            }

            JsonObject outputObject = JSONUtils.getJsonObject(json, "output");
            JsonObject fluidObject = JSONUtils.getJsonObject(outputObject, "fluid");
            String s = JSONUtils.getString(fluidObject, "name");
            ResourceLocation fluidName = ResourceLocation.tryCreate(s);
            Fluid fluid = Registry.FLUID.getOrDefault(fluidName);
            if (fluid == Fluids.EMPTY) {
                throw new IllegalArgumentException("Fluid doesn't exist with ID " + s);
            }
            int fluidAmount = JSONUtils.getInt(fluidObject, "amount", 200);
            if (fluidAmount <= 0) {
                throw new IllegalArgumentException("Fluid amount too low at " + s);
            }
            FluidStack fluidOutput = new FluidStack(fluid, fluidAmount);
            ItemStack output = ItemStack.EMPTY;
            if (JSONUtils.hasField(outputObject, "item")) {
                JsonObject itemOutput = JSONUtils.getJsonObject(outputObject, "item");
                output = CraftingHelper.getItemStack(itemOutput, true);
            }

            Ingredient input = Ingredient.deserialize(JSONUtils.getJsonObject(json, "input"));
            return new RefineryRecipe(id, heat, input, output, fluidOutput);
        }

        @Nullable
        @Override
        public IRefinery read(ResourceLocation id, PacketBuffer buffer) {
            int heat = buffer.readInt();
            Ingredient input = Ingredient.read(buffer);
            ItemStack output = buffer.readItemStack();
            FluidStack fluidOutput = buffer.readFluidStack();
            return new RefineryRecipe(id, heat, input, output, fluidOutput);
        }

        @Override
        public void write(PacketBuffer buffer, IRefinery recipe) {
            buffer.writeInt(recipe.getMinHeat());
            recipe.getInput().write(buffer);
            buffer.writeItemStack(recipe.getRecipeOutput());
            buffer.writeFluidStack(recipe.getFluidOutput());
        }
    }
}
