package de.melanx.cucurbita.blocks;

import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.util.DirectionShape;
import de.melanx.cucurbita.util.Util;
import de.melanx.cucurbita.util.VanillaPacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

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

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        TileHomemadeRefinery tile = (TileHomemadeRefinery) world.getTileEntity(pos);
        if (tile != null) {
            FluidActionResult fluidActionResult = FluidUtil.tryFillContainer(player.getHeldItemMainhand(), tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).orElse(null), 1000, player, true);
            if (fluidActionResult.isSuccess()) {
                if (!player.isCreative()) {
                    player.getHeldItemMainhand().shrink(1);
                    player.addItemStackToInventory(fluidActionResult.getResult());
                }
                return ActionResultType.SUCCESS;
            }

            if (player.isSneaking() && tile.getProgressAnimation() <= 100) {
                Util.withdrawFromInventory(tile.getInventory(), player);
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(tile);
                return ActionResultType.SUCCESS;
            } else {
                if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() != Registration.ITEM_PUMPKIN_WAND.get()) {
                    tile.addToInventory(player.getHeldItemMainhand(), !player.isCreative());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
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
