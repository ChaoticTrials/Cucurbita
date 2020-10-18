package de.melanx.cucurbita.items;

import de.melanx.cucurbita.blocks.BlockHollowedPumpkin;
import de.melanx.cucurbita.core.registration.ModBlocks;
import de.melanx.cucurbita.core.registration.ModItems;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Objects;

public class SpoonItem extends ItemBase {
    public SpoonItem(ModX mod, Properties properties) {
        super(mod, properties);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
        World world = context.getWorld();
        if (!world.isRemote) {
            BlockPos pos = context.getPos();
            BlockState state = world.getBlockState(pos);
            boolean drop = false;
            if (state.getBlock() == Blocks.CARVED_PUMPKIN) {
                world.setBlockState(pos, ModBlocks.HOLLOWED_PUMPKIN.getDefaultState().with(BlockHollowedPumpkin.CARVING, 0)
                        .with(BlockStateProperties.HORIZONTAL_FACING, state.get(BlockStateProperties.HORIZONTAL_FACING)), 11);
                drop = true;
            } else if (state.getBlock() == ModBlocks.HOLLOWED_PUMPKIN) {
                int stage = state.get(BlockHollowedPumpkin.CARVING);
                if (stage < 14) {
                    stage++;
                    world.setBlockState(pos, state.with(BlockHollowedPumpkin.CARVING, stage), 11);
                    drop = true;
                }
            } else {
                return ActionResultType.PASS;
            }
            if (drop) {
                ItemStack stack = new ItemStack(ModItems.PUMPKIN_PULP, world.rand.nextInt(3));
                ItemEntity item = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5, stack);
                item.addTag("recipe_output");
                world.addEntity(item);
                //noinspection ConstantConditions
                if (!context.getPlayer().isCreative()) {
                    context.getItem().damageItem(1, Objects.requireNonNull(context.getPlayer()), entity -> entity.sendBreakAnimation(context.getHand()));
                }
                return ActionResultType.SUCCESS;
            }
        }
        return ActionResultType.PASS;
    }
}