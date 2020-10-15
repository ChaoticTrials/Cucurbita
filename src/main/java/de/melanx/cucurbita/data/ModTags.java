package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.ModFluids;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITag;

public class ModTags {

    public static class Fluids {
        public static final ITag.INamedTag<Fluid> PLANT_OIL = FluidTags.makeWrapperTag("forge:plant_oil");
    }

    public static class FluidModTags extends FluidTagsProvider {

        public FluidModTags(DataGenerator generator) {
            super(generator, Cucurbita.getInstance().modid, null);
        }

        @Override
        protected void registerTags() {
            this.getOrCreateBuilder(Fluids.PLANT_OIL).add(ModFluids.PLANT_OIL);
        }
    }

}
