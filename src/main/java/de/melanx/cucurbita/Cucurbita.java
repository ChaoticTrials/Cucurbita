package de.melanx.cucurbita;

import de.melanx.cucurbita.api.ModRecipeTypes;
import de.melanx.cucurbita.blocks.tesrs.TesrHollowedPumpkin;
import de.melanx.cucurbita.core.CreativeTab;
import de.melanx.cucurbita.core.RecipeReloadListener;
import de.melanx.cucurbita.core.registration.ClientRegistration;
import de.melanx.cucurbita.core.registration.Registration;
import de.melanx.cucurbita.sound.ModSounds;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(Cucurbita.MODID)
public class Cucurbita {

    public static final String MODID = "cucurbita";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final CreativeTab creativeTab = new CreativeTab();
    public static Cucurbita instance;
    public int ticksInGame = 0;

    public Cucurbita() {
        instance = this;

        Registration.init();
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(ClientRegistration.INSTANCE::onClientSetup);
        eventBus.addListener(ClientRegistration.INSTANCE::onModelBake);
        eventBus.addListener(this::onCommonSetup);
        eventBus.addGenericListener(IRecipeSerializer.class, ModRecipeTypes::register);
        eventBus.addGenericListener(SoundEvent.class, ModSounds::registerSounds);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
        MinecraftForge.EVENT_BUS.addListener(this::onClientTick);
        MinecraftForge.EVENT_BUS.register(new RecipeReloadListener(null));
    }

    private void onCommonSetup(final FMLCommonSetupEvent event) {
        Registration.registerBrewingRecipes();
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener(event.getDataPackRegistries()));
    }

    private void onClientTick(final TickEvent.ClientTickEvent event) {
        this.ticksInGame++;
    }

    public static Cucurbita getInstance() {
        return instance;
    }
}
