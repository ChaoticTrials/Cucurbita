package de.melanx.cucurbita.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.cucurbita.core.registration.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class MelonStemModifier extends LootModifier {
    public MelonStemModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot.size() != 0 ? Collections.singletonList(new ItemStack(ModItems.MELON_STEM)) : generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<MelonStemModifier> {
        @Override
        public MelonStemModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new MelonStemModifier(conditions);
        }

        @Override
        public JsonObject write(MelonStemModifier instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}
