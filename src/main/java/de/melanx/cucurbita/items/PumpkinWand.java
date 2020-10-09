package de.melanx.cucurbita.items;

import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.core.LibNames;
import de.melanx.cucurbita.core.Registration;
import de.melanx.cucurbita.sound.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.stats.Stats;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
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

        if (hasSpecialMode(stack)) {
            BlockRayTraceResult rayTraceResult = rayTrace(world, player, RayTraceContext.FluidMode.SOURCE_ONLY);
            if (rayTraceResult.getType() == RayTraceResult.Type.MISS) {
                return new ActionResult<>(ActionResultType.PASS, stack);
            } else if (rayTraceResult.getType() != RayTraceResult.Type.BLOCK) {
                return new ActionResult<>(ActionResultType.PASS, stack);
            } else {
                BlockPos pos = rayTraceResult.getPos();
                if (world.isBlockModifiable(player, pos) && player.canPlayerEdit(pos, rayTraceResult.getFace(), stack)) {
                    BlockState state = world.getBlockState(pos);
                    if (state.getBlock() instanceof IBucketPickupHandler) {
                        Fluid fluid = ((IBucketPickupHandler) state.getBlock()).pickupFluid(world, pos, state);
                        if (fluid != Fluids.EMPTY) {
                            player.addStat(Stats.ITEM_USED.get(this));
                            player.playSound(ModSounds.WOOSH, 1.0F, 0.8F);
                            for (int x = 0; x < 5; x++) {
                                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + Math.random(), pos.getY() + Math.random(), pos.getZ() + Math.random(), 0, 0.05F, 0);
                            }


                            return new ActionResult<>(ActionResultType.SUCCESS, stack);
                        }
                    }

                    return new ActionResult<>(ActionResultType.FAIL, stack);
                }
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
                ((TileHollowedPumpkin) tile).resetFluid(context.getPlayer());
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
