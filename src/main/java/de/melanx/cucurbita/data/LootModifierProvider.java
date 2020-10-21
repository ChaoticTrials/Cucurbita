package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModLootModifiers;
import de.melanx.cucurbita.handler.lootmodifier.MelonStemModifier;
import de.melanx.cucurbita.handler.lootmodifier.PumpkinStemModifier;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.loot.conditions.Alternative;
import net.minecraft.loot.conditions.BlockStateProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;

public class LootModifierProvider extends GlobalLootModifierProvider {
    public LootModifierProvider(DataGenerator generator) {
        super(generator, Cucurbita.getInstance().modid);
    }

    @Override
    protected void start() {
        this.add("pumpkin_stem", ModLootModifiers.PUMPKIN_STEM_MODIFIER, new PumpkinStemModifier(new ILootCondition[]{
                Alternative.builder(
                        BlockStateProperty.builder(Blocks.PUMPKIN_STEM),
                        BlockStateProperty.builder(Blocks.ATTACHED_PUMPKIN_STEM)
                ).build()
        }));
        this.add("melon_stem", ModLootModifiers.MELON_STEM_MODIFIER, new MelonStemModifier(new ILootCondition[]{
                Alternative.builder(
                        BlockStateProperty.builder(Blocks.MELON_STEM),
                        BlockStateProperty.builder(Blocks.ATTACHED_MELON_STEM)
                ).build()
        }));
    }
}
