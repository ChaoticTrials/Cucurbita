package de.melanx.cucurbita.sound;

import de.melanx.cucurbita.Cucurbita;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.IForgeRegistry;

public class ModSounds {
    public static final SoundEvent WOOSH = createSoundEvent("woosh");

    private static SoundEvent createSoundEvent(final String soundName) {
        final ResourceLocation soundID = new ResourceLocation(Cucurbita.MODID, soundName);
        return new SoundEvent(soundID).setRegistryName(soundID);
    }

    public static void registerSounds(RegistryEvent.Register<SoundEvent> event) {
        IForgeRegistry<SoundEvent> registry = event.getRegistry();
        registry.register(WOOSH);
    }
}
