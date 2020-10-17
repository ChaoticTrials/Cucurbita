package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.items.PlantOilBucket;
import de.melanx.cucurbita.items.PumpkinWand;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;

public class ModItems {
    public static final Item BIO_MASS = new ItemBase(Cucurbita.getInstance(), new Item.Properties());
    public static final Item PLANT_OIL_BUCKET = new PlantOilBucket(new Item.Properties().group(Cucurbita.getInstance().tab).maxStackSize(1));
    public static final Item PUMPKIN_PULP = new ItemBase(Cucurbita.getInstance(), new Item.Properties());
    public static final Item PUMPKIN_STEM = new BlockItem(Blocks.PUMPKIN_STEM, new Item.Properties().group(Cucurbita.getInstance().tab));
    public static final Item PUMPKIN_STEW = new ItemBase(Cucurbita.getInstance(), buildFoodProperties(4, 6));
    public static final Item PUMPKIN_WAND = new PumpkinWand(Cucurbita.getInstance(), new Item.Properties().maxStackSize(1));
    public static final Item PUMPKIN_JAM = new ItemBase(Cucurbita.getInstance(), buildFoodProperties(3, 5));

    private static Item.Properties buildFoodProperties(int hunger, float saturation) {
        return new Item.Properties().food(new Food.Builder().hunger(hunger).saturation(saturation).build());
    }

    private static Item.Properties buildFoodProperties(int hunger, float saturation, EffectInstance effect, int chance) {
        return new Item.Properties().food(new Food.Builder().hunger(hunger).saturation(saturation).effect(() -> effect, chance).build());
    }

    public static void register() {
        Cucurbita.getInstance().register("bio_mass", BIO_MASS);
        Cucurbita.getInstance().register("plant_oil_bucket", PLANT_OIL_BUCKET);
        Cucurbita.getInstance().register("pumpkin_pulp", PUMPKIN_PULP);
        Cucurbita.getInstance().register("pumpkin_stem", PUMPKIN_STEM);
        Cucurbita.getInstance().register("pumpkin_stew", PUMPKIN_STEW);
        Cucurbita.getInstance().register("pumpkin_wand", PUMPKIN_WAND);
        Cucurbita.getInstance().register("pumpkin_jam", PUMPKIN_JAM);
    }
}
