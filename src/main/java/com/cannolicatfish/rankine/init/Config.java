package com.cannolicatfish.rankine.init;

import com.cannolicatfish.rankine.util.GasUtilsEnum;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber
public class Config {

    public static class General {

        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_WOODEN_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_STONE_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_IRON_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_GOLDEN_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_DIAMOND_HOE;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_SWORD;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_AXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_SHOVEL;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_PICKAXE;
        public final ForgeConfigSpec.BooleanValue DISABLE_NETHERITE_HOE;

        public final ForgeConfigSpec.BooleanValue MOVEMENT_MODIFIERS;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_SAND;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_GRASS_PATH;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_BRICKS;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_ROMAN_CONCRETE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_DIRT;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_POLISHED_STONE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_WOODEN;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_CONCRETE;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_SNOW;
        public final ForgeConfigSpec.DoubleValue MOVEMENT_MUD;

        public final ForgeConfigSpec.DoubleValue NUGGET_CHANCE;
        public final ForgeConfigSpec.IntValue NUGGET_DISTANCE;
        public final ForgeConfigSpec.IntValue PROSPECTING_STICK_RANGE;
        public final ForgeConfigSpec.IntValue ORE_DETECTOR_RANGE;
        public final ForgeConfigSpec.BooleanValue ORE_DETECTOR_MSG;

        public final ForgeConfigSpec.BooleanValue COLOR_WORLD;
        public final ForgeConfigSpec.BooleanValue FUEL_VALUES;
        public final ForgeConfigSpec.BooleanValue FLINT_FIRE;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_CORK;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_PAPER;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_CINNAMON;
        public final ForgeConfigSpec.BooleanValue STRIPPABLES_STICKS;
        public final ForgeConfigSpec.DoubleValue FLINT_FIRE_CHANCE;
        public final ForgeConfigSpec.DoubleValue FLINT_DROP_CHANCE;
        public final ForgeConfigSpec.DoubleValue FORAGING_CHANCE;
        public final ForgeConfigSpec.BooleanValue MANDATORY_AXE;
        public final ForgeConfigSpec.BooleanValue STARTING_BOOK;
        public final ForgeConfigSpec.BooleanValue PENDANT_CURSE;
        public final ForgeConfigSpec.BooleanValue VILLAGER_TRADES;
        public final ForgeConfigSpec.BooleanValue WANDERING_TRADE_SPECIAL;
        public final ForgeConfigSpec.BooleanValue IGNEOUS_COBBLE_GEN;
        public final ForgeConfigSpec.BooleanValue METAMORPHIC_STONE_GEN;
        public final ForgeConfigSpec.DoubleValue GLOBAL_BREAK_EXHAUSTION;
        public final ForgeConfigSpec.DoubleValue CHEESE_AGE_CHANCE;
        public final ForgeConfigSpec.DoubleValue BRICKS_HARDNESS_MULT;
        public final ForgeConfigSpec.DoubleValue BRICKS_RESISTANCE_MULT;
        public final ForgeConfigSpec.DoubleValue ICE_BREAK;
        public final ForgeConfigSpec.IntValue HERBICIDE_RANGE;
        public final ForgeConfigSpec.IntValue TRAMPOLINE_SIZE;
        public final ForgeConfigSpec.IntValue MAX_TREE;
        public final ForgeConfigSpec.IntValue LEAF_DECAY_SPEED;

        public General(ForgeConfigSpec.Builder b) {
            b.comment("Settings for general mechanics").push("general");

                b.comment("Miscellaneous").push("misc");
                    COLOR_WORLD = b.comment("If enabled, dyes can be used on blocks in-world to dye them (includes concrete, concrete powder, terracotta, glazed terracotta, stained glass, stained glass panes, leds, wool)")
                            .define("colorWorld",true);
                    STRIPPABLES_CINNAMON = b.comment("If enabled, cinnamon will drop from cinnamon trees when stripped.")
                            .define("strippablesCinnamon",true);
                    STRIPPABLES_CORK = b.comment("If enabled, cork will drop from cork oak trees when stripped.")
                            .define("strippablesCork",true);
                    STRIPPABLES_PAPER = b.comment("If enabled, paper will drop from birch trees when stripped (30% chance).")
                            .define("strippablesPaper",true);
                    STRIPPABLES_STICKS = b.comment("If enabled, sticks will drop from all other trees when stripped (30% chance).")
                            .define("strippablesSticks",true);
                    HERBICIDE_RANGE = b.comment("The radius at which herbicide will kill plants.")
                            .defineInRange("herbicideRange", 7, 0, 32);
                    MAX_TREE = b.comment("Maximum blocks to be considered a tree. Set to 0 to disable tree capitation.")
                            .defineInRange("maxTree", 256, 0, 1024);
                    LEAF_DECAY_SPEED = b.comment("Number of leaves to break per tick during tree chopping. Lower numbers mean smoother decay..")
                            .defineInRange("leafDecay", 10, 1, 64);
                    TRAMPOLINE_SIZE = b.comment("The maximum size of a trampoline. Jump factor depends on size. Set to 0 to have a fixed jump factor of 1.3 which is just enough to have the player gain height over time.")
                            .defineInRange("trampolineSize", 289, 0, 961);
                    CHEESE_AGE_CHANCE = b.comment("Chance for unaged cheese to age in a random tick.")
                            .defineInRange("cheeseAgeChance", 0.04D, 0.0D, 1.0D);
                    BRICKS_HARDNESS_MULT = b.comment("A multiplier to determine how much higher the bricks variant hardness is than the stone.")
                            .defineInRange("bricksHardnessMultiplier", 1.5D, 0.0D, 20.0D);
                    BRICKS_RESISTANCE_MULT = b.comment("A multiplier to determine how much higher the bricks variant resistance is than the stone.")
                            .defineInRange("bricksResistanceMultiplier", 1.5D, 0.0D, 20.0D);
                    STARTING_BOOK = b.comment("Enables the Rankine Journal (a guide to the mod)")
                            .define("startingBook",true);
                    PENDANT_CURSE = b.comment("Causes Pendants to spawn in with Curse of Vanishing")
                            .define("pendantCurse",true);
                    MANDATORY_AXE = b.comment("An axe is required to harvest logs")
                            .define("axesOnly",false);
                    FUEL_VALUES = b.comment("Change the fuel values of items for realism.")
                            .define("fuelValuesChange",true);
                    FLINT_DROP_CHANCE = b.comment("Chance for a stone block to drop a flint")
                            .defineInRange("flintDropChance", 0.15D, 0.00D, 1.00D);
                    FORAGING_CHANCE = b.comment("Chance for a dirt block to drop a vegetable")
                            .defineInRange("foragingChance", 0.15D, 0.00D, 1.00D);
                    IGNEOUS_COBBLE_GEN = b.comment("Change the output of a cobblestone generator and basalt generator to intrusive and extrusive igneous rocks respectively.")
                            .define("igneousGen",true);
                    METAMORPHIC_STONE_GEN = b.comment("Change the output of a stone generator from stone to metamorphic rocks.")
                            .define("igneousGen",true);
                    VILLAGER_TRADES = b.comment("Adds trades for Project Rankine to Villagers and the Wandering Trader.")
                            .define("villageTrades",true);
                    WANDERING_TRADE_SPECIAL = b.comment("Adds a trade to the Wandering Trader for a random tool which is not restricted by alloy constraints. May be unbalanced due to complete randomness.")
                            .define("wanderingSpecial",false);
                    GLOBAL_BREAK_EXHAUSTION = b.comment("Amount of additional exhaustion when breaking a block.")
                            .defineInRange("breakExhaustion", 0.00D, 0.00D, 1.00D);
                    ICE_BREAK = b.comment("Chance for ice to break when walking on it.")
                            .defineInRange("iceBreak", 0.002D, 0.0D, 1.0D);
                    FLINT_FIRE = b.comment("Enable the lighting of fires and certain machines using two flint.")
                            .define("flintFire",true);
                    FLINT_FIRE_CHANCE = b.comment("Chance for flint to be consumed when lighting a fire.")
                            .defineInRange("flintFireChance", 0.30D, 0.00D, 1.00D);
                b.pop();
                
                b.comment("Movement speed modifiers").push("movementModifiers");
                    MOVEMENT_MODIFIERS = b.comment("Set to false to disable movement speed modifiers.")
                            .define("movementModifiersEnabled",true);
                    MOVEMENT_SAND = b.comment("Movement speed modifier for walking on Sand blocks.")
                            .defineInRange("movementSand", -0.02D, -1.0D, 1.0D);
                    MOVEMENT_BRICKS = b.comment("Movement speed modifier for walking on Brick / Stone Bricks and variants.")
                            .defineInRange("movementBricks", 0.05D, -1.0D, 1.0D);
                    MOVEMENT_GRASS_PATH = b.comment("Movement speed modifier for walking on Grass Paths.")
                            .defineInRange("movementGrassPath", 0.02D, -1.0D, 1.0D);
                    MOVEMENT_ROMAN_CONCRETE = b.comment("Movement speed modifier for walking on Roman Cooncrete.")
                            .defineInRange("movementRomanConcrete", 0.1D, -1.0D, 1.0D);
                    MOVEMENT_DIRT = b.comment("Movement speed modifier for walking on Dirt / Grass blocks.")
                            .defineInRange("movementDirt", 0.0D, -1.0D, 1.0D);
                    MOVEMENT_POLISHED_STONE = b.comment("Movement speed modifier for walking on #forge:polished_stone blocks.")
                            .defineInRange("movementPolishedStone", 0.02D, -1.0D, 1.0D);
                    MOVEMENT_WOODEN = b.comment("Movement speed modifier for walking on Planks and wooden variants.")
                            .defineInRange("movementWooden", 0.02D, -1.0D, 1.0D);
                    MOVEMENT_CONCRETE = b.comment("Movement speed modifier for walking on Concrete / Gravel Concrete.")
                            .defineInRange("movementConcrete", 0.05D, -1.0D, 1.0D);
                    MOVEMENT_SNOW = b.comment("Movement speed modifier for walking on Snow.")
                            .defineInRange("movementSnow", -0.02D, -1.0D, 1.0D);
                    MOVEMENT_MUD = b.comment("Movement speed modifier for walking on Mud.")
                            .defineInRange("movementMud", -0.02D, -1.0D, 1.0D);
                b.pop();

                b.comment("Ore Detection").push("oreDetection");
                    PROSPECTING_STICK_RANGE = b.comment("Number of blocks away that the Prospecting Stick can detect ore.")
                            .defineInRange("prospectingStickRange", 16, 0, 64);
                    ORE_DETECTOR_RANGE = b.comment("Number of blocks away that the Ore Detector can detect ore.")
                            .defineInRange("oreDetectorRange", 32, 0, 64);
                    ORE_DETECTOR_MSG = b.comment("Set to false to disable the ore detector from outputting the block found.")
                            .define("oreDetectorMessage",true);
                    NUGGET_CHANCE = b.comment("Chance for a block in #rankine:nugget_stones to drop a nugget of a nearby ore.")
                            .defineInRange("nuggetChance", 0.15D, 0.00D, 1.00D);
                    NUGGET_DISTANCE = b.comment("Distance from an ore block in which nuggets have a chance to drop from blocks.")
                            .defineInRange("nuggetRange", 7, 1, 64);
                b.pop();
                
                b.comment("Vanilla Tools").push("vanillaTools");
                    b.comment("Wooden Tools").push("woodenTools");
                    DISABLE_WOODEN_SWORD = b.comment("Disable the use of the wooden sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenSword",true);
                    DISABLE_WOODEN_AXE = b.comment("Disable the use of the wooden axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenAxe",true);
                    DISABLE_WOODEN_SHOVEL = b.comment("Disable the use of the wooden shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenShovel",true);
                    DISABLE_WOODEN_PICKAXE = b.comment("Disable the use of the wooden pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenPickaxe",true);
                    DISABLE_WOODEN_HOE = b.comment("Disable the use of the wooden hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableWoodenHoe",true);
                    b.pop();

                    b.comment("Stone Tools").push("stoneTools");
                    DISABLE_STONE_SWORD = b.comment("Disable the use of the stone sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneSword",true);
                    DISABLE_STONE_AXE = b.comment("Disable the use of the stone axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneAxe",true);
                    DISABLE_STONE_SHOVEL = b.comment("Disable the use of the stone shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneShovel",true);
                    DISABLE_STONE_PICKAXE = b.comment("Disable the use of the stone pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStonePickaxe",true);
                    DISABLE_STONE_HOE = b.comment("Disable the use of the stone hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableStoneHoe",true);
                    b.pop();

                    b.comment("Iron Tools").push("ironTools");
                    DISABLE_IRON_SWORD = b.comment("Disable the use of the iron sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronSword",true);
                    DISABLE_IRON_AXE = b.comment("Disable the use of the iron axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronAxe",true);
                    DISABLE_IRON_SHOVEL = b.comment("Disable the use of the iron shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronShovel",true);
                    DISABLE_IRON_PICKAXE = b.comment("Disable the use of the iron pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronPickaxe",true);
                    DISABLE_IRON_HOE = b.comment("Disable the use of the iron hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableIronHoe",true);
                    b.pop();

                    b.comment("Gold Tools").push("goldTools");
                    DISABLE_GOLDEN_SWORD = b.comment("Disable the use of the gold sword (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldSword",true);
                    DISABLE_GOLDEN_AXE = b.comment("Disable the use of the gold axe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldAxe",true);
                    DISABLE_GOLDEN_SHOVEL = b.comment("Disable the use of the gold shovel (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldShovel",true);
                    DISABLE_GOLDEN_PICKAXE = b.comment("Disable the use of the gold pickaxe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldPickaxe",true);
                    DISABLE_GOLDEN_HOE = b.comment("Disable the use of the gold hoe (still allows crafting for other recipes). This is enabled by default for progression.")
                            .define("disableGoldHoe",true);
                    b.pop();

                    b.comment("Diamond Tools").push("diamondTools");
                    DISABLE_DIAMOND_SWORD = b.comment("Disable the use of the diamond sword (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondSword",true);
                    DISABLE_DIAMOND_AXE = b.comment("Disable the use of the diamond axe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondAxe",true);
                    DISABLE_DIAMOND_SHOVEL = b.comment("Disable the use of the diamond shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondShovel",true);
                    DISABLE_DIAMOND_PICKAXE = b.comment("Disable the use of the diamond pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondPickaxe",true);
                    DISABLE_DIAMOND_HOE = b.comment("Disable the use of the diamond hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableDiamondHoe",true);
                    b.pop();

                    b.comment("Netherite Tools").push("netheriteTools");
                    DISABLE_NETHERITE_SWORD = b.comment("Disable the use of the netherite sword (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteSword",true);
                    DISABLE_NETHERITE_AXE = b.comment("Disable the use of the netherite axe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteAxe",true);
                    DISABLE_NETHERITE_SHOVEL = b.comment("Disable the use of the netherite shovel (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteShovel",true);
                    DISABLE_NETHERITE_PICKAXE = b.comment("Disable the use of the netherite pickaxe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheritePickaxe",true);
                    DISABLE_NETHERITE_HOE = b.comment("Disable the use of the netherite hoe (still allows crafting for other recipes). This is disabled by default for progression.")
                            .define("disableNetheriteHoe",true);
                    b.pop();
                b.pop();
            b.pop();
        }
    }

