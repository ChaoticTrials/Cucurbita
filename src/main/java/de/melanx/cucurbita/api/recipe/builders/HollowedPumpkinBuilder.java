package de.melanx.cucurbita.api.recipe.builders;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.ModRecipeTypes;
import de.melanx.cucurbita.api.util.ItemNBTHelper;
import io.github.noeppi_noeppi.libx.crafting.ingredient.NbtIngredient;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.NBTIngredient;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Consumer;

public class HollowedPumpkinBuilder {
    private final List<Ingredient> ingredients = Lists.newArrayList();
    private String group;
    private int minHeat = 0;
    private FluidStack fluidInput;
    private final List<Pair<ItemStack, Double>> outputs = Lists.newArrayList();

    public static HollowedPumpkinBuilder create() {
        return new HollowedPumpkinBuilder().setGroup("hollowed_pumpkin");
    }

    public HollowedPumpkinBuilder setGroup(String name) {
        this.group = name;
        return this;
    }

    public HollowedPumpkinBuilder setMinHeat(int heat) {
        this.minHeat = heat;
        return this;
    }

    public HollowedPumpkinBuilder setFluidInput(FluidStack fluid) {
        this.fluidInput = fluid;
        return this;
    }

    public HollowedPumpkinBuilder setFluidInput(Fluid fluid) {
        return this.setFluidInput(fluid, 200);
    }

    public HollowedPumpkinBuilder setFluidInput(Fluid fluid, int amount) {
        this.fluidInput = new FluidStack(fluid, amount);
        return this;
    }

    public HollowedPumpkinBuilder addIngredient(ITag<Item> tag) {
        return this.addIngredient(Ingredient.fromTag(tag), 1);
    }

    public HollowedPumpkinBuilder addIngredient(ITag<Item> tag, int quantity) {
        return this.addIngredient(Ingredient.fromTag(tag), quantity);
    }

    public HollowedPumpkinBuilder addIngredient(IItemProvider item) {
        return this.addIngredient(Ingredient.fromItems(item), 1);
    }

    public HollowedPumpkinBuilder addIngredient(IItemProvider item, int quantity) {
        return this.addIngredient(Ingredient.fromItems(item), quantity);
    }

    public HollowedPumpkinBuilder addIngredient(NbtIngredient ingredient) {
        ingredients.add(ingredient);
        return this;
    }

    public HollowedPumpkinBuilder addIngredient(Ingredient ingredient) {
        return this.addIngredient(ingredient, 1);
    }

    public HollowedPumpkinBuilder addIngredient(Ingredient ingredient, int quantity) {
        for (int i = 0; i < quantity; i++) {
            ingredients.add(ingredient);
        }
        return this;
    }

    public HollowedPumpkinBuilder addOutput(IItemProvider item) {
        return this.addOutput(item, 1, 1.0D);
    }

    public HollowedPumpkinBuilder addOutput(IItemProvider item, int quantity) {
        return this.addOutput(item, quantity, 1.0D);
    }

    public HollowedPumpkinBuilder addOutput(IItemProvider item, int quantity, double chance) {
        ItemStack stack = new ItemStack(item, quantity);
        return this.addOutput(stack, chance);
    }

    public HollowedPumpkinBuilder addOutput(ItemStack stack) {
        return this.addOutput(stack, 1.0D);
    }

    public HollowedPumpkinBuilder addOutput(ItemStack stack, double chance) {
        this.outputs.add(Pair.of(stack, chance));
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, this.outputs.get(0).getKey().getItem().getRegistryName());
    }

    public void build(Consumer<IFinishedRecipe> consumerIn, ResourceLocation id) {
        this.validate(id);
        consumerIn.accept(new HollowedPumpkinBuilder.FinishedRecipe(new ResourceLocation(id.getNamespace(), Cucurbita.getInstance().modid + "_hollowed_pumpkin/" + id.getPath()), this.ingredients, this.group == null ? "" : this.group, this.minHeat, this.fluidInput, this.outputs));
    }

    private void validate(ResourceLocation id) {
        if (this.outputs.size() <= 0) {
            throw new IllegalStateException("No output set for " + id);
        }
        if (this.ingredients.size() <= 0) {
            throw new IllegalStateException("No ingredients set for " + id);
        } else if (this.ingredients.size() > 16) {
            throw new IllegalStateException(this.ingredients.size() + " ingredients at " + id + ", max allowed is 16");
        }
        if (this.fluidInput.isEmpty()) {
            throw new IllegalStateException("No fluid set for " + id);
        }
        if (this.minHeat < 0) {
            throw new IllegalStateException("Minimum height is negative at " + id);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final List<Ingredient> ingredients;
        private final String group;
        private final int minHeat;
        private final FluidStack fluidInput;
        private final List<Pair<ItemStack, Double>> outputs;

        public FinishedRecipe(ResourceLocation id, List<Ingredient> ingredients, String group, int minHeat, FluidStack fluidInput, List<Pair<ItemStack, Double>> outputs) {
            this.id = id;
            this.ingredients = ingredients;
            this.group = group;
            this.minHeat = minHeat;
            this.fluidInput = fluidInput;
            this.outputs = outputs;
        }

        @Override
        public void serialize(@Nonnull JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            if (this.minHeat != 0) {
                json.addProperty("heat", this.minHeat);
            }
            JsonObject inputs = new JsonObject();
            JsonObject fluidObject = new JsonObject();
            fluidObject.addProperty("name", this.fluidInput.getRawFluid().getRegistryName().toString());
            fluidObject.addProperty("amount", this.fluidInput.getAmount());
            inputs.add("fluid", fluidObject);
            JsonArray ingredients = new JsonArray();
            for (Ingredient ingredient : this.ingredients) {
                ingredients.add(ingredient.serialize());
            }
            inputs.add("ingredients", ingredients);
            JsonArray outputs = new JsonArray();
            for (Pair<ItemStack, Double> output : this.outputs) {
                JsonObject outputObject = ItemNBTHelper.serializeStack(output.getKey());
                outputObject.addProperty("chance", output.getValue());
                outputs.add(outputObject);
            }
            json.add("input", inputs);
            json.add("output", outputs);
        }

        @Nonnull
        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipeTypes.HOLLOWED_PUMPKIN_SERIALIZER;
        }

        @Nullable
        @Override
        public JsonObject getAdvancementJson() {
            return null;
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementID() {
            return null;
        }
    }
}
