package de.melanx.cucurbita.core.registration;

import de.melanx.cucurbita.Cucurbita;
import de.melanx.cucurbita.blocks.BlockHollowedPumpkin;
import de.melanx.cucurbita.blocks.BlockHomemadeRefinery;
import de.melanx.cucurbita.blocks.tiles.TileHollowedPumpkin;
import de.melanx.cucurbita.blocks.tiles.TileHomemadeRefinery;
import de.melanx.cucurbita.core.LibNames;
import de.melanx.cucurbita.fluids.FluidPlantOil;
import de.melanx.cucurbita.handler.lootmodifier.PumpkinStemModifier;
import de.melanx.cucurbita.items.PlantOilBucket;
import de.melanx.cucurbita.items.PumpkinWand;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.potion.*;
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
    public static final DeferredRegister<Potion> POTIONS = DeferredRegister.create(ForgeRegistries.POTION_TYPES, Cucurbita.MODID);
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, Cucurbita.MODID);
    private static final Item.Properties ITEM_PROPS = new Item.Properties().group(Cucurbita.creativeTab);
    private static final Item.Properties CONTAINER_ITEM_PROPS = new Item.Properties().group(Cucurbita.creativeTab).maxStackSize(1);

    public static final RegistryObject<Fluid> FLUID_PLANT_OIL = FLUIDS.register(LibNames.PLANT_OIL, FluidPlantOil::new);

    public static final RegistryObject<Block> BLOCK_HOLLOWED_PUMPKIN = BLOCKS.register(LibNames.HOLLOWED_PUMPKIN, BlockHollowedPumpkin::new);
    public static final RegistryObject<Block> BLOCK_HOMEMADE_REFINERY = BLOCKS.register(LibNames.HOMEMADE_REFINERY, BlockHomemadeRefinery::new);

    public static final RegistryObject<Item> ITEM_HOLLOWED_PUMPKIN = ITEMS.register(LibNames.HOLLOWED_PUMPKIN, () -> new BlockItem(BLOCK_HOLLOWED_PUMPKIN.get(), ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_HOMEMADE_REFINERY = ITEMS.register(LibNames.HOMEMADE_REFINERY, () -> new BlockItem(BLOCK_HOMEMADE_REFINERY.get(), ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PLANT_OIL_BUCKET = ITEMS.register(LibNames.PLANT_OIL_BUCKET, () -> new PlantOilBucket(CONTAINER_ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PUMPKIN_PULP = ITEMS.register(LibNames.PUMPKIN_PULP, () -> new Item(ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PUMPKIN_STEM = ITEMS.register(LibNames.PUMPKIN_STEM, () -> new BlockItem(Blocks.PUMPKIN_STEM, ITEM_PROPS));
    public static final RegistryObject<Item> ITEM_PUMPKIN_STEW = ITEMS.register(LibNames.PUMPKIN_STEW, () -> new Item(buildFoodProperties(4, 6)));
    public static final RegistryObject<Item> ITEM_PUMPKIN_WAND = ITEMS.register(LibNames.PUMPKIN_WAND, () -> new PumpkinWand(CONTAINER_ITEM_PROPS));
    public static final RegistryObject<Item> FOOD_PUMPKIN_JAM = ITEMS.register(LibNames.PUMPKIN_JAM, () -> new Item(buildFoodProperties(3, 5)));

    public static final RegistryObject<TileEntityType<TileHollowedPumpkin>> TILE_HOLLOWED_PUMPKIN = TILES.register(LibNames.HOLLOWED_PUMPKIN, () -> TileEntityType.Builder.create(TileHollowedPumpkin::new, BLOCK_HOLLOWED_PUMPKIN.get()).build(null));
    public static final RegistryObject<TileEntityType<TileHomemadeRefinery>> TILE_HOMEMADE_REFINERY = TILES.register(LibNames.HOMEMADE_REFINERY, () -> TileEntityType.Builder.create(TileHomemadeRefinery::new, BLOCK_HOMEMADE_REFINERY.get()).build(null));

    public static final RegistryObject<Potion> POTION_RESISTANCE = POTIONS.register(LibNames.RESISTANCE, () -> new Potion(LibNames.RESISTANCE, new EffectInstance(Effects.RESISTANCE, 60 * 20)));
    public static final RegistryObject<Potion> POTION_LONG_RESISTANCE = POTIONS.register(LibNames.LONG_RESISTANCE, () -> new Potion(LibNames.LONG_RESISTANCE, new EffectInstance(Effects.RESISTANCE, 4 * 60 * 20)));
    public static final RegistryObject<Potion> POTION_ABSORPTION = POTIONS.register(LibNames.ABSORPTION, () -> new Potion(LibNames.ABSORPTION, new EffectInstance(Effects.ABSORPTION, 60 * 20)));
    public static final RegistryObject<Potion> POTION_ABSORPTION_II = POTIONS.register(LibNames.ABSORPTION_II, () -> new Potion(LibNames.ABSORPTION_II, new EffectInstance(Effects.ABSORPTION, 60 * 20, 1)));
    public static final RegistryObject<Potion> POTION_ABSORPTION_III = POTIONS.register(LibNames.ABSORPTION_III, () -> new Potion(LibNames.ABSORPTION_III, new EffectInstance(Effects.ABSORPTION, 60 * 20, 2)));
    public static final RegistryObject<Potion> POTION_ABSORPTION_IV = POTIONS.register(LibNames.ABSORPTION_IV, () -> new Potion(LibNames.ABSORPTION_IV, new EffectInstance(Effects.ABSORPTION, 60 * 20, 3)));
    public static final RegistryObject<Potion> POTION_ABSORPTION_V = POTIONS.register(LibNames.ABSORPTION_V, () -> new Potion(LibNames.ABSORPTION_V, new EffectInstance(Effects.ABSORPTION, 60 * 20, 4)));

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
        POTIONS.register(bus);
        Cucurbita.LOGGER.info(POTIONS.getEntries().size() + " potions registered.");
        LOOT_MODIFIERS.register(bus);
        Cucurbita.LOGGER.info(LOOT_MODIFIERS.getEntries().size() + " loot modifiers registered.");
    }

    public static void registerBrewingRecipes() {
        PotionBrewing.addMix(Potions.AWKWARD, Items.NETHERITE_INGOT, POTION_RESISTANCE.get());
        PotionBrewing.addMix(POTION_RESISTANCE.get(), Items.REDSTONE, POTION_LONG_RESISTANCE.get());

        PotionBrewing.addMix(Potions.AWKWARD, Items.GOLDEN_APPLE, POTION_ABSORPTION.get());
        PotionBrewing.addMix(POTION_ABSORPTION.get(), Items.GOLDEN_APPLE, POTION_ABSORPTION_II.get());
        PotionBrewing.addMix(POTION_ABSORPTION_II.get(), Items.GOLDEN_APPLE, POTION_ABSORPTION_III.get());
        PotionBrewing.addMix(POTION_ABSORPTION_III.get(), Items.GOLDEN_APPLE, POTION_ABSORPTION_IV.get());
        PotionBrewing.addMix(POTION_ABSORPTION_IV.get(), Items.GOLDEN_APPLE, POTION_ABSORPTION_V.get());
    }

    private static Item.Properties buildFoodProperties(int hunger, float saturation) {
        return new Item.Properties().group(Cucurbita.creativeTab).food(new Food.Builder().hunger(hunger).saturation(saturation).build());
    }

    private static Item.Properties buildFoodProperties(int hunger, float saturation, EffectInstance effect, int chance) {
        return new Item.Properties().group(Cucurbita.creativeTab).food(new Food.Builder().hunger(hunger).saturation(saturation).effect(() -> effect, chance).build());
    }
}
