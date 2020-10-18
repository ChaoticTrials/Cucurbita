package de.melanx.cucurbita.blocks;

import de.melanx.cucurbita.blocks.tesrs.TesrHollowedPumpkin;
import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.core.registration.ModItems;
import de.melanx.cucurbita.items.WandItem;
import de.melanx.cucurbita.util.Util;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockHollowedPumpkin extends BlockTE<TileHollowedPumpkin> {

    public static final IntegerProperty CARVING = IntegerProperty.create("carving", 0, 14);

    private static final VoxelShape INSIDE_0 = makeCuboidShape(2.0D, 15.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_1 = makeCuboidShape(2.0D, 14.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_2 = makeCuboidShape(2.0D, 13.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_3 = makeCuboidShape(2.0D, 12.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_4 = makeCuboidShape(2.0D, 11.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_5 = makeCuboidShape(2.0D, 10.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_6 = makeCuboidShape(2.0D, 9.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_7 = makeCuboidShape(2.0D, 8.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_8 = makeCuboidShape(2.0D, 7.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_9 = makeCuboidShape(2.0D, 6.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_10 = makeCuboidShape(2.0D, 5.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_11 = makeCuboidShape(2.0D, 4.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_12 = makeCuboidShape(2.0D, 3.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_13 = makeCuboidShape(2.0D, 2.0D, 2.0D, 14.0D, 16.0D, 14.0D);
    private static final VoxelShape INSIDE_14 = makeCuboidShape(2.0D, 1.0D, 2.0D, 14.0D, 16.0D, 14.0D);

    private static final VoxelShape SHAPE_0 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_0), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_1 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_1), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_2 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_2), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_3 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_3), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_4 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_4), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_5 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_5), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_6 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_6), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_7 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_7), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_8 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_8), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_9 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_9), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_10 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_10), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_11 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_11), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_12 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_12), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_13 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_13), IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE_14 = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE_14), IBooleanFunction.ONLY_FIRST);

    public BlockHollowedPumpkin(ModX mod, Class<TileHollowedPumpkin> teClass, Properties properties) {
        super(mod, teClass, properties);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), TesrHollowedPumpkin::new);
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public ActionResultType onBlockActivated(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull PlayerEntity player, @Nonnull Hand hand, @Nonnull BlockRayTraceResult hit) {
        TileEntity tile = world.getTileEntity(pos);

        if (state.get(CARVING) == 14) {
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
                    ((TileHollowedPumpkin) tile).markDispatchable();
                    return ActionResultType.SUCCESS;
                } else {
                    if (!player.getHeldItemMainhand().isEmpty() && !(player.getHeldItemMainhand().getItem() instanceof WandItem || player.getHeldItemMainhand().getItem() == ModItems.SPOON)) {
                        ((TileHollowedPumpkin) tile).addToInventory(player.getHeldItemMainhand(), !player.isCreative());
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onEntityCollision(@Nonnull BlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Entity entity) {
        if (!world.isRemote && entity instanceof ItemEntity) {
            TileHollowedPumpkin tile = (TileHollowedPumpkin) world.getTileEntity(pos);
            if (tile != null && tile.collideEntityItem((ItemEntity) entity)) {
                tile.markDispatchable();
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Nonnull
    @Override
    public VoxelShape getShape(@Nonnull BlockState state, @Nonnull IBlockReader worldIn, @Nonnull BlockPos pos, @Nonnull ISelectionContext context) {
        switch (state.get(CARVING)) {
            case 0:
                return SHAPE_0;
            case 1:
                return SHAPE_1;
            case 2:
                return SHAPE_2;
            case 3:
                return SHAPE_3;
            case 4:
                return SHAPE_4;
            case 5:
                return SHAPE_5;
            case 6:
                return SHAPE_6;
            case 7:
                return SHAPE_7;
            case 8:
                return SHAPE_8;
            case 9:
                return SHAPE_9;
            case 10:
                return SHAPE_10;
            case 11:
                return SHAPE_11;
            case 12:
                return SHAPE_12;
            case 13:
                return SHAPE_13;
            case 14:
            default:
                return SHAPE_14;
        }
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(@Nonnull BlockItemUseContext context) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, context.getPlacementHorizontalFacing().getOpposite())
                .with(CARVING, 14);
    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        builder.add(BlockStateProperties.HORIZONTAL_FACING).add(CARVING);
    }
}
