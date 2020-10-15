package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.handler.lootmodifier.PumpkinStemModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;

public class ModLootModifiers {
    public static final GlobalLootModifierSerializer<PumpkinStemModifier> PUMPKIN_STEM_MODIFIER = new PumpkinStemModifier.Serializer();

    public static void register() {
        Cucurbita.getInstance().register("pumpkin_stem", PUMPKIN_STEM_MODIFIER);
    }
}
