package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;

public class ClientRegistration {
    public static final ClientRegistration INSTANCE = new ClientRegistration();
    public static IBakedModel HOMEMADE_REFINERY_PRESS;
    public static final ResourceLocation LOC_HOMEMADE_REFINERY_PRESS = new ResourceLocation(Cucurbita.getInstance().modid, "block/homemade_refinery_press");

    public void modelRegistryEvent(final ModelRegistryEvent event) {
        ModelLoader.addSpecialModel(LOC_HOMEMADE_REFINERY_PRESS);
    }

    public void onModelBake(final ModelBakeEvent event) {
        HOMEMADE_REFINERY_PRESS = event.getModelRegistry().get(LOC_HOMEMADE_REFINERY_PRESS);
    }
}
