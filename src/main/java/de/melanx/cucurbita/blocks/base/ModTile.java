package de.melanx.cucurbita.blocks.base;

import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import io.github.noeppi_noeppi.libx.mod.registration.TileEntityBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandlerModifiable;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public abstract class ModTile extends TileEntityBase implements ITickableTileEntity {
    public ModTile(TileEntityType<?> type) {
        super(type);
    }

    protected LazyOptional<IItemHandlerModifiable> createHandler(Supplier<IItemHandlerModifiable> inventory) {
        return ItemStackHandlerWrapper.createLazy(inventory);
    }

    @Nonnull
    public abstract BaseItemStackHandler getInventory();

    public abstract boolean isValidStack(int slot, ItemStack stack);

    public abstract void resetFluid(PlayerEntity player);

    public void onWanded() {
    }
}
