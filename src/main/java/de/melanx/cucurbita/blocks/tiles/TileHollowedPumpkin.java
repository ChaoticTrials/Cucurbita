package de.melanx.cucurbita.blocks.tiles;

import de.melanx.cucurbita.api.recipe.HeatSourcesRecipe;
import de.melanx.cucurbita.api.recipe.HollowedPumpkinRecipe;
import de.melanx.cucurbita.api.recipe.IHollowedPumpkin;
import de.melanx.cucurbita.blocks.BlockHollowedPumpkin;
import de.melanx.cucurbita.blocks.base.ModTile;
import de.melanx.cucurbita.sound.ModSounds;
import io.github.noeppi_noeppi.libx.inventory.BaseItemStackHandler;
import io.github.noeppi_noeppi.libx.inventory.ItemStackHandlerWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class TileHollowedPumpkin extends ModTile {

    public static final int FLUID_CAPACITY = 2000;
    private static final FluidStack WATER = new FluidStack(Fluids.WATER, 1);

    private final LazyOptional<IItemHandlerModifiable> handler = this.createHandler(this::getInventory);
    private final BaseItemStackHandler inventory = new BaseItemStackHandler(16, this::onSlotChanged, this::isValidStack);
    private final ModdedFluidTank fluidInventory = new ModdedFluidTank(FLUID_CAPACITY, fluidStack -> true, this::onSlotChanged);
    private final LazyOptional<IFluidHandler> fluidHandler = LazyOptional.of(() -> this.fluidInventory);

    private int progress;
    private int heat;
    private HollowedPumpkinRecipe recipe;
    private boolean init;

    public TileHollowedPumpkin(TileEntityType<?> type) {
        super(type);
        this.inventory.setDefaultSlotLimit(1);
        this.inventory.setInputSlots(IntStream.range(0, 16).toArray());
    }

    protected LazyOptional<IItemHandlerModifiable> createHandler(Supplier<IItemHandlerModifiable> inventory) {
        return ItemStackHandlerWrapper.createLazy(inventory);
    }

    @Nonnull
    @Override
    public BaseItemStackHandler getInventory() {
        return this.inventory;
    }

    public FluidTank getFluidInventory() {
        return this.fluidInventory;
    }

    public boolean isValidStack(int slot, ItemStack stack) {
        return Arrays.stream(this.inventory.getInputSlots()).noneMatch(x -> x == slot);
    }

    private void onSlotChanged(int slot) {
        this.updateRecipe();
        this.markDispatchable();
        this.markDirty();
    }

    public boolean hasHeat() {
        return this.heat > 0;
    }

    public int getHeat() {
        return this.heat;
    }

    private void updateRecipe() {
        if (this.world != null && !this.world.isRemote) {
            for (IHollowedPumpkin recipe : HollowedPumpkinRecipe.PUMPKIN_RECIPES.values()) {
                if (recipe.matches(this.inventory.toIInventory(), this.world)) {
                    if (this.fluidInventory.getFluid().isFluidEqual(recipe.getFluidInput())
                            && this.fluidInventory.getFluidAmount() >= recipe.getFluidInput().getAmount()) {
                        this.recipe = (HollowedPumpkinRecipe) recipe;
                        this.markDispatchable();
                        return;
                    }
                }
            }
        }
        this.recipe = null;
    }

    @Override
    public void tick() {
        if (world != null && getBlockState().get(BlockHollowedPumpkin.CARVING) == 14) {
            if (!this.init) {
                this.init = true;
                this.markDispatchable();
            }
            BlockState state = this.world.getBlockState(this.pos.down());
            this.heat = HeatSourcesRecipe.getHeatValue(state);
            if (!this.world.isRemote) {
                if (this.recipe != null) {
                    if (this.progress < 200 && this.getHeat() >= recipe.getMinHeat()) {
                        this.progress++;
                        this.markDispatchable();
                    }
                }
                if (this.world.getWorldInfo().isRaining() && this.world.canBlockSeeSky(this.pos) &&
                        (this.fluidInventory.isEmpty() || (this.fluidInventory.getFluid().getFluid() == Fluids.WATER &&
                                this.fluidInventory.getFluidAmount() < this.fluidInventory.getCapacity())) &&
                        recipe == null) {
                    this.fluidInventory.fill(WATER, IFluidHandler.FluidAction.EXECUTE);
                    this.markDispatchable();
                }
            } else {
                if (this.progress > 0) {
                    if (this.world.rand.nextDouble() < 0.3D) {
                        double x = pos.getX() + world.rand.nextDouble();
                        double y = pos.getY() + 0.4D + world.rand.nextDouble();
                        double z = pos.getZ() + world.rand.nextDouble();
                        if (this.progress < 200) {
                            this.world.addParticle(ParticleTypes.LARGE_SMOKE, x, y, z, 0.0D, 0.05D, 0.0D);
                        } else {
                            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, 0.0D, 0.0D, 0.0D);
                        }
                    }
                }
                if (this.fluidInventory.getFluidAmount() > 0 && this.hasHeat()) {
                    Random rand = this.world.rand;
                    if (rand.nextDouble() < 0.1D) {
                        double x = this.pos.getX() + 2 / 16D + rand.nextDouble() * 12 / 16D;
                        double y = this.pos.getY() + 1 / 16D + (double) this.fluidInventory.getFluidAmount() / TileHollowedPumpkin.FLUID_CAPACITY + rand.nextDouble() * 0.3D;
                        double z = this.pos.getZ() + 2 / 16D + rand.nextDouble() * 12 / 16D;
                        this.world.addParticle(ParticleTypes.AMBIENT_ENTITY_EFFECT, x, y, z, 0.0F, 0.0F, 0.0F);
                    }
                }
            }
        }
    }

    private int getFreeSlot() {
        for (int i = 0; i < this.inventory.getInputSlots().length; i++) {
            if (this.inventory.getStackInSlot(i).isEmpty()) return i;
        }
        return -1;
    }

    public void addToInventory(ItemStack stack, boolean consume) {
        if (this.world != null) {
            if (this.getFreeSlot() != -1) {
                ItemStack stack1 = stack.copy();
                stack1.setCount(1);
                if (consume) stack.shrink(1);
                this.inventory.setStackInSlot(this.getFreeSlot(), stack1);
                this.markDispatchable();
            }
        }
    }

    @Override
    public void onWanded() {
        if (this.world != null && !this.world.isRemote && this.recipe != null && this.progress >= 200) {
            this.recipe.getIngredients().forEach(ingredient -> {
                for (ItemStack stack : inventory.getStacks()) {
                    if (ingredient.test(stack)) {
                        stack.shrink(1);
                        break;
                    }
                }
            });
            this.fluidInventory.getFluid().setAmount(this.fluidInventory.getFluidAmount() - recipe.getFluidInput().getAmount());
            for (Pair<ItemStack, Double> output : this.recipe.getOutputs()) {
                if (this.world.rand.nextDouble() < output.getValue()) {
                    ItemEntity item = new ItemEntity(this.world, this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D, output.getKey().copy());
                    item.addTag("recipe_output");
                    this.world.addEntity(item);
                }
            }
            this.progress = 0;
            this.recipe = null;
            this.markDispatchable();
        }
    }

    @Override
    public void resetFluid(PlayerEntity player) {
        if (this.world != null && !this.fluidInventory.isEmpty() && this.recipe == null) {
            this.fluidInventory.setFluid(FluidStack.EMPTY);
            for (int i = 0; i < 5; i++) {
                double x = pos.getX() + 0.5D + world.rand.nextDouble() * 0.1D;
                double y = pos.getY() + 0.4D + world.rand.nextDouble();
                double z = pos.getZ() + 0.5D + world.rand.nextDouble() * 0.1D;
                this.world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE, x, y, z, 0.0D, 0.05D, 0.0D);
            }
            player.playSound(ModSounds.WOOSH, 1.0F, 0.8F);
            this.markDispatchable();
        }
    }

    public boolean collideEntityItem(ItemEntity item) {
        if (item.getTags().contains("recipe_output")) return false;
        int freeSlots = (int) this.inventory.getStacks().stream().filter(ItemStack::isEmpty).count();
        ItemStack stack = item.getItem().copy();
        if ((this.world != null && this.world.isRemote) || stack.isEmpty() || !item.isAlive()) return false;
        for (int i = 0; i < freeSlots; i++) {
            if (!item.getItem().isEmpty() && this.getFreeSlot() != -1) {
                stack.setCount(1);
                item.getItem().shrink(1);
                this.inventory.setStackInSlot(this.getFreeSlot(), stack);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void read(@Nonnull BlockState state, @Nonnull CompoundNBT cmp) {
        super.read(state, cmp);
        this.inventory.deserializeNBT(cmp.getCompound("inventory"));
        this.fluidInventory.setFluid(FluidStack.loadFluidStackFromNBT(cmp.getCompound("fluid")));
        this.progress = cmp.getInt("progress");
    }

    @Nonnull
    @Override
    public CompoundNBT write(@Nonnull CompoundNBT cmp) {
        cmp.put("inventory", this.inventory.serializeNBT());
        final CompoundNBT tankTag = new CompoundNBT();
        this.getFluidInventory().getFluid().writeToNBT(tankTag);
        cmp.put("fluid", tankTag);
        cmp.putInt("progress", this.progress);
        return super.write(cmp);
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT cmp) {
        if (world != null && !world.isRemote) return;
        this.inventory.deserializeNBT(cmp.getCompound("inventory"));
        this.fluidInventory.setFluid(FluidStack.loadFluidStackFromNBT(cmp.getCompound("fluid")));
        this.progress = cmp.getInt("progress");
    }

    @Nonnull
    @Override
    public CompoundNBT getUpdateTag() {
        if (world != null && world.isRemote) return super.getUpdateTag();
        CompoundNBT cmp = super.getUpdateTag();
        cmp.put("inventory", this.inventory.serializeNBT());
        final CompoundNBT tankTag = new CompoundNBT();
        this.getFluidInventory().getFluid().writeToNBT(tankTag);
        cmp.put("fluid", tankTag);
        cmp.putInt("progress", this.progress);
        return cmp;
    }

    @Nonnull
    @Override
    public <X> LazyOptional<X> getCapability(@Nonnull Capability<X> cap, @Nullable Direction side) {
        if (this.getBlockState().get(BlockHollowedPumpkin.CARVING) == 14) {
            if (!this.removed && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
                return this.handler.cast();
            }
            if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
                return this.fluidHandler.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    private static class ModdedFluidTank extends FluidTank {
        private final Consumer<Integer> onContentsChanged;

        public ModdedFluidTank(int capacity, Predicate<FluidStack> validator, Consumer<Integer> onContentsChanged) {
            super(capacity, validator);
            this.onContentsChanged = onContentsChanged;
        }

        @Override
        protected void onContentsChanged() {
            if (this.onContentsChanged != null) {
                this.onContentsChanged.accept(-1);
            }
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action) {
            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, IFluidHandler.FluidAction action) {
            return FluidStack.EMPTY;
        }
    }
}
