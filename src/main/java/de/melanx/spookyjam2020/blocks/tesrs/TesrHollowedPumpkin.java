package de.melanx.spookyjam2020.blocks.tesrs;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.melanx.spookyjam2020.blocks.base.HorizontalRotatedTesr;
import de.melanx.spookyjam2020.blocks.tiles.TileHollowedPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

public class TesrHollowedPumpkin extends HorizontalRotatedTesr<TileHollowedPumpkin> {
    public TesrHollowedPumpkin(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected void doRender(@Nonnull TileHollowedPumpkin tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        double fluidPercentage = (double) tile.getFluidInventory().getFluidAmount() / TileHollowedPumpkin.FLUID_CAPACITY;
        if (fluidPercentage > 0) {
            matrixStack.push();
            matrixStack.translate(2 / 16F, 1 / 16F + (14 / 16F * fluidPercentage), 2 / 16F);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.scale(1 / 16F * 1.5F, 1/ 16F * 1.5F, 1 / 16F * 1.5F);

            FluidStack fluidStack = tile.getFluidInventory().getFluid();
            TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
            int fluidColor = fluidStack.getFluid().getAttributes().getColor(tile.getWorld(), tile.getPos());

            IVertexBuilder vertex = buffer.getBuffer(Atlases.getTranslucentCullBlockType());
            renderIconColored(matrixStack, vertex, 0, 0, sprite, 8, 8, 1.0F, fluidColor, light, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }
    }

    public static void renderIconColored(MatrixStack matrixStack, IVertexBuilder buffer, float x, float y, TextureAtlasSprite sprite, float width, float height, float alpha, int color, int light, int overlay) {
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;
        Matrix4f mat = matrixStack.getLast().getMatrix();
        buffer.pos(mat, x, y + height, 0.0F).color(red, green, blue, (int) (alpha * 255.0F)).tex(sprite.getMinU(), sprite.getMaxV()).overlay(overlay).lightmap(light).normal(0.0F, 0.0F, 1.0F).endVertex();
        buffer.pos(mat, x + width, y + height, 0.0F).color(red, green, blue, (int) (alpha * 255.0F)).tex(sprite.getMaxU(), sprite.getMaxV()).overlay(overlay).lightmap(light).normal(0.0F, 0.0F, 1.0F).endVertex();
        buffer.pos(mat, x + width, y, 0.0F).color(red, green, blue, (int) (alpha * 255.0F)).tex(sprite.getMaxU(), sprite.getMinV()).overlay(overlay).lightmap(light).normal(0.0F, 0.0F, 1.0F).endVertex();
        buffer.pos(mat, x, y, 0.0F).color(red, green, blue, (int) (alpha * 255.0F)).tex(sprite.getMinU(), sprite.getMinV()).overlay(overlay).lightmap(light).normal(0.0F, 0.0F, 1.0F).endVertex();
    }
}
