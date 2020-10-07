package de.melanx.cucurbita.core;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.BlockHollowedPumpkin;
import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.fluids.FluidPlantOil;
import de.melanx.cucurbita.handler.lootmodifier.PumpkinStemModifier;
import de.melanx.cucurbita.items.PlantOilBucket;
import de.melanx.cucurbita.items.PumpkinWand;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("ConstantConditions")
public class Registration {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, Cucurbita.MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Cucurbita.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Cucurbita.MODID);
    public static final DeferredRegister<TileEntityType<?>> TILES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, Cucurbita.MODID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Cucurbita.MODID);
    private static final Item.Properties ITEM_PROPS = new Item.Properties().group(Cucurbita.creativeTab);
    private static final Item.Properties CONTAINER_ITEM_PROPS = new Item.Properties().group(Cucurbita.creativeTab).maxStackSize(1);

    public static final RegistryObject<Fluid> FLUID_PLANT_OIL = FLUIDS.register(LibNames.PLANT_OIL, FluidPlantOil::new);

    public static final RegistryObject<Block> BLOCK_HOLLOWED_PUMPKIN = BLOCKS.register(LibNames.HOLLOWED_PUMPKIN, BlockHollowedPumpkin::new);

    public static final RegistryObject<Item> ITEM_HOLLOWED_PUMPKIN = ITEMS.register(LibNames.HOLLOWED_PUMPKIN, () -> new BlockItem(BLOCK_HOLLOWED_PUMPKIN.get(), ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PLANT_OIL_BUCKET = ITEMS.register(LibNames.PLANT_OIL_BUCKET, () -> new PlantOilBucket(ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PUMPKIN_STEM = ITEMS.register(LibNames.PUMPKIN_STEM, () -> new BlockItem(Blocks.PUMPKIN_STEM, ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PUMPKIN_WAND = ITEMS.register(LibNames.PUMPKIN_WAND, () -> new PumpkinWand(CONTAINER_ITEM_PROPS));

    public static final RegistryObject<TileEntityType<TileHollowedPumpkin>> TILE_HOLLOWED_PUMPKIN = TILES.register(LibNames.HOLLOWED_PUMPKIN, () -> TileEntityType.Builder.create(TileHollowedPumpkin::new, BLOCK_HOLLOWED_PUMPKIN.get()).build(null));

    public static final RegistryObject<GlobalLootModifierSerializer<PumpkinStemModifier>> PUMPKIN_STEM_MODIFIER = LOOT_MODIFIERS.register("pumpkin_stem", PumpkinStemModifier.Serializer::new);

    public static void init() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        FLUIDS.register(bus);
        Cucurbita.LOGGER.info(FLUIDS.getEntries().size() + " fluids registered.");
        ITEMS.register(bus);
        Cucurbita.LOGGER.info(ITEMS.getEntries().size() + " items registered.");
        BLOCKS.register(bus);
        Cucurbita.LOGGER.info(BLOCKS.getEntries().size() + " blocks registered.");
        TILES.register(bus);
        Cucurbita.LOGGER.info(TILES.getEntries().size() + " tiles registered.");
        LOOT_MODIFIERS.register(bus);
        Cucurbita.LOGGER.info(LOOT_MODIFIERS.getEntries().size() + " loot modifiers registered.");
    }

}
