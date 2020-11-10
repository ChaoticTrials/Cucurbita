package de.melanx.cucurbita.handler.lootmodifier;

import com.google.gson.JsonObject;
import de.melanx.cucurbita.core.registration.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
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
        BlockState state = context.get(LootParameters.BLOCK_STATE);
        ItemStack tool = context.get(LootParameters.TOOL);
        boolean tooltype = tool != null && tool.getToolTypes().contains(ToolType.HOE);
        return state != null && tooltype && (state.getBlock() == Blocks.ATTACHED_MELON_STEM || state.get(BlockStateProperties.AGE_0_7) == 7) ? Collections.singletonList(new ItemStack(ModItems.MELON_STEM)) : generatedLoot;
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
