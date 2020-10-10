package de.melanx.cucurbita.core;

import de.melanx.cucurbita.Cucurbita;

public class LibNames {

    public static final String HOLLOWED_PUMPKIN = "hollowed_pumpkin";
    public static final String HOMEMADE_REFINERY = "homemade_refinery";

    public static final String PLANT_OIL = "plant_oil";
    public static final String PLANT_OIL_BUCKET = PLANT_OIL + "_bucket";
    public static final String PUMPKIN_STEM = "pumpkin_stem";
    public static final String PUMPKIN_WAND = "pumpkin_wand";

    public static String getTooltipString(String text) {
        return "tooltip." + Cucurbita.MODID + "." + text;
    }
}
