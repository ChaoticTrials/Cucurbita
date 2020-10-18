package de.melanx.cucurbita.items;

import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class StemItem extends ItemBase {

    private final Block block;

    public StemItem(ModX mod, Properties properties, Block block) {
        super(mod, properties);
        this.block = block;
    }

    @Nonnull
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        ActionResultType result = this.tryPlace(new BlockItemUseContext(context));
        //noinspection ConstantConditions
        return !result.isSuccessOrConsume() && this.isFood() ? this.onItemRightClick(context.getWorld(), context.getPlayer(), context.getHand()).getType() : result;
    }

    public ActionResultType tryPlace(BlockItemUseContext context) {
        if (!context.canPlace()) {
            return ActionResultType.FAIL;
        } else {
            BlockState state = block.getStateForPlacement(context);
            BlockPos pos = context.getPos();
            World world = context.getWorld();
            PlayerEntity player = context.getPlayer();
            ItemStack stack = context.getItem();

            if (state == null) {
                return ActionResultType.FAIL;
            }

            if (!context.getWorld().setBlockState(context.getPos(), state, 11)) {
                return ActionResultType.FAIL;
            }

            BlockState placedState = world.getBlockState(pos);
            if (placedState.getBlock() == state.getBlock()) {
                BlockItem.setTileEntityNBT(world, player, pos, stack);
                block.onBlockPlacedBy(world, pos, placedState, player, stack);
                if (player instanceof ServerPlayerEntity) {
                    CriteriaTriggers.PLACED_BLOCK.trigger((ServerPlayerEntity) player, pos, stack);
                }
            }

            SoundType sound = placedState.getSoundType(world, pos, player);
            world.playSound(player, pos, sound.getPlaceSound(), SoundCategory.BLOCKS, (sound.getVolume() + 1) / 2f, sound.getPitch() * 0.8f);
            if (player == null || !player.abilities.isCreativeMode) {
                stack.shrink(1);
            }
            return ActionResultType.func_233537_a_(world.isRemote);
        }
    }
}