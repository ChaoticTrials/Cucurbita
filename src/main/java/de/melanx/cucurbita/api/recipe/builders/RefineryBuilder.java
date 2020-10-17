package de.melanx.cucurbita.api.recipe.builders;

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
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class RefineryBuilder {
    private Ingredient input;
    private String group;
    private int minHeat = 0;
    private FluidStack fluidOutput;
    private ItemStack output = ItemStack.EMPTY;

    public static RefineryBuilder create() {
        return new RefineryBuilder();
    }

    public RefineryBuilder setGroup(String name) {
        this.group = name;
        return this;
    }

    public RefineryBuilder setMinHeat(int heat) {
        this.minHeat = heat;
        return this;
    }

    public RefineryBuilder setInput(IItemProvider item) {
        this.input = Ingredient.fromItems(item);
        return this;
    }

    public RefineryBuilder setInput(ITag<Item> tag) {
        this.input = Ingredient.fromTag(tag);
        return this;
    }

    public RefineryBuilder setInput(Ingredient ingredient) {
        this.input = ingredient;
        return this;
    }

    public RefineryBuilder addIngredient(NbtIngredient ingredient) {
        this.input = ingredient;
        return this;
    }

    public RefineryBuilder setFluidOutput(FluidStack fluid) {
        this.fluidOutput = fluid;
        return this;
    }

    public RefineryBuilder setFluidOutput(Fluid fluid) {
        return this.setFluidOutput(fluid, 1000);
    }

    public RefineryBuilder setFluidOutput(Fluid fluid, int amount) {
        this.fluidOutput = new FluidStack(fluid, amount);
        return this;
    }

    public RefineryBuilder setOutput(ItemStack stack) {
        this.output = stack;
        return this;
    }

    public RefineryBuilder setOutput(IItemProvider item) {
        return this.setOutput(item, 1);
    }

    public RefineryBuilder setOutput(IItemProvider item, int quantity) {
        this.output = new ItemStack(item, quantity);
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, this.fluidOutput.getFluid().getRegistryName());
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        consumer.accept(new FinishedRecipe(new ResourceLocation(id.getNamespace(), Cucurbita.getInstance().modid + "_refinery/" + id.getPath()), this.group == null ? "" : this.group, minHeat, input, output, fluidOutput));
    }

    private void validate(ResourceLocation id) {
        if (this.input == null) {
            throw new IllegalArgumentException("No input set for " + id);
        }
        if (this.fluidOutput.isEmpty()) {
            throw new IllegalArgumentException("No fluid set as output for " + id);
        }
        if (this.minHeat < 0) {
            throw new IllegalStateException("Min heat is negative at " + id);
        }
    }

    public static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final String group;
        private final int minHeat;
        private final Ingredient input;
        private final ItemStack output;
        private final FluidStack fluidOutput;

        public FinishedRecipe(ResourceLocation id, String group, int minHeat, Ingredient ingredient, ItemStack stack, FluidStack fluidOutput) {
            this.id = id;
            this.group = group;
            this.minHeat = minHeat;
            this.input = ingredient;
            this.output = stack;
            this.fluidOutput = fluidOutput;
        }

        @Override
        public void serialize(@Nonnull JsonObject json) {
            if (!group.isEmpty()) json.addProperty("group", this.group);
            if (minHeat > 0) json.addProperty("heat", this.minHeat);
            json.add("input", this.input.serialize());
            JsonObject outputObject = new JsonObject();
            JsonObject fluidObject = new JsonObject();
            fluidObject.addProperty("name", this.fluidOutput.getRawFluid().getRegistryName().toString());
            fluidObject.addProperty("amount", this.fluidOutput.getAmount());
            outputObject.add("fluid", fluidObject);
            if (!this.output.isEmpty()) outputObject.add("item", ItemNBTHelper.serializeStack(this.output));
            json.add("output", outputObject);
        }

        @Nonnull
        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipeTypes.REFINERY_SERIALIZER;
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
