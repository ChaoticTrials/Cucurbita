package de.melanx.spookyjam2020.data;

import de.melanx.spookyjam2020.SpookyJam2020;
import de.melanx.spookyjam2020.core.Registration;
import de.melanx.spookyjam2020.handler.lootmodifier.PumpkinStemModifier;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class LootModifierProvider extends GlobalLootModifierProvider {
    public LootModifierProvider(DataGenerator generator) {
        super(generator, SpookyJam2020.MODID);
    }

    @Override
    protected void start() {
        add("pumpkin_stem", Registration.PUMPKIN_STEM_MODIFIER.get(), new PumpkinStemModifier(new ILootCondition[]{
                BlockStateProperty.builder(Blocks.PUMPKIN_STEM).build(),
                BlockStateProperty.builder(Blocks.ATTACHED_PUMPKIN_STEM).build()
        }));
    }
}
