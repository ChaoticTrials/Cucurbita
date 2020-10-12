package de.melanx.cucurbita.blocks.tiles;

import de.melanx.cucurbita.api.recipe.HeatSourcesRecipe;
import de.melanx.cucurbita.api.recipe.IRefinery;
import de.melanx.cucurbita.api.recipe.RefineryRecipe;
import de.melanx.cucurbita.blocks.base.ModTile;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.sound.ModSounds;
import de.melanx.cucurbita.util.inventory.BaseItemStackHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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
    public static final int FLUID_CAPACITY = 1000;

    private final BaseItemStackHandler inventory = new BaseItemStackHandler(2, null, this::isValidStack);
    private final ModdedFluidTank fluidInventory = new ModdedFluidTank(FLUID_CAPACITY, fluidStack -> true);
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this.fluidInventory);
    private int progress;
    private int progressAnimation;
    private IRefinery recipe;
    private int heat;
    private boolean init;

    public TileHomemadeRefinery() {
        super(Registration.TILE_HOMEMADE_REFINERY.get());
        this.inventory.setInputSlots(0);
        this.inventory.setOutputSlots(1);
        this.inventory.setDefaultSlotLimit(1);
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
        return this.inventory.getStackInSlot(1).isEmpty() && this.progress == 0;
    }

    private void updateRecipe() {
        if (this.world != null && !this.world.isRemote) {
            for (IRefinery recipe : RefineryRecipe.REFINERY_RECIPES.values()) {
                if (recipe.matches(this.inventory.toIInventory(), this.world)) {
                    if ((this.fluidInventory.getFluid().isFluidEqual(recipe.getFluidOutput()) || this.fluidInventory.isEmpty())
                            && this.fluidInventory.getCapacity() >= recipe.getFluidOutput().getAmount() + this.fluidInventory.getFluidAmount()
                            && this.inventory.getStackInSlot(1).isEmpty()) {
                        this.recipe = recipe;
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

    public int getProgress() {
        return this.progress;
    }

    public int getProgressAnimation() {
        return this.progressAnimation;
    }

    @Override
    public void tick() {
        if (this.world != null) {
            if (!this.init) this.init = true;
            this.updateRecipe();
            BlockState state = this.world.getBlockState(this.pos.down());
            this.heat = HeatSourcesRecipe.getHeatValue(state);
            if (!this.world.isRemote) {
                if (this.recipe != null) {
                    if (this.progress < 150 && this.getHeat() >= this.recipe.getMinHeat()) {
                        this.progress++;
                        this.progressAnimation++;
                        this.markDispatchable();
                    } else if (this.progress >= 150) {
                        this.inventory.setStackInSlot(0, this.recipe.getRecipeOutput().copy());
                        this.fluidInventory.setFluid(new FluidStack(this.recipe.getFluidOutput().getFluid(), this.fluidInventory.getFluid().getAmount() + this.recipe.getFluidOutput().getAmount()));
                        this.progress = 0;
                        this.markDirty();
                        this.markDispatchable();
                    }
                } else {
                    if (this.progress > 0 || this.progressAnimation > 0) {
                        this.progress = 0;
                        if (this.progressAnimation >= 200) {
                            ItemStack output = this.inventory.getUnrestricted().extractItem(0, 1, false);
                            this.inventory.getUnrestricted().insertItem(1, output, false);
                            this.progressAnimation = 0;
                        } else if (this.progressAnimation >= 150) {
                            this.progressAnimation++;
                        } else {
                            if (this.progressAnimation > 100) this.progressAnimation = 150;
                            this.progressAnimation = Math.max(0, this.progressAnimation -= 2);
                        }
                        this.markDispatchable();
                    }
                }
            }
        }
        super.tick();
    }

    @Override
    public void resetFluid(PlayerEntity player) {
        if (this.world != null && !this.fluidInventory.isEmpty() && this.recipe == null) {
            this.fluidInventory.setFluid(FluidStack.EMPTY);
            for (int i = 0; i < 5; i++) {
                this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, pos.getX() + 0.5D + world.rand.nextDouble() * 0.1D, pos.getY() + 0.4D + world.rand.nextDouble(), pos.getZ() + 0.5D + world.rand.nextDouble() * 0.1D, 0.0D, 0.05D, 0.0D);
            }
            player.playSound(ModSounds.WOOSH, 1.0F, 0.8F);
            this.markDispatchable();
        }
    }

    public void addToInventory(ItemStack stack, boolean consume) {
        if (this.world != null) {
            if (this.inventory.getStackInSlot(0).isEmpty() && this.inventory.getStackInSlot(1).isEmpty() && this.progressAnimation == 0) {
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
        this.progressAnimation = cmp.getInt("animation");
        if (!this.init) {
            RefineryRecipe.REFINERY_RECIPES.values().forEach(recipe -> {
                ResourceLocation savedRecipe = ResourceLocation.tryCreate(cmp.getString("recipe"));
                if (savedRecipe == null) return;
                if (recipe.getId() == savedRecipe) {
                    this.recipe = recipe;
                }
            });
        }
    }

    @Override
    public void writePacketNBT(CompoundNBT cmp) {
        cmp.put("inventory", this.getInventory().serializeNBT());
        final CompoundNBT tankTag = new CompoundNBT();
        this.fluidInventory.getFluid().writeToNBT(tankTag);
        cmp.put("fluid", tankTag);
        cmp.putInt("progress", this.progress);
        cmp.putInt("animation", this.progressAnimation);
        if (this.init) {
            if (this.recipe != null) {
                cmp.putString("recipe", this.recipe.getId().toString());
            } else {
                cmp.putString("recipe", "");
            }
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (!this.removed && cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return this.fluidHandler.cast();
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