    public static class Alloys {

        public final ForgeConfigSpec.BooleanValue ALLOY_CORROSION;
        public final ForgeConfigSpec.IntValue ALLOY_CORROSION_AMT;
        public final ForgeConfigSpec.BooleanValue ALLOY_HEAT;
        public final ForgeConfigSpec.IntValue ALLOY_HEAT_AMT;
        public final ForgeConfigSpec.BooleanValue ALLOY_TOUGHNESS;
        public final ForgeConfigSpec.DoubleValue ALLOY_WEAR_MINING_AMT;
        public final ForgeConfigSpec.DoubleValue ALLOY_WEAR_DAMAGE_AMT;

        public final ForgeConfigSpec.IntValue ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue AMALGAM_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue AMALGAM_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue AMALGAM_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue AMALGAM_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue AMALGAM_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue AMALGAM_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue AMALGAM_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue AMALGAM_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue AMALGAM_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue BRONZE_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue BRONZE_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue BRONZE_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue BRONZE_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue BRONZE_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue BRONZE_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue BRONZE_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue BRONZE_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue BRONZE_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue INVAR_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue INVAR_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue INVAR_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue INVAR_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue INVAR_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue INVAR_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue INVAR_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue INVAR_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue INVAR_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue ROSE_GOLD_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue ROSE_GOLD_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue ROSE_GOLD_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue ROSE_GOLD_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue WHITE_GOLD_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue WHITE_GOLD_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue WHITE_GOLD_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue WHITE_GOLD_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue WHITE_GOLD_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue WHITE_GOLD_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue WHITE_GOLD_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue WHITE_GOLD_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue WHITE_GOLD_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue GREEN_GOLD_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue GREEN_GOLD_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue GREEN_GOLD_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue GREEN_GOLD_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue GREEN_GOLD_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue GREEN_GOLD_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue GREEN_GOLD_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue GREEN_GOLD_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue GREEN_GOLD_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue BLUE_GOLD_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue BLUE_GOLD_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue BLUE_GOLD_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue BLUE_GOLD_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue BLUE_GOLD_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue BLUE_GOLD_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue BLUE_GOLD_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue BLUE_GOLD_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue BLUE_GOLD_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue PURPLE_GOLD_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue PURPLE_GOLD_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue PURPLE_GOLD_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue PURPLE_GOLD_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue PURPLE_GOLD_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue PURPLE_GOLD_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue PURPLE_GOLD_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue PURPLE_GOLD_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue PURPLE_GOLD_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue BLACK_GOLD_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue BLACK_GOLD_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue BLACK_GOLD_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue BLACK_GOLD_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue BLACK_GOLD_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue BLACK_GOLD_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue BLACK_GOLD_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue BLACK_GOLD_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue BLACK_GOLD_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue PEWTER_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue PEWTER_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue PEWTER_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue PEWTER_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue PEWTER_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue PEWTER_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue PEWTER_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue PEWTER_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue PEWTER_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue STEEL_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue STEEL_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue STEEL_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue STEEL_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue STEEL_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue STEEL_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue STEEL_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue STEEL_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue STEEL_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue TITANIUM_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue TITANIUM_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue TITANIUM_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue TITANIUM_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue TITANIUM_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue TITANIUM_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue TITANIUM_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue TITANIUM_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue TITANIUM_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue STAINLESS_STEEL_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue STAINLESS_STEEL_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue STAINLESS_STEEL_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue STAINLESS_STEEL_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue STAINLESS_STEEL_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue STAINLESS_STEEL_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue STAINLESS_STEEL_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue STAINLESS_STEEL_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue STAINLESS_STEEL_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue TUNGSTEN_HEAVY_ALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue TUNGSTEN_HEAVY_ALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue TUNGSTEN_HEAVY_ALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue TUNGSTEN_HEAVY_ALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue TUNGSTEN_HEAVY_ALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue TUNGSTEN_HEAVY_ALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue TUNGSTEN_HEAVY_ALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue TUNGSTEN_HEAVY_ALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue TUNGSTEN_HEAVY_ALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue NICKEL_SUPERALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue NICKEL_SUPERALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue NICKEL_SUPERALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue NICKEL_SUPERALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue NICKEL_SUPERALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue NICKEL_SUPERALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue NICKEL_SUPERALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue NICKEL_SUPERALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue NICKEL_SUPERALLOY_BONUS_TOUGHNESS;

