package de.melanx.spookyjam2020.data;

import de.melanx.spookyjam2020.SpookyJam2020;
import de.melanx.spookyjam2020.core.Registration;
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
        super(gen, SpookyJam2020.MODID, helper);
    }

    @Override
    protected void registerStatesAndModels() {
        Registration.BLOCKS.getEntries().stream().map(RegistryObject::get).forEach(block -> {
            VariantBlockStateBuilder builder = this.getVariantBuilder(block);
            this.createRotatableState(builder, block);
        });
    }

    private void createRotatableState(VariantBlockStateBuilder builder, Block block) {
        @SuppressWarnings("ConstantConditions")
        ModelFile model = this.models().getExistingFile(new ResourceLocation(SpookyJam2020.MODID, "block/" + block.getRegistryName().getPath()));
        for (Direction direction : BlockStateProperties.HORIZONTAL_FACING.getAllowedValues()) {
            builder.partialState().with(BlockStateProperties.HORIZONTAL_FACING, direction)
                    .addModels(new ConfiguredModel(model, direction.getHorizontalIndex() == -1 ? direction.getOpposite().getAxisDirection().getOffset() * 90 : 0, (int) direction.getOpposite().getHorizontalAngle(), false));
        }
    }
}
