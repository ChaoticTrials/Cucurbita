package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.BlockHollowedPumpkin;
import de.melanx.cucurbita.blocks.BlockHomemadeRefinery;
import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import io.github.noeppi_noeppi.libx.mod.registration.BlockTE;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class ModBlocks {
    public static final BlockTE<TileHollowedPumpkin> HOLLOWED_PUMPKIN = new BlockHollowedPumpkin(Cucurbita.getInstance(), TileHollowedPumpkin.class, AbstractBlock.Properties.create(Material.ORGANIC).harvestTool(ToolType.AXE).harvestLevel(1).hardnessAndResistance(2));
    public static final BlockTE<TileHomemadeRefinery> HOMEMADE_REFINERY = new BlockHomemadeRefinery(Cucurbita.getInstance(), TileHomemadeRefinery.class, AbstractBlock.Properties.create(Material.IRON).harvestTool(ToolType.PICKAXE).harvestLevel(1).hardnessAndResistance(5));

    public static void register() {
        Cucurbita.getInstance().register("hollowed_pumpkin", HOLLOWED_PUMPKIN);
        Cucurbita.getInstance().register("homemade_refinery", HOMEMADE_REFINERY);
    }
}
