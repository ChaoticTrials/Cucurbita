package de.melanx.cucurbita.fluids;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.FluidAttributes;

import javax.annotation.Nonnull;

public class FluidPlantOil extends Fluid {

    public static final ResourceLocation OIL_SOURCE = new ResourceLocation(Cucurbita.getInstance().modid, "block/fluid/plant_oil");

    @Nonnull
    @Override
    protected FluidAttributes createAttributes() {
        return FluidAttributes.builder(OIL_SOURCE, null).color(0xFFFFFF).build(this);
    }

    @Nonnull
    @Override
    public Item getFilledBucket() {
        return ModItems.PLANT_OIL_BUCKET;
    }

    @Override
    protected boolean canDisplace(@Nonnull FluidState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull Fluid fluid, @Nonnull Direction direction) {
        return true;
    }

    @Nonnull
    @Override
    protected Vector3d getFlow(@Nonnull IBlockReader world, @Nonnull BlockPos pos, @Nonnull FluidState state) {
        return Vector3d.ZERO;
    }

    @Override
    public int getTickRate(@Nonnull IWorldReader world) {
        return 0;
    }

    @Override
    protected float getExplosionResistance() {
        return 0;
    }

    @Override
    public float getActualHeight(@Nonnull FluidState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        return 0;
    }

    @Override
    public float getHeight(@Nonnull FluidState state) {
        return 0;
    }

    @Nonnull
    @Override
    protected BlockState getBlockState(@Nonnull FluidState state) {
        return Blocks.AIR.getDefaultState();
    }

    @Override
    public boolean isSource(@Nonnull FluidState state) {
        return false;
    }

    @Override
    public int getLevel(@Nonnull FluidState state) {
        return 0;
    }

    @Nonnull
    @Override
    public VoxelShape func_215664_b(@Nonnull FluidState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos) {
        return VoxelShapes.empty();
    }
}
