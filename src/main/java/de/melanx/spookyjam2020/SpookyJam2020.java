package de.melanx.spookyjam2020;

import de.melanx.spookyjam2020.core.CreativeTab;
import de.melanx.spookyjam2020.api.ModRecipeTypes;
import de.melanx.spookyjam2020.core.Registration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SpookyJam2020.MODID)
public class SpookyJam2020 {

    public static final String MODID = "spookyjam2020";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public static final CreativeTab creativeTab = new CreativeTab();
    public SpookyJam2020 instance;

    public SpookyJam2020() {
        instance = this;

        Registration.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::onClientSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addGenericListener(IRecipeSerializer.class, ModRecipeTypes::register);
    }

    private void onClientSetup(final FMLClientSetupEvent event) {
        RenderTypeLookup.setRenderLayer(Registration.BLOCK_HOLLOWED_PUMPKIN.get(), RenderType.getCutout());
    }
}
