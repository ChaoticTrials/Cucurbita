package de.melanx.cucurbita.blocks.tesrs;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.core.registration.ClientRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.math.vector.Vector3f;

import javax.annotation.Nonnull;

public class TesrHomemadeRefinery extends TileEntityRenderer<TileHomemadeRefinery> {
    public TesrHomemadeRefinery(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull TileHomemadeRefinery tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        IVertexBuilder vertexBuffer = buffer.getBuffer(RenderTypeLookup.func_239220_a_(tile.getBlockState(), false));
        IBakedModel press = ClientRegistration.HOMEMADE_REFINERY_PRESS;
        matrixStack.push();
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
                .renderModelBrightnessColor(matrixStack.getLast(), vertexBuffer, tile.getBlockState(),
                        press, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
        matrixStack.pop();

        matrixStack.push();
        matrixStack.translate(0.5D, 2 / 16D, 0.5D);
        matrixStack.scale(0.6F, 0.6F, 0.6F);
        matrixStack.translate(1.5/16D, 0, -1.5/16D);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(45));

        Minecraft.getInstance().getItemRenderer().renderItem(tile.getInventory().getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, buffer);
        matrixStack.pop();
    }
}
