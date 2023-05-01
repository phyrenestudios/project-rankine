package com.cannolicatfish.rankine.data.loot;

import com.cannolicatfish.rankine.blocks.HollowLogBlock;
import com.cannolicatfish.rankine.blocks.LeafLitterBlock;
import com.cannolicatfish.rankine.blocks.RankineLogBlock;
import com.cannolicatfish.rankine.blocks.block_groups.*;
import com.cannolicatfish.rankine.blocks.buildingmodes.RankineBookshelvesBlock;
import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.MatchTool;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class RankineBlockLootSubProvider extends BlockLootSubProvider {
    protected static final float[] DOUBLE_SAPLING_DROP_RATES = new float[]{0.05F, 0.0625F, 0.083333336F, 0.1F};
    protected static final float[] RARE_SAPLING_DROP_RATES = new float[]{0.025F, 0.027777778F, 0.03125F, 0.041666668F, 0.1F};
    protected static final LootItemCondition.Builder HAS_SILK_TOUCH = MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(Enchantments.SILK_TOUCH, MinMaxBounds.Ints.atLeast(1))));
    protected static final LootItemCondition.Builder HAS_NO_SILK_TOUCH = HAS_SILK_TOUCH.invert();
    protected static final LootItemCondition.Builder HAS_SHEARS = MatchTool.toolMatches(ItemPredicate.Builder.item().of(Items.SHEARS));
    private static final LootItemCondition.Builder HAS_SHEARS_OR_SILK_TOUCH = HAS_SHEARS.or(HAS_SILK_TOUCH);
    private static final LootItemCondition.Builder HAS_NO_SHEARS_OR_SILK_TOUCH = HAS_SHEARS_OR_SILK_TOUCH.invert();

    public RankineBlockLootSubProvider() {
        super(Collections.emptySet(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return RankineBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get).collect(Collectors.toList());
    }

    @Override
    protected void generate() {
        for (RankineStone Stone : RankineLists.RANKINE_STONES) {
            for (Block blk : Stone.getStoneBlocks()) {
                if (blk instanceof SlabBlock) {
                    addSlab(blk);
                } else if (blk instanceof InfestedBlock) {
                    otherWhenSilkTouch(blk, Stone.getStone());
                } else {
                    dropSelf(blk);
                }
            }
        }

        for (RankineWood Wood : RankineLists.RANKINE_WOODS) {
            for (Block blk : Wood.getWoodBlocks()) {
                if (!Wood.hasLogs() && blk instanceof RankineLogBlock) continue;
                if (blk instanceof SlabBlock) {
                    addSlab(blk);
                } else if (blk instanceof RankineBookshelvesBlock) {
                    addSilkTouchSingleItem(blk, Items.BOOK, ConstantValue.exactly(3.0F));
                } else if (blk instanceof DoorBlock) {
                    add(blk, createDoorTable(blk));
                } else if (blk instanceof LeavesBlock) {
                    if (Wood == RankineBlocks.BALSAM_FIR) {
                        add(blk, createLeavesDrops(blk, Wood.getSapling(), DOUBLE_SAPLING_DROP_RATES));
                    } else if (Wood == RankineBlocks.COCONUT_PALM) {
                        add(blk, createExtraDropLeavesDrops(blk, Wood.getSapling(), RankineItems.COCONUT.get(), DOUBLE_SAPLING_DROP_RATES));
                    } else if (Wood == RankineBlocks.BLACK_WALNUT) {
                        add(blk, createExtraDropLeavesDrops(blk, Wood.getSapling(),  RankineItems.BLACK_WALNUT.get(), NORMAL_LEAVES_SAPLING_CHANCES));
                    } else {
                        add(blk, createLeavesDrops(blk, Wood.getSapling(), NORMAL_LEAVES_SAPLING_CHANCES));
                    }
                } else if (blk instanceof LeafLitterBlock) {
                    addWithShearsOnly(blk);
                } else if (blk instanceof FlowerPotBlock) {
                    dropPottedContents(blk);
                } else if (blk instanceof HollowLogBlock) {
                    addSilkTouchSingleItem(blk, Items.STICK, UniformGenerator.between(2.0F, 6.0F));
                } else if (blk != null) {
                    dropSelf(blk);
                }
            }
        }

        for (RankineSandstone Sandstone : RankineLists.RANKINE_SANDSTONES) {
            for (Block blk : Sandstone.getSandstoneBlocks()) {
                if (blk instanceof SlabBlock) {
                    addSlab(blk);
                } else {
                    dropSelf(blk);
                }
            }
        }
        for (RankineCement Cement : RankineLists.RANKINE_CEMENTS) {
            for (Block blk : Cement.getCementBlocks()) {
                dropSelf(blk);
            }
        }
        for (RankineDripstone Dripstone : RankineLists.RANKINE_DRIPSTONES) {
            for (Block blk : Dripstone.getBlocks()) {
                dropSelf(blk);
            }
        }
        for (RankineBricks Bricks : RankineLists.RANKINE_BRICKS) {
            for (Block blk : Bricks.getBricksBlocks()) {
                if (blk instanceof SlabBlock) {
                    addSlab(blk);
                } else {
                    dropSelf(blk);
                }
            }
        }
        for (FiberBlocks fiber : FiberBlocks.values()) {
            dropSelf(fiber.getBlock());
            dropSelf(fiber.getMat());
            if (fiber.hasExtraBlocks()) {
                addSlab(fiber.getSlab());
                dropSelf(fiber.getStairs());
                dropSelf(fiber.getWall());
            }
        }

        for (Block blk : Stream.of(
                RankineLists.VANILLA_BRICKS,
                RankineLists.VANILLA_BRICKS_PRESSURE_PLATES,
                RankineLists.VANILLA_BRICKS_WALLS,
                RankineLists.VANILLA_BRICKS_STAIRS,
                RankineLists.MISC_STAIRS,
                RankineLists.MISC_WALLS,
                RankineLists.SHEETMETALS,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.GLAZED_PORCELAIN_BLOCKS,
                RankineLists.COARSE_SOIL_BLOCKS,
                RankineLists.MUD_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.ALLOY_LADDERS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.MINERAL_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.ELECTROMAGNETS,
                RankineLists.ALLOY_BARS,
                RankineLists.ALLOY_SHEETMETALS,
                RankineLists.ALLOY_POLES,
                RankineLists.ASPHALT_BLOCKS,
                RankineLists.RED_ASPHALT_BLOCKS,
                RankineLists.GRAY_ASPHALT_BLOCKS,
                RankineLists.DARK_GRAY_ASPHALT_BLOCKS,
                RankineLists.BLUE_ASPHALT_BLOCKS,
                RankineLists.GREEN_ASPHALT_BLOCKS,
                RankineLists.CRUSHING_HEADS,
                RankineLists.MINING_HEADS,
                RankineLists.WALL_MUSHROOMS,
                RankineLists.BALES
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            dropSelf(blk);
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.SPERRYLITE_ORE.get(),
                RankineBlocks.MONAZITE_ORE.get(),
                RankineBlocks.INTERSPINIFEX_ORE.get(),
                RankineBlocks.COLTAN_ORE.get(),
                RankineBlocks.KAMACITE_ORE.get(),
                RankineBlocks.ANTITAENITE_ORE.get(),
                RankineBlocks.TAENITE_ORE.get(),
                RankineBlocks.TETRATAENITE_ORE.get(),
                RankineBlocks.FLOOD_GATE.get(),
                RankineBlocks.BONE_CHAR_BLOCK.get(),
                RankineBlocks.UNAMED_EXPLOSIVE.get(),
                RankineBlocks.SLATE_STEPPING_STONES.get(),
                RankineBlocks.ORNAMENT.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.ALLUVIUM.get(),
                RankineBlocks.COB.get(),
                RankineBlocks.STICK_BLOCK.get(),
                RankineBlocks.KAOLIN.get(),
                RankineBlocks.REFINED_COB.get(),
                RankineBlocks.STUMP.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
                RankineBlocks.DISTILLATION_TOWER.get(),
                RankineBlocks.CHARCOAL_PIT.get(),
                RankineBlocks.TREE_TAP.get(),
                RankineBlocks.BOTANIST_STATION.get(),
                RankineBlocks.MATERIAL_TESTING_TABLE.get(),
                RankineBlocks.DIAMOND_ANVIL_CELL.get(),
                RankineBlocks.PARTICLE_ACCELERATOR.get(),
                RankineBlocks.BEEHIVE_OVEN_PIT.get(),
                RankineBlocks.SEDIMENT_FAN.get(),
                RankineBlocks.GAS_VENT.get(),
                RankineBlocks.GEODE.get(),
                RankineBlocks.TRAMPOLINE.get(),
                RankineBlocks.METAL_PIPE.get(),
                RankineBlocks.GROUND_TAP.get(),
                RankineBlocks.HEATING_ELEMENT_1.get(),
                RankineBlocks.SODIUM_VAPOR_LAMP.get(),
                RankineBlocks.TAP_LINE.get(),
                RankineBlocks.AIR_DISTILLATION_PACKING.get()
        )) {
            dropSelf(blk);
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.ROPE.get(),
                RankineBlocks.CORN_STALK.get(),
                RankineBlocks.TILLED_SOIL.get(),
                RankineBlocks.AGED_CHEESE.get(),
                RankineBlocks.UNAGED_CHEESE.get(),
                RankineBlocks.SULFUR_DIOXIDE_FUMAROLE.get(),
                RankineBlocks.HYDROGEN_SULFIDE_FUMAROLE.get(),
                RankineBlocks.HYDROGEN_CHLORIDE_FUMAROLE.get(),
                RankineBlocks.CARBON_DIOXIDE_FUMAROLE.get()
        )) {
            add(blk, noDrop());
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.LOCUST_SPINE.get(),
                RankineBlocks.WILLOW_BRANCHLET.get(),
                RankineBlocks.STINGING_NETTLE.get(),
                RankineBlocks.YELLOW_CLOVER.get(),
                RankineBlocks.RED_CLOVER.get(),
                RankineBlocks.WHITE_CLOVER.get(),
                RankineBlocks.CRIMSON_CLOVER.get()
        )) {
            addWithShearsOnly(blk);
        }
        addWithShearsOnly(RankineBlocks.WILLOW_BRANCHLET_PLANT.get(), RankineItems.WILLOW_BRANCHLET.get());

        for (Block blk : Stream.of(
                RankineLists.LEAF_LITTERS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            addWithShearsOnly(blk);
        }

        for (Block blk : Stream.of(
                RankineLists.VANILLA_BRICKS_SLABS,
                RankineLists.MISC_SLABS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            addSlab(blk);
        }
        for (Block SOIL : Stream.of(RankineLists.SOIL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block GRASS = RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block PODZOL = RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block MYCELIUM = RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block PATH = RankineLists.PATH_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            dropSelf(SOIL);
            dropOther(PATH, SOIL);
            addSilkTouchSingleItem(GRASS, SOIL);
            addSilkTouchSingleItem(PODZOL, SOIL);
            addSilkTouchSingleItem(MYCELIUM, SOIL);
        }
        dropOther(RankineBlocks.MYCELIUM_PATH.get(), Blocks.DIRT);
        dropOther(RankineBlocks.ENDER_SHIRO.get(), Blocks.END_STONE);


        dropOther(RankineBlocks.SAP_CAULDRON.get(), Blocks.CAULDRON);
        dropOther(RankineBlocks.MAPLE_SAP_CAULDRON.get(), Blocks.CAULDRON);
        dropOther(RankineBlocks.MAPLE_SYRUP_CAULDRON.get(), Blocks.CAULDRON);
        dropOther(RankineBlocks.LATEX_CAULDRON.get(), Blocks.CAULDRON);
        dropOther(RankineBlocks.JUGLONE_CAULDRON.get(), Blocks.CAULDRON);
        dropOther(RankineBlocks.RESIN_CAULDRON.get(), Blocks.CAULDRON);

        addNamedBlockEntity(RankineBlocks.BATTERY_CHARGER.get());
        addNamedBlockEntity(RankineBlocks.MIXING_BARREL.get());
        addNamedBlockEntity(RankineBlocks.ALLOY_FURNACE.get());
        addNamedBlockEntity(RankineBlocks.FUSION_FURNACE.get());
        addNamedBlockEntity(RankineBlocks.CRUCIBLE_BLOCK.get());
        addNamedBlockEntity(RankineBlocks.EVAPORATION_TOWER.get());
        addNamedBlockEntity(RankineBlocks.TEMPLATE_TABLE.get());
        addNamedBlockEntity(RankineBlocks.INDUCTION_FURNACE.get());
        addNamedBlockEntity(RankineBlocks.GAS_BOTTLER.get());


        addSilkTouchSingleItem(RankineBlocks.FIRE_CLAY.get(), RankineItems.FIRE_CLAY_BALL.get(), ConstantValue.exactly(4));
        addSilkTouchSingleItem(RankineBlocks.PORCELAIN_CLAY.get(), RankineItems.PORCELAIN_CLAY_BALL.get(), ConstantValue.exactly(4));
        addSilkTouchSingleItem(RankineBlocks.KAOLINITE_BLOCK.get(), RankineItems.KAOLINITE.get(), ConstantValue.exactly(4));
        addSilkTouchSingleItem(RankineBlocks.HALLOYSITE_BLOCK.get(), RankineItems.HALLOYSITE.get(), ConstantValue.exactly(4));
        addFlintChance(RankineBlocks.DARK_GRAVEL.get(), 1.0F);
        addFlintChance(RankineBlocks.LIGHT_GRAVEL.get(), 1.0F);

        for (Block ORE : RankineLists.CRUSHING_ORES) {
            addOre(ORE, ForgeRegistries.ITEMS.getValue(new ResourceLocation(ForgeRegistries.BLOCKS.getKey(ORE).toString().replace("_ore",""))));
        }

        addNativeOre(RankineBlocks.NATIVE_ARSENIC_ORE.get(), RankineItems.ARSENIC_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_BISMUTH_ORE.get(), RankineItems.BISMUTH_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), RankineItems.TELLURIUM_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_SELENIUM_ORE.get(), RankineItems.SELENIUM_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_GALLIUM_ORE.get(), RankineItems.GALLIUM_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_INDIUM_ORE.get(), RankineItems.INDIUM_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_TIN_ORE.get(), RankineItems.TIN_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_LEAD_ORE.get(), RankineItems.LEAD_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_GOLD_ORE.get(), Items.GOLD_NUGGET);
        addNativeOre(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get());
        addNativeOre(RankineBlocks.NATIVE_SULFUR_ORE.get(), RankineItems.SULFUR_NUGGET.get());
        addNativeOre(RankineBlocks.STIBNITE_ORE.get(), RankineItems.ANTIMONY_NUGGET.get());
        addNativeOre(RankineBlocks.PORPHYRY_COPPER.get(), RankineItems.COPPER_NUGGET.get());

        addOre(RankineBlocks.LIGNITE_ORE.get(), RankineItems.LIGNITE.get());
        addOre(RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineItems.SUBBITUMINOUS_COAL.get());
        addOre(RankineBlocks.BITUMINOUS_ORE.get(), RankineItems.BITUMINOUS_COAL.get());
        addOre(RankineBlocks.ANTHRACITE_ORE.get(), RankineItems.ANTHRACITE_COAL.get());
        addOre(RankineBlocks.LONSDALEITE_ORE.get(), RankineItems.LONSDALEITE_DIAMOND.get());
        addOre(RankineBlocks.PLUMBAGO_ORE.get(), RankineItems.GRAPHITE.get());
        addOre(RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(), Items.DIAMOND);
        addOre(RankineBlocks.BERYL_ORE.get(), Items.EMERALD);
        addOre(RankineBlocks.GWIHABAITE_CRYSTAL.get(), RankineItems.GWIHABAITE.get());
        addOreMultiDrop(RankineBlocks.LAZURITE_ORE.get(), Items.LAPIS_LAZULI, 4.0F, 9.0F);


        //Vanilla ores
        addOreMultiDrop(RankineBlocks.COPPER_ORE.get(), Items.RAW_COPPER, 2.0F, 5.0F);
        addOreMultiDrop(RankineBlocks.NETHER_GOLD_ORE.get(), Items.GOLD_NUGGET, 2.0F, 6.0F);
        addOreMultiDrop(RankineBlocks.LAPIS_ORE.get(), Items.LAPIS_LAZULI, 4.0F, 9.0F);
        addOreMultiDrop(RankineBlocks.REDSTONE_ORE.get(), Items.REDSTONE, 4.0F, 5.0F);
        addOre(RankineBlocks.GOLD_ORE.get(), Items.RAW_GOLD);
        addOre(RankineBlocks.IRON_ORE.get(), Items.RAW_IRON);
        addOre(RankineBlocks.COAL_ORE.get(), Items.COAL);
        addOre(RankineBlocks.NETHER_QUARTZ_ORE.get(), Items.QUARTZ);
        addOre(RankineBlocks.DIAMOND_ORE.get(), Items.DIAMOND);
        addOre(RankineBlocks.EMERALD_ORE.get(), Items.EMERALD);

        for (Block blk : RankineLists.HOLLOW_LOGS) {
            addSilkTouchSingleItem(blk, Items.STICK, UniformGenerator.between(3.0F, 6.0F));
        }
        for (Block blk : RankineLists.METAL_DOORS) {
            add(blk, createDoorTable(blk));
        }
        for (Block blk : RankineLists.MUSHROOM_BLOCKS) {
            add(blk, createMushroomBlockDrop(blk, RankineLists.WALL_MUSHROOMS.get(RankineLists.MUSHROOM_BLOCKS.indexOf(blk)).asItem()));
        }
        for (Block blk : RankineLists.TALL_FLOWERS) {
            add(blk, createSinglePropConditionTable(blk, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        }
        for (Block blk : RankineLists.LANTERNS) {
            dropMultiple(blk, Blocks.LANTERN, ForgeRegistries.ITEMS.getValue(new ResourceLocation(ForgeRegistries.BLOCKS.getKey(blk).toString().replace("lantern","nugget"))));
        }


        add(RankineBlocks.SHORT_GRASS.get(), createGrassDrops(RankineBlocks.SHORT_GRASS.get()));

        dropOther(RankineBlocks.ASH.get(), RankineItems.ASH.get());
        dropOther(RankineBlocks.BONE_ASH.get(), RankineItems.BONE_ASH.get());
        dropOther(RankineBlocks.POZZOLANA.get(), RankineItems.POZZOLANA.get());
        dropOther(RankineBlocks.SAWDUST.get(), RankineItems.SAWDUST.get());
        dropOther(RankineBlocks.CHARCOAL_BLOCK.get(), Items.CHARCOAL);

        for (Block BLK : RankineLists.LIGHTNING_GLASSES) {
            dropSelf(BLK);
            //add(BLK, createSilkTouchDispatchTable(BLK, LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.GLOWSTONE_DUST)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(BLK).setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(LightningGlassBlock.GLOWING, true)))));
        }

        dropOther(RankineBlocks.RICE_PLANT.get(), RankineItems.RICE.get());
        dropOther(RankineBlocks.OAT_PLANT.get(), RankineItems.OATS.get());
        dropOther(RankineBlocks.MILLET_PLANT.get(), RankineItems.MILLET.get());
        dropOther(RankineBlocks.CAMPHOR_BASIL_PLANT.get(), RankineItems.CAMPHOR_BASIL_LEAF.get());
        dropOther(RankineBlocks.SOYBEAN_PLANT.get(), RankineItems.SOYBEANS.get());
        dropOther(RankineBlocks.COTTON_PLANT.get(), RankineItems.COTTON.get());
        dropOther(RankineBlocks.RYE_PLANT.get(), RankineItems.RYE.get());
        dropOther(RankineBlocks.BARLEY_PLANT.get(), RankineItems.BARLEY.get());
        dropOther(RankineBlocks.CORN_PLANT.get(), RankineItems.CORN_EAR.get());
        dropOther(RankineBlocks.JUTE_PLANT.get(), RankineItems.JUTE.get());
        dropOther(RankineBlocks.SORGHUM_PLANT.get(), RankineItems.SORGHUM.get());
        dropOther(RankineBlocks.ASPARAGUS_PLANT.get(), RankineItems.ASPARAGUS.get());

        dropOther(RankineBlocks.BLACKBERRY_BUSH.get(), RankineItems.BLACKBERRIES.get());
        dropOther(RankineBlocks.RASPBERRY_BUSH.get(), RankineItems.RASPBERRIES.get());
        dropOther(RankineBlocks.BANANA_YUCCA_BUSH.get(), RankineItems.BANANA_YUCCA.get());
        dropOther(RankineBlocks.SNOWBERRY_BUSH.get(), RankineItems.SNOWBERRIES.get());
        dropOther(RankineBlocks.STRAWBERRY_BUSH.get(), RankineItems.STRAWBERRIES.get());
        dropOther(RankineBlocks.POKEBERRY_BUSH.get(), RankineItems.POKEBERRIES.get());
        dropOther(RankineBlocks.BLUEBERRY_BUSH.get(), RankineItems.BLUEBERRIES.get());
        dropOther(RankineBlocks.ELDERBERRY_BUSH.get(), RankineItems.ELDERBERRIES.get());
        dropOther(RankineBlocks.CRANBERRY_BUSH.get(), RankineItems.CRANBERRIES.get());
        dropOther(RankineBlocks.PINEAPPLE_BUSH.get(), RankineItems.PINEAPPLE.get());
        dropOther(RankineBlocks.ALOE_PLANT.get(), RankineItems.ALOE.get());
        /*
        lootTables.put(RankineBlocks.RICE_PLANT.get(), singleCrop(RankineBlocks.RICE_PLANT.get(), RankineItems.RICE.get(), RankineItems.RICE_SEEDS.get()));
        lootTables.put(RankineBlocks.OAT_PLANT.get(), singleCrop(RankineBlocks.OAT_PLANT.get(), RankineItems.OATS.get(), RankineItems.OAT_SEEDS.get()));
        lootTables.put(RankineBlocks.MILLET_PLANT.get(), singleCrop(RankineBlocks.MILLET_PLANT.get(), RankineItems.MILLET.get(), RankineItems.MILLET_SEEDS.get()));
        lootTables.put(RankineBlocks.CAMPHOR_BASIL_PLANT.get(), singleCrop(RankineBlocks.CAMPHOR_BASIL_PLANT.get(), RankineItems.CAMPHOR_BASIL_SEEDS.get(), RankineItems.CAMPHOR_BASIL_LEAF.get()));
        lootTables.put(RankineBlocks.SOYBEAN_PLANT.get(), singleCrop(RankineBlocks.SOYBEAN_PLANT.get(), RankineItems.SOYBEANS.get(), RankineItems.SOYBEANS.get()));
        lootTables.put(RankineBlocks.COTTON_PLANT.get(), doubleCrop(RankineBlocks.COTTON_PLANT.get(), RankineItems.COTTON.get(), RankineItems.COTTON_SEEDS.get(), 2));
        lootTables.put(RankineBlocks.RYE_PLANT.get(), doubleCrop(RankineBlocks.RYE_PLANT.get(), RankineItems.RYE.get(), RankineItems.RYE_SEEDS.get(), 1));
        lootTables.put(RankineBlocks.BARLEY_PLANT.get(), doubleCrop(RankineBlocks.BARLEY_PLANT.get(), RankineItems.BARLEY.get(), RankineItems.BARLEY_SEEDS.get(), 1));
        lootTables.put(RankineBlocks.CORN_PLANT.get(), tripleCrop(RankineBlocks.CORN_PLANT.get(), RankineItems.CORN_EAR.get(), RankineItems.CORN_SEEDS.get(), 2));
        lootTables.put(RankineBlocks.JUTE_PLANT.get(), tripleCrop(RankineBlocks.JUTE_PLANT.get(), RankineItems.JUTE.get(), RankineItems.JUTE_SEEDS.get(), 3));
        lootTables.put(RankineBlocks.SORGHUM_PLANT.get(), tripleCrop(RankineBlocks.SORGHUM_PLANT.get(), RankineItems.SORGHUM.get(), RankineItems.SORGHUM_SEEDS.get(), 2));
        lootTables.put(RankineBlocks.ASPARAGUS_PLANT.get(), doubleCrop(RankineBlocks.ASPARAGUS_PLANT.get(), RankineItems.ASPARAGUS.get(), RankineItems.ASPARAGUS_SEEDS.get(), 1));

        lootTables.put(RankineBlocks.BLACKBERRY_BUSH.get(), bushOneDrop(RankineBlocks.BLACKBERRY_BUSH.get(), RankineItems.BLACKBERRIES.get()));
        lootTables.put(RankineBlocks.RASPBERRY_BUSH.get(), bushOneDrop(RankineBlocks.RASPBERRY_BUSH.get(), RankineItems.RASPBERRIES.get()));
        lootTables.put(RankineBlocks.BANANA_YUCCA_BUSH.get(), bushOneDrop(RankineBlocks.BANANA_YUCCA_BUSH.get(), RankineItems.BANANA_YUCCA.get()));
        lootTables.put(RankineBlocks.SNOWBERRY_BUSH.get(), bushOneDrop(RankineBlocks.SNOWBERRY_BUSH.get(), RankineItems.SNOWBERRIES.get()));
        lootTables.put(RankineBlocks.STRAWBERRY_BUSH.get(), bushOneDrop(RankineBlocks.STRAWBERRY_BUSH.get(), RankineItems.STRAWBERRIES.get()));
        lootTables.put(RankineBlocks.POKEBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.POKEBERRY_BUSH.get(), RankineItems.POKEBERRIES.get()));
        lootTables.put(RankineBlocks.BLUEBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.BLUEBERRY_BUSH.get(), RankineItems.BLUEBERRIES.get()));
        lootTables.put(RankineBlocks.ELDERBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.ELDERBERRY_BUSH.get(), RankineItems.ELDERBERRIES.get()));
        lootTables.put(RankineBlocks.CRANBERRY_BUSH.get(), doubleBushOneDrop(RankineBlocks.CRANBERRY_BUSH.get(), RankineItems.CRANBERRIES.get()));



*/



    }
    private void addOre(Block blk, ItemLike itemLike) {
        add(blk, createSilkTouchDispatchTable(blk, this.applyExplosionDecay(blk, LootItem.lootTableItem(itemLike).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
    private void addNativeOre(Block blk, ItemLike itemLike) {
        addOreMultiDrop(blk, itemLike, 3.0F, 8.0F);
    }
    private void addOreMultiDrop(Block blk, ItemLike itemLike, float min, float max) {
        add(blk, createSilkTouchDispatchTable(blk, this.applyExplosionDecay(blk, LootItem.lootTableItem(itemLike).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
    }
    private void addWithShearsOnly(Block blk) {
        add(blk, createShearsOnlyDrop(blk));
    }
    private void addWithShearsOnly(Block blk, ItemLike itemLike) {
        add(blk, createShearsOnlyDrop(itemLike));
    }
    private void addFlintChance(Block blk, float modifier) {
        add(blk, createSilkTouchDispatchTable(blk, this.applyExplosionCondition(blk, LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.1F*modifier, 0.14285715F*modifier, 0.25F*modifier, 1.0F*modifier)).otherwise(LootItem.lootTableItem(blk)))));
    }

    private void addSlab(Block blk) {
        add(blk, createSlabItemTable(blk));
    }
    private void addSilkTouchSingleItem(Block blk, ItemLike itemLike) {
        add(blk, createSingleItemTableWithSilkTouch(blk, itemLike));
    }
    private void addSilkTouchSingleItem(Block blk, ItemLike itemLike, NumberProvider p_250047_) {
        add(blk, createSingleItemTableWithSilkTouch(blk, itemLike, p_250047_));
    }
    private void addNamedBlockEntity(Block blk) {
        add(blk, createNameableBlockEntityTable(blk));
    }

    private void dropMultiple(Block blk, ItemLike... itemLikes) {
        LootTable.Builder builder = LootTable.lootTable();
        for (ItemLike item : itemLikes) {
            builder.withPool(this.applyExplosionCondition(blk, LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(item))));
        }
        add(blk, builder);
    }

    protected LootTable.Builder createExtraDropLeavesDrops(Block p_249535_, Block p_251505_, ItemLike itemDrop, float... p_250753_) {
        return this.createLeavesDrops(p_249535_, p_251505_, p_250753_).withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1.0F)).when(HAS_NO_SHEARS_OR_SILK_TOUCH).add(this.applyExplosionCondition(p_249535_, LootItem.lootTableItem(itemDrop)).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.005F, 0.0055555557F, 0.00625F, 0.008333334F, 0.025F))));
    }

}
