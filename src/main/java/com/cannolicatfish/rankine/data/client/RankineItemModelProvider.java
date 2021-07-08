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
            if (s.equals("bamboo")) {
                withExistingParent(s+"_planks", modLoc("block/"+s+"_planks"));
                withExistingParent(s+"_wall", modLoc("block/"+s+"_planks"));
            } else if (s.equals("bamboo_culms")) {
                withExistingParent(s, modLoc("block/"+s));
                withExistingParent(s+"_wall", modLoc("block/"+s));
            } else {
                withExistingParent(s+"_planks", modLoc("block/"+s+"_planks"));
                withExistingParent(s+"_log", modLoc("block/"+s+"_log"));
                withExistingParent(s+"_wood", modLoc("block/"+s+"_wood"));
                withExistingParent("stripped_"+s+"_log", modLoc("block/"+"stripped_"+s+"_log"));
                withExistingParent("stripped_"+s+"_wood", modLoc("block/"+"stripped_"+s+"_wood"));
                withExistingParent(s+"_leaves", modLoc("block/"+s+"_leaves"));
                basicItemBlockTexture(s+"_sapling");
            }
            withExistingParent(s+"_slab", modLoc("block/"+s+"_slab"));
            withExistingParent(s+"_stairs", modLoc("block/"+s+"_stairs"));
            basicItem(s+"_door");
            withExistingParent(s+"_trapdoor", modLoc("block/"+s+"_trapdoor_bottom"));
            withExistingParent(s+"_fence", modLoc("block/"+s+"_fence_inventory"));
            withExistingParent(s+"_fence_gate", modLoc("block/"+s+"_fence_gate"));
            withExistingParent(s+"_vertical_slab", modLoc("block/"+s+"_vertical_slab"));
            withExistingParent(s, modLoc("block/"+s+"_button_inventory"));
            withExistingParent(s, modLoc("block/"+s+"_pressure_plate"));
        }


        //ORES
        for (String s : RankineLists.ORES) {
            withExistingParent(s, modLoc("block/"+s+"0"));
        }
        //GAS BLOCKS
        for (String s : RankineLists.GAS_BLOCKS) {
            withExistingParent(s, modLoc("block/"+s));
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



        for (String s : RankineLists.MINERAL_WOOL) {
            withExistingParent(s, modLoc("block/"+s));
        }
        for (String s : RankineLists.FIBER_BLOCKS) {
            withExistingParent(s, modLoc("block/"+s));
        }
        for (String s : RankineLists.FIBER_MATS) {
            withExistingParent(s, modLoc("block/"+s));
        }

    }

    private ItemModelBuilder basicItem(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "item/" + name);
    }

    private ItemModelBuilder basicItemBlockTexture(String name) {
        return getBuilder(name).parent(getExistingFile(mcLoc("item/generated"))).texture("layer0", "block/" + name);
    }

}
