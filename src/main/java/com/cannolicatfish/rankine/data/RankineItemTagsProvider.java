package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.*;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class RankineItemTagsProvider extends ItemTagsProvider {

    public RankineItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagProvider, ProjectRankine.MODID, existingFileHelper);
    }

    public String getName() {
        return "Project Rankine - Item Tags";
    }

    @Override
    protected void registerTags() {
        //MINECRAFT
        getOrCreateBuilder(ItemTags.ARROWS).add(RankineItems.ROPE_COIL_ARROW.get(),RankineItems.THORIUM_ARROW.get(),RankineItems.MAGNESIUM_ARROW.get(),RankineItems.ALLOY_ARROW.get());
        //getOrCreateBuilder(EntityTypeTags.ARROWS).add(RankineEntityTypes.THORIUM_ARROW,RankineEntityTypes.MAGNESIUM_ARROW,RankineEntityTypes.ALLOY_ARROW);
        getOrCreateBuilder(ItemTags.COALS).add(RankineItems.LIGNITE.get(),RankineItems.SUBBITUMINOUS_COAL.get(),RankineItems.BITUMINOUS_COAL.get(),RankineItems.ANTHRACITE_COAL.get());
        getOrCreateBuilder(ItemTags.STONE_TOOL_MATERIALS).add(RankineItems.SKARN.get(),RankineItems.BRECCIA.get());
        getOrCreateBuilder(ItemTags.PIGLIN_LOVED).addTag(RankineTags.Items.COLORED_GOLD_TOOLS).add(RankineItems.GREEN_GOLD_ALLOY.get(),RankineItems.BLUE_GOLD_ALLOY.get(),RankineItems.ROSE_GOLD_ALLOY.get(),RankineItems.WHITE_GOLD_ALLOY.get(),RankineItems.BLACK_GOLD_ALLOY.get(),RankineItems.PURPLE_GOLD_ALLOY.get(),RankineItems.GREEN_GOLD_BLOCK.get(),RankineItems.BLUE_GOLD_BLOCK.get(),RankineItems.ROSE_GOLD_BLOCK.get(),RankineItems.WHITE_GOLD_BLOCK.get(),RankineItems.BLACK_GOLD_BLOCK.get(),RankineItems.PURPLE_GOLD_BLOCK.get());
        for (Block blk : RankineLists.SAPLINGS) {
            getOrCreateBuilder(ItemTags.SAPLINGS).add(blk.asItem());
        }



        //MOD
        copy(RankineTags.Blocks.CEDAR_LOGS, RankineTags.Items.CEDAR_LOGS);
        copy(RankineTags.Blocks.PINYON_PINE_LOGS, RankineTags.Items.PINYON_PINE_LOGS);
        copy(RankineTags.Blocks.JUNIPER_LOGS, RankineTags.Items.JUNIPER_LOGS);
        copy(RankineTags.Blocks.COCONUT_PALM_LOGS, RankineTags.Items.COCONUT_PALM_LOGS);
        copy(RankineTags.Blocks.BALSAM_FIR_LOGS, RankineTags.Items.BALSAM_FIR_LOGS);
        copy(RankineTags.Blocks.EASTERN_HEMLOCK_LOGS, RankineTags.Items.EASTERN_HEMLOCK_LOGS);
        copy(RankineTags.Blocks.MAGNOLIA_LOGS, RankineTags.Items.MAGNOLIA_LOGS);
        copy(RankineTags.Blocks.MAPLE_LOGS, RankineTags.Items.MAPLE_LOGS);
        copy(RankineTags.Blocks.BLACK_BIRCH_LOGS, RankineTags.Items.BLACK_BIRCH_LOGS);
        copy(RankineTags.Blocks.YELLOW_BIRCH_LOGS, RankineTags.Items.YELLOW_BIRCH_LOGS);
        copy(RankineTags.Blocks.SHARINGA_LOGS, RankineTags.Items.SHARINGA_LOGS);
        copy(RankineTags.Blocks.CORK_OAK_LOGS, RankineTags.Items.CORK_OAK_LOGS);
        copy(RankineTags.Blocks.BLACK_WALNUT_LOGS, RankineTags.Items.BLACK_WALNUT_LOGS);
        copy(RankineTags.Blocks.CINNAMON_LOGS, RankineTags.Items.CINNAMON_LOGS);
        copy(RankineTags.Blocks.PETRIFIED_CHORUS_LOGS, RankineTags.Items.PETRIFIED_CHORUS_LOGS);
        copy(RankineTags.Blocks.CHARRED_LOGS, RankineTags.Items.CHARRED_LOGS);
        copy(RankineTags.Blocks.ERYTHRINA_LOGS, RankineTags.Items.ERYTHRINA_LOGS);

        getOrCreateBuilder(RankineTags.Items.CRAFTING_METAL_NUGGETS).addTag(Tags.Items.NUGGETS_IRON).addTag(RankineTags.Items.NUGGETS_ALUMINUM).addTag(RankineTags.Items.NUGGETS_COBALT).addTag(RankineTags.Items.NUGGETS_MANGANESE).addTag(RankineTags.Items.NUGGETS_TITANIUM).addTag(RankineTags.Items.NUGGETS_BISMUTH).addTag(RankineTags.Items.NUGGETS_LEAD).addTag(RankineTags.Items.NUGGETS_NICKEL).addTag(RankineTags.Items.NUGGETS_TUNGSTEN);
        getOrCreateBuilder(RankineTags.Items.CRAFTING_METAL_INGOTS).addTag(Tags.Items.INGOTS_IRON).addTag(RankineTags.Items.INGOTS_ALUMINUM).addTag(RankineTags.Items.INGOTS_COBALT).addTag(RankineTags.Items.INGOTS_MANGANESE).addTag(RankineTags.Items.INGOTS_TITANIUM).addTag(RankineTags.Items.INGOTS_BISMUTH).addTag(RankineTags.Items.INGOTS_LEAD).addTag(RankineTags.Items.INGOTS_NICKEL).addTag(RankineTags.Items.INGOTS_TUNGSTEN);
        getOrCreateBuilder(RankineTags.Items.CRAFTING_METAL_BLOCKS).addTag(Tags.Items.STORAGE_BLOCKS_IRON).addTag(RankineTags.Items.STORAGE_BLOCKS_ALUMINUM).addTag(RankineTags.Items.STORAGE_BLOCKS_COBALT).addTag(RankineTags.Items.STORAGE_BLOCKS_MANGANESE).addTag(RankineTags.Items.STORAGE_BLOCKS_TITANIUM).addTag(RankineTags.Items.STORAGE_BLOCKS_BISMUTH).addTag(RankineTags.Items.STORAGE_BLOCKS_LEAD).addTag(RankineTags.Items.STORAGE_BLOCKS_NICKEL).addTag(RankineTags.Items.STORAGE_BLOCKS_TUNGSTEN);


        getOrCreateBuilder(RankineTags.Items.FORAGING_ITEMS).add(Items.POTATO,Items.CARROT).addTag(Tags.Items.SEEDS);
        getOrCreateBuilder(RankineTags.Items.COLORED_GOLD_TOOLS).addTags(RankineTags.Items.ROSE_GOLD_TOOLS,RankineTags.Items.GREEN_GOLD_TOOLS,RankineTags.Items.WHITE_GOLD_TOOLS,RankineTags.Items.BLUE_GOLD_TOOLS,RankineTags.Items.PURPLE_GOLD_TOOLS,RankineTags.Items.BLACK_GOLD_TOOLS);
        getOrCreateBuilder(RankineTags.Items.FORAGING_TOOLS).addTags(RankineTags.Items.COLORED_GOLD_TOOLS,RankineTags.Items.FLINT_TOOLS,RankineTags.Items.PEWTER_TOOLS,RankineTags.Items.BRONZE_TOOLS,RankineTags.Items.INVAR_TOOLS,RankineTags.Items.AMALGAM_TOOLS,RankineTags.Items.ALLOY_TOOLS);



        //FORGE
        getOrCreateBuilder(RankineTags.Items.WOODEN_TOOLS).add(Items.WOODEN_AXE).add(Items.WOODEN_PICKAXE).add(Items.WOODEN_HOE).add(Items.WOODEN_SHOVEL).add(Items.WOODEN_SWORD);
        getOrCreateBuilder(RankineTags.Items.STONE_TOOLS).add(Items.STONE_AXE).add(Items.STONE_PICKAXE).add(Items.STONE_HOE).add(Items.STONE_SHOVEL).add(Items.STONE_SWORD);
        getOrCreateBuilder(RankineTags.Items.IRON_TOOLS).add(Items.IRON_AXE).add(Items.IRON_PICKAXE).add(Items.IRON_HOE).add(Items.IRON_SHOVEL).add(Items.IRON_SWORD);
        getOrCreateBuilder(RankineTags.Items.GOLDEN_TOOLS).add(Items.GOLDEN_AXE).add(Items.GOLDEN_PICKAXE).add(Items.GOLDEN_HOE).add(Items.GOLDEN_SHOVEL).add(Items.GOLDEN_SWORD);
        getOrCreateBuilder(RankineTags.Items.DIAMOND_TOOLS).add(Items.DIAMOND_AXE).add(Items.DIAMOND_PICKAXE).add(Items.DIAMOND_HOE).add(Items.DIAMOND_SHOVEL).add(Items.DIAMOND_SWORD);
        getOrCreateBuilder(RankineTags.Items.NETHERITE_TOOLS).add(Items.NETHERITE_AXE).add(Items.NETHERITE_PICKAXE).add(Items.NETHERITE_HOE).add(Items.NETHERITE_SHOVEL).add(Items.NETHERITE_SWORD);
        getOrCreateBuilder(RankineTags.Items.ALLOY_TOOLS).add(RankineItems.ALLOY_AXE.get(),RankineItems.ALLOY_PICKAXE.get(),RankineItems.ALLOY_HOE.get(),RankineItems.ALLOY_SHOVEL.get(),RankineItems.ALLOY_SWORD.get(),RankineItems.ALLOY_HAMMER.get(),RankineItems.ALLOY_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.FLINT_TOOLS).add(RankineItems.FLINT_AXE.get(),RankineItems.FLINT_PICKAXE.get(),RankineItems.FLINT_HOE.get(),RankineItems.FLINT_SHOVEL.get(),RankineItems.FLINT_KNIFE.get(),RankineItems.FLINT_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.PEWTER_TOOLS).add(RankineItems.PEWTER_AXE.get(),RankineItems.PEWTER_PICKAXE.get(),RankineItems.PEWTER_HOE.get(),RankineItems.PEWTER_SHOVEL.get(),RankineItems.PEWTER_SWORD.get(),RankineItems.PEWTER_HAMMER.get(),RankineItems.PEWTER_SPEAR.get(),RankineItems.PEWTER_KNIFE.get());
        getOrCreateBuilder(RankineTags.Items.BRONZE_TOOLS).add(RankineItems.BRONZE_AXE.get(),RankineItems.BRONZE_PICKAXE.get(),RankineItems.BRONZE_HOE.get(),RankineItems.BRONZE_SHOVEL.get(),RankineItems.BRONZE_SWORD.get(),RankineItems.BRONZE_HAMMER.get(),RankineItems.BRONZE_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.INVAR_TOOLS).add(RankineItems.INVAR_AXE.get(),RankineItems.INVAR_PICKAXE.get(),RankineItems.INVAR_HOE.get(),RankineItems.INVAR_SHOVEL.get(),RankineItems.INVAR_SWORD.get(),RankineItems.INVAR_HAMMER.get(),RankineItems.INVAR_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.STEEL_TOOLS).add(RankineItems.STEEL_AXE.get(),RankineItems.STEEL_PICKAXE.get(),RankineItems.STEEL_HOE.get(),RankineItems.STEEL_SHOVEL.get(),RankineItems.STEEL_SWORD.get(),RankineItems.STEEL_HAMMER.get(),RankineItems.STEEL_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.STAINLESS_STEEL_TOOLS).add(RankineItems.STAINLESS_STEEL_AXE.get(),RankineItems.STAINLESS_STEEL_PICKAXE.get(),RankineItems.STAINLESS_STEEL_HOE.get(),RankineItems.STAINLESS_STEEL_SHOVEL.get(),RankineItems.STAINLESS_STEEL_SWORD.get(),RankineItems.STAINLESS_STEEL_HAMMER.get(),RankineItems.STAINLESS_STEEL_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.NICKEL_SUPERALLOY_TOOLS).add(RankineItems.NICKEL_SUPERALLOY_AXE.get(),RankineItems.NICKEL_SUPERALLOY_PICKAXE.get(),RankineItems.NICKEL_SUPERALLOY_HOE.get(),RankineItems.NICKEL_SUPERALLOY_SHOVEL.get(),RankineItems.NICKEL_SUPERALLOY_SWORD.get(),RankineItems.NICKEL_SUPERALLOY_HAMMER.get(),RankineItems.NICKEL_SUPERALLOY_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.COBALT_SUPERALLOY_TOOLS).add(RankineItems.COBALT_SUPERALLOY_AXE.get(),RankineItems.COBALT_SUPERALLOY_PICKAXE.get(),RankineItems.COBALT_SUPERALLOY_HOE.get(),RankineItems.COBALT_SUPERALLOY_SHOVEL.get(),RankineItems.COBALT_SUPERALLOY_SWORD.get(),RankineItems.COBALT_SUPERALLOY_HAMMER.get(),RankineItems.COBALT_SUPERALLOY_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.TUNGSTEN_HEAVY_ALLOY_TOOLS).add(RankineItems.TUNGSTEN_HEAVY_ALLOY_AXE.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_PICKAXE.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_HOE.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_SHOVEL.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_SWORD.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_HAMMER.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.AMALGAM_TOOLS).add(RankineItems.AMALGAM_AXE.get(),RankineItems.AMALGAM_PICKAXE.get(),RankineItems.AMALGAM_HOE.get(),RankineItems.AMALGAM_SHOVEL.get(),RankineItems.AMALGAM_SWORD.get(),RankineItems.AMALGAM_HAMMER.get(),RankineItems.AMALGAM_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.ROSE_GOLD_TOOLS).add(RankineItems.ROSE_GOLD_AXE.get(),RankineItems.ROSE_GOLD_PICKAXE.get(),RankineItems.ROSE_GOLD_HOE.get(),RankineItems.ROSE_GOLD_SHOVEL.get(),RankineItems.ROSE_GOLD_SWORD.get(),RankineItems.ROSE_GOLD_HAMMER.get(),RankineItems.ROSE_GOLD_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.GREEN_GOLD_TOOLS).add(RankineItems.GREEN_GOLD_AXE.get(),RankineItems.GREEN_GOLD_PICKAXE.get(),RankineItems.GREEN_GOLD_HOE.get(),RankineItems.GREEN_GOLD_SHOVEL.get(),RankineItems.GREEN_GOLD_SWORD.get(),RankineItems.GREEN_GOLD_HAMMER.get(),RankineItems.GREEN_GOLD_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.BLUE_GOLD_TOOLS).add(RankineItems.BLUE_GOLD_AXE.get(),RankineItems.BLUE_GOLD_PICKAXE.get(),RankineItems.BLUE_GOLD_HOE.get(),RankineItems.BLUE_GOLD_SHOVEL.get(),RankineItems.BLUE_GOLD_SWORD.get(),RankineItems.BLUE_GOLD_HAMMER.get(),RankineItems.BLUE_GOLD_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.WHITE_GOLD_TOOLS).add(RankineItems.WHITE_GOLD_AXE.get(),RankineItems.WHITE_GOLD_PICKAXE.get(),RankineItems.WHITE_GOLD_HOE.get(),RankineItems.WHITE_GOLD_SHOVEL.get(),RankineItems.WHITE_GOLD_SWORD.get(),RankineItems.WHITE_GOLD_HAMMER.get(),RankineItems.WHITE_GOLD_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.PURPLE_GOLD_TOOLS).add(RankineItems.PURPLE_GOLD_AXE.get(),RankineItems.PURPLE_GOLD_PICKAXE.get(),RankineItems.PURPLE_GOLD_HOE.get(),RankineItems.PURPLE_GOLD_SHOVEL.get(),RankineItems.PURPLE_GOLD_SWORD.get(),RankineItems.PURPLE_GOLD_HAMMER.get(),RankineItems.PURPLE_GOLD_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.BLACK_GOLD_TOOLS).add(RankineItems.BLACK_GOLD_AXE.get(),RankineItems.BLACK_GOLD_PICKAXE.get(),RankineItems.BLACK_GOLD_HOE.get(),RankineItems.BLACK_GOLD_SHOVEL.get(),RankineItems.BLACK_GOLD_SWORD.get(),RankineItems.BLACK_GOLD_HAMMER.get(),RankineItems.BLACK_GOLD_SPEAR.get());

        getOrCreateBuilder(RankineTags.Items.AXES).add(RankineItems.FLINT_AXE.get(),RankineItems.PEWTER_AXE.get(),RankineItems.ALLOY_AXE.get(),RankineItems.BRONZE_AXE.get(),RankineItems.INVAR_AXE.get(),RankineItems.STEEL_AXE.get(),RankineItems.STAINLESS_STEEL_AXE.get(),RankineItems.NICKEL_SUPERALLOY_AXE.get(),RankineItems.COBALT_SUPERALLOY_AXE.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_AXE.get(),RankineItems.ROSE_GOLD_AXE.get(),RankineItems.BLUE_GOLD_AXE.get(),RankineItems.GREEN_GOLD_AXE.get(),RankineItems.WHITE_GOLD_AXE.get(),RankineItems.PURPLE_GOLD_AXE.get(),RankineItems.BLACK_GOLD_AXE.get(),RankineItems.AMALGAM_AXE.get());
        getOrCreateBuilder(RankineTags.Items.PICKAXES).add(RankineItems.FLINT_PICKAXE.get(),RankineItems.PEWTER_PICKAXE.get(),RankineItems.ALLOY_PICKAXE.get(),RankineItems.BRONZE_PICKAXE.get(),RankineItems.INVAR_PICKAXE.get(),RankineItems.STEEL_PICKAXE.get(),RankineItems.STAINLESS_STEEL_PICKAXE.get(),RankineItems.NICKEL_SUPERALLOY_PICKAXE.get(),RankineItems.COBALT_SUPERALLOY_PICKAXE.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_PICKAXE.get(),RankineItems.ROSE_GOLD_PICKAXE.get(),RankineItems.BLUE_GOLD_PICKAXE.get(),RankineItems.GREEN_GOLD_PICKAXE.get(),RankineItems.WHITE_GOLD_PICKAXE.get(),RankineItems.PURPLE_GOLD_PICKAXE.get(),RankineItems.BLACK_GOLD_PICKAXE.get(),RankineItems.AMALGAM_PICKAXE.get());
        getOrCreateBuilder(RankineTags.Items.HOES).add(RankineItems.FLINT_HOE.get(),RankineItems.PEWTER_HOE.get(),RankineItems.ALLOY_HOE.get(),RankineItems.BRONZE_HOE.get(),RankineItems.INVAR_HOE.get(),RankineItems.STEEL_HOE.get(),RankineItems.STAINLESS_STEEL_HOE.get(),RankineItems.NICKEL_SUPERALLOY_HOE.get(),RankineItems.COBALT_SUPERALLOY_HOE.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_HOE.get(),RankineItems.ROSE_GOLD_HOE.get(),RankineItems.BLUE_GOLD_HOE.get(),RankineItems.GREEN_GOLD_HOE.get(),RankineItems.WHITE_GOLD_HOE.get(),RankineItems.PURPLE_GOLD_HOE.get(),RankineItems.BLACK_GOLD_HOE.get(),RankineItems.AMALGAM_HOE.get());
        getOrCreateBuilder(RankineTags.Items.SHOVELS).add(RankineItems.FLINT_SHOVEL.get(),RankineItems.PEWTER_SHOVEL.get(),RankineItems.ALLOY_SHOVEL.get(),RankineItems.BRONZE_SHOVEL.get(),RankineItems.INVAR_SHOVEL.get(),RankineItems.STEEL_SHOVEL.get(),RankineItems.STAINLESS_STEEL_SHOVEL.get(),RankineItems.NICKEL_SUPERALLOY_SHOVEL.get(),RankineItems.COBALT_SUPERALLOY_SHOVEL.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_SHOVEL.get(),RankineItems.ROSE_GOLD_SHOVEL.get(),RankineItems.BLUE_GOLD_SHOVEL.get(),RankineItems.GREEN_GOLD_SHOVEL.get(),RankineItems.WHITE_GOLD_SHOVEL.get(),RankineItems.PURPLE_GOLD_SHOVEL.get(),RankineItems.BLACK_GOLD_SHOVEL.get(),RankineItems.AMALGAM_SHOVEL.get());
        getOrCreateBuilder(RankineTags.Items.SWORDS).add(RankineItems.PEWTER_SWORD.get(),RankineItems.ALLOY_SWORD.get(),RankineItems.BRONZE_SWORD.get(),RankineItems.INVAR_SWORD.get(),RankineItems.STEEL_SWORD.get(),RankineItems.STAINLESS_STEEL_SWORD.get(),RankineItems.NICKEL_SUPERALLOY_SWORD.get(),RankineItems.COBALT_SUPERALLOY_SWORD.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_SWORD.get(),RankineItems.ROSE_GOLD_SWORD.get(),RankineItems.BLUE_GOLD_SWORD.get(),RankineItems.GREEN_GOLD_SWORD.get(),RankineItems.WHITE_GOLD_SWORD.get(),RankineItems.PURPLE_GOLD_SWORD.get(),RankineItems.BLACK_GOLD_SWORD.get(),RankineItems.AMALGAM_SWORD.get());
        getOrCreateBuilder(RankineTags.Items.HAMMERS).add(RankineItems.PEWTER_HAMMER.get(),RankineItems.ALLOY_HAMMER.get(),RankineItems.BRONZE_HAMMER.get(),RankineItems.INVAR_HAMMER.get(),RankineItems.STEEL_HAMMER.get(),RankineItems.STAINLESS_STEEL_HAMMER.get(),RankineItems.NICKEL_SUPERALLOY_HAMMER.get(),RankineItems.COBALT_SUPERALLOY_HAMMER.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_HAMMER.get(),RankineItems.ROSE_GOLD_HAMMER.get(),RankineItems.BLUE_GOLD_HAMMER.get(),RankineItems.GREEN_GOLD_HAMMER.get(),RankineItems.WHITE_GOLD_HAMMER.get(),RankineItems.PURPLE_GOLD_HAMMER.get(),RankineItems.BLACK_GOLD_HAMMER.get(),RankineItems.AMALGAM_HAMMER.get());
        getOrCreateBuilder(RankineTags.Items.SPEARS).add(RankineItems.FLINT_SPEAR.get(),RankineItems.PEWTER_SPEAR.get(),RankineItems.ALLOY_SPEAR.get(),RankineItems.BRONZE_SPEAR.get(),RankineItems.INVAR_SPEAR.get(),RankineItems.STEEL_SPEAR.get(),RankineItems.STAINLESS_STEEL_SPEAR.get(),RankineItems.NICKEL_SUPERALLOY_SPEAR.get(),RankineItems.COBALT_SUPERALLOY_SPEAR.get(),RankineItems.TUNGSTEN_HEAVY_ALLOY_SPEAR.get(),RankineItems.ROSE_GOLD_SPEAR.get(),RankineItems.BLUE_GOLD_SPEAR.get(),RankineItems.GREEN_GOLD_SPEAR.get(),RankineItems.WHITE_GOLD_SPEAR.get(),RankineItems.PURPLE_GOLD_SPEAR.get(),RankineItems.BLACK_GOLD_SPEAR.get(),RankineItems.AMALGAM_SPEAR.get());
        getOrCreateBuilder(RankineTags.Items.CROWBARS).add(RankineItems.BRONZE_CROWBAR.get());
        getOrCreateBuilder(RankineTags.Items.KNIVES).add(RankineItems.FLINT_KNIFE.get(),RankineItems.PEWTER_KNIFE.get(),RankineItems.BRONZE_KNIFE.get());
        getOrCreateBuilder(RankineTags.Items.GOLD_PANS).add(RankineItems.WOODEN_GOLD_PAN.get(), RankineItems.PEWTER_GOLD_PAN.get(), RankineItems.STEEL_GOLD_PAN.get());
        getOrCreateBuilder(RankineTags.Items.SLUICING_TOOLS).addTag(RankineTags.Items.GOLD_PANS);
        getOrCreateBuilder(RankineTags.Items.BOWS).add(Items.BOW);
        getOrCreateBuilder(RankineTags.Items.FISHING_RODS).add(Items.FISHING_ROD);
        getOrCreateBuilder(RankineTags.Items.SHIELDS).add(Items.SHIELD);

        copy(RankineTags.Blocks.BRICKS, RankineTags.Items.BRICKS);
        copy(RankineTags.Blocks.BRICKS_SLAB, RankineTags.Items.BRICKS_SLABS);
        copy(RankineTags.Blocks.BRICKS_STAIRS, RankineTags.Items.BRICKS_STAIRS);
        copy(RankineTags.Blocks.BRICKS_WALL, RankineTags.Items.BRICKS_WALLS);
        copy(RankineTags.Blocks.BRICKS_VERTICAL_SLAB, RankineTags.Items.BRICKS_VERTICAL_SLABS);

        copy(RankineTags.Blocks.WOODEN_VERTICAL_SLABS, RankineTags.Items.WOODEN_VERTICAL_SLABS);
        copy(RankineTags.Blocks.VERTICAL_SLABS, RankineTags.Items.VERTICAL_SLABS);
        copy(RankineTags.Blocks.SHEETMETAL, RankineTags.Items.SHEETMETAL);
        copy(RankineTags.Blocks.SHEETMETAL_VERTICAL_SALBS, RankineTags.Items.SHEETMETAL_VERTICAL_SALBS);
        for (Block blk : RankineLists.SHEETMETALS) {
            String name = blk.getRegistryName().getPath();
            String baseName = Arrays.asList(name.split("_sheetmetal")).get(0);
            getOrCreateBuilder(ItemTags.makeWrapperTag(new ResourceLocation("forge", "sheetmetals/"+baseName).toString())).add(blk.asItem());
        }


        copy(RankineTags.Blocks.STONE_SLAB, RankineTags.Items.STONE_SLAB);
        copy(RankineTags.Blocks.POLISHED_STONE_SLAB, RankineTags.Items.POLISHED_STONE_SLAB);
        copy(RankineTags.Blocks.STONE_BRICKS_SLAB, RankineTags.Items.STONE_BRICKS_SLAB);
        copy(RankineTags.Blocks.STONE_STAIRS, RankineTags.Items.STONE_STAIRS);
        copy(RankineTags.Blocks.POLISHED_STONE_STAIRS, RankineTags.Items.POLISHED_STONE_STAIRS);
        copy(RankineTags.Blocks.STONE_BRICKS_STAIRS, RankineTags.Items.STONE_BRICKS_STAIRS);
        copy(RankineTags.Blocks.STONE_WALL, RankineTags.Items.STONE_WALL);
        copy(RankineTags.Blocks.POLISHED_STONE_WALL, RankineTags.Items.POLISHED_STONE_WALL);
        copy(RankineTags.Blocks.STONE_BRICKS_WALL, RankineTags.Items.STONE_BRICKS_WALL);
        copy(RankineTags.Blocks.STONE_VERTICAL_SLAB, RankineTags.Items.STONE_VERTICAL_SLAB);
        copy(RankineTags.Blocks.POLISHED_STONE_VERTICAL_SLAB, RankineTags.Items.POLISHED_STONE_VERTICAL_SLAB);
        copy(RankineTags.Blocks.STONE_BRICKS_VERTICAL_SLAB, RankineTags.Items.STONE_BRICKS_VERTICAL_SLAB);
        copy(BlockTags.STONE_PRESSURE_PLATES, RankineTags.Items.STONE_PRESSURE_PLATES);



        //plants
        getOrCreateBuilder(RankineTags.Items.SEEDS_FLOWER).add(RankineItems.FLOWER_SEEDS.get());
        getOrCreateBuilder(RankineTags.Items.SEEDS_ASPARAGUS).add(RankineItems.ASPARAGUS_SEEDS.get());
        getOrCreateBuilder(RankineTags.Items.SEEDS_CORN).add(RankineItems.CORN_SEEDS.get());
        getOrCreateBuilder(RankineTags.Items.SEEDS_COTTON).add(RankineItems.COTTON_SEEDS.get());
        getOrCreateBuilder(RankineTags.Items.SEEDS_RICE).add(RankineItems.RICE_SEEDS.get());
        getOrCreateBuilder(RankineTags.Items.SEEDS_JUTE).add(RankineItems.JUTE_SEEDS.get());
        getOrCreateBuilder(RankineTags.Items.SEEDS_CAMPHOR_BASIL).add(RankineItems.CAMPHOR_BASIL_SEEDS.get());
        getOrCreateBuilder(Tags.Items.SEEDS).addTags(RankineTags.Items.SEEDS_FLOWER,RankineTags.Items.SEEDS_ASPARAGUS,RankineTags.Items.SEEDS_CORN,RankineTags.Items.SEEDS_COTTON,RankineTags.Items.SEEDS_RICE,RankineTags.Items.SEEDS_JUTE,RankineTags.Items.SEEDS_CAMPHOR_BASIL);

        getOrCreateBuilder(RankineTags.Items.PINEAPPLE).add(RankineItems.PINEAPPLE.get(),RankineItems.PINEAPPLE_SLEEVES.get());

        getOrCreateBuilder(RankineTags.Items.BERRIES_BLACKBERRY).add(RankineItems.BLACKBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_BLUEBERRY).add(RankineItems.BLUEBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_CRANBERRY).add(RankineItems.CRANBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_ELDERBERRY).add(RankineItems.ELDERBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_JUNIPER).add(RankineItems.JUNIPER_BERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_RASPBERRY).add(RankineItems.RASPBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_SNOWBERRY).add(RankineItems.SNOWBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_STRAWBERRY).add(RankineItems.STRAWBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_POKEBERRY).add(RankineItems.POKEBERRIES.get());
        getOrCreateBuilder(RankineTags.Items.BERRIES_SWEET_BERRY).add(Items.SWEET_BERRIES);
        getOrCreateBuilder(RankineTags.Items.BERRIES).addTags(RankineTags.Items.BERRIES_BLACKBERRY,RankineTags.Items.BERRIES_BLUEBERRY,RankineTags.Items.BERRIES_CRANBERRY,RankineTags.Items.BERRIES_ELDERBERRY,RankineTags.Items.BERRIES_JUNIPER,RankineTags.Items.BERRIES_RASPBERRY,RankineTags.Items.BERRIES_SNOWBERRY,RankineTags.Items.BERRIES_SWEET_BERRY,RankineTags.Items.BERRIES_POKEBERRY,RankineTags.Items.BERRIES_STRAWBERRY);

        for (Item ITEM : RankineLists.GRAINS) {
            getOrCreateBuilder(RankineTags.Items.FLOUR).add(ITEM);
        }

        getOrCreateBuilder(RankineTags.Items.FELDSPAR).add(RankineItems.ORTHOCLASE_FELDSPAR.get(),RankineItems.ORTHOCLASE_FELDSPAR.get());
        getOrCreateBuilder(RankineTags.Items.SALT).add(RankineItems.SODIUM_CHLORIDE.get(),RankineItems.PINK_SALT.get());

        getOrCreateBuilder(RankineTags.Items.BREAD).add(Items.BREAD);
        getOrCreateBuilder(RankineTags.Items.SALTPETER).add(RankineItems.SALTPETER.get());
        getOrCreateBuilder(RankineTags.Items.COKE).add(RankineItems.COKE.get());
        getOrCreateBuilder(RankineTags.Items.CHEESE).add(RankineItems.CHEESE.get());
        getOrCreateBuilder(RankineTags.Items.DOUGH).add(RankineItems.DOUGH.get());
        getOrCreateBuilder(RankineTags.Items.GRAPHITE).add(RankineItems.GRAPHITE.get());
        getOrCreateBuilder(RankineTags.Items.ASH).add(RankineItems.ASH.get(),RankineItems.BONE_ASH.get(),RankineItems.POZZOLANA.get());
        getOrCreateBuilder(RankineTags.Items.RUBBER).add(RankineItems.VULCANIZED_RUBBER.get());
        getOrCreateBuilder(RankineTags.Items.SAWDUST).add(RankineItems.SAWDUST.get());
        getOrCreateBuilder(RankineTags.Items.SLAG).add(RankineItems.SLAG.get());
        getOrCreateBuilder(RankineTags.Items.TRONA).add(RankineItems.TRONA.get());
        getOrCreateBuilder(RankineTags.Items.SILICON).add(RankineItems.SILICON.get());
        getOrCreateBuilder(RankineTags.Items.ASTATINE).add(RankineItems.ASTATINE.get());
        getOrCreateBuilder(RankineTags.Items.SULFUR).add(RankineItems.SULFUR.get());
        getOrCreateBuilder(RankineTags.Items.PHOSPHORUS).add(RankineItems.PHOSPHORUS.get());
        getOrCreateBuilder(RankineTags.Items.FLUORITE).add(RankineItems.FLUORITE.get());
        getOrCreateBuilder(RankineTags.Items.ROPE).add(RankineItems.ROPE.get());
        getOrCreateBuilder(Tags.Items.STRING).add(RankineItems.ROPE.get());
        getOrCreateBuilder(RankineTags.Items.CLAY_BALLS).add(RankineItems.FIRE_CLAY_BALL.get(), Items.CLAY_BALL);

        getOrCreateBuilder(RankineTags.Items.RODS_GRAPHITE).add(RankineItems.GRAPHITE_ELECTRODE.get());
        getOrCreateBuilder(RankineTags.Items.RODS_CARBON).add(RankineItems.HARD_CARBON_ELECTRODE.get());
        getOrCreateBuilder(Tags.Items.RODS).addTags(RankineTags.Items.RODS_GRAPHITE,RankineTags.Items.RODS_CARBON).add(RankineItems.ALLOY_ROD.get());




        copy(RankineTags.Blocks.HARDENED_GLASS, RankineTags.Items.HARDENED_GLASS);
        copy(RankineTags.Blocks.CLAY, RankineTags.Items.CLAY);
        copy(Tags.Blocks.DIRT, RankineTags.Items.DIRT);
        copy(RankineTags.Blocks.TUFF, RankineTags.Items.TUFF);
        copy(RankineTags.Blocks.TERRACOTTA, RankineTags.Items.TERRACOTTA);
        copy(RankineTags.Blocks.GLAZED_TERRACOTTA, RankineTags.Items.GLAZED_TERRACOTTA);
        copy(RankineTags.Blocks.CONCRETE, RankineTags.Items.CONCRETE);
        copy(RankineTags.Blocks.CONCRETE_POWDER, RankineTags.Items.CONCRETE_POWDER);
        copy(RankineTags.Blocks.GEODES, RankineTags.Items.GEODES);
        copy(RankineTags.Blocks.LEDS, RankineTags.Items.LEDS);
        copy(RankineTags.Blocks.MINERAL_WOOL, RankineTags.Items.MINERAL_WOOL);
        copy(RankineTags.Blocks.GEODES, RankineTags.Items.GEODES);

        copy(RankineTags.Blocks.IGNEOUS_STONES, RankineTags.Items.IGNEOUS_STONES);
        copy(RankineTags.Blocks.METAMORPHIC_STONES, RankineTags.Items.METAMORPHIC_STONES);
        copy(RankineTags.Blocks.SEDIMENTARY_STONES, RankineTags.Items.SEDIMENTARY_STONES);


        copy(RankineTags.Blocks.STONES_DACITE, RankineTags.Items.STONES_DACITE);
        copy(RankineTags.Blocks.STONES_ANDESITE, RankineTags.Items.STONES_ANDESITE);
        copy(RankineTags.Blocks.STONES_BASALT, RankineTags.Items.STONES_BASALT);
        copy(RankineTags.Blocks.STONES_GRANITE, RankineTags.Items.STONES_GRANITE);
        copy(RankineTags.Blocks.STONES_SANDSTONE, RankineTags.Items.STONES_SANDSTONE);
        copy(RankineTags.Blocks.STONES_PEGMATITE, RankineTags.Items.STONES_PEGMATITE);
        copy(RankineTags.Blocks.STONES_BRECCIA, RankineTags.Items.STONES_BRECCIA);
        copy(RankineTags.Blocks.STONES_PERIDOTITE, RankineTags.Items.STONES_PERIDOTITE);
        copy(RankineTags.Blocks.STONES_PHYLITE, RankineTags.Items.STONES_PHYLITE);
        copy(RankineTags.Blocks.STONES_PORPHYRY, RankineTags.Items.STONES_PORPHYRY);
        copy(RankineTags.Blocks.STONES_PUMICE, RankineTags.Items.STONES_PUMICE);
        copy(RankineTags.Blocks.STONES_SCORIA, RankineTags.Items.STONES_SCORIA);
        copy(RankineTags.Blocks.STONES_SCHIST, RankineTags.Items.STONES_SCHIST);
        copy(RankineTags.Blocks.STONES_DOLOMITE, RankineTags.Items.STONES_DOLOMITE);
        copy(RankineTags.Blocks.STONES_MARBLE, RankineTags.Items.STONES_MARBLE);
        copy(RankineTags.Blocks.STONES_GABBRO, RankineTags.Items.STONES_GABBRO);
        copy(RankineTags.Blocks.STONES_MARLSTONE, RankineTags.Items.STONES_MARLSTONE);
        copy(RankineTags.Blocks.STONES_MUDSTONE, RankineTags.Items.STONES_MUDSTONE);
        copy(RankineTags.Blocks.STONES_RHYOLITE, RankineTags.Items.STONES_RHYOLITE);
        copy(RankineTags.Blocks.STONES_GRANODIORITE, RankineTags.Items.STONES_GRANODIORITE);
        copy(RankineTags.Blocks.STONES_KIMBERLITE, RankineTags.Items.STONES_KIMBERLITE);
        copy(RankineTags.Blocks.STONES_KOMATIITE, RankineTags.Items.STONES_KOMATIITE);
        copy(RankineTags.Blocks.STONES_GNEISS, RankineTags.Items.STONES_GNEISS);
        copy(RankineTags.Blocks.STONES_LIMESTONE, RankineTags.Items.STONES_LIMESTONE);
        copy(RankineTags.Blocks.STONES_SKARN, RankineTags.Items.STONES_SKARN);
        copy(RankineTags.Blocks.STONES_CHALK, RankineTags.Items.STONES_CHALK);
        copy(RankineTags.Blocks.STONES_SHALE, RankineTags.Items.STONES_SHALE);
        copy(RankineTags.Blocks.STONES_SILTSTONE, RankineTags.Items.STONES_SILTSTONE);
        copy(RankineTags.Blocks.STONES_SERPENTINITE, RankineTags.Items.STONES_SERPENTINITE);
        copy(RankineTags.Blocks.STONES_SLATE, RankineTags.Items.STONES_SLATE);
        copy(RankineTags.Blocks.STONES_SHONKINITE, RankineTags.Items.STONES_SHONKINITE);



    }

}