        public final ForgeConfigSpec.IntValue COBALT_SUPERALLOY_BONUS_DURABILITY;
        public final ForgeConfigSpec.DoubleValue COBALT_SUPERALLOY_BONUS_MINING_SPEED;
        public final ForgeConfigSpec.IntValue COBALT_SUPERALLOY_BONUS_HL;
        public final ForgeConfigSpec.IntValue COBALT_SUPERALLOY_BONUS_ENCHANTABILITY;
        public final ForgeConfigSpec.DoubleValue COBALT_SUPERALLOY_BONUS_DAMAGE;
        public final ForgeConfigSpec.DoubleValue COBALT_SUPERALLOY_BONUS_ATTACK_SPEED;
        public final ForgeConfigSpec.DoubleValue COBALT_SUPERALLOY_BONUS_CORR_RESIST;
        public final ForgeConfigSpec.DoubleValue COBALT_SUPERALLOY_BONUS_HEAT_RESIST;
        public final ForgeConfigSpec.DoubleValue COBALT_SUPERALLOY_BONUS_TOUGHNESS;

        public Alloys(ForgeConfigSpec.Builder b) {
            b.comment("Settings for alloys and alloy tools").push("alloys");
                ALLOY_CORROSION = b.comment("Enables the corrosion negative modifier for alloy tools (chance to consume extra points of durability in water and rain)")
                        .define("alloyCorrosion",true);
                ALLOY_CORROSION_AMT = b.comment("If enabled, modifies the amount of durability damage taken in wet environments.")
                        .defineInRange("alloyCorrosionDmgAmount", 1, 1, 10);
                ALLOY_HEAT = b.comment("Enables the heat negative modifier for alloy tools (chance to consume extra points of durability in hot environments and lava)")
                        .define("alloyHeat",true);
                ALLOY_HEAT_AMT = b.comment("If enabled, modifies the amount of durability damage taken in hot environments.")
                        .defineInRange("alloyHeatDmgAmount", 1, 1, 10);
                ALLOY_TOUGHNESS = b.comment("Enables the toughness negative modifier for alloy tools (chance to consume/resist loss of an extra point of durability)")
                        .define("alloyToughness",true);
                ALLOY_WEAR_MINING_AMT = b.comment("Modifies the severity of the wear effect on mining speed (ex. 0.25 means mining speed will be reduced to 75% of the original value as durability is lost)")
                        .defineInRange("alloyWearMiningAmount", 0.25D, 0.00D, 0.99D);
                ALLOY_WEAR_DAMAGE_AMT = b.comment("Modifies the severity of the wear effect on damage (ex. 0.25 means damage will be reduced to 75% of the original value as durability is lost)")
                        .defineInRange("alloyWearDamageAmount", 0.25D, 0.00D, 0.99D);
                b.pop();
            b.comment("Custom Alloy Tool Properties").push("alloy");
            ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for the custom alloy tools.")
                    .defineInRange("alloyBonusDurability", 0, 0, 10000);
            ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for the custom alloy tools.")
                    .defineInRange("alloyBonusMiningSpeed", 0.0D, 0D, 20D);
            ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for the custom alloy tools.")
                    .defineInRange("alloyBonusHL", 0, 0, 10);
            ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for the custom alloy tools.")
                    .defineInRange("alloyBonusEnchantability", 0, 0, 40);
            ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for the custom alloy tools.")
                    .defineInRange("alloyBonusAttackSpeed", 0.0D, 0D, 4D);
            ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for the custom alloy tools.")
                    .defineInRange("alloyBonusDamage", 0.0D, 0D, 20D);
            ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for the custom alloy tools.")
                    .defineInRange("alloyBonusCorrResist", 0.0D, 0D, 1D);
            ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for the custom alloy tools.")
                    .defineInRange("alloyBonusHeatResist", 0.0D, 0D, 1D);
            ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for the custom alloy tools.")
                    .defineInRange("alloyBonusToughness", 0D, -1D, 1D);
            b.pop();
            b.comment("Amalgam Alloy Tool Properties").push("amalgam");
            AMALGAM_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusDurability", 0, 0, 10000);
            AMALGAM_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusMiningSpeed", 0.0D, 0D, 20D);
            AMALGAM_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusHL", 0, 0, 10);
            AMALGAM_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusEnchantability", 0, 0, 40);
            AMALGAM_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusAttackSpeed", 0.0D, 0D, 4D);
            AMALGAM_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusDamage", 0.0D, 0D, 20D);
            AMALGAM_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusCorrResist", 0.0D, 0D, 1D);
            AMALGAM_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusHeatResist", 0.0D, 0D, 1D);
            AMALGAM_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Amalgam tool.")
                    .defineInRange("amalgamBonusToughness", -0.2D, -1D, 1D);
            b.pop();
            b.comment("Bronze Alloy Tool Properties").push("bronze");
            BRONZE_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Bronze tool.")
                    .defineInRange("bronzeBonusDurability", 51, 0, 10000);
            BRONZE_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Bronze tool.")
                    .defineInRange("bronzeBonusMiningSpeed", 0.0D, 0D, 20D);
            BRONZE_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Bronze tool.")
                    .defineInRange("bronzeBonusHL", 0, 0, 10);
            BRONZE_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Bronze tool.")
                    .defineInRange("bronzeBonusEnchantability", 0, 0, 40);
            BRONZE_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Bronze tool.")
                    .defineInRange("bronzeBonusAttackSpeed", 0.0D, 0D, 4D);
            BRONZE_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Bronze tool.")
                    .defineInRange("bronzeBonusDamage", 0.0D, 0D, 20D);
            BRONZE_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Bronze tool.")
                    .defineInRange("bronzeBonusCorrResist", 0.0D, 0D, 1D);
            BRONZE_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Bronze tool.")
                    .defineInRange("bronzeBonusHeatResist", 0.0D, 0D, 1D);
            BRONZE_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Bronze tool.")
                    .defineInRange("bronzeBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Invar Alloy Tool Properties").push("invar");
            INVAR_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Invar tool.")
                    .defineInRange("invarBonusDurability", 0, 0, 10000);
            INVAR_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Invar tool.")
                    .defineInRange("invarBonusMiningSpeed", 0.0D, 0D, 20D);
            INVAR_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Invar tool.")
                    .defineInRange("invarBonusHL", 0, 0, 10);
            INVAR_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Invar tool.")
                    .defineInRange("invarBonusEnchantability", 0, 0, 40);
            INVAR_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Invar tool.")
                    .defineInRange("invarBonusAttackSpeed", 0.0D, 0D, 4D);
            INVAR_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Invar tool.")
                    .defineInRange("invarBonusDamage", 0.0D, 0D, 20D);
            INVAR_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Invar tool.")
                    .defineInRange("invarBonusCorrResist", 0.0D, 0D, 1D);
            INVAR_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Invar tool.")
                    .defineInRange("invarBonusHeatResist", 0.1D, 0D, 1D);
            INVAR_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Invar tool.")
                    .defineInRange("invarBonusToughness", 0.05D, -1D, 1D);
            b.pop();
            b.comment("Rose Gold Alloy Tool Properties").push("roseGold");
            ROSE_GOLD_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusDurability", 48, 0, 10000);
            ROSE_GOLD_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusMiningSpeed", 0.0D, 0D, 20D);
            ROSE_GOLD_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusHL", 0, 0, 10);
            ROSE_GOLD_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusEnchantability", 0, 0, 40);
            ROSE_GOLD_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusAttackSpeed", 0.0D, 0D, 4D);
            ROSE_GOLD_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusDamage", 0.0D, 0D, 20D);
            ROSE_GOLD_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusCorrResist", 0.05D, 0D, 1D);
            ROSE_GOLD_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusHeatResist", 0.0D, 0D, 1D);
            ROSE_GOLD_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Rose Gold tool.")
                    .defineInRange("roseGoldBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("White Gold Alloy Tool Properties").push("whiteGold");
            WHITE_GOLD_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusDurability", 32, 0, 10000);
            WHITE_GOLD_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusMiningSpeed", 0.0D, 0D, 20D);
            WHITE_GOLD_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusHL", 0, 0, 10);
            WHITE_GOLD_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusEnchantability", 0, 0, 40);
            WHITE_GOLD_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusAttackSpeed", 0.0D, 0D, 4D);
            WHITE_GOLD_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusDamage", 0.0D, 0D, 20D);
            WHITE_GOLD_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusCorrResist", 0.1D, 0D, 1D);
            WHITE_GOLD_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusHeatResist", 0.0D, 0D, 1D);
            WHITE_GOLD_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition White Gold tool.")
                    .defineInRange("whiteGoldBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Green Gold Alloy Tool Properties").push("greenGold");
            GREEN_GOLD_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusDurability", 32, 0, 10000);
            GREEN_GOLD_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusMiningSpeed", 0.0D, 0D, 20D);
            GREEN_GOLD_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusHL", 0, 0, 10);
            GREEN_GOLD_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusEnchantability", 0, 0, 40);
            GREEN_GOLD_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusAttackSpeed", 0.0D, 0D, 4D);
            GREEN_GOLD_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusDamage", 0.0D, 0D, 20D);
            GREEN_GOLD_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusCorrResist", 0.0D, 0D, 1D);
            GREEN_GOLD_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusHeatResist", 0.0D, 0D, 1D);
            GREEN_GOLD_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Green Gold tool.")
                    .defineInRange("greenGoldBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Blue Gold Alloy Tool Properties").push("blueGold");
            BLUE_GOLD_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusDurability", 32, 0, 10000);
            BLUE_GOLD_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusMiningSpeed", 0.0D, 0D, 20D);
            BLUE_GOLD_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusHL", 0, 0, 10);
            BLUE_GOLD_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusEnchantability", 0, 0, 40);
            BLUE_GOLD_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusAttackSpeed", 0.0D, 0D, 4D);
            BLUE_GOLD_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusDamage", 0.0D, 0D, 20D);
            BLUE_GOLD_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusCorrResist", 0.0D, 0D, 1D);
            BLUE_GOLD_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusHeatResist", 0.0D, 0D, 1D);
            BLUE_GOLD_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Blue Gold tool.")
                    .defineInRange("blueGoldBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Purple Gold Alloy Tool Properties").push("purpleGold");
            PURPLE_GOLD_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusDurability", 32, 0, 10000);
            PURPLE_GOLD_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusMiningSpeed", 0.0D, 0D, 20D);
            PURPLE_GOLD_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusHL", 0, 0, 10);
            PURPLE_GOLD_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusEnchantability", 0, 0, 40);
            PURPLE_GOLD_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusAttackSpeed", 0.0D, 0D, 4D);
            PURPLE_GOLD_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusDamage", 0.0D, 0D, 20D);
            PURPLE_GOLD_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusCorrResist", 0.0D, 0D, 1D);
            PURPLE_GOLD_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusHeatResist", 0.0D, 0D, 1D);
            PURPLE_GOLD_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Purple Gold tool.")
                    .defineInRange("purpleGoldBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Black Gold Alloy Tool Properties").push("blackGold");
            BLACK_GOLD_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusDurability", 32, 0, 10000);
            BLACK_GOLD_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusMiningSpeed", 0.0D, 0D, 20D);
            BLACK_GOLD_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusHL", 0, 0, 10);
            BLACK_GOLD_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusEnchantability", 0, 0, 40);
            BLACK_GOLD_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusAttackSpeed", 0.0D, 0D, 4D);
            BLACK_GOLD_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusDamage", 0.0D, 0D, 20D);
            BLACK_GOLD_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusCorrResist", 0.0D, 0D, 1D);
            BLACK_GOLD_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusHeatResist", 0.0D, 0D, 1D);
            BLACK_GOLD_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Black Gold tool.")
                    .defineInRange("blackGoldBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Pewter Alloy Tool Properties").push("pewter");
            PEWTER_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Pewter tool.")
                    .defineInRange("pewterBonusDurability", 20, 0, 10000);
            PEWTER_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Pewter tool.")
                    .defineInRange("pewterBonusMiningSpeed", 0.0D, 0D, 20D);
            PEWTER_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Pewter tool.")
                    .defineInRange("pewterBonusHL", 0, 0, 10);
            PEWTER_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Pewter tool.")
                    .defineInRange("pewterBonusEnchantability", 0, 0, 40);
            PEWTER_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Pewter tool.")
                    .defineInRange("pewterBonusAttackSpeed", 0.0D, 0D, 4D);
            PEWTER_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Pewter tool.")
                    .defineInRange("pewterBonusDamage", 0.0D, 0D, 20D);
            PEWTER_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Pewter tool.")
                    .defineInRange("pewterBonusCorrResist", 0.0D, 0D, 1D);
            PEWTER_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Pewter tool.")
                    .defineInRange("pewterBonusHeatResist", 0.0D, 0D, 1D);
            PEWTER_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Pewter tool.")
                    .defineInRange("pewterBonusToughness", -0.05D, -1D, 1D);
            b.pop();
            b.comment("Steel Alloy Tool Properties").push("steel");
            STEEL_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Steel tool.")
                    .defineInRange("steelBonusDurability", 460, 0, 10000);
            STEEL_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Steel tool.")
                    .defineInRange("steelBonusMiningSpeed", 4.0D, 0D, 20D);
            STEEL_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Steel tool.")
                    .defineInRange("steelBonusHL", 1, 0, 10);
            STEEL_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Steel tool.")
                    .defineInRange("steelBonusEnchantability", 0, 0, 40);
            STEEL_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Steel tool.")
                    .defineInRange("steelBonusAttackSpeed", 0.0D, 0D, 4D);
            STEEL_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Steel tool.")
                    .defineInRange("steelBonusDamage", 0.0D, 0D, 20D);
            STEEL_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Steel tool.")
                    .defineInRange("steelBonusCorrResist", 0.0D, 0D, 1D);
            STEEL_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Steel tool.")
                    .defineInRange("steelBonusHeatResist", 0.0D, 0D, 1D);
            STEEL_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Steel tool.")
                    .defineInRange("steelBonusToughness", 0.25D, -1D, 1D);
            b.pop();
            b.comment("Titanium Alloy Tool Properties").push("titanium");
            TITANIUM_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Titanium tool.")
                    .defineInRange("titaniumBonusDurability", 50, 0, 10000);
            TITANIUM_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Titanium tool.")
                    .defineInRange("titaniumBonusMiningSpeed", 0.0D, 0D, 20D);
            TITANIUM_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Titanium tool.")
                    .defineInRange("titaniumBonusHL", 1, 0, 10);
            TITANIUM_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Titanium tool.")
                    .defineInRange("titaniumBonusEnchantability", 0, 0, 40);
            TITANIUM_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Titanium tool.")
                    .defineInRange("titaniumBonusAttackSpeed", 0.0D, 0D, 4D);
            TITANIUM_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Titanium tool.")
                    .defineInRange("titaniumBonusDamage", 0.0D, 0D, 20D);
            TITANIUM_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Titanium tool.")
                    .defineInRange("titaniumBonusCorrResist", 0.0D, 0D, 1D);
            TITANIUM_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Titanium tool.")
                    .defineInRange("titaniumBonusHeatResist", 0.0D, 0D, 1D);
            TITANIUM_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Titanium tool.")
                    .defineInRange("titaniumBonusToughness", 0.0D, -1D, 1D);
            b.pop();
            b.comment("Stainless Steel Alloy Tool Properties").push("stainlessSteel");
            STAINLESS_STEEL_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusDurability", 760, 0, 10000);
            STAINLESS_STEEL_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusMiningSpeed", 4.0D, 0D, 20D);
            STAINLESS_STEEL_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusHL", 1, 0, 10);
            STAINLESS_STEEL_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusEnchantability", 0, 0, 40);
            STAINLESS_STEEL_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusAttackSpeed", 0.0D, 0D, 4D);
            STAINLESS_STEEL_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusDamage", 0.0D, 0D, 20D);
            STAINLESS_STEEL_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusCorrResist", 0.0D, 0D, 1D);
            STAINLESS_STEEL_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusHeatResist", 0.0D, 0D, 1D);
            STAINLESS_STEEL_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Stainless Steel tool.")
                    .defineInRange("stainlessSteelBonusToughness", 0.25D, -1D, 1D);
            b.pop();
            b.comment("Tungsten Heavy Alloy Alloy Tool Properties").push("tungstenHeavyAlloy");
            TUNGSTEN_HEAVY_ALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusDurability", 370, 0, 10000);
            TUNGSTEN_HEAVY_ALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusMiningSpeed", 3.5D, 0D, 20D);
            TUNGSTEN_HEAVY_ALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusHL", 1, 0, 10);
            TUNGSTEN_HEAVY_ALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusEnchantability", 3, 0, 40);
            TUNGSTEN_HEAVY_ALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusAttackSpeed", 0.0D, 0D, 4D);
            TUNGSTEN_HEAVY_ALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusDamage", 0.0D, 0D, 20D);
            TUNGSTEN_HEAVY_ALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusCorrResist", 0.0D, 0D, 1D);
            TUNGSTEN_HEAVY_ALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusHeatResist", 0.0D, 0D, 1D);
            TUNGSTEN_HEAVY_ALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Tungsten Heavy Alloy tool.")
                    .defineInRange("tungstenHeavyAlloyBonusToughness", 0.15D, -1D, 1D);
            b.pop();
            b.comment("Nickel Superalloy Alloy Tool Properties").push("nickelSuperalloy");
            NICKEL_SUPERALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusDurability", 970, 0, 10000);
            NICKEL_SUPERALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusMiningSpeed", 3.5D, 0D, 20D);
            NICKEL_SUPERALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusHL", 1, 0, 10);
            NICKEL_SUPERALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusEnchantability", 3, 0, 40);
            NICKEL_SUPERALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusAttackSpeed", 0.0D, 0D, 4D);
            NICKEL_SUPERALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusDamage", 0.0D, 0D, 20D);
            NICKEL_SUPERALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusCorrResist", 0.0D, 0D, 1D);
            NICKEL_SUPERALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusHeatResist", 0.0D, 0D, 1D);
            NICKEL_SUPERALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Nickel Superalloy tool.")
                    .defineInRange("nickelSuperalloyBonusToughness", 0.15D, -1D, 1D);
            b.pop();
            b.comment("Cobalt Superalloy Alloy Tool Properties").push("cobaltSuperalloy");
            COBALT_SUPERALLOY_BONUS_DURABILITY = b.comment("Adds bonus durability for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusDurability", 500, 0, 10000);
            COBALT_SUPERALLOY_BONUS_MINING_SPEED = b.comment("Adds bonus mining speed for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusMiningSpeed", 3.5D, 0D, 20D);
            COBALT_SUPERALLOY_BONUS_HL = b.comment("Adds bonus to harvest level for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusHL", 1, 0, 10);
            COBALT_SUPERALLOY_BONUS_ENCHANTABILITY = b.comment("Adds bonus enchantability for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusEnchantability", 3, 0, 40);
            COBALT_SUPERALLOY_BONUS_ATTACK_SPEED = b.comment("Adds bonus attack speed for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusAttackSpeed", 0.0D, 0D, 4D);
            COBALT_SUPERALLOY_BONUS_DAMAGE = b.comment("Adds bonus damage for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusDamage", 0.0D, 0D, 20D);
            COBALT_SUPERALLOY_BONUS_CORR_RESIST = b.comment("Adds bonus corrosion resistance for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusCorrResist", 0.0D, 0D, 1D);
            COBALT_SUPERALLOY_BONUS_HEAT_RESIST = b.comment("Adds bonus heat resistance for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusHeatResist", 0.0D, 0D, 1D);
            COBALT_SUPERALLOY_BONUS_TOUGHNESS = b.comment("Adds bonus toughness for any composition Cobalt Superalloy tool.")
                    .defineInRange("cobaltSuperalloyBonusToughness", 0.3D, -1D, 1D);
            b.pop();
        }
    }

