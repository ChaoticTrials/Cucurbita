package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ItemModels extends ItemModelProvider {
    public ItemModels(DataGenerator generator, ExistingFileHelper helper) {
        super(generator, Cucurbita.MODID, helper);
    }

    @Override
    protected void registerModels() {
        for (RegistryObject<Item> item : Registration.ITEMS.getEntries()) {
            if (item.get() instanceof BlockItem && item.get() != Registration.ITEM_PUMPKIN_STEM.get())
                this.generateBlockItem(item.get());
            else
                this.generateItem(item.get());
        }
    }

    private void generateBlockItem(Item block) {
        @SuppressWarnings("ConstantConditions")
        String path = block.getRegistryName().getPath();
        this.getBuilder(path)
                .parent(new ModelFile.UncheckedModelFile(this.modLoc("block/" + path)));
    }

    private void generateItem(Item item) {
        @SuppressWarnings("ConstantConditions")
        String path = item.getRegistryName().getPath();
        this.getBuilder(path).parent(this.getExistingFile(this.mcLoc("item/handheld")))
                .texture("layer0", "item/" + path);
    }
}
