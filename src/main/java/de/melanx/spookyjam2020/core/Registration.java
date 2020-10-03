package de.melanx.spookyjam2020.core;

import de.melanx.spookyjam2020.SpookyJam2020;
import de.melanx.spookyjam2020.blocks.BlockHollowedPumpkin;
import de.melanx.spookyjam2020.blocks.tiles.TileHollowedPumpkin;
import de.melanx.spookyjam2020.fluids.FluidPlantOil;
import de.melanx.spookyjam2020.items.PlantOilBucket;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public class Registration {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, SpookyJam2020.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, SpookyJam2020.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, SpookyJam2020.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, SpookyJam2020.MODID);
    private static final Item.Properties itemProps = new Item.Properties().group(SpookyJam2020.creativeTab);

    public static final RegistryObject<Fluid> FLUID_PLANT_OIL = FLUIDS.register(LibNames.PLANT_OIL, FluidPlantOil::new);

    public static final RegistryObject<Block> BLOCK_HOLLOWED_PUMPKIN = BLOCKS.register(LibNames.HOLLOWED_PUMPKIN, BlockHollowedPumpkin::new);

    public static final RegistryObject<Item> ITEM_HOLLOWED_PUMPKIN = ITEMS.register(LibNames.HOLLOWED_PUMPKIN, () -> new BlockItem(BLOCK_HOLLOWED_PUMPKIN.get(), itemProps));
    public static final RegistryObject<Item> ITEM_PLANT_OIL_BUCKET = ITEMS.register(LibNames.PLANT_OIL_BUCKET, () -> new PlantOilBucket(itemProps));

    public static final RegistryObject<TileEntityType<TileHollowedPumpkin>> TILE_HOLLOWED_PUMPKIN = TILES.register(LibNames.HOLLOWED_PUMPKIN, () -> TileEntityType.Builder.create(TileHollowedPumpkin::new, BLOCK_HOLLOWED_PUMPKIN.get()).build(null));

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FLUIDS.register(bus);
        SpookyJam2020.LOGGER.info(FLUIDS.getEntries().size() + " fluids registered.");
        ITEMS.register(bus);
        SpookyJam2020.LOGGER.info(ITEMS.getEntries().size() + " items registered.");
        BLOCKS.register(bus);
        SpookyJam2020.LOGGER.info(BLOCKS.getEntries().size() + " blocks registered.");
        TILES.register(bus);
        SpookyJam2020.LOGGER.info(TILES.getEntries().size() + " tiles registered.");
    }

}
