package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.BlockHollowedPumpkin;
import de.melanx.cucurbita.core.registration.ModBlocks;
import io.github.noeppi_noeppi.libx.data.provider.BlockLootProviderBase;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.SurvivesExplosion;
import net.minecraft.loot.functions.CopyBlockState;
import net.minecraft.state.Property;

public class LootTables extends BlockLootProviderBase {

    public LootTables(DataGenerator generator) {
        super(Cucurbita.getInstance(), generator);
    }

    @Override
    protected void setup() {
        this.customLootTable(ModBlocks.PLANT_OIL);
        this.copyProperties(ModBlocks.HOLLOWED_PUMPKIN, BlockHollowedPumpkin.CARVING);
    }

    private void copyProperties(Block b, Property<?>... properties) {
        LootEntry.Builder<?> entry = ItemLootEntry.builder(b);
        CopyBlockState.Builder func = CopyBlockState.func_227545_a_(b);


        for (Property<?> property : properties) {
            func = func.func_227552_a_(property);
        }
        LootPool.Builder pool = LootPool.builder().name("main").rolls(ConstantRange.of(1)).addEntry(entry)
                .acceptCondition(SurvivesExplosion.builder())
                .acceptFunction(func);
        this.customLootTable(b, LootTable.builder().addLootPool(pool));
    }
}
