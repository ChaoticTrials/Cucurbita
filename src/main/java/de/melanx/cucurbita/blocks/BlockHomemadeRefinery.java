package de.melanx.cucurbita.blocks;

import de.melanx.cucurbita.blocks.tesrs.TesrHomemadeRefinery;
import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.core.registration.ModItems;
import de.melanx.cucurbita.util.Util;
import io.github.noeppi_noeppi.libx.block.DirectionShape;
import io.github.noeppi_noeppi.libx.mod.ModX;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
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

public class BlockHomemadeRefinery extends BlockTE<TileHomemadeRefinery> {
    private static final VoxelShape INSIDE = VoxelShapes.combine(new DirectionShape(VoxelShapes.or(
            makeCuboidShape(2.0D, 3.0D, 0.0D, 14.0D, 14.0D, 16.0D),
            makeCuboidShape(0.0D, 3.0D, 2.0D, 16.0D, 14.0D, 14.0D),
            makeCuboidShape(6.0D, 1.0D, 1.0D, 10.0D, 3.0D, 15.0D),
            makeCuboidShape(1.0D, 1.0D, 6.0D, 15.0D, 3.0D, 10.0D))).getShape(Direction.NORTH),
            makeCuboidShape(6.0D, 1.0D, 6.0D, 10.0D, 2.0D, 10.0D),
            IBooleanFunction.ONLY_FIRST);
    private static final VoxelShape SHAPE = VoxelShapes.combineAndSimplify(VoxelShapes.fullCube(), VoxelShapes.or(INSIDE), IBooleanFunction.ONLY_FIRST);

    public BlockHomemadeRefinery(ModX mod, Class<TileHomemadeRefinery> teClass, Properties properties) {
        super(mod, teClass, properties);
    }

    @Override
    public void registerClient(ResourceLocation id) {
        ClientRegistry.bindTileEntityRenderer(this.getTileType(), TesrHomemadeRefinery::new);
        RenderTypeLookup.setRenderLayer(this, RenderType.getCutout());
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
                tile.markDispatchable();
                return ActionResultType.SUCCESS;
            } else {
                if (!player.getHeldItemMainhand().isEmpty() && player.getHeldItemMainhand().getItem() != ModItems.PUMPKIN_WAND) {
                    tile.addToInventory(player.getHeldItemMainhand(), !player.isCreative());
                    return ActionResultType.SUCCESS;
                }
            }
        }
        return super.onBlockActivated(state, world, pos, player, hand, hit);
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
