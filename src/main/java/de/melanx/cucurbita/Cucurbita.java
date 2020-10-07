package de.melanx.cucurbita;

import de.melanx.cucurbita.api.ModRecipeTypes;
import de.melanx.cucurbita.blocks.tesrs.TesrHollowedPumpkin;
import de.melanx.cucurbita.core.CreativeTab;
import de.melanx.cucurbita.core.RecipeReloadListener;
import de.melanx.cucurbita.core.Registration;
import de.melanx.cucurbita.sound.ModSounds;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Cucurbita.MODID)
public class Cucurbita {

    public static final String MODID = "cucurbita";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final CreativeTab creativeTab = new CreativeTab();
    public Cucurbita instance;

    public Cucurbita() {
        instance = this;

        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, ModRecipeTypes::register);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(SoundEvent.class, ModSounds::registerSounds);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
        MinecraftForge.EVENT_BUS.register(new RecipeReloadListener(null));
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener(event.getDataPackRegistries()));
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(Registration.BLOCK_HOLLOWED_PUMPKIN.get(), RenderType.getCutout());

        ClientRegistry.bindTileEntityRenderer(Registration.TILE_HOLLOWED_PUMPKIN.get(), TesrHollowedPumpkin::new);
    }
}