    public static class Machines {

        public final ForgeConfigSpec.BooleanValue TREE_TAP_WATER;
        public final ForgeConfigSpec.BooleanValue TREE_TAP_LAVA;
        public final ForgeConfigSpec.BooleanValue TREE_TAP_MAPLE_SAP;
        public final ForgeConfigSpec.BooleanValue TREE_TAP_SAP;
        public final ForgeConfigSpec.BooleanValue TREE_TAP_LATEX;
        public final ForgeConfigSpec.BooleanValue TREE_TAP_RESIN;
        public final ForgeConfigSpec.BooleanValue TREE_TAP_JUGLONE;
        public final ForgeConfigSpec.IntValue TREE_TAP_SPEED;

        public final ForgeConfigSpec.IntValue CHARCOAL_PIT_SPEED;
        public final ForgeConfigSpec.IntValue CHARCOAL_PIT_RADIUS;
        public final ForgeConfigSpec.IntValue CHARCOAL_PIT_HEIGHT;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_SPEED;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_SPEED_SAP;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_SPEED_MAPLE_SAP;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_SPEED_RESIN;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_SPEED_LATEX;
        public final ForgeConfigSpec.IntValue EVAPORATION_TOWER_RANGE;
        public final ForgeConfigSpec.IntValue RANKINE_BOX_SPEED;
        public final ForgeConfigSpec.BooleanValue RANKINE_BOX_UP;
        public final ForgeConfigSpec.BooleanValue RANKINE_BOX_DOWN;
        public final ForgeConfigSpec.BooleanValue RANKINE_BOX_RIGHT;
        public final ForgeConfigSpec.BooleanValue RANKINE_BOX_LEFT;
        public final ForgeConfigSpec.IntValue ALNICO_MAGNET_RANGE;
        public final ForgeConfigSpec.IntValue RARE_MAGNET_RANGE;
        public final ForgeConfigSpec.BooleanValue ELECTROMAGNET_MATERIAL_REQ;
        public final ForgeConfigSpec.IntValue LASER_QUARRY_RANGE;
        public final ForgeConfigSpec.IntValue LASER_QUARRY_SPEED;

