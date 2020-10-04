package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.Registration;
import net.minecraft.data.DataGenerator;

public class LanguageProvider extends net.minecraftforge.common.data.LanguageProvider {
    public LanguageProvider(DataGenerator gen) {
        super(gen, Cucurbita.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + Cucurbita.MODID, "Spooky Jam 2020");
        add(Registration.ITEM_HOLLOWED_PUMPKIN.get(), "Hollowed Pumpkin");
        add(Registration.ITEM_PLANT_OIL_BUCKET.get(), "Plant Oil Bucket");
        add(Registration.ITEM_PUMPKIN_STEM.get(), "Pumpkin Stem");
        add(Registration.ITEM_PUMPKIN_WAND.get(), "Pumpkin Wand");
    }
}
