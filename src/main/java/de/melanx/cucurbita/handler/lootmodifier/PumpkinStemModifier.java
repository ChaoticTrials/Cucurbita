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

public class PumpkinStemModifier extends LootModifier {
    public PumpkinStemModifier(ILootCondition[] conditions) {
        super(conditions);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        BlockState state = context.get(LootParameters.BLOCK_STATE);
        ItemStack tool = context.get(LootParameters.TOOL);
        boolean tooltype = tool != null && tool.getToolTypes().contains(ToolType.HOE);
        return state != null && tooltype && (state.getBlock() == Blocks.ATTACHED_PUMPKIN_STEM || state.get(BlockStateProperties.AGE_0_7) == 7) ? Collections.singletonList(new ItemStack(ModItems.PUMPKIN_STEM)) : generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<PumpkinStemModifier> {
        @Override
        public PumpkinStemModifier read(ResourceLocation location, JsonObject object, ILootCondition[] conditions) {
            return new PumpkinStemModifier(conditions);
        }

        @Override
        public JsonObject write(PumpkinStemModifier instance) {
            return this.makeConditions(instance.conditions);
        }
    }
}
