package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.tesrs.TesrHollowedPumpkin;
import de.melanx.cucurbita.blocks.tesrs.TesrHomemadeRefinery;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientRegistration {
    public static final ClientRegistration INSTANCE = new ClientRegistration();
    public static IBakedModel HOMEMADE_REFINERY_PRESS;
    private static final ResourceLocation LOC_HOMEMADE_REFINERY_PRESS = new ResourceLocation(Cucurbita.MODID, "block/homemade_refinery_press");

    public void onClientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(Registration.BLOCK_HOLLOWED_PUMPKIN.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(Registration.BLOCK_HOMEMADE_REFINERY.get(), RenderType.getCutout());

        ModelLoader.addSpecialModel(LOC_HOMEMADE_REFINERY_PRESS);

        ClientRegistry.bindTileEntityRenderer(Registration.TILE_HOLLOWED_PUMPKIN.get(), TesrHollowedPumpkin::new);
        ClientRegistry.bindTileEntityRenderer(Registration.TILE_HOMEMADE_REFINERY.get(), TesrHomemadeRefinery::new);
    }

    public void onModelBake(final ModelBakeEvent event) {
        HOMEMADE_REFINERY_PRESS = event.getModelRegistry().get(LOC_HOMEMADE_REFINERY_PRESS);
    }

}
