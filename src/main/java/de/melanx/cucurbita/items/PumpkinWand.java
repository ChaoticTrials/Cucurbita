package de.melanx.cucurbita.items;

import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.core.LibNames;
import de.melanx.cucurbita.core.Registration;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class PumpkinWand extends Item {
    private static final String TAG = "delete_fluid_mode";

    public PumpkinWand(Properties properties) {
        super(new Item.Properties().maxStackSize(1));
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            if (player.isSneaking()) {
                toggleMode(player, stack);
                return ActionResult.resultSuccess(stack);
            }
        }
        return super.onItemRightClick(world, player, hand);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        TileEntity tile = context.getWorld().getTileEntity(context.getPos());
        if (tile instanceof TileHollowedPumpkin) {
            if (!hasSpecialMode(context.getItem())) {
                ((TileHollowedPumpkin) tile).onWanded();
            } else {
                ((TileHollowedPumpkin) tile).resetFluid();
            }
            return ActionResultType.SUCCESS;
        }
        return super.onItemUse(context);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getContainerItem(ItemStack stack) {
        return new ItemStack(this);
    }

    private boolean hasSpecialMode(ItemStack stack) {
        return hasTag(stack) && stack.getOrCreateTag().getBoolean(TAG);
    }

    private static boolean hasTag(ItemStack stack) {
        return !stack.isEmpty() && stack.getOrCreateTag().contains(TAG);
    }

    private static void toggleMode(PlayerEntity player, ItemStack stack) {
        boolean specialMode;
        if (hasTag(stack)) {
            specialMode = stack.getOrCreateTag().getBoolean(TAG);
        } else {
            specialMode = false;
        }
        specialMode = !specialMode;
        stack.getOrCreateTag().putBoolean(TAG, specialMode);
        TranslationTextComponent text;
        if (specialMode) {
            text = new TranslationTextComponent(LibNames.getTooltipString("delete_mode0"));
            text.mergeStyle(TextFormatting.DARK_RED);
        } else {
            text = new TranslationTextComponent(LibNames.getTooltipString("normal_mode0"));
            text.mergeStyle(TextFormatting.GOLD);
        }
        player.sendStatusMessage(text, true);
    }

    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World world, @Nonnull List<ITextComponent> tooltip, @Nonnull ITooltipFlag flag) {
        if (hasTag(stack)) {
            if (stack.getOrCreateTag().getBoolean(TAG)) {
                tooltip.add(new TranslationTextComponent(LibNames.getTooltipString("delete_mode1")).mergeStyle(TextFormatting.GOLD));
                tooltip.add(new TranslationTextComponent(LibNames.getTooltipString("delete_mode2"),
                        Registration.BLOCK_HOLLOWED_PUMPKIN.get().getTranslatedName()).mergeStyle(TextFormatting.GOLD));
                return;
            }
        }
        tooltip.add(new TranslationTextComponent(LibNames.getTooltipString("normal_mode1")).mergeStyle(TextFormatting.GOLD));
        tooltip.add(new TranslationTextComponent(LibNames.getTooltipString("normal_mode2"),
                Registration.BLOCK_HOLLOWED_PUMPKIN.get().getTranslatedName()).mergeStyle(TextFormatting.GOLD));
    }
}
