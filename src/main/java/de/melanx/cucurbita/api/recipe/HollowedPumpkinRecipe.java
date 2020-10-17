package de.melanx.cucurbita.api.recipe;

import com.google.common.base.Preconditions;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class HollowedPumpkinRecipe implements IHollowedPumpkin {
    public static Map<ResourceLocation, IHollowedPumpkin> PUMPKIN_RECIPES = Collections.emptyMap();

    private final ResourceLocation id;
    private final int minHeat;
    private final FluidStack fluidInput;
    private final NonNullList<Ingredient> ingredients;
    private final List<Pair<ItemStack, Double>> outputs;

    public HollowedPumpkinRecipe(ResourceLocation id, int minHeat, FluidStack fluidInput, NonNullList<Ingredient> ingredients, List<Pair<ItemStack, Double>> outputs) {
        Preconditions.checkArgument(minHeat >= 0);
        Preconditions.checkArgument(fluidInput.getAmount() > 0);
        Preconditions.checkArgument(ingredients.size() <= 16);
        this.id = id;
        this.minHeat = minHeat;
        this.fluidInput = fluidInput;
        this.ingredients = ingredients;
        this.outputs = outputs;
    }

    @Override
    public int getMinHeat() {
        return this.minHeat;
    }

    @Override
    public List<Pair<ItemStack, Double>> getOutputs() {
        return this.outputs;
    }

    @Override
    public FluidStack getFluidInput() {
        return this.fluidInput;
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        List<Ingredient> ingredientsMissing = new ArrayList<>(this.ingredients);
        IntStream.range(0, inv.getSizeInventory()).boxed().map(inv::getStackInSlot).filter(stack -> !stack.isEmpty()).forEach(stack ->
                ingredientsMissing.stream().filter(ingredient -> ingredient.test(stack)).findFirst().ifPresent(ingredientsMissing::remove)
        );
        return ingredientsMissing.isEmpty();
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return ModRecipeTypes.HOLLOWED_PUMPKIN_SERIALIZER;
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IHollowedPumpkin> {
        @Override
        public IHollowedPumpkin read(ResourceLocation id, JsonObject json) {
            int minHeat = JSONUtils.getInt(json, "heat", 0);

            JsonObject inputs = JSONUtils.getJsonObject(json, "inputs");
            JsonObject fluidObject = JSONUtils.getJsonObject(inputs, "fluid");
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
            FluidStack fluidInput = new FluidStack(fluid, fluidAmount);

            JsonArray ingrs = JSONUtils.getJsonArray(inputs, "ingredients");
            Ingredient[] ingredients = new Ingredient[ingrs.size()];
            for (int i = 0; i < ingrs.size(); i++) {
                ingredients[i] = Ingredient.deserialize(ingrs.get(i));
            }

            JsonArray outputs = JSONUtils.getJsonArray(json, "outputs");
            List<Pair<ItemStack, Double>> outputList = new ArrayList<>();
            for (JsonElement e : outputs) {
                JsonObject object = e.getAsJsonObject();
                ItemStack stack = CraftingHelper.getItemStack(object, true);
                double chance = JSONUtils.getFloat(object, "chance", 1);
                outputList.add(Pair.of(stack, chance));
            }

            return new HollowedPumpkinRecipe(id, minHeat, fluidInput, NonNullList.from(Ingredient.EMPTY, ingredients), outputList);
        }

        @Nullable
        @Override
        public IHollowedPumpkin read(ResourceLocation id, PacketBuffer buffer) {
            int minHeat = buffer.readVarInt();
            FluidStack fluidInput = buffer.readFluidStack();
            Ingredient[] ingredients = new Ingredient[buffer.readVarInt()];
            for (int i = 0; i < ingredients.length; i++) {
                ingredients[i] = Ingredient.read(buffer);
            }
            int outputSize = buffer.readVarInt();
            List<Pair<ItemStack, Double>> outputs = new ArrayList<>(buffer.readVarInt());
            for (int i = 0; i < outputSize; i++) {
                outputs.add(Pair.of(buffer.readItemStack(), buffer.readDouble()));
            }
            return new HollowedPumpkinRecipe(id, minHeat, fluidInput, NonNullList.from(Ingredient.EMPTY, ingredients), outputs);
        }

        @Override
        public void write(PacketBuffer buffer, IHollowedPumpkin recipe) {
            buffer.writeVarInt(recipe.getMinHeat());
            buffer.writeFluidStack(recipe.getFluidInput());
            buffer.writeVarInt(recipe.getIngredients().size());
            for (Ingredient input : recipe.getIngredients()) {
                input.write(buffer);
            }
            buffer.writeVarInt(recipe.getOutputs().size());
            for (Pair<ItemStack, Double> pair : recipe.getOutputs()) {
                buffer.writeItemStack(pair.getKey());
                buffer.writeDouble(pair.getValue());
            }
        }
    }
}
