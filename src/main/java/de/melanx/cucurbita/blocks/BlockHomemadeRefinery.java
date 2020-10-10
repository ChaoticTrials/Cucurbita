package de.melanx.cucurbita.blocks;

import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.util.DirectionShape;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockHomemadeRefinery extends Block implements ITileEntityProvider {
    private static final VoxelShape INSIDE = VoxelShapes.combine(new DirectionShape(VoxelShapes.or(
            makeCuboidShape(2.0D, 3.0D, 0.0D, 14.0D, 14.0D, 16.0D),
            makeCuboidShape(0.0D, 3.0D, 2.0D, 16.0D, 14.0D, 14.0D),
            makeCuboidShape(6.0D, 1.0D, 1.0D, 10.0D, 3.0D, 15.0D),
            makeCuboidShape(1.0D, 1.0D, 6.0D, 15.0D, 3.0D, 10.0D))).getShape(Direction.NORTH),
            makeCuboidShape(6.0D, 1.0D, 6.0D, 10.0D, 2.0D, 10.0D),
            IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE), IBooleanFunction.ONLY_FIRST);

    public BlockHomemadeRefinery() {
        super(Properties.create(Material.IRON)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .hardnessAndResistance(5)
        );
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader world) {
        return new TileHomemadeRefinery();
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getRenderShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos) {
        return SHAPE;
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader reader, @Nonnull BlockPos pos) {
        return SHAPE;
    }
}
