package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.fluids.FluidPlantOil;

public class ModFluids {
    public static final FluidPlantOil.Source PLANT_OIL_SOURCE = new FluidPlantOil.Source();
    public static final FluidPlantOil.Flowing PLANT_OIL_FLOWING = new FluidPlantOil.Flowing();

    public static void register() {
        Cucurbita.getInstance().register("plant_oil", PLANT_OIL_SOURCE);
        Cucurbita.getInstance().register("plant_oil_flowing", PLANT_OIL_FLOWING);
    }
}
