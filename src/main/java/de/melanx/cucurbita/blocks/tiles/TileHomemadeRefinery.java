package de.melanx.cucurbita.blocks.tiles;

import de.melanx.cucurbita.blocks.base.ModTile;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.util.inventory.BaseItemStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class TileHomemadeRefinery extends ModTile {
    public static final int FLUID_CAPACITY = 4000;

    private final BaseItemStackHandler inventory = new BaseItemStackHandler(2, null, null);
    private final TileHomemadeRefinery.ModdedFluidTank fluidInventory = new TileHomemadeRefinery.ModdedFluidTank(FLUID_CAPACITY, fluidStack -> true);
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this.fluidInventory);
    public TileHomemadeRefinery() {
        super(Registration.TILE_HOMEMADE_REFINERY.get());
    }

    @Nonnull
    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public boolean isValidStack(int slot, ItemStack stack) {
        return true;
    }

    @Override
    public void tick() {
        if (this.world != null) {

        }
        super.tick();
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.removed && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            this.fluidHandler.cast();
        }
        return super.getCapability(cap, side);
    }

    public static class ModdedFluidTank extends FluidTank {
        public ModdedFluidTank(int capacity, Predicate<FluidStack> validator) {
            super(capacity, validator);
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            return 0;
        }
    }
}
