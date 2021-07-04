package com.cannolicatfish.rankine.data.client;

import com.cannolicatfish.rankine.ProjectRankine;
import com.cannolicatfish.rankine.init.RankineItems;
import com.cannolicatfish.rankine.init.RankineLists;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nonnull;
import java.util.Arrays;

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



        for (String s : Arrays.asList("fiber_block","uncolored_concrete","roman_concrete","polished_roman_concrete","roman_concrete_bricks","checkered_marble","checkered_dacite","checkered_porphyry","clay_bricks","refractory_bricks","high_refractory_bricks","ultra_high_refractory_bricks")) {
            withExistingParent(s, modLoc("block/"+s));
            withExistingParent(s+"_slab", modLoc("block/"+s+"_slab"));
            withExistingParent(s+"_stairs", modLoc("block/"+s+"_stairs"));
            withExistingParent(s+"_wall", modLoc("block/"+s+"_wall_inventory"));
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
        for (String s : RankineLists.SHEETMETALS) {
            withExistingParent(s, modLoc("block/"+s));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
        }
        //MINERALS
        for (String s : RankineLists.MINERALS_AND_BLOCKS) {
            basicItem(s);
            withExistingParent(s+"_block", modLoc("block/"+s+"_block"));
        }
        //ALLOYS
        for (String s : RankineLists.ALLOYS) {
            if (s.equals("sodium_potassium_alloy") || s.equals("tungsten_heavy_alloy") || s.equals("cobalt_superalloy") || s.equals("nickel_superalloy") || s.equals("magnesium_alloy") ||s.equals("titanium_alloy")) {
                basicItem(s);
            } else {
                basicItem(s+"_alloy");
            }
            if (!s.equals("solder")) {
                //Blocks
                withExistingParent(s+"_block", modLoc("block/" + s + "_block"));
                //Pedestals
                withExistingParent(s + "_pedestal", modLoc("block/" + s + "_pedestal"));
                //Poles
                withExistingParent(s + "_pole", modLoc("block/" + s + "_pole"));
                //Bars
                //withExistingParent(s + "_bars", modLoc("block/" + s + "_bars"));
            }

        }
        getBuilder("cast_iron" + "_bars").parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + "cast_iron" + "_bars");

        //Wood Items
        for (String s : RankineLists.WOODS) {
            withExistingParent(s+"_planks", modLoc("block/"+s+"_planks"));
            withExistingParent(s+"_log", modLoc("block/"+s+"_log"));
            withExistingParent(s+"_wood", modLoc("block/"+s+"_wood"));
            withExistingParent("stripped_"+s+"_log", modLoc("block/"+"stripped_"+s+"_log"));
            withExistingParent("stripped_"+s+"_wood", modLoc("block/"+"stripped_"+s+"_wood"));
            withExistingParent(s+"_slab", modLoc("block/"+s+"_slab"));
            withExistingParent(s+"_stairs", modLoc("block/"+s+"_stairs"));
            basicItem(s+"_door");
            withExistingParent(s+"_trapdoor", modLoc("block/"+s+"_trapdoor_bottom"));
            withExistingParent(s+"_fence", modLoc("block/"+s+"_fence_inventory"));
            withExistingParent(s+"_fence_gate", modLoc("block/"+s+"_fence_gate"));
            withExistingParent(s+"_leaves", modLoc("block/"+s+"_leaves"));
            basicItemBlockTexture(s+"_sapling");
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
            // withExistingParent(s, modLoc("block/"+s+"_button"));
            withExistingParent(s, modLoc("block/"+s+"_pressure_plate"));
        }
        withExistingParent("bamboo_culms", modLoc("block/"+"bamboo_culms"));
        withExistingParent("bamboo_culms"+"_slab", modLoc("block/"+"bamboo_culms"+"_slab"));
        withExistingParent("bamboo_culms"+"_stairs", modLoc("block/"+"bamboo_culms"+"_stairs"));
        basicItem("bamboo_culms"+"_door");
        withExistingParent("bamboo_culms"+"_trapdoor", modLoc("block/"+"bamboo_culms"+"_trapdoor_bottom"));
        withExistingParent("bamboo_culms"+"_fence", modLoc("block/"+"bamboo_culms"+"_fence_inventory"));
        withExistingParent("bamboo_culms"+"_fence_gate", modLoc("block/"+"bamboo_culms"+"_fence_gate"));
        withExistingParent("bamboo_culms"+"_vertical_slab", modLoc("block/"+"bamboo_culms"+"_vertical_slab"));
        withExistingParent("bamboo_culms"+"_pressure_plate", modLoc("block/"+"bamboo_culms"+"_pressure_plate"));

        withExistingParent("bamboo"+"_planks", modLoc("block/"+"bamboo"+"_planks"));
        withExistingParent("bamboo"+"_slab", modLoc("block/"+"bamboo"+"_slab"));
        withExistingParent("bamboo"+"_stairs", modLoc("block/"+"bamboo"+"_stairs"));
        basicItem("bamboo"+"_door");
        withExistingParent("bamboo"+"_trapdoor", modLoc("block/"+"bamboo"+"_trapdoor_bottom"));
        withExistingParent("bamboo"+"_fence", modLoc("block/"+"bamboo"+"_fence_inventory"));
        withExistingParent("bamboo"+"_fence_gate", modLoc("block/"+"bamboo"+"_fence_gate"));
        withExistingParent("bamboo"+"_vertical_slab", modLoc("block/"+"bamboo"+"_vertical_slab"));
        withExistingParent("bamboo"+"_pressure_plate", modLoc("block/"+"bamboo"+"_pressure_plate"));


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
        for (String s : RankineLists.GEODES) {
            withExistingParent(s, modLoc("block/"+s));
        }

        //Stones
        for (String x : RankineLists.STONES) {
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


        //block models
        withExistingParent("mineral_wool", modLoc("block/mineral_wool"));
        withExistingParent("white_mineral_wool", modLoc("block/white_mineral_wool"));
        withExistingParent("orange_mineral_wool", modLoc("block/orange_mineral_wool"));
        withExistingParent("magenta_mineral_wool", modLoc("block/magenta_mineral_wool"));
        withExistingParent("light_blue_mineral_wool", modLoc("block/light_blue_mineral_wool"));
        withExistingParent("yellow_mineral_wool", modLoc("block/yellow_mineral_wool"));
        withExistingParent("lime_mineral_wool", modLoc("block/lime_mineral_wool"));
        withExistingParent("pink_mineral_wool", modLoc("block/pink_mineral_wool"));
        withExistingParent("gray_mineral_wool", modLoc("block/gray_mineral_wool"));
        withExistingParent("light_gray_mineral_wool", modLoc("block/light_gray_mineral_wool"));
        withExistingParent("cyan_mineral_wool", modLoc("block/cyan_mineral_wool"));
        withExistingParent("purple_mineral_wool", modLoc("block/purple_mineral_wool"));
        withExistingParent("blue_mineral_wool", modLoc("block/blue_mineral_wool"));
        withExistingParent("green_mineral_wool", modLoc("block/green_mineral_wool"));
        withExistingParent("brown_mineral_wool", modLoc("block/brown_mineral_wool"));
        withExistingParent("red_mineral_wool", modLoc("block/red_mineral_wool"));
        withExistingParent("black_mineral_wool", modLoc("block/black_mineral_wool"));
    }

    private ItemModelBuilder basicItem(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder basicItemBlockTexture(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + name);
    }

}
