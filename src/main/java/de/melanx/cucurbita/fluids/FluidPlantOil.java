package de.melanx.cucurbita.fluids;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModBlocks;
import de.melanx.cucurbita.core.registration.ModFluids;
import de.melanx.cucurbita.core.registration.ModItems;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class FluidPlantOil {

    public static final ResourceLocation OIL_SOURCE = new ResourceLocation(Cucurbita.getInstance().modid, "block/plant_oil");
    private static final FluidAttributes.Builder ATTRIBUTES = FluidAttributes.builder(OIL_SOURCE, OIL_SOURCE).color(0xC9CB94).viscosity(500);
    private static final ForgeFlowingFluid.Properties PROPERTIES = new ForgeFlowingFluid.Properties(() -> ModFluids.PLANT_OIL_SOURCE, () -> ModFluids.PLANT_OIL_FLOWING, ATTRIBUTES)
            .block(() -> ModBlocks.PLANT_OIL)
            .bucket(() -> ModItems.PLANT_OIL_BUCKET);

    public static class Source extends ForgeFlowingFluid.Source {
        public Source() {
            super(PROPERTIES);
        }
    }

    public static class Flowing extends ForgeFlowingFluid.Flowing {
        public Flowing() {
            super(PROPERTIES);
        }
    }
}