        public Machines(ForgeConfigSpec.Builder b) {
            b.comment("Settings for machines").push("machines");
                TREE_TAP_WATER = b.comment("Enable the tapping of trees in #rankine:logs_water for water.")
                        .define("tapWater", true);
                TREE_TAP_LAVA = b.comment("Enable the tapping of trees in #rankine:logs_lava for lava.")
                        .define("tapLava", true);
                TREE_TAP_SAP = b.comment("Enable the tapping of trees in #rankine:logs_sap for sap.")
                        .define("tapSap", true);
                TREE_TAP_MAPLE_SAP = b.comment("Enable the tapping of trees in #rankine:logs_maple_sap for maple sap.")
                        .define("tapMapleSap", true);
                TREE_TAP_RESIN = b.comment("Enable the tapping of trees in #rankine:logs_resin for resin.")
                        .define("tapResin", true);
                TREE_TAP_LATEX = b.comment("Enable the tapping of trees in #rankine:logs_latex for latex.")
                        .define("tapLatex", true);
                TREE_TAP_JUGLONE = b.comment("Enable the tapping of trees in #rankine:logs_juglone for juglone.")
                        .define("tapJuglone", true);
                TREE_TAP_SPEED = b.comment("The number of random ticks it takes the Tree Tap to process")
                        .defineInRange("treeTapSpeed", 3, 1, 1000);
                CHARCOAL_PIT_RADIUS = b.comment("Maximum radius the charcoal pit can convert logs.")
                        .defineInRange("charcoalPitRadius", 7, 3, 15);
                CHARCOAL_PIT_SPEED = b.comment("The number of random ticks it takes the Charcoal Pit to process")
                        .defineInRange("charcoalPitSpeed", 5, 1, 200);
                CHARCOAL_PIT_HEIGHT = b.comment("Maximum height a charcoal pile can be")
                        .defineInRange("charcoalPitHeight", 5, 1, 10);
                EVAPORATION_TOWER_SPEED_SAP = b.comment("Base speed (in ticks) at which the evaporation tower boils sap.")
                    .defineInRange("evaporationTowerSpeedSap", 1600, 40, 120000);
                EVAPORATION_TOWER_SPEED_MAPLE_SAP = b.comment("Base speed (in ticks) at which the evaporation tower boils maple sap.")
                    .defineInRange("evaporationTowerSpeedMapleSap", 800, 40, 120000);
                EVAPORATION_TOWER_SPEED_RESIN = b.comment("Base speed (in ticks) at which the evaporation tower boils resin.")
                    .defineInRange("evaporationTowerSpeedResin", 1600, 40, 120000);
                EVAPORATION_TOWER_SPEED_LATEX = b.comment("Base speed (in ticks) at which the evaporation tower boils latex.")
                    .defineInRange("evaporationTowerSpeedLatex", 800, 40, 120000);
                EVAPORATION_TOWER_SPEED = b.comment("Base speed (in ticks) at which the water / lava evaporation tower generates resources.")
                        .defineInRange("evaporationTowerSpeed", 3200, 40, 120000);
                EVAPORATION_TOWER_RANGE = b.comment("Maximum height of the evaporation tower. Height affects yields. Set to 0 to disable functionality.")
                        .defineInRange("evaporationTowerHeight", 40, 0, 40);
                RANKINE_BOX_SPEED = b.comment("Total amount of time required (in ticks) at which the Rankine Box transmutes elements.")
                        .defineInRange("rankineBoxSpeed", 800, 20, 12000);
                RANKINE_BOX_UP = b.comment("Allow for transmuting elements up periods in the Rankine Box.")
                        .define("rankineBoxUp", true);
                RANKINE_BOX_DOWN = b.comment("Allow for transmuting elements down periods in the Rankine Box.")
                        .define("rankineBoxDown", true);
                RANKINE_BOX_RIGHT = b.comment("Allow for transmuting elements to the next highest atomic number in the Rankine Box.")
                        .define("rankineBoxRight", true);
                RANKINE_BOX_LEFT = b.comment("Allow for transmuting elements to the next lowest atomic number in the Rankine Box.")
                        .define("rankineBoxLeft", true);
                ALNICO_MAGNET_RANGE = b.comment("Range for the Alnico Magnet's pickup radius and the Alnico Electromagnet range.")
                        .defineInRange("alnicoMagnetRange",5,1,10);
                RARE_MAGNET_RANGE = b.comment("Range for the Rare Earth Magnet's pickup radius and the Rare Earth Electromagnet range.")
                        .defineInRange("rareEarthMagnetRange",10,1,15);
                ELECTROMAGNET_MATERIAL_REQ = b.comment("Require the material of the block to be Material.IRON in order for the electromagnet to pull the block. If disabled, it will pick up any block as long as it is not a FluidBlock, Tile Entity, or in the rankine:magnet_banned tag (these blocks are also banned if this value is true).")
                        .define("electromagnetMaterialReq",true);
                LASER_QUARRY_RANGE = b.comment("Max range of the laser quarry. Larger numbers may cause lag. Set to 0 to disable functionality.")
                        .defineInRange("laserQuarryRange", 31, 0, 63);
                LASER_QUARRY_SPEED = b.comment("Max speed of the laser quarry in ticks.")
                        .defineInRange("laserQuarrySpeed", 20, 1, 300);
            b.pop();
        }
    }
    
    public static class StoneProperties {

