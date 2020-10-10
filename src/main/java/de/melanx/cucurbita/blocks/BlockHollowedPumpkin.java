package de.melanx.cucurbita.blocks;

import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.util.Util;
import de.melanx.cucurbita.util.VanillaPacketDispatcher;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
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

public class BlockHollowedPumpkin extends Block implements ITileEntityProvider {

    private static final VoxelShape INSIDE = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE), IBooleanFunction.ONLY_FIRST);

    public BlockHollowedPumpkin() {
        super(Properties.create(Material.ORGANIC)
                .harvestTool(ToolType.AXE)
                .harvestLevel(1)
                .hardnessAndResistance(2));
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        TileEntity tile = world.getTileEntity(pos);

        FluidActionResult fluidActionResult = FluidUtil.tryEmptyContainer(player.getHeldItemMainhand(), tile.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).orElse(null), 1000, player, true);
        if (fluidActionResult.isSuccess()) {
            if (!player.isCreative()) {
                player.addItemStackToInventory(fluidActionResult.getResult());
                player.getHeldItemMainhand().shrink(1);
            }
            return ActionResultType.SUCCESS;
        }

        if (tile instanceof TileHollowedPumpkin) {
            if (player.isSneaking()) {
                Util.withdrawFromInventory(((TileHollowedPumpkin) tile).getInventory(), player);
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(tile);
                return ActionResultType.SUCCESS;
            } else {
                if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() != Registration.ITEM_PUMPKIN_WAND.get()) {
                    ((TileHollowedPumpkin) tile).addToInventory(player.getHeldItemMainhand());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onReplaced(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull BlockState newState, boolean isMoving) {
        if (!state.isIn(newState.getBlock())) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileHollowedPumpkin) {
                InventoryHelper.dropInventoryItems(world, pos, ((TileHollowedPumpkin) tile).getInventory().toIInventory());
                world.updateComparatorOutputLevel(pos, this);
            }

            super.onReplaced(state, world, pos, newState, isMoving);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        if (!world.isRemote && entity instanceof ItemEntity) {
            TileHollowedPumpkin tile = (TileHollowedPumpkin) world.getTileEntity(pos);
            if (tile != null && tile.collideEntityItem((ItemEntity) entity)) {
                VanillaPacketDispatcher.dispatchTEToNearbyPlayers(tile);
            }
        }
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new TileHollowedPumpkin();
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

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite());
    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING);
    }
}
