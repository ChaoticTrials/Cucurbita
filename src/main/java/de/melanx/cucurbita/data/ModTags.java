package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.LibNames;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;

public class ModTags {

    public static class Fluids {
        public static final ITag.INamedTag<Fluid> PLANT_OIL = FluidTags.makeWrapperTag("forge:" + LibNames.PLANT_OIL);
    }

    public static class FluidModTags extends FluidTagsProvider {

        public FluidModTags(DataGenerator generator) {
            super(generator, Cucurbita.MODID, null);
        }

        @Override
        protected void registerTags() {
            this.getOrCreateBuilder(Fluids.PLANT_OIL).add(Registration.FLUID_PLANT_OIL.get());
        }
    }

}
