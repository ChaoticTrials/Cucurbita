package de.melanx.cucurbita.api.recipe.builders;

import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.api.util.ItemNBTHelper;
import de.melanx.cucurbita.api.ModRecipeTypes;
import net.minecraft.block.BlockState;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

public class HeatSourcesBuilder {
    private String group;
    private BlockState heatState;
    private int heatValue;

    public static HeatSourcesBuilder heatSource() {
        return new HeatSourcesBuilder().setGroup("heat_sources");
    }

    public HeatSourcesBuilder setHeatState(BlockState state) {
        this.heatState = state;
        return this;
    }

    public HeatSourcesBuilder setHeatValue(int value) {
        this.heatValue = value;
        return this;
    }

    public HeatSourcesBuilder setGroup(String name) {
        this.group = name;
        return this;
    }

    public void build(Consumer<IFinishedRecipe> consumer) {
        this.build(consumer, this.heatState.getBlock().getRegistryName());
    }

    public void build(Consumer<IFinishedRecipe> consumer, ResourceLocation id) {
        this.validate(id);
        consumer.accept(new FinishedRecipe(new ResourceLocation(id.getNamespace(), Cucurbita.MODID + "_heat_sources/" + id.getPath()), this.heatValue, this.heatState, this.group == null ? "" : this.group));
    }

    private void validate(ResourceLocation id) {
        if (this.heatValue < 0) {
            throw new IllegalStateException("Heat value negative at " + id);
        }
        if (this.heatState == null) {
            throw new IllegalStateException("BlockState is null.");
        }
    }

    private static class FinishedRecipe implements IFinishedRecipe {
        private final ResourceLocation id;
        private final int heat;
        private final BlockState catalyst;
        private final String group;

        public FinishedRecipe(ResourceLocation id, int heat, BlockState catalyst, String group) {
            this.id = id;
            this.heat = heat;
            this.catalyst = catalyst;
            this.group = group;
        }

        @Override
        public void serialize(@Nonnull JsonObject json) {
            if (!this.group.isEmpty()) {
                json.addProperty("group", this.group);
            }
            json.addProperty("heat", heat);
            json.add("source", serializeBlockState(catalyst));
        }

        @Nonnull
        @Override
        public ResourceLocation getID() {
            return this.id;
        }

        @Nonnull
        @Override
        public IRecipeSerializer<?> getSerializer() {
            return ModRecipeTypes.HEAT_SOURCES_SERIALIZER;
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

        public static JsonObject serializeBlockState(BlockState state) {
            CompoundNBT nbt = NBTUtil.writeBlockState(state);
            ItemNBTHelper.renameTag(nbt, "Name", "name");
            ItemNBTHelper.renameTag(nbt, "Properties", "properties");
            Dynamic<INBT> dyn = new Dynamic<>(NBTDynamicOps.INSTANCE, nbt);
            return dyn.convert(JsonOps.INSTANCE).getValue().getAsJsonObject();
        }
    }
}