        public final ForgeConfigSpec.IntValue GRAY_GRANITE_HL;
        public final ForgeConfigSpec.DoubleValue GRAY_GRANITE_HARD;
        public final ForgeConfigSpec.DoubleValue GRAY_GRANITE_RESIST;
        public final ForgeConfigSpec.IntValue GRANODIORITE_HL;
        public final ForgeConfigSpec.DoubleValue GRANODIORITE_HARD;
        public final ForgeConfigSpec.DoubleValue GRANODIORITE_RESIST;
        public final ForgeConfigSpec.IntValue HORNBLENDE_ANDESITE_HL;
        public final ForgeConfigSpec.DoubleValue HORNBLENDE_ANDESITE_HARD;
        public final ForgeConfigSpec.DoubleValue HORNBLENDE_ANDESITE_RESIST;
        public final ForgeConfigSpec.IntValue THOLEIITIC_BASALT_HL;
        public final ForgeConfigSpec.DoubleValue THOLEIITIC_BASALT_HARD;
        public final ForgeConfigSpec.DoubleValue THOLEIITIC_BASALT_RESIST;
        public final ForgeConfigSpec.IntValue PYROXENE_GABBRO_HL;
        public final ForgeConfigSpec.DoubleValue PYROXENE_GABBRO_HARD;
        public final ForgeConfigSpec.DoubleValue PYROXENE_GABBRO_RESIST;
        public final ForgeConfigSpec.IntValue ANORTHOSITE_HL;
        public final ForgeConfigSpec.DoubleValue ANORTHOSITE_HARD;
        public final ForgeConfigSpec.DoubleValue ANORTHOSITE_RESIST;
        public final ForgeConfigSpec.IntValue RHYOLITE_HL;
        public final ForgeConfigSpec.DoubleValue RHYOLITE_HARD;
        public final ForgeConfigSpec.DoubleValue RHYOLITE_RESIST;
        public final ForgeConfigSpec.IntValue COMENDITE_HL;
        public final ForgeConfigSpec.DoubleValue COMENDITE_HARD;
        public final ForgeConfigSpec.DoubleValue COMENDITE_RESIST;
        public final ForgeConfigSpec.IntValue BLACK_DACITE_HL;
        public final ForgeConfigSpec.DoubleValue BLACK_DACITE_HARD;
        public final ForgeConfigSpec.DoubleValue BLACK_DACITE_RESIST;
        public final ForgeConfigSpec.IntValue RED_DACITE_HL;
        public final ForgeConfigSpec.DoubleValue RED_DACITE_HARD;
        public final ForgeConfigSpec.DoubleValue RED_DACITE_RESIST;
        public final ForgeConfigSpec.IntValue RED_PORPHYRY_HL;
        public final ForgeConfigSpec.DoubleValue RED_PORPHYRY_HARD;
        public final ForgeConfigSpec.DoubleValue RED_PORPHYRY_RESIST;
        public final ForgeConfigSpec.IntValue PURPLE_PORPHYRY_HL;
        public final ForgeConfigSpec.DoubleValue PURPLE_PORPHYRY_HARD;
        public final ForgeConfigSpec.DoubleValue PURPLE_PORPHYRY_RESIST;
        public final ForgeConfigSpec.IntValue PEGMATITE_HL;
        public final ForgeConfigSpec.DoubleValue PEGMATITE_HARD;
        public final ForgeConfigSpec.DoubleValue PEGMATITE_RESIST;
        public final ForgeConfigSpec.IntValue PERIDOTITE_HL;
        public final ForgeConfigSpec.DoubleValue PERIDOTITE_HARD;
        public final ForgeConfigSpec.DoubleValue PERIDOTITE_RESIST;
        public final ForgeConfigSpec.IntValue TROCTOLITE_HL;
        public final ForgeConfigSpec.DoubleValue TROCTOLITE_HARD;
        public final ForgeConfigSpec.DoubleValue TROCTOLITE_RESIST;
        public final ForgeConfigSpec.IntValue KIMBERLITE_HL;
        public final ForgeConfigSpec.DoubleValue KIMBERLITE_HARD;
        public final ForgeConfigSpec.DoubleValue KIMBERLITE_RESIST;
        public final ForgeConfigSpec.IntValue KOMATIITE_HL;
        public final ForgeConfigSpec.DoubleValue KOMATIITE_HARD;
        public final ForgeConfigSpec.DoubleValue KOMATIITE_RESIST;
        public final ForgeConfigSpec.IntValue PUMICE_HL;
        public final ForgeConfigSpec.DoubleValue PUMICE_HARD;
        public final ForgeConfigSpec.DoubleValue PUMICE_RESIST;
        public final ForgeConfigSpec.IntValue SCORIA_HL;
        public final ForgeConfigSpec.DoubleValue SCORIA_HARD;
        public final ForgeConfigSpec.DoubleValue SCORIA_RESIST;
        public final ForgeConfigSpec.IntValue WHITE_MARBLE_HL;
        public final ForgeConfigSpec.DoubleValue WHITE_MARBLE_HARD;
        public final ForgeConfigSpec.DoubleValue WHITE_MARBLE_RESIST;
        public final ForgeConfigSpec.IntValue BLACK_MARBLE_HL;
        public final ForgeConfigSpec.DoubleValue BLACK_MARBLE_HARD;
        public final ForgeConfigSpec.DoubleValue BLACK_MARBLE_RESIST;
        public final ForgeConfigSpec.IntValue GNEISS_HL;
        public final ForgeConfigSpec.DoubleValue GNEISS_HARD;
        public final ForgeConfigSpec.DoubleValue GNEISS_RESIST;
        public final ForgeConfigSpec.IntValue MICA_SCHIST_HL;
        public final ForgeConfigSpec.DoubleValue MICA_SCHIST_HARD;
        public final ForgeConfigSpec.DoubleValue MICA_SCHIST_RESIST;
        public final ForgeConfigSpec.IntValue PHYLLITE_HL;
        public final ForgeConfigSpec.DoubleValue PHYLLITE_HARD;
        public final ForgeConfigSpec.DoubleValue PHYLLITE_RESIST;
        public final ForgeConfigSpec.IntValue SLATE_HL;
        public final ForgeConfigSpec.DoubleValue SLATE_HARD;
        public final ForgeConfigSpec.DoubleValue SLATE_RESIST;
        public final ForgeConfigSpec.IntValue QUARTZITE_HL;
        public final ForgeConfigSpec.DoubleValue QUARTZITE_HARD;
        public final ForgeConfigSpec.DoubleValue QUARTZITE_RESIST;
        public final ForgeConfigSpec.IntValue MARIPOSITE_HL;
        public final ForgeConfigSpec.DoubleValue MARIPOSITE_HARD;
        public final ForgeConfigSpec.DoubleValue MARIPOSITE_RESIST;
        public final ForgeConfigSpec.IntValue SKARN_HL;
        public final ForgeConfigSpec.DoubleValue SKARN_HARD;
        public final ForgeConfigSpec.DoubleValue SKARN_RESIST;
        public final ForgeConfigSpec.IntValue RINGWOODITE_HL;
        public final ForgeConfigSpec.DoubleValue RINGWOODITE_HARD;
        public final ForgeConfigSpec.DoubleValue RINGWOODITE_RESIST;
        public final ForgeConfigSpec.IntValue WADSLEYITE_HL;
        public final ForgeConfigSpec.DoubleValue WADSLEYITE_HARD;
        public final ForgeConfigSpec.DoubleValue WADSLEYITE_RESIST;
        public final ForgeConfigSpec.IntValue BRIDGMANITE_HL;
        public final ForgeConfigSpec.DoubleValue BRIDGMANITE_HARD;
        public final ForgeConfigSpec.DoubleValue BRIDGMANITE_RESIST;
        public final ForgeConfigSpec.IntValue FERROPERICLASE_HL;
        public final ForgeConfigSpec.DoubleValue FERROPERICLASE_HARD;
        public final ForgeConfigSpec.DoubleValue FERROPERICLASE_RESIST;
        public final ForgeConfigSpec.IntValue PEROVSKITE_HL;
        public final ForgeConfigSpec.DoubleValue PEROVSKITE_HARD;
        public final ForgeConfigSpec.DoubleValue PEROVSKITE_RESIST;
        public final ForgeConfigSpec.IntValue TUFA_LIMESTONE_HL;
        public final ForgeConfigSpec.DoubleValue TUFA_LIMESTONE_HARD;
        public final ForgeConfigSpec.DoubleValue TUFA_LIMESTONE_RESIST;
        public final ForgeConfigSpec.IntValue DOLOSTONE_HL;
        public final ForgeConfigSpec.DoubleValue DOLOSTONE_HARD;
        public final ForgeConfigSpec.DoubleValue DOLOSTONE_RESIST;
        public final ForgeConfigSpec.IntValue CHALK_HL;
        public final ForgeConfigSpec.DoubleValue CHALK_HARD;
        public final ForgeConfigSpec.DoubleValue CHALK_RESIST;
        public final ForgeConfigSpec.IntValue CARBONACEOUS_SHALE_HL;
        public final ForgeConfigSpec.DoubleValue CARBONACEOUS_SHALE_HARD;
        public final ForgeConfigSpec.DoubleValue CARBONACEOUS_SHALE_RESIST;
        public final ForgeConfigSpec.IntValue SILTSTONE_HL;
        public final ForgeConfigSpec.DoubleValue SILTSTONE_HARD;
        public final ForgeConfigSpec.DoubleValue SILTSTONE_RESIST;
        public final ForgeConfigSpec.IntValue QUARTZ_SANDSTONE_HL;
        public final ForgeConfigSpec.DoubleValue QUARTZ_SANDSTONE_HARD;
        public final ForgeConfigSpec.DoubleValue QUARTZ_SANDSTONE_RESIST;
        public final ForgeConfigSpec.IntValue ARKOSE_SANDSTONE_HL;
        public final ForgeConfigSpec.DoubleValue ARKOSE_SANDSTONE_HARD;
        public final ForgeConfigSpec.DoubleValue ARKOSE_SANDSTONE_RESIST;
        public final ForgeConfigSpec.IntValue MUDSTONE_HL;
        public final ForgeConfigSpec.DoubleValue MUDSTONE_HARD;
        public final ForgeConfigSpec.DoubleValue MUDSTONE_RESIST;
        public final ForgeConfigSpec.IntValue BRECCIA_HL;
        public final ForgeConfigSpec.DoubleValue BRECCIA_HARD;
        public final ForgeConfigSpec.DoubleValue BRECCIA_RESIST;
        
