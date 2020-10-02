package de.melanx.spookyjam2020.blocks;

import de.melanx.spookyjam2020.blocks.base.BlockBase;
import de.melanx.spookyjam2020.blocks.tiles.TileHollowedPumpkin;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class HollowedPumpkin extends BlockBase {
    public HollowedPumpkin() {
        super(Properties.create(Material.ORGANIC)
                .harvestLevel(1)
                .hardnessAndResistance(2));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull IBlockReader worldIn) {
        return new TileHollowedPumpkin();
    }
}
