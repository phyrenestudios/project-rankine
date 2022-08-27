package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.init.RankineBlocks;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.BonusLevelTableCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineBlockLootTables extends RankineLootTableProvider {

    public RankineBlockLootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected void addTables() {
        for (Block blk : Stream.of(
                RankineLists.STONES,
                RankineLists.POLISHED_STONES,
                RankineLists.STONE_BRICKS,
                RankineLists.POLISHED_STONES,
                RankineLists.STONE_STAIRS,
                RankineLists.POLISHED_STONE_STAIRS,
                RankineLists.STONE_BRICKS_STAIRS,
                RankineLists.STONE_WALLS,
                RankineLists.POLISHED_STONE_WALLS,
                RankineLists.STONE_BRICKS_WALLS,
                RankineLists.STONE_PRESSURE_PLATES,
                RankineLists.STONE_BRICKS_PRESSURE_PLATES,
                RankineLists.STONE_BUTTONS,
                RankineLists.VANILLA_BRICKS,
                RankineLists.VANILLA_BRICKS_PRESSURE_PLATES,
                RankineLists.VANILLA_BRICKS_WALLS,
                RankineLists.VANILLA_BRICKS_STAIRS,
                RankineLists.SMOOTH_SANDSTONE_WALLS,
                RankineLists.CUT_SANDSTONES,
                RankineLists.CHISELED_SANDSTONES,
                RankineLists.POINTED_DRIPSTONES,
                RankineLists.DRIPSTONES,
                RankineLists.SANDSTONES,
                RankineLists.SANDSTONE_STAIRS,
                RankineLists.SANDSTONE_WALLS,
                RankineLists.STONE_COLUMNS,
                RankineLists.STONE_COBBLES,
                RankineLists.BRICKS,
                RankineLists.BRICKS_STAIRS,
                RankineLists.BRICKS_WALL,
                RankineLists.SHEETMETALS,
                RankineLists.GEODES,
                RankineLists.LEDS,
                RankineLists.GLAZED_PORCELAIN_BLOCKS,
                RankineLists.COARSE_SOIL_BLOCKS,
                RankineLists.MUD_BLOCKS,
                RankineLists.MINERAL_WOOL,
                RankineLists.FIBER_BLOCK,
                RankineLists.FIBER_MAT,
                RankineLists.PLANKS,
                RankineLists.LOGS,
                RankineLists.STRIPPED_LOGS,
                RankineLists.WOODS,
                RankineLists.STRIPPED_WOODS,
                RankineLists.WOODEN_STAIRS,
                RankineLists.WOODEN_TRAPDOORS,
                RankineLists.WOODEN_FENCES,
                RankineLists.WOODEN_FENCE_GATES,
                RankineLists.WOODEN_BUTTONS,
                RankineLists.WOODEN_PRESSURE_PLATES,
                RankineLists.METAL_TRAPDOORS,
                RankineLists.METAL_LADDERS,
                RankineLists.ALLOY_BLOCKS,
                RankineLists.ALLOY_PEDESTALS,
                RankineLists.MINERAL_BLOCKS,
                RankineLists.STANDARD_BLOCKS,
                RankineLists.ROTATION_BLOCKS,
                RankineLists.ELEMENT_BLOCKS,
                RankineLists.QUARTER_SLABS,
                RankineLists.ELECTROMAGNETS,
                RankineLists.ALLOY_BARS,
                RankineLists.ALLOY_SHEETMETALS,
                RankineLists.LANTERNS,
                RankineLists.CRUSHING_HEADS,
                RankineLists.WALL_MUSHROOMS,
                RankineLists.BALES,
                RankineLists.SAPLINGS
                ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, createBlockLootTable(blk));
        }

        for (Block blk : Arrays.asList(
                RankineBlocks.SLATE_STEPPING_STONES.get(),
                RankineBlocks.ORNAMENT.get(),
                RankineBlocks.SOD_BLOCK.get(),
                RankineBlocks.ALLUVIUM.get(),
                RankineBlocks.COB.get(),
                RankineBlocks.REFINED_COB.get(),
                RankineBlocks.STUMP.get(),
                RankineBlocks.REACTION_CHAMBER_CELL.get(),
                RankineBlocks.REACTION_CHAMBER_CORE.get(),
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
                RankineBlocks.AIR_DISTILLATION_PACKING.get()
        )) {
            lootTables.put(blk, createBlockLootTable(blk));
        }
        for (Block blk : RankineLists.INFESTED_STONES) {
            lootTables.put(blk, droppingWithSilkTouch(blk, ForgeRegistries.BLOCKS.getValue(ResourceLocation.tryParse(blk.getRegistryName().getPath().replace("infested_","")))));
        }
        for (Block blk : RankineLists.ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.ASPHALT.get()));
        }
        for (Block blk : RankineLists.RED_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.RED_ASPHALT.get()));
        }
        for (Block blk : RankineLists.GRAY_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.GRAY_ASPHALT.get()));
        }
        for (Block blk : RankineLists.DARK_GRAY_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.DARK_GRAY_ASPHALT.get()));
        }
        for (Block blk : RankineLists.BLUE_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.BLUE_ASPHALT.get()));
        }
        for (Block blk : RankineLists.GREEN_ASPHALT_BLOCKS) {
            lootTables.put(blk, createBlockLootTable(RankineBlocks.GREEN_ASPHALT.get()));
        }

        for (Block SOIL : Stream.of(RankineLists.SOIL_BLOCKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            Block GRASS = RankineLists.GRASS_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block PODZOL = RankineLists.PODZOL_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block MYCELIUM = RankineLists.MYCELIUM_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            Block PATH = RankineLists.PATH_BLOCKS.get(RankineLists.SOIL_BLOCKS.indexOf(SOIL));
            lootTables.put(SOIL, createBlockLootTable(SOIL));
            lootTables.put(PATH, createBlockLootTable(SOIL));
            lootTables.put(GRASS, droppingWithSilkTouch(GRASS,SOIL));
            lootTables.put(PODZOL, droppingWithSilkTouch(PODZOL,SOIL));
            lootTables.put(MYCELIUM, droppingWithSilkTouch(MYCELIUM,SOIL));
        }
        lootTables.put(RankineBlocks.ENDER_SHIRO.get(), droppingWithSilkTouch(RankineBlocks.ENDER_SHIRO.get(),Blocks.END_STONE));
        lootTables.put(RankineBlocks.BONE_CHAR_BLOCK.get(), createBlockLootTable(RankineBlocks.BONE_CHAR_BLOCK.get()));
        lootTables.put(RankineBlocks.FIRE_CLAY.get(), droppingWithSilkTouchOrRandomly(RankineBlocks.FIRE_CLAY.get(), RankineItems.FIRE_CLAY_BALL.get(), ConstantValue.exactly(4)));
        lootTables.put(RankineBlocks.KAOLIN.get(), droppingWithSilkTouchOrRandomly(RankineBlocks.KAOLIN.get(), RankineItems.KAOLINITE.get(), ConstantValue.exactly(4)));
        lootTables.put(RankineBlocks.DARK_GRAVEL.get(), droppingWithSilkTouch(RankineBlocks.DARK_GRAVEL.get(), withSurvivesExplosion(RankineBlocks.DARK_GRAVEL.get(), LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.2F, 0.5F, 1.0F, 1.0F)).otherwise(LootItem.lootTableItem(RankineBlocks.DARK_GRAVEL.get())))));
        lootTables.put(RankineBlocks.LIGHT_GRAVEL.get(), droppingWithSilkTouch(RankineBlocks.LIGHT_GRAVEL.get(), withSurvivesExplosion(RankineBlocks.LIGHT_GRAVEL.get(), LootItem.lootTableItem(Items.FLINT).when(BonusLevelTableCondition.bonusLevelFlatChance(Enchantments.BLOCK_FORTUNE, 0.05F, 0.1F, 0.2F, 0.5F)).otherwise(LootItem.lootTableItem(RankineBlocks.LIGHT_GRAVEL.get())))));


        for (Block blk : Stream.of(
                RankineLists.STONE_SLABS,
                RankineLists.POLISHED_STONE_SLABS,
                RankineLists.STONE_BRICKS_SLABS,
                RankineLists.VANILLA_BRICKS_SLABS,
                RankineLists.BRICKS_SLAB,
                RankineLists.SANDSTONE_SLABS,
                RankineLists.SMOOTH_SANDSTONE_SLABS,
                RankineLists.CUT_SANDSTONE_SLABS,
                RankineLists.MISC_SLABS,
                RankineLists.WOODEN_SLABS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(blk, slabBlockLootTable(blk));
        }

        lootTables.put(RankineBlocks.NATIVE_ARSENIC_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_ARSENIC_ORE.get(), RankineItems.ARSENIC_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_BISMUTH_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_BISMUTH_ORE.get(), RankineItems.BISMUTH_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SILVER_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_TELLURIUM_ORE.get(), RankineItems.TELLURIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SELENIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SELENIUM_ORE.get(), RankineItems.SELENIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_GALLIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_GALLIUM_ORE.get(), RankineItems.GALLIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_INDIUM_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_INDIUM_ORE.get(), RankineItems.INDIUM_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_TIN_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_TIN_ORE.get(), RankineItems.TIN_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_LEAD_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_LEAD_ORE.get(), RankineItems.LEAD_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_GOLD_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_GOLD_ORE.get(), Items.GOLD_NUGGET));
        lootTables.put(RankineBlocks.NATIVE_SILVER_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SILVER_ORE.get(), RankineItems.SILVER_NUGGET.get()));
        lootTables.put(RankineBlocks.NATIVE_SULFUR_ORE.get(), nativeOreBlockLootTable(RankineBlocks.NATIVE_SULFUR_ORE.get(), RankineItems.SULFUR_NUGGET.get()));
        lootTables.put(RankineBlocks.STIBNITE_ORE.get(), nativeOreBlockLootTable(RankineBlocks.STIBNITE_ORE.get(), RankineItems.ANTIMONY_NUGGET.get()));
        lootTables.put(RankineBlocks.PORPHYRY_COPPER.get(), nativeOreBlockLootTable(RankineBlocks.PORPHYRY_COPPER.get(), RankineItems.COPPER_NUGGET.get()));

        lootTables.put(RankineBlocks.LIGNITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LIGNITE_ORE.get(), RankineItems.LIGNITE.get()));
        lootTables.put(RankineBlocks.SUBBITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.SUBBITUMINOUS_ORE.get(), RankineItems.SUBBITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.BITUMINOUS_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.BITUMINOUS_ORE.get(), RankineItems.BITUMINOUS_COAL.get()));
        lootTables.put(RankineBlocks.ANTHRACITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.ANTHRACITE_ORE.get(), RankineItems.ANTHRACITE_COAL.get()));
        lootTables.put(RankineBlocks.LONSDALEITE_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.LONSDALEITE_ORE.get(), RankineItems.LONSDALEITE_DIAMOND.get()));
        lootTables.put(RankineBlocks.PLUMBAGO_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.PLUMBAGO_ORE.get(), RankineItems.GRAPHITE.get()));
        lootTables.put(RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.KIMBERLITIC_DIAMOND_ORE.get(), Items.DIAMOND));
        lootTables.put(RankineBlocks.BERYL_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.BERYL_ORE.get(), Items.EMERALD));
        lootTables.put(RankineBlocks.GWIHABAITE_CRYSTAL.get(), fortunableOreOreBlockLootTable(RankineBlocks.GWIHABAITE_CRYSTAL.get(), RankineItems.GWIHABAITE.get()));
        lootTables.put(RankineBlocks.LAZURITE_ORE.get(), droppingWithSilkTouch(RankineBlocks.LAZURITE_ORE.get(), withExplosionDecay(RankineBlocks.LAZURITE_ORE.get(), LootItem.lootTableItem(Items.LAPIS_LAZULI).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        lootTables.put(RankineBlocks.EMERALD_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.EMERALD_ORE.get(), Items.EMERALD));
        lootTables.put(RankineBlocks.DIAMOND_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.DIAMOND_ORE.get(), Items.DIAMOND));
        lootTables.put(RankineBlocks.NETHER_QUARTZ_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.NETHER_QUARTZ_ORE.get(), Items.QUARTZ));
        lootTables.put(RankineBlocks.COAL_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.COAL_ORE.get(), Items.COAL));
        lootTables.put(RankineBlocks.IRON_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.IRON_ORE.get(), Items.RAW_IRON));
        lootTables.put(RankineBlocks.COPPER_ORE.get(), droppingWithSilkTouch(RankineBlocks.COPPER_ORE.get(), withExplosionDecay(RankineBlocks.COPPER_ORE.get(), LootItem.lootTableItem(Items.RAW_COPPER).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 5.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        lootTables.put(RankineBlocks.GOLD_ORE.get(), fortunableOreOreBlockLootTable(RankineBlocks.GOLD_ORE.get(), Items.RAW_GOLD));
        lootTables.put(RankineBlocks.LAPIS_ORE.get(), droppingWithSilkTouch(RankineBlocks.LAPIS_ORE.get(), withExplosionDecay(RankineBlocks.LAPIS_ORE.get(), LootItem.lootTableItem(Items.LAPIS_LAZULI).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 9.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));
        lootTables.put(RankineBlocks.REDSTONE_ORE.get(), droppingWithSilkTouch(RankineBlocks.REDSTONE_ORE.get(), withExplosionDecay(RankineBlocks.REDSTONE_ORE.get(), LootItem.lootTableItem(Items.REDSTONE).apply(SetItemCountFunction.setCount(UniformGenerator.between(4.0F, 5.0F))).apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE)))));
        lootTables.put(RankineBlocks.NETHER_GOLD_ORE.get(), droppingWithSilkTouch(RankineBlocks.NETHER_GOLD_ORE.get(), withExplosionDecay(RankineBlocks.NETHER_GOLD_ORE.get(), LootItem.lootTableItem(Items.GOLD_NUGGET).apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 6.0F))).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))));

        lootTables.put(RankineBlocks.TAENITE_ORE.get(), createBlockLootTable(RankineBlocks.TAENITE_ORE.get()));
        lootTables.put(RankineBlocks.TETRATAENITE_ORE.get(), createBlockLootTable(RankineBlocks.TETRATAENITE_ORE.get()));
        lootTables.put(RankineBlocks.ANTITAENITE_ORE.get(), createBlockLootTable(RankineBlocks.ANTITAENITE_ORE.get()));
        lootTables.put(RankineBlocks.KAMACITE_ORE.get(), createBlockLootTable(RankineBlocks.KAMACITE_ORE.get()));

        lootTables.put(RankineBlocks.MIXING_BARREL.get(), droppingWithName(RankineBlocks.MIXING_BARREL.get()));
        lootTables.put(RankineBlocks.FUSION_FURNACE.get(), droppingWithName(RankineBlocks.FUSION_FURNACE.get()));
        lootTables.put(RankineBlocks.ALLOY_FURNACE.get(), droppingWithName(RankineBlocks.ALLOY_FURNACE.get()));
        lootTables.put(RankineBlocks.PISTON_CRUSHER.get(), droppingWithName(RankineBlocks.PISTON_CRUSHER.get()));
        lootTables.put(RankineBlocks.GYRATORY_CRUSHER.get(), droppingWithName(RankineBlocks.GYRATORY_CRUSHER.get()));
        lootTables.put(RankineBlocks.CRUCIBLE_BLOCK.get(), droppingWithName(RankineBlocks.CRUCIBLE_BLOCK.get()));
        lootTables.put(RankineBlocks.EVAPORATION_TOWER.get(), droppingWithName(RankineBlocks.EVAPORATION_TOWER.get()));
        lootTables.put(RankineBlocks.TEMPLATE_TABLE.get(), droppingWithName(RankineBlocks.TEMPLATE_TABLE.get()));
        lootTables.put(RankineBlocks.INDUCTION_FURNACE.get(), droppingWithName(RankineBlocks.INDUCTION_FURNACE.get()));
        lootTables.put(RankineBlocks.GAS_BOTTLER.get(), droppingWithName(RankineBlocks.GAS_BOTTLER.get()));

        for (Block ORE : RankineLists.CRUSHING_ORES) {
            lootTables.put(ORE, fortunableOreOreBlockLootTable(ORE, ForgeRegistries.ITEMS.getValue(new ResourceLocation(ORE.getRegistryName().toString().replace("_ore","")))));
        }
        for (Block BLK : RankineLists.FLOWER_POTS) {
            lootTables.put(BLK, droppingAndFlowerPot(((FlowerPotBlock)BLK).getContent()));
        }
        for (Block BLK : RankineLists.EIGHT_LAYER_BLOCKS) {
            lootTables.put(BLK, eightLayerBlock(BLK));
        }
        for (Block BLK : RankineLists.WOODEN_BOOKSHELVES) {
            lootTables.put(BLK, droppingWithSilkTouchOrRandomly(BLK, Items.BOOK, ConstantValue.exactly(3)));
        }
        for (Block BLK : RankineLists.LIGHTNING_GLASSES) {
            lootTables.put(BLK, onlyWithSilkTouch(BLK));
        }
        for (Block BLK : RankineLists.HOLLOW_LOGS) {
            lootTables.put(BLK, droppingWithSilkTouchOrRandomly(BLK, Items.STICK, UniformGenerator.between(2.0F, 6.0F)));
        }
        for (Block BLK : RankineLists.MUSHROOM_BLOCKS) {
            lootTables.put(BLK, droppingItemRarely(BLK, RankineLists.WALL_MUSHROOMS.get(RankineLists.MUSHROOM_BLOCKS.indexOf(BLK)).asItem()));
        }
        for (Block BLK : RankineLists.LEAF_LITTERS) {
            lootTables.put(BLK, withShears(BLK));
        }
        for (Block LEAF : RankineLists.LEAVES) {
            Block SAPLING = RankineLists.SAPLINGS.get(RankineLists.LEAVES.indexOf(LEAF));
            if (LEAF.equals(RankineBlocks.COCONUT_PALM_LEAVES.get()) || LEAF.equals(RankineBlocks.BALSAM_FIR_LEAVES.get())) {
                lootTables.put(LEAF, droppingWithChancesAndSticks(LEAF, SAPLING, DOUBLE_SAPLING_DROP_RATES));
            } else {
                lootTables.put(LEAF, droppingWithChancesAndSticks(LEAF, SAPLING, DEFAULT_SAPLING_DROP_RATES));
            }
        }
        for (Block BLK : Stream.of(
                RankineLists.TALL_FLOWERS,
                RankineLists.METAL_DOORS,
                RankineLists.WOODEN_DOORS
        ).flatMap(Collection::stream).collect(Collectors.toList())) {
            lootTables.put(BLK, droppingWhen(BLK, DoublePlantBlock.HALF, DoubleBlockHalf.LOWER));
        }

        for (Block BLK : RankineLists.ALLOY_POLES) {
            lootTables.put(BLK, metalPole(BLK));
        }

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

        lootTables.put(RankineBlocks.SHORT_GRASS.get(), droppingSeeds(RankineBlocks.SHORT_GRASS.get()));
        lootTables.put(RankineBlocks.STINGING_NETTLE.get(), withShears(RankineBlocks.STINGING_NETTLE.get()));
        lootTables.put(RankineBlocks.YELLOW_CLOVER.get(), withShears(RankineBlocks.YELLOW_CLOVER.get()));
        lootTables.put(RankineBlocks.RED_CLOVER.get(), withShears(RankineBlocks.RED_CLOVER.get()));
        lootTables.put(RankineBlocks.WHITE_CLOVER.get(), withShears(RankineBlocks.WHITE_CLOVER.get()));
        lootTables.put(RankineBlocks.CRIMSON_CLOVER.get(), withShears(RankineBlocks.CRIMSON_CLOVER.get()));


    }



}
