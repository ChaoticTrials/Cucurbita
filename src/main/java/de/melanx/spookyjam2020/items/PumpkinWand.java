package de.melanx.spookyjam2020.items;

import de.melanx.spookyjam2020.blocks.tiles.TileHollowedPumpkin;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class PumpkinWand extends Item {
    public PumpkinWand(Properties properties) {
        super(properties);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World world, @Nonnull PlayerEntity player, @Nonnull Hand hand) {
        return super.onItemRightClick(world, player, hand);
    }

    @Nonnull
    @Override
    public ActionResultType onItemUse(@Nonnull ItemUseContext context) {
            TileEntity tile = context.getWorld().getTileEntity(context.getPos());
            if(tile instanceof TileHollowedPumpkin) {
                ((TileHollowedPumpkin) tile).onWanded();
                return ActionResultType.SUCCESS;
            }
        return super.onItemUse(context);
    }
}
