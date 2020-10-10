package de.melanx.cucurbita.blocks.tiles;

import de.melanx.cucurbita.blocks.base.ModTile;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.util.inventory.BaseItemStackHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
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
    private int progress;

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

    public void addToInventory(ItemStack stack, boolean consume) {
        if (this.world != null) {
            if (this.inventory.getStackInSlot(0).isEmpty()) {
                ItemStack stack1 = stack.copy();
                stack1.setCount(1);
                if (consume) stack.shrink(1);
                this.inventory.setStackInSlot(0, stack1);
                this.markDispatchable();
            }
        }
    }

    @Override
    public void readPacketNBT(CompoundNBT cmp) {
        this.getInventory().deserializeNBT(cmp.getCompound("inventory"));
        this.fluidInventory.setFluid(FluidStack.loadFluidStackFromNBT(cmp.getCompound("fluid")));
        this.progress = cmp.getInt("progress");
    }

    @Override
    public void writePacketNBT(CompoundNBT cmp) {
        cmp.put("inventory", this.getInventory().serializeNBT());
        final CompoundNBT tankTag = new CompoundNBT();
        this.fluidInventory.getFluid().writeToNBT(tankTag);
        cmp.put("fluid", tankTag);
        cmp.putInt("progress", this.progress);
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
