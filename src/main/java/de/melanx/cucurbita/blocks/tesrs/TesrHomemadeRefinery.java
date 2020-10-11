package de.melanx.cucurbita.blocks.tesrs;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.core.registration.ClientRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidStack;

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
        matrixStack.translate(1.5 / 16D, 0, -1.5 / 16D);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(90));
        matrixStack.rotate(Vector3f.ZP.rotationDegrees(45));

        Minecraft.getInstance().getItemRenderer().renderItem(tile.getInventory().getStackInSlot(0), ItemCameraTransforms.TransformType.GROUND, light, overlay, matrixStack, buffer);
        matrixStack.pop();

        double fluidPercentage = (double) tile.getFluidInventory().getFluidAmount() / TileHomemadeRefinery.FLUID_CAPACITY;
        if (fluidPercentage > 0) {
            matrixStack.push();
            matrixStack.translate(1 / 16D, fluidPercentage * (2 / 16D - 0.05 / 16D), 1 / 16D);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(90.0F));
            matrixStack.scale(7 / 64F, 7 / 64F, 1 / 16F);

            FluidStack fluidStack = tile.getFluidInventory().getFluid();
            TextureAtlasSprite sprite = Minecraft.getInstance().getAtlasSpriteGetter(PlayerContainer.LOCATION_BLOCKS_TEXTURE).apply(fluidStack.getFluid().getAttributes().getStillTexture(fluidStack));
            int fluidColor = fluidStack.getFluid().getAttributes().getColor(tile.getWorld(), tile.getPos());

            IVertexBuilder vertex = buffer.getBuffer(Atlases.getTranslucentCullBlockType());
            TesrHollowedPumpkin.renderIconColored(matrixStack, vertex, 0, 0, sprite, 8, 8, 1.0F, fluidColor, light, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }
    }
}
