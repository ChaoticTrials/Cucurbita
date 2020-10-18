package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.items.*;
import io.github.noeppi_noeppi.libx.mod.registration.ItemBase;
import net.minecraft.block.Blocks;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;

public class ModItems {
    public static final Item BIO_MASS = new ItemBase(Cucurbita.getInstance(), new Item.Properties());
    public static final Item MELON_STEM = new StemItem(Cucurbita.getInstance(), new Item.Properties(), Blocks.MELON_STEM);
    public static final Item MELON_WAND = new WandItem(Cucurbita.getInstance(), new Item.Properties().maxStackSize(1));
    public static final Item PLANT_OIL_BUCKET = new PlantOilBucket(new Item.Properties().group(Cucurbita.getInstance().tab).maxStackSize(1));
    public static final Item PUMPKIN_PULP = new ItemBase(Cucurbita.getInstance(), new Item.Properties());
    public static final Item PUMPKIN_STEM = new StemItem(Cucurbita.getInstance(), new Item.Properties(), Blocks.PUMPKIN_STEM);
    public static final Item PUMPKIN_STEW = new ContainerItem(Cucurbita.getInstance(), buildFoodProperties(4, 6), Items.BOWL);
    public static final Item PUMPKIN_WAND = new WandItem(Cucurbita.getInstance(), new Item.Properties().maxStackSize(1));
    public static final Item PUMPKIN_JAM = new ContainerItem(Cucurbita.getInstance(), buildFoodProperties(3, 5), Items.GLASS_BOTTLE);
    public static final Item SPOON = new SpoonItem(Cucurbita.getInstance(), new Item.Properties().maxDamage(15));

    // The name is inspired by the Swaggiest stairs ever from snapshot 20w14∞
    public static final Item SUPER_COOL_SPOON = new SpoonItem(Cucurbita.getInstance(), new Item.Properties().maxDamage(1500));

    private static Item.Properties buildFoodProperties(int hunger, float saturation) {
        return new Item.Properties().food(new Food.Builder().hunger(hunger).saturation(saturation).build());
    }

    private static Item.Properties buildFoodProperties(int hunger, float saturation, EffectInstance effect, int chance) {
        return new Item.Properties().food(new Food.Builder().hunger(hunger).saturation(saturation).effect(() -> effect, chance).build());
    }

    public static void register() {
        Cucurbita.getInstance().register("bio_mass", BIO_MASS);
        Cucurbita.getInstance().register("melon_stem", MELON_STEM);
        Cucurbita.getInstance().register("melon_wand", MELON_WAND);
        Cucurbita.getInstance().register("plant_oil_bucket", PLANT_OIL_BUCKET);
        Cucurbita.getInstance().register("pumpkin_pulp", PUMPKIN_PULP);
        Cucurbita.getInstance().register("pumpkin_stem", PUMPKIN_STEM);
        Cucurbita.getInstance().register("pumpkin_stew", PUMPKIN_STEW);
        Cucurbita.getInstance().register("pumpkin_wand", PUMPKIN_WAND);
        Cucurbita.getInstance().register("pumpkin_jam", PUMPKIN_JAM);
        Cucurbita.getInstance().register("spoon", SPOON);
        Cucurbita.getInstance().register("super_cool_spoon", SUPER_COOL_SPOON);

        ComposterBlock.CHANCES.put(BIO_MASS, 0.6F);
        ComposterBlock.CHANCES.put(MELON_STEM, 0.3F);
        ComposterBlock.CHANCES.put(PUMPKIN_PULP, 0.2F);
        ComposterBlock.CHANCES.put(PUMPKIN_STEM, 0.3F);
    }
}
