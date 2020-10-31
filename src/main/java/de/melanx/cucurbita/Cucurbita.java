package de.melanx.cucurbita;

import de.melanx.cucurbita.api.ModRecipeTypes;
import de.melanx.cucurbita.core.RecipeReloadListener;
import de.melanx.cucurbita.core.registration.*;
import de.melanx.cucurbita.data.DataCreator;
import de.melanx.cucurbita.sound.ModSounds;
import io.github.noeppi_noeppi.libx.mod.registration.ModXRegistration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import javax.annotation.Nonnull;

@Mod("cucurbita")
public class Cucurbita extends ModXRegistration {

    private static Cucurbita instance;

    public Cucurbita() {
        super("cucurbita", new ItemGroup("cucurbita") {
            @Nonnull
            @Override
            public ItemStack createIcon() {
                return new ItemStack(ModBlocks.HOLLOWED_PUMPKIN);
            }
        });
        instance = this;

        this.addRegistrationHandler(ModPotions::register);
        this.addRegistrationHandler(ModFluids::register);
        this.addRegistrationHandler(ModBlocks::register);
        this.addRegistrationHandler(ModItems::register);
        this.addRegistrationHandler(ModLootModifiers::register);
        this.addRegistrationHandler(ModRecipeTypes::register);
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(DataCreator::onGatherData);
        eventBus.addListener(this::setup);
        eventBus.addGenericListener(SoundEvent.class, ModSounds::registerSounds);
        MinecraftForge.EVENT_BUS.addListener(this::addReloadListeners);
        MinecraftForge.EVENT_BUS.register(new RecipeReloadListener(null));

        DistExecutor.runWhenOn(Dist.CLIENT, () -> Cucurbita::addClientListeners);
    }

    @OnlyIn(Dist.CLIENT)
    private static void addClientListeners() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistration.INSTANCE::onModelBake);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(ClientRegistration.INSTANCE::modelRegistryEvent);
    }

    private void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RecipeReloadListener(event.getDataPackRegistries()));
    }

    @Nonnull
    public static Cucurbita getInstance() {
        return instance;
    }

    @Override
    protected void setup(FMLCommonSetupEvent event) {
        ModPotions.registerBrewingRecipes();
    }

    @Override
    protected void clientSetup(FMLClientSetupEvent event) {
        //
    }
}
