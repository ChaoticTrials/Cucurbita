package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.ItemModelProviderBase;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProviderBase {
    public ItemModels(DataGenerator generator, ExistingFileHelper helper) {
        super(Cucurbita.getInstance(), generator, helper);
        this.manualModel(ModItems.PUMPKIN_STEM);
    }

    @Override
    protected void registerModels() {
        super.registerModels();
        this.defaultItem(ModItems.PUMPKIN_STEM.getRegistryName(), ModItems.PUMPKIN_STEM);
    }
}
