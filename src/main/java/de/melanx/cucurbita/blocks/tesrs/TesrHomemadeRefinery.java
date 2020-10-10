package de.melanx.cucurbita.blocks.tesrs;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.core.registration.ClientRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

import javax.annotation.Nonnull;

public class TesrHomemadeRefinery extends TileEntityRenderer<TileHomemadeRefinery> {
    public TesrHomemadeRefinery(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(@Nonnull TileHomemadeRefinery tile, float partialTicks, @Nonnull MatrixStack matrixStack, @Nonnull IRenderTypeBuffer buffer, int light, int overlay) {
        IVertexBuilder vertexBuffer = buffer.getBuffer(RenderTypeLookup.func_239220_a_(tile.getBlockState(), false));
        IBakedModel press = ClientRegistration.HOMEMADE_REFINERY_PRESS;
        Minecraft.getInstance().getBlockRendererDispatcher().getBlockModelRenderer()
                .renderModelBrightnessColor(matrixStack.getLast(), vertexBuffer, tile.getBlockState(),
                        press, 1, 1, 1, light, OverlayTexture.NO_OVERLAY);
    }
}
