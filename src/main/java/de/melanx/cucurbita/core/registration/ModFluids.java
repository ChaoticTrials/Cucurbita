package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.fluids.FluidPlantOil;
import net.minecraft.fluid.Fluid;

public class ModFluids {
    public static final Fluid PLANT_OIL = new FluidPlantOil();

    public static void register() {
        Cucurbita.getInstance().register("plant_oil", PLANT_OIL);
    }
}
