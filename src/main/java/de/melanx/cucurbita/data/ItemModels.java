package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModItems;
import io.github.noeppi_noeppi.libx.data.provider.ItemModelProviderBase;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ItemModels extends ItemModelProviderBase {
    public ItemModels(DataGenerator generator, ExistingFileHelper helper) {
        super(Cucurbita.getInstance(), generator, helper);
    }
}