        public StoneProperties(ForgeConfigSpec.Builder b) {
            b.comment("Properties of Stones").push("stoneProperties");
                b.comment("Gray Granite Properties").push("GrayGranite");
                GRAY_GRANITE_HL = b.comment("Harvest Level of Gray Granite.")
                        .defineInRange("GrayGraniteHL", 0, 0, 10);
                GRAY_GRANITE_HARD = b.comment("Hardness of Gray Granite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("GrayGraniteHardness", 2.5D, 0.0D, 100.0D);
                GRAY_GRANITE_RESIST = b.comment("Resistance of Gray Granite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("GrayGraniteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Granodiorite Properties").push("granodiorite");
                GRANODIORITE_HL = b.comment("Harvest Level of Granodiorite.")
                        .defineInRange("granodioriteHL", 0, 0, 10);
                GRANODIORITE_HARD = b.comment("Hardness of Granodiorite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("granodioriteHardness", 2.5D, 0.0D, 100.0D);
                GRANODIORITE_RESIST = b.comment("Resistance of Granodiorite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("granodioriteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Hornblende Andesite Properties").push("HornblendeAndesite");
                HORNBLENDE_ANDESITE_HL = b.comment("Harvest Level of Hornblende Andesite.")
                        .defineInRange("HornblendeAndesiteHL", 0, 0, 10);
                HORNBLENDE_ANDESITE_HARD = b.comment("Hardness of Hornblende Andesite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("HornblendeAndesiteHardness", 2.5D, 0.0D, 100.0D);
                HORNBLENDE_ANDESITE_RESIST = b.comment("Resistance of Hornblende Andesite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("HornblendeAndesiteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Tholeiitic Basalt Properties").push("TholeiiticBasalt");
                THOLEIITIC_BASALT_HL = b.comment("Harvest Level of Tholeiitic Basalt.")
                        .defineInRange("TholeiiticBasaltHL", 0, 0, 10);
                THOLEIITIC_BASALT_HARD = b.comment("Hardness of Tholeiitic Basalt (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("TholeiiticBasaltHardness", 2.5D, 0.0D, 100.0D);
                THOLEIITIC_BASALT_RESIST = b.comment("Resistance of Tholeiitic Basalt (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("TholeiiticBasaltResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Pyroxene Gabbro Properties").push("PyroxeneGabbro");
                PYROXENE_GABBRO_HL = b.comment("Harvest Level of Pyroxene Gabbro.")
                        .defineInRange("PyroxeneGabbroHL", 0, 0, 10);
                PYROXENE_GABBRO_HARD = b.comment("Hardness of Pyroxene Gabbro (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("PyroxeneGabbroHardness", 2.5D, 0.0D, 100.0D);
                PYROXENE_GABBRO_RESIST = b.comment("Resistance of Pyroxene Gabbro (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("PyroxeneGabbroResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Anorthosite Properties").push("anorthosite");
                ANORTHOSITE_HL = b.comment("Harvest Level of Anorthosite.")
                        .defineInRange("anorthositeHL", 0, 0, 10);
                ANORTHOSITE_HARD = b.comment("Hardness of Anorthosite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("anorthositeHardness", 2.5D, 0.0D, 100.0D);
                ANORTHOSITE_RESIST = b.comment("Resistance of Anorthosite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("anorthositeResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Rhyolite Properties").push("rhyolite");
                RHYOLITE_HL = b.comment("Harvest Level of Rhyolite.")
                        .defineInRange("rhyoliteHL", 0, 0, 10);
                RHYOLITE_HARD = b.comment("Hardness of Rhyolite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("rhyoliteHardness", 2.5D, 0.0D, 100.0D);
                RHYOLITE_RESIST = b.comment("Resistance of Rhyolite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("rhyoliteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Comendite Properties").push("comendite");
                COMENDITE_HL = b.comment("Harvest Level of Comendite.")
                        .defineInRange("comenditeHL", 0, 0, 10);
                COMENDITE_HARD = b.comment("Hardness of Rhyolite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("comenditeHardness", 2.5D, 0.0D, 100.0D);
                COMENDITE_RESIST = b.comment("Resistance of Rhyolite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("comenditeResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Black Dacite Properties").push("BlackDacite");
                BLACK_DACITE_HL = b.comment("Harvest Level of Black Dacite.")
                        .defineInRange("BlackDaciteHL", 0, 0, 10);
                BLACK_DACITE_HARD = b.comment("Hardness of Black Dacite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("BlackDaciteHardness", 2.5D, 0.0D, 100.0D);
                BLACK_DACITE_RESIST = b.comment("Resistance of Black Dacite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("BlackDaciteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Red Dacite Properties").push("RedDacite");
                RED_DACITE_HL = b.comment("Harvest Level of Red Dacite.")
                        .defineInRange("RedDaciteHL", 0, 0, 10);
                RED_DACITE_HARD = b.comment("Hardness of Red Dacite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("RedDaciteHardness", 2.5D, 0.0D, 100.0D);
                RED_DACITE_RESIST = b.comment("Resistance of Red Dacite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("RedDaciteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Red Porphyry Properties").push("RedPorphyry");
                RED_PORPHYRY_HL = b.comment("Harvest Level of Red Porphyry.")
                        .defineInRange("RedPorphyryHL", 0, 0, 10);
                RED_PORPHYRY_HARD = b.comment("Hardness of Red Porphyry (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("RedPorphyryHardness", 2.5D, 0.0D, 100.0D);
                RED_PORPHYRY_RESIST = b.comment("Resistance of Red Porphyry (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("RedPorphyryResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Purple Porphyry Properties").push("PurplePorphyry");
                PURPLE_PORPHYRY_HL = b.comment("Harvest Level of Purple Porphyry.")
                        .defineInRange("PurplePorphyryHL", 0, 0, 10);
                PURPLE_PORPHYRY_HARD = b.comment("Hardness of Purple Porphyry (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("PurplePorphyryHardness", 2.5D, 0.0D, 100.0D);
                PURPLE_PORPHYRY_RESIST = b.comment("Resistance of Purple Porphyry (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("PurplePorphyryResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Pegmatite Properties").push("pegmatite");
                PEGMATITE_HL = b.comment("Harvest Level of Pegmatite.")
                        .defineInRange("pegmatiteHL", 0, 0, 10);
                PEGMATITE_HARD = b.comment("Hardness of Pegmatite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("pegmatiteHardness", 2.5D, 0.0D, 100.0D);
                PEGMATITE_RESIST = b.comment("Resistance of Pegmatite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("pegmatiteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Peridotite Properties").push("peridotite");
                PERIDOTITE_HL = b.comment("Harvest Level of Peridotite.")
                        .defineInRange("peridotiteHL", 0, 0, 10);
                PERIDOTITE_HARD = b.comment("Hardness of Peridotite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("peridotiteHardness", 2.5D, 0.0D, 100.0D);
                PERIDOTITE_RESIST = b.comment("Resistance of Peridotite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("peridotiteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Troctolite Properties").push("troctolite");
                TROCTOLITE_HL = b.comment("Harvest Level of Troctolite.")
                        .defineInRange("troctoliteHL", 0, 0, 10);
                TROCTOLITE_HARD = b.comment("Hardness of Troctolite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("troctoliteHardness", 2.5D, 0.0D, 100.0D);
                TROCTOLITE_RESIST = b.comment("Resistance of Troctolite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("troctoliteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Kimberlite Properties").push("kimberlite");
                KIMBERLITE_HL = b.comment("Harvest Level of Kimberlite.")
                        .defineInRange("kimberliteHL", 0, 0, 10);
                KIMBERLITE_HARD = b.comment("Hardness of Kimberlite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("kimberliteHardness", 2.5D, 0.0D, 100.0D);
                KIMBERLITE_RESIST = b.comment("Resistance of Kimberlite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("kimberliteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Komatiite Properties").push("komatiite");
                KOMATIITE_HL = b.comment("Harvest Level of Komatiite.")
                        .defineInRange("komatiiteHL", 0, 0, 10);
                KOMATIITE_HARD = b.comment("Hardness of Komatiite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("komatiiteHardness", 2.5D, 0.0D, 100.0D);
                KOMATIITE_RESIST = b.comment("Resistance of Komatiite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("komatiiteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Pumice Properties").push("pumice");
                PUMICE_HL = b.comment("Harvest Level of Pumice.")
                        .defineInRange("pumiceHL", 0, 0, 10);
                PUMICE_HARD = b.comment("Hardness of Pumice (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("pumiceHardness", 2.5D, 0.0D, 100.0D);
                PUMICE_RESIST = b.comment("Resistance of Pumice (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("pumiceResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Scoria Properties").push("scoria");
                SCORIA_HL = b.comment("Harvest Level of Scoria.")
                        .defineInRange("scoriaHL", 0, 0, 10);
                SCORIA_HARD = b.comment("Hardness of Scoria (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("scoriaHardness", 2.5D, 0.0D, 100.0D);
                SCORIA_RESIST = b.comment("Resistance of Scoria (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("scoriaResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("White Marble Properties").push("WhiteMarble");
                WHITE_MARBLE_HL = b.comment("Harvest Level of White Marble.")
                        .defineInRange("WhiteMarbleHL", 0, 0, 10);
                WHITE_MARBLE_HARD = b.comment("Hardness of White Marble (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("WhiteMarbleHardness", 2.5D, 0.0D, 100.0D);
                WHITE_MARBLE_RESIST = b.comment("Resistance of White Marble (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("WhiteMarbleResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Black Marble Properties").push("BlackMarble");
                BLACK_MARBLE_HL = b.comment("Harvest Level of Black Marble.")
                        .defineInRange("BlackMarbleHL", 0, 0, 10);
                BLACK_MARBLE_HARD = b.comment("Hardness of Black Marble (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("BlackMarbleHardness", 2.5D, 0.0D, 100.0D);
                BLACK_MARBLE_RESIST = b.comment("Resistance of Black Marble (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("BlackMarbleResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Gneiss Properties").push("gneiss");
                GNEISS_HL = b.comment("Harvest Level of Gneiss.")
                        .defineInRange("gneissHL", 0, 0, 10);
                GNEISS_HARD = b.comment("Hardness of Gneiss (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("gneissHardness", 2.5D, 0.0D, 100.0D);
                GNEISS_RESIST = b.comment("Resistance of Gneiss (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("gneissResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Mica Schist Properties").push("MicaSchist");
                MICA_SCHIST_HL = b.comment("Harvest Level of Mica Schist.")
                        .defineInRange("MicaSchistHL", 0, 0, 10);
                MICA_SCHIST_HARD = b.comment("Hardness of Mica Schist (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("MicaSchistHardness", 2.5D, 0.0D, 100.0D);
                MICA_SCHIST_RESIST = b.comment("Resistance of Mica Schist (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("MicaSchistResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Phyllite Properties").push("phyllite");
                PHYLLITE_HL = b.comment("Harvest Level of Phyllite.")
                        .defineInRange("phylliteHL", 0, 0, 10);
                PHYLLITE_HARD = b.comment("Hardness of Phyllite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("phylliteHardness", 2.5D, 0.0D, 100.0D);
                PHYLLITE_RESIST = b.comment("Resistance of Phyllite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("phylliteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Slate Properties").push("slate");
                SLATE_HL = b.comment("Harvest Level of Slate.")
                        .defineInRange("slateHL", 0, 0, 10);
                SLATE_HARD = b.comment("Hardness of Slate (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("slateHardness", 2.5D, 0.0D, 100.0D);
                SLATE_RESIST = b.comment("Resistance of Slate (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("slateResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Quartzite Properties").push("quartzite");
                QUARTZITE_HL = b.comment("Harvest Level of Quartzite.")
                        .defineInRange("quartziteHL", 0, 0, 10);
                QUARTZITE_HARD = b.comment("Hardness of Quartzite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("quartziteHardness", 2.5D, 0.0D, 100.0D);
                QUARTZITE_RESIST = b.comment("Resistance of Quartzite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("quartziteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Mariposite Properties").push("mariposite");
                MARIPOSITE_HL = b.comment("Harvest Level of Mariposite.")
                        .defineInRange("maripositeHL", 0, 0, 10);
                MARIPOSITE_HARD = b.comment("Hardness of Mariposite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("maripositeHardness", 2.5D, 0.0D, 100.0D);
                MARIPOSITE_RESIST = b.comment("Resistance of Mariposite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("maripositeResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Skarn Properties").push("skarn");
                SKARN_HL = b.comment("Harvest Level of Skarn.")
                        .defineInRange("skarnHL", 0, 0, 10);
                SKARN_HARD = b.comment("Hardness of Skarn (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("skarnHardness", 2.5D, 0.0D, 100.0D);
                SKARN_RESIST = b.comment("Resistance of Skarn (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("skarnResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Ringwoodite Properties").push("ringwoodite");
                RINGWOODITE_HL = b.comment("Harvest Level of Ringwoodite.")
                        .defineInRange("ringwooditeHL", 0, 0, 10);
                RINGWOODITE_HARD = b.comment("Hardness of Ringwoodite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("ringwooditeHardness", 2.5D, 0.0D, 100.0D);
                RINGWOODITE_RESIST = b.comment("Resistance of Ringwoodite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("ringwooditeResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Wadsleyite Properties").push("wadsleyite");
                WADSLEYITE_HL = b.comment("Harvest Level of Wadsleyite.")
                        .defineInRange("wadsleyiteHL", 0, 0, 10);
                WADSLEYITE_HARD = b.comment("Hardness of Wadsleyite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("wadsleyiteHardness", 2.5D, 0.0D, 100.0D);
                WADSLEYITE_RESIST = b.comment("Resistance of Wadsleyite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("wadsleyiteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Bridgmanite Properties").push("bridgmanite");
                BRIDGMANITE_HL = b.comment("Harvest Level of Bridgmanite.")
                        .defineInRange("bridgmaniteHL", 0, 0, 10);
                BRIDGMANITE_HARD = b.comment("Hardness of Bridgmanite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("bridgmaniteHardness", 2.5D, 0.0D, 100.0D);
                BRIDGMANITE_RESIST = b.comment("Resistance of Bridgmanite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("bridgmaniteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Ferropericlase Properties").push("ferropericlase");
                FERROPERICLASE_HL = b.comment("Harvest Level of Ferropericlase.")
                        .defineInRange("ferropericlaseHL", 0, 0, 10);
                FERROPERICLASE_HARD = b.comment("Hardness of Ferropericlase (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("ferropericlaseHardness", 2.5D, 0.0D, 100.0D);
                FERROPERICLASE_RESIST = b.comment("Resistance of Ferropericlase (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("ferropericlaseResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Perovskite Properties").push("perovskite");
                PEROVSKITE_HL = b.comment("Harvest Level of Perovskite.")
                        .defineInRange("perovskiteHL", 0, 0, 10);
                PEROVSKITE_HARD = b.comment("Hardness of Perovskite (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("perovskiteHardness", 2.5D, 0.0D, 100.0D);
                PEROVSKITE_RESIST = b.comment("Resistance of Perovskite (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("perovskiteResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Tufa Limestone Properties").push("TufaLimestone");
                TUFA_LIMESTONE_HL = b.comment("Harvest Level of Tufa Limestone.")
                        .defineInRange("TufaLimestoneHL", 0, 0, 10);
                TUFA_LIMESTONE_HARD = b.comment("Hardness of Tufa Limestone (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("TufaLimestoneHardness", 1.5D, 0.0D, 100.0D);
                TUFA_LIMESTONE_RESIST = b.comment("Resistance of Tufa Limestone (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("TufaLimestoneResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Dolostone Properties").push("dolostone");
                DOLOSTONE_HL = b.comment("Harvest Level of Dolostone.")
                        .defineInRange("dolostoneHL", 0, 0, 10);
                DOLOSTONE_HARD = b.comment("Hardness of Dolostone (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("dolostoneHardness", 1.5D, 0.0D, 100.0D);
                DOLOSTONE_RESIST = b.comment("Resistance of Dolostone (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("dolostoneResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Chalk Properties").push("chalk");
                CHALK_HL = b.comment("Harvest Level of Chalk.")
                        .defineInRange("chalkHL", 0, 0, 10);
                CHALK_HARD = b.comment("Hardness of Chalk (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("chalkHardness", 1.5D, 0.0D, 100.0D);
                CHALK_RESIST = b.comment("Resistance of Chalk (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("chalkResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Carbonaceous Shale Properties").push("CarbonaceousShale");
                CARBONACEOUS_SHALE_HL = b.comment("Harvest Level of Carbonaceous Shale.")
                        .defineInRange("CarbonaceousShaleHL", 0, 0, 10);
                CARBONACEOUS_SHALE_HARD = b.comment("Hardness of Carbonaceous Shale (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("CarbonaceousShaleHardness", 1.5D, 0.0D, 100.0D);
                CARBONACEOUS_SHALE_RESIST = b.comment("Resistance of Carbonaceous Shale (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("CarbonaceousShaleResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Siltstone Properties").push("siltstone");
                SILTSTONE_HL = b.comment("Harvest Level of Siltstone.")
                        .defineInRange("siltstoneHL", 0, 0, 10);
                SILTSTONE_HARD = b.comment("Hardness of Siltstone (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("siltstoneHardness", 1.5D, 0.0D, 100.0D);
                SILTSTONE_RESIST = b.comment("Resistance of Siltstone (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("siltstoneResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Quartz Sandstone Properties").push("QuartzSandstone");
                QUARTZ_SANDSTONE_HL = b.comment("Harvest Level of Quartz Sandstone.")
                        .defineInRange("QuartzSandstoneHL", 0, 0, 10);
                QUARTZ_SANDSTONE_HARD = b.comment("Hardness of Quartz Sandstone (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("QuartzSandstoneHardness", 1.5D, 0.0D, 100.0D);
                QUARTZ_SANDSTONE_RESIST = b.comment("Resistance of Quartz Sandstone (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("QuartzSandstoneResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Arkose Sandstone Properties").push("ArkoseSandstone");
                ARKOSE_SANDSTONE_HL = b.comment("Harvest Level of Arkose Sandstone.")
                        .defineInRange("ArkoseSandstoneHL", 0, 0, 10);
                ARKOSE_SANDSTONE_HARD = b.comment("Hardness of Arkose Sandstone (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("ArkoseSandstoneHardness", 1.5D, 0.0D, 100.0D);
                ARKOSE_SANDSTONE_RESIST = b.comment("Resistance of Arkose Sandstone (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("ArkoseSandstoneResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Mudstone Properties").push("mudstone");
                MUDSTONE_HL = b.comment("Harvest Level of Mudstone.")
                        .defineInRange("mudstoneHL", 0, 0, 10);
                MUDSTONE_HARD = b.comment("Hardness of Mudstone (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("mudstoneHardness", 1.5D, 0.0D, 100.0D);
                MUDSTONE_RESIST = b.comment("Resistance of Mudstone (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("mudstoneResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
                b.comment("Breccia Properties").push("breccia");
                BRECCIA_HL = b.comment("Harvest Level of Breccia.")
                        .defineInRange("brecciaHL", 0, 0, 10);
                BRECCIA_HARD = b.comment("Hardness of Breccia (Obsidian is 50 and vanilla stone is 2).")
                        .defineInRange("brecciaHardness", 1.5D, 0.0D, 100.0D);
                BRECCIA_RESIST = b.comment("Resistance of Breccia (Obsidian is 1200 and vanilla stone is 6).")
                        .defineInRange("brecciaResistance", 6.0D, 0.00D, 2000.0D);
                b.pop();
            b.pop();
        }
    }

    public static class Gases {
        public final ForgeConfigSpec.EnumValue<GasUtilsEnum> GAS_VENT_TYPE;
        public final ForgeConfigSpec.BooleanValue GAS_MOVEMENT;
        public final ForgeConfigSpec.BooleanValue ENABLE_GAS_VENTS;
        public final ForgeConfigSpec.BooleanValue GAS_AFFECT_UNDEAD;
        public final ForgeConfigSpec.IntValue GAS_DISSIPATION_SPEED;


        public Gases(ForgeConfigSpec.Builder b) {
            b.comment("Settings for Gases.").push("gases");
            GAS_MOVEMENT = b.comment("If enabled, gases will move on random tick and dissipate at or above y-level 95 (EXPERIMENTAL).")
                    .define("gasMovement", false);
            GAS_AFFECT_UNDEAD = b.comment("If enabled, gas effects will work against undead mobs.")
                    .define("gasAffectUndead", true);
            ENABLE_GAS_VENTS = b.comment("Enables blocks which emit gases on random tick.")
                    .define("enableGasVents", true);
            GAS_VENT_TYPE = b.comment("Defines the gas that will be released by the Gas Vent block. Do not use SET and RANDOM, as these will break the block.")
                    .defineEnum("gasVentType",GasUtilsEnum.FLUORINE);
            GAS_DISSIPATION_SPEED = b.comment("Defines the number of random ticks until a gas block dissipates. Setting to 0 means it never dissipates.")
                    .defineInRange("gasDissipationSpeed",8,0,16);

            b.pop();
        }
    }

    public static class HardMode {
        public final ForgeConfigSpec.BooleanValue WATER_REACTIVE;
        public final ForgeConfigSpec.BooleanValue RADIOACTIVE;


        public HardMode(ForgeConfigSpec.Builder b) {
            b.comment("Settings for Hard Mode mechanics (HIGHLY EXPERIMENTAL).").push("hardMode");
            WATER_REACTIVE = b.comment("If enabled, certain elements will react with water. Generally creates an explosion.")
                    .define("elementWaterReactive", false);
            RADIOACTIVE = b.comment("If enabled, certain elements will be radioactive which applies a radiation potion effect that causes damage over time.")
                    .define("elementRadioactive", false);
            b.pop();
        }
    }

    

    public static final ForgeConfigSpec COMMON_CONFIG;
    public static final General GENERAL;
    public static final Machines MACHINES;
    public static final Alloys ALLOYS;
    public static final StoneProperties STONE_PROPERTIES;
    public static final HardMode HARD_MODE;
    public static final Gases GASES;

    static {
        ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
        GENERAL = new General(BUILDER);
        MACHINES = new Machines(BUILDER);
        ALLOYS = new Alloys(BUILDER);
        STONE_PROPERTIES = new StoneProperties(BUILDER);
        GASES = new Gases(BUILDER);
        HARD_MODE = new HardMode(BUILDER);


        COMMON_CONFIG = BUILDER.build();
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {

    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
    }


}
