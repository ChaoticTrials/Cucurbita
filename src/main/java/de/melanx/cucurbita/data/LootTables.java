package de.melanx.cucurbita.data;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import de.melanx.cucurbita.Cucurbita;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LootTables extends LootTableProvider {
    public LootTables(DataGenerator generator) {
        super(generator);
    }

    @Nonnull
    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(
                Pair.of(BlockTable::new, LootParameterSets.BLOCK)
        );
    }

    @Override
    protected void validate(@Nonnull Map<ResourceLocation, LootTable> map, @Nonnull ValidationTracker validationtracker) {
        map.forEach((name, table) -> LootTableManager.validateLootTable(validationtracker, name, table));
    }

    private static class BlockTable extends BlockLootTables {
        @Override
        protected void addTables() {
            ForgeRegistries.BLOCKS.getEntries().stream().map(Map.Entry::getValue).filter(block -> block.getRegistryName().getNamespace().equals(Cucurbita.getInstance().modid) &&
                    !(block instanceof FlowingFluidBlock))
                    .collect(Collectors.toList()).forEach(this::registerDropSelfLootTable);
        }

        @Nonnull
        @Override
        protected Iterable<Block> getKnownBlocks() {
            Stream<Block> blockStream = ForgeRegistries.BLOCKS.getEntries().stream().map(Map.Entry::getValue).filter(block -> block.getRegistryName().getNamespace().equals(Cucurbita.getInstance().modid));
            return blockStream::iterator;
        }
    }
}
