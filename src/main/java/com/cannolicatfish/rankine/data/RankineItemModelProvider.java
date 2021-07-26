package com.cannolicatfish.rankine.data;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RankineItemModelProvider extends ItemModelProvider {

    public RankineItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, ProjectRankine.MODID, existingFileHelper);
    }

    @Nonnull
    @Override
    public String getName() {
        return "Project Rankine - Item Models";
    }

    @Override
    protected void registerModels() {
        basicItem(RankineItems.ALUMINA.get().getRegistryName().getPath());
        basicItem(RankineItems.BLACK_WALNUT.get().getRegistryName().getPath());

        basicItemBlockTexture(RankineItems.ORANGE_LILY.get(), modLoc("block/" + "orange_lily_top"));
        basicItemBlockTexture(RankineItems.RED_LILY.get(), modLoc("block/" + "red_lily_top"));
        basicItemBlockTexture(RankineItems.WHITE_LILY.get(), modLoc("block/" + "white_lily_top"));
        basicItemBlockTexture(RankineItems.GOLDENROD.get(), modLoc("block/" + "goldenrod_top"));
        basicItemBlockTexture(RankineItems.BLUE_MORNING_GLORY.get(), modLoc("block/" + "blue_morning_glory_top"));
        basicItemBlockTexture(RankineItems.PURPLE_MORNING_GLORY.get(), modLoc("block/" + "purple_morning_glory_top"));
        basicItemBlockTexture(RankineItems.BLACK_MORNING_GLORY.get(), modLoc("block/" + "black_morning_glory_top"));

        for (String s : Arrays.asList("cast_iron_support","fiber_block","uncolored_concrete","roman_concrete","polished_roman_concrete","roman_concrete_bricks","checkered_marble","clay_bricks","refractory_bricks","high_refractory_bricks","ultra_high_refractory_bricks")) {
            if (!s.equals("cast_iron_support")) {
                withExistingParent(s+"_wall", modLoc("block/"+s+"_wall_inventory"));
            }
            withExistingParent(s, modLoc("block/"+s));
            withExistingParent(s+"_slab", modLoc("block/"+s+"_slab"));
            withExistingParent(s+"_stairs", modLoc("block/"+s+"_stairs"));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
        }


        //EARTHY BLOCKS
        for (String s : RankineLists.NORMAL_BLOCKS) {
            withExistingParent(s, modLoc("block/"+s));
        }
        for (String s : RankineLists.ROTATION_BLOCKS) {
            withExistingParent(s, modLoc("block/"+s));
        }
        //SHEETMETALS
        for (String s : RankineLists.SHEETMETALS_S) {
            withExistingParent(s, modLoc("block/"+s));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
        }
        //MINERALS
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            basicItem(s);
            withExistingParent(s+"_block", modLoc("block/"+s+"_block"));
        }
        //ALLOYS
        for (Item ALLOY : RankineLists.ALLOYS) {
            String name = ALLOY.getRegistryName().getPath();
            basicItem(name);
        }
        for (Block ALLOY : RankineLists.ALLOY_BLOCKS) {
            String name = ALLOY.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block ALLOY : RankineLists.ALLOY_PEDESTALS) {
            String name = ALLOY.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block ALLOY : RankineLists.ALLOY_POLES) {
            String name = ALLOY.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        getBuilder("cast_iron" + "_bars").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + "cast_iron" + "_bars");

        for (Block SOIL : RankineLists.SOILS) {
            String name = SOIL.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block GRASSY_SOIL : RankineLists.GRASSY_SOILS) {
            String name = GRASSY_SOIL.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }

        for (String s : RankineLists.WOODS_S) {
            if (s.equals("bamboo")) {
                withExistingParent(s+"_planks", modLoc("block/"+s+"_planks"));
            } else if (s.equals("bamboo_culms")) {
                withExistingParent(s, modLoc("block/"+s));
            } else {
                if (!s.equals("petrified_chorus")) {
                    withExistingParent(s+"_leaves", modLoc("block/"+s+"_leaves"));
                    basicItemBlockTexture(ForgeRegistries.ITEMS.getValue(modLoc(s+"_sapling")), modLoc("block/"+s+"_sapling"));
                }
                withExistingParent(s+"_planks", modLoc("block/"+s+"_planks"));
                withExistingParent(s+"_log", modLoc("block/"+s+"_log"));
                withExistingParent(s+"_wood", modLoc("block/"+s+"_wood"));
                withExistingParent("stripped_"+s+"_log", modLoc("block/"+"stripped_"+s+"_log"));
                withExistingParent("stripped_"+s+"_wood", modLoc("block/"+"stripped_"+s+"_wood"));

            }
            withExistingParent(s+"_slab", modLoc("block/"+s+"_slab"));
            withExistingParent(s+"_stairs", modLoc("block/"+s+"_stairs"));
            basicItem(s+"_door");
            basicItem(s+"_boat");
            withExistingParent(s+"_trapdoor", modLoc("block/"+s+"_trapdoor_bottom"));
            withExistingParent(s+"_fence", modLoc("block/"+s+"_fence_inventory"));
            withExistingParent(s+"_fence_gate", modLoc("block/"+s+"_fence_gate"));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
            withExistingParent(s+"_button", modLoc("block/"+s+"_button_inventory"));
            withExistingParent(s+"_pressure_plate", modLoc("block/"+s+"_pressure_plate_up"));
        }


        //ORES
        for (String s : RankineLists.ORES) {
            withExistingParent(s, modLoc("block/"+s+"0"));
        }

        //ELEMENTS
        for (String s : RankineLists.ELEMENTS) {
            basicItem(s+"_nugget");
            if (s.equals("silicon") || s.equals("phosphorus") || s.equals("astatine") || s.equals("sulfur")) {
                basicItem(s);
            } else {
                basicItem(s+"_ingot");
            }
            withExistingParent(s+"_block", modLoc("block/"+s+"_block"));
        }

        //GEODES
        basicItem(RankineItems.UNCUT_GEODE.get().getRegistryName().getPath());
        for (Block blk : RankineLists.GEODES) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
