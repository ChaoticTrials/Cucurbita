package de.melanx.cucurbita.data;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.core.registration.Registration;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class BlockStates extends BlockStateProvider {
    public BlockStates(DataGenerator gen, ExistingFileHelper helper) {
        super(gen, Cucurbita.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            VariantBlockStateBuilder builder = this.getVariantBuilder(block);
            if (block != Registration.BLOCK_HOMEMADE_REFINERY.get()) {
                this.createRotatableState(builder, block);
            } else {
                this.createState(builder, block);
            }
        });
    }

    private void createState(VariantBlockStateBuilder builder, Block block) {
        //noinspection ConstantConditions
        builder.partialState().addModels(new ConfiguredModel(this.models().getExistingFile(new ResourceLocation(Cucurbita.MODID, "block/" + block.getRegistryName().getPath()))));
    }

    private void createRotatableState(VariantBlockStateBuilder builder, Block block) {
        @SuppressWarnings("ConstantConditions")
        ModelFile model = this.models().getExistingFile(new ResourceLocation(Cucurbita.MODID, "block/" + block.getRegistryName().getPath()));
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getAllowedValues()) {
            builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .addModels(new ConfiguredModel(model, direction.getHorizontalIndex() == -1 ? direction.getOpposite().getAxisDirection().getOffset() * 90 : 0, (int) direction.getOpposite().getHorizontalAngle(), false));
        }
    }
}
