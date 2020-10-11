package de.melanx.cucurbita.blocks.tiles;

import de.melanx.cucurbita.api.recipe.HeatSourcesRecipe;
import de.melanx.cucurbita.api.recipe.IRefinery;
import de.melanx.cucurbita.api.recipe.RefineryRecipe;
import de.melanx.cucurbita.blocks.base.ModTile;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.util.inventory.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Predicate;

public class TileHomemadeRefinery extends ModTile {
    public static final int FLUID_CAPACITY = 1000;

    private final BaseItemStackHandler inventory = new BaseItemStackHandler(2, null, null);
    private final ModdedFluidTank fluidInventory = new ModdedFluidTank(FLUID_CAPACITY, fluidStack -> true);
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this.fluidInventory);
    private int progress;
    private RefineryRecipe recipe;
    private int heat;

    public TileHomemadeRefinery() {
        super(Registration.TILE_HOMEMADE_REFINERY.get());
        this.inventory.setInputSlots(0);
        this.inventory.setOutputSlots(1);
    }

    @Nonnull
    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    public TileHomemadeRefinery.ModdedFluidTank getFluidInventory() {
        return this.fluidInventory;
    }

    @Override
    public boolean isValidStack(int slot, ItemStack stack) {
        return true;
    }

    private void updateRecipe() {
        if (this.world != null && !this.world.isRemote) {
            for (IRefinery recipe : RefineryRecipe.REFINERY_RECIPES.values()) {
                if (recipe.matches(this.inventory.toIInventory(), this.world)) {
                    if ((this.fluidInventory.getFluid().isFluidEqual(recipe.getFluidOutput()) || this.fluidInventory.isEmpty())
                            && this.fluidInventory.getCapacity() >= recipe.getFluidOutput().getAmount() + this.fluidInventory.getFluidAmount()
                            && (ItemHandlerHelper.canItemStacksStack(this.inventory.getStackInSlot(1), recipe.getRecipeOutput().copy())
                            || this.inventory.getStackInSlot(0).isEmpty() || recipe.getRecipeOutput().isEmpty())) {
                        this.recipe = (RefineryRecipe) recipe;
                        this.markDispatchable();
                        return;
                    }
                }
            }
        }
        this.recipe = null;
    }

    public boolean hasHeat() {
        return this.heat > 0;
    }

    public int getHeat() {
        return this.heat;
    }

    @Override
    public void tick() {
        if (this.world != null) {
            this.updateRecipe();
            BlockState state = this.world.getBlockState(this.pos.down());
            this.heat = HeatSourcesRecipe.getHeatValue(state);
            if (!this.world.isRemote) {
                if (this.recipe != null) {
                    if (this.progress < 200 && this.getHeat() >= this.recipe.getMinHeat()) {
                        this.progress++;
                        this.markDispatchable();
                    } else if (this.progress >= 200) {
                        this.inventory.getStackInSlot(0).shrink(1);
                        this.inventory.getUnrestricted().insertItem(1, this.recipe.getRecipeOutput().copy(), false);
                        this.fluidInventory.setFluid(new FluidStack(this.recipe.getFluidOutput().getFluid(), this.fluidInventory.getFluid().getAmount() + this.recipe.getFluidOutput().getAmount()));
                        this.progress = 0;
                        this.markDirty();
                        this.markDispatchable();
                    }
                }
            }
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
