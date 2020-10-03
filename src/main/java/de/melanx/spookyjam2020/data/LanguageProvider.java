package de.melanx.spookyjam2020.data;

import de.melanx.spookyjam2020.SpookyJam2020;
import de.melanx.spookyjam2020.core.Registration;
import net.minecraft.data.DataGenerator;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    public LanguageProvider(DataGenerator gen) {
        super(gen, SpookyJam2020.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + SpookyJam2020.MODID, "Spooky Jam 2020");
        add(Registration.ITEM_HOLLOWED_PUMPKIN.get(), "Hollowed Pumpkin");
        add(Registration.ITEM_PLANT_OIL_BUCKET.get(), "Plant Oil Bucket");
    }
}
