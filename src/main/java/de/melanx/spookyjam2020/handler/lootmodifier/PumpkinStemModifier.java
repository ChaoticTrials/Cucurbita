package de.melanx.spookyjam2020.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.spookyjam2020.core.Registration;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PumpkinStemModifier extends LootModifier {
    public PumpkinStemModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return Collections.singletonList(new ItemStack(Registration.ITEM_PUMPKIN_STEM.get()));
    }

    public static class Serializer extends GlobalLootModifierSerializer<PumpkinStemModifier> {
        @Override
        public PumpkinStemModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new PumpkinStemModifier(conditions);
        }

        @Override
        public JsonObject write(PumpkinStemModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
