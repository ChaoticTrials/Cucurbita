package de.melanx.cucurbita.core;

import de.melanx.cucurbita.Cucurbita;

public class LibNames {

    public static final String HOLLOWED_PUMPKIN = "hollowed_pumpkin";
    public static final String HOMEMADE_REFINERY = "homemade_refinery";

    public static final String PLANT_OIL = "plant_oil";
    public static final String PLANT_OIL_BUCKET = PLANT_OIL + "_bucket";
    public static final String PUMPKIN_STEM = "pumpkin_stem";
    public static final String PUMPKIN_WAND = "pumpkin_wand";

    public static final String PUMPKIN_JAM = "pumpkin_jam";
    public static final String PUMPKIN_PULP = "pumpkin_pulp";
    public static final String PUMPKIN_STEW = "pumpkin_stew";

    public static final String RESISTANCE = "resistance";
    public static final String LONG_RESISTANCE = "long_resistance";
    public static final String ABSORPTION = "absorption";
    public static final String ABSORPTION_II = "absorption_ii";
    public static final String ABSORPTION_III = "absorption_iii";
    public static final String ABSORPTION_IV = "absorption_iv";
    public static final String ABSORPTION_V = "absorption_v";

    public static String getTooltipString(String text) {
        return "tooltip." + Cucurbita.MODID + "." + text;
    }
}
