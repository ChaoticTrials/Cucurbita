package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import net.minecraft.item.Items;
import net.minecraft.potion.*;

public class ModPotions {
    public static final Potion RESISTANCE = new Potion(new EffectInstance(Effects.RESISTANCE, 60 * 20));
    public static final Potion LONG_RESISTANCE = new Potion(new EffectInstance(Effects.RESISTANCE, 4 * 60 * 20));
    public static final Potion ABSORPTION = new Potion(new EffectInstance(Effects.ABSORPTION, 60 * 20));
    public static final Potion ABSORPTION_II = new Potion(new EffectInstance(Effects.ABSORPTION, 60 * 20, 1));
    public static final Potion ABSORPTION_III = new Potion(new EffectInstance(Effects.ABSORPTION, 60 * 20, 2));
    public static final Potion ABSORPTION_IV = new Potion(new EffectInstance(Effects.ABSORPTION, 60 * 20, 3));
    public static final Potion ABSORPTION_V = new Potion(new EffectInstance(Effects.ABSORPTION, 60 * 20, 4));

    public static void register() {
        Cucurbita.getInstance().register("resistance", RESISTANCE);
        Cucurbita.getInstance().register("long_resistance", LONG_RESISTANCE);
        Cucurbita.getInstance().register("absorption", ABSORPTION);
        Cucurbita.getInstance().register("absorption_ii", ABSORPTION_II);
        Cucurbita.getInstance().register("absorption_iii", ABSORPTION_III);
        Cucurbita.getInstance().register("absorption_iv", ABSORPTION_IV);
        Cucurbita.getInstance().register("absorption_v", ABSORPTION_V);
    }

    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, Items.NETHERITE_INGOT, RESISTANCE);
        PotionBrewing.addMix(RESISTANCE, Items.REDSTONE, LONG_RESISTANCE);

        PotionBrewing.addMix(Potions.AWKWARD, Items.GOLDEN_APPLE, ABSORPTION);
        PotionBrewing.addMix(ABSORPTION, Items.GOLDEN_APPLE, ABSORPTION_II);
        PotionBrewing.addMix(ABSORPTION_II, Items.GOLDEN_APPLE, ABSORPTION_III);
        PotionBrewing.addMix(ABSORPTION_III, Items.GOLDEN_APPLE, ABSORPTION_IV);
        PotionBrewing.addMix(ABSORPTION_IV, Items.GOLDEN_APPLE, ABSORPTION_V);
    }
}
