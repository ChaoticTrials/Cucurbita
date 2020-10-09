package de.melanx.cucurbita.blocks.tesrs;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.base.HorizontalRotatedTesr;
import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;

/*
 * The item rendering part is pretty much taken by the apothecary of Botania
 * https://github.com/Vazkii/Botania/blob/master/src/main/java/vazkii/botania/client/render/tile/RenderTileAltar.java
 */
public class TesrHollowedPumpkin extends HorizontalRotatedTesr<TileHollowedPumpkin> {
    public TesrHollowedPumpkin(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    protected void doRender(@Nonnull TileHollowedPumpkin tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        double fluidPercentage = (double) tile.getFluidInventory().getFluidAmount() / TileHollowedPumpkin.FLUID_CAPACITY;
        if (fluidPercentage > 0) {
            matrixStack.push();
            matrixStack.translate(2 / 16D, 1 / 16D + (14 / 16D * fluidPercentage), 2 / 16D);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.scale(1 / 16F * 1.5F, 1 / 16F * 1.5F, 1 / 16F * 1.5F);

            FluidStack fluidStack = tile.getFluidInventory().getFluid();
            TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
            int fluidColor = fluidStack.getFluid().getAttributes().getColor(tile.getWorld(), tile.getPos());

            IVertexBuilder vertex = buffer.getBuffer(Atlases.getTranslucentCullBlockType());
            renderIconColored(matrixStack, vertex, 0, 0, sprite, 8, 8, 1.0F, fluidColor, light, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }

        int items = 0;
        for (int slot : tile.getInventory().getInputSlots()) {
            if (!tile.getInventory().getStackInSlot(slot).isEmpty())
                items += 1;
        }

        double offsetPerItem = 360D / items;
        double itemTicks = (double) ((float) Cucurbita.getInstance().ticksInGame + partialTicks) / 2;

        matrixStack.push();
        matrixStack.translate(0.5D, fluidPercentage - ((float) 2 / 16), 0.5D);
        matrixStack.scale(0.2F, 0.2F, 0.2F);

        int nextIdx = 0;

        for (int slot : tile.getInventory().getInputSlots()) {
            if (!tile.getInventory().getStackInSlot(slot).isEmpty()) {
                int i = nextIdx++;

                double offset = offsetPerItem * i;
                double deg;
                if (fluidPercentage >= 0.225D) {
                    deg = ((itemTicks / 0.25) % 360) + offset;
                } else {
                    deg = offset;
                }
                double rad = deg * Math.PI / 180;

                double radiusX;
                double radiusZ;
                if (fluidPercentage >= 0.225D) {
                    radiusX = 1.2D + 0.1D * Math.sin(itemTicks / 6);
                    radiusZ = 1.2D + 0.1D * Math.cos(itemTicks / 6);
                } else {
                    radiusX = 1.3D;
                    radiusZ = 1.3D;
                }

                double x = radiusX * Math.cos(rad);
                double z = radiusZ * Math.sin(rad);
                double y = fluidPercentage >= 0.225D ? (float) Math.cos((itemTicks + (double) (50 * i)) / 5.0D) / 10.0F : 1;

                matrixStack.push();
                matrixStack.translate(x, y, z);

                matrixStack.translate(0.0625D, 0.0625D, 0.0625D);
                if (fluidPercentage >= 0.225D) {
                    float xRotate = (float) Math.sin(itemTicks * 0.25D) / 2;
                    float yRotate = (float) Math.max(0.6, Math.sin(itemTicks * 0.1) / 2 + 0.5);
                    float zRotate = (float) Math.cos(itemTicks * 0.25D) / 2;
                    matrixStack.rotate((new Vector3f(xRotate, yRotate, zRotate)).rotationDegrees((float) deg));
                } else {
                    matrixStack.rotate((Vector3f.XP.rotationDegrees(90)));
                }
                matrixStack.translate(-0.0625D, -0.0625D, -0.0625D);

                ItemStack stack = tile.getInventory().getStackInSlot(slot);
                Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, buffer);
                matrixStack.pop();
            }
        }
        matrixStack.pop();
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
