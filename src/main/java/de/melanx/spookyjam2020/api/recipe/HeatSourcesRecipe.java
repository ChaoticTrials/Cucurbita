package de.melanx.spookyjam2020.api.recipe;

import com.google.common.base.Preconditions;
import com.google.gson.JsonObject;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import de.melanx.spookyjam2020.api.util.ItemNBTHelper;
import de.melanx.spookyjam2020.api.ModRecipeTypes;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public class HeatSourcesRecipe implements IHeatSources {

    public static Map<ResourceLocation, IHeatSources> HEAT_SOURCES = Collections.emptyMap();

    private final ResourceLocation id;
    private final BlockState heatState;
    private final int heatValue;

    public HeatSourcesRecipe(ResourceLocation id, BlockState state, int heatValue) {
        Preconditions.checkArgument(state != Blocks.AIR.getDefaultState());
        Preconditions.checkArgument(heatValue > 0);
        this.id = id;
        this.heatState = state;
        this.heatValue = heatValue;
    }

    public BlockState getHeatState() {
        return this.heatState;
    }

    public int getHeatValue() {
        return this.heatValue;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public IRecipeSerializer<IHeatSources> getSerializer() {
        return ModRecipeTypes.HEAT_SOURCES_SERIALIZER;
    }

    @Override
    public IRecipeType<?> getType() {
        return ModRecipeTypes.HEAT_SOURCES_TYPE;
    }

    public static boolean isHeatSource(BlockState state) {
        return getHeatValue(state) >= 0;
    }

    public static int getHeatValue(BlockState state) {
        for (IHeatSources e : HEAT_SOURCES.values()) {
            if (e.getHeatState() == state) {
                return e.getHeatValue();
            }
        }
        return -1;
    }

    public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<IHeatSources> {
        @Override
        public IHeatSources read(ResourceLocation id, JsonObject json) {
            BlockState heatState;
            if (json.get("source").isJsonPrimitive()) {
                String s = JSONUtils.getString(json, "source");
                ResourceLocation blockId = ResourceLocation.tryCreate(s);
                if (blockId == null) {
                    throw new IllegalArgumentException("Invalid heat source ID: " + s);
                }
                Optional<Block> block = Registry.BLOCK.getOptional(blockId);
                if (!block.isPresent()) {
                    throw new IllegalArgumentException("Unknown heat source: " + s);
                }
                heatState = block.get().getDefaultState();
            } else {
                heatState = readBlockState(JSONUtils.getJsonObject(json, "source"));
            }

            int heatValue = JSONUtils.getInt(json, "heat");
            return new HeatSourcesRecipe(id, heatState, heatValue);
        }

        @Nullable
        @Override
        public IHeatSources read(ResourceLocation id, PacketBuffer buffer) {
            int stateId = buffer.readInt();
            BlockState heatState;
            if (stateId == -1) {
                throw new IllegalArgumentException("Invalid heat source at: " + id);
            } else {
                heatState = Block.getStateById(stateId);
            }
            int heatValue = buffer.readVarInt();
            return new HeatSourcesRecipe(id, heatState, heatValue);
        }

        @Override
        public void write(PacketBuffer buffer, IHeatSources recipe) {
            buffer.writeVarInt(recipe.getHeatValue());
            buffer.writeInt(Block.getStateId(recipe.getHeatState()));
        }

        public static BlockState readBlockState(JsonObject object) {
            CompoundNBT nbt = (CompoundNBT) new Dynamic<>(JsonOps.INSTANCE, object).convert(NBTDynamicOps.INSTANCE).getValue();
            ItemNBTHelper.renameTag(nbt, "name", "Name");
            ItemNBTHelper.renameTag(nbt, "properties", "Properties");
            String name = nbt.getString("Name");
            ResourceLocation id = ResourceLocation.tryCreate(name);
            if (id == null || !ForgeRegistries.BLOCKS.containsKey(id)) {
                throw new IllegalArgumentException("Invalid or unknown block ID: " + name);
            }
            return NBTUtil.readBlockState(nbt);
        }
    }
}