/*
        //Stones
        for (String x : RankineLists.STONESSS) {
            for (String s : Arrays.asList(x,"polished_"+x,x+"_bricks")) {
                withExistingParent(s, modLoc("block/" + s));
                withExistingParent(s + "_slab", modLoc("block/" + s + "_slab"));
                withExistingParent(s + "_stairs", modLoc("block/" + s + "_stairs"));
                withExistingParent(s + "_wall", modLoc("block/" + s + "_wall_inventory"));
                withExistingParent(s + "_vertical_slab", modLoc("block/" + s + "_vertical_slab"));
            }
            withExistingParent(x + "_pressure_plate", modLoc("block/" + x + "_pressure_plate_up"));
            withExistingParent(x + "_bricks_pressure_plate", modLoc("block/" + x + "_bricks_pressure_plate_up"));
            withExistingParent(x + "_button", modLoc("block/" + x + "_button_inventory"));
        }
        
 */

        //STONES
        for (Block blk : Stream.of(RankineLists.STONE, RankineLists.POLISHED_STONE, RankineLists.STONE_BRICKS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_SLAB, RankineLists.POLISHED_STONE_SLAB, RankineLists.STONE_BRICKS_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_STAIRS, RankineLists.POLISHED_STONE_STAIRS, RankineLists.STONE_BRICKS_STAIRS).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_WALL, RankineLists.POLISHED_STONE_WALL, RankineLists.STONE_BRICKS_WALL).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_inventory"));
        }
        for (Block blk : Stream.of(RankineLists.STONE_VERTICAL_SLAB, RankineLists.POLISHED_STONE_VERTICAL_SLAB, RankineLists.STONE_BRICKS_VERTICAL_SLAB).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : Stream.of(RankineLists.STONE_PRESSURE_PLATE, RankineLists.STONE_BRICKS_PRESSURE_PLATE).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_up"));
        }
        for (Block blk : Stream.of(RankineLists.STONE_BUTTON).flatMap(Collection::stream).collect(Collectors.toList())) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name + "_inventory"));
        }
        


        for (Block blk : RankineLists.MINERAL_WOOL) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : RankineLists.FIBER_BLOCK) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : RankineLists.FIBER_MAT) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/"+name));
        }
        for (Block blk : RankineLists.LEDS) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }
        for (Block blk : RankineLists.GAS_BLOCKS) {
            String name = blk.getRegistryName().getPath();
            withExistingParent(name, modLoc("block/" + name));
        }

    }

    private ItemModelBuilder basicItem(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder basicItemBlockTexture(Item item, ResourceLocation texture) {
        return getBuilder(item.getRegistryName().getPath()).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", texture);
    }

}
