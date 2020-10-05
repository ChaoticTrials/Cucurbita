package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.Registration;
import de.melanx.cucurbita.handler.lootmodifier.PumpkinStemModifier;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class LootModifierProvider extends GlobalLootModifierProvider {
    public LootModifierProvider(DataGenerator generator) {
        super(generator, Cucurbita.MODID);
    }

    @Override
    protected void start() {
        add("pumpkin_stem", Registration.PUMPKIN_STEM_MODIFIER.get(), new PumpkinStemModifier(new ILootCondition[]{
                Alternative.builder(
                        BlockStateProperty.builder(Blocks.PUMPKIN_STEM),
                        BlockStateProperty.builder(Blocks.ATTACHED_PUMPKIN_STEM)
                ).build()
        }));
    }
}
